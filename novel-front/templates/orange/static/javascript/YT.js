var $C = function (objName) {
    if (typeof (document.getElementById(objName)) != "object")
    { return null; }
    else
    { return document.getElementById(objName); }
}
var YT = {
    BaseCommon: {
        gL: function (x) { var l = 0; while (x) { l += x.offsetLeft; x = x.offsetParent; } return l },
        gT: function (x) { var t = 0; while (x) { t += x.offsetTop; x = x.offsetParent; } return t }
    },
    BaseData: {
        WaitImg: "/images/loading.gif"
    },
    Fun: {
        GetWordLength: function (str) {
            str = str.replace(/(\n)+|(\r\n)+/g, "");
            str = str.replace(" ", "");
            str = str.replace("　", "");
            return str.length;
        },
        ConvertToMoney: function (btanch) {
            if (btanch != undefined) {
                return parseFloat(btanch) / 100;
            }
            else {
                return 0;
            }
        },
        LoadShow: function () {
            if ($C("LayerShowPic") == null) {
                var sp = document.createElement("div");
                sp.innerHTML = "<div id=\"LayerShowPic\" style=\"position:absolute;width:180px;height:70px;z-index:100;background-color: #fdfce9;border: 1px solid #666666;font-size:12px;\"><div align=\"center\" style=\"z-index:91;\"><br><img src=\"" + YT.BaseData.WaitImg + "\" align=\"absmiddle\" /> tunggu sebentar…</div></div><iframe id=\"LayerCover\" style=\"position:absolute;width:100%;height:100%;z-index:10;left: 0px;top: 0px;background-color:#eeeeee;FILTER: alpha(opacity=1);opacity: 0.3 !important; \"></iframe>";
                document.body.appendChild(sp);
            }
            $C("LayerShowPic").style.display = '';
            $C("LayerCover").style.display = '';
            $C("LayerCover").style.height = String(document.documentElement.scrollHeight) + 'px';
            YT.Fun.ScreenCenter($C("LayerShowPic"), 266, 200);

        },
        LoadHide: function () {
            if ($C("LayerShowPic") != null) {
                $C("LayerShowPic").style.display = 'none';
                $C("LayerCover").style.display = 'none';
            }
        },
        ScreenCenter: function (obj, width, height) {
            if (obj.style.display == 'none') {
                obj.style.display = '';
            }
            var scrolltop = document.documentElement.scrollTop;
            if (width <= 0) {
                width = obj.offsetWidth;
            }
            if (height <= 0) {
                height = obj.offsetHeight;
            }
            if (scrolltop == null || scrolltop == 0) {
                scrolltop = document.body.scrollTop;
            }
            var offsetHT = document.body.clientHeight / 2 - height / 2;
            if (offsetHT <= 0) { offsetHT = 10; }
            var offsetWT = document.body.clientWidth / 2 - width / 2;
            if (offsetWT <= 0) { offsetWT = 10; }
            obj.style.top = String(scrolltop + offsetHT) + 'px';
            obj.style.left = String(offsetWT) + 'px';
        },
        NewPanel: function (url, title, width, height, needFits) {
            if (typeof (width) == 'undefind' || width == null) { width = 750; }
            if (typeof (height) == 'undefind' || height == null) { height = 550; }
            var fits = false;
            if (typeof (needFits) != "undefined" && needFits) {
                if (document.body.clientWidth < 650 || document.body.clientHeight < 450 || document.body.clientHeight - 50 < height)
                { fits = true; }
            }
            if ($C("YT_Panel") == null) {
                var sp = document.createElement("div");
                sp.innerHTML = "<div id=\"YT_Panel\" class=\"easyui-panel\"><iframe frameborder=\"0\" id=\"YT_Panel_i\" name=\"YT_Panel_i\" scrolling=\"auto\" src=\"" + url + "\" style=\"height:100%;visibility:inherit; width:100%;z-index:1;\"></iframe></div>";
                document.body.appendChild(sp);
            }
            if (url.indexOf("?") > 0) {
                url = url + "&";
            }
            else {
                url = url + "?";
            }
            url = url + "randomkeys=" + Math.random();
            $C("YT_Panel_i").src = url;
            var sTop = null, sLeft = null;
            if (window.screen.height < 800) {
                sTop = 0;
            }
            if (fits) {
                sLeft = 0;
            }
            $('#YT_Panel').window({
                width: width,
                height: height,
                title: title,
                collapsible: true,
                minimizable: false,
                maximizable: true,
                closable: true,
                modal: true,
                fit: fits,
                top: sTop,
                left: sLeft
            });
        },
        NewPanelNoClose: function (url, title, width, height) {
            if (typeof (width) == 'undefind' || width == null) { width = 750; }
            if (typeof (height) == 'undefind' || height == null) { height = 550; }
            if ($C("YT_Panel") == null) {
                var sp = document.createElement("div");
                sp.innerHTML = "<div id=\"YT_Panel\" class=\"easyui-panel\" ><iframe frameborder=\"0\" id=\"YT_Panel_i\" name=\"YT_Panel_i\" scrolling=\"auto\" src=\"" + url + "\" style=\"height:100%;visibility:inherit; width:100%;z-index:1;\"></iframe></div>";
                document.body.appendChild(sp);
            }
            if (url.indexOf("?") > 0) {
                url = url + "&";
            }
            else {
                url = url + "?";
            }
            url = url + "randomkeys=" + Math.random();
            $C("YT_Panel_i").src = url;
            $('#YT_Panel').window({
                width: width,
                height: height,
                title: title,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                closable: false,
                modal: true
            });
        },
        ClosePanel: function (id) {
            if (typeof (id) == 'undefind' || id == null) {
                $('#YT_Panel').panel('close');
                /*CreateGrid();*/
                CreateGridReload();
            }
            else { $('#' + id).panel('close'); }
        },
        /*格式化时间字符串*/
        formatDate: function (now, types) {
            if (now != null && now != "") {
                var dateN = new Date(+/\d+/.exec(now)[0]);
                var year = dateN.getFullYear();
                var month = dateN.getMonth() + 1;
                var date = dateN.getDate();
                var hour = dateN.getHours();
                var minute = dateN.getMinutes();
                var second = dateN.getSeconds();
                if (typeof (types) != "undefined" && types != null) {
                    return year + "-" + month + "-" + date;
                }
                else if (hour == 0 && minute == 0 && second == 0) {
                    return year + "-" + month + "-" + date;
                }
                else {
                    return year + "-" + month + "-" + date + "   " + hour + ":" + minute + ":" + second;
                }
            }
            else {
                return "";
            }
        },
        /** 获取当前时间月份*/
        formatMonth: function (now) {
            if (now != null && now != "") {
                var dateN = new Date(+/\d+/.exec(now)[0]);
                var month = dateN.getMonth() + 1;
                return month;
            }
            else {
                return "";
            }
        },
        /** 获取当前时间具体的某一天*/
        formatDay: function (now) {
            if (now != null && now != "") {
                var dateN = new Date(+/\d+/.exec(now)[0]);
                var month = dateN.getMonth() + 1;
                var date = dateN.getDate();
                return month + "-" + date;
            }
            else {
                return "";
            }
        },
        /** 获取所属时间的季度*/
        formatSeasonal: function (now) {
            if (now != null && now != "") {
                var dateN = new Date(+/\d+/.exec(now)[0]);
                var year = dateN.getFullYear();
                var month = dateN.getMonth() + 1;
                if (month == 1) {
                    return year + "Season 1";
                }
                else if (month == 4) {
                    return year + "Season 2";
                }
                else if (month == 7) {
                    return year + "Season 3";
                }
                else {
                    return year + "Season 4";
                }
            }
            else {
                return "";
            }
        },

        formatStatus: function (id) {
            if (id == 0) {
                return "Tidak valid";
            }
            else {
                return "Valid";
            }
        },
        ShowPanel: function (obj, divName, xlong, ylong) {
            var showobj = $C(divName);
            if (showobj) {
                if (showobj.style.display == 'none') {
                    showobj.style.display = '';
                }
                if (xlong)
                { showobj.style.top = YT.BaseCommon.gT(obj) + 20 + xlong + "px"; }
                else
                { showobj.style.top = YT.BaseCommon.gT(obj) + 20 + "px"; }
                if (ylong)
                { showobj.style.left = YT.BaseCommon.gL(obj) + ylong + "px"; }
                else
                { showobj.style.left = YT.BaseCommon.gL(obj) + "px"; }
            }
        },
        GetDateDiff:function(startTime,endTime, diffType) {
            startTime = startTime.replace(/\-/g, "/");
            endTime= endTime.replace(/\-/g, "/");
            diffType = diffType.toLowerCase();
            var sTime = new Date(startTime);
            var eTime = new Date(endTime);
            var timeType = 1;
            switch (diffType) {
                case "second":
                    timeType = 1000;
                    break;
                case "minute":
                    timeType = 1000 * 60;
                    break;
                case "hour":
                    timeType = 1000 * 3600;
                    break;
                case "day":
                    timeType = 1000 * 3600 * 24;
                    break;
                default:
                    break;
            }
            return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(timeType));
        }
    },
    Dirt: {
        /*绑定到列表*/
        BindList: function (listId, dirtName, needBlock) {
            var obj = $C(listId);
            if (obj != undefined) {
                obj.length = 0;
                var objV = eval("DirtInfo." + dirtName);
                if (objV != undefined && objV != null) {
                    for (var i = 0; i < objV.length; i++) {
                        obj.options.add(new Option(objV[i][1], objV[i][0]));
                    }
                }
                if (needBlock) {
                    obj.options.add(new Option("Pilih", "0"));
                    obj.value = "";
                }
            }
        },
        /*获取值表示的意义*/
        GetName: function (dirtName, dValue) {
            var obj = eval("DirtInfo." + dirtName);
            if (obj != undefined && obj != null) {
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i][0] == dValue) {
                        return obj[i][1];
                    }
                }
            }
            return "";
        }
    },
    Cookie: function (name, value, options) {
        if (typeof value != 'undefined') { /* name and value given, set cookie*/
            options = options || {};
            if (value === null) {
                value = '';
                options.expires = -1;
            }
            var expires = '';
            if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
                var date;
                if (typeof options.expires == 'number') {
                    date = new Date();
                    date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
                } else {
                    date = options.expires;
                }
                expires = '; expires=' + date.toUTCString(); /* use expires attribute, max-age is not supported by IE */
            }
            var path = options.path ? '; path=' + options.path : '';
            var domain = options.domain ? '; domain=' + options.domain : '';
            var secure = options.secure ? '; secure' : '';
            document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
        } else { /* only name given, get cookie */
            var cookieValue = null;
            if (document.cookie && document.cookie != '') {
                var cookies = document.cookie.split(';');
                for (var i = 0; i < cookies.length; i++) {
                    var cookie = jQuery.trim(cookies[i]);
                    if (cookie.substring(0, name.length + 1) == (name + '=')) {
                        cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                        break;
                    }
                }
            }
            return cookieValue;
        }
    }
}

