<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>

<script src="../js/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="../js/ckeditor/config.js" type="text/javascript"></script>
<script type="text/javascript">
	//var parentCarId = $("[name=curDocId]").val();
	var gArrFile = [ {
		title : "图片列表",
		btn : [ {
			id : "js-Sz",
			text : "设置主题",
			class : "mini-button",
			onclick : "goImgPath('att_img')",
			iconCls : "icon-node"
		}, {
			id : "js-Up",
			text : "上传",
			class : "mini-button",
			onclick : "goUpload('att_img')",
			iconCls : "icon-upload"
		}, {
			id : "js-del",
			text : "删除",
			class : "mini-button",
			onclick : "goDelAttach('att_img')",
			iconCls : "icon-remove"
		}, {
			id : "js-show",
			text : "预览",
			class : "mini-button",
			onclick : "goViewAttach('att_img')",
			iconCls : "icon-node"
		} ],
		grid : {
			id : "att_img",
			multiSelect : true,
			url : gDir + "/profile/findAllAdenexaJson.action",
			style : "width:100%;height:auto",
			valueField : "uuid",
			showPager : false,
			showEmptyText : true,
			emptyText : "没有任何附件",
			columns : [ {
				type : "checkcolumn"
			}, {
				type : "indexcolumn",
				header : "序号",
				headerAlign : "center"
			}, {
				field : "filename",
				width : 180,
				header : "文件名称",
				headerAlign : "center",
				align : "left"
			}, {
				field : "createBy",
				width : 80,
				header : "上传人",
				headerAlign : "center",
				align : "center"
			}, {
				field : "createDate",
				width : 80,
				header : "上传时间",
				headerAlign : "center",
				align : "center",
				dateFormat : "yyyy-MM-dd H:mm"
			}, {
				field : "filesize",
				width : 80,
				header : "大小",
				headerAlign : "center",
				align : "center"
			} ]
		}
	}, {
		title : "附件列表",
		btn : [ {
			id : "js-Up1",
			text : "上传",
			class : "mini-button",
			onclick : "goUpload('att_att_news')",
			iconCls : "icon-upload"
		}, {
			id : "js-del1",
			text : "删除",
			class : "mini-button",
			onclick : "goDelAttach('att_att_news')",
			iconCls : "icon-remove"
		} , {
			id : "js-show",
			text : "预览",
			class : "mini-button",
			onclick : "goViewAttach('att_att_news')",
			iconCls : "icon-node"
		}],
		grid : {
			id : "att_att_news",
			multiSelect : true,
			url : gDir + "/profile/findAllAdenexaJson.action",
			style : "width:100%;height:auto",
			valueField : "uuid",
			showPager : false,
			showEmptyText : true,
			emptyText : "没有任何附件",
			columns : [ {
				type : "checkcolumn"
			}, {
				type : "indexcolumn",
				header : "序号",
				headerAlign : "center"
			}, {
				field : "filename",
				width : 180,
				header : "文件名称",
				headerAlign : "center",
				align : "left"
			}, {
				field : "createBy",
				width : 80,
				header : "上传人",
				headerAlign : "center",
				align : "center"
			}, {
				field : "createDate",
				width : 80,
				header : "上传时间",
				headerAlign : "center",
				align : "center",
				dateFormat : "yyyy-MM-dd H:mm"
			}, {
				field : "filesize",
				width : 80,
				header : "大小",
				headerAlign : "center",
				align : "center"
			} ]
		}
	} ];

	var gForm = document.forms[0];
	var gCkEditor = null;
	
	function afterLoad(){
		mini.getbyName("cby").setValue($("[name=createBy]").val());
		SelectSendBound();
		initCkEditor();
	}
	
	function goImgPath(id){
		var row=mini.get(id).getSelected();
		if(!row){
			mini.alert("请选中一张图片！")
			return;
		}
		var path="/profile/findDowloadAdenexaById.action?uuid="+row.uuid;
		$("[name=newImgAddress]").val(path);
		showTips();
	}
	
	function showTips() {
        mini.showTips({
            content: "<b>提示</b><br/>设置成功！<br/>",
            state: "success",
            x: "center",
            y: "center",
            timeout: 3000
        });
    }
	
	function initCkEditor(){
		if (gIsRead || gLoginUser!=$("[name=createBy]").val()) {
			CKEDITOR.config.readOnly = true;
		}
		if (gCkEditor == null) {
			gCkEditor = CKEDITOR.appendTo('editor', {
				customConfig : 'ckeditor_config_QC.js',
				customConfig : 'ckeditor_config.js',
				on : {
					instanceReady : function(ev) {
						gCkEditor.setData(gForm.newMessage.value);
					}
				}
			});
		}
	}
	
	function saveFun(){
		gForm.newMessage.value = gCkEditor.getData();
	}

	function toSave() {
		saveFun();
		var form = document.forms[0];
		form.submit();
	}
	
	var nums=0;
	function SelectSendBound(){
		var type=mini.getbyName("accordingScopeOf").getValue();
		if (type == "按部门") {
			$("#SelectDeptNames").show();
			$("#SelectPersons").hide();
			$("#SelectRoles").hide();
			$("[name=Roles]").val("");
			$("[name=Persons]").val("");
			$("[name=DeptNames]").val($("#sendRangeId").val());
			nums=1;
		} else if (type == "按个人") {
			$("#SelectPersons").show();
			$("#SelectDeptNames").hide();
			$("#SelectRoles").hide();
			$("[name=DeptNames]").val("");
			$("[name=Roles]").val("");
			$("[name=Persons]").val($("#sendRangeId").val());
			nums=2;
		} else if (type == "按角色") {
			$("#SelectRoles").show();
			$("#SelectPersons").hide();
			$("#SelectDeptNames").hide();
			$("[name=DeptNames]").val("");
			$("[name=Persons]").val("");
			$("[name=Roles]").val($("#sendRangeId").val());
			nums=3;
		} else {
			$("#SelectRoles").hide();
			$("#SelectPersons").hide();
			$("#SelectDeptNames").hide();
			$("[name=DeptNames]").val("");
			$("[name=Persons]").val("");
			$("[name=Roles]").val("");
			
			$("#sendRangeId").val("");
			$("#readId").val("");
		}
	}

	
	function checkEdit(choice) {
		var messages = [];
		var sendRangeIds = [];
		mini.open({
			url : "${pageContext.request.contextPath}/information/sendrange.jsp?choice=" + choice + "&nums="+ nums,
			title : "组织信息",
			width : 510,
			height : 476,
			allowResize : false,
			ondestroy : function(action) {
				if (action == "ok") {
					var iframe = this.getIFrameEl();
					var data = iframe.contentWindow.GetData();
					data = mini.clone(data); //必须
					if (data) {
						for (var i = 0; i < data.length; i++) {
							messages.push(data[i].text);
							sendRangeIds.push(data[i].id);
						}
						//去重
						sendRangeIds = $.unique(sendRangeIds);
						$("[name=" + choice + "]").val(messages.join("\n"));
						$("#sendRangeId").val(messages.join("\n"));
						$("#readId").val(sendRangeIds.join(","));
					}

				}
			}
		});
	}
