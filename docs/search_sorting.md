# Search and Sorting Techniques

## Executive Summary

This document details the search and sorting algorithms implemented in the Nkwa Real Estate Expenditure Management System. The techniques are optimized for financial data operations, ensuring efficient data retrieval and organization for reporting and analysis purposes.

## Sorting Algorithms

### 1. Built-in TimSort (Java Collections.sort())

**Implementation Location**: `ExpenditureRecords.java`

#### Category-based Sorting
```java
static void sortByCategory() {
    List<Expenditure> list = new ArrayList<>(expenditures.values());
    list.sort(Comparator.comparing(e -> e.category.toLowerCase()));
    System.out.println("--- Expenditures Sorted by Category ---");
    for (Expenditure e : list) System.out.println(e);
}
```

#### Date-based Sorting
```java
static void sortByDate() {
    List<Expenditure> list = new ArrayList<>(expenditures.values());
    list.sort(Comparator.comparing(e -> e.date));
    System.out.println("--- Expenditures Sorted by Date ---");
    for (Expenditure e : list) System.out.println(e);
}
```

**Algorithm Choice Justification**:
- **Why TimSort**: Java's built-in sorting algorithm is a hybrid stable sorting algorithm derived from merge sort and insertion sort
- **Performance**: O(n log n) worst-case, O(n) best-case for nearly sorted data
- **Stability**: Maintains relative order of equal elements, crucial for financial records
- **Adaptive**: Performs well on partially sorted data, common in chronological financial data

**Key Benefits**:
- Optimized for real-world data patterns
- Stable sort maintains data integrity
- Built-in optimization for partially sorted sequences
- Memory efficient with O(n) space complexity

### 2. Priority Queue Sorting (MinHeap)

**Implementation Location**: `ExpenditureRecords.java`
```java
static PriorityQueue<BankAccount> bankMinHeap = new PriorityQueue<>(Comparator.comparingDouble(a -> a.balance));
```

**Algorithm Details**:
- **Type**: Min-heap implementation using binary heap
- **Purpose**: Automatically maintains bank accounts sorted by balance
- **Insertion**: O(log n) time complexity
- **Minimum Retrieval**: O(1) time complexity

**Use Cases**:
- Identifying accounts with lowest balances
- Cash flow risk assessment
- Automated alerts for low-balance accounts

## Search Algorithms

### 1. Hash-based Direct Lookup

#### Expenditure Search by Code
```java
static void viewExpenditure() {
    System.out.print("Enter Expenditure Code: ");
    String code = scanner.nextLine();
    if (expenditures.containsKey(code)) {
        System.out.println(expenditures.get(code));
    } else {
        System.out.println("Expenditure not found.");
    }
}
```

**Algorithm Characteristics**:
- **Time Complexity**: O(1) average case, O(n) worst case
- **Space Complexity**: O(1)
- **Method**: Direct hash table lookup
- **Use Case**: Primary key-based record retrieval

### 2. Linear Search with Filtering

#### Date Range Search
```java
static void searchByDateRange(String start, String end) {
    System.out.println("--- Expenditures in Date Range ---");
    for (Expenditure e : expenditures.values()) {
        if (e.date.compareTo(start) >= 0 && e.date.compareTo(end) <= 0) {
            System.out.println(e);
        }
    }
}
```

#### Category Search
```java
static void searchByCategory(String category) {
    System.out.println("--- Expenditures in Category: " + category + " ---");
    for (Expenditure e : expenditures.values()) {
        if (e.category.equalsIgnoreCase(category)) {
            System.out.println(e);
        }
    }
}
```

#### Amount Range Search
```java
static void searchByCostRange(double min, double max) {
    System.out.println("--- Expenditures in Cost Range ---");
    for (Expenditure e : expenditures.values()) {
        if (e.amount >= min && e.amount <= max) {
            System.out.println(e);
        }
    }
}
```

