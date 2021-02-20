package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class HypercubeTopology extends Topology{
    List<Processor> processors = new ArrayList<>();
    @Override
    public List<Processor> createTopology(boolean part) {

        IntStream.range(0, NUMBER_OF_PROCESSORS)
                .forEach(i -> processors.add(new Processor(i)));
        processors.get(0).neighbours = createNeighbours(1, 2, 4);
        processors.get(1).neighbours = createNeighbours(0, 3, 5);
        processors.get(2).neighbours = createNeighbours(0, 3, 6);
        processors.get(3).neighbours = createNeighbours(1, 2, 7);
        processors.get(4).neighbours = createNeighbours(0, 5, 6);
        processors.get(5).neighbours = createNeighbours(1, 4, 7);
        processors.get(6).neighbours = createNeighbours(2, 4, 7);
        processors.get(7).neighbours = createNeighbours(3, 5, 6);
        removeTwoProcessors(part, processors);
        return processors;
    }

    private List<Processor> createNeighbours(int n1, int n2, int n3) {
        return Arrays.asList(processors.get(n1),
                      processors.get(n2),
                      processors.get(n3));
    }

    public HypercubeTopology(int numberOfProcessors) {
        super(numberOfProcessors);
    }
}
