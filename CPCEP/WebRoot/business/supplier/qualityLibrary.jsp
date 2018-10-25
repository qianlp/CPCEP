<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>设备材料分类</title>
<meta name="description" content="设备材料分类">
<meta name="content-type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	border: 0;
	margin: 0;
	padding: 0;
	overflow: visible;
}

.search_body {
	border: 0px solid #000;
	overflow: hidden
}

.search_content,.search_title,.search_field,.search_button {
	float: left
}

.search_content {
	width: 200px;
	margin: 2px;
	height: 30px;
}

.search_title {
	width: 70px;
	text-align: right;
	padding-top: 1px;
	letter-spacing: 2px;
	height: 28px
}

.search_field {
	width: 130px;
	text-align: left
}

.search_button {
	margin: 1px;
}

.search_field .mini-radiobuttonlist {
	padding-top: 3px
}
</style>
	<script language="JavaScript" type="text/javascript">
        var gDir = '${pageContext.request.contextPath}';
        function cancel(){
            closeWindow("cancel");
        }

        function closeWindow(action) {
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();
        }
        /*
        function onOk(){
            window.CloseOwnerWindow('ok');
        }
        */
        function onOk(){
            var nodes = lefttree.getCheckedNodes();
            if (nodes.length == 0) {
                mini.alert("请选择数据！");
                return;
            }
            window.CloseOwnerWindow(mini.encode(nodes));
        }
	</script>
</head>
<body>
<!--功能按钮-->
	<div class='mini-toolbar'
		 style='padding:0;border-bottom-width:0;border:0px;'>
		<table style='width:100%;'>
			<tr>
				<td style='width:100%;white-space:nowrap;' id='leftGridToolBar'>
					<a class="mini-button" onclick="onOk();"
					   title="确定选择" iconCls="icon-ok" plain='true'>确定</a>
				</td>
			</tr>
		</table>
	</div>
	<div class='mini-fit' style='width:100%;height:100%;'>
		<div class="mini-splitter" allowResize="false" style="width:100%;height: 100%">
			<div size="50%">
				<ul id="lefttree" class="mini-tree" showCheckBox="true" checkRecursive="false" showFolderCheckBox="false"
					style="width:95%;height:100%;" idField="id" textField="text" onNodeCheck="rowClick" >
				</ul>
			</div>
			<div>
				<div id="qualityDatagrid" class="mini-datagrid" style="width: 100%;height:100%;" showPager="false">
					<div property="columns">
						<div type="indexcolumn" headerAlign="center" >序号</div>
						<div field="text" width="120" headerAlign="center" >供货范围</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<script type='text/javascript'>
        mini.parse();
        var lefttree = mini.get('lefttree');
        var datagrid = mini.get('datagrid');
        var gCurDB = '${pageContext.request.contextPath}';
        var pid,materialTypeId,materialTypeName,materialTypeNumber;
        $(document).ready(function(){
            loadTree();
        });
        /*
         @Use:质量标准库，进度编制中任务标准库选择
         */
        function loadTree(){
            //var url = gCurDB + "/qualityStandardType/queryLeftTree.action";
            var url = gCurDB + "/business/findCategoryTreeOpen.action";
            $.ajax({
                url:url,
                cache:false,
                type: "get",
                dataType:'json',
                success:function(data){
                	//console.log(data);
                	//console.log(mini.decode(data));
                    lefttree.loadList(mini.decode(data));
                }
            });
        }




        /**
         *树节点点击事件
         */
        function rowClick(e){
            var qualityDatagrid = mini.get('qualityDatagrid');
            var oldData = qualityDatagrid.data;
            qualityDatagrid.removeRows(oldData);
            var data = lefttree.getCheckedNodes();
            var objs = mini.decode(data);
            for(var i=0; i<objs.length; i++){
                qualityDatagrid.addRow(objs[i]);
            }
        }
	</script>
</body>
</html>
