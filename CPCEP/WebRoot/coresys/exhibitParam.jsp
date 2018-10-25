<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<title>新增或更新数据展现配置</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta name="content-type" content="text/html; charset=UTF-8">
<%@include file="../resource.jsp" %>
<style type="text/css">
html,body,form {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
}

.mini-listbox-item td {
	cursor: pointer;
}
</style>
</head>

<body text="#000000" bgcolor="#FFFFFF">
	<form method="post" action="${pageContext.request.contextPath}/admin/exhibitParamJson.action" name="form1" id="form1">
		<div style="display:none">
			<input name="uuid" value="${exhibitParamModel.uuid}" />
			<input name="createBy" value="${exhibitParamModel.createBy}" />
			<textarea name="exhibitHtmlHead" id="HtmlHead">
				<c:if test="${not empty exhibitParamModel.exhibitHtmlHead}">
					${exhibitParamModel.exhibitHtmlHead}
				</c:if>
				<c:if test="${empty exhibitParamModel.exhibitHtmlHead}">
<script type='text/javascript'>
	//当打开方式为window.open时使用
	var gWidth=window.screen.width;
	//当打开方式为window.open时使用
	var gHeight=window.screen.height;

	function CommonOpenDoc(strPathUrl) {
		winH = screen.height - 100;//高度
		winH = winH == 0 ? (screen.height - 100) : winH;
		winW = screen.width - 20;//宽度
		winW = winW == 0 ? (screen.width - 20) : winW;
		if (winH > 0) {
			var T = (screen.height - 100 - winH) / 2
		} else {
			var T = 0
		}
		if (winW > 0) {
			var L = (screen.width - 20 - winW) / 2
		} else {
			var L = 0
		}
		var pstatus = "height="
				+ winH
				+ ",width="
				+ winW
				+ ",top="
				+ T
				+ ",left="
				+ L
				+ ",toolbar=no,menubar=no,location=no,status=yes,resizable=yes,scrollbars=yes";
		window.open(strPathUrl, '_blank', pstatus);
	}
</script>
				</c:if>
			</textarea>
			<textarea name="exhibitHtmlBody" id="HtmlBody">
				<c:if test="${not empty exhibitParamModel.exhibitHtmlBody}">
					${exhibitParamModel.exhibitHtmlBody}
				</c:if>
				<c:if test="${empty exhibitParamModel.exhibitHtmlBody}">
<!--撑满页面-->
<div class='mini-fit' style='width:100%;height:100%;'>
	<div id='miniDataGrid' class='mini-datagrid' style='width:100%;height:100%;' idField='unid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true'
		 showColumnsMenu='true' fitColumns='true'>
		<div property='columns'>
			<div type='indexcolumn'></div>
			<div type='checkcolumn'></div>
			<div field='projectNo' 		width='200' allowSort='true' headerAlign='left' align='left' renderer='CommonRowLink'>项目编号</div>
			<div field='projectName' 	width='200' allowSort='true' headerAlign='left' align='left' >项目名称</div>
			<div field='createBy' 		width='150' allowSort='true' headerAlign='left' align='left' >制单人</div>
			<div field='createDate' 	width='150' allowSort='true' headerAlign='left' align='left' >制单时间</div>
			<div field='wfStatus' 		width='150' allowSort='true' headerAlign='left' align='left' renderer='CommonFlowStatus'>流程状态</div>
			<div field='curUser' 		width='100' allowSort='true' headerAlign='left' align='left' >当前处理人</div>
			<div field='uuid' 			width='50'  visible='false' ></div>
		</div>
	</div>
</div>
<script type='text/javascript'>
	mini.parse();
	var grid = mini.get('miniDataGrid');
	//这里可以重新指定自定义的数据装载路径
	grid.setUrl(gDir+ '/admin/loadListForPageExhibit.action');
	grid.load({menuId : menuId,sortField:"createDate",sortOrder:"desc",uuid : uuid});
</script>
				</c:if>
			</textarea>
			<textarea name="exhibitHtmlSearch" id="HtmlSearch">
				<c:if test="${not empty exhibitParamModel.exhibitHtmlSearch}">
					${exhibitParamModel.exhibitHtmlSearch}
				</c:if>
				<c:if test="${empty exhibitParamModel.exhibitHtmlSearch}">
