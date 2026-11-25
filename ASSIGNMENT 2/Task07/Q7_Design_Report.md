
# Q7 - Diagnose, Redesign and Validate

## A. Diagnose the Bottleneck (2 Marks)

The original Q1 implementation used repeated String
concatenation to generate railway e-tickets.

The statement:

ticket = ticket + data[j];

creates intermediate String results during repeated
concatenation.

This can increase processing time and temporary
memory usage for large workloads.

Evidence:
Refer to Q1 experimental results and Q7 original
implementation measurements.

## B. Define the Design Objective (2 Marks)

The objective is to reduce ticket-generation
processing time and unnecessary intermediate
String creation.

The redesigned implementation must:

1. Produce identical ticket output.
2. Support different workload sizes.
3. Reduce repeated intermediate String creation.
4. Maintain correctness.
5. Be evaluated using repeated measurements.

## C. Design the Revised Solution (4 Marks)

Original:
Repeated String concatenation.

Redesigned:
StringBuilder with append().

String is immutable, whereas StringBuilder
maintains a mutable character sequence.

The redesigned approach avoids repeatedly
creating intermediate immutable String results.

## D. Explain the Design (Part of 4 Marks)

Original complexity:
Repeated concatenation can require O(n²)
character copying for n fields.

Redesigned complexity:
StringBuilder provides O(n) amortized
append processing for n fields.

The final String creation and internal buffer
management also have costs.

## E. Implement and Compare the Solutions

Both approaches use identical input workloads.

Measurements include:
- Small workload
- Medium workload
- Large workload
- Seven measured repetitions
- Median execution time
- Raw timing values
- Output correctness verification
- Operation counts

Insert the actual results from the terminal.

## F. Defend the Final Design (2 Marks)

The redesign is expected to reduce repeated
intermediate String creation.

The final decision must be supported by measured
execution times.

Advantages:
- Mutable text construction.
- Efficient repeated appending.
- Better scalability for large text construction.

Trade-offs:
- Requires mutable buffer management.
- StringBuilder is not synchronized.
- Small workloads may show little measurable
  difference.

The original String approach remains suitable
for simple, occasional concatenation.

## Final Engineering Conclusion

StringBuilder is recommended for repeated
ticket-text generation in a single-threaded
environment when supported by experimental
results.

The final decision is based on correctness,
performance measurements and scalability.
