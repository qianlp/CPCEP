<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>综合评审</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" type='text/css' href="../../css/form/loaders.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap-theme.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/form.css"/>
    <%@include file="../../resource.jsp" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>

    <script>
        var gDir = "${pageContext.request.contextPath}";
        var gLoginUser = "${sessionScope.user.userName}";
    </script>
</head>
<body text="#000000" bgcolor="#FFFFFF" style='padding:0px;width:100%;height:100%;background:#f3f3f3;'>
<div id="bg"></div>
<div class="loader" id="bg-loader">
    <div class="loader-inner ball-spin-fade-loader">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
    </div>
</div>
<form method="post" action="${pageContext.request.contextPath}/business/bid/saveOrUpdateCompBidEval.action" name="form1" id="form1">
    <div style="display:none">
        <input name="biddingFileUuid" class="mini-textbox" value="${bidFile.uuid}"/>
        <input name="suggestSupplierBid" class="mini-textbox" value="${compBidEval.suggestSupplierBid}"/>
        <input name="uuid" class="mini-textbox" value="${compBidEval.uuid}"/>
        <input name="wfStatus" class="mini-textbox" value="${compBidEval.wfStatus}">
    </div>
    <div id="PageBody">
        <div class="col-md-7" name="FormMode" id="FormMode" style="width:100%;margin:auto;float:none;">
            <div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
                <div class="mbox-header">
                    <span class="form-title" style="height:100%;line-height:45px;">
                        综合评审
                    </span>
                </div>
                <div class="mbox-body" style="height:100%;padding:10px;">
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>基本信息</b></legend>
                        <table style="width:95%;margin:5px;" id="tab">
                            <tr>
                                <td class="td_left">评标人：</td>
                                <td class="td_right">
                                    <s:if test='comBidEval==null'>
                                        <input name="createBy" value="${session.user.userName}" class="mini-textbox"
                                               allowInput="false" readonly="true" style="width:99%;" title="编制人">
                                        <input name="createUuid" value="${session.user.uuid}"
                                               class="mini-textbox hidden" allowInput="false" readonly="true"
                                               style="width:99%;" title="编制人ID">
                                    </s:if>
                                    <s:else>
                                        <input name="createBy" value="${comBidEval.createBy}" allowInput="false"
                                               readonly="true" class="mini-textbox" style="width:99%;" title="编制人">
                                        <input name="createUUid" value="${comBidEval.createUuid}" allowInput="false"
                                               readonly="true" class="mini-textbox hidden" style="width:99%;"
                                               title="编制人ID">
                                    </s:else>
                                </td>
                                <td class="td_left">评标时间：</td>
                                <td class="td_right">
                                    <s:if test="comBidEval==null||comBidEval.createDate==null">
                                        <input name="createDate" allowInput="false" readonly="true"
                                               value="<%=(new java.util.Date()).toLocaleString()%>"
                                               class="mini-datepicker" style="width:99%;" title="编制时间">
                                    </s:if>
                                    <s:else>
                                        <input name="createDate" allowInput="false" readonly="true"
                                               value="${comBidEval.createDate}" class="mini-datepicker"
                                               style="width:99%;" title="编制时间">
                                    </s:else>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">项目编号：</td>
                                <td class="td_right">
                                    <input name="projectCode" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${bidFileWithProject.projectCode}"/>
                                </td>
                                <td class="td_left">项目名称：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinName" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${bidFileWithProject.projectName}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">采购名称：</td>
                                <td class="td_right">
                                    <input name="projectCode" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${bidFileWithProject.purchaseName}"/>
                                </td>
                                <td class="td_left">招标方式：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinName" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${bidFileWithProject.purchaseMethod}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">设备参数：</td>
                                <td class="td_right" colspan="3">
                                    <input name="projectCode" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${deviceStr}"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>投标报价(单位:人民币万元)</b></legend>
                        <div id="bidGrid" class="mini-datagrid" style="width:100%;height:auto;" showPager="false">
                            <div property="columns">
                                <div field="index" type="indexcolumn">序号</div>
                                <div field="supplierName" width="80" headerAlign="center">投标商名称</div>
                                <div field="firstTotalPrice" width="80" headerAlign="center">投标价格</div>
                                <div field="thirdTotalPrice" width="80" headerAlign="center">最终报价</div>
                                <div field="paymentMethod" width="80" headerAlign="center">付款方式</div>
                                <div field="deliveryTime" width="80" headerAlign="center">交货期</div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>设备明细</b></legend>
                        <div id="deviceGrid" class="mini-datagrid" multiSelect="true"
                             style="width:100%;height:auto;" showPager="false">
                            <div property="columns">
                                <div field="rowId" name="rowId"  headerAlign="center" align="center">序号</div>
                                <div field="materialName" name="materialName" width="80" headerAlign="center" align="center">设备名称</div>
                                <div header="参数项" headerAlign="center">
                                    <div property="columns">
                                        <div field="paramIndex" width="80" headerAlign="center" align="center">序号</div>
                                        <div field="paramName" width="80" headerAlign="center" align="center">参数名称</div>
                                        <div field="requiredValue" width="80" headerAlign="center" align="center">要求值</div>
                                    </div>
                                </div>
                                <c:forEach items="${suppliers}" var="supplier">
                                    <div header="供应商名称" headerAlign="center">
                                        <div property="columns">
                                            <div width="170" headerAlign="center">${supplier.name}
                                                <div property="columns">
                                                    <div field="responseValue_${supplier.account}" width="80" headerAlign="center" align="center">投标值</div>
                                                    <div field="clarifyValue_${supplier.account}" width="80" headerAlign="center" align="center">澄清值</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                <%--<div field="remark" width="80" headerAlign="center" align="center">备注</div>--%>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>技术评标说明</b></legend>
                        <table style="width:48%;margin:5px;">
                            <tr>
                                <td class="td_left" style="padding-bottom:5px;">1.投标技术方案比较:</td>
                                <td class="td_right" style="padding-bottom:5px;">
                                    <input name="schemeComp" readonly="true" class="mini-textarea" style="width:99%;" value="${techBidEval.schemeComp}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left" style="padding-bottom:5px;">2.供货范围:</td>
                                <td class="td_right" style="padding-bottom:5px;">
                                    <a id="file" href="${pageContext.request.contextPath}/business/download.action?id=${techBidEval.supplyScope}">${techBidEval!=null&&techBidEval.supplyScopeFile!=null&&techBidEval.supplyScopeFile.uuid!=""?techBidEval.supplyScopeFile.fileName:""} </a>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left" style="padding-bottom:5px;">3.与商务有关的问题:</td>
                                <td class="td_right" style="padding-bottom:5px;">
                                    <input name="businessProb" readonly="true" class="mini-textarea" style="width:99%;" value="${techBidEval.businessProb}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left" style="padding-bottom:5px;">4.其他补充问题:</td>
                                <td class="td_right" style="padding-bottom:5px;">
                                    <input name="otherProb" readonly="true" class="mini-textarea" style="width:99%;" value="${techBidEval.otherProb}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left" style="padding-bottom:5px;">5、结论:</td>
                                <td class="td_right" style="padding-bottom:5px;">
                                    <input name="conclusion" readonly="true" class="mini-textarea" style="width:99%;" value="${techBidEval.conclusion}"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>评标结论</b></legend>
                        <div id="bidResultGrid" class="mini-datagrid"
                             allowCellEdit="true" multiSelect="false"  style="width:100%;height:auto;" showPager="false">
                            <div property="columns">
                                <div field="supplierName" width="80" headerAlign="center" align="center">投标单位名称</div>
                                <div header="评审结论" headerAlign="center">
                                    <div property="columns">
                                        <div name="isFeasible" field="isFeasible" width="100" align="center" headerAlign="center">（可行不可行）</div>
                                    </div>
                                </div>
                                <div field="score" width="80" headerAlign="center" align="center">分值</div>
                                <div field="thirdTotalPrice" width="80" headerAlign="center" align="center">报价</div>
                                <div type="indexcolumn" width="80"  headerAlign="center" align="center">排名</div>
                                <div type="checkcolumn" width="80"  headerAlign="center" align="center">建议中标供应商</div>
                                <div field="remark" width="80" headerAlign="center" align="center">备注</div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>
    </div>
    <div class="host-footer">
        <div class="footer-bg"></div>
        <div class="footer-area">
            <div class="pull-left" id="btnContL">
            </div>
            <div class="pull-right" id="btnContR">
            </div>
        </div>
    </div>
