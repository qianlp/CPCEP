<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<c:if test="${not empty param.planId && empty comObj}" >
	<s:action name="findFirstFim" namespace="/business/flow" executeResult="true">
		<s:param name="planId" >${param.planId}</s:param>
	</s:action>
</c:if>
<script src="../js/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="../js/ckeditor/config.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
<script src="${pageContext.request.contextPath}/layui/layui.js"></script>
<%--<script src="${pageContext.request.contextPath}/js/file/swfupload/swfupload.package.js" type="text/javascript"></script>--%>
<script type="text/javascript">
    var gForm = document.forms[0];
    var gCkEditor = null;
    function initCkEditor(){
        if (gIsRead || gLoginUser!=$("[name=createBy]").val()) {
            CKEDITOR.config.readOnly = true;
        }
        if (gCkEditor == null) {
            gCkEditor = CKEDITOR.appendTo('editor', {
                customConfig : 'ckeditor_config_QC.js',
                customConfig : 'ckeditor_config.js',
                on : {
                    instanceReady : function(ev) {
                        gCkEditor.setData(gForm.content.value);
                    }
                }
            });
        }
    }
    function SaveBefore(){
        gForm.content.value = gCkEditor.getData();
    }

    function wfSaveBefore() {
        SaveBefore();
    }
    function wfSubmitDoc(){
        SaveBefore();
        wfSubDocStart();
    }

    function afterLoad(){
    	loadFlowInfo();
        mini.getbyName("cby").setValue($("[name=createBy]").val());
        loadFieldset($("[name=purchaseMethod]").val());
        loadAttach();
        initCkEditor();
        
        gPageEvent["SaveBefore"]="SaveBefore()";
        //TODO 初始化邀请供应商
    }
    
    function loadFlowInfo(){
    	if($("[name=uuid]").val()=="" && "${param.planId}"!=""){
    		mini.getbyName("purchasePlanCode").setShowButton(false);
    		mini.getbyName("purchasePlanUuid").setValue("${plan.uuid}");
    		mini.getbyName("purchasePlanCode").setValue("${plan.purchasePlanCode}");
    		mini.getbyName("purchasePlanCode").setText("${plan.purchasePlanCode}");
    		mini.getbyName("purchasePlanName").setValue("${plan.purchasePlanName}");
    		mini.getbyName("projectCode").setValue("${plan.projectCode}");
    		mini.getbyName("projectName").setValue("${plan.projectName}");
    		mini.getbyName("purchaseMethod").setValue("${plan.purchaseMethod}");
    	}else if("${param.planId}"!=""){
    		if(mini.getbyName("purchasePlanCode")){
    			mini.getbyName("purchasePlanCode").setShowButton(false);
    		}
    	}
    }
