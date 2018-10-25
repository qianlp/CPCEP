<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<title>新增用户</title>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>   
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
<script language="JavaScript" type="text/javascript">
var isCM=true;
		function goSubmit() {
		with (gForm) {
			var strPerEName = mini.getbyName("user.userPerEname").getValue();
			if (strPerEName == "") {
				alert("请填写英文名称！");
				return false;
			}
			var strPerCName = mini.getbyName("user.userName").getValue();
			if (strPerCName == "") {
				alert("请填写中文名称！");
				return false;
			} else {
				if (strPerCName.length > 20) {
					alert("中文名称长度不能超过20！");
					return false;
				}
			}
			
			var strDeptId = mini.getbyName("user.userDeptId").getValue();
			if (strDeptId == "") {
				alert("请选择部门名称！");
				return false;
			}
			
			if(!isCM){
				mini.alert("与其他人员重名！");
				return;
			}
			
			parent.goToolsBtn(false);
			if (confirm("您确信需要保存吗？")) {
				submit();
			} else {
				parent.goToolsBtn(true);
			}
		}
	}
	function afterSubmit(){
		parent.LoadUserInfo();
		goCloseDlg("oWinDlg");
	}

	
	/*
		描述：非中文
	 */
	function onEnglishAndNumberValidation(e) {
		if (e.isValid) {
			if (isEnglishAndNumber(e.value) == false) {
				e.errorText = "必须输入英文+数字";
				e.isValid = false;
			}
		}
	}

	/* 
		描述：是否英文+数字
	 */
	function isEnglishAndNumber(v) {
		var re = new RegExp("^[0-9a-zA-Z\_]+$");
		if (re.test(v))
			return true;
		return false;
	}
	//---------------------
	/*
	 描述：
	     根据父节点id选择同层级部门
	 参数：
	  e：部门树节点对象
	 */
	function loadSameDeptUser(e){
		var deptId = mini.getbyName("user.userDeptId").getValue();
		if(deptId){
			e.sender.load(encodeURI("${pageContext.request.contextPath}/admin/findUsersByDeptId.action?user.userDeptId="+deptId));
		}else{
			alert("请先选择部门!");
			return;
		}
	}
	
	function SelectPosition(obj){
		var objCombox =mini.getbyName("sameLevelNodeId");
		if(obj.value=="0"){
			cleanTreeSel("sameLevelNodeId");
			objCombox.disable();
		}else{
			objCombox.enable();
		}
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
		if(e.sender.getName()=="user.userDeptId"){
			cleanTreeSel("sameLevelNodeId");
		}
	}
	/*
	 描述：
	 选择部门中加入清除图标，点击清除值
	 参数：
	 e：部门树节点对象
	 */
	function onCloseClick(e) {
		e.sender.setValue("");
		e.sender.setText("无");
		if(e.sender.getName()=="user.userDeptId"){
			cleanTreeSel("sameLevelNodeId");
		}
	}
	
	
	//清楚Tree-select中控件的text、value及data
	function cleanTreeSel(name){
		var objCombox =mini.getbyName(name);
		objCombox.setValue();
		objCombox.setText();
		objCombox.setData([]);
	}
	
	function posChange(e){
		if(e.value!=""){
			var rows=e.sender.getSelecteds();
			var names="";
			var ids="";
			$.each(rows,function(){
				if(names!=""){names+=","}
				if(ids!=""){ids+=","}
				names+=this.posName;
				ids+=this.uuid;
			})
			mini.getbyName("user.posId").setValue(ids);
			mini.getbyName("user.posName").setValue(names);
		}else{
			mini.getbyName("user.posId").setValue("");
			mini.getbyName("user.posName").setValue("");
		}
	}
	
	function nameChange(){
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/findUserByName.action?userName="+mini.getbyName("user.userName").getValue(),
			cache : true,
			dataType : "text",
			success : function(data) {
				if(data.toString()=="true"){
					isCM=true;
				}else{
					isCM=false;
					mini.alert("与其他人员重名！");
				}
			}
		})
	}
</script>

</head>

