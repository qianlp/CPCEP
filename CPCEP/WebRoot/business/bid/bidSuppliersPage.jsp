<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String bidFileId=request.getParameter("uuid");
    request.setAttribute("bidFileId",bidFileId);
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>供应商投标列表</title>
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
        body .search_body{border:0px solid #000;overflow:hidden;display:block;padding-top:5px;}
        .search_content,.search_title,.search_field,.search_button{float:left}
        .search_content{width:240px;margin:2px;height:30px;}
        .search_title{width:90px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px}
        .search_field{width:130px;text-align:left}
        .search_button{margin:1px;}
    </style>
    <script type="text/javascript">
        mini.open=function(E) {
                if (!E) return;
                var C = E.url;
                if (!C) C = "";
                var B = C.split("#"),
                    C = B[0];
                if (C && C[O1O1oo]("_winid") == -1) {
                    var A = "_winid=" + mini._WindowID;
                    if (C[O1O1oo]("?") == -1) C += "?" + A;
                    else C += "&" + A;
                    if (B[1]) C = C + "#" + B[1]
                }
                E.url = C;
                E.Owner = window;
                var $ = [];

                function _(A) {
                    try {
                        if (A.mini) $.push(A);
                        if (A.parent && A.parent != A) _(A.parent)
                    } catch (B) {}
                }
                _(window);
                var D = $[0];

                return D["mini"]._doOpen(E)
            }
    </script>
</head>
<body>
<div class="mini-layout" style="width:100%;height:100%;" id="layout">
    <div region="center"
         style="overflow:hidden;border-bottom:0;border-right:1">
        <div class='mini-fit' style='width:100%;height:100%;'>
            <div id='miniDataGrid' class='mini-datagrid' style="width:100%;height:100%;" borderStyle="border:0;" idField='uuid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true'>
                <div property='columns'>
                    <div type='indexcolumn'  width='50'headerAlign='center' align='center' >序号</div>
                    <div field='supplierName' width='150'  headerAlign='center' align='left'>供应商名称</div>
                    <div width="300" headerAlign="center" renderer="getBidSupplierOperation">操作</div>
					<div field='supplierId' visible='false' width='50'  headerAlign='center' align='center'></div>
                    <div field='bidUuid' visible='false'></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var endDate="${endDate}";
    mini.parse();
    var gDir="${pageContext.request.contextPath}";
    var grid = mini.get("miniDataGrid");
    var gType="${param.gType}";
    grid.setUrl(gDir + '/business/bid/bidSuppliers.action?hasBid=true&uuid=${bidFileId}');
    grid.load();

    /*
		描述:提交窗口内Form
	*/
    function goSubmit(){
        mini.get('oWinDlg').getIFrameEl().contentWindow.goSubmit();
    }
    /*
		描述:点击提交窗口内Form时,控制“确定”状态
	*/
    function goToolsBtn(status){
        var oButton=mini.get("btnSave");
        oButton.setEnabled(status);
    }
    /*
		描述:关闭窗口
	*/
    function goCloseDlg(name){
        var oWinDlg=mini.get(name);
        oWinDlg.setUrl("about:blank");
        $(oWinDlg.getBodyEl()).html("");
        $(oWinDlg.getFooterEl()).html("");
        oWinDlg.hide();
    }
    function goReload() {
        mini.alert("操作成功!","消息提示",function(){
            goCloseDlg("oWinDlg");
            grid.reload();
        });
    }

    function getBidSupplierOperation(cell) {
        var uuid = cell.row.uuid;
        var name = cell.row.name;
        var userId = cell.row.userId;
        var supName =cell.row.supplierName;
        var openFile = "${pageContext.request.contextPath}/business/biddingFile/openSupplierBidFile.jsp?uuid=" + cell.row.bidUuid;
        var url1 = "${pageContext.request.contextPath}/business/bid/showBidOffer.action?times=1&uuid=${bidFileId}&userId=" + userId + "&role=view";
        var url2 = "${pageContext.request.contextPath}/business/bid/showBidOffer.action?times=2&uuid=${bidFileId}&userId=" + userId + "&role=view";
        var url3 = "${pageContext.request.contextPath}/business/bid/showBidOffer.action?times=3&uuid=${bidFileId}&userId=" + userId + "&role=view";
        var invitationUrl = "${pageContext.request.contextPath}/business/techEvalClarify/techEvalClarify.action?name=${name}&code=${code}&version=${version}&bidFileUUID=${bidFileId}&supUUID=" + userId+"&supName="+supName;
        console.debug(uuid);
        console.debug(userId);
        var html="";
        if(gType=="1"){
        	html = '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + invitationUrl + '\');">评标澄清</a>';
        }else if(gType=="2"){
        	html = '<a href="javascript:void(0);" onclick="openMiniWindow(\'' + openFile + '\', \'投标附件\');">下载附件</a> &nbsp;' +
            '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url1 + '\');">投标报价</a> &nbsp;' +
            '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url2 + '\');">澄清报价</a> &nbsp;' +
            '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url3 + '\');">竞价报价</a> &nbsp;';
        }else{
        	if(endDate!=null&&endDate!=""){
        		var end=new Date(Date.parse(endDate.replace(/-/g,"/")));
            	if(end>new Date()&&typeof(end)!="undefined"){
            		html = '<a href="javascript:void(0);" onclick="openMiniWindow(\'' + openFile + '\', \'投标附件\');">下载附件</a> &nbsp;' +
                    '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url1 + '\');">投标报价</a> &nbsp;' +
                    '<a href="javascript:void(0);" onclick="CommonendDate();">澄清报价</a> &nbsp;'+
                    '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url3 + '\');">竞价报价</a> &nbsp;' +
        		 	'<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + invitationUrl + '\');">评标澄清</a> ';	
            	}else{
            		html = '<a href="javascript:void(0);" onclick="openMiniWindow(\'' + openFile + '\', \'投标附件\');">下载附件</a> &nbsp;' +
                    '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url1 + '\');">投标报价</a> &nbsp;' +
                    '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url2 + '\');">澄清报价</a> &nbsp;' +
                    '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url3 + '\');">竞价报价</a> &nbsp;' +
        		 	'<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + invitationUrl + '\');">评标澄清</a> ';	
            	}
        	}else{
        		html = '<a href="javascript:void(0);" onclick="openMiniWindow(\'' + openFile + '\', \'投标附件\');">下载附件</a> &nbsp;' +
                '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url1 + '\');">投标报价</a> &nbsp;' +
                '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url2 + '\');">澄清报价</a> &nbsp;' +
                '<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + url3 + '\');">竞价报价</a> &nbsp;' +
    		 	'<a href="javascript:void(0);" onclick="CommonOpenDoc(\'' + invitationUrl + '\');">评标澄清</a> ';	
        	}
        }
        return html;
    }

    function CommonOpenDoc(strPathUrl){
        //当打开方式为window.open时使用
        var gWidth=window.screen.width;
        //当打开方式为window.open时使用
        var gHeight=window.screen.height;
        if(gHeight>0){var T=(screen.height-100-gHeight)/2}else{var T=0}
        if(gWidth>0){var L=(screen.width-20-gWidth)/2}else{var L=0}
        var strStatus="height="+gWidth+",width="+gWidth+",top="+T+",left="+L+",toolbar=no,menubar=no,location=no,status=yes,resizable=yes,scrollbars=yes";
        var objWin=window.open(strPathUrl,"_blank",strStatus);
        setTimeout(function(){objWin.focus()},100)
    }
   function CommonendDate(){
	   alert("未到澄清截止时间");
   }
    function openMiniWindow(url, title) {
        console.log(title);
        var intWidth=600,intHeight=350;
        var oWinDlg = mini.get('oWinDlg');
        if(oWinDlg == null){
            oWinDlg = mini.open({
                id:"oWinDlg",
                showFooter:false,
                headerStyle:"font-weight:bold;letter-spacing:4px"
            });
        }
        oWinDlg.setUrl(url);
        oWinDlg.setTitle(title);
        oWinDlg.setWidth(intWidth);
        oWinDlg.setHeight(intHeight);
        oWinDlg.showAtPos("center","middle");
    }

</script>
</body>
</html>