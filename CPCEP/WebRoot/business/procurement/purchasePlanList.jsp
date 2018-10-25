<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>采购计划选择</title>
    <meta name="description" content="采购计划选择">
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
       <%-- <div class="search_body" id="search_body">
            <div class="search_content">
                <div class="search_title">采购计划编号：</div>
                <div class="search_field" Operator="@" DataType="text" Item="">
                    <input name="purchasePlanCode" id="purchasePlanCode" class="mini-textbox">
                </div>
            </div>
            <div class="search_content">
                <div class="search_title">采购计划名称：</div>
                <div class="search_field" Operator="@" DataType="text" Item="">
                    <input name="purchasePlanName" id="purchasePlanName" class="mini-textbox">
                </div>
            </div>
            <div class="search_button">
                <a class="mini-button" tooltip="清空查询条件" plain="true" iconCls="icon-remove" onclick="ClearForm"></a>
                &nbsp;<a class="mini-button" iconCls="icon-search" onclick="CommonSearch">搜索</a>
            </div>
        </div>--%>
        <div class='mini-fit' style='width:100%;height:100%;'>
            <div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;'  url="${pageContext.request.contextPath}/business/procurement/queryPurchasePlanData.action"
                 idField='unid' sizeList='[5,10,15,30]' pageSize='15' fitColumns='true'>
                <div property='columns'>
                    <div type="checkcolumn"></div>
                    <div type="indexcolumn">序号</div>
                    <div field="purchasePlanCode" width="120" allowSort="true" align='center'>采购计划编号</div>
                    <div field="purchasePlanName" width="120" allowSort="true" align='center'>采购计划名称</div>
                    <div field="projectCode" width="120" allowSort="true" align='center' >项目编号</div>
                    <div field="projectName" width="120" allowSort="true" align='center'>项目名称</div>
                    <div field="purchaseMethod" width="120" allowSort="true" align="center">采购方式</div>
                    <div field="createDate" width="120" allowSort="true" align='center'>制单时间</div>
                    <div field='uuid' width='50'  visible='false'></div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script>
    mini.parse();
    var grid = mini.get("miniDataGrid");
    grid.load({wfStatus:"2",purchaseMethod:["公开招标","邀请招标"]});

    /*//清空查询条件
    function ClearForm(){
        mini.getbyName("purchasePlanCode").setValue("");
        mini.getbyName("purchasePlanName").setValue("");
    }
    /!*
    描述：查询
    *!/
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
        grid.load({search:mini.encode(searchArr),wfStatus:"2"});
    }*/

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
    }

</script>
</body>
</html>