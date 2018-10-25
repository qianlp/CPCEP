<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="display:none">
    <input name="prjID" value="${comObj.prjID}"/>
    <input name="deviceString1" id="deviceString1"/>
    <input name="deviceString2" id="deviceString2"/>
    <input name="deviceString3" id="deviceString3"/>
    <input name="delIds" id="delIds" value="1"/>

</div>
<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;padding-top:35px;">
    <div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
        <div class="mbox-header">
            <span class="form-title" style="height:100%;line-height:45px;">
                <i class="o-host-application" style="background-position: -0px -20px;"></i>
              采购申请
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
                        <td class="td_left">采购申请编号：</td>
                        <td class="td_right">
                            <input name="purchaseCode" class="mini-textbox" style="width:99%;" vtype="maxLength:64"
                                   required="true" value="${comObj.purchaseCode}">
                        </td>
                        <td class="td_left">采购申请名称：</td>
                        <td class="td_right">
                            <input name="purchaseName" class="mini-textbox" style="width:99%;" vtype="maxLength:64"
                                   required="true" value="${comObj.purchaseName}">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">项目编号：</td>
                        <td class="td_right">
                            <input name="projectCode" class="mini-buttonedit" style="width:99%" emptyText="请选择"
                                   value="${comObj.projectCode}" text="${comObj.projectCode}"
                                   allowInput="false" required="true" onbuttonclick="onSelectPrj()">
                        </td>
                        <td class="td_left">项目名称：</td>
                        <td class="td_right">
                            <input name="projectName" class="mini-textbox" style="width:99%;" readonly="readonly"
                                   required="true" allowInput="false" value="${comObj.projectName}">
                        </td>
                    </tr>
                    
                    <tr>   
                        <td class="td_left">采购执行人：</td>
                        <td class="td_right">
                            <input name="executePeoName" type="text"
                            property="editor" class="mini-treeselect" showRadioButton="true"
                            url="${pageContext.request.contextPath}/admin/findOrgTree.action"
                            allowInput="false" required="true" style="width:99%" title="采购执行人"
                            onnodeclick="onNodeClick" value="${comObj.executePeoName}" hasIdInput="true">
							<input type="hidden" name="executePeoId" value="${comObj.executePeoId}"/>
                        </td>
                        <td class="td_left">技术评标人：</td>
                        <td class="td_right">
                            <input name="technologyReviewerName" type="text"
                                   property="editor" class="mini-treeselect" showRadioButton="true"
                                   url="${pageContext.request.contextPath}/admin/findOrgTree.action"
                                   allowInput="false" required="true" style="width:99%" title="采购执行人"
                                   onnodeclick="onNodeClick" value="${comObj.technologyReviewerName}">
							<input type="hidden" name="technologyReviewerId" value="${comObj.technologyReviewerId}"/>
                        </td>
                     </tr>
                   <!-- 采购类型字段暂时隐藏 -->
                   <!--  
                     <tr>    
                        <td class="td_left">采购类型：</td>
                        <td class="td_right">
                            <input name="purchaseType" type="text"
                                   property="editor" class="mini-combobox" value="${comObj.purchaseType}" showRadioButton="true"
                                   url="${pageContext.request.contextPath}/business/procurement/queryInfoTypeList.action"
                                   allowInput="false" required="true" style="width:99%"  onnodeclick="onNodeClick" title="采购类型">
                        </td>
                    </tr>
                   -->
                    <tr>
                        <td class="td_left">采购内容描述：</td>
                        <td class="td_right" colspan="6" style="padding:3px;">
							<input name="remark" value="${comObj.remark}" class="mini-textarea" style="width:99.6%" title="备注">
						</td>
                    </tr>    
                    
                </table>
            </div>
        </fieldset>
        <fieldset style="margin: 10px;">
            <legend style="margin-left:10px;"><b>设备清单</b></legend>
            <div class="mini-toolbar" style="padding:5px;border-bottom-width:0;border:0px;">
                   <div class="mini-button" iconCls="icon-add" plain="true" onclick="addDevice('deviceGrid1')">新增</div>
                   <div class="mini-button" iconCls="icon-remove" plain="true" onclick="remove('deviceGrid1')">删除</div>
            </div>
            <div id="deviceGrid1" name="deviceGrid1" class="mini-datagrid" style="width:100%;height:200px;display: grid"
                 url="${pageContext.request.contextPath}/business/procurement/loadMaterialForPage.action"
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
                        <div field="deviceParam" width="80" headerAlign="center" align="center" renderer="onActionRenderer">参数</div>
                        <div filed="paramsJson" visible="false">参数修改</div>
                        <div filed="haveEditParam" visible="false">参数修改标志</div>

                        <%--<div field="category" width="80" headerAlign="center" align="center">设备分项</div>--%>
                        <div field="num" width="80" headerAlign="center" align="center" >数量
                            <input  property="editor" class="mini-textbox" style="width:99%;height: 99%" value="0">
                        </div>
                        <%--<div field="requiredTime" width="80" dateFormat="yyyy-MM-dd">要求进场时间--%>
                            <%--<input property="editor" class="mini-datepicker" style="width:99%;height: 99%"/>--%>
                        <%--</div>--%>
                        <div field="remark" width="80" headerAlign="center" align="center" >备注
                            <input property="editor" class="mini-textbox" style="width:99%;height: 99%"/>
                        </div>
                        <div field="materialUUID" visible="false"></div>


                    </div>
            </div>
	
       </fieldset>  
       
            <fieldset style="margin: 10px;">
            <legend style="margin-left:10px;"><b>备品备件清单</b></legend>
              <div class="mini-toolbar" style="padding:5px;border-bottom-width:0;border:0px;">
                   <div class="mini-button" iconCls="icon-add" plain="true" onclick="addDevice('deviceGrid2')">新增</div>
                   <div class="mini-button" iconCls="icon-remove" plain="true" onclick="remove('deviceGrid2')">删除</div>
            </div>
            <div id="deviceGrid2" class="mini-datagrid" style="width:100%;height:200px;display: grid"
                 url="${pageContext.request.contextPath}/business/procurement/loadMaterialForPage.action"
                 allowResize="true" pageSize="20"
                     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
                     editNextOnEnterKey="true" editNextRowCell="true" allowCellwrap="true">
                <div property="columns">
                    <!-- 序号, 系统描述, 材料设备名称,单位, 品牌,规格型号,参数,设备分项,数量,要求进场时间,-->

                    <div field="uuid" visible="false"></div>
                    <div field="curDocId" visible="false"></div>
                    <div type="indexcolumn" align="center" headerAlign="center" >序号</div>
                    <div field="materialCode" width="80" headerAlign="center" align="center" visible="false">材料设备Code</div>
                    <div field="materialName" width="80" headerAlign="center" align="center">材料设备名称</div>
                    <div field='categoryUuid' visible='false' width='100'  headerAlign='center' align='center'>分类ID</div>
                    <div field="unit" width="80" headerAlign="center" align="center">单位</div>
