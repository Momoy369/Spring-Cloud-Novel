package com.java2nb.novel.core.exception;

import com.java2nb.novel.core.enums.ResponseStatus;
import lombok.Data;

/**
 * Pengecualian bisnis kustom, digunakan untuk memproses permintaan pengguna, muncul saat terjadi kesalahan bisnis
 */
@Data
public class BusinessException extends RuntimeException {

    private ResponseStatus resStatus;

    public BusinessException(ResponseStatus resStatus) {
        //Jangan panggil metode fillInStackTrace () dari kelas induk Throwable untuk menghasilkan informasi pelacakan tumpukan untuk meningkatkan kinerja aplikasi
        //Panggilan antar konstruktor harus di baris pertama
        super(resStatus.getMsg(), null, false, false);
        this.resStatus = resStatus;
    }


}
