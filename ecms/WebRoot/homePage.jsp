<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String htmlNameFull = request.getServletPath().substring(request.getServletPath().lastIndexOf("/")+1);
String htmlName = htmlNameFull.substring(0,htmlNameFull.lastIndexOf("."));
String currCompetitionId = request.getParameter("competitionId")==null?"":request.getParameter("competitionId");
%>
<!DOCTYPE html>
<html>
  <head>
  	 <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  	<base href="<%=basePath%>">
    <title>英语竞赛报名系统</title>
   
    <link href="js/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="js/colorbox/colorbox.css" rel="stylesheet">
    <link href="js/jquery.bxslider/jquery.bxslider.css" rel="stylesheet">
    <link href="style/v1/style.css" rel="stylesheet" type="text/css" />
    <link href="style/v1/homePage.css" rel="stylesheet">
     <!--[if lte IE 6]>
    <link rel="stylesheet" type="text/css" href="js/bootstrap/css/bootstrap-ie6.css">
    <link href="style/v1/homePage-ie6.css" rel="stylesheet">
    <![endif]-->
    <!--[if lte IE 7]>
    <link rel="stylesheet" type="text/css" href="js/bootstrap/css/ie.css">
     <script type="text/javascript">
   </script>
    <![endif]-->
    
    <script type="text/javascript" src="js/common/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="js/common/commons-ajaxclient.js"></script>
    <script type="text/javascript" src="js/common/jquery-cookies.js"></script>
    <script type="text/javascript" src="js/common/jquery.form.js"></script>
    <script type="text/javascript" src="js/colorbox/jquery.colorbox-min.js"></script>
    <script type="text/javascript" src="js/colorbox/jquery.colorbox-zh-CN.js"></script>
    <script type="text/javascript" src="js/jquery.bxslider/jquery.bxslider.js"></script>
    <script type="text/javascript" src="js/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/common/easySlider1.5.js"></script>
    <script type="text/javascript" src="js/homePage.js"></script>
    <script type="text/javascript">
    	var id = "<%=htmlName%>";
    	var currCompetitionId = ${homePageMap.currCompetitionId};
    	var studentName = "${sessionScope.studentName}" ;
    </script>
</head>
<body>
    <div class="navbar  navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container" style="width: 984px">
          <div>
          	<div class="btn-group pull-left" style="margin-right: 10px">
			  <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
			  	<span id="currCompetitionName">${homePageMap.currCompetitionName}</span>
			  	 <span class="caret"></span>
			  </a>
			  <ul class="dropdown-menu" id="dropdownMenu">
			  	<c:forEach items="${homePageMap.competitionList}" var="competition">
			  		<c:choose>
					   <c:when test="${competition.competitionId==homePageMap.currCompetitionId}">
					   		<li class="active">
								<a href="#" competitionId="${competition.competitionId}" >${competition.competitionName}</a>
					  		</li>
					   </c:when>
					   <c:otherwise>
					   		<li>
					   			<a href="#" competitionId="${competition.competitionId}" >${competition.competitionName}</a>
					  		</li>
					   </c:otherwise>
					</c:choose>
	        	</c:forEach>
			  </ul>
			</div>
          </div>
          <div class="nav-collapse collapse">
            <ul class="nav nav-pills">
              <li id="homePage">
                <a href="#" li="homePage">首页</a>
              </li>
              <li id="competitionRule">
                <a href="#" li="competitionRule">大赛章程</a>
              </li>
              <li id="competitionNotice">
                <a href="#" li="competitionNotice">赛事公告</a>
              </li>
              <li id="competitionPhoto">
                <a href="#" li="competitionPhoto">赛事风采</a>
              </li>
              <li class="dropdown" id="competitionExt">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">赛事功能 <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li id="viewPaper"><a href="#" li="viewPaper">在线测试</a></li>
                  <li id="trainingClass"><a href="#" li="trainingClass">赛前培训</a></li>
                  <li id="stadium"><a href="#" li="stadium">选择赛场</a></li>
                  <li id="nextCompetitionStudent"><a href="#" li="nextCompetitionStudent">晋级名单</a></li>
                  <li id="competitionPrizeStudent"><a href="#" li="competitionPrizeStudent">获奖名单</a></li>
                </ul>
              </li>
              <li id="myAccount">
                <a href="#" li="myAccount">个人账户</a>
              </li>
            </ul>
          </div>
          <div class="pull-right" style="margin-top: 10px;" id="loginStuPanel"> 
	   		<div id="loginedDiv">
	   			欢迎您，<span id="studentName">${studentName}</span>&nbsp;&nbsp;<a href="javaScript:void(0)" id="exitSystem">退出系统</a>
	   		</div>
	   		<div id="loginingDiv">
		   		<a href="javascript:void(0)" id="loginBtnTitle">登录</a>&nbsp;&nbsp;<a href="register.html" target="_blank">注册</a>
	   		</div>
          </div>
        </div>
      </div>
    </div>
