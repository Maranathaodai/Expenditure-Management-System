# Complexity Analysis

## Executive Summary

This document provides a comprehensive complexity analysis of the Nkwa Real Estate Expenditure Management System using Big-O and Omega (Ω) notations. The analysis covers time and space complexities for all major operations, data structures, and algorithms implemented in the system.

## Big-O and Omega Notation Overview

- **Big-O (O)**: Represents the worst-case upper bound of an algorithm's complexity
- **Omega (Ω)**: Represents the best-case lower bound of an algorithm's complexity
- **Theta (Θ)**: Represents tight bounds when O and Ω are the same

## Data Structure Complexity Analysis

### 1. HashMap Operations

**Implementation**: `HashMap<String, Expenditure> expenditures`

| Operation | Best Case (Ω) | Average Case | Worst Case (O) | Space (O) |
|-----------|---------------|--------------|----------------|-----------|
| Insert | Ω(1) | O(1) | O(n) | O(n) |
| Search | Ω(1) | O(1) | O(n) | O(1) |
| Delete | Ω(1) | O(1) | O(n) | O(1) |
| Iteration | Ω(n) | O(n) | O(n) | O(1) |

**Analysis Details**:
- **Best Case (Ω(1))**: No hash collisions, direct access to bucket
- **Average Case (O(1))**: Well-distributed hash function, minimal collisions
- **Worst Case (O(n))**: All keys hash to same bucket, degenerates to linear search
- **Load Factor Impact**: Performance degrades as load factor approaches 1.0

**Real-world Performance**:
```java
// Example: Expenditure lookup by code
Expenditure exp = expenditures.get(code); // O(1) average case
```

### 2. HashSet Operations

**Implementation**: `Set<String> categories`

| Operation | Best Case (Ω) | Average Case | Worst Case (O) | Space (O) |
|-----------|---------------|--------------|----------------|-----------|
| Add | Ω(1) | O(1) | O(n) | O(n) |
| Contains | Ω(1) | O(1) | O(n) | O(1) |
| Remove | Ω(1) | O(1) | O(n) | O(1) |
| Size | Ω(1) | O(1) | O(1) | O(1) |

**Analysis Details**:
- **Uniqueness Guarantee**: Automatic duplicate prevention in O(1) average time
- **Memory Efficiency**: No value storage, only keys maintained

### 3. ArrayList Operations

**Implementation**: `List<String> expenditureCodes` in BankAccount

| Operation | Best Case (Ω) | Average Case | Worst Case (O) | Space (O) |
|-----------|---------------|--------------|----------------|-----------|
| Add (end) | Ω(1) | O(1) | O(n) | O(n) |
| Add (index) | Ω(1) | O(n) | O(n) | O(n) |
| Get | Ω(1) | O(1) | O(1) | O(1) |
| Search | Ω(1) | O(n) | O(n) | O(1) |
| Remove | Ω(1) | O(n) | O(n) | O(n) |

**Analysis Details**:
- **Best Case Add (Ω(1))**: Sufficient capacity, no array resizing needed
- **Worst Case Add (O(n))**: Array resizing required, all elements must be copied
- **Amortized Analysis**: Add operation is O(1) amortized due to doubling strategy

### 4. Queue Operations (LinkedList Implementation)

**Implementation**: `Queue<Receipt> receiptQueue`

| Operation | Best Case (Ω) | Average Case | Worst Case (O) | Space (O) |
|-----------|---------------|--------------|----------------|-----------|
| Enqueue | Ω(1) | O(1) | O(1) | O(n) |
| Dequeue | Ω(1) | O(1) | O(1) | O(1) |
| Peek | Ω(1) | O(1) | O(1) | O(1) |
| Size | Ω(1) | O(1) | O(1) | O(1) |

**Analysis Details**:
- **Consistent Performance**: LinkedList implementation provides true O(1) queue operations
- **Memory Overhead**: Additional pointer storage per node

### 5. PriorityQueue Operations (MinHeap)

**Implementation**: `PriorityQueue<BankAccount> bankMinHeap`

| Operation | Best Case (Ω) | Average Case | Worst Case (O) | Space (O) |
|-----------|---------------|--------------|----------------|-----------|
| Insert | Ω(1) | O(log n) | O(log n) | O(n) |
| Extract Min | Ω(log n) | O(log n) | O(log n) | O(1) |
| Peek Min | Ω(1) | O(1) | O(1) | O(1) |
| Heapify | Ω(n) | O(n) | O(n) | O(1) |

**Analysis Details**:
- **Best Case Insert (Ω(1))**: Element inserted at leaf level, no bubble-up needed
- **Heap Property**: Maintains min-heap invariant through bubble-up and bubble-down operations
- **Binary Tree Structure**: Complete binary tree ensures O(log n) height

## Algorithm Complexity Analysis

### 1. Sorting Algorithms

#### TimSort (Java Collections.sort())

**Implementation**: Category and date sorting in ExpenditureRecords

| Metric | Best Case (Ω) | Average Case | Worst Case (O) | Space (O) |
|--------|---------------|--------------|----------------|-----------|
| Time | Ω(n) | O(n log n) | O(n log n) | O(n) |

**Analysis Details**:
- **Best Case (Ω(n))**: Already sorted data, minimal merge operations
- **Adaptive Algorithm**: Performance improves with partial ordering
- **Stable Sort**: Maintains relative order of equal elements
- **Space Complexity**: Requires O(n) auxiliary space for merge operations

```java
// Example: Sorting expenditures by category
List<Expenditure> list = new ArrayList<>(expenditures.values()); // O(n)
list.sort(Comparator.comparing(e -> e.category.toLowerCase())); // O(n log n)
```

