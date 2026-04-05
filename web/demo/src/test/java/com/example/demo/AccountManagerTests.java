package com.example.demo;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountManagerTests {
    private AccountManager acmanager = new AccountManager();

    @Test
    public void checkAccountTest(){
        assertTrue(acmanager.checkAccount("Patrick"));
        assertFalse(acmanager.checkAccount("Luka"));
    }
    @Test
    public void correctPasswordTest(){
        assertTrue(acmanager.correctPassword("Patrick","1234"));
        assertFalse(acmanager.correctPassword("Molly","incorrect"));
        assertFalse(acmanager.correctPassword("Molly","1234"));
    }
    @Test
    public void createAccountTest(){
        acmanager.createAccount("Luka","Me");
        assertTrue(acmanager.checkAccount("Luka"));
        assertTrue(acmanager.correctPassword("Luka","Me"));
    }
}
