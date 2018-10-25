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
<title>流程图形化</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<script
	src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js"
	type="text/javascript"></script>
<script>
		var BasePath = "${pageContext.request.contextPath}/js/workflow";
		var ImageBasePath = BasePath+"/images/";
		var ResourceExtension = ".properties";
		var gbIsCreateNew = true;
		var gDir="${pageContext.request.contextPath}";
		//字段维护
		var gFieldStatusURL="${pageContext.request.contextPath}/admin/getFieldList.action";
		//按钮清单
		var gBtnURL="${pageContext.request.contextPath}/js/workflow/config/btn.html";
</script>
<script	src="${pageContext.request.contextPath}/js/workflow/js/Client-mini.js" type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/js/workflow/js/common.js" type="text/javascript"></script>
<style>
.mini-splitter-border {
	border: 0
}

fieldset {
	font-weight: bold;
	border: 0;
	border-top: 0px solid #aaa;
}

#wfNodeForm table td,#fmWorkFlow table td {
	font-size: 10pt
}

.mini-grid-headerCell-inner {
	line-height: 22px;
	font-size: 10pt
}
</style>
</head>
<body oncontextmenu="return true" onselectstart="return true" onload="Application(BasePath+'/config/graph-edit.xml')">
	<div class="mini-splitter" style="width:100%;height:100%;border:0"
		vertical="true" allowResize="false">
		<div size="40" showCollapseButton="false" class="header">
			<form method="post"
				action="${pageContext.request.contextPath}/admin/addWorkFlow.action"
				id="fmWorkFlow" style="overflow:hidden">
				<div style="display:none">
					<textarea name="workFlow.wfProcessXml" id="WFProcessXML">
						<Process/>
					</textarea>
					<textarea name="workFlow.wfGraphXml" id="WFGraphXML">
					</textarea>
				</div>
				<table style="width:100%;height:100%">
					<tr>
						<td valign="middle" width="300px"><a class="mini-button"
							iconCls="icon-tip" plain="true"></a> <!-- <span id="divAssigner" style="display:inline-block">演示模块&nbsp;/&nbsp;请假管理</span> -->
							<input class="mini-treeselect" name="menuId" id="menuId"
							emptyText="请选择流程类别!"
							url="${pageContext.request.contextPath}/admin/findMenuByHasWF.action"
							required="true" style="width:260px" multiSelect="false"
							onbeforenodeselect="beforenodeselect" allowInput="false"
							showRadioButton="true" showFolderCheckBox="false" /></td>
						<td>
							<table>
								<tr>
									<td>流程名称：</td>
									<td><input class="mini-textbox" name="workFlow.wfName"
										id="WFName" style="width:150px" emptyText="请输入流程名称!"
										required="true" /></td>
									<td style="padding-left:10px">状态：</td>
									<td><input class="mini-combobox" name="workFlow.wfStatus"
										id="WFStatus" value="0" vrequired="true"
										data="[{id:'0',text:'冻结'},{id:'1',text:'激活'}]"
										style="width:55px" /></td>
									<td style="padding-left:10px">版本：</td>
									<td><input class="mini-spinner" name="workFlow.wfVersion"
										id="WFVersion" value=1 style="width:40px" minValue="1" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div showCollapseButton="false" style="border:0">
			<div class="mini-layout" style="width:100%;height:100%;" id="layout">
				<div region="west" style="overflow:hidden;border-bottom:0"
					showHeader="false" width="200px" minWidth="200" maxWidth="350"
					showSplitIcon="true">

					<div class="mini-splitter" style="width:100%;height:100%;border:0"
						vertical="true" allowResize="false" handlerSize=5>
						<div size="33" showCollapseButton="false" style="border:0">
							<table style="width:100%;height:100%">
								<tr>
									<td align="center" valign="middle">流程导航</td>
								</tr>
							</table>
						</div>
						<div showCollapseButton="false">
							<div id='wfTree' class='mini-tree' showTreeIcon='true'
								url="${pageContext.request.contextPath}/admin/findAllWF.action"
								resultAsTree='false' showCheckBox='false' checkRecursive='true'
								expandOnLoad='0' expandOnDblClick='false'
								onnodeclick="goNodeClick"></div>
						</div>
					</div>

				</div>

				<div region="center"
					style="overflow:hidden;border-bottom:0;border-right:1">
					<div class="mini-splitter" style="width:100%;height:100%;border:0"
						vertical="true" allowResize="false" handlerSize=5>
						<div size="33" showCollapseButton="false" style="border:0">
							<table style="width:100%">
								<tr>
									<td id="basicActions" valign="middle"></td>
									<td id="toolbar" valign="middle" style="padding:0 25px"></td>
									<td id="mainActions" valign="middle"></td>
									<td id="attrWF" valign="middle"></td>
									<td id="divSource" align="right" valign="middle" width="100%"
										style="padding:0 10px">
										<div id="source" class="mini-checkbox" text="图形源码"></div>
									</td>
								</tr>
							</table>
						</div>
						<div showCollapseButton="false">
							<div id="graph"
								style="background-image:url(${pageContext.request.contextPath}/js/workflow/images/bgGraph.gif)"></div>
							<textarea id="GraphXML"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		mini.parse();
		function goNodeClick(e){
            var tree = e.sender;
            var node = e.node;
            var isLeaf=e.isLeaf;
            if (isLeaf) {
            	window.location="${pageContext.request.contextPath}/admin/getWorkFlowById.action?wfId="+node.id;
            }
		}
	    function beforenodeselect(e) {
            //禁止选中父节点
            if (e.isLeaf == false) e.cancel = true;
        }
	</script>
</body>
</html>