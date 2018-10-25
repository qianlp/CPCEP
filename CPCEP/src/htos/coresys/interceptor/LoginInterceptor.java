/**
 * 
 */
/**
 * @author Administrator
 *
 */
package htos.coresys.interceptor;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private String sessionKey="user";  		//session存user对象的key值
	  private String parmKey="withoutAuthentication";  
	  private boolean excluded=false; 
	/** 
     * 这个拦截器的作用是如果是登录，即请求的是LoginAction2，那么就不拦截这个请求，让其通过拦截器，进行登录 
     * 如果请求的是别的Action，若在没有进行登录的情况下，那么拦截器就会起作用，跳转到登录页面，进行登录， 
     * 若是在登录的情况下，则可以通过拦截器，继续下面操作。 
     */  
    @Override  
    public String intercept(ActionInvocation invocation) throws Exception {  
        /** 
         * invocation.getAction()是得到当前访问的Action 
         */
    	
        ActionContext ac=invocation.getInvocationContext();
        Map map = invocation.getInvocationContext().getParameters();
        String parm=(String) map.get(parmKey);
        String actionName =  invocation.getInvocationContext().getName();//获取请求action的名字
        String isLogin="";
        try{
        	if(map.get("isLogin")!=null){
        		String[] p=(String[])map.get("isLogin");
        		if(p.length>0){
        			isLogin=p[0];
        		}
        	}
        }catch(Exception e){}
		if(parm!=null){  
			excluded=parm.toUpperCase().equals("TRUE");  
		}
		if(excluded || "loadFirmConfig".equals(actionName) || "loginApp".equals(actionName) || "yes".equals(isLogin) || "supplierRegisterPage".equals(actionName)){
			return invocation.invoke();
		}

		Map<String,Object> session=ac.getSession();//获得session 
		if(null==session.get(sessionKey)){  
			return "login";  
		}

		return invocation.invoke();  
    }
  
}  