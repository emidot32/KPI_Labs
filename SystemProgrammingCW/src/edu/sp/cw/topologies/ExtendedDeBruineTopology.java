package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.List;

public class ExtendedDeBruineTopology extends Topology{
    private Topology deBruineTopology;
    public ExtendedDeBruineTopology(int numberOfProcessors, String topologyName) {
        super(numberOfProcessors, topologyName);
        deBruineTopology = new DeBruineTopology(numberOfProcessors, "DeBruine");
    }

    @Override
    public List<Processor> createTopology() {
        return deBruineTopology.createTopology();
    }
}
