<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
 <title>附件页面弹出上传</title>
 	<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="../js/file/upload.css">
	<script src="../js/file/dropzone.js" type="text/javascript"></script>
	<script language="JavaScript" type="text/javascript">
		var parentDocId = "${param.parentDocId}";
		var prjID = "${param.prjID}";
		var catalogId = "${param.catalogId}";
		var gDir="${pageContext.request.contextPath}";
		var menuId="${param.menuId}";
		var gIsRead="${param.isRead}";
		var needVersion="${param.needVersion}";
		var gLoginUser="${sessionScope.user.userName}";
		var gUserId="${sessionScope.user.uuid}";
		var curDocId="${param.curDocId}";
		var gOrgIds="${sessionScope.orgIds}";
		
		var objPanel=null;      //附件上传提示框
		var gCurAttId="${param.attId}";       //当前上传表格ID
		var myDropzone=null;    //附件上传对象
		
		//附件查询
		function initComplefj(id) {
			var grid = mini.get("gridAtt");
			//这里可以重新指定自定义的数据装载路径
			grid.load({
				parentDocId : curDocId,
				prjID : prjID,
				area : id
			});
		}
		
		function onLoad(e){
			parent.setTotal(gCurAttId,e.data.length);
		}

		function initUpBtn() {
			objPanel = mini.get("mini-window-attache");
			if(!objPanel){
				objPanel= new mini.Window();
				objPanel.setTitle("附件列表");
				objPanel.setId("mini-window-attache");
				objPanel.setWidth(410);
				objPanel.setHeight(300);
				objPanel.setShowCloseButton(false);
			}
			objPanel.setBody('<div id="previews"></div>');
			objPanel.render(document.body)
			
			obj={
				ID:"",
				prjID:prjID,
				parentDocId:curDocId,
				catalogId:catalogId,
				menuId:menuId,
				userName:gLoginUser,
				userIds:gUserId,
				curDocId:curDocId,
				orgIds:gOrgIds
			}
			
			myDropzone = new Dropzone(document.body, {
		        url: gDir+'/profile/adenexUploadFile.action',
		        thumbnailWidth: 50,
		        thumbnailHeight: 50,
		        parallelUploads: 20,
		        params:obj,
				maxFiles:20,
				autoQueue: true,
		        previewsContainer: "#previews",
		        clickable: ".fileinput-button"
		    });
			
			myDropzone.on("addedfile", function(file) {
				this.options.params.ID=gCurAttId;
				objPanel.showAtPos("center", "middle");
			});
			
			myDropzone.on("removedfile", function(file) {
				if(this.files.length==0){
					objPanel.hide();
					initComplefj(gCurAttId);
				}
			});
			
			myDropzone.on("success", function(file) {
				var isClose=true;
				$.each(this.files,function(){
					if(this.status!="success"){
						isClose=false;
					}
				})
				
				if(isClose){
					objPanel.hide();
					$("#previews").html("");
					initComplefj(gCurAttId);
				}
			});
		}

		//绘制下载链接
		function attTxtLink(e) {
			var row = e.record;
			var link = "<a href='"
					+ encodeURI("${pageContext.request.contextPath}/profile/findDowloadAdenexaById.action?uuid="
							+ row.uuid) + "' target='_blank'>" + e.cellHtml
					+ "</a>";
			return link;
		}
		
		//预览附件
		function goViewAttach(id){
			var grid=mini.get(id);
			var uuid = GetDocID(grid);
			var path = "${pageContext.request.contextPath}/profile/findViewAdenexaById.action?uuid=";
			getViewUpload(uuid,path);
		}

		//上传附件预览
		function getViewUpload(uuid,path){
			if(uuid==""){
				mini.alert("请选择预览的文件！");
				return;
			}
			window.open(path+uuid);
			return;
			mini.open({
				url:path+uuid,
				title:"附件预览",
				width:810,
				height:545,
				showMaxButton: true,
				allowResize:true
			});
		}
		
		//获取id
		function GetDocID(grid){
			var arrID=$.map(grid.getSelecteds(),function(i){
				return i.uuid;
			});
			return arrID.join("^");
		}
		//删除附件
		function goDelAttach(id){
			var grid=mini.get(id);
			var uuid = GetDocID(grid);
			var path = gDir+"/profile/adenexDeleteFile.action?uuid=";
			goDelAttachFile(uuid,path,id);
		}


		//删除附件
		function goDelAttachFile(s,path,id) {
			var arrFile = s;
		    if (arrFile.length > 0) {
		       mini.confirm("确定删除选中附件吗？","确认框", function(r){
		    	   if(r=="ok"){
		                $.ajax({
		                    url: path + arrFile,
		                    dataType: "json",
		                    cache: false,
		                    success: function(data) {
		                        if (data.success) {
		                        	mini.alert(data.data);
		                        	initComplefj(id);
		                        }else{
		                        	mini.alert(data.data);
		                        }
		                    }
		                });
		             }
		        });
		    } else {
		        mini.alert("没有可删除的附件，请先选择！")
		    }
		}
	function updateVersion(){
		var data = grid.getChanges();
	    var json = mini.encode(data);
		$.ajax({ 
	        url : gDir+"/profile/updateAdenexaVersion.action", 
	        type : "post", 
	        data :{
	        	param : json
	        },
	        dataType : "json", 
	        success : function(data) { 
	        	alert("保存成功");
	            /* if(data.length>0){
	                for(var i=0;i<data.length;i++){
	                    
	                    var options = '<option value="'+data[i].id+'">'+data[i].name+'</option>';
	                    $("#usertypeid_detail").append(options);
	                }
	                $("#usertypeid_detail").show();
	            } */
	       }    
	    })
	}
	function getFileCount(){
		return grid.getTotalCount();
	}
	</script>
	</head>
	<body text="#000000" bgcolor="#FFFFFF" style='background-color:#F8F8F8' >
		<form method="post" action="" name="_fmPicture">
			<input type="hidden" name="__Click" value="0"><!--一下区域为表单主体区域-->
			<div class="mini-toolbar" id="FileMode" style="border-bottom:0;padding:3px;">
				<a class="mini-button fileinput-button" id="btnUp" iconCls="icon-upload" style="width:70px;margin-right:5px;">上传</a>
				<a class="mini-button" id="btnRe" iconCls="icon-remove" onclick="goDelAttach('gridAtt')" style="width:70px;margin-right:5px;">删除</a>
				<a class="mini-button" iconCls="icon-print" onclick="goViewAttach('gridAtt')" style="width:70px;">预览</a>
				<a class="mini-button" id="saveButton"  iconCls="icon-save" onclick="updateVersion()" style="width:70px;display: none">保存</a>
			</div>
			<div id="gridAtt" class="mini-datagrid" style="width:100%;height:100px;" 
			allowCellEdit="true" onload="onLoad"
			allowCellSelect="true"
			onlyCheckSelection='true' 
			multiSelect='false'  
			allowMoveColumn='false'  
			emptyText='暂无附件。' 
			showEmptyText='true' 
			virtualScroll='true' 
			idField="id" showPager="false"
				url="${pageContext.request.contextPath}/profile/findAllAdenexaJson.action" 
			>
			    	<div property="columns">
						<div type="checkcolumn" width="30"></div>
				        <div type="indexcolumn" width="30">序号</div>
			       	 	<div field="filename" width="60" headerAlign="center" allowSort="true" renderer='attTxtLink'>附件名称</div>
			       	 	<div field="createBy" width="60" headerAlign="center" allowSort="true" align='center'>上传人</div>
			       	 	<div field="createDate" width="60" headerAlign="center" allowSort="true" align='center' dateFormat='yyyy-MM-dd'>上传时间</div>
			       	 	<div field="filesize" width="60" headerAlign="center" allowSort="true" align='center'>附件大小</div>
			       	 	<div name="version" visible="false" field="version" width="60" headerAlign="center" allowSort="true" align='center'>版本号
			       	 		<input property="editor" class="mini-textbox" style="width:100%;" minWidth="100" />
			       	 	</div>
			       	 	<div field="uuid" visible="false"></div>
					</div>
			</div>
			<script>
				$("#gridAtt").css("height",document.documentElement.clientHeight-50);
				mini.parse();
				initComplefj(gCurAttId);
				if(gIsRead=="true"){
					$("#btnUp").remove();
					$("#btnRe").remove();
				}else{
					initUpBtn();
				}
				if(needVersion != undefined && needVersion == "1"){
					var grid = mini.get("gridAtt");
					grid.showColumn("version");
					$("#saveButton").show();
				}
			</script>
		</form>
	</body>
</html>

