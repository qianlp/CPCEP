<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String menuId = request.getParameter("menuId");

//    String uuid = request.getParameter("uuid");
//    request.setAttribute("uuid", uuid);
//    String name = request.getParameter("name");
//    request.setAttribute("name", name);
//
//    String code = request.getParameter("code");
//    request.setAttribute("code", code);
//    String fileName = request.getParameter("fileName");
//    request.setAttribute("fileName", fileName);
//    String version = request.getParameter("version");
//    request.setAttribute("version", version);


    //  var bidQuestionUrl = gDir+"/business/bid/bidQuestionPage.action?uuid=" + uuid+"$name"+name+"&code"+code+"&fileName"+fileName+"&version"+version;


%>

<!DOCTYPE HTML>
<html>
<head>
    <title>招标质疑</title>
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/miniui/scripts/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/miniui/scripts/miniui/miniui.js"></script>
    <script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/form.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/login/style_log.css">

    <link rel="stylesheet" type='text/css' href="../css/form/bootstrap-theme.css" />
    <link rel="stylesheet" type='text/css' href="../css/form/bootstrap.flow.min.css" />
    <link rel="stylesheet" type='text/css' href="../css/form/form.css" />
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>

    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

    </style>
</head>
<body>
<div class="mini-layout" style="width:100%;height:100%;" id="layout">
    <div region="center" style="overflow:hidden;border-bottom:0;border-right:1">
        <div class='mini-fit' style='width:100%;height:100%;'>
            <form method="post" name="form1" id="form1"
                  action="${pageContext.request.contextPath}/business/biddingFile/saveUpdateBidFileQue.action">
                <table style="width:95%;margin:5px;" id="tab">
                    <tr>
                        <td class="td_left">招标编号：</td>
                        <td class="td_right">
                            <input name="bidCode" class="mini-textbox" style="width:99%;" value="${code}" type="text"
                                   readonly="readonly"/>
                        </td>
                        <td class="td_left">招标名称：</td>
                        <td class="td_right">
                            <input name="bidName" class="mini-textbox" value="${name}" style="width: 99%;" title="用户名"
                                   type="text" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">招标文件版本号：</td>
                        <td class="td_right">
                            <input name="version" class="mini-textbox" style="width:99%;" value="${version}" type="text"
                                   readonly="readonly"/>
                        </td>
                        <td class="td_left">问题类型：</td>
                        <td class="td_right">
                            <input name="issueType" class="mini-combobox"
                                   data="[{id:'技术问题',text:'技术问题'},{id:'商务问题',text:'商务问题'},{id:'其他',text:'其他'}]"
                                   style="width: 99%;" title="用户名"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">供应商名称：</td>
                        <td class="td_right">
                            <input name="supName" class="mini-textbox" style="width:99%;" type="text"
                                   readonly="readonly" value="${session.user.userName}"/>
                        </td>
                        <td class="td_left">联系方式：</td>
                        <td class="td_right">
                            <input name="contact" class="mini-textbox" style="width: 99%;" title="用户名"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">问题描述：</td>
                        <td class="td_right" colspan="6" style="padding:3px">
                            <input name="remark" class="mini-textarea" style="width:99.6%" title="备注">
                        </td>
                    </tr>

                    <tr>
                        <td class="td_left">附件：</td>
                        <td class="td_right">
                            <a id="file_1"></a>
                            <input name="questionFileUUID" allowInput="false" readonly="true"
                                   class="mini-textbox hidden"
                                   value="">
                            <button type="button" class="layui-btn layui-btn-sm" id="upload_1">
                                <i class="layui-icon">&#xe67c;</i>点击上传
                            </button>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_right" colspan="6" style="padding:3px;display: none">
                            <input name="bidFileUUID" value="${uuid}" class="mini-textarea" style="width:99.6%"
                                   title="备注">
                        </td>
                    </tr>


                    <tr>
                        <td class="td_right" colspan="6" style="padding:3px;display: none">
                            <input name="supUUID" value="${session.user.uuid}" class="mini-textarea" style="width:99.6%"
                                   title="备注">

                        </td>
                    </tr>
                    <tr>
                        <td class="rem_sub" colspan="4" style="text-align: center;">
                            <input class="sub_button" style="float: none;text-align: center;"
                                   value="提交" onclick="save();"/>
                        </td>


                    </tr>




                </table>
            </form>
        </div>
    </div>
</div>
<script>
    initUpload();
    function save() {
        var form = new mini.Form("#form1");
        var data = form.getData();      //获取表单多个控件的数据
        var json = mini.encode(data);   //序列化成JSON
        $.ajax({
            url: "${pageContext.request.contextPath}/business/biddingFile/saveUpdateBidFileQue.action",
            type: "post",
            data: {submitData: json},
            success: function (text) {
                mini.confirm("操作成功!", "确认框", function (r) {
                    window.CloseOwnerWindow();
                });
            },

        });
    }

    function initUpload() {
        <%--<s:if test='comObj!=null'>--%>
        <%--$("#file_1").html('${comObj.questionFile==null||comObj.questionFile.uuid==null||comObj.questionFile.uuid==''?"":comObj.questionFile.fileName}');--%>
        <%--$("input[name=questionFileUUID]").val('${comObj.questionFileUUID}');--%>
        <%--$("#file_1").attr("href", "${pageContext.request.contextPath}/business/download.action?id=${comObj.questionFileUUID}");--%>
        <%--</s:if>--%>

        layui.use('upload', function () {
            var upload = layui.upload;
            var uploadInst = upload.render({
                elem: "#upload_" + 1//绑定元素
                , accept: "file"
                , data: {
                    type: 15
                }
                , url: "${pageContext.request.contextPath}/business/upload.action" //上传接口
                , done: function (res) {
                    //上传完毕回调
                    var result = mini.decode(res);
                    $("#file_1").html(result.data.fileName);
                    $("input[name=questionFileUUID]").val(result.data.uuid);
                    mini.getbyName("questionFileUUID").setValue(result.data.uuid);
                    $("#file_1").attr("href", "${pageContext.request.contextPath}/business/download.action?id=" + result.data.uuid);
                }
                , error: function () {
                    //请求异常回调
                    alert("上传失败")
                }
            });
        });

    }
</script>
</body>
</html>