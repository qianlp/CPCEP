<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="display:none">
	<input name="uuid" name="uuid" value="${comObj.uuid}" />
	<input name="userId"  value="${session.user.uuid}" />
	<input name="curPosNo"  value="${session.user.posNo}" />
	<input name="curPosName"  value="${session.user.posName}" />
	<input name="msgTitle" value="" />
	<input name="isReject" value="" />
	<s:if test='workFlow ==""'>
	 <input name="sss">
	</s:if>
	<s:if test='workFlow==null'>
	  <input name="bbb">
	</s:if>
	<c:if test="${workFlow =='' }">
	  <input name="gggg">
	</c:if>
	<c:choose>
	<c:when test="${empty workFlow}">
	     <input name="curUser" value="${baseWorkFlow.curUser}" />
		<input name="allUser" value="${baseWorkFlow.allUser}" />
		<input name="wfTacheName" value="${baseWorkFlow.wfTacheName}" />
		<input name="wfCurNodeId" value="${baseWorkFlow.wfCurNodeId}" />
		<input name="wfPreNodeId" value="${baseWorkFlow.wfPreNodeId}" />
		<input name="wfPreUser" value="${baseWorkFlow.wfPreUser}" />
		<input name="wfPreUserId" value="${baseWorkFlow.wfPreUserId}" />
		<input name="wfMsgUser" value="${baseWorkFlow.wfMsgUser}" />
		<input name="wfWaitApproval" value="${baseWorkFlow.wfWaitApproval}" />
		<input name="wfFinishApproval" value="${baseWorkFlow.wfFinishApproval}" />
		<input name="wfStatus" value="${baseWorkFlow.wfStatus}" />
		<input name="wfRouterId" value="${baseWorkFlow.wfRouterId}" />
		<input name="wfDocLink" value="${baseWorkFlow.wfDocLink}" />
		<textarea name="wfFlowLogXml" >${baseWorkFlow.wfFlowLogXml}</textarea>
		<textarea name="wfProcessXml" >${baseWorkFlow.wfProcessXml}</textarea>
		<textarea name="wfGraphXml" >${baseWorkFlow.wfGraphXml}</textarea>
		<input name="subTime" value="${baseWorkFlow.subTime}" />
		<input name="subPerson" value="${baseWorkFlow.subPerson}" />
		<input name="approvalOrder" value="${baseWorkFlow.approvalOrder}" />
		<input name="alreadyUser" value="${baseWorkFlow.alreadyUser}" />
		<input name="curReadyUser" value="${baseWorkFlow.curReadyUser}" />
		<input name="fffff">
	</c:when>
	 <c:otherwise>
	 <input name="curUser" value="${session.user.userName}" />
		<input name="allUser" value="" />
		<input name="wfTacheName" value="" />
		<input name="wfCurNodeId" value="" />
		<input name="wfPreNodeId" value="" />
		<input name="wfPreUser" value="" />
		<input name="wfPreUserId" value="" />
		<input name="wfMsgUser" value="" />
		<input name="wfWaitApproval" value="" />
		<input name="wfFinishApproval" value="" />
		<input name="wfStatus" value="0" />
		<input name="wfRouterId" value="" />
		<input name="wfDocLink" value="" />
		<input name="wfInitiator" value="${session.user.uuid}" />
		<textarea name="wfFlowLogXml" ></textarea>
		<textarea name="wfProcessXml" >${workFlow.wfProcessXml}</textarea>
		<textarea name="wfGraphXml" >${workFlow.wfGraphXml}</textarea>
		<input name="subTime" value="" />
		<input name="subPerson" value="" />
		<input name="posNo"  value="${session.user.posNo}" />
		<input name="posName"  value="${session.user.posName}" />
		<input name="approvalOrder" value="0" />
		<input name="alreadyUser" value="" />
		<input name="curReadyUser" value="" />
		<input name="ddd">
	 </c:otherwise>
	</c:choose>
