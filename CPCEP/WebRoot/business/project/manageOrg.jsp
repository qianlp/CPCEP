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
    <title>设备材料分类信息维护</title>
    <meta name="description" content="设备材料分类信息维护">
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
<!--功能按钮-->

<div class='mini-toolbar' style='padding:0;border-bottom-width:0'>
    <table style='width:100%;'>
        <tr>
            <td style='width:100%;white-space:nowrap;' id='leftGridToolBar'>
                <a class="mini-button" iconCls="icon-new" tooltip="功能按钮" plain="true" onclick="btnEvent({
action:'new',
open:'Open',
title:menuName,
fm:gDir+'/admin/rtuMenuById.action'
})" >新建</a>

                <a class="mini-button" iconCls="icon-edit" tooltip="功能按钮" plain="true" onclick="btnEvent({
action:'edit',
open:'Open',
title:menuName,
fm:gDir+'/admin/findDocById.action'
})" cs>编辑</a>

                <a class="mini-button" iconCls="icon-remove" tooltip="功能按钮" plain="true" onclick="delDocument()" >删除</a>
            </td>
            <td style='white-space:nowrap;' id='rightGridToolBar'><a class='mini-button' iconCls='icon-reload' plain='true' onclick='refreshGridData()'>刷新</a></td>
        </tr>
    </table>
</div>

<div class="mini-layout" style="width:100%;height:100%;" id="layout">
    <div class='mini-fit' style='width:100%;height:100%;'>
        <div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;' idField='unid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true' fitColumns='true'>
            <div property='columns'>
                <div type='checkcolumn'></div>
                <div type='indexcolumn' align ='center'>序号</div>
                <div field='body' 	width='100' allowSort='true' headerAlign='left'  align='left'	renderer='CommonRowLink'>所属公司</div>
                <div field='updateBy' 	width='80' allowSort='true' headerAlign='center'  align='center'	>维护人</div>
                <div field='createDate' 	width='120' allowSort='true' headerAlign='center'  align='center'	>维护时间</div>
                <div field='uuid' width='50'	visible='false'></div>
            </div>
        </div>
    </div>
</div>
<script>


    mini.parse();
    var grid = mini.get('miniDataGrid');
    //这里可以重新指定自定义的数据装载路径
    grid.setUrl(gDir+'/admin/loadMapListForPageHeadOrList.action');
    grid.load({menuId:menuId,sortField:"createDate",sortOrder:"desc"});


    //自定义参数项列
    grid.on("drawcell",function (e) {
        var record = e.record,
            column = e.column;
        //action列，超连接操作按钮
        /*if (column.name == "action") {
            e.cellStyle = "text-align:center";
            e.cellHtml = '<a href="javascript:edit(\'' + record.uuid + '\')">点击查看</a>';
        }*/
        if (column.name == "history") {
            e.cellStyle = "text-align:center";
            e.cellHtml = '<a href="javascript:edit(\'' + record.uuid + '\')">点击查看</a>';
        }
    });
    //树节点点击事件
    function nodeclick(e){
        categoryUuid = e.node.id
        grid.load({categoryUuid:categoryUuid});
    }
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
    function goMaterial(e){
        var strURL="",strTitle="",intWidth=580,intHeight=250;
        if(e=='New'){
            var myNode = gTree.getSelectedNode();
            if (typeof(myNode)=="undefined") {
                mini.alert("请先选择左侧分类!","消息提示");
                return ;
            }else{
                categoryUuid=myNode.id;
            }
            strTitle="新增";
            strURL="${pageContext.request.contextPath}/business/material/materialOperation.jsp?categoryUuid="+categoryUuid;
        }else{
            var oRow = grid.getSelecteds();
            if(oRow.length>=1){
                if(oRow.length == 1){
                    strURL="${pageContext.request.contextPath}/business/material/findMaterialById.action?uuid="+oRow[0].uuid;
                }else{
                    mini.alert("请只选中一行！");return;
                }
            }else{
                mini.alert("请先选一行！");return;
            }
        }
        var oWinDlg=mini.get('oWinDlg');
        if(oWinDlg==null){
            oWinDlg=mini.open({
                id:"oWinDlg",
                showFooter:true,
                headerStyle:"font-weight:bold;letter-spacing:4px",
                footerStyle:"padding:5px;height:25px"
            });
        }
        var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
        strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
        oWinDlg.setUrl(strURL);
        oWinDlg.setTitle(strTitle);
        oWinDlg.setWidth(intWidth);
        oWinDlg.setHeight(intHeight);
        oWinDlg.setFooter(strButton);
        oWinDlg.showAtPos("center","middle");
    }
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
            gTree.reload();
        });
    }
    function delMaterials(){
        var delUuids =  $.map(grid.getSelecteds(),function(i){
            return i.uuid;
        }).join("^");
        if(delUuids==""){mini.alert("请至少选择一行！");return;}
        mini.confirm("确认要删除选定吗？", "操作提示", function(action){
            if(action=="ok"){
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '数据删除中...'
                });
                $.ajax({
                    url: gDir+"/business/material/deleteMaterialByIds.action",
                    data:{
                        uuid:delUuids
                    },
                    type:"post",
                    success:function(){
                        mini.unmask(document.body);
                        mini.alert("操作成功!","消息提示",function(){
                            grid.reload();
                            gTree.reload();
                        });
                    },
                    error:function () {
                        mini.unmask(document.body);
                    }
                });
            }
        });

    }
</script>
</body>
</html>