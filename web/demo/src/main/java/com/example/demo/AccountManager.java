package com.example.demo;

import java.util.HashMap;

public class AccountManager {
    HashMap<String,String> accounts;
    public AccountManager() {
        accounts = new HashMap<>();
        createAccount("Patrick","1234");
        createAccount("Molly","FloPup");
    }
    public boolean checkAccount(String account) {
        return accounts.containsKey(account);
    }
    public boolean correctPassword(String username,String password) {
        return checkAccount(username) && password.equals(accounts.get(username));
    }
    public void createAccount(String username, String password) {
        accounts.put(username,password);
    }
}
