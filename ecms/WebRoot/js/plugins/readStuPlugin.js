// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.readStuInit = function() {
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
			  var smallSubSize = data.smallSubSize;
			  $(".readSubjectSubAnswer").click(function(){
					var subId = $(this).attr("subId");
					var answer = $(this).attr("option");
					var name = $(this).attr("name");
					var postAnswer = $('#id_'+subId).attr("postAnswer");
					if(postAnswer!=answer){
						$(".readSubjectSubAnswer[name="+name+"]").removeAttr("checked");
						$(this).attr("checked","checked");
						var countSubAnswer = $('.readSubjectSubAnswer[checked=checked]').length;
						if(countSubAnswer<smallSubSize){
							smallStatus = 1;
						}else if(countSubAnswer=smallSubSize){
							smallStatus = 2;
						}
						$('#optionsHtml').mask({maskMsg:'正在保存答题情况'});
						var url ='student/answerRead.do';
						var content = {competitionId:competitionId,groupId:groupId,bigId:bigId,smallId:smallId,subId:subId,answer:answer,smallStatus:smallStatus};
						asyncCallService(url,content,function(result){
							if(result.isSuccess){
								var currTd = $(".small").find("td[smallArray='"+smallArray+"']");
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