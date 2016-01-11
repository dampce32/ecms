$(function(){
	//当前选中的节点
    var currentNode = null;
	tabCloseEven();
	$('#competitionSearchMain').combobox({
		  width:300,
		  data:ECMS.getCompetitionList(),
		  valueField:'competitionId',
		  textField:'competitionName',
		  onChange:function(newValue, oldValue){
			  check();
			  var tabSelect = $('#tabs').tabs('getSelected');
			  if(tabSelect!=null){
				  var id = $('.easyui-layout',tabSelect).attr('id');
				  
				  var title = tabSelect.panel('options').title; 
				  if('赛事获奖'==title){
					//赛事组别
					  $('#competitionGroupSearch_'+id).combobox({
						  width:150,
						  url:'competition/queryComboboxCompetitionGroup.do?competition.competitionId='+newValue,
						  valueField:'competitionGroupId',
						  textField:'groupName',
						  onSelect:function(record){
							  $('#competitionPrizeSearch_'+id).combobox({
								  url:'competition/queryComboboxCompetitionPrize.do?competitionGroup.competitionGroupId='+record.competitionGroupId
							  });
						  }
					  });
					  //赛事奖项
					  $('#competitionPrizeSearch_'+id).combobox({
						  width:150,
						  valueField:'competitionPrizeId',
						  textField:'prizeName'
					  });
				  }else if('赛事晋级'==title){
					//赛事组别
					  $('#competitionGroupSearch_'+id).combobox({
						  width:150,
						  url:'competition/queryComboboxCompetitionGroup.do?competition.competitionId='+newValue,
						  valueField:'competitionGroupId',
						  textField:'groupName'
					  });
				  }else if('培训班级学生'==title){
					  $('#trainingClassSearch_'+id).combobox({
						  width:200,
						  url:'trainingClass/queryComboboxTrainingClass.do?competitionId='+newValue,
						  valueField:'trainingClassId',
		  				  textField:'trainingClassName'
					  });
				  }else if('报名管理'==title){
					  $('#groupSearch_'+id).combobox({
						  width:200,
						  url:'competition/queryComboboxCompetitionGroup.do?competition.competitionId='+newValue,
						  valueField:'groupId',
						  textField:'groupName'
					  });
				  }else if('赛场学生'==title){
					  $('#stadiumSearch_'+id).combobox({
						  width:150,
						  url:'stadium/queryComboboxStadium.do?competitionId='+newValue,
						  valueField:'stadiumId',
						  textField:'stadiumName'
					  });
				  }
				  $('#search_'+id).click();
			  }
		  },
		  onLoadSuccess:function(){
			  var competitonList = ECMS.getCompetitionList();
			  if(competitonList.length>0){
				  $('#competitionSearchMain').combobox('setValue',competitonList[0].competitionId);
			  }
		  }
	  });
 	$('#navi a').click(function(){
		var href =$(this).attr('name');
		var title = $(this).text();
		if(href==null){
			return false;
		}
		
		addTab(title,href);
		return false;
	});
 	
 	$('#rightTree').tree({
		url: 'system/getRootUrlRightTeacher.do',
		onBeforeExpand:function(node,param){
				$('#rightTree').tree('options').url = 'system/getChildrenUrlRightTeacher.do?rightId='+node.id;  
	    },
		onSelect:function (node) {
			$(this).tree('toggle',node.target);
            if (currentNode != null && node.id == currentNode.id) return;
            if (node.attributes == null) return;
            var url = node.attributes.rightUrl;
            var title = node.text;
            CSIT.currTabRightId = node.id;
            addTab(title, url);
        }
	});
 	
 	//退出系统
 	 $('#exitSystem').click(function(){
     	$.messager.confirm('提示','确定要退出系统吗?',function(r){
     		if(r){
     			$.post('system/logoutTeacher.do',function(result){
     				if(result.isSuccess){
     					window.location.href='login.html';
     				}
     			},'json');
     		}
     	});
     });
   //---修改密码---
     $('#modifyPwdDialog').dialog({
     	title: '修改密码',  
 	    width:500,
 	    height:300,
 	    closed: true,  
 	    cache: false,  
 	    modal: true,
 	    closable:false,
 	    toolbar:[{
 			text:'保存',
 			iconCls:'icon-save',
 			handler:function(){
 				onSaveModifyPwd();
 			}
 		},'-',{
 			text:'退出',
 			iconCls:'icon-cancel',
 			handler:function(){
 				$('#modifyPwdDialog').dialog('close');
 			}
 		}]
     });
      $('#modifyPwdBtn').click(function(){
     	$('#modifyPwdForm').form('clear');
      	$('#modifyPwdDialog').dialog('open');
      });
      
      //保存前检验表单值
 	  var setValue = function(){
 		  var passwords = $('#passwords').val();
 		  if('' == passwords){
 			  $.messager.alert('提示','请输入原密码','warning');
 			  return false;
 		  }
 		  var newTeacherPwd = $('#newTeacherPwd').val();
 		  if('' == newTeacherPwd){
 			  $.messager.alert('提示','请输入新密码','warning');
 			  return false;
 		  }
 		  var newTeacherPwd2 = $('#newTeacherPwd2').val();
 		  if(newTeacherPwd!=newTeacherPwd2){
 			  $.messager.alert('提示','两次输入的新密码不一样','warning');
 			  return false;
 		  }
 		  return true;
 	  }
 	  
 	  var onSaveModifyPwd = function(){
 		  $('#modifyPwdForm').form('submit',{
 			url: 'system/modifyPwdTeacher.do',
 			onSubmit: function(){
 				return setValue();
 			},
 			success: function(data){
 				var result = eval('('+data+')');
 				if(result.isSuccess){
 					$('#modifyPwdDialog').dialog('close');
 					$.messager.alert('提示','密码修改成功','info');
 				}else{
 					$.messager.alert( '提示',result.message,"warning");
 				}
 			}
 		  });
 	  }
 	  //加载列表
	
	
	$("#reloadBtn").click(function(){
		check();
	});
	function check(){
		$('#stuList').datagrid({
			title:"最新报名选手",
			singleSelect:true,
			method:"POST",
			nowrap:true,
			striped: true,
			collapsible:true,
			rownumbers:true,
			fit:true,
			columns:[[
			      {field:'studentName',title:'学生',width:100,sortable:true,align:"center"},
			      {field:'competitionName',title:'赛事',width:200,sortable:true,align:"center"},
			      {field:'groupName',title:'参赛组别',width:120,sortable:true,align:"center"},
			      {field:'address',title:'参赛地区',width:150,sortable:true,align:"center",
			    	  formatter:function(value,row,index){
			    		  return row.provinceName+row.cityName+row.areaName;
			    	  }
			      }
			]]
		  });
		//加载列表
		$('#noticeList').datagrid({
			title:"赛事公告",
			singleSelect:true,
			method:"POST",
			nowrap:true,
			striped: true,
			collapsible:true,
			rownumbers:true,
			fit:true,
			columns:[[
				{field:'informationTitle',title:'标题',width:250,align:"center"},
				{field:'category',title:'资讯类型',width:80,align:"center"},
				{field:'competitionName',title:'赛事',width:235,align:"center"}
			]]
		  });
		//加载列表
		$('#stadiumList').datagrid({
			title:"赛场信息列表",
			singleSelect:true,
			method:"POST",
			nowrap:true,
			striped: true,
			collapsible:true,
			rownumbers:true,
			fit:true,
			columns:[[
				  {field:'stadiumName',title:'赛场名称',width:80,align:"center"},
				  {field:'stadiumAddr',title:'比赛地点',width:80,align:"center"},
				  {field:'competitionDate',title:'比赛时间',width:120,align:"center"},
				  {field:'competitionName',title:'赛事名称',width:110,align:"center"},
				  {field:'groupName',title:'参赛组别',width:100,align:"center"}
			]]
		  });
		//加载列表
		$('#trainingClassList').datagrid({
			title:"培训班级",
			singleSelect:true,
			method:"POST",
			nowrap:true,
			striped: true,
			collapsible:true,
			rownumbers:true,
			fit:true,
			columns:[[
			    {field:'trainingClassName',title:'班级名称',width:80,align:"center"},
			    {field:'groupName',title:'参赛组别',width:100,align:"center"},
				{field:'address',title:'培训地点',width:80,align:"center"},
				{field:'classDate',title:'培训时间',width:100,align:"center"},
				{field:'sellingPrice',title:'教材费用',width:60,align:"center"},
				{field:'fee',title:'培训费用',width:60,align:"center"}
			]]
		  });
		var url="system/checkRightTeacher.do";
		var defaultInformationId = $('#competitionSearchMain').combobox('getValue');
		var content={competitionId:defaultInformationId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var  data = result.data;
			if(data.noticeList!=null){
				$('#noticeList').datagrid({
					tools:'#it'
				});
				var noticeList = eval("("+data.noticeList+")");
				$('#noticeList').datagrid('loadData',noticeList);
			}
			if(data.stuList!=null){
				$('#stuList').datagrid({
					tools:'#et'
				});
				var stuList = eval("("+data.stuList+")");
				$('#stuList').datagrid('loadData',stuList);
			}
			if(data.stadiumList!=null){
				$('#stadiumList').datagrid({
					tools:'#st'
				});
				var stadiumList = eval("("+data.stadiumList+")");
				$('#stadiumList').datagrid('loadData',stadiumList);
			}
			if(data.trainingClassList!=null){
				$('#trainingClassList').datagrid({
					tools:'#tt'
				});
				var trainingClassList = eval("("+data.trainingClassList+")");
				$('#trainingClassList').datagrid('loadData',trainingClassList);
			}
		}else{
			$.messager.alert('提示',result.message,'error');
		}
	};
	$("#iMore").click(function(){
		CSIT.currTabRightId = '000040000';
		addTab('资讯','information/information.do');
	});
	$("#sMore").click(function(){
		CSIT.currTabRightId = '000060000';
		addTab('考场管理','stadium/stadium.do');
	});
	$("#tMore").click(function(){
		CSIT.currTabRightId = '000080010';
		addTab('培训班级','trainingClass/trainingClass.do');
	});
	$("#eMore").click(function(){
		CSIT.currTabRightId = '000050010';
		addTab('报名管理','enroll/enroll.do');
	});
})