<!-- 
	search_field:属性说明
	--------------------
	Operator：查询条件（ 特殊“@”=包含  ）其他符号（=,>=,<=,!=）
	DataType：类型 text , number , date , object(一般用于特殊条件：比如 is null)
	暂不支持小组查询
-->
<!-- 需要查询时放开
<div class="search_body" id="search_body">
	<div class="search_content">
		<div class="search_title">项目编号：</div>
		<div class="search_field" Operator="@" DataType="text" Item="">
			<input name="projectNo" id="projectNo" class="mini-textbox">
		</div>
	</div>
	<div class="search_button">
		<a class="mini-button" tooltip="清空查询条件" plain="true" iconCls="icon-remove" onclick="ClearForm"></a>
		&nbsp;<a class="mini-button" iconCls="icon-search" onclick="CommonSearch">搜索</a>
	</div>
</div>
<script>
	//清空查询条件
	function ClearForm(){
		var searchForm = new mini.Form("#search_body");
		searchForm.reset();
	}
	/*
	描述：查询
	*/
	function CommonSearch(){
		var searchArr=[];
		$("#search_body .search_field").each(function(){
			var obj={};
			var name = $($(this).find("input")[0]).attr("id");
			obj.name = name.split("$")[0];
			obj.operator=$(this).attr("Operator");
			obj.dataType=$(this).attr("DataType");
			obj.value=mini.getbyName(obj.name).getValue();
			if(obj.value!=""){
				searchArr.push(obj);
			}
		});
		grid.load({search:mini.encode(searchArr),menuId:menuId,uuid : uuid});
	}
</script>
-->
				</c:if>
			</textarea>
		</div>
		<div class="mini-layout" style="width:100%;height:100%;" borderStyle="border:0">
			<div title="center" region="center" style="border:0;">
				<div id="miniTabs" class="mini-tabs" activeIndex="0" style="width:99%;height:100%;margin:0 auto" plain="true">
					<div title="基本内容">
						<fieldset style="padding-left:10px;padding-right:10px">
							<legend>
								<span>基本信息</span>
							</legend>
							<table border="0" cellpadding="1" cellspacing="2" style="width:100%;margin:0 auto">
								<tr>
									<td style="width:100px; text-align:right">模块名称：</td>
									<td><input name="modularName" value="${exhibitParamModel.modularName}" required="true" class="mini-textbox" vtype="maxLength:20" style="width:100%"></td>
								</tr>
								<tr>
									<td style="width:100px;text-align:right">表格名称：</td>
									<td><input name="tableName" value="${exhibitParamModel.tableName}" required="true" class="mini-textbox" vtype="maxLength:20" style="width:100%"></td>
									<td style="text-align:right">关联菜单：</td>
									<td><input name="menuId" value="${exhibitParamModel.menuId}" required="true" class="mini-treeselect" multiSelect="false"  url="${pageContext.request.contextPath}/admin/findAllMenuList.action" valueField="id" textField="text"  style="width:99%;" showClose="true" oncloseclick="onCloseClick"></td>
								</tr>
								<tr>
									<td style="text-align:right">权限过滤：</td>
									<td colspan="3"><input name="isPower" value="${exhibitParamModel.isPower}" class="mini-radiobuttonlist" data="[{id:'1',text:'是'},{id:'0',text:'否'}]"></td>
								</tr>
								<tr>
									<td style="width:100px;text-align:right">查询条件：</td>
									<td colspan="3"><input name="pageSearchAddress" value="${exhibitParamModel.pageSearchAddress}"  class="mini-textbox" style="width:100%"></td>
								</tr>
								<tr>
									<td style="width:100px;text-align:right">展现地址：</td>
									<td colspan="3"><input name="exhibitPath" value="${exhibitParamModel.exhibitPath}" allowInput="false"  class="mini-textbox" style="width:100%"></td>
								</tr>
								<tr>
									<td style="width:100px;text-align:right">使用备注：</td>
									<td colspan="3" rowspan="3"><input name="remark" value="${exhibitParamModel.remark}" class="mini-textarea" style="width:100%"></td>
								</tr>
							</table>
						</fieldset>
					</div>
					<div title="HTML Head" name="tabHTMLHEAD" url="${pageContext.request.contextPath}/coresys/menu_head.jsp"></div>
					<div title="HTML Body" name="tabHTMLBODY" url="${pageContext.request.contextPath}/coresys/menu_body.jsp"></div>
					<div title="HTML Search" name="tabHTMLSEARCH" url="${pageContext.request.contextPath}/coresys/menu_search.jsp"></div>
				</div>
			</div>
			<div title="south" region="south" showSplit="false" showHeader="false" height="38px" splitSize=1 borderStyle="border:0" style="overflow:hidden">
				<div class="mini-toolbar" style="border:0">
					<table style='width:100%;'>
						<tr>
							<td style='width:100%;white-space:nowrap;text-align:right'>
								<a class="mini-button" iconCls="icon-ok" plain="true" style="display:0" onclick="goSubmit()">保存</a> 
								<a class="mini-button" 	iconCls="icon-remove" plain="true" visible="true" onclick="goDelDoc()" id="remove">删除</a> 
								<a class="mini-button" iconCls="icon-cancel" plain="true" onclick="CloseWindow()">关闭</a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		
