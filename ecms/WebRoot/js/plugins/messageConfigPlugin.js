// 创建一个闭包  
(function($) {
	// 插件的定义  
	$.fn.messageConfigInit = function() {
		var $this = $(this);
		var id = $(this).attr('id');
		var height = $(document.body).height();
		
		var messageClientConfigTabs = $('#messageClientConfigTabs_' + id, $this);
		
		var messageClientAccount = $('#messageClientAccount',$this);
		var historyDialog = $('#historyDialog_'+id,$this);
		var historyList = $('#historyList_'+id,historyDialog);
		var failDialog = $('#failDialog_'+id,$this);
		var failList = $('#failList_'+id,failDialog);
		
		var messageClientSwitchForm = $('#messageClientSwitchForm_' + id, messageClientConfigTabs);
		
		$(messageClientConfigTabs).tabs("select", 0);
		$(messageClientConfigTabs).tabs({
			  onSelect:function(title,index){
			  		if(title=='帐户信息'){
			  			var infoResult = syncCallService('system/initAccountMessageConfig.do');
			  			$('#softwareSerialNo',messageClientAccount).html(infoResult.data.softwareSerialNo);
			  			$('#remainingCount',messageClientAccount).html(infoResult.data.remainingCount);
			  		}
			  		if(title=='短信开关'){
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
	          	{field:'messageContent',title:'内容',width:250,align:"center"},
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
		
	  	$(historyList).datagrid('getPager').pagination({   
	  		onSelectPage: function(page, rows){
	  			var options = $(historyList).datagrid('options');
	  			options.pageNumber = page;
	  			options.pageSize = rows;
		  		search();
	  		}
	  	});
	  	
		var search = function(){
			var beginDate = $('#beginDate',historyDialog).val();
			var endDate = $('#endDate',historyDialog).val();
			var messageType = $('#messageType',historyDialog).combobox('getValue');
			var content ={beginDate:beginDate,endDate:endDate,messageType:messageType};
			//取得列表信息
			$(historyList).datagrid({
				url:'system/queryPromptHistoryMessageSend.do',
				queryParams:content,
				pageNumber:1
			});
		};
		
		$('#search',historyDialog).click(function(){
			search();
		});
		
		//编辑框
		$(historyDialog).dialog({ 
			title:'发送记录',
		    width:750,
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
			search();
			$(historyDialog).dialog('open');
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
	          	{field:'messageContent',title:'内容',width:250,align:"center"},
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
	  	
	  	//分页条
	  	$(failList).datagrid('getPager').pagination({   
	  		onSelectPage: function(page, rows){
	  			var options = $(failList).datagrid('options');
	  			options.pageNumber = page;
	  			options.pageSize = rows;
		  		search();
	  		}
	  	});
		
		$(failDialog).dialog({ 
			title:'失败记录',
		    width:750,
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
	  	
		var url = "system/getMainSwitchMessageConfig.do";
		var result = syncCallService(url, null);
		if (result.isSuccess) {
			var data = result.data;
			var messageConfig = eval("(" + data.messageConfig + ")");
			if (messageConfig.status == 1) {
				$('#parentSwitch', messageClientSwitchForm).attr('checked',true);
			} else {
				$('#parentSwitch', messageClientSwitchForm).attr('checked',false);
			}
		} else {
			$.messager.alert('提示', result.message, "error");
		}
		var subSwitchs = $('#subSwitchs_' + id, messageClientSwitchForm);
		var selectRow = null;
		var selectIndex = null;
		//加载列表
		$(subSwitchs).datagrid( {
			title:'发送点开关',
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
		$('#save_' + id, $this).click(function() {
			onSave();
		});
		var onSave = function() {
			$(subSwitchs).datagrid('endEdit', selectIndex);
			$(subSwitchs).datagrid('unselectAll');
			selectIndex = null;
			var statuss = new Array();
			var heads = new Array();
			var tails = new Array();
			if ($('#parentSwitch', messageClientSwitchForm).attr('checked') != null) {
				statuss.push(1);
			} else {
				statuss.push(0);
			}
			var rows = $(subSwitchs).datagrid('getRows');
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				statuss.push(row.status);
				heads.push(row.head);
				tails.push(row.tail);
			}
			var url = "system/saveMessageConfig.do";
			content = {
				statuss : statuss.join(CSIT.join),
				heads : heads.join(CSIT.join),
				tails : tails.join(CSIT.join)
			};
			var result = syncCallService(url, content);
			if (result.isSuccess) {
				var fn = function() {
					$(messageClientConfigTabs).tabs("select", 1);
				};
				$.messager.alert('提示', "保存成功", "info", fn);
			} else {
				$.messager.alert('提示', result.message, "error");
			}
		};
	};
})(jQuery);