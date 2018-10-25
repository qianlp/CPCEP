<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
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
	.mbox-body table tr td a{
		color:#000;
	}
	.mbox-body table tr td a:hover{
		color:blue;
	}
	
</style>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	var curSelObj=null;
	function getMsgList(str,obj){
		wfDBLoadding();
		if(obj){
			if(!curSelObj){
				curSelObj=$("#WfDocNav").find("li a:first");
			}
			$(curSelObj).parent().removeClass("active");
			curSelObj=obj;
			$(obj).parent().addClass("active");
		}
		var path=gDir+"/msg/getMsgList.action";
		$.post(path,mini.decode(str),function(data){
			data=mini.decode(data);
			var htmlStr="";
			$.each(data,function(){
				var type="db";
				if(this.msgType==2){
					type="tz";
				}else if(this.msgType==3){
					type="yj";
				}
				htmlStr+="<tr>";
				htmlStr+="<td style=\"padding:0px;height:30px;padding-left:5px;\">";
				htmlStr+="<a href=\"javascript:void(0)\" hidefocus=\"true\" target=\"_self\" class=\""+type+"\"><i></i></a>";
				htmlStr+="<a class=\"newA\" onMouseOver=\"javascript:show('"+this.msgType+"','"+this.docId+"','"+this.remark+"','mydiv')\"  onMouseOut=\"javascript:hide('"+this.msgType+"','"+this.docId+"','"+this.remark+"','mydiv')\" href=\"javascript:CommonOpenDoc('"+this.menuId+"','"+this.docId+"','"+this.uuid+"','"+this.docBusinessId+"','"+this.taskId+"','"+this.taskName+"','"+this.biddingFileUuid+"');\">"+(this.msgTitle==""?"无标题":this.msgTitle)+"</a></td>";
				htmlStr+="<td style=\"padding:0px;height:30px;padding-left:5px;width:125px;\">"+mini.formatDate(this.createDate,"yyyy-MM-dd HH:mm")+"</td></tr>";
			})
			if(htmlStr==""){
				htmlStr="<tr>";
				htmlStr+="<td style=\"height:30px;text-align:center;\">无数据信息</td>";
				htmlStr+="</tr>";
			}
			$("#WFDaiBan tbody").html(htmlStr);
		})
	}
	
	function getMsgCount(){
		var path=gDir+"/msg/getMsgCount.action";
		$.post(path,{msgType:1},function(data){
			data=mini.decode(data);
			var pObj=$("#WfDocNav p")[0];
			$(pObj).html(data.total);
		})
		$.post(path,{msgType:2},function(data){
			data=mini.decode(data);
			var pObj=$("#WfDocNav p")[1];
			$(pObj).html(data.total);
		})
		$.post(path,{msgType:3},function(data){
			data=mini.decode(data);
			var pObj=$("#WfDocNav p")[2];
			$(pObj).html(data.total);
		})
	}
	
	function wfDBLoadding(){
		htmlStr="<tr>";
		htmlStr+="<td style=\"height:30px;text-align:center;\">加载中...</td>";
		htmlStr+="</tr>";
		$("#WFDaiBan tbody").html(htmlStr);
	}
	
	function show(msgType,docId,remark,id){
		if(msgType!=null && msgType!="" && msgType==1 &&(docId==null || docId=="") && remark!="" && remark!=null){
			var objDiv = $("#"+id+"");
			//兼容性调整
			var e = event || window.event;
			var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
            var x = e.pageX || e.clientX + scrollX;
            var y = e.pageY || e.clientY + scrollY;
			$(objDiv).html(remark);
			$(objDiv).css("display","block");
			$(objDiv).css("left", x);
			$(objDiv).css("top",y+10); 
		}
	}
	
	function hide(msgType,docId,remark,id){
		if(msgType!=null && msgType!="" && msgType==1 &&(docId==null || docId=="") && remark!="" && remark!=null){
			var objDiv = $("#"+id+"");
			$(objDiv).css("display","none");
		}
	}
	
	function getNewsList(){
		var path=gDir+"/information/findNewJsonPage.action";
		$.post(path,{pageIndex:0,pageSize:1000},function(data){
			data=mini.decode(data).data;
			var htmlStr="";
			$.each(data,function(){
				var path="/information/readNews.action?uuid="+this.uuid;
				htmlStr+="<tr>";
				htmlStr+="<td style=\"padding:0px;height:30px;padding-left:15px;background:url(${pageContext.request.contextPath}/css/home/default/listIco.gif);background-repeat:no-repeat;background-position:5px;\">";
				htmlStr+="<a class=\"newA\" href=\"javascript:CommonReadDoc('"+path+"')\">"+(this.name==""?"无标题":this.name)+"</a></td>";
				htmlStr+="<td style=\"padding:0px;height:30px;padding-left:5px;width:90px;\">"+this.createDate+"</td></tr>";
			})
			$("#NewsDiv tbody").html(htmlStr);
		})
	}
	
	function getNoticeList(){
		var path=gDir+"/information/findNoticeJsonPage.action";
		$.post(path,{pageIndex:0,pageSize:1000},function(data){
			data=mini.decode(data).data;
			var htmlStr="";
			$.each(data,function(){
				var path="/information/readNotice.action?uuid="+this.uuid;
				htmlStr+="<tr>";
				htmlStr+="<td style=\"padding:0px;height:30px;padding-left:15px;background:url(${pageContext.request.contextPath}/css/home/default/listIco.gif);background-repeat:no-repeat;background-position:5px;\">";
				htmlStr+="<a class=\"newA\" href=\"javascript:CommonReadDoc('"+path+"')\">"+(this.name==""?"无标题":this.name)+"</a></td>";
				htmlStr+="<td style=\"padding:0px;height:30px;padding-left:5px;width:90px;\">"+this.createDate+"</td></tr>";
			})
			$("#NoticeDiv tbody").html(htmlStr);
		})
	}
	
	function getImgNewsList(){
		var path=gDir+"/information/findNewsImg.action";
		$.post(path,{pageIndex:0,pageSize:5},function(data){
			data=mini.decode(data);
			var str="";
			$.each(data.data,function(i,e){
				str+="<div class='fcon' style='display: none;'>"
					+"<a  target='_blank' href='#'><img src='"+gDir+e.newImgAddress+"' style='opacity: 1; '></a>"
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
	
	function CommonOpenDoc(menuId,uuid,msgId,docBusinessId,taskId,taskName,biddingFileUuid){
		console.log(biddingFileUuid);
		var strPathUrl=gDir+"/admin/findDocById.action?menuId="+menuId+"&uuid="+uuid+"&msgId="+msgId;
		if(uuid=="" || uuid==null || uuid=="null"){
			strPathUrl=gDir+"/admin/rtuMenuById.action?menuId="+menuId+"&msgId="+msgId+"&docBusinessId="+docBusinessId+"&taskId="+taskId+"&taskName="+encodeURI(encodeURI(taskName));
		}
		if(menuId=="402880e6658db7ec01658dcc6ad30000"){
			  strPathUrl=gDir+"/business/bid/findDocGeneralBid.action?bidFileUuid="+biddingFileUuid+"&msgId="+msgId+"&menuId="+menuId;
			}
		if(menuId=="40288481659e0b8d01659e1517180000"){
			  strPathUrl=gDir+"/business/bid/findTechBid.action?bidFileUuid="+biddingFileUuid+"&msgId="+msgId+"&menuId="+menuId;
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
<body class="ibbody">

	<%@include file="menuTemplate.jsp" %>
	<div class="container-fluid main-content">
		<div class="social-wrapper wrapper" id="description"
			style="overflow:hidden;position:absolute;width:100%;margin-top:3px;">
			<div id="social-container"
				style="margin:auto;margin-top:40px;width:1120px;">
				<div class="item widget-container fluid-height  col-md-7-k"
					style="width: 1100px;">
					<div class="widget-content padded" style="padding:0px;">
						<div class="mbox-header">
							<span class="hTitle">公司新闻</span>
						</div>
						<div class="mbox-body">
							<div id="fsD1" class="focus">
								<div id="D1pic1" class="fPic"></div>
								<div class="fbg">
									<div class="D1fBt" id="D1fBt">
										<a href="javascript:void(0)" hidefocus="true" target="_self"
											class="current"><i>1</i></a> <a href="javascript:void(0)"
											hidefocus="true" target="_self" class=""><i>2</i></a> <a
											href="javascript:void(0)" hidefocus="true" target="_self"
											class=""><i>3</i></a> <a href="javascript:void(0)"
											hidefocus="true" target="_self" class=""><i>4</i></a> <a
											href="javascript:void(0)" hidefocus="true" target="_self"
											class=""><i>5</i></a>
									</div>
								</div>
								<span class="prev"></span> <span class="next"></span>
							</div>
							<div
								style="position:relative;border-left:1px solid #ccc;height:290px;margin-top:5px;left:470px;top:-295px;width:620px;overflow-y:auto">
								<table class="table table-underline" id="NewsDiv">
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="item widget-container fluid-height  col-md-7-k"
					style="width: 540px;">
					<div class="widget-content padded" style="padding:0px;">
						<div class="mbox-header">
							<span class="hTitle" style="float:left;">消息中心</span>
							<ul class="nav nav-skid" id="WfDocNav" style="height: 10px; line-height: 20px;">
								<li class="active"><a onclick="getMsgList('{msgType:1}',this)" href="javascript:;" datatype="1">&nbsp;&nbsp;待办&nbsp;&nbsp;</a><p class="counter">0</p></li>
								<li><a onclick="getMsgList('{msgType:2}',this)" href="javascript:;" datatype="2">&nbsp;&nbsp;通知&nbsp;&nbsp;</a><p class="counter">0</p></li>
								<li><a onclick="getMsgList('{msgType:3}',this)" href="javascript:;" datatype="3">&nbsp;&nbsp;预警&nbsp;&nbsp;</a><p class="counter">0</p></li>
								<li><a onclick="getMsgList('{status:2}',this)" href="javascript:;" datatype="4">&nbsp;&nbsp;已处理&nbsp;&nbsp;</a></li>
							</ul>
							<div class="pull-righ" style="float:right;margin-top:10px;margin-right:10px;">
								<a href="${pageContext.request.contextPath}/homeTemplate/homeOne/msgInfo.jsp" class="db" title="待办"><i></i></a>
								<a href="${pageContext.request.contextPath}/homeTemplate/homeOne/msgInfo.jsp" class="tz" title="通知"><i></i></a>
								<a href="${pageContext.request.contextPath}/homeTemplate/homeOne/msgInfo.jsp" class="yj" title="预警"><i></i></a>
							</div>
						</div>
						<div class="mbox-body" style="height:311px;overflow-y:auto">
							<table class="table table-underline" id="WFDaiBan" >
								<tbody>
									<tr>
										<td style="height:30px;text-align:center;">加载中...</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="item widget-container fluid-height  col-md-7-k"
					style="width: 540px;margin-right:0px;">
					<div class="widget-content padded" style="padding:0px;">
						<div class="mbox-header">
							<span class="hTitle">公告</span>
						</div>
						<div class="mbox-body" style="height:311px;overflow-y:auto">
							<table class="table table-underline" id="NoticeDiv">
								<tbody>
									
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="mydiv" style="position:absolute;display:none;border:1px solid silver;background:silver;"></div>
	<script>
	var treeData=null;
	var fpx = ($("body").innerWidth() - 1130) / 2;
	$("#userInfo").css("right", (fpx+10)+ "px");
	$("#user_login_card").css("right",(fpx+10)+"px");
	$("#logoInfo").css("left",(fpx+3)+"px");
	$.ajax({
		url : encodeURI("${pageContext.request.contextPath}/admin/findHasRightMenus.action"),
		cache : false,
		async : false,
		success : function(MenuText) {
			treeData=mini.arrayToTree(mini.decode(MenuText),"children","id","pid");
			initMenu();
		}
	});
	
	function initMenu(){
		var htmlStr="";
		htmlStr+="<li title='首页'>";
		htmlStr+="<a href=\"javascript:location.href='${pageContext.request.contextPath}/index.jsp';\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
		htmlStr+="<img src=\"${pageContext.request.contextPath}/img/menu/home.png\" width=\"28px\" height=\"28px\"></img>";
		htmlStr+="首页";
		htmlStr+="</a></li>";
		htmlStr+="</a></li>";
		$.each(treeData,function(i){
			if(i>5){
				htmlStr+="<li title='更多'>";
				htmlStr+="<a href=\"javascript:showMenuCard();\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
				htmlStr+="<img src=\"${pageContext.request.contextPath}/img/menu/icon"+i+".png\" width=\"28px\" height=\"28px\"></img>";
				htmlStr+="更多";
				htmlStr+="</a></li>";
				htmlStr+="</a></li>";
				return false;
			}
			htmlStr+="<li title='"+this.text+"'>";
			htmlStr+="<a href=\"javascript:location.href='${pageContext.request.contextPath}/menu.jsp?menuId="+this.id+"';\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
			htmlStr+="<img src=\"${pageContext.request.contextPath}/img/menu/icon"+i+".png\" width=\"28px\" height=\"28px\"></img>";
			htmlStr+=subText(this.text);
			htmlStr+="</a></li>";
			htmlStr+="</a></li>";
		})
		$("#navBar").append(htmlStr);
		initSubMenu();
	}
	
	function initSubMenu(){
		var k=1,o=1;
		$("#menu_body").append("<ul id=\"navBar"+k+"\" class=\"navbar main-nav nav-collapse nav\" style=\"width:100%;text-align:left;padding-left:25px;height:63px;\"></ul>");
		var htmlStr = "";
		var intCar=1;
		for(var b=6;b<treeData.length;b++){
			if(o>8){
				intCar++;
				$("#navBar"+k).append(htmlStr);
				htmlStr="";
				k++;
				o=1;
				$("#menu_body").append("<ul id=\"navBar"+k+"\" class=\"navbar nav\" style=\"width:100%;text-align:left;padding-left:25px;height:63px;\"></ul>");
			}
			htmlStr += "<li title='"+treeData[b].text+"'>";
			htmlStr += "<a href=\"javascript:location.href='${pageContext.request.contextPath}/menu.jsp?menuId="+treeData[b].id+"';\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
			htmlStr += "<img src=\"${pageContext.request.contextPath}/img/menu/icon8.png\" width=\"28px\" height=\"28px\"></img>";
			htmlStr += subText(treeData[b].text);
			htmlStr += "</a></li>";
			htmlStr += "</a></li>";
			o++;
		}
		$("#navBar"+k).append(htmlStr);
		$("#menu_body").css("height",(63*intCar)+"px");
	}
	
	function subText(str){
	    if(str == null)
	        return "";
		if(str.length>5){
			return str.substr(0,4)+".."
		}
		return str;
	}
	
	$(document).ready(function(){
		//获取待办
		getMsgList("{msgType:1}");
		//获取图片新闻
		getImgNewsList();
		//获取新闻
		getNewsList();
		//获取公告
		getNoticeList();
		//获取消息数量
		getMsgCount();
	})
	
	</script>
</body>
</html>