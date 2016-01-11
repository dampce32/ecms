// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.multipleStuInit = function() {
	  var smallArray = $(this).attr('smallArray');
	  var smallNo = $(this).attr('smallNo');
	  var arrangeId = $(this).attr('arrangeId');
	  var bigId = $(this).attr('bigId');
	  var smallId = $(this).attr('smallId');
	  
	  var url ='answerStu/initSmall.do';
	  var content = {arrangeId:arrangeId,bigId:bigId,smallId:smallId,smallNo:smallNo};
	  asyncCallService(url,content,function(result){
		  if(result.isSuccess){
			  var data = result.data;
			  $('#subjectDesc').html(data.subjectDesc);
			  $('#optionsHtml').html(data.optionsHtml);
			  
			  $(":checkbox").click(function(){
				  var answerArray = new Array();
				  $('#optionsHtml :checkbox[checked=checked]').each(function(i){
					  var id = $(this).attr('id');
					  answerArray.push(id);
				  });
				  $('#optionsHtml').mask({maskMsg:'正在保存答题情况'});
				  var url ='answerStu/answerMultiple.do';
				  var content = {arrangeId:arrangeId,bigId:bigId,smallId:smallId,answers:answerArray.join('^')};
				  asyncCallService(url,content,function(result){
					  if(result.isSuccess){
						  var currTd = $(".small").find("td[smallArray='"+smallArray+"']");
						  if(answerArray.length==0){
							  $(currTd).attr("smallStatus",0);
						  }else{
							  $(currTd).attr("smallStatus",2);
						  }
					  }else{
						  $.messager.alert('提示',result.message,"error");
					  }
					  $('#optionsHtml').mask('hide');
				  });
			  });
		  }else{
			  $.messager.alert('提示',result.message,"error");
		  }
	  });
  };
})(jQuery);   