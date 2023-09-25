import java.io.*;
import java.nio.file.*;

public class tls {

    public static void main(String[] args) throws Exception {
        if (args.length < 1 || args.length > 3) {
            System.out.println("Usage: java tls [-o <path_output.csv>] <path_to_test_directory> ");
            System.exit(1);
        }

        String outputPath = null;
        String inputPath;

        // Check for optional -o argument
        if (args[0].equals("-o")) {
            outputPath = args[1];
            inputPath = args[2];
        } else {
            inputPath = args[0];
        }

        try (PrintStream output = (outputPath != null) ? new PrintStream(new FileOutputStream(outputPath)) : System.out) {
            // Process the directory
            processDirectory(Paths.get(inputPath), output);
        }
    }

    private static void processDirectory(Path startPath, PrintStream output) throws Exception {
        Files.walk(startPath)
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(p -> processJavaFile(startPath, p, output));
    }

    private static void processJavaFile(Path startPath, Path path, PrintStream output) {
        String relativePath = ".\\" + startPath.relativize(path).toString();
        String className = path.getFileName().toString().replace(".java", "");
        String packageName = computePackageName(startPath, path);

        int tlocValue = 0;
        try {
            tlocValue = tloc.calculateTloc(path.toFile().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tassertValue = 0;
        try {
            tassertValue = tassert.calculateTassert(path.toFile().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        double tcmp = tassertValue != 0 ? (double) tlocValue / tassertValue : 0;


        output.printf("%s, %s, %s, %d, %d, %.2f%n",
                relativePath, packageName, className, tlocValue, tassertValue, tcmp);
    }

    private static String computePackageName(Path startPath, Path file) {
        Path relativePath = startPath.relativize(file);
        Path parent = relativePath.getParent();
        if (parent == null) {
            return startPath.subpath(4, startPath.getNameCount()).toString().replace(File.separatorChar, '.');
        }
        return parent.toString().replace(File.separatorChar, '.');
    }

}