<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>系统界面</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/animate.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/style.min.css?v=3.2.0" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/home/image.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/diy.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/loaders.css" />
<!-- 全局js -->
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/ht_jsoncode.js"></script>
<!-- 自定义js -->
<style>
.loader{
	margin-top:-25px;
	margin-left:-25px;
}
</style>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	function getMsgList(){
		var path=gDir+"/msg/getMsgList.action";
		$.post(path,{},function(data){
			data=ht.decode(data);
			var htmlStr="";
			$.each(data,function(){
				var type="db";
				var color="blue";
				if(this.msgType==2){
					type="tz";
					color="green";
				}else if(this.msgType==3){
					type="yj";
					color="red";
				}
				htmlStr+="<div class=\"Msg boder-"+color+"\" onclick=\"CommonOpenDoc('"+this.menuId+"','"+this.docId+"','"+this.uuid+"')\" >";
				htmlStr+="<div class=\"msg-status\">";
				htmlStr+="<a href=\"javascript:void(0)\" class=\""+type+"\" ><i></i></a>";
				htmlStr+="</div>";
				htmlStr+="<div class=\"msg-title\" >";
				htmlStr+="<a href=\"#\" >"+(this.msgTitle==""?"无标题":this.msgTitle)+"</a>";
				htmlStr+="</div>";
				htmlStr+="<div class=\"msg-util\">"+this.createBy+"&nbsp;&nbsp;&nbsp;"+$.formatDate(this.createDate,"yyyy-MM-dd hh:mm")+"</div>";
				htmlStr+="</div>";
			})
			if(htmlStr!=""){
				$("#MsgHtml").removeClass("line2");
			}else{
				$("#MsgHtml").addClass("line2");
			}
			$("#MsgHtml").html(htmlStr);
		})
	}
	
	function getNewsList(){
		var path=gDir+"/information/findNewJsonPage.action";
		$.post(path,{pageIndex:0,pageSize:10},function(data){
			data=ht.decode(data);
			var htmlStr="";
			$.each(data.data,function(){
				var path="/information/readNews.action?uuid="+this.uuid;
				htmlStr+="<div class=\"textbox\" onclick=\"parent.goAddTab('新闻查看','"+path+"')\">";
				htmlStr+="<a href=\"#\" >"+(this.name==""?"无标题":this.name)+"</a>";
				htmlStr+="<span>"+this.createDate+"</span>";
				htmlStr+="</div>";
			})
			if(htmlStr!=""){
				$("#NewHtml").removeClass("line1");
			}else{
				$("#NewHtml").addClass("line1");
			}
			$("#NewHtml").html(htmlStr);
		})
	}
	
	function getNoticeList(){
		var path=gDir+"/information/findNoticeJsonPage.action";
		$.post(path,{pageIndex:0,pageSize:10},function(data){
			data=ht.decode(data);
			var htmlStr="";
			$.each(data.data,function(){
				var path="/information/readNotice.action?uuid="+this.uuid;
				htmlStr+="<div class=\"textbox\"  onclick=\"parent.goAddTab('公告查看','"+path+"')\">";
				htmlStr+="<a href=\"#\" >"+(this.name==""?"无标题":this.name)+"</a>";
				htmlStr+="<span>"+this.createDate+"</span>";
				htmlStr+="</div>";
			})
			$("#NiceHtml").html(htmlStr);
		})
	}
	
	function getImgNewsList(){
		var path=gDir+"/information/findNewsImg.action";
		$.post(path,{pageIndex:0,pageSize:5},function(data){
			data=mini.decode(data);
			var str="";
			$.each(data.data,function(i,e){
				str+="<div class='fcon' style='display: none;'>"
					+"<a  target='_blank' href='#'><img src='"+e.newImgAddress+"' style='opacity: 1; '></a>"
					+"<span class='shadow'><a  href='#'>"+e.name+"</a></span>"
					+"</div>";
			});
			$("#D1pic1").html(str);
			Qfast.add('widgets', { path: "${pageContext.request.contextPath}/css/home/default/terminator2.2.min.js", type: "js", requires: ['fx'] });  
			Qfast(false, 'widgets', function () {
				K.tabs({
					id: 'fsD1',   //焦点图包裹id  
					conId: "D1pic1",  //** 大图域包裹id  
					tabId:"D1fBt",  
					tabTn:"a",
					conCn: '.fcon', //** 大图域配置class       
					auto: 1,   //自动播放 1或0
					effect: 'fade',   //效果配置
					eType: 'click', //** 鼠标事件
					pageBt:true,//是否有按钮切换页码
					bns: ['.prev', '.next'],//** 前后按钮配置class                          
					interval: 3000  //** 停顿时间  
				}) 
			})
		})
	}
	
	function CommonOpenDoc(menuId,uuid,msgId){
		var strPathUrl=gDir+"/admin/findDocById.action?menuId="+menuId+"&uuid="+uuid+"&msgId="+msgId;
		if(uuid=="" || uuid==null){
			strPathUrl=gDir+"/admin/rtuMenuById.action?menuId="+menuId+"&msgId="+msgId;
		}
		winH=screen.height-100;//高度
		winH=winH==0?(screen.height-100):winH;
		winW=screen.width-20;//宽度
		winW=winW==0?(screen.width-20):winW;
		if(winH>0){var T=(screen.height-100-winH)/2}else{var T=0}
		if(winW>0){var L=(screen.width-20-winW)/2}else{var L=0}
		var pstatus="height="+winH+",width="+winW+",top="+T+",left="+L+",toolbar=no,menubar=no,location=no,status=yes,resizable=yes,scrollbars=yes";
		window.open(strPathUrl,'_blank',pstatus);
	}
	
	function CommonReadDoc(address){
		var strPathUrl=gDir+address;
		window.open(strPathUrl);
	}
	
</script>
</head>
<body class="ibbody" style="padding:15px 0px;overflow-x:hidden;overflow-y:auto;">
	<div class="textbox" style="display:none">
		<a href="#" >测试新翁龙啊速回复</a>
		<span>2016-01-10 10:33</span>
	</div>
	<div class="Msg boder-blue" style="display:none">
		<div class="msg-status">
			<a href="javascript:void(0)" class="db" title="待办"><i></i></a>
		</div>
		<div class="msg-title">
			<a href="#">测试待办111111111</a>
		</div>
		<div class="msg-util">admin&nbsp;&nbsp;&nbsp;2016-10-19 12:10</div>
	</div>
	<div style="float:left;width:100%;display:block;">
		<div class="col-sm-7" style="float:left;position:relative;">
			<div class="da-panel-widget">
				<h3>公司新闻</h3>
				<div id="NewHtml" class="line1" style="height:305px;">
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
				</div>
			</div>
		</div>
		<div class="col-sm-5" style="float:right;position:relative;">
			<div class="da-panel-widget">
				<h3>最新消息</h3>
				<div class="pull-right" >
					<a href="javascript:void(0)" class="db" title="待办"><i></i></a>
					<a href="javascript:void(0)" class="tz" title="通知"><i></i></a>
					<a href="javascript:void(0)" class="yj" title="预警"><i></i></a>
				</div>
				<div id="MsgHtml" class="line2" style="min-height:677px;">
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
				</div>
			</div>
		</div>
		<div class="col-sm-7" style="float:left;position:relative;">
			<div class="da-panel-widget">
				<h3>公告</h3>
				<div id="NiceHtml" style="height:305px;">
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
				</div>
			</div>
		</div>
	</div>
	<script>
		getMsgList();
		getNewsList();
		getNoticeList();
		//getImgNewsList();
	</script>
</body>
</html>