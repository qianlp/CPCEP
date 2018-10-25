<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>供应商注册</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/business/supplier/css/base.css">
	<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/business/supplier/css/style.css" >--%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/business/supplier/css/login.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/miniui/scripts/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/miniui/scripts/miniui/miniui.js"></script>
	<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/file/swfupload/swfupload.package.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/file/swfupload/handlers.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/file/swfupload/css/swfupload.css">
	<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/form.css" />
	<%--<script src="${pageContext.request.contextPath}/layui/layui.js"></script>--%>
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">--%>

	<script src="${pageContext.request.contextPath}/layui/layui-v2.3.0-rc1/layui/layui.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layui-v2.3.0-rc1/layui/css/layui.css" media="all">
</head>
<body>

<div class="wrap">
	<div class="regist">
		<div class="rmain">
			<div class="rm_tit rt02"></div>
			<div class="rm_con">
				<form name="UserForm" method="post" action="" id="register">
					<table class="m-table" style="width:1000px;" id="orderitems">
						<tbody>
						<tr><td colspan="2" style="font-size:14px;"><b>第一步：账号和联系人信息</b></td></tr>
						<tr>
							<!-- 企业名称-->
							<td class="tdColor">企业名称<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="name" style="width:300px" onvalidation="onValidation"  required="true" maxlength="200" class="required seblanks u-ipt mini-textbox"  required="true" >
								</div>
								<div class="adpSign" id="names" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">用户名(不区分大小写)<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input placeholder="以字母开头，4-20位字母或数字" style="width:300px"  type="text" onblur="checkUsername();" required="true" name="username" maxlength="20" class="required u-ipt mini-textbox" onvalidation="onValidation" >
								</div>
								<div class="adpSign" id="usernames" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor" style="vertical-align:middle;">密码<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input placeholder="6-20位字母、数字或下划线" style="width:300px" type="password" name="password" maxlength="20" class="required u-ipt mini-password" required="true" vtype="rangeLength:5,10" onvalidation="onValidation"/>
								</div>
								<div class="adpSign" id="passwords" style="color:red;"></div>
								<div id="table1" style="display:none;">
									<table style="width: 220px; height:12px; border: 0px;border-collapse: collapse;border-spacing: 0; background: #E6EAED; margin-top: 5px;">
										<tbody>
										<tr align="center">
											<td width="33%" id="strength_L" bgcolor="#E6EAED"
												style="padding:0px; text-align:center;height:12px;">弱
											</td>
											<td width="33%" id="strength_M" bgcolor="#E6EAED"
												style="padding:0px; text-align:center;">中
											</td>
											<td width="33%" id="strength_H" bgcolor="#E6EAED"
												style="padding:0px; text-align:center;">强
											</td>
										</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">确认密码<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="password" name="repassword"  style="width:300px" maxlength="20" class="u-ipt mini-password" required="true" vtype="rangeLength:5,10"  onvalidation="onRepasswordValidation" >
								</div>
								<div class="adpSign" id="repasswords" style="color:red;"></div>
							</td>
						</tr>

						<tr>
							<td class="tdColor">注册地址<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="registerAddress"  onvalidation="onValidation"  style="width:300px" maxlength="60" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="registerAddresss" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">纳税人登记号<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="taxpayerNo"  onvalidation="onValidation"  maxlength="25" style="width:300px" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign"  id="taxpayerNos" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">法人代表<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="corporations"  onvalidation="onValidation"  maxlength="25" style="width:300px" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign"  id="corporationss" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">企业性质<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input name="nature" type="text"  
                                   property="editor" class="mini-combobox" showRadioButton="true" style="width:300px"
                                   url="${pageContext.request.contextPath}/business/bid/queryBsNatureEnterpriseList.action"
                                   allowInput="true" required="true"  onvalidation="onValidation"   title="企业性质"
                                  >
								<div class="adpSign" id="natures" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">注册资金（万元）<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="registerCapital"   onvalidation="onValidation" maxlength="25" style="width:300px" class="required u-ipt mini-textbox" required="true" vtype="int"></div>
								<div class="adpSign"  id="registerCapitals" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">注册时间<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="registerTime"  onvalidation="onValidation"  maxlength="25" style="width:300px" class="required u-ipt mini-datepicker" required="true"></div>
								<div class="adpSign" id="registerTimes" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">公司联系人<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="contacts"  onvalidation="onValidation"  maxlength="25"  style="width:300px" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="contactss" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">通信地址<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="contactAddress"  onvalidation="onValidation"  maxlength="60" style="width:300px" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="contactAddresss" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">公司电话<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="phon" maxlength="25"   onvalidation="onValidation" style="width:300px" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="phons" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">手机号码<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="mobile" id="mobile" maxlength="25"  onvalidation="onValidation"  style="width:300px" class="required u-ipt mini-textbox" required="true">
								</div>
								<div class="adpSign" id="mobiles" style="color:red;"></div>
								<button type="button" class="layui-btn layui-btn-sm" id="verifyBtn">
									<i class="layui-icon">&#xe67c;</i>验证码
								</button>
								<div class="adpSign"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">验证码<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="verifyCode" id="verifyCode"   onvalidation="onValidation"  style="width:300px" maxlength="25" class="required u-ipt mini-textbox" required="true">
									<input type="hidden" name="sendVerifyMsgId" maxlength="25">
								</div>
								<div class="adpSign" id="verifyCodes" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">公司传真<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="fax" maxlength="25"  onvalidation="onValidation"  style="width:300px" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="faxs" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">公司邮箱<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="email" maxlength="25"   onvalidation="onValidation" style="width:300px" class="required u-ipt mini-textbox" required="true" vtype="email"></div>
								<div class="adpSign" id="emails" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">供货范围<em class="sign">*</em></td>
							<td >
									<textarea class="mini-textarea" name="materialName" id="materialName" style="width:500px" readonly="readonly"  required="true" ></textarea>
									<textarea class="mini-textarea" name="materialId" id="materialId" style="display: none" readonly></textarea>
									<a class="mini-button" plain="true" iconCls="icon-add" onclick="onSelectQuality()">选择供货范围</a>
							</td>
						</tr>
						<tr><td colspan="2" style="font-size:14px;"><b>第二步：证件信息</b></td></tr>
