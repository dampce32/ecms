var toUrl = null;
var width = $(document.body).width();
var height = $(document.body).height();
$(function() {
	if(id=='trainingClass'||id=='stadium'){
		$('#competitionExt').addClass("active");
	}
	$("#"+id).addClass("active");
	var url ='indexTitle.do';
	var content = {competitionId:currCompetitionId};
	var result = syncCallService(url,content);
	if(result.isSuccess){
		var data = result.data;
		currCompetitionId = data.currCompetitionId;
		$('#currCompetitionName').html(data.currCompetitionName);
		var currCompetitionName = $('#currCompetitionName').html();
		if(currCompetitionName.length>12){
			$('#currCompetitionName').html(currCompetitionName.substr(0,12)+'...');
		}
		$('#dropdownMenu').html(data.dropdownMenu);
		$('#dropdownMenu li a').click(function(){
			var competitionId = $(this).attr('competitionId');
			//替换当前反问地址的赛事Id
			var currHref = window.location.href;
			var reg = new RegExp("competitionId=\\d+","g");
			var result=reg.exec(currHref);
			if(result){
				//替换
				currHref = currHref.replace(reg, 'competitionId='+competitionId);
			}else{
				if(currHref.indexOf('?') == -1 ){
					currHref = currHref+'?competitionId='+competitionId;
				}
			}
			window.location.href = currHref;
			return false;
		});
	}else{
		$.messager.alert('提示',result.message,'error');
	}
	if(studentName==''){
		 $('#loginedDiv').hide();
		 $('#loginingDiv').show();
	}else{
		 $('#loginedDiv').show();
		 $('#loginingDiv').hide();
	}
	//登录
	$('#loginBtnTitle').click(function(){
		return showLoginModal();
	});
	//退出系统
	 $('#exitSystem').click(function(){
    	if(confirm('确定要退出系统吗?')){
   			$.post('login/logout.do',function(result){
   				if(result.isSuccess){
   					toLoginStatus();
   				}
   			},'json');
   		}
    });
	
	// 点击刷新验证码图片
	$("#loginCaptchaImage").click( function() {
		loginCaptchaImageRefresh();
	});
	$(document).keydown(function(e){ 
		var curKey = e.which; 
		if(curKey == 13){ 
			$('#loginBtn').click();
		} 
	});
	$("#loginBtn").click(function(){
		var url = 'login/studentLogin.do';		
		$('#loginForm').ajaxSubmit({
			url:url,
			type:'post',
			dataType:'json',
			success:function(result){	
				if(result.isSuccess){
					 if($("#mindpwd").attr("checked")){
						 var loginInfo = $("#userCode").val()+"§"+$("#userPwd").val();
						 $.cookie("loginInfoEcms",loginInfo,{expires: 30,path:'/'});
					 }else{
						 $.cookie("loginInfoEcms",null,{path:'/'});
					 }
					 var data = result.data;
					 var studentName = data.studentName;
					 toExitStatus(studentName);
					 if(toUrl!=null){
						window.location.href=toUrl;
						toUrl = null;
					 }
				}else{
					$('#inputWarn').html(result.message);
					loginCaptchaImageRefresh();
				}
			}
		});
		return false;
	});
 	 $('.nav a').click(function(){
 		 var li = $(this).attr('li');
 		 if('viewPaper'==li){
 			if(validateLogin()){
 				$(this).attr('href','student/viewPaper.do?competitionId='+currCompetitionId);
 	 		}else{
 	 			toUrl = 'student/viewPaper.do?competitionId='+currCompetitionId;
 	 			return showLoginModal('viewPaper');
 	 		}
 		 }else if('homePage'==li){
			$(this).attr('href','homePage.do?competitionId='+currCompetitionId);
 		 }else if('competitionRule'==li){
			$(this).attr('href','competitionRule.do?competitionId='+currCompetitionId);
 		 }else if('competitionNotice'==li){
			$(this).attr('href','competitionNotice.do?competitionId='+currCompetitionId+'&page=0');
 		 }else if('competitionPhoto'==li){
			$(this).attr('href','competitionPhoto.do?competitionId='+currCompetitionId+'&photoType=compere&page=0');
 		 }else if('myEnroll'==li){
			$(this).attr('href','myEnroll.jsp?competitionId='+currCompetitionId);
 		 }else if('myAccount'==li){
 			if(validateLogin()){
 				$(this).attr('href','student/initMyAccount.do?competitionId='+currCompetitionId);
 	 		}else{
 	 			toUrl = 'student/initMyAccount.do?competitionId='+currCompetitionId;
 	 			return showLoginModal('viewPaper');
 	 		}
 		 }else if('trainingClass'==li){
 			$(this).attr('href','trainingClass.do?competitionId='+currCompetitionId+'&page=0');
 		 }else if('stadium'==li){
			$(this).attr('href','stadium.do?competitionId='+currCompetitionId+'&page=0');
		 }else if('nextCompetitionStudent'==li){
			 $(this).attr('href','initNextCompetitionStudent.do?competitionId='+currCompetitionId+'&page=0');
		 }else if('competitionPrizeStudent'==li){
			$(this).attr('href','initCompetitionPrizeStudent.do?competitionId='+currCompetitionId+'&page=0');
		 }
 	 });
});
var loginCaptchaImageRefresh = function(){
	$("#loginCaptcha").val('');
	$("#loginCaptchaImage").attr("src", "captcha.jpg?timestamp" + (new Date()).valueOf());
};
//展示登录界面
var showLoginModal = function(kind){
	var userinfo = $.cookie("loginInfoEcms");
	if(userinfo!=null&&""!=userinfo){
		var array = userinfo.split("§");
		$("#userCode").val(array[0]);
		$("#userPwd").val(array[1]);
		$("#mindpwd").attr('checked','true');
	}
	
	loginCaptchaImageRefresh();
	 if(kind=='viewPaper'){
		$('#myModalLabel').html('您还没登录，请先登录');
	 }else{
		$('#myModalLabel').html('用已注册的用户账号，直接登录');
	 }
	 $('#loginModal').modal('show');
	 return false;
 };
//验证登录
 var validateLogin = function(){
 	var url ='validateLogin.do';
 	var result = syncCallService(url);
 	if(result.isSuccess){
 		var data = result.data;
 		if(data.valid){
 			return true;
 		}else{
 			return false;
 		}
 	}else{
 		$.messager.alert('提示',result.message,'error');
 	}
 	return false;
  };
 //注册按钮
 var toRegisterTitleBtn = function(){
 	//登录
	$('#loginBtnTitle').click(function(){
		return showLoginModal();
	});
	//退出系统
 	 $('#exitSystem').click(function(){
     	if(confirm('确定要退出系统吗?')){
    			$.post('login/logout.do',function(result){
    				if(result.isSuccess){
    					toLoginStatus();
    				}
    			},'json');
    		}
     });
};
 //登录状况
 var toLoginStatus = function(){
	 $('#loginedDiv').hide();
	 $('#loginingDiv').show();
 };
 //退出状况
 var toExitStatus = function(studentName){
	 $('#studentName').html(studentName);
	 $('#loginingDiv').hide();
	 $('#loginedDiv').show();
	 $('#loginModal').modal('hide');
	 //清空表
	 $('#userCode').val('');
	 $('#userPwd').val('');
	 $('#loginCaptcha').val('');
	 $("#mindpwd").removeAttr('checked','');
	 $('#inputWarn').html('&nbsp;');
 };