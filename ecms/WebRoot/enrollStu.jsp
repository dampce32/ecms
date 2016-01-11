<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String currCompetitionId = request.getParameter("competitionId")==null?"":request.getParameter("competitionId");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <base href="<%=basePath%>">
    <title>大赛报名</title>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
	
	<link rel="stylesheet" href="style/v1/icon.css" type="text/css"></link>
    <link rel="stylesheet" href="style/v1/easyui/easyui.css" type="text/css"></link>
    <style type="text/css">
    	#main{
			width: 500px;
			background-color: rgb(250,250,250);
		}
    	.title{
			text-align: left;
			background-color: rgb(204,204,255);
			color: rgb(241,130,71);
			height: 30px;
			line-height: 30px;
			overflow:hidden;
			padding-left: 10px;
		}
		.content{
			padding:10px;
		}
		.tdTitle{
			text-align: right;
			width: 120px;
		}
		.tdContent{
			width: 270px;
		}
    </style>
    
    <script type="text/javascript">
    	var currCompetitionId = "<%=currCompetitionId%>";
    </script>
    <script type="text/javascript" src="js/common/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/common/commons-ajaxclient.js"></script>
	<script type="text/javascript" src="js/enrollStu.js"></script>

  </head>
  
  <body style="background-image:url('style/v1/images/register_bg.jpg');">
	  <center>
	  	<h1>大赛报名</h1>
	    <div id="main">
	    	<div class="title">
				 报名信息
		    </div>
		    <div class="content">
			    <form method="post" id="editForm">
					<input type="hidden" name="status" id="status"/>
					<input type="hidden" name="competitionGroup.competition.competitionId" id="competitionId"/>
					<table>
						<tr>
							<td class="tdTitle">参赛地区：</td>
		    				<td class="tdContent">
		    					<input type="text" id="province"/>
		    				</td>
						</tr>
						<tr>
							<td class="tdTitle"></td>
		    				<td class="tdContent">
				    			<input type="text" id="city"/>
		    				</td>
						</tr>
						<tr>
							<td class="tdTitle"></td>
		    				<td class="tdContent">
				    			<input type="text" name="area.areaId" id="area"/>
		    				</td>
						</tr>
						<tr>
							<td class="tdTitle">赛事：</td>
		    				<td class="tdContent">
		    					<span id="competition"></span>
		    				</td>
						</tr>
						<tr>
							<td class="tdTitle">参赛组别：</td>
		    				<td class="tdContent">
		    					<input type="text" name="competitionGroup.competitionGroupId" id="competitionGroup"/>
		    				</td>
						</tr>
					</table>
		    	</form>
			</div>
			<center style="margin-top: 20px;margin-bottom: 20px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="submit" style="cursor: pointer;">提 交</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" id="back" style="cursor: pointer;margin-left:20px;">返 回</a>
			</center>
	    </div>
	</center>
  </body>
</html>
