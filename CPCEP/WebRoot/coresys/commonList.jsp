<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>信息列表</title>
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
	width: calc(100% - 2px);
}
body .search_body{overflow:hidden;display:block;}
	.search_content,.search_title,.search_field,.search_button{float:left}
	.search_content{width:240px;margin:2px;height:30px;}
	.search_title{width:90px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px}
	.search_field{width:130px;text-align:left}
	.search_button{margin:1px;}
	.search_field .mini-radiobuttonlist{padding-top:3px}
	.mini-grid-emptyText{
		font-size:15px;
		color:#ccc;
		height:200px;
		text-align:center;
	}
</style>
<script src="${pageContext.request.contextPath}/js/miniui/scripts/bootView.js" type="text/javascript"></script>
<script type="text/javascript">
	var menuId = "${menu.uuid }";
	var menuName= "${menu.menuName }";
	var modoName = "${menu.entityClsName}";
	var gCurUser = "${session.user.userName}";
	var gUserID = "${session.user.uuid}";
	var gPosNo="${session.user.posNo}";
	var gPosName="${session.user.posName}";
	var gDir="${pageContext.request.contextPath}";
	function btnEvent(obj){//按钮操作方法
		var urlPath=obj.fm;	//访问的表单
		if(obj.action=="del"){
			delDocument();
			return;
		}
		if(urlPath){
			if(urlPath.indexOf("?")==-1){
				urlPath += "?menuId="+menuId;
			}else{
				urlPath += "&menuId="+menuId;
			}
			urlPath=encodeURI(urlPath);
			if(obj.action=="edit"){//修改
				this.EditDocument(urlPath);
			}else{//新增调用
				this.CommonOpenDoc(urlPath);
			}
		}else{
			alert("请管理员在新建按钮函数中添加表单名称！");return;
		}
	}
	
	function CommonRowLink(cell){
		urlPath=gDir+"/admin/findDocById.action?menuId="+menuId+"&type=read&uuid="+cell.row.uuid+"&modoName="+modoName;
		return '<a href="javascript:CommonOpenDoc(\''+urlPath+'\');">' + cell.value + '</a>';
	}
	
	//编辑文档
	function EditDocument(urlPath){
		var strDocID = GetDocID();
		if(strDocID==""){mini.alert("您没有选择文档！");return;}
		if(strDocID.indexOf("^")>0){mini.alert("请您选择一个文档！");return;}
		var docs=grid.getSelecteds();
		CommonOpenDoc(urlPath+"&uuid="+strDocID+"&modoName="+modoName);
	}
	
	//文档流程作废
	function docInvalid(){
		var strDocID = GetDocID();
		if(strDocID==""){mini.alert("您没有选择文档！");return;}
		if(strDocID.indexOf("^")>0){mini.alert("请您选择一个文档！");return;}
		mini.confirm("确认要作废选定文档吗？", "操作提示", function(action){
			if(action=="ok"){
				mini.mask({
		            el: document.body,
		            cls: 'mini-mask-loading',
		            html: '文档作废中...'
		    	});
				var path=gDir+"/admin/docInvalid.action";
				$.post(path,{
					docId:strDocID,
					modoName:modoName
				},function(data){
					if(data=="ok"){
						mini.unmask(document.body);
						mini.alert("已作废！");
						refreshGridData();
					}
				})
			}
		})
	}
	
	//根据id参数删除文档
	function delDocument(){
		var strDocID = GetDocID();
		if(strDocID==""){mini.alert("您没有选择文档！");return;}
		mini.confirm("确认要删除选定文档吗？", "操作提示", function(action){
			if(action=="ok"){
				mini.mask({
		            el: document.body,
		            cls: 'mini-mask-loading',
		            html: '数据删除中...'
		    	});
				$.ajax({
					url: gDir+"/admin/deleteModelId.action?uuid="+strDocID+"&className="+modoName,
					success:function(){
						mini.unmask(document.body);
						mini.alert("删除成功！");
						refreshGridData();
					}
				});
			}
		});
		
	}
	
	//根据实体类model删除文档
	function delmodelDocument(){
		var strDocID = GetDocID();
		if(strDocID==""){mini.alert("您没有选择文档！");return;}
		mini.confirm("确认要删除选定文档吗？", "操作提示", function(action){
			if(action=="ok"){
				mini.mask({
		            el: document.body,
		            cls: 'mini-mask-loading',
		            html: '数据删除中...'
		    	});
				$.ajax({
					url: gDir+"/admin/deleteModel.action?uuid="+strDocID+"&className="+modoName,
					success:function(){
						mini.unmask(document.body);
						mini.alert("删除成功！");
						refreshGridData();
					}
				});
			}
		});
		
	}
	
	//刷新页面数据
	function refreshGridData(){
		grid.reload();
	}
	
	function editRight() {
		var strDocID = GetDocID();
		var strURL = gDir+"/coresys/rightEdit.jsp?docId="+strDocID+"&modoName="+modoName;
		miniOpen({url:strURL,title:"权限维护"});
	}
	
	function showWfInfo(){
		var strDocID = GetDocID();
		var strURL = gDir+"/workflow/wfInfoView.jsp?docId="+strDocID;
		miniOpen({url:strURL,title:"流程查看",width:850,height:600});
	}
	
	function miniOpen(obj) {
		var strDocID = GetDocID();
		if(strDocID==""){mini.alert("您没有选择文档！");return;}
		if(strDocID.indexOf("^")>0){mini.alert("请您选择一个文档！");return;}
		var strURL = obj.url, strTitle = obj.title, intWidth = 700, intHeight = 500;
		if(obj.width){
			intWidth = obj.width;
		}
		if(obj.height){
			intHeight =obj.height;
		}
		var oWinDlg = mini.get('oWinDlg');
		if (oWinDlg == null) {
			oWinDlg = mini.open({
				id : "oWinDlg",
				showMaxButton : true,
				headerStyle : "font-weight:bold;letter-spacing:4px",
				footerStyle : "padding:5px;height:25px"
			});
		}
		oWinDlg.setUrl(strURL);
		oWinDlg.setTitle(strTitle);
		oWinDlg.setWidth(intWidth);
		oWinDlg.setHeight(intHeight);
		oWinDlg.showAtPos("center", "middle");
	}
	
	function GetDocID(){
		var arrID=$.map(grid.getSelecteds(),function(i){
		    if(i.uuid == null || i.uuid == undefined)
		        return i.id;
			return i.uuid;
		});
		return arrID.join("^");
	}
	/*
		描述：
			用于标识流程状态。
		参数：
			cell：单元格对象
		*/
	function CommonFlowStatus(cell){
		var strColor='0f0',strStatus=cell.value;
		switch(parseInt(cell.value,10)){
            case 9 :
                strStatus='待审核';
                strColor='00B83F';
                break;
            case 8 :
                strStatus='审核失败';
                strColor='f00';
                break;
			case 0:
				strStatus='草稿';
				strColor='000';
				break;
			case 1:
				var arrData=grid.getRow(cell.rowIndex)['curUser'].split(',');
				if($.inArray(gCurUser,arrData)>-1){
					strColor='f00';
					strStatus='待审批';
				}else{
					strColor='00f';
					strStatus='进行中';
				}
				break;
			case 2:
				strColor='000';
				strStatus='已审批';
				break;
			case 3:
				strColor='22DD48';
				strStatus='已更新';
				break;
			case 4:
				strColor='2BD5D5';
				strStatus='已发送';
				break;
			case 5:
				strColor='f00';
				strStatus='已作废';
				break;
			default:
		}
		return "<span style='color:#"+strColor+"'>" + strStatus + "</span>";
	}
	
	//常规状态定义
	function CommonStatus(cell){
		var strStatus=cell.value;
		var status="";
		if(strStatus=="1"){
			status="保存";
		}else if(strStatus=="2"){
			status="提交";
		}else if(strStatus=="3"){
			status="待启用";
		}else if(strStatus=="4"){
			status="使用中";
		}else if(strStatus=="94"){
			status="已通知";
		}
		else if(strStatus=="95"){
			status="已上传会议成果";
		}
		else if(strStatus=="96"){
			status="已上传";
		}
		else if(strStatus=="98"){
			status="作废";
		}else if(strStatus=="99"){
			status="删除";
		}else{
			status=strStatus;
		}
		return "<span>" + status + "</span>";
	}


    //根据id参数删除文档
    function checkSupplier(checkStatus){
        var strDocID = GetDocID();
        if(strDocID==""){mini.alert("您没有选择要审核的供应商！");return;}
        mini.confirm("确认要审核选定供应商吗？", "操作提示", function(action){
            if(action=="ok"){
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '审核中...'
                });
                $.ajax({
                    url: gDir+"/business/check.action?id="+strDocID + "&checkStatus=" + checkStatus,
                    success:function(){
                        mini.unmask(document.body);
                        mini.alert("操作成功！");
                        refreshGridData();
                    }
                });
            }
        });

    }

    function checkSignup(checkStatus){
        var arrID=$.map(grid.getSelecteds(),function(i){
            if(i.signupId == null || i.signupId == undefined)
                return i.id;
            return i.signupId;
        });
        var strDocID = arrID.join("*");
        if(strDocID==""){mini.alert("您没有选择要审核的报名!");return;}
        mini.confirm("确认要审核选定的报名吗?", "操作提示", function(action){
            if(action=="ok"){
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '审核中...'
                });
                $.ajax({
                    url: gDir+"/business/signCheck.action?id="+strDocID + "&checkStatus=" + checkStatus,
                    success:function(){
                        mini.unmask(document.body);
                        mini.alert("操作成功！");
                        refreshGridData();
                    }
                });
            }
        });
    }

	function contentDetail(cell) {
        var grid = mini.get("miniDataGrid");
        var url = grid.detailUrl;
        if(url == null || url == undefined || url == "") {
            console.error("detail action is null!");
            return ;
		}
        if(url.indexOf("?") == -1)
            url = url + "?";
        else
            url = url + "&";
        urlPath = gDir + url + "menuId="+menuId+"&type=read&uuid="+cell.row.uuid+"&modoName="+modoName;
        return '<a href="javascript:CommonOpenDoc(\''+urlPath+'\');">' + cell.value + '</a>';
	}

	function traderDetail(cell) {
        var grid = mini.get("miniDataGrid");
        var url = grid.detailUrl;
        if(url == null || url == undefined || url == "") {
            console.error("detail action is null!");
            return ;
        }
        if(url.indexOf("?") == -1)
            url = url + "?";
        else
            url = url + "&";
        urlPath = gDir + "/business/signup.action?" + "menuId="+menuId+"&type=read&supplier=" + cell.row.supplier + "&check=check&id="+cell.row.id+"&modoName="+modoName;
        return '<a href="javascript:CommonOpenDoc(\''+urlPath+'\');">' + cell.value + '</a>';
	}

    function openMiniWindow(url, title) {
        console.log(title);
        var intWidth=800,intHeight=500;
        var oWinDlg = mini.get('oWinDlg');
        if(oWinDlg == null){
            oWinDlg = mini.open({
                id:"oWinDlg",
                showFooter:false,
                headerStyle:"font-weight:bold;letter-spacing:4px"
            });
        }
        oWinDlg.setUrl(url);
        oWinDlg.setTitle(title);
        oWinDlg.setWidth(intWidth);
        oWinDlg.setHeight(intHeight);
        oWinDlg.showAtPos("center","middle");
    }
