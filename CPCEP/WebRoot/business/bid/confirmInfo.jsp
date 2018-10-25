<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>定标</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/loaders.css"/>
    <link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/bootstrap-theme.css"/>
    <link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/bootstrap.flow.min.css"/>
    <link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/form.css"/>
    <%@include file="../../resource.jsp" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/ckeditor/config.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>

    <script>
        var gDir = "${pageContext.request.contextPath}";
        var gLoginUser = "${sessionScope.user.userName}";
    </script>
    <%--<script src="../../js/form/noFlow.js" type="text/javascript"></script>--%>
</head>
<body text="#000000" bgcolor="#FFFFFF" style='padding:0px;width:100%;height:100%;background:#f3f3f3;'>
<div id="bg"></div>
<div class="loader" id="bg-loader">
    <div class="loader-inner ball-spin-fade-loader">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
    </div>
</div>
<form method="post" action="${pageContext.request.contextPath}/business/bidding/confirm/saveOrUpdate.action" name="form1"
      id="form1">
    <div style="display:none">
        <input name="purchaseApplyUuid" class="mini-textbox" value="${view.purchaseApplyUuid}"/>
        <input name="bidId" class="mini-textbox" value="${view.bidId}"/>
        <input name="confirmId" class="mini-textbox" value="${view.confirmId}"/>
        <input name="biddingId" class="mini-textbox" value="${view.biddingId}"/>
        <input name="supplierId" class="mini-textbox" value="${view.supplierId}"/>
        <textarea  name="message" id="message" >${view.message}</textarea>
        <input name="devices" class="mini-textbox" value=""/>
        <input name="baks" class="mini-textbox" value=""/>
        <input name="tools" class="mini-textbox" value=""/>
        <input name="details" class="mini-textbox" value=""/>
        <input name="menuId" value="40289f4261c3066e0161c31c29bc0018" />
        <input name="parentDocId" value="${view.bidId}" />
		<input name="catalogId" value="${view.bidId}" />
		<input name="curDocId" value="${view.bidId}" />
    </div>
    <div id="PageBody">
        <div class="col-md-7" name="FormMode" id="FormMode"
             style="width:100%;margin:auto;float:none;">
            <div class="widget-container fluid-height col-md-7-k"
                 style="height:auto;border-radius:4px;">
                <div class="mbox-header">
                    <span class="form-title" style="height:100%;line-height:45px;">
                        定标内容
                    </span>
                </div>
                <div class="mbox-body" style="height:100%;padding:10px;">
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>基本信息</b></legend>
                        <table style="width:95%;margin:5px;" id="tab">
                            <tr>
                                <td class="td_left">招标编号：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinCode" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${view.code}"/>
                                </td>
                                <td class="td_left">招标名称：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinName" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${view.name}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">定标时间：</td>
                                <td class="td_right">
                                    <c:if test="${view.confirmTime==null}">
                                        <input name="confirmTime" allowInput="false" readonly="true"
                                               value="<%=(new java.util.Date()).toLocaleString()%>"
                                               class="mini-datepicker" style="width:99%;" title="定标时间">
                                    </c:if>
                                    <c:if test="${view.confirmTime!=null}">
                                        <input name="confirmTime" allowInput="false" readonly="true"
                                               value="${view.confirmTime}" class="mini-datepicker"
                                               style="width:99%;" title="定标时间">
                                    </c:if>
                                </td>
                                <td class="td_left">招标文件版本号：</td>
                                <td class="td_right">
                                    <input name="biddingFileReleaseVersion" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true" value="${view.version}"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>候选中标供应商</b></legend>
                        <div id="supplierGrid" class="mini-datagrid" style="width:100%;height:300px;"
                             onselectionchanged="onSelectionChanged" ondrawcell="onDrawSupplierGridCell"
                             showPager="false" multiSelect="false" >
                            <div property="columns">
                                <div type="checkcolumn">中标单位</div>
                                <div field="name"width="100" headerAlign="center" align="center">供应商名称</div>
                                <div field="status" width="120"  headerAlign="center" align="center">技术评标</div>
                                <div field="thirdTotalPrice" width="120" headerAlign="center" align="center">最终竞价</div>
                                <div field="finalPrice" width="120" headerAlign="center" align="center">最终定价</div>
                                <div type="indexcolumn" headerAlign="center" align ="center">排名</div>
                                <%--<div field="backup" width="120" allowSort="false" headerAlign="center" align="center" >备注</div>--%>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>供应商报价</b></legend>
                        <div id="totalPrice" class="mini-datagrid"
                             allowCellEdit="true" allowCellSelect="true" oncellbeginedit="onTotalPriceBeginEdit"
                             ondrawcell="onDrawTotalPriceCell" oncellcommitedit="onTotalPriceCellCommitEdit"
                             showSummaryRow="true" ondrawsummarycell="onDrawTotalPriceSummaryCell"
                             style="width:100%;height:auto;" showPager="false">
                            <div property="columns">
                                <div field="name" width="80" headerAlign="center" align="center">设备名称</div>
                                <div field="other" width="80" headerAlign="center" align="center"></div>
                                <div field="firstPrice" width="80" headerAlign="center" align="center">投标报价
                                </div>
                                <div field="secondPrice" width="80" headerAlign="center" align="center">澄清报价
                                </div>
                                <div field="thirdPrice" width="80" headerAlign="center" align="center">竞价报价
                                </div>
                                <div field="finalPrice" width="80" headerAlign="center" align="center">最终定价
                                    <input property="editor" class="mini-textbox" style="width:100%;"
                                           vtype="float"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>设备分项报价</b></legend>
                        <legend style="margin-left:10px;">单位：元</legend>
                        <div id="devices" class="mini-datagrid" style="width:100%;height:200px;"
                             url="${pageContext.request.contextPath}/business/bid/loadMaterialPrice.action"
                             idField="uuid" allowCellEdit="true" allowCellSelect="true" allowCellwrap="true"
                             ondrawcell="onDrawDevicesCell"
                             showPager="false" showSummaryRow="true" ondrawsummarycell="onDrawSummaryCell"
                        >
                            <div property="columns">
                                <div field="index" type="indexcolumn">序号</div>
                                <div field="uuid" visible="false"></div>
                                <div field="materialPriceUuid" visible="false"></div>
                                <div field="materialName" width="80" headerAlign="center">设备名称</div>
