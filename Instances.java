import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Instances {
	/* 
	 * instances_train2014.json, instances_val2014.json
	 * 
	 * 62=chair, 63=couch, 64=potted plant. 65=bed, 67=dining table, 70=toilet
	 * 72=tv, 73=laptop, 78=microwave, 79=oven, 80=toaster, 81=sink, 82=refrigerator
	 */
	
	public static void main(String[] args) {
		try {
			JsonObject jsonObject = new JsonObject();
			JsonArray jsonArray = new JsonArray();
			
			JsonParser parser = new JsonParser();
			//JsonObject object = (JsonObject) parser.parse(new FileReader("instances_train2014.json"));
			JsonObject object = (JsonObject) parser.parse(new FileReader("instances_val2014.json"));
			
			JsonObject info = object.get("info").getAsJsonObject();
			jsonObject.add("info", info);
			JsonArray images = object.get("images").getAsJsonArray();
			jsonObject.add("images", images);
			JsonArray licenses = object.get("licenses").getAsJsonArray();
			jsonObject.add("licenses", licenses);
			
			JsonArray annotations = object.get("annotations").getAsJsonArray();
			for (int i = 0; i < annotations.size(); ++i){
				JsonObject annotation = annotations.get(i).getAsJsonObject();
				int categoryId = annotation.get("category_id").getAsInt();
				
				if (categoryId == 62 || categoryId == 63 || categoryId == 64 || categoryId == 65 || categoryId == 67 
						|| categoryId == 70 || categoryId == 72 || categoryId == 73 || categoryId == 78 
						|| categoryId == 79 || categoryId == 80 || categoryId == 81 || categoryId == 82){
					jsonArray.add(annotation);
				}
			}
			jsonObject.add("annotations", jsonArray);
			
			//PrintWriter out = new PrintWriter(new FileOutputStream("annotation/instances_train2014_new.json"));
			PrintWriter out = new PrintWriter(new FileOutputStream("annotation/instances_val2014_new.json")); 
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