package htos.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 将一个对象复制到另一个对象上
 * @author zcl
 *
 */
public class CopyUtil {
	
	//将一个对象复制到另一个对象上
	public static void copyToObj(Object dest, Object source)throws IntrospectionException {
		BeanInfo sourceInfo = Introspector.getBeanInfo(source.getClass());
		BeanInfo destInfo = Introspector.getBeanInfo(dest.getClass());
		PropertyDescriptor[] sce = sourceInfo.getPropertyDescriptors();
		PropertyDescriptor[] dec = destInfo.getPropertyDescriptors();
		for (PropertyDescriptor s : sce) {
			Method getMethod = s.getReadMethod();
			if (getMethod != null) {
				try {
					String name = s.getName();
					Object result = getMethod.invoke(source);
					for (PropertyDescriptor d : dec) {
						if (name.equalsIgnoreCase(d.getName()) && result != null && !"".equals(result)) {
							Method setMethod = d.getWriteMethod();
							if (setMethod != null) {
								Object obj = strToObj(s, source, d);
								setMethod.invoke(dest, obj);
							}
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Object strToObj(PropertyDescriptor s, Object source,PropertyDescriptor d) throws Exception {
		Object obj = null;
		Method getMethod = s.getReadMethod();
		String str =  getMethod.invoke(source).toString();
		if (Date.class.equals(d.getPropertyType())) {
			SimpleDateFormat sdf = null;
			if (s.getName().contains("Date")) {
				sdf = new SimpleDateFormat("yyyyMMdd");
				Date date = (Date) getMethod.invoke(source);
				String strdate = sdf.format(date);
				obj = sdf.parse(strdate);
			} else if (s.getName().contains("Time")) {
				sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = (Date) getMethod.invoke(source);
				String strdate = sdf.format(date);
				obj = sdf.parse(strdate);
			}
		} else if (Double.class.equals(d.getPropertyType())
				|| double.class.equals(d.getPropertyType())) {
			obj = Double.parseDouble(str);
		}else if (Float.class.equals(d.getPropertyType())
				|| float.class.equals(d.getPropertyType())) {
			obj = Float.parseFloat(str);
		} else if (Integer.class.equals(d.getPropertyType())
				|| int.class.equals(d.getPropertyType())) {
			obj = Integer.parseInt(str);
		} else if (BigDecimal.class.equals(d.getPropertyType())) {
			BigDecimal bObj = new BigDecimal(str);
			obj = bObj.setScale(6, BigDecimal.ROUND_HALF_UP);
		} else if (String.class.equals(d.getPropertyType())) {
			obj = str;
		}
		return obj;
	}
   
}
