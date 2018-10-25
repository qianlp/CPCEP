/* 标准方法：加载、保存、调试项目，弹出任务面板、日历面板。
-----------------------------------------------------------------------------*/

var ServicesPath = mini_JSPath + "../plusproject/services/";    //Ajax交互路径（根据实际项目部署路径，需要修改）

var LoadProjectUrl = ServicesPath + 'load.aspx';
var SaveProjectUrl = ServicesPath + 'save.aspx';

function LoadXML(params, project, callback) {
    if (typeof params != "object") params = { file: params };

    project.loading();
    $.ajax({
        url: ServicesPath + "loadxml.aspx",
        data: params,
        cache: false,
        success: function (text) {
            var dataProject = mini.decode(text);
            project.loadData(dataProject);
            //
            if (callback) callback(project);
            project.unmask();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            mini.alert("加载失败, 错误码：" + textStatus);
            project.unmask();
        }
    });
}

function LoadProject(params, project, callback) {
    if (typeof params != "object") params = { projectuid: params };

    project.loading();
    $.ajax({
        url: LoadProjectUrl,
        data: params,
        cache: false,
        success: function (text) {
            var dataProject = mini.decode(text);
            project.loadData(dataProject);
            //
            if (callback) callback(project);
            project.unmask();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            mini.alert("加载失败, 错误码：" + textStatus);
            project.unmask();
        }
    });
}

function SaveProject(project, callback, params) {

    project.mask("数据保存中，请稍后...");
    var dataProject = project.getData();
    dataProject.RemovedTasks = project.getRemovedTasks();
    var json = mini.encode(dataProject);

    if (!params) params = {};
    params.project = json;

    $.ajax({
        url: SaveProjectUrl,
        type: "post",
        data: params,
        success: function (text) {
            mini.alert("保存成功,项目UID：" + text);
            project.acceptChanges();
            if (callback) callback(project);
            project.unmask();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            mini.alert("保存失败, 错误码：" + textStatus);
            project.unmask();
        }
    });
}
mini.copyTo(mini, {
	taskToArray: function(C, I, J, A, $) {
		if (!I) I = "children";
		var F = [];
		for (var H = 0,D = C.length; H < D; H++) {
			var B = C[H];
			F[F.length] = B;
			if (A) B[A] = $;
			var _ = B[I];
			if (_ && _.length > 0) {
				var E = B[J],
				G = this["treeToArray"](_, I, J, A, E);
				F.addRange(G)
			}
		}
		for(var m=0,l=F.length;m<l;m++){
			if(typeof(F[m][I])!="undefined"){
				delete F[m][I]
			}
			delete F[m]["_id"];
			delete F[m]["_uid"];
			delete F[m]["_pid"];
			delete F[m]["_level"];
			delete F[m]["_x"];
		}
		return F
	}
});
function LoadJSONProject(url, project, callback) {
    project.loading();
    $.ajax({
        url: url,
        cache: false,
        success: function (text) {
            var dataProject = mini.decode(text);
			dataProject["Tasks"]=mini.arrayToTree(dataProject["Tasks"],"","UID","ParentTaskUID");
			try{if(typeof gRe!="undefined")dataProject["Principals"]=gRe}catch(e){}
            project.loadData(dataProject);
            if (callback) callback(project);
            project.unmask();

        },
        error: function (jqXHR, textStatus, errorThrown) {
            mini.alert("加载失败, 错误码：" + textStatus);
            project.unmask();
        }
    });
}
//创建任务面板

var taskWindow = null;

function ShowTaskWindow(project) {
    var task = project.getSelected();
    if (task) {
        if (!taskWindow) {
            taskWindow = new TaskWindow();
        }
        taskWindow.setTitle("编辑任务");
        taskWindow.show();
        taskWindow.setData(task, project,
            function (action) {
                
                if (action == 'ok') {
                    try {
                        var taskData = taskWindow.getData();
                        project.updateTask(task, taskData);
                    } catch (ex) {
                        mini.alert("error:"+ex.message);
                        return false;
                    }
                }
            }
        );
    } else {
       mini.alert("请先选择任务");
    }
}

//日历面板
var calendarWindow = null;

function ShowCalendarWindow(project) {
    if (!calendarWindow) {
        calendarWindow = new CalendarWindow();
    }
    calendarWindow.show();
    calendarWindow.setData(project.getCalendars(), project,
        function (action) {
            if (action == "ok") {
            
                var calendars = calendarWindow.getData();
                project.setCalendars(calendars);
            }
        }
    );
}
