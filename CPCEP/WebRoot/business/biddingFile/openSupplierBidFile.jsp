<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String uuid = request.getParameter("uuid");//supplierBidUuid
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>附件</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="stylesheet" type='text/css' href="../../css/form/loaders.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/bootstrap-theme.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/form.css" />
	<%@include file="../../resource.jsp" %>
</head>
<body text="#000000" bgcolor="#FFFFFF"  style='padding:0px;width:100%;height:100%;background:#f3f3f3;'>
<div class='mini-fit' style='width:100%;height:100%;'>
	<div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;'
	showPager="false">
		<div property='columns'>
			<div type='indexcolumn' headerAlign='center' align ='center'>序号</div>
			<div field='fileName' 	width='80' headerAlign='center'  align='center' renderer="download">文件名称</div>
			<div field='fileId' 	visible='false' >文件Id</div>
		</div>
	</div>
</div>
<script type='text/javascript'>
    mini.parse();
    var grid = mini.get('miniDataGrid');
    //grid.detailUrl = '/business/bid/list.action';
    //这里可以重新指定自定义的数据装载路径
    var gDir="${pageContext.request.contextPath}";
    var uuid = "<%=uuid%>";
    grid.setUrl(gDir+'/business/bid/findSupplierBidFiles.action');
    grid.load({uuid:uuid});
    function download(cell) {
        console.log(cell);
        var url = "${pageContext.request.contextPath}/business/download.action?id=" + cell.row.fileId;
        return "<a href='" + url + "' target='view_window'>" + cell.value + "</a>"
    }
</script>
</body>
</html>
<script>

</script>