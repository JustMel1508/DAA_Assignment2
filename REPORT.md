# Assignment 2 - Data Structures Report

## 1. Introduction

The goal of this assignment was to implement and compare three data structures from scratch:

- DynamicArray
- MyLinkedList
- MinHeap

All structures store primitive `int` values.

The main purpose of the assignment was not only to implement the data structures correctly, but also to analyze their performance, count physical operations, compare theoretical complexity with real execution time, and explain why structures with similar Big-O complexity may still perform differently.

The benchmark was executed for the following values of `n`:

- 100
- 1,000
- 10,000
- 100,000

The same pseudo-random data was used for every structure.

The data was generated with:

new Random(42)

Using the same random seed makes the benchmark reproducible.

---

# 2. Implemented Data Structures

## 2.1 DynamicArray

DynamicArray is implemented using an internal primitive array:

int[]

The initial capacity of the array is 4.

When the internal array becomes full, its capacity is doubled.

For example:

4 -> 8 -> 16 -> 32 -> ...

The following operations were implemented:

- add(int value)
- add(int index, int value)
- remove(int index)
- get(int index)
- contains(int value)

The structure also counts three types of operations:

- steps
- moves
- comparisons

A step represents reading an array cell.

A move represents shifting or copying an array element.

A comparison represents comparing two values.

The get(index) operation uses direct array indexing, so only one array access is required.

---

## 2.2 MyLinkedList

MyLinkedList is implemented as a singly linked list.

Each node contains:

int value;
Node next;

The list keeps two references:

head
tail

The `head` reference points to the first node.

The `tail` reference points to the last node.

Using `tail` makes the normal `add(value)` operation constant time because the program does not need to traverse the whole list before adding a new node.

The following operations were implemented:

- add(int value)
- add(int index, int value)
- remove(int index)
- get(int index)
- contains(int value)

For the linked list, one step represents moving from one node to the next node.

A move represents changing a pointer, for example:

current.next = newNode

or:

head = newNode

A comparison represents comparing a stored value with another value.

Unlike DynamicArray, MyLinkedList cannot directly access an element by its index.

To reach an element, the list starts at `head` and follows the `next` references.

---

## 2.3 MinHeap

MinHeap is implemented using an internal primitive array:

int[]

It is an array-based binary min-heap.

The main heap property is:

parent <= child

This means that the value of every parent must be smaller than or equal to the values of its children.

The following operations were implemented:

- insert(int value)
- peekMin()
- extractMin()

The `insert()` operation uses bubble-up.

When a new element is inserted, it is first placed at the end of the heap.

If the new value is smaller than its parent, the two values are swapped.

This process continues until the heap property is restored.

The `extractMin()` operation uses bubble-down.

The minimum value is removed from index 0.

Then the last value in the heap is moved to the root.

The new root is compared with its children and swapped with the smaller child until the heap property is restored.

The `peekMin()` operation directly returns the element at index 0.

---

# 3. Complexity Analysis

## 3.1 DynamicArray

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space | Explanation |
|---|---|---|---|---|---|
| add(value) | Θ(1) | Θ(1) amortized | Θ(n) | O(n) during resize | Normally the value is added at the end. When the array is full, all elements must be copied into a new array. |
| add(index, value) | Θ(1) | Θ(n) | Θ(n) | O(n) during resize | Insertion near the end requires little shifting. Insertion near the beginning can shift almost every element. |
| remove(index) | Θ(1) | Θ(n) | Θ(n) | Θ(1) | Removing the last element requires no shifts, while removing near the beginning requires many shifts. |
| get(index) | Θ(1) | Θ(1) | Θ(1) | Θ(1) | Arrays support direct access using an index. |
| contains(value) | Ω(1) | Θ(n) | Θ(n) | Θ(1) | The value may be found immediately or the whole array may need to be scanned. |

### DynamicArray explanation

DynamicArray is especially efficient for random access.

The program can directly calculate the location of an element using its index.

Therefore, `get(index)` does not depend on the number of stored elements.

However, inserting or removing elements near the beginning of the array can be expensive because many elements must be shifted.

---

## 3.2 MyLinkedList

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space | Explanation |
|---|---|---|---|---|---|
| add(value) | Θ(1) | Θ(1) | Θ(1) | Θ(1) | The tail reference gives direct access to the last node. |
| add(index, value) | Θ(1) | Θ(n) | Θ(n) | Θ(1) | Adding at index 0 is constant time, but other positions may require traversal. |
| remove(index) | Θ(1) | Θ(n) | Θ(n) | Θ(1) | Removing the head is constant time, but removing later nodes requires traversal. |
| get(index) | Θ(1) | Θ(n) | Θ(n) | Θ(1) | The list follows nodes from the head until it reaches the requested index. |
| contains(value) | Ω(1) | Θ(n) | Θ(n) | Θ(1) | The value may be stored in the first node or the whole list may need to be checked. |

