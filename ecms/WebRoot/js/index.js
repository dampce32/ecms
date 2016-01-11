$(function() {
	if(id=='competitionRule'){
		$('.toEnrollBtn').click(function(){
    		if(validateLogin()){
    			window.location.href = 'enrollStu.jsp?competitionId='+currCompetitionId;
    		}else{
    			toUrl = 'enrollStu.jsp?competitionId='+currCompetitionId;
    			showLoginModal('viewPaper');
    		}
    	});
	}else if(id=='competitionPhoto'){
		var width = $(document).width();
		var height = $(document).height();
		var currHref = window.location.href;
		var reg = new RegExp("photoType=([\\w]*)","g");
		var result=reg.exec(currHref);
		$(".photoType a[photoType=\'"+result[1]+"\']").parent().addClass("active");
		$('.photoType a').click(function(){
			var photoType = $(this).attr('photoType');
			//替换当前反问地址的赛事Id
			var currHref = window.location.href;
			var reg = new RegExp("photoType=[\\w]*","g");
			var result=reg.exec(currHref);
			if(result){
				//替换
				currHref = currHref.replace(reg, 'photoType='+photoType);
			}else{
				if(currHref.indexOf('?') == -1 ){
					currHref = currHref+'?photoType='+photoType;
				}
			}
			reg = new RegExp("page=[\\w]*","g");
			result=reg.exec(currHref);
			if(result){
				//替换
				currHref = currHref.replace(reg, 'page='+0);
			}else{
				if(currHref.indexOf('?') == -1 ){
					currHref = currHref+'?page='+0;
				}else{
					currHref = currHref+'&page='+0;
				}
			}
			window.location.href = currHref;
			return false;
		});
		function pageselectCallback(page_index, jq){
			//替换当前反问地址的赛事Id
			var currHref = window.location.href;
			var reg = new RegExp("page=[\\w]*","g");
			var result=reg.exec(currHref);
			if(result){
				//替换
				currHref = currHref.replace(reg, 'page='+page_index);
			}else{
				if(currHref.indexOf('?') == -1 ){
					currHref = currHref+'?page='+page_index;
				}else{
					currHref = currHref+'&page='+page_index;
				}
			}
			window.location.href = currHref;
            return false;
        }
		var total = parseInt($('#total').val()) ;
		var currPage = parseInt($('#currPage').val()) ;
		$('#Pagination').pagination(total, {
			callback: pageselectCallback,
            items_per_page:12, 
            num_display_entries:12,
            num_edge_entries:0,
            current_page:currPage,
            prev_text:'上一页',
            next_text:"下一页"
        });
		$(".group1").colorbox({rel:'group1',
			maxWidth:width,
			maxHeight:height-10
		});
	}else if(id=='competitionNotice'){
		function pageselectCallback(page_index, jq){
			//替换当前反问地址的赛事Id
			var currHref = window.location.href;
			var reg = new RegExp("page=[\\w]*","g");
			var result=reg.exec(currHref);
			if(result){
				//替换
				currHref = currHref.replace(reg, 'page='+page_index);
			}else{
				if(currHref.indexOf('?') == -1 ){
					currHref = currHref+'?page='+page_index;
				}else{
					currHref = currHref+'&page='+page_index;
				}
			}
			window.location.href = currHref;
            return false;
        }
		var total = parseInt($('#total').val()) ;
		var currPage = parseInt($('#currPage').val()) ;
		$('#Pagination').pagination(total, {
			callback: pageselectCallback,
            items_per_page:10, 
            num_display_entries:10,
            num_edge_entries:0,
            current_page:currPage,
            prev_text:'上一页',
            next_text:"下一页"
        });
	}else if(id=='myAccount'){
		var currHref = window.location.href;
		var reg = new RegExp("infoType=([\\w]*)","g");
		var result=reg.exec(currHref);
		if(result==null){
			var infoType = $(".infoType a").attr('infoType');
			$(".infoType a[infoType=\'"+infoType+"\']").parent().addClass("active");
		}else{
			$(".infoType a[infoType=\'"+result[1]+"\']").parent().addClass("active");
		}
		var pictureResult = syncCallService('student/getPictureStudent.do');
		if(pictureResult.isSuccess){
			$('#myPicture').attr('src',pictureResult.data.picturePath);
		}else{
			alert(pictureResult.message);
		}
		
		$('#updatePicture').click(function(){
			$('#uploadPictrueDialog').modal('show');
		 	return false;
		});
		$('#uploadPictrueBtn').click(function(){
			var upload = $('#upload','#uploadPictrueForm').val();
			if(upload == ''){
				alert('请选择新照片');
				return false;
			}
			$('#uploadPictrueForm').ajaxSubmit({
				url:'student/uploadPictureStudent.do',
  				type:"post",
  				dataType:"json",
  				success:function(result){
  					if(result.isSuccess){
  						var newPictureResult = syncCallService('student/getPictureStudent.do');
  						if(newPictureResult.isSuccess){
  							$('#myPicture').attr('src',newPictureResult.data.picturePath);
  						}else{
  							alert(newPictureResult.message);
  						}
  						$('#uploadPictrueDialog').modal('hide');
  					}else{
  						alert('修改失败');
  					}
  				}
  			});
		});
		
		$('.infoType a').click(function(){
			var href = $(this).attr('href');
			$(this).attr('href',href+'&competitionId='+currCompetitionId);
		});
	}else if(id=='trainingClass'){
		function pageselectCallback(page_index, jq){
			//替换当前反问地址的赛事Id
			var currHref = window.location.href;
			var reg = new RegExp("page=[\\w]*","g");
			var result=reg.exec(currHref);
			if(result){
				//替换
				currHref = currHref.replace(reg, 'page='+page_index);
			}else{
				if(currHref.indexOf('?') == -1 ){
					currHref = currHref+'?page='+page_index;
				}else{
					currHref = currHref+'&page='+page_index;
				}
			}
			window.location.href = currHref;
            return false;
        }
		var total = parseInt($('#total').val()) ;
		var currPage = parseInt($('#currPage').val()) ;
		$('#Pagination').pagination(total, {
			callback: pageselectCallback,
            items_per_page:10, 
            num_display_entries:10,
            num_edge_entries:0,
            current_page:currPage,
            prev_text:'上一页',
            next_text:"下一页"
        });
	}else if(id=='nextCompetitionStudent'){
		
		var currHref = window.location.href;
		var reg = new RegExp("competitionGroupId=([\\w]*)","g");
		var result=reg.exec(currHref);
		if(result==null){
			var nextCompetitionStudent = $(".nextCompetitionStudent a").attr('nextCompetitionStudent');
			$(".nextCompetitionStudent a[nextCompetitionStudent=\'"+nextCompetitionStudent+"\']").parent().addClass("active");
		}else{
			$(".nextCompetitionStudent a[nextCompetitionStudent=\'"+result[1]+"\']").parent().addClass("active");
		}
		function pageselectCallback(page_index, jq){
			//替换当前反问地址的赛事Id
			var currHref = window.location.href;
			var reg = new RegExp("page=[\\w]*","g");
			var result=reg.exec(currHref);
			if(result){
				//替换
				currHref = currHref.replace(reg, 'page='+page_index);
			}else{
				if(currHref.indexOf('?') == -1 ){
					currHref = currHref+'?page='+page_index;
				}else{
					currHref = currHref+'&page='+page_index;
				}
			}
			window.location.href = currHref;
            return false;
        }
		var total = parseInt($('#total').val()) ;
		var currPage = parseInt($('#currPage').val()) ;
		$('#Pagination').pagination(total, {
			callback: pageselectCallback,
            items_per_page:10, 
            num_display_entries:10,
            num_edge_entries:0,
            current_page:currPage,
            prev_text:'上一页',
            next_text:"下一页"
        });
	}else if(id=='competitionPrizeStudent'){
		var currHref = window.location.href;
		var reg = new RegExp("competitionGroupId=([\\w]*)","g");
		var result=reg.exec(currHref);
		if(result==null){
			var competitionPrizeStudent = $(".competitionPrizeStudent a").attr('competitionPrizeStudent');
			$(".competitionPrizeStudent a[competitionPrizeStudent=\'"+competitionPrizeStudent+"\']").parent().addClass("active");
		}else{
			$(".competitionPrizeStudent a[competitionPrizeStudent=\'"+result[1]+"\']").parent().addClass("active");
		}
		function pageselectCallback(page_index, jq){
			//替换当前反问地址的赛事Id
			var currHref = window.location.href;
			var reg = new RegExp("page=[\\w]*","g");
			var result=reg.exec(currHref);
			if(result){
				//替换
				currHref = currHref.replace(reg, 'page='+page_index);
			}else{
				if(currHref.indexOf('?') == -1 ){
					currHref = currHref+'?page='+page_index;
				}else{
					currHref = currHref+'&page='+page_index;
				}
			}
			window.location.href = currHref;
            return false;
        }
		var total = parseInt($('#total').val()) ;
		var currPage = parseInt($('#currPage').val()) ;
		$('#Pagination').pagination(total, {
			callback: pageselectCallback,
            items_per_page:10, 
            num_display_entries:10,
            num_edge_entries:0,
            current_page:currPage,
            prev_text:'上一页',
            next_text:"下一页"
        });
	}
	
});
