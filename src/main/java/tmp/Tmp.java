package tmp;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;

public class Tmp {
	
	public static void main(String[] args) {
		SecureRandom secRandom = new SecureRandom();

	    byte[] result = new byte[32];
	    secRandom.nextBytes(result);
	    System.out.println(secRandom.nextInt(100));
	    System.out.println(result);
	    System.out.println(result);
	    System.out.println(result);
	    System.out.println(Hex.encodeHexString(result));
	    System.out.println(Hex.encodeHexString(result));
	}
}
