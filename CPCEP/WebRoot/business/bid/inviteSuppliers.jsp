<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String menuId=request.getParameter("menuId");
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>邀请竞价</title>
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
    <%--中心表格--%>
    <div region="center"
         style="overflow:hidden;border-bottom:0;border-right:1">
        <div class="mini-toolbar">
            <a class="mini-button" iconCls="icon-add" onclick="open()">开标</a>
        </div>
        <div class='mini-fit' style='width:100%;height:100%;'>
            <div id='miniDataGrid' class='mini-datagrid' style="width:100%;height:100%;" borderStyle="border:0;" idField='unid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true'>
                <div property='columns'>
                    <%--<div type='checkcolumn'></div>--%>
                    <div type='indexcolumn' headerAlign='center' align='center' >序号</div>
                    <div field='uuid' visible='false' width='50'  headerAlign='center' align='center'>招标编号</div>
                    <div field='name' width='120' headerAlign='center' align='center'>招标名称</div>
                    <div field='supplierName' width='120' headerAlign='center' align='center'>供应商名称</div>
					<div field='wfStatus' width='120' headerAlign='center' align='center' renderer='getStatus'>投标状态</div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    mini.parse();
    var gDir="${pageContext.request.contextPath}";
    var grid = mini.get("miniDataGrid");
    grid.setUrl(gDir + '/business/bid/bidSuppliers.action?uuid=${uuid}');
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
		var strColor, strStatus = cell.value;
        switch(parseInt(cell.value,10)){
            case 8 :
                strStatus='审核失败';
                strColor='red';
                break;
            case 2:
                strColor='blue';
                strStatus='审核通过';
                break;
            default:
        }
        return "<span style='color:"+strColor+"'>" + strStatus + "</span>";
	}

	function open() {
        var fullDate = "${bid.bidOpenTime}".split("-");
        var openTime = new Date(fullDate[0], fullDate[1]-1, fullDate[2].split(" ")[0], 0, 0, 0);
        var now = new Date();
        var title = "确认开标?";
        if(openTime > now)
            title = "未到开标时间，是否提前开标?";
        else if(grid.totalCount < 3)
            title = "投标不足三家，是否确认开标?";

		// 未到开标时间
		mini.confirm(title,"确认框", function(r){
			if(r !="ok")
			    return ;
			$.ajax({
				url: gDir + '/business/bid/open.action?uuid=${uuid}',
				dataType: "json",
				cache: false,
				success: function(data) {
					if (data.code == 200) {
						mini.alert("开标成功!");
					}else{
						mini.alert(data.message);
					}
				}
			});
		});
	}
</script>
</body>
</html>