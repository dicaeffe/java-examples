package demo.java;

public class ClassTypes {

	public static void main(String[] args) {
		
		/* Usually classes are defined in a file with the same name.
		 * There are 3 exceptions: Inner, Local and Anonymous classes. */
		
		/** INNER */
		/* Are classes defined inside another class (the Enclosing class) but outside of any method. */
		ClassTypes enclosing = new ClassTypes();
		
		Inner inner = enclosing.new Inner();
		System.out.println("inner class object: "+ inner);
		
		InnerStatic innerStatic = new InnerStatic();
		System.out.println("inner static class object: "+ innerStatic);
		
		InnerInterface innerInterface = enclosing.new Inner();
		System.out.println("inner interface object: "+ innerInterface);
		
		/** LOCAL */
		/* Are classes defined inside a block {...} (e.g. a method). 
		 * Cannot be static and has visibility only inside the block. 
		 * Local interfaces do not exist due to their not-static nature. */
		enclosing.metod("string");
		
		/** ANONYMOUS */
		/* Are local classes without an assigned name. 
		 * This class will be defined and instatiated at the same time and only once.
		 * All anonymous classes must be part of an instruction (so instruction will end with a ";"). */
		anonymous();
	}

	/** Example of Inner class. */
	class Inner implements InnerInterface {
		//...
	}
	
	/** Example of Inner static class.
	 * This class can be invoked as any other class. */
	static class InnerStatic {
		//...
	}
	
	/** Example of Inner interface.
	 * Is automatically static. */
	interface InnerInterface {
		//...
	}
	
	/** Method. */
	public void metod(String string) {
		
		/** Example of Local class. */
		class LocalClass {
			public void localMetod(String string) {
				System.out.println("print from local: "+ string);
			}
		}
		
		/* Instantiate an object with the local class. */
		LocalClass local = new LocalClass();
		System.out.println("local class object: "+ local);
		
		/* Invoke a method of the local class. */
		String myString = "qwerty123";
		local.localMetod(myString);
	}
	
	/** Example of Anonymous classes. */
	private static void anonymous() {
		/** ANONYMOUS with INTERFACE */
		/* Instantiate an anonymous class with the interface MyInterface. 
		 * The class implements an interface, so there are no construtor and will be used the default one. */
		MyInterface anonymousInterface = new MyInterface() {
			@Override
			public String method1(String name) {
				return name;
			}

			@Override
			public String method2(String name) {
				return name+name;
			}
		};
		System.out.println("anonymous class object fron interface: "+ anonymousInterface);
	}
	
	public interface MyInterface {
		public String method1(String name);
		public String method2(String name);
	}
	
}