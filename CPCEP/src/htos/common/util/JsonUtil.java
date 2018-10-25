package htos.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JsonUtil<T> {

    //替换字符
    public static String getjsonObject(String jsonStr) {
        //接收{}对象，此处接收数组对象会有异常
        String str = null;
        if (jsonStr.indexOf("[", 0) != -1) {
            //  jsonStr = jsonStr.replaceAll("\\[", "");
            str = jsonStr.substring(1, jsonStr.length());
        } else {
            str = jsonStr;
        }
        if (jsonStr.indexOf("]", jsonStr.length() - 1) != -1) {
            // jsonStr = jsonStr.replaceAll("]", "");
            str = str.substring(0, str.length() - 1);
        } else {
            str = jsonStr;
        }
        System.out.println(str);
        return str;
    }

    //将客户端传递过来的的多个json对象转换jsonList
    @SuppressWarnings("unchecked")
    public static <T> List<T> getJsonList(String strjson, T t) {
        try {
            JSONArray array = JSONArray.fromObject(strjson);//将str转换成json对象
            JsonConfig jsonConfig = new JsonConfig();//参数设置
            jsonConfig.setRootClass(t.getClass());//设置array中的对象类型
            List<T> list = (List<T>) JSONArray.toCollection(array, jsonConfig);//将数组转换成T类型的集合
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过typeMap参数，可以把MorphDynaBean转换成想要的bean
     *
     * @param json
     * @param clazz
     * @param typeMap
     * @return
     */
    public static List jsonArrayToList(String json, Class clazz, Map typeMap) {
        if (clazz == null) {
            return JSONArray.fromObject(json);
        }
        List list = new ArrayList();
        for (Iterator iterator = JSONArray.fromObject(json).iterator(); iterator.hasNext(); ) {
            list.add(trans2Bean(((JSONObject) iterator.next()), clazz, typeMap));
        }
        return list;
    }

    private static Object trans2Bean(JSONObject jsonobject, Class<?> class1, Map<String, ?> map) {
        return JSONObject.toBean(jsonobject, class1, map);
    }


}
