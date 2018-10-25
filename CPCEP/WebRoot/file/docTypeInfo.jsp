<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script language="JavaScript" type="text/javascript">

	function toSave(){
		form1.doctype.value = "2";//文档类型
		form1.submit();
	}
</script>
<style>
	#tab .td_left{
		width:150px;
	}
	#tab .td_right{
		width:250px;
	}
</style>
<div style="display:none">
	<input name="doctype" value="${comObj.doctype}" />
</div>
<!--此注释不允许删除读取字段用-->
<div class="col-md-7" name="FormMode" id="FormMode"
		style="width:100%;margin:auto;float:none;">
		<div class="widget-container fluid-height col-md-7-k"
			style="height:auto;border-radius:4px;">
		<div class="mbox-header">
			<span class="form-title" style="height:100%;line-height:45px;">
				文档类型维护
			</span>
		</div>
		<div class="mbox-body" style="height:100%;padding:10px;">
			<table style="width:97%;margin-top:5px;" id="tab">
				<tr>
					<td class="td_left">文档类型名称：</td>
					<td class="td_right"  colspan="3"><input name="name" value="${comObj.name}"
						class="mini-textbox" emptyText='请输入文档类型名称' 
						style="width:99%;" title="项目文档名称"></td>
				</tr>
				<tr>
					<td class="td_left">描述：</td>
					<td class="td_right" colspan="3"><input name="remark" value="${comObj.remark}"
						class="mini-textarea" style="width:99.9%;" title="描述"></td>
				</tr>
			</table>
		</div>
	</div>
</div>
