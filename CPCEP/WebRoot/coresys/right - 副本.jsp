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
<meta name="content-type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 1;
	width: 100%;
	height: 100%;
	overflow: hidden;
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

.header {
	background: url(../js/miniui/demo/header.gif) repeat-x 0 -1px;
}
</style>
<script type="text/javascript">
	var gRootId = "",gActivedMenuTab="";
	function goMenuTabChange(e) {
		var oCurTab = e.tab;
		
		gRootId = oCurTab.name;
		gActivedMenuTab=oCurTab.name;
		var menuListTree = mini.get(gActivedMenuTab);
		if(gOldAssigner==gAssigner&&menuListTree.getList().length<1){
			loadMenuTreeNode();
		}
		gOldAssigner = "";
	}
	
	function loadMenuTreeNode(){
		var strPath = "";
		strPath = "${pageContext.request.contextPath}/admin/findMenuTreeNodeList.action";
		$.post(strPath, {
			"menu.menuRootId" : gRootId
		}, function(data) {
			var list = mini.decode(data);
			mini.get("MenuTree" + gRootId).loadList(list, "id", "pid");
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
		e.nodeHtml = '<span id="' + node.id + '" onclick=\'goCheckBox("'+tree.id+'")\' class="'+strClassName+'"></span><span class="rCheckBoxText">' + node.text + '</span>';
	}

	function goMenu(e) {
		var strURL = "", strTitle = "", pid = "", tabId = "", intWidth = 700, intHeight = 500;
		if (typeof (e.node) == "undefined") {
			var menuListTree = mini.get("MenuTree" + gRootId);
			var node = menuListTree.getSelectedNode();
			if (node) {
				pid = node.id;
				tabId = node.rootId;
			}
			strTitle = "\u65B0\u5EFA";
			strURL = "${pageContext.request.contextPath}/coresys/menu.jsp?pid=" + pid + "&rootId=" + tabId;
		} else {
			intHeight = 500;
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
	function goArragneRight(oNode,TreeName){
		var isDept=oNode.type;
		if(isDept=="dept"){
			alert("不能对部门进行权限的分配！");
			return
		}
		gType=isDept=="role"?"3":(isDept=="dept"?"1":"2");
		gAssigner = oNode.id;
		console.log(gOldAssigner+"--"+gAssigner);
		if(gOldAssigner==gAssigner){return}
		gOldAssigner=gAssigner;
		var strPath="${pageContext.request.contextPath}/admin/getRightByOrgId.action";
		var oTree=mini.get(TreeName);
		var arrAllNode=oTree.getList();
		var arrTreeIds=[];
		var intM=0,intL=arrAllNode.length;
		for(;intM<intL;intM++){
			arrTreeIds.push("$"+arrAllNode[intM]["id"]+"$");
			oTree.updateNode(arrAllNode[intM],{status:"1"});
		}
		$("#divAssigner").html("您正在给 &lt; <b style='color:blue'>" + (isDept=="role"?"角色":"个人") + "</b> ：<b style='color:red'>"+oNode.text+"</b> &gt; 进行权限分配。")
		$.post(strPath, {
			"orgId" : gAssigner,
			"treeDocIds" : arrTreeIds.join("^")
		}, function(data) {
			//console.log(data);
			if(data!=""){
				//设置菜单权限状态
				goSetCheckBox(mini.decode(data),TreeName);
			}
		});
	}
	/*
		描述：Ajax获得对应权限后，重新进行更新
	*/
	//已拥有选中的权限节点ID，用于保存时排除这些已拥有选中权限，只保存发生变化的节点，提高保存效率
	var gCheckedNode=[];
	function goSetCheckBox(oRightNode,TreeName){
		oRightNode=oRightNode[0];
		var oTree=mini.get(TreeName);
		var arrAllNode=oTree.getList();
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
					oTree.updateNode(arrAllNode[intM],{status:oStatus});
				}
				if(oStatus==2){
					gCheckedNode.push(arrAllNode[intM].id);
				}
			}else{
				//如果节点状态为"已选择"，或无效选择状态时，修改节点状态为"未选择"
				if(parseInt(arrAllNode[intM].status,10)>1){
					oTree.updateNode(arrAllNode[intM],{status:"1"});
				}
			}
		}
	}
	
	/*
	描述：改变复选框状态
	*/
	function goCheckBox(TreeName){
		//树对象
		var oTree=mini.get(TreeName);
		//当前选中节点
		var oNode=oTree.getSelectedNode();
		if(gAssigner==""){alert("请双击最左侧的对象进行权限分配！");return}
		//当状态为未选中时，点击后就变为选中。即为1变为2。1为未选中，2为已选中
		if(oNode.status=="1"){
			oTree.updateNode(oNode,{status:"2"});
		}else if(oNode.status=="2"){
			oTree.updateNode(oNode,{status:"1"});
		}
		//所有父节点不含自身
		var arrParent=oTree.getAncestors(oNode),arrChildNode=[];
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
					intCheckBox=0;arrChildNode=oTree.getChildNodes(oN);
					for(var intQ=0,intS=arrChildNode.length;intQ<intS;intQ++){
						if($("#"+arrChildNode[intQ]["id"],"#"+TreeName).hasClass("rChecked")){
							intCheckBox++
						}
					}
					//当本节点状态变为未选中时，如果父节点下没有被选中的子节点时，改变状态为未选中
					if(intCheckBox<1){
						oTree.updateNode(oN,{status: strStatus})
					}
				}else{
					//当本节点状态变为已选中时，如果父节点下就一个已选中子节点时（说明原先父节点下就没有被选中的子节点），改变状态为已选中。
					oTree.updateNode(oN,{status: strStatus})
				}
			}else{
				break;
			}
		}
		//改变所有子孙状态
		oTree.cascadeChild(oNode,function(node){
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
				oTree.updateNode(node,{status: strStatus});
			}
		});
	}
