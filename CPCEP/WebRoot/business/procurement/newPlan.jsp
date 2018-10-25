<%@ page import="htos.coresys.entity.User" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	User checkUser = (User) request.getSession().getAttribute("user");
	request.setAttribute("opUserName", checkUser.getUserName());
%>
<div style="display:none">
    <input name="prjID" value="${comObj.projectUuid}"/>
    <input name="projectUuid" value="${comObj.projectUuid}"/>
    <input name="invSupplier" id="invSupplier"/>
    <input name="canSupplier" id="canSupplier"/>
    <input name="purchaseApplyUuid" id="purchaseApplyUuid" value="${comObj.purchaseApplyUuid}"/>
	<input name="tempId" />
	<input name="technologyReviewerUuid" id="technologyReviewerUuid" value="${comObj.technologyReviewerUuid}">
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;padding-top:35px;">
    <div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
        <div class="mbox-header">
            <span class="form-title" style="height:100%;line-height:45px;">
                <i class="o-host-application" style="background-position: -0px -20px;"></i>
              采购计划
            </span>
        </div>
        <fieldset style="margin: 10px;">
            <legend style="margin-left:10px;"><b>基本信息</b></legend>
            <div class="mbox-body" style="height:100%;padding:10px;">
                <table style="width:95%;margin:5px;" id="tab">
                    <tr>
                        <td class="td_left">制单人：</td>
                        <td class="td_right">
                            <input class="mini-textbox" style="width:99%;" readonly="readonly"
                                   required="true"
                                   value="<c:if test="${empty comObj.createBy}">${session.user.userName}</c:if><c:if test="${not empty comObj.createBy}">${comObj.createBy}</c:if>">
                        </td>
                        <td class="td_left">制单时间：</td>
                        <td class="td_right">
                            <input class="mini-textbox" style="width:99%;" readonly="readonly"
                                   required="true"
                                   value="<c:if test="${empty comObj.createDate}"><%=(new java.util.Date()).toLocaleString()%></c:if><c:if test="${not empty comObj.createDate}">${comObj.createDate}</c:if>">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">采购计划编号：</td>
                        <td class="td_right">
                            <input name="purchasePlanCode" id="purchasePlanCode"  class="mini-textbox" style="width:99%;"
                                   value="<c:if test="${not empty comObj.purchasePlanCode}">${comObj.purchasePlanCode}</c:if>">
                        </td>
                        <td class="td_left">采购计划名称：</td>
                        <td class="td_right">
                            <input name="purchasePlanName" class="mini-textbox" style="width:99%;"
                                   required="true" allowInput="true" value="${comObj.purchasePlanName}">
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">采购申请编号：</td>
                        <td class="td_right">
                            <input name="purchaseApplyCode" class="mini-buttonedit" style="width:99%" emptyText="请选择"
                                   value="${comObj.purchaseApplyCode}" text="${comObj.purchaseApplyCode}"
                                   allowInput="false" required="true" onbuttonclick="onSelectPurchaseApply">
							<%-- <input type="hidden" name="purchaseApplyUuid" value="${comObj.purchaseApplyUuid}" /> --%>
                        </td>
                        <td class="td_left">采购申请名称：</td>
                        <td class="td_right">
                            <input name="purchaseApplyName" class="mini-textbox" style="width:99%;" vtype="maxLength:64"
                                   required="true" value="${comObj.purchaseApplyName}">
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">项目编号：</td>
                        <td class="td_right">
                            <input name="projectCode" class="mini-textbox" style="width:99%;" readonly="readonly"
                                   required="true" allowInput="false" value="${comObj.projectCode}">
                        </td>
                        <td class="td_left">项目名称：</td>
                        <td class="td_right">
                            <input name="projectName" class="mini-textbox" style="width:99%;" readonly="readonly"
                                   required="true" allowInput="false" value="${comObj.projectName}">
                        </td>
                    </tr>


                    <tr>
                        <td class="td_left">采购执行主体：</td>
                        <td class="td_right">
                            <input name="purchaseExecuteUuid" class="mini-combobox" style="width:99%" emptyText="请选择"
                                   url="${pageContext.request.contextPath}/business/procurement/queryExecuteBodyData.action" value="${comObj.purchaseExecuteUuid}" textField="body" valueField="uuid"
                                   allowInput="false" required="true">
                        </td>
                        <td class="td_left">采购执行人：</td>
                        <td class="td_right">
                            <input name="executePeoName" class="mini-textbox" style="width:99%"  readonly="readonly"
                                   value="${comObj.executePeoName}" allowInput="false" required="true">
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">采购方式：</td>
                        <td class="td_right">
                            <input id="purchaseMethod" name="purchaseMethod" class="mini-combobox" style="width:100%;" textField="text" valueField="text"
                                   onvaluechanged="initView"   data="[{id:'1',text:'公开招标'},{id:'2',text:'邀请招标'},{id:'3',text:'长协'},{id:'4',text:'竞争性谈判'}]"
                                   required="true" allowinput="false" emptytext="请选择..."
                                   showNullItem="false"    value="${comObj.purchaseMethod}"
                            />
                        </td>
                        <td class="td_left">技术评标人：</td>
                        <td class="td_right">
                            <input name="technologyReviewerName" class="mini-textbox" style="width:99%"
                                   value="${comObj.technologyReviewerName}" readonly="readonly"
                                   allowInput="false" required="true">
                        </td>
                    </tr>

                   
                    <!-- 
                        <td class="td_left">评标方式：</td>
                        <td class="td_right">
                            <input name="reviewMethod"  id="reviewMethod" class="mini-combobox" style="width: 99%;" textfield="text"
                                   valuefield="text" emptytext="请选择..." required="true"
                                   allowinput="false" shownullitem="true" nullitemtext="请选择..."
                                   data="[{id:'1',text:'评标方式1'},{id:'2',text:'评标方式2'},{id:'3',text:'评标方式3'},{id:'4',text:'评标方式4'}]"
                                   value="${comObj.reviewMethod}"
                            />
                        </td>
                        <td class="td_left">资格审查方式：</td>
                        <td class="td_right">
                            <input name="checkWay" class="mini-combobox" style="width:99%" emptyText="请选择"
                                   url="${pageContext.request.contextPath}/business/procurement/queryCheckWayData.action"
                                   value="${comObj.checkWay}" textField="method" valueField="uuid"
                                   allowInput="false" required="true">
                        </td>
					 
                        <td class="td_left">采购类型：</td>
                        <td class="td_right">
                            <input name="purchaseType" type="text"
                                   class="mini-combobox" value="${comObj.purchaseType}"  readonly="readonly"
                                   url="${pageContext.request.contextPath}/business/procurement/queryInfoTypeList.action"
                                   allowInput="false" required="true" style="width:99%" title="采购类型">
                        </td>
                    -->

                    <tr>
                        <td class="td_left">要求定标时间：</td>
                        <td class="td_right">
                            <input name="requestCalibrationTime" class="mini-datepicker" style="width:99%"
                                   value="${comObj.requestCalibrationTime}" text="${comObj.requestCalibrationTime}"
                                   allowInput="false" required="true" >
                        </td>
                        <td class="td_left">要求进场时间：</td>
                        <td class="td_right">
                            <input name="requestApproachTime" class="mini-datepicker" style="width:99%"
                                   value="${comObj.requestApproachTime}" text="${comObj.requestApproachTime}"
                                   allowInput="false" >
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>


        <div name="supplierManager" id="supplierManager">
            <fieldset style="margin: 10px;">
                <legend style="margin-left:10px;"><b>邀请供应商</b></legend>

                <div class="mini-toolbar" style="padding:5px;border-bottom-width:0;border:0px;">
                    <div class="mini-button" iconCls="icon-add" plain="true" onclick="onSupplier()">新增</div>
                    <div class="mini-button" iconCls="icon-remove" plain="true" onclick="removeSupplier()">删除</div>
                </div>
                <div id="supplier" class="mini-datagrid" style="width:100%;height:200px;"
                     url="${pageContext.request.contextPath}/business/procurement/loadCandiateForPage.action?type=1&planId=${comObj.uuid}"
                     allowResize="false" pageSize="20"
                     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
                     editNextOnEnterKey="true" editNextRowCell="true" allowCellwrap="false">
                    <div property="columns" style="height: 100%">
                        <div field="uuid" visible="false"></div>
                        <div field="curDocId" visible="false"></div>
                        <div field="supplierId" visible="false"></div>
                        <div type="indexcolumn" align="center" headerAlign="center" >序号</div>
                        <div field="name" width="80" headerAlign="center" align="center" >供应商名称</div>
						<%--<div field="materialName" width="80" headerAlign="center" align="center">所供产品名称</div>--%>
						<div field='contactAddress' width='100'  headerAlign='center' align='center'>通讯地址</div>
						<div field="corporations" width="80" headerAlign="center" align="center">法人代表</div>
						<div field="contacts" width="80" headerAlign="center" align="center">联系人</div>
						<div field="phon" width="80" headerAlign="center" align="center">联系方式</div>
                        <div field="remark" width="80" headerAlign="center" align="center" >备注
                            <input property="editor" class="mini-textbox" style="width:99%;height: 99%"/>
                        </div>

                    </div>
                </div>

            </fieldset>
        </div>


        <div name="candidateManager" id="candidateManager" >
            <fieldset style="margin: 10px;">
                <legend style="margin-left:10px;"><b>候选供应商</b></legend>

                <div class="mini-toolbar" style="padding:5px;border-bottom-width:0;border:0px;">
                    <div class="mini-button" iconCls="icon-add" plain="true" onclick="addCandidate()">新增</div>
                    <div class="mini-button" iconCls="icon-remove" plain="true" onclick="removeCandidate()">删除</div>
                </div>
                <div id="candidate" name="candidate" class="mini-datagrid" style="width:100%;height:200px;"
                     url="${pageContext.request.contextPath}/business/procurement/loadCandiateForPage.action?type=2&planId=${comObj.uuid}"
                     idField="uuid"
                     allowResize="true" multiSelect='true' oncellcommitedit="onCandidateCommitEdit"
                     allowCellEdit="true" allowCellSelect="true" oncellvalidation="onCellValidation"
                

                >
                    <div property="columns">
                        <div field="indexcolumn"></div>
                        <div field="uuid" visible="false"></div>
                        <div field="curDocId" visible="false"></div>
                        <div field="supplierId" visible="false"></div>
                        <div field="name" width="80" headerAlign="center" align="center" >供应商名称</div>
                        <div field="tecOffer" width="80" headerAlign="center" align="center">技术评价
                            <input property="editor" class="mini-textbox" style="width:99%;height: 99%"/></div>
                        <%--<div field='busOffer' visible='false' width='100'  headerAlign='center' align='center'>商务报价--%>
                            <%--<input property="editor" class="mini-textbox" style="width:99%;height: 99%"/></div>--%>
                        <div field="finalOffer" width="80" headerAlign="center" align="center">最终定价
                            <input property="editor" class="mini-textbox" style="width:99%;height: 99%"/></div>
                        <div field="rank" type="indexcolumn" width="80" headerAlign="center" align="center">排名</div>
                     <div field='isWin' width="80" type="checkboxcolumn" trueValue="1" falseValue="0"  headerAlign="center" align="center">中标单位
                        </div>
                        <div field="remark" width="80" headerAlign="center" align="center" >备注
                            <input property="editor" class="mini-textbox" style="width:99%;height: 99%"/>
                        </div>

                    </div>
                </div>

            </fieldset>
        </div>

        <div name="devices">
            <fieldset style="margin: 10px;">
                <legend style="margin-left:10px;"><b>设备清单</b></legend>

                <div id="deviceGrid1" name="deviceGrid1" class="mini-datagrid" style="width:100%;height:200px;display: grid"
						<c:choose>
							<c:when test="${empty comObj.purchaseApplyUuid}">url="${pageContext.request.contextPath}/business/procurement/loadMaterialForPage.action"</c:when>
							<c:otherwise>url="${pageContext.request.contextPath}/business/procurement/loadMaterialForPage.action?deviceType=1&purchaseId=${comObj.purchaseApplyUuid}"</c:otherwise>
						</c:choose>
                     idField="uuid"
                     allowResize="true" multiSelect='true'
                     allowCellEdit="true" allowCellSelect="true" allowCellwrap="true"
                     editNextOnEnterKey="true" editNextRowCell="true" oncellvalidation="onCellValidation"
                >
                    <div property="columns">
                        <!-- 序号, 系统描述, 材料设备名称,单位, 品牌,规格型号,参数,设备分项,数量,要求进场时间,-->

                        <div field="uuid" visible="false"></div>
                        <div field="curDocId" visible="false"></div>
                        <div type="indexcolumn" align="center" headerAlign="center">序号</div>
                        <div field="materialCode" width="80" headerAlign="center" align="center" visible="false">材料设备Code</div>
                        <div field="materialName" width="80" headerAlign="center" align="center">材料设备名称</div>
                        <div field='categoryUuid' visible='false' width='100'  headerAlign='center' align='center'>分类ID</div>
                        <div field="unit" width="80" headerAlign="center" align="center">单位</div>
