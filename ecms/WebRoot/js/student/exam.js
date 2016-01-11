$(function() {
	var competitionId = $('#competitionId').val();
	var groupId = $('#groupId').val();
	var currSmallArray = null;
	var currTd = null;
	var maxSmallArray = null;//最大小题排序数
	var standardScore = null;
	var examExplainDialog = $('#examExplainDialog');
	var url ='student/initExamPaper.do';
	var content = {'id.competitionId':competitionId,'id.groupId':groupId};
	$('body').mask({maskMsg:'正在加载试卷'});
	asyncCallService(url,content,function(result){
		$('body').mask('hide');
		if(result.isSuccess){
			var data = result.data;
			$('#paperName').html(data.paperName);
			$('#studentName').html(data.studentName);
			$('#examCode').html(data.examCode);
			var distanceFinishLimits = data.distanceFinishLimits;
			$('#subjectTable').html(data.subjectTableHtml);
			maxSmallArray = parseInt(data.maxSmallArray);
			$("#subjectTable td").click(function(){
				var smallArray = $(this).attr('smallArray');
				standardScore = $(this).attr('standardScore');
				if(currSmallArray!=null&&smallArray==currSmallArray){//防止重复单击已选择的小题
					return;
				}
				//处理前一个小题
				if(currTd!=null){
					$(currTd).removeClass("answering");
					var bigNameTarget = $(currTd).parent().parent().parent().parent().parent().find('.bigName');
					var bigIdPre = $(bigNameTarget).attr('bigId');
					var smallIdPre = $(currTd).attr('smallId');
					var smallStatus = null;
					var url ='student/getSmallStatus.do';
					var content = {competitionId:competitionId,groupId:groupId,bigId:bigIdPre,smallId:smallIdPre};
					var result = syncCallService(url,content);
					if(result.isSuccess){
						smallStatus =  parseInt(result.data.smallStatus);
						//到后台取状态
						if(smallStatus==0){
							$(currTd).addClass("unAnswer");
						}else if(smallStatus==1){
							$(currTd).addClass("partAnswered");
						}else if(smallStatus==2){
							$(currTd).addClass("answered");
						}
					}else{
						$.messager.alert('提示',result.message,"error");
					}
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
				var href ='student/'+subjectType+'Stu_html.do';
				$(this).removeClass("answered unAnswer partAnswered").addClass("answering");
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
		                    d= d.replace(/\$\{competitionId\}/g, competitionId);
		                    d= d.replace(/\$\{groupId\}/g, groupId);
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
			$('#okExam').click(function(){
				$.messager.confirm("提示！","确定要提前交卷吗?交卷后将退出考试",function(t){ 
					if(t){
						var url ='student/finishExamPaper.do';
						var content = {competitionId:competitionId,groupId:groupId};
						asyncCallService(url,content,function(result){
							if(result.isSuccess){
								var fn = function(){
									window.opener=null;
									window.open('','_self');
									window.close();
									window.open('student/initExamAnswer.do?infoType=examAnswer&competitionId='+competitionId);
								};
								$.messager.alert('提示','交卷成功,考试结果在个人账户中的我的在线考试中可查看','info',fn);
							}else{
								$.messager.alert('提示',result.message,'error');
							}
						});
					}
				});
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
			$(".small").find("td[smallArray='1']").click();
			var date = new Date();
			date.setTime(date.getTime()+parseFloat(distanceFinishLimits));
			$('#countdown').countdown({until: date,compact:true, 
		    layout: 
		        '<span class="image{h10}"></span><span class="image{h1}"></span>' + 
		        '<span class="imageSep"></span>' + 
		        '<span class="image{m10}"></span><span class="image{m1}"></span>' + 
		        '<span class="imageSep"></span>' + 
		        '<span class="image{s10}"></span><span class="image{s1}"></span>', onExpiry:function() { 
					var fn = function(){
						var url ='student/finishExamPaper.do';
						var content = {competitionId:competitionId,groupId:groupId};
						asyncCallService(url,content,function(result){
							if(result.isSuccess){
								var fn = function(){
									window.opener=null;
									window.open('','_self');
									window.close();
									window.open('student/initMyAccount.do?infoType=examAnswer&competitionId='+competitionId);
								};
								$.messager.alert('提示','交卷成功,考试结果在个人账户中的我的在线考试中可查看','info',fn);
							}else{
								$.messager.alert('提示',result.message,"error");
							}
						});
					};
					$.messager.alert('提示','考试时间到',"warning",fn);
				}});
		}else{
			var isTimeOver = result.data.isTimeOver;
			if(isTimeOver==true){
				var fn = function(){
					window.opener=null;
					window.open('','_self');
					window.close();
					window.open('student/initMyAccount.do?infoType=examAnswer&competitionId='+competitionId);
				};
				$.messager.alert('提示','答题时间已到，已不能答题了考试结果在个人账户中的我的在线考试中可查看',"warning",fn);
			}else{
				$.messager.alert('提示',result.message,"error");
			}
		}
	});
});
