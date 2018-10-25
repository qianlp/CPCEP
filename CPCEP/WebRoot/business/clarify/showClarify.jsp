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


<c:if test="${ empty feedBack }">
    <div class='mini-toolbar'
         style='padding:0;border-bottom-width:0;border:0px;'>

        <table style='width:100%;height: 35px' >
            <tr>
                <td style="width:100%;" id="tools">
                    <a class="mini-button" iconCls="icon-save" onclick="savePage()" plain="true">保存</a>
                </td>
            </tr>
        </table>
    </div>
</c:if>

<div class="col-md-7" name="FormMode" id="FormMode"
     style="width:100%;margin:auto;float:none;">
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

                            <c:if test="${ not empty techEvalClarify.clarifyFileUUID }">
                                <a style="color: blue;" target="view_window" href="${pageContext.request.contextPath}/business/download.action?id=${techEvalClarify.clarifyFileUUID}" >质疑文件</a>
                            </c:if>
                            <input name="clarifyFileUUID" style="display: none;" allowInput="false" readonly="readonly" value="${techEvalClarify.clarifyFileUUID}"
                                   class="mini-textbox"
                                   renderer ="getQuestionFile">
                        </td>
                        <td class="td_left"></td>
                        <td class="td_right"></td>
                    </tr>



                    <tr>
                        <td class="td_right" style="display:none">
                            <input name="uuid" id="uuid" value="${uuid}" class="mini-textarea" style="width:99.6%" title="备注"/>
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
                            <input name="feedBack" class="mini-textarea" style="width:99.6%"   required="required" value="${feedBack}"
                                   title="备注">
                        </td>

                    </tr>

                    <tr>
                        <td class="td_left">附件：</td>
                        <td class="td_right">
                            <c:if test="${ not empty feedBack }">
                                <c:if test="${ not empty techEvalClarify.feedBackFileUUID }">
                                    <a style="color: blue;" target="view_window"  href="${pageContext.request.contextPath}/business/download.action?id=${techEvalClarify.feedBackFileUUID}" >澄清文件</a>
                                </c:if>
                            </c:if>

                            <c:if test="${ empty feedBack }">
                                <div id="upload">
                                    <a id="file_1"></a>
                                    <input type='hidden' name='feedBackFileUUID' value=''/>
                                    <button type="button" class="layui-btn layui-btn-sm" id="upload_1">
                                        <i class="layui-icon">&#xe67c;</i>点击上传
                                    </button>
                                </div>
                            </c:if>

                        </td>
                    </tr>
                </table>

            </fieldset>

        </div>

    </div>
</div>
<script>
    $(function () {
        layui.use('upload', function () {
            var upload = layui.upload;
            var uploadInst = upload.render({
                elem: "#upload_1"
                , accept: "file"
                , data: {
                    type: 4
                }
                , url: "${pageContext.request.contextPath}/business/upload.action" //上传接口
                , done: function (res) {
                    //上传完毕回调
                    var result = mini.decode(res);
                    $("#file_1").html(result.data.fileName);
                    $("input[name=feedBackFileUUID]").val(result.data.uuid);
                    $("#file_1").attr("href","${pageContext.request.contextPath}/business/download.action?id="+result.data.uuid);
                }
                , error: function () {
                    //请求异常回调
                    alert("上传失败")
                }
            });
        });
    });

    function savePage() {
        var form = new mini.Form("#FormMode");
        var data = form.getData();      //获取表单多个控件的数据
        var feedBackFileUUID = $("input[name=feedBackFileUUID]").val();
        data.feedBackFileUUID = feedBackFileUUID;
        var json = mini.encode(data);   //序列化成JSON
        $.ajax({
            url:"${pageContext.request.contextPath}/business/techEvalClarify/saveUpdateTechEvalClarify.action",
            type: "post",
            data: { submitData: json },
            success: function (text) {
                mini.confirm("操作成功!", "确认框", function (r) {
                    window.CloseOwnerWindow();
                });
            },

        });
    }


    // 统一保存方法
    function toClose() {
        mini.confirm("确定要关闭吗？", "提示", function (action) {
            if (action == "ok") {
                self.close();
            }
        })

    }

</script>

