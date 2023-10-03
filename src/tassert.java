import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class tassert {

    public static void main(String[] args) {
        // verification des arguments
        if (args.length != 1) {
            System.out.println("Usage: java tassert <path_to_java_file>");
            System.exit(1);
        }

        String filePath = args[0];
        try {
            int tassert = calculateTassert(filePath);
            System.out.println(tassert);
        } catch (IOException e) {
            // cas d'erreurs
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Calcul du nombre d'assertions trouvées dans le fichier
    public static int calculateTassert(String filePath) throws IOException {
        // Liste des méthodes d'assertion couramment utilisées dans JUnit
        String[] assertionMethods = {
                "assertEquals", "assertNotEquals", "assertFalse", "assertThrows", "fail",
                "assertTrue", "assertNotNull", "assertNull", "assertSame",
                "assertNotSame", "assertArrayEquals", "assertThat"
        };

        // motifs a partir de la liste assertionMethods
        Pattern pattern = Pattern.compile("\\b(" + String.join("|", assertionMethods) + ")\\b");

        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // si on trouve une ligne avec une methode d'assertion
                if (pattern.matcher(line).find()) {
                    count++;
                }
            }
        }

        return count;
    }
}
