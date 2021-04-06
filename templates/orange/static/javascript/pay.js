var UserPay = {
    czData: [[30, "3000 KOIN"], [50, "5000 KOIN"], [100, "10000 KOIN"], [200, "20000 KOIN"], [500, "50000 KOIN"], [365, "Bacaan sepanjang tahun"] ],
    czPayPalData: [[20, "10000 KOIN"], [50, "25000 KOIN"], [100, "50000 KOIN"], [80, "Bacaan sepanjang tahun"]],
    sendPay: function () {
        $("#payform").submit();
    },
    GetPayState: function (payId) {
        $.post("/ams/api/v1/payments/pay", { act: "getpaystatus", pid: payId }, function (data, textStatus) {
            if (data == "1") {
                location.href = '/pay/wx_return.aspx?out_trade_no=sc'+payId;
            }
            else {
                setTimeout("UserPay.GetPayState("+payId+")",3000);
            }
        }, "html");
    }
}

$(function () {
    $("#ulPayType li").click(function () {

        if($(this).attr("valp")==2){
            layer.alert("WeChat Pay belum dibuka, jadi pantau terus");
        }

        return ;



        $($(this).parent()).children().each(function () {
            $(this).removeClass("on");
        });
        $(this).addClass("on");

        var type = $(this).attr("valp");
        if (type == "3") {
            $("#ulPayPal").show();
            $("#ulPayPalXJ").show();
            $("#ulZFWX").hide();
            $("#ulZFWXXJ").hide();
        }
        else {
            $("#ulPayPal").hide();
            $("#ulPayPalXJ").hide();
            $("#ulZFWX").show();
            $("#ulZFWXXJ").show();
        }

        var postUrl = "";
        switch (type)
        {
            case "1":
                postUrl = "sendalipay.aspx";
                break;
            case "2":
                postUrl = "sendwxpaynowqr.aspx";
                break;
            case "3":
                postUrl = "sendpaypal.aspx";
                break;
        }
        $("#payform").attr("action", postUrl);
    })

    $("#ulZFWX li").click(function () {
        $("#ulZFWX li").removeClass("on");
        $(this).addClass("on");
        if ($(this).attr("vals") > 0) {
            $("#pValue").val($(this).attr("vals"));
            $("#showTotal").html('Rp' + $(this).attr("vals") + 'IDR');
            for (var i = 0; i < UserPay.czData.length; i++) {
                if (UserPay.czData[i][0] == $(this).attr("vals")) {
                    $("#showRemark").html(UserPay.czData[i][1]);
                    break;
                }
            }
        }
    });
    $("#ulPayPal li").click(function () {
        $("#ulPayPal li").removeClass("on");
        $(this).addClass("on");
        if ($(this).attr("vals") > 0) {
            $("#pValue").val($(this).attr("vals"));
            $("#showPayPalTotal").html($(this).attr("vals") + 'USD');
            for (var i = 0; i < UserPay.czData.length; i++) {
                if (UserPay.czPayPalData[i][0] == $(this).attr("vals")) {
                    $("#showPayPalRemark").html(UserPay.czPayPalData[i][1]);
                    break;
                }
            }
        }
    });
});