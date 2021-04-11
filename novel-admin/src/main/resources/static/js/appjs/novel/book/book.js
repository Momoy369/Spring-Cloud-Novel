var prefix = "/novel/book"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // Metode permintaan data server, dapatkan atau posting
                url: prefix + "/list", // Muat alamat data server
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // Setel ke true, akan ada efek perubahan warna yang saling bertautan
                dataType: "json", // Server mengembalikan jenis data dari Jenis data yang dikembalikan oleh server
                pagination: true, // Setel ke true untuk menampilkan bilah halaman di bagian bawah
                // queryParamsType : "limit",
                // //Set to limit akan mengirimkan parameter yang sesuai dengan format RESTFull
                singleSelect: false, // Setel ke true untuk menonaktifkan banyak pilihan
                // contentType : "application/x-www-form-urlencoded",
                // //Jenis pengkodean data dikirim ke server
                pageSize: 10, // Jika paging disetel, jumlah item data per halaman
                pageNumber: 1, // Jika distribusi sudah diatur, nomor halaman rumah
                //search : true, // Apakah akan menampilkan kotak pencarian
                showColumns: false, // Apakah akan menampilkan kotak drop-down konten (pilih kolom yang ditampilkan)
                sidePagination: "server", // Setel di mana paging, nilai opsional adalah "klien" atau "server"
                queryParams: function (params) {
                    //Deskripsi: Parameter yang diteruskan ke latar belakang termasuk indeks awal offset, ukuran langkah batas, urutan pengurutan, urutan: desc atau, dan pasangan nilai kunci dari semua kolom
                    var queryParams = getFormJson("searchForm");
                    queryParams.limit = params.limit;
                    queryParams.offset = params.offset;
                    return queryParams;
                },
                // //Saat meminta data server, Anda dapat menambahkan beberapa parameter tambahan dengan menulis ulang parameter tersebut, seperti parameter di toolbar
                // queryParamsType = 'limit' ,Parameter kembali harus mengandung
                // limit, offset, search, sort, order Jika tidak, Anda perlu memasukkan:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // Mengembalikan nilai false akan menghentikan permintaan
                responseHandler: function (rs) {

                    if (rs.code == 0) {
                        return rs.data;
                    } else {
                        parent.layer.alert(rs.msg)
                        return {total: 0, rows: []};
                    }
                },
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        title: 'Nomor seri',
                        formatter: function () {
                            return arguments[2] + 1;
                        }
                    },
                                                                        {
                                field: 'id',
                                title: 'Kunci utama'
                            },

                        
                                                                        {
                                field: 'workDirection',
                                title: 'Spesialis, 0: Fiksi pria, 1: Umum'
                            },

                        
                                                                        {
                                field: 'catId',
                                title: 'ID Kategori'
                            },

                        
                                                                        {
                                field: 'catName',
                                title: 'Judul Kategori'
                            },

                        
                                                                        {
                                field: 'catChildId',
                                title: 'ID sub-kategori'
                            },

                        
                                                                        {
                                field: 'catChildName',
                                title: 'Nama sub-kategori'
                            },

                        
                                                                        {
                                field: 'picUrl',
                                title: 'Sampul novel'
                            },

                        
                                                                        {
                                field: 'bookName',
                                title: 'Judul buku'
                            },

                        
                                                                        {
                                field: 'heroName',
                                title: 'Nama hero'
                            },

                        
                                                                        {
                                field: 'ladyName',
                                title: 'Nama heroine'
                            },

                        
                                                                        {
                                field: 'bookStyle',
                                title: 'Gaya kerja, 0: hewan peliharaan yang manis, 1: sadomasokisme, 2: lainnya'
                            },

                        
                                                                        {
                                field: 'bookLabel',
                                title: 'Label kerja'
                            },

                        
                                                                        {
                                field: 'authorId',
                                title: 'ID penulis'
                            },

                        
                                                                        {
                                field: 'authorName',
                                title: 'Nama penulis'
                            },

                        
                                                                        {
                                field: 'bookDesc',
                                title: 'Deskripsi buku'
                            },

                        
                                                                        {
                                field: 'score',
                                title: 'Skor, bidang yang dipesan'
                            },

                        
                                                                        {
                                field: 'bookStatus',
                                title: 'Status buku, 0: berlanjut, 1: selesai'
                            },

                        
                                                                        {
                                field: 'visitCount',
                                title: 'Volume klik'
                            },

                        
                                                                        {
                                field: 'wordCount',
                                title: 'Jumlah kata'
                            },

                        
                                                                        {
                                field: 'commentCount',
                                title: 'Jumlah komentar'
                            },

                        
                                                                        {
                                field: 'yesterdayBuy',
                                title: 'Jumlah pembelian kemarin'
                            },

                        
                                                                        {
                                field: 'lastIndexId',
                                title: 'ID katalog terbaru'
                            },

                        
                                                                        {
                                field: 'lastIndexName',
                                title: 'Nama direktori terbaru'
                            },

                        
                                                                        {
                                field: 'lastIndexUpdateTime',
                                title: 'Waktu pembaruan katalog terbaru'
                            },

                        
                                                                        {
                                field: 'isVip',
                                title: 'Status buku, 1: berbayar, 0: gratis'
                            },

                        
                                                                        {
                                field: 'status',
                                title: 'Status, 0: Masuk, 1: Di rak'
                            },

                        
                                                                        {
                                field: 'updateTime',
                                title: 'Waktu update'
                            },

                        
                                                                        {
                                field: 'createTime',
                                title: 'Waktu dibuat'
                            },

                        
                                                                        {
                                field: 'crawlSourceId',
                                title: 'ID stasiun sumber crawler'
                            },

                        
                                                                        {
                                field: 'crawlBookId',
                                title: 'ID novel situs asal yang diambil'
                            },

                        
                                                                        {
                                field: 'crawlLastTime',
                                title: 'Waktu dirayapi terakhir'
                            },

                        
                                                                        {
                                field: 'crawlIsStop',
                                title: 'Apakah pembaruan telah dihentikan, 0: tidak dihentikan, 1: dihentikan'
                            },

                        
                                        {
                        title: 'Pengoperasian',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var d = '<a class="btn btn-primary btn-sm ' + s_detail_h + '" href="#" mce_href="#" title="Rincian" onclick="detail(\''
                                + row.id
                                + '\')"><i class="fa fa-file"></i></a> ';
                            var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="Ubah" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var r = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="Menghapus"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            return d + e + r;
                        }
                    }]
            });
}
function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}
function add() {
    layer.open({
        type: 2,
        title: 'meningkatkan',
        maxmin: true,
        shadeClose: false, // Klik pada topeng untuk menutup lapisan
        area: ['800px', '520px'],
        content: prefix + '/add' // url iframe
    });
}
function detail(id) {
    layer.open({
        type: 2,
        title: 'Rincian',
        maxmin: true,
        shadeClose: false, // Klik pada topeng untuk menutup lapisan
        area: ['800px', '520px'],
        content: prefix + '/detail/' + id // iframe的url
    });
}
function edit(id) {
    layer.open({
        type: 2,
        title: 'Ubah',
        maxmin: true,
        shadeClose: false, // Klik pada topeng untuk menutup lapisan
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}
function remove(id) {
    layer.confirm('Apakah Anda yakin ingin menghapus data yang dipilih?', {
        btn: ['OK', 'Batal']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
}
function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // Kembalikan semua baris yang dipilih, jika tidak ada rekaman yang dipilih, kembalikan larik kosong
    if (rows.length == 0) {
        layer.msg("Pilih data yang akan dihapus");
        return;
    }
    layer.confirm("Konfirmasikan bahwa Anda ingin menghapus'" + rows.length + "' data?", {
        btn: ['OK', 'Batal']
        // Tombol
    }, function () {
        var ids = new Array();
        // Lintasi semua data baris yang dipilih dan dapatkan ID yang sesuai dengan setiap data
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}