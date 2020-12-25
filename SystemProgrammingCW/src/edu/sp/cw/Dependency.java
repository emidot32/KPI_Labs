package edu.sp.cw;

class Dependency {
    Task fromTask;
    Task toTask;
    int weight;

    public Dependency(Task fromTask, Task toTask, int weight) {
        this.fromTask = fromTask;
        this.toTask = toTask;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("[%d -(%d)-> %d]\n", fromTask.id+1, weight, toTask.id+1);
    }
}
