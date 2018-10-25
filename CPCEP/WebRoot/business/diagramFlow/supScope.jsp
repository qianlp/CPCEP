<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title></title>
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
        body .search_body{border:0px solid #000;overflow:hidden;display:block;padding-top:5px;}
        .search_content,.search_title,.search_field,.search_button{float:left}
        .search_content{width:240px;margin:2px;height:30px;}
        .search_title{width:90px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px}
        .search_field{width:130px;text-align:left}
        .search_button{margin:1px;}
    </style>
    <script type="text/javascript">
        
    </script>
</head>
<body>
	<div id='miniDataGrid' class='mini-datagrid' style="width:100%;height:100%;" showPager="false" idField='uuid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true'>
		<div property='columns'>
			<div type='indexcolumn'  width='50'headerAlign='center' align='center' >排名</div>
     		<div field='supName' width='120'  headerAlign='center' align='left'>供应商</div>
            <div field='score' width='80' headerAlign='center' align='left'>分值</div>
  		</div>
	</div>
<script>
    mini.parse();
    var gDir="${pageContext.request.contextPath}";
    var grid = mini.get("miniDataGrid");
    grid.setUrl(gDir + '/business/flow/findSupScopeList.action');
    grid.load({bFileId:"${param.bFileId}"});
</script>
</body>
</html>