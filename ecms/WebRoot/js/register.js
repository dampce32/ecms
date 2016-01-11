$(function(){
	
	$('.validate').hide();
	
	var switchResult = syncCallService('getRegisterSwitchStatuss.do');
	if(switchResult.isSuccess){
		var mailSwitch = switchResult.data.mailSwitch;
		var msgSwitch = switchResult.data.msgSwitch;
		$('#registerFreeBtn').hide();
		if(msgSwitch==0&&mailSwitch==0){
			$('#validateTabs').tabs('close',0);
			$('#validateTabs').tabs('close',0);
			$('#validateTabs').hide();
			$('#validateTitle').hide();
			$('#registerFreeBtn').show();
		}else if(msgSwitch==0&&mailSwitch==1){
			$('#validateTabs').tabs('close',0);
		}else if(msgSwitch==1&&mailSwitch==0){
			$('#validateTabs').tabs('close',1);
		}
	}
	
	//民族
	$('#nation').combobox({
		editable:false,
		width:200,
		url:'register/queryComboboxNation.do',
		valueField:'nationId',
		textField:'nationName'
	});
	//省份
	$('#province').combobox({
		editable:false,
		width:200,
		url:'register/queryComboboxProvince.do',
		valueField:'provinceId',
		textField:'provinceName',
		onSelect:function(record){
			$('#city').combobox({
				editable:false,
				width:200,
				valueField:'cityId',
				textField:'cityName',
				url:'register/queryComboboxCity.do?province.provinceId='+record.provinceId
			});
			$('#area').combobox('loadData',[]);
			$('#area').combobox('clear');
			
			$('#school').combobox('loadData',[]);
			$('#school').combobox('clear');
			
			$('#grade').combobox('loadData',[]);
			$('#grade').combobox('clear');
			
			$('#clazz').combobox('loadData',[]);
			$('#clazz').combobox('clear');
		}
	});
	//市
	$('#city').combobox({
		width:200,
		editable:false,
		onSelect:function(record){
			$('#area').combobox({
				editable:false,
				width:200,
				valueField:'areaId',
				textField:'areaName',
				url:'register/queryComboboxArea.do?city.cityId='+record.cityId
			});
			$('#school').combobox('loadData',[]);
			$('#school').combobox('clear');
			
			$('#grade').combobox('loadData',[]);
			$('#grade').combobox('clear');
			
			$('#clazz').combobox('loadData',[]);
			$('#clazz').combobox('clear');
		}
	});
	//区
	$('#area').combobox({
		width:200,
		editable:false,
		onChange:function(newValue, oldValue){
			$('#school').combobox({
				editable:false,
				valueField:'schoolId',
				textField:'schoolName',
				url:'register/queryComboboxSchool.do?area.areaId='+newValue
			});
			$('#grade').combobox('loadData',[]);
			$('#grade').combobox('clear');
			
			$('#clazz').combobox('loadData',[]);
			$('#clazz').combobox('clear');
		}
	});
	//学校
	$('#school').combobox({
		width:200,
		editable:false,
		onChange:function(newValue, oldValue){
			$('#grade').combobox({
				valueField:'schoolGradeId',
				textField:'gradeName',
				url:'register/queryComboboxSG.do?school.schoolId='+newValue
			});
			
			$('#clazz').combobox('loadData',[]);
			$('#clazz').combobox('clear');
		}
	});
	//年级
	$('#grade').combobox({
		width:200,
		editable:false,
		onChange:function(newValue, oldValue){
			$('#clazz').combobox({
				valueField:'schoolGradeClazzId',
				textField:'clazzName',
				url:'register/queryComboboxSGC.do?schoolGrade.schoolGradeId='+newValue
			});
		}
	});
	//班级
	$('#clazz').combobox({
		width:200,
		editable:false
	});
	
	var studentForm = $('#studentForm');
	
	var familyDiv = $('#studentFamilyMember');
	
	var timeCount = 59;
	var changeInterval = null;
	var changeBtnText = function(){
		if(timeCount==-1){	
			clearInterval(changeInterval);
			timeCount = 59;
			$('#toValidate').linkbutton({text:'点击获取验证码'});
			$('#toValidate').linkbutton('enable');
			return;
		}
		$('#toValidate').linkbutton('disable');
		$('#toValidate').linkbutton({text:'<span style="color:black">'+timeCount+'秒钟后可重新发送</span>'});
		timeCount --;
	};
	
	$('#toValidate').click(function(){
		if($('#toValidate').linkbutton('options').text=='点击获取验证码'){
			var mobilePhone = $('#mobilePhone',studentForm).val();
			if(''==mobilePhone){
				$.messager.alert('提示','请填写手机号码','warning');
				return false;
			}else if(!/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/.test(mobilePhone)){
				$.messager.alert('提示','手机号码格式错误','warning');
				return false;
			}
			var sendResult = syncCallService('sendValidateCode.do',{mobilePhone:mobilePhone});
			if(sendResult.isSuccess){
				var fn = function(){
					changeInterval = setInterval(changeBtnText, 1000);
				};
				$.messager.alert('提示','短信已发出，请查收短信','info',fn);
			}else{
				$.messager.alert('提示',sendResult.message,'warning');
				return false;
			}
		}
	});
	
	$('#registerMobile').click(function(){
		onSave('mobile');
		return false;
	});
	
	$('#registerEmail').click(function(){
		onSave('email');
		return false;
	});
	
	$('#registerFreeBtn').click(function(){
		onSave('free');
		return false;
	});
	
	var setValue = function(kind){
		$('#kindSave',studentForm).val(kind);
		var studentName = $('#studentName',studentForm).val();
		if(''==studentName){
			$.messager.alert('提示','请填写学生姓名','warning');
			return false;
		}
		var sex = $('#sex',studentForm).val();
		if(''==sex){
			$.messager.alert('提示','请选择性别','warning');
			return false;
		}
		var birthday = $('#birthday',studentForm).val();
		if(''==birthday){
			$.messager.alert('提示','请选择出生日期','warning');
			return false;
		}
		var nation = $('#nation',studentForm).combobox('getValue');
		if(''==nation){
			$.messager.alert('提示','请选择民族','warning');
			return false;
		}
		
		var mobilePhone = $('#mobilePhone',studentForm).val();
		if('mobile'==kind){
			if(''==mobilePhone){
				$.messager.alert('提示','请填写手机号码','warning');
				return false;
			}
		}
		if(''!=mobilePhone&&!/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/.test(mobilePhone)){
			$.messager.alert('提示','手机号码格式错误','warning');
			return false;
		}
		var email = $('#email',studentForm).val();
		if('email'==kind){
			if(''==email){
				$.messager.alert('提示','请填写E-Mail','warning');
				return false;
			}
		}
		if(''!=email&&!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)){
			$.messager.alert('提示','邮箱格式错误','warning');
			return false;
		}
		var province = $('#province',studentForm).combobox('getValue');
		var city = $('#city',studentForm).combobox('getValue');
		var area = $('#area',studentForm).combobox('getValue');
		if(''==province){
			$.messager.alert('提示','请选择所在省份','warning');
			return false;
		}
		if(''==city){
			$.messager.alert('提示','请选择所在市','warning');
			return false;
		}
		if(''==area){
			$.messager.alert('提示','请选择所在县/区','warning');
			return false;
		}
		var school = $('#school',studentForm).combobox('getValue');
		var grade = $('#grade',studentForm).combobox('getValue');
		var clazz = $('#clazz',studentForm).combobox('getValue');
		if(''==school){
			$.messager.alert('提示','请选择学校','warning');
			return false;
		}
		if(''==grade){
			$.messager.alert('提示','请选择年级','warning');
			return false;
		}
		if(''==clazz){
			$.messager.alert('提示','请选择班级','warning');
			return false;
		}
		var address = $('#address',studentForm).val();
		if(''==address){
			$.messager.alert('提示','请填写家庭地址','warning');
			return false;
		}
		var userCode = $('#userCode',studentForm).val();
		if(''==userCode){
			$.messager.alert('提示','请填写登录名','warning');
			return false;
		}
		var userPwd = $('#userPwd',studentForm).val();
		if(''==userPwd){
			$.messager.alert('提示','请填写密码','warning');
			return false;
		}else if(/.*[\u4e00-\u9fa5]+.*$/.test(userPwd)){
			$.messager.alert('提示','密码不能为中文','warning');
			return false;
		}
		var userPwd2 = $('#userPwd2',studentForm).val();
		if(''==userPwd2){
			$.messager.alert('提示','请输入确认密码','warning');
			return false;
		}
		if(userPwd!=userPwd2){
			$.messager.alert('提示','两次填写的密码不一致','warning');
			return false;
		}
		var familyMember = $('#familyMember',familyDiv).val();
		if(''==familyMember){
			$.messager.alert('提示','请填写家庭成员','warning');
			return false;
		}
		var familyMemberName = $('#familyMemberName',familyDiv).val();
		if(''==familyMemberName){
			$.messager.alert('提示','请填写家庭成员姓名','warning');
			return false;
		}
		var familyMemberPhone = $('#phone',familyDiv).val();
		if(''==familyMemberPhone){
			$.messager.alert('提示','请填写家庭成员联系电话','warning');
			return false;
		}else if(!/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/.test(familyMemberPhone)){
			$.messager.alert('提示','家庭成员联系电话格式错误','warning');
			return false;
		}
		var familyMemberAge = $('#familyMemberAge',familyDiv).val();
		var workUnitsAndPosition = $('#workUnitsAndPosition',familyDiv).val();
		var familyMemberEmail = $('#email',familyDiv).val();
		var familyMemberList = new Array();
		familyMemberList.push(familyMember);
		familyMemberList.push(familyMemberName);
		if(''==familyMemberAge){
			familyMemberList.push(-1);
		}else{
			familyMemberList.push(familyMemberAge);
		}
		if(''==workUnitsAndPosition){
			familyMemberList.push(-1);
		}else{
			familyMemberList.push(workUnitsAndPosition);
		}
		familyMemberList.push(familyMemberPhone);
		if(''==familyMemberEmail){
			familyMemberList.push(-1);
		}else{
			familyMemberList.push(familyMemberEmail);
		}
		var familyMemberStr = familyMemberList.join('^');
		$('#familyMemberInfo',studentForm).val(familyMemberStr);
		
		if('mobile'==kind){
			var validateCodeInput = $('#validateCodeInput').val();
			if($('#validateTabs').is(':visible')==true&&validateCodeInput==''){
				$.messager.alert('提示','请填写短信验证码','warning');
				return false;
			}else{
				$('#validateCode').val(validateCodeInput);
			}
		}else{
			var emailCaptcha = $('#emailCaptcha').val();
			if(emailCaptcha==''){
				$.messager.alert('提示','请填写验证码','warning');
				return false;
			}else{
				$('#validateCode').val(emailCaptcha);
			}
		}
		return true;
	};
	
	var onSave = function(kind){
		$('#studentForm').form('submit',{
			url:'register/saveStudent.do',
			onSubmit: function(){
				return setValue(kind);
			},
			success: function(data){
				var result = eval('('+data+')');
				var kind = $('#kindSave',studentForm).val();
				if(result.isSuccess){
					if('email'==kind){
						var fn = function(){
							var email = $('#email',studentForm).val();
							var domain = email.substr(email.indexOf('@') + 1);
							if('gmail.com'==domain){
								domain='google.com';
							}
							var url = 'http://mail.' + domain + '/';
							window.location.href=url;
						};
						$.messager.alert('提示','请到注册邮箱激活注册账户','info',fn);
					}else{
						var fn = function(){
							window.location.href='homePage.do';
						};
						$.messager.alert('提示','注册成功','info',fn);
					}
					
				}else{
					if(kind=='email'){
						loginCaptchaImageRefresh();
					}
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	};
	
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
			$("register").click();
		} 
	});
});