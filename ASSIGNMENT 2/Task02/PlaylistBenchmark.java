package Task02;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntFunction;

public class PlaylistBenchmark {

    static volatile long checksum = 0;

    static class Song {
        int id;

        Song(int id) {
            this.id = id;
        }
    }

    static List<Song> createArrayList(int size) {
        List<Song> songs = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            songs.add(new Song(i));
        }

        return songs;
    }

    static List<Song> createLinkedList(int size) {
        List<Song> songs = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            songs.add(new Song(i));
        }

        return songs;
    }

    static long measure(IntFunction<Long> test, int repetitions) {
        long total = 0;

        for (int i = 0; i < repetitions; i++) {
            total += test.apply(i);
        }

        return total / repetitions;
    }

    static long addAtEnd(List<Song> songs) {
        long start = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            songs.add(new Song(i));
        }

        return System.nanoTime() - start;
    }

    static long insertRemoveFront(List<Song> songs) {
        long start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            songs.add(0, new Song(i));
            songs.remove(0);
        }

        return System.nanoTime() - start;
    }

    static long insertRemoveMiddle(List<Song> songs) {
        long start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            int middle = songs.size() / 2;
            songs.add(middle, new Song(i));
            songs.remove(middle);
        }

        return System.nanoTime() - start;
    }

    static long accessKth(List<Song> songs) {
        long start = System.nanoTime();
        long total = 0;

        for (int i = 0; i < 1000; i++) {
            int index = (i * 37) % songs.size();
            total += songs.get(index).id;
        }

        checksum = total;

        return System.nanoTime() - start;
    }

    static long iterate(List<Song> songs) {
        long start = System.nanoTime();
        long total = 0;

        for (int repeat = 0; repeat < 10; repeat++) {
            for (Song song : songs) {
                total += song.id;
            }
        }

        checksum = total;

        return System.nanoTime() - start;
    }

    static void testList(String name, int size, boolean arrayList) {

        System.out.println("\n" + name + " | Playlist Size: " + size);

        IntFunction<List<Song>> factory =
                arrayList ? PlaylistBenchmark::createArrayList
                          : PlaylistBenchmark::createLinkedList;

        System.out.printf("Add at end: %.3f ms%n",
                measure(i -> addAtEnd(factory.apply(size)), 3) / 1_000_000.0);

        System.out.printf("Insert/Remove front: %.3f ms%n",
                measure(i -> insertRemoveFront(factory.apply(size)), 3) / 1_000_000.0);

        System.out.printf("Insert/Remove middle: %.3f ms%n",
                measure(i -> insertRemoveMiddle(factory.apply(size)), 3) / 1_000_000.0);

        System.out.printf("Access k-th element: %.3f ms%n",
                measure(i -> accessKth(factory.apply(size)), 3) / 1_000_000.0);

        System.out.printf("Full iteration: %.3f ms%n",
                measure(i -> iterate(factory.apply(size)), 3) / 1_000_000.0);
    }

    static void testArrayDeque(int size) {

        long total = 0;

        for (int repeat = 0; repeat < 3; repeat++) {

            ArrayDeque<Song> queue = new ArrayDeque<>();

            for (int i = 0; i < size; i++) {
                queue.addLast(new Song(i));
            }

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                queue.addFirst(new Song(i));
                queue.addLast(new Song(i));
                queue.removeFirst();
                queue.removeLast();
            }

            total += System.nanoTime() - start;
        }

        System.out.printf("ArrayDeque end operations: %.3f ms%n",
                total / 3.0 / 1_000_000.0);
    }

    public static void main(String[] args) {

        int[] sizes = {1000, 10000, 30000};

        System.out.println("MUSIC PLAYLIST PERFORMANCE BENCHMARK");
        System.out.println("------------------------------------");

        for (int size : sizes) {

            testList("ArrayList", size, true);

            testList("LinkedList", size, false);

            System.out.println("ArrayDeque | Playlist Size: " + size);
            testArrayDeque(size);
        }

        System.out.println("\nAll playlist experiments completed.");
        System.out.println("Verification checksum: " + checksum);
    }
}
