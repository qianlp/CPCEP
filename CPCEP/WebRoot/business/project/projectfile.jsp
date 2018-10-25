<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/progress/formatDate.js" type="text/javascript"></script>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
    //保存方法
    function toSave(){
        form1.status.value="1";//保存
        form1.submit();
    }
    function afterLoad(){

    };

    function onNodeClick(e) {
        var node = e.node;
        if (node.type == 'root') {
            e.sender.setValue('');
            e.sender.setText('');
        } else {

            e.sender.setValue(node.text);
            e.sender.setText(node.text);
        }
    }

    function onNodeClick1(e) {
        var node = e.node;
        if (node.type == 'root') {
            e.sender.setValue('');
            e.sender.setText('');
        } else {
            e.sender.setValue(node.text);
            e.sender.setText(node.text);

            $("input[name=designManagerUUID]").val(node.id);

        }
    }

    function onNodeClick2(e) {
        var node = e.node;
        if (node.type == 'root') {
            e.sender.setValue('');
            e.sender.setText('');
        } else {

            e.sender.setValue(node.text);
            e.sender.setText(node.text);

            $("input[name=purchaseManagerUUID]").val(node.id);

        }
    }

    function onNodeClick3(e) {
        var node = e.node;
        if (node.type == 'root') {
            e.sender.setValue('');
            e.sender.setText('');
        } else {

            e.sender.setValue(node.text);
            e.sender.setText(node.text);
            $("input[name=projectManagerUUID]").val(node.id);
        }
    }


</script>
<style>
    #gTab .mini-panel-border {
        border: 0px;
    }
</style>
<div style="display:none">

    <input name="status" style="display: none;"/>
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;">
    <div class="widget-container fluid-height col-md-7-k"
         style="height:auto;border-radius:4px;">

        <div class="mbox-header">
				<span class="form-title" style="height:100%;line-height:45px;">
					项目基础档案
				</span>
        </div>

        <div class="mbox-body" style="height:100%;padding:10px;">
            <fieldset style="margin: 10px;">
                <legend style="margin-left:10px;"><b>项目基础档案</b></legend>

                <table style="width:95%;margin:5px;" id="tab">

                    <tr>
                        <td class="td_left">项目编号：</td>
                        <td class="td_right">
                            <input name="projectCode" class="mini-textbox" style="width:99%;"
                                   allowInput="true"  value="${comObj.projectCode}" title="项目编号"/>

                        </td>
                        <td class="td_left">项目名称：</td>
                        <td class="td_right">
                            <input name="projectName" allowInput="true"
                                   class="mini-textbox" value="${comObj.projectName}" style="width: 99%;" title="项目名称"/>
                        </td>

                    </tr>
                    <tr>

                        <td class="td_left">现场联系人：</td>
                        <td class="td_right">
                            <input name="sceneContact" type="text"  value="${comObj.sceneContact}"
                                   property="editor" class="mini-treeselect" showRadioButton="true"
                                   url="${pageContext.request.contextPath}/admin/findOrgTree.action"
                                   allowInput="true" required="true" style="width:99%" title="现场联系人"
                                   onnodeclick="onNodeClick">
                        </td>

                        <td class="td_left">联系方式：</td>
                        <td class="td_right">
                            <input name="contact" allowInput="true"
                                   class="mini-textbox" value="${comObj.contact}" style="width: 99%;" title="联系方式"/>
                        </td>

                    </tr>


                    <tr>
                        <td class="td_left">业主方名称：</td>
                        <td class="td_right">
                            <input name="owner" class="mini-textbox" style="width:99%;" allowInput="true"  value="${comObj.owner}"
                                   title="业主方名称"/>
                        </td>

                        <td class="td_left">项目执行地址：</td>
                        <td class="td_right">
                            <input name="address" class="mini-textbox" style="width:99%;" allowInput="true"  value="${comObj.address}"
                                   title="项目执行地址"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">设计经理：</td>
                        <td class="td_right">

                            <input name="designManagerName" type="text"  value="${comObj.designManagerName}"
                                   property="editor" class="mini-treeselect" showRadioButton="true"
                                   url="${pageContext.request.contextPath}/admin/findOrgTree.action"
                                   allowInput="false" required="true" style="width:99%" title="设计经理"
                                   onnodeclick="onNodeClick1">

							<input type="hidden" name="designManagerUUID" value="${comObj.designManagerUUID}"/>
                            <%--<input name="designManagerUUID" id="designManagerUUID" class="mini-textbox" style="width:99%;visibility: hidden;display:none;" allowInput="true"--%>
                                   <%--title="设计经理" />--%>
                        </td>

                        <td class="td_left">采购经理：</td>
                        <td class="td_right">
                            <input name="purchaseManagerName" type="text"  value="${comObj.purchaseManagerName}"
                                   property="editor" class="mini-treeselect" showRadioButton="true"
                                   url="${pageContext.request.contextPath}/admin/findOrgTree.action"
                                   allowInput="false" required="true" style="width:99%" title="采购经理"
                                   onnodeclick="onNodeClick2">
							<input type="hidden" name="purchaseManagerUUID" value="${comObj.purchaseManagerUUID}" />
                            <%--<input name="purchaseManagerUUID" id="purchaseManagerUUID" class="mini-textbox" style="width:99%;display:none;" allowInput="true"  value="${comObj.purchaseManagerUUID}"--%>
                                   <%--title="采购经理" />--%>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">项目经理：</td>
                        <td class="td_right">

                            <input name="projectManagerName" type="text"  value="${comObj.projectManagerName}"
                                   property="editor" class="mini-treeselect" showRadioButton="true"
                                   url="${pageContext.request.contextPath}/admin/findOrgTree.action"
                                   allowInput="false" required="true" style="width:99%" title="项目经理"
                                   onnodeclick="onNodeClick3">
							<input type="hidden" name="projectManagerUUID" value="${comObj.projectManagerUUID}"/>
                            <%--<input name="projectManagerUUID" id="projectManagerUUID" class="mini-textbox" style="width:99%;visibility: hidden;display:none" allowInput="true"  value="${comObj.projectManagerUUID}"--%>
                                   <%--title="项目经理名称" />--%>
                        </td>

                        <td class="td_left">所属公司：</td>
                        <td class="td_right">

                            <input name="managerCompany" type="text"  value="${comObj.managerCompany}"
                                   property="editor" class="mini-combobox" showRadioButton="true"
                                   url="${pageContext.request.contextPath}/business/project/queryManagerOrgList.action"
                                   allowInput="true" required="true" style="width:99%" title="所属公司"
                                   onnodeclick="onNodeClick">
                        </td>
                    </tr>


                </table>
            </fieldset>

        </div>
    </div>
</div>