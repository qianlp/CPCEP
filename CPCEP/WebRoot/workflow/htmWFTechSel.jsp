<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>.mini-splitter-border{border:0}</style>
<div id="SelOrgLay" class="mini-layout" style="width:497px;height:375px;" borderStyle="border:0">
	<!--web判断隐藏(唯一选择、条件选择时显示)-->
	<div showCollapseButton=false showHeader=false region="west" style="border:0" allowResize="false" showSplitIcon="false" width="240" borderStyle="border:0">
		<div class="mini-tabs" activeIndex="0" style="width:100%;height:100%;border:0" plain="false" onactivechanged="changTab">
			<div title="环节名称" style="border:0">
				<div class="mini-splitter" vertical="true" style="width:228px;height:100%;border:0px" allowResize="false" handlerSize="0">
					<div size="28" showCollapseButton="false" style="border:0">
				<input id="selTacheName" style="width:100%" class="mini-combobox" textField="name" valueField="id" emptyText="点击选择环节名称" allowInput="false" onvaluechanged="wfSelTacheChange" />
					</div>
					<div showCollapseButton="false" style="border:0">
				<div id="tacheList" onitemclick="listNodeClick" class="mini-listbox" style="width:100%;height:100%;border:0" textField="name" valueField="id"></div>
					</div>
				</div>
			</div>
			<div title="组织机构" name="OrgTree" style="border:0" visible="${param.Org}">
				<ul id="orgTree" class="mini-tree" expandOnNodeClick="true" onnodeclick="treeNodeClick" style="width:100%;height:310px;" showTreeIcon="true" showCheckBox="false" idField="id"></ul>
			</div>
		</div>
	</div>
	<!--End-->
	<div showHeader=false region="center" style="border:0" showCollapseButton=false showSplitIcon=false width="230">
		<div class="mini-tabs" style="width:100%;height:100%;border:0" plain="false">
			<div title="已有人员列表" style="border:0">
				<div id="selectList" onitemclick="DelValue" class="mini-listbox" style="height:100%;" textField="name" valueField="id"></div>
			</div>
		</div>
	</div>
</div>