<!--                                 <div field="brand" width="80" headerAlign="center">品牌</div> -->
                                <div field="specModel" width="80" headerAlign="center">规格</div>
                                <div field="unit" width="80" headerAlign="center">单位</div>
                                <div field="num" width="80" headerAlign="center">数量</div>
                                <div header="投标报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="firstUnitPrice" width="80" headerAlign="center">单价
                                        </div>
                                        <div field="sumFirstUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="澄清报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="secondUnitPrice" width="80" headerAlign="center">单价
                                        </div>
                                        <div field="sumSecondUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="竞价报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="thirdUnitPrice" width="80" headerAlign="center">单价
                                        </div>
                                        <div field="sumThirdUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="最终定价" headerAlign="center">
                                    <div property="columns">
<!--                                         <div name="detail" width="80" headerAlign="center" renderer="onDeviceAttr">设备分项</div> -->
                                        <div field="finalUnitPrice" width="80" headerAlign="center">单价
                                        	<input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumFinalUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>备品备件分项报价</b></legend>
                        <legend style="margin-left:10px;">单位：元</legend>
                        <div id="baks" class="mini-datagrid" style="width:100%;height:200px;"
                             url="${pageContext.request.contextPath}/business/bid/loadMaterialPrice.action"
                             idField="uuid" allowCellEdit="true" allowCellSelect="true" allowCellwrap="true"
                             ondrawcell="onDrawDevicesCell"
                             showPager="false" showSummaryRow="true" ondrawsummarycell="onDrawSummaryCell"
                        >
                            <div property="columns">
                                <div field="index" type="indexcolumn">序号</div>
                                <div field="uuid" visible="false"></div>
                                <div field="materialPriceUuid" visible="false"></div>
                                <div field="materialName" width="80" headerAlign="center">设备名称</div>
