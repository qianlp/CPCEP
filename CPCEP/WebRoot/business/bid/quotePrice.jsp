<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String invitationId = request.getParameter("invitationId");
	request.setAttribute("invitationId", invitationId);
	String type = request.getParameter("type");
	request.setAttribute("type", type);
	String minAmount = request.getParameter("minAmount");
	request.setAttribute("minAmount", minAmount);
%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<title></title>
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
<script type="text/javascript">
	var gForm = document.forms[0];
</script>
<div class="col-md-7" name="FormMode" id="FormMode"
	 style="width:100%;margin:auto;float:none;">
	<div class="widget-container fluid-height col-md-7-k"
		 style="height:auto;border-radius:4px;">
		<div class="mbox-body" style="height:100%;padding:10px;">
			<table id="tab" style="width:90%;margin-top:5px;margin-bottom:20px;">
				<tr>
					<td class="td_left" style="height:0px;"></td>
					<td class="td_right" style="height:0px;"></td>
					<td class="td_left" style="height:0px;"></td>
					<td class="td_right" style="height:0px;"></td>
				</tr>
				<tr>
					<td class="td_left">报价：</td>
					<td class="td_right" colspan="3">
						<input name="price"  required="true" value="" class="mini-textbox" style="width:100%;">
					</td>
				</tr>
				<input type="hidden" name="invitationId" value="${invitationId}" />
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
    function goSubmit() {
		var type = ${type};
		var msg;
		if(type == 1)
			msg = "请填写澄清报价金额!";
		else if(type == 2)
			msg = "请填写邀请报价金额!";
		var minAmount = ${minAmount};
		var price = $("input[name=price]").val();
		if (price.value == "") {
			alert(msg);
			return false;
		}
		if(minAmount > parseFloat(price)) {
		    alert("价格不能低于下限金额!");
		    return ;
        }

		if (confirm("您确信需要报价吗？")) {
			$.post("${path}/business/bid/quotePrice.action", {
				price : price,
				invitationId : "${invitationId}"
			}, function(result) {
                result = mini.decode(result);
				if(result.code != 200) {
					alert(result.message);
					return ;
				}
				alert("报价成功!");
				parent.goCloseDlg("oWinDlg");
			});
		}
    }
</script>
</body>
</html>