<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
<head>
<title>系统界面</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/css/nifty.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/css/demo/nifty-demo-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/css/demo/nifty-demo.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/plugins/pace/pace.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/plugins/magic-check/css/magic-check.min.css" rel="stylesheet">
    <link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/loaders.css" />
    <link href="${pageContext.request.contextPath}/css/home/image.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/css/diy.css" rel="stylesheet" type="text/css" />
    
    
    <script src="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/plugins/pace/pace.min.js"></script>
    <script src="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/js/jquery-2.2.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/js/nifty.min.js"></script>
    
    <script src="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/js/demo/nifty-demo.min.js"></script>
    <script src="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/js/ht_jsoncode.js"></script>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	
</script>
<style>
	#content-container{
		padding-bottom:0px;
	}
	.panel-body{
		padding:10px;
	}
	body{
		overflow-y:auto;
	}
</style>
</head>
<body>
	<div id="container"
		class="effect aside-float aside-bright mainnav-lg navbar-fixed">
		<%@include file="menuTemplate.jsp"%>
		<div id="content-container">
			<div id="page-content">
				<%@include file="infoPage.jsp"%>	    
				
			</div>
		</div>

		<%@include file="aside.jsp" %>
	</div>

	<button class="scroll-top btn">
		<i class="pci-chevron chevron-up"></i>
	</button>
	</div>

	<%@include file="settings.jsp" %>
	
	<script>
	var gheight=$("body").innerHeight();
	$.ajax({
		url : encodeURI("${pageContext.request.contextPath}/admin/findHasRightMenus.action"),
		cache : false,
		async : false,
		success : function(MenuText) {
			var treeData=ht.arrayToTree(ht.decode(MenuText),"children","id","pid","-1");
			$("#mainnav-menu").append(EachArr(treeData,0));
		}
	});
	
	function EachArr(arr,intM){
		var str="";
		$.each(arr,function(){
			str+="<li>";
			if(this.children && this.children.length>0){
				var ico="";
				if(intM==0){
					ico="<i class=\"fa "+this.icon+"\"></i>";
					str+="<a href=\"#\">"+ico+" <span class=\"menu-title\"> <strong>"+this.text+"</strong> </span>";
				}else{
					str+="<a href=\"#\">"+ico+" "+this.text+"";
				}
				
				str+="<i class=\"arrow\"></i></a>"
				str+="<ul class=\"collapse\">";
				str+=EachArr(this.children,1);
				str+="</ul>"
			}else{
				if(intM==0){
					str+="<a href=\"javascript:;\" onclick=\"onShow('"+rtuLink(this.id)+"','"+this.text+"',this)\"><i class=\"fa "+this.icon+"\"></i><span class=\"menu-title\"> <strong>"+this.text+"</strong> ";
					str+="</span></a>";
				}else{
					str+="<a href=\"javascript:;\" onclick=\"onShow('"+rtuLink(this.id)+"','"+this.text+"',this)\">"+this.text+"</a>";
				}
			}
			str+="</li>";
		})
		
		return str;
	}
	
	function rtuLink(id){
		return "${pageContext.request.contextPath}/admin/findHasRightMenu.action?menu.uuid="+id;
	}
	
	
	
	var gCurMenu=null;
	function onShow(path,title,o){
		if(gCurMenu!=null){
			$(gCurMenu).parent().removeClass("active-link");
		}
		$(o).parent().addClass("active-link");
		gCurMenu=o;
		//$("#gTitle").html(title);
		$("#page-content").html("<iframe id='gFrm' src='"+path+"' frameborder='no'  style='width:100%;height:"+(gheight-100)+"px'></iframe>");
	}
	
	window.onresize=function(){
		$("#gFrm").css("height",(gheight-100)+"px");
	}
	
	
	function subText(str){
		if(str.length>5){
			return str.substr(0,4)+".."
		}
		return str;
	}
	
	function getHtml(jspName){
		var path="${pageContext.request.contextPath}/homeTemplate/homeThree/"+jspName;
		$.post(path,{},function(html){
			$("#page-content").html(html);
		})
	}
	</script>
</body>
</html>
