var	gOrgTree=null,
	gRoleTree=null,
	gRoleCategory="\u83DC\u5355\u89D2\u8272,\u89D2\u8272",
	gNewNodePid="",
	gParentNodeHrName="",
	gActiveN=1
	gToLoad=0;
function CreateOrgTree(){
	$.ajax({
		url:encodeURI("/"+gOrgDB+"/vwPersonTreeJson?OpenView&ExpandView&Count=9999"),
		dataType:"text",
		success: function(data){
			if(data.indexOf(",")>-1){
				gOrgTree=mini.get("OrgTree");
				gOrgTree.loadList(mini.decode("["+data.substr(1)+"]"),"id","pid");
				gOrgTree.on("nodedblclick",function(e){
					if(!(gTabPlateName=="tabNull")){
						goArragneRight(e.node,gTabPlateName);
					}
				});
			}
		}
	})
}
function CreateRoleTree(){
	$.ajax({
		url:encodeURI("/"+gCurDB+"/agtRoleRead?OpenAgent"),
		type:"post",
		data:{C:gRoleCategory},
		dataType:"text",
		success: function(data){
			if(data.indexOf(",")>-1){
				gRoleTree= mini.get("RoleTree");
				gRoleTree.loadList(mini.decode("["+data.substr(1)+"]"),"id","pid");
				gRoleTree.on("nodedblclick",function(e){
					if(!(gTabPlateName=="tabNull")){
						goArragneRight(e.node,gTabPlateName);
					}
				})
			}
		}
	})
}
/*
 * \u63CF\u8FF0:\u6743\u9650\u5206\u7C7B
 */
function goRightCategory(){
	if(gActiveN==1){
		var oName=mini.get("tabMenu").getActiveTab().name;
		gTabPlateName="MenuTree"+oName;
		$("#tabMenu").show();
		$("#ktMenu").hide();
	}else if(gActiveN==2){
		$("#tabMenu").hide();
		$("#ktMenu").show();
		var oName=mini.get("ktMenu").getActiveTab().name;
		gTabPlateName="MenuTree"+oName;
		goPlateTree(gTabPlateName,0)
	}
}
/*
 * \u63CF\u8FF0:\u5DF2\u52A0\u8F7D\u7684\u6743\u9650\u505A\u6807\u8BB0
 */
function goLoadPlateId(TreeName){
	if(gTabLoadPlate!=""){
		gTabLoadPlate+="^HT^";
	}
	gTabLoadPlate+=TreeName;
}
/*
 * \u63CF\u8FF0:tab\u5207\u6362\u65F6\u5982\u679C\u6B63\u5904\u4E8E\u5206\u914D\u4E2D\uFF0C\u5219\u52A0\u8F7D\u6743\u9650
 */
function goNodePrower(TreeName){
	if(gAssigner==""){
		return
	}
	if(gTabLoadPlate.indexOf(TreeName)!=-1){
		return
	}
	goLoadPlateId(TreeName);
	var msgId=mini.loading("\u6743\u9650\u8BFB\u53D6\u4E2D...","");
	$.ajax({
		url:"/"+gCurDB+"/agtGetRight?OpenAgent",
		type:"post",
		cache: false,
		dataType:"text",
		data:{
			Name: gAssigner,
			ID: gArrNotesID.join("^"),
			SM: gAssignerIsSuperMan
		},
		success:function(objHTML){
			objHTML=objHTML.replace(/\s/g,"");
			if(objHTML!=""){
				var strJSON = objHTML.replace(/\s/g,"").substr(1);
				// \u8BBE\u7F6E\u83DC\u5355\u6743\u9650\u72B6\u6001
				goSetCheckBox(mini.decode("{"+strJSON+"}"),TreeName);
			}
			setTimeout(function(){mini.hideMessageBox(msgId)},500);
		}
	})
}


/*
 * \u63CF\u8FF0:\u83DC\u5355\u7BA1\u7406
 */
