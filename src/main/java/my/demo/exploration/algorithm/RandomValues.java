package demo.algorithm;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RandomValues {
	public static void main(String[] args) {
		//get random long
		System.out.println(createRandomLong(10, 100));
		
		//test randomness
		testRandomValues();
	}

	private static void testRandomValues() {
		System.out.println("BEGIN");
		List<Long> list = new ArrayList<>();
		int count = 0;
		long max = 100000000000L;
		long min =  10000000000L;
		
		for (int i = 0; i < 100000; i++) {
			
			long num = createRandomLong(min, max);
			
			if (list.contains(Long.valueOf(num))) {
				count += 1;
				System.out.println("Duplicate num: "+count);
			} else {
				list.add(Long.valueOf(num));
				System.out.println(count+") - "+num);
			}
		}
		
		System.out.println("END - ListSize: "+ list.size() + " First Element is: "+list.get(0));
		System.out.println("END - Total duplicate: "+count);
	}
	

	
	/** Create a random String made with a random value of 11 numerical digits.
	 * n.b. 11 numerical digits is the maximum allowed for Bluetooth communications.
	 * 
	 * @return
	 */
	public static String createRandomId() {
		return String.valueOf(createRandomLong(100, 1000));
	}

	/** Generate a random long in the range [min, max).
	 * note: min is included in the range, max is not.
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static long createRandomLong (long min, long max) {
		return min + Math.abs((new SecureRandom()).nextLong() % (max-min));
	}
}
