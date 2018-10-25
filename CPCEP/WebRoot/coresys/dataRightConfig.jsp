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
<title>数据权限管理</title>
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 1;
	width: 100%;
	height: 100%;
}
.divP{
	width:100%;
	min-height:50px;
	border-bottom:1px solid #ccc;
}

.one{
	height:70px;
}

.mini-layout-region{
	border-top:0px;
}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/admin/dataRightOperate.action" name="dataRight">
		<div style="display:none">
			<input name="menuId" value="${param.menuId }"  />
			<input name="uuid" value="${comObj.uuid }"  />
			<textarea name="diyRight" 	>${comObj.diyRight }</textarea>
			<textarea name="diyPos" 	>${comObj.diyPos }</textarea>
			<textarea name="diyRole" 	>${comObj.diyRole }</textarea>
			<textarea name="diyUser" 	>${comObj.diyUser }</textarea>
			<textarea name="allPos" 	>${comObj.allPos }</textarea>
			<textarea name="allRole" 	>${comObj.allRole }</textarea>
			<textarea name="allUser" 	>${comObj.allUser }</textarea>
		</div>
		<div class="divP one">
			<div class="mini-toolbar" style="border-top:0px;border-left:0px;border-right:0px;margin-bottom:5px;">
				<b>默认</b>
			</div>
			<input class="mini-checkboxlist" name="rightType" 
			<c:if test="${not empty comObj.rightType}">
				value="${comObj.rightType }" 
			</c:if>
			<c:if test="${empty comObj.rightType}">
				value="0" 
			</c:if>
			multiSelect="false" data="[{id:'0',text:'个人'},{id:'1',text:'部门'}]" style="margin-left:5px;"
			 />
		</div>
		<div class="divP" style="height:610px;">
			<div class="mini-toolbar" style="border-top:0px;border-left:0px;border-right:0px;margin-bottom:5px;">
				<b>自定义</b>
			</div>
			<div style="width:100%;padding-bottom:5px;">
				<div class="mini-toolbar" style="border-bottom:0px;margin-left:5px;margin-right:5px;">
					<a class="mini-button" iconCls="icon-add"  onclick="listAdd(4)"></a>
					<a class="mini-button" iconCls="icon-close" onclick="listDel(4)" ></a>
				</div>
				<div style="margin-left:5px;margin-right:5px;">
				<div id="allGrid" class="mini-datagrid" style="height:250px;" multiSelect="true"  allowCellEdit="true" allowCellSelect="true"
    				showPager="false" oncellbeginedit="cellbeginedit" ondrawcell="drawcell" oncellcommitedit="cellcommitedit"
    			>
        			<div property="columns">
            			<div type="checkcolumn" ></div>        
            			<div field="type" width="100" type="comboboxcolumn" headerAlign="center" >类别
            				<input property="editor" class="mini-combobox" data="[{id:'0',text:'人员'},{id:'1',text:'职能'},{id:'2',text:'角色'}]" style="width:100%;" />
            			</div>    
            			<div field="name" width="150" type="treeselectcolumn" headerAlign="center" >名称
            				<input property="editor" class="mini-treeselect" style="width:100%;" multiSelect="true" expandOnLoad="0" />
            			</div>    
            			<div field="dept" width="320" type="treeselectcolumn" headerAlign="left" align="left">部门
            				<input property="editor" class="mini-treeselect" style="width:100%;" multiSelect="true"
            				 showFolderCheckBox="true" expandOnLoad="0"
            				 url="${pageContext.request.contextPath}/admin/findAllDeptsJson.action"
            				/>
            			</div>    
         			</div>
    			</div>
    			</div>
			</div>
			<div style="width:150px;margin-left:5px;margin-bottom:5px;float:left;">
				<div class="mini-toolbar" style="border-bottom:0px;">
					<a class="mini-button" iconCls="icon-add"  onclick="listAdd(1)"></a>
					<a class="mini-button" iconCls="icon-close" onclick="listDel(1)" ></a>
				</div>
				<div id="listbox1" class="mini-listbox" style="width:100%;height:250px;">
					<div property="columns">
                        <div header="可查看本部门数据职能" field="text"></div>
                    </div>
            	</div>
			</div>
			<div style="width:150px;margin-left:5px;margin-bottom:5px;float:left;">
				<div class="mini-toolbar" style="border-bottom:0px;">
					<a class="mini-button" iconCls="icon-add"  onclick="listAdd(2)"></a>
					<a class="mini-button" iconCls="icon-close" onclick="listDel(2)" ></a>
				</div>
				<div id="listbox2" class="mini-listbox" style="width:100%;height:250px;">
					<div property="columns">
                        <div header="可查看本部门数据角色" field="text"></div>
                    </div>
            	</div>
			</div>
			<div style="width:150px;margin-left:5px;margin-bottom:5px;float:left;">
				<div class="mini-toolbar" style="border-bottom:0px;">
					<a class="mini-button" iconCls="icon-add"  onclick="listAdd(3)"></a>
					<a class="mini-button" iconCls="icon-close" onclick="listDel(3)" ></a>
				</div>
				<div id="listbox3" class="mini-listbox" style="width:100%;height:250px;">
					<div property="columns">
                        <div header="可查看本部门数据人员" field="text"></div>
                    </div>
            	</div>
			</div>
			
		</div>
		<div class="divP" style="height:320px;">
			<div class="mini-toolbar" style="border-top:0px;border-left:0px;border-right:0px;margin-bottom:5px;">
				<b>全局</b>
			</div>
			<div style="width:150px;margin-left:5px;margin-bottom:5px;float:left;">
				<div class="mini-toolbar" style="border-bottom:0px;">
					<a class="mini-button" iconCls="icon-add"  onclick="listAdd(5)"></a>
					<a class="mini-button" iconCls="icon-close" onclick="listDel(5)" ></a>
				</div>
				<div id="listbox5" class="mini-listbox" style="width:100%;height:250px;">
					<div property="columns">
                        <div header="可查看所有数据职能" field="text"></div>
                    </div>
            	</div>
			</div>
			<div style="width:150px;margin-left:5px;margin-bottom:5px;float:left;">
				<div class="mini-toolbar" style="border-bottom:0px;">
					<a class="mini-button" iconCls="icon-add"  onclick="listAdd(6)"></a>
					<a class="mini-button" iconCls="icon-close" onclick="listDel(6)" ></a>
				</div>
				<div id="listbox6" class="mini-listbox" style="width:100%;height:250px;">
					<div property="columns">
                        <div header="可查看所有数据角色" field="text"></div>
                    </div>
            	</div>
			</div>
			<div style="width:150px;margin-left:5px;margin-bottom:5px;float:left;">
				<div class="mini-toolbar" style="border-bottom:0px;">
					<a class="mini-button" iconCls="icon-add"  onclick="listAdd(7)"></a>
					<a class="mini-button" iconCls="icon-close" onclick="listDel(7)" ></a>
				</div>
				<div id="listbox7" class="mini-listbox" style="width:100%;height:250px;">
					<div property="columns">
                        <div header="可查看所有数据人员" field="text"></div>
                    </div>
            	</div>
			</div>
		</div>
		<div class="mini-toolbar" style="border:0px;text-align:center;">
				<a class="mini-button" iconCls="icon-save" onclick="toSave()" >保存配置</a>
		</div>
