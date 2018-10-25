package htos.coresys.util;

import htos.coresys.entity.Menu;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CommonUtil {
	private static final Logger log = Logger.getLogger(CommonUtil.class);
	public static void toJsonStr(HttpServletResponse response, Object data,final String... param) {
		if (data instanceof List || data instanceof Object[]|| data instanceof Set) {
			CommonUtil.toString(response, JSONArray.fromObject(data,getconfig(param)).toString());
			//System.out.println(JSONArray.fromObject(data,getconfig(param)).toString());
		} else {
			CommonUtil.toString(response, JSONObject.fromObject(data,getconfig(param)).toString());
			//System.out.println(JSONObject.fromObject(data,getconfig(param)).toString());
		}
	}
	
	public static JsonConfig getconfig(final String[] param){
		 //过滤级联字段
	    JsonConfig cfg = new JsonConfig();  
	    //过滤关联，避免死循环net.sf.json.JSONException: java.lang.reflect.InvocationTargetException  
	   cfg.setJsonPropertyFilter(new PropertyFilter(){  
		   public boolean apply(Object source, String name, Object value) {
			   for (int i = 0; i < param.length; i++) {
				   if(name.contains(param[i])){
			             return true;  
			       }
			   }
			   return false;
		   	}  
	     }); 
	    //net.sf.json.JSONException: java.lang.reflect.InvocationTargetException异常  
	 //  cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});

	    //处理日期
	 //   JsonConfig cfg = new JsonConfig();  
	   cfg.registerJsonValueProcessor(java.util.Date.class,new JsonValueProcessor() {
           private final String format="yyyy-MM-dd HH:mm:ss";
           public Object processObjectValue(String key, Object value,JsonConfig arg2){
             if(value==null)
                   return "";
             if (value instanceof java.util.Date) {
                   String str = new SimpleDateFormat(format).format((java.util.Date) value);
                   return str;
             }
                   return value.toString();
           }
           public Object processArrayValue(Object value, JsonConfig arg1){
                      return null;
           }
        });
	   return cfg;  
	}
	
	public static void toString(HttpServletResponse response, Object data) {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(data.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}

	}

	//将定义的属性数据转换成json(定义大写就显示大写)
	public static void toJsonStrData(HttpServletResponse response, Object data){
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();  
			out.print(mapper.writeValueAsString(data));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Element> getXmlChildElements(String xml) {
		Document document=null;
		List<Element> childElements =null;
		try {
			document=DocumentHelper.parseText(xml); 
			//搜索文档中所有div中包含field属性的节点
			childElements = document.selectNodes("//div[@field]");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return childElements;
	}
	
	// 判断对象是否为空
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection<?>) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map<?, ?>) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}
	// 把一个字符串的第一个字母大写  
    public static String upperFirstChar(String str){
    	//String substr = str.substring(0, 1);
    	//Pattern p = Pattern.compile("^[A-Z].*$");
    	//Matcher m = p.matcher(str);
    	//boolean dt=str.matches("^[A-Z].*$");
    	if(Pattern.matches("^[A-Z].*$", str)){
    		log.error("=========属性"+str+"首字母大写不符合java命名规范==========");
    	}
        byte[] items = str.getBytes();  
        items[0] = (byte) ((char) items[0] - 'a' + 'A');  
        return new String(items);
    }  
    public static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Set<String> fieldSet(Menu menu){
    	String bodyHtml = "<root>" + menu.getMenuHtmlBody() + "</root>";
		List<Element> eleList = CommonUtil.getXmlChildElements(bodyHtml);
		Set<String> fieldSet = new HashSet<String>();
		for (Element e : eleList) {
			if (!CommonUtil.isNullOrEmpty(e.attributeValue("field"))) {
				fieldSet.add(e.attributeValue("field"));
			}
		}
		return fieldSet;
    }
}


