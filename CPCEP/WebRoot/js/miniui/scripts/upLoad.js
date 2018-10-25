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
document.write('<link href="../css/ht/upload.css" rel="stylesheet" type="text/css" />');
$(document).ready(function(){
	loadAttachGrid(false);
})
//加载工具条
function loadAttachToolbar(){
	$("[id$=_att]").each(function(i, c) {
		if ($(c).attr("toolbar") != undefined) {
			var t=mini.decode("{" + c.attributes["toolbar"].value + "}");
			$("<div class='mini-toolbar toolbar'><div class='toolbar-title'><span class='mini-button-text mini-button-icon icon-fj'></span>"+t.title+"</div>"
			 +"<div class='toolbar-btn'>"
			 +"<a class='mini-button "+t.upId+"' plain='true' id='"+t.upId+" Noprint' iconCls='icon-upload' href=\"javascript:goUploadFile('"+$(c).attr("id")+"');\" >\u4e0a\u4f20</a>"
			// +"<a class='mini-button "+t.editId+"' plain='true' id='"+t.editId+" Noprint' iconCls='icon-edit' href=\"javascript:goEditAttachFile('"+$(c).attr("id")+"');\" >\u7f16\u8f91</a>"
			 +"<a class='mini-button "+t.delId+"' plain='true' id='"+t.delId+" Noprint' iconCls='icon-remove' href=\"javascript:goDelAttachFile('"+$(c).attr("id")+"');\" >\u5220\u9664</a>"
			 +"</div></div>").appendTo("#" + c.id)
		}
	});
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
	//加载工具条
	if(!r){
		loadAttachToolbar();
	}
    /*装载已有附件并生成表格*/
    $.ajax({
        url: "/" + gCurDB + "/vwComReadAttach?OpenView&Count=99&ExpandAll&RestrictToCategory=" + curDocId,
        dataType: "text",
        cache: false,
        success: function(txt) {
            if (r) {
                $("[id$='_tab']").empty();
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
                                trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy' style='width:"+z[i]+";"+sty+"'>" + n + "</td>");
                            });
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody class='attBody' id='" + (item.id + "_tab") + "'></tbody></table>").appendTo("#" + item.id);
                        } else {
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (item.id + "_tab") + "'></tbody></table>").appendTo("#" + item.id);
                        }
                    }

                    i = 1;
                    var attObjs = {};
                    var tdHTML = "";
                    $.each($.grep(tempItem,
                    function(json) {
                        return json.contId == item.id;
                    }),
                    function(num, e) {
                        attObjs = eval("({" + item.attributes["columns"].value + "})");
                        for (var _X in attObjs) {
                            if (_X == "docid") {
                                tdHTML += "<td class='mini-grid-cell attCellTdDiy' width='" + attObjs[_X] + "'><input class='Noprint' type='checkbox' suffix='" + e.suffix + "' name='$$attachfile' value='" + e[_X] + "'/></td>";
                            } else if (_X == "num") {
                                tdHTML += "<td class='mini-grid-cell attCellTdDiy'  width='" + attObjs[_X] + "'>" + (i++) + "</td>";
                            } else if (_X == "showname") {
                                tdHTML += "<td class='mini-grid-cell attCellTdDiy'  width='" + attObjs[_X] + "' style='text-align:left'>&nbsp;&nbsp;<a href='/" + gCurDB + "/0/" + e['docid'] + "/$File/" + e['name'] + "' target='_blank'>" + e[_X] + "</a></td>";
                            } else {
                                tdHTML += "<td class='mini-grid-cell attCellTdDiy'  width='" + attObjs[_X] + "'>" + e[_X] + "</td>";
                            }
                        }
                        $("<tr class='attTr" + num % 2 + "'>" + tdHTML + "</tr>").appendTo("#" + e.contId + "_tab");
                        tdHTML = "";
						$(item).css("border-bottom","0px")
                    });
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
                                trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy' style='width:"+z[i]+";"+sty+"'>" + n + "</td>");
                                cols += 1;
                            });
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody id='" + (item.id + "_tab") + "'><tr><td style='text-align:center' colspan='" + cols + "'>\u6682\u65e0\u9644\u4ef6\u3002</td></tr></tbody></table>").appendTo("#" + item.id);
                        } else {
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (item.id + "_tab") + "'><tr><td>\u6682\u65e0\u9644\u4ef6\u3002</td></tr></tbody></table>").appendTo("#" + item.id); //暂无附件。
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
                                trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy' style='width:"+z[i]+";"+sty+"'>" + n + "</td>");
                                cols += 1;
                            });
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody id='" + (item.id + "_tab") + "'><tr><td style='text-align:center' colspan='" + cols + "'>\u6682\u65e0\u9644\u4ef6\u3002</td></tr></tbody></table>").appendTo("#" + item.id);
                        } else {
                            $("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (item.id + "_tab") + "'><tr><td>\u6682\u65e0\u9644\u4ef6\u3002</td></tr></tbody></table>").appendTo("#" + item.id); //暂无附件。
                        }
                    }
					$(item).css("border-bottom","1px solid #ccc")
                })
            }
        }
    })
}
/*以下是文件上传函数*/
function goUploadFile(contId) {
    //if(arguments.length==0){alert("请先设置附件显示区域id，id命名以“_att”结尾。");return;}
    if (arguments.length == 0) {
        alert("\u8bf7\u5148\u8bbe\u7f6e\u9644\u4ef6\u663e\u793a\u533a\u57dfid\uff0cid\u547d\u540d\u4ee5\u201c_att\u201d\u7ed3\u5c3e\u3002");
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
                            title: '\u4e0a\u4f20\u9644\u4ef6',
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
                            name: "\u4e0a\u4f20",
                            title: "\u4e0a\u4f20\u9644\u4ef6",
                            ico: "icon-save",
                            clickEvent: "ifrSubmit('" + (contId ? contId: "") + "')",
                            align: "2",
                            isHidden: "0"
                        },
                        {
                            name: "\u5173\u95ed",
                            title: "\u5173\u95ed",
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
function ifrSubmit(contId) {
    if (mini.get("oWinDlgFile").isLoading) {
        alert('\u6b63\u5728\u4e0a\u4f20\uff0c\u8bf7\u60a8\u8010\u5fc3\u7b49\u5f85\uff01'); //正在上传，请您耐心等待！
        return;
    }
    var oForm = $.grep($("#oWinDlgFile form"),
    function(item) {
        return $('input[type="file"]', item)[0].value != ""
    }),
    intM = 0;
    //if(oForm.length<1){alert("请选择需要上传的文件！");return};
    if (oForm.length < 1) {
        alert("\u8BF7\u9009\u62E9\u9700\u8981\u4E0A\u4F20\u7684\u6587\u4EF6\uFF01");
        return
    };
    mini.get("oWinDlgFile").loading("\u6b63\u5728\u4e0a\u4f20...");
    mini.get("oWinDlgFile").set({
        "isLoading": true
    });
    var f = function() {
        mini.get("oWinDlgFile").destroy();
        loadAttachGrid(true);
    };
    var options = {
        //target:"#fileloader",                     
        //success:function(){alert(1)},
    };
    $.each(oForm,
    function(i, fm) {
        //fm.pDocID.value = gForm.CurDocID.value;
		fm.pDocID.value = curDocId;
        fm.ContID.value = (contId == "" ? "": (contId));
        options.success = function(text) {
            if (text.indexOf("Success") < 0) {
                fm.innerHTML = "<b color=red>\u4E0A\u4F20\u5931\u8D25\uFF01\u9644\u4EF6\u53EF\u80FD\u592A\u5927\uFF01</b>";
            } else {
                fm.innerHTML = "\u4E0A\u4F20\u6210\u529F\uFF01";
            }
            intM++;
            if (intM == oForm.length) {
                window.setTimeout(f, 1000)
            }
        };
        options.error = function(e) {
            fm.innerHTML = "<b color=red>\u4E0A\u4F20\u5931\u8D25\uFF01\u9644\u4EF6\u53EF\u80FD\u592A\u5927\uFF01</b>";
            intM++;
            if (intM == oForm.length) {
                window.setTimeout(f, 1000)
            }
        };
        $(fm).ajaxSubmit(options);
    });
}

//编辑附件
function goEditAttachFile(s) {
	if($.browser.msie){
		var a = $("input[type=checkbox]:checked", "#"+s);
		if (a.length > 0) {
			var b = ["doc", "docx", "xls", "xlsx"];
			if ($.inArray(a[0].getAttribute("suffix"), b) > -1) {
				var c = "height=" + (screen.height - 60) + ",width=" + (screen.width - 20) + ",top=0,left=3,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
				window.open("/" + gCurDB + "/vwComEditAttach/" + a[0].value + "?EditDocument", "", c);
				return
			} else {
				alert("\u8BE5\u9644\u4EF6\u4E0D\u80FD\u88AB\u7F16\u8F91\uFF01")
			}
		} else {
			alert("\u6CA1\u6709\u53EF\u7F16\u8F91\u7684\u9644\u4EF6\uFF0C\u8BF7\u5148\u9009\u62E9\uFF01")
		}
	}else{
		alert("\u60a8\u597d\uff0c\u975e\u6d4f\u89c8\u5668\u4e0b\u4e0d\u80fd\u4f7f\u7528\u6b64\u529f\u80fd\uff01");
	}
}
//删除附件
function goDelAttachFile(s) {
	var arrFile = $('#'+s+' input[name="$$attachfile"]:checked');

    if (arrFile.length > 0) {
        //if(confirm("您是否需要删除所选附件？")){
        if (confirm("\u60A8\u662F\u5426\u9700\u8981\u5220\u9664\u6240\u9009\u9644\u4EF6\uFF1F")) {
            $.each(arrFile,
            function(i, item) {
                $.ajax({
                    url: "/" + gCurDB + "/(agtComDelAttach)?OpenAgent&DocID=" + item.value,
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
        //alert("没有可删除的附件，请先选择！")
        alert("\u6CA1\u6709\u53EF\u5220\u9664\u7684\u9644\u4EF6\uFF0C\u8BF7\u5148\u9009\u62E9\uFF01")
    }
}