<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String pid=request.getParameter("pid");
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <title>新增类型</title>
    <script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
        html,body,form {
            margin: 0;
            padding: 0;
            border: 0;
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body text="#000000" bgcolor="#FFFFFF">
<form method="post" action="${pageContext.request.contextPath}/business/material/categoryOperate.action" name="addNewDept">
    <script type="text/javascript">
        var gForm = document.forms[0];
    </script>
    <div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;">
    <div class="widget-container fluid-height col-md-7-k"
         style="height:auto;border-radius:4px;">
        <div style="display:none">
            <c:choose>
                <c:when test="${empty materialCategory.pid}">
                    <input name="pid" value="<%=pid%>"/>
                </c:when>
                <c:otherwise>
                    <input name="pid" value="${materialCategory.pid}"/>
                </c:otherwise>
            </c:choose>
            <input name="uuid" value="${materialCategory.uuid}"/>
        </div>
        <div class="mbox-body" style="height:100%;padding:10px;">
            <table id="tab" style="width:90%;margin-top:5px;margin-bottom:20px;">
                <tr>
                    <td class="td_left" style="height:0px;"></td>
                    <td class="td_right" style="height:0px;"></td>
                    <td class="td_left" style="height:0px;"></td>
                    <td class="td_right" style="height:0px;"></td>
                </tr>
                <tr>
                    <td class="td_left">材料分类编码：</td>
                    <td class="td_right" colspan="3">
                        <input name="categoryCode"  required="true" value="${materialCategory.categoryCode}" class="mini-textbox"
                               style="width:100%;"></td>
                </tr>
                <tr>
                    <td class="td_left">材料分类名称：</td>
                    <td class="td_right" colspan="3">
                        <input name="categoryName"  required="true"  value="${materialCategory.categoryName}" class="mini-textbox"
                               style="width:100%;"></td>
                </tr>
            </table>
        </div>
    </div>
</div>
</form>
<script type="text/javascript">
    function goSubmit() {
        with (gForm) {
            var strCategoryCode = mini.getbyName("categoryCode").getValue();
            if (strCategoryCode == "") {
                alert("请填写分类编号！");
                return false;
            }
            var strCategoryName = mini.getbyName("categoryName").getValue();
            if (strCategoryName == "") {
                alert("请填写分类名称！");
                return false;
            }
            parent.goToolsBtn(false);
            if (confirm("您确信需要保存吗？")) {
                submit();
            } else {
                parent.goToolsBtn(true);
            }
        }
    }
</script>
</body>
</html>