function OpenWF(){
	window.location="/"+gCurDB+"/fmWorKFlow?OpenForm";
}
function SaveWF(){
	if(gbIsCreateNew){
		if(dojo.byId("WFType").value==""){alert("\u8BF7\u586B\u5199\u6D41\u7A0B\u7C7B\u522B\uFF01");return}//请填写流程类别！
		if(dojo.byId("WFModule").value==""){alert("\u8BF7\u9009\u62E9\u6D41\u7A0B\u6A21\u5757\uFF01");return}//请选择流程模块！
		if(dojo.byId("WFName").value==""){alert("\u8BF7\u586B\u5199\u6D41\u7A0B\u540D\u79F0\uFF01");return}//请填写流程名称！
	}
	var model=gEditor.graph.model,edge=model.getOutgoingEdges(model.cells["S"]);
	if(edge==""){Utils.alert("\u6CA1\u6709\u5F00\u59CB\u8282\u70B9\u6216\u7ED3\u675F\u8282\u70B9");return};//没有开始节点或结束节点
	if(edge!=null){
		with(gWFProcessXML){
			setAttribute("OriginNode",edge[0].getTerminal(false).getId());
			setAttribute("OriginRouter",edge[0].getId());
		}
	};
	var enc = new Codec(Utils.createXmlDocument());
	var node = enc.encode(gEditor.graph.getModel());
	dojo.byId('WFGraphXML').value = getPrettyXml(node);
	dojo.byId('WFProcessXML').value = dojo.byId('WFGraphXML').value=='<GraphModel><root><Cell id="0"/><Cell id="1" parent="0"/></root></GraphModel>'?"<Process/>":getPrettyXml(gWFProcessXML);
	var gForm=document.forms[0];
	if(gbIsCreateNew){
		dojo.place("WFType",gForm);
		dojo.place("WFModule",gForm);
		dojo.place("WFName",gForm);
		dojo.place("WFStatus",gForm);
		dojo.place("WFVersion",gForm);
		dojo.place("WFMainForm",gForm);
	}
	gForm.submit();
}
function goSelectCurUser(){
	var oWinDlg=dijit.byId('oWinDlg');
	if(oWinDlg==null){
		//选择处理人
		oWinDlg = new dijit.Dialog({title:"\u9009\u62E9\u5904\u7406\u4EBA",id:"oWinDlg",onDownloadEnd:function(){fnAlternateSelectValue("WFActivityOwner","WFempTarget",false,"oWinDlg")},onHide:function(){this.destroyDescendants()}});
		dojo.body().appendChild(oWinDlg.domNode);
	}
	oWinDlg.set("href","/"+gCurDB+"/htmWFSelectUser?ReadForm");
	oWinDlg.show();	
}
function goSelectReceiveUser(){
	var oWinDlg=dijit.byId('oWinDlg');
	if(oWinDlg==null){
		//选择处理人
		oWinDlg = new dijit.Dialog({title:"\u9009\u62E9\u5904\u7406\u4EBA",id:"oWinDlg",onDownloadEnd:function(){fnAlternateSelectValue("WFReceiveObject","WFempTarget",false,"oWinDlg")},onHide:function(){this.destroyDescendants()}});
		dojo.body().appendChild(oWinDlg.domNode);
	}
	oWinDlg.set("href","/"+gCurDB+"/htmWFSelectReceiveUser?ReadForm");
	oWinDlg.show();	
}
function goTargetWF(id){
	id=id.indexOf("input")>0?id:id+"input";
	var obj=dojo.byId(id);
	if(obj.options.length<2){
		for(var intM=0,intL=gTargetWF.length;intM<intL;intM++){
			var _op=new Option(gTargetWF[intM],gTargetWF[intM]);
			obj.add(_op);	
		}
	}
}
function goChangeTargetWF(obj){
	dojo.byId(obj.id.replace("input","")).value=obj.value
}