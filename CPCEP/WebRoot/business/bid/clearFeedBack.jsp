<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String menuId = request.getParameter("menuId");

%>

<!DOCTYPE HTML>
<html>
<head>
    <title>澄清反馈</title>
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/miniui/scripts/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/miniui/scripts/miniui/miniui.js"></script>
    <script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/form.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/login/style_log.css">

    <link rel="stylesheet" type='text/css' href="../css/form/bootstrap-theme.css" />
    <link rel="stylesheet" type='text/css' href="../css/form/bootstrap.flow.min.css" />
    <link rel="stylesheet" type='text/css' href="../css/form/form.css" />
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>

    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

    </style>
</head>
<body>
  <div class="mini-toolbar">
    <%-- <a class="mini-button" iconCls="icon-add" href="${pageContext.request.contextPath}/business/bid/bidQuestionDetail.action?uuid=${param.uuid}">新建</a> --%>
     <c:if test="${param.status==1}">
     <c:if test="${empty user.userType}">
      <a class="mini-button" iconCls="icon-add" onclick="CommonOpenDoc('${pageContext.request.contextPath}/business/techEvalClarify/clearFeedBackDetail.action?bidFileId=${param.uuid}')" >新建</a>
    </c:if>
    </c:if>
   </div>
   <!--撑满页面-->
<div class='mini-fit' style='width:100%;height:100%;'>
	<div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;' idField='unid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true' fitColumns='true'>
		<div property='columns'>
				<div type='checkcolumn'></div>
				<div type='indexcolumn'>序号</div>
 				<div field='remark'   width='120' allowSort='true' headerAlign='left'  align='left'>问题描述</div>
                <div field='issueType' 	width='120' allowSort='true' headerAlign='left'  align='left'>问题类型</div>
		 		<div field='status' 		width='80' allowSort='true' headerAlign='left'  align='left' cellStyle="color:blue" renderer='CommonFlowStatus'>处理状态</div>
				<div field='curUser' 		width='80' allowSort='true' headerAlign='left'  align='left' renderer="CommonLink">操作</div>
				<div field='feedBack' width='50'	visible='false'></div>
				<div field='uuid' width='50'	visible='false'></div>
	    </div>
	</div>
</div>
<script type='text/javascript'>
    var gDir="${pageContext.request.contextPath}";
	mini.parse();
	var bidFileId="${param.uuid}";
	var grid=mini.get("miniDataGrid");
	//这里可以重新指定自定义的数据装载路径
	grid.setUrl(gDir+'/business/techEvalClarify/clearFeedList.action');
	grid.load({bidFileId:bidFileId});
	//grid.sortBy('createDate','desc');
</script>
<script>
    function CommonRowLink(cell){
      var url=gDir+"/business/biddingFile/bidQuestionCheck.action?uuid="+cell.row.uuid+"&type=read";
     return '<a href="javascript:CommonOpenDoc(\''+url+'\');">'+cell.value+'</a>';
    }
    <c:if test="${empty user.userType}">
    function CommonLink(cell){
    	 var url=gDir+"/business/techEvalClarify/checkClarify.action?uuid="+cell.row.uuid+"&type=read";
         return '<a href="javascript:CommonOpenDoc(\''+url+'\');"style="color:blue;">查看</a>';
    }
    </c:if>
    <c:if test="${user.userType==2}">
    function CommonLink(cell){
    	 var url=gDir+"/business/techEvalClarify/checkClarifySup.action?uuid="+cell.row.uuid+"&type=read";
         return '<a href="javascript:CommonOpenDoc(\''+url+'\');"style="color:blue;">查看</a>';
    }
    </c:if>
	function CommonFlowStatus(cell){
		var status =cell.row.feedBack;
		
	 if(status==""){
		 return "未处理";
	 }else{
		 return "已处理";
	 }
	} 
function CommonOpenDoc(strPathUrl){
		winH=screen.height-100;//高度
		winH=winH==0?(screen.height-100):winH;
		winW=screen.width-20;//宽度
		winW=winW==0?(screen.width-20):winW;
		if(winH>0){var T=(screen.height-100-winH)/2}else{var T=0}
		if(winW>0){var L=(screen.width-20-winW)/2}else{var L=0}
		var pstatus="height="+winH+",width="+winW+",top="+T+",left="+L+",toolbar=no,menubar=no,location=no,status=yes,resizable=yes,scrollbars=yes";
		window.open(strPathUrl,'_blank',pstatus);
	}
</script>


</body>
</html>