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
