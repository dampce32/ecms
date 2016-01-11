// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.subjectStuViewInit = function() {
	  var smallNo = $(this).attr('smallNo');
	  var paperId = $(this).attr('paperId');
	  var bigId = $(this).attr('bigId');
	  var smallId = $(this).attr('smallId');
	  
	  var url ='paper/initSmallViewPaper.do';
	  var content = {paperId:paperId,bigId:bigId,smallId:smallId,smallNo:smallNo};
	  asyncCallService(url,content,function(result){
		  if(result.isSuccess){
			  var data = result.data;
			  $('#subjectDesc').html(data.subjectDesc);
			  $('#optionsHtml').html(data.optionsHtml);
		  }else{
			  $.messager.alert('提示',result.message,"error");
		  }
	  });
  };
})(jQuery);   