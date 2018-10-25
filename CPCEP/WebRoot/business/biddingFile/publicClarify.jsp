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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
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
<!--功能按钮-->
<div class='mini-toolbar'
     style='padding:0;border-bottom-width:0;border:0px;'>
    <table id='funcBtn' style='width:100%;'>
        <tr>
            <td style="width:100%;">
                <a class="mini-button" iconCls="icon-add" onclick="save()" plain="true">保存</a>
            </td>
        </tr>
    </table>
</div>
<div class="mini-layout" style="width:100%;height:100%;" id="layout">
    <%--中心表格--%>
    <div region="center"
         style="overflow:hidden;border-bottom:0;border-right:1">

        <div class='mini-fit' style='width:100%;height:100%;'>
            <div id='miniDataGrid' class='mini-datagrid'   style="width:100%;height:100%;" borderStyle="border:0;" idField='uuid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true'

                 allowCellEdit="true" allowCellSelect="true" allowCellwrap="true"
                 editNextOnEnterKey="true" editNextRowCell="true" oncellvalidation="onCellValidation"
            >
                <div property='columns'>
                    <div field='uuid' visible='false' width='50' headerAlign='center' align='center'>UUID</div>
                    <div field='bidFileUUID' visible='false' width='50' headerAlign='center' align='center'>bidFileUUID</div>
                    <div field='supName' visible='false' width='50' headerAlign='center' align='center'>supName</div>
                    <div field='supUUID' visible='false' width='50' headerAlign='center' align='center'>supUUID</div>
                    <div type='checkcolumn' ></div>
                    <div type='indexcolumn' width='40' headerAlign='center' align='center' >问题序号</div>
                    <div field='remark' width='200'  headerAlign='center' align='center'>问题描述</div>
                    <div field='file' width='120' headerAlign='center' align='center' renderer='getQuestionFile' >附件</div>
                    <div field='feedBack' width='120' headerAlign='center' align='center' >澄清反馈
                        <input property="editor" class="mini-textbox" style="width:100%;"/>
                    </div>
                    <div  name="operator" width='180' headerAlign='center' renderer="onActionRenderer" align='center'>附件上传</div>
                </div>
            </div>
        </div>

    </div>
</div>
<script>
    mini.parse();
    var gDir="${pageContext.request.contextPath}";
    var grid = mini.get("miniDataGrid");
    var bidFileUUID = "${bidFileUUID}";
    var questionIds = "${questionIds}";

    grid.setUrl(gDir + '/business/biddingFile/getQuestionListByBidId.action?bidFileUUID='+bidFileUUID+"&questionIds="+questionIds );//bidFileId
    var pid = "-1";//默认pid为-1
    grid.on("load", function () {
        initFile();
    });
    grid.on("cellendedit",function(){
        initFile();
    });
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

    function getQuestionFile(cell) {
        if(!isNull(cell.row.questionFileUUID)){
            return  '<a href="${pageContext.request.contextPath}/business/download.action?id='+ cell.row.questionFileUUID +'" >'+cell.row.questionFileName+'</a> ';
        }
    }

    //格式化表格
    function onActionRenderer(e) {
        var record = e.record, rowIndex = e.rowIndex;
        if(!isNull(record.feedBackFileUUID)){
            return '<a id="file_' + rowIndex + '" href="${pageContext.request.contextPath}/business/download.action?id='+record.feedBackFileUUID+'" >' + record.feedBackFileName + '</a>'
                + '<button type="button" class="layui-btn layui-btn-sm" id="upload_' + rowIndex + '">'
                + '<i class="layui-icon">&#xe67c;</i>点击上传' + '</button>';
        }else{
            return '<a id="file_' + rowIndex + '">' + record.feedBackFileName + '</a>'
                + '<button type="button" class="layui-btn layui-btn-sm" id="upload_' + rowIndex + '">'
                + '<i class="layui-icon">&#xe67c;</i>点击上传' + '</button>';
        }

    }
    function initFile(){
        var grid  = mini.get("miniDataGrid");
        for(var i = 0 ,length = grid.getData().length;i<length;i++){
            initUpload(i);
        }
    }

    function initUpload(rowIndex){
        layui.use('upload', function () {
            var upload = layui.upload;
            upload.render({
                elem: "#upload_" + rowIndex//绑定元素
                , accept: "file"
                , data: {
                    type: 16
                }
                , url: "${pageContext.request.contextPath}/business/upload.action" //上传接口
                , done: function (res) {
                    //上传完毕回调
                    var result = mini.decode(res);
                    $("#file_"+rowIndex).html(result.data.fileName);
                    $("#file_"+rowIndex).attr("href", "${pageContext.request.contextPath}/business/download.action?id=" + result.data.uuid);
                    var grid  = mini.get("miniDataGrid");
                    grid.updateRow(grid.getRow(rowIndex),{feedBackFileName:result.data.fileName,feedBackFileUUID:result.data.uuid})
                }
                , error: function () {
                    //请求异常回调
                    alert("上传失败")
                }
            });
        });
    }

    function save() {


        var row1 = grid.findRows(function (row) {
            if (isNull(row.feedBack)) return true;
        });
        if (row1.length!=0) {
            alert("澄清反馈不能为空");
            return ;
        }
        var columns = grid.getChanges(null, false);
        console.log(columns);
        var json = JSON.stringify(columns);
        console.log(json);
        $.post("${pageContext.request.contextPath}/business/biddingFile/saveFeedBacks.action", {
            feedbacks: json
        }, function (result) {
            result = mini.decode(result);
            if (result.code != 200) {
                mini.alert(result.message);
                return;
            }
            mini.alert("操作成功");
            window.CloseOwnerWindow();

        });
    }

    function isNull(value) {
        if (value == null || value == undefined || value.length == 0) {
            return true;
        }
        return false;
    }


    // 统一保存方法
    function close() {
        mini.confirm("确定要关闭吗？", "提示", function (action) {
            if (action == "ok") {
                window.CloseOwnerWindow();
            }
        })

    }

    function openMiniWindow(url, title) {
        console.log(title);
        var intWidth=800,intHeight=500;
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