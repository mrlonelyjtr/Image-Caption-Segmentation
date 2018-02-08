import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Captions {
	/*
	 * captions_train2014.json, captions_val2014.json
	 */
	
	public static void main(String[] args) {
		try{
			JsonObject jsonObject = new JsonObject();
			JsonArray jsonArray = new JsonArray();
			HashSet<Integer> hashSet = new HashSet<Integer>();
			
			JsonParser parser1 = new JsonParser();
			JsonParser parser2 = new JsonParser();
			//JsonObject object1 = (JsonObject) parser1.parse(new FileReader("captions_train2014.json"));
			//JsonObject object2 = (JsonObject) parser2.parse(new FileReader("annotation/instances_train2014_new.json"));
			JsonObject object1 = (JsonObject) parser1.parse(new FileReader("captions_val2014.json"));
			JsonObject object2 = (JsonObject) parser2.parse(new FileReader("annotation/instances_val2014_new.json"));
			
			JsonObject info = object1.get("info").getAsJsonObject();
			jsonObject.add("info", info);
			JsonArray images = object1.get("images").getAsJsonArray();
			jsonObject.add("images", images);
			JsonArray licenses = object1.get("licenses").getAsJsonArray();
			jsonObject.add("licenses", licenses);
			
			JsonArray annotations1 = object1.get("annotations").getAsJsonArray();
			JsonArray annotations2 = object2.get("annotations").getAsJsonArray();
			for (int j = 0; j < annotations2.size(); ++j) {
				JsonObject annotation = annotations2.get(j).getAsJsonObject();
				int imageId = annotation.get("image_id").getAsInt();
				hashSet.add(imageId);
			}
			for (int i = 0; i < annotations1.size(); ++i){
				JsonObject annotation = annotations1.get(i).getAsJsonObject();
				int imageId = annotation.get("image_id").getAsInt();
				
				if (hashSet.contains(imageId)){
					jsonArray.add(annotation);
				}
			}
			jsonObject.add("annotations", jsonArray);
			
			//PrintWriter out = new PrintWriter(new FileOutputStream("annotation/captions_train2014_new.json")); 
			PrintWriter out = new PrintWriter(new FileOutputStream("annotation/captions_val2014_new.json")); 
			out.println(jsonObject.toString());
			out.close();
			
			System.out.println("Finished!");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		}
	}
}