<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>借阅申请-项目文件列表</title>
		<meta name="description" content="借阅申请-项目文件列表">
		<meta name="content-type" content="text/html; charset=UTF-8">
		<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
		<style type="text/css">
			html,body{width:100%;height:100%;border:0;margin:0;padding:0;overflow:visible;}
			.search_body{border:0px solid #000;overflow:hidden}
			.search_content,.search_title,.search_field,.search_button{float:left}
			.search_content{width:200px;margin:2px;height:30px;}
			.search_title{width:70px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px}
			.search_field{width:130px;text-align:left}
			.search_button{margin:1px;}
			.search_field .mini-radiobuttonlist{padding-top:3px}
		</style>	
	</head>
	<body>
		<!--查询部分-->
	<div class="search_body"><form id="search_form"></form></div>
		<!--功能按钮-->
	<div class='mini-toolbar' style='padding:0;border-bottom-width:0;border:0px;'>
		<table style='width:100%;'>
		<tr>
            		<td style='width:100%;white-space:nowrap;' id='leftGridToolBar'>
            			<a class="mini-button mini-button-plain" href="javascript:onOk();" title="确定选择">
            				<span class="mini-button-text  mini-button-icon icon-ok" >确定</span>
            			</a>
            		</td>
            		<td style='white-space:nowrap;' id='rightGridToolBar'>
                		<a class='mini-button' iconCls='icon-reload' plain='true' onclick='RefreshGridData()'>刷新</a>
					</td>
            	</tr>
		</table>
	</div>
	<!--撑满页面-->
	<div class='mini-fit' style='width:100%;height:100%;'>
		<div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;' idField='unid' sizeList='[15,30,50,100]' pageSize='15'  showColumnsMenu='true' multiSelect='true'>
			<div property='columns'>
				<div type='indexcolumn'></div>
				<div type='checkcolumn'></div>
            	<div field='profilename' headerAlign='left' width='120' align="left">借阅文档名称</div>
                <div field='catalogName' headerAlign='left' width='120' align="left">借阅文档目录</div>
				<div field='cuNo'  headerAlign='left' width='120' align="left">纸质文档编号</div>
                <div field='uuid' hideable='true'    visible='false' >项目id</div>
				
            </div>
		</div>
	</div>
	<script type='text/javascript'>
		mini.parse();
		var grid = mini.get('miniDataGrid');
		//这里可以重新指定自定义的数据装载路径
		grid.setUrl("${pageContext.request.contextPath}/profile/loadMapListForPageHead.action");
		grid.load();
		
		/*
		描述：刷新整个页面
		*/
		function RefreshGridData(){
			grid.load();
		}
		
		function onOk() {
			var rows=grid.getSelecteds();
			if(0==rows.length){
				mini.alert('请选择数据！');
				return;
			}
			window.CloseOwnerWindow(mini.encode(rows));
		}
	</script>
	</body>
</html>
