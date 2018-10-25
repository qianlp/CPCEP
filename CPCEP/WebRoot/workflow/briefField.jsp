<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div style="display:none">
	<input name="uuid" class="mini-textbox" value="${comObj.uuid}" />
	<input name="menuId" class="mini-textbox" value="${menu.uuid}" />
	
	<s:if test='comObj==null'> 
		<input name="createBy" value="${session.user.userName}" class="mini-textbox" style="width:99%;" title="编制人">
		<input name="createDate" class="mini-datepicker" value="<%=(new java.util.Date()).toLocaleString()%>" style="width:99%;" title="编制时间">
	</s:if>
	<s:else>
		<input name="createBy" value="${comObj.createBy}" class="mini-textbox" style="width:99%;" title="编制人">
		<input name="createDate" class="mini-datepicker" value="${comObj.createDate}" style="width:99%;" title="编制时间">
	</s:else>
</div>
<script>
	var gDir="${pageContext.request.contextPath}";
	var menuId="${param.menuId}";
	var gLoginUser="${sessionScope.user.userName}";
	var gIsRead=${isRead};
	var gIsFile=${menu.menuOpenStyle};
</script>				
	