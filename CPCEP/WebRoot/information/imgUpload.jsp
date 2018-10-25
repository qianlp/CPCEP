<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%@include file="../resource.jsp"%>
<style>
	dl{
		width:180px;float:left;margin-left:10px;
		height:180px;
	}
</style>
<script type="text/javascript">
	var CKEditorFuncNum=1;
	var gDir="${pageContext.request.contextPath}";
	function ajaxRoleUser() {
		$("#AttFiled").html("");
		$.ajax({
					url : gDir+"/profile/findAllAdenexaJson.action?area=${param.area}&parentDocId=${param.parentDocId}",
					success : function(text) {
						var models = eval("(" + text + ")");
						//console.log(models);
						var rows = null;
						var urls = null;
						var AttachName = null;
						var uuids = null;
						for ( var i in models) {
							//console.log(models[i].filepath);
							rows = models[i].filepath;
							if (models[i].filepath != null) {
								AttachName = models[i].filename;
								uuids = models[i].uuid;
								urls = "/profile/findDowloadAdenexaById.action?uuid="+uuids;
								var str = "<dl>";
								str+="<dt><img src=\""+gDir+urls+"\" width=\"164px\" height=\"155px\" /></dt>";
								str+="<dd title='"+AttachName+"'><input name=\"$$attachfile\" type='checkbox' CurDocID='"+uuids+"' value='"+uuids+"' link='"+urls+"'>"+rtuStr(AttachName)+"</dd>";
								str+="</dl>";
								$("#AttFiled").append(str);
							}
						}
					}
				});
	}
	ajaxRoleUser();

	function goAdd() {
		var arrFile = $("#AttFiled input[type=checkbox]:checked");
		if (arrFile.length > 1) {
			alert("仅允许选择一项！");
		} else if (arrFile.length == 1) {
			$.each(arrFile,
					function(item) {
						try {
							var fileUrl = $(this).attr("link");
							var funcNum = getUrlParam('CKEditorFuncNum');
							parent.CKEDITOR.tools.callFunction(funcNum,
									gDir+fileUrl);
						} catch (e) {
							alert("添加有异常，请联系管理员！");
						}
						window.CloseOwnerWindow();
					});
		} else {
			alert("请您先选择已上传的图片！");
		}
	}
	
	function rtuStr(o){
		if(o.length>10){
			return o.substr(0,10)+"...";
		}
		return o;
	}

	function getUrlParam( paramName ) {
	    var reParam = new RegExp( '(?:[\?&]|&)' + paramName + '=([^&]+)', 'i' ) ;
	    var match = window.location.search.match(reParam) ;
	    return ( match && match.length > 1 ) ? match[ 1 ] : null ;
	}

	function goAddMain() {
		var arrFile = $("#AttFiled input[type=checkbox]:checked");
		if (arrFile.length > 1) {
			alert("仅允许选择一项！");
		} else if (arrFile.length == 1) {
			$.each(
				arrFile,
				function() {
					try {
						var fileUrl = $(this).attr("link");
						var funcNum = getUrlParam('CKEditorFuncNum');
						parent.CKEDITOR.tools.callFunction(
								funcNum, gDir+fileUrl);
						parent.document.forms[0].newImgAddress.value = fileUrl;
					} catch (e) {
						alert("添加有异常，请联系管理员！");
					}
					window.CloseOwnerWindow();
				});
		} else {
			alert("请您先选择已上传的图片！");
		}
	}
</script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body style="overflow:hidden;margin:0px;widht:100%;height:100%;">
	<div class="mini-toolbar" style="border-top:0px;border-left:0px;border-right:0px;">
			<a class="mini-button jsEn" plain="false" id="jsEn" iconCls="icon-ok"
				href="javascript:goAdd();" style="margin-right:15px;">确认选择</a>
			<a class="mini-button jsGo" plain="false" id="jsGo" iconCls="icon-goto"
				href="javascript:goAddMain();" style="margin-right:15px;">设置为主题图片</a>
	</div>
	<div class="mini-fit" style="width:100%;height:100%;overflow:hidden;">
	<div id="AttFiled" style="width:100%;height:100%;border:1px solid #ccc;overflow-y:scroll">
	</div>
	</div>
</body>
</html>
