<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
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
	<script src="${pageContext.request.contextPath}/js/workflow/js/Client-mini.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/workflow/js/common.js" type="text/javascript"></script>
	<script>
	mini.open=function(E) {
		if (!E) return;
		var C = E.url;
		if (!C) C = "";
		var B = C.split("#"),
			C = B[0];
		if (C && C[O1O1oo]("_winid") == -1) {
			var A = "_winid=" + mini._WindowID;
			if (C[O1O1oo]("?") == -1) C += "?" + A;
			else C += "&" + A;
			if (B[1]) C = C + "#" + B[1]
		}
		E.url = C;
		E.Owner = window;
		var $ = [];

		function _(A) {
			try {
				if (A.mini) $.push(A);
				if (A.parent && A.parent != A) _(A.parent)
			} catch (B) {}
		}
		_(window);
		var D = $[0];
		
		return D["mini"]._doOpen(E)
	}
		function goNodeClick(e){
            var tree = e.sender;
            var node = e.node;
            var isLeaf=e.isLeaf;
            if (isLeaf) {
            	window.location="${pageContext.request.contextPath}/admin/getWorkFlowById.action?wfId="+node.id;
            }
		}
		
		function CopyWF(id){
			var intWidth = 300, intHeight = 500;
			var oWinDlg = mini.get('oWinDlg');
			if (oWinDlg == null) {
				oWinDlg = mini.open({
					id : "oWinDlg",
					showMaxButton : false,
					headerStyle : "font-weight:bold;letter-spacing:4px",
					footerStyle : "padding:5px;height:25px"
				});
			}
			oWinDlg.setUrl("${pageContext.request.contextPath}/workflow/copyWf.jsp?id="+id);
			oWinDlg.setTitle("流程拷贝");
			oWinDlg.setWidth(intWidth);
			oWinDlg.setHeight(intHeight);
			oWinDlg.showAtPos("center", "middle");
		}
		
		function goNewWf(id){
			alert("拷贝成功！");
			window.location="${pageContext.request.contextPath}/admin/getWorkFlowById.action?wfId="+id;
		}
	</script>
	<style>
		.mini-splitter-border{border:0}
	    fieldset{
	    	font-weight:bold;
	    	border:0;
	        border-top:0px solid #aaa;
	    }
	    #wfNodeForm table td,#fmWorkFlow table td{font-size:10pt}
	    .mini-grid-headerCell-inner{line-height:22px;font-size:10pt}
	</style>
</head>
<body oncontextmenu="return true" onselectstart="return true" onload="Application(BasePath+'/config/graph-edit.xml')">
	<div class="mini-splitter" style="width:100%;height:100%;border:0" vertical="true" allowResize="false">
		<div size="40" showCollapseButton="false" class="header">
		<form method="post" action="${pageContext.request.contextPath}/admin/updateWorkFlow.action" id="fmWorkFlow" style="overflow:hidden">
			<div style="display:none">
				<textarea name="workFlow.wfProcessXml" id="WFProcessXML">
					<c:choose>
					   <c:when test="${empty workFlow }">
					   		<Process />
					   </c:when>
					   <c:otherwise>
					    	${workFlow.wfProcessXml }
					    </c:otherwise>
					</c:choose>
				</textarea>
				<textarea name="workFlow.wfGraphXml" id="WFGraphXML">
					<c:choose>
					   <c:when test="${empty workFlow}">
					   		<GraphModel>
							<root>
							<Cell id='0' />
							<Cell id='1' parent='0' /></root>
							</GraphModel>
					   </c:when>
					   <c:otherwise>
					    	${workFlow.wfGraphXml}
					    </c:otherwise>
					</c:choose>				
				</textarea>
				<input name="menuId" id="menuId" type="hidden" value="${workFlow.menu.uuid}">
				<input name="workFlow.uuid" id="wfId" type="hidden" value="${workFlow.uuid}"/>
			</div>
			<table style="width:100%;height:100%"><tr>
			<td valign="middle" width="300px">
				<a class="mini-button" iconCls="icon-tip" plain="true"></a>
				<span id="divAssigner" style="display:inline-block;color:blue;font-weight:bold"></span>
			</td>
			<td>
				<table><tr>
					<td>流程名称：</td><td><input class="mini-textbox" name="workFlow.wfName" id="WFName" style="width:150px" value="${workFlow.wfName}"/></td>
					<td style="padding-left:10px">状态：</td><td><input class="mini-combobox" name="workFlow.wfStatus" id="WFStatus" value="${workFlow.wfStatus}" data="[{id:'0',text:'冻结'},{id:'1',text:'激活'}]" style="width:55px"/></td>
					<td style="padding-left:10px">版本：</td><td><input class="mini-spinner" name="workFlow.wfVersion" id="WFVersion" value="${workFlow.wfVersion}" style="width:40px" minValue="1"/></td>
					<td style="padding-left:10px">
						<a class="mini-button" iconCls="icon-node" onclick="CopyWF('${workFlow.uuid}')">复制</a>
						<a class="mini-button" iconCls="icon-remove" onclick="DelWF('${workFlow.uuid}')">删除</a>
					</td>
				</tr></table>
			</td>
			</tr></table>
		</form>
		</div>
		<div showCollapseButton="false" style="border:0">
			<div class="mini-layout" style="width:100%;height:100%;" id="layout">
				<div region="west" style="overflow:hidden;border-bottom:0" showHeader="false" width="200px" minWidth="200" maxWidth="350" showSplitIcon="true">

					<div class="mini-splitter" style="width:100%;height:100%;border:0" vertical="true" allowResize="false" handlerSize=5>
						<div size="33" showCollapseButton="false" style="border:0">
							<table style="width:100%;height:100%"><tr><td align="center" valign="middle">流程导航</td></tr></table>
						</div>
						<div showCollapseButton="false">
							<div id='wfTree' class='mini-tree'  url='${pageContext.request.contextPath}/admin/findAllWF.action'  showTreeIcon='true' resultAsTree='false' showCheckBox='false' checkRecursive='true' expandOnLoad='0' expandOnDblClick='false' onnodeclick="goNodeClick"></div>
						</div>
					</div>
					
				</div>

				<div region="center" style="overflow:hidden;border-bottom:0;border-right:1">
					<div class="mini-splitter" style="width:100%;height:100%;border:0" vertical="true" allowResize="false" handlerSize=5>
						<div size="33" showCollapseButton="false" style="border:0">
							<table style="width:100%"><tr>
								<td id="basicActions" valign="middle"></td>
								<td id="toolbar" valign="middle" style="padding:0 25px"></td>
								<td id="mainActions" valign="middle"></td>
								<td id="attrWF" valign="middle"></td>
								<td id="divSource" align="right" valign="middle" width="100%" style="padding:0 10px">
									<div id="source" class="mini-checkbox" text="图形源码"></div>
								</td>
							</tr></table>
						</div>
						<div showCollapseButton="false">
							<div id="graph" style="background-image:url(${pageContext.request.contextPath}/js/workflow/images/bgGraph.gif)"></div>
							<textarea id="GraphXML"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	mini.parse();
	var wfTree=mini.get("wfTree");
	wfTree.load({},function(){
		var targetNode=this.getNode($("#menuId").val());
		var arr=[];
		this.bubbleParent (targetNode, function(node){
			arr.push(node.text)
		});
		$("#divAssigner").html(arr.reverse().join("&nbsp;&nbsp;/&nbsp;&nbsp;"));
	});
</script>
</html>