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
<title>系统界面</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<link href="${pageContext.request.contextPath}/css/common/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/home/default/BgSkin.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/home/style.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/css/home/image.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/css/home/default/koala.min.1.5.js" type="text/javascript"></script>
<style type="text/css">
	body{
		padding:0px;
		overflow:hidden;
	}
</style>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	var gImgList=[];
</script>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/admin/imgConfigSave.action" name="form1" id="form1" enctype="multipart/form-data">
	<div style="display:none">
		<input name="userId" type="text" value="${session.user.uuid}" />	
		<input id="UserImgUp" type="file" name="userImg" />	
	</div>
	<div class="col-md-12" style="width:100%;margin:0px;padding:0px;">
	<div class="widget-container fluid-height" id="userPage" style="height:auto;border-radius:4px;padding:0px;">
	<div style="height:300px;">
		<div style="height:100%;width:100%;">
			<div style="width:225px;float:left;height:100%;border-right:1px solid #ccc">
				<div id="toolbar1" class="mini-toolbar" style="padding:2px;border-top:0px;border-left:0px;border-right:0px;">
        				使用过的头像
    				</div>
				<div id="imgList" style="width:100%;height:274px;overflow-y:scroll;padding-top:10px;padding-bottom:10px;">
					
				</div>
			</div>
			<div style="width:150px;float:left;height:100%;text-align:center;padding-top:50px;">
				<a href="#" class="avatar-circle" style="width:100px;height:100px;border-radius:50px;">
					<s:if test="#session.imgPath eq '' ">
						<img  id="userImg" style="width:100px;height:100px;border-radius:50px;" src="${pageContext.request.contextPath}/img/common/pic_user.gif" />
					</s:if>
					<s:else>
						<img  id="userImg" style="width:100px;height:100px;border-radius:50px;" src="${pageContext.request.contextPath}/admin/showPicture.action?userId=${session.user.uuid}" />
					</s:else>
				</a>
				<div style="width:100%;text-align:center;padding-top:3px;margin-top:10px;">
					<a href="javascript:SelectImg()" title="" class="mini-button"  onclick="">选择</a>
				</div>
				<div style="width:100%;text-align:center;padding-top:3px;">
					<a href="javascript:goSubmit()" title="" class="mini-button"  onclick="">上传</a>
				</div>
			</div>
		</div>
	</div>
    </div>
	</div>
</form>
	<script>
	mini.parse();
	$(document).ready(function(){
		mini.mask({
	     	el: document.body,
	     	cls: 'mini-mask-loading',
	     	html: '加载中...'
	     });
		imgCheck();
		getImgList();
		mini.unmask(document.body);
	})
	function SelectImg(){
		$("#UserImgUp").click();
	}

	function goSubmit(){
		mini.mask({
	     	el: document.body,
	     	cls: 'mini-mask-loading',
	     	html: '数据提交中...'
	     });
		document.forms[0].submit();
	}

	function imgCheck(){
		var input = document.getElementById("UserImgUp");
		if(typeof FileReader==='undefined'){ 
			mini.alert("当前浏览器无法检测本地图片路径！");
	    		//input.setAttribute('disabled','disabled'); 
		}else{ 
	    	input.addEventListener('change',readFile,false); 
		}
	}

	function readFile(){
	    var file = this.files[0];
	     $("[name=CurImg]").val(file.name);
	    var result = document.getElementById("userImg"); 
	    
	    if(!/image\/\w+/.test(file.type)){ 
	        mini.alert("文件必须为图片！"); 
	        return false; 
	    } 
	    var reader = new FileReader(); 
	    reader.readAsDataURL(file); 
	    reader.onload = function(e){ 
	        result.src=this.result; 
	    } 
	}
	
	function getImgList(){
		var path=gDir+"/admin/imgConfigList.action";
		$.post(path,{
			userId:"${session.user.uuid}"
		},function(data){
			gImgList=mini.decode(data);
			writeImgList();
		})
	}

	function writeImgList(){
		gImgList=mini.decode(gImgList);
		$.each(gImgList,function(){
			if(this.status=="1"){
				return true;
			}
			$("#imgList").append("<div id='"+this.uuid+"' style='width:100%;padding-left:10px;height:130px;'>"
			+"<a href=\"#\" class=\"avatar-circle\" style=\"width:100px;height:100px;border-radius:50px;float:left;\">"
			+"<img id='"+this.uuid+"_img' style=\"width:100px;height:100px;border-radius:50px;\" width=\"100\" height=\"100\" imgSrc='"+this.imgConfig+"' src='${pageContext.request.contextPath}/admin/showPicture.action?uuid="+this.uuid+"' /></a>"
			+"<div style='width:70px;height:100px;text-align:center;float:left;padding-top:10px;padding-left:10px;'>"
			+"<a href='javascript:upUserImg(\""+this.uuid+"\")' iconCls='icon-ok' style='margin-top:10px'  title=\"\" class=\"mini-button\" ></a>&nbsp;"
			+"<a href='javascript:removeImg(\""+this.uuid+"\")' iconCls='icon-remove' title=\"\" style='margin-top:10px' class=\"mini-button\" ></a></div>"
			+"</div>")
		})
		mini.parse();
	}

	function removeImg(id){
		mini.mask({
	     	el: document.body,
	     	cls: 'mini-mask-loading',
	     	html: '数据提交中...'
	     });
		var path=gDir+"/admin/imgConfigDel.action";
		$.post(path,{
			uuid:id,
			userId:"${session.user.uuid}"
		},function(data){
			mini.alert("移除成功！");
			$("#"+id).remove();
			mini.unmask(document.body);
		})
	}

	function upUserImg(cid){
		mini.mask({
	     	el: document.body,
	     	cls: 'mini-mask-loading',
	     	html: '数据提交中...'
	     });
		var path=gDir+"/admin/imgConfigUpdate.action";
		$.post(path,{
			uuid:cid,
			userId:"${session.user.uuid}"
		},function(data){
			mini.alert("修改成功！");
			parent.closeImgWin($("#"+cid+"_img").attr("imgSrc"));
			mini.unmask(document.body);
		})
	}
	</script>
</body>
</html>
