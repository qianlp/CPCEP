<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html style="min-width:520px">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>手机号找回密码</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/miniui/scripts/jquery-1.7.2.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/business/supplier/css/base.css">
	<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/business/supplier/css/style.css" >--%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/business/supplier/css/login.css">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/miniui/scripts/miniui/miniui.js"></script>
	<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/file/swfupload/swfupload.package.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/file/swfupload/handlers.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/file/swfupload/css/swfupload.css">
	<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/form.css" />
	<%-- <script src="${pageContext.request.contextPath}/layui/layui.js"></script> --%>
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">--%>
    <%-- <%@include file="../../resource.jsp"%> --%>
	<script src="${pageContext.request.contextPath}/layui/layui-v2.3.0-rc1/layui/layui.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layui-v2.3.0-rc1/layui/css/layui.css" media="all">
</head>
<body style="width:500px;height:400px;position: absolute;overflow:scroll;overflow-x:hidden;overflow-y:hidden">
<!-- <div class="wrap">
	<div class="regist">
		<div class="rmain">
			<div class="rm_tit rt02"></div>
			<div class="rm_con"> -->
			<div style="width: 450px; position: relative; left:1%;">
				<form name="UserForm" method="post" action="" id="register">
					<table class="m-table" style="width:550px;" id="orderitems">
						<tbody>
						<tr><td colspan="2" style="font-size:14px;"><b></b></td></tr>
						<tr>
							<td class="tdColor">手机号码<em class="sign">*</em></td>
							<td>
								<div class="adpForm">
									<input type="text" name="mobile" maxlength="25"  onvalidation="onValidation"  style="width:300px" class="required u-ipt mini-textbox" required="true">
								</div>
								<div class="adpSign" id="mobiles" style="color:red;"></div>
								<button type="button" class="layui-btn layui-btn-sm" id="verifyBtn">
									<i class="layui-icon">&#xe67c;</i>验证码
								</button>
								<div class="adpSign" id="adpSign"></div>
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
						</tbody>
					</table>
				</form>
		<!-- 	</div> -->
          <br>
			<div align="center" class="centerBtn" style="margin-left: 50px;">
				 <button class="layui-btn" lay-submit lay-filter="formDemo" onclick="updatePwd()">立即修改</button>
            
			</div>
		<!-- </div>
	</div>
</div> -->
</div>
<script type="text/javascript">
   mini.parse();
   var sendFlag=false;
   $(function(){
	   layui.use('layer', function(){
		   var layer = layui.layer;
		   
		 });      
   });
   
   function updatePwd(){

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
      	var mobile = $("input[name=mobile]").val();
      	var password = $("input[name=password]").val();
    	 $.ajax({
    	  	   url:"${pageContext.request.contextPath}/business/updatePWd.action",
    	  	   data:{
    	  		 mobile:mobile,
    	  		password:password
    	  	   },
    	  	   type: "post",
    	  	   dataType:"json",
    	  	   cache: false,//禁用缓存
    	  	   async:false,
    	  	   success:function(data){
    	  		 data = mini.decode(data);
    	  		   if(data.code!=200){
    	  			  layer.msg(data.message);
    	  			  return ;
    	  		   }else{
    	  			  layer.msg(data.message);
    	  			   parent.pwd.destroy();
    	  		   }
    	  	   }
    	 });
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
   function onValidation(e){
	   console.log(e);
	   if(e.source.name=="mobile"){
			var reg = /^1[34578]\d{9}$/;
			if(e.isValid==false){
				 $("#adpSign").text("请输入手机号码！");
				 e.errorText = "请输入手机号码！";
	        }else{
	       	 $("#adpSign").text("");
	        }
			if(e.value.length != 0){
				if(reg.test(e.value) == false){
					$("#adpSign").text("请输入正确的手机号码！");
					e.errorText = "请输入正确的手机号码！";
					e.isValid = false;
				}else{
					 $("#adpSign").text("");
				}
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
   }

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


</script>
</body>
</html>