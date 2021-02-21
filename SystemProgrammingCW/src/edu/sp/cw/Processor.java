package edu.sp.cw;

import java.util.*;
import java.util.stream.Collectors;


public class Processor {
    public int id;
    public Map<Integer, Tact> execution;
    public List<Processor> neighbours;
    public double workloadCoef = 0;
    public int numberOfForwarding = 0;
    public double hopToWorkloadRatio = 0;

    public Processor(int id) {
        this.execution = new HashMap<>();
        this.neighbours = new ArrayList<>();
        this.id = id;
    }

    public boolean hasNeighbour(Processor neighbour_) {
        return neighbours.stream().anyMatch(neighbour -> neighbour.equals(neighbour_));
    }

    public boolean hasTask(Task task_) {
        return getTasks().stream().anyMatch(task_::equals);
    }

    public List<Task> getTasks(){
        Set<Task> tasks = new HashSet<>();
        execution.forEach((tact, taskOnTact) -> {
            if (taskOnTact != null)
                tasks.add(taskOnTact.task);
        });
        return new ArrayList<>(tasks);
    }

    public int getFreeTact(boolean forTaskLoad){
        int lastBusyTact = 0;
        for (Map.Entry<Integer, Tact> entry : execution.entrySet()) {
            Integer tactNumber = entry.getKey();
            Tact tactObject = entry.getValue();
            if (forTaskLoad){
                if (!tactObject.isFreeForTaskLoad()){
                    lastBusyTact = tactNumber;
                }
            } else {
                if (!tactObject.isFreeForSendReceive()){
                    lastBusyTact = tactNumber;
                }
            }
        }
        return lastBusyTact+1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Processor processor = (Processor) o;
        return id == processor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        List<String> neighbourIds = neighbours.stream()
                .map(neighbour -> Integer.toString(neighbour.id)).collect(Collectors.toList());
        return "Processor{" +
                "id=" + id +
                ", neighbours=" + neighbourIds +
                '}';
    }
}
