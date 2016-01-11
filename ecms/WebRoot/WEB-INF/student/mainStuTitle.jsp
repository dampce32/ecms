<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String htmlNameFull = request.getServletPath().substring(request.getServletPath().lastIndexOf("/")+1);
String htmlName = htmlNameFull.substring(0,htmlNameFull.lastIndexOf("."));
%>

<!DOCTYPE html>
<html>
  <head>
  	 <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  	<base href="<%=basePath%>">
    <title>考试系统</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
   
    
    <link href="js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="js/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    
    <script type="text/javascript" src="js/common/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="js/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    	$(function(){
    		var id = "<%=htmlName%>";
    		$("#"+id).addClass("active");
    		
    		//退出系统
    	 	 $('#exitSystem').click(function(){
    	     	if(confirm('确定要退出系统吗?')){
   	     			$.post('student/logout.do',function(result){
   	     				if(result.isSuccess){
   	     					window.location.href='loginStu.html';
   	     				}
   	     			},'json');
   	     		}
    	     });
    	});
    </script>
  </head>
  <body>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="student/mainStu.do">考试系统</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li id="mainStu">
                <a href="student/mainStu.do">首页</a>
              </li>
              <li id="viewPaper">
                <a href="student/viewPaper.do">考试</a>
              </li>
              <li id="myAnswer">
                <a href="student/myAnswer.do">我的答卷</a>
              </li>
            </ul>
          </div>
          
          <div class="nav-collapse collapse" style="float: right">
            <ul class="nav">
              <li>
				<a id="studentName"></a>
              </li>
              <li>
                <a href="javaScript:void(0)" id="exitSystem">退出系统</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <script type="text/javascript">
	    $.post('student/getStudentName.do',function(result){
			if(result.isSuccess){
				$('#studentName').html('考生：'+result.data.studentName);
			}
		},'json');
    </script>

</body>
</html>
