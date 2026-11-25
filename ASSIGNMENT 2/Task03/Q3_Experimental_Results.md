
# Q3 - Engineer the hashCode-equals Contract

## Objective
To investigate how equals() and hashCode() work
together in HashSet to identify duplicate students.

## 1. Prediction

Without overriding hashCode(), two different Student
objects with the same registration number may be
stored as separate entries.

After overriding hashCode(), equal registration
numbers produce equal hash codes, allowing HashSet
to identify duplicates correctly.

## 2. Experimental Results

### Test 1: Duplicate Registration Numbers

Before correction:
Observed unique students: (enter actual output)

After correction:
Observed unique students: (enter actual output)

Expected after correction: 2

### Test 2: Different Students

Observed unique students: (enter actual output)

Expected: 3

### Test 3: Same Object Added Twice

Observed unique students: (enter actual output)

Expected: 1

### Test 4: Mutable HashCode Field

Contains before modification: (enter actual output)

Contains after modification: (enter actual output)

Set size: (enter actual output)

## 3. Why Duplicates Appear

HashSet uses hashCode() to identify the bucket
where an object should be stored.

It then uses equals() to compare objects within
the relevant bucket.

If equals() considers two objects equal but
hashCode() is not overridden consistently,
duplicate logical objects may be stored.

## 4. Corrected Student Class

The equals() and hashCode() methods use regNo
as the unique student identifier.

The regNo field is final to prevent modification
after object creation.

## 5. Alternative Concepts

TreeSet:
Uses a Comparator on registration number.
It provides O(log n) insertion and lookup.
It maintains sorted order.

HashMap:
Uses registration number as the key and
Student as the value.
It provides O(1) average lookup and insertion.

## 6. Complexity Analysis

| Collection | Average/Typical Lookup | Space |
|---|---|---|
| HashSet | O(1) average | O(n) |
| TreeSet | O(log n) | O(n) |
| HashMap | O(1) average | O(n) |

## 7. Conclusion

Overriding both equals() and hashCode() is
necessary for correct hash-based de-duplication.

Immutable registration numbers help prevent
problems caused by modifying hash-related fields.
