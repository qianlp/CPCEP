var PublicField={
		/*表单顶部及右侧功能按钮*/
		Form:"表单",
		File:"附件",
		Sign:"意见",
		Chart:"流程",
		Top:"置顶",
		Template:"选择显示模块",
		Search:"查询",
		Refresh:"刷新",
		Save:"保存",
		Cancle:"取消",
		Close:"关闭",
		
		/*表单模块*/
		ModeName:"基本信息",
		PrjNo:"项目编号",
		PrjName:"项目名称",
		ZDR:"制单人",
		ZDTime:"制单时间",
		/*附件模块*/
		AttMode:"公共附件",
		AttTitle:"附件列表",
		AttCol1:"选择",
		AttCol2:"序号",
		AttCol3:"名称",
		AttCol4:"上传人",
		AttCol5:"上传时间",
		AttCol6:"附件大小",

		/*签字模块*/
		SignMode:"意见区",
		SignCol1:"环节名称",
		SignCol2:"审批意见",
		SignCol3:"审批人",
		SignCol4:"审批时间",

		OpTitle:"意见",
		OpMsg:"请您填写意见。",
		
		/*流程图模块*/
		ChartMode:"流程图",
			
			
		/*附件按钮*/
		FileBoxTitle:"上传附件",
		UpFile:"上传",
		EditFile:"编辑",
		DelFile:"删除",
		UpLoadMsg:"正在上传，请您耐心等待！",
		UpMsg1:"请选择需要上传的文件。",
		UpMsg2:"正在上传...",
		UpMsg3:"上传失败! 附件可能太大!",
		UpMsg4:"上传成功!",
		UpMsg5:"您是否需要删除所选附件？",
		UpMsg6:"没有可删除的附件，请先选择！",
		UpMsg7:"该附件不能被编辑！",
		UpMsg8:"没有可编辑的附件，请先选择！",
		UpMsg9:"您好，非浏览器下不能使用此功能！",
		UpMsg10:"请先设置附件显示区域id，id命名以“_att”结尾。",
			
		FileMsg:"暂无附件。",
			
		/*流程按钮*/
		FlowSave:"保存",
		FlowSubmit:"提交",
		FlowRefuse:"拒绝",
		FlowInfo:"查看流程信息",
		FlowReturn:"返回",
		FlowClose:"关闭",
		
		Print:"打印",
		
		/*组织机构*/
		Org:"组织机构",
		SelectPerson:"选择人员",
		TacheName:"环节名称",
		ClickTacheName:"点击选择环节名称",
		SelectedPersonList:"已有人员列表",
		SelectedList:"已选项列表",
		SelectDept:"选择部门" ,
		SelectRole:"选择角色",
		SelectPerson:"选择人员",
		SelectOrg:"选择组织信息",
		OnlySelectOne:"仅允许选择一项！",
		DataLoading:"列表加载中...",
		Exists:"已存在！",
		DefinedField:"请定义你需要存放的域名！"
		
};
/*流程*/
var WF_CONST_LANG = {
	USE_ACTION:"处理完毕！",
	ACTION_TO:"提交给了",
	SYMBOL_END:"。",
	NO_USE_WORKFLOW:"没有可用的流程或没有被激活的流程！",
	CONTACT_ADMIN:"请您联系系统管理员！",
	OPEN_BEFORE:"页面打开前执行的函数",
	OPEN_AFTER:"页面打开后执行的函数",
	SAVE_BEFORE:"页面提交前执行的函数",
	SAVE_AFTER:"页面提交后执行的函数",
	PAGE_NO_INIT:"可能未在页面中初始化！",
	NO_CHECK_FIELD:"检测不到以下域的标识：",
	SPECIFIC_TIME:"具体时间：",
	IDEA_AREA_UNDEFINDED:"意见显示区域未定义",
	LOCK_SUBMIT_PREFIX:"在您提交前 [",
	LOCK_SUBMIT_SUFFIX:"] 已经对文件进行了提交操作，请您刷新页面重新提交。",
	YES:"是",
	ONLY_SELECT_ONE:"仅允许选择一个人！",
	EXITED:"已存在！",
	CONFIRM_SUBMIT:"您确定提交吗？",
	CONFIRM_OPERATION:"确定要做相应操作吗？",
	NO_TACHENAME:"环节名称为空，请您先选择！",
	MUTIL_PERSON:"多人",
	SELECT_RElATED_PERSON:"请选择相关人员！",
	LIST_LOADING:"数据加载中...",
	DATA_SUBMIT:"数据提交中...",
	SELECT_APPROVER:"选择处理人",
	WORKFLOW_END:"流程结束",
	DIRECT:"直连",
	CONDITION_DIRECT:"条件直连",
	CONDITION_SELECT:"条件选择",
	ONLY_SELECT:"唯一选择",
	OK:"确定",
	OK_TITLE:"确定并提交",
	CANCEL:"取消",
	NO_FIND_NEXT_PERSON:"没有找到下一环节的处理人，请您联系管理员！",
	ROUTER_CONDITION_BLANK:"路由条件不能为空！",
	MUTIL_BRANCH_NOT_USE_DIRECT:"多条分支时，不允许使用“直连”。请您联系管理员！",
	ROUTER_ERROR:"路由定义错误！\n\n请联系管理员！",
	DOCUMENT_NOT_SUBMIT:"文档不能进行流转！\n\n当前审批人不应为空，请联系管理员！",
	ROUTER_RELATIION_SCENE:"在路由关系为直连情景下，\n\n如果下一环节处理人多于1人，\n\n下一环节点中<审批方式>必须设置为<多人>才能进行提交！\n\n请您联系管理员。",
	NEXT_NODE_NOT_EXITED:"下一环节点不存在，请联系管理员！",
	FORMULA_ERROR:"公式比较失败！\n\n字段名可能不存在或者字段名大小写不正确\n\n可能提交按钮函数中未进行赋值！",
	WEB_FORMULA_ERROR:"网页公式返回值非数组，请联系开发人员进行调整！",
	COMPARE_METHOD_ALERT:"比较函数有错，请使用JavaScript比较方式",
	STOP_ERROR:"您好，草稿状态时，此操作无效！",
	STOP_ERROR_0:"可能系统有异常，未成功终止，请联系管理员！",
	STOP_ERROR_1:"已成功终止！",
	STOP_ERROR_2:"文档此时正在被他人处理，不能被终止！",
	STOP_ERROR_3:"流程已经被终止或结束，不能被取回！",
	STOP_ERROR_4:"您不是文档的提交者，不能被终止！",
	
	SEARCH:"查询",
	REFRESH:"刷新",
	
	/*按钮*/
	"提交":"提交",
	"保存":"保存",
	"同意":"同意",
	"拒绝":"拒绝",
	"终止":"终止"
};