</script>
<div style="display:none">
    <%--字段--%>
    <input name="status" />
    <textarea  name="content" id="content" >${comObj.content}</textarea>
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;">
    <div class="widget-container fluid-height col-md-7-k"
         style="height:auto;border-radius:4px;">
        <div class="mbox-header">
			<span class="form-title"
                  style="height:100%;line-height:45px;text-align:center;">招标公告
			</span>
        </div>
        <fieldset style="margin: 10px;">
            <legend style="margin-left:10px;"><b>基本信息</b></legend>
            <div class="mbox-body" id="mbox-body"
                 style="height:100%;padding:10px;">
                <table cellspacing="1" cellpadding="1" id="tab"
                       style="width:90%;">
                    <tr>
                        <td class="td_left">制单人：</td>
                        <td class="td_right">
                            <input name="cby" class='mini-textbox' style="width:90%" readonly="readonly" />
                        </td>
                        <td class="td_left">制单时间：</td>
                        <td class="td_right">
                            <input name="createDatea" allowInput="false" readonly="readonly"
                                   <s:if test='comObj!=null'>value="${fn:substring(comObj.createDate,0,19)}"</s:if>
                                   <s:else>value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>"</s:else>
                                   class="mini-textbox" style="width: 90%;" title="制单时间"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">招标公告名称：</td>
                        <td class="td_right">
                            <input name="name"  required="true" value="${comObj.name}" class='mini-textbox' style="width:90%" />
                        </td>
                        <td class="td_left">招标公告编号：</td>
                        <td class="td_right">
                            <input name="code" required="true" value="${comObj.code}" class='mini-textbox' style="width:90%" />
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">采购计划编号：</td>
                        <td class="td_right">
                            <input style="display: none;" class='mini-textbox' name="purchasePlanUuid" value="${comObj.purchasePlanUuid}">
                            <input name="purchasePlanCode" class="mini-buttonedit" style="width:90%" emptyText="请选择"
                                   value="${comObj.purchasePlan.purchasePlanCode}" text="${comObj.purchasePlan.purchasePlanCode}" required="true"
                                   allowInput="false" onbuttonclick="onSelectPurchasePlan">
                        </td>
                        <td class="td_left">采购计划名称：</td>
                        <td class="td_right">
                            <input name="purchasePlanName" value="${comObj.purchasePlan.purchasePlanName}" readonly="readonly" class='mini-textbox' style="width:90%" />
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">项目编号：</td>
                        <td class="td_right">
                            <input name="projectCode" value="${comObj.purchasePlan.projectCode}" readonly="readonly" class='mini-textbox' style="width:90%" />
                        </td>
                        <td class="td_left">项目名称：</td>
                        <td class="td_right">
                            <input name="projectName" value="${comObj.purchasePlan.projectName}"  readonly="readonly" class='mini-textbox' style="width:90%" />
                        </td>
                    </tr>
                    <tr>
                        <%--<td class="td_left">采购设备名称：</td>
                        <td class="td_right">
                            <input name="projectCode" class='mini-textbox' style="width:90%" />
                        </td>--%>
                        <td class="td_left">采购方式：</td>
                        <td class="td_right">
                            <input name="purchaseMethod" value="${comObj.purchasePlan.purchaseMethod}"  readonly="readonly" class='mini-textbox' style="width:90%" />
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
        <fieldset style="margin: 10px;">
            <legend style="margin-left:10px;"><b>招标重点信息</b></legend>
            <div class="mbox-body"
                 style="height:100%;padding:10px;">
                <table cellspacing="1" cellpadding="1"
                       style="width:90%;">
                    <tr>
                        <td class="td_left">报名开始时间：</td>
                        <td class="td_right">
                            <input name="registerStartTime" allowInput="false" class="mini-datepicker" required="true"
                                   <s:if test='comObj!=null'>value="${fn:substring(comObj.registerStartTime,0,19)}"</s:if>
                                   class="mini-textbox" style="width: 90%;" title="报名开始时间" ondrawdate="ondrawdate"
                                   format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                        </td>
                        <td class="td_left">报名结束时间：</td>
                        <td class="td_right">
                            <input name="registerEndTime" allowInput="false" class="mini-datepicker" required="true"
                                   <s:if test='comObj!=null'>value="${fn:substring(comObj.registerEndTime,0,19)}"</s:if>
                                   class="mini-textbox" style="width: 90%;" title="报名结束时间" ondrawdate="ondrawdate"
                                   format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">投标开始时间：</td>
                        <td class="td_right">
                            <input name="bidStartTime" allowInput="false" class="mini-datepicker" required="true"
                                   <s:if test='comObj!=null'>value="${fn:substring(comObj.bidStartTime,0,19)}"</s:if>
                                   class="mini-textbox" style="width: 90%;" title="投标开始时间" ondrawdate="ondrawdate"
                                    format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                        </td>
                        <td class="td_left">投标结束时间：</td>
                        <td class="td_right">
                            <input name="bidEndTime" allowInput="false" class="mini-datepicker" required="true"
                                   <s:if test='comObj!=null'>value="${fn:substring(comObj.bidEndTime,0,19)}"</s:if>
                                   class="mini-textbox" style="width: 90%;" title="投标结束时间" ondrawdate="ondrawdate"
                                    format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">预计开标时间：</td>
                        <td class="td_right">
                            <input name="bidOpenTime" allowInput="false" class="mini-datepicker" required="true"
                                   <s:if test='comObj!=null'>value="${fn:substring(comObj.bidOpenTime,0,19)}"</s:if>
                                   class="mini-textbox" style="width: 90%;" title="预计开标时间" ondrawdate="ondrawdate"
                                    format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
        <fieldset style="margin: 10px;display: none;" id="publish">
            <legend style="margin-left:10px;"><b>公告基本信息设置</b></legend>
            <div class="mbox-body"
                 style="height:100%;padding:10px;">
                <table cellspacing="1" cellpadding="1"
                       style="width:90%;">
                    <tr>
                        <td class="td_left">发布媒体：</td>
                        <td class="td_right">
                            <input id="publishMedia" name="publishMedia" class="mini-combobox" style="width:90%;" textField="text" valueField="text"
                                   data="[{text:'院内网站'},{text:'中国招采网'}]"
                                   required="true" allowinput="false" emptytext="请选择..."
                                   showNullItem="false"    value="${comObj.publishMedia}"
                            />
                        </td>
                        <td class="td_left"></td>
                        <td class="td_right"></td>
                    </tr>
                    <tr>
                        <td class="td_left">对外附件：</td>
                        <td class="td_right">
                            <div id="upload">
                                <a id="file_1"></a>
                                <input type='hidden' name='attachUuid' value=''/>
                                <button type="button" class="layui-btn layui-btn-sm" id="upload_1">
                                    <i class="layui-icon">&#xe67c;</i>点击上传
                                </button>
                            </div>
                        </td>
                        <td class="td_left"></td>
                        <td class="td_right"></td>
                    </tr>
                </table>
            </div>
        </fieldset>
        <fieldset style="margin: 10px;display: none;" id="supplierManager">
            <legend style="margin-left:10px;"><b>邀请供应商</b></legend>
            <div id="supplier" class="mini-datagrid" style="width:100%;height:200px;"
                 url="${pageContext.request.contextPath}/business/procurement/loadCandiateForPage.action?type=1&planId=${comObj.purchasePlanUuid}"
                 allowCellwrap="false" showPager="false">
                <div property="columns" style="height: 100%">
                    <div field="uuid" visible="false"></div>
                    <div field="curDocId" visible="false"></div>
                    <div field="supplierId" visible="false"></div>
                    <div type="indexcolumn" align="center">序号</div>
					<div field="name" width="80" headerAlign="center" align="center" >供应商名称</div>
					<%--<div field="materialName" width="80" headerAlign="center" align="center">所供产品名称</div>--%>
					<div field='contactAddress' width='100'  headerAlign='center' align='center'>通讯地址</div>
					<div field="corporations" width="80" headerAlign="center" align="center">法人代表</div>
					<div field="contacts" width="80" headerAlign="center" align="center">联系人</div>
					<div field="phon" width="80" headerAlign="center" align="center">联系方式</div>
                    <div field="remark" width="80" headerAlign="center" align="center" >备注</div>
                </div>
            </div>
        </fieldset>
        <fieldset style="margin: 10px;display: none;" id="bulletin">
            <legend style="margin-left:10px;"><b>公告内容</b></legend>
            <div class="mbox-body"
                 style="height:100%;padding:10px;">
                <table cellspacing="1" cellpadding="1"
                       style="width:90%;margin:0 auto">
                    <tr>
                        <td class="td_left">公告模板：</td>
                        <td class="td_right">
                            <input name="templateUuid" id="template" class="mini-combobox" style="width:90%" emptyText="请选择" onvaluechanged="initContent"
                                   url="${pageContext.request.contextPath}/business/bulletin/queryTemplate.action" value="${comObj.templateUuid}" textField="text" valueField="id"
                                   allowInput="false" required="true">
                        </td>
                        <td class="td_left"></td>
                        <td class="td_right"></td>
                    </tr>
                    <tr>
                        <td class="td_left" colspan=4>
                            <div id='editor'
                                 style="overflow:hidden;"></div>
                            <div id='read'
                                 style='color:black;padding-left:15px;width:90%;display:none'>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
    </div>
