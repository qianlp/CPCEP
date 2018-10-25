<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<base href="<%=basePath%>">
<title>编辑菜单</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta name="content-type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">
html,body,form {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
}

.mini-listbox-item td {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	
	
	//加载默认数据权限
	function LoadDefaultDataRight(){
		var rightDefaultPerson = mini.get("rightDefaultPerson");
		var rightDefaultRole = mini.get("rightDefaultRole");
		var dataJson = $("[name='menu.dataRightJson']").val();
		var arrJson = mini.decode(dataJson);
		var orgArr=[];
		var roleArr = [];
		$.each(arrJson,function(i,obj){
			if(obj.type&&obj.type == "role"){
				roleArr.push(mini.clone(obj));
			}else{
				orgArr.push(mini.clone(obj));
			}
		});
		rightDefaultRole.setData(roleArr);
		rightDefaultPerson.setData(orgArr);
	}
	
	/*
		描述:创建部门树
	 */
	function LoadOrgTree() {
		//异步加载数据，去掉数据最前端的逗号，使得tree数据格式正确。
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/findOrgTree.action",
			cache : false,
			dataType : "text",
			success : function(data) {
				var orgTree = mini.get("OrgTree");
				var rightOrgTree = mini.get("rightOrgTree");
				var orgData = mini.decode(data);
				if (orgData) {
					orgTree.loadList(orgData, "id", "pid");
					rightOrgTree.loadList(mini.clone(orgData), "id", "pid");
					orgTree.expandLevel(0);
					rightOrgTree.expandLevel(0);
					//加载已选的组织列表
					var arrData = [];
					for (var i = 0; i < orgData.length; i++) {
						if ($.inArray(orgData[i].id,gArrOrgId) > -1) {
							arrData.push(mini.clone(orgData[i]));
						}
					}
					
					mini.get("defaultPerson").addItems(arrData);
				} else {
					orgTree.loadList([], "id", "pid");
					rightOrgTree.loadList([], "id", "pid");
				}
				
			}
		});
	}
	
	/*
	function getArrByIds(ids,arr){
		var arrData = [];
		for (var i = 0; i < arr.length; i++) {
			if (gArrOrgId.indexOf(arr[i].id) > -1) {
				arrData.push(mini.clone(arr[i]));
			}
		}
		return arrData;
	}
	*/
	
	/*
		描述:加载角色列表
	 */
	function LoadRoleList() {
		var path = "${pageContext.request.contextPath}/admin/findAllRoleJson.action";
		$.post(path, {}, function(data) {
			var roleList = mini.decode(data);
			var roleGrid = mini.get("allRoleList");
			var rightOrgTree = mini.get("rightRoleList");
			if (roleList) {
				roleGrid.load(roleList);
				rightOrgTree.load(mini.clone(roleList));
				//加载已选的组织列表
				var arrData = [];
				for (var i = 0; i < roleList.length; i++) {
					if ($.inArray(roleList[i].id,gArrOrgId) > -1) {
						arrData.push(mini.clone(roleList[i]));
					}
				}
				mini.get("defaultAllRole").addItems(arrData);
			} else {
				roleGrid.load([]);
				rightOrgTree.load([]);
			}
		});
	}

	function getHasOrgs() {
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/findOrgIds.action",
			cache : false,
			dataType : "text",
			data : {
				"right.docId" : $("[name='menu.uuid']").val(),
				"right.modoName" : "menu"
			},
			type : "post",
			async : false,
			success : function(data) {
				if(mini.decode(data)){
					gArrOrgId = mini.decode(data);
				}
			}
		});
	}
	//角色选择
	function setItemRole(e) {
		var listbox = e.sender;
		var defaultPerson = mini.get("defaultPerson");
		var defaultAllRole = mini.get("defaultAllRole");
		var rightDefaultPerson = mini.get("rightDefaultPerson");
		var rightDefaultRole = mini.get("rightDefaultRole");
		if(listbox.id=="OrgTree"){
			var node = e.node;
			defaultPerson.addItem({id : node.id,text : node.text,type : node.type});
		}else if (listbox.id == "allRoleList") {
			if (defaultAllRole.findItems(listbox.getValue()).length == 0) {
				defaultAllRole.addItem(mini.clone(e.item));
			}
		} else if(listbox.id=="rightOrgTree"){
			var node = e.node;
			rightDefaultPerson.addItem({id : node.id,text : node.text,type : node.type});
		}else if (listbox.id == "rightRoleList") {
			if (rightDefaultRole.findItems(listbox.getValue()).length == 0) {
				rightDefaultRole.addItem(mini.clone(e.item));
			}
		}else if (listbox.id == "defaultPerson") {
			defaultPerson.removeItems(defaultPerson.findItems(listbox
					.getValue()));
		} else if (listbox.id == "defaultAllRole" ) {
			defaultAllRole.removeItems(defaultAllRole.findItems(listbox.getValue()));
		}else if (listbox.id == "rightDefaultPerson") {
			rightDefaultPerson.removeItems(rightDefaultPerson.findItems(listbox
					.getValue()));
		} else if (listbox.id == "rightDefaultRole") {
			rightDefaultRole.removeItems(rightDefaultRole.findItems(listbox.getValue()));
		}
	}
	/*
	 描述：
	     根据父节点id选择同层级部门
	 参数：
	  e：部门树节点对象
	 */
	function loadSameLevDept(e) {
		var menuPid = mini.getbyName("menu.menuPid").getValue();
		if (menuPid) {
			e.sender
					.load(encodeURI("${pageContext.request.contextPath}/admin/findChildMenuList.action?menu.menuPid="
							+ menuPid));
		}
	}
	/*
	 描述：
	 选择部门中加入清除图标，点击清除值
	 参数：
	 e：部门树节点对象
	 */
	function onCloseClick(e) {
		e.sender.setValue("-1");
		e.sender.setText("无");
		cleanTreeSel("sameLevelNodeId");
	}
	/*
	 描述：
	 选择部门后给页面的设置域的实际值和显示值
	 参数：
	 e：部门树节点对象
	 */
	function SelectOrgDept(e) {
		e.sender.setValue(e.node["id"]);
		e.sender.setText(e.node["text"]);
		cleanTreeSel("sameLevelNodeId");
	}
	//清楚Tree-select中控件的text、value及data
	function cleanTreeSel(name) {
		var objCombox = mini.getbyName(name);
		objCombox.setValue();
		objCombox.setText();
		objCombox.setData([]);
	}
	function SelectPosition(e) {
		var objCombox = mini.getbyName("sameLevelNodeId");
		if (e.value == "0") {
			cleanTreeSel("sameLevelNodeId");
			objCombox.disable();
		} else {
			objCombox.enable();
		}
	}
	//-----------------------

	//======================================================================
	function CloseWindow(action) {
		if (window.CloseOwnerWindow) {
			return window.CloseOwnerWindow(action);
		} else {
			window.close();
		}
	}
	function goSubmit() {
		var strNodeName = mini.getbyName("menu.menuName").getValue().replace("/\s/g", "");
		if (strNodeName == "") {
			alert("请填写菜单名称！");
			return;
		}
		//保存去掉空格的节点名称
		mini.getbyName("menu.menuName").setValue(strNodeName);
		var tabs = mini.get("miniTabs");
		var ifmHead = tabs.getTabIFrameEl(tabs.getTab("tabHTMLHEAD"));
		var ifmBody = tabs.getTabIFrameEl(tabs.getTab("tabHTMLBODY"));
		var ifmSearch = tabs.getTabIFrameEl(tabs.getTab("tabHTMLSEARCH"));
		if (ifmHead) {
			$("#HtmlHead").val($.trim(ifmHead.contentWindow.CM.getValue()));
		}

		if (ifmBody) {
			$("#HtmlBody").val($.trim(ifmBody.contentWindow.CM.getValue()));
		}
		
		if (ifmSearch) {
			$("#HtmlSearch").val($.trim(ifmSearch.contentWindow.CM.getValue()));
		}

		if (confirm("您确信需要保存吗？")) {
			//保存菜单查看权限
			var orgList = mini.get("defaultPerson").data;
			var roleList = mini.get("defaultAllRole").data;
			$("[name='rightJson']").val(mini.encode(orgList.concat(roleList)));
			//保存数据初始化权限
			var rightPersonList = mini.get("rightDefaultPerson").data;
			var rightRoleList = mini.get("rightDefaultRole").data;
			$("[name='menu.dataRightJson']").val(mini.encode(rightPersonList.concat(rightRoleList)));
			
			//获取选中的字符串id
			var checkBtn = mini.get("checkButtonList");
			//将多个按钮id转化成字符串进行保存
			$("[name='btnData']").val(mini.encode(checkBtn.getValue()).replace(/["\[\]]/g,""));
			if(mini.get("menuType").getValue()=="2"){
				parent.isBigRef=true;
			}
			document.forms[0].submit();
		}
	}
	/*
	描述：初始化按钮
	 */
	function goInitBtn() {
		var oWinDlg = mini.get('miniWindow');
		var oBtnName = mini.get("btnNameList");
		var checkBtn = mini.get("checkButtonList");
		if (oBtnName.getData().length == 0) {
			$.ajax({
				url : "${pageContext.request.contextPath}/admin/findAllButton.action",
				success : function(data) {
					gBtnList = mini.decode(data);
					oBtnName.setData(gBtnList);
					oBtnName.setValue(checkBtn.getValue());
					var existBtn = oBtnName.findItems(mini.get("existBtnList").getValue());
					oBtnName.removeItems(existBtn);
					oWinDlg.showAtPos("center", "middle");
				}
			});

		} else {
			oWinDlg.showAtPos("center", "middle");
		}
	}

	/*
	 描述：初始化按钮
	 */
	function goAddBtn() {
		var oBtnName = mini.get("btnNameList");
		checkedBtnIds = oBtnName.getValue();
		var arrData = [];
		for (var i = 0; i < gBtnList.length; i++) {
			if (checkedBtnIds.indexOf(gBtnList[i].id) > -1) {
				arrData.push(mini.clone(gBtnList[i]));
			}
		}
		mini.get("checkButtonList").setData(arrData);
		mini.get("checkButtonList").setValue(checkedBtnIds);
		var oWinDlg = mini.get('miniWindow');
		oWinDlg.hide();
	}
	
		/*
		描述：删除菜单
	 */
	function goDelDoc() {
		mini.confirm("您确定删除该菜单下所有子菜单吗？","提示",function(r){
			if (r=="ok"){
				var PathUrl ="${pageContext.request.contextPath}/admin/delMenu.action";
			$.ajax({
				url : encodeURI(PathUrl),
				type : "post",
				dataType:"json",
				data:{"menu.uuid":$("[name='menu.uuid']").val()},
				success : function(data) {
				//	var data = mini.decode(text);
					if(data.success){
						if(mini.get("menuType").getValue()=="2"){
							parent.isBigRef=true;
						}
						mini.alert(data.msg, "消息提示",function() {
								//	parent.gNewNodePid = $("[name=ParDocID]").val();
								//	parent.gParentNodeHrName = $("[name=ParNodeHrName]").val();
									parent.goCloseDlg('oWinDlg');
								});
					} else {
						mini.alert("删除失败！");
					}
				}
			});
			}
		});
	}
		
/*
	描述：清除图标
*/
function goClearIcon(){
	mini.get("classicon").setText("");
	$("#ClassIco").val("");
}
/*
	描述：选择图标,并赋值
*/
function goClickIcon(){
	$("#ClassIco").val(mini.get("classicon").getValue());
}

function goDrawIcon(cell){
	return "<i class=\"fa "+cell.value+"\"></i>";
}
</script>

</head>

<body text="#000000" bgcolor="#FFFFFF">

	<form method="post" action="${pageContext.request.contextPath}/admin/updateMenu.action" name="addMenu">
		<div style="display:none">
			<textarea name="menu.menuHtmlHead" id="HtmlHead">
				${menu.menuHtmlHead }
			</textarea>
			<textarea name="menu.menuHtmlBody" id="HtmlBody">
				${menu.menuHtmlBody }
			</textarea>
			<textarea name="menu.menuHtmlSearch" id="HtmlSearch">
				<c:if test="${not empty menu.menuHtmlSearch}">
					${menu.menuHtmlSearch }
				</c:if>
				<c:if test="${empty menu.menuHtmlSearch}">
<!-- 
	search_field:属性说明
	--------------------
	Operator：查询条件（ 特殊“@”=包含  ）其他符号（=,>=,<=,!=）
	DataType：类型 text , number , date , object(一般用于特殊条件：比如 is null)
	暂不支持小组查询
-->
<!-- 需要查询时放开
<div class="search_body" id="search_body">
	<div class="search_content">
		<div class="search_title">项目编号：</div>
		<div class="search_field" Operator="@" DataType="text" Item="">
			<input name="projectNo" id="projectNo" class="mini-textbox">
		</div>
	</div>
	<div class="search_button">
		<a class="mini-button" tooltip="清空查询条件" plain="true" iconCls="icon-remove" onclick="ClearForm"></a>
		&nbsp;<a class="mini-button" iconCls="icon-search" onclick="CommonSearch">搜索</a>
	</div>
</div>
<script>
	//清空查询条件
	function ClearForm(){
		var searchForm = new mini.Form("#search_body");
		searchForm.reset();
	}
	/*
	描述：查询
	*/
	function CommonSearch(){
		var searchArr=[];
		$("#search_body .search_field").each(function(){
			var obj={};
			var name = $($(this).find("input")[0]).attr("id");
			obj.name = name.split("$")[0];
			obj.operator=$(this).attr("Operator");
			obj.dataType=$(this).attr("DataType");
			obj.value=mini.getbyName(obj.name).getValue();
			if(obj.value!=""){
				searchArr.push(obj);
			}
		});
		grid.load({search:mini.encode(searchArr),menuId:menuId});
	}
</script>
-->
				</c:if>
			</textarea>
			<input name = "btnData" />
			<textarea name="rightJson"></textarea>
			<textarea name="menu.dataRightJson">
				${menu.dataRightJson }
			</textarea>
			<input name="menu.uuid" value="${menu.uuid}" />
			<input name="menu.menuOpsition" value="${menu.menuOpsition}" type="hidden"/>
			<input name="menu.menuIcon" value="${menu.menuIcon}"  id="ClassIco" style="width:100%" />
		</div>
		<div class="mini-layout" style="width:100%;height:100%;" borderStyle="border:0">
			<div title="center" region="center" style="border:0;">
				<div id="miniTabs" class="mini-tabs" activeIndex="0" style="width:99%;height:100%;margin:0 auto" plain="true">
					<div title="菜单内容">
						<fieldset style="padding-left:10px;padding-right:10px">
							<legend>
								<span>基本信息</span>
							</legend>
							<table border="0" cellpadding="1" cellspacing="2" style="width:100%;margin:0 auto">
								<tr>
									<td style="width:100px;text-align:right">关联文件目录：</td>
									<td><input name="menu.menuIsLook" value="${menu.menuIsLook }" class="mini-radiobuttonlist" data="[{id:'1',text:'是'},{id:'0',text:'否'}]" style="width:100%"></td>
									<td style="width:100px;text-align:right">关联流程：</td>
									<td><input name="menu.menuIsHasWF" value="${menu.menuIsHasWF }" class="mini-radiobuttonlist" data="[{id:'1',text:'是'},{id:'0',text:'否'}]" style="width:100%"></td>
								</tr>
								<tr>
									<td style="text-align:right">菜单名称：</td>
									<td><input name="menu.menuName" value="${menu.menuName}" class="mini-textbox" vtype="maxLength:20" style="width:100%" id="NodeName"></td>
									<td style="text-align:right">菜单类型：</td>
									<td><input name="menu.menuType" value="${menu.menuType }" id="menuType" class="mini-combobox" style="width:100%;" onvaluechanged="goChangeType"
										data="[{id:'0',text:'其它'},{id:'3',text:'文件夹'},{id:'1',text:'功能模块'},{id:'2',text:'系统版块'}]"
										tooltip="<div style='text-align:left'>1、必须先建立<系统版块>，并且唯一;</div><div style='text-align:left'>2、最好用<文件夹>包含多个<功能模块>，不要用<其它>;</div><div style='text-align:left'>3、<其它>最好建在<功能模块>下;</div><div style='text-align:left'>4、<功能模块>也可以独立作为导航;</div>"></td>
								</tr>
								<tr>
									<td style="text-align:right">父菜单名称：</td>
									<td><input name="menu.menuPid" value="${menu.menuPid }" <c:if test="${menu.menuPid=='-1' }">
											text="无" 
										</c:if> class="mini-treeselect"
										url="${pageContext.request.contextPath}/admin/findAllMenuList.action" textField="text" valueField="id" parentField="pid" onnodeclick="SelectNodeHrName" showClose="true" oncloseclick="onCloseClick" style="width:100%;"
										expandOnLoad="true" /></td>
									<td style="text-align:right">信息图标：</td>
									<td>
										<div id="classicon" class="mini-combobox" style="width:100%;" value="${menu.menuIcon}" multiSelect="false" showClose="true" oncloseclick="goClearIcon" onitemclick="goClickIcon"
											url="${pageContext.request.contextPath}/coresys/MenuIcon.json"
										>
											<div property="columns">
												<div type='indexcolumn' width="15"></div>
												<div header="图标" field="icon" renderer="goDrawIcon" width="48"></div>
												<div header="描述" field="text"></div>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align:right;display:none">字体样式：</td>
									<td style="display:none"><input name="menu.menuStyle" value="${menu.menuStyle }" class="mini-textbox" class="mini-textbox" style="width:100%;"></td>
									<td style="text-align:right">权限过滤：</td>
									<td><input name="menu.isPrower" value="${menu.isPrower}" class="mini-radiobuttonlist" data="[{id:'1',text:'是'},{id:'0',text:'否'}]"></td>
									<td style="text-align:right">关联附件：</td>
									<td><input name="menu.menuOpenStyle" value="${menu.menuOpenStyle}" class="mini-radiobuttonlist" data="[{id:'1',text:'是'},{id:'0',text:'否'}]"></td>
								</tr>
								<tr>
									<td align="right">菜单位置：</td>
									<td colspan=3><table border=0>
											<tr>
												<td><input name="position" value="2" class="mini-radiobuttonlist" data="[{id:'2',text:'保持当前位置'},{id:'0',text:'同层次最后位置'},{id:'1',text:'在'}]" onvaluechanged="SelectPosition"
													style="border-width:0px"></td>
												<td><input name="sameLevelNodeId" value="" class="mini-combobox" style="width:150px;" textField="text" valueField="id" emptyText="请选择..." showNullItem="true" nullItemText="请选择..."
													onbeforeshowpopup="loadSameLevDept" enabled="false" id="sameLevelNodeId">之前</td>
											</tr>
										</table></td>
								</tr>
								<tr>
									<td style="text-align:right">按钮列表：</td>
									<td colspan=3>
										<table border=0>
											<tr>
												<td style="width:100%"><c:forEach items="${menu.menuToBtn }" var="btn" varStatus="index">
														<c:choose>
															<c:when test="${index.index ==0 }">
																<c:set var="arrValue" value="${btn.btnId }"></c:set>
																<c:set var="strJson" value="{'menuBtnId':'${btn.uuid }','id':'${btn.btnId }','text':'${btn.btnName }'}"></c:set>
															</c:when>
															<c:otherwise>
																<c:set var="arrValue" value="${arrValue},${btn.btnId }"></c:set>
																<c:set var="strJson" value="${strJson},{'menuBtnId':'${btn.uuid }','id':'${btn.btnId }','text':'${btn.btnName }'}"></c:set>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<div class="mini-checkboxlist" id="existBtnList" enabled="false" repeatItems="8" repeatLayout="table" value="${arrValue}" data="[${strJson} ]" style="float:left"></div>
													<div class="mini-checkboxlist" id="checkButtonList" enabled="false" repeatItems="8" repeatLayout="table"></div></td>
												<td style="white-space:nowrap;"><a class="mini-button" id="InitBtn" iconCls="icon-add" plain="true" onclick="goInitBtn()" enabled="true">新增按钮</a></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td style="text-align:right">是否是供应商菜单：</td>
									<td><input name="menu.hasSupplier" value="${menu.hasSupplier}" class="mini-radiobuttonlist" data="[{id:'true',text:'是'},{id:'false',text:'否'}]"></td>
								</tr>
							</table>
						</fieldset>
					</div>
					<div title="HTML Head" name="tabHTMLHEAD" url="${pageContext.request.contextPath}/coresys/menu_head.jsp"></div>
					<div title="HTML Body" name="tabHTMLBODY" url="${pageContext.request.contextPath}/coresys/menu_body.jsp"></div>
					<div title="HTML Search" name="tabHTMLSEARCH" url="${pageContext.request.contextPath}/coresys/menu_search.jsp"></div>
					<div title="参数配置">
							<table border="0" cellpadding="1" cellspacing="2" style="width:100%;margin:0 auto">
								<tr>
									<td style="width:100px;text-align:right">实体类名称：</td>
									<td><input name="menu.entityClsName" value="${menu.entityClsName}" class="mini-textbox" style="width:100%"></td>
								</tr>
								<tr>
									<td style="width:100px;text-align:right">视图实体类名称：</td>
									<td><input name="menu.queryEntityClsname" value="${menu.queryEntityClsname}" class="mini-textbox" style="width:100%"></td>
								</tr>
								<tr>
									<td style="width:100px;text-align:right">Action地址：</td>
									<td><input name="menu.actionAddress" value="${menu.actionAddress}" class="mini-textbox" style="width:100%"></td>
								</tr>
								<tr>
									<td style="text-align:right" rowspan="4" valign="top">主表单地址：</td>
									<td><input name="menu.pageComAddress" value="${menu.pageComAddress}" class="mini-textbox" style="width:100%"></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;维护表单：/template/fmBrief.jsp</td>
								</tr>
								<tr>
									<td>无流程表单：/template/fmTemplate.jsp</td>
								</tr>
								<tr>
									<td>有流程表单：/template/fmWFMainFile.jsp</td>
								</tr>
								<tr>
									<td style="text-align:right">子表单地址：</td>
									<td><input name="menu.pageSubAddress" value="${menu.pageSubAddress}" class="mini-textbox" style="width:100%"></td>
								</tr>
								<tr>
									<td style="text-align:right">查询表单地址：</td>
									<td><input name="menu.pageSearchAddress" value="${menu.pageSearchAddress}" class="mini-textbox" style="width:100%"></td>
								</tr>
							</table>
					</div>
					
					<div title="文档授权">
						<table border="0" cellpadding="1" cellspacing="2" style="width:98%;">
							<tr>
								<td style="text-align:center;width:100px">默认角色名称：<br /> <font color="red">例如:公司领导</font></td>
								<td style="width:520px">
									<div class="mini-listbox" id="rightDefaultRole" onitemdblclick="setItemRole" style="width:260px;height:160px;float:left" data="[]">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="text">名称</div>
										</div>
									</div>
									<div class="mini-listbox" id="rightRoleList"  onitemdblclick="setItemRole" style="width:260px;height:160px;left:10px">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="text">名称</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td style="text-align:center;width:100px">默认组织：</td>
								<td>
									<div class="mini-listbox" id="rightDefaultPerson"  onitemdblclick="setItemRole" style="width:260px;height:180px;float:left" data="[]">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="text">名称</div>
										</div>
									</div>
									<div class="mini-panel-border mini-grid-border"   style="width:260px;height:180px;left:10px;">
										<div id='rightOrgTree' style="width:100%;height:100%" class='mini-tree' showTreeIcon='true' textField="text" valueField="id" resultAsTree='false' showCheckBox='false' checkRecursive='true' onnodedblclick="setItemRole"
											expandOnLoad='0' expandOnDblClick='false'></div>
									</div>
								</td>
							</tr>
						</table>
					</div>
					
					<div title="菜单权限拥有者">
						<table border="0" cellpadding="1" cellspacing="2" style="width:98%;">
							<tr>
								<td style="text-align:center;width:100px">默认角色名称：<br /> <font color="red">例如:公司领导</font></td>
								<td style="width:520px">
									<div class="mini-listbox" id="defaultAllRole" onitemdblclick="setItemRole" style="width:260px;height:160px;float:left" data="[]">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="text">名称</div>
										</div>
									</div>
									<div class="mini-listbox" id="allRoleList"  onitemdblclick="setItemRole" style="width:260px;height:160px;left:10px">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="text">名称</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td style="text-align:center;width:100px">默认组织：</td>
								<td>
									<div class="mini-listbox" id="defaultPerson"  onitemdblclick="setItemRole" style="width:260px;height:180px;float:left" data="[]">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="text">名称</div>
										</div>
									</div>
									<div class="mini-panel-border mini-grid-border"   style="width:260px;height:180px;left:10px;">
										<div id='OrgTree' style="width:100%;height:100%" class='mini-tree' showTreeIcon='true' textField="text" valueField="id" resultAsTree='false' showCheckBox='false' checkRecursive='true' onnodedblclick="setItemRole"
											expandOnLoad='0' expandOnDblClick='false'></div>
									</div>
									<!--<div style="width:100%;height:30px;right:10px;padding-top:3px;">
										<a class="mini-button" iconCls="icon-ok" style="display:0" onclick="ProwerSynch()">权限同步</a>
									</div>-->
								</td>
							</tr>

						</table>
					</div>
				</div>
			</div>
			<div title="south" region="south" showSplit="false" showHeader="false" height="37px" splitSize=1 borderStyle="border:0" style="overflow:hidden">
				<div class="mini-toolbar" style="padding:2px;border:0">
					<table style='width:100%;'>
						<tr>
							<td style='width:100%;white-space:nowrap;text-align:right'><a class="mini-button" iconCls="icon-ok" plain="true" style="display:0" onclick="goSubmit()">保存</a> <a class="mini-button"
								iconCls="icon-remove" plain="true" visible="true" onclick="goDelDoc()">删除</a> <a class="mini-button" iconCls="icon-cancel" plain="true"
								onclick="CloseWindow()">关闭</a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div id="miniWindow" class="mini-window" title="按钮列表" style="width:95px;height:240px;text-align:center" showCloseButton="true" showFooter="true">
			<!--footer-->
			<div property="footer" style="padding:5px">
				<a class="mini-button" plain="false" onclick="goAddBtn()">添加</a>
			</div>
			<!--body-->
			<div id="btnNameList" class="mini-checkboxlist" repeatItems="1" repeatLayout="table"></div>
		</div>
		<script>
			mini.parse();
			$().ready(function() {
				getHasOrgs();
				LoadOrgTree();
				LoadRoleList();
				LoadDefaultDataRight();
			});
		
			var gArrOrgId=[];
			var oTip = new mini.ToolTip();
			oTip.set({
				target : document,
				selector : '[data-tooltip],[title]',
				onbeforeopen : function(e) {
					e.cancel = false;
				}
			});
		</script>
	</form>
</body>
</html>