/*重新定义录入框校验规则*/
$.extend($.fn.validatebox.defaults.rules, {
    chinaMobile: {/*手机号码*/
        validator: function (value, param) {
            var reg = /^(6|14|15|17|18)\d{12}$/;
            var reglt = /^(\d{3}|\d{4})-\d{8}$/;
            /*var reg = /^\d{11,12}$/;*/
            if (value.indexOf('-') > 0) {
                return reglt.test(value);
            }
            else {
                return reg.test(value);
            }
        }, message: 'Nomor telepon salah'
    },
    chinaName: {/*中文名称*/
        validator: function (value, param) {
            //            var reg = /^[\u4e00-\u9fa5a-zA-Z0-9]{2,6}$/;
            var reg = /^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]{1,5}$/;
            //            var reg = /^[\u4e00-\u9fa5,a-zA-Z0-9]{2,5}$/;
            return reg.test(value);
        }, message: 'Angka tidak boleh dimulai dengan nama pena. Dan panjang nama panggilan harus antara 2-6'
    },
    realName: {/*真实姓名*/
        validator: function (value, param) {
            //            var reg = /^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]{1,5}$/;
            var reg = /^[\u4e00-\u9fa5,a-zA-Z0-9]{2,5}$/;
            return reg.test(value);
        }, message: 'Panjang nama aslinya adalah 2-5 karakter'
    },
    maxLength: {
        validator: function (value, param) {
            $.fn.validatebox.defaults.rules.maxLength.message = 'Hanya bisa kurang dari' + param + 'karakter';
            return value.length < param;
        }
    },
    isNumber: {
        validator: function (value, param) {
            var reg = /^(-|[0-9])(|\d{1,9})$/;
            return reg.test(value);
        }, message: 'Harus berupa angka'
    },
    isBankNumber: {
        validator: function (value, param) {
            var reg = /^([0-9]{16}|[0-9]{19})$/;
            return reg.test(value);
        }, message: 'Nomor akun bank salah'
    },
    isEmail: {
        validator: function (value, param) {
            var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
            return reg.test(value);
        }, message: 'Kesalahan format email'
    },

    isPosInt: {
        validator: function (value, param) {
            var reg = /^(\d{1,9})$/;
            if (reg.test(value) && value > 0) {
                return true;
            }
            else {
                return false;
            }
        }, message: 'Harus bilangan bulat positif yang lebih besar dari 0'
    },
    isPosIntTen: {
        validator: function (value, param) {
            var reg = /^(\d{1,9})$/;
            if (reg.test(value) && value > 10) {
                return true;
            }
            else {
                return false;
            }
        }, message: 'Harus bilangan bulat positif yang lebih besar dari 10'
    },
    isDate: {
        validator: function (value, param) {
            var reg = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$/;
            return reg.test(value);
        }, message: 'yyyy-MM-dd'
    },
    isIdCard: {
        validator: function (value, param) {
            var reg = /^(^\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
            return isCardID(value);
        }, message: 'Nomor ID salah'
    },
    isFloat: {
        validator: function (value, param) {
            var reg = /^(^\+?[1-9][0-9]*$)$|^(\d{1,9}\.\d{1,9})$/;
            return reg.test(value);
        }, message: 'Harus lebih besar dari nol'
    },
    isFloatMin0:
        {
            validator: function (value, param) {
                var reg = /^(^\d{1,9})$|^(\d{1,9}\.\d{1,9})$/;
                return reg.test(value);
            }, message: 'Harus lebih besar dari nol'
        },
    isPassWord: {
        validator: function (value, param) {
            var reg = /^[a-zA-Z0-9_]{5,15}$/;
            return reg.test(value);
        }, message: 'Format kata sandi salah'
    },
    isConfirmPassword: {
        validator: function (value, param) {
            return $(param[0]).val() == value;
        }, message: 'Kedua kata sandi yang dimasukkan berbeda'
    },
    phoneCheck: {
        validator: function (value, param) {
            var reg = /^(((\()?\d{2,4}(\))?[-(\s)*]){0,2})?(\d{8})$/;
            return reg.test(value);
        }, message: 'Nomor telepon yang dimasukkan salah'
    },
    isUserName: {
        validator: function (value, param) {
            var reg = /^[a-zA-Z0-9_]{3,15}$/;
            return reg.test(value);
        }, message: 'Format nama pengguna salah'
    },
    equalTo: {
        validator: function (value, param) {
            return $(param[0]).val() == value;
        },
        message: 'Bidang tidak cocok'
    }
});

