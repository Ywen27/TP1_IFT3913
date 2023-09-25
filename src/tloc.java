import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class tloc {

    public static void main(String[] args) {
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

    public static int calculateTloc(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int tloc = 0;
            boolean inCommentBlock = false;

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("/*")) {
                    inCommentBlock = true;
                }

                if (!line.isEmpty() && !line.startsWith("//") && !inCommentBlock) {
                    tloc++;
                }

                if (line.endsWith("*/")) {
                    inCommentBlock = false;
                }
            }

            return tloc;
        }
    }
}
