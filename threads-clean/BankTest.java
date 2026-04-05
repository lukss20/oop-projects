import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    @Test
    public void testSmall() throws InterruptedException {
        Bank bank = new Bank(5);
        String file = "small.txt";
        bank.processFile(file,5);
        assertEquals(999,bank.getAcc()[0].getBalance());
        assertEquals(1,bank.getAcc()[0].getTransactions());
        assertEquals(1001,bank.getAcc()[1].getBalance());
        assertEquals(1,bank.getAcc()[1].getTransactions());
        bank.printRes();
    }
    @Test
    public void test5k() throws InterruptedException {
        Bank bank = new Bank(5);
        String file = "5k.txt";
        bank.processFile(file,5);
        bank.printRes();
        assertEquals(1000,bank.getAcc()[0].getBalance());
        assertEquals(518,bank.getAcc()[0].getTransactions());
        assertEquals(1000,bank.getAcc()[1].getBalance());
        assertEquals(444,bank.getAcc()[1].getTransactions());

    }
    @Test
    public void test100k() throws InterruptedException {
        Bank bank = new Bank(5);
        String file = "100k.txt";
        bank.processFile(file,5);
        bank.printRes();
        assertEquals(1000,bank.getAcc()[0].getBalance());
        assertEquals(10360,bank.getAcc()[0].getTransactions());
        assertEquals(1000,bank.getAcc()[1].getBalance());
        assertEquals(8880,bank.getAcc()[1].getTransactions());

    }
}
