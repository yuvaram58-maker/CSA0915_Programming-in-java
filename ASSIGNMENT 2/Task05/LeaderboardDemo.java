
package Task05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Team {

    final String name;
    final int points;
    final int goalDiff;

    Team(String name, int points, int goalDiff) {
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    @Override
    public String toString() {
        return name + "(" + points + "," + goalDiff + ")";
    }
}

class Leaderboard<T> {

    private final List<T> items = new ArrayList<>();
    private final Comparator<? super T> order;

    Leaderboard(Comparator<? super T> order) {
        this.order = order;
    }

    void add(T item) {
        items.add(item);
    }

    // Accepts a list of T or its subtypes
    void addAll(List<? extends T> newItems) {
        items.addAll(newItems);
    }

    List<T> ranking() {

        // TODO 1: Create and sort a copy without
        // modifying the original items.

        List<T> sorted = new ArrayList<>(items);
        sorted.sort(order);

        return sorted;
    }

    int size() {
        return items.size();
    }
}

public class LeaderboardDemo {

    public static void main(String[] args) {

        System.out.println("GENERIC LEADERBOARD DEMONSTRATION");
        System.out.println("----------------------------------");

        // Correct ranking comparator
        Comparator<Team> correctOrder =
                Comparator.comparingInt((Team t) -> t.points)
                .reversed()
                .thenComparing(
                        Comparator.comparingInt(
                                (Team t) -> t.goalDiff).reversed())
                .thenComparing(t -> t.name);

        Leaderboard<Team> board =
                new Leaderboard<>(correctOrder);

        // Dataset 1: Several ties
        List<Team> teams = new ArrayList<>();

        teams.add(new Team("Falcons", 30, 12));
        teams.add(new Team("Eagles", 30, 15));
        teams.add(new Team("Tigers", 25, 10));
        teams.add(new Team("Lions", 30, 15));
        teams.add(new Team("Bulls", 25, 10));
        teams.add(new Team("Sharks", 20, 5));

        board.addAll(teams);

        System.out.println("\nTest 1: Correct Ranking");
        System.out.println("Leaderboard:");

        for (Team team : board.ranking()) {
            System.out.println(team);
        }

        // Verify original list is not modified
        System.out.println("\nOriginal list size: " + board.size());

        // Test 2: Faulty comparator using subtraction
        Comparator<Team> faultyOrder =
                (a, b) -> b.points - a.points;

        Team high = new Team(
                "HighPoints", Integer.MAX_VALUE, 0);

        Team low = new Team(
                "LowPoints", Integer.MIN_VALUE, 0);

        System.out.println("\nTest 2: Faulty Comparator");

        System.out.println("High points: " + high.points);
        System.out.println("Low points: " + low.points);

        System.out.println("Faulty comparison result: "
                + faultyOrder.compare(high, low));

        System.out.println("Correct comparison result: "
                + Comparator.comparingInt(
                        (Team t) -> t.points)
                        .reversed()
                        .compare(high, low));

        // Test 3: Tie-breaker verification
        System.out.println("\nTest 3: Tie Breaker");

        Team teamA = new Team("Alpha", 40, 12);
        Team teamB = new Team("Beta", 40, 12);

        System.out.println("Alpha vs Beta: "
                + correctOrder.compare(teamA, teamB));

        System.out.println("\nAll leaderboard tests completed.");
    }
}
