<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<title>明细信息数据导入</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">              
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"><META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"><META HTTP-EQUIV="Expires" CONTENT="0">
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">
    html,body,form{
        margin:0;padding:0;border:0;width:100%;height:100%;
    }
</style>
<script language="JavaScript" type="text/javascript">
	function goUploadFile( obj ){
		parent.goToolsBtn(true);
		var  selectPath_File = obj.split("\\");
		if (selectPath_File[selectPath_File.length-1].length>200){
			mini.alert("文件名太长，请不要超过20个字符！");
			parent.goToolsBtn(false);
			return true;
		}
		var  format = selectPath_File[selectPath_File.length-1].split(".")[1].toLowerCase();
		if (format !="xlsx" ) {
	  		mini.alert("对不起，不是.xlsx格式！\n\n请您重新上传xlsx格式文件！");
	  		parent.goToolsBtn(false);
	     	return true;
		}
		return false;
	}
	
	function goSubmit(){
		parent.goToolsBtn(false);
		 //用H5的FormData对象对表单数据进行构造
   		 var formData = new FormData($("#fmImportFile")[0]);//这里要获取零角标我也暂时不知道为什么
			$.ajax({
		        url: $("#fmImportFile").attr("action"),
		        type: "POST",
		        data: formData,
		        dataType: "json",
		        async: true,
		        //要想用jquery的ajax来提交FormData数据,
		        //则必须要把这两项设为false
		         processData: false, 
		        contentType: false,
		        //这里是防表单重复提交,可以忽略
		        success: function(result){
		        	mini.alert(result.msg,"提示",function(){
		        		parent.goCloseDlg("oWinDlg");
		        		parent.parent.window.location.reload();
		        	});
		        }
		    });
		}
</script>
</head>
<body text="#000000" bgcolor="#FFFFFF">

<form method="post" action="${pageContext.request.contextPath}/business/material/ImportExcel.action" enctype="multipart/form-data" name="_fmImportFile" id="fmImportFile">
	<input type="hidden" name="__Click" value="0">
	<table border="0" cellpadding="1" cellspacing="2" style="width:98%;margin:0 auto;top:30px">
		<tr>
			<td align="center"> 
			<input id="ComUpFileID" style="width:95%" onchange=goUploadFile(this.value); type="file" name="file"> </td>
		</tr>
		<tr>
			<td align="center">
			<p style='font-size:14px;color:red'>注：请您先在Excel表格中进行编辑。</p>
			<p style='font-size:14px;color:red'>然后另存为.xlsx格式的文件进行上传！</p>
			</td>
		</tr>
	</table></form>
</body>
</html>
