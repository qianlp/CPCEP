<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<title>新增或更新按钮维护配置</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta name="content-type" content="text/html; charset=UTF-8">
<%@include file="../resource.jsp" %>
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
</head>

<body text="#000000" bgcolor="#FFFFFF">
	<form method="post" action="${pageContext.request.contextPath}/admin/saveButtonJson.action" name="form1" id="form1">
		<div style="display:none">
			<input name="uuid" value="${buttonModel.uuid}" />
		</div>
		<div class="mini-layout" style="width:100%;height:100%;" borderStyle="border:0">
			<div title="center" region="center" style="border:0;">
				<div class="mini-tabs" activeIndex="0" style="width:99%;height:100%;margin:0 auto" plain="true">
					<div title="基本信息">
						<table border="0" cellpadding="1" cellspacing="2" style="width:95%;margin:0 auto">
							<tr>
								<td style="text-align:right">按钮名称：</td>
								<td><input name="btnName" value="${buttonModel.btnName}" required="true" class="mini-textbox" style="width:100%" vtype="maxLength:6"></td>
								<td style="text-align:right">按钮提示语：</td>
								<td>
									<c:if test="${empty  buttonModel.btnTitle}">
										<input name="btnTitle" value="功能按钮" class="mini-textbox" required="true" style="width:100%">
									</c:if>
									<c:if test="${!empty  buttonModel.btnTitle}">
										<input name="btnTitle" value="${buttonModel.btnTitle}" class="mini-textbox" required="true" style="width:100%">
									</c:if>
								</td>
							</tr>
							<tr>
								<td style="width:100px;text-align:right">默认可见：</td>
								<td>
									<c:if test="${empty  buttonModel.btnIsLook}">
										<input name="btnIsLook" value="1" class="mini-radiobuttonlist" data="[{id:'1',text:'是'},{id:'0',text:'否'}]" style="width:100%">
									</c:if>
									<c:if test="${!empty  buttonModel.btnIsLook}">
										<input name="btnIsLook" value="${buttonModel.btnIsLook}" class="mini-radiobuttonlist" data="[{id:'1',text:'是'},{id:'0',text:'否'}]" style="width:100%">
									</c:if>
								</td>
								<td style="text-align:right">按钮图标：</td>
								<td><input name="btnIcon" value="${buttonModel.btnIcon}" class="mini-textbox" style="width:100%" required="true"></td>
							</tr>
							<tr>
								<td style="width:100px;text-align:right">其它：</td>
								<td><input name="btnStyle" value="${buttonModel.btnStyle}" class="mini-textbox" style="width:100%"></td>

							</tr>
							<tr>
								<td style="text-align:right">操作函数：</td>
								<td colspan=3>
									<c:if test="${empty  buttonModel.btnFunction}">
										<textarea name="btnFunction" class="mini-textarea" required="true" style="width:100%;height:200px" rows="2" cols="20">btnEvent({action:'del'})</textarea>
									</c:if>
									<c:if test="${!empty  buttonModel.btnFunction}">
										<textarea name="btnFunction" class="mini-textarea" required="true" style="width:100%;height:200px" rows="2" cols="20">${buttonModel.btnFunction}</textarea>
									</c:if>
								</td>
							</tr>
							<tr>
								<td style="text-align:right">备注：</td>
								<td colspan=3><textarea name="remark" class="mini-textarea" style="width:100%;height:100px" rows="2" cols="15">${buttonModel.remark}</textarea></td>
							</tr>
							<tr>
							<td></td>
								<td style="color: red" colspan=3>*注释：操作函数中可以写自定义事件、函数</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div title="south" region="south" showSplit="false" showHeader="false" height="35px" splitSize=1 borderStyle="border:0" style="overflow:hidden">
				<div class="mini-toolbar" style="padding:2px;border:0">
					<table style='width:100%;'>
						<tr>
							<td style='width:100%;white-space:nowrap;text-align:right'>
								<a class="mini-button" iconCls="icon-ok" plain="true" style="display:0" onclick="goSubmit()">保存</a> 
								<a class="mini-button" 	iconCls="icon-remove" plain="true" onclick="goDelDoc()" id="remove">删除</a> 
								<a class="mini-button" iconCls="icon-cancel" plain="true" onclick="CloseWindow()">关闭</a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		
<script type="text/javascript">
	mini.parse();
	/*
		描述：删除菜单
	 */
	function goDelDoc() {
		mini.confirm("您确定删除按钮维护吗？","提示",function(r){
			if (r=="ok"){
				var PathUrl ="${pageContext.request.contextPath}/admin/deleteButton.action";
			$.ajax({
				url : encodeURI(PathUrl),
				type : "post",
				dataType:"json",
				data:{"uuid":$("[name='uuid']").val()},
				success : function(data) {
					if(data.success){
						mini.alert(data.msg, "消息提示",function() {
									CloseWindow('close');
								});
					} else {
						mini.alert("删除失败！");
					}
				}
			});
			}
		});
	}
	$(function(){
		if($("[name=uuid]").val()==""){
			$("#remove").hide();
		}
	});
	
	//保存提交
	function goSubmit(){
		//获取表单序列化字符串,
	    //也就是将整个表单的输入数据转换为以&作为分隔符的多个key=value的字符串
	    var formData = $("#form1").serialize();
	    mini.confirm("确认要保存吗？", "操作提示", function(action){
			if(action=="ok"){
				mini.mask({
		            el: document.body,
		            cls: 'mini-mask-loading',
		            html: '数据保存中...'
		    	});
	    $.ajax({
	        //获取表单的action作为url
	        url: $("#form1").attr("action"),
	        type: "POST",
	        data: formData,//把序列化后的表单数据放到这里
	        dataType: "json",
	        async: true,
	        error: function(xhr,status,error){
	            mini.alert("请求出错!");
	        },
	        success: function(result){
	        	if(result.success){
	        		mini.unmask(document.body);
	        		mini.alert(result.msg, "消息提示",function() {
	        			CloseWindow('close');
					});
	        	}else{
	        		mini.unmask(document.body);
	        		mini.alert(result.msg);
	        	}
	        }
	    });
	    //这里一定要返回false来阻止表单的submit
	    return false;
	}});}
</script>
	</form>
</body>
</html>
