// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.modifyPwdInit = function() {
	  
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var editForm = $('#editForm_'+id,$this);
	  $(editForm).form('clear');
	  //保存前检验表单值
	  var setValue = function(){
		  var passwords = $('#passwords',$this).val();
		  if('' == passwords){
			  $.messager.alert('提示','请输入原密码','warning');
			  return false;
		  }
		  var newTeacherPwd = $('#newTeacherPwd',$this).val();
		  if('' == newTeacherPwd){
			  $.messager.alert('提示','请输入新密码','warning');
			  return false;
		  }
		  var newTeacherPwd2 = $('#newTeacherPwd2',$this).val();
		  if(newTeacherPwd!=newTeacherPwd2){
			  $.messager.alert('提示','两次输入的新密码不一样','warning');
			  return false;
		  }
		  return true;
	  }
	  
	  $('#saveBtn',$this).click(function(){
		  $(editForm).form('submit',{
			url: 'system/modifyPwdTeacher.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					$.messager.alert('提示','密码修改成功','info');
				}else{
					$.messager.alert('提示',result.message,"warning");
				}
			}
		  });
		  return false;
	  })
	  
  }
})(jQuery);   