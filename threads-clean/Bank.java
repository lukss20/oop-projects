// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts
	private final Transaction finalTransaction = new Transaction(-1, 0, 0);
	private ArrayBlockingQueue<Transaction> transactionsQueue = new ArrayBlockingQueue<Transaction>(100);
	private Account[] accounts = new Account[ACCOUNTS];
	private ArrayList<Thread> workers = new ArrayList<>();
	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
			try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			
			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;
				
				// Use the from/to/amount
				Transaction transaction = new Transaction(from, to, amount);
				transactionsQueue.put(transaction);
				// YOUR CODE HERE
			}


		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public class Worker extends Thread {
		@Override
		public void run() {
            try {
                Transaction transaction = transactionsQueue.take();
				while(!transaction.equals(finalTransaction)) {
					accounts[transaction.from].deposit(-1* transaction.amount);
					accounts[transaction.to].deposit(transaction.amount);
					transaction = transactionsQueue.take();
				}
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(String file, int numWorkers) throws InterruptedException {
		readFile(file);
		for(int i = 0; i < numWorkers; i++) {
			transactionsQueue.add(finalTransaction);

		}
		for(int i = 0; i<numWorkers; i++) {
			workers.get(i).join();
		}
	}

	public Bank(int numWorkers) {
		for (int i = 0; i < ACCOUNTS; i++) {
			accounts[i] = new Account(this,i,1000);
		}
		for (int i = 0; i < numWorkers; i++) {
			Thread temp = new Worker();
			workers.add(temp);
			temp.start();
		}

	}
	public void printRes(){
		for(int i = 0; i < ACCOUNTS; i++) {
			System.out.println(accounts[i].toString());
		}
	}
	public Account[] getAcc(){
		return accounts;
	}
	/*
	 Looks at commandline args and calls Bank processing.
	*/
	/*public static void main(String[] args) throws InterruptedException {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}
		
		String file = args[0];
		
		int numWorkers = 1;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
		}
		Bank bank = new Bank(numWorkers);
		bank.processFile(file, numWorkers);
		bank.printRes();
		// YOUR CODE HERE
	}*/
}