</script>
</head>
<body>
	<div class="mini-splitter" style="width:100%;height:100%;border:0" vertical="true" allowResize="false">
		<div size="60" showCollapseButton="true" class="header">
			<h1 style="margin:0;padding:15px;font-family:微软雅黑,黑体,宋体;">权限管理</h1>
			<div style="position:absolute;top:35px;left:180px;font-size:12px">版本:V1.0</div>
		</div>
		<div showCollapseButton="false" style="border:0">
			<div class="mini-layout" style="width:100%;height:100%;" id="layout">
				<div region="west" style="overflow:hidden;border-bottom:0" showHeader="false" width="200px" minWidth="200" maxWidth="350" showSplitIcon="true">
					<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<table style="width:100%" cellspacing=0 cellpadding=0>
							<tr>
								<td style="width:100%;text-align:center"><input id="key" class='mini-textbox' style="width:98%" onenter="Search" /></td>
								<td style="white-space:nowrap;padding-left:2px"><a class='mini-button' iconCls='icon-search' plain='true' style="width:60px" onclick="Search">查询</a>
								</td>
							</tr>
						</table>
					</div>
					<div class="mini-fit" style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<div id="OrgTabs" class="mini-tabs" style="height:100%;" plain="false" onactivechanged="goOrgTabChange">
							<div title="组织机构" name='OrgTree'>
								<div id='OrgTree' class='mini-tree' showTreeIcon='true' textField="text" resultAsTree='false' showCheckBox='false' checkRecursive='true' expandOnLoad='0' expandOnDblClick='false'></div>
							</div>
							<div title="角色" name='RoleTree'>
								<!-- allowSelect='true' enableHotTrack='false' -->
								<div id='RoleTree' class='mini-tree' showTreeIcon='true' textField="text" resultAsTree='false' showCheckBox='false' checkRecursive='true' expandOnLoad='0' expandOnDblClick='false'></div>
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
	<script type="text/javascript">
		mini.parse();
		var gOrgTree = mini.get("OrgTree");
		var gRoleTree = mini.get("RoleTree");
		
		/*描述：保存权限*/
		function goSave(){
			if(gAssigner==""){mini.alert("请先选择需要分配的对象!","操作提示");return};
			var msgId=mini.loading("权限保存中...","数据提交");
			var arrNoteID = [];
			//所有节点
			var oTabs = mini.get("tabMenu");
			var oTab = oTabs.getActiveTab();
			var oTree=mini.get("MenuTree"+oTab.name);
			var arrAllNode=oTree.getList();
			var intM=0,intL=arrAllNode.length;
			for(;intM<intL;intM++){
				var oNode=arrAllNode[intM];
				//节点在“已拥有权限节点”数组中
				if($.inArray(oNode.id,gCheckedNode)>-1){
					//当节点的状态变为“未选中”时，说明已取消权限
					if(oNode.status=="1"){
						arrNoteID.push(oNode.id+"-1")
					}
				}else{
					//当节点的状态变为“已选中”时，说明已拥有权限
					if(oNode.status=="2"){
						arrNoteID.push(oNode.id+"-2")
					}
				}
			}
			var PathUrl="${pageContext.request.contextPath}/admin/saveRight.action";
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
		/*
		描述：版块页签切换时触发
		*/
		function goOrgTabChange(e){
			var oTabs=e.sender,oCurTab=e.tab;
			if(oCurTab.name=="RoleTree"){
				if(gRoleTree.getList().length<1){
					loadRoleTree()
				}
			}else{
				if(gOrgTree.getList().length<1){
					loadOrgTree()
				}				
			}
		}
		/*
		描述:创建部门树
		 */
		function loadOrgTree() {
			//异步加载数据，去掉数据最前端的逗号，使得tree数据格式正确。
			$.ajax({
				url : "${pageContext.request.contextPath}/admin/findOrgTree.action",
				cache : false,
				dataType : "text",
				success : function(data) {
					var orgData = mini.decode(data);
					if (orgData) {
						gOrgTree.loadList(orgData, "id", "pid");
						gOrgTree.expandLevel(0);
					} else {
						gOrgTree.loadList([], "id", "pid");
					}
					gOrgTree.on("nodedblclick",function(e){
						goArragneRight(e.node,"MenuTree" + gRootId);
					});
				}
			});
		}
		/*
			描述:加载角色列表
		 */
		function loadRoleTree() {
			$.ajax({
				url : "${pageContext.request.contextPath}/admin/findAllRoleJson.action",
				type : "post",
				data : {},
				dataType : "text",
				success : function(data) {
					if (data) {
						var list = mini.decode(data);
						gRoleTree.loadList(list, "id", "pid");
						gRoleTree.on("nodedblclick",function(e){
							goArragneRight(e.node,"MenuTree" + gRootId);
						});
					}
				}
			});
		}

		/*
		描述:关闭窗口
		 */
		function goCloseDlg(name) {
			var oWinDlg = mini.get(name);
			oWinDlg.setUrl("about:blank");
			$(oWinDlg.getBodyEl()).html("");
			$(oWinDlg.getFooterEl()).html("");
			oWinDlg.hide();
		}
	</script>
</body>
</html>
