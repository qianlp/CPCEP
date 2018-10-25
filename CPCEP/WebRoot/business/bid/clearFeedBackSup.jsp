<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>投标文件质疑、回复</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" type='text/css' href="../../css/form/loaders.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap-theme.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/bootstrap.flow.min.css"/>
    <link rel="stylesheet" type='text/css' href="../../css/form/form.css"/>
    <%@include file="../../resource.jsp" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>

    <script>
        var gDir = "${pageContext.request.contextPath}";
        var gLoginUser = "${sessionScope.user.userName}";
    </script>
    <style type="text/css">
body{
    overflow:scroll;
    overflow-x:hidden;
}
</style>
	<body  bgcolor="#FFFFFF" style="padding:0px;width:100%;height:100%;background:#f3f3f3;">
	<!--  <div id="bg"></div>
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
	</div>  -->
	<form method="post" action="${pageContext.request.contextPath}/business/techEvalClarify/saveClear.action" name="form1" id="form1">
	<div id="PageBody">
        <div class="col-md-7" name="FormMode" id="FormMode" style="width:100%;margin:auto;float:none;">
            <div class="widget-container fluid-height col-md-7-k" style="height:auto;border-radius:4px;">
                <div class="mbox-header">
                    <span class="form-title" style="height:100%;line-height:45px;">
                                                               投标文件质疑                                             
                    </span>
                </div>
                <div style="display:none">
                  <c:if test="${ not empty type}">
                    <input name="bidFileUUID" value="${techClarify.bidFileUUID}">
                    
                  </c:if>
                   <input name="uuid"  value="${techClarify.uuid}"> 
                </div>
                <div class="mbox-body" style="height:100%;padding:10px;">
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>基本信息</b></legend>
                        <table style="width:95%;margin:5px;" id="tab">
                            <tr>
                                <td class="td_left">招标文件编号：</td>
                                <td class="td_right">
                                        <input name="bidCode" value="${techClarify.bidCode}" class="mini-textbox"
                                               allowInput="false" readonly="true" style="width:99%;" title="招标文件编号">
                                </td>
                                  <td class="td_left">招标文件名称：</td>
                                  <td class="td_right">
                                        <input name="bidName" value="${techClarify.bidName}" class="mini-textbox"
                                               allowInput="false" readonly="true" style="width:99%;" title="招标文件编号">
                                </td>
                            </tr>
                             <tr>
                                <td class="td_left">招标文件版本号：</td>
                                <td class="td_right">
                                        <input name="version" value="${techClarify.version}" class="mini-textbox"
                                               allowInput="false" readonly="true" style="width:99%;" title="招标文件编号">
                                </td>
                                  <td class="td_left">问题类型：</td>
                                  <td class="td_right">
                                        <input name="issueType" value="${techClarify.issueType}" class="mini-combobox"
                                               allowInput="false"  style="width:99%;" textField="text" valueField="id" readonly="true"
                                                data="[{id:'技术问题',text:'技术问题'},{id:'商务问题',text:'商务问题'},{id:'其他',text:'其他'}]"
                                                title="招标文件编号">
                                              
                                </td>
                            </tr>
                             <tr>
                                <td class="td_left">供应商名称：</td>
                                <td class="td_right">
                                        <input name="supName" value="${techClarify.supName}" class="mini-buttonedit" text="${techClarify.supName}" onbuttonclick="ButtonEdit"
                                               allowInput="false" style="width:99%;" title="招标文件编号">
                                        <input name="supUUID" id="supUUID" type="hidden" name="supUUID" value="${techClarify.supUUID}" >
                                </td>
                                  <td class="td_left">联系方式：</td>
                                  <td class="td_right">
                                        <input name="contact" value="${techClarify.contact}" class="mini-textbox"
                                               allowInput="false" readonly="true" style="width:99%;" title="招标文件编号">
                                              
                                </td>
                            </tr>
                             <tr>
                                <td class="td_left">问题描述：</td>
                                <td class="td_right" colspan="6" style="padding:3px;">
                                   <input name="remark"  value="${techClarify.remark}" allowInput="false" readonly="true" class="mini-textarea" style="width:99.6%" title="备注">
                                 </td>
                            </tr>
                            <tr>
                                <td class="td_left">附件：</td>
                                 <td class="td_right">
                                       <div id="upload">
			                                <a id="file_1"></a>
			                                <input type='hidden' name='clarifyFileUUID' value="${techClarify.clarifyFileUUID }"/>
			                               <input type='hidden' name='questionFileName' value='${ClarifyName}'/>
			                              <!--   <button type="button" class="layui-btn layui-btn-sm" id="upload_1">
			                                    <i class="layui-icon">&#xe67c;</i>点击上传
			                                </button> -->
                                       </div>
                                              
                                </td>
                              </tr>
                            </table>
                            </fieldset>
                     <c:if test="${empty userType }">
                      <div style="margin:10px;">
                       <fieldset >
                         <legend style="margin-left:10px;"><b>投标文件质疑</b></legend>
                         <table style="width:96%;margin:5px;padding:3px" id="tab2">
                         
                           <tr>
                             <td class="td_left" >问题描述：</td>
                                <td class="td_right" colspan="6" style="padding:3px;" >
                                   <input name="feedBack" class="mini-textarea" value="${techClarify.feedBack}" style="width:99.6%" title="备注">
                                 </td>
                                 
                            </tr>
                             <tr>
                                <td class="td_left">附件：</td>
                                 <td class="td_right" >
                                       <div id="upload2">
			                                <a id="file_2"></a>
			                               <input type='hidden' name='feedBackFileUUID' value='${techClarify.feedBackFileUUID }'/>
			                               <input type='hidden' name='feedBackFileName' value='${FeedBackName}'/> 
			                                <button type="button" class="layui-btn layui-btn-sm" id="upload_2">
			                                    <i class="layui-icon">&#xe67c;</i>点击上传
			                                </button>
                                       </div>      
                                </td>
                                <td class="td_left"></td>
                                 <td class="td_right"></td>
                              </tr> 
                         </table>
                       </fieldset>
                       </div>
                     </c:if> 
              <div class="host-footer">
		        <div class="footer-bg"></div>
		        <div class="footer-area">
		            <div class="pull-left" id="btnContL">
		            </div>
		            <div class="pull-right" id="btnContR">
		            </div>
		        </div>
		        </div>
		        </div>
            </div> 
          </div>  
   </div>
    </form>                    
