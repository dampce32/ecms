// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.mailsSenderInit = function() {
	$(this).mask({maskMsg:'正在加载界面'});
    var $this = $(this);
    var id = $(this).attr('id');
    var rightId = $(this).attr('rightId');
    var height = $(document.body).height();
	
    var list = $('#list_'+id,$this);
    var editForm = $('#editForm_'+id,$this);
    
    var editDialog = $('#editDialog_'+id,$this);
    var viewList = $('#viewList_'+id,editDialog);
    var queryContent = $('#queryContent_'+id,$this);
    
    var addDialog = $('#addDialog_'+id,$this);
	var addList = $('#addList_'+id,addDialog);
	var lastIndex = null;
    
  	//新添加的文件Id
  	var fileIdNewArray = null;
  	
  	$('#timingCheckbox',editForm).attr('checked',false);
  	$('#sendTime',editForm).hide();
  	
  	$('#timingCheckbox',editForm).click(function(){
  		if($(this).attr('checked')=='checked'){
  			$('#sendTime',editForm).show();
  		}else{
  			$('#sendTime',editForm).hide();
  		}
  	});
  	
  	var editor = CKEDITOR.replace('content',{
  		toolbar:[
  		    ['Bold','Italic','Underline','Font','FontSize','TextColor','BGColor','Image','Smiley','Maximize'],
  		    '/',
  		    ['JustifyLeft','JustifyCenter','JustifyRight','-','NumberedList','BulletedList','-','Outdent','Indent']
  		],
  		width:545,
  		height:260-667+height
  	});
  	
  	CKFinder.setupCKEditor(editor,'ckfinder/');
	  
  	//获取编辑器的值
  	var getContent = function(){
  		return CKEDITOR.instances.content.getData();    
  	};
  	//赋值给编辑器
  	var setContent = function(data){
		CKEDITOR.instances.content.setData(data);	
  	};
	
	//邮件上传
	var uploadFile;
	$("#uploadFile",editForm).html('');
	//新添加的文件Id
	fileIdNewArray = new Array();
	var property={
			width:547,
			height:110,
			multiple:true,
		    file_types:"*.*",
		    file_types_description: "Web Files",
		    btn_add_text:"添加",
		    btn_up_text:"上传",
		    btn_cancel_text:"放弃",
		    btn_clean_text:"清空",
		    op_del_text:"单项删除",
		    op_up_text:"单项上传",
		    op_fail_text:"上传失败",
		    op_ok_text:"上传成功",
		    op_no_text:"取消上传",
		upload_url:"contacts/mulUploadMailsSender.do;jsessionid="+sessionId,
		flash_url :"js/GooUploader/swfupload.swf"
	};
    uploadFile=$.createGooUploader($("#uploadFile",editForm),property);
    uploadFile.$swfUpload.uploadSuccess=function(file,msg){
        fileIdNewArray.push(msg);
    };
	
	//保存前的赋值操作
	var setValue = function(){
		//附件Ids
	    var fileIds = fileIdNewArray.join(',');
	    $('#attachment',editForm).val(fileIds);
		
	    //编辑器内容
		$('#content',editForm).val(getContent());
		
		//联系人
		var rows = $(list).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','至少选择一个发送对象','warning');
			return false;
		}
		var array = new Array();
		var nameArray = new Array();
		var emailArray = new Array();
		$(rows).each(function(index,row){
			array.push(row.contactsId);
			nameArray.push(row.name==''?' ':row.name);
			emailArray.push(row.email);
		});
		$('#receiveIDs',editForm).val(array.join(','));
		$('#names',editForm).val(nameArray.join(','));
		$('#emails',editForm).val(emailArray.join(','));
		
		return true;
	};
	
	//发送
	var onSend = function(){
		$(editForm).form('submit',{
			url:'contacts/saveMailsSender.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					$.messager.alert('提示','加入发送队列','info');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	};
	
	var onDelete = function(){
		var rows = $(list).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert('提示','至少选择一条数据','warning');
			return;
		}
		$(rows).each(function(index,row){
			var i = $(list).datagrid('getRowIndex',row);
			$(list).datagrid('deleteRow',i);
		});
	};
	
    $(list).datagrid({
		singleSelect:false,
		method:"POST",
		nowrap:true,
		fitColumns:true,
		fit:true,
		striped: true,
		collapsible:true,
		pagination:false,
		rownumbers:true,
		toolbar:[
	    		{text:'通讯录',iconCls:'icon-add',handler:function(){onAdd();}},'-',
	    		{text:'手动输入',iconCls:'icon-add',handler:function(){onAddByInput();}},'-',
	    		{text:'删除',iconCls:'icon-remove',handler:function(){onDelete();}
		}],
		columns:[[
		    {field:'contactsId',checkbox:true},
			{field:'name',title:'姓名',width:80,align:"center"},
			{field:'email',title:'邮箱地址',width:110,align:"center"}
	    ]]
    });
    
    $('#addBtn_'+id,$this).click(function(){
    	onSend();
    });
    
    //-------------------------------------通讯录------------------------------------
    var onSelect = function(){
		var rows = $(viewList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert('提示','至少选择一条数据','warning');
			return;
		}
		$(rows).each(function(index,row){
			$(list).datagrid('appendRow',{contactsId:row.contactsId,name:row.name,email:row.email});
		});
		onExit();
	};
    
    var onExit = function(){
		$(editDialog).dialog('close');
		$('#name',queryContent).val('');
		$('#mail',queryContent).val('');
		$(viewList).datagrid('loadData',CSIT.ClearData);
	};
	
    //编辑框
	$(editDialog).dialog({ 
		title:'通讯录',
	    width:750,
	    height:height-30,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[
	    		{text:'选择',iconCls:'icon-ok',handler:function(){onSelect();}},'-',
	    		{text:'退出',iconCls:'icon-cancel',handler:function(){onExit();}
		}]
	});
    
	var onAdd = function(){
		$(editDialog).dialog('open');
	};
	
	$(viewList).datagrid({
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		pagination:true,
		rownumbers:true,
		singleSelect:false,
		fitColumns:true,
		fit:true,
		columns:[[
		    {field:'contactsId',checkbox:true},
			{field:'name',title:'姓名',width:100,align:"center"},
			{field:'email',title:'邮件地址',width:100,align:"center"}
		]]
	});
	
	$('#search',queryContent).click(function(){
		var name = $('#name',queryContent).val();
		var email = $('#email',queryContent).val();
		var url ='contacts/queryContacts.do';
		var content = {name:name,email:email};
		$(viewList).datagrid({
			url:url,
			queryParams:content,
			pageNumber:1
		});
	});
	
	//-------------------------------------手动输入---------------------------------------
	var onAddSave = function(){
		var rows = $(addList).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','至少添加一条数据','warning');
			return;
		}
		if(lastIndex!=null){
			$(addList).datagrid('endEdit', lastIndex);
			lastIndex=null;
		}
		$(rows).each(function(index,row){
			var contactsId = null;
			if(row.visible=='是'){
				contactsId=0;
			}else{
				contactsId=-1;
			}
			$(list).datagrid('appendRow',{
				contactsId:contactsId,
				name:row.name,
				email:row.email
			});
		});
		onAddExit();
	};

	var onAddExit = function(){
		$(addDialog).dialog('close');
		$(addList).datagrid('loadData',CSIT.empty_row);
	};
	
    $(addDialog).dialog({ 
		title:'添加收件人',
	    width:750,
	    height:height-30,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[
	    		{text:'保存',iconCls:'icon-ok',handler:function(){onAddSave();}},'-',
	    		{text:'退出',iconCls:'icon-cancel',handler:function(){onAddExit();}
		}]
	});
	 
	var onAddByInput = function(){
		$(addDialog).dialog('open');
	};
	
	var onAddSend = function(){
		if(lastIndex!=null){
			$(addList).datagrid('endEdit', lastIndex);
		}
		$(addList).datagrid('appendRow',{
			contactsId:'',
			name:'',
			visible:'是'
		});
	};
	
	var onDeleteSend = function(){
		var rows = $(addList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert('提示','至少选择一条数据','warning');
			return;
		}
		$(rows).each(function(index,row){
			var i = $(addList).datagrid('getRowIndex',row);
			$(addList).datagrid('deleteRow',i);
		});
		lastIndex=null;
	};
	
	$(addList).datagrid({
		singleSelect:false,
		method:"POST",
		nowrap:true,
		fitColumns:true,
		fit:true,
		striped: true,
		collapsible:true,
		pagination:false,
		rownumbers:true,
		toolbar:[
	    		{text:'新增',iconCls:'icon-add',handler:function(){onAddSend();}},'-',
	    		{text:'删除',iconCls:'icon-remove',handler:function(){onDeleteSend();}
		}],
		columns:[[
		    {field:'contactsId',checkbox:true},
			{field:'name',title:'姓名',width:100,align:"center",editor:'text'},
			{field:'email',title:'邮件地址',width:100,align:"center",editor:{type:'validatebox',options:{validType:'email'}}},
			{field:'visible',title:'添加到通讯录',width:100,align:"center",editor:{type:'checkbox',options:{on:'是',off:'否'}}}
		]],
		onClickRow:function(rowIndex){
			if (lastIndex != rowIndex){
				$(addList).datagrid('endEdit', lastIndex);
				$(addList).datagrid('beginEdit', rowIndex);
			}
			lastIndex = rowIndex;
		}
	});
	
	$(this).mask('hide');
  };
})(jQuery);   