package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.List;

public abstract class Topology {
    public int NUMBER_OF_PROCESSORS;
    public abstract List<Processor> createTopology(boolean part);

    public Topology(int numberOfProcessors) {
        this.NUMBER_OF_PROCESSORS = numberOfProcessors;
    }

    public void removeTwoProcessors(boolean part, List<Processor> processors) {
        if (part)
            processors.removeIf(processor -> processor.id==3 || processor.id==4);
    }

}
