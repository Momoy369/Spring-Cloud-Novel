# Novel Spring Cloud Microservice

#### Situs resmi

https://xiongxyang.gitee.io

#### Proyek Baru: Novel Boutique House-Micro Service Edition

Platform portal baru layanan mikro Spring Cloud berdasarkan rumah butik baru.

Alamat GitHub Proyek Asli： https://github.com/201206030/novel-cloud

Alamat Gitee Proyek Asli： https://gitee.com/novel_dev_team/novel-cloud

#### Alamat demo

[Klik](http://47.106.243.172:8888/)


#### Pengenalan Proyek

Novel Boutique House-plus ada di [Fiction Boutique House](https://github.com/201206030/fiction_house)Atas dasar ini, komik dan modul berseri telah dihapus, berfokus pada novel. Ini adalah bacaan multi-end (PC, WAP), sistem CMS literasi yang berfungsi penuh, terdiri dari sistem portal front-end, penulis sistem back-end, Scrapping. Sistem terdiri dari beberapa subsistem, fungsi pendukung seperti pendaftaran anggota, berlangganan, rilis artikel dan laporan statistik Realtime.

Novel Boutique House-plus mendesain ulang basis data, mengubah kode, dan meningkatkan fungsi, yang meningkatkan keterbacaan dan kinerja program secara keseluruhan, dan menambahkan banyak fitur komersial. Peningkatan utama adalah sebagai berikut:

- [x] Basis data didesain ulang dan strukturnya disesuaikan.
- [x] Rekonstruksi kode server, MyBatis3 ditingkatkan ke MyBatis3DynamicSql.
- [x] Mode seluler dipisahkan dari mode PC, dan browser secara otomatis mengenalinya.
- [x] UI PC diperbarui.
- [x] Mendukung kustomisasi template front-end, beberapa set template built-in.
- [x] Modul berita.
- [x] Papan peringkat.
- [x] Modul ulasan novel.
- [x] Modul topik.
- [x] Area penulis.
- [x] Daftar.
- [x] Berlangganan.
- [x] Back-end.
- [x] Scrapping.

#### Struktur proyek

```
novel-plus -- Proyek induk
├── novel-common -- Modul umum
├── novel-front -- Portal front-end & subsistem manajemen back-end penulis
├── novel-crawl -- Subsistem Manajemen Perayap
├── novel-admin -- Subsistem manajemen back-end
└── templates -- Template front-end
```

#### Pemilihan tools

| teknologi                 | Deskripsi                                                         
| -------------------- | ---------------------------
| SpringBoot           | Spring Boot     
| MyBatis              | Kerangka kerja ORM lapisan persistensi
| MyBatis Dynamic SQL  | Sql dinamis mybatis
| PageHelper           | Plugin paging MyBatis
| MyBatisGenerator     | Plugin pembuatan kode lapisan persistensi
| Sharding-Jdbc        | Kode lapisan sub-database sub-tabel middleware
| JJWT                 | Dukungan login JWT
| SpringSecurity       | Kerangka keamanan                         
| Shiro                | Kerangka keamanan  
| Ehcache              | Kerangka kerja cache dalam proses Java (cache default)  
| Redis                | Cache terdistribusi (skema penggantian cache, dinonaktifkan secara default)                             
| ElasticSearch        | Mesin pencari (program peningkatan pencarian, nonaktif secara default)                      
| RabbitMq             | Antrian pesan (nonaktif secara default)  
| OSS                  | Alibaba Cloud Object Storage Service (salah satu metode penyimpanan gambar) 
| FastDfs              | Sistem file terdistribusi ringan open source (salah satu metode penyimpanan gambar)                     
| Redisson             | Menerapkan kunci terdistribusi                                       
| Lombok               | Alat pengemas objek yang disederhanakan                                                                               
| Docker               | Mesin kontainer aplikasi   
| Mysql                | Layanan database   
| Thymeleaf            | Mesin template     
| Layui                | UI bagian depan                    
                 

#### Kunjungi proyek asli di akun author

https://github.com/201206030/novel-cloud
