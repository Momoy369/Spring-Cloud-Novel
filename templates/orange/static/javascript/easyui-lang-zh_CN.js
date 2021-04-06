if ($.fn.pagination){
    $.fn.pagination.defaults.beforePageText = 'Awal';
    $.fn.pagination.defaults.afterPageText = 'Total{pages}halaman';
    $.fn.pagination.defaults.displayMsg = 'Tampilkan{from}dari{to},total{total}konten';
}
if ($.fn.datagrid){
    $.fn.datagrid.defaults.loadMsg = 'tunggu sebentar...';
}
if ($.fn.treegrid && $.fn.datagrid){
    $.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager){
    $.messager.defaults.ok = 'OK';
    $.messager.defaults.cancel = 'Batal';
}
if ($.fn.validatebox){
    $.fn.validatebox.defaults.missingMessage = 'Entri ini wajib diisi';
    $.fn.validatebox.defaults.rules.email.message = 'silakan isi alamat email';
    $.fn.validatebox.defaults.rules.url.message = 'Harap masukkan alamat URL yang valid';
    $.fn.validatebox.defaults.rules.length.message = 'Panjang masukan harus di antara{0}sampai{1}';
    $.fn.validatebox.defaults.rules.remote.message = 'Harap perbaiki bidang ini';
}
if ($.fn.numberbox){
    $.fn.numberbox.defaults.missingMessage = 'Entri ini wajib diisi';
}
if ($.fn.combobox){
    $.fn.combobox.defaults.missingMessage = 'Entri ini wajib diisi';
}
if ($.fn.combotree){
    $.fn.combotree.defaults.missingMessage = 'Entri ini wajib diisi';
}
if ($.fn.combogrid){
    $.fn.combogrid.defaults.missingMessage = 'Entri ini wajib diisi';
}
if ($.fn.calendar){
    $.fn.calendar.defaults.weeks = ['hari','1','2','3','4','5','6'];
    $.fn.calendar.defaults.months = ['Jan','Feb','Mar','Apr','Mei','Jun','Jul','Agu','Sep','Okt','Nov','Des'];
}
if ($.fn.datebox){
    $.fn.datebox.defaults.currentText = 'Sekarang';
    $.fn.datebox.defaults.closeText = 'Matikan';
    $.fn.datebox.defaults.okText = 'OK';
    $.fn.datebox.defaults.missingMessage = 'Entri ini wajib diisi';
    $.fn.datebox.defaults.formatter = function(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    };
    $.fn.datebox.defaults.parser = function(s){
        if (!s) return new Date();
        var ss = s.split('-');
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
            return new Date(y,m-1,d);
        } else {
            return new Date();
        }
    };
}
if ($.fn.datetimebox && $.fn.datebox){
    $.extend($.fn.datetimebox.defaults,{
        currentText: $.fn.datebox.defaults.currentText,
        closeText: $.fn.datebox.defaults.closeText,
        okText: $.fn.datebox.defaults.okText,
        missingMessage: $.fn.datebox.defaults.missingMessage
    });
}
