package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class HypercubeTopology extends Topology{
    List<Processor> processors = new ArrayList<>();
    @Override
    public List<Processor> createTopology() {

        IntStream.range(0, NUMBER_OF_PROCESSORS)
                .forEach(i -> processors.add(new Processor(i)));
        if (NUMBER_OF_PROCESSORS == 8) {
            createTopologyWith8Processors();
        } else if (NUMBER_OF_PROCESSORS == 16) {
            createTopologyWith16Processors();
        }
        removeTwoProcessors(part, processors);
        return processors;
    }

    private void createTopologyWith8Processors() {
        processors.get(0).neighbours = createNeighbours(1, 2, 4);
        processors.get(1).neighbours = createNeighbours(0, 3, 5);
        processors.get(2).neighbours = createNeighbours(0, 3, 6);
        processors.get(3).neighbours = createNeighbours(1, 2, 7);
        processors.get(4).neighbours = createNeighbours(0, 5, 6);
        processors.get(5).neighbours = createNeighbours(1, 4, 7);
        processors.get(6).neighbours = createNeighbours(2, 4, 7);
        processors.get(7).neighbours = createNeighbours(3, 5, 6);
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

    private List<Processor> createNeighbours(int n1, int n2, int n3) {
        return new ArrayList<>(Arrays.asList(processors.get(n1),
                      processors.get(n2),
                      processors.get(n3)));
    }


    public HypercubeTopology(int numberOfProcessors, String topologyName) {
        super(numberOfProcessors, topologyName);
    }
}
