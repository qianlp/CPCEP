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
                    htmlStr+="<a class=\"newA\" onMouseOver=\"javascript:show('"+this.msgType+"','"+this.docId+"','"+this.remark+"','mydiv')\"  onMouseOut=\"javascript:hide('"+this.msgType+"','"+this.docId+"','"+this.remark+"','mydiv')\" href=\"javascript:CommonOpenDoc('"+this.menuId+"','"+this.docId+"','"+this.uuid+"','"+this.docBusinessId+"','"+this.taskId+"','"+this.taskName+"');\">"+(this.msgTitle==""?"无标题":this.msgTitle)+"</a></td>";
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
<body class="ibbody">

<%@include file="menuTemplate.jsp" %>
<div class="container-fluid main-content">
	<div class="social-wrapper wrapper" id="description"
		 style="overflow:hidden;position:absolute;width:100%;margin-top:3px;">
		<div id="social-container"
			 style="margin:auto;margin-top:40px;width:1120px;">
			<div class="item widget-container fluid-height  col-md-7-k" style="width: 1100px;">
				<div class="widget-content padded" style="padding:0px;height:100%">
					<%--<div class="mbox-header">--%>
						<%--<span class="hTitle" style="padding-right: 15px;">招标信息</span><a class="mini-button" onclick="signup();" iconCls="icon-add">报名</a>--%>
					<%--</div>--%>
					<div class="mini-tabs" activeIndex="0" id="tabs" style="width:1100px;height:100%;">
					 <div title="邀请招标" name="first"   >
					<div class="mbox-body" style="height: 90%;">
						<div id="miniSenDataGrid" class="mini-datagrid" showPager="false" style="width:100%;height:100%;" idField="id" sizeList="[5,10,15,30]" pageSize="15" multiSelect="true" >
							<div property="columns">
								<div type="checkcolumn"></div>
								<div type="indexcolumn" headerAlign="center" align ="center">序号</div>
								<div field="name"width="100" allowSort="false" headerAlign="center" renderer="openDetail" align="center">招标名称</div>
								<!-- <div field="contact" width="100" allowSort="false" headerAlign="center" align="center">采购方式</div> -->
								<div field="registerStartTime" width="120" allowSort="true" headerAlign="center" align="center">报名开始时间</div>
								<div field="registerEndTime" width="120" allowSort="false" headerAlign="center" align="center">报名结束时间</div>
								<div field="bidStartTime" width="120" allowSort="true" headerAlign="center" align="center">投标开始时间</div>
								<div field="bidEndTime" width="120" allowSort="false" headerAlign="center" align="center">投标结束时间</div>
								<div field="bidOpenTime" width="120" allowSort="false" headerAlign="center" align="center" >预计投标时间</div>
								<div field="status" width="120" allowSort="true" headerAlign="center" align="center" renderer="getStatus">报名状态</div>
								<div field="id" width="50" visible="false"></div>
							</div>
						</div>
					</div>
					</div>
					<div title="公开招标">
					<div class="mbox-body" style="height: 90%;">
						<div id="miniDataGrid" class="mini-datagrid" showPager="false" style="width:100%;height:100%;" idField="id" sizeList="[5,10,15,30]" pageSize="15" multiSelect="true" >
							<div property="columns">
								<div type="checkcolumn"></div>
								<div type="indexcolumn" headerAlign="center" align ="center">序号</div>
								<div field="name"width="100" allowSort="false" headerAlign="center" renderer="openDetail" align="center">招标名称</div>
								<!-- <div field="contact" width="100" allowSort="false" headerAlign="center" align="center">采购方式</div> -->
								<div field="registerStartTime" width="120" allowSort="true" headerAlign="center" align="center">报名开始时间</div>
								<div field="registerEndTime" width="120" allowSort="false" headerAlign="center" align="center">报名结束时间</div>
								<div field="bidStartTime" width="120" allowSort="true" headerAlign="center" align="center">投标开始时间</div>
								<div field="bidEndTime" width="120" allowSort="false" headerAlign="center" align="center">投标结束时间</div>
								<div field="bidOpenTime" width="120" allowSort="false" headerAlign="center" align="center" >预计投标时间</div>
								<div field="status" width="120" allowSort="true" headerAlign="center" align="center" renderer="getStatus">报名状态</div>
								<div field="id" width="50" visible="false"></div>
							</div>
						</div>
					</div>
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
						<span class="hTitle" style="padding-right: 15px;">报价消息</span><%--<a class="mini-button" onclick="quotePrice();" iconCls="icon-add">报价</a>--%>
					</div>
					<div class="mbox-body" style="height:311px;overflow-y:auto">
						<table class="table table-underline" id="NoticeDiv">
							<tbody>
							<div id="invitation" class="mini-datagrid" style="width:100%;height:100%;" idField="id" sizeList="[5,10,15,30]" showPager="false" pageSize="20" pager="#pager1" multiSelect="true" >
								<div property="columns">
									<div type="checkcolumn"></div>
									<div type="indexcolumn" headerAlign="center" align ="center">序号</div>
									<div field="name"width="100" allowSort="false" headerAlign="center" renderer="openQuotePriceDetail" align="center">招标名称</div>
									<div field="type"width="100" allowSort="false" headerAlign="center" renderer="invitation" align="center">类型</div>
									<!-- <div field="minAmount"width="100" allowSort="false" headerAlign="center" visible=false align="center">竞价下限金额</di
									<div field="minAmount"width="100" allowSort="false" headerAlign="center" align="center">竞价上限金额</div -->>
									<div field="endDate"width="100" allowSort="false" headerAlign="center" align="center">截至时间</div>
									<!-- <div field="payType"width="100" allowSort="false" headerAlign="center" visible=false align="center">付款方式</div> -->
									<!-- <div field="deliveryDate"width="100" allowSort="false" visible=false headerAlign="center"align="center">交货期</div> -->
									<div field="description"width="100" allowSort="false" headerAlign="center" align="center">其他说明</div>
									<div field="id" width="50" visible="false"></div>
									<div field="invitationId" width="50" visible="false"></div>
									<div field="price" width="50" visible="false"></div>
								</div>
							</div>
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
        url : encodeURI("${pageContext.request.contextPath}/admin/findSupplierMenu.action"),
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
        htmlStr+="<a href=\"javascript:location.href='${pageContext.request.contextPath}/supplier.jsp';\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
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
            htmlStr+="<a href=\"javascript:location.href='${pageContext.request.contextPath}/supplierMenu.jsp?menuId="+this.id+"';\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
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
            htmlStr += "<a href=\"javascript:location.href='${pageContext.request.contextPath}/supplierMenu.jsp?menuId="+treeData[b].id+"';\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
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
		// 加载招标信息(公开招标)
		mini.parse();
       var grid = mini.get('miniDataGrid');
        grid.setUrl(gDir + '/business/findTender.action');
        grid.load({sortField:"",sortOrder:"desc"});
        //(邀请招标)
        var gridSen= mini.get('miniSenDataGrid');
        gridSen.setUrl(gDir + '/business/findInvite.action');
        gridSen.load({sortField:"",sortOrder:"desc"});
        
        var grid1 = mini.get('invitation');
        grid1.setUrl(gDir + '/business/bid/findMyInvite.action');
        grid1.load({sortField:"",sortOrder:"desc"});
         console.log(grid1.getData());
                //获取待办
     getMsgList("{msgType:1}");
