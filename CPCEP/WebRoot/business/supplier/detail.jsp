<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>${menu.menuName}</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="stylesheet" type='text/css' href="../../css/form/loaders.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/bootstrap-theme.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css" />
	<link rel="stylesheet" type='text/css' href="../../css/form/form.css" />

	<%@include file="../../resource.jsp" %>
	<script src="../../js/form/language_ZN.js" type="text/javascript"></script>

	<script src="../../js/form/wf-mini.js" type="text/javascript"></script>

	<%
		request.setAttribute("isRead", 1);
	%>
	<script>
        var gDir="${pageContext.request.contextPath}";
        var gIsFile = -1;	// 是否使用原始附件控件
        var gIsRead = 1;
        var gIsShowSign = 1;
        var gForm = 1;
        var gIsEditDoc = 1;
        var gIsNewDoc = 1;
        function wfSubmitDoc(){
            if(arguments.length==1){gWQSagent=arguments[0]}
            wfSubDocStart();
        }

        function wfSaveDoc() {
			/*不做任何操作保存文档*/
            if(confirm("确定保存文档吗？")){
                try{gForm.wfFlowLogXML.value=XML2String(gWFLogXML)}catch(e){};
                //提交前执行事件
                wfSaveBefore();
                fnResumeDisabled(); //恢复部分域的失效状态，以保证保存时值不会变为空。
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '数据提交中...' //数据提交中...
                });
                setTimeout(function(){
                    gForm.submit();
                },500)
            }
        }

        function wfAgreeDoc(){
            //提交前处理数据
            if(confirm("您确定同意吗？")){
                if(arguments.length==1){gWQSagent=arguments[0]}
                gForm.wfTacheName.value="同意";
                wfSubDocStart();
            }
        }

        function wfRejectDoc(){
            if(confirm("您确定拒绝吗？")){
                //标注为拒绝
                gIsReject=1;
                gForm.wfTacheName.value="拒绝";
                wfSubDocStart();
            }
        }
	</script>
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
	<%@include file="../../workflow/workFlowMust.jsp" %>

	<div id="PageBody">
		<script type="text/javascript">
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
				<div class="mbox-header"><span class="form-title" style="height:100%;line-height:45px;">供应商信息</span></div>
				<input type="hidden" name="id" value="${view.id}"/>
				<div class="mbox-body" style="height:100%;padding:10px;">
					<fieldset style="margin: 10px;">
						<legend style="margin-left:10px;"><b>供应商基本信息</b></legend>

						<table style="width:95%;margin:5px;" id="tab">

							<tr>
								<td class="td_left">企业名称：</td>
								<td class="td_right">
									<input name="projectCode" class="mini-textbox" style="width:99%;" value="${view.name}" readonly="readonly"/>
								</td>
								<td class="td_left">用户名：</td>
								<td class="td_right">
									<input name="projectName" class="mini-textbox" value="${view.username}" style="width: 99%;" title="用户名" readonly="readonly"/>
								</td>
							</tr>

							<tr>
								<td class="td_left">纳税人登记号：</td>
								<td class="td_right">
									<input name="taxpayerNo" class="mini-textbox"  value="${view.taxpayerNo}" style="width:99%" readonly="readonly" />
								</td>

								<td class="td_left">法人代表：</td>
								<td class="td_right">
									<input name="corporations" class="mini-textbox" value="${view.corporations}" style="width: 99%;"  readonly="readonly"/>
								</td>

							</tr>


							<tr>
								<td class="td_left">企业性质：</td>
								<td class="td_right">
									<input name="nature" class="mini-combobox" style="width:99%;" value="${view.nature}"  url="${pageContext.request.contextPath}/business/bid/queryBsNatureEnterpriseList.action" readonly="readonly" />
								</td>

								<td class="td_left">注册资金（万元）：</td>
								<td class="td_right">
									<input name="registerCapital" class="mini-textbox" style="width:99%;" value="${view.registerCapital}" readonly="readonly"/>
								</td>
							</tr>

							<tr>
								<td class="td_left">注册时间：</td>
								<td class="td_right">
									<input name="registerTime" class="mini-textbox"  readonly="readonly" value="<fmt:formatDate value="${view.registerTime}" pattern="yyyy-MM-dd"/>" style="width:99%" />
								</td>

								<td class="td_left">公司联系人：</td>
								<td class="td_right">
									<input name="contacts" class="mini-textbox" readonly="readonly" value="${view.contacts}" style="width: 99%;" />
								</td>
							</tr>

							<tr>
								<td class="td_left">通信地址：</td>
								<td class="td_right">
									<input name="contactAddress" class="mini-textbox" readonly="readonly"  value="${view.contactAddress}" style="width:99%" />
								</td>

								<td class="td_left">公司电话：</td>
								<td class="td_right">
									<input name="phon" class="mini-textbox" readonly="readonly" value="${view.phon}" style="width: 99%;" />
								</td>
							</tr>

							<tr>
								<td class="td_left">手机号码：</td>
								<td class="td_right">
									<input name="mobile" class="mini-textbox"  readonly="readonly" value="${view.mobile}" style="width:99%" />
								</td>

								<td class="td_left">公司传真：</td>
								<td class="td_right">
									<input name="fax" class="mini-textbox" readonly="readonly" value="${view.fax}" style="width: 99%;" />
								</td>
							</tr>

							<tr>
								<td class="td_left">公司邮箱：</td>
								<td class="td_right">
									<input name="email" class="mini-textbox" readonly="readonly"  value="${view.email}" style="width:99%" />
								</td>

							</tr>
							<tr>
							<td class="td_left">供货范围：</td>
							<td class="td_right" >
									<textarea class="mini-textarea" name="materialName" id="materialName"  value="${view.materialName}"  readonly="readonly"  style="width:99%"></textarea>
									<textarea class="mini-textarea" name="materialId" id="materialId" value="${view.materialId}"  style="display: none" readonly="readonly" ></textarea>
									<a class="mini-button" plain="true" iconCls="icon-add" onclick="onSelectQuality()" readonly="readonly"  style="display: none">选择供货范围</a>
							</td>
						</tr>
						</table>
					</fieldset>
				</div>
			</div>

			<div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;margin-top:20px;">
				<div class="mbox-header"><span class="form-title" style="height:100%;line-height:45px;">证件信息</span></div>
				<div class="mbox-body" style="height:100%;padding:10px;">
					<fieldset style="margin: 10px;">
						<legend style="margin-left:10px;"><b>证件信息</b></legend>

						<table style="width:95%;margin:5px;" id="tab2">

							<tr>
								<td class="td_left">社会统一信用代码：</td>
								<td class="td_right">
									<input name="license" class="mini-textbox" style="width:99%;" value="${view.license}" readonly="readonly"/>
								</td>
