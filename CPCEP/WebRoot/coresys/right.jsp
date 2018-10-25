<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>权限管理</title>
<meta name="description" content="组织机构管理">
<script src="${pageContext.request.contextPath}/js/miniui/scripts/bootView.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 1;
	width: 100%;
	height: 100%;
	overflow: hidden;
	width:calc(100% - 2px);
}
/*权限复选框样式*/
.rTree {
	background-repeat: no-repeat;
	background-position: 0 1px;
	width: 16px;
	height: 18px;
	display: inline-block;
	overflow: hidden;
	vertical-align: middle;
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
	-khtml-user-select: none;
	user-select: none;
}

.rCheckBoxText {
	height: 20px;
	vertical-align: top;
	overflow: hidden;
	display: inline-block;
}

.rNoUnChecked {
	background-image: url(../img/right/no_unchecked.gif);
}

.rNoChecked {
	background-image: url(../img/right/no_checked.gif);
}

.rChecked {
	background-image: url(../img/right/checked.gif);
}

.rUnChecked {
	background-image: url(../img/right/unchecked.gif);
}

.mini-layout-region{
	border-top:0px;
}
.mini-splitter-border{
	box-sizing: border-box;
}
</style>
<script type="text/javascript">
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
	
	var gRootId = "",gActivedMenuTabName="";
	function goMenuTabChange(e) {
		var oCurTab = e.tab;
		
		gRootId = oCurTab.name;
		//已激活的菜单页签名称
		gActivedMenuTabName=oCurTab.name;
		var oMenuTree = mini.get(gActivedMenuTabName);
		if(oMenuTree.getList().length<1){
			loadMenuTree(oMenuTree);
		}else{
			//如果已经在给对应分配权限
			if(gAssigner!=""){
				var oNode=gActivedNavTree.getSelectedNode();
				if(typeof oNode!="undefined"){
					goArragneRight(oNode,false)
				}
			}
		}
		//gOldAssigner = "";
	}
	
	function loadMenuTree(oMenuTree){
		var strPath = "";
		strPath = "${pageContext.request.contextPath}/admin/findMenuTreeNodeList.action";
		if(gCurNodeId!="" && gCurNodeId!="-1"){
			strPath = "${pageContext.request.contextPath}/admin/findMenuTreeNodeListById.action";
		}
		
		$.post(strPath, {
			"menu.menuRootId" : gActivedMenuTabName,
			"menu.uuid":gCurNodeId
		}, function(data) {
			var list = mini.decode(data);
			if(gCurNodeId!=""){
				var pNode=oMenuTree.getNode(gCurNodeId);
				if(!pNode){
					gCurNodeId="";
					loadMenuTree(oMenuTree);
				}
				list=mini.arrayToTree(list,"children","id","pid");
				oMenuTree.removeNodes(pNode.children);
				oMenuTree.addNodes(list[0].children,pNode);
				gCurNodeId="";
			}else{
				oMenuTree.loadList(list, "id", "pid");
			}
			
			//如果已经在给对应分配权限
			if(gAssigner!=""){
				var oNode=gActivedNavTree.getSelectedNode();
				if(typeof oNode!="undefined"){
					goArragneRight(oNode,false)
				}
			}
		});
	}
	
	function goDrawNode(e) {
		var tree = e.sender;
		var node = e.node;
		var strClassName="";
		switch(node.status){
			case "0":
				strClassName=("rTree rNoUnChecked");
				break;
			case "1":
				strClassName=("rTree rUnChecked");
				break;
			case "2":
				strClassName=("rTree rChecked");
				break;
			default:
				strClassName=("rTree rNoChecked");
		}
		e.nodeHtml = '<span id="' + node.id + '" onclick=\'goCheckBox()\' class="'+strClassName+'"></span><span class="rCheckBoxText">' + node.text + '</span>';
	}

	var gCurNodeId="";
	function goMenu(e) {
		var strURL = "", strTitle = "", pid = "", tabId = "", intWidth = 700, intHeight = 500;
		if (typeof (e.node) == "undefined") {
			var oMenuTree = mini.get(gActivedMenuTabName);
			var node = oMenuTree.getSelectedNode();
			if (node) {
				gCurNodeId=node.id;
				pid = node.id;
				tabId = node.rootId;
			}
			
			strTitle = "\u65B0\u5EFA";
			strURL = "${pageContext.request.contextPath}/coresys/menu.jsp?pid=" + pid + "&rootId=" + tabId;
		} else {
			intHeight = 500;
			gCurNodeId=e.node.pid;
			strDocID = e.node.id;
			strTitle = "\u7EF4\u62A4";
			if(e.node.type=="Menu"){
				strURL = "${pageContext.request.contextPath}/admin/findMenuById.action?menu.uuid=" + strDocID;
			}else{
				strURL = "${pageContext.request.contextPath}/admin/findMenuBtnById.action?menuToBtn.uuid=" + strDocID;
			}
		}
		var oWinDlg = mini.get('oWinDlg');
		if (oWinDlg == null) {
			oWinDlg = mini.open({
				id : "oWinDlg",
				showMaxButton : true,
				headerStyle : "font-weight:bold;letter-spacing:4px",
				footerStyle : "padding:5px;height:25px"
			});
		}
		oWinDlg.setUrl(strURL);
		oWinDlg.setTitle(strTitle);
		oWinDlg.setWidth(intWidth);
		oWinDlg.setHeight(intHeight);
		oWinDlg.showAtPos("center", "middle");
	}
	
	/*
	描述：双击重新安排节点权限
	*/
	var gAssigner = "",gOldAssigner = "",gType="";
	function goArragneRight(oNode,bTab){
		var isDept=oNode.type;
		if(isDept=="dept"){
			//alert("不能对部门进行权限的分配！");
			//return
		}
		gType=isDept=="role"?"3":(isDept=="dept"?"1":"2");
		gAssigner = oNode.id;
		if(gOldAssigner==gAssigner&&bTab){return}
		gOldAssigner=gAssigner;
		var strPath="${pageContext.request.contextPath}/admin/getMenuRight.action";
		var oMenuTree=mini.get(gActivedMenuTabName);
		var arrAllNode=oMenuTree.getList();
		var arrTreeIds=[];
		var intM=0,intL=arrAllNode.length;
		for(;intM<intL;intM++){
			arrTreeIds.push("$"+arrAllNode[intM]["id"]+"$");
			oMenuTree.updateNode(arrAllNode[intM],{status:"1"});
		}
		$("#divAssigner").html("您正在给 &lt; <b style='color:blue'>" + (isDept=="role"?"角色":"个人") + "</b> ：<b style='color:red'>"+oNode.text+"</b> &gt; 进行权限分配。")
		$.post(strPath, {
			"type" : gType,
			"orgId" : gAssigner,
			"treeDocIds" : arrTreeIds.join("^")
		}, function(data) {
			//console.log(data);
			if(data!=""){
				//设置菜单权限状态
				goSetCheckBox(mini.decode(data))
			}
		});
	}
	/*
		描述：Ajax获得对应权限后，重新进行更新
	*/
	//已拥有选中的权限节点ID，用于保存时排除这些已拥有选中权限，只保存发生变化的节点，提高保存效率
	var gCheckedNode=[];
	function goSetCheckBox(oRightNode){
		oRightNode=oRightNode[0];
		var oMenuTree=mini.get(gActivedMenuTabName);
		var arrAllNode=oMenuTree.getList();
		var intM=0,intL=arrAllNode.length,oStatus=null;
		//清空原先被选中的节点
		gCheckedNode=[];
		for(;intM<intL;intM++){
			//节点是否请求过权限
			//status=0：失效未选中；1：有效未选中；2：有效已选中；3：失效已继承
			//不等于undefined时，表示该节点有权限
			oStatus=oRightNode[arrAllNode[intM].id];
			if(typeof(oStatus)!="undefined"){
				//节点前后状态不一致时，修改节点状态为"未选择"
				if(oStatus!=arrAllNode[intM].status){
					oMenuTree.updateNode(arrAllNode[intM],{status:oStatus});
				}
				if(oStatus==2){
					gCheckedNode.push(arrAllNode[intM].id);
				}
			}else{
				//如果节点状态为"已选择"，或无效选择状态时，修改节点状态为"未选择"
				if(parseInt(arrAllNode[intM].status,10)>1){
					oMenuTree.updateNode(arrAllNode[intM],{status:"1"});
				}
			}
		}
	}
	
	/*
	描述：改变复选框状态
	*/
	function goCheckBox(){
		//树对象
		var oMenuTree=mini.get(gActivedMenuTabName);
		//当前选中节点
		var oNode=oMenuTree.getSelectedNode();
		if(gAssigner==""){alert("请双击最左侧的对象进行权限分配！");return}
		//当状态为未选中时，点击后就变为选中。即为1变为2。1为未选中，2为已选中
		if(oNode.status=="1"){
			oMenuTree.updateNode(oNode,{status:"2"});
		}else if(oNode.status=="2"){
			oMenuTree.updateNode(oNode,{status:"1"});
		}
		//所有父节点不含自身
		var arrParent=oMenuTree.getAncestors(oNode),arrChildNode=[];
		//1、父节点对象。2、复选框对象
		var oN=null,intCheckBox=0;
		//1、当前节点状态。2、是否继续检查继承状态
		var strStatus=oNode.status,bCheck=true;
		for(var intM=0,intL=arrParent.length;intL>intM;intL--){
			oN=arrParent[intL-1];
			if(strStatus!="3"&&oN.status!="3"){
				//未选中
				if(strStatus=="1"){
					//1、已选中个数；2、该节点的孩子数（不含子孙）
					intCheckBox=0;arrChildNode=oMenuTree.getChildNodes(oN);
					for(var intQ=0,intS=arrChildNode.length;intQ<intS;intQ++){
						if($("#"+arrChildNode[intQ]["id"],"#"+gActivedMenuTabName).hasClass("rChecked")){
							intCheckBox++
						}
					}
					//当本节点状态变为未选中时，如果父节点下没有被选中的子节点时，改变状态为未选中
					if(intCheckBox<1){
						oMenuTree.updateNode(oN,{status: strStatus})
					}
				}else{
					//当本节点状态变为已选中时，如果父节点下就一个已选中子节点时（说明原先父节点下就没有被选中的子节点），改变状态为已选中。
					oMenuTree.updateNode(oN,{status: strStatus})
				}
			}else{
				break;
			}
		}
		//改变所有子孙状态
		oMenuTree.cascadeChild(oNode,function(node){
			//当父节点为继承状态，就继续找子节点的状态，直到不为继承状态为止
			if(strStatus=="3"&&bCheck){
				if(node.status=="1"){
					strStatus="2";
					bCheck=false;
				}else if(node.status=="2"){
					strStatus="1";
					bCheck=false;
				}
			}
			if(node.status=="1"||node.status=="2"){
				oMenuTree.updateNode(node,{status: strStatus});
			}
		});
	}
