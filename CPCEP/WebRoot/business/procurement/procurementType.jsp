
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="display:none">
    <input name="prjID" value="${comObj.prjID}"/>
    <input name="subjectList" id="subjectList"/>
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:500;height:100%;margin:auto;padding-top:35px;">
    <div class="widget-container fluid-height col-md-7-k" style="height:100%;border-radius:4px;">
        <div class="mbox-header" style="height:100%">
            <span class="form-title" style="height:100%;line-height:45px;">
                <i class="o-host-application" style="background-position: -0px -20px;"></i>
              采购类型维护
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
                        <td class="td_left">采购类型：</td>
                        <td class="td_right">
                            <input class="mini-textbox" style="width:99%;" 
                                   required="true" />
                        </td>
                     </tr>
                     <tr>
                        <td class="td_left">备注：</td>
                        <td class="td_right" colspan="6" style="padding:3px;">
							<input name="problemDescription" value="${comObj.problemDescription}" class="mini-textarea" style="width:99.6%" title="备注">
						</td>
                    </tr>    
                </table> 
            </div>
        </fieldset>
    </div>
 </div>
</html>