<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="search_body" id="search_body">
	<div class="search_content">
		<div class="search_title">标题：</div>
		<div class="search_field" Operator="@" DataType="text" Item="">
			<input name="name" id="name" class="mini-textbox">
		</div>
	</div>
	<div class="search_content">
		<div class="search_title">发起人：</div>
		<div class="search_field" Operator="@" DataType="text" Item="">
			<input name="createBy" id="createBy" class="mini-textbox">
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
	mini.getbyName("name").setValue("");
	mini.getbyName("createBy").setValue("");
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
		//obj.name=$($(this).find("input")[0]).attr("name");
		obj.operator=$(this).attr("Operator");
		obj.dataType=$(this).attr("DataType");
		obj.value=mini.getbyName(obj.name).getValue();
		if(obj.value!=""){
			if(obj.dataType == 'date'){
				obj.value=mini.getbyName(obj.name).getText();
			}
			if(obj.name == 'expectStartDateStart' || obj.name == 'expectStartDateEnd'){
				obj.name = 'expectStartDate';
			}
			if(obj.name == 'expectEndDateStart' || obj.name == 'expectEndDateEnd'){
				obj.name = 'expectEndDate';
			}
			searchArr.push(obj);
		}
	});
	grid.load({search:mini.encode(searchArr),menuId:menuId});
}
</script>
