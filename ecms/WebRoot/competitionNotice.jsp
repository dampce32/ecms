<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="indexTitle.jsp"></jsp:include>
<div class="main">
  <div class="slider_top">
    <div class="header_text2">
      <h2>赛事公告</h2>
     <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg2">
   <div class="clr"></div>
  </div>
  <div class="clr"></div>
  <div class="contentBd" style="">
    	<table  class="table table-bordered" style="background-color: #fff">
      		<c:forEach items="${noticeList}" var="notice">
	        	<tr>
	      			<td style="width: 100%;" >
						<a href='noticeDetail.do?informationId=${notice.informationId}&competitionId=${notice.competition.competitionId}'>
						 ${notice.informationTitle}</a>   <span class="pull-right item-date">${notice.publishDate}</span> 
	      			</td>
	      		</tr>
        	</c:forEach>
      	</table>
   </div>
   	<input type="hidden" id="total" value="${total}">
	<input type="hidden" id="currPage" value="${currPage}">
	<div id="Pagination" class="pagination"></div>
   	<div style="clear: both"></div>
</div>
<jsp:include page="indexFooter.jsp"></jsp:include>
     
