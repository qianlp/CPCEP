<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
<script src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript">
    var gForm = document.forms[0];

    function SaveBefore() {
        gForm.wfStatus.value = 2;
    }

    function wfSubmitDoc() {
        SaveBefore();
        wfSubDocStart();
    }

    function afterLoad() {
    	loadFlowInfo();
        <s:if test="comObj!=null">
        var confirmUuid = $("input[name=confirmUuid]").val();
        var supplierId = $("input[name=supplierId]").val();
        var projectUuid = $("input[name=projectUuid]").val();
        var purchasePlanUuid = $("input[name=purchasePlanUuid]").val();
        loadDeviceInfo(purchasePlanUuid);
        loadProjectInfo(projectUuid);
        //加载供应商信息
        if (confirmUuid == null || confirmUuid == undefined || confirmUuid == '') {
            loadSupplierInfo(supplierId);
        } else {
            loadSupplierBidInfo(confirmUuid);
        }
        </s:if>
        // initUpload();
    }
    
    function loadFlowInfo(){
    	if($("[name=uuid]").val()=="" && "${param.planId}"!=""){
    		mini.getbyName("purchasePlanName").setShowButton(false);
    		var path=gDir+"/business/agreement/queryPurchaseWithProject.action";
    		$.post(path,{
    			pageIndex:0,
    			pageSize:10000
    		},function(data){
    			$.each(mini.decode(data).data,function(){
    				if(this.purchasePlanUuid=="${param.planId}"){
    					mini.getbyName("purchasePlanCode").setValue(this.purchasePlanCode);
                    	mini.getbyName("purchasePlanName").setText(this.purchasePlanName);
                     	mini.getbyName("purchasePlanName").setValue(this.purchasePlanName);
                   	 	mini.getbyName("projectUuid").setValue(this.projectUuid);
                    	mini.getbyName("projectCode").setValue(this.projectCode);
                    	mini.getbyName("projectName").setValue(this.projectName);
//                     	mini.getbyName("projectName").setValue(this.projectName);
                    	mini.getbyName("purchasePlanUuid").setValue(this.purchasePlanUuid);
                    	mini.getbyName("purchaseMethod").setValue(this.purchaseMethod);
                    	mini.getbyName("confirmUuid").setValue(this.confirmUuid);
                    	mini.getbyName("supplierId").setValue(this.supplierId);
                    	mini.getbyName("owner").setValue(this.owner);
                    	mini.getbyName("finalOffer").setValue(this.finalOffer);
                    	//加载设备数量
                    	loadDeviceInfo(this.purchasePlanUuid);
                    	//加载供应商信息
                    	loadProjectInfo(this.projectUuid);
                    	//加载供应商信息
                    	if (this.confirmUuid == null || this.confirmUuid == undefined || this.confirmUuid == '') {
                        	loadSupplierInfo(this.supplierId);
                    	} else {
                        	loadSupplierBidInfo(this.confirmUuid);
                    	}
                    	return false;
    				}
    			})
    		})
    		
    	}else if("${param.planId}"!=""){
    		mini.getbyName("purchasePlanName").setShowButton(false);
    	}
    }
