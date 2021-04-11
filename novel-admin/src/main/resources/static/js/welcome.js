//Pesan selamat datang

layer.config({
    extend: ['extend/layer.ext.js', 'skin/moon/style.css'],
    skin: 'layer-ext-moon'
});

layer.ready(function () {

    var html = $('#welcome-template').html();
    $('a.viewlog').click(function () {
        logs();
        return false;
    });

    $('#pay-qrcode').click(function(){
        var html=$(this).html();
        parent.layer.open({
            title: false,
            type: 1,
            closeBtn:false,
            shadeClose:true,
            area: ['600px', 'auto'],
            content: html
        });
    });

    function logs() {
        parent.layer.open({
            title: 'Melihat cinta untuk pertama kalinya, selamat tinggal cinta',
            type: 1,
            area: ['700px', 'auto'],
            content: html,
            btn: ['OK', 'Batal']
        });
    }

    console.log('Selamat menggunakan H+. Jika Anda menemui masalah selama penggunaan, Anda dapat merujuk ke dokumentasi pengembangan. Terima kasih atas dukungan Anda.');

});