</form>
<script>
    <c:if test="${role != 'view'}">
    var gArrbtn = [{
        id: "btnSave",
        text: "保&nbsp;&nbsp;存",
        style: "btn-primary",
        align: "right",
        event: "toSave()"
    }, {
        id: "btnClose",
        text: "关&nbsp;&nbsp;闭",
        style: "btn-default",
        align: "right",
        event: "toClose()"
    }];
    </c:if>
    <c:if test="${role == 'view'}">
    var gArrbtn = [{
        id: "btnClose",
        text: "关&nbsp;&nbsp;闭",
        style: "btn-default",
        align: "right",
        event: "toClose()"
    }];
    </c:if>
    var dateInputs = [];
    var gIsRead=${isRead==null?false:isRead};
    function changeDivHeight() {
        $("#PageBody").css({
            "width": $(window).width() - 120
        });
        $(".footer-area").css({
            "width": $(window).width() - 120
        });
    }
    // 动态输出按钮
    function dynamicCreateBtn() {
        $.each(gArrbtn, function () {
            var btnStr = "<button onclick=\"" + this.event
                + "\" class=\"btn btn-md " + this.style + "\" type=\"button\">"
                + this.text + "</button>"
            if (this.align == "right") {
                $("#btnContR").append(btnStr);
            } else {
                $("#btnContL").append(btnStr);
            }
        })
    }
    function loadingForm() {
        var oldForm = document.forms[0].submit;
        $(".mini-datepicker").each(function () {
            if ($(this).attr("name") && $(this).attr("name") != "") {
                dateInputs.push($(this).attr("name"));
            }
        });
        $(".mini-datepicker input").each(function () {
            if ($(this).attr("name") && $(this).attr("name") != "") {
                dateInputs.push($(this).attr("name"));
            }
        });
        $.unique(dateInputs);
        document.forms[0].submit = function () {
            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: '数据提交中...' //数据提交中...
            });
            try {
                $.each(dateInputs, function () {
                    if (mini.getbyName(this)) {
                        mini.getbyName(this).setFormat("yyyy-MM-ddTHH:mm:ss");
                    }
                })
            } catch (e) {
            }
            oldForm.call(document.forms[0]);
        }
    }
    function pageUnload() {
        setTimeout(function () {
            $("#bg").remove();
            $("#bg-loader").remove();
            $("body").css("overflow", "auto");
        }, 500)
    }
    initPageEle();
    function initPageEle() {
        changeDivHeight();
        dynamicCreateBtn();
        mini.parse();
        loadingForm();
        pageUnload();
        initBidGrid();
        initDevices();
        initBidResult();
    }
    function initBidGrid() {
        var bidGrid = mini.get("bidGrid");
        bidGrid.setUrl(gDir+"/business/bid/supplierBidResults.action");
        var bidFileUuid = $("input[name=biddingFileUuid]").val();
        bidGrid.load({bidFileUuid:bidFileUuid});
    }

    function initDevices() {
        var deviceGrid = mini.get("deviceGrid");
        deviceGrid.setUrl(gDir+'/business/bid/techBidEvalMaterialParam.action');
        var bidFileUuid = $("input[name=biddingFileUuid]").val();
        deviceGrid.on("load", function () {
            deviceGrid.mergeColumns(["rowId", "materialName"]);
        });
        deviceGrid.load({bidFileUuid:bidFileUuid});
    }

    function initBidResult() {
        var bidResultGrid = mini.get("bidResultGrid");
        bidResultGrid.setUrl(gDir+'/business/bid/supplierScore.action');
        var bidFileUuid = $("input[name=biddingFileUuid]").val();
        bidResultGrid.load({bidFileUuid:bidFileUuid,isFeasible:"可行"},function(){
            var suggestSupplierBid = $("input[name=suggestSupplierBid]").val();
            var selectRow = bidResultGrid.findRow(function(row){
                console.log(row);
                if(row.supplierBidUuid == suggestSupplierBid) {
                    return true;}
            });
            bidResultGrid.setSelected(selectRow);
        });
    }
    function showErrorTexts(errorTexts) {
        var s = errorTexts.join('<br/>');
        mini.showMessageBox({
            width: 250,
            title: "消息提示",
            buttons: ["ok"],
            message: "消息提示",
            html: s,
            showModal: true
        });
    }
    function toSave() {
        var form = document.forms[0];
        //用于页面验证
        var tmpform = new mini.Form(form);
        tmpform.validate();
        if (tmpform.isValid() == false) {
            showErrorTexts(tmpform.getErrorTexts());
            return;
        }
        form.wfStatus.value = "2";// 保存
        var bidResultGrid = mini.get("bidResultGrid");
        var row = bidResultGrid.getSelected();
        if(row==null||row==undefined){
            showErrorTexts(["请选择建议中标供应商"]);
            return ;
        }
        $("input[name=suggestSupplierBid]").val(row.supplierBidUuid);
        mini.mask({
            el: document.body,
            cls: 'mini-mask-loading',
            html: '数据提交中...' //数据提交中...
        });
        setTimeout(function () {
            document.forms[0].submit();
        }, 500)
    }

    function toClose() {
        mini.confirm("确定要关闭吗？", "提示", function (action) {
            if (action == "ok") {
                self.close();
            }
        });
    }
</script>
</body>
</html>