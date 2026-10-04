# DAA Assignment 2 - Data Structures

## Project Description

This project was created for Assignment 2 in Design and Analysis of Algorithms.

The goal of the project is to implement basic data structures from scratch, test their correctness, measure their real performance, count physical operations, and compare theoretical complexity with actual execution time.

The project includes three custom data structures:

- DynamicArray
- MyLinkedList
- MinHeap

All data structures store primitive `int` values.

The project does not use:

- java.util.ArrayList inside DynamicArray
- java.util.LinkedList inside MyLinkedList
- java.util.PriorityQueue inside MinHeap

Java standard collections are used only inside tests and utility code where allowed.

---

## Implemented Data Structures

### DynamicArray

DynamicArray uses an internal primitive array.

Implemented methods:

- add(int value)
- add(int index, int value)
- remove(int index)
- get(int index)
- contains(int value)
- size()

The internal array grows by 2x when it becomes full.

---

### MyLinkedList

MyLinkedList is implemented as a singly linked list.

Each node stores:

- int value
- Node next

The list also stores:

- head
- tail

Implemented methods:

- add(int value)
- add(int index, int value)
- remove(int index)
- get(int index)
- contains(int value)
- size()

The tail reference is used to make adding a value to the end efficient.

---

### MinHeap

MinHeap is implemented using an internal primitive int array.

Implemented methods:

- insert(int value)
- peekMin()
- extractMin()
- size()
- isEmpty()
- isValidHeap()

The heap uses:

- bubble-up after insert
- bubble-down after extractMin

The min-heap property is:

parent <= child

---

## Project Structure

```text
DAA_Assignment2
│
├── pom.xml
├── README.md
├── REPORT.md
│
├── src
│   ├── main
│   │   └── java
│   │       ├── benchmark
│   │       │   ├── BenchmarkRunner.java
│   │       │   └── PlotGenerator.java
│   │       │
│   │       ├── metrics
│   │       │   └── OperationCounter.java
│   │       │
│   │       └── structures
│   │           ├── DynamicArray.java
│   │           ├── MyLinkedList.java
│   │           └── MinHeap.java
│   │
│   └── test
│       └── java
│           └── structures
│               ├── DynamicArrayTest.java
│               ├── MyLinkedListTest.java
│               └── MinHeapTest.java
│
└── results
    ├── results.csv
    └── plots
```

---

## Technologies

The project uses:

- Java 17
- Maven
- JUnit 5
- XChart

---

## Requirements

Before running the project, make sure you have:

- JDK 17
- Maven
- IntelliJ IDEA or another Java IDE

---

## How to Build the Project

Open the project in IntelliJ IDEA.

Make sure Maven dependencies are loaded.

Then use:

```text
Build -> Build Project
```

You can also build the project from the terminal:

```bash
mvn clean compile
```

---

## How to Run Tests

The project uses JUnit 5.

To run all tests in IntelliJ IDEA:

1. Open `src/test/java`
2. Right-click the `structures` package
3. Select `Run Tests`

You can also run tests from Maven:

```bash
mvn test
```

The tests cover:

- empty structures
- one element
- duplicate values
- first index
- last index
- invalid indexes
- random data
- DynamicArray resizing
- LinkedList operations
- heap property
- empty heap exceptions
- sorted MinHeap output

---

## DynamicArray Tests

DynamicArray tests check:

- add values
- insert by index
- remove by index
- get values
- contains values
- duplicate values
- invalid indexes
- resizing
- random values

The random test compares the custom DynamicArray with an expected result.

---

## MyLinkedList Tests

MyLinkedList tests check:

- add values
- add by index
- remove values
- remove first element
- remove last element
- get values
- contains values
- duplicate values
- invalid indexes
- random values

---

## MinHeap Tests

MinHeap tests check:

- insert
- peekMin
- extractMin
- duplicate values
- one element
- empty heap
- heap property after insert
- heap property after extractMin
- random values
- sorted output

Repeated `extractMin()` calls must return values in non-decreasing order.

---

## Operation Metrics

The project counts three physical operation types:

- steps
- moves
- comparisons

### Steps

For DynamicArray and MinHeap:

A step means reading an array cell.

For MyLinkedList:

A step means moving from one node to the next node.

Example:

```java
current = current.next;
```

### Moves

For arrays:

A move means shifting or copying an element.

Example:

```java
data[i] = data[i - 1];
```

For MyLinkedList:

A move means changing a reference.

Example:

```java
current.next = newNode;
```

### Comparisons

A comparison means comparing two stored values.

Example:

```java
data[i] == value
```

or:

```java
heap[left] < heap[smallest]
```

The metrics are counted directly inside the data structure methods.

---

## Benchmark

The benchmark is implemented in:

```text
src/main/java/benchmark/BenchmarkRunner.java
```

To run it in IntelliJ IDEA:

1. Open `BenchmarkRunner.java`
2. Run the `main()` method

The benchmark uses:

```text
n = 100
n = 1,000
n = 10,000
n = 100,000
```

The same random seed is used:

```java
new Random(42)
```

This makes the results reproducible.

---

## Benchmark Workloads

### W1 - Random Access

Structures:

- DynamicArray
- MyLinkedList

Procedure:

