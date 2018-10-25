<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>数据展现配置列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style type='text/css'>
body,form {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
body .search_body{border:0px solid #000;overflow:hidden;display:block;padding-top:5px;}
	.search_content,.search_title,.search_field,.search_button{float:left}
	.search_content{width:280px;margin:2px;height:30px;}
	.search_title{width:120px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px}
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
<%@include file="../resource.jsp" %>
<script type="text/javascript">
	var menuId = "${exhibitParamModel.menuId}";
	var modoName = "${exhibitParamModel.entityClsName}";
	var gCurUser = "${session.user.userName}";
	var gPosNo="${session.user.posNo}";
	var gPosName="${session.user.posName}";
	var gDir="${pageContext.request.contextPath}";
	var uuid = "${exhibitParamModel.uuid}";
	function CommonRowLink(cell){
		urlPath=gDir+"/admin/findDocById.action?menuId="+menuId+"&type=read&uuid="+cell.row.uuid+"&modoName="+modoName;
		return '<a href="javascript:CommonOpenDoc(\''+urlPath+'\');">' + cell.value + '</a>';
	}
	
	//刷新页面数据
	function refreshGridData(){
		grid.reload();
	}
	
	function GetDocID(){
		var arrID=$.map(grid.getSelecteds(),function(i){
			return i.uuid;
		});
		return arrID.join("^");
	}
	
		function onOk() {
			var rows=grid.getSelecteds();
			if(0==rows.length){
				mini.alert('请选择数据！');
				return;
			}
			window.CloseOwnerWindow(mini.encode(rows));
		}
	
	/*
		描述：
			用于标识流程状态。
		参数：
			cell：单元格对象
		*/
	function CommonFlowStatus(cell){
		var strColor='0f0',strStatus=cell.value;
		switch(parseInt(cell.value,10)){
			case 0:
				strStatus='草稿';
				strColor='000';
				break;
			case 1:
				var arrData=grid.getRow(cell.rowIndex)['curUser'].split(',');
				if($.inArray(gCurUser,arrData)>-1){
					strColor='f00';
					strStatus='待审批';
				}else{
					strColor='00f';
					strStatus='进行中';
				}
				break;
			case 2:
				strColor='000';
				strStatus='已审批';
				break;
			case 3:
				strColor='22DD48';
				strStatus='已更新';
				break;
			case 4:
				strColor='2BD5D5';
				strStatus='已发送';
				break;
			default:
		}
		return "<span style='color:#"+strColor+"'>" + strStatus + "</span>";
	}
	
	//常规状态定义
	function CommonStatus(cell){
		var strStatus=cell.value;
		var status="";
		if(strStatus=="1"){
			status="保存";
		}else if(strStatus=="2"){
			status="提交";
		}else if(strStatus=="3"){
			status="待启用";
		}else if(strStatus=="4"){
			status="使用中";
		}else if(strStatus=="94"){
			status="已通知";
		}
		else if(strStatus=="95"){
			status="已上传会议成果";
		}
		else if(strStatus=="96"){
			status="已上传";
		}else if(strStatus=="98"){
			status="作废";
		}else if(strStatus=="99"){
			status="删除";
		}else{
			status=strStatus;
		}
		return "<span>" + status + "</span>";
	}
	
</script>
${exhibitParamModel.exhibitHtmlHead}
</head>

<body>
	<!--功能按钮-->
	<jsp:include page="..${exhibitParamModel.pageSearchAddress}" />
	${exhibitParamModel.exhibitHtmlSearch}
	<div class='mini-toolbar'
		style='padding:0;border-bottom-width:0;border:0px;'>
		<table style='width:100%;'>
			<tr>
				<td style='width:100%;white-space:nowrap;' id='leftGridToolBar'>
					<a class="mini-button" onclick="onOk();"
					title="确定选择" iconCls="icon-ok" plain='true'>确定</a>
				</td>
				<td style='white-space:nowrap;' id='rightGridToolBar'>
					<a class='mini-button' iconCls='icon-reload' plain='true'
					onclick="refreshGridData()">刷新</a></td>
			</tr>
		</table>
	</div>
	${exhibitParamModel.exhibitHtmlBody}
</body>

</html>
