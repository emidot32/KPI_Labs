package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.List;

public class HypercubeTopology extends Topology{
    @Override
    public List<Processor> createTopology() {
        init();
        if (numberOfProcessors == 8) {
            createTopologyWith8Processors();
        } else if (numberOfProcessors == 16) {
            createTopologyWith16Processors();
        }
        removeTwoProcessors();
        return processors;
    }

    private void createTopologyWith8Processors() {
        addNeighbours(processors.get(0),1, 2, 4);
        addNeighbours(processors.get(1),0, 3, 5);
        addNeighbours(processors.get(2),0, 3, 6);
        addNeighbours(processors.get(3),1, 2, 7);
        addNeighbours(processors.get(4),0, 5, 6);
        addNeighbours(processors.get(5),1, 4, 7);
        addNeighbours(processors.get(6),2, 4, 7);
        addNeighbours(processors.get(7),3, 5, 6);
    }

    private void createTopologyWith16Processors() {
        createTopologyWith8Processors();
        for (int i = 0; i < processors.size(); i++) {
            if (i > 7) {
                List<Processor> neighbours = processors.get(i).neighbours;
                List<Processor> neighboursOfInnerCube = processors.get(i - 8).neighbours;
                for (int j = 0; j < neighboursOfInnerCube.size()-1; j++) {
                    neighbours.add(processors.get(neighboursOfInnerCube.get(j).id+8));
                }
                neighbours.add(processors.get(i-8));

            } else {
                processors.get(i).neighbours.add(processors.get(i+8));
            }
        }
    }


    public HypercubeTopology(int numberOfProcessors, String topologyName) {
        super(numberOfProcessors, topologyName);
    }
}
