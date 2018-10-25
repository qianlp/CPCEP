/**
 * 操作方法在这个里面写
 */

/*
描述：
	选择部门中加入清除图标，点击清除值
参数：
	e：部门树节点对象
*/
	function onCloseClick(e) {
		e.sender.setValue("");
		e.sender.setText("");
	}
	
	
	//新增或修改页面关闭按钮操作
	function wnClose() {
			var _SelfClose = null;
			if (window != top) {
				window.parent.window.mini.get('winObj').hide();
			} else {
				try {
					_SelfClose = function() {
						self.close();
					};
					if (window.opener) {
						window.opener.location.reload();
					}
				} catch (e) {
				};
			}
			if (_SelfClose) {
				setTimeout(_SelfClose, 1000);
			}
		}
	
	
	//删除附件
	function goDelAttachFile(s,path) {
		var arrFile = s;
	    if (arrFile.length > 0) {
	       mini.confirm("确定删除该附件","确认框", function(r){
	    	   if(r=="ok"){
	                $.ajax({
	                    url: path + arrFile,
	                    dataType: "json",
	                    cache: false,
	                    success: function(data) {
	                        if (data.success) {
	                        	mini.alert(data.data);
	                        	initComplefj();
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
	
	//上传附件预览
	function getViewUpload(uuid,path){
		if(uuid==""){
			mini.alert("请选择预览的文件！");
			return;
		}
		mini.open({
			url:path+uuid,
			title:"附件预览",
			width:810,
			height:545,
			showMaxButton: true,
			allowResize:true
		});
	}
	
	
	//计算预计周期
	function sumDay(){
		var sDay=mini.getbyName("expectStartDate").getValue();
		var eDay=mini.getbyName("expectEndDate").getValue();
		
		if(sDay!="" && eDay!=""){
			if(sDay>=eDay){
				mini.getbyName("expectEndDate").setValue("");
				mini.getbyName("expectDay").setValue("");
			}else{
				var sunDay=0;
				if(eDay-sDay==0){
					sunDay=1000*60*60*24
				}else{
					sunDay=(eDay-sDay)+(1000*60*60*24)
				}
				mini.getbyName("expectDay").setValue(sunDay/(1000*60*60*24));
			}
		}else{
			mini.getbyName("expectDay").setValue("");
		}
	}
	
	//是否延期归还
	function delayRevert(){
		var applyNo=mini.getbyName("applyNo").getText();
		var sDay=mini.getbyName("expectEndDate").getValue();//预计归还日期
		var eDay = mini.getbyName("sjRevertDate").getValue();//实际归还日期
		if(applyNo=="" || applyNo==null){
			mini.alert("请先选择借阅申请单！");
			return;
		}
		if(sDay!="" && eDay!=""){
			var stDay = new Date(sDay.replace(/-/g,"/"));
			if(stDay>=eDay){
				mini.getbyName("delayRevert").setValue("否");
			}else{
				mini.getbyName("delayRevert").setValue("是");
			}
		}else{
			mini.getbyName("delayRevert").setValue("否");
		}
	}
	
	function CommonOpenDoc(strPathUrl){
		//当打开方式为window.open时使用
		var gWidth=window.screen.width;
		//当打开方式为window.open时使用
		var gHeight=window.screen.height;
		if(gHeight>0){var T=(screen.height-100-gHeight)/2}else{var T=0}
		if(gWidth>0){var L=(screen.width-20-gWidth)/2}else{var L=0}
		var strStatus="height="+gWidth+",width="+gWidth+",top="+T+",left="+L+",toolbar=no,menubar=no,location=no,status=yes,resizable=yes,scrollbars=yes";
		var objWin=window.open(strPathUrl,"_blank",strStatus);
		setTimeout(function(){objWin.focus()},100)
	}
	
	/*
	描述:关闭父页面窗口
	 */
	function goCloseDlg(name) {
		var oWinDlg = mini.get(name);
		oWinDlg.setUrl("about:blank");
		$(oWinDlg.getBodyEl()).html("");
		$(oWinDlg.getFooterEl()).html("");
		oWinDlg.hide();
		window.location.reload();
	}
	
	//关闭子页面窗口
	function CloseWindow(action) {
		if (window.CloseOwnerWindow) {
			return window.CloseOwnerWindow(action);
		} else {
			window.close();
		}
	}
	
	//from表单保存提交
	function goSubmit(){
		//获取表单序列化字符串,
	    //也就是将整个表单的输入数据转换为以&作为分隔符的多个key=value的字符串
	    var formData = $("#form1").serialize();
	    mini.confirm("确认要保存吗？", "操作提示", function(action){
			if(action=="ok"){
				mini.mask({
		            el: document.body,
		            cls: 'mini-mask-loading',
		            html: '数据保存中...'
		    	});
	    $.ajax({
	        //获取表单的action作为url
	        url: $("#form1").attr("action"),
	        type: "POST",
	        data: formData,//把序列化后的表单数据放到这里
	        dataType: "json",
	        async: true,
	        error: function(xhr,status,error){
	            mini.alert("请求出错!");
	        },
	        success: function(result){
	        	if(result.success){
	        		mini.unmask(document.body);
	        		mini.alert(result.msg, "消息提示",function() {
	        			CloseWindow('close');
					});
	        	}else{
	        		mini.unmask(document.body);
	        		mini.alert(result.msg);
	        	}
	        }
	    });
	    //这里一定要返回false来阻止表单的submit
	    return false;
	}});}
	
	
		/**
		 * 日期时间类型转换"yyyy-MM-dd hh:mm:ss"公共方法
		 */
		  function DateTimeToString(value) {
		        if (value == null || value == '') {
		            return '';
		        }
		           var dt;
		    if (value instanceof Date) {
		        dt = value;
		    }else {
		        dt = new Date(value);
		        if (isNaN(dt)) {
		            value = value.replace(/\/Date\((-?\d+)\)\//, '$1'); //标红的这段是关键代码，将那个长字符串的日期值转换成正常的JS日期格式
		            dt = new Date();
		            dt.setTime(value);
		        }
		    }

		    return dt.format("yyyy-MM-dd hh:mm:ss");   //这里用到一个javascript的Date类型的拓展方法，这个是自己添加的拓展方法，在后面的步骤3定义
		}
		  
			  	/**
				 * 日期类型转换"yyyy-MM-dd"公共方法
				 */
				  function DateToString(value) {
				        if (value == null || value == '') {
				            return '';
				        }
				           var dt;
				    if (value instanceof Date) {
				        dt = value;
				    }else {
				        dt = new Date(value);
				        if (isNaN(dt)) {
				            value = value.replace(/\/Date\((-?\d+)\)\//, '$1'); //标红的这段是关键代码，将那个长字符串的日期值转换成正常的JS日期格式
				            dt = new Date();
				            dt.setTime(value);
				        }
				    }

				    return dt.format("yyyy-MM-dd");   //这里用到一个javascript的Date类型的拓展方法，这个是自己添加的拓展方法，在后面的步骤3定义
				}
				  