</script>
<style>
#gTab .mini-panel-border {
	border: 0px;
}
</style>

<div style="display:none">
	<input name="newImgAddress" value="${comObj.newImgAddress}"> 
	<input name="status" />
	<textarea  name="newMessage" id="newMessage" >${comObj.newMessage}</textarea>
	<textarea  name="scopeOfUser" id="sendRangeId">${comObj.scopeOfUser}</textarea>
	<textarea  name="readId" id="readId">${comObj.readId}</textarea>
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
	style="width:100%;margin:auto;float:none;">
	<div class="widget-container fluid-height col-md-7-k"
		style="height:auto;border-radius:4px;">
		<div class="mbox-header">
			<span class="form-title"
				style="height:100%;line-height:45px;text-align:center;">新闻发布
			</span>
		</div>

		<div class="mbox-body" id="mbox-body"
			style="height:100%;padding:10px;">
			<table cellspacing="1" cellpadding="1" id="tab"
				style="width:90%;">
				<tr>
					<td class="td_left">发&nbsp;布&nbsp;人：</td>
					<td class="td_right">
						<input name="cby" class='mini-textbox' style="width:90%" />
					</td>
					<td class="td_left">部&nbsp;&nbsp;门：</td>
					<td class="td_right"><input name="dept" value="${docInfo.createDeptName}"
						class='mini-textbox' style="width:90%;" /></td>
				</tr>
				<tr>
					<td class="td_left">标&nbsp;&nbsp;题：</td>
					<td class="td_right" colspan=3><input name="name" id="Title"
						value="${comObj.name} " class='mini-textbox' style="width:96.3%;"></td>
				</tr>
				<tr>
					<td class="td_left">发送范围：</td>
					<td class="td_right" colspan=3><input name="accordingScopeOf"
						id="SendBound" 
						class="mini-radiobuttonlist" onvaluechanged='SelectSendBound'
						data='[{ "id": "所有人员", "text": "所有人员" },{ "id": "按部门", "text": "按部门" },{ "id": "按个人", "text": "按个人" },{ "id": "按角色", "text": "按角色" }]'
						style="border-width:0px"
						<s:if test='comObj==null'>
							value="所有人员"
						</s:if>
						<s:else>
							value="${comObj.accordingScopeOf}"
						</s:else>

						>
					</td>
				</tr>
				<tr id="SelectDeptNames" style="display:none">
					<td class="td_left">按&nbsp;部&nbsp;门：</td>
					<td class="td_right"  colspan=3>
						<textarea  name="DeptNames" readonly style="height:80px;width:60%" ></textarea>&nbsp;
						<input class="mini-button" iconCls='icon-user' id="js-seld"
						onclick="checkEdit('DeptNames')" type="button" text="选择部门" /> <!--只读模式隐藏--></td>
				</tr>
				<tr id="SelectPersons" style="display:none">
					<td class="td_left">按&nbsp;个&nbsp;人：</td>
					<td class="td_right" colspan=3>
						<textarea  name="Persons" readonly style="height:80px;width:60%" ></textarea>&nbsp;
						<input class="mini-button" iconCls='icon-user' id="js-selp"
						onclick="checkEdit('Persons')" type="button" text="选择人员" /> <!--只读模式隐藏--></td>
				</tr>
				<tr id="SelectRoles" style="display:none">
					<td class="td_left">按&nbsp;角&nbsp;色：</td>
					<td class="td_right"  colspan=3>
						<textarea  name="Roles" readonly style="height:80px;width:60%" ></textarea>&nbsp;
						<input class="mini-button" iconCls='icon-user' id="js-selr"
						onclick="checkEdit('Roles')" type="button" text="选择角色" /> <!--只读模式隐藏--></td>
				</tr>
				<tr>
					<td class="td_left">是否图片新闻：</td>
					<td class="td_right" colspan='3'><s:if test='comObj==null'>
							<input name="isNewsPicture" id="isNewsPicture" value="否"
								checked="checked" class="mini-radiobuttonlist"
								data='[{ "id": "是", "text": "是" },{ "id": "否", "text": "否" }]'
								style="border-width:0px">
						</s:if> <s:else>
							<input name="isNewsPicture" id="isNewsPicture" value="${comObj.isNewsPicture }"
									checked="checked" class="mini-radiobuttonlist"
									data='[{ "id": "是", "text": "是" },{ "id": "否", "text": "否" }]'
									style="border-width:0px">
						</s:else>
				</tr>
			</table>
		</div>
	</div>

	<br />
	<div class="widget-container fluid-height col-md-7-k"
		style="height:auto;border-radius:4px;">
		<div class="mbox-header">
			<span class="form-title"
				style="height:100%;line-height:45px;text-align:center;"> 新闻内容</span>
		</div>
		<div class="mbox-body" id="mbox-body" style="padding:5px;width:100%">
			<table cellspacing="1" cellpadding="1" id="tab"
				style="width:100%;margin:0 auto;">
				<tr>
					<td class="td_left" colspan=4>
						<div id='editor'
							style="overflow:hidden;"></div>
						<div id='read'
							style='color:black;padding-left:15px;width:90%;display:none'>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>



