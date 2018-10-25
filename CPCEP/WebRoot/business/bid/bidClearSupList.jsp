<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String menuId = request.getParameter("menuId");

%>

<!DOCTYPE HTML>
<html>
<head>
    <title>供应商投标列表页</title>
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/miniui/scripts/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/miniui/scripts/miniui/miniui.js"></script>
    <script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/form.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/login/style_log.css">

    <link rel="stylesheet" type='text/css' href="../css/form/bootstrap-theme.css" />
    <link rel="stylesheet" type='text/css' href="../css/form/bootstrap.flow.min.css" />
    <link rel="stylesheet" type='text/css' href="../css/form/form.css" />
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>

    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

    </style>
</head>
<body>
     <div class="mini-toolbar">
        <a class="mini-button" iconCls="icon-ok" title="确定选择" onclick="onOk">确定</a>
     </div>
     <div class='mini-fit' style='width:100%;height:100%;'>
     <div class="mini-datagrid" id="miniDataGrid" style="width:100%;height:100%" sizeList='[5,10,15,30]' pageSize='15' multiSelect='false'  showColumnsMenu='true' fitColumns='true'>
						<div property='columns'>
				<div type='indexcolumn'></div>
				<div type='checkcolumn'></div>
 				  <div field='supplierName' width='150'  headerAlign='center' align='center'>供应商名称</div>
 				  <div field='name' width='70'  headerAlign='center' align='center'>招标编号</div>
 				  <div field='code' width='70'  headerAlign='center' align='center'>招标名称</div>
 				  <div field='contacts' width='70'  headerAlign='center' align='center'>联络</div>
 				  <div field='phon' width='70'  headerAlign='center' align='center'>联系电话</div>
                  <div field='userId' visible='false'></div>
		    </div>
      </div>
      </div>
      <script type="text/javascript">
      mini.parse();
       var grid =mini.get("miniDataGrid");
       var gDir="${pageContext.request.contextPath}";
       var gType="${param.hasBid}";
       grid.setUrl(gDir + '/business/bid/bidSuppliers.action?hasBid=true&uuid=${param.bidFileId}');
       grid.load();
       function onOk(){
    	   var row = grid.getSelected();
    	   if(row.length==0){
    		   mini.alert("请选择供应商");
    		   return ;
    	   }
    	   window.CloseOwnerWindow(mini.encode(row));
       }
      </script>
</body>
</html>