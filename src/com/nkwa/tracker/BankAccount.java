package com.nkwa.tracker;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    public String accountId;
    public String bankName;
    public double balance;
    public List<String> expenditureCodes;

    public BankAccount(String accountId, String bankName, double balance) {
        this.accountId = accountId;
        this.bankName = bankName;
        this.balance = balance;
        this.expenditureCodes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return accountId + " | " + bankName + " | Balance: " + String.format("%.2f", balance) + " | Expenditures: " + expenditureCodes;
    }
}