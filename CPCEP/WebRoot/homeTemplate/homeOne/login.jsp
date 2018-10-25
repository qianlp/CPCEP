<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>

<title>系统登录</title>
<link href="${pageContext.request.contextPath}/js/login/style_log.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/login/style.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/login/userpanel.css"/>
 <%@include file="../../resource.jsp" %>
</head>

<body class="login" mycollectionplug="bind">
<div class="login_m">
<div class="login_logo"><img src="${pageContext.request.contextPath}/js/login/logo.png" /></div>
<div class="login_boder">

<form method="post" action="${pageContext.request.contextPath}/admin/login.action" name="login">
<div class="login_padding" id="login_model">

  <h2>用  户  名</h2>
  <label>
    <input name="user.userName" class="txt_input"  placeholder="请输入用户名" />
  </label>
  <h2>密  码</h2>
  <label>
    <input type="password" name="user.userPassword" class="txt_input"  placeholder="请输入密码" />
  </label>
	<div class="rem_sub_l">
		<!-- <input type="checkbox" name="checkbox" id="save_me"/>
		  <label for="checkbox">记住我</label> -->
		<label style="color: red;"><s:fielderror/></label>
	</div>
        <p class="forgot"><a id="iforget" href="javascript:void(0);" onclick="toPwdForget()">忘记密码?</a></p> 
  <div class="rem_sub">

   <label>
      <input type="submit" class="sub_button" name="button" id="button" value="登    录" style="opacity: 0.7;"/>
   </label>
	  <label>
		  <input type="button" class="sub_button" onclick="window.location.href='${pageContext.request.contextPath}/business/supplierRegisterPage.action';" name="register" id="register" value="供应商注册" style="opacity: 0.7;margin-right: 10px"/>
	  </label>
  </div>
</div>
</form>

<script type="text/javascript">
   var pwd=null;
  function toPwdForget(){
	 pwd= mini.open({
		   url:"${pageContext.request.contextPath}/business/pwdForget.action",
	       title:"手机号找回密码",
	       width:560,
	       height:400,
		   showCloseButton:true,
		   showMaxButton:true,
	    		
	  });
	  
  }
</script>
<!--login_padding  Sign up end-->
</div><!--login_boder end-->
</div><!--login_m end-->
 <br/> <br/><br/>

<p align="center">国能中电:  010-57839977 </p>
<p align="center">所有版权归属 <a>北京华胜龙腾</a> </p>




</body>
</html>