</script>
</head>
<body>
	<div class="mini-splitter" style="width:100%;height:100%;" vertical="true" allowResize="false">
		<div size="60" showCollapseButton="true" class="header" visible="false">
			<h1 style="margin:0;padding:15px;font-family:微软雅黑,黑体,宋体;">权限管理</h1>
			<div style="position:absolute;top:35px;left:180px;font-size:12px">版本:V1.0</div>
		</div>
		<div showCollapseButton="false" style="border:0">
			<div class="mini-layout" style="width:100%;height:100%;" id="layout">
				<div region="west" style="overflow:hidden;border-bottom:0" showHeader="false" width="200px" minWidth="200" maxWidth="350" showSplitIcon="true">
					<!--<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<table style="width:100%" cellspacing=0 cellpadding=0>
							<tr>
								<td style="width:100%;text-align:center"><input id="key" class='mini-textbox' style="width:98%" onenter="Search" /></td>
								<td style="white-space:nowrap;padding-left:2px"><a class='mini-button' iconCls='icon-search' plain='true' style="width:60px" onclick="Search">查询</a>
								</td>
							</tr>
						</table>
					</div>-->
					<div class="mini-fit" style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<div id="OrgTabs" class="mini-tabs" style="height:100%;" plain="false" onactivechanged="goOrgTabChange">
							<div title="组织机构" name='OrgTree'>
								<div id='OrgTree' class='mini-tree' style="height:100%;" showTreeIcon='true' textField="text" resultAsTree='false' showCheckBox='false' checkRecursive='true' expandOnLoad='0' expandOnDblClick='false'></div>
							</div>
							<div title="角色" name='RoleTree'>
								<!-- allowSelect='true' enableHotTrack='false' -->
								<div id='RoleTree' class='mini-tree' style="height:100%;" showTreeIcon='true' textField="text" resultAsTree='false' showCheckBox='true' checkRecursive='true' expandOnLoad='0' expandOnDblClick='false'></div>
							</div>
						</div>
					</div>
				</div>

				<div region="center" style="overflow:hidden;border-bottom:0;border-right:1">

					<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<a class="mini-button" iconCls="icon-tip" plain="true"></a>
						<span id="divAssigner" style="display:inline-block">
							无分配对象，双击 &lt; <b>左侧&nbsp;组织机构或角色</b> &gt; 进行分配。
						</span>
						<span class='separator'></span>
						<a id="barAddMenu" class="mini-button" iconCls="icon-add" plain="true" onclick="goMenu">添加菜单</a>
						<a class="mini-button" iconCls="icon-save" plain="true" onclick="goSave" visible="true">保存权限</a>
						<a class="mini-button" iconCls="icon-reload" plain="true" onclick="window.location.reload()">刷新</a>
					</div>

					<div class="mini-fit" style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<div id="tabMenu" class="mini-tabs" tabPosition="top" style="height:100%;" plain="false" onactivechanged="goMenuTabChange">
							<c:if test="${not empty menuList}">
								<c:forEach items="${menuList}" var="menu" varStatus="index">
									<div title='${menu.menuName}' name='${menu.menuRootId}' iconCls='icon-folder'>
										<div id='${menu.menuRootId}' class='mini-tree' resultAsTree='false' showCheckBox='false' expandOnLoad="true" checkRecursive='false' contextMenu='#treeMenu' expandOnDblClick='false'
											ondrawnode='goDrawNode' autoCheckParent='false' onnodedblclick='goMenu' imgPath='${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/icons/'></div>
									</div>
								</c:forEach>
							</c:if>
							<!--
							<div title='个人办公' name='个人办公' iconCls='icon-folder'>
								<ul id='MenuTree个人办公' class='mini-tree' resultAsTree='false' showCheckBox='false' checkRecursive='false' contextMenu='#treeMenu' expandOnDblClick='false' ondrawnode='goDrawNode'
									autoCheckParent='false' onnodedblclick='goMenu' imgPath='${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/icons/'></ul>
							</div>
							-->
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
<ul id="treeMenu" class="mini-contextmenu">
	<li onclick="onSelectNode">选中</li>
	<li onclick="onClearNode">取消</li>
