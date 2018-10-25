var gWFProcessXML = null, 					//流程处理XML对象，用于流程提交处理
	gCurNode = null,		
	gArrTacheName = null,						// 存储与当前节点有关联的所有目标流程环节名称
	gWFLogXML = null,							// 流程日志XML对象，用于流程流转日志及意见的显示
	gJsonField = null,							// 存储当前流程环节节点的所有域的状态JSON对象
	gIdeaID = [],								// 存储所有意见ID标识
	gArrLogUser = [],							// 所有已处理过的用户
	gAction = [],			// 当前用户操作动作，->"存储目标环节点人员"
	gArrBtns=[],
	gPageEvent = {
	   	"OpenBefore": "",						// 页面Dom装载后，流程过程处理前行为
	    "OpenAfter": "",						// 页面Dom装载后，流程过程处理后行为
	    "SaveBefore": "",						// 流程保存或提交前行为
	    "SaveAfter": ""							// 流程处理后保存表单前行为
	};
var gCloseBtn=[
	{name:"关闭",icon:"btn-default",func:"toClose()",align:"right"}
];

function showWfInfo(){
	var strURL = gDir+"/workflow/wfInfoView.jsp?docId="+$("[name=uuid]").val();
	miniOpen({url:strURL,title:"流程查看",width:850,height:600});
}

function miniOpen(obj) {
	var strURL = obj.url, strTitle = obj.title, intWidth = 700, intHeight = 500;
	if(obj.width){
		intWidth = obj.width;
	}
	if(obj.height){
		intHeight =obj.height;
	}
	var oWinDlg = mini.get('oWinDlg');
	if (oWinDlg == null) {
		oWinDlg = mini.open({
			id : "oWinDlg",
			showMaxButton : true,
			headerStyle : "font-weight:bold;letter-spacing:4px",
			footerStyle : "padding:5px;height:25px"
		});
	}
	oWinDlg.setUrl(strURL);
	oWinDlg.setTitle(strTitle);
	oWinDlg.setWidth(intWidth);
	oWinDlg.setHeight(intHeight);
	oWinDlg.showAtPos("center", "middle");
}

function repXMl(xmlStr){
	return $.parseXML(xmlStr.replace(/[\r\n\t]/g,"").replace(/\&/g, "&amp;"));
}
function loadXml(){
	if(!gForm){
		gForm=document.forms[0];
	}
	var tmpCurUser = $("[name=curUser]").val().replace(/\s/g, ""),
    arrTmpCurUser = tmpCurUser.indexOf(",") > -1 ? tmpCurUser.split(",") : tmpCurUser.split(";");
	gWFProcessXML = repXMl($("[name=wfProcessXml]").val());
	if($("[name=wfFlowLogXml]").val() && $("[name=wfFlowLogXml]").val()!=""){
		gWFLogXML = repXMl($("[name=wfFlowLogXml]").val());
	}else{
		gWFLogXML = $.parseXML("<Log />");
	}
	
	if($("[name=uuid]").val()!=""){
		gCloseBtn.unshift({name:"流程查看",icon:"btn-default",func:"showWfInfo()",align:"right"})
	}
	
    initOnLoad(arrTmpCurUser);
    pageUnload();
    var tip = new mini.ToolTip();
    tip.set({
        target: document,
        selector: '[data-tooltip], [title]'
    });
}

function pageUnload(){
	setTimeout(function(){
        $("#bg").remove();
        $("#bg-loader").remove();
        $("body").css("overflow","auto");
    },500)
}

