package edu.sp.cw;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static edu.sp.cw.Main.NUMBER_OF_PROCESSORS;

public class VectorTopology implements Topology{
    @Override
    // Method for topology setting. In this case - vector or linear.
    public List<Processor> createTopology(){
        List<Processor> processors = new ArrayList<>();
        IntStream.range(0, NUMBER_OF_PROCESSORS)
                .forEach(i -> processors.add(new Processor(i)));
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
}
