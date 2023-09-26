import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.function.ToDoubleFunction;

public class tropcomp {
	
	public static void main(String[] args) throws Exception {
		if (args.length < 2 || args.length > 4 || (args.length == 3 && !args[0].equals("-o"))) {
            System.out.println("Usage: java tropcomp [-o <path_to_output_file.csv>] <path_to_maven_project> " +
                    "<threshold_percentage>");
            System.exit(1);
        }

        String outputPath = null;
        int inputIndex = 0;
        if (args[0].equals("-o")) {
            outputPath = args[1];
            inputIndex = 2;
        }
        String projectPath = args[inputIndex];
        double threshold = Double.parseDouble(args[inputIndex + 1]);
        
        try (PrintStream output = (outputPath != null) ? new PrintStream(new FileOutputStream(outputPath)) : System.out) {
            processProject(Paths.get(projectPath), threshold, output);
        }
	};
	
	private static void processProject(Path projectPath, double threshold, PrintStream output) throws Exception {
		List<ClassMetrics> metricsList = new ArrayList<>();
		
		// Gather metrics for all test files
        Files.walk(projectPath)
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(p -> {
                    try {
                        int tlocValue = tloc.calculateTloc(p.toString());
                        int tassertValue = tassert.calculateTassert(p.toString());
                        double tcmp = tassertValue != 0 ? (double) tlocValue / tassertValue : 0;
                        metricsList.add(new ClassMetrics(p, tlocValue, tassertValue, tcmp));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        // Calculate threshold values
        int tlocThreshold = getTlocThresholdValue(metricsList, ClassMetrics::getTloc, threshold);
        double tcmpThreshold = getTcmpThresholdValue(metricsList, ClassMetrics::getTcmp, threshold);

        for (ClassMetrics metrics : metricsList) {
            if (metrics.getTloc() >= tlocThreshold && metrics.getTcmp() >= tcmpThreshold) {
                tls.processJavaFile(projectPath, metrics.getPath(), output);
            }
        }
    }
	
	private static int getTlocThresholdValue(List<ClassMetrics> metricsList, ToIntFunction<ClassMetrics> metricFunction, double percentile) {
		 return metricsList.stream()
	                .mapToInt(metricFunction)
	                .sorted()
	                .skip((long) (metricsList.size() * (1 - percentile / 100.0)))
	                .findFirst()
	                .orElse(0);
    }

    private static double getTcmpThresholdValue(List<ClassMetrics> metricsList, ToDoubleFunction<ClassMetrics> metricFunction, double percentile) {
    	return metricsList.stream()
                .mapToDouble(metricFunction)
                .sorted()
                .skip((long) (metricsList.size() * (1 - percentile / 100.0)))
                .findFirst()
                .orElse(0.0);
    }
	
	private static class ClassMetrics {
        private final Path path;
        private final int tloc;
        private final int tassert;
        private final double tcmp;

        public ClassMetrics(Path path, int tloc, int tassert, double tcmp) {
            this.path = path;
            this.tloc = tloc;
            this.tassert = tassert;
            this.tcmp = tcmp;
        }

        public Path getPath() {
            return path;
        }

        public int getTloc() {
            return tloc;
        }

        public double getTcmp() {
            return tcmp;
        }
    }

}
