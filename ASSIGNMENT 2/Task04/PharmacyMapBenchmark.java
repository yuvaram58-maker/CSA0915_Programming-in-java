
package Task04;

import java.util.*;

class Medicine {
    String code;
    String name;
    int quantity;

    Medicine(String code, String name, int quantity) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }
}

public class PharmacyMapBenchmark {

    static final int LOOKUPS = 100000;
    static volatile long checksum = 0;

    static String code(int id) {
        return String.format("MED%06d", id);
    }

    static String medicineName(int id) {
        return "Medicine_" + id;
    }

    static Map<String, Medicine> createHashMap(int size) {
        Map<String, Medicine> map = new HashMap<>();

        for (int i = 0; i < size; i++) {
            map.put(code(i),
                    new Medicine(code(i), medicineName(i), 100 + i));
        }

        return map;
    }

    static Map<String, Medicine> createTreeMap(int size) {
        Map<String, Medicine> map = new TreeMap<>();

        for (int i = 0; i < size; i++) {
            map.put(code(i),
                    new Medicine(code(i), medicineName(i), 100 + i));
        }

        return map;
    }

    static Map<String, Medicine> createHashtable(int size) {
        Map<String, Medicine> map = new Hashtable<>();

        for (int i = 0; i < size; i++) {
            map.put(code(i),
                    new Medicine(code(i), medicineName(i), 100 + i));
        }

        return map;
    }

    static Map<String, Medicine> createInsertionOrderMap(int size) {
        Map<String, Medicine> map = new LinkedHashMap<>();

        for (int i = size - 1; i >= 0; i--) {
            map.put(code(i),
                    new Medicine(code(i), medicineName(i), 100 + i));
        }

        return map;
    }

    static long lookupTime(Map<String, Medicine> map, int size) {

        long total = 0;
        long start = System.nanoTime();

        for (int i = 0; i < LOOKUPS; i++) {
            int id = (i * 37) % size;
            Medicine medicine = map.get(code(id));

            if (medicine != null) {
                total += medicine.quantity;
            }
        }

        long end = System.nanoTime();
        checksum = total;

        return end - start;
    }

    static long reportTime(Map<String, Medicine> map) {

        long total = 0;
        long start = System.nanoTime();

        for (Map.Entry<String, Medicine> entry : map.entrySet()) {
            total += entry.getValue().quantity;
        }

        long end = System.nanoTime();
        checksum = total;

        return end - start;
    }

    static boolean isSorted(Map<String, Medicine> map) {

        String previous = null;

        for (String current : map.keySet()) {

            if (previous != null &&
                    previous.compareTo(current) > 0) {
                return false;
            }

            previous = current;
        }

        return true;
    }

    static void runExperiment(int size) {

        System.out.println("\n====================================");
        System.out.println("Number of Medicines: " + size);
        System.out.println("====================================");

        Map<String, Medicine> hashMap = createHashMap(size);
        Map<String, Medicine> treeMap = createTreeMap(size);
        Map<String, Medicine> hashtable = createHashtable(size);
        Map<String, Medicine> linkedMap =
                createInsertionOrderMap(size);

        System.out.println("\nLOOKUP TIME FOR 100000 SEARCHES");

        System.out.printf("HashMap: %.3f ms%n",
                lookupTime(hashMap, size) / 1_000_000.0);

        System.out.printf("TreeMap: %.3f ms%n",
                lookupTime(treeMap, size) / 1_000_000.0);

        System.out.printf("Hashtable: %.3f ms%n",
                lookupTime(hashtable, size) / 1_000_000.0);

        System.out.println("\nREPORT GENERATION TIME");

        System.out.printf("TreeMap Code Order Report: %.3f ms%n",
                reportTime(treeMap) / 1_000_000.0);

        System.out.printf("LinkedHashMap Insertion Order Report: %.3f ms%n",
                reportTime(linkedMap) / 1_000_000.0);

        System.out.println("\nORDER VERIFICATION");

        System.out.println("HashMap code order guaranteed: No");
        System.out.println("TreeMap code order correct: "
                + isSorted(treeMap));

        System.out.println("LinkedHashMap insertion order correct: "
                + code(size - 1).equals(
                    linkedMap.keySet().iterator().next()));

        System.out.println("All maps contain medicines: "
                + (hashMap.size() == size
                && treeMap.size() == size
                && hashtable.size() == size));

    }

    public static void main(String[] args) {

        System.out.println("PHARMACY INVENTORY MAP BENCHMARK");
        System.out.println("--------------------------------");

        int[] sizes = {1000, 10000, 50000};

        for (int size : sizes) {
            runExperiment(size);
        }

        System.out.println("\nAll pharmacy experiments completed.");
        System.out.println("Verification checksum: " + checksum);
    }
}
