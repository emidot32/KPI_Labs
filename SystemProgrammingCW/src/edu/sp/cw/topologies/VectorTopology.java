package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class VectorTopology extends Topology{
    @Override
    // Method for topology setting. In this case - vector or linear.
    public List<Processor> createTopology() {
        init();
        for (int i = 0; i < processors.size(); i++) {
            List<Processor> neighboursOfCurrentProc = processors.get(i).neighbours;
            if (i == 0){
                neighboursOfCurrentProc.add(processors.get(i+1));
            } else if (i == processors.size()-1){
                neighboursOfCurrentProc.add(processors.get(i-1));
            } else {
                neighboursOfCurrentProc.add(processors.get(i-1));
                neighboursOfCurrentProc.add(processors.get(i+1));
            }
        }
        return processors;
    }

    public VectorTopology(int numberOfProcessors, String topologyName) {
        super(numberOfProcessors, topologyName);
    }
}
