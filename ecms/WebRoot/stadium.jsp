<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="indexTitle.jsp"></jsp:include>
<script type="text/javascript">
      //申请
      function onAdd(stadiumId,stadiumName){
    	  if(window.confirm("您确定选择赛场["+stadiumName+"]吗?")){
    		  if(validateLogin()){
    			  var content = {competitionId:currCompetitionId,'stadium.stadiumId':stadiumId};
    			  var url = 'student/saveStadiumStudent.do';
				  var result = syncCallService(url,content);
				  if(result.isSuccess){
					  window.location.href="student/initMyStadium.do?infoType=myStadium&competitionId="+currCompetitionId;
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
      <h2>选择赛场</h2>
     <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg2">
   <div class="clr"></div>
  </div>
  <div class="clr"></div>
    <div class="contentBd">
    	<h3>赛场</h3>
      	<table class="table table-bordered table-hover">
      		<thead>
      			<tr>
	      			<td class="tdThead">
						 参赛组别
	      			</td>
	      			<td class="tdThead">
						 赛场名称
	      			</td>
	      			<td class="tdThead">
						 比赛地点
	      			</td>
	      			<td class="tdThead">
						 比赛时间
	      			</td>
	      			<td class="tdThead">
						 已报人数
	      			</td>
	      			<td class="tdThead">
						 人数上限
	      			</td>
	      			<td class="tdThead">
						 操作
	      			</td>
	      		</tr>
	      	</thead>
	      	<tbody>
      		<c:forEach items="${stadiumList}" var="stadium">
      			<tr>
	      			<td class="td">
						 ${stadium.competitionGroup.group.groupName}
	      			</td>
	      			<td class="td">
						 ${stadium.stadiumName}
	      			</td>
	      			<td class="td">
						 ${stadium.stadiumAddr}
	      			</td>
	      			<td class="td">
						 ${stadium.competitionDate}
	      			</td>
	      			<td class="td">
						${stadium.arrangeNo}
	      			</td>
	      			<td class="td">
						${stadium.limit}
	      			</td>
	      			<td class="td">
						 <button class="toEnrollBtn btn btn-primary btn-small" onclick="onAdd(${stadium.stadiumId},'${stadium.stadiumName}');">选择</button>
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
     
