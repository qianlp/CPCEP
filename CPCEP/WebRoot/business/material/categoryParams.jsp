<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String categoryId=request.getParameter("categoryId");
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
    <table style='width:100%;'>
        <tr>
            <td style="width:100%;">
                <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">增加</a>
                <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
            </td>
        </tr>
    </table>
</div>
<!--撑满页面-->
<div class='mini-fit' style='width:100%;height:100%;'>
    <div id='miniDataGrid' class='mini-datagrid'
         style='width:100%;height:100%;' idField='uuid'
         sizeList='[15,30,50,100]' pageSize='15' showColumnsMenu='true'
         multiSelect='true'>
        <div property="columns">
            <div type='checkcolumn'></div>
            <div type='indexcolumn'>序号</div>
            <div field='uuid' visible='false' width='50'  headerAlign='center' align='center'>UUID</div>
            <div field="paramName" width="120" headerAlign="center" align='center'>名称
                <input property="editor" required="true" class="mini-textarea" style="width:100%;"/>
            </div>
            <div field="paramUnit" width="60" headerAlign="center" align='center'>单位
                <input property="editor" class="mini-textarea" style="width:100%;"/>
            </div>
            <div field='requiredValue' headerAlign='center' width='80' align="center" visible=false>要求值
                <input property="editor" class="mini-textarea" style="width:100%;"/>
            </div>
            <div field="isKeyword" type="checkboxcolumn"   trueValue="是"  falseValue="否"  width="100" headerAlign="center">是否关键参数</div>
            <div field='remark' headerAlign='left' width='120' align="left">备注
                <input property="editor" class="mini-textarea" style="width:100%;"/>
            </div>
            <div name="action" width="120" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
        </div>
    </div>
</div>
<script type='text/javascript'>
    mini.parse();
    var grid = mini.get('miniDataGrid');
    var categoryId = '<%=categoryId%>';
    //这里可以重新指定自定义的数据装载路径
    grid.setUrl("${pageContext.request.contextPath}/business/material/loadCategoryParamForPage.action");
    grid.load({categoryId:categoryId});

    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;
        var s = ' <a class="edit_button" href="javascript:editRow(\'' + uid + '\')" >编辑</a>';
        if (grid.isEditingRow(record)) {
            s = '<a class="update_button" href="javascript:updateRow(\'' + uid + '\')">更新</a>'
                + '<a class="cancel_button" href="javascript:cancelRow(\'' + uid + '\')">取消</a>'
        }
        return s;
    }

    function addRow() {
        if(grid.isEditing()){
            mini.alert("请先处理修改的行","消息提示");
            return;
        }
        var row = {};
        grid.addRow(row);
        grid.cancelEdit();
        grid.beginEditRow(row);
    }

    function editRow(row_uid) {
        var row = grid.getRowByUID(row_uid);
        if (row) {
            grid.cancelEdit();
            grid.beginEditRow(row);
        }
    }

    function cancelRow(row_uid) {
        grid.reload();
    }
    function removeRow() {
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
                    url: "${pageContext.request.contextPath}/business/material/deleteCategoryParamByIds.action",
                    data:{
                        uuid:delUuids
                    },
                    type:"post",
                    success:function(){
                        mini.unmask(document.body);
                        mini.alert("操作成功!","消息提示",function(){
                            grid.reload();
                        });
                    },
                    error:function () {
                        mini.unmask(document.body);
                    }
                });
            }
        });
    }
    function updateRow(row_uid) {
        var row = grid.getRowByUID(row_uid);
        var rowEditData = grid.getEditRowData(row);
        if(rowEditData.paramName.trim().length==0){
            mini.alert("请输入名称","消息提示");
            return;
        }

        grid.commitEdit();
        var rowData = grid.getChanges();

        grid.loading("保存中，请稍后......");
        var json = mini.encode(rowData);

        $.ajax({
            url: "${pageContext.request.contextPath}/business/material/categoryParamOperation.action",
            data: { data: json ,categoryId:categoryId},
            success: function (text) {
                grid.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
            }
        });

    }
</script>
</body>
</html>
