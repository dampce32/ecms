// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.registMessageClientInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  
	  $('#saveBtn_'+id,$this).click(function(){
		  var softwareSerialNo = $('#softwareSerialNo',$this).val();
		  var key = $('#key',$this).val();
		  $($this).mask({maskMsg:'正在进行注册...'});
		  $('#editForm_'+id).form('submit',{
			url:'system/registMessageSend.do',
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					$.messager.alert('提示','注册成功','info');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
		 $($this).mask('hide');
	  })
  }
})(jQuery);   