<!--                     <div field="brand" width="80" headerAlign="center" align="center">品牌</div> -->
                    <div field="specModel" width="80" headerAlign="center" align="center">规格型号</div>
                    <%--<div field="categoryUuid" name="param" width="80" headerAlign="center" align="center">参数</div>--%>
                    <%--<div field="category" width="80" headerAlign="center" align="center">设备分项</div>--%>
                    <div field="num" width="80" headerAlign="center" align="center" >数量
                        <input  property="editor" class="mini-textbox"  style="width:99%;height: 99%"
                                value="0">
                    </div>
                    <%--<div field="requiredTime" width="80" dateFormat="yyyy-MM-dd">要求进场时间--%>
                        <%--<input property="editor" class="mini-datepicker" style="width:99%;height: 99%"/>--%>
                    <%--</div>--%>
                    <div field="remark" width="80" headerAlign="center" align="center" >备注
                        <input property="editor" class="mini-textbox" style="width:99%;height: 99%"/>
                    </div>
                    <div field="materialUUID" visible="false"></div>


                </div>
            </div>
	
       </fieldset>   
       
       
            <fieldset style="margin: 10px;">
            <legend style="margin-left:10px;"><b>专有工具清单</b></legend>
            
            <div class="mini-toolbar" style="padding:5px;border-bottom-width:0;border:0px;">
                   <div class="mini-button" iconCls="icon-add" plain="true" onclick="addDevice('deviceGrid3')">新增</div>
                   <div class="mini-button" iconCls="icon-remove" plain="true" onclick="remove('deviceGrid3')">删除</div>
            </div>
            <div id="deviceGrid3" class="mini-datagrid" style="width:100%;height:200px;display: grid"
                 url="${pageContext.request.contextPath}/business/procurement/loadMaterialForPage.action"
                 allowResize="true" pageSize="20"
                     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
                     editNextOnEnterKey="true" editNextRowCell="true" allowCellwrap="true">
                <div property="columns">
                    <!-- 序号, 系统描述, 材料设备名称,单位, 品牌,规格型号,参数,设备分项,数量,要求进场时间,-->

                    <div field="uuid" visible="false"></div>
                    <div field="curDocId" visible="false"></div>
                    <div type="indexcolumn" headerAlign="center" align="center">序号</div>
                    <div field="materialCode" width="80" headerAlign="center" align="center" visible="false">材料设备Code</div>
                    <div field="materialName" width="80" headerAlign="center" align="center">材料设备名称</div>
                    <div field='categoryUuid' visible='false' width='100'  headerAlign='center' align='center'>分类ID</div>
                    <div field="unit" width="80" headerAlign="center" align="center">单位</div>
