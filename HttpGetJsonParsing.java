import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

class Scratch {

  public static void main(String[] args) {

    final String URL_STRING = "https://raw.githubusercontent.com/arcjsonapi/ApiSampleData/master/api/users";

    try {
      ArrayList<String> input = new ArrayList<>();
      input.add("company.name");
      input.add("IN");
      input.add("Tech Happy Group, Telly Belly, Robert-tecHgIant");

      URL url = new URL(URL_STRING);
      URLConnection urlConnection = url.openConnection();
      Gson gson = new Gson();
      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      JsonArray array = gson.fromJson(br, JsonArray.class);

      Iterator<JsonElement> itr = array.iterator();
      ArrayList<Integer> list = new ArrayList<>();

      while (itr.hasNext()) {
        JsonElement e = itr.next();
        JsonObject obj = e.getAsJsonObject();
        System.out.println(e.toString());
        StringTokenizer tokenizer = new StringTokenizer(input.get(0), ".");
        JsonElement element = null;

        while(tokenizer.hasMoreTokens()) {
          element = obj.get(tokenizer.nextToken());
          if(element.isJsonPrimitive()) {
            break;
          }
          obj = element.getAsJsonObject();
        }
        String fieldToBeMatched = element.getAsString();
        String[] searchStrings = input.get(2).split(",");
        for(String s : searchStrings) {
          if(s.trim().equalsIgnoreCase(fieldToBeMatched)) {
            list.add(e.getAsJsonObject().get("id").getAsInt());
            break;
          }
        }
      }

      System.out.println(list);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}