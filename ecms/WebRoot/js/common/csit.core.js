var CSIT={};
CSIT.id_index = 0;
CSIT.genId = function () {
    return 'CSIT_UI_' + (CSIT.id_index++);
};
CSIT.join ='^'; 
CSIT.empty_row = {rows:[]};
CSIT.empty_row_total = {rows:[],total:1};
CSIT.ClearUrl='common/clearDatagridCommon.do';
CSIT.ClearData ={rows:[],total:0};
CSIT.ClearDataWithoutTotal ={rows:[]};
CSIT.ClearCombobxData = [];
CSIT.currTabRightId = null;
CSIT.tempId = null;
CSIT.tempId2 = null;
var getRights = function(rightId){
	var url = 'system/queryChildrenRightTeacher.do';
	var content ={rightId:rightId,kind:2};
	return syncCallService(url,content);
};

var checkRight = function(checkArray,result){
	$(checkArray).each(function(index,item){
		$(item).hide();
	});
	for ( var i = 0; i < result.length; i++) {
		$(checkArray).each(function(index,item){
			var btnText = $.trim($(item).text());
			if(btnText==$.trim(result[i].text)){
				$(item).show();
				return;
			}
		});
	}
};

var getJsonLength =  function(jsonData){
	var jsonLength = 0;
	for(var item in jsonData){
		jsonLength++;
	}
	return jsonLength;
};

(function () {
    var oneRender = function () {
        var athis = $(this);
        var uistr = athis.attr('ui');
        athis.removeAttr('ui');
        athis.removeClass('cui');
        var uis;
        if (uistr.indexOf('[') == 0)
            try {
                eval('uis=' + uistr + ';');
            } catch (e) {
            }
        else uis = uistr;
        if (typeof(uis) == 'string')uis = [uis];

        $(uis).each(function () {
            var uiName = this;
            if (!athis.attr('c-init-' + uiName)) {
                var optStr = athis.attr(uiName + '-options');
                athis.removeAttr(uiName + '-options');
                if (optStr) {
                    var opts = {};
                    try {
                        var x = 'opts=' + optStr + ';';
//                        alert(x);
                        eval(x);
                    } catch (e) {
                        console.log('error:' + uiName + '-options=' + optStr);
                    }
                }
                if ($.fn[uiName]) {
                    athis[uiName](opts);
                }else if (uiName.indexOf('Init') == uiName.length - 4) {
                    var js = uiName;
                    if (js.indexOf('Init') == js.length - 4) {
                        js = js.substring(0, js.indexOf('Init'))
                    }
                    js = 'js/plugins/' + js + 'Plugin.js?t=' + new Date().getTime();
                    $.xLazyLoader({
                        js:js,
                        success:function () {
                            athis[uiName](opts);
                        }
                    });
                }
            }
            athis.attr('c-init-' + uiName);
        });
    };
    CSIT.initContent = function (els) {
        return $(els).each(function () {
            var el = $(this);
            if (el.hasClass('cui') || el.attr('ui')){
                oneRender.apply(el, []);
            }
            $('.cui,[ui]', el).each(function () {
                oneRender.apply(this, []);
            });
            return el;
        });
    };
})();