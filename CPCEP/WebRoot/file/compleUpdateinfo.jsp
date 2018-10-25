<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
 <title>补充更新提醒详细</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@include file="../resource.jsp" %>
	<style>
		#profile .ptr{height:30px;font-size:12px}
		#profile,#profile .ptr .ptd{border:1px solid #cdcdcd;padding-left:5px;}
	</style>


</head>
<body text="#000000" bgcolor="#FFFFFF"  style='padding:10px;width:100%;height:95%;background:#94ACCD'>

<form action="" id="form1">
	<div style="width:1200px;margin:auto;padding-top:20px;">
	<div class="col-md-7" name="FormMode" id="FormMode" style="width:1200px;margin:20px auto;float:none;">
    <div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
	<div class="mbox-header" >
		<span class="form-title" style="height:100%;line-height:45px;" >
			<i class="o-host-application" style="background-position: -0px -20px;"></i>
			基本信息
		</span>
	</div>
	<div class="mbox-body"  style="height:100%;padding:10px;"> 
<div style="width:100%;text-align:center;"><h3>基本信息</h3></div>
<table cellspacing="1" cellpadding="1" id="tab" style="width:90%;margin:0 auto 20px auto;">
	<tr>
		<td class="td_left">项目编号：</td>
		<td class="td_right"><input name="projectNo"  class="mini-textbox" allowInput="false" required="true"  style="width:99%;" title="项目编号"></td>
		<td class="td_left">项目名称：</td>
		<td class="td_right"><input name="projectName"  class="mini-textbox" allowInput="false" required="true"  style="width:99%;" title="项目名称"></td>
	</tr>
	<tr>
		<td class="td_left">提醒人：</td>
		<td class="td_right"><input name="warnBy" class="mini-treeselect" url="${pageContext.request.contextPath}/admin/findOrgTree.action" allowInput="false" textField='text' valueField='id' required="true" style='width:99%;'></td>
		<td class="td_left">当前附件：</td>
		<td class="td_right" id="curFJ" colspan="3"></td>
	</tr>
	<tr>
		<td class="td_left">提醒内容：</td>
		<td class="td_right" colspan="3" style="padding:3px;"><input name="warnContent"  class="mini-textbox" allowInput="false"  style="width:99%;" title="提醒内容"></td>
	</tr>
</table>
</div></div></div>
<div class="col-md-7" name="AttMode" id="AttMode"  style="width:1200px;margin:20px auto;float:none;">
    <div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
	<div class="mbox-header"  style="border-bottom:2px solid #58B358">
		<span class="form-title" style="height:100%;line-height:45px;" >
			<i class="o-view-attachment" style="background-position: -0px -180px;"></i>
			公共附件
		</span>
	</div>
	<div class="mbox-body"  style="height:100%;padding:10px;">
		<div class="toolbar mini-toolbar">
			<div class="toolbar-title" style="line-height:30px;">&nbsp; 附件列表</div>
			<div class="toolbar-btn">
				
				<div style="display:inline-block">
					<a class="mini-button jsReGG" href="javascript:goViewAttach();">
						<span class="mini-button-text  mini-button-icon icon-print">预览</span>
					</a>
				</div>
			</div>
		</div>
		<div id='miniDataGrid' class='mini-datagrid' showPager="false"  showColumnsMenu='true' fitColumns='false' >
		<div property='columns'>
				<div type='checkcolumn' width='70' >选择</div>
				<div type='indexcolumn' width='70' >序号</div>
 				<div field='profilename' width='250' allowSort='true' headerAlign='left'  align='left' >文件名称</div>
                <div field='createBy' width='200' allowSort='true' headerAlign='left'  align='left'>上传人</div>
                <div field='createDate' width='200' allowSort='true' headerAlign='left'  align='left'>上传时间</div>
				<div field='profilesize' width='150' allowSort='true' headerAlign='left'  align='left'>大小</div>
				<div field='remark' width='238' allowSort='true' headerAlign='left'  align='left'>备注</div>
				<div field='uuid' visible='false'></div>
	    </div>
		</div>
      </div>
   </div>
</div>
<div class="Noprint mini-toolbar" style="left:0;width:100%;top:0;height:35px;color:#fff;border-top-color:#ebeff6;background-color:rgba(255,255,255)!important;border-top-width:1px;border-top-style:solid;position:fixed;text-align:center">
			<div class="pull-right" id="btnContR">
				<button style="margin:0px 7px 15px 7px" onclick="wnClose()" class="btn btn-md btn-default" type="button">关&nbsp;&nbsp;闭</button>
			</div>
        </div>
	</div>
  </form>
  
  <script language="JavaScript" type="text/javascript">
    mini.parse();
	var grid = mini.get('miniDataGrid');
  function initCompleUpdate(){
	var form = new mini.Form("#form1");
	//form.loading();
		$.ajax({
			url : "<%=path%>/profile/findCompleUpdateById.action",
			data : {
				"uuid" : "${param.uuid}"
			},
			success : function(text) {
				var data=mini.decode(text);//反序列化成对象  
				var pid = data.uuid;
				form.setData(data); //设置多个控件数据  
				form.unmask();
				$("#curFJ").html("<a style='color:#B94A48;' href='<%=path%>/profile/findDowloadCompleUpdateById.action?uuid="+pid+"' target='_blank'>" + data.profilename + "</a>");

				//这里可以重新指定自定义的数据装载路径
				grid.setUrl('<%=path%>/profile/findAllParentTaskUIDJson.action');
				grid.load({parentTaskUID:pid});
		grid.on("drawcell", function (e) {
            var record = e.record;
	        field = e.field;
            //field列，超连接操作按钮
            if (field == "profilename") {
                e.cellStyle = "text-align:center";
                e.cellHtml = '<a href="<%=path%>/profile/findDowloadCompleUpdateById.action?uuid=' +record.uuid + '" target="_blank" >'+record.profilename+'</a>';
            }
        });
			}
		});
	}
	initCompleUpdate();
	
	//获取id
	function GetDocID(){
		var arrID=$.map(grid.getSelecteds(),function(i){
			return i.uuid;
		});
		return arrID.join("^");
	}
	//预览
	function goViewAttach(){
		var uuid = GetDocID();
		var path = "${pageContext.request.contextPath}/profile/getViewUploadSwf.action?uuid=";
		getViewUpload(uuid,path);
	}
	
</script>
</body>
</html>
