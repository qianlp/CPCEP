<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>参数维护</title>
    <meta name="description" content="参数维护">
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
        html,body {
            width: 100%;
            height: 100%;
            border: 0;
            margin: 0;
            padding: 0;
            overflow: visible;
        }
        .edit_button, .update_button, .cancel_button
        {
            font-size:11px;color:#1B3F91;font-family:Verdana;
            margin-right:5px;
        }
    </style>
</head>
<body>
<!--功能按钮-->
<div class='mini-toolbar'
     style='padding:0;border-bottom-width:0;border:0px;'>
    <table style='width:100%;' >
        <tr>
            <td style="width:100%;" id="tools">
                <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">增加</a>
                <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
                <a class="mini-button" iconCls="icon-save" onclick="saveRows()" plain="true">保存</a>
            </td>
        </tr>
    </table>
</div>
<!--撑满页面-->
<div class='mini-fit' style='width:100%;height:100%;'>
    <div id='miniDataGrid' class='mini-datagrid'
         style='width:100%;height:100%;' idField='uuid'
         showPager="false"
         editNextOnEnterKey="true" editNextRowCell="true" allowCellSelect="true"
         multiSelect='true'  allowCellEdit="true" contenteditable="true">
        <div property="columns">
            <div type='checkcolumn'></div>
            <div type='indexcolumn'>序号</div>
            <div field='uuid' visible='false' width='50'  headerAlign='center' align='center'>UUID</div>
            <div field='pmpUuid' visible='false'>pmpUuid</div>
            <div field="paramName" width="120" headerAlign="center" align='center'>名称
                <input property="editor" required="true" class="mini-textbox" style="width:100%;"/>
            </div>
            <div field="paramUnit" width="60" headerAlign="center" align='center'>单位
                <input property="editor" class="mini-textbox" style="width:100%; "/>
            </div>
            <div field='requiredValue' headerAlign='center' width='80' align="center">要求值
                <input property="editor" class="mini-textbox" style="width:100%;"/>
            </div>
            <div field="isKeyword"  width="100" headerAlign="center" align="center">是否关键参数
                <input property="editor" class="mini-comboBox" textField="text" valueField="id" data="[{'id':'是' ,'text':'是'},{'id':'否' , 'text':'否'}]" />
            </div>

        </div>
    </div>
</div>
<script type='text/javascript'>
    mini.parse();
    var grid = mini.get('miniDataGrid');

    function addRow() {
        if(grid.isEditing()){
            mini.alert("请先处理修改的行","消息提示");
            return;
        }
        if(checkNullRow()){
            mini.alert("请先完善其他行数据","消息提示");
            return;
        }
        var row = {};
        grid.addRow(row);
    }

    function checkNullRow(){
        var rows = grid.findRows(function(row){
            if(isNull(row.paramName)) return true;
            if(isNull(row.paramUnit)) return true;
            if(isNull(row.requiredValue)) return true;
            if(isNull(row.isKeyword)) return true;
        });
        return rows.length!=0;
    }

    function removeRow() {
        var rows = grid.getSelecteds();
        if(rows.length==0){mini.alert("请至少选择一行！");return;}
        grid.removeRows(rows);
    }
    function saveRows(){
        if(checkNullRow()){
            mini.alert("请先补充或删除空数据","消息提示");
            return;
        }
        window.CloseOwnerWindow(grid.getData());
    }
    function setData(data){
        data = mini.clone(data);
        console.log(data);
        if(data.gIsRead){
            $("#tools").hide();
            grid.setAllowCellEdit(false);
        }
        if(isNull(data.pmUuid)&&isNull(data.paramsJson)){
            loadGridByCate(data.categoryUuid);
        }else if(!isNull(data.paramsJson)){
            loadGridByParamJson(data.paramsJson);
        }else {
            loadGridWithUuid(data.pmUuid);
        }
    }

    function loadGridByCate(categoryUuid){
        //这里可以重新指定自定义的数据装载路径
        $.ajax({
            url:"${pageContext.request.contextPath}/business/material/loadCategoryParam.action",
            type:"get",
            data:{
                categoryId:categoryUuid
            },
            success:function(res) {
              var data = JSON.parse(res);
                console.log(data);
                grid.addRows(data);
            }
        });
    }

    function loadGridByParamJson(paramsJson){
        grid.addRows(mini.decode(paramsJson));
    }

    function loadGridWithUuid(pmUuid) {
        $.ajax({
            url:"${pageContext.request.contextPath}/business/procurement/loadParam.action",
            type:"get",
            data:{
                pmUuid:pmUuid
            },
            success:function(res) {
                var data = JSON.parse(res);
                console.log(data);
                grid.addRows(data);
            }
        });
    }

    function isNull(data){
        return data==null||data==undefined||data=="";
    }
</script>
</body>
</html>
