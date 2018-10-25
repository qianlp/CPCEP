<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String planId = request.getParameter("planId");
    request.setAttribute("planId", planId);
    String materialId = request.getParameter("materialId");
    request.setAttribute("materialId", materialId);

    String status = request.getParameter("status");
    request.setAttribute("status", status);
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>设备分项</title>
    <meta name="description" content="设备分项">
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
        html, body {
            width: 100%;
            height: 100%;
            border: 0;
            margin: 0;
            padding: 0;
            overflow: visible;
        }

        .edit_button, .update_button, .cancel_button {
            font-size: 11px;
            color: #1B3F91;
            font-family: Verdana;
            margin-right: 5px;
        }
    </style>
</head>
<body>
<!--功能按钮-->
<div class='mini-toolbar'
     style='padding:0;border-bottom-width:0;border:0px;'>
    <table id='funcBtn' style='width:100%;'>
        <tr>
            <td style="width:100%;">
                <a class="mini-button" iconCls="icon-add" onclick="saveRow()" plain="true">保存</a>
                <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">增加</a>
                <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
            </td>
        </tr>
    </table>
</div>
<!--撑满页面-->
<div class='mini-fit' style='width:100%;height:100%;'>
    <div id='miniDataGrid' class='mini-datagrid'
         style='width:100%;height:100%;' idField='id'
         sizeList='[15,30,50,100]' pageSize='15' showColumnsMenu='true'
         editNextOnEnterKey="true" editNextRowCell="true" allowCellSelect="true"
         multiSelect='true' allowCellEdit="true" contenteditable="true">
        <div property="columns">
            <div type='checkcolumn'></div>
            <div type='indexcolumn'>序号</div>
            <div field='id' visible='false' width='50' headerAlign='center' align='center'>id</div>
            <div field="name" width="120" headerAlign="center" align='center'>设备分项名称
                <input property="editor" required="true" class="mini-textbox" style="width:100%;"/>
            </div>
            <div field="norms" width="60" headerAlign="center" align='center'>规格型号
                <input property="editor" class="mini-textbox" style="width:100%; "/>
            </div>
            <div field='brand' headerAlign='center' width='80' align="center">品牌
                <input property="editor" class="mini-textbox" style="width:100%;"/>
            </div>
            <div field="count" trueValue="Y" falseValue="N" width="100" headerAlign="center" align="center">数量
                <input property="editor" class="mini-textbox" style="width:100%;"/>
            </div>
        </div>
    </div>
</div>
<script type='text/javascript'>
    mini.parse();
    var grid = mini.get('miniDataGrid');
    //这里可以重新指定自定义的数据装载路径
    grid.setUrl("${pageContext.request.contextPath}/business/procurement/getDeviceAttr.action?planId=${planId}&materialId=${materialId}");
    grid.load();


    var funcBtn = $("#funcBtn");
    console.log ($("#funcBtn"));
    var status ="${status}";
    if(status==undefined||status==""||status=="0"){
        $("#funcBtn").css('display','block');
    }else{
        $("#funcBtn").css('display','none');
    }


    function addRow() {
        if (grid.isEditing()) {
            mini.alert("请先处理修改的行", "消息提示");
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
        var rows = grid.getSelecteds();
        if(rows.length==0){
            mini.alert("请至少选择一行！");
            return ;
        }
        mini.confirm("确认要删除选定吗？", "操作提示", function (action) {
            if (action == "ok") {
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '数据删除中...'
                });
                var delUuids = $.map(grid.getSelecteds(), function (i) {
                    return i.id;
                }).join("^");
                grid.removeRows(rows);
                if (delUuids == "") {
                    mini.unmask(document.body);
                    return;
                }
                $.ajax({
                    url: "${pageContext.request.contextPath}/business/procurement/deleteDeviceAttr.action",
                    data: {
                        uuid: delUuids
                    },
                    type: "post",
                    success: function () {
                        mini.unmask(document.body);
                        mini.alert("操作成功!", "消息提示", function () {
                            // grid.reload();
                        });
                    },
                    error: function () {
                        mini.unmask(document.body);
                    }
                });
            }
        });
    }

    function saveRow() {
        var planId = "${planId}";
        var materialId = "${materialId}";
        var columns = grid.getData();
        console.log(columns);
        var json = JSON.stringify(columns);
        console.log(json);
        $.post("${pageContext.request.contextPath}/business/procurement/saveDeviceAttr.action", {
            planId: planId,
            materialId: materialId,
            deviceAttr: json
        }, function (result) {
            result = mini.decode(result);
            if (result.code != 200) {
                mini.alert(result.message);
                return;
            }
            mini.alert("保存成功");
            window.CloseOwnerWindow();
        });
    }

</script>
</body>
</html>
