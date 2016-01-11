// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.shortAnswerStuInit = function() {
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
				$("#shortAnswer").blur(function(){
					var answer = $.trim($(this).val());
					var postAnswer = $.trim($(this).attr("postAnswer"));
					if(answer!=postAnswer){
						$('#optionsHtml').mask({maskMsg:'正在保存答题情况'});
						var url ='answerStu/answerShortAnswer.do';
						var content = {arrangeId:arrangeId,bigId:bigId,smallId:smallId,answer:answer};
						asyncCallService(url,content,function(result){
							if(result.isSuccess){
								var currTd = $(".small").find("td[smallArray='"+smallArray+"']");
								var smallStatus = 0;
								if(''!=answer){
									smallStatus = 2;
								}
								$(currTd).attr("smallStatus",smallStatus);
							}else{
								$.messager.alert('提示',result.message,"error");
							}
							$('#optionsHtml').mask('hide');
						});
					}
				});
			}else{
				$.messager.alert('提示',result.message,"error");
			}
		});
  };
})(jQuery);   