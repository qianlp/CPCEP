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
<title>系统界面-供应商</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeFour/js/echarts.min.js"></script>
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/default/large-mode.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/pure/skin.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/homeTemplate/homeFour/js/amazeui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/amazeui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/admin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/app.css">
<style type="text/css">
	body {
		margin-top: 90px
	}
	#tbInfoTab tbody tr td{
		text-align:center;
		overflow: hidden;
		text-overflow:ellipsis;
	}
</style>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	var curSelObj=null;
	var loId="DaiBan";
	function getMsgList(str,obj){
		loId=obj;
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
				htmlStr+="<li onclick=\"javascript:CommonOpenDoc('"+this.menuId+"','"+this.docId+"','"+this.uuid+"','"+this.docBusinessId+"','"+this.taskId+"','"+this.taskName+"');\" style=\"cursor:pointer\">";
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
			$("#dbNumT").html(data.total);
		})
		$.post(path,{msgType:2},function(data){
			data=mini.decode(data);
			$("#tzNumT").html(data.total);
		})
		$.post(path,{msgType:3},function(data){
			data=mini.decode(data);
			$("#yjNumT").html(data.total);
		})
	}
	
	function wfDBLoadding(){
		htmlStr="<li style=\"height:30px;text-align:center;border:0px;\">";
		htmlStr+="加载中...";
		htmlStr+="</li>";
		$("#"+loId).html(htmlStr);
	}
	
	function CommonOpenDoc(menuId,uuid,msgId,docBusinessId,taskId,taskName){
		var strPathUrl=gDir+"/admin/findDocById.action?menuId="+menuId+"&uuid="+uuid+"&msgId="+msgId;
		if(uuid=="" || uuid==null || uuid=="null"){
			strPathUrl=gDir+"/admin/rtuMenuById.action?menuId="+menuId+"&msgId="+msgId+"&docBusinessId="+docBusinessId+"&taskId="+taskId+"&taskName="+encodeURI(encodeURI(taskName));
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
	<div class="tpl-page-container tpl-page-header-fixed" style="padding-top: 0px">
		<%@include file="leftTemplate.jsp" %>
		<div class="tpl-content-wrapper">
        	<div class="row" style="background:#fff;margin-top:-9px;margin-right:-3px;margin-left:-3px;    border-radius: 4px;"> 
              	<div class="am-scrollable-horizontal">
              		<ul class="am-tabs-nav am-nav am-nav-tabs">
                   		<li class="am-active" onclick="table1('tab1',this)"><a href="javascript:;">邀请招标</a></li>
                   		<li onclick="table1('tab2',this)"><a href="javascript:;">公开招标</a></li>
              		</ul>
              		<table class="am-table am-table-bordered am-table-striped am-text-nowrap" id="tab1">
				    	<thead>
				        	<tr>
					        	<th>序号</th>
					        	<th>招标公告名称</th>
					       		<th>报名开始时间</th>
				            	<th>报名结束时间</th>
				            	<th>投标开始时间</th>
				            	<th>投标结束时间</th>
				            	<th>预计开标时间</th>
				            	<th>报名状态</th>
				        	</tr>
				    	</thead>
				    	<tbody>
				    	</tbody>
					</table>
				 	<table class="am-table am-table-bordered am-table-striped am-text-nowrap" id="tab2" style="display:none">
				    	<thead>
				        	<tr>
					        	<th>序号</th>
					        	<th>招标公告名称</th>
					        	<th>报名开始时间</th>
				            	<th>报名结束时间</th>
				            	<th>投标开始时间</th>
				            	<th>投标结束时间</th>
				            	<th>预计开标时间</th>
				            	<th>报名状态</th>
				        	</tr>
				    	</thead>
				    	<tbody>
				    	</tbody>
					</table>
				</div>
            </div>
            <div class="row" style="height:20px;width:100%"></div>
            <div class="row">
               <div class="am-u-md-6 am-u-sm-12 row-mb">
                    <div class="tpl-portlet">
                        <div class="tpl-portlet-title">
                            <div class="tpl-caption font-green ">
                                <span>消息中心</span>
                            </div>
                        </div>
                        <div class="am-tabs tpl-index-tabs" data-am-tabs="">
                            <ul class="am-tabs-nav am-nav am-nav-tabs">
                                <li onclick='getMsgList("{msgType:1}","DaiBan");' class="am-active"><a href="#" style="color:#36c6d3">待办 <span id="dbNumT" class="am-badge tpl-badge-danger am-round">0</span></a></li>
                                <li onclick='getMsgList("{msgType:2}","TongZhi");'><a href="#">通知 <span id="tzNumT" class="am-badge tpl-badge-danger am-round">0</span></a></li>
                                <li onclick='getMsgList("{msgType:3}","YuJing");'><a href="#">预警 <span id="yjNumT" class="am-badge tpl-badge-danger am-round">0</span></a></li>
                                <li onclick='getMsgList("{status:2}","YiChuLi");'><a href="#">已处理</a></li>
                            </ul>
                            <div class="am-tabs-bd" style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
                                <div class="am-tab-panel am-fade am-in am-active" id="tab3">
                                    <div id="wrapperA" class="wrapper">
                                        <div id="scroller" class="scroller" style="transform: translate(0px, 0px) translateZ(0px);">
                                            <ul class="tpl-task-list tpl-task-remind" id="DaiBan">
                                            </ul>
                                        </div>
                                    	<div class="iScrollVerticalScrollbar iScrollLoneScrollbar"  ><div class="iScrollIndicator" ></div></div>
                                    </div>
                                </div>
                                <div class="am-tab-panel am-fade am-in" id="tab2">
                                    <div id="wrapperA" class="wrapper">
                                        <div id="scroller" class="scroller" style="transform: translate(0px, 0px) translateZ(0px);">
                                            <ul class="tpl-task-list tpl-task-remind" id="TongZhi" >
                                            </ul>
                                        </div>
                                    	<div class="iScrollVerticalScrollbar iScrollLoneScrollbar"  ><div class="iScrollIndicator" ></div></div>
                                    </div>
                                </div>
                                <div class="am-tab-panel am-fade am-in" id="tab1">
                                    <div id="wrapperA" class="wrapper">
                                        <div id="scroller" class="scroller" style="transform: translate(0px, 0px) translateZ(0px);">
                                            <ul class="tpl-task-list tpl-task-remind" id="YuJing">
                                            </ul>
                                        </div>
                                    	<div class="iScrollVerticalScrollbar iScrollLoneScrollbar"  ><div class="iScrollIndicator" ></div></div>
                                    </div>
                                </div>
                                <div class="am-tab-panel am-fade am-in" id="tab0">
                                    <div id="wrapperA" class="wrapper">
                                        <div id="scroller" class="scroller" style="transform: translate(0px, 0px) translateZ(0px);">
                                            <ul class="tpl-task-list tpl-task-remind" id="YiChuLi">
                                            </ul>
                                        </div>
                                    	<div class="iScrollVerticalScrollbar iScrollLoneScrollbar"  ><div class="iScrollIndicator" ></div></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="am-u-md-6 am-u-sm-12 row-mb">
                    <div class="tpl-portlet">
                        <div class="tpl-portlet-title">
                            <div class="tpl-caption font-red ">
                                <i class="am-icon-bar-chart"></i>
                                <span> 报价消息</span>
                            </div>
                        </div>
                        <div class="tpl-scrollable" style="height:275px;overflow-y:scroll">
                            <table class="am-table tpl-table" id="tbInfoTab">
                                <thead>
                                    <tr class="tpl-table-uppercase">
                                        <th style="width:40px;text-align:center;">序号</th>
                                        <th style="width:150px;">招标公告名称</th>
                                        <th style="width:80px;text-align:center;">类型</th>
                                        <th style="width:80px;text-align:center;">截止日期</th>
                                        <th style="width:150px;">其他说明</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
              </div>  
		</div>
	</div>	
<script>
	var fpx = ($("body").innerWidth() - 1130) / 2;
	
	function table1(a,b){
    	$("#"+a).show().siblings().hide();
 		$(b).addClass('am-active').siblings().removeClass('am-active').parent('ul').show();
    }
    
    function getTBInfo(){
    	var path=gDir+'/business/bid/findMyInvite.action';
    	$.post(path,{
    		sortField:"",sortOrder:"desc",
    		pageSize:"1000",
    		pageIndex:"0"
    	},function(data){
    		data=mini.decode(data);
    		var htmlStr="";
    		if(data.total==0){
    			htmlStr="<tr><td style=\"text-align:center;color:red\" colspan=\"5\">没有报价信息！</td></tr>"
    		}else{
    		$.each(data.data,function(i){
    			htmlStr+="<tr >";
                htmlStr+="<td style=\"color:#000\">"+(i+1)+"</td>";
                htmlStr+="<td style=\"text-align:left;\" class=\" font-red\" title='"+this.name+"' >"+openQuotePriceDetail(this.name,this.type,this.invitationId)+"</td>";
                htmlStr+="<td class=\" font-green\" >"+invitation(this.type)+"</td>";
                htmlStr+="<td style=\"color:#000\">"+mini.formatDate(mini.parseDate(this.endDate),"yyyy-MM-dd HH:mm:ss")+"</td>";
                htmlStr+="<td style=\"color:#000;text-align:left;\" title='"+this.description+"'>"+this.description+"</td>";
                htmlStr+="</tr>";
    		})
    		}
    		$("#tbInfoTab tbody").html(htmlStr);
    	})
    }
    
    function invitation(val) {
        if(val == 1)
            return "<font color='blue'>澄清报价</font>";
        else if(val == 2)
            return "<font color='#006400'>邀请竞价</font>";
		return "<font color='red'>未知类型</font>";
	}
	
	function openQuotePriceDetail(v1,v2,v3) {
		var  type =v2;
		if(type == 1){
			 return "<a style='color:red;' >"+v1+"</a>";
		}
		if(type == 2){
			var urlPath="${pageContext.request.contextPath}/business/findInvitation.action?id=" + v3;
			return "<a style='color:green;' href='javascript:openTrader(\"" + urlPath + "\", \"" + v1 + "\");'>" + v1 + "</a>";
		}
	}
	
	function openTrader(urlPath, title) {
        mini.open({id:"oWinDlg",title:title,url:encodeURI(urlPath),showMaxButton: true});
	}
	
	function getZBInfo(path,id){
    	$.post(path,{
    		sortField:"",sortOrder:"desc",
    		pageSize:"1000",
    		pageIndex:"0"
    	},function(data){
    		data=mini.decode(data);
    		var htmlStr="";
    		if(data.total==0){
    			htmlStr="<tr><td style=\"text-align:center;color:red\" colspan=\"8\">没有招标信息！</td></tr>"
    		}else{
    		$.each(data.data,function(i){
    			htmlStr+="<tr>";
				htmlStr+="<td>"+(i+1)+"</td>";
				if(id=="tab1"){
					htmlStr+="<td>"+openDetail(this.id,this.name,"邀请招标",this.status)+"</td>";
				}else{
					htmlStr+="<td>"+openDetail(this.id,this.name,"公开招标",this.status)+"</td>";
				}
				htmlStr+="<td>"+mini.formatDate(mini.parseDate(this.registerStartTime),"yyyy-MM-dd HH:mm:ss")+"</td>";
				htmlStr+="<td>"+mini.formatDate(mini.parseDate(this.registerEndTime),"yyyy-MM-dd HH:mm:ss")+"</td>";
				htmlStr+="<td>"+mini.formatDate(mini.parseDate(this.bidStartTime),"yyyy-MM-dd HH:mm:ss")+"</td>";
				htmlStr+="<td>"+mini.formatDate(mini.parseDate(this.bidEndTime),"yyyy-MM-dd HH:mm:ss")+"</td>";
				htmlStr+="<td>"+mini.formatDate(mini.parseDate(this.bidOpenTime),"yyyy-MM-dd HH:mm:ss")+"</td>";
				htmlStr+="<td>"+getStatus(this.status)+"</td>";
				htmlStr+="</tr>";
    		})
    		}
    		$("#"+id+" tbody").html(htmlStr);
    	})
	}
	
	var zbObj=null;
	var tabTitle="";
	function signup() {
        var tabType;
        var cell = zbObj;
        if(tabTitle=="邀请招标"){
            tabType=1;
        }else{
            tabType=2;
        }
        if(cell == null) {
        	return;
		}
		if(cell.status == 9 || cell.status==2) {
            mini.alert("已报名!");
            return ;
		}  
		//当打开方式为window.open时使用
        var gWidth=window.screen.width;
        //当打开方式为window.open时使用
        var gHeight=window.screen.height;
        if(gHeight>0){var T=(screen.height-300-gHeight)/2}else{var T=0}
        if(gWidth>0){var L=(screen.width-300-gWidth)/2}else{var L=0}
        var strStatus="height="+(gHeight - 300)+",width="+(gWidth - 300)+",top="+T+",left="+L+",toolbar=no,menubar=no,location=no,status=yes,resizable=yes,scrollbars=yes";
        var objWin=window.open("${pageContext.request.contextPath}/business/signup.action?id=" + cell.id+"&tabType="+tabType,"_blank",strStatus);
        setTimeout(function(){objWin.focus()},100)
        <%--mini.confirm("确定报名该招标吗?","确认框", function(r){--%>
            <%--if(r != "ok")--%>
                <%--return ;--%>
			<%--$.ajax({--%>
				<%--url: "${pageContext.request.contextPath}/business/signup.action?id=" + cell.id,--%>
				<%--dataType: "json",--%>
				<%--cache: false,--%>
				<%--success: function(data) {--%>
					<%--mini.alert(data.message);--%>
				<%--}--%>
			<%--});--%>
        <%--});--%>
    }
	
	function getStatus(val){
        var strColor='0f0',strStatus = val;
        switch(parseInt(val,10)){
            case 9 :
                strStatus='待审批';
                strColor='00B83F';
                break;
            case 8 :
                strStatus='审核失败';
                strColor='f00';
                break;
            case 0:
                strColor='f00';
                strStatus='草稿';
                break;
            case 1:
				strColor='f00';
				strStatus='未报名';
                break;
            case 2:
                strColor='000';
                strStatus='已报名';
                break;
            default:
        }
        return "<span style='color:#"+strColor+"'>" + strStatus + "</span>";
    }
	
	function openDetail(v1,v2,v3,v4) {
		zbObj={id:v1,name:v2,status:v4};
		tabTitle=v3;
		var urlPath = "${pageContext.request.contextPath}/business/getTradeDetail.action?id=" +v1;
		return "<a style='color:blue;' href='javascript:openTrader(\"" + urlPath + "\", \"" + v2 + "\");'>" + v2 + "</a>"
	}
    
    $(document).ready(function(){
		//获取待办
		getMsgList("{msgType:1}","DaiBan");
		//获取消息数量
		getMsgCount();
		getTBInfo();
		getZBInfo(gDir+'/business/findInvite.action',"tab1");
		getZBInfo(gDir+'/business/findTender.action',"tab2");
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