<!--                     <div field="brand" width="80" headerAlign="center" align="center">品牌</div> -->
                    <div field="specModel" width="80" headerAlign="center" align="center">规格型号</div>
                    <%--<div field="categoryUuid" name="param" width="80" headerAlign="center" align="center">参数</div>--%>
                    <%--<div field="category" width="80" headerAlign="center" align="center">设备分项</div>--%>
                    <div field="num" width="80" headerAlign="center" align="center" aria-required="true">数量
                        <input  property="editor" class="mini-textbox"  style="width:99%;height: 99%"
                                value="0">
                    </div>
                    <%--<div field="requiredTime" width="80" dateFormat="yyyy-MM-dd">要求进场时间--%>
                        <%--<input property="editor" class="mini-datepicker" style="width:99%;height: 99%"/>--%>
                    <%--</div>--%>
                    <div field="remark" width="80" headerAlign="center" align="center" >备注
                        <input property="editor" class="mini-textbox" style="width:99%;height: 99%"/>
                    </div>

                    <div field="materialUUID" visible="false"></div>

                </div>
            </div>
	
       </fieldset>

    </div>

</div>
<script >


    var gDir="${pageContext.request.contextPath}";
    mini.parse();
    var grid = mini.get("deviceGrid1");

        //自定义参数项列
    grid.on("drawcell",function (e) {
            var record =e.record,
                column = e.column;
            //action列，超连接操作按钮
            /*if (column.name == "action") {
                e.cellStyle = "text-align:center";
                e.cellHtml = '<a href="javascript:edit(\'' + record.uuid + '\')">点击查看</a>';
            }*/
            if (column.name == "param") {
                e.cellStyle = "text-align:center";
                e.cellHtml = '<a href="javascript:onSelectPrj()">参数项</a>';
            }
    });

    function afterLoad() {
        var grid1 = mini.get("deviceGrid1");
        var grid2 = mini.get("deviceGrid2");
        var grid3 = mini.get("deviceGrid3");
        var uuid = "${comObj.uuid}";
        if(uuid !=null&&uuid!=''){

            grid1.load({deviceType:'1',purchaseId:uuid});
            grid2.load({deviceType:'2',purchaseId:uuid});
            grid3.load({deviceType:'3',purchaseId:uuid});
        }
    }

    function onNodeClick(e) {
        var node = e.node;
        console.log(node);
        console.log(e.sender);
        if (node.type != 'user') {
            e.sender.setValue('');
            e.sender.setText('');
            if(e.sender.name == "executePeoName") {
                $("input[name=executePeoId]").val('');
            } else if(e.sender.name == "technologyReviewerName") {
                $("input[name=technologyReviewerId]").val('');
			}
        } else {
            e.sender.setValue(node.text);
            e.sender.setText(node.text);
            if(e.sender.name == "executePeoName") {
                $("input[name=executePeoId]").val(node.id);
			} else if(e.sender.name == "technologyReviewerName") {
                $("input[name=technologyReviewerId]").val(node.id);
			}
        }
    }

    function onSelectPrj() {
        mini.open({
            url: "${pageContext.request.contextPath}/admin/findExhibitById.action?randomId=eae7cce4-d052-4c4b-ac26-2ff4af7687ee",
            title: "项目列表",
            width: 725,
            height: 500,
            showMaxButton: true,
            ondestroy: function (data) {
                if (data.indexOf("[") == 0) {
                    var objs = mini.decode(data);
                    console.log("objs:", objs[0]);
                    $("[name=prjID]").val(objs[0].uuid);
                    mini.getbyName("projectCode").setValue(objs[0].projectCode);
                    mini.getbyName("projectCode").setText(objs[0].projectCode);
                    mini.getbyName("projectName").setValue(objs[0].projectName);
                    initUpBtn();
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

                console.log(data);
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
                            materialUUID:objs[i].uuid,
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

    function remove(gridName) {
            var  deviceGrid= mini.get(gridName);
            var rows = deviceGrid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除此记录？")) {
                    deviceGrid.removeRows(rows, true);
                    var delIds =$("#delIds")[0].value;
                    for(var i=0;i<rows.length;i++){
                        var uuid = rows[i].uuid;
                        if(uuid!=undefined && uuid !="")
                            delIds =delIds +","+rows[i].uuid;
                    }
                    $("#delIds").val(delIds);

                }
            }else{
                mini.alert("没有选择记录");
            }
    }

    function wfSubmitDoc(){
        SaveBefore();
        var row1 = mini.get("deviceGrid1").findRows(function (row) {
            if (isNull(row.num)) return true;
        });
        if (row1.length!=0) {
            alert("设备清单中设备数量不能为空");
            return ;
        }

        var row2 = mini.get("deviceGrid2").findRows(function (row) {
            if (isNull(row.num)) return true;
        });
        if (row2.length!=0) {
            alert("备品备件清单中设备数量不能为空");
            return ;
        }


        var row3 = mini.get("deviceGrid3").findRows(function (row) {
            if (isNull(row.num)) return true;
        });
        if (row3.length!=0) {
            alert("专有工具清单中设备数量不能为空");
            return ;
        }



        wfSubDocStart();
    }
    function wfSaveDoc() {
        /*不做任何操作保存文档*/
        if(confirm("确定保存文档吗？")){
            try{gForm.wfFlowLogXML.value=XML2String(gWFLogXML)}catch(e){};
            //提交前执行事件
            SaveBefore();
            var row1 = mini.get("deviceGrid1").findRows(function (row) {
                if (isNull(row.num)) return true;
            });
            if (row1.length!=0) {
                alert("设备清单中设备数量不能为空");
                return ;
            }

            var row2 = mini.get("deviceGrid2").findRows(function (row) {
                if (isNull(row.num)) return true;
            });
            if (row2.length!=0) {
                alert("备品备件清单中设备数量不能为空");
                return ;
            }


            var row3 = mini.get("deviceGrid3").findRows(function (row) {
                if (isNull(row.num)) return true;
            });
            if (row3.length!=0) {
                alert("专有工具清单中设备数量不能为空");
                return ;
            }
            wfSaveBefore();
            fnResumeDisabled(); //恢复部分域的失效状态，以保证保存时值不会变为空。
            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: '数据提交中...' //数据提交中...
            });
            setTimeout(function(){
                gForm.submit();
            },500)
        }
    }


    function isNull(value) {
        if (value == null || value == undefined || value.length == 0) {
            return true;
        }
        return false;
    }



    function onCellValidation(e) {
        if (e.field == "num") {
            var value=e.value
            if (value =='undefined'||value != null && value != '') {
                e.isValid = false;
                e.errorText = "数量不能为空";
            }
        }
    }
    function SaveBefore() {

        mini.get("deviceGrid1").commitEdit();
        var data = mini.get("deviceGrid1").getData();
        var json = mini.encode(data);
        if (json != null && json != '' && json != 'undefined') {
            $("#deviceString1").val(json);
        } else {
            mini.alert("请录入设备清单");
            return false;
        }


        mini.get("deviceGrid2").commitEdit();
        var data = mini.get("deviceGrid2").getData();
        var json = mini.encode(data);
        if (json != null && json != '' && json != 'undefined') {
            $("#deviceString2").val(json);
        }
        mini.get("deviceGrid3").commitEdit();
        var data = mini.get("deviceGrid3").getData();
        var json = mini.encode(data);
        if (json != null && json != '' && json != 'undefined') {
            $("#deviceString3").val(json);
        }

    }


    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var categoryUuid = record.categoryUuid;
        var rowIndex = e.rowIndex;
        var s = ' <a class="edit_button" href="javascript:setDeviceParam(' + rowIndex + ')" >参数</a>';
        return s;
    }

    function setDeviceParam(rowIndex) {
        var deviceGrid1 = mini.get("deviceGrid1");
        var row = deviceGrid1.getRow(rowIndex);
        console.log(row);
        mini.open({
            url: "${pageContext.request.contextPath}/business/procurement/deviceParam.jsp",
            title: "设备参数",
            width: 725,
            height: 500,
            showMaxButton: true,
            onload: function () {       //弹出页面加载完成
                var iframe = this.getIFrameEl();
               // var data = {categoryUuid: row.categoryUuid ,pmUuid:row.uuid,paramsJson:row.paramsJson,gIsRead:gIsRead};
               //0809 取子项的参数
               var data = {categoryUuid: row.materialUUID ,pmUuid:row.uuid,paramsJson:row.paramsJson,gIsRead:gIsRead};
                //调用弹出页面方法进行初始化
                iframe.contentWindow.setData(data);

            },
            ondestroy: function (data) {
                if(data!='close'){
                    var dJson = mini.decode(data);
                    console.log(dJson);
                    deviceGrid1.updateRow(row,{paramsJson:dJson,haveEditParam:"1"});
                }
            }
        });
    }


</script>

