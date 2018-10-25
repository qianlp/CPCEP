<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div style="display:none">
	<input name="status"  />
</div>
<!--此注释不允许删除读取字段用-->
<div class="col-md-7" name="FormMode" id="FormMode"
		style="width:100%;margin:auto;float:none;">
		<div class="widget-container fluid-height col-md-7-k"
			style="height:auto;border-radius:4px;">
		<div class="mbox-header">
			<span class="form-title" style="height:100%;line-height:45px;">
				${menu.menuName}
			</span>
		</div>
		<div class="mbox-body" style="height:100%;padding:10px;">
			<table style="width:95%;margin-top:5px;" id="tab">
				<tr>
					<td class="td_left" style="height:0px;"></td>
					<td class="td_right" style="height:0px;"></td>
					<td class="td_left" style="height:0px;"></td>
					<td class="td_right" style="height:0px;"></td>
				</tr>
				<tr>
					<td class="td_left">公式名称：</td>
					<td class="td_right"  colspan="3"><input name="typeName" value="${comObj.typeName}"
						class="mini-textbox"
						style="width:99%;" ></td>
				</tr>
				<tr>
					<td class="td_left">地址：</td>
					<td class="td_right"  colspan="3"><input name="address" value="${comObj.address}"
						class="mini-textbox"
						style="width:99%;" ></td>
				</tr>
				<tr>
					<td class="td_left">说明：</td>
					<td class="td_right" colspan="3"><input name="remark" value="${comObj.remark}"
						class="mini-textarea" style="width:99%;height:150px;" title="描述"></td>
				</tr>
			</table>
		</div>
	</div>
</div>
