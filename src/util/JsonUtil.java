package util;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
public class JsonUtil {
    public static String Json(Object object){
        return JSONSerializer.toJSON(object).toString();
    }
    public static JSONArray JsonList(Object object){
        List list = new ArrayList();
        list.add(object);
        JSONArray jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray);
        return jsonArray;
    }


}
