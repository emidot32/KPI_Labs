package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.List;

public abstract class Topology {
    public int NUMBER_OF_PROCESSORS;
    public String name;
    public boolean part;

    public abstract List<Processor> createTopology();

    public Topology(int numberOfProcessors, String topologyName) {
        this.NUMBER_OF_PROCESSORS = numberOfProcessors;
        this.name = topologyName;
    }

    public void removeTwoProcessors(boolean part, List<Processor> processors) {
        if (part)
            processors.removeIf(processor -> processor.id==3 || processor.id==4 || processor.id==6);
    }

}