### MyLinkedList explanation

The linked list is useful when many operations happen at the beginning of the structure.

For example, inserting or removing at index 0 only requires updating a small number of references.

No array elements need to be shifted.

However, accessing an element in the middle or near the end is slower because the list must follow the nodes one by one.

---

## 3.3 MinHeap

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space | Explanation |
|---|---|---|---|---|---|
| insert(value) | Ω(1) | O(log n) | Θ(log n) | O(n) during resize | The inserted value may already satisfy the heap property, or it may need to bubble up through several levels. |
| peekMin() | Θ(1) | Θ(1) | Θ(1) | Θ(1) | The minimum value is always stored at index 0. |
| extractMin() | Ω(1) | O(log n) | Θ(log n) | Θ(1) | The replacement root may need to move down through the height of the heap. |

### MinHeap explanation

MinHeap is designed for priority-based processing.

The smallest value is always available at the root.

This makes `peekMin()` constant time.

Insertion and extraction may move through the height of the binary heap.

Since the height of a binary heap is logarithmic, these operations have logarithmic worst-case complexity.

---

# 4. Loop Invariant Proofs

## 4.1 Loop Invariant 1 - DynamicArray contains()

The `contains()` method searches through the array from left to right.

The important part of the method is:

public boolean contains(int value) {
for (int i = 0; i < size; i++) {
counter.addStep();
counter.addComparison();

        if (data[i] == value) {
            return true;
        }
    }

    return false;
}

### Invariant

Before every iteration with index `i`, none of the elements at indexes from `0` to `i - 1` is equal to the searched value.

### Initialization

Before the first iteration:

i = 0

No elements have been checked yet.

Therefore, there are no previously checked elements that could contain the searched value.

The invariant is true before the first iteration.

### Maintenance

Assume that the invariant is true before an iteration.

The algorithm compares:

data[i]

with the searched value.

If the values are equal, the method immediately returns `true`.

If they are not equal, then after this iteration all elements from index `0` through index `i` are known not to contain the searched value.

Therefore, before the next iteration the invariant is still true.

### Termination

The loop terminates when:

i == size

At this moment, every valid element in the DynamicArray has been checked.

If the program has not returned `true`, no stored element is equal to the searched value.

Therefore, returning `false` is correct.

### Conclusion

The loop invariant proves that the method checks every necessary element in order.

If the value exists, the method returns `true`.

If the loop finishes without finding the value, returning `false` is correct.

---

# 4.2 Loop Invariant 2 - MinHeap Bubble-Down

Bubble-down is used inside the `extractMin()` operation.

After removing the root, the final element of the heap is moved to index 0.

This new value may violate the min-heap property.

The algorithm repeatedly compares the current value with its children.

If one of the children is smaller, the program swaps the current value with the smaller child.

### Invariant

Before every iteration of the bubble-down loop, all nodes outside the subtree rooted at the current index satisfy the min-heap property.

The only possible heap-property violation is between the current node and one of its children.

### Initialization

Before the first iteration, the last element of the heap has been moved to the root.

Before removing the minimum value, the original heap satisfied the min-heap property.

Therefore, all unchanged subtrees still satisfy the heap property.

The only possible problem is at the root.

The invariant is true before the first iteration.

### Maintenance

Assume that the invariant is true before an iteration.

The algorithm checks the current node and its children.

It selects the smallest value.

If the current node is already the smallest, the heap property is satisfied and the loop terminates.

Otherwise, the current node is swapped with its smaller child.

After this swap, the previous parent position satisfies the heap property.

The only possible remaining violation is now lower in the heap, at the new current position.

Therefore, the invariant remains true before the next iteration.

### Termination

The loop stops when the current node is smaller than or equal to both of its children, or when the node has no children.

At this point, no violation remains at the current position.

According to the invariant, all other parts of the heap already satisfy the min-heap property.

Therefore, the entire heap is valid.

### Conclusion

The loop invariant proves that every bubble-down iteration fixes the heap property at one level.

Any possible violation moves downward.

When the loop terminates, the heap property is restored for the whole MinHeap.

---

# 5. Benchmark Methodology

The benchmark uses the following input sizes:

- n = 100
- n = 1,000
- n = 10,000
- n = 100,000

All structures receive data produced using:

new Random(42)

This ensures that the same data is used between benchmark runs.

Every benchmark case includes a warm-up run.

After the warm-up, the benchmark is executed five times.

The median execution time is saved.

Using the median reduces the influence of unusual execution-time spikes.

The final CSV file uses the following columns:

workload,variant,structure,n,time_ms,steps,moves,comparisons

The metrics are counted inside the data-structure operations rather than estimated after the benchmark.

