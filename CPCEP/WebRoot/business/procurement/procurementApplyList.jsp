<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>采购申请</title>
    <meta name="description" content="采购申请">
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
        html, body {
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

        .search_content, .search_title, .search_field, .search_button {
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
        .mini-button {
            margin-left: 15px;
        }

        .search_field .mini-radiobuttonlist {
            padding-top: 3px
        }
    </style>
</head>
<body>

<!--查询部分-->
<div class="search_body" id="search_body">
	<div class="search_content">
		<div class="search_title">项目编号：</div>
		<div class="search_field" Operator="@" DataType="text" Item="">
			<input name="projectNo" id="projectNo" class="mini-textbox">
		</div>
	</div>
	<div class="search_button">
		<a class="mini-button" margin= "10px"  tooltip="清空查询条件" plain="true" iconCls="icon-remove" onclick="ClearForm"></a>
		 <a class="mini-button" iconCls="icon-search" onclick="CommonSearch">搜索</a>
	</div>
</div>

<!--功能按钮-->
<div class="mini-toolbar"
     style="padding:0;border-bottom-width:0;border:0px;">
    <table style="width:100%;">
        <tr>
          <td style="width:100%;white-space:nowrap;" id="leftGridToolBar">   
             <a class="mini-button" iconCls="icon-new" tooltip="功能按钮" plain="true" 
             onclick="btnEvent({ action:'new',open:'Open',title:menuName,fm:gDir+'/admin/rtuMenuById.action'})">新建</a>			
			 <a class="mini-button" iconCls="icon-edit" tooltip="功能按钮" plain="true">编辑</a>			
			 <a class="mini-button" iconCls="icon-remove" tooltip="功能按钮" plain="true" onclick="delDocument()" >删除</a>
	      </td>
          <td style="white-space:nowrap;" id="rightGridToolBar">
             <a class="mini-button" iconCls="icon-reload" plain="true" onclick="RefreshGridData()">刷新</a>
           </td>
        </tr>
    </table>
</div>
<!--撑满页面-->
<div class="mini-fit" style="width:100%;height:100%;">
    <div id="miniDataGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="uuid"
         sizeList="[15,30,50,100]" pageSize="15" showColumnsMenu="true" multiSelect="true" fitColumns="true">
        <div property="columns">
 
            <div type="indexcolumn"></div>
            <div type="checkcolumn"></div>
           <div field="precurementNo" width="120" allowSort="true">采购申请编号</div>
           <div field="projectId" width="120" allowSort="true">项目编号编号</div>
           <div field="precurementNo" width="120" allowSort="true">项目名称</div>
           <div field="procurementDesc" width="120" allowSort="true">采购描述</div>
           <div field="procurementType" width="120" allowSort="true">采购类型</div>
           <div field="procurementName" width="120" allowSort="true">采购设备名称</div>
           <!-- <div width="120" allowSort="true">单位</div>
           <div field="precurementNo" width="120" allowSort="true">品牌</div>
           <div field="precurementNo" width="120" allowSort="true">规格型号</div>
           <div field="precurementNo" width="120" allowSort="true">数量</div> -->
           <div field="orderEnterTime" width="120" allowSort="true">要求进场时间</div>
           <div field="createId" width="120" allowSort="true">制单人</div>
           <div field="createTime" width="120" allowSort="true">制单时间</div>
           <div field="status" width="120" allowSort="true">流程状态</div>
           <div field="handleId" width="120" allowSort="true">当前处理人</div>
           
        </div>
    </div>
</div>
<script type="text/javascript">
    mini.parse();
    RefreshGridData();
    /*
     描述：刷新整个页面
     */
     function RefreshGridData() {
        var grid = mini.get('miniDataGrid');
        var args = mini.decode(decodeURI('${param.args}'));
        //这里可以重新指定自定义的数据装载路径
        grid.setUrl("${pageContext.request.contextPath}/scene/loadListCheckPlanForPage.action");
        grid.load(args);
    } 

    function onOk() {
        var grid = mini.get('miniDataGrid');
        var rows = grid.getSelecteds();
        if (0 == rows.length) {
            mini.alert('请选择数据！');
            return;
        }
        window.CloseOwnerWindow(mini.encode(rows));
    }
</script>
</body>
</html>
