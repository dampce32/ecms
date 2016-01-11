<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="<%=basePath%>">
	<title>骏华英语竞赛管理系统</title>
	<link rel="stylesheet" type="text/css" href="style/common.css">
	<link rel="stylesheet" type="text/css" href="style/v1/easyui/easyui.css">
	<link rel="stylesheet" type="text/css" href="style/v1/icon.css">
	<link rel="stylesheet" type="text/css" href="style/v1/main.css">
	<link rel="stylesheet" type="text/css" href="js/GooUploader/GooUploader.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery.countdown/jquery.countdown.css">
	<link rel="stylesheet" type="text/css" href="style/v1/exam.css">
	
	<style type = "text/css">   
		a:link {text-decoration:none;}
	</style>
	<script type="text/javascript" src="js/common/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/common/jquery.xLazyLoader.js"></script>
	<script type="text/javascript" src="js/common/serializeForm.js"></script>
	<script type="text/javascript" src="js/common/commons-ajaxclient.js"></script>
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/GooUploader/GooUploader.js"></script>
	<script type="text/javascript" src="js/GooUploader/swfupload/swfupload.js"></script>
	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="ckfinder/ckfinder.js"></script>
	<script type="text/javascript" src="js/common/csit.core.js"></script>
	<script type="text/javascript" src="js/common/mask.js"></script>
	<script type="text/javascript" src="js/common/ecms.cache.js"></script>
	<script type="text/javascript" src="js/common/dateUtil.js"></script>
	<script type="text/javascript" src="js/common/datagrid-datailview.js"></script>
	<script type="text/javascript" src="js/jquery.countdown/jquery.countdown.min.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
	<script type="text/javascript">
		var sessionId = '${pageContext.session.id}' ;	
	</script>
	 
</head>
<body class="easyui-layout" id="all-body" >
<div region="north" id="index-north" border="false">
	<div  id="index-north-bd">
			<div id="index-north-bd-left" class="f-l">
            	<span style="font-size: 25;padding-left: 22px">骏华英语竞赛管理系统</span>
            </div>
            <div id="index-north-bd-right" class="f-r">
            	 赛事：&nbsp;&nbsp;<input id="competitionSearchMain"  class="queryInput"/>&nbsp;&nbsp;&nbsp;
            	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="modifyPwdBtn">修改密码</a>
                 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" id="exitSystem">退出</a>
                 <a href="help/index.html" target="_blank"  class="easyui-linkbutton" iconCls="icon-help">帮助</a>
                 <!-- -->
            </div>
    </div>
</div>
<div region="west" split="true" title="--------导航菜单--------" icon="icon-none" id="index-left">
  <ul id="rightTree"></ul>
</div>

<div region="center" style="overflow: hidden;"  border="false">
      <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
                <div title="首页" id="reminder">
						<div class="easyui-layout" data-options="fit:true">
							<div data-options="region:'center',border:false">
								<div class="easyui-layout" data-options="fit:true">
									<div data-options="region:'north',split:false,border:false"  style="height:50px;hoverflow:hidden;padding: 10px;">
							    		 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" id="reloadBtn">刷新</a>
									</div>
									<div data-options="region:'center',border:false,split:false" style="padding: 10px;">
							    		<div id="stuList" data-options="border:true" ></div>
							    	</div>
							    	<div data-options="region:'south',split:false,border:false"  style="height:250px;hoverflow:hidden;padding: 10px;">
							    		<div id="noticeList" data-options="border:true"></div>
									</div>
								</div>
					    	</div>
					    	<div data-options="region:'east',split:false,border:false"  style="width: 550px;overflow: hidden;">
					    		<div class="easyui-layout" data-options="fit:true">
					    			<div data-options="region:'north',split:false,border:false"  style="height:50px;hoverflow:hidden;padding: 10px;">
									</div>
						    		<div data-options="region:'center',split:false,border:false" style="padding: 10px;">
							    		<div id="stadiumList" data-options="border:true" ></div>
							    	</div>
							    	<div data-options="region:'south',split:false,border:false"  style="height:250px;overflow: hidden;padding: 10px">
							    		<div id="trainingClassList" data-options="border:true"></div>
									</div>
								</div>
							</div>
					    </div>
				</div>
        </div>
</div>
<div id="it">
	<a href="javascript:void(0)" style="width: 40px;" id="iMore">更多>></a>
</div>
<div id="et">
	<a href="javascript:void(0)" style="width: 40px;" id="eMore">更多>></a>
</div>
<div id="tt">
	<a href="javascript:void(0)" style="width: 40px;" id="tMore">更多>></a>
</div>
<div id="st">
	<a href="javascript:void(0)" style="width: 40px;" id="sMore">更多>></a>
</div>
	<div id="mm" class="easyui-menu" style="width:120px">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseother">关闭其他</div>
		<div id="mm-tabcloseall">关闭全部</div>
		<div id="mm-tabcloseright">关闭当前右侧</div>
		<div id="mm-tabcloseleft">关闭当前左侧</div>
	</div>
	
	<div id="modifyPwdDialog">
			<form id="modifyPwdForm">
					<table border="0" cellpadding="0" cellspacing="0">
		     		<tr >
		     			<td class="tdFirstTitle">原密码：</td>
		     			<td class="tdFirstContent">
		     				<input type="password" name ="passwords" id="passwords">
		     			</td>
		     		</tr>
		     		<tr>

		     			<td class="tdTitle">新密码：</td>
		     			<td class="tdContent">
		     				<input type="password" name="newTeacherPwd" id="newTeacherPwd">
		     			</td>
		     		</tr>
		     		<tr>
		     			<td class="tdTitle">重新输入新密码：</td>
		     			<td class="tdContent">
		     				<input type="password" name="newTeacherPwd2" id="newTeacherPwd2">
		     			</td>
		     		</tr>
		     	</table>
			</form>
		</div>
</body>
</html>