</script>
<div style="display:none">
    <input name="purchasePlanUuid" value="${viewObj.purchasePlanUuid}" class='mini-textbox' style="width:90%"/>
    <input name="purchaseMethod" value="${viewObj.purchaseMethod}" class='mini-textbox' style="width:90%">
    <input name="confirmUuid" value="${viewObj.confirmUuid}" class='mini-textbox' style="width:90%">
    <input name="supplierId" value="${viewObj.supplierId}" class='mini-textbox' style="width:90%">
    <input name="status" value="" class='mini-textbox' style="width:90%">
    <input name="wfStatus" value="${viewObj.wfStatus}" class='mini-textbox' style="width:90%">
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;">
    <div class="widget-container fluid-height col-md-7-k"
         style="height:auto;border-radius:4px;">
        <div class="mbox-header">
			<span class="form-title"
                  style="height:100%;line-height:45px;text-align:center;">合同档案登记
			</span>
        </div>
        <fieldset style="margin: 10px;">
            <legend style="margin-left:10px;"><b>基本信息</b></legend>
            <div class="mbox-body" id="mbox-body"
                 style="height:100%;padding:10px;">
                <table cellspacing="1" cellpadding="1" id="tab"
                       style="width:90%;">
                    <tr>
                        <td class="td_left">采购计划名称：</td>
                        <td class="td_right">
                            <input name="projectUuid" style="display: none;" 
                            value="${viewObj.projectUuid}" 
                                   class='mini-textbox' style="width:90%"/>
                            <input name="purchasePlanName" class="mini-buttonedit" style="width:90%" emptyText="请选择"
                                   value="${viewObj.purchasePlanName}" text="${viewObj.purchasePlanName}" required="true"
                                   allowInput="false" onbuttonclick="onSelectProject">
                        </td>
                        <td class="td_left">采购计划编号：</td>
                        <td class="td_right">
                            <input name="purchasePlanCode" readonly="readonly"
                             value="${viewObj.purchasePlanCode}"
                                   class='mini-textbox' style="width:90%"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">项目名称：</td>
                        <td class="td_right">
<!--                             <input name="projectUuid" style="display: none;" value="${viewObj.projectUuid}" -->
<!--                                    class='mini-textbox' style="width:90%"/> -->
<!--                             <input name="projectName" class="mini-buttonedit" style="width:90%" emptyText="请选择" -->
<!--                                    value="${viewObj.projectName}" text="${viewObj.projectName}" required="true" -->
<!--                                    allowInput="false" onbuttonclick="onSelectProject"> -->
                             <input name="projectName" readonly="readonly" value="${viewObj.projectName}"
                                   class='mini-textbox' style="width:90%"/>
                        </td>
                        <td class="td_left">项目编号：</td>
                        <td class="td_right">
                            <input name="projectCode" readonly="readonly" value="${viewObj.projectCode}"
                                   class='mini-textbox' style="width:90%"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">合同名称：</td>
                        <td class="td_right">
                            <input name="name" required="true" value="${viewObj.name}" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                        <td class="td_left">买方合同编号：</td>
                        <td class="td_right">
                            <input name="code" required="true" value="${viewObj.code}" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">业主方名称：</td>
                        <td class="td_right">
                            <input name="owner" readonly="readonly" value="${viewObj.owner}" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                        <td class="td_left">设备数量：</td>
                        <td class="td_right">
                            <input name="deviceInfo" readonly="readonly" value="" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">合同价格：</td>
                        <td class="td_right">
                            <input name="finalOffer" readonly="readonly" value="${viewObj.finalOffer}"
                                   class='mini-textbox' style="width:90%"/>
                        </td>
                        <td class="td_left">付款方式：</td>
                        <td class="td_right">
                            <input name="paymentMethod" readonly="readonly" value="" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">交货期：</td>
                        <td class="td_right">
                            <input name="deliveryTime" readonly="readonly" value="" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                        <td class="td_left">交货地点：</td>
                        <td class="td_right">
                            <input name="deliveryAddress" value="${viewObj.deliveryAddress}" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                    </tr>
                    <%--<tr>--%>
                        <%--<td class="td_left">技术协议：</td>--%>
                        <%--<td class="td_right">--%>
                            <%--<a id="file_1"></a>--%>
                            <%--<input type='hidden' name='techFileUuid' value=''/>--%>
                            <%--<button type="button" class="layui-btn layui-btn-sm" id="upload_1">--%>
                                <%--<i class="layui-icon">&#xe67c;</i>点击上传--%>
                            <%--</button>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                </table>
            </div>
        </fieldset>
        <fieldset style="margin: 10px;">
            <legend style="margin-left:10px;"><b>投标联系人信息</b></legend>
            <div class="mbox-body" id="mbox-body2"
                 style="height:100%;padding:10px;">
                <table cellspacing="1" cellpadding="1" id="tab2"
                       style="width:90%;">
                    <tr>
                        <td class="td_left">供应商名称：</td>
                        <td class="td_right">
                            <input name="supplierName" readonly="readonly" value="" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                        <td class="td_left">联系人：</td>
                        <td class="td_right">
                            <input name="supplierContacts" readonly="readonly" value="" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">工位电话(T)：</td>
                        <td class="td_right">
                            <input name="supplierPhone" readonly="readonly" value="" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                        <td class="td_left">公司传真：</td>
                        <td class="td_right">
                            <input name="supplierFax" readonly="readonly" value="" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">手机：</td>
                        <td class="td_right">
                            <input name="supplierMobile" readonly="readonly" value="" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                        <td class="td_left">电子邮箱：</td>
                        <td class="td_right">
                            <input name="supplierEmail" readonly="readonly" value="" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
        <fieldset style="margin: 10px;">
            <legend style="margin-left:10px;"><b>现场联系人及联系方式</b></legend>
            <div class="mbox-body" id="mbox-body3"
                 style="height:100%;padding:10px;">
                <table cellspacing="1" cellpadding="1" id="tab3"
                       style="width:90%;">
                    <tr>
                        <td class="td_left">联系人：</td>
                        <td class="td_right">
                            <input name="sceneContact" readonly="readonly" value="" class='mini-textbox'
                                   style="width:90%"/>
                        </td>
                        <td class="td_left">联系方式：</td>
                        <td class="td_right">
                            <input name="contact" readonly="readonly" value="" class='mini-textbox' style="width:90%"/>
                        </td>
                    </tr>

                </table>
            </div>
        </fieldset>
    </div>
