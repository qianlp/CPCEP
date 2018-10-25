<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:action name="findFirstFim" namespace="/business/flow" executeResult="true">
</s:action>
<html>
<head>
    <title>框图维护</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <style type='text/css'>
        body,form {
            margin: 0;
            padding: 0;
            border: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
        #miniDataGrid .mini-grid-border{
        	border:0px;
        }
        #mPanel .mini-panel-border{
        	border:0px;
        }
    </style>
    <script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <script type="text/javascript">
        var menuId = '${param.menuId}';
        var modoName = "${param.entityClsName}";
        var gCurUser = "${session.user.userName}";
        var gDir="${pageContext.request.contextPath}";
        //刷新页面数据
        function refreshGridData(){
            window.location.reload();
        }
        
        function S4() {
        	return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    	}
    	function guid() {
        	return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
    	}


        function addHandler(){
            var arrSelected = grid.getSelectedNode();
            var newNode = {id:guid(),itemName:"",menuId:"",itemEvent:"",pid:""};
            if(!arrSelected){
                grid.addNode(newNode);
            }else{
            	newNode.pid=arrSelected.id;
                grid.addNode(newNode,"add",arrSelected);
            }
        }
        
        function oncloseclick(e){
        	e.sender.setValue("");
        }

        function saveHandler(){
        	var data = grid.getData() ;
           	$("[name=dataList]").val(mini.encode(data));
           	var mPanel=mini.get("mPanel").getIFrameEl();
           	if (mPanel) {
				$("#HtmlBody").val($.trim(mPanel.contentWindow.CM.getValue()));
			}
			mini.mask({
	        	el: document.body,
	        	cls: 'mini-mask-loading',
	       	 	html: '数据提交中...' //数据提交中...
	    	});
			document.forms[0].submit();
        }

        //根据id参数删除文档
        function delDocument(){
            var arrSelect = grid.getSelectedNodes();
            grid.removeNodes(arrSelect);
        }
    </script>
</head>
<body>
<div style="display:none">
<form method="post" action="${pageContext.request.contextPath}/business/flow/fimOperate.action" >
	<input name="uuid" value="${fim.uuid}" />
	<textarea name="dataList" id="dataList">${fim.dataList}</textarea>
	<textarea name="htmlBody" id="HtmlBody">
		${fim.htmlBody}
	</textarea>
</form>
</div>
<div class='mini-toolbar' style='padding:0;border-bottom-width:0'>
    <table style='width:100%;'>
        <tr>
            <td style='width:100%;white-space:nowrap;' id='leftGridToolBar'>
                <a class="mini-button" iconCls="icon-add" tooltip="功能按钮" plain="true" onclick="addHandler()">增加</a>

                <a class="mini-button" iconCls="icon-remove" tooltip="功能按钮" plain="true" onclick="delDocument()">删除</a>

                <a class="mini-button" iconCls="icon-save" tooltip="功能按钮" plain="true" onclick="saveHandler()">保存</a>
            </td>
            <td style='white-space:nowrap;' id='rightGridToolBar'><a class='mini-button' iconCls='icon-reload' plain='true' onclick='refreshGridData()'>刷新</a></td>
        </tr>
    </table>
</div>
<div class='mini-fit'>
<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="60%" >
        <div id='miniDataGrid' class='mini-treegrid' style='width:100%;height:100%;' fitColumns='false'
         	showTreeIcon="true" treeColumn="itemName" idField="id" parentField="pid" resultAsTree="false"
         	expandOnLoad="true" allowCellEdit="true" allowCellSelect="true" showCheckBox="true" >
        	<div property="columns">
            	<div type="indexcolumn"></div>
            	<div field="itemName" name="itemName" width="200">框图名称
                	<input property="editor" class="mini-textbox" />
            	</div>
            	<div field="menuId" width="200" type="treeselectcolumn">关联菜单
                	<input property="editor" class="mini-treeselect" showClose="true" oncloseclick="oncloseclick"
                		url="${pageContext.request.contextPath}/admin/findAllMenuList.action" 
                	/>
            	</div>
            	<div field="itemEvent" width="200">点击事件
                	<input property="editor" class="mini-textbox"  />
            	</div>
        	</div>
    	</div>
    </div>
    <div showCollapseButton="true">
        <div class="mini-panel" id="mPanel"  style="width:100%;height:100%;" showHeader="false" 
        	url="item_body.jsp"
        >
		</div>
    </div>        
</div>
    
</div>
<script type="application/javascript">
    mini.parse();
    var grid = mini.get('miniDataGrid');
    $(document).ready(function(){
    	grid.setData(mini.decode($("[name=dataList]").val()));
    })
</script>
</body>
</html>
