package edu.sp.cw;

import edu.sp.cw.topologies.Topology;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Planner {
    final int NUMBER_OF_PROCESSORS;
    private final int NUMBER_OF_TASKS;
    private Integer count = 1;
    private List<Task> tasks;
    private List<Dependency> dependencies = new ArrayList<>();
    private List<Processor> processors;
    List<String> modelingResult = new ArrayList<>();
    private final String fileName;

    public Planner(int[][] adjacencyMatrix, int[] tasksWeights, Topology topology, String fileName, boolean part) {
        NUMBER_OF_TASKS = adjacencyMatrix.length;
        getDataFromAdjacencyMatrix(adjacencyMatrix, tasksWeights);
        processors = topology.createTopology(part);
        this.fileName = fileName;
        this.NUMBER_OF_PROCESSORS = topology.NUMBER_OF_PROCESSORS;
    }

    void getDataFromAdjacencyMatrix(int[][] adjacencyMatrix, int[] tasksWeights) {
        tasks = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_TASKS; i++) {
            Task task = new Task(i, tasksWeights[i-1]);
            tasks.add(task);
        }
        for (int i = 0; i < NUMBER_OF_TASKS; i++) {
            for (int j = 0; j < NUMBER_OF_TASKS; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    tasks.get(i).dependentTasks.add(tasks.get(j));
                    dependencies.add(new Dependency(tasks.get(i), tasks.get(j), adjacencyMatrix[i][j]));
                }
                if (adjacencyMatrix[j][i] != 0) {
                    tasks.get(i).parentTasks.add(tasks.get(j));
                }
            }
        }
    }

    // Bubble Scheduling and Allocation algorithm for task allocation for multiprocessing system
    public void run(){
        initWrite();
        //processors.sort(Comparator.comparingInt(p -> p.neighbours.size()));
        for (Task task : tasks) {
            for (int j = 0; j < task.leadTime; j++) {
                Tact tact = new Tact(task, null, null);
                for (int i = 1; i < processors.size(); i++) {
                    processors.get(i).execution.put(count, new Tact());
                }
                processors.get(0).execution.put(count++, tact);
            }
        }
        for (Processor processor: processors){
            List<Task> tasksOfPivot = processor.getTasks();
            for (int i = 1; i < tasksOfPivot.size(); i++) {
                Task task = tasksOfPivot.get(i);
                handleForwarding(task, processor, false, true);
                int oldST = getStartTimeOrEndTime(task, processor, true);
                Map.Entry<Processor, Integer> newBetterSt = getBetterSt(task, processor);
                if (newBetterSt.getValue() < oldST){
                    migrateToOtherProcessor(task, newBetterSt, processor);
                } else {
                    handleForwarding(task, processor, true, false);
                }
            }
            break;
        }
        saveModelingResults();
        writeToFile(fileName);
    }

    private void migrateToOtherProcessor(Task task, Map.Entry<Processor, Integer> newProcessorAndTime, Processor oldProcessor){
        Processor newProc = newProcessorAndTime.getKey();
        int tactNum = newProcessorAndTime.getValue();
        for (int j = 0; j < task.leadTime; j++) {
            newProc.execution.get(j+tactNum).task = task;
        }
        for (int i = 1; i <= oldProcessor.execution.size(); i++) {
            if (task.equals(oldProcessor.execution.get(i).task)) {
                shiftExecutionUp(oldProcessor, i, task.leadTime);
                break;
            }
        }
        handleForwarding(task, newProc, true, false);
        task.dependentTasks.forEach(child -> {
            Processor childProcessor = getProcessorOfTask(child);
            if (!newProc.equals(childProcessor)){
                int weight = getDependencyWeight(child, task)*steps(newProc, childProcessor, 1);
                shiftDownOrUpForForwarding(child, task, childProcessor, newProc, weight, false);
            }
        });
    }

    private void handleForwarding(Task task, Processor processor, boolean writeSendReceive, boolean shiftUp) {
        for (Task parent: task.parentTasks) {
            Processor parentProcessor = getProcessorOfTask(parent);
            if (!processor.equals(parentProcessor)) {
                int weight = getDependencyWeight(task, parent)*steps(parentProcessor, processor, 1);
                int startTime = shiftDownOrUpForForwarding(task, parent, processor, parentProcessor, weight, shiftUp);
                if (writeSendReceive) {
                    for (int i = startTime-1; i >= startTime-weight; i--) {
                        parentProcessor.execution.get(i).sendData=parent.id;
                        processor.execution.get(i).receiveData=parent.id;
                    }
                }
            }
        }
    }

    private int shiftDownOrUpForForwarding(Task task, Task parent, Processor processor, Processor parentProcessor, int weight, boolean shiftUp) {
        int startTime = getStartTimeOrEndTime(task, processor, true);
        int parentEndTime = findTaskAndGetStOrEt(parent, false);
        int parentEndTimeWithForwarding = parentEndTime + getTactsWithSendingReceivingDataAfterEt(parentProcessor, parentEndTime);
        int tactDifs =  startTime - parentEndTimeWithForwarding;
        if (tactDifs < weight) {
            shiftExecutionDown(processor, startTime, weight-tactDifs);
            startTime += weight-tactDifs;
        }
        int shiftDown = Math.max(getShiftDownForTask(processor, startTime, weight, parentEndTimeWithForwarding),
                                 getShiftDownForTask(parentProcessor, startTime, weight, parentEndTimeWithForwarding));
        shiftExecutionDown(processor, startTime, shiftDown);
        startTime += shiftDown;
        if (shiftDown == 0 && tactDifs > weight && shiftUp) {
            int availableStartTime = getNearestFreeTactForLoad(startTime-tactDifs+weight, startTime, processor);
            shiftExecutionUp(processor, availableStartTime, startTime-availableStartTime);
            startTime = availableStartTime;
        }
        return startTime;
    }

    private int getShiftDownForTask(Processor processor, int startTime, int weight, int parentEndTime) {
        int shiftDown = weight;
        int endTime = Math.max(startTime-weight, parentEndTime);
        for (int i = startTime-1; i >= endTime; i--) {
            if (processor.execution.get(i).isFreeForSendReceive())
                shiftDown--;
            else
                break;
        }
        return shiftDown;
    }

    private Map.Entry<Processor, Integer> getBetterSt(Task task, Processor oldProcessor) {
        Map<Processor, Integer> tactsOnProcessors = new HashMap<>();
        for(Processor processor: processors){
            if (!processor.equals(oldProcessor)){
                tactsOnProcessors.put(processor, Math.max(getDAT(task, processor), processor.getFreeTact(true)));
            }
        }
        return Collections.min(tactsOnProcessors.entrySet(), Map.Entry.comparingByValue());
    }

    private int getDAT(Task task, Processor processor) {
        List<Integer> times = new ArrayList<>();
        int sumTime = 0;
        for (Task parent: task.parentTasks) {
            Processor parentProcessor = getProcessorOfTask(parent);
            int taskEndTime = findTaskAndGetStOrEt(parent, false);
            if (processor.equals(parentProcessor))
                times.add(taskEndTime);
            else {
                Processor processorOfParent = getProcessorOfTask(parent);
                sumTime = Math.max(taskEndTime + getTactsWithSendingReceivingDataAfterEt(processorOfParent, taskEndTime), sumTime) +
                        getDependencyWeight(task, parent) * steps(processor, processorOfParent, 1);
                times.add(sumTime);
            }
        }
        return times.stream().mapToInt(Integer::intValue).max().orElse(-1)+1;
    }

    private void shiftExecutionDown(Processor processor, int start, int shift) {
        for (int i = processor.execution.size(); i >= start; i--) {
            processor.execution.put(i+shift, processor.execution.get(i));
        }
        for (int i = start; i < start+shift; i++) {
            processor.execution.put(i, new Tact());
        }
    }

    private int getNearestFreeTactForLoad(int startTime, int endTime, Processor processor) {
        for (int j = endTime-1; j >= startTime; j--) {
            if (!processor.execution.get(j).isFullFree())
                return j+1;
        }
        return startTime;
    }

    private void shiftExecutionUp(Processor processor, int start, int shift) {
        for (int i = start; i <= processor.execution.size(); i++) {
            processor.execution.put(i, processor.execution.get(i + shift));
        }
        for (int i = processor.execution.size(); i > processor.execution.size()-shift; i--) {
            processor.execution.put(i, new Tact());
        }
    }

    private int getTactsWithSendingReceivingDataAfterEt(Processor processor, int taskEndTime) {
        int count = 0;
        int i = taskEndTime+1;
        while (!(processor.execution.get(i) != null && processor.execution.get(i).isFreeForSendReceive())) {
            count++;
            i++;
        }
        return count;
    }

    private int getStartTimeOrEndTime(Task task, Processor processor, boolean start){
        List<Integer> tacts = new ArrayList<>();
        processor.execution.forEach((tact, taskOnTact) -> {
            if (taskOnTact != null && task.equals(taskOnTact.task)) tacts.add(tact);
        });
        if (start)
            return tacts.stream().mapToInt(v -> v).min().orElse(-1);
        return tacts.stream().mapToInt(v -> v).max().orElse(-1);
    }

    private int findTaskAndGetStOrEt(Task task, boolean start){
        for (Processor processor: processors){
            if (processor.hasTask(task)){
                return getStartTimeOrEndTime(task, processor, start);
            }
        }
        return -1;
    }

    private Processor getProcessorOfTask(Task task){
        for (Processor processor: processors){
            if (processor.hasTask(task)) return processor;
        }
        return null;
    }

    private int steps(Processor processor1, Processor processor2, int count){
        List<Integer> steps = new ArrayList<>();
        if (processor1.hasNeighbour(processor2) || count > NUMBER_OF_PROCESSORS) return count;
        else {
            count++;
            for (Processor neighbour: processor1.neighbours) {
                steps.add(steps(neighbour, processor2, count));
            }
            return steps.stream().mapToInt(Integer::intValue).min().orElse(NUMBER_OF_PROCESSORS-1);
        }
    }

    private void initWrite(){
        StringBuilder line = new StringBuilder(String.format("%5s;", "t"));
        for (int i = 1; i <= NUMBER_OF_PROCESSORS; i++) {
            line.append(String.format("%11s;", "P"+i));
        }
        modelingResult.add(line.toString());
    }

    private void saveModelingResults(){
        for (int i = 1; i <= count; i++) {
            StringBuilder tactLine = new StringBuilder(String.format("%5d;", i));
            for (Processor processor: processors){
                tactLine.append(String.format("%11s;",processor.execution.get(i)));
            }
            modelingResult.add(tactLine.toString());
        }
    }

    private int getDependencyWeight(Task task1, Task task2) {
        for (Dependency dependency : dependencies) {
            if ((dependency.fromTask.equals(task1)) && (dependency.toTask.equals(task2))) {
                return dependency.weight;
            }
            if ((dependency.fromTask.equals(task2)) && (dependency.toTask.equals(task1))) {
                return dependency.weight;
            }
        }
        return -1;
    }

    private void writeToFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists())
                file.delete();
            BufferedWriter newFile = new BufferedWriter(new FileWriter(fileName));
            for (String line : modelingResult) {
                newFile.write(line + "\n");
            }
            newFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
