<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>   
<title>新增职能</title>
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">
html,body,form {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
}
</style>
<script  type="text/javascript">
	var addIdArr=[],delIdArr=[];
	function goSubmit() {
		with (gForm) {
			var strDeptName = mini.getbyName("posName").getValue();
			if (strDeptName == "") {
				alert("请填写职能名称！");
				return false;
			} 
			parent.goToolsBtn(false);
			if (confirm("您确信需要保存吗？")) {
				var posDefaultPerson = mini.get("posDefaultPerson");
				$.each(posDefaultPerson.getData(),function(){
					addIdArr.push(this.id);
				})
				//赋值为0，为了阻止，文档提交就保存。希望通过提交代理后才保存，这样视图中的文档此不会变化，为了处理多人并发保存。
				//同时也为了防止在客户端修改这个值时。导致代理提交就保存
				$("[name=addAllId]").val(addIdArr.join(","));
				$("[name=delAllId]").val(delIdArr.join(","));
				submit();
			} else {
				parent.goToolsBtn(true);
			}
		}
	}
	
	//角色选择
	function setItemRole(e) {
		var listbox = e.sender;
		var posDefaultPerson = mini.get("posDefaultPerson");
		if(listbox.id=="posOrgTree"){
			var node = e.node;
			var isAdd=true;
			$.each(posDefaultPerson.getData(),function(){
				if(this.id==node.id){
					isAdd=false;
					return false;
				}
			})
			if(isAdd){
				posDefaultPerson.addItem({id : node.id,name : node.text});
			}
			
		}else if (listbox.id == "posDefaultPerson") {
			delIdArr.push(listbox.getValue());
			posDefaultPerson.removeItems(posDefaultPerson.findItems(listbox
					.getValue()));
		} 
	}
	
	function LoadOrgTree() {
		//异步加载数据，去掉数据最前端的逗号，使得tree数据格式正确。
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/findOrgTree.action",
			cache : false,
			dataType : "text",
			success : function(data) {
				var posOrgTree = mini.get("posOrgTree");
				var orgData = mini.decode(data);
				if (orgData) {
					posOrgTree.loadList(mini.clone(orgData), "id", "pid");
					posOrgTree.expandLevel(0);
					
				} else {
					posOrgTree.loadList([], "id", "pid");
				}
				
			}
		});
	}
	
	function getPosUser(){
		if("${pos.posNo }"==""){
			return;
		}
		var path="${pageContext.request.contextPath}/admin/findPersonByPos.action";
		$.post(path,{
			posNo:"${pos.posNo }"
		},function(data){
			var posDefaultPerson = mini.get("posDefaultPerson");
			posDefaultPerson.setData(mini.decode(data));
		})
	}
	
	$(document).ready(function(){
		getPosUser();
		LoadOrgTree();
	})
</script>


</head>

<body text="#000000" bgcolor="#FFFFFF">
	<form method="post" action="${pageContext.request.contextPath}/admin/posOperate.action" name="addNewDept">
		<script type="text/javascript">
			var gForm = document.forms[0], gIsNewDoc = 1;var name = "";
		</script>
		<table border="0" cellpadding="1" cellspacing="2" style="width:98%;margin:0 auto">
			<tr>
				<td colspan="2" style="display:none">
					ID：<input name="uuid" value="${pos.uuid }"/>
					<textarea name="addAllId"></textarea>
					<textarea name="delAllId"></textarea>
					<input name="oldPosName" value="${pos.posName }"/>
					<input name="oldPosNo" value="${pos.posNo }"/>
				</td>
			</tr>
			<tr>
				<td align="right" style="width:85px">职能编码：</td>
				<td><input name="posNo" value="${pos.posNo }" class="mini-textbox" allowInput="false"
					emptyText="保存时自动生成"
					style="width:100%;"></td>
			</tr>
			<tr>
				<td align="right" style="width:85px">职能名称：</td>
				<td><input name="posName" value="${pos.posName }" class="mini-textbox"
					style="width:100%;"></td>
			</tr>
			<tr>
				<td style="text-align:center;width:100px">成员：</td>
				<td>
					<div class="mini-listbox" id="posDefaultPerson"
						onitemdblclick="setItemRole"
						style="width:100%;height:180px;float:left" data="[]">
						<div property="columns">
							<div type="indexcolumn" width="5px"></div>
							<div field="name">名称</div>
						</div>
					</div>
					
				</td>
			</tr>
			<tr>
				<td style="text-align:center;width:100px">添加成员：</td>
				<td>
					<div class="mini-panel-border mini-grid-border"
						style="width:100%;height:180px;">
						<div id='posOrgTree' style="width:100%;height:100%"
							class='mini-tree' showTreeIcon='true' textField="text"
							valueField="id" resultAsTree='false' showCheckBox='false'
						    onnodedblclick="setItemRole"
							expandOnLoad='0' expandOnDblClick='false'></div>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
