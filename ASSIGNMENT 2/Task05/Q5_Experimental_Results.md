
# Q5 - Generic Ranking Component

## Objective
To develop a reusable generic leaderboard using
Comparator and Java Generics.

## Ranking Rules

1. Higher points rank first.
2. Higher goal difference ranks first.
3. Team name is compared alphabetically.

## Test Cases

### Test 1: Multiple Ties
Verified points and goal-difference tie breakers.

### Test 2: Faulty Comparator
Tested integer subtraction using extreme integer
values to demonstrate overflow.

### Test 3: Alphabetical Tie Breaker
Verified alphabetical ordering for teams with
equal points and goal difference.

## Generic Design

Leaderboard<T> stores any object type T.

Comparator<? super T> allows a comparator
that compares T or its parent type.

List<? extends T> allows adding lists containing
T or its subclasses.

## Complexity Analysis

| Operation | Complexity |
|---|---|
| Add one item | O(1) amortized |
| Generate ranking | O(n log n) |
| Copy list | O(n) |
| Extra space | O(n) |

## Alternative Concepts

Comparable:
Defines a natural ordering inside a class.

Comparator:
Defines external ordering and supports
multiple ranking strategies.

Stable sorting:
Multiple stable sorts can be performed from
the least important field to the most important
field, but require multiple sorting passes.

## Conclusion

The generic leaderboard supports reusable ranking
with multiple comparison criteria.

Comparator chaining provides a clear and flexible
solution while avoiding integer subtraction overflow.