#### Heap Sort (PriorityQueue internal)

**Implementation**: Bank account balance ordering

| Metric | Best Case (Ω) | Average Case | Worst Case (O) | Space (O) |
|--------|---------------|--------------|----------------|-----------|
| Time | Ω(n log n) | O(n log n) | O(n log n) | O(1) |

**Analysis Details**:
- **Consistent Performance**: Guaranteed O(n log n) regardless of input distribution
- **In-place Sorting**: O(1) additional space requirement
- **Not Stable**: May change relative order of equal elements

### 2. Search Algorithms

#### Hash-based Search

**Implementation**: Direct lookup operations

| Metric | Best Case (Ω) | Average Case | Worst Case (O) | Space (O) |
|--------|---------------|--------------|----------------|-----------|
| Time | Ω(1) | O(1) | O(n) | O(1) |

**Analysis Details**:
- **Hash Function Quality**: Performance depends on uniform distribution
- **Collision Resolution**: Linear probing or chaining affects worst-case behavior

#### Linear Search with Filtering

**Implementation**: Range queries and filtered searches

| Metric | Best Case (Ω) | Average Case | Worst Case (O) | Space (O) |
|--------|---------------|--------------|----------------|-----------|
| Time | Ω(1) | O(n) | O(n) | O(1) |

**Analysis Details**:
- **Best Case (Ω(1))**: Target found at first position
- **Early Termination**: Search can stop when first match is found
- **Memory Efficient**: No additional space required for search operation

```java
// Example: Date range search
for (Expenditure e : expenditures.values()) { // O(n)
    if (e.date.compareTo(start) >= 0 && e.date.compareTo(end) <= 0) { // O(1)
        System.out.println(e);
    }
}
```

## Aggregate Operation Complexity

### 1. Complete Expenditure Workflow

```java
// Add expenditure: O(1)
expenditures.put(code, exp);

// Update bank account: O(log n) for heap update
bankMinHeap.remove(account); // O(n)
account.balance -= amount;
bankMinHeap.add(account); // O(log n)

// Add receipt to queue: O(1)
receiptQueue.add(receipt);
```

**Total Time Complexity**: O(n) due to heap remove operation
**Optimization Opportunity**: Use decrease-key operation if supported

### 2. Report Generation Workflow

```java
// Copy to list: O(n)
List<Expenditure> list = new ArrayList<>(expenditures.values());

// Sort by category: O(n log n)
list.sort(Comparator.comparing(e -> e.category));

// Filter by date range: O(n)
list.stream().filter(e -> dateInRange(e.date)).collect(toList());
```

**Total Time Complexity**: O(n log n)
**Space Complexity**: O(n) for temporary collections

## Space Complexity Analysis

### 1. Memory Usage Breakdown

| Component | Space Complexity | Description |
|-----------|------------------|-------------|
| HashMap Storage | O(n) | n expenditure records |
| HashSet Categories | O(m) | m unique categories |
| Queue Receipts | O(r) | r pending receipts |
| ArrayList Links | O(k) | k expenditure codes per account |
| Heap Structure | O(a) | a bank accounts |

**Total Space**: O(n + m + r + k + a)

### 2. Memory Growth Patterns

- **Linear Growth**: Space requirements grow linearly with data volume
- **Load Factor Impact**: HashMap efficiency decreases as load factor increases
- **Garbage Collection**: Temporary collections during sorting may trigger GC

## Performance Optimization Recommendations

### 1. Immediate Optimizations

1. **HashMap Load Factor**: Maintain load factor below 0.75 for optimal performance
2. **Initial Capacity**: Set appropriate initial capacity to avoid rehashing
3. **String Interning**: Use string interning for frequently repeated category names

### 2. Algorithmic Improvements

1. **Indexed Search**: Implement secondary indexes for frequent range queries
2. **Caching**: Cache sorted results for repeated access patterns
3. **Batch Operations**: Group multiple operations to amortize overhead

### 3. Data Structure Enhancements

```java
// Example: TreeMap for range queries
TreeMap<String, Expenditure> dateIndexedExpenditures; // O(log n) range queries

// Example: Multi-level indexing
Map<String, TreeSet<Expenditure>> categoryDateIndex; // O(log n) within category
```

## Scalability Analysis

### 1. Small Dataset (< 1,000 records)
- **Current Implementation**: Excellent performance across all operations
- **Bottlenecks**: None identified
- **Memory Usage**: Minimal impact

### 2. Medium Dataset (1,000 - 10,000 records)
- **Hash Operations**: Remain O(1) with proper tuning
- **Linear Searches**: May show noticeable delays
- **Sorting**: Acceptable performance for reporting

### 3. Large Dataset (> 10,000 records)
- **Recommendations**: Implement indexed searches and database storage
- **Critical Operations**: Prioritize hash-based access patterns
- **Memory Management**: Consider lazy loading and pagination

## Conclusion

The current implementation provides excellent performance characteristics for small to medium-scale financial data management. The predominant use of hash-based data structures ensures O(1) average-case performance for critical operations, while the hybrid approach of linear searches for complex queries maintains system simplicity. As data volumes grow, the modular design allows for targeted optimizations without major architectural changes.

## Future Complexity Improvements

1. **B-Tree Implementation**: O(log n) sorted access with better cache performance
2. **Bloom Filters**: O(1) negative lookup optimization
3. **Parallel Algorithms**: O(n/p) where p is the number of processors
4. **External Sorting**: Handle datasets larger than available memory