1. Fill the structure with `n` values
2. Perform 10,000 random `get(index)` operations

---

### W2 - Search

Structures:

- DynamicArray
- MyLinkedList

Procedure:

1. Perform 1,000 `contains(value)` operations
2. Half of the searched values are present
3. Half of the searched values are absent

---

### W3 - Insert and Remove

Structures:

- DynamicArray
- MyLinkedList

Two variants are tested.

#### Head

- 1,000 insertions at index 0
- 1,000 removals at index 0

#### Middle

- 1,000 insertions around index `n / 2`
- 1,000 removals around index `n / 2`

---

### W4 - Priority Processing

Structure:

- MinHeap

Procedure:

1. Insert `n` values
2. Call `extractMin()` until the heap is empty
3. Verify that returned values are in non-decreasing order

---

## Benchmark Runs

Every benchmark case uses:

- 1 warm-up run
- 5 measured runs

The warm-up run is discarded.

The median time from the 5 measured runs is saved.

---

## Benchmark Results

The benchmark creates:

```text
results/results.csv
```

The CSV columns are:

```text
workload,variant,structure,n,time_ms,steps,moves,comparisons
```

For W3, the variant is:

```text
head
```

or:

```text
middle
```

For other workloads, the variant is:

```text
-
```

---

## How to Generate Plots

Plots are generated by:

```text
src/main/java/benchmark/PlotGenerator.java
```

To generate plots:

1. Run `BenchmarkRunner` first
2. Make sure `results/results.csv` exists
3. Open `PlotGenerator.java`
4. Run the `main()` method

The generated plots are saved in:

```text
results/plots/
```

---

## Plot Types

Detailed plots are generated for every workload.

The project creates plots for:

- time vs input size
- operations overview
- steps vs input size
- moves vs input size
- comparisons vs input size

Each plot contains:

- descriptive title
- x-axis label
- y-axis label
- measurement units
- legend
- grid lines
- separate series for relevant structures

---

## Report

The full analysis is available in:

```text
REPORT.md
```

The report includes:

- implementation description
- complexity analysis
- best case
- average case
- worst case
- auxiliary space
- two loop invariant proofs
- benchmark methodology
- workload analysis
- benchmark plots
- CPU cache locality
- spatial locality
- pointer chasing
- linked-list memory overhead
- garbage collector effects
- comparison of all three structures

---

## Loop Invariants

Two operations are formally analyzed using loop invariants.

### DynamicArray contains()

The proof contains:

- invariant
- initialization
- maintenance
- termination
- conclusion

The invariant states that before iteration `i`, none of the already checked elements from index 0 to index `i - 1` is equal to the searched value.

### MinHeap Bubble-Down

The proof also contains:

- invariant
- initialization
- maintenance
- termination
- conclusion

The invariant states that before every bubble-down iteration, all nodes outside the current subtree already satisfy the heap property and the only possible violation is located at the current node.

---

## Invalid Input Handling

DynamicArray and MyLinkedList throw:

```text
IndexOutOfBoundsException
```

for invalid indexes in:

- get(index)
- add(index, value)
- remove(index)

MinHeap throws:

```text
IllegalStateException
```

when `peekMin()` or `extractMin()` is called on an empty heap.

---

## Reproducibility

The benchmark uses:

```java
new Random(42)
```

for reproducible input data.

The same benchmark configuration can be executed again to produce a new `results.csv`.

All operation counters are collected inside the implementations.

---

## Git Workflow

The required branches are:

```text
main
feature/array
feature/list
feature/heap
feature/metrics
```

The `main` branch contains the final working version.

Example commit messages:

```text
feat(array): implement dynamic array
test(array): add dynamic array tests
feat(list): implement linked list
test(list): add linked list tests
feat(heap): implement min heap
test(heap): check heap property
feat(metrics): add operation counters
feat(benchmark): add workloads
docs(report): add benchmark analysis
```

The final release must have the tag:

```text
v1.0
```

---

## Running the Complete Project

Recommended order:

1. Build the project
2. Run all JUnit tests
3. Run `BenchmarkRunner`
4. Check `results/results.csv`
5. Run `PlotGenerator`
6. Check `results/plots/`
7. Open `REPORT.md`

From the terminal, tests can be executed with:

```bash
mvn test
```

The project can be compiled with:

```bash
mvn clean compile
```

---

## Main Results

DynamicArray is effective for random access because array indexing provides direct access to elements.

It also benefits from contiguous memory and CPU cache locality.

MyLinkedList is effective for frequent modifications at the beginning of the structure because no array elements need to be shifted.

However, random indexed access requires pointer traversal from the head.

MinHeap is useful for priority-based workloads where the minimum element must be accessed and removed repeatedly.

Its minimum value is stored at the root, so `peekMin()` is constant time.

---

## Conclusion

This project implements DynamicArray, MyLinkedList, and MinHeap from scratch using primitive `int` values.

The project compares theoretical complexity with real execution behavior.

JUnit 5 tests are used to verify correctness and edge cases.

The benchmark measures execution time and physical operations for four different workloads.

The results are exported to CSV and visualized using detailed plots.

The project demonstrates that choosing a data structure depends not only on Big-O complexity, but also on the real workload, memory layout, cache behavior, pointer traversal, element shifting, and implementation details.