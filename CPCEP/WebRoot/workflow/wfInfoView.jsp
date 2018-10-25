
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:action name="findWorkFlowByDocId" namespace="/admin" executeResult="true">
	<s:param name="docId" >${param.docId}</s:param>
</s:action>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>流程图形化显示</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
		<style type='text/css'>
			html,body{
				overflow:hidden;
				padding:0px;
				margin:0px;
			}
			.dvAllIdea{
				float:left;margin:2px 2px;
			}
			.dvCurr{
				background-color:lightblue;
			}
			.dvAllIdea table{
				width:150px;border:1px solid #888;font-size:14px
			}
			.dvAllIdea table tr{
				height:30px;
			}
			.dvAllIdea table td{
				border:1px solid #888;
			}
			.tdLogtache{
				font-weight:bold;text-indent:5px;background-color:#ffc
			}
			.tdLogidea{
				padding:2px;padding-left:5px;line-height:20px
			}
			.tdLogname{
				padding-left:80px;letter-spacing:5px
			}
			.tdLogtime{
				text-align:right;padding-right:2px
			}
			.mini-tabs{
				border-top:0px;
			}
			.mini-tabs-bodys{
				padding:0px;
			}
			.mini-tabs-body{
				overflow:hidden;
			}
		</style>
		<script>
		var BasePath="${pageContext.request.contextPath}/js/workflow",gWFGraphXML='',gWFFlowLogXML='${baseWorkFlow.wfFlowLogXml }',gWFCurNodeID=['${baseWorkFlow.wfCurNodeId }'],gWFRouterID="${baseWorkFlow.wfRouterId }",arrTmpCurUser=["${baseWorkFlow.curUser }"],gCurUser="${baseWorkFlow.curUser }",gWFStatus="${baseWorkFlow.wfStatus }",gWFTacheName="${baseWorkFlow.wfTacheName }";
		function repXMl(xmlStr){
			return $.parseXML(xmlStr.replace(/[\r\n\t]/g,"").replace(/\&/g, "&amp;"));
		}
		
		function loadWfLog(){
			var gWFLogXML=repXMl(gWFFlowLogXML);
			var sTacheNum=1,DataPrefix=DataSuffix=strId=WFIdeaPrefix=WFIdeaSuffix="";
			
			$.each($(gWFLogXML).find("Log").children(),function(){
				var _action=$(this).attr("action");
				DataPrefix='<table cellspacing=1 cellpadding=1><tr><td class="tdLogtache">'+"("+sTacheNum+")&nbsp;"+$(this).attr("tache")+'</td></tr>';
				DataSuffix='<tr><td class="tdLogname">'+$(this).attr("user")+'</td></tr><tr><td class="tdLogtime">'+$(this).attr("time")+'</td></tr><tr><td class="tdLogtime" title="'+_action+'" nowrap=true>'+(_action.length>15?_action.substr(0,15)+"...":_action)+'</td></tr></table>';
				//dojo.addClass(dojo.create("div",{innerHTML:DataPrefix+DataSuffix},"WFInfo","last"),"dvAllIdea");
				$("#WFInfo").append("<div class='dvAllIdea'>"+(DataPrefix+DataSuffix)+"</div>");
				sTacheNum++;
			})
			
			DataPrefix='<table cellspacing=1 cellpadding=1><tr><td class="tdLogtache">('+sTacheNum+')&nbsp;当前环节名称</td></tr>';
			DataPrefix+='<tr><td class="tdLogTime">'+gWFTacheName+'</td></tr>';
			if(gWFStatus<2){
				DataPrefix+='<tr><td class="tdLogtache">相关处理人</td></tr>';
				DataPrefix+='<tr><td class="tdLogTime" nowrap=true title="'+arrTmpCurUser.join(";")+'">'+arrTmpCurUser.slice(0,5).join(";")+(arrTmpCurUser.length<6?'':'...')+'</td></tr></table>';
			}else{
				DataPrefix="<table cellspacing=1 cellpadding=1><tr><td class='tdLogtache'>流程已结束</td></tr></table>";
			}
			$("#WFInfo").append("<div class='dvAllIdea dvCurr'>"+(DataPrefix)+"</div>");
		}
		
		function ChangeDivHeight(){
			var inHeight=$("body").innerHeight();
			var inWidth=$("body").innerWidth();
			$("#grh").css("height",inHeight-30);
			$("#grh").css("width",inWidth-135);
		}
		
		window.onresize=function(){
			location.href="${pageContext.request.contextPath}/workflow/wfInfoView.jsp?docId=${param.docId}";
		}
		
		</script>
		<script src="${pageContext.request.contextPath}/js/workflow/js/Client-mini.js" type='text/javascript'></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/workflow/js/showApplication.js"></script>
	</head>
	<body>
		<div id="tabMenu" class="mini-tabs" tabPosition="top" style="width:100%;height:100%;" plain="false" >
				<div title='流程图' name='流程图'  style="">
					<div style="width:100px;height:100%;float:left;text-align:left;padding-top:5px;">
						<IMG src="${pageContext.request.contextPath}/js/workflow/images/picLegend.png">
					</div>
					<div id="grh"  style="width:85%;height:100%;float:right;overflow:hidden">
						<textarea id="WFGraphXML" style="display:none">
							${baseWorkFlow.wfGraphXml }
						</textarea>
						<div id="graph" style="width:100%;height:100%"></div>
						<textarea style="display:none" value="" id="GraphXML"></textarea>
					</div>
				</div>
				<div title='流程信息' name='流程信息' >
					<div id='WFInfo' style="width:100%;height:100%;overflow-x:scroll;overflow-y:hidden;float:left;">
						
					</div>
				</div>
			</div>
		<script>
		mini.parse();
		Application('${pageContext.request.contextPath}/js/workflow/config/graph-show.xml');
		loadWfLog();
		
		ChangeDivHeight();
		</script>
	</body>
	
</html>
