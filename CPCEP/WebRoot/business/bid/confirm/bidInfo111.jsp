<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>定标</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/loaders.css" />
	<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/bootstrap-theme.css" />
	<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/bootstrap.flow.min.css" />
	<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/form.css" />
	<%@include file="../../../resource.jsp" %>
	<script src="${pageContext.request.contextPath}/js/form/language_ZN.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/form/wf-mini.js" type="text/javascript"></script>

</head>
<body text="#000000" bgcolor="#FFFFFF"  style='padding:0px;width:100%;height:100%;background:#f3f3f3;'>
<div id="bg"></div>
<div class="loader" id="bg-loader">
	<div class="loader-inner ball-spin-fade-loader">
		<div></div>
		<div1></div1>
		<div></div>
		<div></div>
		<div></div>
		<div></div>
		<div></div>
		<div></div>
	</div>
</div>
<form method="post" action="${pageContext.request.contextPath}${menu.actionAddress}" name="form1" id="form1" >

	<div id="PageBody">
		<script type="text/javascript">
			var gIsRead = 1, gIsFile = 2, gIsShowSign =1, gUserId = 1;
            //保存方法
            function toSave(){
                form1.status.value="1";//保存
                form1.submit();
            }
            function afterLoad(){

            };

            function onNodeClick(e) {
                var node = e.node;
                if (node.type != 'user') {
                    e.sender.setValue('');
                    e.sender.setText('');
                } else {
                    e.sender.setValue(node.text);
                    e.sender.setText(node.text);
                }
            }


		</script>
		<style>
			#gTab .mini-panel-border {
				border: 0px;
			}
		</style>
		<div style="display:none">

			<input name="status" style="display: none;"/>
		</div>
		<div class="col-md-7" name="FormMode" id="FormMode" style="width:100%;margin:auto;float:none;">
			<div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
				<div class="mbox-header"><span class="form-title" style="height:100%;line-height:45px;">招标基本信息</span></div>
				<input type="hidden" name="id" value="${view.bidId}"/>
				<div class="mbox-body" style="height:100%;padding:10px;">
					<fieldset style="margin: 10px;">
						<legend style="margin-left:10px;"></legend>
						<table style="width:95%;margin:5px;" id="tab">
							<tr>
								<td class="td_left">招标编号：</td>
								<td class="td_right">${view.code}</td>
								<td class="td_left">招标名称：</td>
								<td class="td_right">${view.name}</td>
							</tr>

							<tr>
								<td class="td_left">评标日期：</td>
								<td class="td_right">
									<input name="taxpayerNo" class="mini-textbox"  value="" style="width:99%" />
								</td>

								<td class="td_left">招标文件版本号：</td>
								<td class="td_right">${view.version}</td>

							</tr>
						</table>
					</fieldset>
				</div>
			</div>

			<div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;margin-top:20px;">
				<div class="mbox-header"><span class="form-title" style="height:100%;line-height:45px;">候选中标供应商</span></div>
				<div class="mbox-body" style="height:100%;padding:10px;">
					<div id="supplierGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" sizeList="[5,10,15,30]" pageSize="15" multiSelect="true" >
						<div property="columns">
							<div type="checkcolumn"></div>
							<div type="indexcolumn" headerAlign="center" align ="center">序号</div>
							<div field="name"width="100" allowSort="false" headerAlign="center" renderer="openDetail" align="center">供应商名称</div>
							<div field="tecBid" width="120" allowSort="true" headerAlign="center" align="center">技术评标</div>
							<%--<div field="registerEndTime" width="120" allowSort="false" headerAlign="center" align="center">商务报价</div>--%>
							<div field="finalOffer" width="120" allowSort="true" headerAlign="center" align="center">最终定价</div>
							<div field="rank" width="120" allowSort="false" headerAlign="center" align="center">排名</div>
							<div field="backup" width="120" allowSort="false" headerAlign="center" align="center" >备注</div>
							<div field="id" width="50" visible="false"></div>
						</div>
					</div>
				</div>
			</div>

			<div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;margin-top:20px;">
				<div class="mbox-header"><span class="form-title" style="height:100%;line-height:45px;">供应商调价</span></div>
				<div class="mbox-body" style="height:100%;padding:10px;">
					<fieldset style="margin: 10px;">
						<legend style="margin-left:10px;"></legend>

						<table style="width:95%;margin:5px;" id="tab3">

							<tr>
								<td class="td_left">企业开票名称：</td>
								<td class="td_right">
									<input name="billingName" class="mini-textbox" style="width:99%;" value=""/>
								</td>
								<td class="td_left">银行账号：</td>
								<td class="td_right">
									<input name="bankAccount" class="mini-textbox" value="" style="width: 99%;"/>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</div>
		</div>

		<jsp:include page="../../supplier/plug/attachmentView.jsp" />
	</div>

	<div class="host-footer">
		<div class="footer-bg"></div>
		<div class="footer-area">
			<div class="pull-left" id="btnContL">
			</div>
			<div class="pull-right" id="btnContR">
			</div>
		</div>
	</div>
	<script>
