package com.bobocode.hw4;


import com.bobocode.data.Accounts;

import java.time.LocalDate;

public class DemoApp {
    public static void main(String[] args) {
        var accounts = Accounts.generateAccountList(100);
        var emailToBirthdayTable = new HashTable<String, LocalDate>();
        accounts.forEach(a -> emailToBirthdayTable.put(a.getEmail(), a.getBirthday()));
        emailToBirthdayTable.printTable();
    }
}