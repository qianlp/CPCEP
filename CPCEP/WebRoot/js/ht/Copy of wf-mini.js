var gWFProcessXML = null,
    gCurNode = null,
    gArrTacheName = null,
    gWFLogXML = null,
    gJsonField = null,
    gIdeaID = [],
    gWQSagent = "(wqsWFSubmitDoc)",
    gArrLogUser = [],
    gAction = WF_CONST_LANG.USE_ACTION,
    gPageEvent = {
        "OpenBefore": "",
        "OpenAfter": "",
        "SaveBefore": "",
        "SaveAfter": ""
    };
$(function () {
    if (gIsNewDoc && gWFID == "") {
        alert(WF_CONST_LANG.NO_USE_WORKFLOW + "\n\n" + WF_CONST_LANG.CONTACT_ADMIN)
    } else {
        log("----页面装载开始----");
        var d = gForm.CurUser.value.replace(/\s/g, ""),
            arrTmpCurUser = d.indexOf(",") > -1 ? d.split(",") : d.split(";");
        log("当前流程处理人员: ", d);
        var e = "vwWFXML";
        if (gWFDebug) {
            e = "vwWFDebug"
        }
        var f = "/" + gWorkFlowDB + "/" + e + "/" + gWFID + ".?OpenDocument";
        if (!gIsNewDoc) {
            f += "&id=" + gCurDocID
        }
        log("流程路径: " + f);
        $.ajax({
            url: f,
            cache: false,
            dataType: "text",
            success: function (b) {
                var c = $.parseXML(b.replace(/[\n\r]/g, ""));
                $.each(c.documentElement.childNodes, function (i, a) {
                    if (a.nodeName == "Process") {
                        gWFProcessXML = a
                    } else if (a.nodeName == "Log") {
                        gWFLogXML = a
                    }
                });
                initOnLoad(arrTmpCurUser)
            }
        })
    }
});

