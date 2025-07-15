package com.nkwa.tracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankLedger {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, BankAccount> accounts = new HashMap<>();

    static void loadAccounts() {
        accounts.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("data/accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: accountId|bankName|balance|expenditureCode1,expenditureCode2,...
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
                    accounts.put(parts[0], acc);
                }
            }
        } catch (IOException e) {
            // File may not exist on first run; that's OK
        }
    }

    static void saveAccounts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/accounts.txt"))) {
            for (BankAccount acc : accounts.values()) {
                String codes = String.join(",", acc.expenditureCodes);
                bw.write(acc.accountId + "|" + acc.bankName + "|" + acc.balance + "|" + codes);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        loadAccounts();
        while (true) {
            System.out.println("\n--- Bank Ledger Menu ---");
            System.out.println("1. Add Bank Account");
            System.out.println("2. View Bank Account");
            System.out.println("3. View All Accounts");
            System.out.println("4. Link Expenditure to Account");
            System.out.println("5. Analyze Material Price Affordability");
            System.out.println("6. Exit to Main Menu");
            System.out.print("Choose option: ");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addAccount();
                    saveAccounts();
                    break;
                case 2:
                    viewAccount();
                    break;
                case 3:
                    viewAllAccounts();
                    break;
                case 4:
                    linkExpenditure();
                    saveAccounts();
                    break;
                case 5:
                    analyzeMaterialAffordability();
                    break;
                case 6:
                    saveAccounts();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Reads material prices and estimates house affordability.
     * Assumes a fixed quantity for each material for demonstration.
     */
    static void analyzeMaterialAffordability() {
        System.out.println("\n--- Material Price Affordability Analysis ---");
        Map<String, Double> materialPrices = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/materials.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    materialPrices.put(parts[0], Double.parseDouble(parts[1]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading material prices: " + e.getMessage());
            return;
        }
        // Ask user for quantities
        Map<String, Integer> materialQuantities = new HashMap<>();
        Scanner inputScanner = new Scanner(System.in);
        for (String mat : materialPrices.keySet()) {
            int qty = 0;
            while (true) {
                System.out.printf("Enter quantity for %s: ", mat);
                String qtyStr = inputScanner.nextLine();
                try {
                    qty = Integer.parseInt(qtyStr);
                    if (qty < 0) {
                        System.out.println("Quantity cannot be negative.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Please enter a valid integer.");
                }
            }
            materialQuantities.put(mat, qty);
        }
        double totalCost = 0.0;
        for (String mat : materialQuantities.keySet()) {
            double price = materialPrices.getOrDefault(mat, 0.0);
            int qty = materialQuantities.get(mat);
            totalCost += price * qty;
            System.out.printf("%s: %d units x %.2f = %.2f\n", mat, qty, price, price * qty);
        }
        System.out.printf("Total Material Cost for House: %.2f\n", totalCost);
        // Example affordability threshold
        double affordabilityThreshold = 50000.0;
        if (totalCost <= affordabilityThreshold) {
            System.out.println("House is affordable based on current material prices.");
        } else {
            System.out.println("House is NOT affordable based on current material prices.");
        }
    }
    static void addAccount() {
        String id = "";
        do {
            System.out.print("Enter Account ID: ");
            id = scanner.nextLine();
            if (id == null || id.trim().isEmpty()) {
                System.out.println("Account ID cannot be empty. Please enter a valid account ID.");
            }
        } while (id == null || id.trim().isEmpty());

        String name = "";
        do {
            System.out.print("Enter Bank Name: ");
            name = scanner.nextLine();
            if (name == null || name.trim().isEmpty()) {
                System.out.println("Bank name cannot be empty. Please enter a valid bank name.");
            }
        } while (name == null || name.trim().isEmpty());
        double balance = 0.0;
        while (true) {
            System.out.print("Enter Balance: ");
            String balanceInput = scanner.nextLine();
            try {
                balance = Double.parseDouble(balanceInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid balance. Please enter a valid number.");
            }
        }
        accounts.put(id, new BankAccount(id, name, balance));
        System.out.println("Account added.");
    }

    static void viewAccount() {
        System.out.print("Enter Account ID: ");
        String id = scanner.nextLine();
        if (accounts.containsKey(id)) {
            System.out.println(accounts.get(id));
        } else {
            System.out.println("Account not found.");
        }
    }

    static void viewAllAccounts() {
        for (BankAccount account : accounts.values()) {
            System.out.println(account);
        }
    }

    static void linkExpenditure() {
        System.out.print("Enter Account ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Expenditure Code: ");
        String code = scanner.nextLine();
        if (accounts.containsKey(id)) {
            accounts.get(id).expenditureCodes.add(code);
            System.out.println("Expenditure linked.");
        } else {
            System.out.println("Account not found.");
        }
    }
}