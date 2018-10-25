//style:btn-primary,btn-info,btn-default,btn-success,btn-danger,btn-warning
var gArrbtn = [ {
	id : "btnSave",
	text : "保&nbsp;&nbsp;存",
	style : "btn-primary",
	align : "right",
	event : "toSave()"
}, {
	id : "btnClose",
	text : "关&nbsp;&nbsp;闭",
	style : "btn-default",
	align : "right",
	event : "toClose()"
} ];

var dateInputs=[];
function loadingForm(){
	var oldForm=document.forms[0].submit;
	$(".mini-datepicker").each(function(){
		if($(this).attr("name") && $(this).attr("name")!=""){
			dateInputs.push($(this).attr("name"));
		}
	})
	$(".mini-datepicker input").each(function(){
		if($(this).attr("name") && $(this).attr("name")!=""){
			dateInputs.push($(this).attr("name"));
		}
	})
	$.unique(dateInputs);
	document.forms[0].submit=function(){
		if(gIsRead){
			mini.alert("非法提交！","警告");
			return;
		}
		mini.mask({
	        el: document.body,
	        cls: 'mini-mask-loading',
	        html: '数据提交中...' //数据提交中...
	    });
		try{
			$.each(dateInputs,function(){
				if(mini.getbyName(this)){
					mini.getbyName(this).setFormat("yyyy-MM-ddTHH:mm:ss");
				}
			})
		}catch(e){
		}
		oldForm.call(document.forms[0]);
	}
}

// 动态输出按钮
function dynamicCreateBtn() {
	$.each(gArrbtn, function() {
		var btnStr = "<button onclick=\"" + this.event
				+ "\" class=\"btn btn-md " + this.style + "\" type=\"button\">"
				+ this.text + "</button>"
		if (this.align == "right") {
			$("#btnContR").append(btnStr);
		} else {
			$("#btnContL").append(btnStr);
		}
	})
}

// 统一保存方法
function toSave() {
	var form = document.forms[0];
	form.status.value = "1";// 保存
	mini.mask({
        el: document.body,
        cls: 'mini-mask-loading',
        html: '数据提交中...' //数据提交中...
    });
	setTimeout(function(){
		document.forms[0].submit();
	},500)
	
}

// 统一保存方法
function toClose() {
	mini.confirm("确定要关闭吗？", "提示", function(action) {
		if (action == "ok") {
			self.close();
		}
	})

}

// 自动调整页面宽度
function changeDivHeight() {
	$("#PageBody").css({
		"width" : $(window).width() - 120
	});
	$(".footer-area").css({
		"width" : $(window).width() - 120
	});
}

window.onresize = function() {
	changeDivHeight();
}

// 表单只读
function labelModel() {
	mini.parse();
	var fields = new mini.Form("PageBody").getFields();
	for (var i = 0, l = fields.length; i < l; i++) {
		var c = fields[i];
		if (c.setRequired)
			c.setRequired(false);
		if (c.setEnabled)
			c.setEnabled(true);
		if (c.setShowClose)
			c.setShowClose(false);
		if (c.setShowButton)
			c.setShowButton(false);
		if (c.setEmptyText)
			c.setEmptyText("未输入任何信息");
		if (c.setReadOnly)
			c.setReadOnly(true); // 只读
		if (c.setIsValid)
			c.setIsValid(true); // 去除错误提示
		if (c.addCls)
			c.addCls("asLabel"); // 增加asLabel外观
	}
	$(".td_right").css("padding-top", "2px");
	$(".toolbar-btn a").remove();
	$("#btnContL button").remove();
	$("#ideaList").parent().remove();
	$('#PageBody').show();
}

function pageUnload(){
	setTimeout(function(){
        $("#bg").remove();
        $("#bg-loader").remove();
        $("body").css("overflow","auto");
    },500)
}

function initPageEle() {
	changeDivHeight();
	//gIsFile==1关联附件,否则不关联附件
	if(gIsFile=="1"){
		dynamicFileList();
	}
	if(gIsRead){
		labelModel();
	}else{
		dynamicCreateBtn();
		mini.parse();
	}
	loadingForm();
	if(typeof(afterLoad)!="undefined"){
		afterLoad();
	}
	pageUnload();
}
