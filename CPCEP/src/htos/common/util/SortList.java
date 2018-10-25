package htos.common.util;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortList<ViewConfirmBid> {
   public void Sort(List<ViewConfirmBid> list,final String method,final String sort){
	   Collections.sort(list, new Comparator<Object>() {
			public int compare(Object a, Object b) {
				int ret = 0;
				try {
					Method m1 = ((ViewConfirmBid) a).getClass().getMethod(method, null);
					Method m2 = ((ViewConfirmBid) b).getClass().getMethod(method, null);
					if (sort != null && "desc".equals(sort))// 倒序
						ret = new BigDecimal(m2.invoke(((ViewConfirmBid) b), null).toString())
								.compareTo(new BigDecimal(m1.invoke(((ViewConfirmBid) a), null).toString()));
				} catch (NoSuchMethodException ne) {
					System.out.println(ne);
				} catch (IllegalAccessException ie) {
					System.out.println(ie);
				} catch (InvocationTargetException it) {
					System.out.println(it);
				}
				return ret;
			}
		});
	}

}
