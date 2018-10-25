<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
<head>
<title>系统界面</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeFour/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeFour/js/amazeui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/amazeui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/admin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/app.css">
<style type="text/css">
	body {
		margin-top: 90px
	}
</style>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	var curSelObj=null;
	var loId="DaiBan";
	function getMsgList(str,obj){
		if(obj=="yes"){
			loId="MsgHtml";
		}
		wfDBLoadding();
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
				htmlStr+="<li onclick=\"javascript:CommonOpenDoc('"+this.menuId+"','"+this.docId+"','"+this.uuid+"','"+this.docBusinessId+"','"+this.taskId+"','"+this.taskName+"','"+this.biddingFileUuid+"');\" style=\"cursor:pointer\">";
				htmlStr+="<div class=\"cosB\">"+timeago(this.createDate.getTime())+"</div>";
				htmlStr+="<div class=\"cosA\">";
				htmlStr+="<span class=\"cosIco\"> <i class=\"am-icon-bell-o\"></i>";
				htmlStr+="</span><span>&nbsp;&nbsp;"+(this.msgTitle==""?"无标题":this.msgTitle)+"</span>";
				htmlStr+="</div>";
				htmlStr+="</li>";
			})
			if(htmlStr==""){
				htmlStr="<li style=\"height:30px;text-align:center;border:0px;\">";
				htmlStr+="无数据信息";
				htmlStr+="</li>";
			}
			$("#"+loId).html(htmlStr);
		})
	}
	
	function getMsgCount(){
		var path=gDir+"/msg/getMsgCount.action";
		$.post(path,{msgType:1},function(data){
			data=mini.decode(data);
			$("#dbNum").html(data.total);
			$("#dbNumT").html(data.total+"条");
		})
		$.post(path,{msgType:2},function(data){
			data=mini.decode(data);
			$("#tzNumT").html(data.total+"条");
		})
		$.post(path,{msgType:3},function(data){
			data=mini.decode(data);
			$("#yjNumT").html(data.total+"条");
		})
		$.post(path,{status:2},function(data){
			data=mini.decode(data);
			$("#yclNumT").html(data.total+"条");
		})
	}
	
	function wfDBLoadding(){
		htmlStr="<li style=\"height:30px;text-align:center;border:0px;\">";
		htmlStr+="加载中...";
		htmlStr+="</li>";
		$("#"+loId).html(htmlStr);
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
				htmlStr+="<li onclick=\"CommonReadDoc('"+path+"')\" style=\"cursor:pointer\">"
				htmlStr+="<div class=\"task-title\">"
				htmlStr+="<span class=\"task-title-sp\" style=\"color: #000\">"+(this.name==""?"无标题":this.name)+"</span>";
				htmlStr+="<span class=\"label label-sm label-success\">新闻</span>";
				htmlStr+="</div>";
				htmlStr+="</li>";
			})
			$("#NewsDiv").html(htmlStr);
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
	
	function CommonOpenDoc(menuId,uuid,msgId,docBusinessId,taskId,taskName,biddingFileUuid){
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
<body data-type="index" style="">
	<%@include file="menuTemplate.jsp" %>
	<div class="tpl-page-container tpl-page-header-fixed"
		style="padding-top: 0px">
		<%@include file="leftTemplate.jsp" %>
        <div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
            <div class="am-modal-dialog">
                	<div class="am-tabs-bd"
								style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
								<div class="am-tab-panel am-fade am-in am-active" id="tab1">
									<div id="wrapperA" class="wrapper">
										<div id="scroller" class="scroller"
											style="transform: translate(0px, 0px) translateZ(0px);">
											<ul class="tpl-task-list tpl-task-remind" id="MsgHtml">
												
											</ul>
										</div>
										<div class="iScrollVerticalScrollbar iScrollLoneScrollbar">
											<div class="iScrollIndicator"></div>
										</div>
									</div>
								</div>
							</div>
                <div class="am-modal-footer">
                	<span class="am-modal-btn">关闭</span>
                </div>
            </div>
        </div>
		
		<div class="tpl-content-wrapper" style="padding-top: 0px;">
			<ol class="am-breadcrumb">
				<li><a href="#" class="am-icon-home">首页</a></li>
			</ol>
			<div class="row" style="margin-top: -10px;">
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12" onclick="openModal(1)">
				
					<div class="dashboard-stat blue">
						<div class="visual">
							<i class="am-icon-envelope"></i>
						</div>
						<div class="details">
							<div class="number" id="dbNumT">0条</div>
							<div class="desc">待办消息</div>
						</div>
						<a class="more" href="#"> 查看更多 <i
							class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12" onclick="openModal(2)">
					<div class="dashboard-stat green">
						<div class="visual">
							<i class="am-icon-envelope"></i>
						</div>
						<div class="details">
							<div class="number" id="tzNumT">0条</div>
							<div class="desc">通知消息</div>
						</div>
						<a class="more" href="#"> 查看更多 <i
							class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12" onclick="openModal(3)">
					<div class="dashboard-stat red">
						<div class="visual">
							<i class="am-icon-envelope"></i>
						</div>
						<div class="details">
							<div class="number" id="yjNumT">0条</div>
							<div class="desc">预警消息</div>
						</div>
						<a class="more" href="#"> 查看更多 <i
							class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>

				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12" onclick="openModal(4)">
					<div class="dashboard-stat purple">
						<div class="visual">
							<i class="am-icon-envelope"></i>
						</div>
						<div class="details">
							<div class="number" id="yclNumT">0条</div>
							<div class="desc">已处理消息</div>
						</div>
						<a class="more" href="#"> 查看更多 <i
							class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
			</div>
			<div class="row">

				<div class="am-u-md-6 am-u-sm-12 row-mb">

					<div class="tpl-portlet">
						<div class="tpl-portlet-title">
							<div class="tpl-caption font-green ">
								<span>新闻/公告</span>
							</div>
							<div class="tpl-portlet-input">
								<a href="#">更多</a>
							</div>
						</div>
						<div id="wrapper" class="wrapper">

							<div id="scroller" class="scroller"
								style="transform: translate(0px, 0px) translateZ(0px);">
								<ul class="tpl-task-list" id="NewsDiv">
								</ul>
							</div>
						</div>
					</div>
				</div>

				<div class="am-u-md-6 am-u-sm-12 row-mb">
					<div class="tpl-portlet">
						<div class="tpl-portlet-title">
							<div class="tpl-caption font-green ">
								<span>待办消息</span>
							</div>
							<div class="tpl-portlet-input">
								<a href="${pageContext.request.contextPath}/homeTemplate/homeFour/msgInfo.jsp">更多</a>
							</div>
						</div>

						<div class="am-tabs tpl-index-tabs" data-am-tabs="">
							<div class="am-tabs-bd"
								style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
								<div class="am-tab-panel am-fade am-in am-active" id="tab1">
									<div id="wrapperA" class="wrapper">
										<div id="scroller" class="scroller"
											style="transform: translate(0px, 0px) translateZ(0px);">
											<ul class="tpl-task-list tpl-task-remind" id="DaiBan">
												
											</ul>
										</div>
										<div class="iScrollVerticalScrollbar iScrollLoneScrollbar">
											<div class="iScrollIndicator"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div></div>
<script>
	function openModal(ki){
		if(ki==4){
			getMsgList("{status:2}","yes");
		}else{
			getMsgList("{msgType:"+ki+"}","yes");
		}
		$('#my-alert').modal();
	}
	
	$(document).ready(function(){
		//获取待办
		getMsgList("{msgType:1}");
		//获取图片新闻
		//getImgNewsList();
		//获取新闻
		getNewsList();
		//获取公告
		//getNoticeList();
		//获取消息数量
		getMsgCount();
	})
	
	function timeago(dateTimeStamp){   //dateTimeStamp是一个时间毫秒，注意时间戳是秒的形式，在这个毫秒的基础上除以1000，就是十位数的时间戳。13位数的都是时间毫秒。
    var minute = 1000 * 60;      //把分，时，天，周，半个月，一个月用毫秒表示
    var hour = minute * 60;
    var day = hour * 24;
    var week = day * 7;
    var halfamonth = day * 15;
    var month = day * 30;
    var now = new Date().getTime();   //获取当前时间毫秒
    console.log(now)
    var diffValue = now - dateTimeStamp;//时间差

    if(diffValue < 0){
        return;
    }
    var minC = diffValue/minute;  //计算时间差的分，时，天，周，月
    var hourC = diffValue/hour;
    var dayC = diffValue/day;
    var weekC = diffValue/week;
    var monthC = diffValue/month;
    if(monthC >= 1 && monthC <= 3){
        result = " " + parseInt(monthC) + "月前"
    }else if(weekC >= 1 && weekC <= 3){
        result = " " + parseInt(weekC) + "周前"
    }else if(dayC >= 1 && dayC <= 6){
        result = " " + parseInt(dayC) + "天前"
    }else if(hourC >= 1 && hourC <= 23){
        result = " " + parseInt(hourC) + "小时前"
    }else if(minC >= 1 && minC <= 59){
        result =" " + parseInt(minC) + "分钟前"
    }else if(diffValue >= 0 && diffValue <= minute){
        result = "刚刚"
    }else {
        var datetime = new Date();
        datetime.setTime(dateTimeStamp);
        var Nyear = datetime.getFullYear();
        var Nmonth = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var Ndate = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        var Nhour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
        var Nminute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
        var Nsecond = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
        result = Nyear + "-" + Nmonth + "-" + Ndate
    }
    return result;
}
	</script>
</body>
</html>