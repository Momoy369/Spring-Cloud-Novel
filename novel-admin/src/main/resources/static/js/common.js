var HtmlUtil = {
    /*1.Gunakan konverter internal browser untuk mencapai encoding html (escaping) */
    htmlEncode:function (html){
        //1.Pertama-tama secara dinamis buat elemen label penampung, seperti DIV
        var temp = document.createElement ("div");
        //2.然后将要转换的字符串设置为这个元素的innerText或者textContent
        (temp.textContent != undefined ) ? (temp.textContent = html) : (temp.innerText = html);
        //3.最后返回这个元素的innerHTML，即得到经过HTML编码转换的字符串了
        var output = temp.innerHTML;
        temp = null;
        return output;
    },
    /*2.Gunakan konverter internal browser untuk mencapai decoding html (arti sebaliknya) */
    htmlDecode:function (text){
        //1.Pertama-tama secara dinamis buat elemen label penampung, seperti DIV
        var temp = document.createElement("div");
        //2.然后将要转换的字符串设置为这个元素的innerHTML(ie，火狐，google都支持)
        temp.innerHTML = text;
        //3.最后返回这个元素的innerText或者textContent，即得到经过HTML解码的字符串了。
        var output = temp.innerText || temp.textContent;
        temp = null;
        return output;
    },
    /*3.Gunakan ekspresi reguler untuk mencapai encoding html (pelolosan) */
    htmlEncodeByRegExp:function (str){
        var temp = "";
        if(str.length == 0) return "";
        temp = str.replace(/&/g,"&amp;");
        temp = temp.replace(/</g,"&lt;");
        temp = temp.replace(/>/g,"&gt;");
        temp = temp.replace(/\s/g,"&nbsp;");
        temp = temp.replace(/\'/g,"&#39;");
        temp = temp.replace(/\"/g,"&quot;");
        return temp;
    },
    /*4.Gunakan ekspresi reguler untuk mencapai decoding html (arti terbalik) */
    htmlDecodeByRegExp:function (str){
        var temp = "";
        if(str.length == 0) return "";
        temp = str.replace(/&amp;/g,"&");
        temp = temp.replace(/&lt;/g,"<");
        temp = temp.replace(/&gt;/g,">");
        temp = temp.replace(/&nbsp;/g," ");
        temp = temp.replace(/&#39;/g,"\'");
        temp = temp.replace(/&quot;/g,"\"");
        return temp;
    },
    /*5.Gunakan ekspresi reguler untuk mencapai encoding html (pelolosan) (cara penulisan lain) */
    html2Escape:function(sHtml) {
        if(sHtml == undefined || sHtml == null || sHtml.length == 0) return "";
        return sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
    },
    /*6.Gunakan ekspresi reguler untuk mencapai decoding html (arti terbalik) (cara penulisan lain) */
    escape2Html:function (str) {
        if(str == undefined || str == null || str.length == 0) return "";
        var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
        return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
    }
};

function getFormJson(formID) {
    var fields = $('#'+formID).serializeArray();
    var obj = {}; //声明一个对象
    $.each(fields, function (index, field) {
        obj[field.name] = field.value; //通过变量，将属性值，属性一起放到对象中
    })
    return obj;
}


//全站ajax加载提示
(function ($) {
    $(document).ajaxStart(function () {
        var index = layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
    });
    $(document).ajaxStop(function () {
        layer.closeAll('loading');
    });
    //登录过期，shiro返回登录页面
    $.ajaxSetup({
        complete: function (xhr, status,dataType) {
            if('text/html;charset=UTF-8'==xhr.getResponseHeader('Content-Type')){
                top.location.href = '/login';
            }
        }
    });
})(jQuery);