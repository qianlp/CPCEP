function Application(config){
	gWFGraphXML=$("#WFGraphXML").val();
	if(gWFGraphXML==""){alert("\u5F53\u524D\u60A8\u4E0D\u80FD\u67E5\u770B\u6D41\u7A0B\u56FE\uFF0C\u53EF\u80FD\u6587\u6863\u6743\u9650\u6216\u8DEF\u5F84\u6709\u95EE\u9898\uFF01");return}
	//try{
		if (!Client.isBrowserSupported()){
			Utils.alert('\u5F53\u524D\u6D4F\u89C8\u5668\u4E0D\u652F\u6301\uFF01');//��ǰ�������֧�֣�
		}else{
			var node = Utils.load(config).getDocumentElement();
			var editor = new Editor(node);
		}
	//}catch (e){
	//	Utils.alert('\u4E0D\u80FD\u88C5\u8F7D\u6D41\u7A0B: '+e.message);//����װ�����̣�
	//	throw e;
	//}
	return editor;
};
var gEditor=null;
function onInit(editor,isFirstTime){
	gEditor=editor;
	editor.graph.setTooltips(true);
	var doc = $.parseXML(gWFGraphXML.replace(/@line@/g,"&#xa;").replace(/\r|\n/g,""));

	var dec = new Codec(doc);
	dec.decode(doc.documentElement,editor.graph.getModel());

	$('#graph').css("visibility","visible");
	var getCells=function(ids){
		var cells=[];
		for(var i=0,m=ids.length;i<m;i++){cells[i]=model.getCell(ids[i])}
		return cells;
	};
	var model = editor.graph.getModel();
	
	doc = $.parseXML(gWFFlowLogXML);

	var strId=doc.documentElement.getAttribute("OriginNode");
	
	var arrCells=[],arrRouters=[],arrRID=[];
	
	arrRouters.push(model.getCell(strId));
	arrRID=gWFRouterID.split(";");
	for(var i=0,m=arrRID.length;i<m;i++){arrRouters.push(model.getCell(arrRID[i]))};
	
	arrRID=Utils.getChildNodes(doc.firstChild);
	for(var i=0,m=arrRID.length;i<m;i++){arrCells.push(model.getCell(arrRID[i].nodeName))};

	editor.graph.setCellStyles(Constants.STYLE_FILLCOLOR,'green',arrCells);
	editor.graph.setCellStyles(Constants.STYLE_STROKECOLOR,'green',arrRouters);
	editor.graph.setCellStyles(Constants.STYLE_STROKEWIDTH,"2",arrRouters);
	editor.graph.setCellStyles(Constants.STYLE_DASHED,"dashed",arrRouters);
	editor.graph.setCellStyles(Constants.STYLE_FILLCOLOR,'red',getCells(gWFCurNodeID));	
}