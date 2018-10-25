<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>${menu.menuName}</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="stylesheet" type='text/css' href="../css/form/loaders.css" />
	<%@include file="../resource.jsp" %>
	<script src="../js/form/language_ZN.js" type="text/javascript"></script>
	<script src="../js/form/wf-mini.js" type="text/javascript"></script>
	
	<script>
		function wfSubmitDoc(){
			if(arguments.length==1){gWQSagent=arguments[0]}
			wfSubDocStart();
		}
		
		function wfSaveDoc() {
			/*不做任何操作保存文档*/
			if(confirm("确定保存文档吗？")){
				try{gForm.wfFlowLogXML.value=XML2String(gWFLogXML)}catch(e){};
				//提交前执行事件
				wfSaveBefore();
				fnResumeDisabled(); //恢复部分域的失效状态，以保证保存时值不会变为空。
				mini.mask({
		            el: document.body,
		            cls: 'mini-mask-loading',
		            html: '数据提交中...' //数据提交中...
		        });
				setTimeout(function(){
					gForm.submit();
				},500)
			}
		}
		
		function wfAgreeDoc(){
			//提交前处理数据
			if(confirm("您确定同意吗？")){
				if(arguments.length==1){gWQSagent=arguments[0]}
				gForm.wfTacheName.value="同意";
				wfSubDocStart();
			}
		}

		function wfRejectDoc(){
			if(confirm("您确定拒绝吗？")){
				//标注为拒绝
				gIsReject=1;
				gForm.wfTacheName.value="拒绝";
				wfSubDocStart();
			}
		}
	</script>
</head>
<body text="#000000" bgcolor="#FFFFFF"  style='padding:0px;width:100%;height:100%;background:#f3f3f3;'>
<div id="readUserMsg">文档处于会签状态，当前占用人：陈冰</div>
<div id="bg"></div>
<div class="loader" id="bg-loader">
	<div class="loader-inner ball-spin-fade-loader">
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
    </div>
</div>
<form method="post" action="${pageContext.request.contextPath}${menu.actionAddress}" name="form1" id="form1" >
<%@include file="../workflow/workFlowMust.jsp" %>
<script src="../js/file/upLoad.js" type="text/javascript"></script>
<div id="PageBody">
	<jsp:include page="..${menu.pageSubAddress}" />
	<c:if test="${menu.menuOpenStyle=='1'}">
	<%@include file="../file/attachFile.jsp" %>
	</c:if>
	<%@include file="../workflow/flowSign.jsp" %>
</div>
<%@include file="../toolbar/flowFoot.jsp" %>
</form>
</body>
</html>
