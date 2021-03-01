package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.List;


public class DeBruijnTopology extends Topology {
    @Override
    public List<Processor> createTopology() {
        init();
        for (int i = 0; i < numberOfProcessors; i++) {
            Processor currentProcessor = processors.get(i);
            List<Processor> neighbours = currentProcessor.neighbours;
            addNeighbourWithCheck(processors.get(shiftLeft(i)), neighbours, currentProcessor);
            addNeighbourWithCheck(processors.get(shiftLeft(i)+1), neighbours, currentProcessor);
            addNeighbourWithCheck(processors.get(i >> 1), neighbours, currentProcessor);
            addNeighbourWithCheck(processors.get((i >> 1)+ numberOfProcessors /2), neighbours, currentProcessor);
        }
        removeTwoProcessors();
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

    public DeBruijnTopology(int numberOfProcessors, String topologyName) {
        super(numberOfProcessors, topologyName);
    }
}
