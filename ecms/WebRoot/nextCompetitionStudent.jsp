<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="indexTitle.jsp"></jsp:include>
<div class="main">
  <div class="slider_top">
    <div class="header_text2">
      <h2>晋级名单</h2>
     <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg2">
   <div class="clr"></div>
  </div>
  <div class="clr"></div>
	<div class="contentBd">
		<div class="row">
			<div class="span2" style="width: 240px">
	          <div class="well sidebar-nav" style="margin:10px">
	            <ul class="nav nav-list">
	              <li class="nav-header" style="font-weight: bold;font-size: 16px">参赛组别</li>
	              <c:forEach items="${competitionGroupList}" var="competitionGroup">
	             	 <li class="nextCompetitionStudent" style="font-size: 12px"><a href='initNextCompetitionStudent.do?competitionId=${competitionGroup.competition.competitionId}&competitionGroupId=${competitionGroup.competitionGroupId}&page=0' nextCompetitionStudent="${competitionGroup.competitionGroupId}">${competitionGroup.group.groupName}</a></li>
	              </c:forEach>
	            </ul>
	          </div>
	        </div>
	        	<div class="span8">
			      	<table class="table table-bordered  table-hover" style="margin:10px">
			      		<thead>
			      			<tr>
				      			<td class="tdThead">
									赛事
				      			</td>
				      			<td class="tdThead">
									  参赛组别
				      			</td>
				      			<td class="tdThead">
									 晋级学生
				      			</td>
				      			<td class="tdThead">
									 分数
				      			</td>
				      		</tr>
				      	</thead>
				      	<thead>
				      		<c:forEach items="${nextCompetitionStudentList}" var="nextCompetitionStudent">
					      		<tr>
					      			<td  style="font-size: 14px;text-align: center;">${nextCompetitionStudent.competitionGroup.competition.competitionName}</td>
					      			<td  style="font-size: 14px;text-align: center;">${nextCompetitionStudent.competitionGroup.group.groupName}</td>
					      			<td  style="font-size: 14px;text-align: center;">${nextCompetitionStudent.student.studentName}</td>
					      			<td  style="font-size: 14px;text-align: center;">${nextCompetitionStudent.score}</td>
					      		</tr>
				      		</c:forEach>
				      	</thead>
			      	</table>
			      	<div>
			      		<input type="hidden" id="total" value="${total}">
			  			<input type="hidden" id="currPage" value="${currPage}">
						<div id="Pagination" class="pagination"></div>
			      		<div style="clear: both"></div>
			      	</div>
		    	</div>
			</div>
     </div>
</div>
<jsp:include page="indexFooter.jsp"></jsp:include>
     
