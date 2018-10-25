document.write('<link href="../css/ht/upload.css" rel="stylesheet" type="text/css" />');
var gWFProcessXML = null,
	gCurNode = null,
	gArrTacheName = null,
	gWFLogXML = null,
	gJsonField = null,
	gUserAction = "",
	gIdeaID = [],
	gArrLogUser = [],
	gAction = "处理完毕！",
	gPageEvent = {
		"OpenBefore": "",
		"OpenAfter": "",
		"SaveBefore": "",
		"SaveAfter": ""
	};
$(function() {
	if (gWFID == "") {
		alert("没有可用的流程或没有被激活的流程！\n\n请您联系系统管理员！")
	} else {
		var d = $("[name='curUser']").val().replace(/\s/g, ""),
			arrTmpCurUser = d.indexOf(",") > -1 ? d.split(",") : d.split(";");
		/*
		if (!gIsEditDoc) {
			if (gWFStatus < 2) {
				var e = gForm.Path_Info.value.split("?"),
					pathurl = e[0] + "?EditDocument" + (e[1].indexOf("&") > -1 ? e[1].substr(e[1].indexOf("&")) : "") + "&_=" + (new Date()).getTime();
				if ($.inArray(gUserCName, arrTmpCurUser) > -1) {
					window.location = pathurl;
					return
				}
			} else {}
		}
		*/
			
		//var f = "/" + gCurDB + "/vwWFXML/" + gCurDocID + "?OpenDocument";
		//if (gIsNewDoc) {
			f = "../admin/getWorkFlowXml.action?wfId="+gWFID;
		//}
		$.ajax({
			url: f,
			cache: false,
			dataType: "text",
			success: function(b) {
				var c = $.parseXML(b);
				//var c = $.parseXML(b.replace(/[\n\r]/g, ""));
				$.each(c.documentElement.childNodes, function(i, a) {
					if (a.nodeName == "Process") {
						gWFProcessXML = a;
						$("[name='wfProcessXml']").val(a.outerHTML);
					} else if (a.nodeName == "Log") {
						gWFLogXML = a;
					}else if(a.nodeName == "GraphModel"){
						$("[name='wfGraphXml']").val(a.outerHTML);
					}
				});
				initOnLoad(arrTmpCurUser);
			}
		});
	}
});