<div class="main">
  <div class="slider_top">
    <div class="header_text">
      <div class="gallery">
        <div id="slider">
          <ul>
            <li>
              <div class="div">
                <div class="left1">
                 <h2>${homePageMap.currCompetitionName}</h2>
        		  <p>${homePageMap.currCompetitionNote}</p>
                 <p><a href="#" class="btn btn-primary btn-large" id="toCompetition">我要报名 &raquo;</a></p>
                </div>
                <img src="${pictureBasePath}/${homePageMap.currCompetitionPic.pictureId}.png" alt="screen 1" style="width: 416px;height:240px;border: 0px; " class="screen"  /> </div>
            </li>
            <li>
              <div class="div">
                <div class="left1">
                  <h2>${homePageMap.systemConfig.companyName}</h2>
                  ${homePageMap.systemConfig.companyProfiles}
                    <br />
                </div>
              <img src="${pictureBasePath}/${homePageMap.systemConfig.picture.pictureId}.png"" alt="screen 1" style="width: 416px;height:240px;border: 0px; " class="screen"  /> </div>
            </li>
          </ul>
          <div class="clr"></div>
        </div>
      </div>
      <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg">
  <div class="top_bg_resize"><h2></h2></div>
  <div class="clr"></div>
  </div>
  <div class="clr"></div>
  <div class="FBG ">
  <div class="FBG_resize  unit"> 
    <div class="Recent">
    <img src="style/v1/images/homePage/top_img_1.gif" alt="picture" width="61" height="60" />
<p><strong>大赛章程</strong><br />
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本次大赛的章程和报名入口</p>
    </div>
    <div class="Recent">
    <img src="style/v1/images/homePage/top_img_2.gif" alt="picture" width="61" height="60" />
<p><strong>赛事公告&赛事风采</strong><br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;大赛的公告、主持人风采和选手风采</p>
    </div>
    <div class="Recent">
    <img src="style/v1/images/homePage/top_img_3.gif" alt="picture" width="61" height="60" />
<p><strong>赛事功能</strong><br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在线测试、赛前培训、选择赛场、晋级名单和获奖名单</p>
    </div>
    <div class="Recent2">
    <img src="style/v1/images/homePage/top_img_4.gif" alt="picture" width="61" height="60" />
<p><strong>个人账户</strong><br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个人信息、我的报名、我的在线考试、我的培训班级和我的赛场</p>
    </div>
    
    <div class="clr"></div>
  </div>
  <div class="clr"></div>
</div>
  <div class="clr"></div>
   <div class="body">
    <div class="body_resize unit">
      <div class="left">
        <h2>${homePageMap.systemConfig.companyName}</h2>
        ${homePageMap.systemConfig.companyProfiles}
      </div>
      <div class="right" >
        <h2>咨询电话</h2>
        <p style="width: 300px">
        	${homePageMap.systemConfig.hotline}
       	</p>
      </div>
      <div class="clr"></div>
    </div>
    <div class="clr"></div>
  </div>
    <div class="body_resize">
    	友情链接：<a href="http://www.eflyedu.cn/" target="_balank">福州一飞教育培训学校</a> &nbsp; &nbsp;&nbsp;<a href="http://www.csit.cc/" target="_balank">福建骏华信息科技有限公司</a>
    </div>
  <div class="footer">
  	<div class="footer_resize">
	  <p class="leftt">© Copyright 2009. Your Site Name Dot Com. All Rights Reserved<br />
	    <p class="rightt"><a href="#"><strong>Design by 福建骏华信息科技有限公司 </strong></a></p>
	    <div class="clr"></div>
	  <div class="clr"></div>
</div>
</div>
</div>
    
    <div id="loginModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 600px;">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">用已注册的用户账号，直接登录</h3>
	  </div>
	  <div class="modal-body">
	  	<div class="pull-left">
	  		<form id="loginForm">
			   <table>
					<tr  style="padding-top: 3px">
						<td align="right">用户名：</td>
						<td><input name="userCode" type="text" id="userCode" required style="width:200px;" placeholder="用户名"/>
						</td>
					</tr>
					<tr>
						<td align="right">密码：</td>
						<td><input  name="userPwd" type="password" id="userPwd" required  style="width:200px;"  placeholder="密码"/>
						</td>
					</tr>
					<tr>
						<td align="right">验证码：</td>
						<td>
							<input type="text" id="loginCaptcha" required name="j_captcha"  class="formTextS" style="width:113px;text-transform: uppercase;" />
							<img id="loginCaptchaImage"  src="captcha.jpg" alt="换一张" style="cursor: pointer; margin-top:-18px"/>
						</td>
					</tr>
					<tr>
						<td align="right">&nbsp;
						</td>
						<td>
						    <button class="btn btn-primary"  id="loginBtn">登录</button>&nbsp;&nbsp;&nbsp;<input name="mindpwd" type="checkbox" id="mindpwd" />&nbsp;记住密码 &nbsp;&nbsp;
						    <a href="resetPwd.html" target="_blank">忘记密码</a>
						</td>
					</tr>
					<tr>
						<td align="right">&nbsp;
						</td>
						<td>
							  <span id="inputWarn">&nbsp;</span> 
						</td>
					</tr>
				</table>
			</form>
	  	</div>
	  	<div  class="pull-right" style="margin:20px auto;width: 200px" >
	  		还没注册，立即注册吧！
	  		<a href="register.html" target="_blank" class="btn btn-primary" id="toRegister">注册 &raquo;</a>
	  	</div>
	  </div>
	</div>
    
  </body>
</html>
     
