<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div style="display:none">
	<input name="uuid"  value="${comObj.uuid}" />
	<input name="menuId"  value="${menu.uuid}" />
	<input name="userId"  value="${session.user.uuid}" />
	<input name="curPosNo"  value="${session.user.posNo}" />
	<input name="curPosName"  value="${session.user.posName}" />
	
	
	<s:if test='comObj==null'> 
		<input name="catalogId"  value="${requestScope.catalogId}" />
		<input name="curDocId"  value="${requestScope.curDocId}" />
		<input name="createBy" value="${session.user.userName}"  style="width:99%;" title="编制人">
		<input name="createDate" class="mini-datepicker" value="<%=(new java.util.Date()).toLocaleString()%>"  style="width:99%;" title="编制时间">
		<input name="docBusId"  value="${requestScope.docBusId}" />
		<input name="posNo"  value="${session.user.posNo}" />
		<input name="posName"  value="${session.user.posName}" />
	</s:if>
	<s:else>
		<s:if test="menu.menuIsLook==1">
			<s:if test="comObj.catalogId==''">
				<input name="catalogId"  value="${requestScope.catalogId}" />
			</s:if>
			<s:else>
				<input name="catalogId"  value="${comObj.catalogId}" />
			</s:else>
		</s:if>
		<s:if test="menu.menuOpenStyle==1">
			<input name="curDocId"  value="${comObj.curDocId}" />
		</s:if>
		<input name="createBy" value="${comObj.createBy}"  style="width:99%;" title="编制人">
		<input name="createDate" value="${comObj.createDate}" class="mini-datepicker"  style="width:99%;" title="编制时间">
		<input name="posNo"  value="${docInfo.posNo}" />
		<input name="posName"  value="${docInfo.posName}" />
	</s:else>
	<input name="createDeptId" value="${docInfo.createDeptId}" style="width:99%;" >
	<input name="createDeptName" value="${docInfo.createDeptName}" style="width:99%;" >
</div>
<script>
	var gForm=document.forms[0];
	var gDir="${pageContext.request.contextPath}";
	var menuId="${param.menuId}";
	var gLoginUser="${sessionScope.user.userName}";
	var gIsRead=${isRead};
	var gIsFile="${menu.menuOpenStyle}"==""?"0":"${menu.menuOpenStyle}";
	var gUserId="${sessionScope.user.uuid}";
	var gOrgIds="${sessionScope.orgIds}";
	var gServerTime="<%=(new java.util.Date()).toLocaleString()%>";
</script>				
	