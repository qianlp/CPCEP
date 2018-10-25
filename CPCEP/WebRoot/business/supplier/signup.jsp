<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>${menu.menuName}</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="stylesheet" type='text/css' href="../../css/form/loaders.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/bootstrap-theme.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/form.css" />

	<%@include file="../../resource.jsp" %>
	<script src="../../js/form/language_ZN.js" type="text/javascript"></script>

	<script src="../../js/form/wf-mini.js" type="text/javascript"></script>

	<%
		request.setAttribute("isRead", 1);

	%>
	<script>
        var gDir="${pageContext.request.contextPath}";
        var gIsFile = -1;	// 是否使用原始附件控件
        var gIsRead = 1;
        var gIsShowSign = 1;
        var gForm = 1;
        var gIsEditDoc = 1;
        var gIsNewDoc = 1;
        //招标类型
        var tabType ="${param.tabType}";
        function wfSubmitDoc(){
            if(arguments.length==1){gWQSagent=arguments[0]}
            wfSubDocStart();
        }

        function wfSaveDoc() {
			/*不做任何操作保存文档*/
            if(confirm("确定保存文档吗？")){
                try{gForm.wfFlowLogXML.value=XML2String(gWFLogXML)}catch(e){};
                //提交前执行事件
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

        function wfAgreeDoc(){
            //提交前处理数据
            if(confirm("您确定同意吗？")){
                if(arguments.length==1){gWQSagent=arguments[0]}
                gForm.wfTacheName.value="同意";
                wfSubDocStart();
            }
        }

        function wfRejectDoc(){
            if(confirm("您确定拒绝吗？")){
                //标注为拒绝
                gIsReject=1;
                gForm.wfTacheName.value="拒绝";
                wfSubDocStart();
            }
        }
	</script>
</head>
<body text="#000000" bgcolor="#FFFFFF"  style='padding:0px;width:100%;height:100%;background:#f3f3f3;'>
<div id="bg"></div>
<div class="loader" id="bg-loader">
	<div class="loader-inner ball-spin-fade-loader">
		<div></div>
		<div1></div1>
		<div></div>
		<div></div>
		<div></div>
		<div></div>
		<div></div>
		<div></div>
	</div>
</div>
<form method="post" action="${pageContext.request.contextPath}${menu.actionAddress}" name="form1" id="form1" >
	<%@include file="../../workflow/workFlowMust.jsp" %>

	<div id="PageBody">
		<script type="text/javascript">
            //保存方法
            function toSave(){
                form1.status.value="1";//保存
                form1.submit();
            }
            function afterLoad(){

            };

            function onNodeClick(e) {
                var node = e.node;
                if (node.type != 'user') {
                    e.sender.setValue('');
                    e.sender.setText('');
                } else {
                    e.sender.setValue(node.text);
                    e.sender.setText(node.text);
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
		</div>
		<div class="col-md-7" name="FormMode" id="FormMode" style="width:100%;margin:auto;float:none;">
			<div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
				<div class="mbox-header"><span class="form-title" style="height:100%;line-height:45px;">供应商信息</span></div>
				<div class="mbox-body" style="height:100%;padding:10px;">
					<fieldset style="margin: 10px;">
						<legend style="margin-left:10px;"><b>供应商基本信息</b></legend>

						<table style="width:95%;margin:5px;" id="tab">

							<tr>
								<td class="td_left">企业名称：</td>
								<td class="td_right">
									<input name="projectCode" class="mini-textbox" style="width:99%;" value="${view.name}" readonly/>
								</td>
								<td class="td_left">用户名：</td>
								<td class="td_right">
									<input name="projectName" class="mini-textbox" value="${view.username}" style="width: 99%;" title="用户名" readonly/>
								</td>
							</tr>

							<tr>
								<td class="td_left">纳税人登记号：</td>
								<td class="td_right">
									<input name="taxpayerNo" class="mini-textbox"  value="${view.taxpayerNo}" style="width:99%" readonly/>
								</td>

								<td class="td_left">法人代表：</td>
								<td class="td_right">
									<input name="corporations" class="mini-textbox" value="${view.corporations}" style="width: 99%;" readonly/>
								</td>

							</tr>


							<tr>
								<td class="td_left">企业性质：</td>
								<td class="td_right">
									<input name="nature" class="mini-combobox" style="width:99%;" value="${view.nature}"  url="${pageContext.request.contextPath}/business/bid/queryBsNatureEnterpriseList.action" readonly="readonly"/>
								</td>

								<td class="td_left">注册资金（万元）：</td>
								<td class="td_right">
									<input name="registerCapital" class="mini-textbox" style="width:99%;" value="${view.registerCapital}" readonly/>
								</td>
							</tr>

							<tr>
								<td class="td_left">注册时间：</td>
								<td class="td_right">
									<input name="registerTime" class="mini-textbox"  value="<fmt:formatDate value="${view.registerTime}" pattern="yyyy-MM-dd"/>" style="width:99%" readonly/>
								</td>

								<td class="td_left">公司联系人：</td>
								<td class="td_right">
									<input name="contacts" class="mini-textbox" value="${view.contacts}" style="width: 99%;" readonly/>
								</td>
							</tr>

							<tr>
								<td class="td_left">通信地址：</td>
								<td class="td_right">
									<input name="contactAddress" class="mini-textbox"  value="${view.contactAddress}" style="width:99%" readonly/>
								</td>

								<td class="td_left">公司电话：</td>
								<td class="td_right">
									<input name="phon" class="mini-textbox" value="${view.phon}" style="width: 99%;" readonly/>
								</td>
							</tr>

							<tr>
								<td class="td_left">手机号码：</td>
								<td class="td_right">
									<input class="mini-textbox"  value="${view.mobile}" style="width:99%" readonly/>
								</td>

								<td class="td_left">公司传真：</td>
								<td class="td_right">
									<input name="fax" class="mini-textbox" value="${view.fax}" style="width: 99%;" readonly/>
								</td>
							</tr>

							<tr>
								<td class="td_left">公司邮箱：</td>
								<td class="td_right">
									<input class="mini-textbox"  value="${view.email}" style="width:99%" readonly/>
								</td>

							</tr>
						</table>
					</fieldset>
				</div>
			</div>

			<div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;margin-top:20px;">
				<div class="mbox-header"><span class="form-title" style="height:100%;line-height:45px;">证件信息</span></div>
				<div class="mbox-body" style="height:100%;padding:10px;">
					<fieldset style="margin: 10px;">
						<legend style="margin-left:10px;"><b>证件信息</b></legend>

						<table style="width:95%;margin:5px;" id="tab2">

							<tr>
								<td class="td_left">社会统一信用代码：</td>
								<td class="td_right">
									<input name="license" class="mini-textbox" style="width:99%;" value="${view.license}" readonly/>
								</td>
								<td class="td_left">专业能力资质证书：</td>
								<td class="td_right">
									<input name="certificate" class="mini-textbox" value="${view.certificate}" style="width: 99%;" readonly />
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</div>

			<div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;margin-top:20px;">
				<div class="mbox-header"><span class="form-title" style="height:100%;line-height:45px;">投标联系人信息</span></div>
				<div class="mbox-body" style="height:100%;padding:10px;">
					<fieldset style="margin: 10px;">
						<legend style="margin-left:10px;"></legend>

						<table style="width:95%;margin:5px;" id="tab5">
							<form id="trader">
								<input type="hidden" name="id" value="${trader.uuid}"/>
								<input type="hidden" name="signupId" value="${signup.uuid}"/>
							<tr>
								<td class="td_left">投标联系人：</td>
								<td class="td_right">
									<input name="contact" placeholder="请输入投标联系人" <c:if test="${!signup.canWrite}">class="mini-textbox" </c:if> value="${signup.contact}"/>
								</td>
								<td class="td_left">职位：</td>
								<td class="td_right">
									<input name="position" placeholder="请输入职位" <c:if test="${!signup.canWrite}">class="mini-textbox" </c:if> value="${signup.position}"/>
								</td>
							</tr>

							<tr>
								<td class="td_left">工位电话：</td>
								<td class="td_right">
									<input name="phone" placeholder="请输入工位电话" <c:if test="${!signup.canWrite}">class="mini-textbox" </c:if> value="${signup.phone}"/>
								</td>

								<td class="td_left">手机号码：</td>
								<td class="td_right">
									<input name="mobile" placeholder="请输入手机号码" <c:if test="${!signup.canWrite}">class="mini-textbox" </c:if>  value="${signup.mobile}"/>
								</td>
							</tr>

							<tr>
								<td class="td_left">电子邮箱：</td>
								<td class="td_right">
									<input name="email" <c:if test="${!signup.canWrite}">class="mini-textbox" </c:if> placeholder="请输入电子邮箱" value="${signup.email}"/>
								</td>
							</tr>
							</form>
						</table>
					</fieldset>
				</div>
			</div>

			<div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;margin-top:20px;">
				<div class="mbox-header"><span class="form-title" style="height:100%;line-height:45px;">开票信息</span></div>
				<div class="mbox-body" style="height:100%;padding:10px;">
					<fieldset style="margin: 10px;">
						<legend style="margin-left:10px;"><b>开票信息</b></legend>

						<table style="width:95%;margin:5px;" id="tab3">

							<tr>
								<td class="td_left">企业开票名称：</td>
								<td class="td_right">
									<input name="billingName" class="mini-textbox" style="width:99%;" value="${view.billingName}" readonly/>
								</td>
								<td class="td_left">银行账号：</td>
								<td class="td_right">
									<input name="bankAccount" class="mini-textbox" value="${view.billingBankAccount}" style="width: 99%;" readonly/>
								</td>
							</tr>

							<tr>
								<td class="td_left">开户行名称：</td>
								<td class="td_right">
									<input name="bankName" class="mini-textbox"  value="${view.billingBankName}" style="width:99%" readonly/>
								</td>

								<td class="td_left">纳税人识别号：</td>
								<td class="td_right">
									<input name="billingTaxpayerId" class="mini-textbox" value="${view.billingTaxpayerId}" style="width: 99%;" readonly/>
								</td>
							</tr>

							<tr>
								<td class="td_left">开票地址：</td>
								<td class="td_right">
									<input name="address" class="mini-textbox"  value="${view.billingAddress}" style="width:99%" readonly/>
								</td>

								<td class="td_left">联系电话：</td>
								<td class="td_right">
									<input class="mini-textbox" value="${view.billingPhone}" style="width: 99%;" readonly/>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</div>
		</div>

		<jsp:include page="plug/attachmentView.jsp" />
	</div>

	<%@include file="../../toolbar/flowFoot.jsp" %>
</form>
</body>
</html>
<script>
	$(function() {
	    var columns = [{
            type: "checkcolumn"
        }, {
            field : "id",
            visible : false
        }, {
            type : "indexcolumn",
            header : "序号",
            headerAlign : "center"
        }, {
            field : "fileName",
            width : 180,
            header : "文件名称",
            headerAlign : "center",
            align : "left",
			renderer : download
        }, {
            field : "uploadTime",
            width : 80,
            header : "上传时间",
            headerAlign : "center",
            align : "center",
            dateFormat : "yyyy-MM-dd H:mm"
        }, {
            field : "size",
            width : 80,
            header : "大小",
            headerAlign : "center",
            align : "center"
        } ];
        initAttachmentGrid("/business/attachments.action?uuid=${view.id}", columns);
        var canWrite = ${signup.canWrite};
        var canCheck = ${signup.canCheck};
        if(canCheck) {
            $("#btnContL").prepend("<button class='btn btn-md btn-default' type='button' onclick='checkSignup(8);' style='margin:0px 7px 15px 7px'>拒&nbsp;&nbsp;绝</button>");
            $("#btnContL").prepend("<button class='btn btn-md btn-default' type='button' onclick='checkSignup(2);' style='margin:0px 7px 15px 7px'>审&nbsp;&nbsp;核</button>");
        }
        if(canWrite) {
            $("#btnContR").prepend("<button class='btn btn-md btn-default' type='button' onclick='submitSignup(true);' style='margin:0px 7px 15px 7px'>提&nbsp;&nbsp;交</button>");
//             $("#btnContR").prepend("<button class='btn btn-md btn-default' type='button' onclick='submitSignup(false);' style='margin:0px 7px 15px 7px'>保&nbsp;&nbsp;存</button>");
            var fields = new mini.Form("PageBody").getFields();
            $.each(fields, function(index, field) {
                var name = field.name;
                if(name == "contact" || name == "position" || name == "phone" || name == "mobile" || name == "email")
                    field.setReadOnly(false);
            });
        }
	});
    function checkSignup(checkStatus){
        var id = $("input[name=signupId]").val();
        var title = "确认要拒绝该报名吗?";
        if(checkStatus == 2) {
            title = "确认要审核选定的报名吗?";
		}
        mini.confirm(title, "操作提示", function(action){
            if(action=="ok"){
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '操作中...'
                });
                $.ajax({
                    url: gDir+"/business/signCheck.action?id=" + id + "&checkStatus=" + checkStatus,
                    success:function(){
                        mini.unmask(document.body);
                        mini.alert("操作成功！");

                        window.opener.location.reload();
                        self.close();

                       // refreshGridData();
                    }
                });
            }
        });
    }

	function submitSignup(signup) {
//        var oldForm=parent.document.forms[0].submit;
//        console.log(oldForm);
////        oldForm.call(parent.document.forms[0]);
//
//		console.log(window.opener);
////        parent.parent.location.reload();
//
//        return ;
        var title = "确定提交报名申请吗?";

        if(!signup) {
            title = "确认要保存报名申请吗?该操作不会提交报名申请!"
        }
        mini.confirm(title, "操作提示", function(action){
            if(action=="ok"){
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '操作中...'
                });
                $.ajax({
                    url: gDir+"/business/traderSignup.action?signup=" + signup,
					data : {
                        contact : $("input[name=contact]").val(),
						position : $("input[name=position]").val(),
                        phone : $("input[name=phone]").val(),
                        mobile : $("input[name=mobile]").val(),
                        email : $("input[name=email]").val(),
                        id : $("input[name=id]").val(),
                        tabType:tabType

                    },
                    success:function(result){
                        result = mini.decode(result);
                        if(result.code != 200) {
                            mini.alert(result.message);
                            return ;
						}
                        window.opener.location.reload();
                        mini.unmask(document.body);
                        mini.alert("操作成功！", "提示",function() {
//                             window.close();
                        	window.opener.location.reload();
                        	self.close();
						});
                    }
                });
            }
        });
	}

    function checkSupplier(check){
        var title = "确认要审核选定供应商吗？";
        var checkStatus = 2;

        if(!check) {
            title = "确认要拒绝选定供应商吗？"
			checkStatus = 8;
		}
		var id = $("input[name=id]").val();
        if(id == null || id == undefined || id =="") {
            alert("没有找到供应商信息!");
            return ;
		}
        mini.confirm(title, "操作提示", function(action){
            if(action=="ok"){
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '操作中...'
                });
                $.ajax({
                    url: gDir+"/business/check.action?id="+ id + "&checkStatus=" + checkStatus,
                    success:function(){
                        mini.unmask(document.body);
                        mini.alert("操作成功！", "提示",function() {
                        	window.opener.location.reload();
                        	self.close();
						});
                    }
                });
            }
        });

    }

	function download(cell) {
	    console.log(cell);
		var url = "/business/download.action?id=" + cell.row.id;
		return "<a href='" + url + "'>" + cell.value + "</a>"
	}
</script>