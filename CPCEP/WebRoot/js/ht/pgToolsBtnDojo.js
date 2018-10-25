function DrawToolsBar(id,parentBtn,btnAttribute){
	/*parentBtn[标题-0,提示-1,图标-2,事件-3,排列-4,隐藏-5]*/
	toolsBgStyle = "divToolsBar" + btnAttribute.border + btnAttribute.bgImage;
	var tab='<table class="tabToolsBar" cellspacing=0 cellpadding=0><tr><td class="divToolsBar" id='+toolsBgStyle+'>';
	var tabend='</td></tr></table>';
	var arrStr="",tempStr1="",tempStr2="";
	//绘制按钮
	tempStr1 = "<ul id='ToolsBarLeft'>",tempStr2 = "<ul id='ToolsBarRight'>";
	for(var i=0;i<parentBtn.length;i++){
		if(!parseInt(parentBtn[i].isHidden)){
			if(parentBtn[i].align!="1" && parentBtn[i].align!="2"){
				alert("输入排列方式有错！请您修改程序.\n1为居左，2位居右。");return;
			}
			tempStr ="";
			tempStr += parentBtn[i].id&&parentBtn[i].id!=""?"<li id='"+parentBtn[i].id+"'>":"<li>";
			tempStr += "<a title='"+(parentBtn[i].title&&parentBtn[i].title!=""?parentBtn[i].title:"")+"' href='#' onclick=\"javascript:"+parentBtn[i].clickEvent+"\">";
			tempStr += "<span class='btnSpLeft'></span><span class='btnSpCenter'><b class='"+(parentBtn[i].ico&&parentBtn[i].ico!=""?parentBtn[i].ico:"")+"'></b>";
			tempStr += "<span class='btnFont'>"+(parentBtn[i].name&&parentBtn[i].name!=""?parentBtn[i].name:"")+"</span></span>";
			tempStr += "<span class='btnSpRight'></span></a>";
			tempStr += "</li>";
			if(parentBtn[i].align=="1"){
				tempStr1+=tempStr;
			}else{
				tempStr2+=tempStr;
			}
		}
	}
	tempStr1+="</ul>",tempStr2+="</ul>";
	tab+=tempStr1+tempStr2+tabend;
	document.getElementById(id).innerHTML=tab;
}
