<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%@include file="../../resource.jsp" %>
  </head>
  <body>
  <div style="display:none">
    <input name="bidFileId" value="${param.bidFileId}"/>
    <input name="type" value="${param.type}"/>
</div>
      <!--撑满页面-->
      <c:if test="${param.type==1 }">
<div class='mini-fit' style='width:100%;height:100%;'>
	<div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;' idField='unid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true' fitColumns='true'>
		<div property='columns'>
				<div type='indexcolumn'></div>
				<div type='checkcolumn'></div>
                <div field='supplierName' 	width='120' allowSort='true' headerAlign='left'  align='left'>联系人</div>
				<div field='endDate' width='120' allowSort='true' headerAlign='left'  align='left' dateFormat='yyyy-MM-dd HH:mm:ss'>结束时间</div>
			    <div field='description' 	width='120' allowSort='true' headerAlign='left'  align='left'>其他说明</div>
				<div field='uuid' width='50'	visible='false'></div>
	    </div>
	</div>
</div>
</c:if>
    <c:if test="${param.type==2 }">
<div class='mini-fit' style='width:100%;height:100%;'>
	<div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;' idField='unid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true' fitColumns='true'>
		<div property='columns'>
				<div type='indexcolumn'></div>
				<div type='checkcolumn'></div>
                <div field='supplierName' 	width='120' allowSort='true' headerAlign='left'  align='left'>联系人</div>
                <div field='minAmount' width='120' allowSort='true' headerAlign='left'  align='left'	>竞价上限金额</div>
				<div field='endDate' width='120' allowSort='true' headerAlign='left'  align='left' dateFormat='yyyy-MM-dd HH:mm:ss'	>结束时间</div>
				<div field='downExtent' width='120' allowSort='true' headerAlign='left'  align='left'	>下调幅度</div>
			    <div field='description' 	width='120' allowSort='true' headerAlign='left'  align='left'>其他说明</div>
				<div field='uuid' width='50'	visible='false'></div>
	    </div>
	</div>
</div>
</c:if>
<script type="text/javascript">
   var gDir = "${pageContext.request.contextPath}";
   mini.parse();
   var bidFileId=$("[name=bidFileId]").val();
   var type=$("[name=type]").val();
   var grid = mini.get("miniDataGrid");
    grid.setUrl(gDir+'/business/bid/bidClarifySupList.action');
    grid.load({bidFileId:bidFileId,type:type});
 </script>
  </body>
</html>
