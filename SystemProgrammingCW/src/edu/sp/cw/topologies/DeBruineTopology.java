package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class DeBruineTopology extends Topology {
    @Override
    public List<Processor> createTopology(boolean part) {
        List<Processor> processors = new ArrayList<>();
        IntStream.range(0, NUMBER_OF_PROCESSORS)
                .forEach(i -> processors.add(new Processor(i)));
        for (int i = 0; i < NUMBER_OF_PROCESSORS; i++) {
            Processor currentProcessor = processors.get(i);
            List<Processor> neighbours = currentProcessor.neighbours;
            addNeighbourWithCheck(processors.get(shiftLeft(i)), neighbours, currentProcessor);
            addNeighbourWithCheck(processors.get(shiftLeft(i)+1), neighbours, currentProcessor);
            addNeighbourWithCheck(processors.get(i >> 1), neighbours, currentProcessor);
            addNeighbourWithCheck(processors.get((i >> 1)+NUMBER_OF_PROCESSORS/2), neighbours, currentProcessor);
        }
        removeTwoProcessors(part, processors);
        return processors;
    }

    private void addNeighbourWithCheck(Processor processor, List<Processor> neighbours, Processor currentProcessor) {
        boolean flag = false;
        if (currentProcessor.equals(processor)) return;
        for (Processor neighbour: neighbours) {
            if (neighbour.equals(processor)) {
                flag = true;
                break;
            }
        }
        if (!flag) neighbours.add(processor);
    }

    private int shiftLeft(int i) {
        String binary = Integer.toBinaryString(i << 1);
        if (binary.length() > 3) return Integer.parseInt(binary.substring(1), 2);
        return Integer.parseInt(binary, 2);
    }

    public DeBruineTopology(int numberOfProcessors) {
        super(numberOfProcessors);
    }
}
