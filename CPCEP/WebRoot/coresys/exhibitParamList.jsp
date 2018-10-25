<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>数据展现配置</title>
<%@include file="../resource.jsp" %>
<style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    #exhibitTree{
		border-bottom:1px solid #ccc;
	}	    
</style>

</head>
<body text="#000000" bgcolor="#FFFFFF">
	<div class='mini-toolbar' style='padding:0;border-bottom-width:0;'>
			<table style='width:100%;'>
			<tr>
	            <td style='white-space:nowrap;width:50%;text-align:left;' id='rightGridToolBar'>
					<a class="mini-button" iconCls="icon-add" plain="true" onclick="CommonOpenDoc();">添加视图</a>
	                <a class='mini-button' iconCls='icon-reload' plain='true' style="float:none;" onclick='window.location.reload();'>刷新</a>
				</td>
	           </tr>
			</table>
	    </div>
	    <!--撑满页面-->
	    <div class="mini-fit">
   		<div id="exhibitTree" class="mini-treegrid"
   			style="width:100%;height:100%;" showTreeIcon="false" url="${pageContext.request.contextPath}/admin/findExhibitListJson.action" treeColumn="modularName" idField="uuid"  parentField="exhibitPid" resultAsTree="false" 
   			expandOnLoad="true" virtualScroll="true">
	            <div property="columns">
	            	<div type="indexcolumn"></div>
	           		<div field="modularName" name="modularName" width="140" headerAlign="center">模块名称</div>
	            	<div field="tableName"  width="140"  headerAlign="center" renderer='CommonRowLink'>表格名称</div>
	            	<div field="createBy"  width="80"  headerAlign="center">创建人 </div>                            
			    	<div field="exhibitPath"  width="250"  headerAlign="center">展现地址 </div>
	            	<div field="remark" width="330"  headerAlign="center">备注</div>
	            </div>
	        </div> 
	        </div>
<script language="JavaScript" type="text/javascript">
	mini.open=function(E){if(!E)return;var C=E.url;if(!C)C="";var B=C.split("#"),C=B[0];if(C&&C[OOoOOl]("_winid")==-1){var A="_winid="+mini._WindowID;if(C[OOoOOl]("?")==-1)C+="?"+A;else C+="&"+A;if(B[1])C=C+"#"+B[1]}E.url=C;E.Owner=window;var $=[];function _(A){try{if(A.mini)$.push(A);if(A.parent&&A.parent!=A)_(A.parent)}catch(B){}}_(window);var D=$[0];return D["mini"]._doOpen(E)}
	function CommonRowLink(cell){
		if(!cell.value) return "";
		return '<a href="javascript:CommonOpenDoc(\''+cell.row.uuid+'\');">' + cell.row.tableName+ '</a>';
	}
	
	function CommonOpenDoc(docid){
		var strPathUrl="";
		if(docid!=null && docid!=""){//更新
			strPathUrl=	"${pageContext.request.contextPath}/admin/queryExhibitById.action?uuid="+docid;
		}else{//新增
			strPathUrl=	"${pageContext.request.contextPath}/admin/queryExhibitById.action";
		}
		mini.open({id:"oWinDlg",title:"数据展示配置",url:encodeURI(strPathUrl),showMaxButton: true,ondestroy: function (action) {
                  location.reload();
                }
		});
	}
	
	
</script>
</body>
</html>
