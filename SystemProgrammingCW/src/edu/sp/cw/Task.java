package edu.sp.cw;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Task {
    int id;
    int leadTime;
    List<Task> dependentTasks = new ArrayList<>();
    List<Task> parentTasks = new ArrayList<>();

    public Task(int id, int leadTime) {
        this.id = id;
        this.leadTime = leadTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        List<Integer> indexesOfDependentTasks = dependentTasks.stream()
                .map(task -> task.id).collect(Collectors.toList());
        List<Integer> indexesOfParentTasks = parentTasks.stream()
                .map(task -> task.id).collect(Collectors.toList());
        return String.format("Task{id=%d, leadTime=%d, dependentTasks=%s, parents=%s}",
                id, leadTime, indexesOfDependentTasks, indexesOfParentTasks);
    }
}
