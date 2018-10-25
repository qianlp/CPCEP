<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	//
	function beforenodeselect(e) {
	    //禁止选中父节点
	    if (e.isLeaf == false){ 
	    	e.cancel = true;
	    }else{
	    	mini.getbyName("formCName").setValue(e.node.text);
	    	mini.getbyName("formEName").setValue(e.node.pageSubAddress);
	    	getField(e.node.pageSubAddress);
	    }
	}
	//增加行
	function addRow() {          
	    var newRow = {};
	    grid.addRow({});
	}
	//删除行
	function removeRow() {
	    var rows = grid.getSelecteds();
	    if (rows.length > 0) {
	        grid.removeRows(rows, true);
	    }
	}
</script>
<div style="display:none">
	<textarea id="fieldList" name="fieldList">
		${comObj.fieldList}
	</textarea>
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
	style="width:100%;margin:auto;float:none;">
	<div class="widget-container fluid-height col-md-7-k"
		style="height:auto;border-radius:4px;">
		<div class="mbox-header">
			<span class="form-title" style="height:100%;line-height:45px;">
				表单字段 </span>
		</div>
		<div class="mbox-body" style="height:100%;padding:10px;">
			<table id="tab" style="width:90%;margin-top:5px;margin-bottom:20px;">
				<tr>
						<td class="td_left" style="height:0px;"></td>
						<td class="td_right" style="height:0px;"></td>
						<td class="td_left" style="height:0px;"></td>
						<td class="td_right" style="height:0px;"></td>
				</tr>
				<tr>
					<td class="td_left">类别：</td>
					<td class="td_right" colspan="3"><input class="mini-treeselect" name="formId" id="formId"
						url="${pageContext.request.contextPath}/admin/findMenuByHasWF.action"
						required="true" style="width:100%" multiSelect="false"
						onbeforenodeselect="beforenodeselect" allowInput="false"
						showRadioButton="true" showFolderCheckBox="false" value="${comObj.menu.uuid}" /></td>
				</tr>
				<tr>
					<td class="td_left">中文名称：</td>
					<td class="td_right" colspan="3">
						<input name="formCName"  value="${comObj.formCName}" class="mini-textbox" 
						style="width:100%;"></td>
				</tr>
				<tr>
					<td class="td_left">英文名称：</td>
					<td class="td_right" colspan="3">
					<input name="formEName"  value="${comObj.formEName}" class="mini-textbox"
						style="width:100%;"></td>
				</tr>
			</table>
		</div>
	</div>
</div>
<div class="col-md-7" name="FormMode" id="GressMode"
	style="width:100%;margin:20px auto;float:none;">
	<div class="widget-container fluid-height col-md-7-k"
		style="height:auto;border-radius:4px;padding-bottom:3px;">
		<div class="mbox-header">
			<span class="form-title" style="height:100%;line-height:45px;">
				字段列表 </span>
		</div>
		<div class="mbox-body" style="padding:10px;">
			<div class="mini-toolbar toolbar-btn" style="border-bottom:0px;height:35px;width:100%;">
				<a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">增加</a>
		 		<a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
			</div>
			<div id="fieldStatus" class="mini-datagrid" style="height:300px"
				sortMode="client" showPager="false" allowCellEdit="true"
				allowCellSelect="true" multiSelect="false" editNextOnEnterKey="true"
				editNextRowCell="true" showModified="false">
				<div property="columns">
					<div type="indexcolumn" width="10"></div>
					<div field="name" headerAlign="center" allowSort="true" width="120">
						中文名称 <input property="editor" class="mini-textbox"
							style="width:100%;" />
					</div>
					<div field="id" headerAlign="center" allowSort="true" width="120">
						英文名称 <input property="editor" class="mini-textbox"
							style="width:100%;" />
					</div>
					<div field="datatype" width="60" headerAlign="center"
						type="comboboxcolumn">
						数据类型 <input property="editor" class="mini-combobox"
							style="width:100%;"
							data="[{id:'string',text:'字符'},{id:'int',text:'数字'},{id:'float',text:'浮点型'},{id:'date',text:'日期'},{id:'email',text:'邮箱'}]" />
					</div>
					<div field="minLength" width="60" headerAlign="center">最小长度
	            		<input property="editor" class="mini-spinner" allowLimitValue="false" style="width:100%;"/>
	            	</div>
	            	<div field="maxLength" width="60" headerAlign="center">最大长度
	            		<input property="editor" class="mini-spinner" allowLimitValue="false" style="width:100%;"/>
	            	</div>
					<div field="status" width="60" allowSort="true" headerAlign="center" type="comboboxcolumn">状态
	            		<input property="editor" class="mini-combobox" style="width:100%;" data="[{id:'edit',text:'编辑'},{id:'must',text:'必填'},{id:'read',text:'只读'},{id:'hide',text:'隐藏'}]" /> 
	            	</div>
	            	<div field="tip" width="120" headerAlign="center">提示
	            		<input property="editor" class="mini-textbox" style="width:100%;"/>
	            	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="htmlFind" style="display:none;"></div>
<script>
	mini.parse();
	var grid=mini.get("fieldStatus");
	if($("[name=fieldList]").val()!=""){
		grid.setData(mini.decode($("[name=fieldList]").val()).data);
	}
	
	function saveGrid(){
		var _fieldList=$.each(mini.get("fieldStatus").getData(),function(i,item){
			for(o in item){
				if(o.indexOf("_")>-1){delete item[o]}
			}
		});
		$("#fieldList").val( mini.encode( { total:_fieldList.length,data:_fieldList } ) );
	}
	
	function getField(address){
		var path=gDir+address;
		$.post(path,{},function(data){
			$("#htmlFind").html(htmlSubStr(data,"script","span"));
			var fieldArr=[];
			$("#htmlFind").find("input").each(function(){
				if(check($(this).attr("name"))!=""){
					var field={
							name:check($(this).attr("title")),
							id:check($(this).attr("name"))
					};
					fieldArr.push(field);
				}
			})
			
			$("#htmlFind").find("textarea").each(function(){
				if(check($(this).attr("name"))!=""){
					var field={
							name:check($(this).attr("title")),
							id:check($(this).attr("name"))
					};
					fieldArr.push(field);
				}
			})
			var field={
				name:"审批意见",
				id:"ID_ideaArea"
			};
			fieldArr.push(field);
			grid.setData(fieldArr);
		})
	}
	
	function check(o){
		if(!o){
			return "";
		}
		return o;
	}
	
	
	function htmlSubStr(str,reallyDo,replaceWith) {
		var e=new RegExp(reallyDo,"g");
		return str.replace(e, replaceWith);
	} 
	
	function toSave(){
		saveGrid();
		document.forms[0].submit();
	}
</script>
