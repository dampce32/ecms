<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>考试系统</title>
	<link rel="stylesheet" type="text/css" href="style/common.css">
	<link rel="stylesheet" type="text/css" href="style/v1/easyui/easyui.css">
	<link rel="stylesheet" type="text/css" href="style/v1/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery.countdown/jquery.countdown.css">
	<link rel="stylesheet" type="text/css" href="style/v1/exam.css">
	
	<script type="text/javascript" src="js/common/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/common/jquery.xLazyLoader.js"></script>
	<script type="text/javascript" src="js/common/commons-ajaxclient.js"></script>
	<script type="text/javascript" src="js/common/mask.js"></script>
	<script type="text/javascript" src="js/common/csit.core.js"></script>
	<script type="text/javascript" src="js/jquery.countdown/jquery.countdown.min.js"></script>
	<script type="text/javascript" src="js/student/exam.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',title:'答题卡',split:true,border:false" style="width: 250px">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'south'" style="height: 70px;border-bottom: none;border-left: none;border-right: none;font-size: 14px;padding: 10px">
				<span style="font-weight: bold;">姓名：</span><span id="studentName"></span><br><br>
				
				<span style="font-weight: bold;">准考证号：</span><span id="examCode"></span>
			</div>
			<div data-options="region:'center',border:false">
				<div id="subjectTable">
				</div>
			</div>
		</div>
	
	</div>
	<div data-options="region:'center',title:'答题区',border:false" >
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" class="paperInfo">
				<span id="paperName"></span>
				<p style="font-size: 14px;text-align: left;margin-top: 2px">
					左侧答题卡说明：
					&nbsp;<span class="unAnswerNote">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;未答题
					&nbsp;<span class="answeringNote">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;正在答题
					&nbsp;<span class="partAnsweredNote">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;已部分答题
					&nbsp;<span class="answeredNote">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;已答完题
				</p>
				<input id="competitionId" type="hidden" value="${examMap.competitionId}">
				<input id="groupId" type="hidden" value="${examMap.groupId}">
			</div>
			<div data-options="region:'center',border:false">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north',border:false" class="bigInfo">
						<div class="bigNameAnswer" id="bigName">
						</div>
					</div>
					<div id="answerPanel" data-options="region:'center'">
					</div>
				</div>
			</div>
			<div data-options="region:'south',split:true" class="examToolbar">
				<div id="timeIndex">距考试结束时间还有：</div>
				<div id="countdown"></div>
				<div class="btnBar">
					<a href="#" class="easyui-linkbutton" id="okExam" data-options="iconCls:'icon-ok',plain:true" style="cursor: pointer;float: right;font-size: 23px">交卷</a>
					<a href="#" class="easyui-linkbutton" id="nextSmall" data-options="iconCls:'icon-down',plain:true"  style="cursor: pointer;float: right;font-size: 23px">下一题</a>
					<a href="#" class="easyui-linkbutton" id="upSmall" data-options="iconCls:'icon-up',plain:true" style="cursor: pointer;float: right;font-size: 23px">上一题</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
