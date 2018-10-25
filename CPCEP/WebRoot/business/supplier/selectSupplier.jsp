<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String menuId=request.getParameter("menuId");
    request.setAttribute("menuId", menuId);
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
    <%-- 左侧分类树 --%>
    <div region="west" style="overflow:hidden;border-bottom:0"
         showHeader="false" width="200px" minWidth="200" maxWidth="350"
         showSplitIcon="true">
        <div class="mini-fit" style="padding:2px;border-top:0;border-left:0;border-right:0;">
            <div id='categoryTree' class='mini-tree' style="height:100%;" showTreeIcon='true' showCheckBox='false' checkRecursive='true' expandOnLoad='0' expandOnDblClick='false'
                 url="${pageContext.request.contextPath}/business/findCategoryTreeOpen.action" resultAsTree="false"
                 onnodeclick="nodeclick">
            </div>
        </div>
    </div>
    <%--中心表格--%>
    <div region="center"
         style="overflow:hidden;border-bottom:0;border-right:1">
        <div class="mini-toolbar">
            <a class="mini-button" iconCls="icon-add" onclick="detail()">查看</a>
            <a class="mini-button" iconCls="icon-ok" onclick="toSave('Ok')">确定</a>


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
            <div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;' idField='unid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true' fitColumns='true'>
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
                    <div field='enableFlag' width='100' allowSort='true' headerAlign='center' align='center' renderer='enableStatus'>启用</div>
                    <div field='updateDate' width='100' allowSort='true' headerAlign='center' align='center'>更新时间</div>
                    <div field='uuid' width='50'	visible='false'></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>


    //这里可以重新指定自定义的数据装载路径
    function enableStatus(cell) {
        var enableFlag = cell.value;
        if(enableFlag)
            return "<font color='blue'>启用</font>";
        return "<font color='red'>禁用</font>";
    }

    mini.parse();
    var gDir="${pageContext.request.contextPath}";
    var grid = mini.get("miniDataGrid");
    grid.detailUrl = '/business/detail.action';
    <%--grid.setUrl(gDir+'/admin/loadMapListForPageHeadOrList.action?menuId=${menuId}');--%>
    grid.setUrl(gDir+'/business/findPage.action?menuId=${menuId}');
    grid.load({sortField:"registerTime",sortOrder:"desc",pass:"pass"});

    var gTree = mini.get("categoryTree");
    var categoryUuid = "-1";//默认pid为-1

    //树节点点击事件
    var scopeId;
    function nodeclick(e){
        categoryUuid = e.node.id;
        scopeId = categoryUuid;
        grid.load({scopeId:categoryUuid, name : $("input[name=name]").val(),pass:"pass"});
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
        grid.load({scopeId:scopeId, name : $("input[name=name]").val()});
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

    function enable() {
        var strDocID = GetDocID();
        if(strDocID==""){mini.alert("您没有选择要设置的供应商！");return;}
        mini.confirm("确认要设置选定供应商吗？", "操作提示", function(action){
            if(action=="ok"){
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '设置中...'
                });
                $.ajax({
                    url: gDir+"/business/enable.action?id="+strDocID,
                    success:function(){
                        mini.unmask(document.body);
                        mini.alert("操作成功！");
                        grid.reload();
                    }
                });
            }
        });
    }

    //根据id参数删除文档
    function checkSupplier(checkStatus){
        var strDocID = GetDocID();
        if(strDocID==""){mini.alert("您没有选择要审核的供应商！");return;}
        mini.confirm("确认要审核选定供应商吗？", "操作提示", function(action){
            if(action=="ok"){
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '审核中...'
                });
                $.ajax({
                    url: gDir+"/business/check.action?id="+strDocID + "&checkStatus=" + checkStatus,
                    success:function(){
                        mini.unmask(document.body);
                        mini.alert("操作成功！");
                        grid.reload();
                    }
                });
            }
        });

    }

    function detail(){//按钮操作方法
        var obj = {
            action:'edit',
            open:'Open',
            title:'供应商详情',
            fm:gDir+'/business/detail.action'
        };
        var urlPath=obj.fm;	//访问的表单
        if(obj.action=="del"){
            delDocument();
            return;
        }
        if(urlPath){
            if(urlPath.indexOf("?")==-1){
                urlPath += "?menuId=${menuId}";
            }else{
                urlPath += "&menuId=${menuId}";
            }
            urlPath=encodeURI(urlPath);
            if(obj.action=="edit"){//修改
                this.EditDocument(urlPath);
            }else{//新增调用
                this.CommonOpenDoc(urlPath);
            }
        }else{
            alert("请管理员在新建按钮函数中添加表单名称！");return;
        }
    }

    function EditDocument(urlPath){
        var strDocID = GetDocID();
        if(strDocID==""){mini.alert("您没有选择文档！");return;}
        if(strDocID.indexOf("^")>0){mini.alert("请您选择一个文档！");return;}
        var docs=grid.getSelecteds();
        CommonOpenDoc(urlPath+"&uuid="+strDocID+"&modoName=SupplierExt");
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

    function GetDocID(){
        var arrID=$.map(grid.getSelecteds(),function(i){
            if(i.uuid == null || i.uuid == undefined)
                return i.id;
            return i.uuid;
        });
        return arrID.join("^");
    }

    function CommonFlowStatus(cell){
        var strColor='0f0',strStatus=cell.value;
        switch(parseInt(cell.value,10)){
            case 9 :
                strStatus='待审核';
                strColor='00B83F';
                break;
            case 8 :
                strStatus='审核失败';
                strColor='f00';
                break;
            case 0:
                strStatus='草稿';
                strColor='000';
                break;
            case 1:
                var arrData=grid.getRow(cell.rowIndex)['curUser'].split(',');
                if($.inArray(gCurUser,arrData)>-1){
                    strColor='f00';
                    strStatus='待审批';
                }else{
                    strColor='00f';
                    strStatus='进行中';
                }
                break;
            case 2:
                strColor='000';
                strStatus='已审批';
                break;
            case 3:
                strColor='22DD48';
                strStatus='已更新';
                break;
            case 4:
                strColor='2BD5D5';
                strStatus='已发送';
                break;
            case 5:
                strColor='f00';
                strStatus='已作废';
                break;
            default:
        }
        return "<span style='color:#"+strColor+"'>" + strStatus + "</span>";
    }


    /*描述:保存*/
    function toSave(e){
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