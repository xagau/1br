import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OneBillionRowChallengeInMemory {

    // Data structure to hold station statistics.
    static class StationStats {
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        double sum = 0.0;
        long count = 0;

        void update(double temp) {
            if (temp < min) min = temp;
            if (temp > max) max = temp;
            sum += temp;
            count++;
        }

        void merge(StationStats other) {
            if (other.min < this.min) this.min = other.min;
            if (other.max > this.max) this.max = other.max;
            this.sum += other.sum;
            this.count += other.count;
        }
    }

    // Cache to avoid creating duplicate station name strings.
    private static final Map<String, String> stationCache = new ConcurrentHashMap<>();

    /**
     * A fast, minimal double parser that assumes a simple numeric format (optional '-' then digits,
     * optional '.' and digits). This version does not support exponent notation.
     */
    private static double fastParseDouble(char[] chars, int start, int end) {
        boolean negative = false;
        int i = start;
        if (i < end && chars[i] == '-') {
            negative = true;
            i++;
        }
        double value = 0.0;
        // Parse integer part.
        while (i < end) {
            char c = chars[i];
            if (c == '.') break;
            value = value * 10 + (c - '0');
            i++;
        }
        // Parse fractional part, if present.
        if (i < end && chars[i] == '.') {
            i++;  // Skip the dot.
            double frac = 0.0;
            double factor = 0.1;
            while (i < end) {
                char c = chars[i];
                frac += (c - '0') * factor;
                factor *= 0.1;
                i++;
            }
            value += frac;
        }
        return negative ? -value : value;
    }

    /**
     * Process a single line of the input, given as a segment of a char array.
     */
    private static void processLine(char[] chars, int start, int end, Map<String, StationStats> statsMap) {
        // Find the semicolon.
        int sep = -1;
        for (int i = start; i < end; i++) {
            if (chars[i] == ';') {
                sep = i;
                break;
            }
        }
        if (sep < 0) return; // Skip if no semicolon found.

        // Extract the station name.
        String stationKey = new String(chars, start, sep - start);
        String station = stationCache.computeIfAbsent(stationKey, k -> k);

        // Parse the temperature from the remainder of the line.
        double temp = fastParseDouble(chars, sep + 1, end);

        // Update station statistics.
        StationStats stats = statsMap.get(station);
        if (stats == null) {
            stats = new StationStats();
            statsMap.put(station, stats);
        }
        stats.update(temp);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java OneBillionRowChallengeInMemory <input-file>");
            System.exit(1);
        }
        String fileName = args[0];

        // Determine file size.
        long fileSize = new File(fileName).length();
        System.out.println("File size: " + fileSize + " bytes");

        // Read the entire file into a byte array.
        long startTime = System.nanoTime();
        byte[] allBytes = Files.readAllBytes(Paths.get(fileName));
        long readTime = System.nanoTime();
        System.out.printf("File read in %.3f seconds.%n", (readTime - startTime) / 1e9);

        // Convert bytes to chars using ISO-8859-1 (1:1 mapping).
        char[] allChars = new char[allBytes.length];
        for (int i = 0; i < allBytes.length; i++) {
            allChars[i] = (char)(allBytes[i] & 0xFF);
        }
        long convertTime = System.nanoTime();
        System.out.printf("Conversion complete in %.3f seconds.%n", (convertTime - readTime) / 1e9);

        // Process the char array by scanning for newline characters.
        Map<String, StationStats> statsMap = new HashMap<>();
        int lineStart = 0;
        for (int i = 0; i < allChars.length; i++) {
            if (allChars[i] == '\n') {
                if (i > lineStart) {
                    processLine(allChars, lineStart, i, statsMap);
                }
                lineStart = i + 1;
            }
        }
        // Process any remaining data as the final line.
        if (lineStart < allChars.length) {
            processLine(allChars, lineStart, allChars.length, statsMap);
        }
        long processTime = System.nanoTime();
        System.out.printf("Processing complete in %.3f seconds.%n", (processTime - convertTime) / 1e9);

        // Sort station names alphabetically (case-insensitive).
        List<String> stations = new ArrayList<>(statsMap.keySet());
        stations.sort(String.CASE_INSENSITIVE_ORDER);
        StringBuilder output = new StringBuilder("{");
        boolean first = true;
        for (String station : stations) {
            StationStats stats = statsMap.get(station);
            double mean = stats.count > 0 ? stats.sum / stats.count : 0.0;
            if (!first) {
                output.append(", ");
            }
            output.append(station)
                  .append("=")
                  .append(String.format("%.1f", stats.min))
                  .append("/")
                  .append(String.format("%.1f", mean))
                  .append("/")
                  .append(String.format("%.1f", stats.max));
            first = false;
        }
        output.append("}");
        System.out.println(output);
        long endTime = System.nanoTime();
        System.out.printf("Total processing time: %.3f seconds.%n", (endTime - startTime) / 1e9);
    }
}
