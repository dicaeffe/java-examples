package demo.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.NoArgsConstructor;

public class Functions {

	public static void main(String[] args) {
		
		/* Is a Functional Interface. */
		
		/** String -> Integer */
		funcFromStringToInt();
        
        /** CHAIN FUNCTIONs */
        /* Functions can be chained */
		funcFromStringToIntChained();

        /** List -> Map */
		funcFromListToMap();

        /** List -> List */
		funcFromListToList();
		
		/** PREDICATEs */
		funcAsPredicate();
	}
	
	/** Example of Functional Interface.
	 *
	 * @param <T> Type of the input to the function.
	 * @param <R> Type of the result of the function.
	 */
	@FunctionalInterface
	public interface MyFunction<T, R> {
	      R apply(T t);
	}
	
	/** Example of Functional Interface. */
	private static void funcAsPredicate() {
		/* Create prerequisites */
    	Functions obj = new Functions();
		Animal cat = obj.new Animal();
		Animal eagle = obj.new Bird();
		IsBird isBirdPredicate = obj.new IsBird();
		ToFly  toFlyPredicate = obj.new ToFly();
		
		/* Check if the animal is a bird and/or fly. */
		System.out.println("eagle is a bird:"+
							isBirdPredicate.test(eagle) + " To fly:"+ toFlyPredicate.test(eagle));
		System.out.println("cat is a bird:"+
							isBirdPredicate.test(cat) + " To fly:"+ toFlyPredicate.test(cat));
	}
	
	/** Example of Functional Interface. */
    private static void funcFromListToList() {
    	Functions obj = new Functions();
        List<String> list = Arrays.asList("node", "c++", "java", "javascript");
        // lambda
        //List<String> result = obj.map(list, x -> obj.sha256(x))
        // method reference
        List<String> result = obj.map(list, obj::sha256);
        result.forEach(System.out::println);
	}
	
	/** Example of Functional Interface. */
	private static void funcFromListToMap() {
		Functions obj = new Functions();
		List<String> list = Arrays.asList("node", "c++", "java", "javascript");
		// lambda
		Map<String, Integer> map = obj.convertListToMap(list, x -> x.length());
		System.out.println(map);    // {node=4, c++=3, java=4, javascript=10}
		// method reference
		Map<String, Integer> map2 = obj.convertListToMap(list, obj::getLength);
		System.out.println(map2);
	}
	
	/** Example of Functional Interface. */
	private static void funcFromStringToIntChained() {
        Function<String, Integer> func = x -> x.length();
        Function<Integer, Integer> func2 = x -> x * 2;
        Integer result = func.andThen(func2).apply("qwerty");
        System.out.println(result);
	}
	
	/** Example of Functional Interface. */
	private static void funcFromStringToInt() {
        Function<String, Integer> func = x -> x.length();
        Integer apply = func.apply("qwerty");
        System.out.println(apply);
	}
	
	/* ***** UTILITIES ***** */
	
    public <T, R> List<R> map(List<T> list, Function<T, R> func) {
        List<R> result = new ArrayList<>();
        
        for (T t : list) {
            result.add(func.apply(t));
        }
        return result;
    }

    /** sha256 a string.
     * 
     * @param str
     * @return
     */
    public String sha256(String str) {
        return DigestUtils.sha256Hex(str);
    }

	public <T, R> Map<T, R> convertListToMap(List<T> list, Function<T, R> func) {

        Map<T, R> result = new HashMap<>();
        for (T t : list) {
            result.put(t, func.apply(t));
        }
        return result;

    }
	
	/** Return the length of the input String str.
	 * 
	 * @param str
	 * @return
	 */
	public Integer getLength(String str) {
		return str.length();
	}
	
	@NoArgsConstructor
	public class Animal {}
	
	public class Bird extends Animal {}
	
	/** Check if the Animal is a Bird. */
	public class IsBird implements Predicate<Animal>{
		@Override
		public boolean test(Animal animal) {
			return animal instanceof Bird;
		}
	}
	
	/** Check if the Animal can fly. An Animal can fly if is a bird. */
	public class ToFly implements Predicate<Animal>{
		@Override
		public boolean test(Animal animal) {
			/* reuse the IsBird test method */
			return new IsBird().test(animal);
		}
	}
}