<!--                                 <div field="brand" width="80" headerAlign="center">品牌</div> -->
                                <div field="specModel" width="80" headerAlign="center">规格</div>
                                <div field="unit" width="80" headerAlign="center">单位</div>
                                <div field="num" width="80" headerAlign="center">数量</div>
                                <div header="投标报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="firstUnitPrice" width="80" headerAlign="center">单价
                                        </div>
                                        <div field="sumFirstUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="澄清报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="secondUnitPrice" width="80" headerAlign="center">单价
                                        </div>
                                        <div field="sumSecondUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="竞标报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="thirdUnitPrice" width="80" headerAlign="center">单价
                                        </div>
                                        <div field="sumThirdUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="最终定价" headerAlign="center">
                                    <div property="columns">
                                        <div field="finalUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumFinalUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>专用工具分项报价</b></legend>
                        <legend style="margin-left:10px;">单位：元</legend>
                        <div id="tools" class="mini-datagrid" style="width:100%;height:200px;"
                             url="${pageContext.request.contextPath}/business/bid/loadMaterialPrice.action"
                             idField="uuid" allowCellEdit="true" allowCellSelect="true" allowCellwrap="true"
                             ondrawcell="onDrawDevicesCell"
                             showPager="false" showSummaryRow="true" ondrawsummarycell="onDrawSummaryCell"
                        >
                            <div property="columns">
                                <div field="index" type="indexcolumn">序号</div>
                                <div field="uuid" visible="false"></div>
                                <div field="materialPriceUuid" visible="false"></div>
                                <div field="materialName" width="80" headerAlign="center">设备名称</div>
