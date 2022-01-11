package demo.java;

public class Signatures {

	public static void main(String[] args) {

		/** VARARGS */
		Signatures va = new Signatures();
		va.testVarargs("do","re","mi","fa","sol","la","si");
		va.testVarargs("1","2","3","4");
	}
	
	/** The triple dot (ellipsis) make "String..." equivalent to "String[]". */
	public void testVarargs(String... list) {
		System.out.println("Method 1");
		for (String tmp:list) {
			System.out.println("#" + tmp);
		}
	}
}