<!-- 原【营业执照】、【税务登记证】、【织机构代码证】去掉换成【社会统一信用代码】 -->
						<tr>
							<td class="tdColor">社会统一信用代码<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="license" maxlength="25"  onvalidation="onValidation"  style="width:300px" class="required u-ipt mini-textbox" required="true">
								</div>
								<div class="adpSign" id="licenses" style="color:red;"></div>
								<button type="button" class="layui-btn layui-btn-sm" id="licenseFileUpload">
									<i class="layui-icon">&#xe67c;</i>上传
								</button>
								<div class="adpSign">
									<span id="licenseFileSpan"></span>
								</div>

							</td>
						</tr>
						
						<tr><td colspan="2" style="font-size:14px;"><b>第三步：开票信息</b></td></tr>
						<tr>
							<td class="tdColor">企业开票名称<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="billingName"  onvalidation="onValidation"  style="width:300px" maxlength="25" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="billingNames" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">银行账号<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="billingBankAccount"   onvalidation="onValidation" style="width:300px" maxlength="25" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="billingBankAccounts" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">开户行名称<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="billingBankName"  onvalidation="onValidation"  style="width:300px" maxlength="25" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="billingBankNames" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">纳税人识别号<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="billingTaxpayerId"  onvalidation="onValidation"  style="width:300px" maxlength="25" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="billingTaxpayerIds" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">开票地址<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="billingAddress"   onvalidation="onValidation" style="width:300px" maxlength="60" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="billingAddresss" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td class="tdColor">联系电话<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="billingPhone" maxlength="25"  onvalidation="onValidation"  style="width:300px" class="required u-ipt mini-textbox" required="true"></div>
								<div class="adpSign" id="billingPhones" style="color:red;"></div>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="font-size:14px;"><b>第四步：上传附件</td>
						</tr>
						<tr>
							<td colspan="2" style="font-size:14px;"><b>业绩情况</b></td>
						</tr>
						<tr>
							<td class="tdColor">业绩统计表<em class="sign">*</em></td>
							<td>
								<select name="fileTtypeStatistical" style="display: none">
									<option value="1">业绩统计表</option>
								</select>
								<button type="button" class="layui-btn layui-btn-sm" id="attachmentBtnStatistical">
									<i class="layui-icon">&#xe67c;</i>上传
								</button>
								<span id="performance" type="1"></span>
								<span id="performanceFile"></span>
							</td>
						</tr>
						<tr>
							<td class="tdColor">业绩证明文件<em class="sign">*</em></td>
							<td>
								<select name="fileTtypeProofDocument" style="display: none">
									<option value="2">业绩证明文件</option>
								</select>
								<button type="button" class="layui-btn layui-btn-sm" id="attachmentBtnProofDocument">
									<i class="layui-icon">&#xe67c;</i>上传
								</button>
								<span id="performance_prove" type="2" ></span>
								<span id="performance_proveFile"></span>
							</td>
						</tr>
						<tr>
							<td class="tdColor">专业能力资质证书<em class="sign">*</em></td>
							<td>