/*空函数*/
function CreateGrid() { }
function CreateGridReload() { }

/*身份证校验正确性*/
var NumbCardCity = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外" };
function isCardID(sId) {
    var iSum = 0;
    var info = "";
    if (!/^\d{17}(\d|x)$/i.test(sId)) return false; /* "你输入的身份证长度或格式错误";  */
    sId = sId.replace(/x$/i, "a");
    if (NumbCardCity[parseInt(sId.substr(0, 2))] == null) return false; /*"你的身份证地区非法";*/
    sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-" + Number(sId.substr(12, 2));
    var d = new Date(sBirthday.replace(/-/g, "/"));
    if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) return false; /* "身份证上的出生日期非法";*/
    for (var i = 17; i >= 0; i--) iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
    if (iSum % 11 != 1) return false; /*"你输入的身份证号非法";*/
    return true;
}

function getSex(val) {
    if (parseInt(val.charAt(16) / 2) * 2 != val.charAt(16))
        return '1';
    else
        return '0';
}
function showBirthday(val) {
    var mm;
    if (18 == val.length) {/*18位身份证号码*/
        mm = val.charAt(6) + val.charAt(7) + val.charAt(8) + val.charAt(9) + '-' + val.charAt(10) + val.charAt(11) + '-' + val.charAt(12) + val.charAt(13);

    }
    return mm;
}


