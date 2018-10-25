<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:action name="loadFirmConfig" namespace="/admin" executeResult="true"></s:action>
<s:if test="#application.firmConfig.cssType==1 ">
	<jsp:include page="/homeTemplate/homeOne/login.jsp" />
</s:if>
<s:elseif test="#application.firmConfig.cssType==2 ">
	<jsp:include page="/homeTemplate/homeTwo/login.jsp" />
</s:elseif>
<s:elseif test="#application.firmConfig.cssType==3 ">
	<jsp:include page="/homeTemplate/homeThree/login.jsp" />
</s:elseif>
<s:elseif test="#application.firmConfig.cssType==4 ">
	<jsp:include page="/homeTemplate/homeFour/login.jsp" />
</s:elseif>
<s:else>
	<jsp:include page="/homeTemplate/homeFour/login.jsp" />
</s:else>