function setFieldErrorTip(obj){
	if (obj.id.indexOf("js-") > -1) {
		return;
	}
	var vtype="";
	var minLen=0,maxLen=0;
	if(obj.datatype!=undefined && obj.datatype!=""){
		vtype=obj.datatype;
		if(vtype=="int"){
			$("[name="+obj.id+"]").attr("intErrorText", obj.name+"必须为整数！");
		}else if(vtype=="float"){
			$("[name="+obj.id+"]").attr("floatErrorText", obj.name+"必须为浮点类型！");
		}else if(vtype=="email"){
			$("[name="+obj.id+"]").attr("emailErrorText", obj.name+"必须为邮箱格式！");
		}
	}
	if(obj.minLength!=undefined && obj.minLength>0){
		minLen=obj.minLength;
	}
	if(obj.maxLength!=undefined && obj.maxLength>0){
		maxLen=obj.maxLength;
	}
	if(minLen>0 && maxLen>0){
		$("[name="+obj.id+"]").attr("rangeLengthErrorText", obj.name+"字符数必须在 "+minLen+" 到 "+maxLen+" 之间！");
		if(vtype!=""){vtype+=";"}
		vtype+="rangeLength:"+minLen+","+maxLen;
	}else if(minLen>0){
		$("[name="+obj.id+"]").attr("minLengthErrorText", obj.name+"不能少于 "+minLen+" 个字符！");
		if(vtype!=""){vtype+=";"}
		vtype+="minLength:"+minLen;
	}else if(maxLen>0){
		$("[name="+obj.id+"]").attr("maxLengthErrorText", obj.name+"不能超过  "+maxLen+" 个字符！");
		if(vtype!=""){vtype+=";"}
		vtype+="maxLength:"+maxLen;
	}
	$("[name="+obj.id+"]").attr("vtype", vtype);
}
/*
//根据流程规则，初始化表单相关属性及事件
@arrTmpCurUser 当前流程处理人员
*/
function initOnLoad(arrTmpCurUser) {
    //页面装载前执行全局方法
	if(typeof beforeLoad != "undefined"){
		beforeLoad()
	}
    //编辑状态下执行
    $.each($('textarea[name^="ID_"]', gForm),
    function(i, item) {
        $("<div></div>").insertBefore(item).attr("id", $(item).attr("name"));
        if (gIsEditDoc) {
            $(item).remove()
        }
    });
    if (!gIsEditDoc) {
		log("$进入可编辑模式$");
        var strCurID = gIsNewDoc ? ($(gWFProcessXML).find("Process").attr("OriginNode")) : gForm.wfCurNodeId.value;
        gCurNode = $(strCurID, gWFProcessXML);
        if (gIsNewDoc) {
        	$(gWFLogXML).find("Log").attr("OriginNode",$(gWFProcessXML).find("Process").attr("OriginNode"))
            gForm.wfCurNodeId.value = strCurID;
            gForm.wfTacheName.value = getNodeValue(gCurNode[0], "WFNodeName");
        }
        if (gWfStatus < 2) {
            //读取初始化事件
            $.each($(gForm.wfCurNodeId.value + ">WFOpenBefore", gWFProcessXML),
            function(i, item) {
                gPageEvent["OpenBefore"] = item.getAttribute("value")
            });
            $.each($(gForm.wfCurNodeId.value + ">WFOpenAfter", gWFProcessXML),
            function(i, item) {
                gPageEvent["OpenAfter"] = item.getAttribute("value")
            });
            $.each($(gForm.wfCurNodeId.value + ">WFSaveBefore", gWFProcessXML),
            function(i, item) {
                gPageEvent["SaveBefore"] = item.getAttribute("value")
            });
            $.each($(gForm.wfCurNodeId.value + ">WFSaveAfter", gWFProcessXML),
            function(i, item) {
                gPageEvent["SaveAfter"] = item.getAttribute("value")
            });

            //装载前执行函数
            var _pe = gPageEvent["OpenBefore"];
            if (_pe.replace(/\s/, "") !== "") {
                try {
					//V6.1 - 屏蔽eval功能(2015-09-16)
					new Function(_pe)();
                } catch(e) {
					//V6.1 * 调整为多语言 (2015-09-18)
                    alert(WF_CONST_LANG.OPEN_BEFORE + " < " + _pe + " > " + WF_CONST_LANG.PAGE_NO_INIT);
                }
            }
			log("*装载按钮*");
			gArrBtns=mini.decode($(gCurNode).find("WFBtnAssign").attr("value"));
			gJsonField=mini.decode($(gCurNode).find("WFFieldStatus").attr("value"));
			$.each(gJsonField,function(){
				switch (this.status) {
				case "edit":
					if (this.id.indexOf("ID_") > -1) {
						gIdeaID.push(this.id)
						/*存储意见的ID*/
					}
					setFieldErrorTip(this);
					break;
				case "read":
					if (this.id.indexOf("ID_") > -1) {
						$("[name="+this.id+"]").remove()
					}else{
						$("[name="+this.id+"]").attr("enabled", false)
					}
					break;
				case "must":
					
					/*编辑*/
					if (this.id.indexOf("ID_") > -1) {
						gIdeaID.push(this.id)
						/*存储意见的ID*/
					}
					$("[name="+this.id+"]").attr("required", true);
					$("[name="+this.id+"]").attr("requiredErrorText", this.tip);
					setFieldErrorTip(this);
					break;
				case "hide":
					/*隐藏*/
					if (this.id.indexOf("js-") > -1) {
						$("#"+this.id).css("display", "none");
					} else {
						if (this.id.indexOf("ID_") > -1) {
							$("[name="+this.id+"]").empty()
						}else{
							$("[name="+this.id+"]").attr("visible", false)
						}
					}
					break;
				default:
					setFieldErrorTip(this);
				}
			})
            
            
            //装载后执行函数
            var _pe = gPageEvent["OpenAfter"];
            if (_pe.replace(/\s/, "") != "") {
                try {
					//V6.1 * 屏蔽eval功能(2015-09-16)
					new Function(_pe)();
                } catch(e) {
					//V6.1 * 调整为多语言 (2015-09-18)
                    alert(WF_CONST_LANG.OPEN_AFTER + " < " + _pe + " > " + WF_CONST_LANG.PAGE_NO_INIT);
                }
            }
        }
    }
	else 
	{
		log("$进入只读模式$");
		labelModel();
    }
	gJsonField=null;
	log("*开始生成按钮*");
	var isChinese=function(v) {
		var re = new RegExp("^[\u4e00-\u9fa5]+$");
		if (re.test(v)) return true;
		return false;
	};
	
	var btndom = '<button class="btn btn-md" type="button"></button>';
	gArrBtns=gArrBtns.concat(gCloseBtn);
	$.each(gArrBtns,function(i){
		var gBtn=$(this.align == "left" ? $(btndom).appendTo("#btnContL") : $(btndom).appendTo("#btnContR"))
		.html(isChinese(this.name)?this.name.split("").join("&nbsp;&nbsp;"):this.name)
		.addClass(this.icon)
		.attr({
            onClick: this.func,
            style: "margin:0px 7px 15px 7px"
        });
		log("　　按钮"+i+": "+this.name+"|"+this.icon+"|"+this.func);
	})
	log("*结束生成按钮*");
    
    if (!gIsNewDoc) {
        //生成意见
        if (gWFLogXML.childNodes) {
			log("*开始生成意见*");
            var sTacheNum = 1, strId = "", WFIdeaPrefix = "", WFIdeaSuffix = "";
            $.each($(gWFLogXML).children(":first").children(),
            function(i, item) {
				log(XML2String(item));
				if(typeof(unionIdea)=="undefined" || !unionIdea){
					strId = item.getAttribute("id") ? $.trim(item.getAttribute("id")) : "";
					if (strId.indexOf("ID_") > -1) {
						var _time = item.getAttribute("time"),
						_tache=item.getAttribute("tache"),
						_tdID = "td" + strId + sTacheNum,
						_idea = DecodeHtml(item.getAttribute("idea")),
						_mark = item.getAttribute("mark"),
						_status = item.getAttribute("status");
						
						WFIdeaPrefix = '<div class="ideaDiv" '+($(".ideaDiv").length>0 ? 'style="margin-top:5px;"': '')+'><div class="nodeDiv"><b>环节名称：'+_tache+'</b></div><div id="' + _tdID + '" class="textDiv" '+ (_status != 'undefined' && _status == '拒绝' ? 'style="color:red;"': '') + '>未填写。</div>';
						WFIdeaSuffix = '<div class="signDiv"><b><span style="margin-right:25px;">审批人：' + item.getAttribute("user") + '</span><span >' + _time + '</span></b></div></div>';
						$("#showWFIdea").append(WFIdeaPrefix+WFIdeaSuffix);
						if(_idea==""){
							_idea="未填写。";
						}
						$("#" + _tdID).empty().html(_idea);
						/*
						WFIdeaPrefix = '<tr><td class="tdTecheName" style="width:150px;text-align:center">'+_tache+'</td><td class="tdIdea" ' + (_mark != 'undefined' && _mark == '1' ? 'style="color:red;"': '') + '><div id="' + _tdID + '">&nbsp;</div></td>';
						WFIdeaSuffix = '<td class="tdIdeaUser">' + item.getAttribute("user") + '</td><td class="tdIdeaTime">' + _time + '</td></tr>';
						$("#showWFIdea").append(WFIdeaPrefix+WFIdeaSuffix);
						$("#" + _tdID).empty().html(_idea);
						*/
					}
				}else{
					if(typeof(document.getElementById("ideaArea"))=="undefined"){
						alert(WF_CONST_LANG.IDEA_AREA_UNDEFINDED); //意见显示区域未定义
					}else{
						strId = "ID_ideaArea";
						if(item.getAttribute("idea")!=undefined){
							var _time = item.getAttribute("time"),
								_tache=item.getAttribute("tache"),
								_tdID = "td" + strId + sTacheNum,
								_idea = DecodeHtml(item.getAttribute("idea"))==""?"\u9605\u3002":DecodeHtml(item.getAttribute("idea")), //如果意见为空则显示"阅。"
								_mark = item.getAttribute("mark");
							WFIdeaPrefix = '<tr><td class="tdTecheName" style="width:150px;text-align:center">'+_tache+'</td><td class="tdIdea" ' + (_mark != 'undefined' && _mark == '1' ? 'style="color:red;"': '') + '><div id="' + _tdID + '">&nbsp;</div></td>';
							WFIdeaSuffix = '<td class="tdIdeaUser">' + item.getAttribute("user") + '</td><td class="tdIdeaTime">' + _time + '</td></tr>';
							$("#showWFIdea").append(WFIdeaPrefix+WFIdeaSuffix);
							$("#" + _tdID).empty().html(_idea);
						}
					}
				}
                sTacheNum++;
            });
			log("*结束生成意见*");
        }
    }
    mini.parse();
  //页面装载后执行全局方法
	if(typeof(afterLoad)!="undefined"){
		afterLoad();
	}
	log("----页面装载完毕----");
	log("");
}

//获取XML节点属性的值
function getNodeValue(node, name) {
    var reValue = "";
    $.each($(name, node),
    function(i, item) {
        reValue = item.getAttribute("value").replace(/@line@/g, "")
    });
    return $.trim(reValue);
}

function fnResumeDisabled() {
	//恢复部分域的失效状态，以保证“文档保存”时值不会变为空
	$("input[disabled],textarea[disabled],select[disabled]").prop("disabled",false);
}

