import java.util.Arrays;

public class TicketBenchmark {

    static volatile long result;

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

    interface Test {
        long execute();
    }

    static long buildUsingString(Workload w) {
        long checksum = 0;

        for (int i = 0; i < w.tickets; i++) {
            String ticket = "";

            for (int j = 0; j < w.fields; j++) {
                ticket = ticket + w.data[j];
            }

            checksum += ticket.length();
        }

        return checksum;
    }

    static long buildUsingStringBuilder(Workload w) {
        long checksum = 0;

        for (int i = 0; i < w.tickets; i++) {
            StringBuilder ticket = new StringBuilder();

            for (int j = 0; j < w.fields; j++) {
                ticket.append(w.data[j]);
            }

            checksum += ticket.length();
        }

        return checksum;
    }

    static long buildUsingStringBuffer(Workload w) {
        long checksum = 0;

        for (int i = 0; i < w.tickets; i++) {
            StringBuffer ticket = new StringBuffer();

            for (int j = 0; j < w.fields; j++) {
                ticket.append(w.data[j]);
            }

            checksum += ticket.length();
        }

        return checksum;
    }

    static long usedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    static void benchmark(String approach, Test test) throws InterruptedException {

        // Warm-up
        result = test.execute();

        long totalTime = 0;
        long memoryDifference = 0;
        int repetitions = 5;

        for (int i = 0; i < repetitions; i++) {

            System.gc();
            Thread.sleep(100);

            long beforeMemory = usedMemory();
            long start = System.nanoTime();

            result = test.execute();

            long end = System.nanoTime();
            long afterMemory = usedMemory();

            totalTime += (end - start);
            memoryDifference = afterMemory - beforeMemory;
        }

        double averageMs =
                totalTime / (double) repetitions / 1_000_000.0;

        System.out.printf(
                "%-20s Average Time: %10.3f ms | Approx. Heap Change: %d bytes%n",
                approach, averageMs, memoryDifference
        );
    }

    static void runWorkload(Workload w) throws InterruptedException {

        System.out.println("\n========================================");
        System.out.println("Workload: " + w.name);
        System.out.println("Number of Tickets: " + w.tickets);
        System.out.println("Fields per Ticket: " + w.fields);
        System.out.println("========================================");

        benchmark("String", () -> buildUsingString(w));

        benchmark("StringBuilder",
                () -> buildUsingStringBuilder(w));

        benchmark("StringBuffer",
                () -> buildUsingStringBuffer(w));
    }

    public static void main(String[] args) throws InterruptedException {

        Workload shortTicket =
                new Workload("Short Ticket", 1, 6);

        Workload largeWorkload =
                new Workload("Large Workload", 10000, 50);

        Workload customWorkload =
                new Workload("Custom Workload", 2000, 100);

        runWorkload(shortTicket);
        runWorkload(largeWorkload);
        runWorkload(customWorkload);

        System.out.println("\nAll benchmarks completed successfully.");
        System.out.println("Verification checksum: " + result);
    }
}
