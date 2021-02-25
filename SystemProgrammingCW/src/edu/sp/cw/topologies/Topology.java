package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public abstract class Topology {
    public int numberOfProcessors;
    public String name;
    public boolean part;
    protected List<Processor> processors = new ArrayList<>();

    public abstract List<Processor> createTopology();

    public Topology(int numberOfProcessors, String topologyName) {
        this.numberOfProcessors = numberOfProcessors;
        this.name = topologyName;
    }

    protected void init() {
        IntStream.range(0, numberOfProcessors)
                .forEach(i -> processors.add(new Processor(i)));
    }

    protected void addNeighbours(Processor processor, int ... neighbourIds) {
        for (int i = 0; i < neighbourIds.length; i++) {
            processor.neighbours.add(processors.get(neighbourIds[i]));
        }
    }

    protected void removeTwoProcessors() {
        if (part)
            processors.removeIf(processor -> processor.id==3 || processor.id==4 || processor.id==6);
    }

}
