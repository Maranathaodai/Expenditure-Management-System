# Data Structures Justification

## Executive Summary

This document provides a comprehensive analysis of the data structure choices implemented in the Nkwa Real Estate Expenditure Management System. Each data structure was carefully selected based on the specific operational requirements, performance considerations, and the nature of financial data management.

## Data Structure Analysis

### 1. HashMap<String, Expenditure> - Expenditure Storage

**Implementation Location**: `ExpenditureRecords.java`
```java
static HashMap<String, Expenditure> expenditures = new HashMap<>();
```

**Justification**:
- **Primary Use**: Storing and retrieving expenditure records by unique codes
- **Why HashMap**: Direct O(1) average-case lookup by expenditure code is critical for financial applications where quick record retrieval is essential
- **Business Logic**: Each expenditure has a unique code that serves as a natural key, making HashMap the optimal choice
- **Alternative Considered**: ArrayList would require O(n) search time, unacceptable for large datasets

**Key Benefits**:
- Fast insertion of new expenditure records
- Instant retrieval for audit trails and reporting
- Efficient memory usage with dynamic resizing
- Built-in collision handling

### 2. HashMap<String, BankAccount> - Bank Account Management

**Implementation Location**: `ExpenditureRecords.java`
```java
static HashMap<String, BankAccount> bankAccounts = new HashMap<>();
```

**Justification**:
- **Primary Use**: Managing multiple bank accounts with unique identifiers
- **Why HashMap**: Account lookup by ID must be immediate for transaction processing
- **Business Logic**: Financial institutions require rapid account verification and balance updates
- **Performance Requirement**: O(1) access time ensures scalability as the number of accounts grows

**Key Benefits**:
- Instant account validation
- Efficient balance updates
- Support for multiple banking relationships
- Easy integration with expenditure tracking

### 3. HashSet<String> - Category Management

**Implementation Location**: `CategoryManager.java`
```java
static Set<String> categories = new HashSet<>();
```

**Justification**:
- **Primary Use**: Ensuring category uniqueness and fast category validation
- **Why HashSet**: Prevents duplicate categories and provides O(1) membership testing
- **Business Logic**: Financial categorization requires consistent, unique category names
- **Data Integrity**: Automatic duplicate prevention maintains data quality

**Key Benefits**:
- Guaranteed uniqueness of categories
- Fast category existence checks
- Memory-efficient storage
- Simple iteration for display purposes

### 4. Queue<Receipt> (LinkedList Implementation) - Receipt Processing

**Implementation Location**: `ExpenditureRecords.java`
```java
static Queue<Receipt> receiptQueue = new LinkedList<>();
```

**Justification**:
- **Primary Use**: Managing receipt review workflow in FIFO order
- **Why Queue**: Simulates real-world accountant workflow where receipts are processed in order received
- **Business Logic**: Auditing requirements mandate chronological receipt processing
- **Workflow Management**: FIFO ensures fair and systematic receipt review

**Key Benefits**:
- Maintains processing order integrity
- Supports batch processing workflows
- Easy addition of new receipts
- Natural workflow simulation

### 5. PriorityQueue<BankAccount> - Low Balance Monitoring

**Implementation Location**: `ExpenditureRecords.java`
```java
static PriorityQueue<BankAccount> bankMinHeap = new PriorityQueue<>(Comparator.comparingDouble(a -> a.balance));
```

**Justification**:
- **Primary Use**: Identifying accounts with lowest balances for cash flow management
- **Why MinHeap**: Provides O(1) access to the account with minimum balance
- **Business Logic**: Cash flow management requires immediate identification of low-balance accounts
- **Risk Management**: Prevents overdrafts and ensures adequate liquidity

**Key Benefits**:
- Instant identification of at-risk accounts
- Automatic reordering when balances change
- Efficient memory usage
- Supports proactive financial management

### 6. ArrayList<String> - Expenditure Code Lists

**Implementation Location**: `BankAccount.java`
```java
public List<String> expenditureCodes = new ArrayList<>();
```

**Justification**:
- **Primary Use**: Linking bank accounts to their associated expenditures
- **Why ArrayList**: Provides ordered collection with dynamic resizing and efficient iteration
- **Business Logic**: Maintains audit trail between accounts and expenditures
- **Flexibility**: Supports variable number of expenditures per account

**Key Benefits**:
- Dynamic size adjustment
- Maintains insertion order
- Efficient iteration for reporting
- Simple add/remove operations

### 7. Custom Data Structures (Future Implementation)

#### Binary Search Tree (BST)
**Planned Use**: Sorted storage and range queries for expenditures
**Justification**: O(log n) search, insert, and delete operations for sorted data access

#### Graph Structure
**Planned Use**: Modeling relationships between accounts, categories, and expenditures
**Justification**: Complex relationship analysis and pathfinding between financial entities

#### Custom HashMap
**Planned Use**: Optimized hash table with open addressing
**Justification**: Better cache performance and reduced memory fragmentation

## Data Structure Selection Matrix

| Data Structure | Primary Use Case | Time Complexity | Space Complexity | Justification |
|----------------|------------------|-----------------|------------------|---------------|
| HashMap | Record lookup | O(1) avg | O(n) | Fast access by key |
| HashSet | Uniqueness | O(1) avg | O(n) | Duplicate prevention |
| Queue | Order processing | O(1) | O(n) | FIFO workflow |
| PriorityQueue | Priority access | O(log n) | O(n) | Min/max retrieval |
| ArrayList | Dynamic arrays | O(1) amortized | O(n) | Ordered collections |

## Design Principles Applied

1. **Performance First**: Critical operations (lookup, insertion) use O(1) structures
2. **Business Logic Alignment**: Data structures match real-world financial workflows
3. **Data Integrity**: Built-in constraints prevent invalid states
4. **Scalability**: Structures handle growth efficiently
5. **Memory Efficiency**: Optimal space usage for expected data volumes

## Conclusion

The selected data structures create a robust foundation for financial data management, balancing performance requirements with business logic constraints. Each choice directly supports the system's core objectives of efficient expenditure tracking, category management, and financial analysis.