<body>
	<form method="post" action="${pageContext.request.contextPath}/admin/updateUser.action" onsubmit="afterSubmit()">
		<input name="user.uuid" value="${user.uuid }" type="hidden">
		<div style="display:none">
		<input name="user.posId" class="mini-textbox" value="${user.posId }" >
		<input name="user.posName" class="mini-textbox" value="${user.posName }" >
		</div>
		<div style="width:98%;margin:0 auto">
			<fieldset style="padding-left:10px;padding-right:10px">
				<legend>
					<span>基本信息</span>
				</legend>
				<table border="0" cellpadding="1" cellspacing="2"
					style="width:98%;margin:0 auto">
					<tr>
						<td align="right">姓名(中文)：</td>
						<td><input name="user.userName" value="${user.userName }"  onblur="nameChange" class="mini-textbox"
							 style="width:100%"></td>
						<td align="right">姓名(拼音)：</td>
						<td><input name="user.userPerEname" value="${user.userPerEname }" class="mini-textbox"
							 onvalidation="onEnglishAndNumberValidation"
							style="width:100%"></td>
					</tr>
					<tr>
						<td style="width:80px" align="right">编号：</td>
						<td><input name="user.userNo" value="${user.userNo }" class="mini-textbox"
							allowInput='false' style="width:100%"></td>
						<td align="right">邮箱：</td>
						<td><input name="user.userMail" value="${user.userMail }" class="mini-textbox"
							style="width:100%"></td>
						
					</tr>
					
					<tr>
						<td align="right">部门名称：</td>
						<td><input name="user.userDeptId" value="${user.userDeptId}" 
							class="mini-treeselect" url="${pageContext.request.contextPath}/admin/findAllDeptsJson.action" expandOnLoad="false" 
							textField="text" valueField="id" parentField="pid" onnodeclick="SelectOrgDept" showClose="true"
							oncloseclick="onCloseClick" style="width:100%" ></td>
						<!--<td align="right">兼职部门：</td>
						<td><input name="OtherDeptHrName" value="" text="无"
							class="mini-treeselect"
							url="${pageContext.request.contextPath}/admin/findAllDeptsJson.action" expandOnLoad="false" 
							textField="text" valueField="id" parentField="pid" onnodeclick="SelectOrgDept" showClose="true"
							oncloseclick="onCloseClick" style="width:100%" >-->
						<!--<td align="right">口令：</td>
						<td><input name="user.userPassword" value="${user.userPassword }" class="mini-textbox"  style="width:100%"></td>-->
					</tr>
					
					<tr>
						<td align="right">民族：</td>
						<td><input name="user.userEthnic" value="${user.userEthnic }" class="mini-textbox"
							style="width:100%"></td>
						<td align="right">性别：</td>
						<td>
							<select name="user.userSex" value="${user.userSex}" class="mini-radiobuttonlist">
								<option value="男">男
								<option value="女">女
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">电话：</td>
						<td><input name="user.userTelephone" value="${user.userTelephone }" class="mini-textbox"
							style="width:100%"></td>

						<td align="right">分机号：</td>
						<td><input name="user.userOtherTel" value="${user.userOtherTel }" class="mini-textbox"
							  style="width:100%"></td>
					</tr>
					<tr>
						<td align="right">传真：</td>
						<td><input name="user.userFax" value="${user.userFax }" class="mini-textbox"
							style="width:100%"></td>
						<td align="right">职能：</td>
						<td><input name="user.posNo" value="${user.posNo }" class="mini-combobox"
							url="${pageContext.request.contextPath}/admin/findAllPosJson.action"
							valueField="posNo" textField="posName" onvaluechanged="posChange"
							multiSelect="true"
							style="width:100%"></td>
					</tr>
					<tr>
						<td align="right">个人位置：</td>
						<td colspan=3>
							<label> 
								<input type="radio" name="Position" value="0" checked onclick="SelectPosition(this)" style="border-width:0px"/>保持当前位置
							</label>
							<label> 
								<input type="radio" name="Position" value="1" onclick="SelectPosition(this)" style="border-width:0px">在
							</label> 
							<input name="sameLevelNodeId" class="mini-combobox" style="width:150px;" textField="userName" valueField="userId" emptyText="请选择..." showNullItem="true" nullItemText="请选择..." onbeforeshowpopup="loadSameDeptUser" enabled="false">之前
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset style="padding-left:10px;padding-right:10px">
		<legend>
		<span>密码重置</span>
		</legend>
		<table border="0" cellpadding="1" cellspacing="2" style="width:98%;margin:0 auto">
			<tr>
				<td align="right">新密码：</td>
				<td><input name="passwordNew" class="mini-password" value="" /></td>
				<td><a class="mini-button" iconCls="icon-ok" style="display:0" id="btnSave" onclick="updatePassWord()">重置</a></td>
			</tr>
		</table>
	</fieldset>
		</div>
	</form>
	
	<script type="text/javascript">
			var gForm = document.forms[0];
			
			function updatePassWord(){
				if(mini.getbyName("passwordNew").getValue()==""){
					alert("请输入新密码！");
					return;
				}
				mini.mask({
			     	el: document.body,
			     	cls: 'mini-mask-loading',
			     	html: '请稍候...'
			     });
				var path="${pageContext.request.contextPath}/admin/resetPwd.action";
				$.post(path,{
					userId:"${user.uuid }",
					passwordNew:mini.getbyName("passwordNew").getValue()
				},function(data){
					mini.unmask(document.body);
					mini.alert("重置成功！");
				})
			}
	</script>
</body>
</html>
