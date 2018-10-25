<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>

<style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    #CatalogTree .mini-grid-bottomCell{
		border-bottom:1px solid #ccc;
	}	    
</style>

<script language="JavaScript" type="text/javascript">
	var TreeGrid=null; 
	var MenuData=[];
	var MenuKTData=[];
	var OrgData=[];
	var RoleData=[];
	//文档加载完毕后执行
	$(document).ready(function(){
		mini.parse();
		TreeGrid=mini.get("CatalogTree");
		
		//TreeGrid 绑定事件
		CatalogMenu();
		//异步加载目录树
		ajaxLoadCatalog();
		//异步加载菜单数据
		 ajaxMenu();
		//异步加载框图菜单数据
		//ajaxKTMenu();
		//加载组织机构
		ajaxOrg();
		ajaxRole();
	})
	
	//保存方法
	function ToSave(){
		//获取所有改变的数据
		var data=TreeGrid.getChanges();
		//前台拦截空数组，避免后台请求压力
		if(data.length==0){
			mini.alert("未做新增或更新不允许保存操作！");
			return;
		}
		
		//异步保存	
		ajaxSave(mini.encode(data));
	}
	
	//删除方法
	function ToDel(){
		mini.mask({
	            el: document.body,
	            cls: 'mini-mask-loading',
	            html: '数据移除中...'
	     });
		var ids="";
		if(TreeGrid.getSelectedNodes().length==0){
			mini.alert("请选择一条数据！");
			mini.unmask(document.body);
			return;
		}
		$.each(TreeGrid.getSelectedNodes(),function(){
			if(this.uuid){
				if(ids!=""){ids+="^"}
				ids+=this.uuid;
			}else{
				TreeGrid.removeNode(this);
			}
		})
		if(ids!="" || ids!="undefined"){
			var path="${pageContext.request.contextPath}/profile/fileDirectoryDelete.action";
			$.post(path,{
				"uuid":ids
			},function(data){
				TreeGrid.removeNodes(TreeGrid.getSelectedNodes());
				mini.unmask(document.body);
			})
		}else{
			mini.unmask(document.body);
		}
		
	}
	
	//异步保存
	function ajaxSave(data){
		mini.mask({
	            el: document.body,
	            cls: 'mini-mask-loading',
	            html: '数据提交中...'
	     });
		var path="${pageContext.request.contextPath}/profile/fileDirectoryUpdate.action";
		$.post(path,{
			data:data
		},function(data){
			mini.unmask(document.body);
			//保存完刷新
			ajaxLoadCatalog();
			
		})
	}
	
	//异步加载目录树
	function ajaxLoadCatalog(){
		mini.mask({
	            el: document.body,
	            cls: 'mini-mask-loading',
	            html: '数据加载中...'
	     });
		var path="${pageContext.request.contextPath}/profile/fileDirectoryList.action";
		$.post(path,{},function(data){
			if(data.indexOf(",")!=-1){
				//加载到TreeGrid
				TreeGrid.loadList(mini.decode(data.replace(/\n|\r/g,"")),"UID","parentTaskUID");
			}
			mini.unmask(document.body);
		})
	}


	//TreeGrid 绑定事件
	function CatalogMenu(){
		TreeGrid.on("drawcell",function(e){
		var field=e.field;
		//父节点清空属性
		if(field=="catalogNo"){
			if(!TreeGrid.isLeaf(e.record)){
				e.record.catalogType="";
				e.record.relationMenu="";
				e.record.relationKTMenu="";
			}
		}
	})
	TreeGrid.on("cellbeginedit",function(e){
		var field=e.field;
		
		//父节点不允许编辑
		if(field=="catalogType" || field=="relationMenu" || field=="relationKTMenu"){
			if(!TreeGrid.isLeaf(e.record)){
				e.cancel=true;
			}
		}
		
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
		//选择关联菜单时触发
		if(field=="relationMenu"){
			var ids="";
			var data=e.editor.getCheckedNodes(false);
			$.each(data,function(){
				if(ids!="") ids+=",";
				if(this.children && this.children.length>0){
					var p=0;
					for(var k=0;k<this.children.length;k++){
						if(this.children[k].checked){
							if(p>0)p++;
							ids+=this.children[k].id;
							p++;
						}
					}
				}else{
					ids+=this.id;
				}
			})
			e.record.relationMenuID=ids;
		}
		
		//选择关联框图菜单时触发
		if(field=="relationKTMenu"){
			var ids="";
			var data=e.editor.getCheckedNodes(false);
			$.each(data,function(){
				if(ids!="") ids+=",";
				if(this.children && this.children.length>0){
					var p=0;
					for(var k=0;k<this.children.length;k++){
						if(this.children[k].checked){
							if(p>0)p++;
							ids+=this.id;
							p++;
						}
					}
				}else{
					ids+=this.id;
				}
			})
			e.record.RelationKTMenuID=ids;
		}
	})
}

//替换字符串
var regs=function(str,to,th) { 
	var e=new RegExp(to,"g"); 
	return str.replace(e,th);
} 

//非空判断
function isUn(str){
	if(str==undefined || str==""){
		return "";
	}
	return str;
}