//        //获取图片新闻
//        getImgNewsList();
//        //获取新闻
//        getNewsList();
//        //获取公告
//        getNoticeList();
      //获取消息数量
        getMsgCount();
    });

    function signup() {
         var grid = mini.get('miniDataGrid');
         var tabType;
        var minGrid =mini.get('miniSenDataGrid');
        var cell = grid.getSelected();
        console.log(cell);
        var tabs=mini.get("tabs");
        var tabAct =tabs.getActiveTab();
        if(tabAct.title=="邀请招标"){
            tabType=1;
        }else{
            tabType=2;
        }
        if(cell == null) {
        	var minCell = minGrid.getSelected();
        	cell=minCell;
            /* mini.alert("请选择要报名的公开招标!");
            return ; */
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

	function openDetail(cell) {
		var urlPath = "${pageContext.request.contextPath}/business/getTradeDetail.action?id=" + cell.row.id;
		return "<a style='color:blue;' href='javascript:openTrader(\"" + urlPath + "\", \"" + cell.row.name + "\");'>" + cell.value + "</a>"
	}

	function openQuotePriceDetail(cell) {
		var  type =cell.row.type;
		if(type == 1){
			 return "<a style='color:red;' >"+cell.value+"</a>";
		}
		if(type == 2){
			var urlPath="${pageContext.request.contextPath}/business/findInvitation.action?id=" + cell.row.invitationId;
			return "<a style='color:green;' href='javascript:openTrader(\"" + urlPath + "\", \"" + cell.row.name + "\");'>" + cell.value + "</a>";
		}
	}

	function openTrader(urlPath, title) {
        mini.open({id:"oWinDlg",title:title,url:encodeURI(urlPath),showMaxButton: true});
	}

    function getStatus(cell){
        var strColor='0f0',strStatus = cell.value;
        switch(parseInt(cell.value,10)){
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

    function invitation(cell) {
        var type = cell.value;
        if(type == 1)
            return "<font color='blue'>澄清报价</font>";
        else if(type == 2)
            return "<font color='#006400'>邀请竞价</font>";
		return "<font color='red'>未知类型</font>";
	}

	function quotePrice() {
        var grid = mini.get('invitation');
        var cell = grid.getSelected();
        if(cell == null) {
            mini.alert("请选择要报价的招标!");
            return ;
        }
        var invitationId = cell.invitationId;
        var minAmount = cell.minAmount;
        var type = cell.type;
        var name = cell.name;
        var oWinDlg=mini.get('oWinDlg');
        if(oWinDlg==null){
            oWinDlg=mini.open({
                id:"oWinDlg",
                showFooter:true,
                headerStyle:"font-weight:bold;letter-spacing:4px",
                footerStyle:"padding:5px;height:25px"
            });
        }
        var strURL=gDir+"/business/bid/quotePrice.jsp?invitationId=" + invitationId + "&type=" + type + "&minAmount=" + minAmount;
        var strTitle = name;
        if(type == 1)
            strTitle += "-澄清报价";
		else if(type == 2)
		    strTitle += "-邀请竞价";

        var intWidth=400,intHeight=200;
        var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit(' + type + ');">确定</a>';
        strButton+='<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
        oWinDlg.setUrl(strURL);
        oWinDlg.setTitle(strTitle);
        oWinDlg.setWidth(intWidth);
        oWinDlg.setHeight(intHeight);
        oWinDlg.setFooter(strButton);
        oWinDlg.showAtPos("center","middle");
	}

    function goSubmit(type) {
        mini.get('oWinDlg').getIFrameEl().contentWindow.goSubmit();
    }

    function goCloseDlg(name){
        var oWinDlg=mini.get(name);
        oWinDlg.setUrl("about:blank");
        $(oWinDlg.getBodyEl()).html("");
        $(oWinDlg.getFooterEl()).html("");
        oWinDlg.hide();
        mini.get('invitation').reload();
    }
</script>
</body>
</html>