<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>JavaScript</title>
<script src="${pageContext.request.contextPath}/js/right/codemirror/lib/codemirror-mini.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/right/codemirror/lib/codemirror.css">
<style>html,body{width:100%;height:100%;padding:0px;margin:0px;overflow:hidden;}.CodeMirror{border:1px solid #eee;height:100%;}</style>
<script>
var CM=null;
window.onload = function() {
	CM = CodeMirror(document.body, {
		mode: "text/html",
		lineNumbers: true,
		styleActiveLine: true,
		indentWithTabs: true,
		indentUnit: 4
	});
	var $ = parent.$;
	CM.setValue($.trim($("#HtmlBody").val()));
};
</script>
</head>
<body>
</body>
</html>