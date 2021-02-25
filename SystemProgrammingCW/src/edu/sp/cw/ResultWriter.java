package edu.sp.cw;

import edu.sp.cw.topologies.Topology;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultWriter {
    List<String> allocationResult = new ArrayList<>();
    List<String> allocationMetrics = new ArrayList<>();
    Planner planner;
    Topology topology;
    String allocationFileName;

    public ResultWriter(Planner planner, String allocationFileName, Topology topology) {
        this.planner = planner;
        this.allocationFileName = allocationFileName;
        this.topology = topology;
    }

    public void saveResults() {
        int maxTact = planner.calculateParametersAndGetMaxTact();
        saveAllocationResults(maxTact);
        saveAllocationMetrics(maxTact);
    }

    private void initWrite(){
        StringBuilder line = new StringBuilder(String.format("%5s;", "t"));
        for (int i = 0; i < planner.NUMBER_OF_PROCESSORS; i++) {
            line.append(String.format("%11s;", "P"+i));
        }
        allocationResult.add(line.toString());
    }

    private void saveAllocationResults(int maxTact){
        initWrite();
        for (int i = 1; i <= maxTact; i++) {
            StringBuilder tactLine = new StringBuilder(String.format("%5d;", i));
            for (Processor processor: planner.processors){
                tactLine.append(String.format("%11s;", processor.execution.get(i)));
            }
            allocationResult.add(tactLine.toString());
        }
        writeToFile(allocationFileName, allocationResult, false);
    }

    private void saveAllocationMetrics(int maxTact) {
        allocationMetrics.add(String.format("***;Topology:; %s;", topology.name));
        //allocationMetrics.add(String.format("   ;Method:; %s;", "Newton"));
        allocationMetrics.add(String.format("   ;Number of processors:; %d;", topology.numberOfProcessors));
        allocationMetrics.add(String.format("   ;Duration for 1 processor:; %d;", planner.count));
        allocationMetrics.add(String.format("   ;Duration for multiprocessor system:; %d;", maxTact));
        allocationMetrics.add(String.format("   ;Acceleration coefficient:; %.3f;", planner.count / (double) maxTact));
        allocationMetrics.add(String.format("   ;Efficiency coefficient:; %.3f;",
                planner.count / (double) (maxTact * planner.NUMBER_OF_PROCESSORS)));
        allocationMetrics.add("   ;Processor number/metric; Workload, %; Number of forwarding; Hop to payload, %;");
        planner.processors.stream()
                .peek(processor ->
                    allocationMetrics.add(String.format("   ; Processor №%d; %.1f; %d; %.1f;", processor.id,
                            processor.workloadCoef, processor.numberOfForwarding, processor.hopToWorkloadRatio))
                )
                .mapToDouble(processor -> processor.workloadCoef)
                .average()
                .ifPresent(value -> allocationMetrics.add(String.format("   ;Average workload:; %.1f;", value)));
        planner.processors.stream()
                .mapToInt(processor -> processor.numberOfForwarding)
                .average()
                .ifPresent(value -> allocationMetrics.add(String.format("   ;Average number of forwarding:; %d;", (int) value)));
        planner.processors.stream()
                .mapToDouble(processor -> processor.hopToWorkloadRatio)
                .average()
                .ifPresent(value -> allocationMetrics.add(String.format("   ;Average hop to payload ratio:; %.1f;", value)));
        writeToFile("metrics.csv", allocationMetrics, true);
    }

    private void writeToFile(String fileName, List<String> results, boolean append) {
        String filePathName = "src/edu/sp/cw/results/"+fileName;
        try {
            File file = new File(filePathName);
            if (file.exists() && !append)
                file.delete();
            BufferedWriter newFile = new BufferedWriter(new FileWriter(file, append));
            for (String line : results) {
                newFile.write(line + "\n");
            }
            newFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
