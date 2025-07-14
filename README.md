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

See the `docs/` folder for:

- Data structure justifications
- Search and sorting techniques
- Complexity analysis

---

*This project was built as a practical exercise in using core data structures for real-world accounting workflows. All code and design choices are explained in the docs folder for easy review by my lecturer or teammates.*

