package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.List;

public class ExtendedDeBruijnTopology extends Topology{

    public ExtendedDeBruijnTopology(int numberOfProcessors, String topologyName) {
        super(numberOfProcessors, topologyName);
    }

    @Override
    public List<Processor> createTopology() {
        init();
        if (numberOfProcessors == 8) {
            createTopologyWith8Processors();
        }
        if (numberOfProcessors == 16) {
            createTopologyWith16Processors();
        }
        return processors;
    }

    private void createTopologyWith8Processors() {
        addNeighbours(processors.get(0),1, 2, 4, 5);
        addNeighbours(processors.get(1),0, 2, 3, 4, 5, 6);
        addNeighbours(processors.get(2),0, 1, 3, 4);
        addNeighbours(processors.get(3),1, 2, 4, 7);
        addNeighbours(processors.get(4),0, 1, 2, 3, 5, 6);
        addNeighbours(processors.get(5),0, 1, 4, 6);
        addNeighbours(processors.get(6),1, 4, 5, 7);
        addNeighbours(processors.get(7),3, 6);
    }

    private void createTopologyWith16Processors() {
        addNeighbours(processors.get(0),1, 4, 8, 11);
        addNeighbours(processors.get(1),0, 2, 3, 4, 5, 10, 11, 12, 13, 14);
        addNeighbours(processors.get(2),1, 3, 4, 5, 10, 11, 12);
        addNeighbours(processors.get(3),1, 2, 5, 6, 7, 8, 9, 10);
        addNeighbours(processors.get(4),0, 1, 2, 6, 8, 9);
        addNeighbours(processors.get(5),1, 2, 3, 6, 7, 9);
        addNeighbours(processors.get(6),3, 4, 5, 7, 8);
        addNeighbours(processors.get(7),3 ,5, 6, 8, 15);
        addNeighbours(processors.get(8),0, 3, 4, 5, 6, 7, 9, 10, 11, 12);
        addNeighbours(processors.get(9),3, 4, 5, 8, 10, 11, 12);
        addNeighbours(processors.get(10),1, 2, 3, 8, 9, 12, 13, 14);
        addNeighbours(processors.get(11),0, 1, 2, 8, 9, 13);
        addNeighbours(processors.get(12),1, 2, 8, 9, 10, 13, 14);
        addNeighbours(processors.get(13),1, 10, 11, 12, 14);
        addNeighbours(processors.get(14),1, 10, 12, 13, 15);
        addNeighbours(processors.get(15),7, 14);
    }

}