function goMenu(e){
	var strURL="",strTitle="",strDeptHrName="",strDocID="",strParID="",strAppType="",intWidth=700,intHeight=500;
	if(typeof(e.node)=="undefined"){
		var oMenuTree= mini.get(gTabPlateName);
		if(typeof(oMenuTree)!="undefined"){
			var oNode=oMenuTree.getSelectedNode();	
			if(typeof(oNode)!="undefined"){
				if(typeof(oNode.img)!="undefined"&&oNode.img=="filter.gif"){alert("\u6309\u94AE\u4E0B\u4E0D\u80FD\u6DFB\u52A0\u83DC\u5355\uFF01");return}
				strDeptHrName=oNode.fullname;
				strParID=oNode.id;
				strAppType=oNode.AppType;
			}
		}
		strTitle="\u65B0\u5EFA";
		strURL="/"+gCurDB+"/fmNode?OpenForm&ParDept="+strDeptHrName+"&ParID="+strParID+"&AppType="+strAppType;
	}else{
		strDeptHrName=e.node.fullname;
		intHeight=500;
		strDocID=e.node.id;
		strTitle="\u7EF4\u62A4";
		strURL="/"+gCurDB+"/vwNodeEdit/"+strDocID+".?EditDocument";
	}
	var oWinDlg=mini.get('oWinDlg');
	if(oWinDlg==null){
		oWinDlg=mini.open({
			id:"oWinDlg",
			showMaxButton:true,
			headerStyle:"font-weight:bold;letter-spacing:4px",
			footerStyle:"padding:5px;height:25px"
		});
	}
	oWinDlg.setUrl(strURL);
	oWinDlg.setTitle(strTitle);
	oWinDlg.setWidth(intWidth);
	oWinDlg.setHeight(intHeight);
	oWinDlg.showAtPos("center","middle");
}
/*
 * \u63CF\u8FF0\uFF1A\u6539\u53D8\u590D\u9009\u6846\u72B6\u6001
 */
function goCheckBox(TreeName){
	// \u6811\u5BF9\u8C61
	var oTree=mini.get(TreeName);
	// \u5F53\u524D\u9009\u4E2D\u8282\u70B9
	var oNode=oTree.getSelectedNode();
	if(!gSuperMan){if(gAssigner==""){alert("\u8BF7\u53CC\u51FB\u6700\u5DE6\u4FA7\u7684\u5BF9\u8C61\u8FDB\u884C\u6743\u9650\u5206\u914D\uFF01");return}}
	// \u5F53\u72B6\u6001\u4E3A\u672A\u9009\u4E2D\u65F6\uFF0C\u70B9\u51FB\u540E\u5C31\u53D8\u4E3A\u9009\u4E2D\u3002\u5373\u4E3A1\u53D8\u4E3A2\u30021\u4E3A\u672A\u9009\u4E2D\uFF0C2\u4E3A\u5DF2\u9009\u4E2D
	if(oNode.status==1){
		oTree.updateNode(oNode,{status:2});
	}else if(oNode.status==2){
		oTree.updateNode(oNode,{status:1});
	}
	// \u6240\u6709\u7236\u8282\u70B9\u4E0D\u542B\u81EA\u8EAB
	var arrParent=oTree.getAncestors(oNode),arrChildNode=[];
	// 1\u3001\u7236\u8282\u70B9\u5BF9\u8C61\u30022\u3001\u590D\u9009\u6846\u5BF9\u8C61
	var oN=null,intCheckBox=0;
	// 1\u3001\u5F53\u524D\u8282\u70B9\u72B6\u6001\u30022\u3001\u662F\u5426\u7EE7\u7EED\u68C0\u67E5\u7EE7\u627F\u72B6\u6001
	var intStatus=oNode.status,bCheck=true;
	for(var intM=0,intL=arrParent.length;intL>intM;intL--){
		oN=arrParent[intL-1];
		if(intStatus!=3&&oN.status!=3){
			// \u672A\u9009\u4E2D
			if(intStatus==1){
				// 1\u3001\u5DF2\u9009\u4E2D\u4E2A\u6570\uFF1B2\u3001\u8BE5\u8282\u70B9\u7684\u5B69\u5B50\u6570\uFF08\u4E0D\u542B\u5B50\u5B59\uFF09
				intCheckBox=0;arrChildNode=oTree.getChildNodes(oN);
				for(var intQ=0,intS=arrChildNode.length;intQ<intS;intQ++){
					if($("#"+arrChildNode[intQ]["id"],"#"+TreeName).hasClass("rChecked")){
						intCheckBox++
					}
				}
				// \u5F53\u672C\u8282\u70B9\u72B6\u6001\u53D8\u4E3A\u672A\u9009\u4E2D\u65F6\uFF0C\u5982\u679C\u7236\u8282\u70B9\u4E0B\u6CA1\u6709\u88AB\u9009\u4E2D\u7684\u5B50\u8282\u70B9\u65F6\uFF0C\u6539\u53D8\u72B6\u6001\u4E3A\u672A\u9009\u4E2D
				if(intCheckBox<1){
					oTree.updateNode(oN,{status:intStatus})
				}
			}else{
				// \u5F53\u672C\u8282\u70B9\u72B6\u6001\u53D8\u4E3A\u5DF2\u9009\u4E2D\u65F6\uFF0C\u5982\u679C\u7236\u8282\u70B9\u4E0B\u5C31\u4E00\u4E2A\u5DF2\u9009\u4E2D\u5B50\u8282\u70B9\u65F6\uFF08\u8BF4\u660E\u539F\u5148\u7236\u8282\u70B9\u4E0B\u5C31\u6CA1\u6709\u88AB\u9009\u4E2D\u7684\u5B50\u8282\u70B9\uFF09\uFF0C\u6539\u53D8\u72B6\u6001\u4E3A\u5DF2\u9009\u4E2D\u3002
				oTree.updateNode(oN,{status:intStatus})
			}
		}else{
			break;
		}
	}
	// \u6539\u53D8\u6240\u6709\u5B50\u5B59\u72B6\u6001
	oTree.cascadeChild(oNode,function(node){
		// \u5F53\u7236\u8282\u70B9\u4E3A\u7EE7\u627F\u72B6\u6001\uFF0C\u5C31\u7EE7\u7EED\u627E\u5B50\u8282\u70B9\u7684\u72B6\u6001\uFF0C\u76F4\u5230\u4E0D\u4E3A\u7EE7\u627F\u72B6\u6001\u4E3A\u6B62
		if(intStatus==3&&bCheck){
			if(node.status==1){
				intStatus=2;
				bCheck=false;
			}else if(node.status==2){
				intStatus=1;
				bCheck=false;
			}
		}
		if(node.status==1||node.status==2){
			oTree.updateNode(node,{status:intStatus});
		}
	});
}
/*
 * \u63CF\u8FF0\uFF1A\u91CD\u65B0\u7ED8\u5236\u6811\u8282\u70B9
 */
