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
            System.out.println("5. Exit to Main Menu");
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
                    saveAccounts();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    static void addAccount() {
        System.out.print("Enter Account ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Bank Name: ");
        String name = scanner.nextLine();
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