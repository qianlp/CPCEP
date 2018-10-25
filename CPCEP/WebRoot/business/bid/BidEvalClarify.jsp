<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>技术评标</title>
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
<form method="post" action="${pageContext.request.contextPath}/business/bid/saveOrUpdateTechBidEval.action" name="form1"
      id="form1">
    <div style="display:none">
        <input name="biddingFileUuid" class="mini-textbox" value="${bidFile.uuid}"/>
        <input name="uuid" class="mini-textbox" value="${techBidEval.uuid}"/>
        <input name="bidResultGrid" class="mini-textbox" value="">
        <input name="wfStatus" class="mini-textbox" value="${techBidEval.wfStatus}">
        <input name="supplyScope" class="mini-textbox" value="${techBidEval.supplyScope}"/>
    </div>
    <div id="PageBody">
        <div class="col-md-7" name="FormMode" id="FormMode" style="width:100%;margin:auto;float:none;">
            <div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
                <div class="mbox-header">
                    <span class="form-title" style="height:100%;line-height:45px;">
                        技术评标
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
                                           value="${bidFile.biddingBulletin.code}"/>
                                </td>
                                <td class="td_left">招标名称：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinName" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${bidFile.biddingBulletin.name}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">评标时间：</td>
                                <td class="td_right">
                                    <s:if test="techBidEval==null||techBidEval.createDate==null">
                                        <input name="createDate" allowInput="false" readonly="true"
                                               value="<%=(new java.util.Date()).toLocaleString()%>"
                                               class="mini-datepicker" style="width:99%;" title="编制时间">
                                    </s:if>
                                    <s:else>
                                        <input name="createDate" allowInput="false" readonly="true"
                                               value="${techBidEval.createDate}" class="mini-datepicker"
                                               style="width:99%;" title="编制时间">
                                    </s:else>
                                </td>
                                <td class="td_left">招标文件版本号：</td>
                                <td class="td_right">
                                    <input name="bidFileVersion" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true" value="${bidFile.version}"/>
                                </td>
                            </tr>
                        </table>
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
                                    <input name="schemeComp" class="mini-textarea" style="width:99%;" value="${techBidEval.schemeComp}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left" style="padding-bottom:5px;">2.供货范围:</td>
                                <td class="td_left" style="padding-bottom:5px;">
                                    <input name="supplyScopeDescribe" class="mini-textarea" style="width:99%;" value="${techBidEval.supplyScopeDescribe}"/>
                                    <a id="file_12"></a>
                                    <button type="button" class="layui-btn layui-btn-sm" id="upload_12">
                                        <i class="layui-icon">&#xe67c;</i>点击上传
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left" style="padding-bottom:5px;">3.与商务有关的问题:</td>
                                <td class="td_right" style="padding-bottom:5px;">
                                    <input name="businessProb" class="mini-textarea" style="width:99%;" value="${techBidEval.businessProb}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left" style="padding-bottom:5px;">4.其他补充问题:</td>
                                <td class="td_right" style="padding-bottom:5px;">
                                    <input name="otherProb" class="mini-textarea" style="width:99%;" value="${techBidEval.otherProb}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left" style="padding-bottom:5px;">5、结论:</td>
                                <td class="td_right" style="padding-bottom:5px;">
                                    <input name="conclusion" class="mini-textarea" style="width:99%;" value="${techBidEval.conclusion}"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>评标结论</b></legend>
                        <div id="bidResultGrid" class="mini-datagrid"
                             allowCellEdit="true" allowCellSelect="true" style="width:100%;height:auto;" showPager="false">
                            <div property="columns">
                                <div field="supplierName" width="80" headerAlign="center" align="center">投标单位名称</div>
                                <div header="评审结论" headerAlign="center">
                                    <div property="columns">
                                        <div type="comboboxcolumn" autoShowPopup="true" name="isFeasible" field="isFeasible" width="100" align="center" headerAlign="center">（可行不可行）
                                            <input property="editor" class="mini-combobox" style="width:100%;" data="IsFeasible" />
                                        </div>
                                    </div>
                                </div>
                                <div field="score" width="80" headerAlign="center" align="center">分值
                                    <input property="editor" class="mini-textbox" style="width:100%;"/>
                                </div>
                                <div field="remark" width="80" headerAlign="center" align="center">备注
                                    <input property="editor" class="mini-textbox" style="width:100%;"/>
                                </div>
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
    initPageEle();
    var IsFeasible = [{ id: '可行', text: '可行' }, { id: '不可行', text: '不可行'}];
    function initPageEle() {
        changeDivHeight();
        dynamicCreateBtn();
        mini.parse();
        loadingForm();
        pageUnload();
        initDevices();
        initBidResult();
        initUpload(12,"supplyScope");
    }

    function initUpload(id,inputName){
        layui.use('upload', function () {
            var upload = layui.upload;
            upload.render({
                elem: "#upload_" + id//绑定元素
                , accept: "file"
                , data: {
                    type: id
                }
                , url: "${pageContext.request.contextPath}/business/upload.action" //上传接口
                , done: function (res) {
                    //上传完毕回调
                    var result = mini.decode(res);
                    $("#file_"+id).html(result.data.fileName);
                    $("input[name="+inputName+"]").val(result.data.uuid);
                }
                , error: function () {
                    //请求异常回调
                    alert("上传失败")
                }
            });
        });
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
        bidResultGrid.load({bidFileUuid:bidFileUuid});
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

    function toClose() {
        mini.confirm("确定要关闭吗？", "提示", function (action) {
            if (action == "ok") {
                self.close();
            }
        });
    }

    function initSaveGrid() {
        var bidResultGrid = mini.get("bidResultGrid").getData();
        var json1 = mini.encode(bidResultGrid);
        if (json1 != null && json1 != '' && json1 != 'undefined') {
            $("input[name=bidResultGrid]").val(json1);
        }
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
        var row3 = mini.get("bidResultGrid").findRows(function (row) {
            if (isNull(row.score)) return true;
        });
        if (row3.length!=0) {
            alert("分数不能为空");
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
    function isNull(value) {
        if (value == null || value == undefined || value.length == 0) {
            return true;
        }
        return false;
    }
</script>
</body>
</html>