<!-- 								<div class="adpForm"> -->
<!-- 									<input type="text" name="certificate" style="width:300px"  onvalidation="onValidation"  maxlength="25" class="required u-ipt mini-textbox" required="true"> -->
<!-- 								</div> -->
<!-- 								<div class="adpSign" id="certificates" style="color:red;"></div> -->
								<button type="button" class="layui-btn layui-btn-sm" id="certificateFileUpload">
									<i class="layui-icon">&#xe67c;</i>上传
								</button>
								<div class="adpSign">
									<span id="certificateFileSpan"></span>
								</div>
							</td>
						</tr>
						<tr><td colspan="2" style="font-size:14px;"><b>其他附件</b></td></tr>
						<tr>
							<td class="tdColor">其他附件<em class="sign">*</em></td>
							<td>
								<select name="fileTtypeOther" style="display: none">
									<option value="3">其他附件</option>
								</select>
								<button type="button" class="layui-btn layui-btn-sm" id="attachmentBtnOther">
									<i class="layui-icon">&#xe67c;</i>上传
								</button>
								<span id="other" type="2" ></span>
								<span id="otherFile" ></span>
							</td>
						</tr>
						</tbody>
					</table>
				</form>
			</div>

			<div align="center" class="centerBtn">
				<button type="button" onclick="register();" name="button" class="button btn-lg" id="nextBtn"><span>提交</span></button>
			</div>
		</div>
	</div>
