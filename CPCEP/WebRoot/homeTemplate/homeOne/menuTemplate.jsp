<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
								<img id="bigUserImg" src="${pageContext.request.contextPath}/admin/showPicture.action?userId=${session.user.uuid}">
							</s:else>
							</a>
							<div class="media-body">
								<h5 class="media-heading">
									<strong>${session.user.userName}</strong>
								</h5>
								<p class="fss">
									<s:if test="#session.user.userType==null">
										职员
									</s:if>
									<s:elseif test="#session.user.userType==0">
										超级管理员
									</s:elseif>
									<s:elseif test="#session.user.userType==1">
										管理员
									</s:elseif>
								</p>
							</div>
						</div>
					</div>
					<div class="uil-card-body">
						<div class="mbm"></div>
						<div class="btn-group btn-group-justified">
							<a href="${pageContext.request.contextPath}/homeTemplate/homeOne/personConfig.jsp" class="btn"><i class="om-user"></i>个人</a> <a
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
	<div class="navbar scroll-hide" style="margin-bottom:0px;height:auto;">
		<div class="container-fluid main-nav clearfix" style="height:auto;">
			<div class="nav-collapse" style="text-align:center;" id="menu_body" >
				
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