<%-- 	<s:if test='workFlow == null or workFlow eq ""'> --%>
   <%-- <s:if test='workFlow =="" or workFlow == null'>
		<input name="curUser" value="${baseWorkFlow.curUser}" />
		<input name="allUser" value="${baseWorkFlow.allUser}" />
		<input name="wfTacheName" value="${baseWorkFlow.wfTacheName}" />
		<input name="wfCurNodeId" value="${baseWorkFlow.wfCurNodeId}" />
		<input name="wfPreNodeId" value="${baseWorkFlow.wfPreNodeId}" />
		<input name="wfPreUser" value="${baseWorkFlow.wfPreUser}" />
		<input name="wfPreUserId" value="${baseWorkFlow.wfPreUserId}" />
		<input name="wfMsgUser" value="${baseWorkFlow.wfMsgUser}" />
		<input name="wfWaitApproval" value="${baseWorkFlow.wfWaitApproval}" />
		<input name="wfFinishApproval" value="${baseWorkFlow.wfFinishApproval}" />
		<input name="wfStatus" value="${baseWorkFlow.wfStatus}" />
		<input name="wfRouterId" value="${baseWorkFlow.wfRouterId}" />
		<input name="wfDocLink" value="${baseWorkFlow.wfDocLink}" />
		<textarea name="wfFlowLogXml" >${baseWorkFlow.wfFlowLogXml}</textarea>
		<textarea name="wfProcessXml" >${baseWorkFlow.wfProcessXml}</textarea>
		<textarea name="wfGraphXml" >${baseWorkFlow.wfGraphXml}</textarea>
		<input name="subTime" value="${baseWorkFlow.subTime}" />
		<input name="subPerson" value="${baseWorkFlow.subPerson}" />
		<input name="approvalOrder" value="${baseWorkFlow.approvalOrder}" />
		<input name="alreadyUser" value="${baseWorkFlow.alreadyUser}" />
		<input name="curReadyUser" value="${baseWorkFlow.curReadyUser}" />
		<input name="fffff">
	</s:if>
	<s:else>
		<input name="curUser" value="${session.user.userName}" />
		<input name="allUser" value="" />
		<input name="wfTacheName" value="" />
		<input name="wfCurNodeId" value="" />
		<input name="wfPreNodeId" value="" />
		<input name="wfPreUser" value="" />
		<input name="wfPreUserId" value="" />
		<input name="wfMsgUser" value="" />
		<input name="wfWaitApproval" value="" />
		<input name="wfFinishApproval" value="" />
		<input name="wfStatus" value="0" />
		<input name="wfRouterId" value="" />
		<input name="wfDocLink" value="" />
		<input name="wfInitiator" value="${session.user.uuid}" />
		<textarea name="wfFlowLogXml" ></textarea>
		<textarea name="wfProcessXml" >${workFlow.wfProcessXml}</textarea>
		<textarea name="wfGraphXml" >${workFlow.wfGraphXml}</textarea>
		<input name="subTime" value="" />
		<input name="subPerson" value="" />
		<input name="posNo"  value="${session.user.posNo}" />
		<input name="posName"  value="${session.user.posName}" />
		<input name="approvalOrder" value="0" />
		<input name="alreadyUser" value="" />
		<input name="curReadyUser" value="" />
		<input name="ddd">
	</s:else> --%>
	<s:if test='comObj==null'> 
		<input name="catalogId" value="${requestScope.catalogId}" />
		<input name="curDocId" value="${requestScope.curDocId}" />
		<input name="createBy" value="${session.user.userName}" style="width:99%;" >
		<input name="createDate" class="mini-datepicker" value="<%=(new java.util.Date()).toLocaleString()%>" style="width:99%;" >
		<input name="docBusId" value="${requestScope.docBusId}" />
	</s:if>
	<s:else>
		<s:if test="menu.menuIsLook==1">
			<s:if test="comObj.catalogId==''">
				<input name="catalogId" value="${requestScope.catalogId}" />
			</s:if>
			<s:else>
				<input name="catalogId" value="${comObj.catalogId}" />
			</s:else>
		</s:if>
		<s:if test="menu.menuOpenStyle==1">
			<input name="curDocId" value="${comObj.curDocId}" />
		</s:if>
		<input name="createBy" value="${comObj.createBy}" style="width:99%;" >
		<input name="createDate" value="${comObj.createDate}" class="mini-datepicker" style="width:99%;" >
		<input name="posNo"  value="${docInfo.posNo}" />
		<input name="posName"  value="${docInfo.posName}" />
	</s:else>
	<input name="createDeptId" value="${docInfo.createDeptId}" style="width:99%;" >
	<input name="createDeptName" value="${docInfo.createDeptName}" style="width:99%;" >
</div>
<script>
	var gForm=document.forms[0];
	var gWfFlowLogXml='${baseWorkFlow.wfFlowLogXml}'.replace(/\&/g, "&amp;");
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	var gUserId="${sessionScope.user.uuid}";
	var gOrgIds="${sessionScope.orgIds}";
	var gServerTime="<%=(new java.util.Date()).toLocaleString()%>";
	var gIsAppointUser=0;
	 var gIsEditDoc=${isRead};  
     var gIsRead=${isRead}; 
	var gIsReject=0;
	var gIsFile="1";
	 var gIsNewDoc=("${comObj.uuid}"==""?true:false);
	var gWfStatus=parseInt($("[name=wfStatus]").val());
	var gIsShowSign=0;
</script>				
	