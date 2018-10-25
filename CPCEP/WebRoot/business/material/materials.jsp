<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String menuId=request.getParameter("menuId");
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>设备材料分类信息维护</title>
    <meta name="description" content="设备材料分类信息维护">
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
        .search_content{width:280px;margin:2px;height:30px;}
        .search_title{width:100px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px}
        .search_field{width:130px;text-align:left}
        .search_button{margin:1px;}
    </style>
    <script type="text/javascript">
        mini.open=function(E) {
                if (!E) return;
                var C = E.url;
                if (!C) C = "";
                var B = C.split("#"),
                    C = B[0];
                if (C && C[O1O1oo]("_winid") == -1) {
                    var A = "_winid=" + mini._WindowID;
                    if (C[O1O1oo]("?") == -1) C += "?" + A;
                    else C += "&" + A;
                    if (B[1]) C = C + "#" + B[1]
                }
                E.url = C;
                E.Owner = window;
                var $ = [];

                function _(A) {
                    try {
                        if (A.mini) $.push(A);
                        if (A.parent && A.parent != A) _(A.parent)
                    } catch (B) {}
                }
                _(window);
                var D = $[0];

                return D["mini"]._doOpen(E)
            }
    </script>
</head>
<body>
<div class="mini-layout" style="width:100%;height:100%;" id="layout">
    <%-- 左侧分类树 --%>
    <div region="west" style="overflow:hidden;border-bottom:0"
         showHeader="false" width="340px" minWidth="200" maxWidth="350"
         showSplitIcon="true">
           <div class="mini-toolbar">
            <a class="mini-button" iconCls="icon-add" id="addCategory" onclick="goCategory('New')">新增类型</a>
            <a class="mini-button" iconCls="icon-edit" onclick="goCategory('Edit')">编辑类型</a>
            <a class="mini-button" iconCls="icon-remove" onclick="delCategories()">删除类型</a>
            <a class="mini-button" iconCls="icon-upload" onclick="goImportFile()">导入</a>
        </div>
        <div class="mini-fit" style="padding:2px;border-top:0;border-left:0;border-right:0;">
            <div id='categoryTree' class='mini-tree' style="height:100%;" showTreeIcon='true' showCheckBox='false' checkRecursive='true' expandOnLoad='0' expandOnDblClick='false'
                 url="${pageContext.request.contextPath}/business/material/findCategoryTree.action" resultAsTree="false"
                 onnodeclick="nodeclick">
            </div>
        </div>
    </div>
    <%--中心表格--%>
    <div region="center"
         style="overflow:hidden;border-bottom:0;border-right:1">
         
        <div class="mini-toolbar">
            <a class="mini-button" iconCls="icon-add" id="addMaterial" onclick="goMaterial('New')">新增</a>
            <a class="mini-button" iconCls="icon-edit" id="editMaterial" onclick="goMaterial('Edit')">编辑</a>
            <a class="mini-button" iconCls="icon-remove" id="delMaterial" onclick="delMaterials()">删除</a>
        </div>
        <div class="search_body" id="search_body">
            <div class="search_content">
                <div class="search_title">设备材料编码：</div>
                <div class="search_field" Operator="@" DataType="text" Item="">
                    <input name="materialCode" id="materialCode" class="mini-textbox">
                </div>
            </div>
            <div class="search_content">
                <div class="search_title">设备材料名称：</div>
                <div class="search_field" Operator="@" DataType="text" Item="">
                    <input name="materialName" id="materialName" class="mini-textbox">
                </div>
            </div>
            <div class="search_button">
                <a class="mini-button" tooltip="清空查询条件" plain="true" iconCls="icon-remove" onclick="ClearForm"></a>
                &nbsp;<a class="mini-button" iconCls="icon-search" onclick="CommonSearch">搜索</a>
            </div>
        </div>
        <div class='mini-fit' style='width:100%;height:100%;'>
            <div id='miniDataGrid' class='mini-datagrid' style="width:100%;height:100%;" borderStyle="border:0;" idField='unid' sizeList='[5,10,15,30]' pageSize='15' multiSelect='true' showColumnsMenu='true'>
                <div property='columns'>
                    <div type='checkcolumn'></div>
                    <div type='indexcolumn' headerAlign='center' align='center' >序号</div>
                    <div field='uuid' visible='false' width='100'  headerAlign='center' align='center'>UUID</div>
                    <div field='materialCode' width='120' headerAlign='center' align='center'>设备材料编码</div>
                    <div field='materialName' width='120' headerAlign='center' align='center'>设备材料名称</div>
                    <div field='categoryUuid' visible='false' width='100'  headerAlign='center' align='center'>分类ID</div>
                    <div field='specModel' width='120' headerAlign='center' align='center'>规格型号</div>
                    <div field='brand' visible='false' width='50'  headerAlign='center' align='center'>品牌</div>
                    <div field='unit' width='50' headerAlign='center' align='center'>单位</div>
                    <div name="action" width="100" headerAlign="center" renderer="renderAction" align='center'>参数项</div>
                    <!-- <div name="history" width="100" headerAlign="center" >历史记录</div> -->
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    mini.parse();
    var gDir="${pageContext.request.contextPath}";
    var grid = mini.get("miniDataGrid");
    var gTree = mini.get("categoryTree");
    <%--var menuId = '<%=menuId%>';--%>
    grid.setUrl(gDir+'/business/material/loadMaterialForPage.action');
    var categoryUuid = "-1";//默认pid为-1
    grid.load({categoryUuid:categoryUuid});
 
    function renderAction(e){
    	var record = e.record;
    	var s ='<a href="javascript:nodeParam(\''+record.uuid+'\')"> 点击查看</a>';
    	return s;
    };
    
     function nodeParam(categoryId){
    	  var strURL="",strTitle="参数项维护",intWidth=800,intHeight=500;
          strURL="${pageContext.request.contextPath}/business/material/categoryParams.jsp?categoryId="+categoryId;
          var oWinDlg=mini.get('oWinDlg');
          if(oWinDlg==null){
              oWinDlg=mini.open({
                  id:"oWinDlg",
                  showFooter:false,
                  headerStyle:"font-weight:bold;letter-spacing:4px"
              });
          }
          oWinDlg.setUrl(strURL);
          oWinDlg.setTitle(strTitle);
          oWinDlg.setWidth(intWidth);
          oWinDlg.setHeight(intHeight);
          oWinDlg.showAtPos("center","middle");
     };

     /*描述:分类管理*/
     function goCategory(e){
         var strURL="",strTitle="",intWidth=400,intHeight=200;
         var myNode = gTree.getSelectedNode();
         if (typeof(myNode)=="undefined") {
             pid="-1";
         }else{
             pid=myNode.id;
         }
         if(e=='New'){
             strTitle="新建类型";
             strURL="${pageContext.request.contextPath}/business/material/categoryOperation.jsp?pid="+pid;
         }else{
        	console.log(myNode);
        	if(!gTree.isLeaf(myNode)){
        		mini.alert("请选择子节点修改！");return;
        	}else{
        		strURL="${pageContext.request.contextPath}/business/material/findCategoryById.action?uuid="+pid;
        	}
            /*   var oRow = grid.getSelecteds();
             if(oRow.length>=1){
                 if(oRow.length == 1){
                     strURL="${pageContext.request.contextPath}/business/material/findCategoryById.action?uuid="+oRow[0].uuid;
                 }else{
                     mini.alert("请只选中一行！");return;
                 }
             }else{
                 mini.alert("请先选一行！");return;
             }  */
         }
         var oWinDlg=mini.get('oWinDlg');
         if(oWinDlg==null){
             oWinDlg=mini.open({
                 id:"oWinDlg",
                 showFooter:true,
                 headerStyle:"font-weight:bold;letter-spacing:4px",
                 footerStyle:"padding:5px;height:25px"
             });
         }
         var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
         strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
         oWinDlg.setUrl(strURL);
         oWinDlg.setTitle(strTitle);
         oWinDlg.setWidth(intWidth);
         oWinDlg.setHeight(intHeight);
         oWinDlg.setFooter(strButton);
         oWinDlg.showAtPos("center","middle");
     }
     //删除子节点
     function delCategories(){
       /*   var delUuids =  $.map(grid.getSelecteds(),function(i){
             return i.uuid;
         }).join("^");
         var gridData = grid.getData();
         console.log(gridData);
         var delParUuids =  $.map(gTree.getSelecteds(),function(i){
              return i.id;
          }).join("^");
         if(gridData.length>0){
         if(delUuids==""){mini.alert("请至少选择一行！");return;}}
         if(gridData.length==0){
         	if(delParUuids==""){
         		mini.alert("请选择节点！");return;
         	}else{
         		delUuids = delParUuids;
         	}
         } */
         var gNode = gTree.getSelecteds();
         console.log(gNode);
         var delUuids=gNode[0].id;
         console.log(mini.encode(delUuids));
         if(!gTree.isLeaf(gNode)){
        	 mini.alert("请先删除子节点！");return;
         }
         console.log(delUuids);
          mini.confirm("确认要删除选定吗？", "操作提示", function(action){
             if(action=="ok"){
                 mini.mask({
                     el: document.body,
                     cls: 'mini-mask-loading',
                     html: '数据删除中...'
                 });
                 $.ajax({
                     url: gDir+"/business/material/deleteCategoryByIds.action",
                     data:{
                         uuid:delUuids
                     },
                     type:"post",
                     success:function(){
                         mini.unmask(document.body);
                         mini.alert("操作成功!","消息提示",function(){
                             grid.reload();
                             gTree.reload();
                         });
                     },
                     error:function () {
                         mini.unmask(document.body);
                     }
                 });
             }
         }); 
     }
    //自定义参数项列
    grid.on("drawcell",function (e) {
        var record = e.record,
            column = e.column;
        //action列，超连接操作按钮
        /*if (column.name == "action") {
            e.cellStyle = "text-align:center";
            e.cellHtml = '<a href="javascript:edit(\'' + record.uuid + '\')">点击查看</a>';
        }*/
        if (column.name == "history") {
            e.cellStyle = "text-align:center";
            e.cellHtml = '<a href="javascript:edit(\'' + record.uuid + '\')">点击查看</a>';
        }
    });
    var delSelect = -1;
    //树节点点击事件
    function nodeclick(e){
        categoryUuid = e.node.id;
        if(!gTree.isLeaf(e.node)){
        	$("#addMaterial").hide();
        	$("#editMaterial").hide();
        	$("#delMaterial").hide();
        	grid.load({});
        }else{
        	$("#addMaterial").show();
        	$("#editMaterial").show();
        	$("#delMaterial").show();
           grid.load({categoryUuid:categoryUuid});
        }
      
    }
    //清空查询条件
    function ClearForm(){
        mini.getbyName("materialCode").setValue("");
        mini.getbyName("materialName").setValue("");
    }
    /*
    描述：查询
    */
    function CommonSearch(){
        var searchArr=[];
        $("#search_body .search_field").each(function(){
            var obj={};
            var name = $($(this).find("input")[0]).attr("id");
            obj.name = name.split("$")[0];
            //obj.name=$($(this).find("input")[0]).attr("name");
            obj.operator=$(this).attr("Operator");
            obj.dataType=$(this).attr("DataType");
            obj.value=mini.getbyName(obj.name).getValue();
            if(obj.value!=""){
                if(obj.dataType == 'date'){
                    obj.value=mini.getbyName(obj.name).getText();
                }
                searchArr.push(obj);
            }
        });
        grid.load({search:mini.encode(searchArr),categoryUuid:categoryUuid});
    }

    /*描述:分类管理*/
    function goMaterial(e){
        var strURL="",strTitle="",intWidth=580,intHeight=250;
        if(e=='New'){
            var myNode = gTree.getSelectedNode();
            if (typeof(myNode)=="undefined") {
                mini.alert("请先选择左侧分类!","消息提示");
                return ;
            }else{
                categoryUuid=myNode.id;
            }
            strTitle="新增";
            strURL="${pageContext.request.contextPath}/business/material/materialOperation.jsp?categoryUuid="+categoryUuid;
        }else{
            var oRow = grid.getSelecteds();
            if(oRow.length>=1){
                if(oRow.length == 1){
                    strURL="${pageContext.request.contextPath}/business/material/findMaterialById.action?uuid="+oRow[0].uuid;
                }else{
                    mini.alert("请只选中一行！");return;
                }
            }else{
                mini.alert("请先选一行！");return;
            }
        }
        var oWinDlg=mini.get('oWinDlg');
        if(oWinDlg==null){
            oWinDlg=mini.open({
                id:"oWinDlg",
                showFooter:true,
                headerStyle:"font-weight:bold;letter-spacing:4px",
                footerStyle:"padding:5px;height:25px"
            });
        }
        var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
        strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
        oWinDlg.setUrl(strURL);
        oWinDlg.setTitle(strTitle);
        oWinDlg.setWidth(intWidth);
        oWinDlg.setHeight(intHeight);
        oWinDlg.setFooter(strButton);
        oWinDlg.showAtPos("center","middle");
    }
    /*
		描述:提交窗口内Form
	*/
    function goSubmit(){
        mini.get('oWinDlg').getIFrameEl().contentWindow.goSubmit();
    }
    /*
		描述:点击提交窗口内Form时,控制“确定”状态
	*/
    function goToolsBtn(status){
        var oButton=mini.get("btnSave");
        oButton.setEnabled(status);
    }
    /*
		描述:关闭窗口
	*/
    function goCloseDlg(name){
        var oWinDlg=mini.get(name);
        oWinDlg.setUrl("about:blank");
        $(oWinDlg.getBodyEl()).html("");
        $(oWinDlg.getFooterEl()).html("");
        oWinDlg.hide();
    }
    function goReload() {
        mini.alert("操作成功!","消息提示",function(){
            goCloseDlg("oWinDlg");
            grid.reload();
            gTree.reload();
        });
    }
    function delMaterials(){
        var delUuids =  $.map(grid.getSelecteds(),function(i){
            return i.uuid;
        }).join("^");
        if(delUuids==""){mini.alert("请至少选择一行！");return;}
        mini.confirm("确认要删除选定吗？", "操作提示", function(action){
            if(action=="ok"){
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '数据删除中...'
                });
                $.ajax({
                    url: gDir+"/business/material/deleteMaterialByIds.action",
                    data:{
                        uuid:delUuids
                    },
                    type:"post",
                    success:function(){
                        mini.unmask(document.body);
                        mini.alert("操作成功!","消息提示",function(){
                            grid.reload();
                            gTree.reload();
                        });
                    },
                    error:function () {
                        mini.unmask(document.body);
                    }
                });
            }
        });

    }
	    /*
		描述:导入数据
	*/
	function goImportFile(){
		var strURL="${pageContext.request.contextPath}/business/material/importExcel.jsp",strTitle="数据导入",intWidth=400,intHeight=350;
		var oWinDlg=mini.get('oWinDlg');
		if(oWinDlg==null){
			oWinDlg=mini.open({
				id:"oWinDlg",
				showFooter:true,
				headerStyle:"font-weight:bold;letter-spacing:4px",
				footerStyle:"padding:5px;height:25px"
			});
		}
		var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
		strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
		oWinDlg.setUrl(strURL);
		oWinDlg.setTitle(strTitle);
		oWinDlg.setWidth(intWidth);
		oWinDlg.setHeight(intHeight);
		oWinDlg.setFooter(strButton);
		oWinDlg.showAtPos("center","middle");
	}
</script>
</body>
</html>