<%@ page language="java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta NAME="robots" content="NOINDEX,NOFOLLOW">
<meta name="robots" content="noarchive">
<meta name="Googlebot" content="noarchive">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<title>显示文件</title>
<style>
	body{
		width:100%;
		height:100%;
		overflow:hidden;
	}
</style>
</head>
<body>
	<script language="JavaScript">
		var v = 0;
		function OnFlashLoaded() {
			var urlData = '{"transformPage":"${pageContext.request.contextPath}/profile/webCADLook.action?uuid=${param.docId}","CheckTaskStatus":"${pageContext.request.contextPath}/profile/webCADLook.action?uuid=${param.docId}"}';
			thisMovie("WebCAD").setDataUrl("urlData" , urlData);
			
			v = 1;
		}

		var fullScreen = 0;
		function OnFullScreen(blSuccess) {
			if (blSuccess) {
				fullScreen = 1 - fullScreen;
			}
		}

		function thisMovie(movieName) {
			if (navigator.appName.indexOf("Microsoft") != -1) {
				return window[movieName];
			} else {
				return document[movieName];
			}
		}
		var flashVars = 'showToolBar=1&runmode=0&OnLoadedEvent=OnFlashLoaded&OnFullScreenEvent=OnFullScreen&fileId=16133&fileName=办公楼天正图.dwg&cacheSize=1024&ocfSizeLimit=50&extData=eyJ3YXRlcklkIjowLCJmaWxlSWQiOjE2MTMzLCJjdG9rZW4iOiI4RjZENTRFOTRCQzI4NDhFNTNG%0AMTk2Mjc5NEE0NUI0QUAzIiwidWlkIjoxfQ%3D%3D&languagePage=${pageContext.request.contextPath}/file/webCAD/util/cn.xml';
	</script>
	<div class="wid100" style="height:100%;">
		<div class="content clearfix" style="height:100%;">
			<div id="flashContent" style="height:100%;">
			<!-- 这里并没有对各个浏览器进行代码适配，请根据flash官方教程，或者参考浩辰网站上的云图代码，对各个浏览器进行代码适配 -->

			</div>
			<div class="big_bomb_bottom_cont">
				<a class="cursor dis_inline mar_r40 bigbotmb_btn bigbomb_btn_one"
					title="选择"></a> <a
					class="cursor dis_inline mar_r40 bigbotmb_btn bigbomb_btn_two"
					title="显示全图"></a> <a
					class="cursor dis_inline mar_r40 bigbotmb_btn bigbomb_btn_three"
					title="移动视图"></a> <a
					class="cursor dis_inline mar_r40 bigbotmb_btn bigbomb_btn_four"
					title="窗口缩放"></a> <a
					class="cursor dis_inline mar_r40 bigbotmb_btn bigbomb_btn_five"
					title="缩小视图"></a> <a
					class="cursor dis_inline mar_r40 bigbotmb_btn bigbomb_btn_six"
					title="放大视图"></a> <a
					class="cursor dis_inline mar_r40 bigbotmb_btn bigbomb_btn_seven"
					title="背景颜色"></a> <a
					class="cursor dis_inline mar_r40 bigbotmb_btn bigbomb_btn_eight"
					title="全屏显示"></a> <a
					class="cursor dis_inline mar_r40 bigbotmb_btn bigbomb_btn_nine"
					title="测量长度"></a> <a
					class="cursor dis_inline mar_r40 bigbotmb_btn bigbomb_btn_ten"
					title="测量面积"></a>
			</div>
		</div>
	</div>
	<script language="JavaScript">
	function myBrowser(){
	    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
	    var isOpera = userAgent.indexOf("Opera") > -1;
	    if (isOpera) {
	        return "Opera"
	    }; //判断是否Opera浏览器
	    if (userAgent.indexOf("Firefox") > -1) {
	        return "FF";
	    } //判断是否Firefox浏览器
	    if (userAgent.indexOf("Chrome") > -1){
	  return "Chrome";
	 }
	    if (userAgent.indexOf("Safari") > -1) {
	        return "Safari";
	    } //判断是否Safari浏览器
	    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
	        return "IE";
	    }; //判断是否IE浏览器
	}
	
	
//以下是调用上面的函数
var op_ie10='<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="WebCAD" width="100%" height="100%" align="middle">'
			+'<param name="movie" value="${pageContext.request.contextPath}/file/webCAD/util/cadViewer.swf" />'
			+'<param name="quality" value="high" /><param name="bgcolor" value="#ffffff" />'
			+'<param name="allowFullScreen" value="true">'
			+'<param name="play" value="true" /><param name="loop" value="true" /><param name="wmode" value="transparent" /><param name="scale" value="showall" />'
			+'<param name="menu" value="true" /><param name="devicefont" value="false" /><param name="salign" value="" /><param name="allowScriptAccess" value="always" />'
			+'<param name="FlashVars" value="' + flashVars + '" /></object>';
