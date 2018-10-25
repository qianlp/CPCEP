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
    <div region="center"
         style="overflow:hidden;border-bottom:0;border-right:1">
        <div class="mini-toolbar">
            <a class="mini-button" iconCls="icon-add" onclick="invitation();">邀请</a>
        </div>
        <div class="search_body" id="search_body">
            <div class="search_content">
                <div class="search_title">供应商名称：</div>
                <div class="search_field" Operator="@" DataType="text" Item="">
                    <input name="name" id="name" class="mini-textbox">
                </div>
            </div>
            <div class="search_button">
                <a class="mini-button" tooltip="清空查询条件" plain="true" iconCls="icon-remove" onclick="ClearForm"></a>
                &nbsp;<a class="mini-button" iconCls="icon-search" onclick="CommonSearch">搜索</a>
            </div>
        </div>
        <div class='mini-fit' style='width:100%;height:100%;'>
            <div id='miniDataGrid' class='mini-datagrid' style="width:100%;height:100%;" borderStyle="border:0;" idField='unid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true'>
				<div property='columns'>
					<div type='checkcolumn'></div>
					<div type='indexcolumn' headerAlign='center' align='center' >序号</div>
					<div field='name' width='50'  headerAlign='center' align='center'>供应商名称</div>
					<div field='contactAddress' width='50'  headerAlign='center' align='center'>通讯地址</div>
					<div field='corporations' width='50'  headerAlign='center' align='center'>法人代表</div>
					<div field='contacts' width='50'  headerAlign='center' align='center'>联系人</div>
					<div field='mobile' width='50'  headerAlign='center' align='center'>联系方式</div>
					<div field='id' visible='false' width='50'  headerAlign='center' align='center'></div>
				</div>
            </div>
        </div>
    </div>
</div>
<script>
    mini.parse();
    var gDir="${pageContext.request.contextPath}";
    var grid = mini.get("miniDataGrid");
    grid.setUrl(gDir + '/business/bid/findInvitationSupplier.action?uuid=${uuid}');
    var pid = "-1";//默认pid为-1
    grid.load({pid:pid});

    //清空查询条件
    function ClearForm(){
        mini.getbyName("name").setValue("");
    }
    function CommonSearch(){
        var searchArr=[];
        $("#search_body .search_field").each(function(){
            var obj={};
            var name = $($(this).find("input")[0]).attr("id");
            obj.name = name.split("$")[0];
            //obj.name=$($(this).find("input")[0]).attr("name");
            obj.operator=$(this).attr("Operator");
            obj.dataType=$(this).attr("DataType");
            obj.value=mini.getbyName(obj.name).getValue();
            if(obj.value!=""){
                if(obj.dataType == 'date'){
                    obj.value=mini.getbyName(obj.name).getText();
                }
                searchArr.push(obj);
            }
        });
        grid.load({search:mini.encode(searchArr),pid:pid});
    }

    /**
	 * 邀请竞价
	 */
    function invitation() {
        var grid = mini.get('miniDataGrid');
        var cell = grid.getSelected();
        if(cell == null) {
            mini.alert("请选择要邀请的供应商!");
            return ;
        }
		var url = "${pageContext.request.contextPath}/business/bid/inviteForm.action?bidId=${uuid}&supplierId=" + cell.uuid;
        openMiniWindow(url, "邀请竞价", "invitation");
	}

    function openMiniWindow(url, title, id) {
        var intWidth=600,intHeight=350;
        var oWinDlg = mini.get(id);
        if(oWinDlg == null){
            oWinDlg = mini.open({
                id : id,
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