function initOnLoad(p) {
    if (typeof beforeLoad != "undefined") {
        beforeLoad()
    }
    $.each($('textarea[name^="ID_"]', gForm), function (i, a) {
        $("<div></div>").insertBefore(a).attr("id", $(a).attr("name"));
        if (!gIsEditDoc) {
            $(a).remove()
        }
    });
    if (gIsEditDoc) {
        log("$进入可编辑模式$");
        var q = gIsNewDoc ? (gWFProcessXML.getAttribute("OriginNode")) : gForm.WFCurNodeID.value;
        gCurNode = $(q, gWFProcessXML);
        if (gIsNewDoc) {
            gWFLogXML.setAttribute("OriginRouter", gWFProcessXML.getAttribute("OriginRouter"));
            gForm.WFCurNodeID.value = q;
            gForm.WFTacheName.value = getNodeValue(gCurNode[0], "WFNodeName")
        }
        if (gWFStatus < 2) {
            $.each($(gForm.WFCurNodeID.value + ">WFOpenBefore", gWFProcessXML), function (i, a) {
                gPageEvent["OpenBefore"] = a.getAttribute("value")
            });
            $.each($(gForm.WFCurNodeID.value + ">WFOpenAfter", gWFProcessXML), function (i, a) {
                gPageEvent["OpenAfter"] = a.getAttribute("value")
            });
            $.each($(gForm.WFCurNodeID.value + ">WFSaveBefore", gWFProcessXML), function (i, a) {
                gPageEvent["SaveBefore"] = a.getAttribute("value")
            });
            $.each($(gForm.WFCurNodeID.value + ">WFSaveAfter", gWFProcessXML), function (i, a) {
                gPageEvent["SaveAfter"] = a.getAttribute("value")
            });
            var r = gPageEvent["OpenBefore"];
            if (r.replace(/\s/, "") !== "") {
                try {
                    new Function(r)()
                } catch (e) {
                    alert(WF_CONST_LANG.OPEN_BEFORE + " < " + r + " > " + WF_CONST_LANG.PAGE_NO_INIT)
                }
            }
            log("*装载按钮*");
            $.each($(gForm.WFCurNodeID.value + ">WFBtnAssign>tr", gWFProcessXML), function (i, d) {
                var e = {};
                $.each($("td", d), function (a, b) {
                    if (a > 0) {
                        var c = b.getAttribute("value");
                        switch (a) {
                        case 1:
                            if (typeof WF_CONST_LANG[c] == "undefined") {
                                WF_CONST_LANG[c] = c
                            };
                            e.name = WF_CONST_LANG[c];
                            break;
                        case 2:
                            e.ico = c;
                            break;
                        case 3:
                            e.clickEvent = c;
                            break;
                        case 4:
                            c = (c == "\u5C45\u53F3" || c == "\u5C45\u5DE6" ? c == "\u5C45\u53F3" ? "2" : "1" : c);
                            e.align = c;
                            break;
                        default:
                            e.isHidden = "0"
                        }
                    }
                });
                gArrBtns.push(e)
            });
            loadAttachGrid(false);
            $.each($(gForm.WFCurNodeID.value + ">WFFieldStatus", gWFProcessXML), function (i, g) {
                var h = g.getAttribute("value");
                if (h !== "") {
                    h = h.replace(/\'/g, "\"");
                    log("*字段控制*: ");
                    gJsonField = $.parseJSON(h);
                    var j = function (a) {
                        for (o in a) {
                            return o
                        }
                    };
                    var k = function (a, b, f) {
                        try {
                            log("　　", f, " -> ", b);
                            var c = a.tagName.toLowerCase(),
                                strType = a.type ? a.type.toLowerCase() : "div";
                            switch (b) {
                            case "e":
                                if (f.indexOf("ID_") > -1) {
                                    gIdeaID.push(f)
                                }
                                break;
                            case "r":
                                if (c == "textarea" && f.indexOf("ID_") > -1) {
                                    $('[name=\"' + f + '\"]', gForm).remove()
                                } else {
                                    $(a).attr("enabled", false)
                                }
                                break;
                            case "h":
                                if (f.indexOf("js-") > -1) {
                                    $(a).css("display", "none")
                                } else {
                                    if (c != "textarea") {
                                        $(a).attr("visible", false)
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
                                            $(a).attr("visible", false)
                                        }
                                    } else {
                                        if (f.indexOf("js-") > -1) {
                                            $(a).css("display", "")
                                        }
                                    }
                                }
                                break;
                            case "w":
                                if (f.indexOf("ID_") > -1) {
                                    gIdeaID.push(f)
                                }
                                $(a).attr("required", true);
                                break;
                            case "m":
                                var d = gJsonField[f].m.split("$$");
                                if (_arrSeeUser == null) {
                                    _arrSeeUser = wfFormula(d[0].split("|"), "")
                                }
                                if ($.inArray(gUserCName, _arrSeeUser) < 0) {
                                    if (f.indexOf("js-") > -1) {
                                        $(a).css("display", "none")
                                    } else {
                                        $(a).attr("visible", false)
                                    }
                                } else {
                                    if (f.indexOf("js-") > -1) {
                                        $(a).css("display", "")
                                    }
                                    if (f.indexOf("ID_") > -1) {
                                        gIdeaID.push(f)
                                    }
                                    $(a).attr("required", true);
                                    break
                                }
                                break;
                            default:
                            }
                        } catch (e) {
                            log("字段设置状态错误")
                        }
                    };
                    var l = [];
                    for (f in gJsonField) {
                        var m = j(gJsonField[f]),
                            type = "name",
                            _arrSeeUser = null;
                        if (f.indexOf("js-") > -1) {
                            var n = $("." + f, gForm)
                        } else {
                            var n = $('[' + type + '=\"' + f + '\"]', gForm)
                        } if (n.length == 0) {
                            l.push(f)
                        }
                        $.each(n, function (i, a) {
                            k(a, m, f)
                        })
                    }
                    if (l.length > 0) {
                        alert(WF_CONST_LANG.NO_CHECK_FIELD + "\n" + l.join("\n"))
                    }
                }
            });
            var r = gPageEvent["OpenAfter"];
            if (r.replace(/\s/, "") != "") {
                try {
                    new Function(r)()
                } catch (e) {
                    alert(WF_CONST_LANG.OPEN_AFTER + " < " + r + " > " + WF_CONST_LANG.PAGE_NO_INIT)
                }
            }
            if (gWFStatus == 1) {
                try {
                    $("body").everyTime("5s", "docLock", function () {
                        $.ajax({
                            url: "/" + gCommonDB + "/(agtGetSubTimeO)?OpenAgent&id=" + gCurDocID + "&db=" + gCurDBName,
                            cache: false,
                            dataType: "text",
                            async: false,
                            success: function (a) {
                                var b = $.trim(a);
                                if (b != "") {
                                    if (mini.formatDate(mini.parseDate(b.split("^")[0]), "yyyy-MM-dd HH:mm:ss") > mini.formatDate(mini.parseDate(gServerTime), "yyyy-MM-dd HH:mm:ss")) {
                                        $.gritter.add({
                                            title: WF_CONST_LANG.CONFLICT_MSG_TITLE,
                                            text: "[" + b.split("^")[1] + "]" + WF_CONST_LANG.CONFLICT_MSG_CONTENT,
                                            class_name: "coding123",
                                            time: 1000000
                                        });
                                        $("body").stopTime("docLock")
                                    }
                                }
                            }
                        })
                    })
                } catch (e) {}
            }
        }
    } else {
        log("$进入只读模式$");
        var s = function (k) {
            $.each($(k + ">WFFieldStatus", gWFProcessXML), function (i, c) {
                var d = c.getAttribute("value");
                if (d !== "") {
                    d = d.replace(/'/g, "\"");
                    gJsonField = $.parseJSON(d);
                    var g = function (a) {
                        for (o in a) {
                            return o
                        }
                    };
                    var h = function (a, b, f) {
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
                            $.each($("." + f, gForm), function (i, a) {
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
            var t = gForm.WFPreNodeID.value;
            if ($.trim(t) != "") {
                s(t.substr(t.lastIndexOf("N")))
            }
        } else {
            var u = [];
            $.each($('[user="' + gUserCName + '"]', gWFLogXML), function (i, a) {
                var b = a.nodeName;
                if ($.inArray(b, u) < 0) {
                    u.push(b);
                    s(b)
                }
            })
        }
    }
    gJsonField = null;
    if (gCloseBtn.length > 0) {
        gArrBtns = gArrBtns.concat(gCloseBtn)
    }
    var w = '<button class="btn btn-md" type="button"></button>';
    $("#btnCont").empty();
    var x = function (v) {
        var a = new RegExp("^[\u4e00-\u9fa5]+$");
        if (a.test(v)) return true;
        return false
    };
    log("*开始生成按钮*");
    $.each($.grep(gArrBtns, function (a) {
        return a.isHidden != "1"
    }), function (i, e) {
        var a = $(e.align == "1" ? $(w).appendTo("#btnContL") : $(w).appendTo("#btnContR")).html(x(e.name) ? e.name.split("").join("&nbsp;&nbsp;") : e.name).addClass(e.ico).attr({
            onClick: e.clickEvent,
            style: "margin:0px 7px 15px 7px"
        });
        log("　　按钮" + i + ": " + e.name + "|" + e.ico + "|" + e.clickEvent)
    });
    log("*结束生成按钮*");
    $("[class^='miniui-']").attr("class", function () {
        return this.className.replace(/miniui-/g, "mini-")
    });
    log("*转换为MINIUI元素,并渲染*");
    mini.parse();
    if (!gIsNewDoc) {
        if (gWFLogXML.childNodes) {
            log("*开始生成意见*");
            var y = 1,
                strId = "",
                WFIdeaPrefix = "",
                WFIdeaSuffix = "";
            $.each(gWFLogXML.childNodes, function (i, a) {
                log(XML2String(a));
                if (typeof (unionIdea) == "undefined" || !unionIdea) {
                    strId = a.getAttribute("id") ? $.trim(a.getAttribute("id")) : "";
                    if (strId.indexOf("ID_") > -1) {
                        var b = a.getAttribute("time"),
                            _tache = a.getAttribute("tache"),
                            _tdID = "td" + strId + y,
                            _idea = DecodeHtml(a.getAttribute("idea")),
                            _mark = a.getAttribute("mark");
                        WFIdeaPrefix = '<tr><td class="tdTecheName" style="width:150px;text-align:center">' + _tache + '</td><td class="tdIdea" ' + (_mark != 'undefined' && _mark == '1' ? 'style="color:red;"' : '') + '><div id="' + _tdID + '">&nbsp;</div></td>';
                        WFIdeaSuffix = '<td class="tdIdeaUser">' + a.getAttribute("user").replace(/[0-9]/g, "") + '</td><td class="tdIdeaTime">' + b + '</td></tr>';
                        $("#showWFIdea").append(WFIdeaPrefix + WFIdeaSuffix);
                        $("#" + _tdID).empty().html(_idea)
                    }
                } else {
                    if (typeof (document.getElementById("ideaArea")) == "undefined") {
                        alert(WF_CONST_LANG.IDEA_AREA_UNDEFINDED)
                    } else {
                        strId = "ID_ideaArea";
                        if (a.getAttribute("idea") != undefined) {
                            var b = a.getAttribute("time"),
                                _tache = a.getAttribute("tache"),
                                _tdID = "td" + strId + y,
                                _idea = DecodeHtml(a.getAttribute("idea")) == "" ? "\u9605\u3002" : DecodeHtml(a.getAttribute("idea")),
                                _mark = a.getAttribute("mark");
                            WFIdeaPrefix = '<tr><td class="tdTecheName" style="width:150px;text-align:center">' + _tache + '</td><td class="tdIdea" ' + (_mark != 'undefined' && _mark == '1' ? 'style="color:red;"' : '') + '><div id="' + _tdID + '">&nbsp;</div></td>';
                            WFIdeaSuffix = '<td class="tdIdeaUser">' + a.getAttribute("user").replace(/[0-9]/g, "") + '</td><td class="tdIdeaTime">' + b + '</td></tr>';
                            $("#showWFIdea").append(WFIdeaPrefix + WFIdeaSuffix);
                            $("#" + _tdID).empty().html(_idea)
                        }
                    }
                }
                y++
            });
            log("*结束生成意见*")
        }
    }
    if (typeof (afterLoad) != "undefined") {
        afterLoad()
    }
    log("----页面装载完毕----");
    log("")
}

function getNodeValue(b, c) {
    var d = "";
    $.each($(c, b), function (i, a) {
        d = a.getAttribute("value").replace(/@line@/g, "")
    });
    return $.trim(d)
}

function fnResumeDisabled() {
    $("input[disabled],textarea[disabled],select[disabled]").prop("disabled", false)
}

function wfSubDocStart() {
    log("");
    log("----页面提交开始----");
    var n = false;
    var o = "";
    try {
        if (!gIsNewDoc) {
            log("*检测文档冲突*");
            $.ajax({
                url: '/' + gCommonDB + "/(agtGetSubTime)?OpenAgent&id=" + gCurDocID + "&db=" + gCurDBName,
                cache: false,
                dataType: 'text',
                async: false,
                success: function (a) {
                    var b = $.trim(a);
                    if (b != "") {
                        var c = b.split("^");
                        if (mini.formatDate(mini.parseDate(c[0]), "yyyy-MM-dd HH:mm:ss") != mini.formatDate(mini.parseDate(gSubTime), "yyyy-MM-dd HH:mm:ss")) {
                            n = true;
                            o = c[1]
                        }
                        log("前端处理时间: ", mini.formatDate(mini.parseDate(gSubTime), "yyyy-MM-dd HH:mm:ss"));
                        log("后端处理时间: ", mini.parseDate(c[0]), "yyyy-MM-dd HH:mm:ss")
                    }
                }
            })
        }
    } catch (e) {
        n = false
    }
    if (n) {
        alert(WF_CONST_LANG.LOCK_SUBMIT_PREFIX + o + WF_CONST_LANG.LOCK_SUBMIT_SUFFIX);
        return
    }
    var p = new mini.Form(gForm);
    p.validate();
    if (p.isValid() == false) {
        log("*表单验证失败,终止提交*");
        return
    }
    log("当前节点ID(WFCurNodeID): ", gForm.WFCurNodeID.value);
    var q = $(gForm.WFCurNodeID.value, gWFProcessXML);
    var r = getNodeValue(q[0], "WFWithOrg") === "" ? 0 : 1;
    log("是否启用组织机构: ", r);
    if (arguments.length == 1) {
        gWQSagent = arguments[0]
    }
    var s = gPageEvent["SaveBefore"];
    if (s.replace(/\s/, "") != "") {
        try {
            new Function(s)()
        } catch (e) {
            alert(WF_CONST_LANG.SAVE_BEFORE + "< " + s + " > " + WF_CONST_LANG.PAGE_NO_INIT)
        }
    }
    gArrTacheName = [];
    if (!gIsNewDoc) {
        if (q.length == 1) {
            var t = "",
                strSequenceApprove = "",
                intApproveNum = 0,
                tmpGetValue = "";
            strSequenceApprove = getNodeValue(q[0], "WFSequenceApprove");
            t = getNodeValue(q[0], "WFApproveStyle");
            tmpGetValue = getNodeValue(q[0], "WFApproveNum");
            log("审批方式: ", t, " ", "审批人数: ", tmpGetValue, " ", "是否顺序审批: ", strSequenceApprove);
            if (tmpGetValue != "") {
                intApproveNum = parseInt(tmpGetValue, 10)
            }
            if (t == WF_CONST_LANG.MUTIL_PERSON) {
                var u = [],
                    strWF = gForm.WFFinishApproval.value.replace(/\s/g, ""),
                    bGo2Next = true;
                if (strWF != "") {
                    log("已处理完人员: ", strWF);
                    u = strWF.split(";")
                };
                if ($.grep(u, function (a) {
                    return $.trim(a) == gUserCName
                }).length == 0) {
                    u.push(gUserCName)
                }
                gForm.WFFinishApproval.value = u.join(";");
                if (strSequenceApprove == WF_CONST_LANG.YES) {
                    log("*顺序审批*");
                    if ($.trim(gForm.WFWaitApproval.value) == "") {
                        gForm.WFWaitApproval.value = "";
                        log("多人审批已经处理完毕");
                        bGo2Next = false
                    } else {
                        var v = gForm.WFWaitApproval.value.replace(/\s/g, "").split(";");
                        log("多人审批->原先所有等待处理人: ", gForm.WFWaitApproval.value);
                        gForm.CurUser.value = v[0];
                        log("多人审批->待处理人: ", gForm.CurUser.value);
                        gForm.WFWaitApproval.value = v.slice(1).join(";");
                        log("多人审批->将要待处理人: ", gForm.WFWaitApproval.value)
                    }
                } else {
                    log("*随机审批*");
                    var w = gForm.CurUser.value.replace(/\s/g, "");
                    if (w == "") {
                        alert(WF_CONST_LANG.DOCUMENT_NOT_SUBMIT);
                        return
                    }
                    log("多人审批->原先所有待处理人: ", gForm.CurUser.value);
                    var x = w.split(";");
                    var y = $.grep(x, function (a) {
                        return $.trim(a) != gUserCName
                    });
                    if (intApproveNum > 0) {
                        if (intApproveNum == u.length) {
                            bGo2Next = false
                        } else {
                            if (y.length > 0) {
                                gForm.CurUser.value = y.join(";")
                            } else {
                                bGo2Next = false
                            }
                        }
                    } else {
                        if (x.length == 1) {
                            bGo2Next = false
                        } else {
                            gForm.CurUser.value = y.join(";")
                        }
                    }
                    log("多人审批->待处理人: ", gForm.CurUser.value)
                }
                log("是否多人审批已处理完毕: ", bGo2Next ? "YES" : "NO");
                if (bGo2Next) {
                    var z = 0;
                    $('[source="' + gForm.WFCurNodeID.value + '"]', gWFProcessXML).each(function (i, a) {
                        if (getNodeValue(a, "WFAgreeMark") == WF_CONST_LANG.YES) {
                            z = 1
                        }
                    });
                    if (confirm(WF_CONST_LANG.CONFIRM_SUBMIT)) {
                        wfSubDocEndSave(false, z)
                    } else {
                        gForm.WFStatus.value = gWFStatus
                    }
                    return
                }
            }
        }
    }
    gForm.WFStatus.value = 1;
    var A = $('[source="' + gForm.WFCurNodeID.value + '"]', gWFProcessXML);
    if (A.length == 1) {
        log("路由分支: ", 1, " 条");
        var B = getNodeValue(A[0], "WFRelationType");
        log("路由类型: ", B);
        if (B != "") {
            var C = A[0].getAttribute("target"),
                strTacheName = WF_CONST_LANG.WORKFLOW_END;
            ClearRepeat("WFRouterID", A[0].nodeName);
            if (B == WF_CONST_LANG.DIRECT) {
                if (!confirm(WF_CONST_LANG.CONFIRM_SUBMIT)) {
                    gForm.WFStatus.value = gWFStatus;
                    return
                }
                if (C.indexOf("E") > -1) {
                    log("直连 --> 流程结束");
                    gForm.WFStatus.value = 2;
                    wfSubDocEnd("", [], strTacheName);
                    return
                }
                var D = $(C, gWFProcessXML);
                if (D.length == 1) {
                    strTacheName = getNodeValue(D[0], "WFNodeName");
                    tmpValue = getNodeValue(D[0], "WFActivityOwner");
                    t = getNodeValue(D[0], "WFApproveStyle");
                    var E = getNodeValue(D[0], "WFFormula");
                    log("环节名称: ", strTacheName);
                    log("环节处理人: ", tmpValue);
                    log("环节审批类型: ", t);
                    log("是否网页公式: ", E);
                    if (tmpValue != "") {
                        gArrLogUser = wfFormula(tmpValue.split("|"), E);
                        log("直连", gArrLogUser);
                        if (gArrLogUser.length == 1) {
                            wfSubDocEnd(C, gArrLogUser, strTacheName)
                        } else {
                            if (t != WF_CONST_LANG.MUTIL_PERSON) {
                                alert(WF_CONST_LANG.ROUTER_RELATIION_SCENE)
                            } else {
                                wfSubDocEnd(C, gArrLogUser, strTacheName);
                                return
                            }
                        }
                    } else {
                        alert(WF_CONST_LANG.NO_FIND_NEXT_PERSON)
                    }
                } else {
                    alert(WF_CONST_LANG.NEXT_NODE_NOT_EXITED);
                    return
                }
            } else if (B == WF_CONST_LANG.ONLY_SELECT) {
                if (C.indexOf("E") > -1) {
                    log("唯一选择 --> 流程结束");
                    gForm.WFStatus.value = 2;
                    wfSubDocEnd("", [], strTacheName);
                    return
                }
                strTacheName = getNodeValue(A[0], "WFNodeDetail");
                strTacheNameSelect = getNodeValue(A[0], "WFTacheNameSelect");
                gArrTacheName.push([strTacheName, C + "^" + A[0].nodeName, strTacheNameSelect]);
                log("目标环节名称: ", strTacheName);
                log("是否默认选中: ", strTacheNameSelect);
                log("目标环节编号: ", A[0].nodeName);
                var F = mini.get('oWinDlg');
                if (!F) {
                    F = new mini.Window();
                    F.set({
                        id: "oWinDlg",
                        title: WF_CONST_LANG.SELECT_APPROVER,
                        url: '',
                        allowDrag: true,
                        allowResize: false,
                        showModal: true,
                        enableDragProxy: false,
                        showFooter: true,
                        showCloseButton: true,
                        footerStyle: "padding:6px 12px;",
                        width: 510,
                        height: 460
                    });
                    F.show();
                    mini.mask({
                        el: F.getEl(),
                        cls: 'mini-mask-loading',
                        html: WF_CONST_LANG.LIST_LOADING
                    });
                    $.ajax({
                        url: '/HTCommon/HT_Common.nsf/htmWFTechSel?OpenPage&Org=' + (r ? "" : "1"),
                        async: true,
                        dataType: 'text',
                        success: function (e) {
                            var a = e;
                            F.setBody(baidu.template(a, PublicField));
                            F.setFooter("<div id='SubmitDocActionBar' style='text-align:left'></div>");
                            setTimeout(function () {
                                wfAddToolbar("oWinDlg")
                            }, 10);
                            mini.unmask(F.getEl())
                        }
                    })
                } else {
                    F.show()
                }
            } else {
                alert(WF_CONST_LANG.ROUTER_ERROR);
                return
            }
        }
    } else {
        log("路由分支: ", A.length, " 条");
        var G = false;
        $.each(A, function (d, f) {
            var g = getNodeValue(f, "WFRelationType"),
                tmpValue = "",
                bReturn = true;
            log("路由类型: ", g);
            if (g == WF_CONST_LANG.CONDITION_DIRECT) {
                tmpValue = getNodeValue(f, "WFCondition");
                log("条件公式: ", tmpValue);
                if (tmpValue != "") {
                    if (wfFormulaCompare(gForm, tmpValue)) {
                        log("满足条件");
                        ClearRepeat("WFRouterID", f.nodeName);
                        var h = f.getAttribute("target");
                        if (h.indexOf("E") > -1) {
                            log(g, " --> 流程结束");
                            gForm.WFStatus.value = 2;
                            wfSubDocEnd("", [], WF_CONST_LANG.WORKFLOW_END)
                        } else {
                            var j = $(h, gWFProcessXML);
                            if (j.length == 1) {
                                strTacheName = getNodeValue(j[0], "WFNodeName");
                                t = getNodeValue(j[0], "WFApproveStyle");
                                tmpValue = getNodeValue(j[0], "WFActivityOwner");
                                var k = getNodeValue(j[0], "WFFormula");
                                log("环节名称: ", strTacheName);
                                log("环节处理人: ", tmpValue);
                                log("是否网页公式: ", k);
                                if (tmpValue != "") {
                                    gArrLogUser = [];
                                    $.each($(h, gWFLogXML), function (i, a) {
                                        var b = a.getAttribute("user");
                                        if (b) {
                                            if ($.inArray(b, gArrLogUser) < 0) {
                                                gArrLogUser.push(b + "^P")
                                            }
                                        }
                                    });
                                    log(h, "环节号已有下列人员参与过处理: ", gArrLogUser);
                                    if (gArrLogUser.length == 0) {
                                        gArrLogUser = $.map(wfFormula(tmpValue.split("|"), k), function (a) {
                                            return a + "^P"
                                        });
                                        log("重新获取人员: ", gArrLogUser)
                                    }
                                    var l = [];
                                    $.each(gArrLogUser, function (i, a) {
                                        if ($.inArray(a, l) < 0) {
                                            l.push(a)
                                        }
                                    });
                                    gArrLogUser = l;
                                    if (!(t != WF_CONST_LANG.MUTIL_PERSON && gArrLogUser.length > 1)) {
                                        wfSubDocEnd(h, gArrLogUser, strTacheName)
                                    } else {
                                        log("审批方式为: 单人,但有多位处理人,故需弹出选择其一. ", gArrLogUser);
                                        var m = new mini.Window();
                                        m.set({
                                            id: "oCheckUserDlg",
                                            title: WF_CONST_LANG.SELECT_APPROVER,
                                            allowDrag: true,
                                            allowResize: false,
                                            showModal: true,
                                            enableDragProxy: false,
                                            showFooter: true,
                                            footerStyle: "padding:6px 12px;",
                                            showCloseButton: true,
                                            width: 510,
                                            height: 460
                                        });
                                        m.show();
                                        mini.mask({
                                            el: m.getEl(),
                                            cls: 'mini-mask-loading',
                                            html: WF_CONST_LANG.LIST_LOADING
                                        });
                                        $.ajax({
                                            url: '/HTCommon/HT_Common.nsf/htmWFTechSel?OpenPage&Back=1',
                                            async: true,
                                            dataType: 'text',
                                            success: function (e) {
                                                var a = e;
                                                m.setBody(baidu.template(a, PublicField));
                                                m.setFooter("<div id='SubmitDocActionBar' style='text-align:right'></div>");
                                                var b = [{
                                                    name: WF_CONST_LANG.OK,
                                                    title: WF_CONST_LANG.OK_TITLE,
                                                    ico: "icon-ok",
                                                    clickEvent: "wfSubDocProcess('" + h + "','" + strTacheName + "','','empTarget')",
                                                    align: "2",
                                                    isHidden: "0"
                                                }, {
                                                    name: WF_CONST_LANG.CANCEL,
                                                    title: WF_CONST_LANG.CANCEL,
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
                                    alert(WF_CONST_LANG.NO_FIND_NEXT_PERSON)
                                }
                            }
                        }
                        G = false;
                        bReturn = false
                    }
                } else {
                    alert(WF_CONST_LANG.ROUTER_CONDITION_BLANK);
                    G = false;
                    bReturn = false
                }
                log("是否允许弹框: ", G ? "YES" : "NO");
                log("是否阻止循环路由: ", bReturn ? "NO" : "YES")
            } else if (g == WF_CONST_LANG.DIRECT) {
                alert(WF_CONST_LANG.MUTIL_BRANCH_NOT_USE_DIRECT);
                G = false;
                bReturn = false
            } else {
                if (g == WF_CONST_LANG.ONLY_SELECT) {
                    tmpValue = getNodeValue(f, "WFNodeDetail");
                    log("环节名称: ", tmpValue);
                    if (tmpValue != "") {
                        strTacheNameSelect = getNodeValue(f, "WFTacheNameSelect");
                        log("是否默认选中: ", strTacheNameSelect);
                        log("目标环节编号: ", f.nodeName);
                        gArrTacheName.push([tmpValue, f.getAttribute("target") + "^" + f.nodeName, strTacheNameSelect])
                    }
                    G = true
                } else {
                    tmpValue = getNodeValue(f, "WFCondition");
                    log("条件公式: ", tmpValue);
                    if (tmpValue != "") {
                        if (wfFormulaCompare(gForm, tmpValue)) {
                            log("*满足条件*");
                            tmpValue = getNodeValue(f, "WFNodeDetail");
                            log("环节名称: ", tmpValue);
                            if (tmpValue != "") {
                                strTacheNameSelect = getNodeValue(f, "WFTacheNameSelect");
                                log("是否默认选中: ", strTacheNameSelect);
                                log("目标环节编号: ", f.nodeName);
                                gArrTacheName.push([tmpValue, f.getAttribute("target") + "^" + f.nodeName, strTacheNameSelect])
                            }
                            G = true
                        }
                    }
                }
                log("是否允许弹框: ", G ? "YES" : "NO");
                log("是否阻止循环路由: ", bReturn ? "NO" : "YES")
            }
            return bReturn
        });
        if (G) {
            log("*弹出处理人员选择框*");
            var F = mini.get('oWinDlg');
            if (!F) {
                F = new mini.Window();
                F.set({
                    id: "oWinDlg",
                    title: WF_CONST_LANG.SELECT_APPROVER,
                    url: '',
                    allowDrag: true,
                    allowResize: false,
                    showModal: true,
                    enableDragProxy: false,
                    showFooter: true,
                    footerStyle: "padding:6px 12px;",
                    showCloseButton: true,
                    width: 510,
                    height: 460
                });
                F.show();
                mini.mask({
                    el: F.getEl(),
                    cls: 'mini-mask-loading',
                    html: WF_CONST_LANG.LIST_LOADING
                });
                $.ajax({
                    url: '/HTCommon/HT_Common.nsf/htmWFTechSel?OpenPage&Org=' + (r ? "" : "1"),
                    async: true,
                    dataType: 'text',
                    success: function (e) {
                        var a = e;
                        F.setBody(baidu.template(a, PublicField));
                        F.setFooter("<div id='SubmitDocActionBar' style='text-align:right'></div>");
                        setTimeout(function () {
                            wfAddToolbar("oWinDlg")
                        }, 10);
                        mini.unmask(F.getEl())
                    }
                })
            } else {
                F.show()
            }
        }
    }
}

function wfAddCheckUser() {
    listbox = mini.get('userList');
    $.each(gArrLogUser, function (a, b) {
        listbox.addItem($.parseJSON('{"name":"' + b.split("^")[0] + '","id":"' + b.split("^")[0] + '"}'))
    })
}

function wfSubDocProcess(a, b, c, d) {
    var f = [];
    var g = mini.get("selectList");
    if (g.data.length > 0) {
        f = $.map(g.data, function (e) {
            return e.name
        });
        var h = $(a, gWFProcessXML);
        if (h.length == 1) {
            strAppoveStyle = getNodeValue(h[0], "WFApproveStyle");
            if (strAppoveStyle == "" && f.length > 1) {
                alert(WF_CONST_LANG.ONLY_SELECT_ONE);
                mini.unmask(document.body);
                return
            }
        }
        ClearRepeat("WFRouterID", c);
        wfSubDocEnd(a, f, b)
    } else {
        alert(WF_CONST_LANG.SELECT_RElATED_PERSON);
        mini.unmask(document.body)
    }
}

function wfSubDocEnd(b, c, d) {
    var e = 0;
    $('[source="' + gForm.WFCurNodeID.value + '"]', gWFProcessXML).each(function (i, a) {
        if (a.getAttribute("target") == b) {
            if (getNodeValue(a, "WFAgreeMark") == WF_CONST_LANG.YES) {
                e = 1
            }
        }
    });
    log("是否意见标识: ", e ? "YES" : "NO");
    gForm.WFPreNodeID.value = gForm.WFCurNodeID.value;
    gForm.WFCurNodeID.value = b;
    gForm.WFTacheName.value = d;
    log("目标环节ID: ", b);
    log("目标环节名称: ", d);
    if (gForm.WFFinishApproval.value == "") {
        gForm.WFPreUser.value = gForm.CurUser.value
    } else {
        gForm.WFPreUser.value = gForm.WFFinishApproval.value;
        gForm.WFFinishApproval.value = ""
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
            log("审批方式: ", h);
            log("是否顺序审批: ", strSequenceApprove);
            if (h == WF_CONST_LANG.MUTIL_PERSON) {
                if (strSequenceApprove == WF_CONST_LANG.YES) {
                    gForm.CurUser.value = arrUsers[0];
                    gForm.WFWaitApproval.value = arrUsers.slice(1).join(";");
                    f = false;
                    log("下一环节为多人顺序审批,审批人为: ", arrUsers[0]);
                    log("下一环节为多人顺序审批,待审批人为: ", gForm.WFWaitApproval.value)
                }
            }
        }
    }
    if (f) {
        gForm.CurUser.value = arrUsers.join(";");
        log("下一环节审批人为: ", gForm.CurUser.value)
    }
    wfSubDocEndSave(true, e)
}

function ClearRepeat(a, b) {
    var c = gForm[a].value.replace(/\s/g, "").split(";");
    if ($.inArray(b, c) < 0) {
        if (b != "") c.push(b)
    }
    gForm[a].value = c.join(";")
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
        if (gArrTacheName[b][2] == WF_CONST_LANG.YES) {
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
        alert(WF_CONST_LANG.NO_TACHENAME);
        return
    }
    if (!confirm(WF_CONST_LANG.CONFIRM_SUBMIT)) {
        gForm.WFStatus.value = gWFStatus;
        return
    }
    mini.mask({
        el: document.body,
        cls: 'mini-mask-loading',
        html: '数据处理中...'
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
    $.each($(d.value.split("^")[1], gWFProcessXML), function (i, a) {
        f = getNodeValue(a, "WFFormula")
    });
    $.each($(d.value.split("^")[1] + ">WFActivityOwner", gWFProcessXML), function (i, c) {
        $.each(wfFormula(c.getAttribute("value").split("|"), f), function (a, b) {
            e.addItem($.parseJSON('{"name":"' + b + '","id":"' + b + '"}'))
        })
    })
}

function AddValue(b, c) {
    var d = mini.get(c),
        objSelTacheName = mini.get("selTacheName");
    var e = ((objSelTacheName != undefined) && (objSelTacheName.value != "")) ? objSelTacheName.value.split("^")[1] : "";
    if (e != "") {
        var f = $(e, gWFProcessXML);
        if (f.length == 1) {
            var g = getNodeValue(f[0], "WFApproveStyle");
            if (g == "" && d.data.length > 0) {
                alert(WF_CONST_LANG.ONLY_SELECT_ONE);
                mini.unmask(document.body);
                return
            }
        }
    }
    if ($.grep(d.data, function (a) {
        return b == a.name
    }).length > 0) {
        alert("'" + b + "' " + WF_CONST_LANG.EXITED);
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

function wfFormulaCompare(h, j) {
    j = j.replace(/\s/g, "");
    try {
        if ($.trim(j) == "") {
            return false
        }
        if (j.indexOf("{") > -1 && j.indexOf("}") > -1) {
            var k = (j.match(/\(\{.*?}\)|\{.*?}/ig));
            $.each(k, function (i, b) {
                var c = b,
                    v = "",
                    bC = c.match(/\(\{.*?}\)/ig);
                if (bC) {
                    b = b.substr(2).slice(0, -2)
                } else {
                    b = b.substr(1).slice(0, -1)
                } if (mini.getbyName(b)) {
                    v = mini.getbyName(b).getFormValue() ? mini.getbyName(b).getFormValue() : mini.getbyName(b).getValue()
                } else {
                    var d = h[b];
                    var e = (typeof d.length != "undefined") ? d[0].tagName.toLowerCase() : d.tagName.toLowerCase();
                    var f = (typeof d.length != "undefined") ? d[0].type : d.type;
                    if (e == "input") {
                        if (typeof d.length == "undefined") {
                            v = d.value
                        } else {
                            var g = [];
                            $.each($('input[name="' + b + '"][type="' + f + '"]:checked', h), function (i, a) {
                                g.push(a.value.toString())
                            });
                            v = g.join(',')
                        }
                    } else if (e == "textarea" || e == "select") {
                        v = d.value
                    } else {
                        return false
                    }
                } if (bC) {
                    v = "" ? 0 : v;
                    v = parseFloat(v)
                } else {
                    v = "'" + v + "'"
                }
                j = j.replace(c, v);
                log("公式: ", j)
            });
            return new Function("return " + j)()
        } else {
            alert(WF_CONST_LANG.COMPARE_METHOD_ALERT)
        }
    } catch (e) {
        alert(WF_CONST_LANG.FORMULA_ERROR);
        return false
    }
}

function wfSubDocEndSave(d, f) {
    var g = $.parseXML("<" + gCurNode[0].nodeName + "/>").documentElement,
        strIdea = "",
        bCloneNode = true;
    g.setAttribute("tache", getNodeValue(gCurNode[0], "WFNodeName"));
    g.setAttribute("user", gUserCName);
    g.setAttribute("time", gServerTime);
    if (d) {
        log("是否为多人审批过程: ", d ? "YES" : "NO");
        if (gForm.WFTacheName.value == WF_CONST_LANG.WORKFLOW_END) {
            gAction = gUserCName + WF_CONST_LANG.USE_ACTION
        } else {
            gAction = gUserCName + WF_CONST_LANG.ACTION_TO + gForm.CurUser.value + WF_CONST_LANG.SYMBOL_END
        }
    }
    g.setAttribute("action", gAction);
    g.setAttribute("mark", f);
    log("所有意见ID标识:" + gIdeaID);
    if (gIdeaID.length > 0) {
        $.each(gIdeaID, function (i, b) {
            var c = g;
            c.setAttribute("id", b);
            $.each($('[name=\"' + b + '\"]', gForm), function (i, a) {
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
    if ("" !== XML2String(gWFLogXML)) {
        gForm.WFFlowLogXML.value = XML2String(gWFLogXML)
    }
    log("当前处理人: ", gUserCName);
    ClearRepeat("AllUser", gUserCName);
    fnResumeDisabled();
    log("提交代理名称: ", gWQSagent);
    gForm["$$QuerySaveAgent"].value = gWQSagent;
    var h = gPageEvent["SaveAfter"];
    if (h.replace(/\s/, "") != "") {
        try {
            new Function(h)()
        } catch (e) {
            alert(WF_CONST_LANG.SAVE_AFTER + "< " + h + " > " + WF_CONST_LANG.PAGE_NO_INIT)
        }
    }
    if (d) {
        var j = gForm.WFRouterID.value,
            arrID = $.trim(j) == "" ? [] : j.split(";");
        if (arrID.length > 0) {
            var k = $(arrID[arrID.length - 1], gWFProcessXML);
            if (k.length > 0) {
                try {
                    if (getNodeValue(k[0], "WFRtxEnabled") == WF_CONST_LANG.YES) {
                        var l = "/" + gCurDB + "/vwComOpenDoc/" + gDocKey + ".?OpenDocument",
                            msg = wfMsgContent(getNodeValue(k[0], "WFRtxContent"));
                        log("RTX.link: ", l);
                        log("RTX.msg: ", msg);
                        var m = "";
                        if (typeof gForm.WFModule != "undefined") {
                            m = gForm.WFModule.value
                        }
                        log("RTX.title: ", m);
                        var n = "",
                            args = "&L=" + l + "&M=" + msg + "&T=" + m;
                        if (getNodeValue(k[0], "WFAllObject") == WF_CONST_LANG.YES) {
                            n = "all"
                        } else if (getNodeValue(k[0], "WFAllReadUsers") == WF_CONST_LANG.YES) {
                            n = gForm.AllUser.value.split(";").concat(gForm.CurUser.value.split(";")).join(",")
                        } else if (getNodeValue(k[0], "WFOnlyInitiator") == WF_CONST_LANG.YES) {
                            n = gForm.WFInitiator.value.split(";").concat(gForm.CurUser.value.split(";")).join(",")
                        } else {
                            n = gForm.CurUser.value.split(";").join(",")
                        }
                        log("RTX.user: ", n);
                        args += "&U=" + n;
                        if (!gWebDebug) {
                            $.ajax({
                                url: encodeURI("/" + gCommonDB + "/(agtWFRTX)?OpenAgent" + args),
                                cache: false,
                                async: false,
                                success: function (a) {}
                            })
                        }
                    }
                } catch (e) {
                    log("RTX发送异常")
                }
            }
        }
    }
    log("----页面提交结束----");
    setTimeout(function () {
        var a = mini.get('oWinDlg');
        if (typeof a != "undefined") {
            a.destroy()
        }
        a = mini.get('oCheckUserDlg');
        if (typeof a != "undefined") {
            a.destroy()
        }
        mini.unmask(document.body)
    }, 1000);
    if (gWebDebug) {
        gForm.WFCurNodeID.value = gForm.WFPreNodeID.value;
        gForm.CurUser.value = gForm.WFPreUser.value
    } else {
        gForm.submit()
    }
}

function wfMsgContent(b) {
    var c = $.trim(b);
    if (c != "") {
        var d = (c.match(/\{.*?}/ig));
        $.each(d, function (i, a) {
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
        $.each(c, function (i, a) {
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
                success: function (b) {
                    b = b.replace(/\s/g, "");
                    if (b != "") {
                        if (d == "") {
                            arrT = b.split("^")
                        } else {
                            $.each(b.split("^"), function (i, a) {
                                try {
                                    _tmp = new Function("return (" + a + ")")();
                                    if ($.type(_tmp) == "array") {
                                        arrT = arrT.concat(_tmp)
                                    } else {
                                        alert(WF_CONST_LANG.WEB_FORMULA_ERROR);
                                        return
                                    }
                                } catch (e) {}
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
        var b = "/" + gWorkFlowDB + "/htmWFViewWindow?ReadForm&db=" + gCurDBName + "&id=" + gCurDocID + "&cu=" + encodeURI(gForm.CurUser.value) + "&status=" + gWFStatus + "&gWFTacheName=" + encodeURI(gForm.WFTacheName.value);
        window.open(b, "newwindow", a)
    }
}

function wfStop() {
    if (!gIsNewDoc) {
        if (confirm(WF_CONST_LANG.CONFIRM_OPERATION)) {
            $.ajax({
                url: encodeURI("/" + gCommonDB + "/agtWFStop?OpenAgent&DocID=" + gCurDocID + "&TDB=" + gCurDBName + "&User=" + gUserCName),
                dataType: "text",
                success: function (a) {
                    var b = parseInt(a, 10);
                    if (b == 0) {
                        alert(WF_CONST_LANG.STOP_ERROR_0)
                    } else if (b == 1) {
                        alert(WF_CONST_LANG.STOP_ERROR_1);
                        window.location.reload()
                    } else if (b == 2) {
                        alert(WF_CONST_LANG.STOP_ERROR_2)
                    } else if (b == 3) {
                        alert(WF_CONST_LANG.STOP_ERROR_3)
                    } else {
                        alert(WF_CONST_LANG.STOP_ERROR_4)
                    }
                }
            })
        }
    } else {
        alert(WF_CONST_LANG.STOP_ERROR)
    }
}

function wfAddToolbar(a) {
    var b = [{
        name: WF_CONST_LANG.SEARCH,
        ico: "icon-search",
        clickEvent: "wfOrgSearch()",
        align: "1",
        isHidden: "1",
        id: "wfS"
    }, {
        name: WF_CONST_LANG.REFRESH,
        ico: "icon-reload",
        clickEvent: "wfOrgSearch(true)",
        align: "1",
        isHidden: "1",
        id: "wfRe"
    }, {
        name: WF_CONST_LANG.CANCEL,
        ico: "icon-cancel",
        clickEvent: "mini.get(\'" + a + "\').destroy();gForm.WFStatus.value=gWFStatus;gForm.WFTacheName.value='';",
        align: "2",
        isHidden: "0"
    }, {
        name: WF_CONST_LANG.OK,
        ico: "icon-ok",
        clickEvent: "wfDlgBtnSave()",
        align: "2",
        isHidden: "0"
    }];
    $("<input type='text' class='mini-textbox' id='searchValue' visible=false />").appendTo("#SubmitDocActionBar");
    var c = "";
    for (var i = 0; i < b.length; i++) {
        c = "<a class='mini-button' id='" + (b[i].id ? b[i].id : "") + "' style='" + (b[i].align == "1" ? "" : "float:right") + "' plain=true visible=" + (b[i].isHidden == "1" ? false : true) + " iconCls='" + b[i].ico + "'>" + b[i].name + "</a>";
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
        c.filter(function (a) {
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
    if (e.tab.name == "OrgTree") {
        var a = mini.get("orgTree");
        if (typeof mini.get("orgTree") != "undefined" && a.getData().length == 0) {
            loadOrgTree()
        }
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

function XML2String(a) {
    try {
        return (typeof XMLSerializer !== "undefined") ? (new window.XMLSerializer()).serializeToString(a) : a.xml
    } catch (e) {
        return ""
    }
}

function loadOrgTree() {
    var b = mini.get("orgTree");
    var c = mini.get('oWinDlg');
    mini.mask({
        el: document.body,
        cls: 'mini-mask-loading',
        html: WF_CONST_LANG.LIST_LOADING
    });
    $.ajax({
        url: encodeURI("/" + gOrgDB + "/vwPersonTreeJson?OpenView&Count=9999&ExpandAll"),
        cache: false,
        success: function (a) {
            if (a.indexOf(",") > -1) {
                b.loadList(new Function("return [" + a.substr(1) + "]")(), "id", "pid");
                mini.unmask(document.body)
            }
        }
    })
}

function treeNodeClick(e) {
    if (e.node.isdept != "1") {
        AddValue(e.node.name, "selectList")
    }
}

function listNodeClick(e) {
    AddValue(e.sender.getSelected().name, "selectList")
}

function log() {
    if (typeof gWebDebug == 'undefined') {
        gWebDebug = 0
    }
    if (gWebDebug == 0) {
        return
    }
    var a = '[ht.workflow] ' + Array.prototype.join.call(arguments, '');
    if (window.console) {
        if (window.console.debug) {
            window.console.debug(a)
        } else {
            window.console.log(a)
        }
    } else if (window.opera && window.opera.postError) {
        window.opera.postError(a)
    }
}