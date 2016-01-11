// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.dictInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  
	  var dictTree =  $('#dictTree_'+id,$this);
	  var dictPanel =  $('#dictPanel_'+id,$this);
	  
	  //基础数据按钮
	  $(dictTree).tree({
			data: [{
				id:'Lesson',
				text: '课程'
			},{
				id:'Room',
				text: '考场'
			},{
				id:'Grade',
				text: '年级'
			},{
				id:'Major',
				text: '专业'
			},{
				id:'Class',
				text: '班级'
			}],
			onSelect:function (node) {
				var url ="dict/"+node.id.toLowerCase()+".do";
				$(dictPanel).panel({ 
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
	  
	  var node = $(dictTree).tree('find', 'Lesson');
	  $(dictTree).tree('select', node.target);
  }
})(jQuery);   