//TreeGrid 添加行
function addRow(){
	//判断是否选中节点
	var date=new Date();
	if(getSelects()){
		var apid="";
		//拼接所有父节点ID
		if(isUn(getSelects().AllPid)!=""){
			apid=getSelects().AllPid+"-";
		}
		apid+=isUn(getSelects().UID);
		//将节点添加到指定节点下
		TreeGrid.addNode({catalogNo:rtuUID(),UID:rtuUID(),parentTaskUID:isUn(getSelects().UID),AllPid:apid},"add",getSelects());
	}else{
		//添加父节点
		TreeGrid.addNode({catalogNo:rtuUID(),UID:rtuUID(),parentTaskUID:"-1"});
	}
}

//判断UID是否唯一
function rtuUID(){
	var intM=mathRedom();
	$.each(TreeGrid.getList(),function(){
		if(this.UID==intM){
			intM=rtuUID();
			return false;
		}
	})
	return intM;
}

//随机数生成
function mathRedom(){
	var max=999999999;
	var min=111111111;
	return parseInt(Math.random()*(max-min+1)+min,10);
}

//获取选中数据
function getSelects(){
	return TreeGrid.getSelectedNode();
}

//异步加载菜单数据
function ajaxMenu(){
	$.ajax({
		url:"${pageContext.request.contextPath}/admin/findAllMenuList.action",
		cache:false,
		dataType:'text',
		success:function(MenuText){
				if(MenuText){
					MenuData=mini.arrayToTree(mini.decode(MenuText),"children","id","pid");
				}
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

<form action=""><div style="display:none">
</div>
<script>
	var gForm=document.forms[0],
	    gCurDB = "OA6.1/HT_FileMgr.nsf",
	    gCommon = "OA6.1",
	    gCommonDB = "",
	    gRightUrl =  "//vwDataView/",
	    gIsNewDoc = 1,
	    gIsEditDoc = 0,
	    gInitiator="Admin",//<!--启动者-->
	    gCurDocID = "60509F384C406AE748257FDA004FD282",
	    gCurDBName = "HT_FileMgr.nsf",
	    gDocKey="60509F384C406AE748257FDA004FD282",
	    gOrgDB="HTCommon/HT_Org.nsf",
	    gIsUpFile=0,
	    gFormType="fm"
</script>
<div id="CatalogNos" style="display:none">
</div>
<div  style="display:none">HTCommon/HT_Org.nsf</div>
<script>
	var deptJson="";
	var gOrgDB="HTCommon/HT_Org.nsf";
</script>
<div class='mini-toolbar' style='padding:0;border-bottom-width:0;'>
		<table style='width:100%;'>
		<tr>
            		<td style='width:50%;white-space:nowrap;' id='leftGridToolBar'>
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow()">新建</a>
		  		<a class="mini-button" iconCls="icon-close" plain="true" onclick="ToDel()">删除</a>
			</td>
            		<td style='white-space:nowrap;width:50%;text-align:right;' id='rightGridToolBar'>
				<a class='mini-button' iconCls='icon-save' plain='true' style="float:none;" onclick='ToSave()'>保存</a>
                		<a class='mini-button' iconCls='icon-reload' plain='true' style="float:none;" onclick='ajaxLoadCatalog()'>刷新</a>
			</td>
            	</tr>
		</table>
    </div>
    <!--撑满页面-->
    <div class="mini-fit" id="CatalogFit" style="height:100%;">
        <div id="CatalogTree" class="mini-treegrid" style="width:100%;height:100%;"
		treeColumn="catalogName" fitColumns='false' multiSelect="true" allowCellEdit="true" allowCellSelect="true"
		idField="UID" parentField="parentTaskUID"  allowAlternating="true" expandOnLoad="0"
		 frozenStartColumn="0" frozenEndColumn="3" resultAsTree="false">
            <div property="columns">
                <div type="checkcolumn" width="50" ></div>
                <div field="catalogNo" name="catalogNo" width="180" headerAlign="center" align="left" visible="false">目录编号
			<input property="editor" class="mini-combobox" style="width:100%;" />
		  </div>
                <div field="catalogName" name="catalogName"  width="180"  headerAlign="center" align="left">目录名称
			<input property="editor" class="mini-textbox" style="width:100%;" />
		  </div>                            
		  <div field="docLink"  width="180"  headerAlign="center" align="left">文档要求
			<input property="editor" class="mini-textarea" style="width:100%;" />
		  </div> 
                <div field="relationMenu" width="300"  headerAlign="center" align="left">关联菜单
			<input property="editor" class="mini-treeselect" multiSelect="true"  showFolderCheckBox="false"  showCheckBox="true" valueField="text" data="MenuData" style="width:100%;" />
		  </div>
		  <div field="relationKTMenu" width="300"  headerAlign="center" align="left" visible="false">关联框图菜单
			<input property="editor" class="mini-treeselect" multiSelect="true"  showFolderCheckBox="false"  showCheckBox="true" valueField="text" expandOnLoad="0"  data="MenuKTData" style="width:100%;" />
		  </div>
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
	var inHeight=$("body").innerHeight();
	$("#CatalogFit").css("height",inHeight-50);
</script></form>
</body>
</html>
