<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<header id="navbar">
	<div id="navbar-container" class="boxed">
		<div class="navbar-header">
			<a href="javascript:location.reload()" class="navbar-brand"> <img
				src="img/logo.png" alt="Nifty Logo" class="brand-icon">
				<div class="brand-title">
					<span class="brand-text">Nifty</span>
				</div>
			</a>
		</div>
		<div class="navbar-content clearfix">
			<ul class="nav navbar-top-links pull-left">
				<li class="tgl-menu-btn"><a class="mainnav-toggle" href="#">
						<i class="demo-pli-view-list"></i>
				</a></li>
				<li class="dropdown"><a href="#" data-toggle="dropdown"
					class="dropdown-toggle"> <i class="demo-pli-bell"></i> <span
						class="badge badge-header badge-danger"></span>
				</a> <!--Notification dropdown menu-->
					<div class="dropdown-menu dropdown-menu-md">
						<div class="pad-all bord-btm">
							<p class="text-semibold text-main mar-no">您收到 9 条消息。</p>
						</div>
						<div class="nano scrollable">
							<div class="nano-content">
								<ul class="head-list">
									<!-- Dropdown list-->
									<li><a class="media" href="#">
											<div class="media-left">
												<img class="img-circle img-sm" alt="Profile Picture"
													src="img/profile-photos/9.png">
											</div>
											<div class="media-body">
												<div class="text-nowrap">Lucy sent you a message</div>
												<small class="text-muted">30 minutes ago</small>
											</div>
									</a></li>
								</ul>
							</div>
						</div>

						<!--Dropdown footer-->
						<div class="pad-all bord-top">
							<a href="javascript:getHtml('msgInfo.jsp');" class="btn-link text-dark box-block"> <i
								class="fa fa-angle-right fa-lg pull-right"></i>显示所有消息
							</a>
						</div>
					</div></li>
				<li class="mega-dropdown"><a href="#"
					class="mega-dropdown-toggle"> <i class="demo-pli-layout-grid"></i>
				</a>
					<div class="dropdown-menu mega-dropdown-menu">
						<div class="row">
							<div class="col-sm-4 col-md-3">
								<!--Mega menu list-->
								<ul class="list-unstyled">
									<li class="dropdown-header"><i
										class="demo-pli-file icon-fw"></i> Pages</li>
									<li><a href="#">Profile</a></li>
								</ul>
							</div>
						</div>
					</div></li>

			</ul>
			<ul class="nav navbar-top-links pull-right">
				<li id="dropdown-user" class="dropdown"><a href="#"
					data-toggle="dropdown" class="dropdown-toggle text-right"> <span
						class="pull-right"> <!--<img class="img-circle img-user media-object" src="img/profile-photos/1.png" alt="Profile Picture">-->
							<i class="demo-pli-male ic-user"></i>
					</span>
						<div class="username hidden-xs">${session.user.userName}</div>
				</a>


					<div class="dropdown-menu dropdown-menu-md dropdown-menu-right panel-default">
						<!-- User dropdown menu -->
						<ul class="head-list">
							<li><a href="#"> <i
									class="demo-pli-male icon-lg icon-fw"></i> 个人信息
							</a></li>
							<li><a href="javascript:getHtml('msgInfo.jsp');">
								<span class="badge badge-danger pull-right">9</span>
								<i class="demo-pli-mail icon-lg icon-fw"></i> 消息中心
							</a></li>
							<li><a href="#">
								<i class="demo-pli-gear icon-lg icon-fw"></i> 相关设置
							</a></li>
							<li><a href="#"> <i
									class="demo-pli-computer-secure icon-lg icon-fw"></i> 锁定
							</a></li>
						</ul>

						<!-- Dropdown footer -->
						<div class="pad-all text-right">
							<a href="${pageContext.request.contextPath}/admin/loginOut.action" class="btn btn-primary"> <i
								class="demo-pli-unlock"></i> 退出
							</a>
						</div>
					</div></li>
				<li><a href="#" class="aside-toggle navbar-aside-icon"> <i
						class="pci-ver-dots"></i>
				</a></li>
			</ul>
		</div>
	</div>
</header>
<div class="boxed">
	<nav id="mainnav-container">
		<div id="mainnav" >

			<!--Menu-->
			<!--================================-->
			<div id="mainnav-menu-wrap">
				<div class="nano">
					<div class="nano-content">

						<!--Profile Widget-->
						<!--================================-->
						<div id="mainnav-profile" class="mainnav-profile">
							<div class="profile-wrap">
								<div class="pad-btm">
									<s:if test="#session.imgPath eq '' ">
										<img id="bigUserImg" class="img-circle img-sm img-border"
											src="${pageContext.request.contextPath}/img/common/pic_user.gif"
											alt="Profile Picture">
									</s:if>
									<s:else>
										<img id="bigUserImg" class="img-circle img-sm img-border"
											src="${pageContext.request.contextPath}${session.imgPath}"
											alt="Profile Picture">
									</s:else>
								</div>
								<a href="#profile-nav" class="box-block" data-toggle="collapse"
									aria-expanded="false"> <span
									class="pull-right dropdown-toggle"> <i
										class="dropdown-caret"></i>
								</span>
									<p class="mnp-name">${session.user.userName}</p> <span
									class="mnp-desc">aaron.cha@themeon.net</span>
								</a>
							</div>
							<div id="profile-nav" class="collapse list-group bg-trans">
								<a href="#" class="list-group-item"> <i
									class="demo-pli-male icon-lg icon-fw"></i> 个人中心
								</a> <a href="#" class="list-group-item"> <i
									class="demo-pli-gear icon-lg icon-fw"></i> 相关设置
								</a>  <a href="${pageContext.request.contextPath}/admin/loginOut.action" class="list-group-item"> <i
									class="demo-pli-unlock icon-lg icon-fw"></i> 退出
								</a>
							</div>
						</div>


						<!--Shortcut buttons-->
						<!--================================-->
						<div id="mainnav-shortcut">
							<ul class="list-unstyled">
								<li class="col-xs-3" data-content="个人信息"><a
									class="shortcut-grid" href="#"> <i class="demo-psi-male"></i>
								</a></li>
								<li class="col-xs-3" data-content="锁定"><a
									class="shortcut-grid" href="#"> <i class="demo-psi-lock-2"></i>
								</a></li>
								<li class="col-xs-3" data-content="消息中心"><a
									class="shortcut-grid" href="javascript:getHtml('msgInfo.jsp');"> <i
										class="demo-psi-speech-bubble-3"></i>
								</a></li>
								<li class="col-xs-3" data-content="首页"><a
									class="shortcut-grid" href="javascript:getHtml('infoPage.jsp')"> <i class="demo-psi-home"></i>
								</a></li>
							</ul>
						</div>
						<ul id="mainnav-menu" class="list-group" >
							<li class="list-divider"></li>
						</ul>


						<!--Widget-->
						<!--================================-->
						<div class="mainnav-widget">

							<!-- Show the button on collapsed navigation -->
							<div class="show-small">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</nav>