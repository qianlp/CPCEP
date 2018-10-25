<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar navbar-fixed-top scroll-hide">
	<div class="container-fluid main-nav clearfix">
		<div class="nav-collapse" style="text-align:center;height:63px;">
			<div style="width:1200px;margin:auto;padding:0px 50px;">
				<div id="logoInfo"
					style="margin-top:10px;margin-left:10px;position:absolute;left:10px;width:160px;text-align:left;">
				</div>
				<ul id="navBar" class="nav" style="width:100%;text-align:center">
					
				</ul>
				<ul class="nav navbar-nav" id="userInfo"
					style="width:170px;height:66px;margin-top:0px">
					<li class="dropdown user hidden-xs"
						style="width:165px;position:absolute;top:0px"><a
						data-toggle="dropdown"
						style="padding-bottom:12px;font-weight:bold;line-height:40px"
						class="dropdown-toggle" href="javascript:userCard();">
							&nbsp;${session.user.userName}<b class="caret"></b>
					</a></li>
				</ul>
				<div class="uil-card" id="user_login_card" >
					<div class="uil-card-header">
						<div class="media">
							<a href="#" class="pull-left avatar-circle">
							<s:if test="#session.imgPath eq '' ">
								<img id="bigUserImg" src="${pageContext.request.contextPath}/img/common/pic_user.gif">
							</s:if>
							<s:else>
								<img id="bigUserImg" src="${pageContext.request.contextPath}${session.imgPath}">
							</s:else>
							</a>
							<div class="media-body">
								<h5 class="media-heading">
									<strong>${session.user.userName}</strong>
								</h5>
								<c:choose>
									<c:when test="${session.user.userType == 2}"><p class="fss">供应商</p></c:when>
									<c:when test="${session.user.userType == 0}"><p class="fss">超级管理员</p></c:when>
									<c:when test="${session.user.userType == 1}"><p class="fss">管理员</p></c:when>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="uil-card-body">
						<div class="mbm"></div>
						<div class="btn-group btn-group-justified">
							<a href="${pageContext.request.contextPath}/user/personConfig.jsp" class="btn"><i class="om-user"></i>个人</a> <a
								href="javascript:location.href='${pageContext.request.contextPath}/admin/loginOut.action'" class="btn"><i
								class="om-shutdown"></i>退出</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="menu-card" id="menu_card">
	<div class="navbar scroll-hide" style="margin-bottom:0px;">
		<div class="container-fluid main-nav clearfix">
			<div class="nav-collapse" style="text-align:center;height:63px;" id="menu_body" >
				
			</div>
		</div>
	</div>
</div>
<script>
var fpx = ($("body").innerWidth() - 850) / 2;
$("#menu_card").css("left",fpx+"px");

function userCard(){
	if($("#user_login_card").is(":hidden")){
		$("#user_login_card").show();   
	}else{
		$("#user_login_card").hide();   
	}
}

function showMenuCard(){
	if($("#menu_card").is(":hidden")){
		$("#menu_card").show();   
	}else{
		$("#menu_card").hide();   
	}
}
</script>
