<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="mainStuTitle.jsp"></jsp:include>
  	<div class="container">
      <div class="hero-unit">
        <table border="1px">
        	<tr align="center">
       			<td style="width: 200px;">
       				试卷名称
       			</td>
       			<td style="width: 200px">
       				得分
       			</td>
       		</tr>
        	<c:forEach items="${answerList}" var="answer">
       		<tr align="center">
       			<td>
       				${answer.paperName}
       			</td>
       			<td>
       				${answer.score}
       			</td>
       		</tr>
        	</c:forEach>
        </table>
        <div class="pagination">  
        	<ul>    
	        	<li><a href="student/myAnswer.do">首页</a></li>
	        	<c:if test="${page<=1}">
	        		<li class="disabled"><a>◄上一页</a></li>
	        	</c:if>
	        	<c:if test="${page>1}">
	        		<li><a href="student/myAnswer.do?page=${page-1}">◄上一页</a></li>
	        	</c:if>
	        	<c:if test="${page>=lastPage}">
	        		<li class="disabled"><a href="student/myAnswer.do?page=${page+1}">下一页►</a></li>
	        	</c:if>
	        	<c:if test="${page<lastPage}">
	        		<li><a href="student/myAnswer.do?page=${page+1}">下一页►</a></li>
	        	</c:if>
	        	<li><a href="student/myAnswer.do?page=${lastPage}">末页</a></li>
			</ul>
			<ul class="pull-center">  
		        <li class="disabled"><a>第  ${startIndex}- ${endIndex}条记录 / 共${total}条</a></li>  
		    </ul> 
			
		</div>
       </div>
    </div>
<jsp:include page="mainStuFooter.jsp"></jsp:include>
     
