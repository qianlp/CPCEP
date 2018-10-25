<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String planId = request.getParameter("planId");
    request.setAttribute("planId", planId);
	String materialId = request.getParameter("materialId");
	request.setAttribute("materialId", materialId);
	String userId= request.getParameter("userId");
    request.setAttribute("userId", userId);
    String type= request.getParameter("type");
    request.setAttribute("type", type);
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>设备分项</title>
    <meta name="description" content="设备分项">
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
				<a class="mini-button" iconCls="icon-add" onclick="saveRow()" plain="true">保存</a>
            </td>
        </tr>
    </table>
</div>
<!--撑满页面-->
<div class='mini-fit' style='width:100%;height:100%;'>
    <div id='miniDataGrid' class='mini-datagrid'
         style='width:100%;height:100%;' idField='uuid'
         showPager="false" showColumnsMenu='true'
         editNextOnEnterKey="true" editNextRowCell="true" allowCellSelect="true"
		 ondrawcell="onDrawDevicesCell"
         multiSelect='true'  allowCellEdit="true" contenteditable="true">
        <div property="columns">
            <div type='checkcolumn'></div>
            <div type='indexcolumn'>序号</div>
            <div field='uuid' visible='false' width='50'  headerAlign='center' align='center'>UUID</div>
            <div field="name" width="120" headerAlign="center" align='center'>设备分项名称
            </div>
            <div field="norms" width="60" headerAlign="center" align='center'>规格型号
            </div>
            <div field='brand' headerAlign='center' width='80' align="center">品牌
            </div>
            <div field="count"   width="100" headerAlign="center" align="center">数量
            </div>
			<div field="price" width="100" headerAlign="center" align="center">单价
                <c:if test='${empty type || !type.equals("2")}'>
				    <input property="editor" class="mini-textbox" style="width:100%;"/>
                </c:if>
			</div>
            <div field="totalPrice" width="100" headerAlign="center" align="center">总价
            </div>
            <c:if test='${!empty type && type.equals("2")}'>
                <div field="finalPrice" width="100" headerAlign="center" align="center">最终单价
                    <input property="editor" class="mini-textbox" style="width:100%;"/>
                </div>
                <div field="finalTotalPrice" width="100" headerAlign="center" align="center">最终总价
                </div>
            </c:if>
        </div>
    </div>
</div>
<script type='text/javascript'>
    mini.parse();
    var grid = mini.get('miniDataGrid');
    //这里可以重新指定自定义的数据装载路径
    grid.setUrl("${pageContext.request.contextPath}/business/procurement/getSupplierDeviceAttr.action?planId=${planId}&materialId=${materialId}&userId=${userId}");
    grid.load();


    function addRow() {
        if(grid.isEditing()){
            mini.alert("请先处理修改的行","消息提示");
            return;
    }
        var row = {};
        grid.addRow(row, 0);
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

    function saveRow() {
        var planId = "${planId}";
        var materialId = "${materialId}";
        var columns = grid.getData();
        console.log(columns);
        var json = JSON.stringify(columns);
        console.log(json);
		$.post("${pageContext.request.contextPath}/business/procurement/saveDeviceAttr.action", {
            planId : planId,
            materialId : materialId,
            deviceAttr : json,
            <c:if test='${!empty type && type.equals("2")}'>
            userId:'${userId}',
            </c:if>
            supplier : "supplier"
		}, function(result) {
		    result = mini.decode(result);
		    if(result.code != 200) {
		        mini.alert(result.message);
		        return ;
			}
            mini.alert("保存成功","提醒",function () {
                //计算总价
                var dates = grid.getData();
                var sum = 0;
                for(var i = 0,length = dates.length;i<length ;i++){
                    var price = dates[i].price;
                    <c:if test='${!empty type && type.equals("2")}'>
                    price = dates[i].finalPrice;
                    </c:if>
                    var t = price * dates[i].count;
                    if (isNaN(t)) continue;
                    sum += t;
                }
                window.CloseOwnerWindow(sum);
            });
		});
    }

    function onDrawDevicesCell(e) {
        var record = e.record;
        if (e.field == "totalPrice") {
            var price = record.price;
            var num = record.count;
            var t = price * num;
            if (!isNaN(t)) e.cellHtml = t;
        }
        if (e.field == "finalTotalPrice") {
            var price = record.finalPrice;
            var num = record.count;
            var t = price * num;
            if (!isNaN(t)) e.cellHtml = t;
        }
    }
</script>
</body>
</html>
