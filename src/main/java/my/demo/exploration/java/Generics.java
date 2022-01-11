package demo.java;

public class Generics<I> {
	private I input;
	
	public static void main(String[] args) {
		
		/** GENERICS as CLASS **/
		/* This class is an example of Generic at "class" level.
		 * The "input" variable will be evaluated with the provided object of any "T" class.
		 * "T" is only a placeholder: is not a specific class type. */
		Generics<String> generic = new Generics<String>("qwerty123");
		
		/** GENERICS as METHOD **/
		/* This class is an example of Generic at "method" level. */
		
		/* Input. */
		generic.methodWithGenerics(generic);
		
		/** GENERICS with LIMITATION as CLASS **/
		/* Limited class types.
		 * Allows to keep a semantic coherence of the objects. */
		LimitedGenerics<SpecificClassType> limitedGeneric = new LimitedGenerics<>();
		System.out.println("LimitedGenerics: "+ limitedGeneric);
		
		/** GENERICS with LIMITATION as METHOD **/
		methodWithLimitedGenerics(new SpecificClassType(), limitedGeneric);
	}
	
	/** Constructor. */
	public Generics(I myInput){
		input = myInput;
	}
	
	/** Getter. */
	public I getInput() {
		return input;
	}
	
	/** Method. */
	/* "?" is a Jolly placeholder and can be limited as:
	 * - superior class: <? extends ClassName>.
	 * - inferior class: <? super SubClassName>. */
	public void methodWithGenerics(Generics<?> genericInput){
		System.out.println("input: "+ genericInput.getInput());
	}
	
	/** Example of Inner static class. */
	public interface GenericClassType {
		//...
	}
	
	/** Example of Inner static class. */
	public static class SpecificClassType implements GenericClassType {
		//...
	}
	
	/** Example of Inner static class. */
	public static class LimitedGenerics<I extends GenericClassType> {
		//...
	}
	
	/** Method. */
	public static <I extends GenericClassType, L extends LimitedGenerics<? extends SpecificClassType>> void methodWithLimitedGenerics(I genericInput, L limitedGeneric){
		System.out.println("input: "+ genericInput +" and limited: "+ limitedGeneric);
	}
}
