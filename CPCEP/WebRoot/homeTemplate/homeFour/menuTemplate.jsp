<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<style>
.am-dropdown-content{
	cursor:pointer;
}
</style>
<header class="am-topbar am-topbar-inverse admin-header">
		<div class="am-topbar-brand">
			<a href="javascript:;" class="tpl-logo"> <img src="${pageContext.request.contextPath}/homeTemplate/homeFour/img/logo.png"
				alt="">
			</a>
		</div>
		<button
			class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
			data-am-collapse="{target: &#39;#topbar-collapse&#39;}">
			<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
		</button>
		<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
			<ul
				class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list tpl-header-list">
				<li class="am-dropdown" data-am-dropdown=""
					data-am-dropdown-toggle=""><a
					class="am-dropdown-toggle tpl-header-list-link" href="javascript:;">
						<span class="tpl-header-list-user-ico"> </span><span
						class="tpl-header-list-user-nick"></span>
				</a></li>
				<li class="am-dropdown" data-am-dropdown=""
					data-am-dropdown-toggle="">
					<a class="am-dropdown-toggle tpl-header-list-link" href="javascript:;">
						<span class="tpl-header-list-user-ico">
						<s:if test="#session.imgPath eq '' ">
							<img id="bigUserImg" src="${pageContext.request.contextPath}/img/common/pic_user.gif">
						</s:if>
						<s:else>
							<img id="bigUserImg" src="${pageContext.request.contextPath}/admin/showPicture.action?userId=${session.user.uuid}">
						</s:else>
						</span>
						<span class="tpl-header-list-user-nick">${session.user.userName}</span>
					</a>
					<ul class="am-dropdown-content">
						<li onclick="location.href='${pageContext.request.contextPath}/homeTemplate/homeFour/msgInfo.jsp'"><a href="#"><span class="am-icon-bell-o"></span> 消息中心</a></li>
						<li onclick="openPwd()"><a href="#"><span class="am-icon-cog"></span> 修改密码</a></li>
						<li onclick="location.href='${pageContext.request.contextPath}/homeTemplate/homeFour/personConfig.jsp'"><a href="#"><span class="am-icon-bell-o"></span> 设置</a></li>
						<li onclick="location.href='${pageContext.request.contextPath}/admin/loginOut.action'"><a href="#"><span class="am-icon-power-off"></span>退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</header>
<script>
	var win=null;
	function openPwd(){
		var path=gDir+"/user/updatePassword.jsp";
		win=mini.open({
			title:"修改密码",
			url:path,
			width: 400,
			allowResize:false,
	    	height: 175
		});
	}
		
	function closeWin(){
		if(win){
			win.destroy();
		}
	}
</script>