<!--                         <div field="brand" width="80" headerAlign="center" align="center">品牌</div> -->
                        <div field="specModel" width="80" headerAlign="center" align="center">规格型号</div>
                        <%--<div width="80" headerAlign="center" align="center">参数</div>--%>
<!--                         <div field="category" width="80" headerAlign="center" align="center" renderer="onActionRenderer">设备分项</div> -->
                        <div field="num" width="80" headerAlign="center" align="center" >数量
                        </div>
                        <div field="requireDate" width="80" dateFormat="yyyy-MM-dd" visible=false>要求进场时间
							<input property="editor" name="test" visible=false readonly="true" visible=false  class="mini-datepicker" style="width:99%;height: 99%"/>
                        </div>
                        <div field="remark" width="80" headerAlign="center" align="center" >备注
                            <input property="editor" class="mini-textbox" style="width:99%;height: 99%"/>
                        </div>

                    </div>
                </div>

            </fieldset>

            <fieldset style="margin: 10px;">
                <legend style="margin-left:10px;"><b>备品备件清单</b></legend>

                <div id="deviceGrid2" class="mini-datagrid" style="width:100%;height:200px;display: grid"
					 <c:choose>
						 <c:when test="${empty comObj.purchaseApplyUuid}">url="${pageContext.request.contextPath}/business/procurement/loadMaterialForPage.action"</c:when>
						 <c:otherwise>url="${pageContext.request.contextPath}/business/procurement/loadMaterialForPage.action?deviceType=2&purchaseId=${comObj.purchaseApplyUuid}"</c:otherwise>
					 </c:choose>
                     allowResize="true" pageSize="20"
                     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
                     editNextOnEnterKey="true" editNextRowCell="true" allowCellwrap="true">
                    <div property="columns">
                        <!-- 序号, 系统描述, 材料设备名称,单位, 品牌,规格型号,参数,设备分项,数量,要求进场时间,-->

                        <div field="uuid" visible="false"></div>
                        <div field="curDocId" visible="false"></div>
                        <div type="indexcolumn" align="center" headerAlign="center">序号</div>
                        <div field="materialCode" width="80" headerAlign="center" align="center" visible="false">材料设备Code</div>
                        <div field="materialName" width="80" headerAlign="center" align="center">材料设备名称</div>
                        <div field='categoryUuid' visible='false' width='100'  headerAlign='center' align='center'>分类ID</div>
                        <div field="unit" width="80" headerAlign="center" align="center">单位</div>
