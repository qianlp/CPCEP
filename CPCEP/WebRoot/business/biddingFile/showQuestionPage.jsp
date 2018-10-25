<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type='text/css' href="../../css/form/loaders.css"/>
<link rel="stylesheet" type='text/css' href="../../css/form/bootstrap-theme.css"/>
<link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css"/>
<link rel="stylesheet" type='text/css' href="../../css/form/form.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
<script src="${pageContext.request.contextPath}/layui/layui.js"></script>

<%

    String bidFileUUID = request.getParameter("bidFileUUID");   //获取url中的参数值
    String supUUID = request.getParameter("supUUID");   //获取url中的参数值
%>


<%@include file="../../resource.jsp" %>
<script src="../../js/form/language_ZN.js" type="text/javascript"></script>

<script src="../../js/form/wf-mini.js" type="text/javascript"></script>

<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;padding-top:35px;">
    <div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;padding-bottom: 10px">

        <fieldset style="margin: 10px;height: auto">
            <legend style="margin-left:10px;"><b>招标质疑</b></legend>
            <div class="mbox-body" style="height:auto;padding:10px;">

                <table style="width:95%;margin:5px;" id="tab">
                    <tr>
                        <td class="td_left">招标编号：</td>
                        <td class="td_right">
                            <input name="bidCode" class="mini-textbox" style="width:99%;" readonly="readonly" value="${bidCode}"
                                   >
                        </td>
                        <td class="td_left">招标名称：</td>
                        <td class="td_right">
                            <input name="bidName" class="mini-textbox" style="width:99%;" readonly="readonly" value="${bidName}"
                                    >
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">招标文件版本号：</td>
                        <td class="td_right">
                            <input name="version" class="mini-textbox" style="width:99%;" readonly="readonly" value="${version}"
                                   >
                        </td>
                        <td class="td_left">问题类型：</td>

                        <td class="td_right">
                            <input name="issueType" class="mini-textbox" readonly="readonly" value="${issueType}"
                                   data="[{id:'技术问题',text:'技术问题'},{id:'商务问题',text:'商务问题'},{id:'其他',text:'其他'}]"
                                   style="width: 99%;" title="用户名"  />
                        </td>
                    </tr>
                    <tr>


                        <td class="td_left">供应商名称：</td>
                        <td class="td_right">
                            <input class="mini-textbox" style="width:99%;" readonly="readonly" value="${supName}"
                                   name="supName">
                        </td>
                        <td class="td_left">联系方式：</td>

                        <td class="td_right">
                            <input name="contact" class="mini-textbox" style="width: 99%;" title="" value="${contact}" readonly="readonly"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">问题描述：</td>
                        <td class="td_right" colspan="6" style="padding:3px;">
                            <input name="remark" class="mini-textarea" style="width:99.6%" value="${remark}" readonly="readonly"
                                   title="备注">
                        </td>

                    </tr>

                    <tr>
                        <td class="td_left">附件：</td>
                        <td class="td_right">

                            <c:if test="${ not empty questionFileUUID }">
                                <a style="color: blue;" target="view_window"  href="${pageContext.request.contextPath}/business/download.action?id=${questionFileUUID}" >质疑文件</a>
                            </c:if>

                        </td>
                        <td class="td_left"></td>
                        <td class="td_right"></td>
                    </tr>

                    <tr>
                        <td class="td_right" style="display:none">
                            <input name="bidFileUUID" id="bidFileUUID" value="${bidFileUUID}" class="mini-textarea" style="width:99.6%" title="备注"/>

                        </td>
                    </tr>


                    <tr>
                        <td class="td_right" style="display:none">
                            <input name="uuid" id="uuid" value="${uuid}" class="mini-textarea" style="width:99.6%" title="备注"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_right" style="display:none">
                            <input name="questionFileUUID" id="questionFileUUID" value="${questionFileUUID}" class="mini-textarea" style="width:99.6%" title="备注"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_right" style="display:none">
                            <input name="clarifyFileUUID" id="clarifyFileUUID" value="${questionFileUUID}" class="mini-textarea" style="width:99.6%" title="备注"/>

                        </td>
                    </tr>

                    <tr>
                        <td class="td_right" style="display:none">
                            <input name="supUUID" id="supUUID" value="${supUUID}" class="mini-textarea" style="width:99.6%" title="备注"/>
                        </td>
                    </tr>

                </table>
            </div>
        </fieldset>

        <div style="margin: 10px">
            <fieldset style="margin: 10px;">
                <legend style="margin-left:10px;"><b>澄清反馈</b></legend>
                <table style="width:95%;margin:5px;" id="tab2">

                    <tr>
                        <td class="td_left">反馈：</td>
                        <td class="td_right" colspan="6" style="padding:3px;">
                            <input name="feedBack" class="mini-textarea" style="width:99.6%"    readonly="readonly" value="${feedBack}"
                                   title="备注">
                        </td>

                    </tr>

                    <tr>
                        <td class="td_left">附件：</td>
                        <td class="td_right">
                            <c:if test="${ not empty feedBackFileUUID }">
                                <a style="color: blue;" target="view_window"  href="${pageContext.request.contextPath}/business/download.action?id=${feedBackFileUUID}" >澄清文件</a>
                            </c:if>
                        </td>

                        <td class="td_left"></td>
                        <td class="td_right"></td>
                    </tr>
                </table>

            </fieldset>

        </div>

    </div>
</div>
<script>

</script>

