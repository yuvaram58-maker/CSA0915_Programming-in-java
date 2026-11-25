
# Q4 - Choosing a Map for a Pharmacy Inventory

## Objective
To compare HashMap, TreeMap and Hashtable for
hospital pharmacy inventory management.

## Experimental Sizes

1. 1,000 medicines
2. 10,000 medicines
3. 50,000 medicines

## Experimental Results

Enter actual values from the terminal.

| Size | Map | 100,000 Lookups (ms) |
|---:|---|---:|
| 1,000 | HashMap | |
| 1,000 | TreeMap | |
| 1,000 | Hashtable | |
| 10,000 | HashMap | |
| 10,000 | TreeMap | |
| 10,000 | Hashtable | |
| 50,000 | HashMap | |
| 50,000 | TreeMap | |
| 50,000 | Hashtable | |

## Report Generation Results

Record actual execution times.

| Size | TreeMap Sorted Report (ms) | LinkedHashMap Insertion Report (ms) |
|---:|---:|---:|
| 1,000 | | |
| 10,000 | | |
| 50,000 | | |

## Order Verification

- HashMap: Does not guarantee ordering.
- TreeMap: Maintains sorted key order.
- LinkedHashMap: Maintains insertion order.
- Hashtable: Does not guarantee ordering.

## Scalability Inquiry

For this experiment, define an acceptable average
lookup response time as 1 millisecond per lookup.

Average lookup time =
Total time for 100,000 lookups / 100,000.

HashMap is suitable when fast medicine-code lookups
are the main requirement.

TreeMap is suitable when sorted code reports are
required directly.

Hashtable is generally not preferred in new Java
applications because it is a legacy synchronized
collection. ConcurrentHashMap is usually a better
choice for concurrent access.

## Alternative: Sorted Array and Binary Search

A sorted array of medicine codes can use
Arrays.binarySearch() for lookup.

Complexity comparison:

| Operation | Sorted Array | HashMap | TreeMap |
|---|---|---|---|
| Lookup | O(log n) | O(1) average | O(log n) |
| Insertion | O(n) | O(1) average | O(log n) |
| Sorted report | O(n) | O(n log n) to sort | O(n) |
| Space | O(n) | O(n) | O(n) |

## Conclusion

The map selection depends on lookup speed,
report ordering and concurrency requirements.

The final deployment decision should be supported
by the measured experimental results.
