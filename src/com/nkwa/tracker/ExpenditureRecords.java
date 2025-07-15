package com.nkwa.tracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

// Simple Receipt class for demonstration
class Receipt {
    String receiptId;
    String details;
    public Receipt(String receiptId, String details) {
        this.receiptId = receiptId;
        this.details = details;
    }
    public String toString() {
        return "Receipt ID: " + receiptId + ", Details: " + details;
    }
}

// Add receipt field to Expenditure
class Expenditure {
    String code;
    double amount;
    String date;
    String phase;
    String category;
    String accountUsed;
    Receipt receipt; // Link to receipt

    public Expenditure(String code, double amount, String date, String phase, String category, String accountUsed) {
        this.code = code;
        this.amount = amount;
        this.date = date;
        this.phase = phase;
        this.category = category;
        this.accountUsed = accountUsed;
    }

    @Override
    public String toString() {
        return "Code: " + code + ", Amount: " + String.format("%.2f", amount) + ", Date: " + date + ", Phase: " + phase +
               ", Category: " + category + ", Account Used: " + accountUsed;
    }
}

public class ExpenditureRecords {
    static void loadBankAccounts() {
        bankAccounts.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("data/accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    BankAccount acc = new BankAccount(
                        parts[0],
                        parts[1],
                        Double.parseDouble(parts[2])
                    );
                    if (parts.length == 4 && !parts[3].isEmpty()) {
                        for (String code : parts[3].split(",")) {
                            acc.expenditureCodes.add(code);
                        }
                    }
                    bankAccounts.put(parts[0], acc);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading bank accounts: " + e.getMessage());
        }
    }
    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, Expenditure> expenditures = new HashMap<>();
    static Queue<Receipt> receiptQueue = new LinkedList<>();
    static HashMap<String, BankAccount> bankAccounts = new HashMap<>();
    static PriorityQueue<BankAccount> bankMinHeap = new PriorityQueue<>(Comparator.comparingDouble(a -> a.balance));

    static void loadReceipts() {
        receiptQueue.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("data/receipts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: receiptId|details
                String[] parts = line.split("\\|", 2);
                if (parts.length == 2) {
                    receiptQueue.add(new Receipt(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            // File may not exist on first run; that's OK
        }
    }

    static void saveReceipts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/receipts.txt"))) {
            for (Receipt r : receiptQueue) {
                bw.write(r.receiptId + "|" + r.details);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving receipts: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        loadExpenditures();
        loadReceipts();
        loadBankAccounts();
        bankMinHeap.clear();
        bankMinHeap.addAll(bankAccounts.values());
        // Only load bank accounts from user data file
        // (Assume another method loads accounts from accounts.txt)
        // bankMinHeap.addAll(bankAccounts.values());

        while (true) {
            System.out.println("\n--- Expenditure Records Menu ---");
            System.out.println("1. Add Expenditure");
            System.out.println("2. View Expenditure by Code");
            System.out.println("3. Sort Expenditures by Category");
            System.out.println("4. Sort Expenditures by Date");
            System.out.println("5. Search Expenditures");
            System.out.println("6. Link Receipt to Expenditure");
            System.out.println("7. Review Next Receipt in Queue");
            System.out.println("8. Update Bank Balance for Expenditure");
            System.out.println("9. Show Bank with Lowest Balance");
            System.out.println("10. Monthly Burn Rate");
            System.out.println("11. Forecast Profitability");
            System.out.println("12. Exit to Main Menu");
            System.out.print("Select option: ");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            switch (choice) {
                case 1:
                    addExpenditure();
                    saveExpenditures();
                    break;
                case 2:
                    viewExpenditure();
                    break;
                case 3:
                    sortByCategory();
                    break;
                case 4:
                    sortByDate();
                    break;
                case 5:
                    searchMenu();
                    break;
                case 6:
                    linkReceiptToExpenditure();
                    saveReceipts();
                    break;
                case 7:
                    reviewNextReceipt();
                    saveReceipts();
                    break;
                case 8:
                    updateBankBalanceForExpenditure();
                    break;
                case 9:
                    showBankWithLowestBalance();
                    break;
                case 10:
                    monthlyBurnRate();
                    break;
                case 11:
                    forecastProfitability();
                    break;
                case 12:
                    saveExpenditures();
                    saveReceipts();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void addExpenditure() {
        System.out.print("Enter Code: ");
        String code = scanner.nextLine();
        double amount = 0.0;
        while (true) {
            System.out.print("Enter Amount: ");
            String amountInput = scanner.nextLine();
            try {
                amount = Double.parseDouble(amountInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
            }
        }
        String date = "";
        while (true) {
            System.out.print("Enter Date (DD/MM/YYYY): ");
            date = scanner.nextLine();
            String[] dateParts = date.split("/");
            if (dateParts.length == 3) {
                try {
                    int day = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    int year = Integer.parseInt(dateParts[2]);
                    if (day >= 1 && day <= 31 && month >= 1 && month <= 12 && year > 1900) {
                        break;
                    } else {
                        System.out.println("Invalid date. Please enter a valid day (1-31), month (1-12), and year.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid date format. Please use DD/MM/YYYY.");
                }
            } else {
                System.out.println("Invalid date format. Please use DD/MM/YYYY.");
            }
        }
        System.out.print("Enter Phase: ");
        String phase = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        String accountUsed = "";
        while (true) {
            System.out.print("Enter Account Used: ");
            accountUsed = scanner.nextLine();
            if (accountUsed != null && !accountUsed.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Account ID cannot be empty. Please enter a valid account ID.");
            }
        }

        Expenditure exp = new Expenditure(code, amount, date, phase, category, accountUsed);
        expenditures.put(code, exp);
        System.out.println("Expenditure recorded successfully.");
    }

    static void viewExpenditure() {
        System.out.print("Enter Expenditure Code: ");
        String code = scanner.nextLine();

        if (expenditures.containsKey(code)) {
            System.out.println(expenditures.get(code));
        } else {
            System.out.println("Expenditure not found.");
        }
    }

    static void sortByCategory() {
        List<Expenditure> list = new ArrayList<>(expenditures.values());
        list.sort(Comparator.comparing(e -> e.category.toLowerCase()));
        System.out.println("--- Expenditures Sorted by Category ---");
        for (Expenditure e : list) System.out.println(e);
    }

    static void sortByDate() {
        List<Expenditure> list = new ArrayList<>(expenditures.values());
        list.sort(Comparator.comparing(e -> e.date));
        System.out.println("--- Expenditures Sorted by Date ---");
        for (Expenditure e : list) System.out.println(e);
    }

    static void searchMenu() {
        System.out.println("1. By Date Range");
        System.out.println("2. By Category");
        System.out.println("3. By Cost Range");
        System.out.println("4. By Bank Account");
        System.out.print("Select search type: ");
        int opt = Integer.parseInt(scanner.nextLine());
        switch (opt) {
            case 1:
                System.out.print("Start date (DD/MM/YYYY): ");
                String start = scanner.nextLine();
                System.out.print("End date (DD/MM/YYYY): ");
                String end = scanner.nextLine();
                searchByDateRange(start, end);
                break;
            case 2:
                System.out.print("Category: ");
                String cat = scanner.nextLine();
                searchByCategory(cat);
                break;
            case 3:
                System.out.print("Min amount: ");
                double min = Double.parseDouble(scanner.nextLine());
                System.out.print("Max amount: ");
                double max = Double.parseDouble(scanner.nextLine());
                searchByCostRange(min, max);
                break;
            case 4:
                System.out.print("Account Used: ");
                String acc = scanner.nextLine();
                searchByAccount(acc);
                break;
            default:
                System.out.println("Invalid search type.");
        }
    }

    static void searchByDateRange(String start, String end) {
        System.out.println("--- Expenditures in Date Range ---");
        for (Expenditure e : expenditures.values()) {
            if (e.date.compareTo(start) >= 0 && e.date.compareTo(end) <= 0) {
                System.out.println(e);
            }
        }
    }

    static void searchByCategory(String category) {
        System.out.println("--- Expenditures in Category: " + category + " ---");
        for (Expenditure e : expenditures.values()) {
            if (e.category.equalsIgnoreCase(category)) {
                System.out.println(e);
            }
        }
    }

    static void searchByCostRange(double min, double max) {
        System.out.println("--- Expenditures in Cost Range ---");
        for (Expenditure e : expenditures.values()) {
            if (e.amount >= min && e.amount <= max) {
                System.out.println(e);
            }
        }
    }

    static void searchByAccount(String account) {
        System.out.println("--- Expenditures for Account: " + account + " ---");
        for (Expenditure e : expenditures.values()) {
            if (e.accountUsed.equalsIgnoreCase(account)) {
                System.out.println(e);
            }
        }
    }

    static void linkReceiptToExpenditure() {
        System.out.print("Enter Expenditure Code: ");
        String code = scanner.nextLine();
        Expenditure exp = expenditures.get(code);
        if (exp == null) {
            System.out.println("Expenditure not found.");
            return;
        }
        System.out.print("Enter Receipt ID: ");
        String rid = scanner.nextLine();
        System.out.print("Enter Receipt Details: ");
        String details = scanner.nextLine();
        Receipt r = new Receipt(rid, details);
        exp.receipt = r;
        receiptQueue.add(r);
        System.out.println("Receipt linked and added to review queue.");
    }

    static void reviewNextReceipt() {
        if (receiptQueue.isEmpty()) {
            System.out.println("No receipts to review.");
            return;
        }
        Receipt r = receiptQueue.poll();
        System.out.println("Reviewing receipt: " + r);
    }

    static void updateBankBalanceForExpenditure() {
        System.out.print("Enter Expenditure Code: ");
        String code = scanner.nextLine();
        Expenditure exp = expenditures.get(code);
        if (exp == null) {
            System.out.println("Expenditure not found.");
            return;
        }
        BankAccount acc = bankAccounts.get(exp.accountUsed);
        if (acc == null) {
            System.out.println("Bank account not found.");
            return;
        }
        acc.balance -= exp.amount;
        acc.expenditureCodes.add(code);
        // Rebuild min-heap
        bankMinHeap.clear();
        bankMinHeap.addAll(bankAccounts.values());
        if (acc.balance < 1000) {
            System.out.println("Warning: Low balance in account " + acc.accountId);
        } else {
            System.out.println("Bank balance updated. New balance: " + String.format("%.2f", acc.balance));
        }
    }

    static void showBankWithLowestBalance() {
        if (bankMinHeap.isEmpty()) {
            System.out.println("No bank accounts available.");
            return;
        }
        BankAccount acc = bankMinHeap.peek();
        System.out.println("Bank with lowest balance: " + acc);
    }

    static void monthlyBurnRate() {
        System.out.print("Enter month (MM): ");
        String month = scanner.nextLine();
        double total = 0;
        for (Expenditure e : expenditures.values()) {
            if (e.date.startsWith(month)) {
                total += e.amount;
            }
        }
        System.out.println("Total spent in " + month + ": " + total);
    }

    static void forecastProfitability() {
        System.out.print("Enter projected income: ");
        double income = Double.parseDouble(scanner.nextLine());
        double totalSpent = 0;
        for (Expenditure e : expenditures.values()) {
            totalSpent += e.amount;
        }
        double profit = income - totalSpent;
        System.out.println("Projected profit: " + profit);
    }

    static void loadExpenditures() {
        expenditures.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("data/expenditures.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip header or comment lines
                if (line.trim().startsWith("#") || line.trim().isEmpty()) continue;
                // Format: code|amount|date|phase|category|accountUsed
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    Expenditure exp = new Expenditure(
                        parts[0],
                        Double.parseDouble(parts[1]),
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5]
                    );
                    expenditures.put(parts[0], exp);
                }
            }
        } catch (IOException e) {
            // File may not exist on first run; that's OK
        }
    }

    static void saveExpenditures() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/expenditures.txt"))) {
            for (Expenditure exp : expenditures.values()) {
                bw.write(exp.code + "|" + exp.amount + "|" + exp.date + "|" + exp.phase + "|" + exp.category + "|" + exp.accountUsed);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenditures: " + e.getMessage());
        }
    }
}