var op_other='<embed id="WebCAD" width="100%" height="100%" flashvars="' + flashVars + '" allowscriptaccess="always" allowfullscreen="true" bgcolor="#000000" wmode="direct" src="${pageContext.request.contextPath}/file/webCAD/util/cadViewer.swf" type="application/x-shockwave-flash" style="outline:none;" name="krpanoSWFObject">';
var op_chome='<object type="application/x-shockwave-flash"  id="WebCAD" data="${pageContext.request.contextPath}/file/webCAD/util/cadViewer.swf" width="100%" height="100%" >'
			+'<param name="movie" value="${pageContext.request.contextPath}/file/webCAD/util/cadViewer.swf" />'
			+'<param name="quality" value="high" /><param name="bgcolor" value="#ffffff" />'
			+'<param name="allowFullScreen" value="true">'
			+'<param name="play" value="true" /><param name="loop" value="true" />'
			+'<param name="wmode" value="window" /><param name="wmode" value="Opaque">'
			+'<param name="scale" value="showall" />'
			+'<param name="menu" value="true" /><param name="devicefont" value="false" /><param name="salign" value="" />'
			+'<param name="allowScriptAccess" value="always" />'
			+'<param name="FlashVars" value="' + flashVars + '" /></object>';



//以下是调用上面的函数
var mb = myBrowser();
if ("IE" == mb) {
	//alert("IEop_ie10:" + op_ie10);
	$("#flashContent").html(op_ie10);
	
}else if ("FF" == mb) {
	//alert("FFop_other:" + op_other);
	$("#flashContent").html(op_other);
}else if ("Chrome" == mb) {
	//alert("Chromeop_chome:" + op_chome);
	$("#flashContent").html(op_chome);
}else if ("Opera" == mb) {
	//alert("Operaop_other:" + op_other);
	$("#flashContent").html(op_other);
}else if ("Safari" == mb) {
	//alert("Safariop_chome:" + op_chome);
	$("#flashContent").html(op_chome);
}else{
	//alert("op_other:" + op_other);
	$("#flashContent").html(op_other);
}
		//点击第一个按钮时
		$(".bigbomb_btn_one").click(function() {
			if (v == 1) {
				thisMovie('WebCAD').executeCommand('');
			}
		})
		//点击第二个按钮时
		$(".bigbomb_btn_two").click(function() {
			if (v == 1) {
				thisMovie('WebCAD').executeCommand('ZOOMALL');
			}
		})
		//点击第三个按钮时
		$(".bigbomb_btn_three").click(function() {
			if (v == 1) {
				thisMovie('WebCAD').executeCommand('DRAG');
			}
		})
		//点击第四个按钮时
		$(".bigbomb_btn_four").click(function() {
			if (v == 1) {
				thisMovie('WebCAD').executeCommand('ZOOMWINDOW');
			}
		})
		//点击第五个按钮时
		$(".bigbomb_btn_five").click(function() {
			if (v == 1) {
				thisMovie('WebCAD').executeCommand('ZOOMIN');
			}
		})
		//点击第六个按钮时
		$(".bigbomb_btn_six").click(function() {
			if (v == 1) {
				thisMovie('WebCAD').executeCommand('ZOOMOUT');
			}
		})
		//点击第七个按钮时
		$(".bigbomb_btn_seven").click(function() {
			if (v == 1) {
				thisMovie('WebCAD').executeCommand('BKCOLOR');
			}
		})
		//点击第八个按钮时
		$(".bigbomb_btn_eight").click(function() {
			if (v == 1) {
				try {
					thisMovie("WebCAD").executeCommand('FULLSCREEN');
				} catch (e) {
					alert(e);
				}
			}
		})
		//点击第九个按钮时
		$(".bigbomb_btn_nine").click(function() {
			if (v == 1) {
				$(this).addClass("bigbomb_btn_nine_checked");
				thisMovie('WebCAD').executeCommand('DIST');
			}
		})
		//点击第十个按钮时
		$(".bigbomb_btn_ten").click(function() {
			if (v == 1) {
				$(this).addClass("bigbomb_btn_ten_checked");
				thisMovie('WebCAD').executeCommand('AREA');
			}
		})
	</script>
</body>
</html>