<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="indexTitle.jsp"></jsp:include>
<div class="main">
  <div class="slider_top">
    <div class="header_text2">
      <h2>赛事风采</h2>
     <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg2">
   <div class="clr"></div>
  </div>
  <div class="clr"></div>
	<div class="contentBd">
		<div class="row">
			<div class="span2" style="width: 170px;">
	          <div class="well sidebar-nav" style="margin:10px">
	            <ul class="nav nav-list">
	              <li class="nav-header" style="font-weight: bold;font-size: 16px">图片类型</li>
	              <li class="photoType"><a href="#" photoType="compere">主持人风采</a></li>
	              <li class="photoType" ><a href="#" photoType="competitor">选手风采</a></li>
	            </ul>
	          </div>
	        </div>
	        <div class="span9">
	        	<c:forEach items="${photoList}" var="photo">
		        	<a class="group1"  href="${pictureBasePath}/${photo.picture.pictureId}.png" title="${photo.note}" style="border: 0px;width: 200px;height: 130px">
		        		<img alt="" src="${pictureBasePath}/t_${photo.picture.pictureId}.png" style="margin: 10px;width: 200px;height: 130px">
				  	</a>
	        	</c:forEach>
	        	<input type="hidden" id="total" value="${total}">
	        	<input type="hidden" id="currPage" value="${currPage}">
	        	<div id="Pagination" style="line-height: 20px;"></div>
	        </div>
		</div>
     </div>
</div>
<jsp:include page="indexFooter.jsp"></jsp:include>
     
