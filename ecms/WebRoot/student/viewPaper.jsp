<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../indexTitle.jsp"></jsp:include>
<div class="main">
  <div class="slider_top">
    <div class="header_text2">
      <h2>在线测试</h2>
     <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg2">
   <div class="clr"></div>
  </div>
  <div class="clr"></div>
  <div class="contentBd">
      	 <h3>请选择答卷</h3>
      	 <c:if test="${not empty examPaperList}">
      	 	<table   class="table table-bordered table-hover">
	        	<c:forEach items="${examPaperList}" var="examPaper">
	        		<tr>
	        			<td >
	        				<a href="student/readyExam.do?competitionId=${examPaper.competitionId}&groupId=${examPaper.groupId}&paperId=${examPaper.paperId}&studentCompetitionGroupId=${examPaper.studentCompetitionGroupId}">${examPaper.groupName}--${examPaper.paperName}</a>
	        			</td>
	        		</tr>
	        	</c:forEach>
	        </table>
      	 </c:if>
        
      </div>
    </div> 
<jsp:include page="../indexFooter.jsp"></jsp:include>
     
