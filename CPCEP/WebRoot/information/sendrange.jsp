<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String Choice = request.getParameter("choice");
	String nums = request.getParameter("nums");
	if (Choice != null) {
		Choice = new String(Choice.getBytes("iso-8859-1"), "utf-8");
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style>
#split	.mini-splitter-border{
		border:0px;
	}
</style>
<title>选择</title>
<link href="../js/miniui/demo/demo.css" rel="stylesheet"
	type="text/css" />
<script src="../js/miniui/scripts/boot.js" type="text/javascript"></script>
</head>
<body onselectstart='return false' style="margin:0;padding:0px;overflow:hidden">
	<div id="split" class="mini-splitter" style="width:100%;height:415px;">
    	<div size="50%" showCollapseButton="false" style="padding:2px;">
        	 <div class="mini-tabs" id="tabs" onactivechanged='' style="width:100%;height:100%">
				<c:if test="${param.nums=='2'}">
				<div title="选择人员" name="Persons"
					style="width:232px;height:390px;overflow:hidden;" visible="false" >
					<ul id="perTree" class="mini-tree"
						style="width:100%;padding:5px;overflow:hidden;"
						showTreeIcon="true" expandOnNodeClick="true" textField="text"
						idField="id" onnodeclick="AddItem(e,false)"></ul>
				</div>
				</c:if>
				<c:if test="${param.nums=='1'}">
				<div title="选择部门" name="DeptNames"
					style="width:232px;height:390px;overflow:hidden" visible="false" >
					<ul id="deptTree" class="mini-tree"
						style="width:100%;padding:5px;padding-left:2px;overflow:hidden;"
						showTreeIcon="true" textField="text" idField="id" expandOnNodeClick="true"
						onnodeclick="AddItem(e,false)"></ul>
				</div>
				</c:if>
				<c:if test="${param.nums=='3'}">
				<div title="选择角色" name="Roles"
					style="width:232px;height:390px;overflow:hidden" visible="false" >
					<ul id="rolTree" class="mini-tree" style="width:100%;padding:5px;"
						showTreeIcon="true" expandOnNodeClick="true" textField="text"
						idField="id" onnodeclick="AddItem(e,false)"></ul>
				</div>
				</c:if>
			</div>
   	 	</div>
    	<div showCollapseButton="false" style="padding:2px;">
    		<div class="mini-toolbar" style="border-bottom:0px;">
    			已选列表
    		</div>
    		<div class="mini-fit">
    			<div id="listBox" multiSelect="true" class="mini-listbox"
				onitemclick="DelItem"
				style="width:100%;height:100%;padding:5px;"
				textField="text" valueField="id"></div>
    		</div>
        	
    	</div>        
	</div>
	<div class="mini-panel-footer">
		<table cellpadding="0" cellspacing="0"
			style="border:0; width:100%;margin-top: 3px;margin-bottom:3px;">
			<tbody>
				<tr>
					<td style="text-align:left;padding-left:5px;">
						<input id="key" class='mini-textbox'  />
						<a class="mini-button" >查询</a> 
						<a class="mini-button" onclick='loadTree("<%=Choice%>")'>刷新</a>
					</a></td>
					<td style="text-align:right;line-height: 18px;padding-right:5px;">
						<a class="mini-button" onclick="onOk()" id="btnSure">确定</a> 
						<a class="mini-button" onclick="CloseWindow('refresh')">取消</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<script language="javascript">
	$(document).ready(function(){ 
		mini.parse();
	    //类型:P 选择人员,D 选择部门,R 角色,A 自定义组织
	    loadTree("<%=Choice%>");
	});
		mini.parse();
		var sep = ';';//多项分隔符
		var aUrl = '';
		//加载已选
		function loadTree(name) {
			var tabs = mini.get("tabs");
			if (name == "Persons") {
	            var tab = tabs.getTab("Persons");
	            if (tab) {
	                tabs.updateTab(tab, {visible: true});
	            }
	            getJson("perTree","findOrgTree.action");
			} else if (name == "DeptNames") {
	            var tab = tabs.getTab("DeptNames");
	            if (tab) {
	                tabs.updateTab(tab, {visible: true});
	            }
				getJson("deptTree","findAllDeptsJson.action");
			} else if (name == "Roles") {
	            var tab = tabs.getTab("Roles");
	            if (tab) {
	                tabs.updateTab(tab, {visible: true});
	            }
				getJson("rolTree","findAllRoleJson.action");
			} 
		}
		
		function getJson(type,action){
			var tree = mini.get(type);
			var url = encodeURI("${pageContext.request.contextPath}/admin/"+action);
			$.post(url,function(MenuText) {
					var data = mini.decode(MenuText);
					if (MenuText != "{}" && MenuText.indexOf(",") > -1) {
						tree.loadList(data);
					}
				}
			);
		}
		
		//重复检测
		function checkRepeat(txt, lst) {
			var listNodes = lst.getData();
			if (0 == listNodes.length) {
				return false;
			} else {
				for (var i = 0; i < listNodes.length; i++) {
					if (txt == listNodes[i].text) {
						return true;
					}
				}
			}
			return false;
		}
		var gArrListBox = [],ids="";
		var listbox = mini.get("listBox");
		//增加项
		function AddItem(obj, isLeaf) {
			var names = obj.node.text;
			ids = obj.node.id;
			if (sep == "" && listbox.data.length > 0) {
				mini.alert("仅允许选择一项！");
				return;
			}
			if (!isLeaf || (obj.isLeaf && obj.node.isdept != '1')) {
				if (checkRepeat(names, listbox)) {
					mini.alert("'" + names + "' 已存在！");
					return;
				}
				if (obj.node.pid == "-1") {
					//mini.alert("公司名称暂不允许插入！");
					return;
				}
				listbox.addItem({
					id:ids,
					text:names
				});
				gArrListBox.push(ids);
				//alert(mini.decode(mini.get("listBox").getData())[0]["id"]);
			}
		}
		
		function GetData() {
	       // var row = gArrListBox.join(",") ;
	        var row = mini.decode(listbox.getData());
	        return row;
	    }
		
		//删除项
		function DelItem(e) {
			e.sender.removeItem(e.sender.getSelected());
		}
		//关闭当前窗口
		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
		//取消
		function onCancel(e) {
			CloseWindow("cancel");
		}
		//确定
		function onOk() {
	        CloseWindow("ok");
	    }

	</script>
</body>
</html>

