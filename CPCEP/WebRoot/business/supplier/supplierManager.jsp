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
    <title>供应商库</title>
    <meta name="description" content="供应商库">
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
        .search_content{width:280px;margin:2px;height:30px;}
        .search_title{width:100px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px}
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
            <a class="mini-button" iconCls="icon-ok" onclick="goManager('Ok')">确定</a>
        </div>
        <div class="search_body" id="search_body">
            <div class="search_content">
                <div class="search_title">供应商编号：</div>
                <div class="search_field" Operator="@" DataType="text" Item="">
                    <input name="projectCode" id="projectCode" class="mini-textbox">
                </div>
            </div>
            <div class="search_content">
                <div class="search_title">供应商名称：</div>
                <div class="search_field" Operator="@" DataType="text" Item="">
                    <input name="projectName" id="projectName" class="mini-textbox">
                </div>
            </div>
            <div class="search_button">
                <a class="mini-button" tooltip="清空查询条件" plain="true" iconCls="icon-remove" onclick="ClearForm"></a>
                &nbsp;<a class="mini-button" iconCls="icon-search" onclick="CommonSearch">搜索</a>
            </div>
        </div>
        <div class='mini-fit' style='width:100%;height:100%;'>
            <div id='miniDataGrid' name="miniDataGrid" class="mini-datagrid" style="width:100%;height:100%;"
                 url="${pageContext.request.contextPath}/business/loadSupplierForPager.action"
                 idField="uuid"
                 allowResize="true" multiSelect='true'
                 allowCellEdit="true" allowCellSelect="true" allowCellwrap="true"
                 editNextOnEnterKey="true" editNextRowCell="true"/>

            <div property='columns'>
                <div type='checkcolumn'></div>
                <div type='indexcolumn' headerAlign='center' align ='center'>序号</div>
                <div field='name' 	width='100' allowSort='true' headerAlign='center'  align='center'	renderer='contentDetail'>供应商名称</div>
                <div field='contacts' 	width='100' allowSort='false' headerAlign='center'  align='center'	>联系人</div>
                <div field='phone' 	width='120' allowSort='false' headerAlign='center'  align='center'	>电话</div>
                <div field='mobile' 	width='120' allowSort='false' headerAlign='center'  align='center'	>手机</div>
                <div field='fax' 	width='120' allowSort='false' headerAlign='center'  align='center'	>传真</div>
                <div field='email' 	width='120' allowSort='false' headerAlign='center'  align='center'	>邮箱</div>
                <div field='contactAddress' 	width='120' allowSort='false' headerAlign='center'  align='center'	>通信地址</div>
                <div field='corporations' 	width='120' allowSort='false' headerAlign='center'  align='center'	>法人代表</div>
                <div field='wfStatus' width='100' allowSort='true' headerAlign='center' align='center' renderer='CommonFlowStatus'>流程状态</div>
                <div field='curUser' 	width='120' allowSort='true' headerAlign='center'  align='center'	>当前处理人</div>
                <div field='uuid' width='50'	visible='false'></div>
            </div>
        </div>
    </div>
</div>
</div>
<script>

    mini.parse();
    var grid = mini.get("miniDataGrid");
    var searchArr=[];
    var obj={};
    var obj2={};
    obj.name = "wfStatus";
    obj.operator="=";
    obj.dataType="text";
    obj.value="2";

    searchArr.push(obj);
    grid.load({search:mini.encode(searchArr)});

    //清空查询条件
    function ClearForm(){
        mini.getbyName("materialCode").setValue("");
        mini.getbyName("materialName").setValue("");
    }
    /*
    描述：查询
    */
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
        grid.load({search:mini.encode(searchArr),categoryUuid:categoryUuid});
    }

    /*描述:分类管理*/
    function goManager(e){
        var strURL="",strTitle="",intWidth=580,intHeight=250;
        if(e=="Ok"){
            var oRow = grid.getSelecteds();
            if(oRow.length>=1) {
                window.CloseOwnerWindow(mini.encode(oRow));
            }else{
                mini.alert("请先选一行！");return;
            }

        }
        // var oWinDlg=mini.get('oWinDlg');
        // if(oWinDlg==null){
        //     oWinDlg=mini.open({
        //         id:"oWinDlg",
        //         showFooter:true,
        //         headerStyle:"font-weight:bold;letter-spacing:4px",
        //         footerStyle:"padding:5px;height:25px"
        //     });
        // }
        // var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
        // strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
        // oWinDlg.setUrl(strURL);
        // oWinDlg.setTitle(strTitle);
        // oWinDlg.setWidth(intWidth);
        // oWinDlg.setHeight(intHeight);
        // oWinDlg.setFooter(strButton);
        // oWinDlg.showAtPos("center","middle");
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
            gTree.reload();
        });
    }

</script>
</body>
</html>