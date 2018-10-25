
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="display:none">
    <input name="prjID" value="${comObj.prjID}"/>
    <input name="subjectList" id="subjectList"/>
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;padding-top:35px;">
    <div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
        <div class="mbox-header">
            <span class="form-title" style="height:100%;line-height:45px;">
                <i class="o-host-application" style="background-position: -0px -20px;"></i>
              采购计划变更
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
                        <td class="td_left">项目编号：</td>
                        <td class="td_right">
                            <input name="projectNo" class="mini-buttonedit" style="width:99%" emptyText="请选择"
                                   value="${comObj.projectNo}" text="${comObj.projectNo}"
                                   allowInput="false" required="true" onbuttonclick="onSelectPrj">
                        </td>
                        <td class="td_left">项目名称：</td>
                        <td class="td_right">
                            <input name="projectName" class="mini-textbox" style="width:99%;" readonly="readonly"
                                   required="true" allowInput="false" value="${comObj.projectName}">
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="td_left">采购变更编号：</td>
                        <td class="td_right">
                            <input name="projectNo" class="mini-buttonedit" style="width:99%" emptyText="请选择"
                                   value="${comObj.projectNo}" text="${comObj.projectNo}"
                                   allowInput="false" required="true" onbuttonclick="onSelectPrj">
                        </td>
                        <td class="td_left">采购变更名称：</td>
                        <td class="td_right">
                            <input name="planName" class="mini-textbox" style="width:99%;" vtype="maxLength:64"
                                   required="true" value="${comObj.planName}">
                        </td>
                    </tr> 
                    
                       <tr>
                        <td class="td_left">采购申请编号：</td>
                        <td class="td_right">
                            <input name="projectNo" class="mini-buttonedit" style="width:99%" emptyText=""
                                   value="${comObj.projectNo}" text="${comObj.projectNo}"
                                   allowInput="false" required="true" >
                        </td>
                        <td class="td_left">采购申请名称：</td>
                        <td class="td_right">
                            <input name="planName" class="mini-textbox" style="width:99%;" vtype="maxLength:64"
                                   required="true" value="${comObj.planName}">
                        </td>
                    </tr> 
                    
                
                     <tr>    
                      </td>
                        <td class="td_left">采购方式：</td>
                        <td class="td_right">
                            <input name="projectNo" class="mini-buttonedit" style="width:99%" emptyText="请选择"
                                   value="${comObj.projectNo}" text="${comObj.projectNo}"
                                   allowInput="false" required="true" onbuttonclick="onSelectExecute">
                        </td>
                        
                        <td class="td_left">开标时间：</td>
                        <td class="td_right">
                            <input name="projectNo" class="mini-buttonedit" style="width:99%" emptyText="请选择"
                                   value="${comObj.projectNo}" text="${comObj.projectNo}"
                                   allowInput="false" required="true" onbuttonclick="onSelectExecute">
                        </td>
                    </tr>
                    
                     <tr>  
                      <td class="td_left">采购执行人：</td>
                        <td class="td_right">
                            <input name="projectNo" class="mini-buttonedit" style="width:99%" emptyText="请选择"
                                   value="${comObj.projectNo}" text="${comObj.projectNo}"
                                   allowInput="false" required="true" onbuttonclick="onSelectExecute">
                        </td>
                          
                        <td class="td_left">评标人：</td>
                        <td class="td_right">
                            <input name="projectNo" class="mini-buttonedit" style="width:99%" emptyText="请选择"
                                   value="${comObj.projectNo}" text="${comObj.projectNo}"
                                   allowInput="false" required="true" onbuttonclick="onSelectExecute">
                        </td>
                    </tr>
                    
                </table> 
            </div>
        </fieldset>
        
             <fieldset style="margin: 10px;">
            <legend style="margin-left:10px;"><b>邀请供应商</b></legend>
             <div class="mini-toolbar" style="padding:5px;border-bottom-width:0;border:0px;">
                   <div class="mini-button" iconCls="icon-add" plain="true" onclick="RefreshGridData()">添加供应商</div>
                   <div class="mini-button" iconCls="icon-remove" plain="true" onclick="RefreshGridData()">删除</div>            
            </div>
            <div id="deviceGrid" class="mini-datagrid" style="width:100%;height:200px;"
                     allowResize="true" pageSize="20"
                     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
                     editNextOnEnterKey="true" editNextRowCell="true" allowCellwrap="true">
                    <div property="columns">
                      <!-- 选择,序号,供应商名称,所供产品名称,通讯地址,法人代表,联系人,联系方式,备注,-->         
                       <div field="uuid" visible="false"></div>
                        <div field="curDocId" visible="false"></div>
                         <div type="checkcolumn" ></div>
                        <div type="indexcolumn" align="center">序号</div>
                        <div field="content" width="80" headerAlign="center" align="center">供应商名称</div>
                        <div field="content" width="80" headerAlign="center" align="center">所供产品名称</div>
                        <div field="content" width="80" headerAlign="center" align="center">法人代表</div>
                        <div field="content" width="80" headerAlign="center" align="center">联系人</div>
                        <div field="content" width="80" headerAlign="center" align="center">联系方式</div>
                        <div field="content" width="80" headerAlign="center" align="center">备注</div>
                    </div>
            </div>
       </fieldset>      
    </div>
</div>
<script type="text/javascript">

</script>

