<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String purchaseMaterialId=request.getParameter("purchaseMaterialId");
    String supplierBidUuid = request.getParameter("supplierBidUuid");
    String times = request.getParameter("times");
    request.setAttribute("times",times);
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
<div class='mini-toolbar'
     style='padding:0;border-bottom-width:0;border:0px;'>
    <table style='width:100%;'>
        <tr>
            <td style="width:100%;" id="tools">
                <a class="mini-button" iconCls="icon-ok" onclick="goManager('Ok')">保存</a>
            </td>
        </tr>
    </table>
</div>
<!--撑满页面-->
<div class='mini-fit' style='width:100%;height:100%;'>
    <div id='miniDataGrid' class='mini-datagrid'
         style='width:100%;height:100%;' idField='uuid' allowCellEdit="true" allowCellSelect="true"
         showPager="false">
        <div property="columns">
            <div type='indexcolumn'>序号</div>
            <div field='uuid' visible='false' width='50'  headerAlign='center' align='center'>UUID</div>
            <div field="paramName" width="120" headerAlign="center" align='center'>参数
            </div>
            <div field="paramUnit" width="60" headerAlign="center" align='center'>单位
            </div>
            <div field="isKeyword"  width="100" headerAlign="center" align='center'>是否关键参数</div>
            <div field='requiredValue' headerAlign='center'    width='80' align="center">要求值
            </div>
            <div field='responseValue' headerAlign='center' width='80' align="center">响应值
                <c:if test="${times!=null&&times==1}"> <input property="editor" class="mini-textarea" style="width:100%;"/></c:if>
            </div>
            <c:if test="${times!=null&&times==2}">
                <div field='clarifyValue' headerAlign='center' width='80' align="center">澄清值
                    <input property="editor" class="mini-textarea" style="width:100%;"/>
                </div>
            </c:if>
            <div field='remark' headerAlign='left' width='120' align="left">备注
            </div>
        </div>
    </div>
</div>
<script type='text/javascript'>
    mini.parse();
    var grid = mini.get('miniDataGrid');
    var purchaseMaterialId = '<%=purchaseMaterialId%>';
    var supplierBidUuid = '<%=supplierBidUuid%>';

    function goManager(e){
        if(e=="Ok"){
       var rowRes = grid.findRows(function(row){
        		if(isNull(row.responseValue)){
        			return true;
        		}
        	});
         if(rowRes.length!=0){
        	mini.alert("响应值不能为空");
        	 return;
         }
            var data= grid.getChanges();
            window.CloseOwnerWindow(data);
        }
    }
    function setData(data){
        data = mini.clone(data);
        console.log(data);
        if(data.gIsRead){
            $("#tools").hide();
            grid.setAllowCellEdit(false);
        }
        if(!isNull(data.paramsJson)){
            loadGridByParamJson(data.paramsJson);
        }else {
            loadGridWithUuid();
        }
    }

    function loadGridByParamJson(paramsJson){
        grid.addRows(mini.decode(paramsJson));
    }

    function loadGridWithUuid() {
        //这里可以重新指定自定义的数据装载路径
        $.ajax({
            url:"${pageContext.request.contextPath}/business/bid/loadSupplierMaterialParam.action",
            type:"get",
            data:{
                purchaseMaterialId:purchaseMaterialId,
                supplierBidUuid:supplierBidUuid
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
