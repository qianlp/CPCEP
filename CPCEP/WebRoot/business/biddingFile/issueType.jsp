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
        if($("[name=uuid]").val()==""){
            mini.getbyName("whp").setValue("${sessionScope.user.userName}");
        }
    };

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
					问题类型维护
				</span>
        </div>

        <div class="mbox-body" style="height:100%;padding:10px;">
            <fieldset style="margin: 10px;">
                <legend style="margin-left:10px;"><b>基本信息</b></legend>

                <table style="width:95%;margin:5px;" id="tab">

                    <tr>
                        <td class="td_left">制单人：</td>
                        <td class="td_right">
                            <input name="whp" class="mini-textbox" style="width:99%;"
                                   allowInput="false"  value="${comObj.createBy}"/>

                        </td>
                        <td class="td_left">制单时间：</td>
                        <td class="td_right">
                            <input name="createDatea" allowInput="false"
                                   <s:if test='comObj!=null'>value="${fn:substring(comObj.createDate,0,19)}"</s:if>
                                   <s:else>value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>"</s:else>
                                   class="mini-textbox" style="width: 99%;" title="编制时间"/>
                        </td>
                        <td class="td_left">问题类型：</td>
                        <td class="td_right">
                            <input name ="issueType" class="mini-textbox" style="width:99%;"  value="${comObj.issueType}"
                                   required="true" />
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

        </div>
    </div>
</div>