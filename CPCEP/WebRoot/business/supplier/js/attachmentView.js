function initAttachmentGrid(fileAction, columns) {
    if(columns == null || columns == undefined) {
        log.error("attachment grid columns is undefined!");
        return ;
    }
    gArrFile[0].grid['columns'] = columns;
    gArrFile[0].grid['url'] = fileAction;


    $.each(gArrFile,
        function(i) {
            var gid=this.grid.id;
            var divBtnID = "fileMode_btn_" + i;
            var tableStr = "<table style=\"width:100%;\"><tr><td style=\"width:100%;padding-left:10px;\"><b>"
                + this.title
                + "</b></td><td style=\"white-space:nowrap;\" id='fileMode_btn_"
                + i + "'></td></tr></table>";
            $("#FileMode .mbox-body")
                .append(
                    "<div id='fileMode_"
                    + i
                    + "' style='"+(i>0?"margin-top:10px;":"")+"'><div class='mini-toolbar' style='border-bottom:0px;'>"
                    + tableStr + "</div></div>");
            var grid = new mini.DataGrid();
            grid.set(this.grid);
            grid.on("drawcell", function (e) {
                var record = e.record;
                field = e.field;
                //field列，超连接操作按钮
                if (field == "filename") {
                    e.cellStyle = "text-align:left";
                    e.cellHtml = '<a href="'+gDir+'/profile/findDowloadAdenexaById.action?uuid=' +record.uuid + '" target="_blank" >'+record.filename+'</a>';
                }
            });
            grid.on("rowdblclick", function (e) {
                var record = e.record;
                goView(record.uuid);
            });
            if($("[name=curDocId]").val() && $("[name=curDocId]").val()!=""){
                grid.load({parentDocId:$("[name=curDocId]").val(),prjID:$("[name=prjID]").val(),area:gid});
            }
            grid.render(document.getElementById("fileMode_" + i));
            grid.reload();
        });

    $("#FileMode").show();
    if(gIsRead){
        return;
    }
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

    if(($("[name=prjID]").length>0 && $("[name=prjID]").val()=="") || ($("[name=projectId]").length>0 && $("[name=projectId]").val()=="")){
        return
    }

}

var gArrFile = [ {
    title : "附件列表",
    grid : {
        id : "att_att",
        multiSelect : true,
        url:gDir + "/profile/findAllAdenexaJson.action",
        style : "width:100%;height:auto",
        valueField : "uuid",
        showPager : false,
        showEmptyText : true,
        emptyText : "没有任何附件",
    }
}];

var objPanel=null;      //附件上传提示框

//获取id
function GetDocID(grid){
    var arrID=$.map(grid.getSelecteds(),function(i){
        return i.uuid;
    });
    return arrID.join("^");
}

//预览附件
function goViewAttach(id){
    var grid=mini.get(id);
    var uuid = GetDocID(grid);
    var path = gDir+"/profile/findViewAdenexaById.action?uuid=";
    getViewUpload(uuid,path);
}

function goView(uuid){
    var path = gDir+"/profile/findViewAdenexaById.action?uuid=";
    getViewUpload(uuid,path);
}
