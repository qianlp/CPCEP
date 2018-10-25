<%@ page import="htos.coresys.entity.User" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String  name = request.getParameter("name");   //获取url中的参数值
    String  code = request.getParameter("code");   //获取url中的参数值
    String  verson = request.getParameter("name");   //获取url中的参数值
    String uuid =request.getParameter("uuid");

%>


<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;" >
    <div class="widget-container fluid-height col-md-7-k" style="height:100%;border-radius:4px;">
        <div class="mbox-header">
        </div>
        <fieldset style="margin: 10px;">
            <div class="mbox-body" style="height:60%;padding:10px;">

                <table style="width:95%;margin:5px;" id="tab">
                    <tr>
                        <td class="td_left">招标编号：</td>
                        <td class="td_right">
                            <div  class="mini-textbox" name="bidCode"  style="width:99%;" type="text" readonly="readonly"
                                  required="true" value="${param.name}"></div>
                        </td>
                        <td class="td_left">招标名称：</td>
                        <td class="td_right">
                            <div class="mini-textbox" name="bidName"  style="width:99%;" vtype="maxLength:64" readonly="readonly"  type="text"
                                   required="true" value="<%=code%>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">招标文件版本号：</td>
                        <td class="td_right">
                            <div class="mini-textbox" name="version"  style="width:99%;" vtype="maxLength:64" readonly="readonly"  type="text"
                                   required="true" value="${param.version}"/>
                        </td>
                        <td class="td_left">问题类型：</td>
                        <td class="td_right">
                            <input  name="issueType" class="mini-textbox" style="width:99%;" vtype="maxLength:64"
                                   required="true" value="${comObj.issueType}">
                        </td>
                    </tr>
                    <tr>


                        <td class="td_left">供应商名称：</td>
                        <td class="td_right">
                            <input class="mini-textbox" style="width:99%;" readonly="readonly"  type="text"
                                   required="true"
                                   name="supName"
                                   value="${session.user.userName}">
                        </td>
                        <td class="td_left">联系方式：</td>
                        <td class="td_right">
                            <input name="contact" class="mini-textbox" style="width:99%;"
                                   required="true" allowInput="true" value="${comObj.contact}">
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">问题描述：</td>
                        <td class="td_right" colspan="6" style="padding:3px;">
                            <input name="remark" value="${comObj.remark}" class="mini-textarea" style="width:99.6%" title="备注">
                        </td>

                    </tr>

                    <tr>

                        <td class="td_left"  style="display:none">
                            <input name="bidFileUUID" id="bidFileUUID" value="${bidFileUUID}"/>

                        </td>

                        <td class="td_right" style="display:none">
                            <input class="mini-textbox" style="width:99%;" readonly="readonly"  type="text"
                                   required="true"
                                   name="supUUID"
                                   value="${session.user.userNo}">
                        </td>
                    </tr>

                </table>
            </div>

        </fieldset>

        <div align="center" class="centerBtn">
            <button type="button" onclick="save();" name="button"  style="height: 35px;width:70px;background-color: #1d98dc;color: white" id="nextBtn"><span>提交</span></button>
        </div>
    </div>

</div>
<script >

    function save() {
        var form = new mini.Form("#FormMode");
        var data = form.getData();      //获取表单多个控件的数据
        var json = mini.encode(data);   //序列化成JSON
        $.ajax({
            url:"${pageContext.request.contextPath}/business/biddingFile/saveUpdateBidFileQue.action",
            type: "post",
            data: { submitData: json },
            success: function (text) {
                mini.alert("保存成功！");
                parent.closeMini();
                mini.unmask(document.body);
            },

        });
    }
</script>

