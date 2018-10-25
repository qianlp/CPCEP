<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String categoryUuid=request.getParameter("categoryUuid");
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <title>新增材料</title>
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
<form method="post" action="${pageContext.request.contextPath}/business/material/materialOperation.action">
    <script type="text/javascript">
        var gForm = document.forms[0];
    </script>
    <div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;">
    <div class="widget-container fluid-height col-md-7-k"
         style="height:auto;border-radius:4px;">
        <div style="display:none">
            <c:choose>
                <c:when test="${empty material.categoryUuid}">
                    <input name="categoryUuid" value="<%=categoryUuid%>"/>
                </c:when>
                <c:otherwise>
                    <input name="categoryUuid" value="${material.categoryUuid}"/>
                </c:otherwise>
            </c:choose>
            <input name="uuid" value="${material.uuid}"/>
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
                    <td class="td_left">材料编码：</td>
                    <td class="td_right">
                        <input name="materialCode"  required="true" value="${material.materialCode}" class="mini-textbox"
                               style="width:100%;"></td>
                    <td class="td_left">材料名称：</td>
                    <td class="td_right">
                        <input name="materialName"  required="true" value="${material.materialName}" class="mini-textbox"
                               style="width:100%;"></td>
                </tr>
                <tr>
                    <td class="td_left">规格型号：</td>
                    <td class="td_right">
                        <input name="specModel" value="${material.specModel}" class="mini-textbox"
                               style="width:100%;"></td>
<!--                     <td class="td_left">品牌：</td> -->
<!--                     <td class="td_right"> -->
<!--                         <input name="brand" value="${material.brand}" class="mini-textbox" -->
<!--                                style="width:100%;"></td> -->
					<td class="td_left">单位：</td>
                    <td class="td_right">
                        <input name="unit" value="${material.unit}" class="mini-textbox"
                               style="width:100%;"></td>
                </tr>
<!--                 <tr> -->
<!--                     <td class="td_left">单位：</td> -->
<!--                     <td class="td_right"> -->
<!--                         <input name="unit" value="${material.unit}" class="mini-textbox" -->
<!--                                style="width:100%;"></td> -->
<!--                     <td class="td_left"></td> -->
<!--                     <td class="td_right"></td> -->
<!--                 </tr> -->
            </table>
        </div>
    </div>
</div>
</form>
<script type="text/javascript">
    function goSubmit() {
        with (gForm) {
            var strMaterialCode = mini.getbyName("materialCode").getValue();
            if (strMaterialCode == "") {
                alert("请填写材料编号！");
                return false;
            }
            var strMaterialName = mini.getbyName("materialName").getValue();
            if (strMaterialName == "") {
                alert("请填写材料名称！");
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