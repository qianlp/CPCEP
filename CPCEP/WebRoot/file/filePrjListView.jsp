<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>项目目录文件权限配置列表</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@include file="../resource.jsp"%>
<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
#CatalogTree .mini-grid-bottomCell {
	border-bottom: 1px solid #ccc;
}

</style>

<script language="JavaScript" type="text/javascript">
	var TreeGrid=null; 
	var prjID="${param.prjID}";
	var OrgData=[];
	var RoleData=[];
	//异步加载目录树
	function ajaxLoadCatalog(){
		mini.mask({
	            el: document.body,
	            cls: 'mini-mask-loading',
	            html: '数据加载中...'
	     });
		
		var path="${pageContext.request.contextPath}/profile/getPrjFileTree.action";
		$.post(path,{"prjID":"${param.prjID}"},function(data){
			if(data!=""){
				//加载到TreeGrid
				TreeGrid.loadList(mini.decode(data.replace(/\n|\r/g,"")),"UID","parentTaskUID");
			}
			mini.unmask(document.body);
		})
	}
	
	//保存方法
	function ToSave(){
		//获取所有改变的数据
		var data=TreeGrid.getChanges();
		//前台拦截空数组，避免后台请求压力
		if(data.length==0){
			mini.alert("未做更新不允许保存操作！");
			return;
		}
		//异步保存	
		ajaxSave(mini.encode(data));
	}
	//异步保存
	function ajaxSave(data){
		mini.mask({
	            el: document.body,
	            cls: 'mini-mask-loading',
	            html: '数据提交中...'
	     });
		var path="${pageContext.request.contextPath}/profile/prjDirFileUpdate.action";
		$.post(path,{
			data:data
		},function(data){
			mini.unmask(document.body);
			//保存完刷新
			ajaxLoadCatalog();
			
		})
	}
	
	//TreeGrid 绑定事件
	function CatalogMenu(){
	TreeGrid.on("cellbeginedit",function(e){
		var field=e.field;
		if(field=="lookRole"){
			e.editor.setData(RoleData);
		}
		if(field=="lookPerson"){
			e.editor.setData(OrgData);
		}
	})
	TreeGrid.on("cellcommitedit",function(e){
		var field=e.field;
		//选择查看人员
		if(field=="lookPerson"){
			var names="";
			var userId="";
			var data=e.editor.getCheckedNodes(false);
			$.each(data,function(){
				if(names!="") names+=",";
				if(userId!="") userId+=",";
				names+=this.text;
				userId+=this.id;
			})
			e.record.LookNames=names;
			e.record.lookNamesId=userId;
		}
		//选择查看角色
		if(field=="lookRole"){
			var roles="";
			var rolesId="";
			var data=e.editor.getCheckedNodes(false);
			$.each(data,function(){
				if(roles!="") roles+=",";
				if(rolesId!="") rolesId+=",";
				roles+=this.text;
				rolesId+=this.id;
			})
			e.record.LookRoles=roles;
			e.record.lookRolesId=rolesId;
		}
	})
}

//异步加载组织人员数据
function ajaxOrg(){
	$.ajax({
		url:"${pageContext.request.contextPath}/admin/findOrgTree.action",
		cache:false,
		dataType:'text',
		success:function(MenuText){
				if(MenuText!=""){
					OrgData=mini.arrayToTree(mini.decode(MenuText),"children","id","pid");
				}
			}
		})
	}

//异步加载角色
function ajaxRole(){
	//异步加载数据
	$.ajax({url:"${pageContext.request.contextPath}/admin/findAllRoleJson.action",cache:false,dataType:'text',success:function(RoleText){
		if(RoleText!=""){
			RoleData=mini.decode(RoleText);
		}
	}})
}
	
</script>
</head>
<body text="#000000" bgcolor="#FFFFFF">
	<form action="">
		<div class='mini-toolbar' style='padding:0;border-bottom-width:0;'>
		<table style='width:100%;'>
		<tr>
            		<td style='white-space:nowrap;width:50%;text-align:right;' id='rightGridToolBar'>
				<a class='mini-button' iconCls='icon-save' plain='true' style="float:none;" onclick='ToSave()'>保存</a>
                		<a class='mini-button' iconCls='icon-reload' plain='true' style="float:none;" onclick='ajaxLoadCatalog()'>刷新</a>
			</td>
            	</tr>
		</table>
    </div>
		<!--撑满页面-->
   		<div class="mini-fit" id="CatalogFit" style="height:100%;">
			<div id="CatalogTree" class="mini-treegrid"  style="width:100%;height:100%;" treeColumn="catalogName"
				fitColumns='false' multiSelect="true" allowCellEdit="true" allowCellSelect="true" idField="UID" parentField="parentTaskUID"
				allowAlternating="true" expandOnLoad="0">
            <div property="columns">
                <div type="indexcolumn" width="50">序号</div>
                <div field="catalogNo" width="80" headerAlign="center" align="left">目录编号</div>
                <div field="catalogName" name="catalogName"  width="260"  headerAlign="center" align="left">目录/文件名称</div>
                <div field="size" width="80" headerAlign="center" align="left">文件大小</div>
				  <div field="lookRole" width="300"  headerAlign="center" align="left">可查看角色
						<input property="editor" allowInput="true" class="mini-treeselect" multiSelect="true"   showFolderCheckBox="false"  showCheckBox="true" valueField="text" textField="text"  expandOnLoad="0" style="width:100%;" />
				  </div>
				  <div field="lookPerson" width="300"  headerAlign="center" align="left">可查看人员
						<input property="editor" allowInput="true" class="mini-treeselect" multiSelect="true"  showFolderCheckBox="false"  showCheckBox="true" valueField="text" textField="text" expandOnLoad="0"   style="width:100%;" />
				  </div>
            </div>
        </div> 
    </div>
		<script>
			mini.parse();
			var inHeight=$("body").innerHeight();
			$("#CatalogFit").css("height",inHeight);
			TreeGrid=mini.get("CatalogTree");
			//TreeGrid 绑定事件
		CatalogMenu();
		//异步加载目录树
		ajaxLoadCatalog();
		//加载组织机构
		ajaxOrg();
		ajaxRole();
			
		</script>
	</form>
</body>
</html>