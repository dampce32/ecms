<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<jsp:include page="indexTitle.jsp"></jsp:include>
<div class="main">
  <div class="slider_top">
    <div class="header_text2">
      <h2>公告：${informationTitle}</h2>
     <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg2">
   <div class="clr"></div>
  </div>
  <div class="clr"></div>
  <div class="contentBd" style="">
  		<table  class="table table-bordered" >
  			<tr>
  				<td>${content}</td>
  			</tr>
  		</table>
		
	</div>
</div>
<jsp:include page="indexFooter.jsp"></jsp:include>
     
