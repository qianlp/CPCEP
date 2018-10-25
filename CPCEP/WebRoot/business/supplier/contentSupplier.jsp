<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@include file="../../resource.jsp" %>
    <title>信息</title>
   
  </head>
  
  <body>
  <div style="margin:2px,3px,5px,4px">
    <a class="mini-button" iconCls="icon-add" onclick="saveContent('ok')">保存</a>
    </div>
      <textarea name="content" id="content" style="width:280px;height:130px;"></textarea>
      <script type="text/javascript">
      function saveContent(e){
    	  if($("#content").val()==""||$("#content").val()==null){
    		  alert("请输入审核信息");
    		  return ;
    	  }
    	  if(e=="ok"){
    		 window.CloseOwnerWindow($("#content").val());
    	  }
      }
      </script>
  </body>
</html>
