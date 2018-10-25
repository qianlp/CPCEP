var gObjField=null,gSeparator="";
function fnSelectOrg(json){
	var type=json.type||"P",field=json.field||"",tit=json.title||"";
	gSeparator=json.sep||"";
	/*
	type:P表示人员
	type:D表示部门
	type:HD表示层次部门
	type:H_D表示带下划线层次部门
	*/
	if(field==""){
		alert("请定义你需要存放的域名！");return
	}
	if(typeof field=="string"){				gObjField=mini.get(field)?mini.get(field):(document.getElementsByName(field).length?document.forms[0][field]:document.getElementById(field));
	}else{
		gObjField=field;
	}
	var oWinDlg=mini.get('oWinDlg');
	if(oWinDlg==null){
		oWinDlg = new mini.Window();
		oWinDlg.set({
						title:"组织机构信息",
						id:"oWinDlg",
						url:encodeURI('/OA6.0/HT_Common.nsf/htmOrgSelect?readform&T='+type+"&A="+tit+"&sep="+gSeparator),
						allowDrag:false,
						allowResize:false,
						showModal: true,
						enableDragProxy:false,
						showFooter:true,
						showCloseButton:false,
						headerStyle:"font-weight:bold",
						footerStyle:"padding:5px",
						width: 510,
						height: 476,
						onload:function(){
							var ifr=mini.get('oWinDlg').getIFrameEl().contentWindow;
							var lst=ifr.mini.get('listBox');
							if(gObjField.enabled){
								var arrUser=(gSeparator=="")?[gObjField.getValue()]:gObjField.getValue().split(gSeparator);
							}else{
								var arrUser=(gSeparator=="")?[gObjField.value]:gObjField.value.split(gSeparator);
							}
							if(arrUser[0]==""){return;}
							var arrObj=[];
							var temp={};
							for(var i=0;i<arrUser.length;i++){
								temp.name=temp.id=arrUser[i];
								arrObj.push(temp);
								temp={};
							}
							lst.addItems(arrObj);
						}
				});
	}
	oWinDlg.show();
	var tdom='<table cellpadding="0" cellspacing="0" style="border:0; width:100%"><tr><td style="text-align:left"><input id="key" class="mini-textbox" style="margin-top:2px;width:150px;float:left" onenter="search()"/><a class="mini-button"  plain=true iconCls="icon-search" onclick="search()">查询</a><a class="mini-button"  plain=true iconCls="icon-reload" onclick="search(true)">刷新</a></td><td style="text-align:right"><a id="btnSure" class="mini-button" plain=true iconCls="icon-ok">确定</a><a class="mini-button" plain=true iconCls="icon-cancel" onclick="mini.get(\'oWinDlg\').destroy()">取消</a></td></tr></table>'
	setTimeout(function(){
		oWinDlg.setFooter(tdom);
		mini.get('btnSure').on("click",function(){getNodeListValue(json)});
	},10);
}
function getNodeListValue(json){
	var ifr=mini.get('oWinDlg').getIFrameEl().contentWindow;
	var lst=ifr.mini.get('listBox');
	if(gSeparator==""&&lst.data.length>1){
		alert("仅允许选择一项！");return;
	}
	var ck=json.f||"";
	var arrUser=$.map(lst.data,function(e){return e.name;});    
	try{
		if(gObjField.enabled){
			gObjField.set({'value':arrUser.join(gSeparator)})
		}else{
			gObjField.value=arrUser.join(gSeparator);gObjField.focus();
		}
	}catch(e){alert(e.message)}
	try{if(ck!=""){typeof ck=="function"?ck():eval(ck+"()")}}catch(e){}
	mini.get('oWinDlg').destroy();
	gObjField.focus();
}
function search(r) {
	var ifrmini=mini.get('oWinDlg').getIFrameEl().contentWindow.mini;
	var tabs=ifrmini.get('tabs');
	var tree="";
	switch(tabs.activeIndex){
		case 0:
		    tree=ifrmini.get("perTree");
		    break;
		case 1:
		    tree=ifrmini.get("deptTree");
		    break;
		case 2:
		    tree=ifrmini.get("rolTree");
		    break;
		case 3:
		    tree=ifrmini.get("aTree");
		    break;
		default:
		    tree=ifrmini.get("perTree");
	}
	var key = mini.get("key");
	if(r=="expand"){
		tree.expandAll();
	}else if(r=="collapse"){
		tree.collapseAll();
	}else if (key.getValue() == "" || r) {
		tree.clearFilter();
		tree.collapseAll();
		key.setValue("");
	} else {
		key = key.getValue().toLowerCase();
		tree.filter(function (node) {
			var text = node.name? node.name.toLowerCase() : "";
			if (text.indexOf(key) != -1) {
				return true;
			}
		});
		tree.expandAll();
	}
}