var DirtInfo = {
    TrueOrFalse: [[0, "Tidak"], [1, "Ya"]],
    EnumUserCommendStatus: [[0, "Baru"], [1, "Diproses"], [2, "Dilihat"]],
    AvailablesStatus: [[0, "Nonaktifkan"], [1, "Tersedia"]],
    SettleClass: [[0, "Tunai"], [1, "Potongan prabayar"]],
    EnumSexClass: [[0, "Tak terbatas"], [1, "Laki-laki"], [2, "Perempuan"]],
    EnumUserType: [[1, "Aplikasi seluler"], [2, "Ponsel wap"]],
    EnumPayClass: [[1, "Alipay"], [2, "Wechat wechat"], [3, "Kode pemindaian WeChat"], [100, "Ikat hadiah ponsel"]],
    EnumPayStatus: [[0, "Aplikasi baru"], [2, "Isi ulang gagal"], [3, "Berhasil"]],
    EnumMoneyClass: [[0, "Beli"], [1, "Berikan"]],
    EnumUserFrom: [[1, "Lainnya"], [2, "Weibo"], [3, "qq"], [4, "Wechat wechat"], [10, "Daftar di aplikasi"], [11, "Daftar di WAP"], [12, "Menghubungkan Weibo"], [13, "Menghubungkan QQ"], [14, "Menghubungkan WeChat"]],
    EnumSignType: [[0, "Tidak ditandatangani"], [1, "Dibagi menjadi"], [2, "Pembelian"], [3, "Terjamin"], [4, "Beli seluruh buku"], [9, "Terjamin"], [15, "Bayar"], [30, "Hadiah masuk"]],
    EnumLogType: [[0, "Masuk aplikasi"], [1, "Masuk WAP"]],
    EnumAuditStatus: [[-10, "Offline"], [-1, "Kegagalan audit"], [0, "Sunting"], [1, "Ajukan permohonan"], [2, "Disetujui"], [3, "Diterbitkan"]],
    EnumHandleStatus: [[-1, "Pemrosesan gagal"], [0, "Permohonan Baru"], [1, "Tertunda"], [2, "Berhasil diproses"]],
    EnumAuthorLevel: [[1, "Level 1"], [2, "Level 2"], [3, "Level 3"], [4, "Level 4"], [5, "Level 5"]],
    EnumChannelClass: [[0, "Nilai Khusus"], [1, "Tingkat Pertama"], [2, "Tingkat Kedua"], [3, "Level 3"], [4, "Level 4"], [5, "Level 5"], [6, "Level 6"], [7, "Level 7"], [8, "Level 8"], [9, "Level 9"], [1100, "Level ribuan"]],
    EnumVipChapter: [[0, "Publik"], [1, "VIP"]],
    EnumBookLeveType: [[1, "Level A"], [2, "Level B"], [3, "Level C"], [4, "Normal"], [5, "Level S"]],
    EnumBookLeveTypeL: [[1, "A"], [2, "B"], [3, "C"]],
    EnumAdmActClass: [[50, "Modifikasi Tingkat Kontrak"], [51, "Modifikasi Sampul"], [52, "Penambahan Saluran"], [53, "Modifikasi Saluran"], [54, "Penghapusan Saluran"], [55, "Penghapusan Bab"]],
    EnumSettlementType: [[0, "Belum diselesaikan"], [1, "diselesaikan"], [2, "penyelesaian gagal"]],
    EnumBookProcess: [[0, "serial"], [1, "end"]],
    EnumAuthStatus: [[1, "eksklusif"], [2, "non-eksklusif"]]
};