---

# 6. W1 - Random Access

In W1, DynamicArray and MyLinkedList are filled with `n` values.

After that, 10,000 calls to:

get(index)

are performed using random indexes.

## Expected behavior

DynamicArray should perform significantly better because array indexing provides direct access.

MyLinkedList must start at the head and follow `next` references until the requested node is reached.

## W1 Time

![W1 Time](results/plots/w1_time.png)

## W1 Operations Overview

![W1 Operations](results/plots/w1_operations_overview.png)

## W1 Detailed Steps

![W1 Steps](results/plots/w1_steps.png)

## W1 Detailed Moves

![W1 Moves](results/plots/w1_moves.png)

## W1 Detailed Comparisons

![W1 Comparisons](results/plots/w1_comparisons.png)

The main difference in this workload should appear in the number of steps.

DynamicArray uses approximately one array access for each `get()` call.

MyLinkedList may perform many node transitions for each random index.

---

# 7. W2 - Search

In W2, 1,000 `contains(value)` queries are performed.

Half of the searched values are present in the structure.

The other half are absent.

The workload is tested on:

- DynamicArray
- MyLinkedList

Both structures use linear search.

## W2 Time

![W2 Time](results/plots/w2_time.png)

## W2 Operations Overview

![W2 Operations](results/plots/w2_operations_overview.png)

## W2 Detailed Steps

![W2 Steps](results/plots/w2_steps.png)

## W2 Detailed Moves

![W2 Moves](results/plots/w2_moves.png)

## W2 Detailed Comparisons

![W2 Comparisons](results/plots/w2_comparisons.png)

The number of comparisons for both structures can be similar because both perform a sequential search.

However, their real running time may still be different because arrays and linked lists use memory differently.

---

# 8. W3 - Insert and Remove

W3 tests frequent insertion and removal.

Two different variants are tested:

- head
- middle

Each variant performs:

- 1,000 insertions
- 1,000 removals

---

## 8.1 W3 Head

For the head variant, insertions and removals are performed at:

index 0

### W3 Head Time

![W3 Head Time](results/plots/w3_head_time.png)

### W3 Head Operations Overview

![W3 Head Operations](results/plots/w3_head_operations_overview.png)

### W3 Head Steps

![W3 Head Steps](results/plots/w3_head_steps.png)

### W3 Head Moves

![W3 Head Moves](results/plots/w3_head_moves.png)

### W3 Head Comparisons

![W3 Head Comparisons](results/plots/w3_head_comparisons.png)

For head operations, MyLinkedList has an important advantage.

Adding at the head only requires updating pointers.

Removing the first element also requires only a small number of pointer updates.

DynamicArray must shift many elements after an insertion or removal at index 0.

As `n` becomes larger, the cost of these shifts becomes more visible.

---

## 8.2 W3 Middle

For the middle variant, insertions and removals are performed near:

n / 2

### W3 Middle Time

![W3 Middle Time](results/plots/w3_middle_time.png)

### W3 Middle Operations Overview

![W3 Middle Operations](results/plots/w3_middle_operations_overview.png)

### W3 Middle Steps

![W3 Middle Steps](results/plots/w3_middle_steps.png)

### W3 Middle Moves

![W3 Middle Moves](results/plots/w3_middle_moves.png)

### W3 Middle Comparisons

![W3 Middle Comparisons](results/plots/w3_middle_comparisons.png)

For DynamicArray, middle insertion or removal requires shifting a large part of the array.

For MyLinkedList, no array values are shifted.

However, the linked list must traverse from the head toward the middle of the structure before changing the links.

Therefore, both structures have important costs in this workload, but those costs come from different operations.

---

# 9. W4 - Priority Processing

W4 tests MinHeap.

First, `n` values are inserted into the heap.

After that, `extractMin()` is called until the heap becomes empty.

The benchmark verifies that extracted values are returned in non-decreasing order.

If the current extracted value is smaller than the previous extracted value, the benchmark reports an error.

## W4 Time

![W4 Time](results/plots/w4_time.png)

## W4 Operations Overview

![W4 Operations](results/plots/w4_operations_overview.png)

## W4 Detailed Steps

![W4 Steps](results/plots/w4_steps.png)

## W4 Detailed Moves

![W4 Moves](results/plots/w4_moves.png)

## W4 Detailed Comparisons

![W4 Comparisons](results/plots/w4_comparisons.png)

The results show the cost of maintaining the heap property during insertion and extraction.

The minimum value remains available at the root.

Each extraction restores the heap using bubble-down.

---

# 10. Performance Discussion

The benchmark demonstrates that Big-O complexity is not the only factor that affects real program performance.

DynamicArray performs very well for random access because its elements are stored next to each other in memory.

This contiguous memory layout provides good spatial locality.

