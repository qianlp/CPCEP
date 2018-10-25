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
			<div type='indexcolumn'  width='50'headerAlign='center' align='center' >序号</div>
     		<div field='supName' width='120'  headerAlign='center' align='left'  renderer='openPage'>供应商</div>
            <div field='issueType' width='80' headerAlign='center' align='center'>问题类型</div>
            <div field='feedBack' width='80' headerAlign='center' align='center' renderer='getStatus'>反馈状态</div>
  		</div>
	</div>
<script>
    mini.parse();
    var gDir="${pageContext.request.contextPath}";
    var grid = mini.get("miniDataGrid");
    grid.setUrl(gDir + '/business/flow/findBidReturn.action');
    grid.load({code:"${param.code}"});
    
    function openPage(cell){
        var clarifyUUID = cell.row.uuid;
        var url =gDir+'/business/techEvalClarify/showClarifyPage.action?uuid='+clarifyUUID;
        return '<a  href="javascript:openMini(\''+url+'\', \'澄清反馈\');" >' + cell.value + '</a>';
    }

 
	function openMini(url,title){
	    mini.open({
			title:title,
			url:url,
		    id:"oWinDlg",
            showFooter:false,
			width:600,
			height:500,
            headerStyle:"font-weight:bold;letter-spacing:4px",
			ondestroy: function (data) {
			grid.reload();	
			}
        });
    }
    
    function getStatus(cell){
        var strColor='0f0',feedBack=cell.value;
   
        if(isNull(feedBack)){
             strColor='f00';
            strStatus='未处理';
        }else{
            strColor='0f0';
            strStatus='已处理';
        }
        
        
        return "<span style='color:#"+strColor+"'>" + strStatus + "</span>";
    }
    
    function isNull(str){
        return str == null || str == undefined || str=="";
    }
</script>
</body>
</html>