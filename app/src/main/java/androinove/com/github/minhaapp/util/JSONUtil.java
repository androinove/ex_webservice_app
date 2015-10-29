package androinove.com.github.minhaapp.util;

import com.google.gson.Gson;

/**
 * Created by PedroFelipe on 28/10/2015.
 */
public class JSONUtil {

    public static String objectToJson(Object o) {
        return new Gson().toJson(o, o.getClass());
    }

    public static Object jsonToObject(String json, Object o) {
        return new Gson().fromJson(json, o.getClass());
    }

}