When the CPU reads one array element, nearby elements may also be loaded into the same cache line.

As a result, sequential array operations can make effective use of the CPU cache.

MyLinkedList stores values inside separate node objects.

These nodes may be located in different areas of memory.

Moving from one node to another using the `next` reference is known as pointer chasing.

Pointer chasing may cause additional CPU cache misses because the next node may not already be available in the cache.

This means that a linked list may have worse real execution time even when its theoretical Big-O complexity looks similar to another structure.

Linked-list nodes also require additional memory for object headers and references.

Creating many separate node objects can also increase the work of the Java garbage collector.

DynamicArray is a strong choice when the program frequently uses random access or sequential iteration.

MyLinkedList is more useful when frequent insertion and removal occur at the beginning of the structure.

MinHeap is useful when the application repeatedly needs to process the smallest-priority element.

Its `peekMin()` operation gives constant-time access to the minimum value.

Insertion and extraction are efficient because the height of a binary heap grows logarithmically.

Therefore, the correct choice of data structure depends on the workload and not only on one theoretical complexity value.

---

# 11. Testing

JUnit 5 was used to test all three custom data structures.

The tests cover normal operations and edge cases.

## DynamicArray tests

The DynamicArray tests check:

- adding values
- getting values
- insertion by index
- removing values
- duplicate values
- contains()
- first index
- last index
- empty structure
- invalid index
- automatic resizing
- random data

The random-data test compares the custom DynamicArray with an expected result stored using a Java standard collection.

---

## MyLinkedList tests

The MyLinkedList tests check:

- adding values
- getting values
- insertion by index
- removal
- removing the first element
- removing the last element
- duplicate values
- contains()
- one element
- empty structure
- invalid indexes
- random data

The tests also compare MyLinkedList with an expected standard Java list when checking random data.

---

## MinHeap tests

The MinHeap tests check:

- insert()
- peekMin()
- extractMin()
- one element
- duplicate values
- empty heap
- invalid empty-heap operations
- heap property after insertion
- heap property after extraction
- random values
- sorted extraction output

After inserts and extractions, the heap property is checked.

The required condition is:

parent <= child

The extraction tests also confirm that repeated `extractMin()` calls produce values in non-decreasing order.

Java standard collections are only used in the test code for comparison and expected results.

The custom data structures themselves do not use ArrayList, LinkedList, or PriorityQueue internally.

---

# 12. Metrics

The program counts three physical-operation metrics:

- steps
- moves
- comparisons

## Steps

For DynamicArray and MinHeap, a step represents reading an array cell.

For MyLinkedList, a step represents moving to the next node.

Example:

current = current.next;

## Moves

For arrays, a move represents shifting or copying an element.

Example:

data[i] = data[i - 1];

For the linked list, a move represents changing a reference.

Example:

current.next = newNode;

## Comparisons

A comparison represents comparing two stored values.

Example:

data[i] == value

or:

heap[left] < heap[smallest]

The counters are updated directly inside the data-structure methods.

This means that the values stored in `results.csv` represent operations performed by the implementation itself instead of estimates calculated after execution.

---

# 13. Benchmark Output

The benchmark exports its results to:

results/results.csv

The CSV columns are:

workload,variant,structure,n,time_ms,steps,moves,comparisons

For W3, the variant is either:

head

or:

middle

For other workloads, the variant is:

-

The results can be reproduced by running the benchmark again because the random seed remains fixed at 42.

---

# 14. Plots

Benchmark plots are stored in:

results/plots/

For every workload, detailed plots were generated for:

- execution time
- operation overview
- steps
- moves
- comparisons

Each plot contains:

- a descriptive title
- input size on the x-axis
- measurement units on the y-axis
- a legend
- grid lines
- separate series for the relevant data structures

These plots make it easier to compare the theoretical analysis with the measured behavior of the structures.

---

# 15. Conclusion

In this assignment, three fundamental data structures were implemented from scratch:

- DynamicArray
- MyLinkedList
- MinHeap

DynamicArray provides very efficient indexed access and benefits from good cache locality.

Its main disadvantage is the cost of shifting elements during insertion or removal near the beginning of the array.

MyLinkedList avoids array shifting and supports efficient changes at the head.

However, indexed access requires traversal through nodes.

Its separated node objects also make it more affected by pointer chasing and cache misses.

MinHeap is appropriate for priority-based processing.

It keeps the minimum value at the root and provides constant-time `peekMin()`.

Insertion and extraction preserve the heap property using bubble-up and bubble-down.

The benchmark demonstrates that theoretical complexity and real execution performance are related but not identical.

Memory layout, CPU cache behavior, pointer traversal, element shifting, comparisons, and implementation details can all affect actual running time.

Therefore, choosing a data structure should always depend on the workload that the program needs to support.