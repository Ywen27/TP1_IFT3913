import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.function.ToDoubleFunction;

public class tropcomp {
	
	public static void main(String[] args) throws Exception {
		
	};
	
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
