<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="tpl-left-nav tpl-left-nav-hover">
	<div class="tpl-left-nav-title">您好，${session.user.userName}</div>
	<div class="tpl-left-nav-list">
		<ul class="tpl-left-nav-menu">
		
		</ul>
	</div>
</div>
<script>
	var userType="${session.user.userType}";
	var treeData=null;
	var menuPath="${pageContext.request.contextPath}/admin/findHasRightMenus.action";
	if(userType=="2"){
		menuPath="${pageContext.request.contextPath}/admin/findSupplierMenu.action";
	}
	$.ajax({
		url : encodeURI(menuPath),
		cache : false,
		async : false,
		success : function(MenuText) {
			treeData=mini.arrayToTree(mini.decode(MenuText),"children","id","pid");
			initMenu();
		}
	});
	
	function initMenu(){
		var iconTArr=["am-icon-newspaper-o",
		"am-icon-cogs"
		];
		var htmlStr="";
		htmlStr+="<li class='tpl-left-nav-item'>";
		htmlStr+="<a href=\"${pageContext.request.contextPath}/"+(userType=="2"?"homeTemplate/homeFour/supplier.jsp":"index.jsp")+"\" class=\"nav-link active\"> <i class=\"am-icon-home\"></i> <span>首页</span></a>";
		htmlStr+="</li>";
		$.each(treeData,function(i){
			htmlStr+="<li class=\"tpl-left-nav-item\">"
			htmlStr+="<a href=\"#\" class=\"nav-link tpl-left-nav-link-list\">"
			if(iconTArr[i]){
				htmlStr+="<i class=\""+iconTArr[i]+"\"></i> <span>"+this.text+"</span>"
			}else{
				htmlStr+="<i class=\""+iconTArr[1]+"\"></i> <span>"+this.text+"</span>"
			}
			htmlStr+="<i class=\"am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate\"></i>"
			htmlStr+="</a>";
			if(this.children){
				htmlStr+=subMenu(this.children);
			}
			htmlStr+="</li>";
		})
		$(".tpl-left-nav-menu").html(htmlStr);
		$('.tpl-left-nav-link-list').on('click', function() {
        	$(this).siblings('.tpl-left-nav-sub-menu').slideToggle(80)
            .end()
            .find('.tpl-left-nav-more-ico').toggleClass('tpl-left-nav-more-ico-rotate');
    	})
	}
	
	function subMenu(data){
		var paramMenuId="${param.menuId}";
		var subHtml="<ul class=\"tpl-left-nav-sub-menu\" style=\""+(userType=="2"?"display:block":"")+"\">"
		$.each(data,function(){
			if(paramMenuId!=""){
				subHtml+="<li><a href=\"javascript:setMenuFram('"+this.id+"','"+this.text+"')\"><i class=\"am-icon-bookmark-o\"></i><span>"+this.text+"</span></a></li>";
			}else{
				subHtml+="<li><a href=\"javascript:location.href='${pageContext.request.contextPath}/menu.jsp?menuId="+this.id+"&menuName="+this.text+"'\"><i class=\"am-icon-bookmark-o\"></i><span>"+this.text+"</span></a></li>";
			}
		})
		subHtml+="</ul>";
		return subHtml;
	}
	
	function setMenuFram(id,text){
		$("#pMenuName").html(text);
		mini.get("pMenuFram").setUrl("${pageContext.request.contextPath}/admin/findHasRightMenu.action?menu.uuid="+id);
	}
</script>