function dateToDate(date) {
    var sDate = new Date();
    if (typeof date == 'object'
        && typeof new Date().getMonth == "function"
    ) {
        sDate = date;
    }
    else if (typeof date == "string") {
        var arr = date.split('-')
        if (arr.length == 3) {
            sDate = new Date(arr[0] + '-' + arr[1] + '-' + arr[2]);
        }
    }

    return sDate;
}


function addMonth(date, num) {
    num = parseInt(num);
    var sDate = dateToDate(date);

    var sYear = sDate.getFullYear();
    var sMonth = sDate.getMonth() + 1;
    var sDay = sDate.getDate();

    var eYear = sYear;
    var eMonth = sMonth + num;
    var eDay = sDay;
    while (eMonth > 12) {
        eYear++;
        eMonth -= 12;
    }

    var eDate = new Date(eYear, eMonth - 1, eDay);

    while (eDate.getMonth() != eMonth - 1) {
        eDay--;
        eDate = new Date(eYear, eMonth - 1, eDay);
    }

    return eDate;
}

function checkAll() {
    if ($("#selAll").attr("checked")) {
        $("input[name='selBox']").each(function () {
            $(this).attr("checked", true);
        });
    }
    else {
        $("input[name='selBox']").each(function () {
            $(this).removeAttr("checked");
        });
    }
}

$(function () {
    initSubmitButton(3);
});

//停留时间
function initSubmitButton(wait) {
    $("input[type='submit']").each(function () {
        $(this).click(function () {
            if ($(this).attr("submited") == "1") {
                return false;
            }
            var oldVal = $(this).val();
            $(this).val("Memproses ... tunggu sebentar(" + wait + ")");
            $(this).attr("submited", "1");
            setTimeout('ButtonLimit("' + $(this).attr("id") + '",' + wait + ',"' + oldVal + '")', 1000);
        });
    });
}
function ButtonLimit(objId, wait, oldVal) {
    wait--;
    if (wait > 0) {
        $("#" + objId).val("Memproses ... tunggu sebentar(" + wait + ")");
        setTimeout('ButtonLimit("' + objId + '",' + wait + ',"' + oldVal + '");', 1000);
    }
    else {
        $("#" + objId).removeAttr("submited");
        $("#" + objId).val(oldVal);
    }
}