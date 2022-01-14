package my.demo.exploration.json;

import java.util.Arrays;
import java.util.List;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
	
	/** https://www.baeldung.com/introduction-to-json-schema-in-java
	 * Required the dependency:
		<dependency>
		    <groupId>org.everit.json</groupId>
		    <artifactId>org.everit.json.schema</artifactId>
		    <version>1.3.0</version>
		</dependency>
	*/
	public static void validate() {
		String schemaJson = "{\n"
				+ "    \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n"
				+ "    \"title\": \"Product\",\n"
				+ "    \"description\": \"A product from the catalog\",\n"
				+ "    \"type\": \"object\",\n"
				+ "    \"properties\": {\n"
				+ "        \"id\": {\n"
				+ "            \"description\": \"The unique identifier for a product\",\n"
				+ "            \"type\": \"integer\"\n"
				+ "        },\n"
				+ "        \"name\": {\n"
				+ "            \"description\": \"Name of the product\",\n"
				+ "            \"type\": \"string\"\n"
				+ "        },\n"
				+ "        \"price\": {\n"
				+ "            \"type\": \"number\",\n"
				+ "            \"minimum\": 0,\n"
				+ "            \"exclusiveMinimum\": true\n"
				+ "        }\n"
				+ "    },\n"
				+ "    \"required\": [\"id\", \"name\", \"price\"]\n"
				+ "}";
		
		String jsonToBeValidate = "{\n"
				+ "    \"id\": 1,\n"
				+ "    \"name\": \"Lampshade\",\n"
				+ "    \"price\": 0\n"
				+ "}";
		
		JSONObject jsonSchema = new JSONObject(new JSONTokener(schemaJson));
		Schema schema = SchemaLoader.load(jsonSchema);
		
		JSONObject jsonSubject = new JSONObject(new JSONTokener(jsonToBeValidate));
		schema.validate(jsonSubject);
	}
	
	public static void findValue() {
        try {
    		String jsonString = "{\n"
    				+ "	\"id\": 1,\n"
    				+ "	\"name\": \"Lampshade\",\n"
    				+ "	\"details\": {\n"
    				+ "		\"day-1\": \"yesterday\",\n"
    				+ "		\"day1\": \"today\",\n"
    				+ "		\"day2\": \"tomorrow\"\n"
    				+ "	},\n"
    				+ "	\"price\": 0\n"
    				+ "}";
			System.out.println(jsonString);
			System.out.println("---");
    		
            ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.readValue(jsonString, ObjectNode.class);
			System.out.println(node.findValue("day1"));
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
