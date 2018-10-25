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
	<div id='miniDataGrid' class='mini-datagrid' style="width:100%;height:100%;" showPager="false" idField='uuid' pageSize='1000' multiSelect='true' showColumnsMenu='true'>
		<div property='columns'>
     		<div field='createBy' width='120'  headerAlign='center' align='left'>供应商</div>
            <div field='contact' width='80' headerAlign='center' align='left'>投标联系人</div>
       		<div field='position' width='80' headerAlign='center' align='left'>职位</div>
       		<div field='phone' width='80' headerAlign='center' align='left'>工位电话</div>
       		<div field='mobile' width='80' headerAlign='center' align='left'>手机</div>
       		<div field='email' width='80' headerAlign='center' align='left'>邮箱</div>
			<div field='createDate' width='80' headerAlign='center' align='center' dateFormat="yyyy-MM-dd HH:mm" >报名时间</div>
  		</div>
	</div>
	<div id='miniDataGrid2' class='mini-datagrid' showModified="false" onupdate="onupdate" style="width:100%;height:100%;display:none;" showPager="false" idField='uuid' pageSize='1000' multiSelect='true' showColumnsMenu='true'>
		<div property='columns'>
     		<div field='name' width='120'  headerAlign='center' align='left'>供应商</div>
     		<div field="contacts" width="80" headerAlign="center" align="center">联系人</div>
			<div field="phon" width="80" headerAlign="center" align="center">联系方式</div>
       		<div field='email' width='80' headerAlign='center' align='left'>邮箱</div>
			<div field='createDate' width='80' headerAlign='center' align='center' dateFormat="yyyy-MM-dd HH:mm" >报名时间</div>
       		<div field='status' width='50' headerAlign='center' align='left' renderer='comStatus' >状态</div>
  		</div>
	</div>
<script>
    mini.parse();
    var gDir="${pageContext.request.contextPath}";
    var grid = mini.get("miniDataGrid");
    var grid2 = mini.get("miniDataGrid2");
    
    if(parent.pMethod=="邀请招标"){
    	grid.hide();
    	grid2.show();
    	grid2.setUrl(gDir + '/business/procurement/loadCandiateForPage.action?type=1&planId='+parent.planId);
    	grid2.load();
    }else{
    	grid.setUrl(gDir + '/business/flow/findSupSignupList.action');
    	grid.load({bid:"${param.bFileUuid}"});
    }
    
    function onupdate(e){
    	$.post(gDir + '/business/flow/findSupSignupList.action',{
    		bid:"${param.bFileUuid}"
    	},function(data){
    		data=mini.decode(data);
    		var gData=grid2.getData();
    		$.each(gData,function(){
    			for(var i=0;i<data.length;i++){
    				if(this.name==data[i].createBy){
    					data[i].status="已报名";
    					grid2.updateRow(this,data[i]);
    				}
    			}
    		})
    	})
    }
    
    function comStatus(cell){
    	if(cell.value){
    		return "<span style='color:green'>"+cell.value+"</span>";
    	}else{
    		return "<span style='color:red'>未报名</span>";
    	}
    }
</script>
</body>
</html>