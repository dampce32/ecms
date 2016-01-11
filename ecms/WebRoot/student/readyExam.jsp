<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../indexTitle.jsp"></jsp:include>
<div class="main">
  <div class="slider_top">
    <div class="header_text2">
      <h2>考试准备</h2>
     <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg2">
   <div class="clr"></div>
  </div>
  <div class="clr"></div>
  <div class="contentBd" style="">
      	<table class="table table-bordered">
      		<tr>
       			<td class="tdThead" style="width: 150px">
       				参赛组别：
       			</td>
       			<td >
       				${readyExamMap.groupName}
       			</td>
       		</tr>
       		<tr>
       			<td class="tdThead">
       				试卷名称：
       			</td>
       			<td>
       				${readyExamMap.paperName}
       			</td>
       		</tr>
       		<tr>
       			<td class="tdThead">
       				限时：
       			</td>
       			<td>
       				${readyExamMap.limits}（分钟）
       			</td>
       		</tr>
       		<tr>
       			<td class="tdThead" colspan="2">
       				<a href="student/exam.do?competitionId=${readyExamMap.competitionId}&groupId=${readyExamMap.groupId}&paperId=${readyExamMap.paperId}&studentCompetitionGroupId=${readyExamMap.studentCompetitionGroupId}" class="btn btn-primary btn-large" style="float: left;margin-left: 160px">开始考试&raquo;</a>
       			</td>
       		</tr>
        </table>
       </div>
	</div>
<jsp:include page="../indexFooter.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		$("#viewPaper").addClass("active");
	});
</script>