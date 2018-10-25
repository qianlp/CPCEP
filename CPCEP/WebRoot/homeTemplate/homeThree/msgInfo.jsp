<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	var docType="";
	var status="";
	
	function getMsgList(){
		var path=gDir+"/msg/getMsgList.action";
		$.post(path,{
			msgTitle:$("[name=msgTitle]").val(),
			createBy:$("[name=createBy]").val(),
			msgType:docType,
			status:status
		},function(data){
			data=ht.decode(data);
			var htmlStr="";
			$.each(data,function(){
				var type="db";
				var color="blue";
				if(this.msgType==2){
					type="tz";
					color="green";
				}else if(this.msgType==3){
					type="yj";
					color="red";
				}
				htmlStr+="<div class=\"Msg boder-"+color+"\" onclick=\"CommonOpenDoc('"+this.menuId+"','"+this.docId+"','"+this.uuid+"')\" >";
				htmlStr+="<div class=\"msg-status\">";
				htmlStr+="<a href=\"javascript:void(0)\" class=\""+type+"\" ><i></i></a>";
				htmlStr+="</div>";
				htmlStr+="<div class=\"msg-title\" >";
				htmlStr+="<a href=\"#\" >"+(this.msgTitle==""?"无标题":this.msgTitle)+"</a>";
				htmlStr+="</div>";
				htmlStr+="<div class=\"msg-util\">"+this.createBy+"&nbsp;&nbsp;&nbsp;"+mini.formatDate(this.createDate,"yyyy-MM-dd HH:mm")+"</div>";
				htmlStr+="</div>";
			})
			if(htmlStr!=""){
				$("#MsgHtml").removeClass("line2");
			}else{
				$("#MsgHtml").addClass("line2");
			}
			$("#MsgHtml").html(htmlStr);
		})
	}
	
	function CommonOpenDoc(menuId,uuid,msgId){
		var strPathUrl=gDir+"/admin/findDocById.action?menuId="+menuId+"&uuid="+uuid+"&msgId="+msgId;
		if(uuid=="" || uuid==null){
			strPathUrl=gDir+"/admin/rtuMenuById.action?menuId="+menuId+"&msgId="+msgId;
		}
		winH=screen.height-100;//高度
		winH=winH==0?(screen.height-100):winH;
		winW=screen.width-20;//宽度
		winW=winW==0?(screen.width-20):winW;
		if(winH>0){var T=(screen.height-100-winH)/2}else{var T=0}
		if(winW>0){var L=(screen.width-20-winW)/2}else{var L=0}
		var pstatus="height="+winH+",width="+winW+",top="+T+",left="+L+",toolbar=no,menubar=no,location=no,status=yes,resizable=yes,scrollbars=yes";
		window.open(strPathUrl,'_blank',pstatus);
	}
</script>
	<div style="float:left;width:100%;display:block;">
		<div class="col-sm-12" style="float:right;position:relative;">
		
		<div class="da-panel-widget">
				<h3>消息中心</h3>
				<div class="Msg-right" >
					<a href="javascript:void(0)" class="db" title="待办"><i></i></a>
					<a href="javascript:void(0)" class="tz" title="通知"><i></i></a>
					<a href="javascript:void(0)" class="yj" title="预警"><i></i></a>
				</div>
				<div id="MsgDivHtml"
					style="min-height:500px;">
					<div class="search_body" id="search_body">
						<div class="search_content" style="width:230px;">
							<div class="radio" style="margin:3px;">
					        	<input id="demo-inline-form-radio" class="magic-radio" type="radio" name="inline-form-radio" >
					        	<label for="demo-inline-form-radio">待办</label>
					                        
					        	<input id="demo-inline-form-radio-2" class="magic-radio" type="radio" name="inline-form-radio">
					        	<label for="demo-inline-form-radio-2">通知</label>
					                       
					        	<input id="demo-inline-form-radio-3" class="magic-radio" type="radio" name="inline-form-radio">
					        	<label for="demo-inline-form-radio-3">预警</label>
					        </div>
					    </div>
						<div class="search_content">
							<div class="search_title">标题：</div>
							<div class="search_field" Operator="@" DataType="text" Item="">
								<input class="form-control input-sm" id="msgTitle" type="text">
							</div>
						</div>
						<div class="search_content">
							<div class="search_title">发起人：</div>
							<div class="search_field" Operator="@" DataType="text" Item="">
								<input class="form-control input-sm" id="createBy" type="text">
							</div>
						</div>
						<div class="search_button">
							<a class="mini-button" tooltip="清空查询条件" plain="true"
								iconCls="icon-remove" onclick="ClearForm"></a> &nbsp;<a
								class="mini-button" iconCls="icon-search" onclick="CommonSearch">搜索</a>
						</div>
					</div>
					<div id="MsgHtml"  class="line2" style="border-top:1px solid #EDB23F;margin-top:10px;min-height:450px;padding-bottom:20px;">
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
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
	function onCheckedChanged(e) {
        var btn = e.sender;
        var checked = btn.getChecked();
        var text = btn.getText();

        if(checked){
            if(text=="待办"){
            	docType=1;
            }
            if(text=="通知"){
            	docType=2;
            }
            if(text=="预警"){
            	docType=3;
            }
            getMsgList();
        }
    }
	
	//清空查询条件
	function ClearForm(){
		var form = new mini.Form("#search_body");
		form.reset();
		docType="";
		status="";
		getMsgList();
	}
	/*
	描述：查询
	*/
	function CommonSearch(){
		getMsgList();
	}
	getMsgList();
	</script>