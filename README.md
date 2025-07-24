# Nkwa Real Estate Expenditure Management System

This project is a command-line application for tracking expenditures, managing categories, and analyzing financial data for Nkwa Real Estate Ltd. It is built using core Java and fundamental data structures, with all data stored locally in text files.

---

## Project Overview

This section provides a summary of the system's purpose and its offline, data-structure-driven approach.

## Features

- Add, view, search, and sort expenditures
- Manage unique categories
- Track multiple bank accounts and their relationships
- Link expenditures to receipts/invoices
- Simulate accountant workflows with queues, stacks, sets, maps, trees, and graphs
- Generate reports and perform financial analysis

## Directory Structure

- `src/` - Java source code (organized by feature and data structure)
- `data/` - Persistent storage for expenditures, accounts, categories, and receipts
- `docs/` - Documentation (data structures, design, complexity analysis, etc.)
- `reports/` - Generated financial and analytical reports
- `resources/` - Static files or templates (if needed)
- `config/` - Configuration files (future use)

## How to Run

1. Compile the project using your preferred Java IDE or command line.
2. Run `Main.java` to start the menu-driven CLI.
3. Follow on-screen prompts to manage expenditures, categories, accounts, and reports.

## Data Structure Choices (Student Notes)

### Why I Chose Each Data Structure
- **HashMap**: I used HashMaps for storing expenditures and bank accounts because I wanted to be able to look up any record quickly using a unique code or ID. HashMaps make this super fast (like O(1) time most of the time).
- **HashSet**: For categories, I used a HashSet so that no category can be added twice. It also makes checking if a category exists really quick.
- **ArrayList**: I used ArrayLists for things like lists of expenditures or account links because they are easy to grow and let me loop through everything easily.
- **Queue (LinkedList)**: For receipts, I used a queue to simulate the way accountants review receipts in the order they come in (FIFO).
- **PriorityQueue (MinHeap)**: I used a min-heap to always know which bank account has the lowest balance, so I can warn if funds are running low.

### Sorting and Searching Techniques
- **Sorting**: When I need to sort expenditures (like by date or category), I just copy them into an ArrayList and use the built-in sort with a custom comparator. This is usually fast enough for our needs (O(n log n)).
- **Searching**: For direct lookups (like by code or category), I use HashMaps or HashSets for O(1) access. For things like searching by amount range or date range, I just loop through the list (O(n)), which is fine for a small dataset.

### Complexity Analysis (Big O and Omega)
- **HashMap/HashSet**: Insert and search are O(1) on average (and best case, Omega(1)), but can be O(n) in the worst case if there are lots of collisions (rare).
- **ArrayList**: Adding at the end is O(1) most of the time, but searching is O(n) because you might have to look at every item.
- **PriorityQueue (MinHeap)**: Inserting is O(log n), and getting the minimum is O(1).
- **Sorting**: Using Java's sort is O(n log n) on average and best case.

---

## Documentation
1. Data Structures Justification (data_structures.md)
Detailed analysis of HashMap for expenditure storage
HashSet justification for category management
Queue implementation for receipt workflow
PriorityQueue for bank account monitoring
Business logic alignment and design principles

2. Search and Sorting Techniques (search_sorting.md)
TimSort implementation analysis
Hash-based search algorithms
Linear search with filtering techniques
Performance optimization strategies
Algorithm selection matrix and integration patterns

3. Complexity Analysis (complexity_analysis.md)
Complete Big-O and Omega notation analysis
Time and space complexity for all operations
Scalability analysis across different data volumes
Performance optimization recommendations
Future complexity improvements

4. Comprehensive Technical Report (comprehensive_technical_report.md)
Executive summary of the entire system
Complete system architecture overview
Detailed performance evaluation
Design justifications and trade-off analysis
Future optimization roadmap

5. Documentation Index (README.md)
Overview of all documentation files
Quick reference tables for complexity and algorithms
Implementation highlights and academic achievements

Key Features of the Documentation:
✅ Complete Big-O and Omega Analysis: Every operation analyzed with best, average, and worst-case scenarios

✅ Data Structure Justifications: Detailed explanations for why each data structure was chosen based on business requirements

✅ Algorithm Implementation Details: Comprehensive analysis of sorting and searching techniques with code examples

✅ Performance Evaluation: Scalability analysis for small, medium, and large datasets

✅ Academic Rigor: Professional-level complexity analysis demonstrating deep understanding of computer science fundamentals

✅ Business Logic Integration: Technical choices clearly linked to real-world financial management requirements

The documentation demonstrates sophisticated understanding of data structures, algorithms, and complexity analysis while maintaining practical relevance to the financial domain. Each report stands alone while contributing to a comprehensive technical analysis of your system.

See the `docs/` folder for:

- Data structure justifications
- Search and sorting techniques
- Complexity analysis

---
MARANATHA OKELEY ODAI - 11025671
PATRICK LARYEA- 11066506
DAVID KANDA - 11038463
SHEPHERD DAYIE - 11011472
EMMANUEL OPOKU EFFAH - 11135584
MANASSEH SEYRAM BAVUG - 11302416
EBENEZER BADGER - 1