function initOnLoad(r) {
	if (typeof(beforeLoad) != "undefined") {
		beforeLoad();
	}
	$.each($('textarea[name^="ID_"]', gForm), function(i, a) {
		$("<div></div>").insertBefore(a).attr("id", $(a).attr("name"));
		if (!gIsEditDoc) {
			$(a).remove();
		}
	});
	if (gIsEditDoc) {
		var s = gIsNewDoc ? (gWFProcessXML.getAttribute("OriginNode")) : gForm.WFCurNodeID.value;
		gCurNode = $(s, gWFProcessXML);
		if (gIsNewDoc) {
			gWFLogXML.setAttribute("OriginRouter", gWFProcessXML.getAttribute("OriginRouter"));
			$("[name='wfCurNodeId']").val(s);
			console.log(s);
			$("[name='wfTacheName']").val(getNodeValue(gCurNode[0], "WFNodeName"));
			console.log(getNodeValue(gCurNode[0], "WFNodeName"));
		}
		if (gWFStatus < 2) {
			$.each($($("[name='wfCurNodeId']").val() + ">WFOpenBefore", gWFProcessXML), function(i, a) {
				gPageEvent["OpenBefore"] = a.getAttribute("value")
			});
			$.each($($("[name='wfCurNodeId']").val() + ">WFOpenAfter", gWFProcessXML), function(i, a) {
				gPageEvent["OpenAfter"] = a.getAttribute("value")
			});
			$.each($($("[name='wfCurNodeId']").val() + ">WFSaveBefore", gWFProcessXML), function(i, a) {
				gPageEvent["SaveBefore"] = a.getAttribute("value")
			});
			$.each($($("[name='wfCurNodeId']").val() + ">WFSaveAfter", gWFProcessXML), function(i, a) {
				gPageEvent["SaveAfter"] = a.getAttribute("value")
			});
			var t = gPageEvent["OpenBefore"];
			if (t.replace(/\s/, "") != "") {
				try {
					eval(t)
				} catch (e) {
					alert("页面打开前执行的函数 < " + t + " > 可能未在页面中初始化！")
				}
			}
			$.each($($("[name='wfCurNodeId']").val() + ">WFBtnAssign>tr", gWFProcessXML), function(i, d) {
				var e = {};
				$.each($("td", d), function(a, b) {
					if (a > 0) {
						var c = b.getAttribute("value");
						switch (a) {
						case 1:
							e.name = c;
							e.title = c;
							break;
						case 2:
							e.ico = c;
							break;
						case 3:
							e.clickEvent = c;
							break;
						case 4:
							c = (c == "居右" || c == "居左" ? c == "居右" ? "2" : "1" : c);
							e.align = c;
							break;
						default:
							e.isHidden = "0"
						}
					}
				});
				gArrBtns.push(e)
			});
			//loadAttachGrid(false);
			$.each($($("[name='wfCurNodeId']").val() + ">WFFieldStatus", gWFProcessXML), function(i, h) {
				var j = h.getAttribute("value");
				if (j != "") {
					gJsonField = $.parseJSON(j);
					var k = function(a) {
							for (o in a) {
								return o
							}
						};
					var l = function(c, d, f) {
							try {
								if (d == "select") {
									$(c).css("background-color", "#fc9")
								} else if (f == "radio" || f == "checkbox") {
									if (c.parentNode && c.parentNode.tagName.toLowerCase() == "label") {
										$(c.parentNode).css("color", "#f00")
									}
								} else if (d == "div") {
									var g = document.getElementById("WFIdea");
									if (g) {
										$(g).css("border", gCssBorder)
									}
									if ($.grep($(gForm.WFCurNodeID.value, gWFLogXML), function(a) {
										var b = a.getAttribute("idea") ? $.trim(a.getAttribute("idea")) : "";
										if (b == "") {
											return true
										}
									}).length > 0) {
										$(c).css("height", "60px")
									}
									$(c).css("border", gCssBorder)
								} else {
									$(c).css("border", gCssBorder)
								}
							} catch (e) {}
						};
					var m = function(a, b, f) {
							try {
								var c = a.tagName.toLowerCase(),
									strType = a.type ? a.type.toLowerCase() : "div";
								var d = ($(a).attr("class") != undefined) && $(a).attr("class").split("mini-").length > 1;
								switch (b) {
								case "e":
									if (f.indexOf("ID_") > -1) {
										gIdeaID.push(f)
									}
									break;
								case "r":
									if (d) {
										if (c == "textarea" && f.indexOf("ID_") > -1) {
											$('[name=\"' + f + '\"]', gForm).remove()
										} else {
											$(a).attr("enabled", false)
										}
										break
									}
									switch (c) {
									case "textarea":
										if (f.indexOf("ID_") > -1) {
											$('[name=\"' + f + '\"]', gForm).remove()
										} else {
											a.setAttribute("disabled", true)
										}
										break;
									case "input":
										if (strType == "text") {
											$(a).attr({
												"disabled": true,
												"onclick": null,
												"onfocus": null,
												"onblur": null,
												style: "backgroundImage:none;borderWidth:0"
											})
										} else {
											a.setAttribute("disabled", true)
										}
										break;
									case "select":
										a.setAttribute("disabled", true);
									default:
									}
									break;
								case "h":
									if (f.indexOf("js-") > -1) {
										$(a).css("display", "none")
									} else {
										if (c != "textarea") {
											if (strType == "radio" || strType == "checkbox") {
												if (a.parentNode && a.parentNode.tagName.toLowerCase() == "label") {
													$(a.parentNode).css("visibility", "hidden")
												}
											} else {
												$(a).css("visibility", "hidden")
											}
										} else if (c == "textarea") {
											if (f.indexOf("ID_") > -1) {
												$('[name=\"' + f + '\"]', gForm).empty()
											}
										}
									}
									break;
								case "s":
									if (gJsonField[f].s != "") {
										if (_arrSeeUser == null) {
											_arrSeeUser = wfFormula(gJsonField[f].s.split("|"), "")
										}
										if ($.inArray(gUserCName, _arrSeeUser) < 0) {
											if (f.indexOf("js-") > -1) {
												$(a).css("display", "none")
											} else {
												$(a).css("visibility", "hidden")
											}
										} else {
											if (f.indexOf("js-") > -1) {
												$(a).css("display", "")
											}
										}
									}
									break;
								case "w":
									if (gJsonField[f].w != "") {
										if (f.indexOf("ID_") > -1) {
											gIdeaID.push(f)
										}
										if (d && (c == "input" || c == "textarea")) {
											$(a).attr("required", true);
											break
										}
										l(a, c, strType)
									}
									break;
								case "m":
									if (gJsonField[f].m != "") {
										var g = gJsonField[f].m.split("$$");
										if (_arrSeeUser == null) {
											_arrSeeUser = wfFormula(g[0].split("|"), "")
										}
										if ($.inArray(gUserCName, _arrSeeUser) < 0) {
											if (f.indexOf("js-") > -1) {
												$(a).css("display", "none")
											} else {
												$(a).css("visibility", "hidden")
											}
										} else {
											if (f.indexOf("js-") > -1) {
												$(a).css("display", "")
											}
											if (f.indexOf("ID_") > -1) {
												gIdeaID.push(f)
											}
											if (d && (c == "input" || c == "textarea")) {
												$(a).attr("required", true);
												break
											}
											l(a, c, strType)
										}
									}
									break;
								default:
								}
							} catch (e) {
								alert(e.description)
							}
						};
					var n = [];
					for (f in gJsonField) {
						var p = k(gJsonField[f]),
							type = "name",
							_arrSeeUser = null;
						if (f.indexOf("js-") > -1) {
							var q = $("." + f, gForm)
						} else {
							var q = $('[' + type + '=\"' + f + '\"]', gForm)
						}
						if (q.length == 0) {
							n.push(f)
						}
						$.each(q, function(i, a) {
							m(a, p, f)
						})
					}
					if (n.length > 0) {
						alert("检测不到以下域的标识：\n" + n.join("\n"))
					}
				}
			});
			var t = gPageEvent["OpenAfter"];
			if (t.replace(/\s/, "") != "") {
				try {
					eval(t)
				} catch (e) {
					alert("页面打开后执行的函数 < " + t + " > 可能未在页面中初始化！")
				}
			}
		}
	} else {
		loadAttachGrid(true);
		var u = function(k) {
				$.each($(k + ">WFFieldStatus", gWFProcessXML), function(i, c) {
					var d = c.getAttribute("value");
					if (d != "") {
						gJsonField = $.parseJSON(d);
						var g = function(a) {
								for (o in a) {
									return o
								}
							};
						var h = function(a, b, f) {
								try {
									switch (b) {
									case "s":
										if (gJsonField[f].s != "") {
											if (_arrSeeUser == null) {
												_arrSeeUser = wfFormula(gJsonField[f].s.split("|"), "")
											}
											if ($.inArray(gUserCName, _arrSeeUser) < 0) {
												$(a).css("display", "none")
											} else {
												$(a).css("display", "")
											}
										}
										break;
									default:
									}
								} catch (e) {}
							};
						for (f in gJsonField) {
							if (f.indexOf("js-") > -1) {
								var j = g(gJsonField[f]),
									_arrSeeUser = null;
								$.each($("." + f, gForm), function(i, a) {
									h(a, j, f)
								})
							} else {
								if (gJsonField[f]) delete gJsonField[f]
							}
						}
					}
				})
			};
		if (gWFStatus > 1) {
			var v = gForm.WFPreNodeID.value;
			if ($.trim(v) != "") {
				u(v.substr(v.lastIndexOf("N")))
			}
		} else {
			var w = [];
			$.each($('[user="' + gUserCName + '"]', gWFLogXML), function(i, a) {
				var b = a.nodeName;
				if ($.inArray(b, w) < 0) {
					w.push(b);
					u(b)
				}
			})
		}
	}
	if (gCloseBtn.length > 0) {
		gArrBtns = gArrBtns.concat(gCloseBtn)
	};
	var x = "<a class='mini-button'></a>";
	$("#btnCont").empty();
	gArrBtns = $.grep(gArrBtns, function(a) {
		return a.isHidden != "1"
	});
	$.each(gArrBtns, function(i, e) {
		$(e.align == "1" ? $(x).appendTo("#btnContL") : $(x).appendTo("#btnContR")).attr({
			text: e.name,
			title: e.title,
			id: (e.id ? e.id : ""),
			iconCls: e.ico,
			plain: true,
			onClick: e.clickEvent,
			style: "font-size:12px;font-family:'Microsoft YaHei';"
		})
	});
	mini.parse();
	if (!gIsNewDoc) {
		if (gWFLogXML.childNodes) {
			var y = 1,
				DataPrefix = DataSuffix = strId = WFIdeaPrefix = WFIdeaSuffix = "";
			$.each(gWFLogXML.childNodes, function(i, a) {
				if (typeof(unionIdea) == "undefined" || !unionIdea) {
					strId = a.getAttribute("id") ? $.trim(a.getAttribute("id")) : "";
					if (strId.indexOf("ID_") > -1) {
						var b = a.getAttribute("time"),
							_tdID = "td" + strId + y,
							_idea = DecodeHtml(a.getAttribute("idea")),
							_mark = a.getAttribute("mark");
						WFIdeaPrefix = '<table cellspacing=1 cellpadding=1 id="showWFIdea"><tr><td class="tdIdea" id="' + _tdID + '" ' + (_mark != 'undefined' && _mark == '1' ? 'style="color:red;font-weight:bold"' : '') + '>&nbsp;</td></tr>';
						WFIdeaSuffix = '<tr><td class="tdIdeaUser" title="具体时间：' + b + '">' + a.getAttribute("user").replace(/[0-9]/g, "") + '&nbsp;&nbsp;<span>' + b + '</span></td></tr></table>';
						$("#" + strId).append("<div>" + WFIdeaPrefix + WFIdeaSuffix + "</div>");
						$("td#" + _tdID).empty().append(_idea)
					}
				} else {
					if (typeof(document.getElementById("ideaArea")) == "undefined") {
						alert("意见显示区域未定义。")
					} else {
						strId = "ID_ideaArea";
						if (a.getAttribute("idea") != undefined) {
							var b = a.getAttribute("time"),
								_tache = a.getAttribute("tache"),
								_tdID = "td" + strId + y,
								_idea = DecodeHtml(a.getAttribute("idea")) == "" ? "阅。" : DecodeHtml(a.getAttribute("idea")),
								_mark = a.getAttribute("mark");
							WFIdeaPrefix = '<table cellspacing=1 cellpadding=1 id="showWFIdea" style="width:100%"><tr><td class="techename" style="width:150px;text-align:center">' + _tache + '</td><td class="tdIdea" id="' + _tdID + '" ' + (_mark != 'undefined' && _mark == '1' ? 'style="color:red;font-weight:bold;"' : '') + '>&nbsp;</td>';
							WFIdeaSuffix = '<td class="tdIdeaUser" style="width:250px;text-align:left" title="具体时间：' + b + '"><span style="width:100px;float:left;text-align:center">' + a.getAttribute("user").replace(/[0-9]/g, "") + '</span><span style="float:left">' + b + '</span></td></tr></table>';
							$("#" + strId).append("<div onmousemove='this.style.backgroundColor=\"#e1dfdf\"' onmouseout='this.style.backgroundColor=\"#fff\"'>" + WFIdeaPrefix + WFIdeaSuffix + "</div>");
							$("td#" + _tdID).empty().append(_idea)
						}
					}
				}
				y++;
			});
			if (gWFStatus < 2) {} else {
				var z = function() {
						$.ajax({
							url: encodeURI("/" + gWorkFlowDB + "/pgReadField?OpenPage&N=" + gWFModule + "-" + gFormType),
							dataType: "text",
							cache: false,
							success: function(e) {
								if (e != "") {
									var f = eval("[" + e + "]");
									$.each(f, function(i, b) {
										var c = b.split("|")[1],
											type = "name";
										if (c.indexOf("ID_") > -1) {
											type = "id";
											var d = $('[' + type + '=\"' + c + '\"]', gForm);
											if (d.length == 0) {
												type = "name"
											}
										}
										var d = $('[' + type + '=\"' + c + '\"]', gForm);
										if (d.length == 0) {
											_tmpField.push(c)
										}
										$.each(d, function(i, a) {
											a.setAttribute("disabled", true)
										})
									});
									if (_tmpField.length > 0) {
										alert("检测不到以下域的标识：\n" + _tmpField.join("\n"))
									}
								}
							}
						})
					};
				if (gIsEditDoc) {
					z()
				}
			}
		}
	}
	var A = function() {
			try {
				return gIsLoadDateJS
			} catch (e) {
				return false
			}
		};
	if (gIsEditDoc && A()) {
		var B = document.getElementsByTagName('HEAD').item(0);
		var C = document.createElement("script");
		C.type = "text/javascript";
		C.src = "/ht/My97DatePicker/WdatePicker.js";
		B.appendChild(C)
	}
	if (typeof(afterLoad) != "undefined") {
		afterLoad()
	}
}
function getNodeValue(b, c) {
	var d = "";
	$.each($(c, b), function(i, a) {
		d = a.getAttribute("value").replace(/@line@/g, "")
	});
	return $.trim(d)
}
function fnResumeDisabled() {
	$("input[disabled],textarea[disabled],select[disabled]").prop("disabled", false)
}
function wfSubDocStart() {
	var n = false;
	var p = "";
	/*
	try {
		if (!gIsNewDoc) {
			$.ajax({
				url: '/' + gCommonDB + "/(agtGetSubTime)?openagent&id=" + gCurDocID + "&db=" + gCurDBName,
				cache: false,
				dataType: 'text',
				async: false,
				success: function(a) {
					if ($.trim(a) != "" && (mini.formatDate(mini.parseDate($.trim(a).split("^")[0]), "yyyy-MM-dd HH:mm:ss") != mini.formatDate(mini.parseDate(gSubTime), "yyyy-MM-dd HH:mm:ss"))) {
						n = true;
						p = $.trim(a).split("^")[1]
					}
				}
			})
		}
	} catch (e) {
		n = false
	}
	if (n) {
		alert("在您提交前 [" + p + "] 已经对文件进行了提交操作，请您刷新页面重新提交.");
		return
	}
	*/
	var q = $($("[name='wfCurNodeId']").val(), gWFProcessXML);
	var r = getNodeValue(q[0], "WFWithOrg");
	/*
	var t = gPageEvent["SaveBefore"];
	if (t.replace(/\s/, "") != "") {
		try {
			var u = eval(t);
			if (typeof u != "undefined") {
				if (!u) {
					return
				}
			}
		} catch (e) {
			alert("页面提交前执行的函数 < " + t + " > 可能未在页面中初始化！")
		}
	}
	*/
	var v = function(o, s) {
			return typeof(o[s]) != "undefined"
		};
	var w = function(f, a, b) {
			if (a) {
				if (gJsonField[f].w != "") {
					alert(gJsonField[f].w)
				}
			} else {
				var c = gJsonField[f].m.split("^");
				if ($.inArray(gUserCName), c[0].split("|") > -1) {
					if (c[1] != "") {
						alert(c[1])
					}
				} else {
					return false
				}
			}
			return true
		};
	var x = false,
		bm = false,
		_tmpField = [];
	/*
	for (f in gJsonField) {
		if (f.indexOf("js-") < 0) {
			be = v(gJsonField[f], "e"), x = v(gJsonField[f], "w"), bm = v(gJsonField[f], "m"), bObjHTML = true;
			var y = (mini.getbyName(f) != "" ? mini.getbyName(f) : false);
			if (be || x || bm) {
				if (bObjHTML) {
					var z = $('[name=\"' + f + '\"]', gForm);
					if (z.length > 0) {
						z = z[0]
					} else {
						_tmpField.push(f);
						continue
					}
				}
				if (y) {
					y.validate();
					if (!y.isValid()) {
						y.focus();
						w(f, x, bm);
						return
					}
				} else {
					var A = z.type ? z.type.toLowerCase() : "div";
					if (A == "radio" || A == "checkbox") {
						var B = $('input[name=\"' + f + '\"][type=' + A + ']:checked', gForm);
						if (B.length < 1) {
							if (w(f, x, bm)) {
								z.focus();
								return
							}
						}
					} else {
						if ($.trim(z.value) == "") {
							if ((!be) && w(f, x, bm)) {
								z.focus();
								return
							}
						}
					}
				}
			}
		}
	}
	if (_tmpField.length > 0) {
		alert("检测不到以下域的标识：\n" + _tmpField.join("\n"))
	}
	*/
	gArrTacheName = [];
	if (!gIsNewDoc) {
		if (q.length == 1) {
			var C = "",
				strSequenceApprove = "",
				intApproveNum = 0,
				tmpGetValue = "";
			strSequenceApprove = getNodeValue(q[0], "WFSequenceApprove");
			C = getNodeValue(q[0], "WFApproveStyle");
			tmpGetValue = getNodeValue(q[0], "WFApproveNum");
			if (tmpGetValue != "") {
				intApproveNum = parseInt(tmpGetValue, 10)
			}
			if (C == "多人") {
				var D = [],
					strWF = gForm.WFFinishApproval.value.replace(/\s/g, ""),
					bGo2Next = true;
				if (strWF != "") {
					D = strWF.split(";")
				};
				if (!$.grep(D, function(a) {
					return $.trim(a) == gUserCName
				}).length == 0) {
					D.push(gUserCName)
				}
				gForm.WFFinishApproval.value = D.join(";");
				if (strSequenceApprove == "是") {
					if ($.trim(gForm.WFWaitApproval.value) == "") {
						gForm.WFWaitApproval.value = "";
						bGo2Next = false
					} else {
						var E = gForm.WFWaitApproval.value.replace(/\s/g, "").split(";");
						gForm.CurUser.value = E[0];
						gForm.WFWaitApproval.value = E.slice(1).join(";")
					}
				} else {
					var F = gForm.CurUser.value.replace(/\s/g, "");
					if (F == "") {
						alert("文档不能进行流转！\n\n当前审批人不应为空，请联系管理员！");
						return
					}
					var G = F.split(";");
					var H = $.grep(G, function(a) {
						return $.trim(a) != gUserCName
					});
					if (intApproveNum > 0) {
						if (intApproveNum == D.length) {
							bGo2Next = false
						} else {
							if (H.length > 0) {
								gForm.CurUser.value = H.join(";")
							} else {
								bGo2Next = false
							}
						}
					} else {
						if (G.length == 1) {
							bGo2Next = false
						} else {
							gForm.CurUser.value = H.join(";")
						}
					}
				}
				if (bGo2Next) {
					var I = 0;
					$('[source="' + gForm.WFCurNodeID.value + '"]', gWFProcessXML).each(function(i, a) {
						if (getNodeValue(a, "WFAgreeMark") == "是") {
							I = 1
						}
					});
					if (confirm("您确定提交吗？")) {
						wfSubDocEndSave(false, I)
					} else {
						gForm.WFStatus.value = gWFStatus
					}
					return
				}
			}
		}
	}
	$("[name='wfStatus']").val(1);
	var J = $('[source="' + $("[name='wfCurNodeId']").val() + '"]', gWFProcessXML);
	if (J.length == 1) {
		var K = getNodeValue(J[0], "WFRelationType");
		if (K != "") {
			var L = J[0].getAttribute("target"),
				strTacheName = "流程结束";
			ClearRepeat("wfRouterId", J[0].nodeName);
			if (K == "直连") {
				if (!confirm("您确定提交吗？")) {
					$("[name='wfStatus']").val(gWFStatus) ;
					return;
				};
				if (L.indexOf("E") > -1) {
					$("[name='wfStatus']").val(2) ;
					wfSubDocEnd("", [], strTacheName);
					return
				}
				var M = $(L, gWFProcessXML);
				if (M.length == 1) {
					if (M[0].getAttribute("nType") != "Judge") {
						strTacheName = getNodeValue(M[0], "WFNodeName");
						tmpValue = getNodeValue(M[0], "WFActivityOwner");
						C = getNodeValue(M[0], "WFApproveStyle");
						var N = getNodeValue(M[0], "WFFormula");
						if (tmpValue != "") {
							gArrLogUser = wfFormula(tmpValue.split("|"), N);
							if (gArrLogUser.length == 1) {
								wfSubDocEnd(L, [gArrLogUser[0]], strTacheName)
							} else {
								if (C != "多人") {
									alert("在路由关系为直连情景下，\n\n如果下一环节处理人多于1人，\n\n下一环节点中\3C审批方式\3E必须设置为\3C多人\3E才能进行提交！\n\n请您联系管理员解决此问题。")
								} else {
									wfSubDocEnd(L, gArrLogUser, strTacheName);
									return
								}
							}
						} else {
							alert("没有找到下一环节的处理人，请您联系管理员！")
						}
					} else {
						var O = getNodeValue(M[0], "WFAlert");
						if (O != "") {
							var P = dijit.byId('otWinDlg');
							if (P == null) {
								P = new dijit.Dialog({
									title: "决策",
									id: "otWinDlg",
									onDownloadEnd: function() {
										var a = [{
											name: "是",
											clickEvent: dojo.query("WFYesFn", M[0])[0].getAttribute("value") + ";wfJudge2Next('" + L + "')",
											align: "2",
											isHidden: "0"
										}, {
											name: "否",
											clickEvent: dojo.query("WFNoFn", M[0])[0].getAttribute("value") + ";wfJudge2Next('" + L + "')",
											align: "2",
											isHidden: "0"
										}];
										DrawToolsBar("tSubmitDocActionBar", a, {
											border: 0,
											bgImage: 0
										});
										dojo.byId("tipContent").innerHTML = O
									},
									onHide: function() {
										try {
											gForm.WFStatus.value = gWFStatus
										} catch (e) {};
										this.destroyDescendants()
									}
								});
								dojo.body().appendChild(P.domNode)
							}
							P.set("href", "/" + gCommonDB + "/htmWFJudgeDlg?ReadForm");
							P.show()
						} else {
							wfJudge2Next(L)
						}
					}
				} else {
					alert("下一环节点不存在，请联系管理员！");
					return
				}
			} else if (K == "唯一选择") {
				if (L.indexOf("E") > -1) {
					gForm.WFStatus.value = 2;
					wfSubDocEnd("", [], strTacheName);
					return
				}
				strTacheName = getNodeValue(J[0], "WFNodeDetail");
				strTacheNameSelect = getNodeValue(J[0], "WFTacheNameSelect");
				gArrTacheName.push([strTacheName, L + "^" + J[0].nodeName, strTacheNameSelect]);
				var Q = mini.get('oWinDlg');
				if (!Q) {
					Q = new mini.Window();
					Q.set({
						id: "oWinDlg",
						title: '选择处理人',
						url: '',
						allowDrag: false,
						allowResize: false,
						showModal: true,
						enableDragProxy: false,
						showFooter: true,
						showCloseButton: false,
						footerStyle: "padding:6px 12px;",
						width: 510,
						height: 460
					});
					Q.show();
					mini.mask({
						el: Q.getEl(),
						cls: 'mini-mask-loading',
						html: '列表加载中...'
					});
					$.ajax({
						url: '../workflow/htmWFTechSel.html?s=select',
						async: true,
						dataType: 'text',
						success: function(e) {
							var a = e;
							Q.setBody(a);
							Q.setFooter("<div id='SubmitDocActionBar' style='text-align:right'></div>");
							setTimeout(function() {
								wfAddToolbar("oWinDlg")
							}, 10);
							loadOrgTree();
							mini.unmask(Q.getEl())
						}
					})
				} else {
					Q.show()
				}
			} else {
				alert("路由定义错误！\n\n请联系管理员！");
				return
			}
		}
	} else {
		var R = false;
		$.each(J, function(d, f) {
			var g = getNodeValue(f, "WFRelationType"),
				tmpValue = "",
				bReturn = true;
			if (g == "条件直连") {
				tmpValue = getNodeValue(f, "WFCondition");
				if (tmpValue != "") {
					if (wfFormulaCompare(gForm, tmpValue)) {
						ClearRepeat("wfRouterId", f.nodeName);
						var h = f.getAttribute("target");
						if (h.indexOf("E") > -1) {
							gForm.WFStatus.value = 2;
							wfSubDocEnd("", [], "流程结束")
						} else {
							var j = $(h, gWFProcessXML);
							if (j.length == 1) {
								strTacheName = getNodeValue(j[0], "WFNodeName");
								tmpValue = getNodeValue(j[0], "WFActivityOwner");
								var k = getNodeValue(j[0], "WFFormula");
								if (tmpValue != "") {
									gArrLogUser = [];
									$.each($(h, gWFLogXML), function(i, a) {
										var b = a.getAttribute("user");
										if (b) {
											if ($.inArray(b, gArrLogUser) < 0) {
												gArrLogUser.push(b + "^P")
											}
										}
									});
									if (gArrLogUser.length == 0) {
										gArrLogUser = $.map(wfFormula(tmpValue.split("|"), k), function(a) {
											return a + "^P"
										})
									}
									var l = [];
									$.each(gArrLogUser, function(i, a) {
										if ($.inArray(a, l) < 0) {
											l.push(a)
										}
									});
									gArrLogUser = l;
									if (gArrLogUser.length == 1) {
										wfSubDocEnd(h, gArrLogUser, strTacheName)
									} else {
										var m = new mini.Window();
										m.set({
											id: "oCheckUserDlg",
											title: '选择处理人',
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
										m.show();
										mini.mask({
											el: m.getEl(),
											cls: 'mini-mask-loading',
											html: '列表加载中...'
										});
										$.ajax({
											url: '../workflow/htmWFTechSel.html?s=select',
											async: true,
											dataType: 'text',
											success: function(e) {
												var a = e;
												m.setBody(a);
												m.setFooter("<div id='SubmitDocActionBar' style='text-align:right'></div>");
												var b = [{
													name: "确定",
													title: "确定并提交",
													ico: "icon-ok",
													clickEvent: "wfSubDocProcess('" + h + "','" + strTacheName + "','','empTarget')",
													align: "2",
													isHidden: "0"
												}, {
													name: "关闭",
													title: "关闭",
													ico: "icon-cancel",
													clickEvent: "mini.get('oCheckUserDlg').destroy();gForm.WFTacheName.value='';",
													align: "2",
													isHidden: "0"
												}];
												var c = "";
												for (var i = 0; i < b.length; i++) {
													c = "<a class='mini-button' plain=true iconCls='" + b[i].ico + "'>" + b[i].name + "</a>";
													$(c).appendTo("#SubmitDocActionBar").attr('onClick', b[i].clickEvent)
												}
												mini.parse();
												wfAddCheckUser();
												mini.unmask(m.getEl())
											}
										})
									}
								} else {
									alert("没有找到下一环节的处理人，请您联系管理员！")
								}
							}
						}
						R = false;
						bReturn = false
					}
				} else {
					alert("路由条件不能为空！\n\n请您联系管理员！");
					R = false;
					bReturn = false
				}
			} else if (g == "直连") {
				alert("多条分支时，不允许使用“直连”。\n\n请您联系管理员！");
				R = false;
				bReturn = false
			} else {
				if (g == "唯一选择") {
					tmpValue = getNodeValue(f, "WFNodeDetail");
					if (tmpValue != "") {
						strTacheNameSelect = getNodeValue(f, "WFTacheNameSelect");
						gArrTacheName.push([tmpValue, f.getAttribute("target") + "^" + f.nodeName, strTacheNameSelect])
					}
					R = true
				} else {
					tmpValue = getNodeValue(f, "WFCondition");
					if (tmpValue != "") {
						if (wfFormulaCompare(gForm, tmpValue)) {
							tmpValue = getNodeValue(f, "WFNodeDetail");
							if (tmpValue != "") {
								strTacheNameSelect = getNodeValue(f, "WFTacheNameSelect");
								gArrTacheName.push([tmpValue, f.getAttribute("target") + "^" + f.nodeName, strTacheNameSelect])
							}
							R = true
						}
					}
				}
			}
			return bReturn
		});
		if (R) {
			var Q = mini.get('oWinDlg');
			if (!Q) {
				Q = new mini.Window();
				Q.set({
					id: "oWinDlg",
					title: '选择处理人',
					url: '',
					allowDrag: false,
					allowResize: false,
					showModal: true,
					enableDragProxy: false,
					showFooter: true,
					footerStyle: "padding:6px 12px;",
					showCloseButton: false,
					width: 510,
					height: 460
				});
				Q.show();
				mini.mask({
					el: Q.getEl(),
					cls: 'mini-mask-loading',
					html: '列表加载中...'
				});
				$.ajax({
					url: '/' + gCommonDB + '/htmWFTechSel?openpage&s=select',
					async: true,
					dataType: 'text',
					success: function(e) {
						var a = e;
						Q.setBody(a);
						Q.setFooter("<div id='SubmitDocActionBar' style='text-align:right'></div>");
						setTimeout(function() {
							wfAddToolbar("oWinDlg")
						}, 10);
						loadOrgTree();
						mini.unmask(Q.getEl())
					}
				})
			} else {
				Q.show()
			}
		}
	}
}
function wfAddCheckUser() {
	listbox = mini.get('userList');
	$.each(gArrLogUser, function(a, b) {
		listbox.addItem($.parseJSON('{"name":"' + b.split("^")[0] + '","id":"' + b.split("^")[0] + '"}'))
	})
}
function wfSubDocProcess(a, b, c, d) {
	var f = [];
	var g = mini.get("selectList");
	if (g.data.length > 0) {
		f = $.map(g.data, function(e) {
			return e.name
		});
		var h = $(a, gWFProcessXML);
		if (h.length == 1) {
			strAppoveStyle = getNodeValue(h[0], "WFApproveStyle");
			if (strAppoveStyle == "" && f.length > 1) {
				alert("仅允许选择一个人！");
				$("#SubmitDocActionBar").css({
					display: ""
				});
				return
			}
		}
		ClearRepeat("wfRouterId", c);
		wfSubDocEnd(a, f, b)
	} else {
		alert("请选择相关人员！");
		$("#SubmitDocActionBar").css({
			display: ""
		})
	}
}
function wfSubDocEnd(b, c, d) {
	var e = 0;
	$('[source="' + $("[name='wfCurNodeId']").val() + '"]', gWFProcessXML).each(function(i, a) {
		if (a.getAttribute("target") == b) {
			if (getNodeValue(a, "WFAgreeMark") == "是") {
				e = 1
			}
		}
	});
	$("[name='wfPreNodeId']").val($("[name='wfCurNodeId']").val());
	$("[name='wfCurNodeId']").val(b);
	$("[name='wfTacheName']").val(d);
	if ($("[name='wfFinishApproval']").val() == "") {
		$("[name='wfPreUser']").val($("[name='curUser']").val());
	} else {
		$("[name='wfPreUser']").val($("[name='wfFinishApproval']").val());
		$("[name='wfFinishApproval']").val();
	}
	var f = true,
		arrUsers = wfFormula(c, "");
	if (b != "") {
		var g = $(b, gWFProcessXML);
		if (g.length == 1) {
			var h = "",
				strSequenceApprove = "";
			h = getNodeValue(g[0], "WFApproveStyle");
			strSequenceApprove = getNodeValue(g[0], "WFSequenceApprove");
			if (h == "多人") {
				if (strSequenceApprove == "是") {
					$("[name='curUser']").val(arrUsers[0]);
					$("[name='wfWaitApproval']").val(arrUsers.slice(1).join(";"));
					f = false
				}
			}
		}
	}
	if (f) {
		$("[name='curUser']").val(arrUsers.join(";"));
	};
	wfSubDocEndSave(true, e)
}
function ClearRepeat(a, b) {
	var c=[];
	var val = $("[name='"+a+"']").val().replace(/\s/g, "");
	if(val){
		c=val.split(";")
	}
	if ($.inArray(b, c) < 0) {
		if (b != "") c.push(b)
	}
	$("[name='"+a+"']").val(c.join(";"));
}
function wfJudge2Next(h) {
	var j = $('[source="' + h + '"]', gWFProcessXML);
	gArrTacheName = [];
	$.each(j, function(i, b) {
		var c = b.getAttribute("target");
		var d = getNodeValue(b, "WFRelationType");
		if (d != "") {
			if (d.indexOf("条件选择") > -1) {
				tmpValue = getNodeValue(b, "WFCondition");
				if (tmpValue != "") {
					if (wfFormulaCompare(gForm, tmpValue)) {
						tmpValue = getNodeValue(b, "WFNodeDetail");
						if (tmpValue != "") {
							strTacheNameSelect = getNodeValue(b, "WFTacheNameSelect");
							gArrTacheName.push([tmpValue, c + "^" + b.nodeName, strTacheNameSelect]);
							var f = mini.get('oWinDlg');
							if (!f) {
								f = new mini.Window();
								f.set({
									id: "oWinDlg",
									title: '选择处理人',
									url: '',
									allowDrag: false,
									allowResize: false,
									showModal: true,
									enableDragProxy: false,
									showFooter: true,
									footerStyle: "padding:6px 12px;",
									showCloseButton: false,
									width: 510,
									height: 460
								});
								f.show();
								mini.mask({
									el: f.getEl(),
									cls: 'mini-mask-loading',
									html: '列表加载中...'
								});
								$.ajax({
									url: '/' + gCommonDB + '/htmWFTechSel?openpage&s=select',
									async: true,
									dataType: 'text',
									success: function(e) {
										var a = e;
										f.setBody(a);
										f.setFooter("<div id='SubmitDocActionBar' style='text-align:right'></div>");
										setTimeout(function() {
											wfAddToolbar("oWinDlg")
										}, 10);
										loadOrgTree();
										mini.unmask(f.getEl())
									}
								})
							} else {
								f.show()
							}
						}
					}
				}
			} else if (d.indexOf("条件直连") > -1) {
				tmpValue = getNodeValue(b, "WFCondition");
				if (tmpValue != "") {
					if (wfFormulaCompare(gForm, tmpValue)) {
						var g = $(c, gWFProcessXML);
						if (g.length == 1) {
							ClearRepeat("wfRouterId", b.nodeName);
							strTacheName = getNodeValue(g[0], "WFNodeName");
							wfSubDocEnd(c, getNodeValue(g[0], "WFActivityOwner").split("|"), strTacheName)
						}
					}
				}
			} else {
				alert("条件未满足，无法提交！\n\n请联系管理员！");
				return
			}
		}
	})
}
function wfAddTacheName() {
	var a = mini.get("selTacheName"),
		selValue = "",
		arrVal = [],
		tempData = {},
		_index = "";
	for (var b = 0, intL = gArrTacheName.length; b < intL; b++) {
		tempData.id = gArrTacheName[b][0] + "^" + gArrTacheName[b][1];
		tempData.name = gArrTacheName[b][0];
		if (gArrTacheName[b][2] == "是") {
			_index = tempData.id
		}
		arrVal.push(tempData);
		tempData = {}
	}
	a.set({
		data: arrVal
	});
	a.setValue(_index);
	wfSelTacheChange(a)
}
function wfDlgBtnSave() {
	if (mini.get("selTacheName").value == "") {
		alert("环节名称为空，请您先选择！");
		return
	}
	if (!confirm("您确定提交吗？")) {
		gForm.WFStatus.value = gWFStatus;
		return
	};
	$("#SubmitDocActionBar").css({
		display: "none"
	});
	var a = mini.get("selTacheName").value.split("^");
	wfSubDocProcess(a[1], a[0], a[2], "empTarget")
}
function wfSelTacheChange(d) {
	var e = mini.get('selectList');
	e.removeAll();
	e = mini.get('tacheList');
	e.removeAll();
	var f = "";
	$.each($(d.value.split("^")[1], gWFProcessXML), function(i, a) {
		f = getNodeValue(a, "WFFormula")
	});
	$.each($(d.value.split("^")[1] + ">WFActivityOwner", gWFProcessXML), function(i, c) {
		$.each(wfFormula(c.getAttribute("value").split("|"), f), function(a, b) {
			e.addItem($.parseJSON('{"name":"' + b + '","id":"' + b + '"}'))
		})
	})
}
function AddValue(b, c) {
	var d = mini.get(c);
	var e = ((mini.get("selTacheName") != undefined) && (mini.get("selTacheName").value != "")) ? mini.get("selTacheName").value.split("^")[1] : "";
	if (e != "") {
		var f = $(e, gWFProcessXML);
		if (f.length == 1) {
			var g = getNodeValue(f[0], "WFApproveStyle");
			if (g == "" && d.data.length > 0) {
				alert("仅允许选择一个人！");
				$("#SubmitDocActionBar").css({
					display: ""
				});
				return
			}
		}
	}
	if ($.grep(d.data, function(a) {
		return b == a.name
	}).length > 0) {
		alert("'" + b + "' 已存在！");
		return
	}
	var h = '{"name":"' + b + '","id":"' + b + '"}';
	var i = $.parseJSON(h);
	d.addItem(i)
}
function DelValue(e) {
	e.sender.removeItem(e.sender.getSelected())
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
	return s
}
function DecodeHtml(s) {
	s = s || '';
	s = s.replace(/&amp;/g, '&');
	s = s.replace(/&lt;br&gt;/g, '<br>');
	return s
}
function wfFormulaCompare(r, t) {
	t = t.replace(/\s/g, "");
	try {
		if ($.trim(t) == "") {
			return false
		};
		var u = t.split("|");
		var v = $.grep(u, function(p) {
			var q = function(m) {
					var n = m.split("&");
					var o = $.grep(n, function(h) {
						var j = function(c, b) {
								var d = b.split(c),
									bT = (d[1].indexOf("'") > -1 || d[1].indexOf("\"") > -1),
									leftValue = "",
									leftObj = {},
									rightValue = new String(d[1].replace(/[\'\"]/g, ""));
								rightValue = rightValue.split(',').join('');
								if (typeof(mini.getbyName(d[0])) != "undefined") {
									leftValue = mini.getbyName(d[0]).getFormValue() ? mini.getbyName(d[0]).getFormValue() : mini.getbyName(d[0]).getValue();
									leftValue = leftValue.split(',').join('')
								} else {
									leftObj = r[d[0]];
									var e = leftObj.length ? leftObj[0].tagName.toLowerCase() : leftObj.tagName.toLowerCase();
									var f = leftObj.length ? leftObj[0].type : leftObj.type;
									if (e == "input") {
										if (typeof leftObj.length == "undefined") {
											leftValue = leftObj.value
										} else {
											$.each($('input[name="' + d[0] + '"][type="' + f + '"]:checked', r), function(i, a) {
												leftValue += a.value.toString()
											})
										}
									} else if (e == "textarea" || e == "select") {
										leftValue = leftObj.value
									} else {
										return false
									}
								}
								if (!bT) {
									var g = parseFloat(leftValue),
										Num2 = parseFloat(d[1])
								}
								switch (c) {
								case "<>":
									if (bT) {
										return leftValue != rightValue
									} else {
										return g != Num2
									}
									break;
								case "=":
									if (bT) {
										return leftValue == rightValue
									} else {
										return g == Num2
									}
									break;
								case "<=":
									return g <= Num2;
									break;
								case ">=":
									return g >= Num2;
									break;
								case ">":
									return g > Num2;
									break;
								case "<":
									return g < Num2;
									break;
								default:
								}
							};
						var k = function(f, s) {
								return f.indexOf(s) > -1
							};
						var l = function(a) {
								if (k(a, "<>")) {
									return j("<>", a)
								} else if (k(a, "<=") || k(a, "=<")) {
									return j("<=", a)
								} else if (k(a, "=>") || k(a, ">=")) {
									return j(">=", a)
								} else if (k(a, "=")) {
									return j("=", a)
								} else if (k(a, ">")) {
									return j(">", a)
								} else if (k(a, "<")) {
									return j("<", a)
								} else {
									arrText = a.split("@");
									return r[arrText[0]].value.indexOf(arrText[1].replace(/[\"\']/g, "")) > -1
								}
							};
						return l(h)
					});
					return o.length == n.length
				};
			return q(p)
		});
		return v.length > 0
	} catch (e) {
		alert("公式比较失败！\n\n字段名可能不存在或者字段名大小写不正确\n\n可能提交按钮函数中未进行赋值！");
		return false
	}
}
function wfSubDocEndSave(d, f) {
	try {} catch (e) {};
	var g = $.parseXML("<" + gCurNode[0].nodeName + "/>").documentElement,
		strIdea = "",
		bCloneNode = true;
	g.setAttribute("tache", getNodeValue(gCurNode[0], "WFNodeName"));
	g.setAttribute("user", gUserCName);
	g.setAttribute("time", gServerTime);
	if (d) {
		if ($("[name='wfTacheName']").val() == "流程结束") {
			gAction = gUserCName + "处理完毕！"
		} else {
			gAction = gUserCName + "提交给了" + $("[name='curUser']").val() + "。"
		}
	}
	g.setAttribute("action", gAction);
	g.setAttribute("mark", f);
	if (gIdeaID.length > 0) {
		$.each(gIdeaID, function(i, b) {
			var c = g;
			c.setAttribute("id", b);
			$.each($('[name=\"' + b + '\"]', gForm), function(i, a) {
				strIdea = a.value
			});
			c.setAttribute("idea", EncodeHtml(strIdea));
			$(c).appendTo(gWFLogXML);
			c = null;
			bCloneNode = false
		})
	}
	if (bCloneNode) {
		$(g).appendTo(gWFLogXML)
	}
	$("[name='wfFlowLogXml']").val(parseInnerXML(gWFLogXML));
	ClearRepeat("allUser", gUserCName);
	fnResumeDisabled();
	var h = gPageEvent["SaveAfter"];
	if (h.replace(/\s/, "") != "") {
		try {
			var j = eval(h);
			if (typeof j != "undefined") {
				if (!j) {
					return
				}
			}
		} catch (e) {
			alert("页面提交前执行的函数 < " + h + " > 可能未在页面中初始化！")
		}
	}
	if (d) {
		var k =$("[name='wfRouterId']").val(),
			arrID = $.trim(k) == "" ? [] : k.split(";");
		if (arrID.length > 0) {
			var l = $(arrID[arrID.length - 1], gWFProcessXML);
			if (l.length > 0) {
				try {
					if (getNodeValue(l[0], "WFRtxEnabled") == "是") {
						var m = "/" + gCurDB + "/vwComOpenDoc/" + gDocKey + ".?OpenDocument",
							msg = wfMsgContent(getNodeValue(l[0], "WFRtxContent"));
						var n = "";
						if (gForm.WFModule) {
							n = gForm.WFModule.value
						}
						var o = "",
							args = "&L=" + m + "&M=" + msg + "&T=" + n;
						if (getNodeValue(l[0], "WFAllObject") == "是") {
							o = "all"
						} else if (getNodeValue(l[0], "WFAllReadUsers") == "是") {
							o = gForm.AllUser.value.split(";").concat(gForm.CurUser.value.split(";")).join(",")
						} else if (getNodeValue(l[0], "WFOnlyInitiator") == "是") {
							o = gForm.WFOnlyInitiator.value.split(";").concat(gForm.CurUser.value.split(";")).join(",")
						} else {
							o = gForm.CurUser.value.split(";").join(",")
						}
						args += "&U=" + o;
						$.ajax({
							url: encodeURI("/" + gCommonDB + "/(agtWFRTX)?OpenAgent" + args),
							cache: false,
							async: false,
							success: function(a) {}
						})
					}
				} catch (e) {}
			}
		}
	}
	document.forms[0].submit();
}
function wfMsgContent(b) {
	var c = $.trim(b);
	if (c != "") {
		var d = (c.match(/\{.*?}/ig));
		$.each(d, function(i, a) {
			tmp = a;
			a = a.substr(1).slice(0, -1);
			if (typeof gForm[a] != "undefined") {
				c = c.replace(tmp, gForm[a].value)
			} else {
				c = c.replace(tmp, "")
			}
		})
	}
	return c == "" ? "-*-" : c
}
function wfFormula(c, d) {
	var f = [];
	if (c.length > 0) {
		var g = [],
			arrT = [];
		$.each(c, function(i, a) {
			if (a.indexOf("^P") > 0) {
				f.push(a.split("^")[0])
			} else if (a.indexOf("^F") > 0) {
				g.push(a.split("^")[0])
			} else {
				f.push(a)
			}
		});
		if (g.length > 0) {
			var h = "&W=" + d + "&I=" + gInitiator + "&CU=" + gUserCName + "&TDB=" + gCurDBName + "&ID=" + gCurDocID + "&IS=" + gIsNewDoc + "&F=" + g.join("|");
			$.ajax({
				url: encodeURI("/" + gFormulaDB + "/(agtWFFormula)?OpenAgent" + h),
				cache: false,
				async: false,
				dataType: "text",
				success: function(b) {
					b = b.replace(/\s/g, "");
					if (b != "") {
						if (d == "") {
							arrT = b.split("^")
						} else {
							$.each(b.split("^"), function(i, a) {
								try {
									_tmp = eval("(" + a + ")");
									if ($.type(_tmp) == "array") {
										arrT = arrT.concat(_tmp)
									} else {
										alert("网页公式返回值非数组，请联系开发人员进行调整！");
										return
									}
								} catch (e) {
									alert(e.name + ": " + e.message)
								}
							})
						}
					}
				}
			})
		}
		if (arrT.length > 0) {
			f = f.concat(arrT)
		}
	}
	return f
}
function loadAttachToolbar() {
	$("[id$=_att]").each(function(i, c) {
		if ($(c).attr("toolbar") != undefined) {
			var t = mini.decode("{" + c.attributes["toolbar"].value + "}");
			$("<div class='mini-toolbar toolbar'><div class='toolbar-title'><span class='mini-button-text mini-button-icon icon-fj'></span>" + t.title + "</div>" + "<div class='toolbar-btn'>" + "<a class='mini-button " + t.upId + "' plain='true' id='" + t.upId + "' iconCls='icon-upload' href=\"javascript:goUploadFile('" + $(c).attr("id") + "');\" >上传</a>" + "<a class='mini-button " + t.editId + "' plain='true' id='" + t.editId + "' iconCls='icon-edit' href=\"javascript:goEditAttachFile('" + $(c).attr("id") + "');\" >编辑</a>" + "<a class='mini-button " + t.delId + "' plain='true' id='" + t.delId + "' iconCls='icon-remove' href=\"javascript:goDelAttachFile('" + $(c).attr("id") + "');\" >删除</a>" + "</div></div>").appendTo("#" + c.id)
		}
	})
}
function returnCellWidth(c) {
	var f = eval("({" + c.attributes["columns"].value + "})");
	var d = [];
	for (var b in f) {
		d.push(f[b])
	}
	return d
}
function loadAttachGrid(r) {
	if (!r) {
		loadAttachToolbar()
	}
	$.ajax({
		url: "/" + gCurDB + "/vwComReadAttach?OpenView&Count=99&ExpandAll&RestrictToCategory=" + gForm.CurDocID.value,
		dataType: "text",
		cache: false,
		success: function(o) {
			j = o;
			if (r) {
				$("[id$='_tab']").empty()
			}
			var p = [];
			if (j.toUpperCase().indexOf("<H2>") < 0) {
				k = eval("[" + j.substr(1) + "]");
				tempItem = $.grep(k, function(i) {
					return i.contId != ""
				});
				var l = true;
				$("[id$=_att]").each(function(i, c) {
					l = true;
					if (!document.getElementById(c.id + "_tab")) {
						trHTML = "";
						if ($(c).attr("colHeaders") != undefined) {
							var d = eval("[" + c.attributes["colHeaders"].value + "]");
							var z = returnCellWidth(c);
							$.each(d, function(i, n) {
								var a = "";
								if ((i + 1) == d.length) {
									a = "border-right:0px;"
								}
								trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy' style='width:" + z[i] + ";" + a + "'>" + n + "</td>")
							});
							$("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody class='attBody' id='" + (c.id + "_tab") + "'></tbody></table>").appendTo("#" + c.id)
						} else {
							$("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (c.id + "_tab") + "'></tbody></table>").appendTo("#" + c.id)
						}
					}
					i = 1;
					var f = {};
					var g = "";
					$.each($.grep(tempItem, function(a) {
						return a.contId == c.id
					}), function(a, e) {
						l = false;
						f = eval("({" + c.attributes["columns"].value + "})");
						for (var b in f) {
							if (b == "docid") {
								g += "<td class='mini-grid-cell attCellTdDiy' width='" + f[b] + "'><input type='checkbox' suffix='" + e.suffix + "' name='$$attachfile' value='" + e[b] + "'/></td>"
							} else if (b == "num") {
								g += "<td class='mini-grid-cell attCellTdDiy' width='" + f[b] + "'>" + (i++) + "</td>"
							} else if (b == "showname") {
								g += "<td class='mini-grid-cell attCellTdDiy' width='" + f[b] + "' style='text-align:left'>&nbsp;&nbsp;<a href='/" + gCurDB + "/0/" + e['docid'] + "/$File/" + e['name'] + "' target='_blank'>" + e[b] + "</a></td>"
							} else {
								g += "<td class='mini-grid-cell attCellTdDiy' width='" + f[b] + "'>" + e[b] + "</td>"
							}
						}
						$("<tr class='attTr" + a % 2 + "'>" + g + "</tr>").appendTo("#" + e.contId + "_tab");
						g = "";
						$(c).css("border-bottom", "0px")
					});
					if (l) {
						var g = "";
						$(c).css("border-bottom", "1px solid #ccc");
						if (!document.getElementById(c.id + "_tab")) {
							trHTML = "";
							if ($(c).attr("colHeaders") != undefined) {
								var d = eval("[" + c.attributes["colHeaders"].value + "]");
								var z = returnCellWidth(c);
								var h = 0;
								$.each(d, function(i, n) {
									var a = "";
									if ((i + 1) == d.length) {
										a = "border-right:0px;"
									}
									trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy' style='width:" + z[i] + ";" + a + "'>" + n + "</td>");
									h += 1
								});
								$("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody id='" + (c.id + "_tab") + "'><tr><td style='text-align:center' colspan='" + h + "'>暂无附件。</td></tr></tbody></table>").appendTo("#" + c.id)
							} else {
								$("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (c.id + "_tab") + "'><tr><td class='mini-grid-cell'>暂无附件。</td></tr></tbody></table>").appendTo("#" + c.id)
							}
						} else {
							$(c).find("table").remove();
							trHTML = "";
							if ($(c).attr("colHeaders") != undefined) {
								var d = eval("[" + c.attributes["colHeaders"].value + "]");
								var z = returnCellWidth(c);
								var h = 0;
								$.each(d, function(i, n) {
									var a = "";
									if ((i + 1) == d.length) {
										a = "border-right:0px;"
									}
									trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy'  style='width:" + z[i] + ";" + a + "'>" + n + "</td>");
									h += 1
								});
								$("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody id='" + (c.id + "_tab") + "'><tr><td style='text-align:center' colspan='" + h + "'>暂无附件。</td></tr></tbody></table>").appendTo("#" + c.id)
							} else {
								$("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (c.id + "_tab") + "'><tr><td class='mini-grid-cell'>暂无附件。</td></tr></tbody></table>").appendTo("#" + c.id)
							}
						}
					}
				})
			} else {
				var m = "";
				$("[id$=_att]").each(function(i, a) {
					$(a).css("border-bottom", "1px solid #ccc");
					if (!document.getElementById(a.id + "_tab")) {
						trHTML = "";
						if ($(a).attr("colHeaders") != undefined) {
							var b = eval("[" + a.attributes["colHeaders"].value + "]");
							var z = returnCellWidth(a);
							var c = 0;
							$.each(b, function(i, n) {
								var a = "";
								if ((i + 1) == b.length) {
									a = "border-right:0px;"
								}
								trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy' style='width:" + z[i] + ";" + a + "'>" + n + "</td>");
								c += 1
							});
							$("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody id='" + (a.id + "_tab") + "'><tr><td style='text-align:center' colspan='" + c + "'>暂无附件。</td></tr></tbody></table>").appendTo("#" + a.id)
						} else {
							$("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (a.id + "_tab") + "'><tr><td class='mini-grid-cell'>暂无附件。</td></tr></tbody></table>").appendTo("#" + a.id)
						}
					} else {
						$(a).find("table").remove();
						trHTML = "";
						if ($(a).attr("colHeaders") != undefined) {
							var b = eval("[" + a.attributes["colHeaders"].value + "]");
							var z = returnCellWidth(a);
							var c = 0;
							$.each(b, function(i, n) {
								var a = "";
								if ((i + 1) == b.length) {
									a = "border-right:0px;"
								}
								trHTML += ("<td class='mini-grid-headerCell attHeadTdDiy' style='width:" + z[i] + ";" + a + "'>" + n + "</td>");
								c += 1
							});
							$("<table style='width:100%;height:100%;border-collapse:collapse;'><thead class='attHead'><tr>" + trHTML + "</tr></thead><tbody id='" + (a.id + "_tab") + "'><tr><td style='text-align:center' colspan='" + c + "'>暂无附件。</td></tr></tbody></table>").appendTo("#" + a.id)
						} else {
							$("<table style='width:100%;height:100%;border-collapse:collapse;'><tbody class='attBody' id='" + (a.id + "_tab") + "'><tr><td class='mini-grid-cell'>暂无附件。</td></tr></tbody></table>").appendTo("#" + a.id)
						}
					}
				})
			}
		}
	})
}
function goUploadFile(g) {
	if (arguments.length == 0) {
		alert("请先设置附件显示区域id，id命名以“_att”结尾。");
		return
	}
	$.ajax({
		url: "/" + gCurDB + "/fmComUpFile?OpenForm",
		dataType: "text",
		success: function(a) {
			if (a.indexOf("ComUpFileID") > -1) {
				try {
					a = a.split("</head>")[1].split("</body>")[0].substr(a.indexOf(">") + 1).replace(/\r|\n|/g, "").replace(/<br>/g, "").replace(/></g, "/><").split("</form>")[0];
					var b = $.parseXML("<body>" + a + "</body>");
					var c = $('input[id="ComUpFileID"]', b)[0].getAttribute("name");
					a = '<form method="post" action="/' + gCurDB + '/fmComUpFile?OpenForm&amp;Seq=1" enctype="multipart/form-data"><input type="hidden" name="__Click" value="0"><input name="FormType" value="fmUpFile" type="hidden"><input name="pDocID" value="" type="hidden"><input name="ContID" value="" type="hidden"><input type="file" style="width:97%" class="mini-htmlfile" name="' + c + '"></form>';
					a += (a + a + a + a + a);
					var d = mini.get("oWinDlgFile");
					if (!d) {
						d = new mini.Window();
						d.set({
							id: "oWinDlgFile",
							title: '上传附件',
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
						d.setBody(a);
						arrBtns = [{
							name: "上传",
							title: "上传附件",
							ico: "icon-save",
							clickEvent: "ifrSubmit('" + (g ? g : "") + "')",
							align: "2",
							isHidden: "0"
						}, {
							name: "关闭",
							title: "关闭",
							ico: "icon-cancel",
							clickEvent: "mini.get('oWinDlgFile').destroy()",
							align: "2",
							isHidden: "0"
						}];
						d.setFooter("<div id='SubmitDocActionBar' style='text-align:right'></div>");
						var f = "";
						setTimeout(function() {
							for (var i = 0; i < arrBtns.length; i++) {
								f = "<a class='mini-button' plain=true iconCls='" + arrBtns[i].ico + "'>" + arrBtns[i].name + "</a>";
								$(f).appendTo("#SubmitDocActionBar").attr('onClick', arrBtns[i].clickEvent);
								mini.parse()
							}
						}, 10)
					}
					d.show()
				} catch (e) {
					for (var x in e) {
						alert(x + "--" + e[x])
					}
				}
			}
		}
	})
}
function ifrSubmit(c) {
	if (mini.get("oWinDlgFile").isLoading) {
		alert('正在上传，请您耐心等待！');
		return
	}
	var d = $.grep($("#oWinDlgFile form"), function(a) {
		return $('input[type="file"]', a)[0].value != ""
	}),
		intM = 0;
	if (d.length < 1) {
		alert("请选择需要上传的文件！");
		return
	};
	mini.get("oWinDlgFile").loading("正在上传...");
	mini.get("oWinDlgFile").set({
		"isLoading": true
	});
	var f = function() {
			mini.get("oWinDlgFile").destroy();
			loadAttachGrid(true)
		};
	var g = {};
	$.each(d, function(i, b) {
		b.pDocID.value = gForm.CurDocID.value;
		b.ContID.value = (c == "" ? "" : (c));
		g.success = function(a) {
			if (a.indexOf("Success") < 0) {
				b.innerHTML = "<b color=red>上传失败！附件可能太大！</b>"
			} else {
				b.innerHTML = "上传成功！"
			}
			intM++;
			if (intM == d.length) {
				window.setTimeout(f, 1000)
			}
		};
		g.error = function(e) {
			b.innerHTML = "<b color=red>上传失败！附件可能太大！</b>";
			intM++;
			if (intM == d.length) {
				window.setTimeout(f, 1000)
			}
		};
		$(b).ajaxSubmit(g)
	})
}
function attachSelectAll(b) {
	$.each($('input[name="$$attachfile"]', "#AttachFileGrid"), function(i, a) {
		a.setAttribute("checked", b.checked)
	})
}
function goEditSendFile() {
	var a = "height=" + (screen.height - 60) + ",width=" + (screen.width - 20) + ",top=0,left=3,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no",
		strPathUrl = "/" + gCurDB + "/(agtComCheckZhengWen)?OpenAgent&P=" + gForm.CurDocID.value + (arguments.length > 0 ? ("_" + arguments[0]) : "_0") + "&S=" + (arguments.length == 2 ? arguments[1] : "");
	window.open(encodeURI(strPathUrl), "", a)
}
function goEditAttachFile(s) {
	if ($.browser.msie) {
		var a = $("input[type=checkbox]:checked", "#" + s);
		if (a.length > 0) {
			var b = ["doc", "docx", "xls", "xlsx"];
			if ($.inArray(a[0].getAttribute("suffix"), b) > -1) {
				var c = "height=" + (screen.height - 60) + ",width=" + (screen.width - 20) + ",top=0,left=3,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
				window.open("/" + gCurDB + "/vwComEditAttach/" + a[0].value + "?EditDocument", "", c);
				return
			} else {
				alert("该附件不能被编辑！")
			}
		} else {
			alert("没有可编辑的附件，请先选择！")
		}
	} else {
		alert("您好，非IE浏览器下不能使用此功能！")
	}
}
function goDelAttachFile(s) {
	var c = $('#' + s + ' input[name="$$attachfile"]:checked');
	if (c.length > 0) {
		if (confirm("您是否需要删除所选附件？")) {
			$.each(c, function(i, b) {
				$.ajax({
					url: "/" + gCurDB + "/(agtComDelAttach)?OpenAgent&DocID=" + b.value,
					dataType: "text",
					cache: false,
					success: function(a) {
						if (parseInt(a) == 1) {
							loadAttachGrid(true)
						}
					}
				})
			})
		}
	} else {
		alert("没有可删除的附件，请先选择！")
	}
}
function goAppendComLang(a) {
	if (a.replace(/\s/g, "") != "") {
		var b = document.getElementById("WFIdea");
		b.value = a
	}
}
function goLookWorkFlow() {
	if (window != top) {
		top.goWorkFlow(gCurDBName, gCurDocID, false)
	} else {
		var a = "height=580,width=880,top=50,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
	//	var b = "/" + gWorkFlowDB + "/htmWFViewWindow?ReadForm&db=" + gCurDBName + "&id=" + gCurDocID + "&cu=" + encodeURI(gForm.CurUser.value) + "&status=" + gWFStatus + "&gWFTacheName=" + encodeURI(gForm.WFTacheName.value);
		var b="../admin/getEntityById.action?uuid="+strDocID+"&className="+modoName;
		window.open(b, "newwindow", a)
	}
}
function wfSign(a) {
	$("#WFSign").css("display", "inline");
	document.getElementById("WFSign").innerHTML = gUserCName + "��" + gServerTime;
	$(a).css("display", "none")
}
function wfStop() {
	if (!gIsNewDoc) {
		if (confirm("确定要做相应操作吗？")) {
			$.ajax({
				url: encodeURI("/" + gCommonDB + "/agtWFStop?OpenAgent&DocID=" + gCurDocID + "&TDB=" + gCurDBName + "&User=" + gUserCName),
				dataType: "text",
				success: function(a) {
					var b = parseInt(a, 10);
					if (b == 0) {
						alert("可能系统有异常，未成功终止，请联系管理员！")
					} else if (b == 1) {
						alert("已成功终止！");
						window.location.reload()
					} else if (b == 2) {
						alert("文档此时正在被他人处理，不能被终止！")
					} else if (b == 3) {
						alert("流程已经被终止或结束，不能被取回！")
					} else {
						alert("您不是文档的提交者，不能被终止！")
					}
				}
			})
		}
	} else {
		alert("您好，草稿状态时，此操作无效！")
	}
}
function wfAddToolbar(a) {
	var b = [{
		name: "查询",
		title: "查询",
		ico: "icon-search",
		clickEvent: "wfOrgSearch()",
		align: "2",
		isHidden: "1",
		id: "wfS"
	}, {
		name: "刷新",
		title: "刷新",
		ico: "icon-reload",
		clickEvent: "wfOrgSearch(true)",
		align: "2",
		isHidden: "1",
		id: "wfRe"
	}, {
		name: "确定",
		title: "确定并提交",
		ico: "icon-ok",
		clickEvent: "wfDlgBtnSave()",
		align: "2",
		isHidden: "0"
	}, {
		name: "关闭",
		title: "关闭",
		ico: "icon-cancel",
		clickEvent: "mini.get(\'" + a + "\').destroy();gForm.WFStatus.value=gWFStatus;gForm.WFTacheName.value='';",
		align: "2",
		isHidden: "0"
	}];
	$("<input type='text' class='mini-textbox' id='searchValue' visible=false />").appendTo("#SubmitDocActionBar");
	var c = "";
	for (var i = 0; i < b.length; i++) {
		c = "<a class='mini-button' id='" + (b[i].id ? b[i].id : "") + "' plain=true visible=" + (b[i].isHidden == "1" ? false : true) + " iconCls='" + b[i].ico + "'>" + b[i].name + "</a>";
		$(c).appendTo("#SubmitDocActionBar").attr('onClick', b[i].clickEvent)
	}
	mini.parse();
	wfAddTacheName()
}
function wfOrgSearch(r) {
	var c = mini.get("orgTree");
	var d = mini.get("searchValue");
	if (d.getValue() == "" || r) {
		c.clearFilter();
		c.collapseAll();
		d.setValue("")
	} else {
		d = d.getValue().toLowerCase();
		c.filter(function(a) {
			var b = a.name ? a.name.toLowerCase() : "";
			if (b.indexOf(d) != -1) {
				return true
			}
		});
		c.expandAll()
	}
}
function changTab(e) {
	if (!(mini.get("wfS") && mini.get("wfRe"))) {
		return
	}
	if (e.tab.title == "组织机构") {
		mini.get("wfS").set({
			visible: true
		});
		mini.get("wfRe").set({
			visible: true
		});
		mini.get("searchValue").set({
			visible: true
		});
		return
	}
	mini.get("wfS").set({
		visible: false
	});
	mini.get("wfRe").set({
		visible: false
	});
	mini.get("searchValue").set({
		visible: false
	})
}
function parseInnerXML(a) {
	if (a.innerXML) {
		return a.innerXML
	} else if (a.xml) {
		return a.xml
	} else if (typeof XMLSerializer != "undefined") {
		return (new XMLSerializer()).serializeToString(a)
	}
	return null
}
function loadOrgTree() {
	var b = mini.get("orgTree");
	$.ajax({
		url: encodeURI("../admin/findOrgTree.action"),
		cache: false,
		async: false,
		success: function(a) {
			if (a) {
				b.loadList(eval(a), "id", "pid")
			}
		}
	})
}
function treeNodeClick(e) {
	if (e.node.type != "dept") {
		AddValue(e.node.text, "selectList")
	}
}
function listNodeClick(e) {
	AddValue(e.sender.getSelected().name, "selectList")
}