<!--                                 <div field="brand" width="80" headerAlign="center">品牌</div> -->
                                <div field="specModel" width="80" headerAlign="center">规格</div>
                                <div field="unit" width="80" headerAlign="center">单位</div>
                                <div field="num" width="80" headerAlign="center">数量</div>
                                <div header="投标报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="firstUnitPrice" width="80" headerAlign="center">单价
                                        </div>
                                        <div field="sumFirstUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="澄清报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="secondUnitPrice" width="80" headerAlign="center">单价
                                        </div>
                                        <div field="sumSecondUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="竞标报价" headerAlign="center">
                                    <div property="columns">
                                        <div field="thirdUnitPrice" width="80" headerAlign="center">单价
                                        </div>
                                        <div field="sumThirdUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                                <div header="最终定价" headerAlign="center">
                                    <div property="columns">
                                        <div field="finalUnitPrice" width="80" headerAlign="center">单价
                                            <input property="editor" class="mini-textbox" style="width:100%;"
                                                   vtype="float"/>
                                        </div>
                                        <div field="sumFinalUnitPrice" width="80" headerAlign="center">合价</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <%--<fieldset style="margin: 10px;">
                        <legend style="margin-left:10px;"><b>项目明细汇总标</b></legend>
                        <div id="details" class="mini-datagrid" style="width:100%;height:200px;"
                             idField="uuid" allowCellEdit="true" allowCellSelect="true" allowCellwrap="true"
                             showPager="false" oncellcommitedit="onDetailPriceCellCommitEdit" ondrawcell="onDrawDetailCell"
                        >
                            <div property="columns">
                                <div field="rowId" name="rowId" headerAlign="center">序号</div>
                                <div field="uuid" visible="false"></div>
                                <div field="materialPriceUuid" visible="false"></div>
                                <div field="materialName" name="materialName" width="80" headerAlign="center">材料设备名称</div>
                                <div field="materialAttrName" width="80" headerAlign="center">分项设备名称</div>
                                <div field="norms" width="80" headerAlign="center">规格型号</div>
                                <div field="brand" width="80" headerAlign="center">品牌</div>
                                <div field="count" width="80" headerAlign="center">数量</div>
                                <div field="price" width="80" headerAlign="center">单价</div>
                                <div field="sumPrice" width="80" headerAlign="center">合价</div>
                                <div field="finalUnitPrice" width="80" headerAlign="center">最终单价
                                    <input property="editor" class="mini-textbox" style="width:100%;"
                                           vtype="float"/>
                                </div>
                                <div field="sumFinalUnitPrice" width="80" headerAlign="center">最终合价</div>
                            </div>
                        </div>
                    </fieldset>--%>
                </div>
            </div>
            <script src="${pageContext.request.contextPath}/js/file/upLoad.js" type="text/javascript"></script>
   	
   	<script>
		var menuId = $("[name=menuId]").val();
		var gLoginUser="${sessionScope.user.userName}";
		var gUserId="${sessionScope.user.uuid}";
		var gOrgIds="${sessionScope.orgIds}";
		var curDocId = $("[name=curDocId]").val();
		var catalogId = $("[name=catalogId]").val();
		var parentDocId = $("[name=curDocId]").val();
		
		/*
		function randomString(len) {
      	　　len = len || 32;
      	　　var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    
      	　　var maxPos = $chars.length;
      	　　var pwd = '';
      	　　for (i = 0; i < len; i++) {
      	　　　　pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
      	　　}
      	　　return pwd;
      }
	  if($("[name=curDocId]").val() == null || $("[name=curDocId]").val()==""){
		  curDocId = randomString(32);
		  $("[name=curDocId]").val(curDocId);
	  }
	  if($("[name=catalogId]").val() == null || $("[name=catalogId]").val()==""){
		  catalogId = randomString(32);
		  $("[name=catalogId]").val(catalogId); 
	  }
	  */
   	</script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/file/upload.css">
	<script src="${pageContext.request.contextPath}/js/file/dropzone.js" type="text/javascript"></script>
	<div class="col-md-7" name="FormMode" id="FileMode"
		style="width:100%;margin:20px auto;float:none;">
		<div class="widget-container fluid-height col-md-7-k"
			style="height:auto;border-radius:4px;padding-bottom:3px;">
			<div class="mbox-header">
				<span class="form-title" style="height:100%;line-height:45px;">
					附件 </span>
			</div>
			<div class="mbox-body" style="padding:5px;">
			</div>
		</div>
	</div>
            <br />
            <div class="widget-container fluid-height col-md-7-k"
                 style="height:auto;border-radius:4px;">
                <div class="mbox-header">
			<span class="form-title"
                  style="height:100%;line-height:45px;text-align:center;"> 中标通知信息</span>
                </div>
                <div class="mbox-body" id="mbox-body" style="padding:5px;width:100%">
                    <table cellspacing="1" cellpadding="1" id="tab2"
                           style="width:100%;margin:0 auto;">
                        <tr>
                            <td class="td_left" colspan=4>
                                <div id='editor'
                                     style="overflow:hidden;"></div>
                                <div id='read'
                                     style='color:black;padding-left:15px;width:90%;display:none'>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
    	</div>
    </div>
    <div class="host-footer">
        <div class="footer-bg"></div>
        <div class="footer-area">
            <div class="pull-left" id="btnContL">
            </div>
            <div class="pull-right" id="btnContR">
            </div>
        </div>
    </div>
    <div style="display: none;">
        <input name="createUuid" class="mini-textbox"  value=""/>
        <input name="finalTotalPrice" class="mini-textbox"  value=""/>
        <input name="transportFinalPrice" class="mini-textbox"  value=""/>
        <input name="techFinalPrice" class="mini-textbox"  value=""/>
        <input name="techFirstPrice" class="mini-textbox" value="">
        <input name="techSecondPrice" class="mini-textbox" value="">
        <input name="techThirdPrice" class="mini-textbox" value="">
        <input name="transportFirstPrice" class="mini-textbox" value="">
        <input name="transportSecondPrice" class="mini-textbox" value="">
        <input name="transportThirdPrice" class="mini-textbox" value="">
    </div>
    <script>
        var gArrbtn = [{
            id: "btnSave",
            text: "保&nbsp;&nbsp;存",
            style: "btn-primary",
            align: "right",
            event: "toSave()"
        }, {
            id: "btnClose",
            text: "关&nbsp;&nbsp;闭",
            style: "btn-default",
            align: "right",
            event: "toClose()"
        }];
        <c:if test="${role == 'view'}">
        gArrbtn = [{
            id: "btnClose",
            text: "关&nbsp;&nbsp;闭",
            style: "btn-default",
            align: "right",
            event: "toClose()"
        }];
        </c:if>
        var dateInputs = [];
        var gForm = document.forms[0];
        var gCkEditor = null;
        var gIsRead=false;
        // 自动调整页面宽度
        function changeDivHeight() {
            $("#PageBody").css({
                "width": $(window).width() - 120
            });
            $(".footer-area").css({
                "width": $(window).width() - 120
            });
        }
        window.onresize = function () {
            changeDivHeight();
        };
        // 动态输出按钮
        function dynamicCreateBtn() {
            $.each(gArrbtn, function () {
                var btnStr = "<button onclick=\"" + this.event
                    + "\" class=\"btn btn-md " + this.style + "\" type=\"button\">"
                    + this.text + "</button>"
                if (this.align == "right") {
                    $("#btnContR").append(btnStr);
                } else {
                    $("#btnContL").append(btnStr);
                }
            })
        }

        function loadingForm() {
            var oldForm = document.forms[0].submit;
            $(".mini-datepicker").each(function () {
                if ($(this).attr("name") && $(this).attr("name") != "") {
                    dateInputs.push($(this).attr("name"));
                }
            })
            $(".mini-datepicker input").each(function () {
                if ($(this).attr("name") && $(this).attr("name") != "") {
                    dateInputs.push($(this).attr("name"));
                }
            })
            $.unique(dateInputs);
            document.forms[0].submit = function () {
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '数据提交中...' //数据提交中...
                });
                try {
                    $.each(dateInputs, function () {
                        if (mini.getbyName(this)) {
                            mini.getbyName(this).setFormat("yyyy-MM-ddTHH:mm:ss");
                        }
                    })
                } catch (e) {
                }
                oldForm.call(document.forms[0]);
            }
        }

        function pageUnload() {
            setTimeout(function () {
                $("#bg").remove();
                $("#bg-loader").remove();
                $("body").css("overflow", "auto");
            }, 500)
        }
        initPageEle();
        function initPageEle() {
            changeDivHeight();
            dynamicCreateBtn();
            mini.parse();
            loadingForm();
            pageUnload();
            dynamicFileList();
            initCkEditor();
            initSupplier();
        }
        function initSupplier(){
            var supplierGrid = mini.get("supplierGrid");
            supplierGrid.setUrl('${pageContext.request.contextPath}/business/bidding/confirm/bidSupplier.action?bidFileId=${view.bidId}');
            supplierGrid.on("load", function () {
                var biddingId = $("input[name=biddingId]").val();
                var row = supplierGrid.findRow(function(row){
                    if(row.biddingId == biddingId) return true;
                });
                supplierGrid.select( row, true);
            });
            supplierGrid.load();
        }
        function onDrawSupplierGridCell(e){
            var record = e.record;
            var biddingId = $("input[name=biddingId]").val();
            /*if (record.biddingId!=biddingId && e.field == "finalPrice") {
                e.cellHtml = "";
            }*/
        }
        function onSelectionChanged(e){
            var grid = e.sender;
            var record = grid.getSelected()
            $("input[name=biddingId]").val(record.biddingId);
            $("input[name=supplierId]").val(record.supplierId);
            var load = mini.loading("加载中", "提示");
            $.ajax({
                url:"${pageContext.request.contextPath}/business/bidding/confirm/loadSupplierBidInfo.action",
                type:"post",
                data:{
                    uuid:record.biddingId
                },
                async:false,
                success:function(res){
                    var data = JSON.parse(res);
                    console.log(data);
                    mini.hideMessageBox(load);
                    $("input[name=createUuid]").val(data.createUuid);
                    $("input[name=techFirstPrice]").val(data.techFirstPrice);
                    $("input[name=techSecondPrice]").val(data.techSecondPrice);
                    $("input[name=techThirdPrice]").val(data.techThirdPrice);
                    $("input[name=techFinalPrice]").val(data.techFinalPrice);
                    $("input[name=transportFirstPrice]").val(data.transportFirstPrice);
                    $("input[name=transportSecondPrice]").val(data.transportSecondPrice);
                    $("input[name=transportThirdPrice]").val(data.transportThirdPrice);
                    $("input[name=transportFinalPrice]").val(data.transportFinalPrice);
                    $("input[name=finalTotalPrice]").val(data.finalTotalPrice);
                    initTotalPrice();
                    initDevice();
                    initDetail();
                },
                error:function () {
                    mini.hideMessageBox(load);
                    mini.alert("加载失败","提示");
                }
            });
        }

        function initTotalPrice() {
            var totalPriceGrid = mini.get("totalPrice");
            totalPriceGrid.set({
                data: [{
                    name: "设备价格",
                    other: "设备本体"
                }, {
                    name: "其中",
                    other: "随机备品备件"
                }, {
                    name: "其中",
                    other: "专用工具"
                }, {
                    name: "技术服务费",
                    firstPrice: $("input[name=techFirstPrice]").val(),
                    secondPrice: $("input[name=techSecondPrice]").val(),
                    thirdPrice: $("input[name=techThirdPrice]").val(),
                    finalPrice: $("input[name=techFinalPrice]").val()
                }, {
                    name: "运输保险费",
                    firstPrice: $("input[name=transportFirstPrice]").val(),
                    secondPrice: $("input[name=transportSecondPrice]").val(),
                    thirdPrice: $("input[name=transportThirdPrice]").val(),
                    finalPrice: $("input[name=transportFinalPrice]").val()
                }]
            });
            var marges = [
                {rowIndex: 0, columnIndex: 0, rowSpan: 3, colSpan: 1},
                {rowIndex: 3, columnIndex: 0, rowSpan: 1, colSpan: 2},
                {rowIndex: 4, columnIndex: 0, rowSpan: 1, colSpan: 2}
            ];
            totalPriceGrid.mergeCells(marges);
        }
        //限定供应商调价的编辑
        function onTotalPriceBeginEdit(e) {
            var record = e.record, field = e.field, rowIndex = e.rowIndex;
            if ((rowIndex == 3 || rowIndex == 4 ) && field == "finalPrice" ) {
                e.cancel = false;    //其中的多次报价不可编辑
            }else{
                e.cancel = true;
            }
        }
        function onDrawTotalPriceCell(e) {

        }
        function onTotalPriceCellCommitEdit(e) {
            var field = e.field, value = e.value, rowIndex = e.rowIndex;
            var editor = e.editor;
            editor.validate();
            if (editor.isValid() == false || editor.value < 0) {
                alert("请输入数字且不小于0");
                e.cancel = true;
                return;
            }
            if (rowIndex == 3 && field == "finalPrice") {
                $("input[name=techFinalPrice]").val(value);
            }
            if (rowIndex == 4 && field == "finalPrice") {
                $("input[name=transportFinalPrice]").val(value);
            }
        }
        //devices表格总数
        function onDrawTotalPriceSummaryCell(e) {
            var result = e.result;
            var grid = e.sender;
            var rows = e.data;
            if (e.field == "name") {
                e.cellHtml = "总计";
            }
            if (e.field == "firstPrice") {
                var total = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var t = row.firstPrice;
                    if (isNaN(t)) continue;
                    total += Number(t);
                }
                e.cellHtml = total.toFixed(2);
            }
            if (e.field == "secondPrice") {
                var total = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var t = row.secondPrice;
                    if (isNaN(t)) continue;
                    total += Number(t);
                }
                e.cellHtml = total.toFixed(2);
            }
            if (e.field == "thirdPrice") {
                var total = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var t = row.thirdPrice;
                    if (isNaN(t)) continue;
                    total += Number(t);
                }
                e.cellHtml = total.toFixed(2);
            }
            if (e.field == "finalPrice") {
                var total = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var t = row.finalPrice;
                    if (isNaN(t)) continue;
                    total += Number(t);
                }
                e.cellHtml = total.toFixed(2);
                //给supplier赋值
                var supplierGrid = mini.get("supplierGrid");
                var biddingId = $("input[name=biddingId]").val();//投标id
                var row = supplierGrid.findRow(function(row){
                    if(row.biddingId == biddingId) return true;
                });
                supplierGrid.updateRow(row, {finalPrice:total.toFixed(2)});
                //更新最终报价
                $("input[name=finalTotalPrice]").val(total.toFixed(2));
            }
        }
        function initDevice() {
            var purchaseApplyUuid = $("input[name=purchaseApplyUuid]").val();
            var supplierBidUuid = $("input[name=biddingId]").val();
            var devices = mini.get("devices");
            devices.load({deviceType: '1', purchaseId: purchaseApplyUuid, supplierBidUuid: supplierBidUuid});
            var baks = mini.get("baks");
            baks.load({deviceType: '2', purchaseId: purchaseApplyUuid, supplierBidUuid: supplierBidUuid});
            var tools = mini.get("tools");
            tools.load({deviceType: '3', purchaseId: purchaseApplyUuid, supplierBidUuid: supplierBidUuid});
        }
        //device绘制
        function onDrawDevicesCell(e) {
            var record = e.record;
            if (e.field == "sumFirstUnitPrice") {
                var firstUnitPrice = record.firstUnitPrice;
                var num = record.num;
                var t = firstUnitPrice * num;
                if (!isNaN(t)) e.cellHtml = t.toFixed(2);
            }
            if (e.field == "sumSecondUnitPrice") {
                var secondUnitPrice = record.secondUnitPrice;
                var num = record.num;
                var t = secondUnitPrice * num;
                if (!isNaN(t)) e.cellHtml = t.toFixed(2);
            }
            if (e.field == "sumThirdUnitPrice") {
                var thirdUnitPrice = record.thirdUnitPrice;
                var num = record.num;
                var t = thirdUnitPrice * num;
                if (!isNaN(t)) e.cellHtml = t.toFixed(2);
            }
            if (e.field == "sumFinalUnitPrice") {
                var finalUnitPrice = record.finalUnitPrice;
                var num = record.num;
                var t = finalUnitPrice * num;
                if (!isNaN(t)) e.cellHtml = t.toFixed(2);
            }
        }
        //devices表格总数
        function onDrawSummaryCell(e) {
            var result = e.result;
            var grid = e.sender;
            var index = grid.id == "devices" ? 0 : grid.id == "baks" ? 1 : 2;
            var rows = e.data;
            if (e.field == "index") {
                e.cellHtml = "总计";
            }
            if (e.field == "sumFirstUnitPrice") {
                var total = 0, j = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var firstUnitPrice = row.firstUnitPrice;
                    var num = row.num;
                    var t = firstUnitPrice * num;
                    if (isNaN(t)) {
                        j++;
                        continue;
                    }
                    total += t;
                }
                var firstPrice = "";
                if (j != rows.length) {
                    e.cellHtml = total.toFixed(2);
                    firstPrice = total.toFixed(2);
                }
                var totalGrid = mini.get("totalPrice");
                totalGrid.updateRow(totalGrid.getRow(index), {firstPrice: firstPrice});
            }
            if (e.field == "sumSecondUnitPrice") {
                var total = 0, j = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var secondUnitPrice = row.secondUnitPrice;
                    var num = row.num;
                    var t = secondUnitPrice * num;
                    if (isNaN(t)) {
                        j++;
                        continue;
                    }
                    total += t;
                }
                var secondPrice = "";
                if (j != rows.length) {
                    e.cellHtml = total.toFixed(2);
                    secondPrice = total.toFixed(2);
                }
                var totalGrid = mini.get("totalPrice");
                totalGrid.updateRow(totalGrid.getRow(index), {secondPrice: secondPrice});
            }
            if (e.field == "sumThirdUnitPrice") {
                var total = 0, j = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var thirdUnitPrice = row.thirdUnitPrice;
                    var num = row.num;
                    var t = thirdUnitPrice * num;
                    if (isNaN(t)) {
                        j++;
                        continue;
                    }
                    total += t;
                }
                var thirdPrice = "";
                if (j != rows.length) {
                    e.cellHtml = total.toFixed(2);
                    thirdPrice = total.toFixed(2);
                }
                var totalGrid = mini.get("totalPrice");
                totalGrid.updateRow(totalGrid.getRow(index), {thirdPrice: thirdPrice});
            }
            if (e.field == "sumFinalUnitPrice") {
                var total = 0, j = 0;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var row = rows[i];
                    var finalUnitPrice = row.finalUnitPrice;
                    var num = row.num;
                    var t = finalUnitPrice * num;
                    if (isNaN(t)) {
                        j++;
                        continue;
                    }
                    total += t;
                }
                var finalPrice = "";
                if (j != rows.length) {
                    e.cellHtml = total.toFixed(2);
                    finalPrice = total.toFixed(2);
                }
                var totalGrid = mini.get("totalPrice");
                totalGrid.updateRow(totalGrid.getRow(index), {finalPrice: finalPrice});
            }
        }
        function initDetail() {
            var purchaseApplyUuid = $("input[name=purchaseApplyUuid]").val();
            var supplierBidUuid = $("input[name=biddingId]").val();
            var details= mini.get("details");
            details.setUrl("${pageContext.request.contextPath}/business/bidding/confirm/loadMaterialDetailPrice.action");
            details.on("load", function () {
                details.mergeColumns(["rowId", "materialName"]);
            });
            details.load({supplierBidUuid: supplierBidUuid});
        }
        //device绘制
        function onDrawDetailCell(e) {
            var record = e.record;
            if (e.field == "sumPrice") {
                var price = record.price;
                var count = record.count;
                var t = price * count;
                if (!isNaN(t)) e.cellHtml = t;
            }
            if (e.field == "sumFinalUnitPrice") {
                var finalUnitPrice = record.finalUnitPrice;
                var count = record.count;
                var t = finalUnitPrice * count;
                if (!isNaN(t)) e.cellHtml = t;
                //获取所有同uuid
                var details = mini.get("details");
                var rows = details.findRows(function(row){
                    if(row.uuid == record.uuid ) return true;
                });
                //计算单价
                var price = 0;
                for (i = 0 ;i<rows.length;i++){
                    price += Number(rows[i].finalUnitPrice);
                }
                var gId = record.type==1?"devices":record.type==2?"baks":"tools";
                var grid = mini.get(gId);
                var row =  grid.findRow(function(row){
                    if(row.uuid == record.uuid ) return true;
                });
                grid.updateRow(row,{finalUnitPrice:price});
            }
        }
        function onDetailPriceCellCommitEdit(e) {
            var field = e.field, value = e.value, rowIndex = e.rowIndex;
            var editor = e.editor;
            editor.validate();
            if (editor.isValid() == false || editor.value < 0) {
                alert("请输入数字且不小于0");
                e.cancel = true;
                return;
            }
        }
        function initCkEditor(){
            /*if (gIsRead || gLoginUser!=$("[name=createBy]").val()) {
                CKEDITOR.config.readOnly = true;
            }*/
            if (gCkEditor == null) {
                gCkEditor = CKEDITOR.appendTo('editor', {
                    customConfig : 'ckeditor_config_QC.js',
                    customConfig : 'ckeditor_config.js',
                    on : {
                        instanceReady : function(ev) {
                            gCkEditor.setData(gForm.message.value);
                        }
                    }
                });
            }
        }
        function toClose() {
            mini.confirm("确定要关闭吗？", "提示", function (action) {
                if (action == "ok") {
                    self.close();
                }
            })

        }
        function showErrorTexts(errorTexts) {
            var s = errorTexts.join('<br/>');
            mini.showMessageBox({
                width: 250,
                title: "消息提示",
                buttons: ["ok"],
                message: "消息提示",
                html: s,
                showModal: true
            });
        }
        function toSave() {
            var form = document.forms[0];
            //用于页面验证
            var tmpform = new mini.Form(form);
            tmpform.validate();
            if (tmpform.isValid() == false) {
                showErrorTexts(tmpform.getErrorTexts());
                return;
            }
            // form.wfStatus.value = "2";// 保存
            var supplierBidUuid = $("input[name=biddingId]").val();
            if(supplierBidUuid==null||supplierBidUuid==undefined||supplierBidUuid==""){
                showErrorTexts(["请选择中标的供应商"]);
                return ;
            }
            /*var details = mini.get("details");
            var rows = details.findRows(function (row) {
                if (isNull(row.finalUnitPrice)) return true;
            });
            if(rows.length!=0){
                showErrorTexts(["项目明细汇总最终单价不能为空"]);
                return ;
            }*/
            initSaveGrids();
            var message = gCkEditor.getData();
            if(message==null||message==undefined||message==""){
                showErrorTexts(["请输入通知信息"]);
                return ;
            }
            gForm.message.value = message;
            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: '数据提交中...' //数据提交中...
            });
            setTimeout(function () {
                document.forms[0].submit();
            }, 500)
        }
        function initSaveGrids(){
            var devices = mini.get("devices").getData();
            var json1 = mini.encode(devices);
            if (json1 != null && json1 != '' && json1 != 'undefined') {
                $("input[name=devices]").val(json1);
            }
            var baks = mini.get("baks").getData();
            var json2 = mini.encode(baks);
            if (json2 != null && json2 != '' && json2 != 'undefined') {
                $("input[name=baks]").val(json2);
            }
            var tools = mini.get("tools").getData();
            var json3 = mini.encode(tools);
            if (json3 != null && json3 != '' && json3 != 'undefined') {
                $("input[name=tools]").val(json3);
            }
            /*var details = mini.get("details").getData();
            var json4 = mini.encode(details);
            if (json4 != null && json4 != '' && json4 != 'undefined') {
                $("input[name=details]").val(json4);
            }*/
        }
        function isNull(value) {
            if (value == null || value == undefined || value.length == 0) {
                return true;
            }
            return false;
        }
        /* 暂时隐藏掉设备分项（2018-08-14）
        function onDeviceAttr(e) {
            var planId = "${planId}";
            var record = e.record;
            var materialId = record.uuid;
            return ' <a style="color:blue;text-align: center;" href="javascript:setDeviceAttr('+e.rowIndex +',\'' + planId + '\', \'' + materialId + '\')" >设备分项</a>';
        }
        */
        function setDeviceAttr(rowIndex,planId, materialId) {
            if(planId == null || planId == "" || planId == undefined) {
                mini.alert("采购计划未保存!");
                return ;
            }
            var userId = $("input[name='createUuid']").val();
            mini.open({
                url: "${pageContext.request.contextPath}/business/procurement/bidOfferDeviceAttr.jsp?type=2&planId=" + planId + "&materialId=" + materialId+"&userId="+userId,
                title: "设备分项",
                width: 725,
                height: 500,
                name : "deviceAttr",
                showMaxButton: true,
                ondestroy: function (data) {
                    if(data!='close'){
                        var devices = mini.get("devices");
                        var row = devices.getRow(rowIndex);
                        devices.updateRow(row,{finalUnitPrice:data});
                    }
                }
            });
        }
    </script>
</form>
</body>
</html>