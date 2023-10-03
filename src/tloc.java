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

                // si la ligne commence par un bloc de commentaire
                if (line.startsWith("/*")) {
                    inCommentBlock = true;
                }

                // si ligne pas vide et n'est pas un commentaire
                if (!line.isEmpty() && !line.startsWith("//") && !inCommentBlock) {
                    tloc++;
                }

                // si la ligne termine par un bloc de commentaire
                if (line.endsWith("*/")) {
                    inCommentBlock = false;
                }
            }

            return tloc;
        }
    }
}
