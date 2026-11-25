
package Task06;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayDeque;
import java.util.Queue;

class Patient {

    final String name;
    final int severity;
    final long arrival;

    Patient(String name, int severity, long arrival) {
        this.name = name;
        this.severity = severity;
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return name + "(S" + severity + ", Arrival " + arrival + ")";
    }
}

public class Triage {

    private long counter = 0;

    // Lower severity number gets higher priority.
    // Earlier arrival gets priority when severity is equal.
    private final PriorityQueue<Patient> queue =
            new PriorityQueue<>(
                    Comparator.comparingInt((Patient p) -> p.severity)
                    .thenComparingLong(p -> p.arrival)
            );

    void admit(String name, int severity) {

        if (severity < 1 || severity > 5) {
            throw new IllegalArgumentException(
                    "Severity must be between 1 and 5");
        }

        queue.add(new Patient(name, severity, counter++));
    }

    Patient next() {
        return queue.poll();
    }

    int size() {
        return queue.size();
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }

    // Test with eight patients
    static void handVerificationTest() {

        System.out.println("\nTEST 1: EIGHT PATIENTS");
        System.out.println("----------------------");

        Triage triage = new Triage();

        triage.admit("Patient A", 3);
        triage.admit("Patient B", 1);
        triage.admit("Patient C", 2);
        triage.admit("Patient D", 1);
        triage.admit("Patient E", 5);
        triage.admit("Patient F", 3);
        triage.admit("Patient G", 2);
        triage.admit("Patient H", 4);

        System.out.println("Total patients: " + triage.size());

        System.out.println("\nTreatment Order:");

        while (!triage.isEmpty()) {
            System.out.println(triage.next());
        }
    }

    // Demonstrate why direct PriorityQueue printing
    // does not guarantee treatment order.
    static void queueDisplayTest() {

        System.out.println("\nTEST 2: PRIORITY QUEUE DISPLAY");
        System.out.println("------------------------------");

        PriorityQueue<Patient> pq =
                new PriorityQueue<>(
                        Comparator.comparingInt(
                                (Patient p) -> p.severity)
                        .thenComparingLong(p -> p.arrival)
                );

        pq.add(new Patient("A", 4, 0));
        pq.add(new Patient("B", 1, 1));
        pq.add(new Patient("C", 3, 2));
        pq.add(new Patient("D", 2, 3));

        System.out.println("Direct queue display:");
        System.out.println(pq);

        System.out.println("\nActual treatment order:");

        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }

    // Performance experiment
    static void growthExperiment(int size) {

        PriorityQueue<Patient> pq =
                new PriorityQueue<>(
                        Comparator.comparingInt(
                                (Patient p) -> p.severity)
                        .thenComparingLong(p -> p.arrival)
                );

        long startAdd = System.nanoTime();

        for (int i = 0; i < size; i++) {
            pq.add(new Patient(
                    "Patient" + i,
                    (i % 5) + 1,
                    i
            ));
        }

        long endAdd = System.nanoTime();

        long startRemove = System.nanoTime();

        while (!pq.isEmpty()) {
            pq.poll();
        }

        long endRemove = System.nanoTime();

        double addTime =
                (endAdd - startAdd) / 1_000_000.0;

        double removeTime =
                (endRemove - startRemove) / 1_000_000.0;

        System.out.println("\nQueue Size: " + size);

        System.out.printf(
                "Adding patients: %.3f ms%n", addTime);

        System.out.printf(
                "Removing patients: %.3f ms%n", removeTime);
    }

    // Compare normal FIFO queue with priority queue
    static void normalQueueTest() {

        System.out.println("\nTEST 3: NORMAL FIFO QUEUE");
        System.out.println("-------------------------");

        Queue<Patient> normalQueue = new ArrayDeque<>();

        normalQueue.add(new Patient("Minor Patient", 5, 0));
        normalQueue.add(new Patient("Critical Patient", 1, 1));
        normalQueue.add(new Patient("Moderate Patient", 3, 2));

        while (!normalQueue.isEmpty()) {
            System.out.println(normalQueue.poll());
        }
    }

    public static void main(String[] args) {

        System.out.println("HOSPITAL EMERGENCY TRIAGE SYSTEM");
        System.out.println("================================");

        handVerificationTest();

        queueDisplayTest();

        normalQueueTest();

        System.out.println("\nGROWTH INVESTIGATION");
        System.out.println("--------------------");

        int[] sizes = {1000, 10000, 50000};

        for (int size : sizes) {
            growthExperiment(size);
        }

        System.out.println("\nAll triage experiments completed.");
    }
}
