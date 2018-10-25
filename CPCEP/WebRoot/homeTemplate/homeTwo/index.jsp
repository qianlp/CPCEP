<%@page import="java.text.SimpleDateFormat"%>
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
<title>系统界面</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/animate.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/style.min.css?v=3.2.0" rel="stylesheet">

<!-- 全局js -->
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/bootstrap.min.js?v=3.4.0"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/plugins/layer/layer.min.js"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/ht_jsoncode.js"></script>
<!-- 自定义js -->
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/hplus.min.js?v=3.2.0"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/contabs.min.js"></script>
<!-- 第三方插件 -->
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/plugins/pace/pace.min.js"></script>
<style type="text/css">
	.nav.navbar-top-links li a{
		padding:9px 10px;
		font-size:22px;
	}
	.nav > li > a i{
		margin-right:0px;
	}
	.minimalize-styl-2{
		margin-top:10px;
	}
</style>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	
	//上一个选中的Tab
	var nopTab=null;
	function goAddTab(title,path){
		nopTab=$(".J_menuTab.active");
		$("#CIframe").attr("href",gDir+path);
		$("#CIframe").html(title);
		$("#CIframe").click();
	}
</script>
</head>
<body  class="fixed-sidebar full-height-layout gray-bg pace-done skin-1">
	<div style="display:none;"><a class="J_menuItem" href="#" id="CIframe"></a></div>
	<div id="wrapper" style="overflow:hidden;">
        <%@include file="menuTemplate.jsp" %>
		<div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0;text-align:right;">
                    <div class="navbar-header" style="width:20%;">
						<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#" style="margin:10px 0px 0px 20px;">
							<i class="fa fa-bars"></i>
						</a>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        <li class="dropdown">
                            <a href="javascript:goAddTab('消息中心','/homeTemplate/homeTwo/msgInfo.jsp')" title="消息中心" >
                                <i class="fa  fa-bell"></i>  
                            </a>
                        </li>
						<li>
							<a href="javascript:goAddTab('个人中心','/homeTemplate/homeTwo/userInfo.jsp')" title="个人中心" >
								<i class="fa fa fa-user"></i>
							</a>
						</li>
						<li>
							<a href="javascript:location.href='${pageContext.request.contextPath}/admin/loginOut.action'" title="退出">
								<i class="fa fa fa-sign-out"></i>
							</a>
						</li>
                    </ul>
                </nav>
            </div>
			<div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="indexV2" >首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight" style="right:80px;"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right" style="right:0px;">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span></button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
                    </ul>
                </div>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" id="homePage" name="iframe0" width="100%" height="100%" src="${pageContext.request.contextPath}/homeTemplate/homeTwo/infoPage.jsp" frameborder="0"  data-id="indexV2" seamless>
				</iframe>
            </div>
        </div>
    </div>
	<script>
	var fpx = ($("body").innerWidth() - 1130) / 2;
	$("#userInfo").css("right", (fpx+10)+ "px");
	$("#user_login_card").css("right",(fpx+10)+"px");
	$.ajax({
		url : encodeURI("${pageContext.request.contextPath}/admin/findHasRightMenus.action"),
		cache : false,
		async : false,
		success : function(MenuText) {
			var treeData=ht.arrayToTree(ht.decode(MenuText),"children","id","pid","-1");
			$("#side-menu").append(EachArr(treeData,0));
		}
	});
	
	function EachArr(arr,intM){
		var str="";
		$.each(arr,function(){
			str+="<li>"
			if(this.children && this.children.length>0){
				var ico="";
				if(intM==0){
					ico="<i class=\"fa "+this.icon+"\"></i>";
				}
				str+="<a href=\"#\">"+ico+" <span class=\"nav-label\">"+this.text+"</span>";
				str+="<span class=\"fa arrow\"></span></a>"
				if(intM==0){
					str+="<ul class=\"nav nav-second-level\">";
				}else{
					str+="<ul class=\"nav nav-third-level\">";
				}
				str+=EachArr(this.children,1);
				str+="</ul>"
			}else{
				var cls="J_menuItem";
				if(this.openstyle=="1"){
					cls="";
				}
				if(this.pid=="0"){
					str+="<a class=\""+cls+"\" target=\"_blank\" href=\""+rtuLink(this.id)+"\"><i class=\"fa "+this.icon+"\"></i> <span class=\"nav-label\">"+this.text+"</span>";
				}else{
					str+="<a class=\""+cls+"\"  target=\"_blank\" href=\""+rtuLink(this.id)+"\">"+this.text+"</a>"
				}
			}
			str+="</li>";
		})
		
		return str;
	}
	
	function rtuLink(id){
		return "${pageContext.request.contextPath}/admin/findHasRightMenu.action?menu.uuid="+id;
	}
	
	function subText(str){
		if(str.length>5){
			return str.substr(0,4)+".."
		}
		return str;
	}

	</script>
</body>
</html>