<!--                         <div field="brand" width="80" headerAlign="center" align="center">品牌</div> -->
                        <div field="specModel" width="80" headerAlign="center" align="center">规格型号</div>
                        <%--<div field="categoryUuid" name="param" width="80" headerAlign="center" align="center">参数</div>--%>
                        <%--<div field="category" width="80" headerAlign="center" align="center">设备分项</div>--%>
                        <div field="num" width="80" headerAlign="center" align="center" >数量
                        </div>
                        <%--<div field="requiredTime" width="80" readonly="readonly" dateFormat="yyyy-MM-dd">要求进场时间--%>
                        <%--</div>--%>
                        <div field="requireDate" width="80" dateFormat="yyyy-MM-dd" visible=false>要求进场时间
                            <input property="editor" name="test" readonly="true" visible=false class="mini-datepicker" style="width:99%;height: 99%"/>
                        </div>
                        <div field="remark" width="80" headerAlign="center" align="center" >备注
                            <input property="editor" class="mini-textbox" style="width:99%;height: 99%"/>
                        </div>

                    </div>
                </div>

            </fieldset>


            <fieldset style="margin: 10px;">
                <legend style="margin-left:10px;"><b>专有工具清单</b></legend>

                <div id="deviceGrid3" class="mini-datagrid" style="width:100%;height:200px;display: grid"
						<c:choose>
							<c:when test="${empty comObj.purchaseApplyUuid}">url="${pageContext.request.contextPath}/business/procurement/loadMaterialForPage.action"</c:when>
							<c:otherwise>url="${pageContext.request.contextPath}/business/procurement/loadMaterialForPage.action?deviceType=3&purchaseId=${comObj.purchaseApplyUuid}"</c:otherwise>
						</c:choose>
                     allowResize="true" pageSize="20"
                     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
                     editNextOnEnterKey="true" editNextRowCell="true" allowCellwrap="true">
                    <div property="columns">
                        <!-- 序号, 系统描述, 材料设备名称,单位, 品牌,规格型号,参数,设备分项,数量,要求进场时间,-->

                        <div field="uuid" visible="false"></div>
                        <div field="curDocId" visible="false"></div>
                        <div type="indexcolumn" align="center" headerAlign="center">序号</div>
                        <div field="materialCode" width="80" headerAlign="center" align="center" visible="false">材料设备Code</div>
                        <div field="materialName" width="80" headerAlign="center" align="center">材料设备名称</div>
                        <div field='categoryUuid' visible='false' width='100'  headerAlign='center' align='center'>分类ID</div>
                        <div field="unit" width="80" headerAlign="center" align="center">单位</div>
