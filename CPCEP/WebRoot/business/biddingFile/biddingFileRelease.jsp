<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="${pageContext.request.contextPath}/js/progress/formatDate.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
<script src="${pageContext.request.contextPath}/layui/layui.js"></script>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty param.planId && empty comObj}" >
	<s:action name="findFirstFim" namespace="/business/flow" executeResult="true">
		<s:param name="planId" >${param.planId}</s:param>
	</s:action>
</c:if>
<style>
    #gTab .mini-panel-border {
        border: 0px;
    }
</style>
<div style="display:none">

    <input name="status" style="display: none;"/>
    <input name="planId" value="${comObj.planId}"/>
    <input name="invSupplier" id="invSupplier"/>
    <input name="canSupplier" id="canSupplier"/>
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;">
    <div class="widget-container fluid-height col-md-7-k"
         style="height:auto;border-radius:4px;">

        <div class="mbox-header">
				<span class="form-title" style="height:100%;line-height:45px;">
					招标文件发布
				</span>
        </div>

        <div class="mbox-body" style="height:100%;padding:10px;">
            <fieldset style="margin: 10px;">
                <legend style="margin-left:10px;"><b>基本信息</b></legend>

                <table style="width:95%;margin:5px;" id="tab">

                    <tr>
                        <td class="td_left">招标编号：</td>
                        <td class="td_right">
                        <input name="code" class="mini-buttonedit" style="width:99%" emptyText="请选择"
                               value="${comObj.code}" text="${comObj.code}" textField="code" valueField="code"
                               allowInput="false" required="true"  allowSelect="true"
							   onbuttonclick="onSelectBid"
						>
							<input type="hidden" name="bulletinUuid" value="${comObj.bulletinUuid}"/>
                        </td>
                        </td>
                        <td class="td_left">招标名称：</td>
                        <td class="td_right">
                            <input name="name" class="mini-textbox" style="width:99%;"
                                   required="true" allowInput="false" value="${comObj.name}">
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">招标文件名称：</td>
                        <td class="td_right">
                            <input name="fileName" class="mini-textbox" style="width:99%;"
                                   required="true" value="${comObj.fileName}">
                        </td>
                        <td class="td_left">版本号：</td>
                        <td class="td_right">
                            <input name="version" class="mini-textbox" style="width:99%;"
                                   required="true" allowInput="true" value="${comObj.version}">
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">备注：</td>
                        <td class="td_right" colspan="6" style="padding:3px;">
                            <input name="remark"  value="${comObj.remark}" class="mini-textarea" style="width:99.6%" title="备注">
                        </td>
                    </tr>


                </table>
            </fieldset>
			<input type="hidden" name="teachFile" value="${comObj.teachFile}"/>
			<input type="hidden" name="businessFile" value="${comObj.businessFile}"/>
			<input type="hidden" name="overallFile" value="${comObj.overallFile}"/>
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

        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        initAttach();
    });
    //保存方法
    function toSave(){
        form1.status.value="${empty comObj.status?0:comObj.status}";
        form1.submit();
    }

    function afterLoad(){
		loadFlowInfo();
    };
    
    function loadFlowInfo(){
    	if($("[name=uuid]").val()=="" && "${param.planId}"!=""){
    		mini.getbyName("code").setShowButton(false);
    		$("[name=planId]").val("${plan.uuid}");
    		$("[name=bulletinUuid]").val("${bBull.uuid}")
    		mini.getbyName("code").setValue("${bBull.code}");
    		mini.getbyName("code").setText("${bBull.code}");
    		mini.getbyName("name").setValue("${bBull.name}");
    	}else if("${param.planId}"!=""){
    		mini.getbyName("code").setShowButton(false);
    	}
    }

    function onNodeClick(e) {
        var node = e.node;
        mini.getbyName("name").setValue(node.name);
        mini.getbyName("code").setValue(node.code);
        $("input[name=bulletinUuid]").val(node.id);
    }

    function initAttach() {
        var grid = mini.get("attach");
        grid.set({
            data: [{
                name: "技术标",
                attach: 7,
                <c:choose>
					<c:when test="${empty comObj.teachFile}">
					fileName: ""
					</c:when>
					<c:otherwise>
                fileName: "<a href='${pageContext.request.contextPath}/business/download.action?id=${comObj.teachFile}' target='_blank'>技术标</a>"
					</c:otherwise>
                </c:choose>
            }, {
                name: "商务标",
                attach: 8,
                <c:choose>
					<c:when test="${empty comObj.businessFile}">
					fileName: ""
					</c:when>
					<c:otherwise>
                fileName: "<a href='${pageContext.request.contextPath}/business/download.action?id=${comObj.businessFile}' target='_blank'>商务标</a>"
					</c:otherwise>
                </c:choose>
            }, {
                name: "价格标",
                attach: 9,
				<c:choose>
					<c:when test="${empty comObj.overallFile}">
                fileName: ""
					</c:when>
					<c:otherwise>
                fileName: "<a href='${pageContext.request.contextPath}/business/download.action?id=${comObj.overallFile}' target='_blank'>价格标</a>"
					</c:otherwise>
				</c:choose>
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
                    $("input[name=teachFile]").val(result.data.uuid);
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
                    $("input[name=businessFile]").val(result.data.uuid);
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
                    $("input[name=overallFile]").val(result.data.uuid);
                }
                , error: function () {
                    //请求异常回调
                    alert("上传失败")
                }
            });
        });
    }


    function onActionRenderer(e) {
        var record = e.record, rowIndex = e.rowIndex;
        return '<a id="file_' + rowIndex + '">' + record.fileName + '</a>'
            + '<button type="button" class="layui-btn layui-btn-sm" id="upload_' + rowIndex + '">'
            + '<i class="layui-icon">&#xe67c;</i>点击上传' + '</button>';
    }


    function onSelectBid() {
        mini.open({
            url: gDir+"/admin/findExhibitById.action?randomId=b07f3f2f-6c32-4ea7-8294-53e3885ddf58",
            title: "招标公告",
            width: 725,
            height: 500,
            showMaxButton: false,
            ondestroy: function (data) {
                if (data.indexOf("[") == 0) {
                    var objs = mini.decode(data);

                    mini.getbyName("code").setText(objs[0].code);
                    mini.getbyName("code").setValue(objs[0].code);
                    mini.getbyName("name").setValue(objs[0].name);
					$("input[name=bulletinUuid]").val(objs[0].uuid);
					$("input[name=planId]").val(objs[0].purchasePlanUuid);
                }
            }
        });
    }
</script>