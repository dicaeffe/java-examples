package demo.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class JSON {

	/** Serialize the list into a JSON String.
	 * 
	 * @param list
	 * @return
	 */
	public static String toJson (List<?> list) {
		// return this object in JSON format
		return (new Gson()).toJson(list);
	}

	/** Deserialize the JSON String into a java Object.
	 * 
	 * @param json 
	 * @param classOfT 
	 * @return
	 */
	public static <T> T fromJson (String json, Class<T> classOfT) {
		return new Gson().fromJson(json, classOfT);
	}

	/** Deserialize the JSON String into a java Object.
	 * 
	 * @param json 
	 * @param classOfT 
	 * @return
	 */
	public static <T> List<T> listFromJson(String s, Class<T[]> clazz) {
	    T[] arr = new Gson().fromJson(s, clazz);
	    return Arrays.asList(arr);
	}
}
