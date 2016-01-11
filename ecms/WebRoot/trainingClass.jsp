<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="indexTitle.jsp"></jsp:include>
<script type="text/javascript">
      //申请
      function onAdd(trainingClassId,trainingClassName){
    	  if(window.confirm("您确定申请培训班级["+trainingClassName+"]吗?")){
    		  if(validateLogin()){
    			  var content = {competitionId:currCompetitionId,'trainingClass.trainingClassId':trainingClassId};
    			  var url = 'student/saveTrainingClassStudent.do';
				  var result = syncCallService(url,content);
				  if(result.isSuccess){
					  window.location.href="student/initMyTrainingClass.do?infoType=myStadium&competitionId="+currCompetitionId;
				  }else{
					   alert(result.message);
				  }
	 		}else{
	 			showLoginModal('viewPaper');
	 		}
          }
      }
</script>
<div class="main">
  <div class="slider_top">
    <div class="header_text2">
      <h2>赛前培训</h2>
     <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg2">
   <div class="clr"></div>
  </div>
  <div class="clr"></div>
    <div class="contentBd" >
    	<h3>培训班级</h3>
      	<table class="table table-bordered  table-hover">
      		<thead>
      			<tr>
	      			<td class="tdThead">
						 参赛组别
	      			</td>
	      			<td class="tdThead">
						 班级名称
	      			</td>
	      			<td class="tdThead">
						 地址
	      			</td>
	      			<td class="tdThead">
						 上课时间
	      			</td>
	      			<td class="tdThead">
						 上课老师
	      			</td>
	      			<td class="tdThead">
						 已报人数
	      			</td>
	      			<td class="tdThead">
						 人数上限
	      			</td>
	      			<td class="tdThead">
						 费用
	      			</td>
	      			<td class="tdThead">
						 操作
	      			</td>
	      			</tr>
	      		</thead>
	      		<tbody>
      		<c:forEach items="${trainingClassList}" var="trainingClass">
      			<tr>
	      			<td class="td">
						 ${trainingClass.competitionGroup.group.groupName}
	      			</td>
	      			<td class="td">
						 ${trainingClass.trainingClassName}
	      			</td>
	      			<td class="td">
						${trainingClass.address}
	      			</td>
	      			<td class="td">
						 ${trainingClass.classDate}
	      			</td>
	      			<td class="td">
						 ${trainingClass.classTeacher}
	      			</td>
	      			<td class="td">
						 ${trainingClass.stuCount}
	      			</td>
	      			<td class="td">
						 ${trainingClass.limit}
	      			</td>
	      			<td class="td">
						${trainingClass.total}
	      			</td>
	      			<td class="td">
						 <button class="toEnrollBtn btn btn-primary btn-small" onclick="onAdd(${trainingClass.trainingClassId},'${trainingClass.trainingClassName}');">申请</button>
	      			</td>
	      		</tr>
        	</c:forEach>
        	</tbody>
      	</table>
      	<div>
      		<input type="hidden" id="total" value="${total}">
  			<input type="hidden" id="currPage" value="${currPage}">
			<div id="Pagination" class="pagination"></div>
      		<div style="clear: both"></div>
      	</div>
    </div>
   
</div>
<jsp:include page="indexFooter.jsp"></jsp:include>
     
