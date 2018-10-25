<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>

<title>系统登录</title>
<link href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/login.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all">
<link href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/demo.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all">
<%@include file="../../resource.jsp" %>
<style>
body{
	font-family:"微软雅黑";
	line-height:14px;
	font-size:13px;
}
a{
	cursor:pointer;
}
.login-aside{
	z-index:1000;
}
</style>
</head>
<body style="background:#274777">
<img src="${pageContext.request.contextPath}/homeTemplate/homeFour/img/banner_01.jpg" width="100%" height="100%"/>
<form method="post" action="${pageContext.request.contextPath}/admin/login.action" name="login">
<div>
<div class="banner">
<div class="login-aside">
  <div id="o-box-up"></div>
  <div id="o-box-down" style="table-layout:fixed;">
   <div class="fm-item">
	<center><img src="${pageContext.request.contextPath}/homeTemplate/homeFour/img/logo.png" style="margin-right:30px;margin-bottom:30px;    margin-top: 30px;"/></center>
  </div>
   <div class="fm-item">
	   <input type="text" name="user.userName"  placeholder="请输入账号" maxlength="100" id="username" class="i-text" >    
       <div class="ui-form-explain"></div>
  </div>
  <div style="width:100%;height:18px;"></div>
  <div class="fm-item">
	   <input type="password" name="user.userPassword" value="" placeholder="请输入密码" maxlength="100" id="password" class="i-text" >    
       <div class="ui-form-explain"></div>
  </div>
  <div class="fm-item" >
	   <a style="float: right;margin-right:20px;margin-top:10px;" href="#" onclick="toPwdForget()">忘记密码？</a>
  </div>
  <div class="fm-item" style="clear: both;margin-top:60px">
  	<a class="abtn" href="${pageContext.request.contextPath}/business/supplierRegisterPage.action"> 供应商注册<a/>
  	<a class="abtn1" onclick="toSubmit()">登录</a>
  </div>
  </div>

</div>
</div></div>
</form>
<div class="footer">
<div class="footerdiv"></div>
<div class="footerdiv">
<p><span style="margin-right: 30px;">国能中电能源集团有限责任公司</span><span style="margin-right: 30px;">电话：010-57839977</span><span>地址：北京市通惠河北路69号院4号楼</span>
</p></div>
  <div class="footerdiv"><span style="margin-right: 82px;">版权所有：国能中电 京ICP备13034556号-1</span><span>技术支持：北京华胜龙腾软件技术有限公司</span></div> 
</div>
<div style="clear: both;"></div>
<script type="text/javascript">
   var pwd=null;
  function toPwdForget(){
	 pwd= mini.open({
		   url:"${pageContext.request.contextPath}/business/pwdForget.action",
	       title:"手机号找回密码",
	       width:800,
	       height:500,
		   showCloseButton:true,
		   showMaxButton:true,
	    		
	  });
	  
  }
  
  function toSubmit(){
  	document.forms[0].submit();	
  }
</script>
</body></html>