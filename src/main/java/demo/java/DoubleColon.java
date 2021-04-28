package demo.java;

public class DoubleColon {
	public static void main(String[] args) {

		/** STATIC METHODS **/
		/* Invoke as: ClassName::methodName */
		/* Implement the interface IMath with the method "max" of the Integer class.
		 * Signature are the same, so the new object "myMath" will inherit the method. */
		IMath myMath = Integer::max;
		System.out.println("max: "+myMath.max(5, 10));

		/** NOT STATIC METHODS **/
		/* Invoke as: object::methodName */
		String dummyString = "Pluto";
		/* Implement the interface IString with the method "substring" of the String class.
		 * Signature are the same, so the new object "myStr" will inherit the method. */
		IString myStr = dummyString::substring;
		System.out.println("substring 2-4:"+ myStr.subString(2, 4));

		/** CONSTRUCTOR **/
		/* Invoke as: ClassName::new */
		IFileCreator fileCreator = DoubleColon::new;
		System.out.println("new object:"+ fileCreator.create("FileName", "FilePath"));
	}

	/** Example of Functional Interface. */
	@FunctionalInterface
	public interface IMath {
		public int max(int value1, int value2);
	}

	/** Example of Functional Interface. */
	@FunctionalInterface
	public interface IString {
		public String subString(int start, int end);
	}

	/** Example of Functional Interface for the file creation. */
	@FunctionalInterface
	public interface IFileCreator {
		public DoubleColon create(String name, String path);
	}
	
	/* ***** UTILITIES ***** */

	/** Generic constructor. */
	public DoubleColon(String name, String path){
		System.out.println("Two arguments constructor");
	}

	/** Generic constructor. */
	public DoubleColon(String name){}
}
