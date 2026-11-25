
# Q6 - Priority, Order and Fairness in a Queue

## Objective
To implement a hospital emergency triage system
using Java PriorityQueue and Comparator.

## 1. Priority Rules

1. Severity 1 receives the highest priority.
2. Severity 5 receives the lowest priority.
3. Equal severity patients are treated in arrival order.

## 2. Hand Verification

Eight patients were used to verify the treatment order.

Expected order:

B, D, C, G, A, F, H, E

The program output was compared with this expected order.

## 3. Why Normal Queue Is Not Suitable

A normal FIFO Queue serves patients according
to arrival order.

It does not consider medical severity.

Therefore, a critical patient may wait behind
a minor patient.

## 4. PriorityQueue Design

PriorityQueue uses a Comparator.

The comparator first compares severity and then
compares arrival sequence.

This provides priority-based treatment and
FIFO ordering among equal severity patients.

## 5. Direct Queue Printing

Printing PriorityQueue directly does not guarantee
sorted treatment order.

The queue guarantees that poll() returns the
highest-priority element.

Therefore, repeated poll() operations are used
to display the actual treatment sequence.

## 6. Growth Investigation

Record actual results from the terminal.

| Queue Size | Adding Time (ms) | Removing Time (ms) |
|---:|---:|---:|
| 1,000 | | |
| 10,000 | | |
| 50,000 | | |

## 7. Complexity Analysis

| Operation | Complexity |
|---|---|
| Add patient | O(log n) |
| Remove patient | O(log n) |
| Peek next patient | O(1) |
| Space | O(n) |

## 8. Alternative Designs

### Sorted LinkedList
Insertion: O(n)
Removal of first patient: O(1)

### Five FIFO Queues
One queue is maintained for each severity level.
Insertion: O(1)
Removal of next patient: O(1) when finding
the first non-empty severity queue takes O(1).

Space: O(n)

### PriorityQueue
Insertion: O(log n)
Removal: O(log n)
Space: O(n)

## 9. Starvation and Fairness

A severity-5 patient may wait indefinitely if
higher-severity patients continue arriving.

This is called starvation.

Aging can reduce starvation by gradually increasing
the priority of patients who have waited longer.

For example, a waiting patient can receive a higher
priority after a defined waiting-time threshold.

Any aging policy must preserve clinical safety and
be approved by hospital medical staff.

## 10. Deployment Decision

A PriorityQueue has O(log n) insertion and removal
complexity and O(n) storage.

It is technically suitable for handling a workload
of 5,000 patients per day.

However, production deployment also requires
concurrency handling, persistent patient records,
monitoring, clinical validation and reliable backup.

## Conclusion

The experiment demonstrates how PriorityQueue
provides severity-based patient scheduling while
preserving arrival order among patients with
equal severity.
