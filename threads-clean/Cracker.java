// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.*;
import java.util.ArrayList;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	public static byte[] toHash(String input) {
		MessageDigest mymessage = null;
		try {
			mymessage = MessageDigest.getInstance("SHA");
			mymessage.update(input.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return mymessage.digest();
	}
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	private ArrayList<Thread> workers = new ArrayList<>();
	private String result;
	public String findResult(String targ, int maxlen,int numWorkers) throws InterruptedException {
		for(int i = 0;i<numWorkers;i++){
			Thread temp = new Worker((CHARS.length/numWorkers)*i,(CHARS.length/numWorkers)*(i+1),maxlen,targ);
			workers.add(temp);
			temp.start();
		}
		for(int i = 0;i<numWorkers;i++){
			workers.get(i).join();
		}
		System.out.println(result);
		return result;
	}

	public class Worker extends Thread{
		int start,end,maxlen;
		String target;
		public Worker(int start, int end, int maxlen, String target) {
			this.start = start;
			this.end = end;
			this.maxlen = maxlen;
			this.target = target;
		}
		@Override
		public void run() {
			for(int i =start; i<end; i++){
				recursionHelper(CHARS[i]+"",maxlen);
			}
		}
		private void recursionHelper(String now, int maxlen) {
			if(now.length() > maxlen) {
				return;
			}
			if (hexToString(toHash(now)).equals(target)) {
				result = now;

			}
			for (int i = 0; i < CHARS.length;i++) {
				recursionHelper(now + CHARS[i],maxlen);
			}
		}
	}

	/*public static void main(String[] args) throws InterruptedException {
		if (args.length == 0) {
			System.out.println("Args: target length [workers]");
			System.exit(1);
		}
		// args: targ len [num]
		String targ = args[0];
		if(args.length == 1){
			System.out.println(hexToString(toHash(targ)));
		}else {
			int len = Integer.parseInt(args[1]);
			int num = 1;
			if (args.length > 2) {
				num = Integer.parseInt(args[2]);
			}
			Cracker cracker = new Cracker();
			cracker.findResult(targ,len,num);
			// a! 34800e15707fae815d7c90d49de44aca97e2d759
			// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

			// YOUR CODE HERE
		}
	}*/
}
