<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>按钮模板配置</title>
<%@include file="../resource.jsp" %>
<style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    #buttonTree{
		border-bottom:1px solid #ccc;
	}	    
</style>

</head>
<body text="#000000" bgcolor="#FFFFFF">

<form action="">
	<div class='mini-toolbar' style='padding:0;border-bottom-width:0;'>
			<table style='width:100%;'>
			<tr>
	            <td style='white-space:nowrap;width:50%;text-align:right;' id='rightGridToolBar'>
					<a class="mini-button" iconCls="icon-add" plain="true" onclick="CommonOpenDoc();">添加按钮</a>
	                <a class='mini-button' iconCls='icon-reload' plain='true' style="float:none;" onclick='window.location.reload();'>刷新</a>
				</td>
	           </tr>
			</table>
	    </div>
	    <!--撑满页面-->
   		<div id="buttonTree" class="mini-treegrid"
   			style="width:100%;height:500px;" showTreeIcon="false" url="${pageContext.request.contextPath}/admin/findButtonListJson.action" treeColumn="uuid" idField="uuid"  parentField="parentId" resultAsTree="false" 
   			expandOnLoad="true" virtualScroll="true">
	            <div property="columns">
	            	<div type="indexcolumn"></div>
	           		<div field="btnName" width="140" headerAlign="center" renderer='CommonRowLink'>按钮名称</div>
	            	<div field="btnTitle"  width="140"  headerAlign="center">按钮提示</div>
	            	<div field="createBy"  width="80"  headerAlign="center">创建人 </div>                            
			    	<div field="createDate"  width="120"  headerAlign="center">创建时间 </div>
	            	<div field="remark" width="330"  headerAlign="center">备注</div>
	            	<div field="uuid" width="3" name="uuid" visible="false"></div>
	            </div>
	        </div> 
	</form>
	
<script language="JavaScript" type="text/javascript">
	function CommonRowLink(cell){
		if(!cell.value) return "";
		return '<a href="javascript:CommonOpenDoc(\''+cell.row.uuid+'\');">' + cell.row.btnName+ '</a>';
	}
	
	function CommonOpenDoc(docid){
		var strPathUrl="";
		if(docid!=null && docid!=""){//更新
			strPathUrl=	"${pageContext.request.contextPath}/admin/queryButtonById.action?uuid="+docid;
		}else{//新增
			strPathUrl=	"${pageContext.request.contextPath}/admin/queryButtonById.action";
		}
		mini.open({id:"oWinDlg",title:"按钮维护配置",url:encodeURI(strPathUrl),showMaxButton: true,ondestroy: function (action) {
                  location.reload();
                }
		});
	}
</script>
</body>
</html>
