var UserUtil = {
    msgStyle: 'background-color:#333; color:#fff; text-align:center; border:none; font-size:20px; padding:10px;',
    GetFavoritesNew: function () {
        var bIdList = "";
        $(".book_list").each(function () {
            bIdList += "," + $(this).attr("vals");
        });
        if (bIdList != "") {
        }
    },
    GetHistory: function () {
        var bIdList = "";
        $(".book_list").each(function () {
            bIdList += "," + $(this).attr("vals");
        });
        if (bIdList != "") {
        }
    },
    GetChapterInfo: function () {
        var cIdList = "";
        $(".showCName").each(function () {
            cIdList += "," + $(this).attr("vals");
        });
        if (cIdList != "") {
        }
    },
    SignDay: function () {
        if (!signed) {
            signed = true;
        }
    },
    SignDayStatus: function () {
    },
    RegSendSms: function () {
        var mob = $("#txtUName").val();
        var cCode = $("#TxtChkCode").val();
        if (mob != "" && cCode != "") {
            $("#btnSendSms").attr("disabled", "disabled");
            $("#txtUName").attr("readonly", "true");
        }
        else {
            layer.open({
                content: 'Nomor ponsel dan kode verifikasi harus diisi',
                style: UserUtil.msgStyle,
                time: 2
            });
        }
    },
    GetPassSendSms: function () {
        var mob = $("#txtMobile").val();
        var cCode = $("#TxtChkCode").val();
        if (mob != "" && cCode != "") {
            $("#btnSendSms").attr("disabled", "disabled");
            $("#txtMobile").attr("readonly", "true");
        }
        else {
            layer.open({
                content: 'Nomor ponsel dan kode verifikasi harus diisi',
                style: UserUtil.msgStyle,
                time: 2
            });
        }
    },
    RegSmsWait: function () {
        if (secondStep > 0) {
            $("#btnSendSms").val("Kirim ulang(" + secondStep + ")");
            secondStep--;
            setTimeout("UserUtil.RegSmsWait()", 1000);
        }
        else {
            secondStep = 180;
            $("#btnSendSms").val("Dapatkan kembali kode verifikasi");
            $("#btnSendSms").removeAttr("disabled");
            $("#txtUName").removeAttr("readonly");
        }
    }
}