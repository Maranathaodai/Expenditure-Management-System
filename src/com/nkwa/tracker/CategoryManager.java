package com.nkwa.tracker;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CategoryManager {
    static Scanner scanner = new Scanner(System.in);
    static Set<String> categories = new HashSet<>();

    public static void main(String[] args) {
        loadCategories();
        while (true) {
            System.out.println("\n--- Category Management Menu ---");
            System.out.println("1. Add Category");
            System.out.println("2. View All Categories");
            System.out.println("3. Search Category");
            System.out.println("4. Exit to Main Menu");
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
                    addCategory();
                    break;
                case 2:
                    viewCategories();
                    break;
                case 3:
                    searchCategory();
                    break;
                case 4:
                    saveCategories();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void loadCategories() {
        categories.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("data/categories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                categories.add(line.trim());
            }
        } catch (IOException e) {
            // File may not exist on first run; that's OK
        }
    }

    static void saveCategories() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/categories.txt"))) {
            for (String cat : categories) {
                bw.write(cat);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving categories: " + e.getMessage());
        }
    }

    static void addCategory() {
        System.out.print("Enter Category Name: ");
        String category = scanner.nextLine();
        if (categories.add(category)) {
            System.out.println("Category added.");
            saveCategories();
        } else {
            System.out.println("Category already exists.");
        }
    }

    static void viewCategories() {
        System.out.println("All Categories:");
        for (String cat : categories) {
            System.out.println("- " + cat);
        }
    }

    static void searchCategory() {
        System.out.print("Enter Category to Search: ");
        String category = scanner.nextLine();
        if (categories.contains(category)) {
            System.out.println("Category found.");
        } else {
            System.out.println("Category not found.");
        }
    }
}