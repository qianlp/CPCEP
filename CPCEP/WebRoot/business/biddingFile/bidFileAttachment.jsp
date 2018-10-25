<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>${menu.menuName}</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="stylesheet" type='text/css' href="../../css/form/loaders.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/bootstrap-theme.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/form.css" />

	<%@include file="../../resource.jsp" %>
	<script src="../../js/form/language_ZN.js" type="text/javascript"></script>

	<script src="../../js/form/wf-mini.js" type="text/javascript"></script>

	<%
		request.setAttribute("isRead", 1);
	%>
	<script>
        var gDir="${pageContext.request.contextPath}";
        var gIsFile = -1;	// 是否使用原始附件控件
        var gIsRead = 1;
        var gIsShowSign = 1;
        var gForm = 1;
        var gIsEditDoc = 1;
        var gIsNewDoc = 1;

	</script>
</head>
<body text="#000000" bgcolor="#FFFFFF"  style='padding:0px;width:100%;height:100%;background:#f3f3f3;'>
<link rel="stylesheet" type="text/css" href="../../js/file/upload.css">
<script src="../../js/file/dropzone.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/business/supplier/js/attachmentView.js" type="text/javascript"></script>
<div class="col-md-7" name="FormMode" id="FileMode" style="width:100%;height:100%;margin:20px auto;float:none;display:none;">
	<div class="widget-container fluid-height col-md-7-k"
		 style="height:100%;border-radius:4px;padding-bottom:3px;">
		<div class="mbox-body" style="padding:5px;">

		</div>
	</div>
</div>
</body>
</html>
<script>
    $(function() {
        mini.unmask(document.body);
        var columns = [{
            type: "checkcolumn"
        }, {
            field : "id",
            visible : false
        }, {
            type : "indexcolumn",
            header : "序号",
            headerAlign : "center"
        }, {
            field : "fileName",
            width : 180,
            header : "文件名称",
            headerAlign : "center",
            align : "left",
            renderer : download
        /*}, {
            field : "type",
            width : 80,
            header : "文件类型",
            headerAlign : "center",
            align : "center",
            renderer : getFileType
        }, {
            field : "uploadTime",
            width : 80,
            header : "上传时间",
            headerAlign : "center",
            align : "center",
            dateFormat : "yyyy-MM-dd H:mm"
        }, {
            field : "size",
            width : 80,
            header : "大小",
            headerAlign : "center",
            align : "center"*/
        } ];
        initAttachmentGrid("${pageContext.request.contextPath}/business/biddingFile/attachment.action?uuid=${uuid}", columns);
    });

    function getFileType(cell) {
        var fileType = cell.row.type;
        if(fileType == 1) {
            return "业绩统计表";
        } else if(fileType == 2) {
            return "业绩证明文件";
        } else if(fileType == 3) {
            return "其他附件文件";
        }
        return "<font color='red'>未知文件</font>"
    }

    function download(cell) {
        console.log(cell);
        var url = "${pageContext.request.contextPath}/business/download.action?id=" + cell.row.id;
        return "<a href='" + url + "' target='view_window'>" + cell.value + "</a>"
    }
</script>