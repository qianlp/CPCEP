<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>公告</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="../resource.jsp"%>
	<style>
body {
	font-size: 12px;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-repeat: repeat-x;
}

.tab {
	margin-top: 1px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
	border: 1px solid #CCCCCC;
}

td,th {
	font-size: 14px;
}

a:link {
	text-decoration: none;
	color: black;
}

a:visited {
	text-decoration: none;
	color: black;
}

a:hover {
	text-decoration: none;
	color: black;
}

a:active {
	text-decoration: none;
	color: black;
}

.divClassForRead {
	font: 11pt 宋体;
	line-height: 20px;
	font-weight: normal;
}

img {
	border-width: 0px;
}

* {
	padding: 0px;
	margin: 0px;
}

* li {
	list-style: none;
}

a {
	text-decoration: none;
	color: #20537A;
}

a:hover {
	text-decoration: underline;
}

.clearfix:after {
	content: "\0020";
	display: block;
	height: 0;
	clear: both;
}

.clearfix {
	_zoom: 1;
}

*+html .clearfix {
	overflow: auto;
}

p {
	margin-bottom: 0px
}

.subject {
	font-size: 24px;
	font-weight: bold;
	line-height: 35px;
	color: #be0000;
}

.style1 {
	font-size: 14px;
	font-weight: bold;
	line-height: 30px;
}

.bottom_text {
	color: #113159;
	font-size: 12px;
	vertical-align: middle;
}

.STYLE12 {
	color: #FFFFFF;
	font-size: 12px;
}
</style>
</head>
<body text="#000000" bgcolor="white" margin=0 style="width:100%">
	<table width="962" border="0" align="center" cellpadding="0"
		cellspacing="0" style="margin:0 auto">
		<tr>
			<td>
				<div
					style="background-repeat:no-repeat;width:100%;height:50px;float:left;"></div>
			</td>
		</tr>
	</table>
	<table class="tab" width="960" height="800" border="0" cellpadding="0"
		cellspacing="0" align="center" style="margin:0 auto">
		<tr>
			<td width="940" height="770"><table width="940" height="770"
					border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="940" height="60" valign="bottom"><table
								width="940" height="30" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="65" height="30">&nbsp;</td>
									<td width="810" height="30" align="center"><span
										class="subject">${requestScope.notice.name}</span></td>
									<td width="65" height="30">&nbsp;</td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td height=40 width=940 align=right valign=middle>
							<table width="960" height="30" border="0" cellpadding="0"
								cellspacing="0" align="center" style="margin:0 auto">
								<tr>
									<td width="30">&nbsp;</td>
									<td width="900"
										style="border-bottom-width: 1px;border-bottom-style: dashed;border-bottom-color: #0066ff;"
										align="center">发布人：${requestScope.notice.createBy}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布时间：${requestScope.notice.createDate}
									</td>
									<td width="30">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="960" height="670" valign="top">
							<table width="960" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="60">&nbsp;</td>
									<td width="810" valign="top" align="left"><br>
										<div style="display:inline1;" class=divClassForRead>
											<div class=divClassForRead>
												<span style="line-height: 150%; font-family: 宋体; font-size: 13.5pt">
													${requestScope.notice.noticeMessage}
												</span>
												<br />
												<br />
											</div>
											<br/>
										</div>
									</td>
									<td width="60">&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td colspan="2" id="fileAtt" style="padding-bottom:50px;">
										
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table></td>
		</tr>
	</table>
	
	<p>&nbsp;</p>
	<script>
	var gDir="${pageContext.request.contextPath}";
//文件下载
function goDownFile(id){
	var path="${pageContext.request.contextPath}/profile/findDowloadAdenexaById.action?uuid="+id+"";
	window.open(path);
}
$.ajax({
	url : "${pageContext.request.contextPath}/profile/findAllAdenexaJson.action?area=att_att_news&parentDocId=${requestScope.notice.curDocId}",
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
				urls = rows.substring(rows
						.lastIndexOf("upload"));
				urls = "${pageContext.request.contextPath}/"
						+ urls;
				var str="<a href=\"javascript:;\" style='color:#be0000;'  ><b>"+AttachName+"</b></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:goView('"+uuids+"')\"  style='color:blue'><b>预览</b></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:goDownFile('"+uuids+"')\"  style='color:blue'><b>下载</b></a><br/>";
				$("#fileAtt").append(str);
			}
		}
	}
});

function goView(uuid){
	var path = gDir+"/profile/findViewAdenexaById.action?uuid=";
	getViewUpload(uuid,path);
}

function getViewUpload(uuid,path){
	if(uuid==""){
		mini.alert("请选择预览的文件！");
		return;
	}
	window.open(path+uuid);
}
</script>
</body>
</html>
