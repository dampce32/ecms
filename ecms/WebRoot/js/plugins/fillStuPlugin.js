// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.fillStuInit = function() {
	  var smallArray = $(this).attr('smallArray');
	  var smallNo = $(this).attr('smallNo');
	  var competitionId = $(this).attr('competitionId');
	  var groupId = $(this).attr('groupId');
	  var bigId = $(this).attr('bigId');
	  var smallId = $(this).attr('smallId');
	  var currInput = null;
	  
	  var url ='student/initSmall.do';
	  var content = {competitionId:competitionId,groupId:groupId,bigId:bigId,smallId:smallId,smallNo:smallNo};
	  asyncCallService(url,content,function(result){
		  if(result.isSuccess){
				var data = result.data;
				$('#subjectDesc').html(data.subjectDesc);
				$('#optionsHtml').html(data.optionsHtml);
				$(".fillAnswerInput").blur(function(){
					var postAnswer = $(this).attr('postAnswer');
					var answer =  $(this).val();
					var option = $(this).attr('option');
					var optionCount = $(this).attr('optionCount');
					currInput = $(this);
					if(postAnswer!=answer){
						var optionAnswerArray = new Array();
						$(".fillAnswerInput").each(function(){
							var optionAnswer = $.trim($(this).val());
							if(optionAnswer!=''){
								optionAnswerArray.push(optionAnswer);
							}
						});
						var smallStatus = 0;
						if(optionAnswerArray.length==0){
							smallStatus = 0;
						}else if(optionAnswerArray.length<parseInt(optionCount)){
							smallStatus = 1;
						}else if(optionAnswerArray.length=parseInt(optionCount)){
							smallStatus = 2;
						}
						$('#optionsHtml').mask({maskMsg:'正在保存答题情况'});
						var url ='student/answerFill.do';
						var content = {competitionId:competitionId,groupId:groupId,bigId:bigId,smallId:smallId,option:option,answer:answer,smallStatus:smallStatus};
						var result = syncCallService(url,content);
						if(result.isSuccess){
								var currTd = $(".small").find("td[smallArray='"+smallArray+"']");
								$(currTd).attr("smallStatus",smallStatus);
								$(currInput).attr('postAnswer',answer);
						}else{
							$.messager.alert('提示',result.message,"error");
						}
						$('#optionsHtml').mask('hide');
					}
				});
			}else{
				$.messager.alert('提示',result.message,"error");
			}
		});
  };
})(jQuery);   