function goDrawNode(e){
	var tree = e.sender;
	var node = e.node;
	var strClassName="";
	switch(node.status){
		case 0:
			strClassName=("rTree rNoUnChecked");
			break;
		case 1:
			strClassName=("rTree rUnChecked");
			break;
		case 2:
			// \u8BBE\u7F6E\u4E3A\u5DF2\u9009\u4E2D\u72B6\u6001
			strClassName=("rTree rChecked");
			//
			break;
		default:
			strClassName=("rTree rNoChecked");
	}
	// \u5F53\u9875\u7B7E\u4E3A\u6570\u636E\u5E93\u89D2\u8272\u6216\u5176\u4ED6\u6570\u636E\u5C55\u73B0\u65F6,\u8282\u70B9\u72B6\u6001\u4E3A3\u65F6\u4E0D\u9700\u8981\u66F4\u65B0\u8282\u70B9\u72B6\u6001
	if(gTabPlateName=="GridTree"){
		// \u5982\u679C\u662F\u6839\u5C31\u8BBE\u7F6E\u72B6\u6001\u4E3A3
		if(!e.isLeaf){		
			strClassName=("rTree rNoChecked");
		}
	}
	e.nodeHtml = '<span id="' + node.id + '" onclick=\'goCheckBox("'+tree.id+'")\' class="'+strClassName+'"></span><span class="rCheckBoxText">' + node.text + '</span>';
}
/*
 * \u63CF\u8FF0\uFF1A\u53CC\u51FB\u91CD\u65B0\u5B89\u6392\u8282\u70B9\u6743\u9650
 */
