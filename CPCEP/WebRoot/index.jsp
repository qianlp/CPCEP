<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#application.firmConfig.cssType==1 ">
	<jsp:include page="/homeTemplate/homeOne/index.jsp"  />
</s:if>
<s:elseif test="#application.firmConfig.cssType==2 ">
	<jsp:include page="/homeTemplate/homeTwo/index.jsp" />
</s:elseif>
<s:elseif test="#application.firmConfig.cssType==3 ">
	<jsp:include page="/homeTemplate/homeThree/index.jsp" />
</s:elseif>
<s:elseif test="#application.firmConfig.cssType==4 ">
	<s:if test="#session.user.userType==2 ">
		<jsp:include page="/homeTemplate/homeFour/supplier.jsp" />
	</s:if>
	<s:else>
		<jsp:include page="/homeTemplate/homeFour/index.jsp" />
	</s:else>
</s:elseif>
<s:else>
	<s:if test="#session.user.userType==2 ">
		<jsp:include page="/homeTemplate/homeFour/supplier.jsp" />
	</s:if>
	<s:else>
		<jsp:include page="/homeTemplate/homeFour/index.jsp" />
	</s:else>
</s:else>
