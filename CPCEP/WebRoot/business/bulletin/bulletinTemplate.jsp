<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>

<script src="../js/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="../js/ckeditor/config.js" type="text/javascript"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
    var gForm = document.forms[0];
    var gCkEditor = null;

    function saveFun(){
        gForm.content.value = gCkEditor.getData();
    }
    //保存方法
    function toSave() {
        saveFun();
        var form = document.forms[0];
        form.status.value="1";//保存
        form.submit();
    }
    function afterLoad(){
        if($("[name=uuid]").val()==""){
            mini.getbyName("whp").setValue("${sessionScope.user.userName}");
        }
        initCkEditor();
    };

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
</script>
<style>
    #gTab .mini-panel-border {
        border: 0px;
    }
</style>
<div style="display:none">

    <input name="status" style="display: none;"/>
    <textarea  name="content" id="content" >${comObj.content}</textarea>
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;">
    <div class="widget-container fluid-height col-md-7-k"
         style="height:auto;border-radius:4px;">

        <div class="mbox-header">
				<span class="form-title" style="height:100%;line-height:45px;">
                    公告内容
				</span>
        </div>

        <div class="mbox-body" style="height:100%;padding:10px;">
            <fieldset style="margin: 10px;">
                <legend style="margin-left:10px;"><b>基本信息</b></legend>

                <table style="width:95%;margin:5px;" id="tab">

                    <tr>
                        <td class="td_left">维护人：</td>
                        <td class="td_right">
                            <input name="whp" class="mini-textbox" style="width:99%;"
                                   allowInput="false"  value="${comObj.createBy}"/>

                        </td>
                        <td class="td_left">维护时间：</td>
                        <td class="td_right">
                            <input name="createDatea" allowInput="false"
                                   <s:if test='comObj!=null'>value="${fn:substring(comObj.createDate,0,19)}"</s:if>
                                   <s:else>value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>"</s:else>
                                   class="mini-textbox" style="width: 99%;" title="编制时间"/>
                        </td>
                        <td class="td_left">公告模板名称：</td>
                        <td class="td_right">
                            <input name ="name" class="mini-textbox" style="width:99%;"  value="${comObj.name}"
                                   required="true" />
                        </td>
                    </tr>
                </table>
            </fieldset>

        </div>
    </div>
    <br />
    <div class="widget-container fluid-height col-md-7-k"
         style="height:auto;border-radius:4px;">
        <div class="mbox-header">
			<span class="form-title"
                  style="height:100%;line-height:45px;text-align:center;"> 模板内容</span>
        </div>
        <div class="mbox-body" id="mbox-body" style="padding:5px;width:100%">
            <table cellspacing="1" cellpadding="1" id="tab"
                   style="width:100%;margin:0 auto;">
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
    </div>
</div>