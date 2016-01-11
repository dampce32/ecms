// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.subjectInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  
	  var subjectTree =  $('#subjectTree_'+id,$this);
	  var subjectPanel =  $('#subjectPanel_'+id,$this);
	  
	  //基础数据按钮
	  $(subjectTree).tree({
			data: [{
				id:'Uniterm',
				text: '单项选择'
			},{
				id:'Fill',
				text: '填空'
			},{
				id:'Cloze',
				text: '完型填空'
			},{
				id:'Read',
				text: '阅读理解'
			}
			],
			onSelect:function (node) {
				var url ="subject/"+node.id.toLowerCase()+".do";
				$(subjectPanel).panel({ 
				    href:url,  
				    cache:false,
				    border:false, 
				    plain:true,
				    fit:true,
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
				});
	        }
		});
	  
	  var node = $(subjectTree).tree('find', 'Uniterm');
	  $(subjectTree).tree('select', node.target);
  };
})(jQuery);   