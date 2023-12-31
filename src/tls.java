import java.io.*;
import java.nio.file.*;

public class tls {

    public static void main(String[] args) throws Exception {
        // verification des arguments
        if (args.length < 1 || args.length > 3) {
            System.out.println("Usage: java tls [-o <path_output.csv>] <path_to_test_directory> ");
            System.exit(1);
        }

        String outputPath = null;
        String inputPath;

        // verification de l'argument optionnel -o
        if (args[0].equals("-o")) {
            outputPath = args[1];
            inputPath = args[2];
        } else {
            inputPath = args[0];
        }

        try (PrintStream output = (outputPath != null) ? new PrintStream(new FileOutputStream(outputPath)) : System.out) {
            // traitement du répertoire
            processDirectory(Paths.get(inputPath), output);
        }
    }

    public static void processDirectory(Path startPath, PrintStream output) throws Exception {
        // parcours du répertoire pour trouver les fichiers java 
        Files.walk(startPath)
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(p -> processJavaFile(startPath, p, output));
    }

    // Calcul des valeurs et des chemins  pour le fichier Java actuel
    public static void processJavaFile(Path startPath, Path path, PrintStream output) {
        String relativePath = ".\\" + startPath.relativize(path).toString();
        String className = path.getFileName().toString().replace(".java", "");
        String packageName = computePackageName(startPath, path);

        int tlocValue = 0;
        // calcul du nb de lignes de commentaires
        try {
            tlocValue = tloc.calculateTloc(path.toFile().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tassertValue = 0;
        // calcul du nombre d'assertions
        try {
            tassertValue = tassert.calculateTassert(path.toFile().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // tcmp = tloc / tassert
        double tcmp = tassertValue != 0 ? (double) tlocValue / tassertValue : 0;


        output.printf("%s, %s, %s, %d, %d, %.2f%n",
                relativePath, packageName, className, tlocValue, tassertValue, tcmp);
    }
    // nom du package en fonction du chemin  du fichier
    public static String computePackageName(Path startPath, Path file) {
        Path relativePath = startPath.relativize(file);
        Path parent = relativePath.getParent();
        if (parent == null) {
            return startPath.subpath(4, startPath.getNameCount()).toString().replace(File.separatorChar, '.');
        }
        return parent.toString().replace(File.separatorChar, '.');
    }

}