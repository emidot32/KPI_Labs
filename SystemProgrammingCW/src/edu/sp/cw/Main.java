package edu.sp.cw;


import edu.sp.cw.topologies.*;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        String topologyName = "Extended_DeBruijn";
        String method = "newton";
        int numberOfProcessor = 8;
        int dimensity = 4;
        int numberOfTasks = 53;
        String allocationFileName = String.format("result_%s_%d_%s_%dx%d_graph.csv",
                topologyName.toLowerCase(), numberOfProcessor, method, dimensity, dimensity);
        String matrixFileName = String.format("matrixes/%s_%dx%d.txt", method, dimensity, dimensity);
        MatrixReader matrixReader = new MatrixReader(matrixFileName, numberOfTasks);
        Topology topology = new ExtendedDeBruijnTopology(numberOfProcessor, topologyName);
        Planner planner = new Planner(matrixReader.getMatrixFromFile(), matrixReader.getWeightsFromFile(),
                topology);
        ResultWriter resultWriter = new ResultWriter(planner, allocationFileName, topology);
        planner.run();
        resultWriter.saveResults();
    }
}