</div>
<script>
	mini.parse();
	var materialName = mini.get("materialName");
	var materialId = mini.get("materialId");
	var sendFlag = false;
	var clickChange = false;
	var suppliers = [];
    function checkUsername() {
    	var username = $("input[name=username]").val();
    	var regname = /^\w+$/; 
    	if(!regname.test(username)){ 
    		 layer.msg("用户名只能用英文字母和数字及下划线");
    		 $("input[name=username]").val("");
    		 return ;
    	} 
        
        if(username == null || username == "") {
            return ;
        }
        $.post("${pageContext.request.contextPath}/business/checkUser.action", {
            username : username
        }, function(result) {
            result = mini.decode(result);
            if(result.code != 200) {
                layer.msg(result.message);
                return ;
            }
        });
    }
	function register() {
	    var form = $("#register");
        var registerForm = new mini.Form("#register");
        registerForm.validate();
        if(!registerForm.isValid()){
            return ;
        }
        
        var username = $("input[name=username]").val();
    	var regname = /^\w+$/; 
    	if(!regname.test(username)){ 
    		 layer.msg("用户名只能用英文字母和数字及下划线");
    		 return ;
    	} 
        
        
        var verifyCode = $("input[name=verifyCode]").val();
        if(!clickChange){
        	layer.msg("请点击验证码!");
            return ;
        }
       	if(!sendFlag) {
            layer.msg("请输入正确的验证码！");
            $("input[name=verifyCode]").val("");
            return ;
		}
      
		if(materialName.getValue() == null || materialName.getValue==""||materialId.getValue()==null || materialId.getValue()==""){
			layer.msg("请选择供货范围!");
		}
		
		var password = mini.getbyName("password").getValue();
		var repassword = mini.getbyName("repassword").getValue();
		if(password != repassword){
			layer.msg("两次密码不一致，请重新输入!");
			return ;
		}
        var licenseFile = $("input[name=licenseFile]");
		var certificateFile = $("input[name=certificateFile]");
        var performance = $("input[name=performance]");
        var performanceProve = $("input[name=performanceProve]");
        var otherAttachment = $("input[name=otherAttachment]");
        if(licenseFile.length == 0 ){
        	layer.msg("社会统一信用代码附件必须上传!");
            return ;
        }
        if(certificateFile.length == 0){
        	layer.msg("专业能力资质证书附件必须上传!");
            return ;
        }
        if(performance.length == 0){
        	layer.msg("业绩统计表附件必须上传!");
            return ;
        }
        if(performanceProve.length == 0){
        	layer.msg("业绩证明文件附件必须上传!");
            return ;
        }
        if(otherAttachment.length == 0 ){
        	layer.msg("其他附件必须上传!");
            return ;
        }

		$.post("${pageContext.request.contextPath}/business/register.action", form.serialize(), function(result) {
		    result = mini.decode(result);
			if(result.code != 200) {
			    alert(result.message);
			    return ;
			}
			alert("注册成功!");
            window.history.back();
            location.reload();
		});
	}

	$(function() {
        layui.use(['layer', 'upload'], function(){
            var layer = layui.layer;
            var upload = layui.upload;
            /* 
            *原营业执照，现社会统一信用代码
            */
            upload.render({
                elem: '#licenseFileUpload',
                accept : "file",
                url: '${pageContext.request.contentLength}/business/upload.action?type=4',
                before : function() {
                    layer.load(1);
                },
                done: function(res){
                    layer.closeAll();
					if(res.code != 200) {
					    layer.msg(res.message);
					    return ;
                    }
                    $("#licenseFileSpan").empty();
                    var file = res.data;
                    var html = '<span style="margin-right: 10px;">';
                    html += '<a href="${pageContext.request.contextPath}/business/download.action?id='+file.uuid+'">' + file.fileName + '</a>';
                    html += '<input type="hidden" name="licenseFile" value="' + file.uuid + '"/>';
                    html += '<a style="color:red;" href="javascript:void(0);" onclick="removeFile(this);">×</a>';
                    html += '</span>';
                    $("#licenseFileSpan").append(html);
					layer.msg(res.message);
                },
				error: function(){
                    //请求异常回调
                }
            });
			

            upload.render({
                elem: '#certificateFileUpload',
                accept : "file",
                url: '${pageContext.request.contextPath}/business/upload.action?type=7',
                before : function() {
                    layer.load(1);
                },
                done: function(res){
                    layer.closeAll();
                    if(res.code != 200) {
                        layer.msg(res.message);
                        return ;
                    }
                    $("#certificateFileSpan").empty();
                    var file = res.data;
                    var html = '<span style="margin-right: 10px;">';
                    html += '<a href="${pageContext.request.contextPath}/business/download.action?id='+file.uuid+'">' + file.fileName + '</a>';
                    html += '<input type="hidden" name="certificateFile" value="' + file.uuid + '"/>';
                    html += '<a style="color:red;" href="javascript:void(0);" onclick="removeFile(this);">×</a>';
                    html += '</span>';
                    $("#certificateFileSpan").append(html);
                    layer.msg(res.message);
                },
                error: function(){
                    //请求异常回调
                }
            });

            upload.render({
                elem: '#attachmentBtnStatistical',
                accept : "file",
                url: '${pageContext.request.contextPath}/business/upload.action',
				data : {
                    type : function() {
                        return $("select[name=fileTtypeStatistical]").val();
                    }
                },
                before : function() {
                    layer.load(1);
                },
                multiple : true,
                done: function(res){
                    layer.closeAll();
                    if(res.code != 200) {
                        layer.msg(res.message);
                        return ;
                    }
                    // 回显
                    $("#performanceFile").empty();
                    var file = res.data;
                    var name = "performance";
                    var id = getInputId(file.type);
                    var html = "<span style='margin-right: 10px;'><a href='${pageContext.request.contextPath}/business/download.action?id="+file.uuid+"'>" + file.fileName + "</a><input type='hidden' name='" + name + "' value='" + file.uuid + "'/> <a style='color:red;' href='javascript:void(0);' onclick='removeFile(this);'>×</a></span>";
                    $("#" + id + "File").append(html);
                    layer.msg(res.message);
                },
                error: function(){
                    //请求异常回调
                }
            });
            
            upload.render({
                elem: '#attachmentBtnProofDocument',
                accept : "file",
                url: '${pageContext.request.contextPath}/business/upload.action',
				data : {
                    type : function() {
                        return $("select[name=fileTtypeProofDocument]").val();
                    }
                },
                before : function() {
                    layer.load(1);
                },
                multiple : true,
                done: function(res){
                    layer.closeAll();
                    if(res.code != 200) {
                        layer.msg(res.message);
                        return ;
                    }
                    // 回显
                    $("#performance_proveFile").empty();
                    var file = res.data;
                    var name = "performanceProve";
                    var id = getInputId(file.type);
                    var html = "<span style='margin-right: 10px;'><a href='${pageContext.request.contextPath}/business/download.action?id="+file.uuid+"'>" + file.fileName + "</a><input type='hidden' name='" + name + "' value='" + file.uuid + "'/> <a style='color:red;' href='javascript:void(0);' onclick='removeFile(this);'>×</a></span>";
                    $("#" + id + "File").append(html);
                    layer.msg(res.message);
                },
                error: function(){
                    //请求异常回调
                }
            });
            
            upload.render({
                elem: '#attachmentBtnOther',
                accept : "file",
                url: '${pageContext.request.contextPath}/business/upload.action',
				data : {
                    type : function() {
                        return $("select[name=fileTtypeOther]").val();
                    }
                },
                before : function() {
                    layer.load(1);
                },
                multiple : true,
                done: function(res){
                    layer.closeAll();
                    if(res.code != 200) {
                        layer.msg(res.message);
                        return ;
                    }
                    // 回显
                    $("#otherFile").empty();
                    var file = res.data;
                    var name = "otherAttachment";
                    var id = getInputId(file.type);
                    var html = "<span style='margin-right: 10px;'><a href='${pageContext.request.contextPath}/business/download.action?id="+file.uuid+"'>" + file.fileName + "</a><input type='hidden' name='" + name + "' value='" + file.uuid + "'/> <a style='color:red;' href='javascript:void(0);' onclick='removeFile(this);'>×</a></span>";
                    $("#" + id + "File").append(html);
                    layer.msg(res.message);
                },
                error: function(){
                    //请求异常回调
                }
            });
            
            
        });
        
        $("#mobile").change(function(){
        	 var mobile = $("input[name=mobile]").val();
        	 if(mobile==null||mobile==""){
        		 return ; 
        	 }
        	 $.ajax({
        		 url:"${pageContext.request.contextPath}/business/checkPhon.action",
        		 data:{mobile:mobile},
        	     cache: false,//禁用缓存
              	 async:false,
              	 dataType:'json',
        		 success:function(result){
        			  layer.msg(result.msg);
        		 }
        	 });
        });
        
         $("#verifyCode").change(function(){
        	 var verifyCode = $("input[name=verifyCode]").val();
        	 if(verifyCode.length<6){
        		layer.msg("请输入正确的验证码!");
        		$("input[name=verifyCode]").val("");
                return ; 
        	 }
		     var sendVerifyMsgId =$("input[name=sendVerifyMsgId]").val();
		     var mobile = $("input[name=mobile]").val();
		     if(mobile==null||mobile==""){
		    	 layer.msg("请输入手机号码！");  
		    	 return ; 
		     }
        	 $.ajax({
        	   url:"${pageContext.request.contextPath}/business/checkCode.action",
        	   data:{
        		   verifyCode:verifyCode,
        		   sendVerifyMsgId:sendVerifyMsgId,
        		   mobile:mobile
        	   },
        	   type: "post",
        	   dataType:"json",
        	   cache: false,//禁用缓存
        	   async:false,
        	   success:function(data){
        		   if(data.message=="success"){
        			   sendFlag = true;
        			   layer.msg("验证码正确，请输入其他信息");  
        		   }else if(data.message=="error"){
		   			   layer.msg("请输入正确的验证码！");  
		   			   $("input[name=verifyCode]").val("");
		   		   }else{
        			   layer.msg("验证码错误，请重新输入");  
        			   $("input[name=verifyCode]").val("");
        		   }
        	   },
        	   error:function(data){
        		   layer.msg("验证码错误");   
        		   $("input[name=verifyCode]").val("");
        	   }
        	 });
        	 
         });
        
         
         $("#verifyBtn").click(function() {
            if(sendFlag)
                return ;
            var mobile = $("input[name=mobile]").val();
            if(mobile == null || mobile == "") {
                layer.msg("请输入正确的手机号!");
                return ;
            }
            var seconds = 60;
            var interval = setInterval(function(){
                if(seconds > 0) {
                    $("#verifyBtn").text(seconds--);
				} else {
                    clearInterval(interval);
                    $("#verifyBtn").text("验证码");
                    //sendFlag = false;
				}
            }, 1000);
            $.get("${pageContext.request.contextPath}/business/verify.action", {
				mobile : mobile
			}, function(result) {
                result = mini.decode(result);
				/* if(result.code != 200) {
				    layer.msg(result.message);
				    return ;
				} */
				$("input[name=sendVerifyMsgId]").val(result.msgId);
				clickChange = true;
				layer.msg("验证码已发送，请及时查收!");
			});
		});
	});


	/*
	* 供货范围
	*/
	 function onSelectQuality(e) {
		var scopeid = "";
		var matertype = "";
        mini.open({
            url : "${pageContext.request.contextPath}/business/qualityLibrary.action",
            title : "供货范围",
            width : 725,
            height : 500,
            showMaxButton : true,
            ondestroy : function(data) {
                if (data.indexOf("[") == 0) {
                    var objs = mini.decode(data);
                    for(var i=0; i<objs.length; i++){
                    	if(i==objs.length-1){
                    		matertype = matertype + objs[i].text;
                    		scopeid = scopeid +objs[i].id;
                    	}else{
                    		matertype = matertype + objs[i].text +";";
                    		scopeid = scopeid +objs[i].id +";";
                    	}
                    }
                    materialName.setValue(matertype);
                    materialId.setValue(scopeid);
                    materialName.validate();
                }
            }
        });
    }

	function removeFile(del) {
	    $(del).parent().remove();
	}
	
	/*
	*	校验提示
	*/
	
	function onValidation(e){
		if(e.source.name=="name"){
			if(e.isValid==false){
				 $("#names").text("请输入企业名称！");
				 e.errorText = "请输入企业名称！";
	         }else{
	        	 $("#names").text(""); 
	         }
		}
		
		if(e.source.name=="username"){
			if(e.isValid==false){
				 $("#usernames").text("请输入用户名！");
				 e.errorText = "请输入用户名！";
	         }else{
	        	 $("#usernames").text(""); 
	         }
		}
		if(e.source.name=="password"){
        	if(e.isValid== false){
             	$("#passwords").text("请输入确认密码！");
             	e.errorText = "请输入确认密码！";
             }else{
             	$("#passwords").text("");
             }
        	if(e.value.length < 5 || e.value.length > 10){
             	$("#passwords").text("字符长度必须在5到10之间！");
                 e.errorText = "字符长度必须在5到10之间！";
                 e.isValid = false;
             }
		}

		if(e.source.name=="registerAddress"){
			if(e.isValid==false){
				 $("#registerAddresss").text("请输入注册地址！");
				 e.errorText = "请输入注册地址！";
	         }else{
	        	 $("#registerAddresss").text("");
	         }
		}
		if(e.source.name=="taxpayerNo"){
			if(e.isValid==false){
				 $("#taxpayerNos").text("请输入纳税人登记号！");
				 e.errorText ="请输入纳税人登记号！";
	         }else{
	        	 $("#taxpayerNos").text("");
	         }
		}
		if(e.source.name=="corporations"){
			if(e.isValid==false){
				 $("#corporationss").text("请输入法人代表！");
				 e.errorText = "请输入法人代表！";
	         }else{
	        	 $("#corporationss").text("");
	         }
		}
		if(e.source.name=="nature"){
			if(e.isValid==false){
				 $("#natures").text("请选择企业性质！");
				 e.errorText = "请选择企业性质！";
	         }else{
	        	 $("#natures").text("");
	         }
		}
		if(e.source.name=="registerCapital"){
			if(e.errorText=="请输入整数"){
				 $("#registerCapitals").text("请输入正确的注册资金！");
				 e.errorText = "请输入正确的注册资金！";
			}else if(e.isValid==false){
				 $("#registerCapitals").text("请输入注册资金！");
				 e.errorText = "请输入注册资金！";
	         }else{
	        	 $("#registerCapitals").text("");
	         }
			
		}
		if(e.source.name=="registerTime"){
			if(e.isValid==false){
				 $("#registerTimes").text("请输入注册时间！");
				 e.errorText = "请输入注册时间！";
	         }else{
	        	 $("#registerTimes").text("");
	         }
		}
		if(e.source.name=="contacts"){
			if(e.isValid==false){
				 $("#contactss").text("请输入公司联系人！");
				 e.errorText = "请输入公司联系人！";
	         }else{
	        	 $("#contactss").text("");
	         }
		}
		if(e.source.name=="contactAddress"){
			if(e.isValid==false){
				 $("#contactAddresss").text("请输入通信地址！");
				 e.errorText = "请输入通信地址！";
	         }else{
	        	 $("#contactAddresss").text("");
	         }
		}
		if(e.source.name=="phon"){
			if(e.isValid==false){
				 $("#phons").text("请输入公司电话！");
				 e.errorText = "请输入公司电话！";
	         }else{
	        	 $("#phons").text("");
	         }
		}
		if(e.source.name=="mobile"){
			var reg = /^1[34578]\d{9}$/;
			if(e.isValid==false){
				 $("#mobiles").text("请输入手机号码！");
				 e.errorText = "请输入手机号码！";
	         }else{
	        	 $("#mobiles").text("");
	         }
			if(e.value.length != 0){
				if(reg.test(e.value) == false){
					$("#mobiles").text("请输入正确的手机号码！");
					e.errorText = "请输入正确的手机号码！";
					e.isValid = false;
				}else{
					 $("#mobiles").text("");
				}
			}
			
		}
		if(e.source.name=="verifyCode"){
			if(e.value.length != 6 && e.value.length != 0){
				$("#verifyCodes").text("请输入正确的验证码！");
				e.errorText = "请输入正确的验证码！";
				e.isValid=false;
			}else if(e.isValid==false){
				 $("#verifyCodes").text("请输入验证码！");
				 e.errorText = "请输入验证码！";
	         }else{
	        	 $("#verifyCodes").text("");
	         }
		}
		if(e.source.name=="fax"){
			if(e.isValid==false){
				 $("#faxs").text("请输入公司传真！");
				 e.errorText = "请输入公司传真！";
	         }else{
	        	 $("#faxs").text(""); 
	         }
		}
		if(e.source.name=="email"){
			if(e.isValid==false){
				if(e.errorText=="请输入邮件格式"){
					$("#emails").text("请输入正确的邮箱格式！");
					e.errorText="请输入正确的邮箱格式！"
				}else if(e.errorText=="不能为空"){
					$("#emails").text("请输入公司邮箱！");
					e.errorText="请输入公司邮箱！"
				}else{
					$("#emails").text("");
				}
			}else{
				$("#emails").text("");
			}
			
		}
		
		//onValidation
		if(e.source.name=="materialName"){
			if(e.isValid==false){
				 e.errorText = "请选择供货范围！";
				 var materialName = mini.getbyName("materialName").getValue();
				 if(materialName==null||materialName==""){
				 }else{
					 e.isValid=true;
				 }
	         }
		}
		
		if(e.source.name=="license"){
			if(e.isValid==false){
				 $("#licenses").text("请输入社会统一信用代码！");
				 e.errorText = "请输入社会统一信用代码！";
	         }else{
	        	 $("#licenses").text(""); 
	         }
		}
		/*
		if(e.source.name=="certificate"){
			if(e.isValid==false){
				 $("#certificates").text("请输入专业能力资质证书！");
				 e.errorText = "请输入专业能力资质证书！";
	         }else{
	        	 $("#certificates").text("");
	         }
		}
		*/
		if(e.source.name=="billingName"){
			if(e.isValid==false){
				 $("#billingNames").text("请输入企业开票名称！");
				 e.errorText = "请输入企业开票名称！";
	         }else{
	        	 $("#billingNames").text(""); 
	         }
		}
		if(e.source.name=="billingBankAccount"){
			if(e.isValid==false){
				 $("#billingBankAccounts").text("请输入银行账号！");
				 e.errorText = "请输入银行账号！";
	         }else{
	        	 $("#billingBankAccounts").text("");
	         }
		}
		if(e.source.name=="billingBankName"){
			if(e.isValid==false){
				 $("#billingBankNames").text("请输入开户行名称！");
				 e.errorText = "请输入开户行名称！";
	         }else{
	        	 $("#billingBankNames").text("");
	         }
		}
		if(e.source.name=="billingTaxpayerId"){
			if(e.isValid==false){
				 $("#billingTaxpayerIds").text("请输入纳税人识别号！");
				 e.errorText = "请输入纳税人识别号！";
	         }else{
	        	 $("#billingTaxpayerIds").text("");
	         }
		}
		if(e.source.name=="billingAddress"){
			if(e.isValid==false){
				 $("#billingAddresss").text("请输入开票地址！");
				 e.errorText = "请输入开票地址！";
	         }else{
	        	 $("#billingAddresss").text(""); 
	         }
		}
		if(e.source.name=="billingPhone"){
			if(e.isValid==false){
				 $("#billingPhones").text("请输入联系电话！");
				 e.errorText = "请输入联系电话！";
	         }else{
	        	 $("#billingPhones").text(""); 
	         }
		}
	         
	}

	

    function getInputId(type) {
		if(type == 1)
		    return "performance";
		if(type == 2)
		    return "performance_prove";
		if(type == 3)
		    return "other";
	}
    function onRepasswordValidation(e) {
    	var chooses = true;
        if (e.isValid) {
            var password = mini.getbyName("password").getValue();
            if (password!=e.value) {
            	$("#repasswords").text("密码不一致！");
                e.errorText = "密码不一致！";
                e.isValid = false;
                chooses = false;
            }
           
        }else{
        	$("#repasswords").text("");
        }
        if(chooses){
        	if(e.isValid== false){
             	$("#repasswords").text("请输入确认密码！");
             	e.errorText = "请输入确认密码！";
             }else{
             	$("#repasswords").text("");
             }
        	if(e.value.length < 5 || e.value.length > 10){
             	$("#repasswords").text("字符长度必须在5到10之间！");
                 e.errorText = "字符长度必须在5到10之间！";
                 e.isValid = false;
                 chooses = false;
             }
        }
        
       
    }
</script>
</body>
</html>