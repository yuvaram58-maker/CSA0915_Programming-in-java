
# Q1 - Building Text Under Load

## Objective
To compare String, StringBuilder and StringBuffer
for generating railway e-tickets.

## Experimental Workloads

| Workload | Tickets | Fields |
|---|---:|---:|
| Short Ticket | 1 | 6 |
| Large Workload | 10000 | 50 |
| Custom Workload | 2000 | 100 |

## Experimental Results

| Workload | Approach | Average Time (ms) |
|---|---|---:|
| Short Ticket | String | |
| Short Ticket | StringBuilder | |
| Short Ticket | StringBuffer | |
| Large Workload | String | |
| Large Workload | StringBuilder | |
| Large Workload | StringBuffer | |
| Custom Workload | String | |
| Custom Workload | StringBuilder | |
| Custom Workload | StringBuffer | |

## Prediction
String is expected to create more intermediate objects
when repeatedly concatenated.

StringBuilder is expected to perform efficiently
for single-threaded ticket generation.

StringBuffer provides synchronized methods and may
have additional overhead.

## Conclusion
(To be completed using actual experimental results.)

## 1. Prediction Before Experiment

1. String: Repeated concatenation creates intermediate
   String objects, increasing processing overhead.

2. StringBuilder: Uses a mutable sequence of characters.
   It is expected to perform efficiently for repeated
   appending in a single thread.

3. StringBuffer: Similar to StringBuilder, but its
   methods are synchronized. This may introduce
   additional overhead.

## 2. Estimated Intermediate Objects

For n fields:

- String: Approximately n intermediate String results
  per ticket, excluding implementation optimizations.
- StringBuilder: One builder and its internal storage,
  followed by the final String.
- StringBuffer: One buffer and its internal storage,
  followed by the final String.

These are estimates, not exact JVM object counts.

## 3. Design Decision

For individual e-ticket generation, StringBuilder is
recommended because it provides efficient mutable
text construction in a single thread.

For a shared booking log accessed by multiple threads,
StringBuffer is a suitable choice because its methods
are synchronized.

For complete log-entry safety, synchronization should
also protect the entire append operation when necessary.

## 4. Complexity Analysis

Let n represent the number of fields.

| Approach | Time Complexity |
|---|---|
| Repeated String + | O(n²) character copying |
| StringBuilder | O(n) amortized |
| StringBuffer | O(n) amortized |

Repeated String concatenation creates intermediate
strings, increasing temporary memory usage.

StringBuilder and StringBuffer use mutable character
storage and reduce repeated intermediate String creation.

## 5. Alternative Concepts

StringJoiner:
Can combine multiple ticket fields using a delimiter.

String.join():
Can combine a collection of strings using a delimiter.

String.format():
Can construct formatted ticket text, but formatting
has additional processing overhead.

## 6. Final Conclusion

The experiment compares the performance of String,
StringBuilder and StringBuffer under different workloads.

The final recommendation will be based on the
measured execution times and observed behaviour.
