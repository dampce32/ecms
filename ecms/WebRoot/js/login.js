$(function() {
	var width = $(document.body).width();
	var height = $(document.body).height();
	
	var userinfo = $.cookie("loginInfoExam");
	if(userinfo!=null&&""!=userinfo){
		var array = userinfo.split("§");
		$("#userCode").val(array[0]);
		$("#userPwd").val(array[1]);
		$("#mindpwd").attr('checked','true');
	}
	var loginCaptchaImageRefresh = function(){
		$("#loginCaptcha").val('');
		$("#loginCaptchaImage").attr("src", "captcha.jpg?timestamp" + (new Date()).valueOf());
	};
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
		var url = 'login/teacherLogin.do';			
		$('#loginForm').form('submit', {
			url : url,
			onSubmit : function() {
				if($(this).form('validate')){
					//记住密码
					 if($("#mindpwd").attr("checked")){
						 var loginInfo = $("#userCode").val()+"§"+$("#userPwd").val();
						 $.cookie("loginInfoExam",loginInfo,{expires: 30,path:'/'});
					 }else{
						 $.cookie("loginInfoExam",null,{path:'/'});
					 }
					 $(".loginDetail").mask({maskMsg:'正在登陆验证'});
					 return true;
				}
				return false;
			},
			success : function(data) {
				var result = eval('(' + data + ')');
				if(result.isSuccess){
					window.location='main.do';
				}else{
					$.messager.alert('提示：',result.message,'error');
					loginCaptchaImageRefresh();
				}
				$(".loginDetail").mask('hide');
			}
		});
	});
	
});
function aClick(v) {
	alert(v);
}