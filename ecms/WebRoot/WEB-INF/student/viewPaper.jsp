<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="mainStuTitle.jsp"></jsp:include>
  	<div class="container">
      <div class="hero-unit">
        <h2>请选择答卷</h2>
        <table>
        	<c:forEach items="${arrangeList}" var="arrange">
        		<tr>
        			<td>
        				<a href="student/readyExam.do?examStudentCompetitionGroupId=${arrange.arrangeId}">${arrange.paperName}</a>
        			</td>
        		</tr>
        	</c:forEach>
        </table>
       </div>
    </div>
<jsp:include page="mainStuFooter.jsp"></jsp:include>
     
