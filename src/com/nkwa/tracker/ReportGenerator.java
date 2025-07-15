package com.nkwa.tracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Handles the generation of various financial and analytical reports for the system.
// This class will read data from storage and produce summaries, analyses, and exportable files.

public class ReportGenerator {

    public static void generateReport() {
        System.out.println("\n--- Generating Report ---\n");
        StringBuilder report = new StringBuilder();

        // Read accounts
        report.append("Accounts:\n");
        try (BufferedReader br = new BufferedReader(new FileReader("data/accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                report.append(line).append("\n");
            }
        } catch (IOException e) {
            report.append("Error reading accounts: ").append(e.getMessage()).append("\n");
        }

        // Read categories
        report.append("\nCategories:\n");
        try (BufferedReader br = new BufferedReader(new FileReader("data/categories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                report.append(line).append("\n");
            }
        } catch (IOException e) {
            report.append("Error reading categories: ").append(e.getMessage()).append("\n");
        }

        // Read expenditures
        report.append("\nExpenditures:\n");
        try (BufferedReader br = new BufferedReader(new FileReader("data/expenditures.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                report.append(line).append("\n");
            }
        } catch (IOException e) {
            report.append("Error reading expenditures: ").append(e.getMessage()).append("\n");
        }

        // Read receipts
        report.append("\nReceipts:\n");
        try (BufferedReader br = new BufferedReader(new FileReader("data/receipts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                report.append(line).append("\n");
            }
        } catch (IOException e) {
            report.append("Error reading receipts: ").append(e.getMessage()).append("\n");
        }

        // Save report to file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reports/summary_report.txt"))) {
            bw.write(report.toString());
            System.out.println("Report generated successfully: reports/summary_report.txt\n");
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }
}