//        initPageEle();
	</script>
</form>
</body>
</html>
<script>
	$(function() {
	    var columns = [{
            type: "checkcolumn"
        }, {
            field : "id",
            visible : false
        }, {
            type : "indexcolumn",
            header : "序号",
            headerAlign : "center"
        }, {
            field : "fileName",
            width : 180,
            header : "文件名称",
            headerAlign : "center",
            align : "left",
			renderer : download
        }, {
            field : "type",
            width : 80,
            header : "文件类型",
            headerAlign : "center",
            align : "center",
			renderer : getFileType
        }, {
            field : "uploadTime",
            width : 80,
            header : "上传时间",
            headerAlign : "center",
            align : "center",
            dateFormat : "yyyy-MM-dd H:mm"
        }, {
            field : "size",
            width : 80,
            header : "大小",
            headerAlign : "center",
            align : "center"
        } ];
        initAttachmentGrid("${pageContext.request.contextPath}/business/attachments.action?uuid=", columns);

        // 供应商
        var grid = mini.get('supplierGrid');
        grid.setUrl(gDir + '/business/bidding/confirm/bidSupplier.action?bidFileId=${view.bidId}');
        grid.load();

        $("#btnContL").append("<button class='btn btn-md btn-default' type='button' onclick='checkSupplier(true);' style='margin:0px 7px 15px 7px'>通&nbsp;&nbsp;过</button>");
        $("#btnContL").append("<button class='btn btn-md btn-default' type='button' onclick='checkSupplier(false);' style='margin:0px 7px 15px 7px'>拒&nbsp;&nbsp;绝</button>");

        $("#bg").remove();
        $("#bg-loader").remove();
	});

    function checkSupplier(check){
        var title = "确认要审核选定供应商吗？";
        var checkStatus = 2;

        if(!check) {
            title = "确认要拒绝选定供应商吗？"
			checkStatus = 8;
		}
		var id = $("input[name=id]").val();
        if(id == null || id == undefined || id =="") {
            alert("没有找到供应商信息!");
            return ;
		}
        mini.confirm(title, "操作提示", function(action){
            if(action=="ok"){
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '操作中...'
                });
                $.ajax({
                    url: gDir+"/business/check.action?id="+ id + "&checkStatus=" + checkStatus,
                    success:function(){
                        mini.unmask(document.body);
                        mini.alert("操作成功！");
                    }
                });
            }
        });

    }

	function getFileType(cell) {
        var fileType = cell.row.type;
	    if(fileType == 1) {
	        return "业绩统计表";
		} else if(fileType == 2) {
	        return "业绩证明文件";
		} else if(fileType == 3) {
	        return "其他附件文件";
		} else if(fileType == 4) {
	        return "营业执照";
		} else if(fileType == 5) {
	        return "税务登记证";
		} else if(fileType == 6) {
	        return "组织机构代码证";
		} else if(fileType == 7) {
	        return "专业能力资质证书";
		}
		return "<font color='red'>未知文件</font>"
	}

	function download(cell) {
	    console.log(cell);
		var url = "/business/download.action?id=" + cell.row.id;
		return "<a href='" + url + "'>" + cell.value + "</a>"
	}
</script>