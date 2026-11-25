
package Task07;

import java.util.Arrays;

public class RedesignBenchmark {

    static volatile long checksum;

    static class Workload {
        String name;
        int tickets;
        int fields;
        String[] data;

        Workload(String name, int tickets, int fields) {
            this.name = name;
            this.tickets = tickets;
            this.fields = fields;

            data = new String[fields];

            for (int i = 0; i < fields; i++) {
                data[i] = "FIELD" + i + "=VALUE" + i + ";";
            }
        }
    }

    // ORIGINAL SOLUTION: String concatenation
    static long originalString(Workload w) {

        long totalLength = 0;

        for (int i = 0; i < w.tickets; i++) {

            String ticket = "";

            for (int j = 0; j < w.fields; j++) {
                ticket = ticket + w.data[j];
            }

            totalLength += ticket.length();
        }

        return totalLength;
    }

    // REDESIGNED SOLUTION: StringBuilder
    static long redesignedBuilder(Workload w) {

        long totalLength = 0;

        for (int i = 0; i < w.tickets; i++) {

            StringBuilder ticket = new StringBuilder();

            for (int j = 0; j < w.fields; j++) {
                ticket.append(w.data[j]);
            }

            totalLength += ticket.length();
        }

        return totalLength;
    }

    static double median(long[] values) {

        Arrays.sort(values);

        if (values.length % 2 == 1) {
            return values[values.length / 2];
        }

        return (values[values.length / 2 - 1]
                + values[values.length / 2]) / 2.0;
    }

    static long measureString(Workload w) {

        long start = System.nanoTime();

        checksum = originalString(w);

        return System.nanoTime() - start;
    }

    static long measureBuilder(Workload w) {

        long start = System.nanoTime();

        checksum = redesignedBuilder(w);

        return System.nanoTime() - start;
    }

    static void runExperiment(Workload w) {

        System.out.println("\n====================================");
        System.out.println("WORKLOAD: " + w.name);
        System.out.println("Tickets: " + w.tickets);
        System.out.println("Fields per ticket: " + w.fields);
        System.out.println("====================================");

        // Verify correctness before timing
        long originalResult = originalString(w);
        long redesignedResult = redesignedBuilder(w);

        boolean correct =
                originalResult == redesignedResult;

        System.out.println("Output verification: " + correct);

        if (!correct) {
            throw new IllegalStateException(
                    "Output mismatch detected!");
        }

        // Warm-up both approaches
        for (int i = 0; i < 3; i++) {
            measureString(w);
            measureBuilder(w);
        }

        int repetitions = 7;

        long[] originalTimes = new long[repetitions];
        long[] redesignedTimes = new long[repetitions];

        // Alternate measurement order to reduce order bias
        for (int i = 0; i < repetitions; i++) {

            if (i % 2 == 0) {
                originalTimes[i] = measureString(w);
                redesignedTimes[i] = measureBuilder(w);
            } else {
                redesignedTimes[i] = measureBuilder(w);
                originalTimes[i] = measureString(w);
            }
        }

        double originalMedian =
                median(originalTimes) / 1_000_000.0;

        double redesignedMedian =
                median(redesignedTimes) / 1_000_000.0;

        System.out.printf(
                "Original String median: %.3f ms%n",
                originalMedian);

        System.out.printf(
                "Redesigned StringBuilder median: %.3f ms%n",
                redesignedMedian);

        System.out.println(
                "Field append operations: "
                + ((long) w.tickets * w.fields));

        if (redesignedMedian > 0) {

            double improvement =
                    ((originalMedian - redesignedMedian)
                    / originalMedian) * 100.0;

            double speedup =
                    originalMedian / redesignedMedian;

            System.out.printf(
                    "Relative time reduction: %.2f%%%n",
                    improvement);

            System.out.printf(
                    "Speedup ratio: %.2fx%n",
                    speedup);
        }

        System.out.println(
                "Original raw times (ns): "
                + Arrays.toString(originalTimes));

        System.out.println(
                "Redesigned raw times (ns): "
                + Arrays.toString(redesignedTimes));
    }

    public static void main(String[] args) {

        System.out.println(
                "Q7 - REDESIGN AND VALIDATION");

        System.out.println(
                "String vs StringBuilder");

        Workload small =
                new Workload("Small Workload", 100, 20);

        Workload medium =
                new Workload("Medium Workload", 1000, 50);

        Workload large =
                new Workload("Large Workload", 5000, 100);

        runExperiment(small);
        runExperiment(medium);
        runExperiment(large);

        System.out.println(
                "\nAll redesign experiments completed.");

        System.out.println(
                "Final verification checksum: " + checksum);
    }
}
