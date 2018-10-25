<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>邀请竞价</title>
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript"
            src="/CPCEP/js/miniui/scripts/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="/CPCEP/js/miniui/scripts/miniui/miniui.js"></script>
    <script src="/CPCEP/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <link rel="stylesheet" type='text/css' href="../../css/form/form.css"/>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>
<body>
<div class="mini-layout" style="width:100%;height:100%;" id="layout">
    <div region="center" style="overflow:hidden;border-bottom:0;border-right:1">
        <div class='mini-fit' style='width:100%;height:100%;'>
            <form method="post" name="form1" id="form1">
               <!--  <div style="display: none;">
                    <input name="supplierBidUuids" value="2c90a5e064599a3a01645de5266900ea">
                    <input name="type" value="2">
                </div> -->
                    <table style="width:95%;margin:5px;" id="tab">
                        <tr>
                            <td class="td_left">竞价上限金额：</td>
                            <td class="td_right">
                                <input name="minAmount" class="mini-textbox" style="width: 99%;" value="${invitation.minAmount}" />
                            </td>
								<td class="td_left">竞价截止时间：</td>
								<td class="td_right">
									<input name="endDate" class="mini-datepicker" style="width: 99%;" value="${invitation.endDate}"
									format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"
									/>
								</td>
                        </tr>
                        <tr>
							<td class="td_left">下调幅度：</td>
							<td class="td_right">
								<input name="downExtent" class="mini-textbox" style="width: 99%;"value="${invitation.downExtent}"/>
							</td>
                            <td class="td_left">其他说明：</td>
                            <td class="td_right">
                                <input name="description" class="mini-textbox" style="width:99%;" value="${invitation.description}"/>
                            </td>
                        </tr>
						<!-- <tr>
							<td colspan="4" style="text-align: center;">
								<input type="button" class="btn btn-md btn-default" value="提交" onclick="submitInvite();"/>
							</td>
						</tr> -->
                    </table>
                
                
            </form>
        </div>
    </div>
</div>
<!-- <script>
    function submitInvite() {
        $.post("/CPCEP/business/bid/invite.action", $("#form1").serialize(), function (result) {
            result = mini.decode(result);
            if (result.code != 200) {
                alert(result.message);
                return;
            }
            mini.confirm("操作成功!", "确认框", function (r) {
                window.CloseOwnerWindow();
            });
        });
    }
</script> -->
</body>
</html>