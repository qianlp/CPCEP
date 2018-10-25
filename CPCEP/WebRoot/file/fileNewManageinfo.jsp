<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <title>新项目文件管理</title>
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/ht/pagertree.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/file/swfupload/swfupload.package.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/file/swfupload/handlers.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="../js/file/swfupload/css/swfupload.css">
<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/form.css" />
<style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
	.mini-splitter-border{
		border:0px;
	}
	.mini-layout-proxy-north{
		border-top:0px;
		border-left:0px;
	}
	.mini-layout-proxy-west{
		border-left:0px;
	}
	.toolbar{
		height:26px;border-top:0px;border-left:0px;border-right:0px;font-weight:bold;color:#444;width:100%;
	}
	.mini-grid-border{
		border-top:0px;
		border-left:0px;
		border-right:0px;
	}
	body .mini-tools-collapse{
		display:none;
	}
	body .search_body{border:0px solid #000;overflow:hidden;display:block;padding-top:5px;}
	.search_content,.search_title,.search_field,.search_button{float:left}
	.search_content{width:250px;margin:2px;height:30px;}
	.search_title{width:90px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px}
	.search_field{width:130px;text-align:left}
	.search_button{margin:1px;}
	.search_field .mini-radiobuttonlist{padding-top:3px}
	.mini-grid-emptyText{
		font-size:15px;
		color:#ccc;
		height:200px;
		text-align:center;
	}
	body .mini-toolbar .mini-button{
		line-height:25px;
		height:25px;
	}
	.element{
		width:20px;
		height:20px;
		margin-right:20px;
		float:left;
		margin-top:10px;
		margin-bottom:10px;
		border-radius:15px;
		text-align:center;
		cursor:pointer;
	}
	.orange{
		background:#FFD700;
		box-shadow:0px 0px 5px #FFD700;
	}

	.green{
		background:green;
		box-shadow:0px 0px 5px green;
	}	
</style>

<script language="JavaScript" type="text/javascript">
var menuId="${param.menuId}";
var orgIds="${session.orgIds}";
var TreeGrid=null;
var grid = null;
var FileGrid=null;
//当前选中的项目
var curPrj=null;
//当前选中的节点
var curNode=null;
var gDir="${pageContext.request.contextPath}";
var isVersion="";
//文档加载完毕后执行
$(document).ready(function(){
	mini.parse();
	ajaxRoleUser();
	TreeGrid=mini.get("CatalogTree");
	//异步加载目录树
	ajaxLoadCatalog();
	grid=mini.get('miniDataGrid');
	grid.setUrl('${pageContext.request.contextPath}/admin/loadListForPage.action');
	var searchArr=[];
	var obj={};
	obj.wfStatus="2";
	searchArr.push(obj);
	grid.load({search:mini.encode(searchArr),menuId:"${param.menuId}"});
	
	//获取文件Grid
	FileGrid=mini.get("fileGrid");
	BindFileGrid();
	FileGrid.load();
	//这里可以重新指定自定义的数据装载路径
	FileGrid.setUrl('${pageContext.request.contextPath}/profile/loadListAdenexaForPage.action');
	
})



function isCG(k){
	if(isVersion==k){
		isVersion="";
	}else{
		isVersion=k;
	}
	ajaxFileList();
}

//附件查询
function initComplefj(id){
	ajaxFileList();
}

function initUpBtn(){
	if(!curPrj || !curNode || !TreeGrid.isLeaf(curNode)){
		$("#btnUp").hide();
		return;
	}
	$("[iconCls=icon-upload]").each(function(){
			var obj={};
			var id=curPrj.uuid;
			if(!id){
				id="";
			}
			obj.queueID=$("#"+this.id).attr("gId");
			obj.formData={
					prjID:id,
					parentDocId:"",
					catalogId:curNode.uuid,
					userName:"${sessionScope.user.userName}",
					menuId:""
			}
			$("#"+this.id).htUpload(obj);
			$("#"+this.id).css("margin-right","5px");
			$("#"+this.id).attr("iconCls","icon-upload");
			$("#"+this.id).attr("gId",obj.queueID);
			$("#"+this.id).show();
	})
}

//预览附件
function goViewAttach(id){
	var grid=mini.get(id);
	var uuid = GetDocID(grid);
	var path = "${pageContext.request.contextPath}/profile/findViewAdenexaById.action?uuid=";
	getViewUpload(uuid,path);
}

//上传附件预览
function getViewUpload(uuid,path){
	if(uuid==""){
		mini.alert("请选择预览的文件！");
		return;
	}
	window.open(path+uuid);
	return;
	mini.open({
		url:path+uuid,
		title:"附件预览",
		width:810,
		height:545,
		showMaxButton: true,
		allowResize:true
	});
}

//获取id
function GetDocID(grid){
	var arrID=$.map(grid.getSelecteds(),function(i){
		return i.uuid;
	});
	return arrID.join("^");
}

//删除附件
function goDelAttach(id){
	var grid=mini.get(id);
	var uuid = GetDocID(grid);
	var path = gDir+"/profile/adenexDeleteFile.action?uuid=";
	goDelAttachFile(uuid,path,id);
}


//删除附件
function goDelAttachFile(s,path,id) {
	var arrFile = s;
    if (arrFile.length > 0) {
       mini.confirm("确定删除选中附件吗？","确认框", function(r){
    	   if(r=="ok"){
                $.ajax({
                    url: path + arrFile,
                    dataType: "json",
                    cache: false,
                    success: function(data) {
                        if (data.success) {
                        	mini.alert(data.data);
                        	ajaxFileList();
                        }else{
                        	mini.alert(data.data);
                        }
                    }
                });
             }
        });
    } else {
        mini.alert("没有可删除的附件，请先选择！")
    }
}

//更新文档状态
function AjaxUpFile(){
	mini.mask({
        el: document.body,
        cls: 'mini-mask-loading',
        html: '数据提交中...'
 	});
	var path="${pageContext.request.contextPath}/profile/updateAdenexa.action";
	$.post(path,{
		uuid:FileGrid.getSelected().uuid,
		status:mini.getbyName("status").getValue(),
		cuNo:mini.getbyName("cuNo").getValue(),
		version:mini.getbyName("version").getValue()
	},function(data){
		hideWindow();
		mini.unmask(document.body);
		ajaxFileList();
	})
}

//隐藏归档窗口
function hideWindow() {
	mini.getbyName("version").setValue("");
	mini.getbyName("status").setValue("已归档");
	var win = mini.get("UpFile");
	win.hide();
}


//显示归档窗口
function showWindow() {
	if(!FileGrid.getSelected()){
		mini.alert("请选择一个文档！");
		return
	}
	
	var win = mini.get("UpFile");
	win.show();
	/*//校验文档是否审核通过
	var path="${pageContext.request.contextPath}/admin/findDocByCurDocStatus.action?menuId="+FileGrid.getSelected().menuId+"&uuid="+FileGrid.getSelected().parentDocId;
	$.post(path,{},function(data){
		if(data!=null || data!=""){
			data = mini.decode(data);
			if(data.wfStatus!="2"){
				mini.alert("文件未审核通过不允许归档！");
				return;
			}else{
				
			}
		}else{
			mini.alert("文件未审核通过不允许归档！");
			return;
		}
	});*/
}


//文件下载
function goDownFile(){
	var row=FileGrid.getSelected();
	if(row){
		var path="${pageContext.request.contextPath}/profile/findDowloadCompleUpdateById.action?uuid="+row.uuid+"";
		window.open(path);
	}
}

//绑定文件列表事件
function BindFileGrid(){
	FileGrid.on("drawcell",function(e){
		var field=e.field,record=e.record;
		if(field=="delFlag"){
			if(record.version && record.version!=""){
				e.cellStyle="background:green;"
			}else{
				e.cellStyle="background:orange;"
			}
		}
		if(field=="carTao"){
			var htmlStr="";
			if(!record.menuId || record.menuId==""){
				htmlStr="<a style='color:red;margin-right:15px;' title='无来源' href=\"javascript:;\" >----</a>";
			}else{
				var cid=record.parentDocId;
				if(cid.indexOf("_HT_")!=-1){
					cid=cid.split("_HT_")[0];
				}
				htmlStr="<a style='color:#B94A48;margin-right:15px;' href=\"javascript:DocTracking('"+cid+"','"+record.menuId+"');\" >跟踪</a>";
			}
			htmlStr+="<a style='color:blue;' href=\"#\" onclick=\"DocFileEary('"+record.uuid+"')\">补充更新提醒</a>";

			e.cellHtml=htmlStr;
		}
		if(field=="status"){
			if(!e.value || e.value=="---" || e.value=="4"){//待归档
				e.cellHtml="待归档";
				e.cellStyle="color:red";
			}else{//已归档
				e.cellStyle="color:green";
			}
		}
	})
}

//上传附件
function UpLoadFile(){
	var ptjId="";
	if(curPrj!=null){
		ptjId=curPrj.CurDocID;
	}
	var path="/"+gCurDB+"/fmTemplates?openForm&Type=Collect&PrjID="+ptjId;
	CommonOpenDoc(path);
}

//文档追踪
function DocTracking(uuid,menuId){
	var path="${pageContext.request.contextPath}/admin/findDocByCurDocId.action?menuId="+menuId+"&uuid="+uuid+"&type=1";
	CommonOpenDoc(path);
}

//补充更新提醒
var LinObj="";
function DocFileEary(uid){
	LinObj=mini.get("fileGrid").findRow(function(row){
   		if(row.uuid == uid) return true;
	});
	var path="${pageContext.request.contextPath}/admin/rtuMenuById.action?menuId=4028811756d4ca850156d4e5450e0000";
	CommonOpenDoc(path);
}

//文档打开
function CommonOpenDoc(path){
	window.open(path,'_blank','status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes,left=0,top=0,width='+(screen.availWidth - 10) +',height=' + (screen.availHeight - 30));
}

//项目列表刷新
function PrjReLoad(){
	//清空当前选择的项目
	curPrj=null;
	//刷新项目列表
	grid.deselectAll();
	grid.reload();
	//刷新文件列表
	ajaxFileList();
}

//取消选中项目
function GridDeselect(obj){
	obj.deselectAll();
	//清空当前选择的项目
	curPrj=null;
	//刷新文件列表
	ajaxFileList();
}
//取消选中目录
function TreeDeselect(obj){
	obj.deselectAll();
	//清空当前选择的目录
	curNode=null;
	//刷新文件列表
	ajaxFileList();
}

//目录树刷新
function CatReLoad(){
	//清空当前选择的目录
	curNode=null;
	//异步加载目录树
	ajaxLoadCatalog();
	//刷新文件列表
	ajaxFileList();
}

//异步加载目录树
function ajaxLoadCatalog(prjID){
	mini.mask({
            el: document.body,
            cls: 'mini-mask-loading',
            html: '数据加载中...'
     });
		var path="${pageContext.request.contextPath}/profile/fileNewDirectoryListByPower.action";
		if(prjID!=null && prjID!=""){
			path="${pageContext.request.contextPath}/profile/fileNewDirOrgListByPower.action?prjID="+prjID;
		}
		$.post(path,{},function(data){
			if(data.indexOf(",")!=-1){
				//加载到TreeGrid
				TreeGrid.loadList(mini.decode(data.replace(/\n|\r/g,"")),"UID","parentTaskUID");
			}else{
				var path="${pageContext.request.contextPath}/profile/fileDirectoryListByPower.action";
				$.post(path,{},function(data){
					if(data.indexOf(",")!=-1){
						//加载到TreeGrid
						TreeGrid.loadList(mini.decode(data.replace(/\n|\r/g,"")),"UID","parentTaskUID");
					}
				})
			}
			mini.unmask(document.body);
		})
}

//项目列表行点击事件
function PrjdbClick(e){
	curPrj=e.record;
	ajaxLoadCatalog(curPrj.uuid);
	setTimeout("ajaxFileList();",1500);//设置延迟1.5秒执行
	
}

//目录节点点击事件
function CatalogdbClick(e){
	curNode=e.node;
	ajaxFileList();
}
//加载文件列表
function loadAttachGrid(){
	ajaxFileList();
}

//异步加载文件列表
var fileObj={};
function ajaxFileList(){
	//initUpBtn();
	FileGrid.removeRows(FileGrid.getData());
	if(curPrj==null){
		return
	}
	var searchStr="";
	var prjID = "";
	var catalogId = "";
	
	if(curPrj!=null){
		prjID = curPrj.uuid;
	}
	if(curNode!=null){
		if(TreeGrid.isLeaf(curNode)){
			catalogId=curNode.uuid
		}else{
			var subNodes=TreeGrid.getAllChildNodes(curNode);
			$.each(subNodes,function(){
				if(TreeGrid.isLeaf(this)){
					if(catalogId!="")catalogId+=",";
					catalogId+=this.uuid;
				}
			})
		}
	}else{
		$.each(TreeGrid.getList(),function(){
			if(TreeGrid.isLeaf(this)){
				if(catalogId!="")catalogId+=",";
				catalogId+=this.uuid;
			}
		})
	}
	fileObj.prjID=prjID;
	fileObj.catalogId=catalogId;
	fileObj.version=isVersion;
	fileObj.wfStatus="2";
	FileGrid.load(fileObj);
}

	function appendStr(str,field,value){
		if(str!="") str+="&";
		str+=field+"="+value;
		return str;
	}
	
	function CommonRowLink(cell){
		var uuid = cell.row.uuid;
		return "<a style='color:#B94A48;' href='${pageContext.request.contextPath}/profile/findDowloadAdenexaById.action?uuid="+uuid+"' target='_blank'>" + cell.value + "</a>";
	}
	
	//异步加载文件配置角色和用户数据
	function ajaxRoleUser(){
		$.ajax({
			url:"${pageContext.request.contextPath}/profile/findAllFileParamJson.action",
			success:function(text){
				var data=mini.decode(text);
				var isUp=false;
				var isDel=false;
				if(data.upRole && data.upRole!=""){
					$.each(data.upRole.split(","),function(){
						if(orgIds.indexOf(this)!=-1){
							isUp=true;
							return false;
						}
					})
				}
				if(data.upUser && data.upUser!="" && !isUp){
					$.each(data.upUser.split(","),function(){
						if(orgIds.indexOf(this)!=-1){
							isUp=true;
							return false;
						}
					})
				}
				if(data.delRole && data.delRole!=""){
					$.each(data.delRole.split(","),function(){
						if(orgIds.indexOf(this)!=-1){
							isDel=true;
							return false;
						}
					})
				}
				if(data.delUser && data.delUser!="" && !isDel){
					$.each(data.delUser.split(","),function(){
						if(orgIds.indexOf(this)!=-1){
							isDel=true;
							return false;
						}
					})
				}
				if(isUp || "${session.user.userName}"=="admin"){
					$("#btnUp").show();
				}
				if(isDel || "${session.user.userName}"=="admin"){
					$("#btnDel").show();
				}
			}
		});
	}
	
	//弹出目录树维护
	function openDiretory(){
	if(curPrj==null || curPrj.uuid==null || curPrj.uuid==""){
		mini.alert("请先选择项目");
		return;
	}
	mini.open({
			url : "${pageContext.request.contextPath}/file/fileNewDirectoryList.jsp?prjID="+curPrj.uuid,
			title : "项目列表",
			width : 1000,
			height : 500,
			showMaxButton : true,
			ondestroy : function(data) {
				ajaxLoadCatalog(curPrj.uuid);
			}
		});
	}
	
	function webCADLook(){
		var row=FileGrid.getSelected();
		if(row && row.filename.indexOf(".dwg")!=-1){
			window.open("${pageContext.request.contextPath}/file/webCAD/view.jsp?docId="+row.uuid);
		}else{
			mini.alert("请选择一个有效的图纸！");
		}
	}
</script>

</head>
<body text="#000000" bgcolor="#FFFFFF">
<div style="width:200px;height:30px;position:absolute;top:3px;right:0px;z-index:99999;">
		<span style="color:#000;margin-top:4px;float:right;margin-right:25px;">&nbsp;成果型</span>
		<div class="element green" style="float:right;margin:0px;margin-top:10px;height:10px;" onclick="isCG('yes')"></div>
		<span style="color:#000;margin-top:4px;float:right;margin-right:25px;">&nbsp;过程类</span>
		<div class="element orange" style="float:right;margin:0px;margin-top:10px;height:10px;" onclick="isCG('no')"></div>
	</div>
<div id="layout1" class="mini-layout" style="width:100%;height:100%;"  borderStyle="border:solid 1px #aaa;">
    <div title="查询条件" region="north" showHeader="false" height="40" expanded="true" showCollapseButton="false" showSplitIcon="false" >
	<jsp:include page="../promanage/projectSearch.jsp" />
    </div>
    <div title="项目列表" showProxyText="true" showHeader="true" region="west" width="370" showCollapseButton="false" showSplitIcon="true">
       	<div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;' showPageInfo="false" 
			idField='unid' sizeList='[50,100,200,400]' pageSize='50'  onrowclick="PrjdbClick"
			multiSelect='true' showColumnsMenu='true' fitColumns='false' contextMenu="#gridMenu">
			<div property='columns'>
				<div type='indexcolumn' width="50">.No</div>
                		 <div field='projectNo'  width='150' headerAlign='left' align='left' >项目编号</div>
               			 <div field='projectName'  width='150'  headerAlign='left' align='left'>项目名称</div>
						 <div field='uuid' width='120' visible="false">项目id</div>
            		</div>
		</div>
    </div>
    <div title="文件列表" region="center">
	<div class="mini-splitter"  style="width:100%;height:100%;">
    		<div size="200" showCollapseButton="false">
    		<table style='width:60%;'>
				<tr><td>
						<a class="mini-button" iconCls="icon-edit" plain="true" onclick="openDiretory()">维护目录</a>
				</td></tr>
			</table>
			<div class="mini-toolbar toolbar">
				目录树
			</div>
        		<ul id="CatalogTree" class="mini-tree" style="width:100%;height:100%;padding:5px;" 
    showTreeIcon="true" textField="catalogName" idField="UID"  onnodeclick="CatalogdbClick" contextMenu="#treeMenu" >        
			</ul>
    		</div>
    		<div showCollapseButton="true">
        		<div class="mini-toolbar toolbar" id="FileMode">
				<span id="btnUp" gId="fileGrid" id="btnUp" iconCls="icon-upload" style="display:none"></span>
				<a  class="mini-button" id="btnDel" iconCls="icon-remove" onclick="goDelAttach('fileGrid');" style="display:none">删除</a>
				<a  class="mini-button" iconCls="icon-download" onclick="goDownFile();">下载</a>
				<a  class="mini-button" iconCls="icon-node" onclick="goViewAttach('fileGrid');">预览</a>
				<a  class="mini-button" iconCls="icon-node" onclick="showWindow()" style="width:100px;">正式归档</a>
				<a  class="mini-button" iconCls="icon-node" onclick="webCADLook();" style="width:130px;">CAD在线查看</a>
			</div>
			<div class='mini-fit' style='width:100%;height:100%;'>
			<div id='fileGrid' class='mini-pagertree' style='width:100%;height:100%;' showPageInfo="false" 
					 sizeList='[100,200,300,400]' pageSize='100' emptyText="请选择左侧的项目或目录"
					multiSelect='false' showColumnsMenu='true' fitColumns='false' showEmptyText="true"
					idField="UID" parentField="parentTaskUID">
					<div property='columns'>
						<div type='checkcolumn' width="50"></div>
						<div type='indexcolumn' field="delFlag" width="50" headerAlign='center'>.No</div>
						<div field='filename' width='220'  headerAlign='center' align='left'  renderer='CommonRowLink'>文件名称</div>
						<div field='cuNo' width='100'  headerAlign='center' align='left' >纸质文档编号</div>
						<div field='version'  width='100'  headerAlign='center' align='left' >版本号</div>
	                	<div field='createBy' width='100'  headerAlign='center' 	align='left'>上传人</div>
						<div field='createDate' width='150'  headerAlign='center' align='center'  dateFormat='yyyy-MM-dd hh:mm'>上传时间</div>
						<div field='filesize' width='80'  headerAlign='center' align='center'>大小</div>
						<div field='status'  width='80'  headerAlign='center' align='center' >状态</div>
						<div field='carTao' width='150' headerAlign='center' align='center'>操作</div>
						<div field='catalogId' width='120'  visible="false"></div>
						<div field='comeTo' width='120'  visible="false"/>
						<div field='uuid' width='120'  visible="false"/>
            		</div>
			</div></div>
    		</div>        
	</div>
    </div>
</div>
<ul id="gridMenu" class="mini-contextmenu" >
	<li iconCls="icon-tip" onclick="GridDeselect(grid)">取消选中</li>
	<li iconCls="icon-reload" onclick="PrjReLoad()" >刷新</li>
</ul>
<ul id="treeMenu" class="mini-contextmenu" >
	<li iconCls="icon-tip"  onclick="TreeDeselect(TreeGrid)">取消选中</li>
	<li iconCls="icon-reload" onclick="CatReLoad()">刷新</li>
</ul>
<div id="UpFile" class="mini-window" title="正式归档" style="width:450px;height:210px;" 
    showMaxButton="false" showModal="true" allowResize="true" allowDrag="true"  showFooter="true">
	<table cellspacing="1" cellpadding="1" id="tab" style="width:90%;margin:0 auto 20px auto;">
		<tr>
			<td class="td_left" style="line-height:35px;font-size:13px;">纸质文档编号：</td>
			<td class="td_right">
				<input type="text" class="mini-textbox" name="cuNo" style="width:100%" />
			</td>
		</tr>
		<tr>
			<td class="td_left" style="line-height:35px;font-size:13px;">版本号：</td>
			<td class="td_right">
				<input type="text" class="mini-textbox" name="version" style="width:100%" />
			</td>
		</tr>
		<tr>
			<td class="td_left" style="line-height:35px;font-size:13px;">状态：</td>
			<td class="td_right"><input type="text" class="mini-textbox" name="status" value="已归档" style="width:100%" /></td>    
		</tr>
	</table>
	<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		<a  class="mini-button" iconCls="icon-ok" onclick="AjaxUpFile()">确认</a>
	</div>
</div>
</body>
</html>