</body>
<script type="text/javascript">
<c:if test="${empty userType }">
$(function () {
    layui.use('upload', function () {
        var upload = layui.upload;
        var uploadInst = upload.render({
            elem: "#upload_" + 2//绑定元素
            , accept: "file"
            , data: {
                type: 14
            }
            , url: gDir+"/business/upload.action" //上传接口
            , done: function (res) {
                //上传完毕回调
                var result = mini.decode(res);
                 $("#file_2").html(result.data.fileName);
                $("input[name=feedBackFileUUID]").val(result.data.uuid);
                $("input[name=feedBackFileName]").val(result.data.fileName);
                $("#file_2").attr("href",gDir+"/business/download.action?id="+result.data.uuid);
            }
            , error: function () {
                //请求异常回调
                alert("上传失败")
            }
        });
    });
});
</c:if>


$("input[name=questionFileName]").val("${ClarifyName}");
$("#file_1").html("${ClarifyName}");
$("#file_1").attr("href",gDir+"/business/download.action?id="+"${techClarify.clarifyFileUUID}");
$("input[name=feedBackFileName]").val("${FeedBackName}");
$("#file_2").html("${FeedBackName}");
$("#file_2").attr("href",gDir+"/business/download.action?id="+"${techClarify.feedBackFileUUID}");

</script>
<script type="text/javascript">
mini.parse();
<c:choose>
<c:when test="${not empty techClarify.feedBack}">
var gArrbtn=[{
    	id:"btnClose",
    	text:"关&nbsp;&nbsp;闭",
    	style:"btn-default",
    	align:"right",
    	Event:"toClose()"
    }];
    </c:when>
    <c:otherwise>
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
    </c:otherwise>
    </c:choose>
     createBtn();
     function createBtn(){
    	$.each(gArrbtn,function(){
    		 var btnHtml="<button id=\""+this.id+"\" onclick=\""+this.Event+"\" class=\"btn btn-md "+this.style+"\"  type=\"button\" >"+this.text+"</button>";
    		if(this.align!="right"){
    			$("#btnContL").append(btnHtml);
    		}else{
    			$("#btnContR").append(btnHtml);
    		}
    	})
     }
     function toSave(){
    	var form= new mini.Form("#form1");
    	console.log(document.forms[0]);
    	//console.log(form.createDate);
  	  mini.mask({
          el: document.body,
          cls: 'mini-mask-loading',
          html: '数据提交中...' //数据提交中...
      });
      setTimeout(function () {
          document.forms[0].submit();
      }, 500);
    	
     }
  function ButtonEdit(e){
	  mini.open({
		  url:gDir+"/business/bid/bidClearSupList.jsp?bidFileId="+"${param.bidFileId}"+"&hasBid=true",
		  //url:"${pageContext.request.contextPath}/business/bid/bidClearSupList.jsp",
		  title:"供应商投标列表页",
		  width:500,
		  height:300,
		  allowResize:true,
		  showCloseButton:true, 
		  showMaxButton:true,
		  ondestroy:function(data){
			  var date=mini.decode(data);
			 console.log(date);
			 console.log(date.supplierName);
			 mini.getbyName("supName").setText(date.supplierName);
			 mini.getbyName("supName").setValue(date.supplierName);
			// mini.getbyName("supUUID").setText(data[0].userId);
			$("#supUUID").val(date.userId);
			console.log($("#supUUID").val());
			 mini.getbyName("contact").setValue(date.phon);
		  }
		  
	  });
  }
</script>
</html>