$(function() {
	var paperId = $('#paperId').val();
	var currSmallArray = null;
	var currTd = null;
	var maxSmallArray = null;//最大小题排序数
	var examExplainDialog = $('#examExplainDialog');
	var url ='paper/initExamViewPaper.do';
	var content = {paperId:paperId};
	asyncCallService(url,content,function(result){
		if(result.isSuccess){
			var data = result.data;
			$('#paperName').html(data.paperName);
			$('#studentName').html('考生姓名');
			$('#examCode').html('考生准考证号');
			var distanceFinishLimits = data.distanceFinishLimits;
			$('#subjectTable').html(data.subjectTableHtml);
			maxSmallArray = parseInt(data.maxSmallArray);
			//时钟
			var date = new Date();
			date.setTime(date.getTime()+parseFloat(distanceFinishLimits));
			$('#countdown').countdown({until: date,compact:true, 
		    layout: 
		        '<span class="image{h10}"></span><span class="image{h1}"></span>' + 
		        '<span class="imageSep"></span>' + 
		        '<span class="image{m10}"></span><span class="image{m1}"></span>' + 
		        '<span class="imageSep"></span>' + 
		        '<span class="image{s10}"></span><span class="image{s1}"></span>', onExpiry:function() { 
					$.messager.alert('提示','考试时间到',"warning");
				}});
			
			$("#subjectTable td").click(function(){
				var smallArray = $(this).attr('smallArray');
				var standardScore = $(this).attr('standardScore');
				if(currSmallArray!=null&&smallArray==currSmallArray){//防止重复单击已选择的小题
					return;
				}
				//处理前一个小题
				if(currTd!=null){
					$(currTd).removeClass("answering");
					$(currTd).addClass("unAnswer");
				}
				currTd = $(this);
				var smallId = $(this).attr('smallId');
				var smallNo = $(this).text();
				var bigNameTarget = $(this).parent().parent().parent().parent().parent().find('.bigName');
				var bigId = $(bigNameTarget).attr('bigId');
				var subjectType = $(bigNameTarget).attr('subjectType');
				
				currSubjectType = subjectType;
				currSmallArray = smallArray;
				if("单项选择"==subjectType){
					subjectType = "uniterm";
				}else if("填空"==subjectType){
					subjectType = "fill";
				}else if("阅读理解"==subjectType){
					subjectType = "read";
				}else if("完型填空"==subjectType){
					subjectType = "cloze";
				}
				$(this).removeClass("answered unAnswer partAnswered").addClass("answering");
				var href ='paper/'+subjectType+'StuView.do';
				$('#answerPanel').panel({ 
				    href:href,  
				    cache:false,
				    border:false, 
				    plain:true,
				    fit:true,
		            extractor:function (d) {
		            	if (!d) {
		                    return d;
		                }
		                if (window['CSIT']) {
		                    var id = CSIT.genId();
		                    d= d.replace(/\$\{smallArray\}/g, smallArray);
		                    d= d.replace(/\$\{smallNo\}/g, smallNo);
		                    d= d.replace(/\$\{paperId\}/g, paperId);
		                    d= d.replace(/\$\{bigId\}/g, bigId);
		                    d= d.replace(/\$\{smallId\}/g, smallId);
		                    return d.replace(/\$\{id\}/g, id);
		                }
		                return d;
		            },
		            onLoad:function () {
		            	//大题标题
		            	var bigNameTarget = $(".small").find("td[smallArray='"+smallArray+"']").parent().parent().parent().parent().parent().find('.bigName');
		            	var bigName = $(bigNameTarget).attr('bigName');
		            	$('#bigName').html(bigName);
		    			//小题题号
		            	$('#smallNoSmall').html(smallNo+standardScore);
		            	
		            	(window['CSIT'] && CSIT.initContent && CSIT.initContent(this));
		            }
				});
			});
			$(".small").find("td[smallArray='1']").click();
			$('#nextSmall').click(function(){
				var next = parseInt(currSmallArray)+1;
				if(next>maxSmallArray){
					$.messager.alert('提示','已经是最后一个小题了','warning');
					return false;
				}
				$(".small").find("td[smallArray='"+next+"']").click();
				return false;
			});
			$('#upSmall').click(function(){
				var pre = parseInt(currSmallArray)-1;
				if(pre<1){
					$.messager.alert('提示','已经是第一小题了','warning');
					return false;
				}
				$(".small").find("td[smallArray='"+pre+"']").click();
				return false;
			});
			$(examExplainDialog).dialog({ 
				title:'考试说明',
			    width:280,
			    height:250,
			    closed: true,  
			    cache: false,  
			    modal: true,
			    closable:false,
			    toolbar:[{
					text:'退出',
					iconCls:'icon-cancel',
					handler:function(){
						$(examExplainDialog).dialog('close');
					}
				}]
			});
			$('#examExplain').click(function(){
				$(examExplainDialog).dialog('open');
			});
		}
	});
	
});