<!-- 								<td class="td_left">税务登记证：</td> -->
<!-- 								<td class="td_right"> -->
<!-- 									<input name="taxCertificate" class="mini-textbox" value="${view.taxCertificate}" style="width: 99%;"/> -->
<!-- 								</td> -->
								<td class="td_left">专业能力资质证书：</td>
								<td class="td_right">
									<input name="certificate" class="mini-textbox" value="${view.certificate}" style="width: 99%;" readonly="readonly" />
								</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td class="td_left">组织机构代码证：</td> -->
<!-- 								<td class="td_right"> -->
<!-- 									<input name="orgCode" class="mini-textbox"  value="${view.orgCode}" style="width:99%" /> -->
<!-- 								</td> -->

								
<!-- 							</tr> -->
						</table>
					</fieldset>
				</div>
			</div>

			<div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;margin-top:20px;">
				<div class="mbox-header"><span class="form-title" style="height:100%;line-height:45px;">开票信息</span></div>
				<div class="mbox-body" style="height:100%;padding:10px;">
					<fieldset style="margin: 10px;">
						<legend style="margin-left:10px;"><b>开票信息</b></legend>

						<table style="width:95%;margin:5px;" id="tab3">

							<tr>
								<td class="td_left">企业开票名称：</td>
								<td class="td_right">
									<input name="billingName" class="mini-textbox" style="width:99%;" value="${view.billingName}" readonly="readonly"/>
								</td>
								<td class="td_left">银行账号：</td>
								<td class="td_right">
									<input name="bankAccount" class="mini-textbox" value="${view.billingBankAccount}" style="width: 99%;" readonly="readonly"/>
								</td>
							</tr>

							<tr>
								<td class="td_left">开户行名称：</td>
								<td class="td_right">
									<input name="bankName" class="mini-textbox"  value="${view.billingBankName}" style="width:99%" readonly="readonly" />
								</td>

								<td class="td_left">纳税人识别号：</td>
								<td class="td_right">
									<input name="billingTaxpayerId" class="mini-textbox" value="${view.billingTaxpayerId}" style="width: 99%;" readonly="readonly" />
								</td>
							</tr>

							<tr>
								<td class="td_left">开票地址：</td>
								<td class="td_right">
									<input name="address" class="mini-textbox"  value="${view.billingAddress}" style="width:99%" readonly="readonly" />
								</td>

								<td class="td_left">联系电话：</td>
								<td class="td_right">
									<input name="phone" class="mini-textbox" value="${view.billingPhone}" style="width: 99%;"  readonly="readonly"/>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</div>
		</div>

		<jsp:include page="plug/attachmentView.jsp" />
		<%--<div class="col-md-7" name="FormMode" id="FileMode" style="width:100%;margin:20px auto;float:none;display:none;">--%>
			<%--<div class="widget-container fluid-height col-md-7-k"--%>
				 <%--style="height:auto;border-radius:4px;padding-bottom:3px;">--%>
				<%--<div class="mbox-header">--%>
				<%--<span class="form-title" style="height:100%;line-height:45px;">--%>
					<%--附件 </span>--%>
				<%--</div>--%>
				<%--<div class="mbox-body" style="padding:5px;">--%>

				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>

	</div>

	<%@include file="../../toolbar/flowFoot.jsp" %>
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
        initAttachmentGrid("${pageContext.request.contextPath}/business/attachments.action?uuid=${view.id}", columns);
        $("#btnContL").append("<button class='btn btn-md btn-default' type='button' onclick='checkSupplier(true);' style='margin:0px 7px 15px 7px'>通&nbsp;&nbsp;过</button>");
        $("#btnContL").append("<button class='btn btn-md btn-default' type='button' onclick='checkSupplierFail(8);' style='margin:0px 7px 15px 7px'>拒&nbsp;&nbsp;绝</button>");
	});
  
	 //审核失败
    //根据id参数删除文档
   function checkSupplierFail(checkStatus){
	   var id = $("input[name=id]").val();
	   mini.open({
		   url:gDir+"/business/supplier/contentSupplier.jsp",
		   title:"信息",
		   width:300,
		   height:200,
		   showCloseButton:true,
		   ondestroy:function(data){
			     console.log(data);
			     if(data=="close"){
			    	 grid.reload(); 
			     }else{
				    $.ajax({
	                   url: gDir+"/business/check.action?id="+id + "&checkStatus=" + checkStatus+"&content="+data,
	                   success:function(){
	                       mini.unmask(document.body);
	                       mini.alert("操作成功！");
	                       grid.reload();
	                   }
	               });
			  }
		   }
		   
	   });
   }
	 
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
                        mini.alert("操作成功！", "提示",function() {
                        	window.opener.location.reload();
                        	self.close();
						});
                    }
                });
            }
            
        });
//         setTimeout(function(){
//        	 	self.close();
//        	},2500);
        

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
	        return "社会统一信用代码";
		} 
// 		else if(fileType == 5) {
// 	        return "税务登记证";
// 		} else if(fileType == 6) {
// 	        return "组织机构代码证";
// 		} 
		else if(fileType == 7) {
	        return "专业能力资质证书";
		}
		return "<font color='red'>未知文件</font>"
	}

	function download(cell) {
	    console.log(cell);
		var url = "${pageContext.request.contextPath}/business/download.action?id=" + cell.row.id;
		return "<a href='" + url + "'>" + cell.value + "</a>"
	}
</script>