</script>
${menu.menuHtmlHead }

<script>
function CommonOpenDoc(strPathUrl){
		winH=screen.height-100;//高度
		winH=winH==0?(screen.height-100):winH;
		winW=screen.width-20;//宽度
		winW=winW==0?(screen.width-20):winW;
		if(winH>0){var T=(screen.height-100-winH)/2}else{var T=0}
		if(winW>0){var L=(screen.width-20-winW)/2}else{var L=0}
		var pstatus="height="+winH+",width="+winW+",top="+T+",left="+L+",toolbar=no,menubar=no,location=no,status=yes,resizable=yes,scrollbars=yes";
		window.open(strPathUrl,'_blank',pstatus);
	}
</script>
</head>

<body>
	<c:if test="${not empty menu.pageSearchAddress}">
		<jsp:include page="..${menu.pageSearchAddress}" />
	</c:if>
	${menu.menuHtmlSearch}
	<!--功能按钮-->
	<c:if test="${not empty menu.menuToBtn}">
		<div class='mini-toolbar' style='padding:0;border-bottom-width:0'>
			<table style='width:100%;'>
				<tr>
					<td style='width:100%;white-space:nowrap;' id='leftGridToolBar'><c:forEach items="${menu.menuToBtn }" var="btn" varStatus="index">
							<a class="mini-button" iconCls="${btn.btnIcon }" tooltip="${btn.btnTitle }" plain="true" onclick="${btn.btnFunction }" ${btn.btnStyle }>${btn.btnName }</a>
						</c:forEach></td>
					<td style='white-space:nowrap;' id='rightGridToolBar'><a class='mini-button' iconCls='icon-reload' plain='true' onclick='refreshGridData()'>刷新</a></td>
				</tr>
			</table>
		</div>
	</c:if>
	${menu.menuHtmlBody }
</body>

</html>
