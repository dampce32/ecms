// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.clozeStuInit = function() {
	  var smallArray = $(this).attr('smallArray');
	  var smallNo = $(this).attr('smallNo');
	  var competitionId = $(this).attr('competitionId');
	  var groupId = $(this).attr('groupId');
	  var bigId = $(this).attr('bigId');
	  var smallId = $(this).attr('smallId');
	  
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  
	  var url ='student/initSmall.do';
		var content = {competitionId:competitionId,groupId:groupId,bigId:bigId,smallId:smallId,smallNo:smallNo};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			$('#subjectDesc').html(data.subjectDesc);
			$('#optionsHtml').html(data.optionsHtml);
			var smallSubSize = data.smallSubSize;
			$(".shortAnswerAnswer").click(function(){
				var tr = $(this).parent().parent();
				var subId = $(tr).attr("subId");
				var postAnswer = $(tr).attr("postAnswer");
				var answer = $(this).attr("option");
				var name = $(this).attr("name");
				if(answer!=postAnswer){//更新答案
					$(".shortAnswerAnswer[name="+name+"]").removeAttr("checked");
					$(this).attr("checked","checked");
					var countSubAnswer = $('.shortAnswerAnswer[checked=checked]').length;
					var smallStatus = 0;
					if(countSubAnswer<smallSubSize){
						smallStatus = 1;
					}else if(countSubAnswer=smallSubSize){
						smallStatus = 2;
					}
					$('#optionsHtml').mask({maskMsg:'正在保存答题情况'});
					var url ='student/answerCloze.do';
					var content = {competitionId:competitionId,groupId:groupId,bigId:bigId,smallId:smallId,subId:subId,answer:answer,smallStatus:smallStatus};
					var result = syncCallService(url,content);
					if(result.isSuccess){
						var currTd = $(".small").find("td[smallArray='"+smallArray+"']");
						$(currTd).attr("smallStatus",smallStatus);
						$(tr).attr("postAnswer",answer);
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}
				$('#optionsHtml').mask('hide');
			});
		}else{
			$.messager.alert('提示',result.message,"error");
		}
  };
})(jQuery);   