</ul>
	<script type="text/javascript">
		mini.parse();
		//用于右键菜单选中节点
		function onSelectNode(e){
			var oTree = mini.get(gActivedMenuTabName);
			var node = oTree.getSelectedNode();
			oTree.updateNode(node,{status:2});
 		}
		//用于右键菜单不选中节点
		function onClearNode(e){
			var oTree = mini.get(gActivedMenuTabName);
			var node = oTree.getSelectedNode();
			oTree.updateNode(node,{status:1});
 		}
		/*描述：保存权限*/
		function goSave(){
			if(gAssigner==""){mini.alert("请先选择需要分配的对象!","操作提示");return};
			if(gActivedNavTree.getId()=="RoleTree"){
				var nodes=gActivedNavTree.getCheckedNodes();
				$.each(nodes,function(){
					if(gAssigner!=""){gAssigner+=",";}
					gAssigner+=this.id;
				})
			}
			
			var msgId=mini.loading("权限保存中...","数据提交");
			var arrNoteID = [];
			//所有节点
			//var oTabs = mini.get("tabMenu");
			//var oTab = oTabs.getActiveTab();
			var oMenuTree=mini.get(gActivedMenuTabName);
			var arrAllNode=oMenuTree.getList();
			var intM=0,intL=arrAllNode.length;
			for(;intM<intL;intM++){
				var oNode=arrAllNode[intM];
				//节点在“已拥有权限节点”数组中
				if($.inArray(oNode.id,gCheckedNode)>-1){
					//当节点的状态变为“未选中”时，说明已取消权限
					if(oNode.status=="1"){
						arrNoteID.push(oNode.id+"-1-"+oNode.type)
					}
				}else{
					//当节点的状态变为“已选中”时，说明已拥有权限
					if(oNode.status=="2"){
						arrNoteID.push(oNode.id+"-2-"+oNode.type)
					}
				}
			}
			var PathUrl="${pageContext.request.contextPath}/admin/saveMenuRight.action";
			$.ajax({
				url:encodeURI(PathUrl),
				type:"post",
				dataType:"text",
				data:{
					"type" : gType,
					"orgId" : gAssigner,
					"treeDocIds": arrNoteID.join("^")
				},
				success: function(objHTML){
					if(objHTML==""){
						setTimeout(function(){mini.hideMessageBox(msgId);mini.alert("保存成功!","消息提示");},500);
					}
				}
			});
		}
		//已激活的左侧分配对象树
		var gActivedNavTree=null;
		/*
		描述：组织页签切换时触发
		*/
		function goOrgTabChange(e){
			var oTabs=e.sender,oCurTab=e.tab;
			var strTabName=oCurTab.name;
			gActivedNavTree=mini.get(strTabName);
			
			var strPath="${pageContext.request.contextPath}/admin/";
			if(strTabName=="RoleTree"){
				strPath+="findMenuRoleJson.action";
			}else{
				strPath+="findOrgTree.action";		
			}
			if(gActivedNavTree.getList().length<1){
				$.ajax({
					url : strPath,
					cache : false,
					dataType : "text",
					success : function(data) {
						var orgData = mini.decode(data);
						if (orgData) {
							gActivedNavTree.loadList(orgData, "id", "pid");
							gActivedNavTree.expandLevel(0);
						} else {
							gActivedNavTree.loadList([], "id", "pid");
						}
						gActivedNavTree.on("nodeclick",function(e){
							goArragneRight(e.node,true);
						});
					}
				});
			}
		}
		/*
		描述:关闭窗口
		 */
		var isBigRef=false;
		function goCloseDlg(name) {
			var oWinDlg = mini.get(name);
			oWinDlg.setUrl("about:blank");
			$(oWinDlg.getBodyEl()).html("");
			$(oWinDlg.getFooterEl()).html("");
			oWinDlg.hide();
			
			if(isBigRef){
				window.location.reload();
			}else{
				if(gActivedMenuTabName!=""){
					var oMenuTree = mini.get(gActivedMenuTabName);
					loadMenuTree(oMenuTree);
				}
			
			}
			
			
		}
		/*
		描述:保存或更新后刷新页面
		 */
		function goReload() {
			mini.alert("保存成功!","消息提示",function(){
				goCloseDlg("oWinDlg");
			});
	//		setTimeout(goCloseDlg("oWinDlg"),1500);
		}
	</script>
</body>
</html>