#### Account-based Search
```java
static void searchByAccount(String account) {
    System.out.println("--- Expenditures for Account: " + account + " ---");
    for (Expenditure e : expenditures.values()) {
        if (e.accountUsed.equalsIgnoreCase(account)) {
            System.out.println(e);
        }
    }
}
```

**Algorithm Characteristics**:
- **Time Complexity**: O(n) for all filtering operations
- **Space Complexity**: O(1) additional space
- **Method**: Sequential scan with condition matching
- **Justification**: For secondary attributes without indexed access, linear search is appropriate for small to medium datasets

### 3. Set-based Membership Testing

#### Category Existence Check
```java
static void searchCategory() {
    System.out.print("Enter Category to Search: ");
    String category = scanner.nextLine();
    if (categories.contains(category)) {
        System.out.println("Category found.");
    } else {
        System.out.println("Category not found.");
    }
}
```

**Algorithm Characteristics**:
- **Time Complexity**: O(1) average case
- **Space Complexity**: O(1)
- **Method**: Hash set membership testing
- **Use Case**: Validation and existence verification

## Advanced Search Techniques (Planned Implementation)

### 1. Binary Search Tree (BST) Range Queries
**Purpose**: Efficient range searches on sorted expenditure data
**Time Complexity**: O(log n + k) where k is the number of results
**Implementation**: Custom BST for date and amount range queries

### 2. Graph-based Relationship Search
**Purpose**: Finding connections between accounts, categories, and expenditures
**Algorithm**: Breadth-First Search (BFS) and Depth-First Search (DFS)
**Use Cases**: Audit trails, dependency analysis, fraud detection

### 3. Indexed Search with B-Trees
**Purpose**: Multi-attribute indexing for complex queries
**Time Complexity**: O(log n) for individual attribute searches
**Implementation**: Custom B-tree for composite key searches

## Performance Optimization Strategies

### 1. Data Structure Selection
- **Primary Keys**: HashMap for O(1) access
- **Range Queries**: Planned BST implementation
- **Set Operations**: HashSet for uniqueness and membership

### 2. Search Algorithm Selection Matrix

| Search Type | Data Structure | Algorithm | Time Complexity | Use Case |
|-------------|----------------|-----------|-----------------|----------|
| Exact Match | HashMap | Hash Lookup | O(1) avg | Primary key search |
| Range Query | ArrayList | Linear Scan | O(n) | Date/amount ranges |
| Membership | HashSet | Hash Lookup | O(1) avg | Category validation |
| Minimum | PriorityQueue | Heap Extract | O(1) | Lowest balance |
| Sorted Data | ArrayList | Binary Search | O(log n) | Future implementation |

### 3. Memory vs. Speed Tradeoffs
- **Caching**: Store frequently accessed results
- **Indexing**: Trade memory for faster searches
- **Lazy Loading**: Load data on demand for large datasets

## Search and Sort Integration

### 1. Search-then-Sort Pattern
```java
// Example: Search by category, then sort by date
List<Expenditure> categoryResults = searchByCategory(category);
categoryResults.sort(Comparator.comparing(e -> e.date));
```

### 2. Sort-then-Search Pattern
```java
// Example: Sort by amount, then use binary search
List<Expenditure> sortedByAmount = new ArrayList<>(expenditures.values());
sortedByAmount.sort(Comparator.comparingDouble(e -> e.amount));
// Binary search implementation would follow
```

## Error Handling and Edge Cases

### 1. Empty Result Sets
- Graceful handling of no-match scenarios
- Informative user feedback
- Consistent return types

### 2. Invalid Search Parameters
- Input validation before search execution
- Type checking for numeric ranges
- Date format validation

### 3. Large Dataset Considerations
- Pagination for large result sets
- Progress indicators for long-running searches
- Memory management for bulk operations

## Conclusion

The implemented search and sorting techniques provide a solid foundation for financial data management. The combination of hash-based lookups for primary access and linear searches for filtered queries offers optimal performance for the expected data volumes while maintaining simplicity and reliability. Future enhancements will focus on implementing indexed searches and tree-based structures for more complex query requirements.
