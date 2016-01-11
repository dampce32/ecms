// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.systemConfigInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var height = $(document.body).height();
	  var systemConfigTabs = $('#systemConfigTabs_' + id, $this);
	  
	  var emailConfigForm = $('#emailConfigForm_'+id,$this);
	  var companyConfigForm = $('#companyConfigForm_'+id,$this);
	  var companyProfiles = CKEDITOR.replace('companyProfiles',{ 
		  height:140,
		  width:627,
		  toolbar:'Text'
	  });
	  CKFinder.setupCKEditor(companyProfiles,'ckfinder/') ;
	  var getContent = function(){
	     return CKEDITOR.instances.companyProfiles.getData();    //获取编辑器的值
	  };
	  var setContent = function(data){
		CKEDITOR.instances.companyProfiles.setData(data);	//赋值给编辑器
	  };
	  var url = "system/initSystemConfig.do";
	  var result = syncCallService(url,null);
	  if(result.isSuccess){
		  var data=result.data;
		  var systemConfig=eval("("+data.systemConfig+")");
		  $(emailConfigForm).form('load',systemConfig);
		  setContent(systemConfig.companyProfiles);
		  $('#companyName',companyConfigForm).val(systemConfig.companyName);
		  $('#hotline',companyConfigForm).val(systemConfig.hotline);
		  $('#companyPicId',companyConfigForm).val(systemConfig.pictureId);
		  var uploadPicture;
		  var property={
					multiple:false,
					sizeLimit:"2MB",
				    file_types:"*.jpg;*.gif;*.png",
				    btn_add_text:"添加",
				    btn_up_text:"上传",
				    btn_cancel_text:"放弃",
				    btn_clean_text:"清空",
				    op_del_text:"单项删除",
				    op_up_text:"单项上传",
				    op_fail_text:"上传失败",
				    op_ok_text:"上传成功",
				    op_no_text:"取消上传",
				upload_url:"information/mulUploadPhotoCompetitionPhoto.do;jsessionid="+sessionId,
				flash_url :"js/GooUploader/swfupload.swf"
			};
		    uploadPicture=$.createGooUploader($('#uploadCompanyPicture'),property);
		    uploadPicture.$swfUpload.uploadSuccess=function(file,msg){
		        var id=file.id;
		        uploadPicture.$fileList[id].span.html("100%");
		        var li=uploadPicture.$content.children("#"+id);
		        li.children(".op_no").css("display","none");
		        li.children(".op_ok").css("display","block");
		        $('#companyPicId',companyConfigForm).val(msg);
		        $('#companyPic',companyConfigForm).attr('src',ECMS.competitionPhotoPath+'/t_'+msg+'.png');
		    };
		    $('#companyPicId',companyConfigForm).val(systemConfig.pictureId);
			$('#companyPic',companyConfigForm).attr('src',ECMS.competitionPhotoPath+'/t_'+systemConfig.pictureId+'.png');
	  }else{
		  $.messager.alert('提示',result.message,"error");
	  }
	  //修改
	  $('#saveCompany_'+id,$this).click(function(){
		  onSave('company');
	  });
	//修改
	  $('#saveEmail_'+id,$this).click(function(){
		  onSave('email');
	  });
	  var onSave = function(kindSave){
		  var url = "system/saveSystemConfig.do";
		  var content = null;
		  if('company'==kindSave){
			  var companyName = $('#companyName',companyConfigForm).val();
			  var companyPicId = $('#companyPicId',companyConfigForm).val();
			  var hotline = $('#hotline',companyConfigForm).val();
			  var companyProfiles = getContent();
			  content = {companyName:companyName,'picture.pictureId':companyPicId,hotline:hotline
			  			,companyProfiles:companyProfiles,kindSave:kindSave};
		  }else if('email'==kindSave){
			  var emailHost = $('#emailHost',emailConfigForm).val();
			  var emailCode = $('#emailCode',emailConfigForm).val();
			  var emailPwd = $('#emailPwd',emailConfigForm).val();
			  var emailExpireTime = $('#emailExpireTime',emailConfigForm).val();
			  var registerEmailExpireTime = $('#registerEmailExpireTime',emailConfigForm).val();
			  content = {emailCode:emailCode,emailPwd:emailPwd,emailExpireTime:emailExpireTime,
					  	registerEmailExpireTime:registerEmailExpireTime,emailHost:emailHost,kindSave:kindSave};
		  }
		  var result = syncCallService(url,content);
		  if(result.isSuccess){
			  var fn = function() {
				  
				}
			  $.messager.alert('提示','保存成功','info',fn);
		  }else{
			  $.messager.alert('提示',result.message,"error");
		  }
	  }
	  //------------系统短信服务器注册
	  $('#msgRegisterBtn_'+id,$this).click(function(){
		  $($this).mask({maskMsg:'正在进行注册...'});
		  $('#msgRegisterForm_'+id).form('submit',{
			url:'system/registMessageSend.do',
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					$.messager.alert('提示','注册成功','info');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
		 $($this).mask('hide');
	  })
	  //-------------短信账户------------
	  var messageClientAccount = $('#messageClientAccount',$this);
	  var historyDialog = $('#historyDialog_'+id,$this);
	  var historyList = $('#historyList_'+id,historyDialog);
	  var failDialog = $('#failDialog_'+id,$this);
	  var failList = $('#failList_'+id,failDialog);
	  $(systemConfigTabs).tabs({
		  onSelect:function(title,index){
		  		if(title=='短信账户信息'){
		  			var infoResult = syncCallService('system/initAccountMessageConfig.do');
		  			$('#softwareSerialNo',messageClientAccount).html(infoResult.data.softwareSerialNo);
		  			$('#remainingCount',messageClientAccount).html(infoResult.data.remainingCount);
		  		}
		  		if(title=='系统开关'){
		  			var url = "system/getMainSwitchMessageConfig.do";
		  			var result = syncCallService(url);
		  			if (result.isSuccess) {
		  				var data = result.data;
		  				var messageConfigs = eval("(" + data.messageConfig + ")");
		  				for(var messageConfig in messageConfigs){
		  					var switchId = messageConfigs[messageConfig].switchId;
		  					var status = messageConfigs[messageConfig].status;
		  					if(switchId==1){
		  						if (status == 1) {
				  					$('#msgMainSwitch', systemSwitchForm).attr('checked',true);
				  				} else {
				  					$('#msgMainSwitch', systemSwitchForm).attr('checked',false);
				  				}
		  					}else if(switchId==2){
		  						if (status == 1) {
				  					$('#mailMainSwitch', systemSwitchForm).attr('checked',true);
				  				} else {
				  					$('#mailMainSwitch', systemSwitchForm).attr('checked',false);
				  				}
		  					}
		  				}
		  				
		  			} else {
		  				$.messager.alert('提示', result.message, "error");
		  			}
		  		   	 $(subSwitchs).datagrid({
		  		   		 url : 'system/queryMessageConfig.do'
		  		   	 });
			  	}
		  }
	  });
	  $(historyList).datagrid({
	  		singleSelect:true,
	  		method:"POST",
	  		nowrap:true,
	  		striped: true,
	  		collapsible:true,
	  		pagination:true,
	  		rownumbers:true,
	  		selectOnCheck:false,
		  	checkOnSelect:false,
		  	fit:true,
			columns:[[
		      	{field:'sendTime',title:'发送时间',width:150,align:"center"},
	          	{field:'messageContent',title:'内容',width:450,align:"center"},
		      	{field:'receiveIDs',title:'收件人',width:100,align:"center"},
		      	{field:'messageType',title:'短信类型',width:100,align:"center",
		      		formatter:function(value,row,index){
		      			if(value==1){
		      				return '普通短信';
		      			}else if(value==2){
		      				return '注册验证';
		      			}else if(value==3){
		      				return '报名通知';
		      			}
		      		}
		      	},
		      	{field:'teacherName',title:'发件人',width:100,align:"center"}
			]]
	  	});
	  	//编辑框
		$(historyDialog).dialog({ 
			title:'历史记录',
		    width:1000,
		    height:height-30,
		    closed: true,  
		    cache: false,  
		    modal: true,
		    closable:false,
		    toolbar:[
		    	{text:'退出',iconCls:'icon-cancel',handler:function(){$(historyDialog).dialog('close');}
			}]
		});
	  	$('#showHistory',messageClientAccount).click(function(){
	  		searchHistoryMsg();
			$(historyDialog).dialog('open');
		});
		var searchHistoryMsg = function(){
			var beginDate = $('#beginDate',historyDialog).val();
			var endDate = $('#endDate',historyDialog).val();
			var messageType = $('#messageType',historyDialog).combobox('getValue');
			var content ={beginDate:beginDate,endDate:endDate,messageType:messageType};
			//取得列表信息
			$(historyList).datagrid({
				url:'system/queryPromptHistoryMessageSend.do',
				queryParams:content
			});
		};
		$('#searchHistoryMsg_'+id,historyDialog).click(function(){
			searchHistoryMsg();
		});
		$(failList).datagrid({
	  		singleSelect:true,
	  		method:"POST",
	  		nowrap:true,
	  		striped: true,
	  		collapsible:true,
	  		pagination:true,
	  		rownumbers:true,
	  		selectOnCheck:false,
		  	checkOnSelect:false,
		  	fit:true,
			columns:[[
		      	{field:'sendTime',title:'发送时间',width:150,align:"center"},
	          	{field:'messageContent',title:'内容',width:450,align:"center"},
		      	{field:'receiveIDs',title:'收件人',width:100,align:"center"},
		      	{field:'messageType',title:'短信类型',width:100,align:"center",
		      		formatter:function(value,row,index){
		      			if(value==1){
		      				return '普通短信';
		      			}else if(value==2){
		      				return '注册验证';
		      			}else if(value==3){
		      				return '报名通知';
		      			}
		      		}
		      	},
		      	{field:'teacherName',title:'发件人',width:100,align:"center"}
			]]
		});
		$(failDialog).dialog({ 
			title:'失败记录',
		    width:1000,
		    height:height-30,
		    closed: true,  
		    cache: false,  
		    modal: true,
		    closable:false,
		    toolbar:[
		    	{text:'退出',iconCls:'icon-cancel',handler:function(){$(failDialog).dialog('close');}}	
		    ]
		});
		$('#showFail',messageClientAccount).click(function(){
	  		$(failList).datagrid({ 
	  			url:'system/queryPromptFailMessageSend.do'
	  		});
			$(failDialog).dialog('open');
		});
		
		//-----------------系统开关----------------
		var systemSwitchForm = $('#systemSwitchForm_' + id, systemConfigTabs);
		var subSwitchs = $('#subSwitchs_' + id, systemSwitchForm);
		var selectIndex = null;
		//加载列表
		$(subSwitchs).datagrid( {
			title:'开关',
			width : 1000,
			method : "POST",
			nowrap : true,
			striped : true,
			collapsible : true,
			singleSelect : true,
			columns:[[
				{field : 'switchName',title : '开关名称',width : 100,align : "center"}, 
				{field : 'status',title : '状态',width : 50,align : "center",
					formatter : function(value, row, index) {
						if (value == 0) {
							return '关';
						} else if (value == 1) {
							return '开';
						}},editor : {type : 'checkbox',options : {on : '1',off : '0'}}}, 
				{field : 'head',title : '自定义头',width : 130,align : "center",editor : {type : 'text'}}, 
				{field : 'fixedPart',title : '固定部分例子',width : 377,align : "center"}, 
				{field : 'tail',title : '自定义尾',width : 130,align : "center",editor : {type : 'text'}}, 
				{field : 'note',title : '备注',width : 186,align : "center"} 
			]],
			onClickRow : function(rowIndex, rowData) {
				if (selectIndex != rowIndex) {
					$(subSwitchs).datagrid('endEdit', selectIndex);
					$(subSwitchs).datagrid('beginEdit', rowIndex);
				}
				selectRow = rowData;
				selectIndex = rowIndex;
			},
			onLoadSuccess : function() {
				selectRow = null;
				selectIndex = null;
			}
		});
		//修改
		$('#saveSystemSwitch_' + id, $this).click(function() {
			onSaveSystemSwitch();
		});
		var onSaveSystemSwitch = function() {
			$(subSwitchs).datagrid('endEdit', selectIndex);
			$(subSwitchs).datagrid('unselectAll');
			selectIndex = null;
			var switchIds = new Array();
			var statuss = new Array();
			var heads = new Array();
			var tails = new Array();
			//短信总开关
			switchIds.push(1);
			if ($('#msgMainSwitch', systemSwitchForm).attr('checked') != null) {
				statuss.push(1);
			} else {
				statuss.push(0);
			}
			heads.push('');
			tails.push('');
			//邮件总开关
			switchIds.push(2);
			if ($('#mailMainSwitch', systemSwitchForm).attr('checked') != null) {
				statuss.push(1);
			} else {
				statuss.push(0);
			}
			heads.push('');
			tails.push('');
			var rows = $(subSwitchs).datagrid('getRows');
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				switchIds.push(row.switchId);
				statuss.push(row.status);
				heads.push(row.head);
				tails.push(row.tail);
			}
			var url = "system/saveMessageConfig.do";
			var content = {
				switchIds : switchIds.join(CSIT.join),
				statuss : statuss.join(CSIT.join),
				heads : heads.join(CSIT.join),
				tails : tails.join(CSIT.join)
			};
			var result = syncCallService(url, content);
			if (result.isSuccess) {
				$.messager.alert('提示', "保存成功", "info");
			} else {
				$.messager.alert('提示', result.message, "error");
			}
		};
  }
})(jQuery);   