function wfSaveBefore(){
	//页面提交前执行
    var _pe = gPageEvent["SaveBefore"];
    if (_pe.replace(/\s/, "") != "") {
        try {
			//V6.1 *屏蔽eval功能(2015-09-16)
			new Function(_pe)();
        } catch(e) {
            alert(WF_CONST_LANG.SAVE_BEFORE + "< " + _pe + " > "+WF_CONST_LANG.PAGE_NO_INIT);
        }
    }
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


function wfSubDocStart() {
	log("");
	log("----页面提交开始----");
	var isLock=false;
	var lastPerson="";//最后一次保存人
	
	//用于页面验证
	var tmpform = new mini.Form(gForm);
	tmpform.validate();
	if (tmpform.isValid() == false){
		showErrorTexts(tmpform.getErrorTexts());
		log("*表单验证失败,终止提交*");
		return;
	}
	
	log("当前节点ID(WFCurNodeID): ",gForm.wfCurNodeId.value);
    var objCurNode = $(gForm.wfCurNodeId.value, gWFProcessXML);
    var isWFOrg = getNodeValue(objCurNode[0], "WFWithOrg");
    if(isWFOrg=="0"){
    	isWFOrg=false;
    }else{
    	isWFOrg=true;
    }
	
	log("是否启用组织机构: ",isWFOrg);
    if (arguments.length == 1) {
        gWQSagent = arguments[0];
    }
    
    //页面提交前执行
    var _pe = gPageEvent["SaveBefore"];
    if (_pe.replace(/\s/, "") != "") {
        try {
			//V6.1 *屏蔽eval功能(2015-09-16)
			new Function(_pe)();
        } catch(e) {
            alert(WF_CONST_LANG.SAVE_BEFORE + "< " + _pe + " > "+WF_CONST_LANG.PAGE_NO_INIT);
        }
    }
   
	gArrTacheName = []; //清空
    if (!gIsNewDoc) {
        /*
		多人审批，及多人顺序审批
		 */
        if (objCurNode.length == 1) {
            var strAppoveStyle = "",
            strSequenceApprove = "",
            intApproveNum = 0,
            tmpGetValue = "";
            strSequenceApprove = getNodeValue(objCurNode[0], "WFSequenceApprove");
            strAppoveStyle = getNodeValue(objCurNode[0], "WFApproveStyle");
            tmpGetValue = getNodeValue(objCurNode[0], "WFApproveNum");
			log("审批方式: ",strAppoveStyle," ","审批人数: ",tmpGetValue," ","是否顺序审批: ",strSequenceApprove);
            if (tmpGetValue != "") {
                intApproveNum = parseInt(tmpGetValue, 10)
            }
            if (strAppoveStyle == "1") { //多人
                var arrFinishUser = [],
                strWF = gForm.wfFinishApproval.value.replace(/\s/g, ""),
                bGo2Next = true;
                if (strWF != "") {
					log("已处理完人员: ",strWF);
                    arrFinishUser = strWF.split(";")
                };
                if ($.grep(arrFinishUser,
                function(item) {
                    return $.trim(item) == gLoginUser
                }).length == 0) {
                    arrFinishUser.push(gLoginUser)
                }
                gForm.wfFinishApproval.value = arrFinishUser.join(";");
                if (strSequenceApprove == "0") { //是
					log("*顺序审批*");
                    /*
					如果是所人审批情况：最好能逐一添加人员进行处理，当第二个人以后审批时，通知方式可以读取与当前节点相关的路由
					如果当前审批人是领导，有相关的督办人员，督办人员可督促，可协办处理。这种情况时，CurUser存放2个人，一个是领导人，一个是督办人。(待开发)
					 */
                    if ($.trim(gForm.wfWaitApproval.value) == "") {
                        gForm.wfWaitApproval.value = "";
                        gForm.alreadyUser.value="";
						log("多人审批已经处理完毕");
                        bGo2Next = false;
                    } else {
                        var arrWaitUsers = gForm.wfWaitApproval.value.replace(/\s/g, "").split(";");
                        var arrWaitIds = gForm.alreadyUser.value.replace(/\s/g, "").split(";");
						log("多人审批->原先所有等待处理人: ",gForm.wfWaitApproval.value);
                        gForm.curUser.value = arrWaitUsers[0];
                        gForm.userId.value = arrWaitIds[0];
						log("多人审批->待处理人: ",gForm.curUser.value);
                        gForm.wfWaitApproval.value = arrWaitUsers.slice(1).join(";");
                        gForm.alreadyUser.value= arrWaitIds.slice(1).join(";");
						log("多人审批->将要待处理人: ",gForm.wfWaitApproval.value);
                    }
                } else {
					log("*随机审批*");
					gForm.approvalOrder.value = "1";
                    var strUser = gForm.curUser.value.replace(/\s/g, "");
                    var strUserIds = gForm.alreadyUser.value.replace(/\s/g, "");
                    //"文档不能进行流转！\n\n当前审批人不应为空，请联系管理员！"
                    if (strUser == "") {
                        alert(WF_CONST_LANG.DOCUMENT_NOT_SUBMIT);
                        return
                    }
					log("多人审批->原先所有待处理人: ",gForm.curUser.value);
                    var arrCurUser = strUser.split(";");
                    var arrCurUserIds = strUserIds.split(";");
                    var arrNewCurUser = $.grep(arrCurUser,
                    function(item) {
                        return $.trim(item) != gLoginUser
                    });
                    var arrNewUserIds=$.grep(arrCurUserIds,
                    		function(item) {
                        		return $.trim(item) != gUserId;
                    	});
                    if (intApproveNum > 0) {
                        /*有人数限制*/
                        if (intApproveNum == arrFinishUser.length) {
                            /*已经审批完的人数等于规定审批人数，表示已处理完毕*/
                            bGo2Next = false;
                        } else {
                            if (arrNewCurUser.length > 0) {
                                gForm.curUser.value = arrNewCurUser.join(";");
                                gForm.alreadyUser.value = arrNewUserIds.join(";");
                                gForm.userId.value = arrNewUserIds.join(";");
                            } else {
                                /*审批总人数小于所设置的审批人数（即待审批人已为空）*/
                                bGo2Next = false
                            }
                        }
                    } else {
                        if (arrCurUser.length == 1) {
                            bGo2Next = false;
                        } else {
                            gForm.curUser.value = arrNewCurUser.join(";");
                            gForm.alreadyUser.value = arrNewUserIds.join(";");
                            gForm.userId.value = arrNewUserIds.join(";");
                        }
                    }
					log("多人审批->待处理人: ",gForm.curUser.value);
                }
				log("是否多人审批已处理完毕: ",bGo2Next?"YES":"NO");
                if (bGo2Next) {
                    var bWFAgreeMark = 0;
                    $('[source="' + gForm.wfCurNodeId.value + '"]', gWFProcessXML).each(function(i, item) {
                        if (getNodeValue(item, "WFAgreeMark") == WF_CONST_LANG.YES) {
                            bWFAgreeMark = 1
                        }
                    });
                    //CONFIRM_SUBMIT:您确定提交吗？
                    if (confirm(WF_CONST_LANG.CONFIRM_SUBMIT)) {
                        wfSubDocEndSave(false, bWFAgreeMark)
                    } else {
                        gForm.wfStatus.value = gWFStatus
                    }
                    return
                }else{
                	gForm.approvalOrder.value = "0";
                }
            }
        }
    }
    /*
	直连：两个节点间无任何干扰直接传递；
	唯一选择：在环节名称列表中存在；
	条件选择：满足某种条件后，方可在环节名称列表中存在；
	条件直连：满足某种条件后，不在环节列表中存在，直接提交到下一环节；
	阅办:可临时用于可传阅给其他人员办理。
	 */

    gForm.wfStatus.value = 1;
    var arrEdges = $('[source="' + gForm.wfCurNodeId.value + '"]', gWFProcessXML);
    
    if (arrEdges.length == 1) {
		log("路由分支: ",1," 条");
        //一个路由线时，连接类型只能是唯一或直连
        //直连时，不需要弹出框，直接提交给下一环节已经定义好的审批人。
        //唯一选择时，需要弹出框，提交给所选择的人。
        //注：下一环节点为结束节点时，是特殊情况，需加注意。
        var vRelationType = getNodeValue(arrEdges[0], "WFRelationType");
		log("路由类型: ",vRelationType);
        if (vRelationType != "") {
            //WORKFLOW_END:流程结束
            var tarID = arrEdges[0].getAttribute("target"),strTacheName = WF_CONST_LANG.WORKFLOW_END;
            var msgConfig=getEdgeConfig(arrEdges[0]);
            ClearRepeat("wfRouterId", arrEdges[0].nodeName); //增加路由线
			//DIRECT:直连
            if (vRelationType == "0") {
                if (!confirm(WF_CONST_LANG.CONFIRM_SUBMIT)) {
                    gForm.wfStatus.value = gWFStatus;
                    return
                }
				/*结束节点*/
                if (tarID.indexOf("E") > -1) {
					log("直连 --> 流程结束");
                    gForm.wfStatus.value = 2;
                    wfSubDocEnd("", [], strTacheName);
                    return
                }
                var nxtNode = $(tarID, gWFProcessXML);
                if (nxtNode.length == 1) {
					strTacheName = getNodeValue(nxtNode[0], "WFNodeName");
					tmpValue = getNodeValue(nxtNode[0], "WFActivityOwner");
					strAppoveStyle = getNodeValue(nxtNode[0], "WFApproveStyle");
					//var strFormula = getNodeValue(nxtNode[0], "WFFormula");
				
					log("环节名称: ",strTacheName);
					log("环节处理人: ",tmpValue);
					//log("环节审批类型: ",strAppoveStyle);
					//log("是否网页公式: ",strFormula);
					if(gIsAppointUser==1){
						wfSubDocEnd(tarID, [], strTacheName)
					}else if (tmpValue != "") {
						gArrLogUser = rtuArrPerson(mini.decode(tmpValue));
						log("直连",gArrLogUser);
						if (gArrLogUser.length == 1) {
							wfSubDocEnd(tarID, gArrLogUser, strTacheName)
						} else {
							//MUTIL_PERSON:多人
							if (strAppoveStyle != "1") {
								alert(WF_CONST_LANG.ROUTER_RELATIION_SCENE);
							} else {
								wfSubDocEnd(tarID, gArrLogUser, strTacheName);
								return;
							}
						}
					} else {
						alert(WF_CONST_LANG.NO_FIND_NEXT_PERSON);
					}
                } else {
                    alert(WF_CONST_LANG.NEXT_NODE_NOT_EXITED);
                    return
                }
			//ONLY_SELECT:唯一选择
            } else if (vRelationType == "1") {
                /*结束节点*/
                if (tarID.indexOf("E") > -1) {
					log("唯一选择 --> 流程结束");
                    gForm.wfStatus.value = 2;
                    wfSubDocEnd("", [], strTacheName);
                    return
                }
                strTacheName = getNodeValue(arrEdges[0], "WFNodeDetail");
                strTacheNameSelect = getNodeValue(arrEdges[0], "WFTacheNameSelect");
                gArrTacheName.push([strTacheName, tarID + "^" + arrEdges[0].nodeName, strTacheNameSelect,msgConfig]);
				log("目标环节名称: ",strTacheName);
				log("是否默认选中: ",strTacheNameSelect);
				log("目标环节编号: ",arrEdges[0].nodeName);
                var oWinDlg = mini.get('oWinDlg');
                if (!oWinDlg) {
					oWinDlg=new mini.Window();
                    oWinDlg.set({
                        id: "oWinDlg",
                        title: WF_CONST_LANG.SELECT_APPROVER, //选择处理人
                        url: '',
                        allowDrag: true,
                        allowResize: false,
                        showModal: true,
                        enableDragProxy: false,
                        showFooter: true,
                        showCloseButton: false,
                        footerStyle: "padding:6px 12px;",
                        width: 510,
                        height: 455
                    });
					oWinDlg.show();
                    mini.mask({
                        el: oWinDlg.getEl(),
                        cls: 'mini-mask-loading',
                        html: WF_CONST_LANG.LIST_LOADING //列表加载中...
                    });
                    $.ajax({
                        url: gDir+'/workflow/htmWFTechSel.jsp?Org='+isWFOrg,
                        async: true,
                        dataType: 'text',
                        success: function(e) {
                            var selOrgDom = e;
                            oWinDlg.setBody(selOrgDom);
                            oWinDlg.setFooter("<div id='SubmitDocActionBar' style='text-align:left'></div>");
                            setTimeout(function(){wfAddToolbar("oWinDlg")},10);
                            mini.unmask(oWinDlg.getEl());
                        }
                    })
                } else {
                    oWinDlg.show()
                }
            } else {
                alert(WF_CONST_LANG.ROUTER_ERROR);
                return;
            }
        }
    }
	else
	{
        /*
		多条路由情况：
		1、多条唯一线
		2、多条唯一线与条件选择或条件直连组合，注意：（只要条件直连满足其它的都无需在进行判断，直接进行提交;常用于“拒绝”、“同意”）
		3、
		 */
		log("路由分支: ",arrEdges.length," 条");
		var bWinDlg=false;
		$.each(arrEdges,function(index,edge){
            var vRelationType = getNodeValue(edge, "WFRelationType"),
            tmpValue = "",
            bReturn = true;
            var msgConfig=getEdgeConfig(edge);
			log("路由类型: ",vRelationType);
			//CONDITION_DIRECT:条件直连
            if (vRelationType == "3") {
                //条件直连；此环境下，无需弹出框，该功能常用于“拒绝”、“同意”等类似情况较多。
                tmpValue = getNodeValue(edge, "WFCondition");
				log("条件公式: ",tmpValue);
                if (tmpValue != "") {
                    if (wfFormulaCompare(gForm, tmpValue)) {
						log("满足条件");
                        ClearRepeat("wfRouterId", edge.nodeName); //增加路由线
                        var tarID = edge.getAttribute("target");
                        if (tarID.indexOf("E") > -1) {
							log(vRelationType," --> 流程结束");
                            gForm.wfStatus.value = 2;
                            //获取路由通知人员
                            getMsgUser(msgConfig);
                            //"流程结束"
                            wfSubDocEnd("", [], WF_CONST_LANG.WORKFLOW_END);
                        } else {
                            var nxtNode = $(tarID, gWFProcessXML);
                            if (nxtNode.length == 1) {
                                strTacheName = getNodeValue(nxtNode[0], "WFNodeName");
								strAppoveStyle = getNodeValue(nxtNode[0], "WFApproveStyle");
                                tmpValue = getNodeValue(nxtNode[0], "WFActivityOwner");
                                var strFormula = getNodeValue(nxtNode[0], "WFFormula");
								log("环节名称: ",strTacheName);
								log("环节处理人: ",tmpValue);
								log("是否网页公式: ",strFormula);
                                if (tmpValue != "") {
                                    gArrLogUser = [];
                                    var ids=[];
                                    $.each($(tarID, gWFLogXML),
                                    function(i, item) {
                                        var _u = item.getAttribute("user");
                                        var _d = item.getAttribute("userId");
                                        if (_d) {
                                            if ($.inArray(_d, ids) < 0) {
                                            	ids.push(_d);
                                                gArrLogUser.push({id:_d,name:_u});
                                            }
                                        }
                                    });
									log(tarID,"环节号已有下列人员参与过处理: ",mini.encode(gArrLogUser));
                                    //
                                    //	拒绝有两种可能:
                                    //		1、拒绝回去的环节点已经参与过审批
                                    //		2、环节点未参与审批
                                    //	不管参与与否，只要检测到该环节点存在多人时，如果所要提交到的环节设置的多人审批，则不弹出对话，否则弹出选择唯一人员。
                                    //	如果以环节点已参与，并且仅有一人参与过，不过是否“多人处理”，就直接进行提交，否则弹出选择。
                                    //	同意情景同上。
                                    //
									
									/*
                                    if (gArrLogUser.length == 0) {
                                        gArrLogUser = $.map(wfFormula(tmpValue.split("|"), strFormula),
                                        function(item) {
                                            return item + "^P"
                                        });
										log("重新获取人员: ",gArrLogUser);
                                    }
                                    var arrLogUser = [];
                                    $.each(gArrLogUser,
                                    function(i, item) {
                                        if ($.inArray(item, arrLogUser) < 0) {
                                            arrLogUser.push(item)
                                        }
                                    });
                                    gArrLogUser = arrLogUser;
                                    */
                                    if (!(strAppoveStyle != "1" && gArrLogUser.length > 1)) {
                                    	//获取路由通知人员
                                    	getMsgUser(msgConfig);
                                        wfSubDocEnd(tarID, gArrLogUser, strTacheName);
                                    } else {
										log("审批方式为: 单人,但有多位处理人,故需弹出选择其一. ",gArrLogUser);
                                        var oCheckUserDlg = new mini.Window();
										oCheckUserDlg.set({
                                            id: "oCheckUserDlg",
                                            title: WF_CONST_LANG.SELECT_APPROVER,
                                            //选择处理人
                                            allowDrag: true,
                                            allowResize: false,
                                            showModal: true,
                                            enableDragProxy: false,
                                            showFooter: true,
                                            footerStyle: "padding:6px 12px;",
                                            showCloseButton: false,
                                            width: 510,
                                            height: 460
                                        });
										oCheckUserDlg.show();
                                        mini.mask({
                                            el: oCheckUserDlg.getEl(),
                                            cls: 'mini-mask-loading',
                                            html: WF_CONST_LANG.LIST_LOADING //列表加载中...
                                        });
                                        $.ajax({
                                            url: gDir+'/workflow/htmWFTechSel.jsp?Back=1',
                                            async: true,
                                            dataType: 'text',
                                            success: function(e) {
                                                var selOrgDom = e;
                                                oCheckUserDlg.setBody(selOrgDom);
                                                oCheckUserDlg.setFooter("<div id='SubmitDocActionBar' style='text-align:right'></div>");
                                                var arrBtns = [{
                                                    name: WF_CONST_LANG.OK,
                                                    title: WF_CONST_LANG.OK_TITLE,
                                                    ico: "icon-ok",
                                                    clickEvent: "wfSubDocProcess('" + tarID + "','" + strTacheName + "','','empTarget')",
                                                    align: "2",
                                                    isHidden: "0"
                                                },
                                                {
                                                    name: WF_CONST_LANG.CANCEL,
                                                    title: WF_CONST_LANG.CANCEL,
                                                    ico: "icon-cancel",
                                                    clickEvent: "mini.get('oCheckUserDlg').destroy();gForm.WFTacheName.value='';",
                                                    align: "2",
                                                    isHidden: "0"
                                                }];
                                                var btnHtml = "";
                                                for (var i = 0; i < arrBtns.length; i++) {
                                                    btnHtml = "<a class='mini-button' plain=true iconCls='" + arrBtns[i].ico + "'>" + arrBtns[i].name + "</a>";
                                                    $(btnHtml).appendTo("#SubmitDocActionBar").attr('onClick', arrBtns[i].clickEvent);
                                                }
                                                mini.parse();
                                                wfAddCheckUser();
                                                mini.unmask(oCheckUserDlg.getEl());
                                            }
                                        })
                                    }
                                } else {
                                    //alert("没有找到下一环节的处理人，请您联系管理员！");
                                    alert(WF_CONST_LANG.NO_FIND_NEXT_PERSON);
                                }
                            }
                        }
						bWinDlg=false;
                        bReturn = false;
                    }
                } else {
                    //路由条件不能为空！
                    alert(WF_CONST_LANG.ROUTER_CONDITION_BLANK);
                    bWinDlg=false;
					bReturn = false;
                }
				log("是否允许弹框: ",bWinDlg?"YES":"NO");
				log("是否阻止循环路由: ",bReturn?"NO":"YES");
			//DIRECT:直连
            } else if (vRelationType == "0") {                
                alert(WF_CONST_LANG.MUTIL_BRANCH_NOT_USE_DIRECT); //多条分支时，不允许使用“直连”。请您联系系统管理员！
                bWinDlg=false;
				bReturn = false;
            } else {
                if (vRelationType == "1") { //唯一选择
                    tmpValue = getNodeValue(edge, "WFNodeDetail");
					log("环节名称: ",tmpValue);
                    if (tmpValue != "") {
                        strTacheNameSelect = getNodeValue(edge, "WFTacheNameSelect");
						log("是否默认选中: ",strTacheNameSelect);
						log("目标环节编号: ",edge.nodeName);
                        gArrTacheName.push([tmpValue, edge.getAttribute("target") + "^" + edge.nodeName, strTacheNameSelect,msgConfig])
                    }
					bWinDlg=true; //允许弹出窗口
                } else {
                    tmpValue = getNodeValue(edge, "WFCondition");
					log("条件公式: ",tmpValue);
                    if (tmpValue != "") {
                        //条件选择时，满足条件就加入环节选择
                        if (wfFormulaCompare(gForm, tmpValue)) {
							log("*满足条件*");
                            tmpValue = getNodeValue(edge, "WFNodeDetail");
							log("环节名称: ",tmpValue);
                            if (tmpValue != "") {
                                strTacheNameSelect = getNodeValue(edge, "WFTacheNameSelect");
								log("是否默认选中: ",strTacheNameSelect);
								log("目标环节编号: ",edge.nodeName);
                                gArrTacheName.push([tmpValue, edge.getAttribute("target") + "^" + edge.nodeName, strTacheNameSelect,msgConfig]);
                            }
							bWinDlg=true; //允许弹出窗口
                        }
                    }
                }
				log("是否允许弹框: ",bWinDlg?"YES":"NO");
				log("是否阻止循环路由: ",bReturn?"NO":"YES");
            }
            return bReturn;
		});
		if(bWinDlg){
			log("*弹出处理人员选择框*");
            var oWinDlg = mini.get('oWinDlg');
            if (!oWinDlg) {
                oWinDlg=new mini.Window();
                oWinDlg.set({
                    id: "oWinDlg",
                    title: WF_CONST_LANG.SELECT_APPROVER,
                    url: '',
                    allowDrag: true,
                    allowResize: false,
                    showModal: true,
                    enableDragProxy: false,
                    showFooter: true,
                    footerStyle: "padding:6px 12px;",
                    showCloseButton: false,
                    width: 510,
                    height: 460
                });
				oWinDlg.show();
                mini.mask({
                    el: oWinDlg.getEl(),
                    cls: 'mini-mask-loading',
                    html: WF_CONST_LANG.LIST_LOADING //列表加载中...
                });
                $.ajax({
                    url:gDir+'/workflow/htmWFTechSel.jsp?Org='+isWFOrg,
                    async: true,
                    dataType: 'text',
                    success: function(e) {
                        var selOrgDom = e;
                        oWinDlg.setBody(selOrgDom);
                        oWinDlg.setFooter("<div id='SubmitDocActionBar' style='text-align:right'></div>");
                        setTimeout(function(){wfAddToolbar("oWinDlg")},10);
                        mini.unmask(oWinDlg.getEl());
                    }
                });
            } else {
                oWinDlg.show()
            }
		}
    }
}

//获取路由通知配置
function getEdgeConfig(edge){
	var msgConfig=mini.decode(getNodeValue(edge,"WFActivityOwner"));
	msgConfig.init=getNodeValue(edge,"WFOnlyInitiator");
	msgConfig.read=getNodeValue(edge,"WFAllReadUsers");
	msgConfig.top=getNodeValue(edge,"WFAllObject");
	return msgConfig;
}

//获取当前路由需要通知的人员
function getMsgUser(msgConfi){
	var arrUserId=[];
	if(msgConfi.init=="1"){
		arrUserId.push($("[name=wfInitiator]").val());
	}
	if(msgConfi.top=="1"){
		arrUserId.push($("[name=wfPreUserId]").val());
	}
	if(msgConfi.read=="1"){
		var allUser=$("[name=allUser]").val();
		if(allUser!=""){
			allUser=allUser.split(";");
			$.each(allUser,function(){
				if(this && this!="" && this.indexOf("^")!=-1){
					arrUserId.push(this.split("^")[0]);
				}
			})
		}
	}
	
	$.each(rtuArrPerson(msgConfi),function(){
		arrUserId.push(this.id);
	})
	
	$("[name=wfMsgUser]").val(arrUserId.join(";"));
}

function wfAddCheckUser() {
	mini.unmask(document.body);
    listbox = mini.get('tacheList');
    $.each(gArrLogUser,
    function(idx, item) {
        listbox.addItem($.parseJSON('{"name":"' + item.split("^")[0] + '","id":"' + item.split("^")[0] + '"}'));
    })
}

function wfSubDocProcess(tarID, TacheName, TacheID, empid) {
    var arrUser = [];
    var lst = mini.get("selectList");
    /*兼容手机*/
    if (lst.data.length > 0) {
        arrUser = lst.data;
        var objCurNode = $(tarID, gWFProcessXML);
        if (objCurNode.length == 1) {
            strAppoveStyle = getNodeValue(objCurNode[0], "WFApproveStyle");
            if (strAppoveStyle == "0" && arrUser.length > 1) {
                //ONLY_SELECT_ONE:仅允许选择一个人！
                alert(WF_CONST_LANG.ONLY_SELECT_ONE);
				mini.unmask(document.body);
                return
            }
        }	
        ClearRepeat("wfRouterId", TacheID); //增加路由线
        //获取当前路由需要通知的人员
        getMsgUser(mini.get("selTacheName").getSelected().msgConfig);
        wfSubDocEnd(tarID, arrUser, TacheName);
    } else {
        alert(WF_CONST_LANG.SELECT_RElATED_PERSON);
		mini.unmask(document.body);
    }
}
function wfSubDocEnd(tarID, Users, TacheName) {
    var bWFAgreeMark = 0;
    $('[source="' + gForm.wfCurNodeId.value + '"]', gWFProcessXML).each(function(i, item) {
        if (item.getAttribute("target") == tarID) {
            if (getNodeValue(item, "WFAgreeMark") == WF_CONST_LANG.YES) {
                bWFAgreeMark = 1
            }
        }
    });
	log("是否意见标识: ",bWFAgreeMark?"YES":"NO");
    gForm.wfPreNodeId.value = gForm.wfCurNodeId.value;
    gForm.wfCurNodeId.value = tarID;
    gForm.wfTacheName.value = TacheName;
	log("目标环节ID: ",tarID);
	log("目标环节名称: ",TacheName);
    if (gForm.wfFinishApproval.value == "") {
        gForm.wfPreUser.value = gForm.curUser.value;
        gForm.wfPreUserId.value = gUserId;
    } else {
        gForm.wfPreUser.value = gForm.wfFinishApproval.value;
        gForm.wfPreUserId.value = gUserId;
        gForm.wfFinishApproval.value = "";
    }
    var bWrite = true;
    
    var uIds=[];
    var uNames=[];
    $.each(Users,function(){
    	uNames.push(this.name);
    	uIds.push(this.id);
    })
    
    //arrUsers = wfFormula(Users, "");
    if (tarID != "") {
        var objCurNode = $(tarID, gWFProcessXML);
        if (objCurNode.length == 1) {
            var strAppoveStyle = "",
            strSequenceApprove = "";
            strAppoveStyle = getNodeValue(objCurNode[0], "WFApproveStyle");
            strSequenceApprove = getNodeValue(objCurNode[0], "WFSequenceApprove");
			log("审批方式: ",strAppoveStyle);
			log("是否顺序审批: ",strSequenceApprove);
			
            if (strAppoveStyle == "1") {
                if (strSequenceApprove == "0") {
                    gForm.curUser.value = uNames[0];
                    gForm.userId.value = uIds[0];
                    gForm.wfWaitApproval.value = uNames.slice(1).join(";");
                    gForm.alreadyUser.value = uIds.slice(1).join(";");
                    bWrite = false;
					log("下一环节为多人顺序审批,审批人为: ",arrUsers[0]);
					log("下一环节为多人顺序审批,待审批人为: ",gForm.WFWaitApproval.value);
                }
            }
        }
    }
    
    if(gIsAppointUser!=1){
        if (bWrite) {
            gForm.curUser.value = uNames.join(";");
            gForm.userId.value = uIds.join(";");
            gForm.alreadyUser.value = uIds.join(";");
    		log("下一环节审批人为: ",gForm.curUser.value);
        }
    }
    
    log("下一环节审批人为: ",gForm.curUser.value);
    wfSubDocEndSave(true, bWFAgreeMark);
}
//去重
function ClearRepeat(source, target) {
	var arrAllUser=[];
    if(gForm[source].value!=""){
    	arrAllUser=gForm[source].value.replace(/\s/g, "").split(";");
    }
    if ($.inArray(target, arrAllUser) < 0) {
        if (target != "") arrAllUser.push(target)
    }
    gForm[source].value = arrAllUser.join(";");
}
//增加环节名称
function wfAddTacheName() {
	var obj=mini.get("selTacheName"), selValue="", arrVal=[], tempData={}, _index="";
	for (var intM = 0, intL = gArrTacheName.length; intM < intL; intM++) {
		tempData.id=gArrTacheName[intM][0] + "^" + gArrTacheName[intM][1];
		tempData.name=gArrTacheName[intM][0];
		tempData.msgConfig=gArrTacheName[intM][3];
        if (gArrTacheName[intM][2] == "1") {
            _index = tempData.id;
        }
		arrVal.push(tempData);
		tempData={};
    }
	obj.set({data:arrVal});
	obj.setValue(_index);
	wfSelTacheChange(obj);
}
// 弹出框，确定按钮事件
function wfDlgBtnSave() {
    if (mini.get("selTacheName").value == "") {
        alert(WF_CONST_LANG.NO_TACHENAME); //环节名称为空，请您先选择！
        return
    }
    if (!confirm(WF_CONST_LANG.CONFIRM_SUBMIT)) {
        gForm.wfStatus.value = gWfStatus;
        return
    }
	mini.mask({
		el : document.body,
		cls : 'mini-mask-loading',
		html : '数据处理中...'
	});
    var arrVal = mini.get("selTacheName").value.split("^");
    wfSubDocProcess(arrVal[1], arrVal[0], arrVal[2], "empTarget");
}
// 改变环节名称
function wfSelTacheChange(tacID) {
    var listbox = mini.get('selectList');
    listbox.removeAll();
    listbox = mini.get('tacheList');
	listbox.loading();
    listbox.removeAll();
    var strFormula = "";
    $.each($(tacID.value.split("^")[1], gWFProcessXML),
    function(i, item) {
        strFormula = getNodeValue(item, "WFFormula");
    });
    
    var Owner=mini.decode($(tacID.value.split("^")[1] + ">WFActivityOwner", gWFProcessXML).attr("value"));
    
	listbox.addItems(rtuArrPerson(Owner));
	listbox.unmask();
}

function rtuArrPerson(Owner){
	var arrP=[];
    if(Owner.P){
    	arrP=Owner.P
    }

    if(Owner.R){
    	var roleIds="";
    	$.each(Owner.R,function(){
    		if(roleIds!=""){ roleIds+=","; }
    		roleIds+=this.id;
    	})
    	if(roleIds!=""){
    		$.ajax({
    			url:gDir+'/admin/findRoleUsersById.action?ids='+roleIds,
    			async: false,
    			dataType: 'text',
    			success: function(data) {
    				$.each(mini.decode(data),function(){
    					if(uniqueUser(arrP,this.id)){
    						arrP.push(this);
    					}
    				})
            	}
    		});
    	}
    }
    
    if(Owner.F){
    	var roleIds="";
    	$.each(Owner.F,function(){
    		if(this.address!=""){
    			var path=this.address;
    			if(path.indexOf("{")>-1&&path.indexOf("}")>-1){
    				var arrItem = (this.address.match(/\(\{.*?}\)|\{.*?}/ig));
    				for(k=0;k<arrItem.length;k++){
    					var tmp = arrItem[k],v="",bC=tmp.match(/\(\{.*?}\)/ig);
    					if(bC){
    						arrItem[k] = arrItem[k].substr(2).slice(0, -2);
    					}else{
    						arrItem[k] = arrItem[k].substr(1).slice(0, -1);
    					}
    					if(mini.getbyName(arrItem[k])){
    						v=mini.getbyName(arrItem[k]).getFormValue()?mini.getbyName(arrItem[k]).getFormValue():mini.getbyName(arrItem[k]).getValue();
    					}else{
    						v=$("[name="+arrItem[k]+"]").val();
    					}
    					v=v.replace(/&/g, '%26');
    					v=v.replace(/=/g, '%3D');
    					v=v.replace(/\?/g, '%3F');
    					path=path.replace(tmp,v);
    				}
    			}
    			if(path.indexOf("[")>-1&&path.indexOf("]")>-1){
    				var arrItem = (this.address.match(/\(\[.*?]\)|\[.*?]/ig));
    				for(k=0;k<arrItem.length;k++){
    					var tmp = arrItem[k],v="",bC=tmp.match(/\(\[.*?]\)/ig);
    					if(bC){
    						arrItem[k] = arrItem[k].substr(2).slice(0, -2);
    					}else{
    						arrItem[k] = arrItem[k].substr(1).slice(0, -1);
    					}
    					v=eval(arrItem[k]);
    					v=v.replace(/&/g, '%26');
    					v=v.replace(/=/g, '%3D');
    					v=v.replace(/\?/g, '%3F');
    					path=path.replace(tmp,v);
    				}
    			}
    			$.ajax({
    	            url:gDir+path,
    	            async: false,
    	            dataType: 'text',
    	            success: function(data) {
    	                $.each(mini.decode(data),function(){
    	                	if(uniqueUser(arrP,this.id)){
    	                		arrP.push(this);
    	                	}
    	                })
    	            }
    	        });
    		}
    	})
    }
    return arrP;
}

function uniqueUser(arr,id){
	var isUn=true;
	$.each(arr,function(){
		if(this.id==id){
			isUn=false;
			return false;
		}
	})
	return isUn;
}
function AddValue(obj, id) {
    var listbox = mini.get(id),objSelTacheName=mini.get("selTacheName");
    var tarID = ((objSelTacheName!=undefined)&&(objSelTacheName.value!="")) ? objSelTacheName.value.split("^")[1] : "";
    if (tarID != "") {
        var objCurNode = $(tarID, gWFProcessXML);
        if (objCurNode.length == 1) {
            var strAppoveStyle = getNodeValue(objCurNode[0], "WFApproveStyle");
            if (strAppoveStyle == "0" && listbox.data.length > 0) {
                alert(WF_CONST_LANG.ONLY_SELECT_ONE); //仅允许选择一个人！
				mini.unmask(document.body);
                return
            }
        }
    }
    if ($.grep(listbox.data,
    function(item) {
        return name == item.name;
    }).length > 0) {
        alert("'" + name + "' "+WF_CONST_LANG.EXITED);
        return;
    } 
    
    listbox.addItem(obj);
}
function DelValue(e) {
    e.sender.removeItem(e.sender.getSelected());
}
function EncodeHtml(s) {
    s = s || '';
    s = s.replace(/&/g, '&amp;');
    s = s.replace(/"/g, '&quot;');
    s = s.replace(/\'/g, '&#39;');
    s = s.replace(/</g, '&lt;');
    s = s.replace(/>/g, '&gt;');
    s = s.replace(/\n/g, '&lt;br&gt;');
    s = s.replace(/\s/g, '&amp;nbsp;');
    return s;
}
function DecodeHtml(s) {
    s = s || '';
    s = s.replace(/&amp;/g, '&');
    //s=s.replace(/&quot;/g,'"');
    //s=s.replace(/&#39;/g,"'");
    //s=s.replace(/&lt;/g,'<');
    //s=s.replace(/&gt;/g,'>');
    //s=s.replace(/&#xa;/g,'\n');
    s = s.replace(/&lt;br&gt;/g, '<br>');
    return s;
}
/*
公式比较
V6.1 -去除旧的公式比较，仅留JavaScript比较法
*/
function wfFormulaCompare(objFM, ConditionFormula) {
	ConditionFormula=ConditionFormula.replace(/\s/g,"");
    try {
        if ($.trim(ConditionFormula) == "") {
            return false
        }
		if(ConditionFormula.indexOf("{")>-1&&ConditionFormula.indexOf("}")>-1){
			var arrItem = (ConditionFormula.match(/\(\{.*?}\)|\{.*?}/ig));
			$.each(arrItem,
			function(i, item) {
				var tmp = item,v="",bC=tmp.match(/\(\{.*?}\)/ig);
				if(bC){
					item = item.substr(2).slice(0, -2);
				}else{
					item = item.substr(1).slice(0, -1);
				}
				if(mini.getbyName(item)){
					v=mini.getbyName(item).getFormValue()?mini.getbyName(item).getFormValue():mini.getbyName(item).getValue();
				}else{
					var leftObj = objFM[item];//公式中的域对象
					var _tag = (typeof leftObj.length != "undefined")?leftObj[0].tagName.toLowerCase():leftObj.tagName.toLowerCase();
					var _type = (typeof leftObj.length != "undefined")?leftObj[0].type:leftObj.type;
					if (_tag == "input") {
						if (typeof leftObj.length == "undefined") {
							v = leftObj.value
						} else {
							var varr=[];
							$.each($('input[name="' + item + '"][type="' + _type + '"]:checked', objFM),
							function(i, item) {
								varr.push(item.value.toString());
							});
							v=varr.join(',');
						}
					} else if (_tag == "textarea" || _tag == "select") {
						v = leftObj.value
					} else {
						return false
					}
				}
				if (bC) {
					v=""?0:v;
					v=parseFloat(v);
				}else{
					v="'"+v+"'";
				}
				ConditionFormula = ConditionFormula.replace(tmp, v);
				log("公式: ",ConditionFormula);
			});
			return new Function("return " + ConditionFormula)();
		}else{
			alert(WF_CONST_LANG.COMPARE_METHOD_ALERT);
		}
    } catch(e) {
        alert(WF_CONST_LANG.FORMULA_ERROR);
        return false
    }
}
/*--------------------------------------Common--------------------------------------*/
function wfSubDocEndSave(bGo2Next, bWFAgreeMark) {
    /*添加流转信息*/
    var tmpNode = $.parseXML("<" + gCurNode[0].nodeName + "/>").documentElement,
    strIdea = "",
    bCloneNode = true;
    tmpNode.setAttribute("tache", getNodeValue(gCurNode[0], "WFNodeName"));

    tmpNode.setAttribute("user", gLoginUser);
    tmpNode.setAttribute("userId", gUserId);
    tmpNode.setAttribute("time", gServerTime);
    if (bGo2Next) {
		log("是否为多人审批过程: ",bGo2Next?"YES":"NO");
        if (gForm.wfTacheName.value == WF_CONST_LANG.WORKFLOW_END) { //流程结束
            gAction = gLoginUser + WF_CONST_LANG.USE_ACTION
        } else {
            gAction = gLoginUser + WF_CONST_LANG.ACTION_TO + gForm.curUser.value + WF_CONST_LANG.SYMBOL_END
        }
    }
    tmpNode.setAttribute("action", gAction);
    /*处理完毕*/
    tmpNode.setAttribute("mark", bWFAgreeMark);
	log("所有意见ID标识:"+gIdeaID);
    if (gIdeaID.length > 0) {
        $.each(gIdeaID,
        function(i, id) {
            var cloneNode = tmpNode;
            cloneNode.setAttribute("id", id);
            $.each($('[name=\"' + id + '\"]', gForm),
            function(i, item) {
                strIdea = item.value
            });
            cloneNode.setAttribute("idea", EncodeHtml(strIdea));
            cloneNode.setAttribute("status", "拒绝");
            if(gIsReject==0){
            	cloneNode.setAttribute("status", "同意");
            }
            
            $(gWFLogXML).find("Log").append(cloneNode);
            cloneNode = null;
            bCloneNode = false;
        })
    }
    if (bCloneNode) {
    	$(gWFLogXML).find("Log").append(tmpNode);
    }
    
	if(""!==XML2String(gWFLogXML)){
		gForm.wfFlowLogXml.value = XML2String(gWFLogXML)
	}
	log("当前处理人: ",gLoginUser);
    ClearRepeat("allUser", gUserId+"^"+gLoginUser);
    fnResumeDisabled();
    
    //页面提交后执行
    var _pe = gPageEvent["SaveAfter"];
    if (_pe.replace(/\s/, "") != "") {
        try {
			new Function(_pe)()
        } catch(e) {
			alert(WF_CONST_LANG.SAVE_AFTER + "< " + _pe + " > "+WF_CONST_LANG.PAGE_NO_INIT)
        }
    }
    
	log("----页面提交结束----");
	setTimeout(function(){
		var oWinDlg = mini.get('oWinDlg');
        if (typeof oWinDlg!="undefined") {
			oWinDlg.destroy()
		}
		oWinDlg = mini.get('oCheckUserDlg');
        if (typeof oWinDlg!="undefined") {
			oWinDlg.destroy()
		}
		mini.unmask(document.body);
	},1000);
	if(gWebDebug){
		//这里还不够完善
		gForm.wfCurNodeId.value=gForm.wfPreNodeId.value;
		gForm.curUser.value=gForm.wfPreUser.value;
	}else{
		mini.mask({
            el: document.body,
            cls: 'mini-mask-loading',
            html: WF_CONST_LANG.DATA_SUBMIT //数据提交中...
        });
		$("[name=isReject]").val(gIsReject);
		gForm.submit()
	}
}
//处理消息提醒内容
function wfMsgContent(content) {
    var strContent = $.trim(content);
    if (strContent != "") {
        var arrItem = (strContent.match(/\{.*?}/ig));
        $.each(arrItem,
        function(i, item) {
            tmp = item;
            item = item.substr(1).slice(0, -1);
            if (typeof gForm[item] != "undefined") {
                strContent = strContent.replace(tmp, gForm[item].value)
            } else {
                strContent = strContent.replace(tmp, "")
            }
        });
    }
    return strContent == "" ? "-*-": strContent;
}
//处理自定义公式
function wfFormula(arrItems, IsWeb) {
    var arrP = [];
    if (arrItems.length > 0) {
        var arrF = [],
        arrT = [];
        $.each(arrItems,
        function(i, item) {
            if (item.indexOf("^P") > 0) {
                arrP.push(item.split("^")[0]);
            } else if (item.indexOf("^F") > 0) {
                arrF.push(item.split("^")[0]);
            } else {
                arrP.push(item);
            }
        });
        if (arrF.length > 0) {
            var args = "&W=" + IsWeb + "&I=" + gInitiator + "&CU=" + gLoginUser + "&TDB=" + gCurDBName + "&ID=" + gCurDocID + "&IS=" + gIsNewDoc + "&F=" + arrF.join("|");
			$.ajax({
                url: encodeURI("/" + gFormulaDB + "/(agtWFFormula)?OpenAgent" + args),
                cache: false,
                async: false,
                dataType: "text",
                success: function(txt) {
                    txt = txt.replace(/\s/g, "");
                    if (txt != "") {
                        if (IsWeb == "") {
                            arrT = txt.split("^");
                        } else {
                            //看是否网页有此函数(等待扩展)
                            $.each(txt.split("^"),
                            function(i, item) {
                                try {
                                    _tmp = new Function("return ("+item+")")();
                                    if ($.type(_tmp) == "array") {
                                        arrT = arrT.concat(_tmp);
                                    } else {
                                        alert(WF_CONST_LANG.WEB_FORMULA_ERROR);
                                        return;
                                    }
                                } catch(e) {}
                            });
                        }
                    }
                }
            });
        }
        if (arrT.length > 0) {
            arrP = arrP.concat(arrT);
        }
    }
    return arrP;
}
//常用语句
function goAppendComLang(val) {
    if (val.replace(/\s/g, "") != "") {
        var tmp = document.getElementById("WFIdea");
        //if(tmp.value==""){tmp.value=val}else{tmp.value=tmp.value+"\n"+val}
        tmp.value = val
    }
}
//查看流程图
function goLookWorkFlow() {
    if (window != top) {
        top.goWorkFlow(gCurDBName, gCurDocID, false);
    } else {
        var linkType = "height=580,width=880,top=50,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
        var link = "/" + gWorkFlowDB + "/htmWFViewWindow?ReadForm&db=" + gCurDBName + "&id=" + gCurDocID + "&cu=" + encodeURI(gForm.CurUser.value) + "&status=" + gWFStatus + "&gWFTacheName=" + encodeURI(gForm.WFTacheName.value);
        window.open(link, "newwindow", linkType);
    }
}
//流程终止
function wfStop() {
    //确定要做相应操作吗？
    if (!gIsNewDoc) {
        if (confirm(WF_CONST_LANG.CONFIRM_OPERATION)) {
            //if(confirm("确定要做相应操作吗？")){
            $.ajax({
                url: encodeURI("/" + gCommonDB + "/agtWFStop?OpenAgent&DocID=" + gCurDocID + "&TDB=" + gCurDBName + "&User=" + gLoginUser),
                dataType: "text",
                success: function(txt) {
                    var intM = parseInt(txt, 10);
                    if (intM == 0) {
                        alert(WF_CONST_LANG.STOP_ERROR_0)  //alert("可能系统有异常，未成功终止，请联系管理员！")
                    } else if (intM == 1) {
                        alert(WF_CONST_LANG.STOP_ERROR_1);  //alert("已成功终止！");
                        window.location.reload()
                    } else if (intM == 2) {
                        alert(WF_CONST_LANG.STOP_ERROR_2)  //alert("文档此时正在被他人处理，不能被终止！")
                    } else if (intM == 3) {
                        alert(WF_CONST_LANG.STOP_ERROR_3)  //alert("流程已经被终止或结束，不能被取回！")
                    } else {
                        alert(WF_CONST_LANG.STOP_ERROR_4)  //alert("您不是文档的提交者，不能被终止！")
                    }
                }
            })
        }
    } else {
        alert(WF_CONST_LANG.STOP_ERROR)                    //您好，草稿状态时，此操作无效！
    }
}
//在工具条中增加按钮
function wfAddToolbar(id) {
    var arrBtns = [
	{
        name: WF_CONST_LANG.SEARCH,//查询
        ico: "icon-search",
        clickEvent: "wfOrgSearch()",
        align: "1",
        isHidden: "1",
		id:"wfS"
    },
	{
        name: WF_CONST_LANG.REFRESH,//刷新
        ico: "icon-reload",
        clickEvent: "wfOrgSearch(true)",
        align: "1",
        isHidden: "1",
		id:"wfRe"
    },
	{
        name: WF_CONST_LANG.CANCEL,
        ico: "icon-cancel",
        clickEvent: "mini.get(\'" + id + "\').destroy();gForm.wfStatus.value=gWfStatus;gForm.wfTacheName.value='';",
        align: "2",
        isHidden: "0"
    },
    {
        name: WF_CONST_LANG.OK,
        ico: "icon-ok",
        clickEvent: "wfDlgBtnSave()",
        align: "2",
        isHidden: "0"
    }];
	$("<input type='text' class='mini-textbox' id='searchValue' visible=false style='margin-right:5px;' />").appendTo("#SubmitDocActionBar");
    var btnHtml = "";
    for (var i = 0; i < arrBtns.length; i++) {
        btnHtml = "<a class='mini-button' id='"+(arrBtns[i].id?arrBtns[i].id:"")+"' style='"+(arrBtns[i].align=="1"?"":"float:right")+"' plain=true visible="+(arrBtns[i].isHidden=="1"?false:true)+" iconCls='" + arrBtns[i].ico + "'>" + arrBtns[i].name + "</a>";
        $(btnHtml).appendTo("#SubmitDocActionBar").attr('onClick', arrBtns[i].clickEvent);
    }
    
    mini.parse();
    wfAddTacheName();
}
//组织搜索
function wfOrgSearch(r) {
	var tree=mini.get("orgTree");
	var key = mini.get("searchValue");
	if (key.getValue() == "" || r) {
		tree.clearFilter();
		tree.collapseAll();
		key.setValue("");
	} else {
		key = key.getValue().toLowerCase();
		tree.filter(function (node) {
			var text = node.text? node.text.toLowerCase() : "";
			if (text.indexOf(key) != -1) {
				return true;
			}
		});
		tree.expandAll();
	}
}
//选择组织机构切换面板时控制查询按钮功能
function changTab(e){
	if(!(mini.get("wfS")&&mini.get("wfRe"))){
		return;
	}
	if(e.tab.name=="OrgTree"){
		var tree=mini.get("orgTree");
		if(typeof mini.get("orgTree")!="undefined"&&tree.getData().length==0){
			loadOrgTree();
		}
		mini.get("wfS").set({visible:true});
		mini.get("wfRe").set({visible:true});
		mini.get("searchValue").set({visible:true});
		return;
	}
	mini.get("wfS").set({visible:false});
	mini.get("wfRe").set({visible:false});
	mini.get("searchValue").set({visible:false});
}
//将XML对象转换为字符串
function XML2String(xmlDom){
	try{
		return (typeof XMLSerializer!=="undefined") ? (new window.XMLSerializer()).serializeToString(xmlDom) : xmlDom.xml;
	}catch(e){
		return ""
	}
}
//加载组织树
function loadOrgTree() {
    var orgTree = mini.get("orgTree");
	var oWinDlg = mini.get('oWinDlg');
	mini.mask({
		el: document.body,
		cls: 'mini-mask-loading',
		html: WF_CONST_LANG.LIST_LOADING //组织数据加载中...
	});
    $.ajax({
        url: gDir+"/admin/findOrgTree.action",
        cache: false,
        success: function(MenuText) {
        	if(MenuText!=""){
        		orgTree.loadList(mini.decode(MenuText), "id", "pid");
				mini.unmask(document.body);
			}
        }
    });
}
//组织树元素选择
function treeNodeClick(e) {
    if (!e.node.type || e.node.type != "dept") {
    	e.node.name=e.node.text;
        AddValue(e.node, "selectList")
    }
}
//加载已选元素
function listNodeClick(e) {
    AddValue(e.sender.getSelected(), "selectList")
}
//日志
function log() {
	if(typeof gWebDebug == 'undefined'){
		gWebDebug=0;
	}
    if (gWebDebug==0) {
        //return;
    }
    var msg = '[ht.workflow] ' + Array.prototype.join.call(arguments,'');
    if (window.console) {
		if(window.console.debug){
			window.console.debug(msg)
		}else{
			window.console.log(msg)
		}
    }
    else if (window.opera && window.opera.postError) {
        window.opera.postError(msg)
    }
}

//表单只读
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
	mini.unmask(document.body);
}

//关闭事件
function toClose() {
	mini.confirm("确定要关闭吗？", "提示", function(action) {
		if (action == "ok") {
			if(!gIsRead && $("[name=curUser]").val().indexOf(";")>-1){
				var path=gDir+"/admin/docReadUserClear.action";
				$.post(path,{
					docId:$("[name=uuid]").val()
				},function(data){
					if(data=="ok"){
						self.close();
					}else{
						mini.alert("释放占用失败！");
					}
				})
			}else{
				self.close();
			}
		}
	})

}

//自动调整页面宽度
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

function initPageEle() {
	changeDivHeight();
	if(gIsFile=="1"){
		dynamicFileList();
	}
	if(gIsShowSign==1){
		$("#SignMode").hide();
	}
	readyUserPage();
	loadXml();
	loadingForm();
}

function readyUserPage(){
	if($("[name=curReadyUser]").val()!="" && gUserId!=$("[name=curReadyUser]").val()){
		var users=$("[name=curUser]").val().split(";");
		var userIds=$("[name=alreadyUser]").val().split(";");
		$.each(userIds,function(i){
			if(this==$("[name=curReadyUser]").val()){
				$("[name=curReadyUser]").val(users[i]);
			}
		})
		$("#readUserMsg").html("文档处于会签状态，当前占用人："+$("[name=curReadyUser]").val());
		$("#readUserMsg").show();
		gIsEditDoc=true;
		gIsRead=true;
	}
}