<div id="win1" class="mini-window" title="选择列表" style="width:300px;height:450px;" 
     showFooter="true" showModal="true" 
    >
    <div id='selTree' class='mini-tree' style="height:100%;" showCheckBox='true' showFolderCheckBox="false" expandOnLoad='0'  multiSelect="true"
	resultAsTree="false"
	>
	</div>
    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
        <a class="mini-button" iconCls="icon-ok" onclick="enterWin" >确认</a>
    </div>
</div>
</form>
	<script type="text/javascript">
		mini.parse();
		var gDir="${pageContext.request.contextPath}";
		function listAdd(type){
			if(type=="1" || type=="5"){
				showWin(gDir+"/admin/findJsonPosAll.action",type);
			}
			if(type=="2" || type=="6"){
				showWin(gDir+"/admin/findMenuRoleJson.action",type);
			}
			if(type=="3" || type=="7"){
				showWin(gDir+"/admin/findOrgTree.action",type);
			}
			if(type=="4"){
				mini.get("allGrid").addRow({});
			}
		}
		
		function listDel(type){
			if(type=="1" || type=="5"){
				mini.get("listbox"+type).removeItem(mini.get("listbox"+type).getSelected());
			}
			if(type=="2" || type=="6"){
				mini.get("listbox"+type).removeItem(mini.get("listbox"+type).getSelected());		
			}
			if(type=="3" || type=="7"){
				mini.get("listbox"+type).removeItem(mini.get("listbox"+type).getSelected());
			}
			if(type=="4"){
				mini.get("allGrid").removeRows(mini.get("allGrid").getSelecteds());
			}
		}
		
		var gType="";
		function showWin(url,type){
			gType=type;
			mini.get("selTree").setUrl(url);
			mini.get("win1").show();
		}
		
		function enterWin(){
			mini.get("listbox"+gType).addItems(mini.get("selTree").getCheckedNodes(false));
			mini.get("win1").hide();
		}
		
		function drawcell(e){
			if(e.field=="name" && e.record.nameT && e.record.nameT!=""){
				e.cellHtml=e.record.nameT;
			}
		}
		
		function cellcommitedit(e){
			if(e.field=="name"){
				var nodes=e.editor.getCheckedNodes();
				var nameT="";
				$.each(nodes,function(){
					if(nameT!="")nameT+=",";
					nameT+=this.text;
				})
				e.record.nameT=nameT;
			}
		}
		
		function cellbeginedit(e){
			if(e.field=="name" && e.record.type && e.record.type!=""){
				if(e.record.type=="0"){
					e.editor.setUrl(gDir+"/admin/findOrgTree.action");
				}else if(e.record.type=="1"){
					e.editor.setUrl(gDir+"/admin/findJsonPosAll.action");
				}else if(e.record.type=="2"){
					e.editor.setUrl(gDir+"/admin/findMenuRoleJson.action");
				}
			}
		}
		
		function saveFun(){
			$("[name=diyRight]").val(mini.encode(mini.get("allGrid").getData()));
			$("[name=diyPos]").val(mini.encode(mini.get("listbox1").getData()));
			$("[name=diyRole]").val(mini.encode(mini.get("listbox2").getData()));
			$("[name=diyUser]").val(mini.encode(mini.get("listbox3").getData()));
			$("[name=allPos]").val(mini.encode(mini.get("listbox5").getData()));
			$("[name=allRole]").val(mini.encode(mini.get("listbox6").getData()));
			$("[name=allUser]").val(mini.encode(mini.get("listbox7").getData()));
		}
		
		function toSave(){
			mini.mask({
				el: document.body,
				cls: 'mini-mask-loading',
				html: '数据提交中...'
		    });
			saveFun();
			setTimeout(function(){
				//alert(111)
				document.forms[0].submit();
			},1500);
		}
		
		function afterLoad(){
			mini.get("allGrid").setData(mini.decode($("[name=diyRight]").val()));
			mini.get("listbox1").setData(mini.decode($("[name=diyPos]").val()));
			mini.get("listbox2").setData(mini.decode($("[name=diyRole]").val()));
			mini.get("listbox3").setData(mini.decode($("[name=diyUser]").val()));
			mini.get("listbox5").setData(mini.decode($("[name=allPos]").val()));
			mini.get("listbox6").setData(mini.decode($("[name=allRole]").val()));
			mini.get("listbox7").setData(mini.decode($("[name=allUser]").val()));
		}
		
		$(document).ready(function(){
			if($("[name=menuId]").val()==""){
				mini.alert("请先选择一个模块！");
				return;
			}
			afterLoad();
		})
	</script>
</body>
</html>
