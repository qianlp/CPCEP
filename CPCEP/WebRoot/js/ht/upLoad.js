/**
	使用说明，使用前请先如下操作(如果已经存在就无须更改，避免重复操作)：
		引入miniui库：
			<script src="/miniui/scripts/boot.js" type="text/javascript"></script>
			<script src="/miniui/scripts/jquery.form.js" type="text/javascript"></script>
		定义普通文本域CurDocID，域默认值为@Text(@DocumentUniqueID)
		定义几个全局变量并赋值：
			gCurDB：当前数据库
			gForm:当前表单:document.forms[0]
			curDocId=计算的文本  //计算的文本显示域CurDocID的值
	
	显示附件方法loadAttachGrid(false);
	上传附件方法goUploadFile(id)//id为显示附件的容器div的id，命名格式以"_att"结尾 如：myatts_att,可以定义多个附件上传的区域，使用不同的id即可，调用方法相同
	删除附件方法：goDelAttachFile()
	
	附件容器div属性说明：
		<div id="att_att"  columns="docid:'5%',num:'5%',showname:'',user:'',size:'',suffix:''" colHeaders="'选择','序号','名称','上传人','大小','格式'">
		columns:必选属性, 属性值为列宽，设置显示的列，docid：选择框，num：序号，showname：附件名，user：上传人，size：附件大小，suffix：附件格式(附加名的结尾)
		colHeaders:可选属性，显示列标题，如果不配置该属性那么就不显示列标题

	2014-10-30 wenxun
	更新
		附件容器div 
		新增 class='att_att' 属性
		新增 toolbar 属性
		toolbar="title:'附件列表',upId:'js-att1',editId:'js-edit1',delId:'js-del1'"
		toolbar:必选属性,设置工具条标题，上传、编辑、删除按钮ID ID命名规则：js-*

		新增js方法
		loadAttachToolbar() 加载各区域上传工具条
		returnCellWidth(c)  参数：附件区域 返回该区域列宽

		新增css  upload.css
		区域附件样式

		修改方法
		goEditAttachFile 为区域编辑
		goDelAttachFile  为区域删除
	2015-02-13
		1、修复附件中含有异常字符。
		修改方法：
		将encodeURI("/" + gCurDB + "/0/" + e['docid'] + "/$File/" + e['name'])中的encodeURI去除掉。原因是json字符串中已经用@URLEnCode方法处理过。
		2、暂时屏蔽了"编辑"按钮
		
*/
//加载工具条
$(document).ready(function(){
	try{
		if(!gIsEditDoc){
			loadAttachGrid(true);
		}else if(!gWFModule){
			loadAttachGrid(false);
		}
	}catch(e){
		loadAttachGrid();
	}
	
})
	
function loadAttachToolbar(){
	if(DiyJs.FileArr){
		$.each(DiyJs.FileArr,function(){
			var uploadObj={
				formData       : objAttacheData,
				queueID        : this.id,				//附件区域标识
				imageZipServer : this.imageZipServer   //是否在服务器端进行图片压缩，如果选择文件中是图片的即进行压缩。
			};
			if(this.imageZipWeb){
				uploadObj.imageZipWeb=this.imageZipWeb;
			}
			
			$("#"+this.btn.upId).htUpload(uploadObj);
			$("#"+this.btn.upId).css("margin-right","10px");
			$("#"+this.btn.upId).addClass(this.btn.upId);
			if($("[name=PrjID]").length>0 && $("[name=PrjID]").val()==""){
				$("#"+this.btn.upId).hide();
			}
		})
	}
		
	mini.parse();
}

