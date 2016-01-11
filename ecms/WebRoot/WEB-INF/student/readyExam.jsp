<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="mainStuTitle.jsp"></jsp:include>
	<div class="container">
      <div class="hero-unit">
      	<h2>考试准备</h2>
      	<table>
      		<tr>
       			<td>
       				考试类型：
       			</td>
       			<td>
       				${arrange.examType}
       			</td>
       		</tr>
       		<tr>
       			<td>
       				答卷类型：
       			</td>
       			<td>
       				${arrange.resultType}
       			</td>
       		</tr>
       		<tr>
       			<td>
       				试卷名称：
       			</td>
       			<td>
       				${arrange.paper.paperName}
       			</td>
       		</tr>
       		<tr>
       			<td>
       				限时：
       			</td>
       			<td>
       				${arrange.paper.limits}（分钟）
       			</td>
       		</tr>
       		<tr>
       			<td>
       				开考时间：
       			</td>
       			<td>
       				${arrange.beginDateTime}
       			</td>
       		</tr>
       		<tr>
       			<td>
       				截止时间：
       			</td>
       			<td>
       				${arrange.endDateTime}
       			</td>
       		</tr>
       		<tr>
       			<td colspan="2">
       				<a href="student/exam.do?paper.paperId=${arrange.paper.paperId}&arrange.arrangeId=${arrange.arrangeId}" class="btn btn-primary btn-large">开始考试&raquo;</a>
       			</td>
       		</tr>
        </table>
       </div>
	</div>
<jsp:include page="mainStuFooter.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		$("#viewPaper").addClass("active");
	});
</script>