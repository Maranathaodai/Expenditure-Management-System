package com.nkwa.tracker;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Nkwa Real Estate Expenditure System ===");
            System.out.println("1. Manage Expenditure Records");
            System.out.println("2. Manage Categories");
            System.out.println("3. Overview Bank");
            System.out.println("4. Generate Reports");
            System.out.println("5. Exit");
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
                    ExpenditureRecords.main(null);
                    break;
                case 2:
                    CategoryManager.main(null);
                    break;
                case 3:
                    BankLedger.main(null);
                    break;
                case 4:
                    ReportGenerator.generateReport();
                    break;
                case 5:
                    System.out.println("Thank you for using the system.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}