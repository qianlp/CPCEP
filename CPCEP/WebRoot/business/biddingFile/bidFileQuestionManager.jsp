<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String bidFileUUID=request.getParameter("bidFileUUID");
    request.setAttribute("bidFileUUID",bidFileUUID);%>

<!DOCTYPE HTML>
<html>
<head>
    <title></title>
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

</head>
<body>
        <div class="mini-toolbar" style="border:0px;">
            <a class="mini-button"  onclick="privateClarify()">单独澄清</a>
<!--             <a class="mini-button" onclick="publicClarify()">公开澄清</a> -->
        </div>
        <div class='mini-fit' style='width:100%;height:100%;'>
            <div id='miniDataGrid' class='mini-datagrid' style="width:100%;height:100%;" idField='uuid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true'>
                <div property='columns'>
                    <div type='checkcolumn'></div>
                    <div type='indexcolumn' headerAlign='center' align='center' >问题序号</div>
                    <div field='supName' width='80'  headerAlign='center' align='center'>供应商名称</div>
                    <div field='remark' width='200'  headerAlign='center' align='center'>问题描述</div>
                    <div field='file' width='120' headerAlign='center' align='center' renderer='getQuestionFile'>附件</div>
                    <div field='feedBack' width='120' headerAlign='center' align='center' >澄清反馈</div>
                    <div field='status' width='120' headerAlign='center' align='center' renderer='getFeedBack'>澄清反馈文件</div>
                    <div field='status' width='120' headerAlign='center' align='center' renderer='getStatus'>状态</div>
                </div>
            </div>
        </div>
<script>
    mini.parse();
    var gDir="${pageContext.request.contextPath}";
    var grid = mini.get("miniDataGrid");
    var bidFileUUID = "${bidFileUUID}";
    grid.setUrl(gDir + '/business/biddingFile/getQuestionListByBidId.action?bidFileUUID='+bidFileUUID );//bidFileId
    var pid = "-1";//默认pid为-1
    grid.load({pid:pid});

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


    function getStatus(cell) {
        var feedBack  =cell.row.feedBack;
        var statusStr = "";
        if(feedBack !=null && feedBack!=""){
            statusStr = "已处理"
        }else{
            statusStr = "未处理"
        }
        var html = '<a href="javascript:void(0);" >'+statusStr+'</a> ';
        return html;
    }
    function isNull(str){
        return str == null || str == undefined || str=="";
    }
    function getQuestionFile(cell) {
        if(!isNull(cell.row.questionFileUUID)){
           return  '<a   target="view_window" href="${pageContext.request.contextPath}/business/download.action?id='+ cell.row.questionFileUUID +'" >'+cell.row.questionFileName+'</a> ';
        }
    }
    function getFeedBack(cell) {
        if(!isNull(cell.row.feedBackFileUUID)){
            return '<a target="view_window" href="${pageContext.request.contextPath}/business/download.action?id='+ cell.row.feedBackFileUUID +'" >'+cell.row.feedBackFileName+'</a> ';
        }
    }

    function privateClarify() {
        var rows = grid.getSelecteds();
        if(rows.length>1){
            mini.alert("仅可以选择一行！");
            return;
        }else{
            var feedBack = rows[0].feedBack;
            if(feedBack!=null&&feedBack!=""){
                mini.alert("已经澄清！");
                return;
            }
            var uuid = rows[0].uuid;
            var fileUUID = rows[0].bidFileUUID;
            var url = gDir+"/business/biddingFile/bidFileFeedBack.action?uuid="+uuid+"&bidFileUUID="+fileUUID;
            CommonOpenDoc(url);
        }

    }

    /* 暂时隐藏 by 2018-08-29 hzl
    function publicClarify() {
        var rows = grid.getSelecteds();
        if(rows.length==0){
            mini.alert("请先选择数据");
            return;
        }else{
            for(var i=0;i<rows.length;i++){
                var feedBack = rows[i].feedBack;
                if(feedBack!=null&&feedBack!=""){
                    mini.alert("请选择未澄清数据！");
                    return;
                }
            }

            var questionIds ="";
            for(var i=0;i<rows.length;i++){
                questionIds = questionIds+","+rows[i].uuid;
            }


            var url = gDir+"/business/biddingFile/publicClarifyList.action?bidFileUUID="+bidFileUUID+"&questionIds="+questionIds;
            mini.open({
                name:"xxx",
                url:url,
                title:"公开澄清",
                showFooter:false,
                width:800,
                height:500,
                headerStyle:"font-weight:bold;letter-spacing:4px",
                ondestroy:function (data) {
                    grid.reload();
                }
            });
        }

    }
    */

    function openMiniWindow(url, title) {
        console.log(title);
        var intWidth=800,intHeight=500;
        var oWinDlg = mini.get('oWinDlg');
        if(oWinDlg == null){
            oWinDlg = mini.open({
                id:"oWinDlg",
                showFooter:false,
                headerStyle:"font-weight:bold;letter-spacing:4px",
                ondestroy:function () {
                    grid.reload();
                }
            });
        }
        oWinDlg.setUrl(url);
        oWinDlg.setTitle(title);
        oWinDlg.setWidth(intWidth);
        oWinDlg.setHeight(intHeight);
        oWinDlg.showAtPos("center","middle");
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