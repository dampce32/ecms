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
		<div id="subjectTable">
		</div>
	</div>
	<div data-options="region:'center',title:'答题区',border:false" >
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" class="paperInfo">
				<span id="paperName"></span>
				<input id="paperId" type="hidden" value="${paper.paperId}">
				<input id="arrangeId" type="hidden" value="${arrange.arrangeId}">
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
					<a href="#" class="easyui-linkbutton" id="okExam" data-options="iconCls:'icon-ok',plain:true" style="cursor: pointer;float: right">交卷</a>
					<a href="#" class="easyui-linkbutton" id="nextSmall" data-options="iconCls:'icon-down',plain:true"  style="cursor: pointer;float: right">下一题</a>
					<a href="#" class="easyui-linkbutton" id="upSmall" data-options="iconCls:'icon-up',plain:true" style="cursor: pointer;float: right">上一题</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