//选择完成之后重新创建
function initUpload(){
	objAttacheData.PrjID=$("[name=PrjID]").val();
	if(DiyJs.FileArr){
		$.each(DiyJs.FileArr,function(){
			$("#"+this.btn.upId).html("");
			var uploadObj={
				formData       : objAttacheData,
				queueID        : this.id,				//附件区域标识
				imageZipServer : this.imageZipServer   //是否在服务器端进行图片压缩，如果选择文件中是图片的即进行压缩。
			};
			if(this.imageZipWeb){
				uploadObj.imageZipWeb=this.imageZipWeb;
			}
			
			$("#"+this.btn.upId).htUpload(uploadObj);
			$("#"+this.btn.upId).css("margin-right","10px");
			$("#"+this.btn.upId).addClass(this.btn.upId);
			$("#"+this.btn.upId).show();
		})
	}
	
	mini.parse();
}
//返回列宽度
function returnCellWidth(c){
	var f = eval("({" + c.attributes["columns"].value + "})");
	var d=[];
	for (var b in f) {
		d.push(f[b]);
	}
	return d;
}
//装载附件
function loadAttachGrid(r) {
	console.log(r);
	//加载工具条
	if(!r){
		loadAttachToolbar();
	}
    /*装载已有附件并生成表格*/
    $.ajax({
        url: "/" + gCommonDB + "/vwComReadAttach?OpenView&Count=99&ExpandAll&RestrictToCategory=" + gDocKey,
        dataType: "text",
        cache: false,
        success: function(txt) {
            if (r) {
                //$("[id$='_tab']").empty();
            }
            var arrItem = [];
            if (txt.toUpperCase().indexOf("<H2>") < 0) {
                arrItem = eval("[" + txt.substr(1) + "]");
                //自定义附件展示区域
                tempItem = $.grep(arrItem,
                function(i) {
                    return i.contId != ""
                });
                $("[id$=_att]").each(function(i, item) {
					//$("#"+item.id + "_tab").empty();
					if($.grep(tempItem,function(json) {return json.contId == item.id;}).length>0){
						$("#"+item.id + "_tab").empty();
					}
                    if (!document.getElementById(item.id + "_tab")) {
                        trHTML = "";
                        if ($(item).attr("colHeaders") != undefined) {
                            var arrHeads = eval("[" + item.attributes["colHeaders"].value + "]");
							var z=returnCellWidth(item);
                            $.each(arrHeads,
                            function(i, n) {
								var sty="";
								if((i+1)==arrHeads.length){
									sty="border-right:0px;"
								}
                                trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy' style='width:"+z[i]+"%;"+sty+"'>" + n + "</td>");
                            });
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody class='attBody' id='" + (item.id + "_tab") + "'></tbody></table>").appendTo("#" + item.id);
                        } else {
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (item.id + "_tab") + "'></tbody></table>").appendTo("#" + item.id);
                        }
                    }

                    i = 1;
                    var attObjs = {};
                    var tdHTML = "";
					if($.grep(tempItem,function(json){return json.contId == item.id;}).length>0){
						gIsUpFile=1;
					}
					if($("#"+item.id+"_true").length>0){
						$("#"+item.id+"_true").html("<div class=\"m\" style=\"width:99.8%;margin:auto;height:300px;max-height:300px;\"><ul class=\"pgwSlider\" ></ul></div>");
					}
                    $.each($.grep(tempItem,
                    function(json) {
                        return json.contId == item.id;
                    }),
                    function(num, e) {
						
                        attObjs = eval("({" + item.attributes["columns"].value + "})");
                        for (var _X in attObjs) {
                            if (_X == "docid") {
                                tdHTML += "<td class='mini-grid-cell attCellTdDiy' width='" + attObjs[_X] + "%'><input class='Noprint' type='checkbox' suffix='" + e.suffix + "' name='$$attachfile' value='" + e[_X] + "'/></td>";
                            } else if (_X == "num") {
                                tdHTML += "<td class='mini-grid-cell attCellTdDiy'  width='" + attObjs[_X] + "%'>" + (i++) + "</td>";
                            } else if (_X == "showname") {
                                tdHTML += "<td class='mini-grid-cell attCellTdDiy'  width='" + attObjs[_X] + "%' style='text-align:left'>&nbsp;&nbsp;<a href='/" + gCommonDB + "/0/" + e['docid'] + "/$File/" + e['name'] + "' target='_blank'>" + e[_X] + "</a></td>";
                            } else {
                                tdHTML += "<td class='mini-grid-cell attCellTdDiy'  width='" + attObjs[_X] + "%'>" + e[_X] + "</td>";
                            }
							
                        }
						if($("#"+item.id+"_true").length>0){
							$("#"+item.id+"_true .pgwSlider").append("<li><img src='/" + gCommonDB + "/0/" + e['docid'] + "/$File/" + e['name'] + "'   alt='"+e['showname']+"' data-description='"+e['showname']+"'/></li>");
						}
                        $("<tr class='attTr" + num % 2 + "'>" + tdHTML + "</tr>").appendTo("#" + e.contId + "_tab");
                        tdHTML = "";
						$(item).css("border-bottom","0px")
                    });
					if($("#"+item.id+"_true").length>0){
						$("#"+item.id+"_true .pgwSlider").pgwSlider();
					}
                });
            } else {
                var tdHTML = "";
                $("[id$=_att]").each(function(i, item) {
                    if (!document.getElementById(item.id + "_tab")) {
                        trHTML = "";
                        if ($(item).attr("colHeaders") != undefined) {
                            var arrHeads = eval("[" + item.attributes["colHeaders"].value + "]");
                            var cols = 0;
							var z=returnCellWidth(item);
                            $.each(arrHeads,
                            function(i, n) {
								var sty="";
								if((i+1)==arrHeads.length){
									sty="border-right:0px;"
								}
                                trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy' style='width:"+z[i]+"%;"+sty+"'>" + n + "</td>");
                                cols += 1;
                            });
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody id='" + (item.id + "_tab") + "'><tr><td style='text-align:center' colspan='" + cols + "'>"+PublicField.FileMsg+"</td></tr></tbody></table>").appendTo("#" + item.id);
                        } else {
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (item.id + "_tab") + "'><tr><td>"+PublicField.FileMsg+"</td></tr></tbody></table>").appendTo("#" + item.id); //暂无附件。
                        }
                    } else {
                        //$("[id$='_att']").empty();
						$(item).find("table").remove();
                        trHTML = "";
                        if ($(item).attr("colHeaders") != undefined) {
                            var arrHeads = eval("[" + item.attributes["colHeaders"].value + "]");
                            var cols = 0;
							var z=returnCellWidth(item);
                            $.each(arrHeads,
                            function(i, n) {
								var sty="";
								if((i+1)==arrHeads.length){
									sty="border-right:0px;"
								}
                                trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy' style='width:"+z[i]+"%;"+sty+"'>" + n + "</td>");
                                cols += 1;
                            });
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody id='" + (item.id + "_tab") + "'><tr><td style='text-align:center' colspan='" + cols + "'>"+PublicField.FileMsg+"</td></tr></tbody></table>").appendTo("#" + item.id);
                        } else {
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (item.id + "_tab") + "'><tr><td>"+PublicField.FileMsg+"</td></tr></tbody></table>").appendTo("#" + item.id); //暂无附件。
                        }
                    }
					$(item).css("border-bottom","1px solid #ccc")
                })
            }
        }
    })
}
/*以下是文件上传函数*/
/*
function goUploadFile(contId) {
    //if(arguments.length==0){alert("请先设置附件显示区域id，id命名以“_att”结尾。");return;}
    if (arguments.length == 0) {
        alert(PublicField.UpMsg10);
        return;
    }
    $.ajax({
        url: "/" + gCurDB + "/fmComUpFile?OpenForm",
        dataType: "text",
        success: function(txt) {
            if (txt.indexOf("ComUpFileID") > -1) {
                try {
                    txt = txt.split("</head>")[1].split("</body>")[0].substr(txt.indexOf(">") + 1).replace(/\r|\n|/g, "").replace(/<br>/g, "").replace(/></g, "/><").split("</form>")[0];
                    var objXML = $.parseXML("<body>" + txt + "</body>");
                    var filename = $('input[id="ComUpFileID"]', objXML)[0].getAttribute("name");
                    txt = '<form method="post" action="/' + gCurDB + '/fmComUpFile?OpenForm&amp;Seq=1" enctype="multipart/form-data"><input type="hidden" name="__Click" value="0"><input name="FormType" value="fmUpFile" type="hidden"><input name="pDocID" value="" type="hidden"><input name="ContID" value="" type="hidden"><input type="file" style="width:97%" class="mini-htmlfile" name="' + filename + '"></form>';
                    txt += (txt + txt + txt + txt + txt);
                    var oWinDlg = mini.get("oWinDlgFile");
                    if (!oWinDlg) {
                        oWinDlg = mini.open({
                            id: "oWinDlgFile",
                            title: PublicField.FileBoxTitle,
                            //上传附件
                            allowDrag: false,
                            allowResize: false,
                            showModal: true,
                            enableDragProxy: false,
                            showFooter: true,
                            footerStyle: "padding:5px 12px;",
                            showCloseButton: false,
                            bodyStyle: "padding:5px",
                            width: 450,
                            height: 250
                        });
                        oWinDlg.setBody(txt);
                        arrBtns = [{
                            name: PublicField.UpFile,
                            title: PublicField.FileBoxTitle,
                            ico: "icon-save",
                            clickEvent: "ifrSubmit('" + (contId ? contId: "") + "')",
                            align: "2",
                            isHidden: "0"
                        },
                        {
                            name: PublicField.FlowClose,
                            title: PublicField.FlowClose,
                            ico: "icon-cancel",
                            clickEvent: "mini.get('oWinDlgFile').destroy()",
                            align: "2",
                            isHidden: "0"
                        }];
                        oWinDlg.setFooter("<div id='SubmitDocActionBar' style='text-align:right'></div>");
                        var btnHtml = "";
                        for (var i = 0; i < arrBtns.length; i++) {
                            btnHtml = "<a class='mini-button' plain=true iconCls='" + arrBtns[i].ico + "'>" + arrBtns[i].name + "</a>";
                            $(btnHtml).appendTo("#SubmitDocActionBar").attr('onClick', arrBtns[i].clickEvent);
                        }
                        mini.parse();
                    }
                    oWinDlg.show();
                } catch(e) {
                    for (var x in e) {
                        alert(x + "--" + e[x])
                    }
                }
            }
        }
    })
}
var gIntSubmitNum=0;
var gSetInterval=null;
function ifrSubmit(contId) {
    if (mini.get("oWinDlgFile").isLoading) {
        alert(PublicField.UpLoadMsg); //正在上传，请您耐心等待！
        return;
    }
    var oForm = $.grep($("#oWinDlgFile form"),
    function(item) {
		if($('input[type="file"]', item)[0].value != ""){
			return true
		}else{
			$(item).empty().remove();
		}
    });
   
    //if(oForm.length<1){alert("请选择需要上传的文件！");return};
    if (oForm.length < 1) {
		alert(PublicField.UpMsg1);
        return
    };
    mini.get("oWinDlgFile").loading("\u6b63\u5728\u4e0a\u4f20...");
    mini.get("oWinDlgFile").set({
        "isLoading": true
    });
	
	gIntSubmitNum=0;
	gSetInterval=null;
	
	var arrFM=[],arrOPTION=[];
    var f = function() {
		var t=function(){
			if (gIntSubmitNum == arrFM.length) {
				//console.log("over:"+gIntSubmitNum);
				clearInterval(gSetInterval);
				setTimeout(function(){
					mini.get("oWinDlgFile").destroy();
					loadAttachGrid(true);
				},100);
			}
		};
		var objDATA=$.data(arrFM[gIntSubmitNum],"jqxhr");
		if(typeof(objDATA)=='undefined'){
			//console.log("up:"+gIntSubmitNum);
			$.data($(arrFM[gIntSubmitNum]).ajaxSubmit(arrOPTION[gIntSubmitNum])[0],"jqxhr").done(function(){
				t()
			}).fail(function(){
				//console.log("fail");
				t()
			});
		}else{
			//console.log("go:"+gIntSubmitNum);
		}
    };

    $.each(oForm,
		function(i, fm) {
			var options = {iframe:true};
			//fm.pDocID.value = gForm.CurDocID.value;
			fm.pDocID.value = gDocKey;
			fm.ContID.value = (contId == "" ? "": (contId));
			options.success = function(text) {
				if (text.indexOf("Success") < 0) {
					fm.innerHTML = "<b color=red>"+PublicField.UpMsg3+"</b>";
				} else {
					fm.innerHTML = PublicField.UpMsg4;
				}
				gIntSubmitNum++;
			};
			options.error = function(e) {
				fm.innerHTML = "<b color=red>"+PublicField.UpMsg3+"</b>";
				gIntSubmitNum++;
			};
			arrFM.push(fm);
			arrOPTION.push(options);
		}
	);
	var gSetInterval=setInterval(f,100);
}
*/
//编辑附件
function goEditAttachFile(s) {
	var arrFile = $("input[type=checkbox]:checked", "#"+s);
    if (arrFile.length > 0) {
        var arrF = ["doc", "docx", "xls", "xlsx"];
        if ($.inArray(arrFile[0].getAttribute("suffix"), arrF) > -1) {
            var pstatus = "height=" + (screen.height - 60) + ",width=" + (screen.width - 20) + ",top=0,left=3,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
            window.open("/" + gCurDB + "/vwComEditAttach/" + arrFile[0].value + "?EditDocument", "", pstatus);
            return
        } else {
            //alert("该附件不能被编辑！")
            alert(PublicField.UpMsg7)
        }
    } else {
        //alert("没有可编辑的附件，请先选择！")
        alert(PublicField.UpMsg8)
    }
}

//删除附件
function goDelAttachFile(s) {
	var arrFile = $('#'+s+' input[name="$$attachfile"]:checked');

    if (arrFile.length > 0) {
        //if(confirm("您是否需要删除所选附件？")){
        if (confirm(PublicField.UpMsg5)) {
            $.each(arrFile,
            function(i, item) {
                $.ajax({
                    url: "/" + gCommonDB + "/(agtComDelAttach)?OpenAgent&DocID=" + item.value,
                    dataType: "text",
                    cache: false,
                    success: function(txt) {
                        if (parseInt(txt) == 1) {
                            loadAttachGrid(true);
                        }
                    }
                })
            });
        }
    } else {
        alert("没有可删除的附件，请先选择！")
      //  alert(PublicField.UpMsg6)
    }
}