</div>
<script type="text/javascript">
    mini.parse();
    function onSelectProject() {
        mini.open({
            url: "${pageContext.request.contextPath}/business/agreement/projectInfo.jsp",
            title: "项目列表",
            width: 725,
            height: 500,
            showMaxButton: true,
            ondestroy: function (data) {
                if (data.indexOf("[") == 0) {
                    var objs = mini.decode(data);
                    console.log("objs:", objs[0]);
                    //purchasePlanCode
                    //purchasePlanName
                     mini.getbyName("purchasePlanCode").setValue(objs[0].purchasePlanCode);
                    
                     mini.getbyName("purchasePlanName").setText(objs[0].purchasePlanName);
                     mini.getbyName("purchasePlanName").setValue(objs[0].purchasePlanName);
                    mini.getbyName("projectUuid").setValue(objs[0].projectUuid);
                    mini.getbyName("projectCode").setValue(objs[0].projectCode);
                    mini.getbyName("projectName").setValue(objs[0].projectName);
//                     mini.getbyName("projectName").setValue(objs[0].projectName);
                    mini.getbyName("purchasePlanUuid").setValue(objs[0].purchasePlanUuid);
                    mini.getbyName("purchaseMethod").setValue(objs[0].purchaseMethod);
                    mini.getbyName("confirmUuid").setValue(objs[0].confirmUuid);
                    mini.getbyName("supplierId").setValue(objs[0].supplierId);
                    mini.getbyName("owner").setValue(objs[0].owner);
                    mini.getbyName("finalOffer").setValue(objs[0].finalOffer);
                    //加载设备数量
                    loadDeviceInfo(objs[0].purchasePlanUuid);
                    //加载供应商信息
                    loadProjectInfo(objs[0].projectUuid);
                    //加载供应商信息
                    if (objs[0].confirmUuid == null || objs[0].confirmUuid == undefined || objs[0].confirmUuid == '') {
                        loadSupplierInfo(objs[0].supplierId);
                    } else {
                        loadSupplierBidInfo(objs[0].confirmUuid);
                    }
                }
            }
        });
    }

    function loadDeviceInfo(purchasePlanUuid) {
        $.ajax({
            url: "${pageContext.request.contextPath}/business/agreement/loadPurchaseDeviceInfo.action",
            type: "post",
            data: {
                purchasePlanUuid: purchasePlanUuid
            },
            async: false,
            success: function (res) {
                var data = JSON.parse(res);
                console.log(data);
                mini.getbyName("deviceInfo").setValue(data.deviceInfo);
            },
            error: function () {

            }
        });
    }

    function loadProjectInfo(projectUuid) {
        $.ajax({
            url: "${pageContext.request.contextPath}/business/agreement/loadProjectInfo.action",
            type: "post",
            data: {
                projectUuid: projectUuid
            },
            async: false,
            success: function (res) {
                var data = JSON.parse(res);
                console.log(data);
                mini.getbyName("sceneContact").setValue(data.sceneContact);
                mini.getbyName("contact").setValue(data.contact);
                //mini.getbyName("email").setValue(data.em  ail);
            },
            error: function () {

            }
        });
    }

    function loadSupplierInfo(supplierId) {
        $.ajax({
            url: "${pageContext.request.contextPath}/business/agreement/loadSupplierInfo.action",
            type: "post",
            data: {
                supplierId: supplierId
            },
            async: false,
            success: function (res) {
                var data = JSON.parse(res);
                console.log(data);
                mini.getbyName("supplierName").setValue(data.supplierName);
                mini.getbyName("supplierContacts").setValue(data.supplierContacts);
                mini.getbyName("supplierPhone").setValue(data.supplierPhone);
                mini.getbyName("supplierFax").setValue(data.supplierFax);
                mini.getbyName("supplierMobile").setValue(data.supplierMobile);
                mini.getbyName("supplierEmail").setValue(data.supplierEmail);
            },
            error: function () {

            }
        });
    }

    function loadSupplierBidInfo(confirmUuid) {
        $.ajax({
            url: "${pageContext.request.contextPath}/business/agreement/loadSupplierBidInfo.action",
            type: "post",
            data: {
                confirmUuid: confirmUuid
            },
            async: false,
            success: function (res) {
                var data = JSON.parse(res);
                console.log(data);
                mini.getbyName("paymentMethod").setValue(data.paymentMethod);
                mini.getbyName("deliveryTime").setValue(data.deliveryTime);
                mini.getbyName("supplierName").setValue(data.supplierName);
                mini.getbyName("supplierContacts").setValue(data.supplierContacts);
                mini.getbyName("supplierPhone").setValue(data.supplierPhone);
                mini.getbyName("supplierFax").setValue(data.supplierFax);
                mini.getbyName("supplierMobile").setValue(data.supplierMobile);
                mini.getbyName("supplierEmail").setValue(data.supplierEmail);
            },
            error: function () {

            }
        });
    }

    <%--function initUpload() {--%>
        <%--<s:if test='comObj!=null'>--%>
        <%--$("#file_1").html('${comObj.techFile==null||comObj.techFile.uuid==null||comObj.techFile.uuid==''?"":comObj.techFile.fileName}');--%>
        <%--$("input[name=techFileUuid]").val('${comObj.techFileUuid}');--%>
        <%--$("#file_1").attr("href", "${pageContext.request.contextPath}/business/download.action?id=${comObj.techFileUuid}");--%>
        <%--</s:if>--%>
        <%--if (gIsRead) {--%>
            <%--$("#upload_1").remove();--%>
        <%--} else {--%>
            <%--layui.use('upload', function () {--%>
                <%--var upload = layui.upload;--%>
                <%--var uploadInst = upload.render({--%>
                    <%--elem: "#upload_" + 1//绑定元素--%>
                    <%--, accept: "file"--%>
                    <%--, data: {--%>
                        <%--type: 4--%>
                    <%--}--%>
                    <%--, url: "${pageContext.request.contextPath}/business/upload.action" //上传接口--%>
                    <%--, done: function (res) {--%>
                        <%--//上传完毕回调--%>
                        <%--var result = mini.decode(res);--%>
                        <%--$("#file_1").html(result.data.fileName);--%>
                        <%--$("input[name=techFileUuid]").val(result.data.uuid);--%>
                        <%--$("#file_1").attr("href", "${pageContext.request.contextPath}/business/download.action?id=" + result.data.uuid);--%>
                    <%--}--%>
                    <%--, error: function () {--%>
                        <%--//请求异常回调--%>
                        <%--alert("上传失败")--%>
                    <%--}--%>
                <%--});--%>
            <%--});--%>
        <%--}--%>
    <%--}--%>
</script>