function goArragneRight(oNode,TreeName){
	var isDept=oNode.isdept;
	var strFullName=isDept=="2"?oNode.name:oNode.fullname;
	if(isDept=="1"){
		strFullName=strFullName.replace(/\//g,"_");
	}
	gAssigner = strFullName;
	if(gOldAssigner==gAssigner){return}
	// \u6E05\u7A7A\u5386\u53F2\u52A0\u8F7D\u6743\u9650\u6807\u8BC6
	gTabLoadPlate="";
	// \u6E05\u7A7A\u539F\u5148\u88AB\u9009\u4E2D\u7684\u8282\u70B9
	gCheckedNode=[];
	gOldAssigner=strFullName;
	$.ajax({
		url:encodeURI("/"+gCurDB+"/agtSuperMan?OpenAgent&Name="+strFullName),
		cache: false,
		async: false,
		dataType:"text",
		success: function(data){
			var oTree=mini.get(TreeName);
			var arrAllNode=oTree.getList();
			var intM=0,intL=arrAllNode.length;
			// \u8D85\u7EA7\u7BA1\u7406\u5458
			var bSuperMan=data.replace(/\s/g,"");
			gAssignerIsSuperMan=bSuperMan
			if(bSuperMan=="1"){
				// \u8BBE\u7F6E\u6240\u6709\u7684\u5931\u6548\u590D\u9009\u6846
				// $(".rTree",TreeName).removeClass().addClass("rTree
				// rNoUnChecked");
				for(;intM<intL;intM++){
					oTree.updateNode(arrAllNode[intM],{status:3});
				}
				$("#divAssigner").html("<b style='color:red'>"+strFullName.replace(/(OU=|\/.*)/g,"")+"</b> \u662F\u8D85\u7EA7\u7BA1\u7406\u5458\uFF0C\u53EA\u5141\u8BB8\u5BF9\u201C\u6570\u636E\u5E93\u89D2\u8272\u201D\u8FDB\u884C\u5206\u914D\u3002");
				return;
			}else{
				for(;intM<intL;intM++){
					oTree.updateNode(arrAllNode[intM],{status:1});
				}
				$("#divAssigner").html("\u60A8\u6B63\u5728\u7ED9 &lt; <b style='color:blue'>"+(isDept=="1"?"\u90E8\u95E8":(isDept=="2"?"\u89D2\u8272":"\u4E2A\u4EBA"))+"</b> \uFF1A<b style='color:red'>"+(isDept=="1"?strFullName.split("_")[0]:strFullName.replace(/(OU=|\/.*)/g,""))+"</b> &gt; \u8FDB\u884C\u6743\u9650\u5206\u914D\u3002")
			}
			var msgId=mini.loading("\u6743\u9650\u8BFB\u53D6\u4E2D...","");
			$.ajax({
				url:"/"+gCurDB+"/agtGetRight?OpenAgent",
				type:"post",
				cache: false,
				dataType:"text",
				data:{
					Name: strFullName,
					ID: gArrNotesID.join("^"),
					SM: bSuperMan
				},
				success:function(objHTML){
					objHTML=objHTML.replace(/\s/g,"");
					if(objHTML!=""){
						var strJSON = objHTML.replace(/\s/g,"").substr(1);
						// \u8BBE\u7F6E\u83DC\u5355\u6743\u9650\u72B6\u6001
						goSetCheckBox(mini.decode("{"+strJSON+"}"),TreeName);
					}
					goLoadPlateId(TreeName)
					setTimeout(function(){mini.hideMessageBox(msgId)},500);
   				}
   			}
		)}
	});
}
/*
 * \u63CF\u8FF0\uFF1AAjax\u83B7\u5F97\u5BF9\u5E94\u6743\u9650\u540E\uFF0C\u91CD\u65B0\u8FDB\u884C\u66F4\u65B0
 */
// \u5DF2\u62E5\u6709\u9009\u4E2D\u7684\u6743\u9650\u8282\u70B9ID\uFF0C\u7528\u4E8E\u4FDD\u5B58\u65F6\u6392\u9664\u8FD9\u4E9B\u5DF2\u62E5\u6709\u9009\u4E2D\u6743\u9650\uFF0C\u53EA\u4FDD\u5B58\u53D1\u751F\u53D8\u5316\u7684\u8282\u70B9\uFF0C\u63D0\u9AD8\u4FDD\u5B58\u6548\u7387
var gCheckedNode=[];
function goSetCheckBox(oRightNode,TreeName){
	var oTree=mini.get(TreeName);
	var arrAllNode=oTree.getList();
	var intM=0,intL=arrAllNode.length,oStatus=null;
	// \u6E05\u7A7A\u539F\u5148\u88AB\u9009\u4E2D\u7684\u8282\u70B9
	// gCheckedNode=[];
	for(;intM<intL;intM++){
		// \u8282\u70B9\u662F\u5426\u8BF7\u6C42\u8FC7\u6743\u9650
		// status=0\uFF1A\u5931\u6548\u672A\u9009\u4E2D\uFF1B1\uFF1A\u6709\u6548\u672A\u9009\u4E2D\uFF1B2\uFF1A\u6709\u6548\u5DF2\u9009\u4E2D\uFF1B3\uFF1A\u5931\u6548\u5DF2\u7EE7\u627F
		// \u4E0D\u7B49\u4E8Eundefined\u65F6\uFF0C\u8868\u793A\u8BE5\u8282\u70B9\u6709\u6743\u9650
		oStatus=oRightNode[arrAllNode[intM].id];
		if(typeof(oStatus)!="undefined"){
			// \u8282\u70B9\u524D\u540E\u72B6\u6001\u4E0D\u4E00\u81F4\u65F6\uFF0C\u4FEE\u6539\u8282\u70B9\u72B6\u6001\u4E3A"\u672A\u9009\u62E9"
			if(oStatus!=arrAllNode[intM].status){
				oTree.updateNode(arrAllNode[intM],{status:oStatus});
			}
			if(oStatus==2){
				gCheckedNode.push(arrAllNode[intM].id);
			}
		}else{
			// \u5982\u679C\u8282\u70B9\u72B6\u6001\u4E3A"\u5DF2\u9009\u62E9"\uFF0C\u6216\u65E0\u6548\u9009\u62E9\u72B6\u6001\u65F6\uFF0C\u4FEE\u6539\u8282\u70B9\u72B6\u6001\u4E3A"\u672A\u9009\u62E9"
			if(arrAllNode[intM].status>1){
				oTree.updateNode(arrAllNode[intM],{status:1});
			}
		}
	}
}
/*
 * \u63CF\u8FF0\uFF1A\u7248\u5757\u9875\u7B7E\u5207\u6362\u65F6\u89E6\u53D1
 */
function goTabChange(e){
	var oTabs=e.sender,oCurTab=e.tab;
	var oMenu=mini.get("barAddMenu");
	var oMenuInit=mini.get("barMenuInit");
	var oMenuIn=mini.get("barInMenu");
	var oMenuOut=mini.get("barOutMenu");
	
	var oRole=mini.get("barAddRole");
	var oInRole=mini.get("barImportRole");
	// gTabPlateName=oCurTab.name;
	
	// \u5207\u6362\u9875\u7B7E\u65F6\uFF0C\u6E05\u7A7A\u5DF2\u9009\u62E9\u5206\u914D\u7684\u4EBA\uFF0C\u5229\u4E8E\u53CC\u51FB\u6709\u6548
	// gOldAssigner="";
	try{
		
		if(oCurTab.name=="tabRole"){
			// \u53EA\u5141\u8BB8\u8D85\u7EA7\u7BA1\u7406\u5458\uFF0C\u624D\u80FD\u770B\u89C1\u6309\u94AE
			if(gSuperMan){
				// \u7248\u5757\u5B58\u5728\u65F6\u624D\u5141\u8BB8\u521B\u5EFA\u89D2\u8272
				if(!gPlateNum){
					oMenuInit.setVisible(true);
				}else{
					oMenu.setVisible(false);
					oMenuIn.setVisible(false);
					oMenuOut.setVisible(false);
					oRole.setVisible(true);
					oInRole.setVisible(true);
				}
			}
		}else if(oCurTab.name=="tabGrid"){
			if(gSuperMan){
				mini.get("barAddGrid").setVisible(true);
				oMenu.setVisible(false);
				oMenuIn.setVisible(false);
				oMenuOut.setVisible(false);
				oRole.setVisible(false);
				oInRole.setVisible(false);
				gTabPlateName="GridTree";
				// \u88C5\u8F7D\u7248\u5757\u6811
				goGridTree();
			}
		}else if(oCurTab.name=="tabNull"){
			oMenu.setVisible(false);
			oRole.setVisible(false);
			oInRole.setVisible(false);
		}else{
			// \u53EA\u5141\u8BB8\u8D85\u7EA7\u7BA1\u7406\u5458\uFF0C\u624D\u80FD\u770B\u89C1\u6309\u94AE
			if(gSuperMan){
				mini.get("barAddGrid").setVisible(false);
				oMenu.setVisible(true);
				oRole.setVisible(false);
				oInRole.setVisible(false);
				oMenuIn.setVisible(true);
				oMenuOut.setVisible(true);
			}
			// \u6240\u9009\u4E2D\u7684\u9875\u7B7E\u4E2D\u5BF9\u5E94\u7684\u8868\u793A
			goRightCategory()
			// \u88C5\u8F7D\u7248\u5757\u6811
			goPlateTree(gTabPlateName,gToLoad);
		}
	}catch(e){
		window.location.reload();
	}
}
/*
 * \u63CF\u8FF0\uFF1A\u91CD\u65B0\u88C5\u8F7D\u6811\u8282\u70B9
 */
function goPlateTree(gPlateName,intB){
	var strPath="",strPN=gPlateName.split("MenuTree")[1];
	var oMenuTree= mini.get(gPlateName);
	if(oMenuTree.getList().length>0 && intB==0){
		goNodePrower(gPlateName);
		return
	}
	// \u590D\u539F\u52A0\u8F7D\u72B6\u6001
	gToLoad=0;
	if(gSuperMan){
		strPath="/"+gCurDB+"/vwMenuCat$Btn?OpenView&ExpandView&Count=999&RestrictToCategory="+strPN;
	}else{
		strPath="/"+gCurDB+"/(agtGetPlateTree$Btn)?OpenAgent&platename="+strPN+"&name="+gCurUserName;
	}
	var msgId=mini.loading("\u6570\u636E\u52A0\u8F7D\u4E2D...","");
	$.ajax({
		url:encodeURI(strPath),
		cache:false,
		dataType:"text",
		success: function(data){
			if(data.indexOf(",")>-1){
				var arrNodes=mini.decode("["+data.substr(1)+"]");
				oMenuTree.loadList(arrNodes,"id","pid");
				gArrNotesID=[];
				for(var intM=0,intL=arrNodes.length;intM<intL;intM++){
					// \u5B58\u50A8\u6240\u6709\u83DC\u5355\u8282\u70B9\u7684ID\uFF0C\u7528\u4E8E\u8BFB\u53D6\u4E0E\u4FDD\u5B58\u6743\u9650\u7528
					gArrNotesID.push(arrNodes[intM].id);
				}
				// \u5C55\u5F00\u6240\u6709
				oMenuTree.expandAll();
				mini.hideMessageBox(msgId);
				goNodePrower(gPlateName);
			}else{
				mini.hideMessageBox(msgId);
			}
		}
	});
}

/*
 * \u63CF\u8FF0\uFF1A\u5237\u65B0\u5355\u4E2A\u8282\u70B9
 */
function goRefNode(){
	if(gParentNodeHrName==""){
		return
	}
	var oNames=gParentNodeHrName.split("/")
	var oMenuTree=mini.get(gTabPlateName);
	var nodes=oMenuTree.findNodes(function(node){
	    if(node.id==gNewNodePid) return true;
	});
	if(nodes.length==0){
		if(gActiveN==1){
			var tabs=mini.get("tabMenu");
			// \u91CD\u7F6E\u52A0\u8F7D\u72B6\u6001
			gToLoad=1;
			tabs.activeTab(tabs.getTab(oNames[oNames.length-1]));
			return
		}else if(gActiveN==2){
			var tabs=mini.get("ktMenu");
			// \u91CD\u7F6E\u52A0\u8F7D\u72B6\u6001
			gToLoad=1;
			tabs.activeTab(tabs.getTab(oNames[oNames.length-1]));
			return
		}
	}
	var strPath="";
	if(gSuperMan){
		strPath="/"+gCurDB+"/(agtGetMenuByPid)?OpenAgent&parDocId="+gNewNodePid;
	}else{
		strPath="/"+gCurDB+"/(agtGetMenuByPid)?OpenAgent&parDocId="+gNewNodePid+"&name="+gCurUserName;
	}
	$.ajax({
		url:encodeURI(strPath),
		cache:false,
		dataType:"text",
		success: function(data){
			if(data.indexOf(",")>-1){
				var arrNodes=mini.arrayToTree(mini.decode("["+data.substr(1)+"]"),"children","id","pid");
				oMenuTree.removeNodes(oMenuTree.getAllChildNodes(nodes[0]));
				oMenuTree.addNodes(arrNodes,nodes[0]);
				for(var intM=0,intL=oMenuTree.getList().length;intM<intL;intM++){
					// \u5B58\u50A8\u6240\u6709\u83DC\u5355\u8282\u70B9\u7684ID\uFF0C\u7528\u4E8E\u8BFB\u53D6\u4E0E\u4FDD\u5B58\u6743\u9650\u7528
					gArrNotesID.push(oMenuTree.getList()[intM].id);
				}
			}else{
				oMenuTree.loadList([],"id","pid");
			}
	}
});
}
/*
 * \u63CF\u8FF0\uFF1A\u91CD\u65B0\u88C5\u8F7D\u6811\u8282\u70B9
 */
function goGridTree(){
	var strPath="";
	strPath="/"+gCurDB+"/vwMenuGrid$Btn?OpenView&ExpandView&Count=999";
	$.ajax({
		url:encodeURI(strPath),
		cache:false,
		dataType:"text",
		success: function(data){
			var oGridTree= mini.get("GridTree");
			if(data.indexOf(",")>-1){
				var arrNodes=mini.decode("["+data.substr(1)+"]");
				oGridTree.loadList(arrNodes,"id","pid");
				gArrNotesID=[];
				for(var intM=0,intL=arrNodes.length;intM<intL;intM++){
					// \u5B58\u50A8\u6240\u6709\u83DC\u5355\u8282\u70B9\u7684ID\uFF0C\u7528\u4E8E\u8BFB\u53D6\u4E0E\u4FDD\u5B58\u6743\u9650\u7528
					gArrNotesID.push(arrNodes[intM].id);
				}
				// \u5C55\u5F00\u6240\u6709
				oGridTree.expandAll();
			}else{
				oGridTree.loadList([],"id","pid");
			}
		}
	});
}
/* \u63CF\u8FF0:\u5173\u95ED\u7A97\u53E3 */
function goCloseDlg(name){
	var oWinDlg=mini.get(name);
	oWinDlg.setUrl("about:blank");
	$(oWinDlg.getBodyEl()).html("");
	$(oWinDlg.getFooterEl()).html("");
	// \u91CD\u65B0\u52A0\u8F7D\u5F53\u524D\u9875\u7B7E\u7684\u6811
	if(gTabPlateName=="GridTree"){
		goGridTree();
	}else{
		goRefNode();
	}
	oWinDlg.hide()
}
/* \u63CF\u8FF0\uFF1A\u4FDD\u5B58\u6743\u9650 */
function goSave(){
	if(gAssigner==""){mini.alert("\u8BF7\u5148\u9009\u62E9\u9700\u8981\u5206\u914D\u7684\u5BF9\u8C61!","\u64CD\u4F5C\u63D0\u793A");return};
	var msgId=mini.loading("\u6743\u9650\u4FDD\u5B58\u4E2D...","\u6570\u636E\u63D0\u4EA4");
	var arrNoteID = [];
	// \u6240\u6709\u8282\u70B9
	var gPlateNameArr=gTabLoadPlate.split("^HT^");
	$.each(gPlateNameArr,function(){
		if(this==""){ return true; }
		var oTree=mini.get(this.toString());
		var arrAllNode=oTree.getList();
		var intM=0,intL=arrAllNode.length;
		for(;intM<intL;intM++){
			var oNode=arrAllNode[intM];
			// \u8282\u70B9\u5728"\u5DF2\u62E5\u6709\u6743\u9650\u8282\u70B9"\u6570\u7EC4\u4E2D
			if($.inArray(oNode.id,gCheckedNode)>-1){
				// \u5F53\u8282\u70B9\u7684\u72B6\u6001\u53D8\u4E3A\u201C\u672A\u9009\u4E2D\u201D\u65F6\uFF0C\u8BF4\u660E\u5DF2\u53D6\u6D88\u6743\u9650
				if(oNode.status==1){
					arrNoteID.push(oNode.id+"-1")
				}
			}else{
				// \u5F53\u8282\u70B9\u7684\u72B6\u6001\u53D8\u4E3A\u201C\u5DF2\u9009\u4E2D\u201D\u65F6\uFF0C\u8BF4\u660E\u5DF2\u62E5\u6709\u6743\u9650
				if(oNode.status==2){
					arrNoteID.push(oNode.id+"-2")
				}
			}
		}
	});
	var PathUrl="/"+gCurDB+"/agtSaveRight?OpenAgent";
	$.ajax({
		url:encodeURI(PathUrl),
		type:"post",
		dataType:"text",
		data:{
			Name: gAssigner,
			ID: arrNoteID.join("^")
		},
		success: function(objHTML){
			if(objHTML==""){
				setTimeout(function(){mini.hideMessageBox(msgId);mini.alert("\u4FDD\u5B58\u6210\u529F!","\u6D88\u606F\u63D0\u793A");},500);
			}
		}
	});
}
/*
 * \u63CF\u8FF0\uFF1A\u7EC4\u7EC7\u6216\u89D2\u8272\u641C\u7D22
 */
function Search() {
	if(mini.get("OrgTabs").getActiveTab().id=="OrgTree")
		var oTree=gOrgTree;
	else
		var oTree=gRoleTree;
	var key = mini.get("key").getValue();
	if (key == "") {
		oTree.clearFilter();
	} else {
		key = key.toLowerCase();
		oTree.filter(function (node) {
			var text = node.name ? node.name.toLowerCase() : "";
			if (text.indexOf(key) != -1) {
				return true;
			}
		});
     }
}
/*
 * \u63CF\u8FF0\uFF1A\u83DC\u5355\u521D\u59CB\u5316
 */
function goInit(){
	window.location="/"+gCurDB+"/(agtMenuInit2XML)?OpenAgent"
}
/*
 * \u63CF\u8FF0:\u5BFC\u5165\u83DC\u5355
 */
function goImport(){
	var strURL="/"+gCurDB+"/fmImportFile?OpenForm",strTitle="\u83DC\u5355\u5BFC\u5165",intWidth=400,intHeight=350;
	var oWinDlg=mini.get('oWinDlg');
	if(oWinDlg==null){
		oWinDlg=mini.open({
			id:"oWinDlg",
			showFooter:true,
			headerStyle:"font-weight:bold;letter-spacing:4px",
			footerStyle:"padding:5px;height:25px"
		});
	}
	oWinDlg.setUrl(strURL);
	oWinDlg.setTitle(strTitle);
	oWinDlg.setWidth(intWidth);
	oWinDlg.setHeight(intHeight);
	oWinDlg.showAtPos("center","middle");
}
/*
 * \u63CF\u8FF0:\u5BFC\u51FA\u83DC\u5355
 */
function goExport(){
	var msgId=mini.loading("\u83DC\u5355\u5BFC\u51FA...","");
	var arrNoteID = [];
	// \u6240\u6709\u8282\u70B9
	var oTree=mini.get(gTabPlateName);
	var arrAllNode=oTree.getList();
	var intM=0,intL=arrAllNode.length;
	for(;intM<intL;intM++){
		var oNode=arrAllNode[intM];
		if(oNode.status==2){
			arrNoteID.push(oNode.id)
		}
	}
	if(arrNoteID.length==0){mini.hideMessageBox(msgId);mini.alert("\u6CA1\u9009\u62E9\u4EFB\u4F55\u5BFC\u51FA\u5BF9\u8C61!","\u64CD\u4F5C\u63D0\u793A");return};	
	var PathUrl="/"+gCurDB+"/(agtExportMenu)?OpenAgent";
	$.ajax({
		url:encodeURI(PathUrl),
		type:"post",
		dataType:"text",
		data:{
			ID: arrNoteID.join("^")
		},
		success: function(objHTML){
			if(objHTML!=""){
				setTimeout(function(){
					mini.hideMessageBox(msgId);

			        var oWinOut = mini.open({
			            title: '\u5BFC\u51FA\u6210\u529F',
			            allowResize:false,
			            width: 350,
			            height: 200
			        });
			        oWinOut.setBody("<div style='padding:10px'><p><a href='/"+ objHTML +"'><h2>"+objHTML+"</h2></a></p><p>\u8BF7\u53F3\u952E\u9009\u62E9<\u76EE\u6807\u53E6\u5B58\u4E3A>\u8FDB\u884C\u4E0B\u8F7D\uFF01</p></div>");
					oWinOut.showAtPos("center","middle");
				},500);
			}
		}
	});
}
/*
 * \u63CF\u8FF0:\u6570\u636E\u5E93\u89D2\u8272\u7BA1\u7406
 */
function goRole(e){
	var strURL="",strTitle="",strDeptHrName="",strDocID="",strParID="",intWidth=700,intHeight=500;
	if(typeof(e.node)=="undefined"){
		strTitle="\u65B0\u5EFA";
		strURL="/"+gCurDB+"/fmRoleName?OpenForm";
	}else{
		strDeptHrName=e.node.fullname;
		intHeight=500;
		strDocID=e.node.id;
		strTitle="\u7EF4\u62A4";
		strURL="/"+gCurDB+"/vwNodeEdit/"+strDocID+".?EditDocument";
	}
	var oWinDlg=mini.get('oWinDlg');
	if(oWinDlg==null){
		oWinDlg=mini.open({
			id:"oWinDlg",
			showMaxButton:true,
			headerStyle:"font-weight:bold;letter-spacing:4px",
			footerStyle:"padding:5px;height:25px"
		});
	}
	oWinDlg.setUrl(strURL);
	oWinDlg.setTitle(strTitle);
	oWinDlg.setWidth(intWidth);
	oWinDlg.setHeight(intHeight);
	oWinDlg.showAtPos("center","middle");
}
/*
 * \u63CF\u8FF0:\u8868\u683C\u5C55\u73B0\u7BA1\u7406
 */
function goGrid(e){
	var strURL="",strTitle="",strDocID="",intWidth=700,intHeight=500;
	if(typeof(e.node)=="undefined"){
		strTitle="\u65B0\u5EFA";
		strURL="/"+gCurDB+"/fmGrid?OpenForm";
	}else{
		intHeight=500;
		strDocID=e.node.id;
		strTitle="\u7EF4\u62A4";
		strURL="/"+gCurDB+"/vwNodeEdit/"+strDocID+".?EditDocument";
	}
	var oWinDlg=mini.get('oWinDlg');
	if(oWinDlg==null){
		oWinDlg=mini.open({
			id:"oWinDlg",
			showMaxButton:true,
			headerStyle:"font-weight:bold;letter-spacing:4px",
			footerStyle:"padding:5px;height:25px"
		});
	}
	oWinDlg.setUrl(strURL);
	oWinDlg.setTitle(strTitle);
	oWinDlg.setWidth(intWidth);
	oWinDlg.setHeight(intHeight);
	oWinDlg.showAtPos("center","middle");
}