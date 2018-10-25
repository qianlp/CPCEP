<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side" role="navigation">
	<div class="nav-close">
		<i class="fa fa-times-circle"></i>
	</div>
	<div class="sidebar-collapse">
		<ul class="nav" id="side-menu">
			<li class="nav-header">
				<div class="dropdown profile-element">
					<span>
					<s:if test="#session.imgPath eq '' ">
						<img id="bigUserImg" class="img-circle" id="userImg" width="64" height="64" src="${pageContext.request.contextPath}/img/common/pic_user.gif">
					</s:if>
					<s:else>
						<img id="bigUserImg" class="img-circle" id="userImg" width="64" height="64" src="${pageContext.request.contextPath}${session.imgPath}">
					</s:else>
					</span>
					<div style="color:#fff;cursor:pointer;" onclick="goAddTab('个人中心','/homeTemplate/homeTwo/userInfo.jsp')" >
						<span class="clear">
							<span class="block m-t-xs"><strong>${session.user.userName}</strong></span>
						</span>
					</div>
				</div>
				<div class="logo-element">
					<s:if test="#session.imgPath eq '' ">
						<img id="bigUserImg" class="img-circle" width="40" height="40" src="${pageContext.request.contextPath}/img/common/pic_user.gif">
					</s:if>
					<s:else>
						<img id="bigUserImg" class="img-circle" width="40" height="40" src="${pageContext.request.contextPath}${session.imgPath}">
					</s:else>
				</div>
			</li>
		</ul>
	</div>
</nav>