<script type="text/javascript">
	mini.parse();
	/*
		描述：删除菜单
	 */
	function goDelDoc() {
		mini.confirm("您确定删除展示配置吗？","提示",function(r){
			if (r=="ok"){
				var PathUrl ="${pageContext.request.contextPath}/admin/deleteExhibit.action";
			$.ajax({
				url : encodeURI(PathUrl),
				type : "post",
				dataType:"json",
				data:{"uuid":$("[name='uuid']").val()},
				success : function(data) {
					if(data.success){
						mini.alert(data.msg, "消息提示",function() {
									CloseWindow('close');
								});
					} else {
						mini.alert("删除失败！");
					}
				}
			});
			}
		});
	}
	$(function(){
		if($("[name=uuid]").val()==""){
			mini.getbyName("exhibitPath").setValue("/admin/findExhibitById.action?randomId=${requestScope.randomId}");
			$("#remove").hide();
		}
	});
	
	//保存提交
	function goSubmit(){
		var tabs = mini.get("miniTabs");
		var ifmHead = tabs.getTabIFrameEl(tabs.getTab("tabHTMLHEAD"));
		var ifmBody = tabs.getTabIFrameEl(tabs.getTab("tabHTMLBODY"));
		var ifmSearch = tabs.getTabIFrameEl(tabs.getTab("tabHTMLSEARCH"));
		if (ifmHead) {
			$("#HtmlHead").val($.trim(ifmHead.contentWindow.CM.getValue()));
		}
		if (ifmBody) {
			$("#HtmlBody").val($.trim(ifmBody.contentWindow.CM.getValue()));
		}
		if (ifmSearch) {
			$("#HtmlSearch").val($.trim(ifmSearch.contentWindow.CM.getValue()));
		}
		//获取表单序列化字符串,
	    //也就是将整个表单的输入数据转换为以&作为分隔符的多个key=value的字符串
	    var formData = $("#form1").serialize();
	    mini.confirm("确认要保存吗？", "操作提示", function(action){
			if(action=="ok"){
				mini.mask({
		            el: document.body,
		            cls: 'mini-mask-loading',
		            html: '数据保存中...'
		    	});
	    $.ajax({
	        //获取表单的action作为url
	        url: $("#form1").attr("action"),
	        type: "POST",
	        data: formData,//把序列化后的表单数据放到这里
	        dataType: "json",
	        async: true,
	        error: function(xhr,status,error){
	            mini.alert("请求出错!");
	        },
	        success: function(result){
	        	if(result.success){
	        		mini.unmask(document.body);
	        		mini.alert(result.msg, "消息提示",function() {
	        			CloseWindow('close');
					});
	        	}else{
	        		mini.unmask(document.body);
	        		mini.alert(result.msg);
	        	}
	        }
	    });
	    //这里一定要返回false来阻止表单的submit
	    return false;
	}});}
</script>
	</form>
</body>
</html>
