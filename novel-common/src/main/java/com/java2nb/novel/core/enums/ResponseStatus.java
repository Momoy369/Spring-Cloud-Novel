package com.java2nb.novel.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 11797
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResponseStatus {

    /**
     * Permintaan berhasil
     * */
    OK(200,"SUCCESS"),

    /**
     * Pengecualian server
     * */
    ERROR(500,"Pengecualian tidak diketahui, harap hubungi administrator!"),

    /**
     * Kesalahan parameter
     * */
    PARAM_ERROR(400,"Parameter ilegal!"),

    /**
     * akses ditolak
     * */
    FORBIDDEN(403,"Akses ditolak!"),


    /**
     * Kesalahan terkait pengguna
     * */
   NO_LOGIN(1001, "Tidak login atau login tidak valid!"),
    VEL_CODE_ERROR(1002, "Kesalahan kode verifikasi!"),
    USERNAME_EXIST(1003,"Nomor telepon telah terdaftar!"),
    USERNAME_PASS_ERROR(1004,"Nomor telepon atau kata sandi salah!"),
    TWO_PASSWORD_DIFF(1005, "Kata sandi baru yang dimasukkan dua kali tidak cocok!"),
    OLD_PASSWORD_ERROR(1006, "Kata sandi lama tidak cocok!"),
    USER_NO_BALANCE(1007, "Saldo pengguna tidak mencukupi"),

    /**
     * Kesalahan terkait komentar
     * */
    HAS_COMMENTS(3001, "Buku telah ditinjau!"),

    /**
     * Kesalahan terkait penulis
     * */
    INVITE_CODE_INVALID(4001, "Kode undangan tidak valid!"),
    AUTHOR_STATUS_FORBIDDEN(4002, "Penulis dalam keadaan tidak normal dan tidak dapat mengelola novel untuk sementara waktu!")
    , BOOKNAME_EXISTS(4003,"Sebuah novel dengan nama yang sama telah diterbitkan!"),

    /**
     * Kesalahan terkait novel
     */
    BOOK_EXISTS(5001,"Novelnya sudah ada")

            ,
    /**
     * Kesalahan terkait mesin telusur
     * */
    ES_SEARCH_FAIL(9001,"Kesalahan kueri mesin telusur!"),


    /**
     * Kesalahan umum lainnya
     * */
    PASSWORD_ERROR(88001,"kata sandi salah!");

    private int code;
    private String msg;


}
