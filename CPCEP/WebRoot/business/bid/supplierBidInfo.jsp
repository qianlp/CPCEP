<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>投标</title>
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
    <%--<script src="../../js/form/noFlow.js" type="text/javascript"></script>--%>
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
<form method="post" action="${pageContext.request.contextPath}/business/bid/saveOrUpdate.action" name="form1"
      id="form1">
    <div style="display:none">
        <input name="purchaseApplyUuid" class="mini-textbox"
               value="${biddingFileRelease.biddingBulletin.purchasePlan.purchaseApplyUuid}">
        <input name="uuid" class="mini-textbox" value="${supplierBid.uuid}"/>
        <input name="technicalFileUuid" class="mini-textbox" value="${supplierBid.technicalFileUuid}"/>
        <input name="businessFileUuid" class="mini-textbox" value="${supplierBid.businessFileUuid}"/>
        <input name="priceFileUuid" class="mini-textbox" value="${supplierBid.priceFileUuid}"/>
        <input name="deviceFirstPrice" class="mini-textbox" value="${supplierBid.deviceFirstPrice}">
        <input name="deviceSecondPrice" class="mini-textbox" value="${supplierBid.deviceSecondPrice}">
        <input name="deviceThirdPrice" class="mini-textbox" value="${supplierBid.deviceThirdPrice}">
        <input name="devicePriceRemark" class="mini-textbox" value="${supplierBid.devicePriceRemark}">
        <input name="techFirstPrice" class="mini-textbox" value="${supplierBid.techFirstPrice}">
        <input name="techSecondPrice" class="mini-textbox" value="${supplierBid.techSecondPrice}">
        <input name="techThirdPrice" class="mini-textbox" value="${supplierBid.techThirdPrice}">
        <input name="techPriceRemark" class="mini-textbox" value="${supplierBid.techPriceRemark}">
        <input name="transportFirstPrice" class="mini-textbox" value="${supplierBid.transportFirstPrice}">
        <input name="transportSecondPrice" class="mini-textbox" value="${supplierBid.transportSecondPrice}">
        <input name="transportThirdPrice" class="mini-textbox" value="${supplierBid.transportThirdPrice}">
        <input name="transportPriceRemark" class="mini-textbox" value="${supplierBid.transportPriceRemark}">
        <input name="deviceRemark" class="mini-textbox" value="${supplierBid.deviceRemark}">
        <input name="spareRemark" class="mini-textbox" value="${supplierBid.spareRemark}">
        <input name="toolRemark" class="mini-textbox" value="${supplierBid.toolRemark}">
        <input name="devices" class="mini-textbox" value="">
        <input name="baks" class="mini-textbox" value="">
        <input name="tools" class="mini-textbox" value="">
        <input name="wfStatus" class="mini-textbox" value="">
        <input name="times" class="mini-textbox"
                <s:if test="supplierBid==null">
                    value="1"
                </s:if>
                <s:elseif test="supplierBid!=null && (supplierBid.deviceSecondPrice==null || supplierBid.deviceSecondPrice.length()==0)">
                    value="2"
                </s:elseif>
                <s:else>
                    value="3"
                </s:else>
        />
    </div>
    <div id="PageBody">
        <div class="col-md-7" name="FormMode" id="FormMode"
             style="width:100%;margin:auto;float:none;">
            <div class="widget-container fluid-height col-md-7-k"
                 style="height:auto;border-radius:4px;">
                <div class="mbox-header">
                    <span class="form-title" style="height:100%;line-height:45px;">
                        投标内容
                    </span>
                </div>
                <div class="mbox-body" style="height:100%;padding:10px;">
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>基本信息</b></legend>
                        <table style="width:95%;margin:5px;" id="tab">
                            <tr>
                                <td class="td_left">招标编号：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinCode" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${biddingFileRelease.biddingBulletin.code}"/>
                                </td>
                                <td class="td_left">招标名称：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinName" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${biddingFileRelease.biddingBulletin.name}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">招标文件名称：</td>
                                <td class="td_right">
                                    <input name="biddingFileUuid" allowInput="false" readonly="true"
                                           class="mini-textbox hidden"
                                           value="${biddingFileRelease.uuid}">
                                    <input name="biddingFileReleaseName" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true" value="${biddingFileRelease.name}"/>
                                </td>
                                <td class="td_left">招标文件版本号：</td>
                                <td class="td_right">
                                    <input name="biddingFileReleaseVersion" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true" value="${biddingFileRelease.version}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">预计开标时间：</td>
                                <td class="td_right">
                                    <input name="bidOpenTime" allowInput="false" readonly="true"
                                           <s:if test='biddingFileRelease.biddingBulletin!=null'>value="${fn:substring(biddingFileRelease.biddingBulletin.bidOpenTime,0,19)}"</s:if>
                                           class="mini-textbox" style="width: 99%;" title="编制时间"/>
                                </td>
                                <td class="td_left">截止投标时间：</td>
                                <td class="td_right">
                                    <input name="bidEndTime" allowInput="false" readonly="true"
                                           <s:if test='biddingFileRelease.biddingBulletin!=null'>value="${fn:substring(biddingFileRelease.biddingBulletin.bidEndTime,0,19)}"</s:if>
                                           class="mini-textbox" style="width: 99%;" title="编制时间"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">供应商名称：</td>
                                <td class="td_right">
                                    <s:if test='supplierBid==null'>
                                        <input name="createBy" value="${session.user.userName}" class="mini-textbox"
                                               allowInput="false" readonly="true" style="width:99%;" title="编制人">
                                        <input name="createUuid" value="${session.user.uuid}"
                                               class="mini-textbox hidden" allowInput="false" readonly="true"
                                               style="width:99%;" title="编制人ID">
                                    </s:if>
                                    <s:else>
                                        <input name="createBy" value="${supplierBid.createBy}" allowInput="false"
                                               readonly="true" class="mini-textbox" style="width:99%;" title="编制人">
                                        <input name="createUUid" value="${supplierBid.createUuid}" allowInput="false"
                                               readonly="true" class="mini-textbox hidden" style="width:99%;"
                                               title="编制人ID">
                                    </s:else>
                                </td>
                                <td class="td_left">投标时间：</td>
                                <td class="td_right">
                                    <s:if test="supplierBid==null">
                                        <input name="createDate" allowInput="false" readonly="true"
                                               value="<%=(new java.util.Date()).toLocaleString()%>"
                                               class="mini-datepicker" style="width:99%;" title="编制时间">
                                    </s:if>
                                    <s:else>
                                        <input name="createDate" allowInput="false" readonly="true"
                                               value="${supplierBid.createDate}" class="mini-datepicker"
                                               style="width:99%;" title="编制时间">
                                    </s:else>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">付款方式：</td>
                                <td class="td_right">
                                    <input name="paymentMethod" class="mini-textbox" style="width:99%;" required="true"
                                           value="${supplierBid.paymentMethod}"/>
                                </td>
                                <td class="td_left">联系电话：</td>
                                <td class="td_right">
                                    <input name="phone" class="mini-textbox" style="width:99%;" required="true"
                                           value="${supplierBid.phone}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">联系人：</td>
                                <td class="td_right">
                                    <input name="contacts" class="mini-textbox" style="width:99%;" required="true"
                                           value="${supplierBid.contacts}"/>
                                </td>
                                <td class="td_left">交货期：</td>
                                <td class="td_right">
                                    <input name="deliveryTime" class="mini-textbox" style="width:99%;" required="true"
                                           value="${supplierBid.deliveryTime}"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>投标文件上传</b></legend>
                        <div id="attach" class="mini-datagrid" style="width:100%;" showPager="false">
                            <div property="columns">
                                <div field="name" width="80" headerAlign="center" align="center">文件名称</div>
                                <div name="operator" width="80" renderer="onActionRenderer" headerAlign="center"
                                     align="center">附件
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>合同价格总表</b></legend>
                        <div id="totalPrice" class="mini-datagrid"
                             allowCellEdit="true" allowCellSelect="true" oncellbeginedit="onTotalPriceBeginEdit"
                             ondrawcell="onDrawTotalPriceCell" oncellcommitedit="onTotalPriceCellCommitEdit"
                             showSummaryRow="true" ondrawsummarycell="onDrawTotalPriceSummaryCell"
                             style="width:100%;height:auto;" showPager="false">
                            <div property="columns">
                                <div field="name" width="80" headerAlign="center" align="center">设备名称</div>
                                <div field="other" width="80" headerAlign="center" align="center"></div>
                                <div header="初次报价" headerAlign="center" align="center">
                                    <div property="columns">
                                        <div field="firstPrice" width="80" headerAlign="center" align="center">初次报价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="secondPrice" width="80" headerAlign="center" align="center">二次报价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="thirdPrice" width="80" headerAlign="center" align="center">三次报价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                    </div>
                                </div>
                                <div field="remark" width="80" headerAlign="center" align="center">备注
                                    <input property="editor" class="mini-textbox" style="width:100%;"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>设备分项价格表</b></legend>
                        <legend style="margin-left:10px;">单位：元&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">请注意本次投标价格的单位</span>
                        </legend>
                        <div id="devices" class="mini-datagrid" style="width:100%;height:200px;"
                             url="${pageContext.request.contextPath}/business/bid/loadMaterialPrice.action"
                             idField="uuid" allowCellEdit="true" allowCellSelect="true" allowCellwrap="true"
                             ondrawcell="onDrawDevicesCell"
                             oncellbeginedit="onDevicesBeginEdit" oncellcommitedit="onDevicesCellCommitEdit"
                             showPager="false" showSummaryRow="true" ondrawsummarycell="onDrawSummaryCell"
                        >
                            <div property="columns">
                                <div field="index" type="indexcolumn">序号</div>
                                <div field="uuid" visible="false"></div>
                                <div field="materialPriceUuid" visible="false"></div>
                                <div field="materialName" width="80" headerAlign="center">设备名称</div>
                                <div field="brand" width="80" headerAlign="center">品牌</div>
                                <div field="specModel" width="80" headerAlign="center">规格</div>
                                <div name="detail" width="80" headerAlign="center">设备分项</div>
                                <div field="unit" width="80" headerAlign="center">单位</div>
                                <div field="num" width="80" headerAlign="center">数量</div>
                                <div header="初次报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="firstUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumFirstUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="二次报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="secondUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumSecondUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="三次报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="thirdUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumThirdUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div field="priceRemark" width="80" headerAlign="center">备注
                                    <input property="editor" class="mini-textbox" style="width:100%;"/>
                                </div>
                                <div name="params" width="80" headerAlign="center" renderer="onDeviceParamRenderer">参数</div>
                                <div filed="paramsJson" visible="false">参数修改</div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>备品备件分项价格表</b></legend>
                        <legend style="margin-left:10px;">单位：元&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">请注意本次投标价格的单位</span>
                        </legend>
                        <div id="baks" class="mini-datagrid" style="width:100%;height:200px;"
                             url="${pageContext.request.contextPath}/business/bid/loadMaterialPrice.action"
                             idField="uuid" allowCellEdit="true" allowCellSelect="true" allowCellwrap="true"
                             ondrawcell="onDrawDevicesCell"
                             oncellbeginedit="onDevicesBeginEdit" oncellcommitedit="onDevicesCellCommitEdit"
                             showPager="false" showSummaryRow="true" ondrawsummarycell="onDrawSummaryCell"
                        >
                            <div property="columns">
                                <div field="index" type="indexcolumn">序号</div>
                                <div field="uuid" visible="false"></div>
                                <div field="materialPriceUuid" visible="false"></div>
                                <div field="materialName" width="80" headerAlign="center">设备名称</div>
                                <div field="brand" width="80" headerAlign="center">品牌</div>
                                <div field="specModel" width="80" headerAlign="center">规格</div>
                                <div name="detail" width="80" headerAlign="center">设备分项</div>
                                <div field="unit" width="80" headerAlign="center">单位</div>
                                <div field="num" width="80" headerAlign="center">数量</div>
                                <div header="初次报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="firstUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumFirstUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="二次报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="secondUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumSecondUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="三次报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="thirdUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumThirdUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div field="priceRemark" width="80" headerAlign="center">备注
                                    <input property="editor" class="mini-textbox" style="width:100%;"/>
                                </div>
                                <div name="params" width="80" headerAlign="center" renderer="onBakParamRenderer">参数</div>
                                <div filed="paramsJson" visible="false">参数修改</div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>专用工具分项价格表</b></legend>
                        <legend style="margin-left:10px;">单位：元&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">请注意本次投标价格的单位</span>
                        </legend>
                        <div id="tools" class="mini-datagrid" style="width:100%;height:200px;"
                             url="${pageContext.request.contextPath}/business/bid/loadMaterialPrice.action"
                             idField="uuid" allowCellEdit="true" allowCellSelect="true" allowCellwrap="true"
                             ondrawcell="onDrawDevicesCell"
                             oncellbeginedit="onDevicesBeginEdit" oncellcommitedit="onDevicesCellCommitEdit"
                             showPager="false" showSummaryRow="true" ondrawsummarycell="onDrawSummaryCell"
                        >
                            <div property="columns">
                                <div field="index" type="indexcolumn">序号</div>
                                <div field="uuid" visible="false"></div>
                                <div field="materialPriceUuid" visible="false"></div>
                                <div field="materialName" width="80" headerAlign="center">设备名称</div>
                                <div field="brand" width="80" headerAlign="center">品牌</div>
                                <div field="specModel" width="80" headerAlign="center">规格</div>
                                <div name="detail" width="80" headerAlign="center">设备分项</div>
                                <div field="unit" width="80" headerAlign="center">单位</div>
                                <div field="num" width="80" headerAlign="center">数量</div>
                                <div header="初次报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="firstUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumFirstUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="二次报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="secondUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumSecondUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="三次报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="thirdUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumThirdUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div field="priceRemark" width="80" headerAlign="center">备注
                                    <input property="editor" class="mini-textbox" style="width:100%;"/>
                                </div>
                                <div name="params" width="80" headerAlign="center" renderer="onToolParamRenderer">参数</div>
                                <div filed="paramsJson" visible="false">参数修改</div>
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
    <script>
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
        <c:if test="${role == 'view'}">
        gArrbtn = [{
            id: "btnClose",
            text: "关&nbsp;&nbsp;闭",
            style: "btn-default",
            align: "right",
            event: "toClose()"
        }];
		</c:if>

        var dateInputs = [];

        function loadingForm() {
            var oldForm = document.forms[0].submit;
            $(".mini-datepicker").each(function () {
                if ($(this).attr("name") && $(this).attr("name") != "") {
                    dateInputs.push($(this).attr("name"));
                }
            })
            $(".mini-datepicker input").each(function () {
                if ($(this).attr("name") && $(this).attr("name") != "") {
                    dateInputs.push($(this).attr("name"));
                }
            })
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

        function initSaveGrid() {
            var devices = mini.get("devices").getData();
            var json1 = mini.encode(devices);
            if (json1 != null && json1 != '' && json1 != 'undefined') {
                $("input[name=devices]").val(json1);
            }
            var baks = mini.get("baks").getData();
            var json2 = mini.encode(baks);
            if (json2 != null && json2 != '' && json2 != 'undefined') {
                $("input[name=baks]").val(json2);
            }
            var tools = mini.get("tools").getData();
            var json3 = mini.encode(tools);
            if (json3 != null && json3 != '' && json3 != 'undefined') {
                $("input[name=tools]").val(json3);
            }
        }

        function isNull(value) {
            if (value == null || value == undefined || value.length == 0) {
                return true;
            }
            return false;
        }

        // 统一保存方法
        function toSave() {
            var form = document.forms[0];
            //用于页面验证
            var tmpform = new mini.Form(form);
            tmpform.validate();
            if (tmpform.isValid() == false) {
                showErrorTexts(tmpform.getErrorTexts());
                return;
            }
            if (isNull($("input[name=technicalFileUuid]").val())) {
                alert("请上传技术标")
                return;
            }
            if (isNull($("input[name=businessFileUuid]").val())) {
                alert("请上传商务标");
                return;
            }
            if (isNull($("input[name=priceFileUuid]").val())) {
                alert("请上传价格标");
                return;
            }
            var times = findTimes();
            var devices = mini.get("devices");
            var baks = mini.get("baks");
            var tools = mini.get("tools");
            var row1 = devices.findRows(function (row) {
                if (times == 1 && isNull(row.firstUnitPrice)) return true;
                if (times == 2 && isNull(row.secondUnitPrice)) return true;
                if (times == 3 && isNull(row.thirdUnitPrice)) return true;
            });
            var row2 = baks.findRows(function (row) {
                if (times == 1 && isNull(row.firstUnitPrice)) return true;
                if (times == 2 && isNull(row.secondUnitPrice)) return true;
                if (times == 3 && isNull(row.thirdUnitPrice)) return true;
            });
            var row3 = tools.findRows(function (row) {
                if (times == 1 && isNull(row.firstUnitPrice)) return true;
                if (times == 2 && isNull(row.secondUnitPrice)) return true;
                if (times == 3 && isNull(row.thirdUnitPrice)) return true;
            });
            if (row1.length != 0||row2.length!=0||row3.length!=0) {
                alert("设备的价格不能为空");
                return ;
            }
            initSaveGrid();
            form.wfStatus.value = "2";// 保存
            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: '数据提交中...' //数据提交中...
            });
            setTimeout(function () {
                document.forms[0].submit();
            }, 500)
        }

        // 统一保存方法
        function toClose() {
            mini.confirm("确定要关闭吗？", "提示", function (action) {
                if (action == "ok") {
                    self.close();
                }
            })

        }

        // 自动调整页面宽度
        function changeDivHeight() {
            $("#PageBody").css({
                "width": $(window).width() - 120
            });
            $(".footer-area").css({
                "width": $(window).width() - 120
            });
        }

        window.onresize = function () {
            changeDivHeight();
        }

        function pageUnload() {
            setTimeout(function () {
                $("#bg").remove();
                $("#bg-loader").remove();
                $("body").css("overflow", "auto");
            }, 500)
        }

        function onActionRenderer(e) {
            var record = e.record, rowIndex = e.rowIndex;
            return '<a id="file_' + rowIndex + '">' + record.fileName + '</a>'
                + '<button type="button" class="layui-btn layui-btn-sm" id="upload_' + rowIndex + '">'
                + '<i class="layui-icon">&#xe67c;</i>点击上传' + '</button>';
        }
        function onDeviceParamRenderer(e){
            return  '<a  style="color:blue;text-align: center;"  href="javascript:openParam('+e.rowIndex+',\'' + e.record.uuid + '\',\'devices\')">响应</a>';
        }
        function onBakParamRenderer(e){
            return  '<a  style="color:blue;text-align: center;"  href="javascript:openParam('+e.rowIndex+',\'' + e.record.uuid + '\',\'baks\')">响应</a>';
        }
        function onToolParamRenderer(e){
            return  '<a  style="color:blue;text-align: center;"  href="javascript:openParam('+e.rowIndex+',\'' + e.record.uuid+ '\',\'tools\')">响应</a>';
        }

        function openParam(rowIndex,purchaseMaterialId,gridId) {
            var uuid = $("input[name='uuid']").val();
            var strURL="",strTitle="参数表",intWidth=800,intHeight=500;
            if(uuid==undefined||uuid==null) uuid="";
            strURL="${pageContext.request.contextPath}/business/bid/supplierMaterialParams.jsp?purchaseMaterialId="+purchaseMaterialId+"&supplierBidUuid="+uuid;
            mini.open({
                url: strURL,
                title: strTitle,
                width: intWidth,
                height: intHeight,
                showMaxButton: true,
                ondestroy: function (data) {
                    var dJson = mini.encode(data);
                    var dGrid = mini.get(gridId);
                    dGrid.updateRow(dGrid.getRow(rowIndex),{paramsJson:dJson});
                }
            });
        }

        function initAttach() {
            var grid = mini.get("attach");
            grid.set({
                data: [{
                    name: "技术标",
                    attach: 7,
                    fileName: ""
                }, {
                    name: "商务标",
                    attach: 8,
                    fileName: ""
                }, {
                    name: "价格标",
                    attach: 9,
                    fileName: ""
                }]
            });
            initUploadBtn();
        }

        function initUploadBtn() {
            layui.use('upload', function () {
                var upload = layui.upload;
                var uploadInst = upload.render({
                    elem: "#upload_" + 0//绑定元素
                    , accept: "file"
                    , data: {
                        type: 7
                    }
                    , url: "${pageContext.request.contextPath}/business/upload.action" //上传接口
                    , done: function (res) {
                        //上传完毕回调
                        var result = mini.decode(res);
                        $("#file_0").html(result.data.fileName);
                        $("input[name=technicalFileUuid]").val(result.data.uuid);
                    }
                    , error: function () {
                        //请求异常回调
                        alert("上传失败")
                    }
                });
                var uploadInst = upload.render({
                    elem: "#upload_" + 1//绑定元素
                    , accept: "file"
                    , data: {
                        type: 7
                    }
                    , url: "${pageContext.request.contextPath}/business/upload.action" //上传接口
                    , done: function (res) {
                        //上传完毕回调
                        var result = mini.decode(res);
                        $("#file_1").html(result.data.fileName);
                        $("input[name=businessFileUuid]").val(result.data.uuid);
                    }
                    , error: function () {
                        //请求异常回调
                        alert("上传失败")
                    }
                });
                var uploadInst = upload.render({
                    elem: "#upload_" + 2//绑定元素
                    , accept: "file"
                    , data: {
                        type: 7
                    }
                    , url: "${pageContext.request.contextPath}/business/upload.action" //上传接口
                    , done: function (res) {
                        //上传完毕回调
                        var result = mini.decode(res);
                        $("#file_2").html(result.data.fileName);
                        $("input[name=priceFileUuid]").val(result.data.uuid);
                    }
                    , error: function () {
                        //请求异常回调
                        alert("上传失败")
                    }
                });
            });
        }

        function initTotalPrice() {
            var totalPriceGrid = mini.get("totalPrice");
            totalPriceGrid.set({
                data: [{
                    name: "设备价格",
                    firstPrice: $("input[name=deviceFirstPrice]").val(),
                    secondPrice: $("input[name=deviceSecondPrice]").val(),
                    thirdPrice: $("input[name=deviceThirdPrice]").val(),
                    remark: $("input[name=devicePriceRemark]").val()
                }, {
                    name: "其中",
                    other: "设备本体",
                    remark: $("input[name=deviceRemark]").val()
                }, {
                    name: "其中",
                    other: "随机备品备件",
                    remark: $("input[name=spareRemark]").val()
                }, {
                    name: "其中",
                    other: "专用工具",
                    remark: $("input[name=toolRemark]").val()
                }, {
                    name: "技术服务费",
                    firstPrice: $("input[name=techFirstPrice]").val(),
                    secondPrice: $("input[name=techSecondPrice]").val(),
                    thirdPrice: $("input[name=techThirdPrice]").val(),
                    remark: $("input[name=techPriceRemark]").val()
                }, {
                    name: "运输保险费",
                    firstPrice: $("input[name=transportFirstPrice]").val(),
                    secondPrice: $("input[name=transportSecondPrice]").val(),
                    thirdPrice: $("input[name=transportThirdPrice]").val(),
                    remark: $("input[name=transportPriceRemark]").val()
                }]
            });
            var marges = [
                {rowIndex: 0, columnIndex: 0, rowSpan: 1, colSpan: 2},
                {rowIndex: 1, columnIndex: 0, rowSpan: 3, colSpan: 1},
                {rowIndex: 4, columnIndex: 0, rowSpan: 1, colSpan: 2},
                {rowIndex: 5, columnIndex: 0, rowSpan: 1, colSpan: 2}
            ];
            totalPriceGrid.mergeCells(marges);
        }

        function initDevice() {
            var purchaseApplyUuid = $("input[name=purchaseApplyUuid]").val();
            var supplierBidUuid = $("input[name=uuid]").val();
            var devices = mini.get("devices");
            devices.load({deviceType: '1', purchaseId: purchaseApplyUuid, supplierBidUuid: supplierBidUuid});
            var baks = mini.get("baks");
            baks.load({deviceType: '2', purchaseId: purchaseApplyUuid, supplierBidUuid: supplierBidUuid});
            var tools = mini.get("tools");
            tools.load({deviceType: '3', purchaseId: purchaseApplyUuid, supplierBidUuid: supplierBidUuid});
        }

        function initPageEle() {
            changeDivHeight();
            dynamicCreateBtn();
            mini.parse();
            loadingForm();
            if (typeof(afterLoad) != "undefined") {
                afterLoad();
            }
            pageUnload();
            initAttach();
            initTotalPrice();
            initDevice();
        }


        //限定合同价格总表的编辑
        function onTotalPriceBeginEdit(e) {
            var record = e.record, field = e.field, rowIndex = e.rowIndex;
            var times = findTimes();
            if ((rowIndex == 1 || rowIndex == 2 || rowIndex == 3) && (field == "firstPrice" || field == "secondPrice" || field == "thirdPrice")) {
                e.cancel = true;    //其中的多次报价不可编辑
            }
            if (times == 1 && (field == "secondPrice" || field == "thirdPrice")) {
                e.cancel = true;
            }
            if (times == 2 && (field == "firstPrice" || field == "thirdPrice")) {
                e.cancel = true;
            }
            if (times == 3 && (field == "firstPrice" || field == "secondPrice")) {
                e.cancel = true;
            }

        }

        function findTimes() {
            return $("input[name=times]").val();
        }

        //devices表格总数
        function onDrawTotalPriceSummaryCell(e) {
            var result = e.result;
            var grid = e.sender;
            var rows = e.data;
            if (e.field == "name") {
                e.cellHtml = "小计";
            }
            if (e.field == "firstPrice") {
                var total = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var t = row.firstPrice;
                    if (isNaN(t)) continue;
                    total += Number(t);
                }
                e.cellHtml = total;
            }
            if (e.field == "secondPrice") {
                var total = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var t = row.secondPrice;
                    if (isNaN(t)) continue;
                    total += Number(t);
                }
                e.cellHtml = total;
            }
            if (e.field == "thirdPrice") {
                var total = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var t = row.thirdPrice;
                    if (isNaN(t)) continue;
                    total += Number(t);
                }
                e.cellHtml = total;
            }
        }

        function onTotalPriceCellCommitEdit(e) {
            var field = e.field, value = e.value, rowIndex = e.rowIndex;
            var editor = e.editor;
            editor.validate();
            if (editor.isValid() == false || editor.value < 0) {
                alert("请输入数字且不小于0");
                e.cancel = true;
                return;
            }
            if (rowIndex == 0 && field == "firstPrice") {
                $("input[name=deviceFirstPrice]").val(value);
            }
            if (rowIndex == 0 && field == "secondPrice") {
                $("input[name=deviceSecondPrice]").val(value);
            }
            if (rowIndex == 0 && field == "thirdPrice") {
                $("input[name=deviceThirdPrice]").val(value);
            }
            if (rowIndex == 0 && field == "remark") {
                $("input[name=devicePriceRemark]").val(value);
            }
            if (rowIndex == 1 && field == "remark") {
                $("input[name=deviceRemark]").val(value);
            }
            if (rowIndex == 2 && field == "remark") {
                $("input[name=spareRemark]").val(value);
            }
            if (rowIndex == 3 && field == "remark") {
                $("input[name=toolRemark]").val(value);
            }
            if (rowIndex == 4 && field == "firstPrice") {
                $("input[name=techFirstPrice]").val(value);
            }
            if (rowIndex == 4 && field == "secondPrice") {
                $("input[name=techSecondPrice]").val(value);
            }
            if (rowIndex == 4 && field == "thirdPrice") {
                $("input[name=techThirdPrice]").val(value);
            }
            if (rowIndex == 4 && field == "remark") {
                $("input[name=techPriceRemark]").val(value);
            }
            if (rowIndex == 5 && field == "firstPrice") {
                $("input[name=transportFirstPrice]").val(value);
            }
            if (rowIndex == 5 && field == "secondPrice") {
                $("input[name=transportSecondPrice]").val(value);
            }
            if (rowIndex == 5 && field == "thirdPrice") {
                $("input[name=transportThirdPrice]").val(value);
            }
            if (rowIndex == 5 && field == "remark") {
                $("input[name=transportPriceRemark]").val(value);
            }
        }

        function onDrawTotalPriceCell(e) {

        }

        //开始编辑前
        function onDevicesBeginEdit(e) {
            var record = e.record, field = e.field, rowIndex = e.rowIndex;
            var times = findTimes();
            if (times == 1 && (field == "secondUnitPrice" || field == "thirdUnitPrice")) {
                e.cancel = true;
            }
            if (times == 2 && (field == "firstUnitPrice" || field == "thirdUnitPrice")) {
                e.cancel = true;
            }
            if (times == 3 && (field == "firstUnitPrice" || field == "secondUnitPrice")) {
                e.cancel = true;
            }
        }

        //提交编辑后
        function onDevicesCellCommitEdit(e) {
            var editor = e.editor;

            editor.validate();
            if (editor.isValid() == false || editor.value < 0) {
                alert("请输入数字且不小于0");
                e.cancel = true;
            }
        }

        //device绘制
        function onDrawDevicesCell(e) {
            var record = e.record;
            if (e.field == "sumFirstUnitPrice") {
                var firstUnitPrice = record.firstUnitPrice;
                var num = record.num;
                var t = firstUnitPrice * num;
                if (!isNaN(t)) e.cellHtml = t;
            }
            if (e.field == "sumSecondUnitPrice") {
                var secondUnitPrice = record.secondUnitPrice;
                var num = record.num;
                var t = secondUnitPrice * num;
                if (!isNaN(t)) e.cellHtml = t;
            }
            if (e.field == "sumThirdUnitPrice") {
                var thirdUnitPrice = record.thirdUnitPrice;
                var num = record.num;
                var t = thirdUnitPrice * num;
                if (!isNaN(t)) e.cellHtml = t;
            }
        }

        //devices表格总数
        function onDrawSummaryCell(e) {
            var result = e.result;
            var grid = e.sender;
            var index = grid.id == "devices" ? 1 : grid.id == "baks" ? 2 : 3;
            var rows = e.data;
            if (e.field == "index") {
                e.cellHtml = "小计";
            }
            if (e.field == "sumFirstUnitPrice") {
                var total = 0, j = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var firstUnitPrice = row.firstUnitPrice;
                    var num = row.num;
                    var t = firstUnitPrice * num;
                    if (isNaN(t)) {
                        j++;
                        continue;
                    }
                    total += t;
                }
                var firstPrice = "";
                if (j != rows.length) {
                    e.cellHtml = total;
                    firstPrice = total;
                }
                var totalGrid = mini.get("totalPrice");
                totalGrid.updateRow(totalGrid.getRow(index), {firstPrice: firstPrice});
            }
            if (e.field == "sumSecondUnitPrice") {
                var total = 0, j = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var secondUnitPrice = row.secondUnitPrice;
                    var num = row.num;
                    var t = secondUnitPrice * num;
                    if (isNaN(t)) {
                        j++;
                        continue;
                    }
                    total += t;
                }
                var secondPrice = "";
                if (j != rows.length) {
                    e.cellHtml = total;
                    secondPrice = total;
                }
                var totalGrid = mini.get("totalPrice");
                totalGrid.updateRow(totalGrid.getRow(index), {secondPrice: secondPrice});
            }
            if (e.field == "sumThirdUnitPrice") {
                var total = 0, j = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var thirdUnitPrice = row.thirdUnitPrice;
                    var num = row.num;
                    var t = thirdUnitPrice * num;
                    if (isNaN(t)) {
                        j++;
                        continue;
                    }
                    total += t;
                }
                var thirdPrice = "";
                if (j != rows.length) {
                    e.cellHtml = total;
                    thirdPrice = total;
                }
                var totalGrid = mini.get("totalPrice");
                totalGrid.updateRow(totalGrid.getRow(index), {thirdPrice: thirdPrice});
            }
        }

        initPageEle();
    </script>
</form>
</body>
</html>