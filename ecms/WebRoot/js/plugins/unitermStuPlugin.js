// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.unitermStuInit = function() {
	  var smallArray = $(this).attr('smallArray');
	  var smallNo = $(this).attr('smallNo');
	  var competitionId = $(this).attr('competitionId');
	  var groupId = $(this).attr('groupId');
	  var bigId = $(this).attr('bigId');
	  var smallId = $(this).attr('smallId');
	  
	  var url ='student/initSmall.do';
	  var content = {competitionId:competitionId,groupId:groupId,bigId:bigId,smallId:smallId,smallNo:smallNo};
	  asyncCallService(url,content,function(result){
		  if(result.isSuccess){
			  var data = result.data;
			  $('#subjectDesc').html(data.subjectDesc);
			  $('#optionsHtml').html(data.optionsHtml);
			  
			  $(":radio").click(function(){
				  var answer =  $(this).attr('option');
				  var postAnswer =  $('#postAnswer').attr('postAnswer');
				  if(postAnswer!=answer){
					  var url ='student/answerUniterm.do';
					  var content = {competitionId:competitionId,groupId:groupId,bigId:bigId,smallId:smallId,answer:answer};
					  asyncCallService(url,content,function(result){
						  if(result.isSuccess){
							  var currTd = $(".small").find("td[smallArray='"+smallArray+"']");
							  $(currTd).attr("smallStatus",2);
							  $(".small").find("td[smallArray='"+(parseInt(smallArray)+1)+"']").click();
						  }else{
							  $.messager.alert('提示',result.message,"error");
						  }
					  });
				  }
			  });
		  }else{
			  $.messager.alert('提示',result.message,"error");
		  }
	  });
  };
})(jQuery);   