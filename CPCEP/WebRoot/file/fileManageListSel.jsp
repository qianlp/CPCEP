<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <title>项目文件管理</title>
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/ht/pagertree.js" type="text/javascript"></script>

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
	.mini-layout-region{
		border:0px;
	}
	.mini-layout-border{
		border:0px;
	}	
</style>

<script language="JavaScript" type="text/javascript">
var TreeGrid=null;
var grid = null;
var FileGrid=null;
//当前选中的项目
var curPrj={uuid:"${param.prjID}"};
//当前选中的节点
var curNode=null;
//文档加载完毕后执行
$(document).ready(function(){
	mini.parse();
	TreeGrid=mini.get("CatalogTree");
	//异步加载目录树
	ajaxLoadCatalog();
	
	//获取文件Grid
	FileGrid=mini.get("fileGrid");
	FileGrid.load();
	//这里可以重新指定自定义的数据装载路径
	FileGrid.setUrl('${pageContext.request.contextPath}/profile/loadListAdenexaForPage.action');
	
})

function onOk() {
	var rows=FileGrid.getSelected();
	if(!rows){
		mini.alert('请选择数据！');
		return;
	}
	$.each(TreeGrid.getList(),function(){
		if(this.uuid==rows.catalogId){
			rows.catalogName=this.catalogName;
		}
	});
	console.log(rows);
	window.CloseOwnerWindow(mini.encode(rows));
}

//绑定文件列表事件
function BindFileGrid(){
	FileGrid.on("drawcell",function(e){
		var field=e.field,record=e.record;
		if(field=="delFlag"){
			if(record.proversion!="0"){
				e.cellStyle="background:green;"
			}else{
				e.cellStyle="background:orange;"
			}
		}
		if(field=="carTao"){
			var htmlStr="<a style='color:#B94A48;margin-right:15px;' href=\"javascript:DocTracking('"+record.uuid+"');\" >跟踪</a>";
			if(record.comeTo=="no"){
				htmlStr="<a style='color:red;margin-right:15px;' title='无来源' href=\"javascript:;\" >----</a>";
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



//文档打开
function CommonOpenDoc(path){
	window.open(path,'_blank','status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes,left=0,top=0,width='+(screen.availWidth - 10) +',height=' + (screen.availHeight - 30));
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
			TreeGrid.loadList(mini.decode(data),"UID","parentTaskUID");
			ajaxFileList();
			mini.unmask(document.body);
		}
	})
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

	//清空查询条件
	function ClearForm(){
		$('input[name="projectNo"]').val("");
		$('input[name="projectName"]').val("");
	}
	
</script>

</head>
<body text="#000000" bgcolor="#FFFFFF">

<form action="">
<div id="layout1" class="mini-layout" style="width:100%;height:100%;"  borderStyle="border:0px">
    <div title="文件列表" region="center"  >
	<div class="mini-splitter"  style="width:100%;height:100%;">
    		<div size="200" showCollapseButton="false">
			<div class="mini-toolbar toolbar">
				目录树
			</div>
        		<ul id="CatalogTree" class="mini-tree" style="width:100%;height:100%;padding:5px;" 
    showTreeIcon="true" textField="catalogName" idField="UID"  onnodeclick="CatalogdbClick" contextMenu="#treeMenu" >        
			</ul>
    		</div>
    		<div showCollapseButton="true">
        		<div class="mini-toolbar toolbar" id="toolbar">
				<a  class="mini-button" iconCls="icon-ok" onclick="onOk()">确认</a>
			</div>
			<div class='mini-fit' style='width:100%;height:100%;'>
			<div id='fileGrid' class='mini-pagertree' style='width:100%;height:100%;' showPageInfo="false" 
					 sizeList='[100,200,300,400]' pageSize='100' emptyText="请选择左侧的目录"
					multiSelect='true' showColumnsMenu='true' fitColumns='false' showEmptyText="true"
					idField="UID" parentField="parentTaskUID">
					<div property='columns'>
						<div type='checkcolumn' width="50"></div>
						<div type='indexcolumn' field="delFlag" width="50" headerAlign='center'>.No</div>
						<div field='filename' width='220'  headerAlign='center' align='left'  renderer='CommonRowLink'>文件名称</div>
						<div field='cuNo' width='100'  headerAlign='center' align='left' >纸质文档编号</div>
						<div field='version'  width='100'  headerAlign='center' align='left' >版本号</div>
	                	<div field='createBy' width='100'  headerAlign='center' 	align='left'>上传人</div>
						<div field='createDate' width='150'  headerAlign='center' align='center'  dateFormat='yyyy-MM-dd hh:mm'>上传时间</div>
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
</form>
</body>
</html>

