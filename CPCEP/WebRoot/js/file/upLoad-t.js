var gArrFile = [ {
	title : "附件列表",
	btn : [ {
		id : "js-Up",
		
		text : "上传",
		class : "mini-button",
		onclick : "test",
		iconCls : "icon-upload"
	}, {
		id : "js-del",
		text : "删除",
		class : "mini-button",
		onclick : "goDelAttach('att_att')",
		iconCls : "icon-remove"
	} , {
		id : "js-show",
		text : "预览",
		class : "mini-button",
		onclick : "goViewAttach('att_att')",
		iconCls : "icon-node"
	} ],
	grid : {
		id : "att_att",
		multiSelect : false,
		url:gDir+"/profile/findAllAdenexaJson.action",
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
}];

// 动态创建附件列表
function dynamicFileList() {
	$.each(
			gArrFile,
			function(i) {
				var gid=this.grid.id;
				var divBtnID = "fileMode_btn_" + i;
				var tableStr = "<table style=\"width:100%;\"><tr><td style=\"width:100%;padding-left:10px;\"><b>"
								+ this.title
								+ "</b></td><td style=\"white-space:nowrap;\" id='fileMode_btn_"
								+ i + "'></td></tr></table>";
				$("#FileMode .mbox-body")
								.append(
										"<div id='fileMode_"
												+ i
												+ "' style='"+(i>0?"margin-top:10px;":"")+"'><div class='mini-toolbar' style='border-bottom:0px;'>"
												+ tableStr + "</div></div>");
				var grid = new mini.DataGrid();
				grid.set(this.grid);
				grid.on("drawcell", function (e) {
			        var record = e.record;
			        field = e.field;
			        //field列，超连接操作按钮
			        if (field == "filename") {
			            e.cellStyle = "text-align:left";
			            e.cellHtml = '<a href="'+gDir+'/profile/findDowloadAdenexaById.action?uuid=' +record.uuid + '" target="_blank" >'+record.filename+'</a>';
			        }
			    });
				grid.on("rowdblclick", function (e) {
			        var record = e.record;
			        goView(record.uuid);
			    });
				if($("[name=curDocId]").val() && $("[name=curDocId]").val()!=""){
					grid.load({parentDocId:$("[name=curDocId]").val(),prjID:$("[name=prjID]").val(),area:gid});
				}
				grid.render(document.getElementById("fileMode_" + i));
				
				$.each(this.btn, function() {
					if(gIsRead && this.iconCls!="icon-node"){
						return true;
					}
					if(this.iconCls=="icon-upload"){
						$("#"+divBtnID).append("<span id=\""+this.id+"\" gId=\""+gid+"\" iconCls=\""+this.iconCls+"\"></span>");
						return true;
					}
					var btn = new mini.Button();
					btn.set(this);
					btn.set({
						style : "margin-right:10px;"
					})
					btn.render(document.getElementById(divBtnID));
				})
	})
	if(gArrFile.length>0){
		$("#FileMode").show();
		if($("[name=prjID]").length>0 && $("[name=prjID]").val()==""){
			return
		}
		initUpBtn();
	}
}

function initUpBtn(){
	$("#FileMode [iconCls=icon-upload]").each(function(){
			var obj={};
			var id=$("[name=prjID]").val();
			if(!id){
				id="";
			}
			obj.queueID=$("#"+this.id).attr("gId");
			obj.formData={
					prjID:id,
					parentDocId:$("[name=curDocId]").val(),
					catalogId:$("[name=catalogId]").val(),
					menuId:menuId,
					userName:gLoginUser,
					userIds:gUserId,
					curDocId:$("[name=curDocId]").val(),
					orgIds:gOrgIds
			}
			$("#"+this.id).htUpload(obj);
			$("#"+this.id).css("margin-right","20px");
			$("#"+this.id).attr("iconCls","icon-upload");
			$("#"+this.id).attr("gId",obj.queueID);
			$("#"+this.id).attr("class","btnWidth");
	})
}

//附件查询
function initComplefj(id){
	var grid=mini.get(id);
	//这里可以重新指定自定义的数据装载路径
	grid.load({parentDocId:$("[name=curDocId]").val(),prjID:$("[name=prjID]").val(),area:id});
}

//获取id
function GetDocID(grid){
	var arrID=$.map(grid.getSelecteds(),function(i){
		return i.uuid;
	});
	return arrID.join("^");
}

//删除附件
function goDelAttach(id){
	var grid=mini.get(id);
	var uuid = GetDocID(grid);
	var path = gDir+"/profile/adenexDeleteFile.action?uuid=";
	goDelAttachFile(uuid,path,id);
}


//删除附件
function goDelAttachFile(s,path,id) {
	var arrFile = s;
    if (arrFile.length > 0) {
       mini.confirm("确定删除选中附件吗？","确认框", function(r){
    	   if(r=="ok"){
                $.ajax({
                    url: path + arrFile,
                    dataType: "json",
                    cache: false,
                    success: function(data) {
                        if (data.success) {
                        	mini.alert(data.data);
                        	initComplefj(id);
                        }else{
                        	mini.alert(data.data);
                        }
                    }
                });
             }
        });
    } else {
        mini.alert("没有可删除的附件，请先选择！")
    }
}

//预览附件
function goViewAttach(id){
	var grid=mini.get(id);
	var uuid = GetDocID(grid);
	var path = gDir+"/profile/findViewAdenexaById.action?uuid=";
	getViewUpload(uuid,path);
}

function goView(uuid){
	var path = gDir+"/profile/findViewAdenexaById.action?uuid=";
	getViewUpload(uuid,path);
}

//上传附件预览
function getViewUpload(uuid,path){
	if(uuid==""){
		mini.alert("请选择预览的文件！");
		return;
	}
	window.open(path+uuid);
//	return;
//	mini.open({
//		url:path+uuid,
//		title:"附件预览",
//		width:810,
//		height:545,
//		showMaxButton: true,
//		allowResize:true
//	});
}