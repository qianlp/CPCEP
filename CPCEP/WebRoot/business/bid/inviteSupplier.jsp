<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String bidFileId = request.getParameter("uuid");
    request.setAttribute("bidFileId", bidFileId);
    String type = request.getParameter("type");
    request.setAttribute("type", type);
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>${type==1?"邀请澄清":"邀请竞价"}</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" type='text/css' href="../../css/form/loaders.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap-theme.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/form.css"/>
    <%@include file="../../resource.jsp" %>
</head>
<body text="#000000" bgcolor="#FFFFFF" style='padding:0px;width:100%;height:100%;background:#f3f3f3;'>
<div style="display:none">
    <input name="bidFileId" value="${bidFileId}"/>
    <input name="type" value="${type}"/>
</div>
<div class='mini-toolbar' style='border:0px;'>
  <c:if test="${type==1}">
     <c:if test="${!param.clarifyStruts}">
         <a class="mini-button" iconCls="" onclick="invite('${type}')">${type.equals("1")?"邀请澄清":"邀请竞价"}</a>
     </c:if>
      <c:if test="${param.clarifyStruts}">
         <a class="mini-button" iconCls="" onclick="openBidSuppiler();">查看</a>
     </c:if>
     </c:if>
     <c:if test="${type==2}">
       <c:if test="${!param.bidStruts}">
         <a class="mini-button" iconCls="" onclick="invite('${type}')">${type.equals("1")?"邀请澄清":"邀请竞价"}</a>
     </c:if>
      <c:if test="${param.bidStruts}">
         <a class="mini-button" iconCls="" onclick="openBidSuppiler();">查看</a>
     </c:if>
     </c:if>
</div>
<!--撑满页面-->
<div class='mini-fit' style='width:100%;height:100%;'>
    <div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;' idField='uuid' showPager="false"
         multiSelect='true'>
        <div property='columns'>
            <div type='checkcolumn'></div>
            <div field='supplierName' width='120' allowSort='false' headerAlign='center' align='center'>
                供应商名称
            </div>
            <div field='name' width='100' allowSort='false' headerAlign='center' align='center'>所供产品名称</div>
            <div field='contactAddress' width='120' allowSort='false' headerAlign='center' align='center'>通讯地址</div>
            <div field='corporations' width='100' allowSort='false' headerAlign='center' align='center'>法人代表</div>
            <div field='contact' width='100' allowSort='false' headerAlign='center' align='center'>联系人</div>
            <div field='mobile' width='100' allowSort='false' headerAlign='center' align='center'>联系方式</div>
            <div field='uuid' width='50' visible='false'></div>
            <div field='supplierUuid' width='50' visible='false'></div>
            <c:if test="${type == 1}">
                <div field='firstTotalPrice' width='100' allowSort='false' headerAlign='center' align='center'>投标报价金额
                </div>
                <div field='rank' width='100' allowSort='false' headerAlign='center' align='center'>投标报价排名</div>
            </c:if>
            <c:if test="${type == 2 }">
                <div field='thirdTotalPrice' width='100' allowSort='false' headerAlign='center' align='center'>报价金额
                </div>
                <div field='rank' width='100' allowSort='false' headerAlign='center' align='center'>报价排名</div>
            </c:if>
        </div>
    </div>
</div>
<script type='text/javascript'>
   console.log("${param.bidStruts}");
    mini.parse();
    var gDir = "${pageContext.request.contextPath}";
    var grid = mini.get('miniDataGrid');
    console.log("${param.bidStruts}");
    if("{param.isK}"=="2"){
    	$(".mini-toolbar").hide();
    }
    //这里可以重新指定自定义的数据装载路径
    grid.setUrl(gDir + '/business/bid/inviteSupplierList.action');
    var bidFileId = $("input[name=bidFileId]").val();
    var type = $("input[name=type]").val();
    grid.load({bidFileId: bidFileId, type: type});

    function invite(type) {
        var grid = mini.get('miniDataGrid');
        var cells = grid.getSelecteds();
        if (cells == null || cells.length == 0) {
            mini.alert("请选择要邀请的供应商!");
            return;
        }
        var list = new Array();
        for (var i = 0; i < cells.length; i++) {
            list.push(cells[i].uuid);
        }
        var url = "${pageContext.request.contextPath}/business/bid/supplierInviteForm.jsp?type=" + type + "&supplierBidUuids=" + list.join(",");
        openMiniWindow(url, type == "1" ? "邀请澄清" : "邀请竞价", "invitation");
    }

    //查看澄清或竟完价的供应商及时间
    function openBidSuppiler(){
    	var bidFileId=$("[name=bidFileId]").val();
    	var type=$("[name=type]").val();
    	var bidClarifyUrl=gDir+"/business/bid/bidClarifySupList.jsp?bidFileId="+bidFileId+"&type="+type;
    	mini.open({
    		 url:bidClarifyUrl,
    		 title: "供应商列表",      //标题
    		 width: 800,      //宽度
    		 height: 500,     //高度
    		 showCloseButton: true,   //显示关闭按钮
    		 showMaxButton: true,     //显示最大化按钮
    	});
    	
    }
    function openMiniWindow(url, title, id) {
        var intWidth = 600, intHeight = 350;
        var oWinDlg = mini.get(id);
        if (oWinDlg == null) {
            oWinDlg = mini.open({
                id: id,
                showFooter: false,
                headerStyle: "font-weight:bold;letter-spacing:4px"
            });
        }
        oWinDlg.setUrl(url);
        oWinDlg.setTitle(title);
        oWinDlg.setWidth(intWidth);
        oWinDlg.setHeight(intHeight);
        oWinDlg.showAtPos("center", "middle");
    }
</script>
</body>
</html>