function addTab(title,href){
	if($('#tabs').tabs('exists',title)){//选择并更新tab
		$('#tabs').tabs('select',title);
	}else{
		if(href.indexOf('.do')!=-1){
			var panelInfo = {
	                title:title,
	                closable:true,
	                href:href, border:false, plain:true,
	                extractor:function (d) {
	                    if (!d) {
	                        return d;
	                    }
	                    var currTabRightId = CSIT.currTabRightId;
	                    d = d.replace(/\$\{rightId\}/g, currTabRightId);
	                    if (window['CSIT']) {
	                        var id = CSIT.genId();
	                        return d.replace(/\$\{id\}/g, id);
	                    }
	                    return d;
	                },
	                onLoad:function (panel) {
	                    var tab = $('.plugins', this);
	                    if ($(tab).size() == 0) {
	                        return;
	                    }
	                   (window['CSIT'] && CSIT.initContent && CSIT.initContent(this));
	                }
	            };
			 $('#tabs').tabs('add', panelInfo);
		}else{
        	var content = createFrame(href);
    		$('#tabs').tabs('add',{
    			title:title,
    			content:content,
    			closable:true
    		});
        }
			
           
	}
	tabClose();
}

function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose() {
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}	
//绑定右键菜单事件
function tabCloseEven() {
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != '首页') {
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			})
		}
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t != '首页') {
				$('#tabs').tabs('close',t);
			}
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		var nextall = $('.tabs-selected').nextAll();		
		if(prevall.length>0){
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != '首页') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		if(nextall.length>0) {
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != '首页') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		return false;
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			if(t != '首页') {
				$('#tabs').tabs('close',t);
			}
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==1){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			if(t != '首页') {
				$('#tabs').tabs('close',t);
			}
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}