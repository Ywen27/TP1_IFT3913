import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class tassert {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java tassert <path_to_java_file>");
            System.exit(1);
        }

        String filePath = args[0];
        try {
            int tassert = calculateTassert(filePath);
            System.out.println(tassert);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static int calculateTassert(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int tassert = 0;
            boolean inCommentBlock = false;

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("/*")) {
                    inCommentBlock = true;
                }

                if (!line.isEmpty() && line.contains("assert") && !line.startsWith("//") && !inCommentBlock) {
                    tassert++;
                }

                if (line.endsWith("*/")) {
                    inCommentBlock = false;
                }
            }

            return tassert;
        }
    }
}
