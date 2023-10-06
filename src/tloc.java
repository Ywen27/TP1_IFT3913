import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class tloc {

    public static void main(String[] args) {
        // Vérification des arguments
        if (args.length != 1) {
            System.out.println("Usage: java tloc <path_to_java_file>");
            System.exit(1);
        }

        String filePath = args[0];
        try {
            int tloc = calculateTloc(filePath);
            System.out.println(tloc);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Calcul du nombre de lignes de code dans le fichier (hors commentaires)
    public static int calculateTloc(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int tloc = 0;
            boolean inCommentBlock = false;

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Suppression des espaces en début et fin de ligne

                // commentaires multi-lignes
                if (inCommentBlock) {
                    if (line.contains("*/")) {
                        inCommentBlock = false;
                        line = line.substring(line.indexOf("*/") + 2);
                    } else {
                        continue; // Ignorer le reste de la ligne
                    }
                }

                // commentaires en ligne
                int lineCommentIndex = line.indexOf("//");
                if (lineCommentIndex != -1) {
                    line = line.substring(0, lineCommentIndex); // Ignorer le commentaire en ligne
                }

                // débuts de commentaires multi-lignes
                if (line.startsWith("/*")) {
                    inCommentBlock = true;
                    if (line.contains("*/")) {
                        inCommentBlock = false;
                        line = line.substring(line.indexOf("*/") + 2);
                    } else {
                        continue; // ignorer le reste de la ligne
                    }
                }

                // Vérifier si la ligne n'est pas vide après avoir pris en compte les commentaires
                if (!line.isEmpty()) {
                    tloc++;
                }
            }

            return tloc;
        }
    }
}