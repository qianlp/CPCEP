<%@ page language="java" pageEncoding="UTF-8" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="/struts-tags" prefix="s" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE HTML>
<html>
<head>
    <title>综合评审</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <link rel="stylesheet" type='text/css' href="../../css/form/loaders.css"/>
         <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap-theme.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css"/>
     <link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/business/bid/workflow/form.css" />
    <%@include file="../../resource.jsp" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
   	<script src="${pageContext.request.contextPath}/js/form/language_ZN.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/business/bid/workflow/wf-mini.js" type="text/javascript"></script>
     <script>
        var gDir = "${pageContext.request.contextPath}";
        var gLoginUser = "${sessionScope.user.userName}";
        var menuId="402880e6658db7ec01658dcc6ad30000";
    </script> 
    	<script>
		function wfSubmitDoc(){
			if(arguments.length==1){gWQSagent=arguments[0]}
	         //form.wfStatus.value = "2";// 保存
	         var row = bidResultGrid.getSelected();
	         if(row==null||row==undefined){
	             showErrorTexts(["请选择建议中标供应商"]);
	             return ;
	         }
	         $("input[name=suggestSupplierBid]").val(row.supplierBidUuid);
	    	 bidResultGrid = bidResultGrid.getData();
	    	 $("#bidResultGrid").val(mini.encode(bidResultGrid));
	    	 console.log($("#bidResultGrid").val());
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
    <style type="text/css">
	body{
	    overflow:scroll;
	    overflow-x:hidden;
	}
</style>
</head>
<body  bgcolor="#FFFFFF" style="padding:0px;width:100%;height:100%;background:#f3f3f3;">
 <div id="bg"></div>
<div class="loader" id="bg-loader">
    <div class="loader-inner ball-spin-fade-loader">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
    </div>
</div> 
<form method="post" action="${pageContext.request.contextPath}/business/bid/saveOrUpdateCompBidEval.action" name="form1" id="form1">

	<script src="${pageContext.request.contextPath}/business/bid/workflow/upLoad.js" type="text/javascript"></script>
    <div style="display:none">
       <input name="biddingFileUuid" class="mini-textbox" value="${bidFile.uuid}"/>
     <%--  <textarea id="bidResultGrid" name="bidResultGrid" value="${compBidEval.bidResultGrid}"></textarea> --%>
       <input name="suggestSupplierBid" class="mini-textbox" value="${compBidEval.suggestSupplierBid}"/>
       <input name="uuid" class="mini-textbox" value="${compBidEval.uuid}"/>
       <%--  <input name="wfStatus" class="mini-textbox" value="${compBidEval.wfStatus}"> --%>
        <input name="menuId" class="mini-textbox" value="402880e6658db7ec01658dcc6ad30000">
   </div>
     <!-- 加入流程 -->
	<%@include file="/business/bid/workflow/workFlowMust.jsp" %>
 <div id="PageBody">

        <div class="col-md-7" name="FormMode" id="FormMode" style="width:100%;margin:auto;float:none;">
            <div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
                <div class="mbox-header">
                    <span class="form-title" style="height:100%;line-height:45px;">
                                                                        综合评审
                    </span>
                </div>
                <div class="mbox-body" style="height:100%;padding:10px;">
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>基本信息</b></legend>
                        <table style="width:95%;margin:5px;" id="tab">
                            <tr>
                                <td class="td_left">评标人：</td>
                                <td class="td_right">
                                    <s:if test='comBidEval==null'>
                                        <input name="createBy" value="${session.user.userName}" class="mini-textbox"
                                               allowInput="false" readonly="true" style="width:99%;" title="编制人">
                                        <input name="createUuid" value="${session.user.uuid}"
                                               class="mini-textbox hidden" allowInput="false" readonly="true"
                                               style="width:99%;" title="编制人ID">
                                    </s:if>
                                    <s:else>
                                        <input name="createBy" value="${comBidEval.createBy}" allowInput="false"
                                               readonly="true" class="mini-textbox" style="width:99%;" title="编制人">
                                        <input name="createUUid" value="${comBidEval.createUuid}" allowInput="false"
                                               readonly="true" class="mini-textbox hidden" style="width:99%;"
                                               title="编制人ID">
                                    </s:else>
                                </td>
                                <td class="td_left">评标时间：</td>
                                <td class="td_right">
                                    <s:if test="comBidEval==null||comBidEval.createDate==null">
                                        <input name="createDate" allowInput="false" readonly="true"
                                               value="<%=(new java.util.Date()).toLocaleString()%>"
                                               class="mini-datepicker" style="width:99%;" title="编制时间">
                                    </s:if>
                                    <s:else>
                                        <input name="createDate" allowInput="false" readonly="true"
                                               value="${comBidEval.createDate}" class="mini-datepicker"
                                               style="width:99%;" title="编制时间">
                                    </s:else>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">项目编号：</td>
                                <td class="td_right">
                                    <input name="projectCode" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${bidFileWithProject.projectCode}"/>
                                </td>
                                <td class="td_left">项目名称：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinName" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${bidFileWithProject.projectName}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">采购名称：</td>
                                <td class="td_right">
                                    <input name="projectCode" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${bidFileWithProject.purchaseName}"/>
                                </td>
                                <td class="td_left">招标方式：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinName" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${bidFileWithProject.purchaseMethod}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">设备参数：</td>
                                <td class="td_right" colspan="3">
                                    <input name="projectCode" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${deviceStr}"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                 
                    <fieldset style="margin:10px;">
                         <legend style="margin-left:10px;"><b>投标报价(单位:人民币万元)</b></legend>
                        <div id="bidGrid" class="mini-datagrid" style="width:100%;height:150px;" showPager="false" >
                            <div property="columns">
                                <div field="index" type="indexcolumn">序号</div>
                                <div field="supplierName" width="80" headerAlign="center" align="center">投标商名称</div>
                                <div field="firstTotalPrice" width="80" headerAlign="center" align="center">投标价格</div>
                                <div field="secondTotalPrice" width="80" headerAlign="center" align="center">澄清价格</div>
                                <div field="thirdTotalPrice" width="80" headerAlign="center" align="center">竞价</div>
                                <div field="paymentMethod" width="80" headerAlign="center" align="center">付款方式</div>
                                <div field="deliveryTime" width="80" headerAlign="center" align="center">交货期</div>
                                <div filed="remark" width="80" headerAlign="center" align="center">备注 </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin:10px;">
                       <legend><b>技术评标中评标结论</b></legend>
                      <div id="technologyData" class="mini-datagrid" style="width:100%;height:150px;" showPager="false" >
                         <div property="columns">
                           <div field="supplierName" width="80" align="center">投标单位名称</div>
                           <div header="评审结论" headerAlign="center">
                             <div property="columns">
                               <div type="comboboxcolumn" autoShowPopup="true" name="isFeasible" field="isFeasible" width="100" align="center" headerAlign="center">（可行不可行）
                                  <input property="editor" class="mini-combobox" style="width:100%;" data="IsFeasible" />
                                </div>
                               </div>
                           </div>
                          <div field="score" width="80" headerAlign="center" align="center">分值
                                 <input property="editor" class="mini-textbox" style="width:100%;"/>
                           </div>
                           <div field="remark" width="80" headerAlign="center" align="center">备注
                                 <input property="editor" class="mini-textbox" style="width:100%;"/>
                           </div>
                         </div>
                      </div>
                    </fieldset>
                     <fieldset style="margin: 10px;">
                        <legend ><b>评标结论</b></legend>
                        <div id="bidResultGrid" class="mini-datagrid"
                              style="width:100%;height:150px;" showPager="false" ondrawcell="ondrawcell"  >
                            <div property="columns">
                                <div field="supplierName" width="80" headerAlign="center" align="center">投标单位名称</div>
                                <div header="评审结论" headerAlign="center">
                                    <div property="columns">
                                       <div type="comboboxcolumn" name="isFeasible" autoShowPopup="true" allowInput="true" field="isFeasible"  width="100" align="center" headerAlign="center">（可行不可行）
                                          <input property="editor" class="mini-combobox" textField="text" valueField="id"   allowInput="true" data="[{id:'可行',text:'可行'},{id:'不可行',text:'不可行'}]">
                                       </div>
                                    </div>
                                </div>
                                <div field="score" width="80" headerAlign="center" align="center">分值
                                  <input property="editor" class="mini-textbox" style="width:100%;"/>
                                </div>
                                <div field="thirdTotalPrice" width="80" headerAlign="center" align="center">报价
                                  <input property="editor" class="mini-textbox" style="width:100%;"/>
                                </div>
                                <div field="indexTotal" width="80"  headerAlign="center" align="center">排名
                                  <input property="editor" class="mini-textbox" style="width:100%;"/>
                                </div>
                                <div type="checkcolumn" width="80"  headerAlign="center" align="center">建议中标供应商</div>
                                <div field="remark" width="80" headerAlign="center" align="center">备注
                                  <input property="editor" class="mini-textbox" style="width:100%;"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>  
                    <fieldset style="margin: 10px;">
                    <legend><b>综合评审意见</b></legend>
                   <table style="width:96%;padding:3px;margin:4px">
                      <tr> 
                          <td colspan="5" align="center">
                            <input name="content" width="98%" height="200px" id="content" class="mini-textarea tdInput" value="${compBidEval.content}">
                         </td>
                      </tr>
                   
                   </table>
                    
                    </fieldset>
              </div>
                
             </div>
            </div>
              <%@include file="/business/bid/workflow/attachFile.jsp" %> 
            <%@include file="/business/bid/workflow/flowSign.jsp" %>
          </div>
          <%@include file="/business/bid/workflow/flowFoot.jsp" %>
    </form>           
    <script type="text/javascript">
    mini.parse();
    var techData =mini.get('technologyData');
    var bidGrid =mini.get('bidGrid');
    var bidResultGrid = mini.get("bidResultGrid");
    var IsFeasible = [{ id: '可行', text: '可行' }, { id: '不可行', text: '不可行'}];
    <c:if test="${role != 'view'}">
    var gArrbtn=[{
    	id:"btnSave",
    	text:"保&nbsp;&nbsp;存",
    	style:"btn-primary",
    	align:"right",
    	Event:"toSave()"
    },{
    	id:"btnClose",
    	text:"关&nbsp;&nbsp;闭",
    	style:"btn-default",
    	align:"right",
    	Event:"toClose()"
    }];
    </c:if>
    <c:if test="${role == 'view'}">
    var gArrbtn = [{
        id: "btnClose",
        text: "关&nbsp;&nbsp;闭",
        style: "btn-default",
        align: "right",
        Event: "toClose()"
    }];
    </c:if>
     function toSave(){
    	 var form = document.forms[0];
         var tmpform = new mini.Form(form);
         tmpform.validate();
         if (tmpform.isValid() == false) {
             showErrorTexts(tmpform.getErrorTexts());
             return;
         }
         form.wfStatus.value = "2";// 保存
         var row = bidResultGrid.getSelected();
         if(row==null||row==undefined){
             showErrorTexts(["请选择建议中标供应商"]);
             return ;
         }
         $("input[name=suggestSupplierBid]").val(row.supplierBidUuid);
    	 bidResultGrid = bidResultGrid.getData();
    	 $("#bidResultGrid").val(mini.encode(bidResultGrid));
    	 console.log($("#bidResultGrid").val());
    	  mini.mask({
              el: document.body,
              cls: 'mini-mask-loading',
              html: '数据提交中...' //数据提交中...
          });
          setTimeout(function () {
              document.forms[0].submit();
          }, 500);
     }
     
     var indexTotal =0,rowIndex=0;
     function ondrawcell(e){
    	 //console.log(e);
       var record,field;
       record=e.record; field=e.field;
       if(field=="thirdTotalPrice"){
    	  // indexTotal =e.value;
    	  if(e.value>indexTotal){
    		  rowIndex+=1;
    		  indexTotal= e.record.thirdTotalPrice;
    		  //e.record.indexTotal=rowIndex;
    	  }
    	  if(e.value==indexTotal){
    		  e.record.indexTotal=rowIndex;
    	  }
       }
       if(field=="indexTotal"){
    	   e.cellHtml=rowIndex;
       }
      // e.record.indexTotal=rowIndex;
     
     }
    $(function(){
    /* 	pageLoad();
       dynamicCreateBtn(); 
    	  changeDivHeight();   */ 
    	 initTechData();
    	 initBidResult();
    	 //加载投标报价
    	 initBidGrid();
    });
    
    function initBidGrid(){
    	bidGrid.setUrl(gDir+'/business/bid/getBidGrid.action');
    	var bidFileUuid = $("input[name=biddingFileUuid]").val();
    	bidGrid.load({bidFileUuid:bidFileUuid});
    }
    //动态创建按钮
    function dynamicCreateBtn(){
 
    	$.each(gArrbtn,function(){
    		var btnHtml="<button onclick=\""+this.Event+"\" class=\"btn btn-md "+this.style+"\" type=\"button\">"+
    		this.text+"</button>";
    		if(this.align="right"){
    			$("#btnContL").append(btnHtml);
    		}else{
    			$("#btnContR").append(btnHtml);
    		}
    	});
    
    }
   function changeDivHeight() {
        $("#PageBody").css({
            "width": $(window).width() - 120
        });
        $(".footer-area").css({
            "width": $(window).width() - 120
        });
    } 
    //技术评标
    function initTechData(){
    	techData.setUrl(gDir+'/business/bid/supplierScore.action');
    	var bidFileUuid = $("input[name=biddingFileUuid]").val();
    	techData.load({bidFileUuid:bidFileUuid});
    }
    
    function initBidResult() {
      
        bidResultGrid.setUrl(gDir+'/business/bid/supplierScore.action');
        var bidFileUuid = $("input[name=biddingFileUuid]").val();
       /*  bidResultGrid.load({bidFileUuid:bidFileUuid,isFeasible:"可行"}); */
        bidResultGrid.load({bidFileUuid:bidFileUuid,isFeasible:"可行"},function(){
            var suggestSupplierBid = $("input[name=suggestSupplierBid]").val();
            var selectRow = bidResultGrid.findRow(function(row){
                console.log(row);
                if(row.supplierBidUuid == suggestSupplierBid) {
                    return true;}
            });
            bidResultGrid.setSelected(selectRow);
        }); 
    }
    function pageLoad(){
    	setTimeout(function(){
    		$("#bg-loader").remove();
    		$("#bg").remove();
    	},500);
    }
    function showErrorTexts(errorTexts) {
        var s = errorTexts.join('<br/>');
        mini.showMessageBox({
            width: 250,
            title: "消息提示",
            buttons: ["ok"],
            message: "消息提示",
            html: s,
            showModal: true
        });
    }
    function toClose(){
    	mini.confirm("确认要关闭吗？","提示",function(action){
    		if (action == "ok") {
                self.close();
            }
    	});
    }
    </script>
</body>
</html>