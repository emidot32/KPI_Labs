package edu.sp.cw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Planner {
    private final int NUMBER_OF_PROCESSORS = 4;;
    private final int numberOfTasks;
    private Integer count = 1;
    private List<Task> tasks;
    private List<Dependency> dependencies = new ArrayList<>();
    private List<Processor> processors = new ArrayList<>();
    List<String> modelingResult = new ArrayList<>();

    public Planner(int[][] adjacencyMatrix, int[] tasksWeights) {
        numberOfTasks = adjacencyMatrix.length;
        getDataFromAdjacencyMatrix(adjacencyMatrix, tasksWeights);
        IntStream.range(0, NUMBER_OF_PROCESSORS)
                .forEach(i -> processors.add(new Processor(i+1)));
        createTopology();
    }

    // Method for topology setting. In this case - vector or linear.
    private void createTopology(){
        for (int i = 0; i < processors.size(); i++) {
            List<Processor> neighboursOfCurrentProc = processors.get(i).neighbours;
            if (i == 0){
                neighboursOfCurrentProc.add(processors.get(i+1));
            } else if (i == processors.size()-1){
                neighboursOfCurrentProc.add(processors.get(i-1));
            } else {
                neighboursOfCurrentProc.add(processors.get(i-1));
                neighboursOfCurrentProc.add(processors.get(i+1));
            }
        }
    }

    // Get the data from the adjacency matrix and tasks weights array and set to corresponding fields
    void getDataFromAdjacencyMatrix(int[][] adjacencyMatrix, int[] tasksWeights) {
        tasks = new ArrayList<>();
        for (int i = 1; i <= numberOfTasks; i++) {
            Task task = new Task(i, tasksWeights[i-1]);
            tasks.add(task);
        }
        for (int i = 0; i < numberOfTasks; i++) {
            for (int j = 0; j < numberOfTasks; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    tasks.get(i).dependentTasks.add(tasks.get(j));
                    dependencies.add(new Dependency(tasks.get(i), tasks.get(j), adjacencyMatrix[i][j]));
                }
                if (adjacencyMatrix[j][i] != 0) {
                    tasks.get(i).parentTasks.add(tasks.get(j));
                }
            }
        }
        //tasks.forEach( task -> System.out.format("[%d: %s]\n", task.id, task));
        //System.out.println(dependencies);
    }

    public void run(){
        initWrite();
        //processors.sort(Comparator.comparingInt(p -> p.neighbours.size()));
        for (Task task : tasks) {
            Tact tact = new Tact(task, null, null);
            Tact emptyTact = new Tact();
            for (int j = 0; j < task.leadTime; j++) {
                for (Processor processor : processors) {
                    processor.execution.put(count, emptyTact);
                }
                processors.get(0).execution.put(count++, tact);
            }
        }
        //processors.get(1).execution.forEach((tact, taskOnTact) -> System.out.format("%d: %s\n", tact, taskOnTact));
        for (Processor processor: processors){
            List<Task> tasksOfPivot = processor.getTasks();
            for (int i = 1; i < tasksOfPivot.size(); i++) {
                Map<Integer, Tact> execution = processor.execution;
                Task task = tasksOfPivot.get(i);
                int oldST = getStartTimeOrEndTime(task, processor, true);
                int dat = getDAT(task, processor);
                if (oldST > dat){
                    Map.Entry<Processor, Integer> newBetterSt = getBetterSt(task, processor);
                    if (newBetterSt.getValue() < oldST){
                        migrateToOtherProcessor(task, newBetterSt, processor);
                    }
                }
            }
        }
        saveModelingResults();
        writeToFile(modelingResult);
    }

    private void migrateToOtherProcessor(Task task, Map.Entry<Processor, Integer> newProcessorAndTime, Processor oldProcessor){
        Processor newProc = newProcessorAndTime.getKey();
        int tactNum = newProcessorAndTime.getValue();
        for (int j = 0; j < task.leadTime; j++) {
            newProc.execution.put(j+tactNum, new Tact(task, null, null));
        }
        for (int i = 1; i <= oldProcessor.execution.size(); i++) {
            if (oldProcessor.execution.get(i).task.equals(task)) {
                shiftExecutionUp(oldProcessor, i, task.leadTime);
                break;
            }
        }
        task.parentTasks.forEach(parent -> {
            if (!isParentTaskOnSameProcessor(parent, task)){
                Processor parentProc = getProcessorOfTask(parent);
                int weight = getDependencyWeight(task, parent)*steps(parentProc, newProc, 1);
                int parentEndTime = getStartTimeOrEndTime(parent, parentProc, false);
                for (int i = 1; i <= weight; i++) {
                    parentProc.execution.get(parentEndTime+i).sendData=parent.id;
                    newProc.execution.get(parentEndTime+i).receiveData=parent.id;
                }
            }
        });
        task.dependentTasks.forEach(child -> {
            if (!isParentTaskOnSameProcessor(child, task)){
                Processor childProc = getProcessorOfTask(child);
                int weight = getDependencyWeight(child, task)*steps(newProc, childProc, 1);
                int childStartTime = getStartTimeOrEndTime(child, childProc, true);
                int parentEndTime = getStartTimeOrEndTime(task, newProc, false);
                int tactDifs =  childStartTime - parentEndTime;
                if (tactDifs < weight) {
                    shiftExecutionDown(childProc, childStartTime, weight-tactDifs);
                }
                for (int i = 1; i <= weight; i++) {
                    newProc.execution.get(parentEndTime+i).sendData=task.id;
                    System.out.println((parentEndTime+i) +": "+ childProc.execution.get(parentEndTime+i));
                    childProc.execution.get(parentEndTime+i).receiveData=task.id;
                }
            }
        });
    }

    private void shiftExecutionUp(Processor processor, int start, int shift) {
        for (int i = start; i <= processor.execution.size(); i++) {
            processor.execution.put(i, processor.execution.get(i+shift));
        }
        for (int i = processor.execution.size(); i > processor.execution.size()-shift; i--) {
            processor.execution.put(i, new Tact());
        }
    }

    private void shiftExecutionDown(Processor processor, int start, int shift) {
        for (int i = processor.execution.size(); i >= start; i--) {
            processor.execution.put(i+shift, processor.execution.get(i));
        }
        for (int i = start; i < start+shift; i++) {
            processor.execution.put(i, new Tact());
        }
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
        return task.parentTasks.stream()
                .mapToInt(parent -> {
                    if (isParentTaskOnSameProcessor(task, parent))
                        return findTaskAndGetStOrEt(parent, false);
                    Processor processorOfParent = getProcessorOfTask(parent);
                    return findTaskAndGetStOrEt(parent, false) + getDependencyWeight(task, parent)
                            * steps(processor, processorOfParent, 1);
                })
                .max()
                .orElse(-1)+1;
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

    private boolean isParentTaskOnSameProcessor(Task task, Task parent){
        for (Processor processor: processors){
            return (processor.hasTask(task) && processor.hasTask(parent));
        }
        return false;
    }

    private int steps(Processor processor1, Processor processor2, int count){
        if (processor1.hasNeighbour(processor2) || count > NUMBER_OF_PROCESSORS) return count;
        else {
            List<Processor> neighbours = processor1.neighbours;
            count++;
            return Math.min(steps(neighbours.get(0), processor2, count),
                    steps(neighbours.get(neighbours.size()-1), processor2, count));
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

    private void writeToFile(List<String> results) {
        String fileName = "results.csv";
        try {
            BufferedWriter newFile = new BufferedWriter(new FileWriter(fileName));
            for (String line : results) {
                newFile.write(line + "\n");
            }
            newFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
