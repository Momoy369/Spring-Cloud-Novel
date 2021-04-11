/**
 * Bootstrap Table Chinese translation
 * Author: Zhixin Wen<wenzhixin2010@gmail.com>
 */
(function ($) {
    'use strict';

    $.fn.bootstrapTable.locales['zh-CN'] = {
        formatLoadingMessage: function () {
            return 'Mencoba memuat data, harap tunggu ...';
        },
        formatRecordsPerPage: function (pageNumber) {
            return 'Setiap halaman menampilkan ' + pageNumber + ' data';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            return 'Menampilkan data dari' + pageFrom + ' sampai ' + pageTo + ' dari total ' + totalRows + ' data';
        },
        formatSearch: function () {
            return 'Cari';
        },
        formatNoMatches: function () {
            return 'Tidak ditemukan catatan yang cocok';
        },
        formatPaginationSwitch: function () {
            return 'Sembunyikan/tampilkan pagination';
        },
        formatRefresh: function () {
            return 'Segarkan';
        },
        formatToggle: function () {
            return 'Beralih';
        },
        formatColumns: function () {
            return 'Kolom';
        }
    };

    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

})(jQuery);
