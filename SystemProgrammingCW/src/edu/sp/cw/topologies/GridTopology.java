package edu.sp.cw.topologies;

import edu.sp.cw.Processor;

import java.util.List;

public class GridTopology extends Topology {
    public GridTopology(int numberOfProcessors, String topologyName) {
        super(numberOfProcessors, topologyName);
    }

    @Override
    public List<Processor> createTopology() {
        init();
        int step = numberOfProcessors == 8 ? 3 : 4;
        for (int i = 0; i < numberOfProcessors; i++) {
            if (i - step >= 0)
                processors.get(i).neighbours.add(processors.get(i - step));
            if (i - 1 >= 0 && i % step != 0)
                processors.get(i).neighbours.add(processors.get(i - 1));
            if (i + 1 < numberOfProcessors && (i+1) % step != 0)
                processors.get(i).neighbours.add(processors.get(i + 1));
            if (i + step < numberOfProcessors)
                processors.get(i).neighbours.add(processors.get(i + step));
        }
        return processors;
    }
}