</div>
<script type="text/javascript">
    function onSelectPurchasePlan() {
        mini.open({
            url: "${pageContext.request.contextPath}/business/procurement/purchasePlanList.jsp",
            title: "采购计划列表",
            width: 725,
            height: 500,
            showMaxButton: true,
            ondestroy: function (data) {
                if (data.indexOf("[") == 0) {
                    var objs = mini.decode(data);
                    console.log("objs:", objs[0]);

                    mini.getbyName("projectCode").setValue(objs[0].projectCode);
                    mini.getbyName("projectName").setValue(objs[0].projectName);
                    mini.getbyName("purchasePlanUuid").setValue(objs[0].uuid);
                    mini.getbyName("purchasePlanCode").setValue(objs[0].purchasePlanCode);
                    mini.getbyName("purchasePlanCode").setText(objs[0].purchasePlanCode);
                    mini.getbyName("purchasePlanName").setValue(objs[0].purchasePlanName);
                    mini.getbyName("purchaseMethod").setValue(objs[0].purchaseMethod);
                    loadFieldset(objs[0].purchaseMethod);
                }
            }
        });
    }
    function loadFieldset(purchaseMethod) {
        if (purchaseMethod=='公开招标'){
            $("#publish").show();
            $("#bulletin").show();
            $("#supplierManager").hide();
        }else if (purchaseMethod=='邀请招标'){
            $("#publish").hide();
            $("#supplierManager").show();
            $("#bulletin").show();
            //
            var grid = mini.get("supplier");
            var planId = $("input[name=purchasePlanUuid]").val();
            grid.setUrl("${pageContext.request.contextPath}/business/procurement/loadCandiateForPage.action?type=1&planId="+planId);
            grid.load();
        }
    }
    function initContent(e){
        gCkEditor.setData(e.selected.content);
    }
    
    function loadAttach() {
        <s:if test='comObj!=null && comObj.attachUuid != null %% comObj.attachUuid != ""'>
            $("#file_1").html('${comObj.supplierAttachment.fileName}');
            $("input[name=attachUuid]").val('${comObj.attachUuid}');
            $("#file_1").attr("href","${pageContext.request.contextPath}/business/download.action?id=${comObj.attachUuid}");
        </s:if>
        if (gIsRead){
            $("#upload_1").remove();
        }else {
            layui.use('upload', function () {
                var upload = layui.upload;
                var uploadInst = upload.render({
                    elem: "#upload_" + 1//绑定元素
                    , accept: "file"
                    , data: {
                        type: 4
                    }
                    , url: "${pageContext.request.contextPath}/business/upload.action" //上传接口
                    , done: function (res) {
                        //上传完毕回调
                        var result = mini.decode(res);
                        $("#file_1").html(result.data.fileName);
                        $("input[name=attachUuid]").val(result.data.uuid);
                        $("#file_1").attr("href","${pageContext.request.contextPath}/business/download.action?id="+result.data.uuid);
                    }
                    , error: function () {
                        //请求异常回调
                        alert("上传失败")
                    }
                });
            });
        }
    }

	function ondrawdate(e) {
        var name = e.source.name;
        var date = e.date;
        var now = new Date();
        if (date.getTime() < now.getTime()) {
            e.allowSelect = false;
        }
        if(name == "registerStartTime") {
            var endTime = mini.getbyName("registerEndTime").getValue();
			if(endTime != null && endTime != "" && date.getTime() >= endTime)
			    e.allowSelect = false;
		} else if(name == "registerEndTime") {
            var startTime = mini.getbyName("registerStartTime").getValue();
            if (startTime != null && startTime != "" && date.getTime() <= startTime)
                e.allowSelect = false;
        } else if(name == "bidStartTime") {
            var endTime = mini.getbyName("bidEndTime").getValue();
            if (endTime != null && endTime != "" && date.getTime() >= endTime)
                e.allowSelect = false;
        } else if(name == "bidEndTime") {
            var startTime = mini.getbyName("bidStartTime").getValue();
            if (startTime != null && startTime != "" && date.getTime() <= startTime)
                e.allowSelect = false;
        } else if(name == "bidOpenTime") {

		}

	}
</script>