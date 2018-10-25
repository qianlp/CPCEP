package htos.coresys.aop;

import htos.coresys.entity.User;
import htos.coresys.util.ReflectObjUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Aspect
@Component
public class MyAspectInterceptor extends ActionSupport {
	private static final Logger log = Logger.getLogger(MyAspectInterceptor.class);
	private static final long serialVersionUID = 1L;

	public void beforeSave(JoinPoint jp) {
		Object[] args = jp.getArgs();
		Object obj = null;
		if (args.length > 0) {
			try{
				HttpSession session = ServletActionContext.getRequest().getSession();
				obj = args[0];
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("delFlag", "1");	//添加默认删除标记属性
				try{
					//map.put("createBy", ((User)session.getAttribute("user")).getUserName());	//添加创建人
				}catch(Exception e){
					log.error("=====================切入点前获取用户信息出现异常=========="+e.getMessage());
					System.out.println(e.getMessage());
				}
			//	map.put("createDate", new Date());		//添加创建时间
				ReflectObjUtil.setAttrributesValues(obj, map);
			}catch(Exception e){
				log.error("=====================切入点前出现异常(原因：可能是后台执行定时任务时为在Struts中proxy,导致无法获取session出现的异常)=========="+e.getMessage());
				System.out.println(e.getMessage());
			}
		}
		//System.out.println("在切面类方法[前]执行的");
	}

	public void after(JoinPoint jp) {
		Object[] args = jp.getArgs();
		for (int i = 0; i < args.length; i++) {
			log.info("=====================切入点后after:=========="+ args[i].getClass().getName());
			System.out.println("after:" + args[i].getClass().getName());
		}
		//System.out.println("在切面类方法[后]执行的");
	}

}