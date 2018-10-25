var gEditor=null,gWFProcessXML=null,gRulesXML=null;
function Application(config){
	try{
		if (!Client.isBrowserSupported()){
			Utils.alert('\u5F53\u524D\u6D4F\u89C8\u5668\u4E0D\u652F\u6301\uFF01');//��ǰ�������֧�֣�
		}else{
			var node = Utils.load(config).getDocumentElement();
			var editor = new Editor(node);
			editor.dblClickAction="showProperties";
		}
	}catch (e){
		Utils.alert('\u4E0D\u80FD\u88C5\u8F7D\u6D41\u7A0B: '+e.message);//����װ�����̣�
		throw e;
	};
	return editor;
};
function repXMl(xmlStr){
	return $.parseXML(xmlStr.replace(/[\r\n\t]/g,"").replace(/\&/g, "&amp;"));
}
function onInit(editor){
	gWFProcessXML=$(repXMl($("#WFProcessXML").val())).find("Process");
	$.ajax({
		url: BasePath + "/config/rules.xml",
		cache: false,
		dataType: "xml",
		success: function(oXML) {
			gRulesXML = oXML
		}
	});
	gEditor=editor;
	editor.graph.setTooltips(false);
	ConnectionHandler.prototype.connectImage = new Image(BasePath+'/images/connector.gif',16,16);
	GraphHandler.prototype.guidesEnabled = true;

	editor.graph.setConnectable(true);
	var _btn=function(icon,title,action,area){		
		$("<img src='"+BasePath + "/images/" + icon + ".gif' title='"+title+"'/>").appendTo("#"+area).addClass("ToolbarMode").bind("click",{},action);
	};
	if(gbIsCreateNew){
		_btn("New","\u65B0\u5EFA\u6D41\u7A0B",window.OpenWF,"basicActions");//�½�����
	}
	_btn("Save","\u4FDD\u5B58\u6587\u6863",window.SaveWF,"basicActions");//�����ĵ�

	_btn("Grid","\u7F51\u683C\u7EBF",window.showGrid,"mainActions");//�����

	var _factory = function(editor){return function(){wfRemoveCheck(editor.graph)}};
	_btn("Delete","\u5220\u9664\u8282\u70B9",_factory(editor),"mainActions");//ɾ��ڵ�
	
	var _factory = function(editor,name){return function(){editor.execute(name)}};
	_btn("SelectAll","\u9009\u62E9\u6240\u6709\u8282\u70B9",_factory(editor,"selectAll"),"mainActions");//ѡ�����нڵ�

	_btn("SelectNone","\u53D6\u6D88\u5DF2\u9009\u8282\u70B9",_factory(editor,"selectNone"),"mainActions");	//ȡ����ѡ�ڵ�
	
	_btn=_factory=null;

	//��ȡ�Ѵ��ڵ�����ͼ
	var textNode = $('#GraphXML');
	var doc = Utils.parseXml($('#WFGraphXML').val().replace(/[\r\n]/g,"").replace(/@line@/g,"&#xa;"));

	var dec = new Codec(doc);
	dec.decode(doc.documentElement,editor.graph.getModel());
	
	if(Client.IS_IE){document.selection.empty()}
	
	var sourceCK = mini.get("#source");
	sourceCK.setChecked(false);
	var graphNode = editor.graph.container;
	var funct=function(editor){
		if(sourceCK.getChecked()){
			graphNode.style.display='none';
			textNode.css("display", 'inline');
			var enc = new Codec(Utils.createXmlDocument());
			var node = enc.encode(editor.graph.getModel());
			
			textNode.val(Utils.getPrettyXml(node));
			textNode.attr("originalValue",textNode.val());
			textNode.focus();
			
			$("#toolbar","#mainActions").css("display","none");
		}else{
			graphNode.style.display = '';
			if (textNode.val()!=textNode.attr("originalValue")){
				var doc = Utils.parseXml(textNode.val());
				var dec = new Codec(doc);
				dec.decode(doc.documentElement, editor.graph.getModel());
			}
			textNode.attr("originalValue",null);
			if (Client.IS_IE){document.selection.empty()}
			
			textNode.css("display", 'none');
			textNode.blur();
			editor.graph.container.focus();
			
			$("#toolbar","#mainActions").css("display","none");
		}
	};
	editor.addAction('switchView',funct);
	sourceCK.on('click',function(){editor.execute('switchView')});
	$("#graph").css("visibility","visible");
};
function showGrid(){
	$("#graph").css("backgroundImage", $("#graph").css("backgroundImage") == "none" ? 'url(' + BasePath + '/images/bgGraph.gif)': '')
};
function wfInitNode(cell){
	if (mini.get('nodeDlg')) {
		var cellStyle=cell.getStyle(),cellId=cell.getId();
		mini.get('WFNodeId').setValue(cellId);
		mini.get('WFNodeName').setValue(gEditor.graph.getLabel(cell)==undefined?"":gEditor.graph.getLabel(cell));
		
		cellStyle=cell.isEdge()?"Router":cellStyle;
		
		var menuId=$("input[name='menuId']").val();
		
		if(cellStyle!="Swimlane"){
			var nNode=$(cellId,gWFProcessXML);
			if(nNode.length>0){
				var arrRules=$(cellStyle + ">*",gRulesXML);
				if(arrRules.length>0){
					$.each(arrRules,function(idxRule,itemRule){
						try{
							var _itemname=itemRule.nodeName,tmpNode=$(cellId+">"+_itemname,gWFProcessXML);
							if(tmpNode.length>0){
								var objItem=mini.get(_itemname),tmpValue=$(tmpNode).attr("value");
								if(typeof tmpValue!="undefined" && tmpValue!=""){
									switch (_itemname){
										case "WFActivityOwner":
											var obj=mini.decode(tmpValue);
											mini.get("defaultRole").setData(obj.R);
											mini.get("defaultPerson").setData(obj.P);
											mini.get("defaultFormula").setData(obj.F);
											break;
										case "WFFieldStatus":
											var fieldGrid=mini.get("fieldStatus");
											fieldGrid.setUrl(gFieldStatusURL+"?menuId="+menuId);
											fieldGrid.on("load",function(e){
												$.each(mini.decode(tmpValue),function(i,item){
													var _row=fieldGrid.findRow(function(row){
													    if(row.id == item.id) return true;
													});
													if(typeof(_row)!="undefined"){
														delete _row.status;
														delete _row.tip;
														delete item.datatype;
														delete item.maxLength;
														delete item.minLength;
														fieldGrid.updateRow(_row,item);
													}
												});
											})
											fieldGrid.load();
											break;
										case "WFBtnAssign":
											var btnGrid=mini.get("btnAssign");
											btnGrid.setUrl(gBtnURL);
											btnGrid.load({},function(){
												$.each(mini.decode(tmpValue),function(i,item){
													var _row = btnGrid.findRow(function(row){
													    if(row.name == item.name) return true;
													});
													item.status=1;
													if(typeof(_row)!="undefined"){
														btnGrid.updateRow(_row,item);
													}else{
														btnGrid.addRow(item);
													}
												});
											});
											break;
										default:
											if(objItem){
												objItem.setValue(tmpValue.replace(/@line@/g,"\n").toString());
											}
									}
								}
							}
						}catch(e){
							//console.log("--"+itemRule.nodeName);
						}
					});
					
					if(cellStyle=="Basic"){
						if(mini.get("WFApproveStyle").getValue()=="0"){
							mini.get("WFSequenceApprove").setEnabled(false);
							mini.get("WFApproveNum").setEnabled(false)
						}else{
							mini.get("WFSequenceApprove").setEnabled(true);
							mini.get("WFApproveNum").setEnabled(true)		
						}
					}else if(cellStyle=="Router"){
						switch (mini.get("WFRelationType").getValue()){
							case "0":
								mini.get("WFCondition").setEnabled(false);
								mini.get("WFTacheNameSelect").setEnabled(false);			
								break;
							case "1":
								mini.get("WFCondition").setEnabled(false);
								mini.get("WFTacheNameSelect").setEnabled(true);
								break;
							default:
								mini.get("WFCondition").setEnabled(true);
								mini.get("WFTacheNameSelect").setEnabled(false);
						}
					}
				}
			}else{
				var fieldGrid=mini.get("fieldStatus");
				if(typeof fieldGrid!="undefined"){
					fieldGrid.setUrl(gFieldStatusURL+"?menuId="+menuId);
					fieldGrid.load();	
				}
				var btnGrid=mini.get("btnAssign");
				if(typeof btnGrid!="undefined"){
					btnGrid.setUrl(gBtnURL);
					btnGrid.load();
				}
			}
			mini.parse();
		}
	}
};
function wfSaveNode(cell){
	if(mini.get('nodeDlg')){
		var nodevalue=mini.get('WFNodeName').getValue(),cellId=cell.getId(),cellStyle=cell.isEdge()?"Router":cell.getStyle();
		if(cellStyle!="Swimlane"){
			var nNode=$(cellId,gWFProcessXML),arrRules=$(cellStyle+">*",gRulesXML);
			
			if(arrRules.length>0){
				var isRW=nNode.length<1;
				var _fnRule=function(arrRules,objNode){
					$.each(arrRules,function(idx,item){
						try{
							var _itemname=item.nodeName,_itemnode=$(_itemname,objNode),isRW=_itemnode.length<1,tmpNode=isRW?$($.parseXML("<"+_itemname+"/>")).find(_itemname):_itemnode;
							var objItem=mini.get(_itemname),tmpValue="";
							switch (_itemname){
								case "WFActivityOwner":
									var _listRole=$.each(mini.get("defaultRole").getData(),function(i,item){
										for(o in item){
											if(o.indexOf("_")>-1){delete item[o]}
										}
									});
									var _listPerson=$.each(mini.get("defaultPerson").getData(),function(i,item){
										for(o in item){
											if(o.indexOf("_")>-1){delete item[o]}
										}
									});
									var _listFormula=$.each(mini.get("defaultFormula").getData(),function(i,item){
										for(o in item){
											if(o.indexOf("_")>-1){delete item[o]}
										}
									});
									var obj={"R":_listRole,"P":_listPerson,"F":_listFormula};
									tmpValue=mini.encode(obj);
									break;
								case "WFFieldStatus":
									//�ɱ༭^e|ֻ��^r|����^h|����^w|�����ֿɼ�^s|���ֿɼ����^m
									
									var _list=$.grep(mini.get("fieldStatus").getData(),function(item){
										//var bR=!((typeof item.status=="undefined"||item.status=="edit")&&item.id.indexOf("ID_")==-1);
										//if(bR){
											for(o in item){
												if(o.indexOf("_")>-1){delete item[o]}
											}
										//}
										return true;
									});
									tmpValue=mini.encode(_list);
									break;
								case "WFBtnAssign":
									var _list=$.grep(mini.get("btnAssign").getData(),function(item){
										var bR=!(typeof item.status=="undefined"||item.status==0);
										if(bR){
											for(o in item){
												if(o.indexOf("_")>-1||o=="status"){delete item[o]}
											}
										}
										return bR
									});
									tmpValue=mini.encode(_list);
									break;
								default:
									if(objItem){
										tmpValue=objItem.getValue();
									}
							}
							$(tmpNode).attr("value",tmpValue.replace(/["]/g,"'").replace(/\n/g,"@line@"));
						}catch(e){
							//console.log("--"+item.nodeName);
						}
						if(isRW){if(tmpValue!=""){$(objNode).append(tmpNode)}}
					});	
				};
				var objNode = isRW?$($.parseXML("<"+cellId+"/>")).find(cellId):$(cellId,gWFProcessXML);
				
				if(cell.isEdge()){
					var sNodeId=cell.getTerminal(true).getId(),tNodeId=cell.getTerminal(false).getId();
					$(objNode).attr("source",sNodeId);
					$(objNode).attr("target",tNodeId);
				}
				$(objNode).attr("nType",cellStyle);
				
				if(isRW){$(gWFProcessXML).append(objNode)}
				
				_fnRule(arrRules,objNode);
			}
			//console.log(gWFProcessXML[0]);
		}
	}
}
function wfLine(editor,style){
	$.each(editor.graph.getSelectionCells(),function(idx,cell){
		if(cell!= null&&editor.graph.model.isEdge(cell)){
			editor.graph.setCellStyle(style,[cell])
		}
	});
}
//��ã�\n\n��ȷ����Ҫɾ����ѡ����\n\nע�⣺ɾ��󽫲����ٻָ��������������������
function wfRemoveCheck(graph){if(confirm("\u60A8\u597D\uFF1A\n\n\u60A8\u786E\u4FE1\u9700\u8981\u5220\u9664\u6240\u9009\u5BF9\u8C61\uFF1F\n\n\u6CE8\u610F\uFF1A\u5220\u9664\u540E\u5C06\u4E0D\u53EF\u518D\u6062\u590D\uFF01\u8BF7\u60A8\u8C28\u614E\u64CD\u4F5C\uFF01\uFF01\uFF01")){graph.removeCells()}}
function wfCreateNode(editor,vertex,evt,target,style){
	var graph = editor.graph;
	if(graph.canImportCell(vertex)){
		var pt = Utils.convertPoint(graph.container,evt.clientX,evt.clientY);
		graph.model.prefix="",bS=style=="S",bE=style=="E";
		if(bS&&graph.model.cells["S"]){
			//�����?��һ��<��ʼ�ڵ�>��
			Utils.alert("\u4EC5\u5141\u8BB8\u521B\u5EFA\u4E00\u4E2A<\u5F00\u59CB\u8282\u70B9>\uFF01");return
		}else{
			if(bS){
				vertex.setId("S")
			}else if(bE){
				vertex.setId(style+(new Date()).getTime())
			}
		}
		graph.model.prefix="N";
		return editor.addVertex(target,vertex,pt.x,pt.y)
	}
}
function wfRemoveNode(cell){
	var nNode=$(cell.getId(),gWFProcessXML);
	if(nNode.length>0){nNode.empty()}
}
Utils.alert=function(err){
	try{
		if(mini){
			var tmperr=err.replace(/\n/g,"<br/>");
			mini.alert(tmperr);
		}
	}catch(e){
		alert(err);
	}
};
Editor.prototype.showProperties = function(cell) {
    cell = cell || this.graph.getSelectionCell();
    if (cell == null) {
        cell = this.graph.getCurrentRoot();
        if (cell == null) {
            cell = this.graph.getModel().getRoot();
        }
    }
	if(cell!=null&&this.graph.isEnabled()){			
		var tmpS=cell.getStyle()==null?"":cell.getStyle().split(";")[0],isOrgin=cell.isEdge()&&cell.getTerminal(true).getId()=="S"?true:false;
		
		if(tmpS=="Start"||tmpS=="End"||isOrgin){
			Utils.alert("\u8BE5\u8282\u70B9\u65E0\u9700\u7F16\u8F91\uFF01\n");//�ýڵ�����༭��
		}else{
			if($("input[name='menuId']").val()==""){
				Utils.alert("\u8bf7\u9009\u62e9\u6d41\u7a0b\u7c7b\u522b\n");
				return;
			}
			var oWinDlg=mini.get('nodeDlg');
			if(oWinDlg==null){
				oWinDlg = new mini.Window();
				oWinDlg.set({
					title: "\u8282\u70b9\u914d\u7f6e",
					id:"nodeDlg",
					allowDrag:true,
					allowResize:false,
					showModal: true,
					enableDragProxy:false,
					showFooter:true,
					showCloseButton:false,
					headerStyle:"font-weight:bold",
					footerStyle:"padding:5px",
					width: 510,
					height: 476
				});
			};
			var tdom='<table style="border:0;width:100%"><tr><td align="right"><a id="btnSave" class="mini-button" plain=true iconCls="icon-ok">\u786e\u5b9a</a><a id="btnCancel" class="mini-button" plain=true iconCls="icon-cancel">\u53d6\u6d88</a></td></tr></table>'
			oWinDlg.setFooter(tdom);
			mini.get('btnSave').on("click",function(){
				wfSaveNode(cell);
				gEditor.graph.labelChanged(cell, mini.get('WFNodeName').getValue());
				mini.get("nodeDlg").destroy()
			});
			mini.get('btnCancel').on("click",function(){
				gEditor.graph.labelChanged(cell, mini.get('WFNodeName').getValue());
				mini.get("nodeDlg").destroy()
			});
			$.ajax({
				url: BasePath + "/config/" + (cell.isEdge() ? "Router": tmpS) + ".html",
				cache: false,
				dataType: "text",
				async: false,
				success: function(bdom) {
					oWinDlg.setBody(bdom);
					mini.parse();
					oWinDlg.show();
					setTimeout(function(){
						wfInitNode(cell);
					},10);
				}
			});
		}
	}
};
Cell.prototype.remove = function(index) {
    var child = null;
    if (this.children != null && index >= 0) {
        child = this.getChildAt(index);
        if (child != null){
			this.children.splice(index,1);
            child.setParent(null);
			wfRemoveNode(child);//ɾ��ڵ㴦
        }
    }
    return child;
};
function getPrettyXml(node, tab, indent) {
	var result = [];
	if (node != null) {
		tab = tab || '  ';
		indent = indent || '';
		if (node.nodeType == Constants.NODETYPE_TEXT) {
			result.push(node.nodeValue)
		} else {
			result.push(indent + '<' + node.nodeName);
			var attrs = node.attributes;
			if (attrs != null) {
				for (var i = 0; i < attrs.length; i++) {
					var val = htmlEntities(attrs[i].nodeValue,"@line@");
					result.push(' ' + attrs[i].nodeName + '="' + val + '"')
				}
			}
			var tmp = node.firstChild;
			if (tmp != null) {
				result.push('>\n');
				while (tmp != null) {
					result.push(getPrettyXml(tmp, tab, indent + tab));
					tmp = tmp.nextSibling
				}
				result.push(indent + '</' + node.nodeName + '>\n')
			} else {
				result.push('/>\n')
			}
		}
	}
	return result.join('')
}
function htmlEntities(s, newline) {
	s = s || '';
	s = s.replace(/&/g, '&amp;');
	s = s.replace(/"/g, '&quot;');
	s = s.replace(/\'/g, '&#39;');
	s = s.replace(/</g, '&lt;');
	s = s.replace(/>/g, '&gt;');
	if (newline == null) {
		s = s.replace(/\n/g, '&#xa;')
	}else{
		s = s.replace(/\n/g, newline)
	}
	return s
}
//
function fnRelationType(){
	switch (this.getValue()){
		case "0":
			mini.get("WFCondition").setEnabled(false);
			mini.get("WFTacheNameSelect").setEnabled(false);			
			break;
		case "1":
			mini.get("WFCondition").setEnabled(false);
			mini.get("WFTacheNameSelect").setEnabled(true);
			break;
		default:
			mini.get("WFCondition").setEnabled(true);
			mini.get("WFTacheNameSelect").setEnabled(false);
	}
}
//�ֶο���
function fnApproveStyle(){
	if(this.getValue()=="0"){
		mini.get("WFSequenceApprove").setEnabled(false);
		mini.get("WFApproveNum").setEnabled(false)
	}else{
		mini.get("WFSequenceApprove").setEnabled(true);
		mini.get("WFApproveNum").setEnabled(true)		
	}
}
//��ɫѡ��
function setItem(e) {
	var listbox = e.sender;
	var defaultPerson = mini.get("defaultPerson");
	var defaultRole = mini.get("defaultRole");
	var defaultFormula = mini.get("defaultFormula");
	
	if(listbox.id=="OrgTree"){
		var node = e.node;
		if(node.type=="user"){
			defaultPerson.addItem({id:node.id,name:node.text})
		}
	}else if (listbox.id == "RoleList") {
		if (defaultRole.findRows(function(row){if(row.id==e.item.text) return true}) == 0) {
			defaultRole.addRow ({id:e.item.id,name:e.item.text,js:"0"})
		}
	}else if (listbox.id == "FarmulaList") {
		if (defaultFormula.findRows(function(row){if(row.id==e.item.text) return true}) == 0) {
			defaultFormula.addRow ({id:e.item.id,name:e.item.text,address:e.item.address})
		}
	}else if (listbox.id == "defaultPerson") {
		defaultPerson.removeItems(defaultPerson.findItems(listbox.getValue()));
	} else if (listbox.id == "defaultRole" ) {
		defaultRole.removeRow(defaultRole.getSelected())
	}else if (listbox.id == "defaultFormula" ) {
		defaultFormula.removeRow(defaultFormula.getSelected())
	}
}
//
function OnCellBeginEdit(e) {
    var record = e.record, field = e.field;
    if (field == "tip" && record.status != "must") {
        e.cancel = true;    //���Ǳ���ʱ��������༭�Ա�
    }
}
//�����ֶ�����
function goCopy(){
	var arrData=mini.clone(mini.get("fieldStatus").getData());
	var _list=$.grep(arrData,function(item){
		var bR=!(typeof item.status=="undefined"||item.status=="edit");
		if(bR){
			for(o in item){
				if(o.indexOf("_")>-1||o=="name"){delete item[o]}
			}
		}
		return bR
	});
	$("body").data("fieldStatus",_list);

    mini.showTips({content: "<b>\u6210\u529f</b> <br/>\u6570\u636e\u590d\u5236\u6210\u529f",state: "success"});
}
//ճ���ֶ�����
function goPaste(){
	var obj=$("body").data("fieldStatus");
	if(obj!=undefined){
		var fieldGrid=mini.get("fieldStatus");
		$.each(obj,function(i,item){
			var _row=fieldGrid.findRow(function(row){
			    if(row.id == item.id) return true;
			});
			if(typeof(_row)!="undefined"){
				fieldGrid.updateRow(_row,item);
			}
		});
		mini.showTips({content: "<b>\u6210\u529f</b> <br/>\u6570\u636e\u7c98\u8d34\u6210\u529f",state: "success"});
	}
}
function OpenWF() {
	window.location = gDir+"/workflow/workflow.jsp";
}
function DelWF(id) {
	 mini.confirm("确定删除该流程吗？", "确定？", function(action) {
		if (action == "ok") {
			window.location = gDir + "/admin/delWorkFlow.action?wfId=" + id;
		}
	});
	
}
function SaveWF() {
	var a = gEditor.graph.model,
	edge = a.getOutgoingEdges(a.cells["S"]);
	if (edge == "") {
		Utils.alert("\u6CA1\u6709\u5F00\u59CB\u8282\u70B9\u6216\u7ED3\u675F\u8282\u70B9");
		return
	};
	if (edge != null) {
		$(gWFProcessXML).attr({"OriginNode":edge[0].getTerminal(false).getId(),"OriginRouter":edge[0].getId()});
	};
	var b = new Codec(Utils.createXmlDocument());
	var c = b.encode(gEditor.graph.getModel());
	$('#WFGraphXML').val( getPrettyXml(c) );
	$('#WFProcessXML').val( $('#WFGraphXML').val() == '<GraphModel><root><Cell id="0"/><Cell id="1" parent="0"/></root></GraphModel>' ? "<Process/>": Utils.getPrettyXml(gWFProcessXML[0]).replace(/&#39;/g,"'"));
    
	var form = new mini.Form("#fmWorkFlow");
	form.validate();
    if (form.isValid() == false) return;
	document.forms[0].submit()
}