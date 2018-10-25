<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>文件权限配置</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="../resource.jsp" %>
	<style>
	html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
	.mini-splitter-border{
		border:0px;
	}
	.mini-layout-proxy-north{
		border-top:0px;
		border-left:0px;
	}
	.mini-layout-proxy-west{
		border-left:0px;
	}
	.mini-layout-region-center{
		border:0px;
	}
	.toolbar{
		height:26px;border-top:0px;border-left:0px;border-right:0px;font-weight:bold;color:#444;width:100%;
	}
	.mini-grid-border{
		border-top:0px;
		border-left:0px;
		border-right:0px;
	}
	body .search_body{border:0px solid #000;overflow:hidden;display:block;padding-top:5px;}
	.search_content,.search_title,.search_field,.search_button{float:left}
	.search_content{width:240px;margin:2px;height:30px;}
	.search_title{width:90px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px}
	.search_field{width:130px;text-align:left}
	.search_button{margin:1px;}
	.search_field .mini-radiobuttonlist{padding-top:3px}
	.mini-grid-emptyText{
		font-size:15px;
		color:#ccc;
		height:200px;
		text-align:center;
	}
	</style>
	<script>
	var menuId="${param.menuId}";
	</script>
</head>
<body text="#000000" bgcolor="#FFFFFF"  style='padding:0px;width:100%;height:100%;background:#f3f3f3;'>
<div id="layout1" class="mini-layout" style="width:100%;height:100%;"  borderStyle="border:solid 1px #aaa;">
    <div title="查询条件" region="north" showHeader="false" height="40" expanded="true" showCollapseButton="false" showSplitIcon="false" >
		<jsp:include page="../promanage/projectSearch.jsp" />
    </div>
    <div title="项目列表" showProxyText="true" showHeader="true" region="west" width="370" showCollapseButton="false" showSplitIcon="true">
        <div class='mini-fit' style='width:100%;height:100%;'>
		<div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;' showPageInfo="false" 
			idField='unid' sizeList='[50,100,200,400]' pageSize='50'  onrowclick="rowClick"
			multiSelect='true' showColumnsMenu='true' fitColumns='false' contextMenu="#gridMenu">
			<div property='columns'>
				<div type='indexcolumn' width="50">.No</div>
                		<div field='projectNo'    width='150' headerAlign='left' align='left'>项目编号</div>
                		<div field='projectName'  width='150' headerAlign='left' align='left'>项目名称</div>
				<div field='CurDocID' width='120' visible="false"></div>
            </div>
		</div>
	</div>
    </div>
    <div title="项目目录文件权限" showHeader="false" region="center"  >
	<div id="meetTab" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;border:0px;" plain="false">
		<div title="项目目录文件权限配置" ><div class="mini-grid-emptyText" style="padding-top:20%;">请点击左侧的项目分配文件目录及文件权限</div></div>
	</div>
    </div>
</div>
<script>
mini.parse();
var grid = mini.get('miniDataGrid');
var tabs=mini.get("meetTab");
grid.setUrl('${pageContext.request.contextPath}/admin/loadListForPage.action');
grid.load({menuId:"${param.menuId}"});

function rowClick(e){
	tabs.loadTab("${pageContext.request.contextPath}/file/filePrjListView.jsp?prjID="+e.record.uuid,tabs.getActiveTab());
}
</script>
</body>
</html>
