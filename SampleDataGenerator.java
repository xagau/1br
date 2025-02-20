import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SampleDataGenerator {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java SampleDataGenerator <output-file> [numRows]");
            System.exit(1);
        }

        String outputFile = args[0];
        // Default to a lower number for testing; change to 1_000_000_000L for full-scale challenge.
        long numRows = 1_000_000L;  
        if (args.length >= 2) {
            try {
                numRows = Long.parseLong(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number for numRows. Using default value: " + numRows);
            }
        }

        // A sample list of station names
        String[] stations = {
            "Abha", "Abidjan", "Abéché", "Accra", "Addis Ababa", "Adelaide", "Amsterdam", "Ankara", "Athens", "Auckland",
            "Bangkok", "Barcelona", "Beijing", "Belgrade", "Berlin", "Bogotá", "Boston", "Brisbane", "Brussels", "Bucharest",
            "Budapest", "Buenos Aires", "Cairo", "Cape Town", "Casablanca", "Chicago", "Copenhagen", "Dallas", "Delhi",
            "Dubai", "Dublin", "Frankfurt", "Geneva", "Helsinki", "Hong Kong", "Istanbul", "Jakarta", "Johannesburg",
            "Kiev", "Kuala Lumpur", "Lagos", "Lima", "Lisbon", "London", "Los Angeles", "Madrid", "Manila", "Mexico City",
            "Milan", "Moscow", "Mumbai", "Munich", "Nairobi", "New York", "Oslo", "Paris", "Prague", "Rio de Janeiro",
            "Rome", "San Francisco", "Santiago", "Seoul", "Singapore", "Stockholm", "Sydney", "Taipei", "Tokyo", "Toronto",
            "Vienna", "Warsaw", "Zurich"
        };

        Random random = new Random();

        System.out.println("Generating sample data file: " + outputFile);
        System.out.println("Total rows: " + numRows);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Optionally, you might write a header here if required.
            // For this challenge, we produce raw data lines.

            for (long i = 0; i < numRows; i++) {
                // Randomly select a station.
                String station = stations[random.nextInt(stations.length)];
                // Generate a random temperature between -20.0 and 50.0.
                double temperature = -20.0 + random.nextDouble() * 70.0;
                // Format the temperature to one decimal place.
                String tempStr = String.format("%.1f", temperature);
                writer.write(station + ";" + tempStr);
                writer.newLine();

                // Log progress every 10 million rows (adjust as needed).
                if (i > 0 && i % 10_000_000 == 0) {
                    System.out.println("Generated " + i + " rows...");
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Sample data file generated successfully: " + outputFile);
    }
}
