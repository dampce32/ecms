<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="indexTitle.jsp"></jsp:include>
<div class="main">
  <div class="slider_top">
    <div class="header_text2">
      <h2>大赛章程</h2>
     <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg2">
   <div class="clr"></div>
  </div>
  <div class="clr"></div>
  <div class="contentBd"  style="border: 1px solid #d4d4d4;">
		<div style="text-align: center;margin-top: 5px">
	  		<input type="button" class="toEnrollBtn btn btn-primary btn-large" value="已阅读完 ，我要报名"/>
	  	</div>
		<div id="content" style="padding: 0px 15px">
			${content}
		</div>
		<div style="text-align: center;margin-bottom: 5px">
	  		<input type="button" class="toEnrollBtn btn btn-primary btn-large" value="已阅读完 ，我要报名"/>
	  	</div>
	</div>
</div>
<jsp:include page="indexFooter.jsp"></jsp:include>
     