<!--                         <div field="brand" width="80" headerAlign="center" align="center">品牌</div> -->
                        <div field="specModel" width="80" headerAlign="center" align="center">规格型号</div>
                        <%--<div field="categoryUuid" name="param" width="80" headerAlign="center" align="center">参数</div>--%>
                        <%--<div field="category" width="80" headerAlign="center" align="center">设备分项</div>--%>
                        <div field="num" width="80" headerAlign="center" align="center" aria-required="true">数量
                        </div>
                        <%--<div field="requiredTime" width="80" dateFormat="yyyy-MM-dd">要求进场时间--%>
                        <%--</div>--%>

                        <div field="requireDate" width="80" dateFormat="yyyy-MM-dd" visible=false>要求进场时间
                            <input property="editor" name="test" readonly="true" class="mini-datepicker" visible=false style="width:99%;height: 99%"/>
                        </div>
                        <div field="remark" width="80" headerAlign="center" align="center" >备注
                            <input property="editor" class="mini-textbox" style="width:99%;height: 99%"/>
                        </div>

                    </div>
                </div>

            </fieldset>

        </div>
    </div>
</div>
<script type="text/javascript">

    mini.parse();
    $('#candidateManager').hide();
    $('#supplierManager').hide();
    $('#candidateManager').hide();
    $('#candidateManager').select(0);

    var sameExecutePeo = ${comObj.executePeoName == opUserName};
    function onSelectPurchaseApply() {
        mini.open({
            url: "${pageContext.request.contextPath}/business/procurement/purchaseApplySelectList.jsp",
            title: "采购申请列表",
            width: 725,
            height: 500,
            showMaxButton: true,
            ondestroy: function (data) {
                if (data.indexOf("[") == 0) {
                    var objs = mini.decode(data);
                    console.log("objs:", objs[0]);

                    $("[name=prjID]").val(objs[0].prjID);
                    $("[name=projectUuid]").val(objs[0].prjID);
                    $("#purchaseApplyUuid").val(objs[0].uuid);
                    console.log($("#purchaseApplyUuid").val());
                    mini.getbyName("projectCode").setValue(objs[0].projectCode);
                    mini.getbyName("projectName").setValue(objs[0].projectName);
                    mini.getbyName("purchaseApplyCode").setValue(objs[0].purchaseCode);
                    mini.getbyName("purchaseApplyCode").setText(objs[0].purchaseCode);

//                    $("input[name=purchaseApplyUuid]").val(objs[0].uuid);

                    mini.getbyName("purchaseApplyName").setValue(objs[0].purchaseName);
                    mini.getbyName("executePeoName").setValue(objs[0].executePeoName);
//                     mini.getbyName("purchaseType").setValue(objs[0].purchaseType);
                    mini.getbyName("technologyReviewerName").setValue(objs[0].technologyReviewerName);
                  
                  /*   mini.getbyName("technologyReviewerUuid").setValue(objs[0].technologyReviewerId); */
                    $("#technologyReviewerUuid").val(objs[0].technologyReviewerId);
                    console.log( $("#technologyReviewerUuid").val());
                    loadDevice(objs[0].uuid, objs[0].sameExecutePeo);
                    sameExecutePeo = objs[0].sameExecutePeo;
                    $("input[name=requiredTime]").removeAttr("readonly");
                    initUpBtn();

                }
            }
        });
    }


    function onSupplier() {
        mini.open({
            url: "${pageContext.request.contextPath}/business/supplier/selectSupplier.jsp",
            title: "供应商库",
            width: 725,
            height: 500,
            showMaxButton: true,
            ondestroy: function (data) {
                	var grid = mini.get("supplier");
                    if (data.indexOf('[') == 0) {
                        var objs = mini.decode(data);
                        var rows = [];

                        // var columns = grid.getChanges(null, false);

                        for (var i = 0; i < objs.length; i++) {
                            var row = grid.findRow(function(row){
                                if(row.supplierId == objs[i].uuid) return true;
                            });
                            if(row!=null&&row!=undefined){
                                continue;
                            }
                            grid.addRow({name: objs[i].name,
                                contactAddress: objs[i].contactAddress,
                                corporations:objs[i].corporations,
                                contacts:objs[i].contacts,
                                phon:objs[i].mobile,
                                supplierId:objs[i].uuid});
                        }
                    }
            }
        });
    }


    function addCandidate() {
        mini.open({
            url: "${pageContext.request.contextPath}/business/supplier/supplierManager.jsp",
            title: "供应商库",
            width: 725,
            height: 500,
            showMaxButton: true,
            ondestroy: function (data) {
 
            	if (data.indexOf('[') == 0) {
                    var objs = mini.decode(data);
                    var grid = mini.get("candidate");
                    for (var i = 0; i < objs.length; i++) {
                        var row = grid.findRow(function(row){
                            if(row.supplierId == objs[i].uuid) return true;
                        });
                        if(row!=null&&row!=undefined){
                            continue;
                        }
                        grid.addRow({name: objs[i].name,
                            supplierId:objs[i].uuid});
                    }
                }
            }
        });
    }
    //添加设备
    function addDevice(gridName) {

        mini.open({
            url: '${pageContext.request.contextPath}/business/material/materialManageList.jsp',
            title: '添加设备',
            width: 725,
            height: 500,
            showMaxButton: true,
            ondestroy: function (data) {

                log(data);
                for (var i=0;i<data.sizcache;i++){
                    mini.getbyName(gridName)
                }

                if (data.indexOf('[') == 0) {
                    var objs = mini.decode(data);
                    var rows = [];
                    for (var i = 0; i < objs.length; i++) {

                        rows.push({materialCode: objs[i].materialCode,
                            materialName: objs[i].materialName,
                            categoryUuid:objs[i].categoryUuid,
                            unit:objs[i].unit,
//                             brand:objs[i].brand,
                            specModel:objs[i].specModel,
                            param:objs[i].categoryUuid});

                    }

                    if (rows.length > 0) {
                        var grid = mini.get(gridName);
                        grid.addRows(rows);
                    }
                }
            }
        });
    }

    function loadDevice(purchaseId, sameExecutePeo) {
        if(purchaseId!=null&&purchaseId!='undefined'){
            var grid1 = mini.get("deviceGrid1");
            var grid2 = mini.get("deviceGrid2");
            var grid3 = mini.get("deviceGrid3");

            grid1.on("cellbeginedit", function (e) {
                var status = "${comObj.wfStatus}";
//                if(sameExecutePeo && status == "2")
				if(sameExecutePeo)
                	e.editor.readOnly = false;
            });

            grid2.on("cellbeginedit", function (e) {
                var status = "${comObj.wfStatus}";
//                if(sameExecutePeo && status == "2")
                if(sameExecutePeo)
                    e.editor.readOnly = false;
            });

            grid3.on("cellbeginedit", function (e) {
                var status = "${comObj.wfStatus}";
//                if(sameExecutePeo && status == "2")
                if(sameExecutePeo)
                    e.editor.readOnly = false;
            });

            grid1.load({deviceType:'1',purchaseId:purchaseId});
            grid2.load({deviceType:'2',purchaseId:purchaseId});
            grid3.load({deviceType:'3',purchaseId:purchaseId});

        }
    }




    function initView() {

      var purchaseMethod= mini.getbyName("purchaseMethod").getValue()
      if(purchaseMethod =="公开招标"){

          $('#candidateManager').hide();
          $('#supplierManager').hide();
          $('#candidateManager').hide();

      } else if(purchaseMethod =="邀请招标"){
          $('#candidateManager').hide();
          $('#candidateManager').hide();
          $('#supplierManager').show();



      } else{
          $('#candidateManager').hide();
          $('#supplierManager').hide();
          $('#candidateManager').show();


      }

    }

    function afterLoad() {
        var grid1 = mini.get("deviceGrid1");
        var grid2 = mini.get("deviceGrid2");
        var grid3 = mini.get("deviceGrid3");
        var uuid = "${comObj.purchaseApplyUuid}";
        if(uuid !=null&&uuid!=''){

            grid1.load({deviceType:'1',purchaseId:uuid});
            grid2.load({deviceType:'2',purchaseId:uuid});
            grid3.load({deviceType:'3',purchaseId:uuid});
        }

        var purchaseMethod ="${comObj.purchaseMethod}";
        if(purchaseMethod !=null&&purchaseMethod!=''){

            if(purchaseMethod=='公开招标'){

                $('#candidateManager').hide();
                $('#supplierManager').hide();
                $('#candidateManager').hide();


            }else if(purchaseMethod=='邀请招标'){

                $('#candidateManager').hide();
                $('#candidateManager').hide();
                $('#supplierManager').show();

                var grid =mini.get("supplier");
                grid.load({purchaseId:uuid});

            }else{

                $('#candidateManager').hide();
                $('#supplierManager').hide();
                $('#candidateManager').show();

                var searchArr=[];
                var obj={};
                obj.name = "planId";
                obj.operator="="
                obj.dataType="text";
                obj.value="${comObj.uuid}";

                searchArr.push(obj);
                var grid =mini.get("candidate");
                grid.load({search:mini.encode(searchArr)});

            }
        }

        /*自动生成采购计划编号*/
        var purchasePlanCode ="${comObj.purchasePlanCode}";
        if(purchasePlanCode==''||purchasePlanCode=="undefined"||purchasePlanCode==null){

            $.ajax({
                url: gDir+"/business/procurement/getAmount.action",
                type:"post",
                success:function(data){
                    var result = mini.decode(data);
                    mini.unmask(document.body);
                    mini.getbyName("purchasePlanCode").setValue(result.data);
                /*     mini.getbyName("purchasePlanCode").setText(result.data); */
                },
                error:function (data) {
                    mini.unmask(document.body);
                }
            });
        }

    }

    function wfSubmitDoc(){
        SaveBefore();


      /*  var row1 = mini.get("deviceGrid1").findRows(function (row) {
            if (isNull(row.requireDate)) return true;
        });
        if (row1.length!=0) {
            alert("设备清单中设备进场时间不能为空");
            return ;
        }

        var row2 = mini.get("deviceGrid2").findRows(function (row) {
            if (isNull(row.requireDate)) return true;
        });
        if (row2.length!=0) {
            alert("备品备件清单中设备进场时间不能为空");
            return ;
        }

        var row3 = mini.get("deviceGrid3").findRows(function (row) {
            if (isNull(row.requireDate)) return true;
        });
        if (row3.length!=0) {
            alert("专有工具清单中设备进场时间不能为空");
            return ;
        } */
 

        wfSubDocStart();
    }

    function wfSaveBefore() {
        SaveBefore();
    }
    function isNull(value) {
        if (value == null || value == undefined || value.length == 0) {
            return true;
        }
        return false;
    }


    function SaveBefore() {
        var grid = mini.get("candidate");
        var rows = grid.findRows(function(row) {
			return true;
		});
        var json = mini.encode(rows);
        if (json != null && json != '' && json != 'undefined') {
            $("#canSupplier").val(json);
        }

        grid = mini.get("supplier");
        rows = grid.findRows(function(row) {
            return true;
        });
        json = JSON.stringify(rows);
        $("#invSupplier").val(json);
    }

    function onActionRenderer(e) {
        var planId = "${comObj.uuid}";
        var record = e.record;
        var materialId = record.uuid;
        return ' <a class="edit_button" href="javascript:setDeviceAttr(\'' + planId + '\', \'' + materialId + '\')" >设备分项</a>';
    }

    function setDeviceAttr(planId, materialId) {
        if(planId == null || planId == "" || planId == undefined) {
//            mini.alert("采购计划未保存!");
//            return ;
			var tempId = $("input[name=tempId]").val();
			if(tempId == null || tempId == "") {
                planId = new Date().getTime();
                $("input[name=tempId]").val(planId);
			} else {
                planId = tempId;
            }
        }
        var status = "${comObj.wfStatus}";
        // if(status<2){
        //     if(!sameExecutePeo) {
        //         mini.alert("只有当前操作人为采购执行人时才能添加设备分项!");
        //         return ;
        //     }
        // }
        mini.open({
            url: "${pageContext.request.contextPath}/business/procurement/deviceAttr.jsp?planId=" + planId + "&materialId=" + materialId+"&status="+status,
            title: "设备分项",
            width: 725,
            height: 500,
			name : "deviceAttr",
            showMaxButton: true,
            ondestroy: function (data) {
            }
        });
    }
    function removeSupplier(){
        var grid = mini.get("supplier");
        var rows = grid.getSelecteds();
        if(rows==null||rows.length==0){
            mini.alert("请先选择移除的供应商");
            return ;
        }
        mini.confirm("是否移除选定的供应商", "操作提示", function(action){
            if(action=="ok"){
                grid.removeRows(rows);
            }
        });
    }
    function removeCandidate(){
        var grid = mini.get("candidate");
        var rows = grid.getSelecteds();
        if(rows==null||rows.length==0){
            mini.alert("请先选择移除的供应商");
            return ;
        }
        mini.confirm("是否移除选定的供应商", "操作提示", function(action){
            if(action=="ok"){
                grid.removeRows(grid.getSelecteds());
            }
        });
    }
    function onCandidateCommitEdit(e) {
        var field = e.field, value = e.value, rowIndex = e.rowIndex,record=e.record;
        var grid = mini.get("candidate");
        if(field == "isWin"){
            var rows = grid.findRows(function(row){
                if(row.supplierId!=record.supplierId&&row.isWin==1) return true;
            });
            for(var i = 0,length=rows.length;i<length;i++){
                grid.updateRow(rows[i],{isWin:0});
            }
        }
    } 
</script>