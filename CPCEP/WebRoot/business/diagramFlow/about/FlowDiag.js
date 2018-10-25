// JavaScript Document

//动态生成过程模块
var menuIds=[];
function CreateProHtml(){
	for(var ki=1;ki<=5;ki++){
		$("#panal_"+ki).html("")
	}
	if(ProwerCon==null || ProwerCon.length==0){
		return
	}
	var mkNum=0,pNum=1;
	var proId=$("[name=prjId]").val();
	//var stageArr=GetStage();
	//阶段下标，已有0、6
	var stageId=0;
	//第几行展现
	var indDIV;
	$.each(ProwerCon,function(){
		stageId++;
		var strHtml="";
		if(mkNum==4){
			pNum++;
			mkNum=0;
		}
		mkNum++;
		if(!this.children){
			return true;
			this.children=[];
		}
		strHtml+="<div class='span3'>"
		strHtml+="<div class='box'>"
		strHtml+="<div class='box-head' style='padding:5px 12px 5px 12px;border-top:0px;'>"
		strHtml+="<div style='float:left'><font size='2'>"+this.itemName+"</font></div>"
		strHtml+="<div style='text-align:right;padding-right:10px;display:none1'>&nbsp;</div>"
		strHtml+="<div class='progress progress-striped progress-blue progress-preview active' style='margin-bottom:0px;margin-top:5px;display:none'>"
		strHtml+="<div id='sub_"+stageId+"' class='bar' style='width:0%;'>0%</div>"
		strHtml+="</div>"
		strHtml+="</div>"
		strHtml+="<div id='sub_"+stageId+"_title' style='height:26px;background:rgb(243, 243, 243);'><div  id='sub_"+stageId+"_bta' >"
		strHtml+="<table  class='table table-striped table-bordered' style='margin:0px;' ><thead>"
		strHtml+="<tr><th style='height:20px;padding:3px;line-height:20px;width:55%'>节点</th>"
		strHtml+="<th style='line-height:20px;width:16%;padding:1px;'>状态</th><th style='line-height:20px;width:16%;padding:1px;'>处理人</th></tr></thead></table>"
		strHtml+="</div></div>"
		strHtml+="<div id='sub_"+stageId+"_main' style='position:relative;overflow:hidden;border-top:1px solid #CCCCCC;'  class='box-content box-nomargin'><div id='sub_"+stageId+"_tab' >"
		strHtml+="<table  class='table table-striped table-bordered' ><tbody>"
		
		
		$.each(this.children,function(){
			menuIds.push(this.id);
			strHtml+="<tr><td style='background:#FFFFFF;height:20px;width:55%;line-height:20px;padding:3px;'><span onmouseover=\"this.style.cursor='pointer'\" onclick=\"NodeClick('"+this.pid+"','"+this.id+"')\">&nbsp;"+this.itemName+"</span></td><td style='background:#FFFFFF;text-align:center;width:16%;line-height:20px;padding:1px' id='"+this.id+"'>--</td><td style='background:#FFFFFF;text-align:center;width:16%;line-height:20px;padding:1px;' id='"+this.id+"_C'>---</td></tr>"
		})
		for(var k=5-(this.children.length);k>0;k--){
			strHtml+="<tr><td style='background:#FFFFFF;width:55%;height:20px;line-height:20px'>&nbsp;</td><td style='background:#FFFFFF;text-align:center;width:16%;line-height:20px'>&nbsp;</td><td style='background:#FFFFFF;text-align:center;width:16%;line-height:20px;padding:1px'>&nbsp;</td></tr>";
		}
		strHtml+="</tbody></table></div></div></div></div>";
		//$("#panal_"+pNum).css("width",mkNum*340+"px");
		$("#panal_"+pNum).append(strHtml)
		
		
		var spanHeight=0;
		$("#panal_"+pNum+" .span3").each(function(){
			if($(this).innerHeight()>spanHeight){
				spanHeight=$(this).innerHeight()
			}
		})
		
		$("#panal_"+pNum).css("height",spanHeight+"px")
	})
	getNodeStatus();
}

function rtuTaskStatus(task){
	if(task.taskStatus && task.taskStatus!=""){
		return task.TaskStatus;
	}else{
		if(task.percentComplete==100 && task.finish>task.actualFinish){
			return "延期完成";
		}
		if(task.percentComplete==100 && getDay(task.finish,task.actualFinish)==1){
			return "正常完成";
		}
		if(task.percentComplete==100 && task.finish<task.actualFinish){
			return "超前完成";
		}
		if(task.milestone==1 && task.percentComplete!=100 && task.actualStart<new Date() &&  (task.finish<new Date() || task.start>task.actualStart)){
			
			return "里程碑延期";
		}
		if(task.percentComplete!=100 && task.actualStart<new Date() &&  (task.finish<new Date() || task.start>task.actualStart)){
			
			return "延期";
		}
	}
	return "";
}

//节点点击事件
function NodeClick(pid,id){
	var obj=null;
	$.each(ProwerCon,function(){
		if(this.id==pid){
			$.each(this.children,function(){
				if(this.id==id){
					obj=this;
					return false;
				}
			})
		}
	})
	if(obj!=null){
		isNQ(obj);
	}
}

//获取两个时间段的天数
var getDay=function(s,f){
	var i=(s - f) / 1000 / 60 / 60 /24;
	if(i<0){
		i-=1;
	}else{
		i+=1;
	}
	iDays = i; 
	return iDays;
}

function rtuString(o){
	if(o==null || o=="") {
		return "--";
	}
	if(StatusColor[o]){
		return "<font color='"+StatusColor[o]+"'>"+o+"</font>";
	}
	return o;
}
//公共变量
var nodeObj=null;

//内嵌打开方式
function isNQ(obj){
	nodeObj=obj;
	ModalShow(obj);
}

var isBidNew=true;
var isBidFileNew=true;
function getNodeStatus(){
	mini.mask({
        el: document.body,
        cls: 'mini-mask-loading',
        html: '加载中...'
    });
	var path=gDir+"/business/flow/findNodeStatus.action";
	$.post(path,{
		prjId:$("[name=prjId]").val(),
		planId:$("[name=planId]").val()
	},function(data){
		mini.unmask(document.body);
		data=mini.decode(data);
		for(var i=0;i<menuIds.length;i++){
			if((data.length-1)>=i){
				var Status=data[i][0];
				if(Status=="0"){
					Status="<font color='blue'>待处理</font>";
				}else if(Status=="1"){
					Status="<font color='orange'>处理中</font>";
				}else if(Status=="2"){
					Status="<font color='green'>已处理</font>";
				}else if(Status=="3"){
					Status="<font color='red'>未处理</font>";
				}else if(Status=="4"){
					Status="<font color='red'>待开标</font>";
				}else if(Status=="5"){
					isOpenSupBid=true;
					Status="<font color='green'>已开标</font>";
				}else if(Status=="6"){
					Status="<font color='green'>定标</font>";
				}
				if(i==0){
					if(Status!='--' && Status!='---'){
						isBidNew=false;
					}
				}else if(i==1){
					if(Status!='--' && Status!='---'){
						isBidFileNew=false;
					}
				}
				$("#"+menuIds[i]).html(Status);
				$("#"+menuIds[i]+"_C").html(data[i][1]);
			}
		}
	})
}
