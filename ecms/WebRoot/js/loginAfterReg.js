$(function() {
	$(document).keydown(function(e){ 
		var curKey = e.which; 
		if(curKey == 13){ 
			$('#loginBtn').click();
		} 
	});
	
	var loginCaptchaImageRefresh = function(){
		$("#loginCaptcha").val('');
		$("#loginCaptchaImage").attr("src", "captcha.jpg?timestamp" + (new Date()).valueOf());
	};
	// 点击刷新验证码图片
	$("#loginCaptchaImage").click( function() {
		loginCaptchaImageRefresh();
	});
	
	$("#loginBtn").click(function(){
		var userCode = $.trim($('#userCode').val()) ;
		var userPwd = $.trim($('#userPwd').val()) ;
		var j_captcha = $.trim($('#loginCaptcha').val()) ;
		var url ='login/studentLogin.do';
		var content = {userCode:userCode,userPwd:userPwd,j_captcha:j_captcha};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			//记住密码
			if($("#mindpwd").attr("checked")){
				var loginInfo = $("#userCode").val()+"§"+$("#userPwd").val();
				$.cookie("loginInfoEcms",loginInfo,{expires: 30,path:'/'});
			}else{
				$.cookie("loginInfoEcms",null,{path:'/'});
			}
			window.location.href="homePage.do";
		}else{
			$('#inputWarn').html(result.message);
			loginCaptchaImageRefresh();
		}
		return false;
	});
	
});