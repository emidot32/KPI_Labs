package edu.sp.cw;


import edu.sp.cw.topologies.DeBruineTopology;
import edu.sp.cw.topologies.HypercubeTopology;
import edu.sp.cw.topologies.Topology;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        String topologyName = "DeBruine";
        int numberOfProcessor = 16;
        int dimensity = 4;
        int numberOfTasks = dimensity == 4 ? 25 : 136;
        String allocationFileName = String.format("result_%s_%d_cramer_%dx%d_graph.csv",
                topologyName.toLowerCase(), numberOfProcessor, dimensity, dimensity);
        String matrixFileName = String.format("src/edu/sp/cw/matrixes/cramer_%dx%d.txt", dimensity, dimensity);
        MatrixReader matrixReader = new MatrixReader(matrixFileName, numberOfTasks);
        Topology topology = new DeBruineTopology(numberOfProcessor, topologyName);
        Planner planner = new Planner(matrixReader.getMatrixFromFile(), matrixReader.getWeightsFromFile(),
                topology);
        ResultWriter resultWriter = new ResultWriter(planner, allocationFileName, topology);
        planner.run();
        resultWriter.saveResults();
    }
}
