<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>流程拷贝</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
	<style>
		body{
			padding:0px;margin:0px;
		}
		.mini-splitter-border{border:0}
	    fieldset{
	    	font-weight:bold;
	    	border:0;
	        border-top:0px solid #aaa;
	    }
	    #wfNodeForm table td,#fmWorkFlow table td{font-size:10pt}
	    .mini-grid-headerCell-inner{line-height:22px;font-size:10pt}
	</style>
	<script>
		function goNodeClick(e){
            var tree = e.sender;
            var node = e.node;
            $("[name=copyId]").val(node.id);
            $("#toolbar1").html("流程拷贝至-"+node.text);
		}
		
		function submit(){
			if(mini.getbyName("wfName").getValue()==""){
				mini.alert("请填写流程名称");
				return;
			}
			if($("[name=copyId]").val()==""){
				mini.alert("请选择要粘贴的菜单");
				return;
			}
			document.forms[0].submit();
		}
	</script>
</head>
<body>
	<form method="post" action="${pageContext.request.contextPath}/admin/copyWorkFlow.action" name="form1" id="form1" >
	<div style="display:none">
		<input name="id"  value="${param.id }"/>
		<input name="copyId" />
	</div>
	<div id="toolbar2" class="mini-toolbar" style="padding:2px;border-top:0px;border-left:0px;border-right:0px;text-align:right;">
		<a class="mini-button" iconCls="icon-ok" onclick="submit()">确认</a>
	</div>
	<div class="mini-fit" >
	<div class="mini-splitter" style="width:100%;height:100%;border:0"
		vertical="true" allowResize="false" handlerSize=5>
		<div size="110" showCollapseButton="false" style="border:0">
			<table style="width:100%;height:100%">
				<tr>
					<td style="text-align:right">流程名称：</td><td><input class="mini-textbox" name="wfName" id="WFName" style="width:150px" value=""/></td>
				</tr>
				<tr>
					<td style="text-align:right">状态：</td><td><input class="mini-combobox" name="wfStatus" id="WFStatus" style="width:150px" value="0" data="[{id:'0',text:'冻结'},{id:'1',text:'激活'}]" style="width:55px"/></td>
				</tr>
				<tr>
					<td style="text-align:right">版本：</td><td><input class="mini-spinner" name="wfVersion" id="WFVersion" value="1" style="width:150px" minValue="1"/></td>
				</tr>
			</table>
		</div>
		<div showCollapseButton="false">
			<div id="toolbar1" class="mini-toolbar" style="padding:2px;border-top:0px;border-left:0px;border-right:0px;">
				流程拷贝至-请选择
			</div>
			<div class="mini-fit" >
				<div id='wfTree' class='mini-tree'
				url='${pageContext.request.contextPath}/admin/findAllWF.action'
				showTreeIcon='true' resultAsTree='false' showCheckBox='false'
				checkRecursive='true' expandOnLoad='0' expandOnDblClick='false'
				onnodeclick="goNodeClick"
				></div>
			</div>
		</div>
	</div>
</div>
</form>
</body>
</html>
