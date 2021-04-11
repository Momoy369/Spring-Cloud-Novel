package com.java2nb.novel.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.java2nb.novel.core.bean.UserDetails;
import com.java2nb.novel.core.config.AlipayProperties;
import com.java2nb.novel.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 11797
 */
@Controller
@RequestMapping("pay")
@RequiredArgsConstructor
@Slf4j
public class PayController extends BaseController {


    private final AlipayProperties alipayConfig;

    private final OrderService orderService;


    /**
     * Bayar dengan Ali-Pay
     */
    @SneakyThrows
    @PostMapping("aliPay")
    public void aliPay(Integer payAmount,HttpServletRequest request,HttpServletResponse httpResponse) {

        UserDetails userDetails = getUserDetails(request);
        if (userDetails == null) {
            //Belum login, lompat ke halaman login
            httpResponse.sendRedirect("/user/login.html?originUrl=/pay/aliPay?payAmount="+payAmount);
            return;
        }else {
            //Buat pesanan isi ulang
            Long outTradeNo = orderService.createPayOrder((byte)1,payAmount,userDetails.getId());

            //Dapatkan AlipayClient yang telah diinisialisasi
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getMerchantPrivateKey(), "json", alipayConfig.getCharset(), alipayConfig.getPublicKey(), alipayConfig.getSignType());
            //Buat permintaan yang sesuai dengan API
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
            //Setel lompatan kembali dan alamat notifikasi di parameter publik
            alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
            //Isi parameter bisnis
            alipayRequest.setBizContent("{" +
                    "    \"out_trade_no\":\"" + outTradeNo + "\"," +
                    "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                    "    \"total_amount\":" + payAmount + "," +
                    "    \"subject\":\"Novel Loka\"" +
                    "  }");
            //Panggil SDK untuk menghasilkan formulir
            String form = alipayClient.pageExecute(alipayRequest).getBody();

            httpResponse.setContentType("text/html;charset=utf-8");
            //Keluarkan format html lengkap langsung ke halaman
            httpResponse.getWriter().write(form);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        }




    }

    /**
     * Pemberitahuan pembayaran Alipay
     * */
    @SneakyThrows
    @RequestMapping("aliPay/notify")
    public void aliPayNotify(HttpServletRequest request,HttpServletResponse httpResponse){


        PrintWriter out = httpResponse.getWriter();

        //Dapatkan umpan balik dari Alipay POST
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        //Panggil SDK untuk memverifikasi tanda tangan
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());

        //——Harap tulis program Anda di sini (kode berikut hanya untuk referensi) ——

	/* Dalam proses verifikasi yang sebenarnya, merchant disarankan untuk menambahkan verifikasi berikut:
	1、Perlu dilakukan verifikasi apakah out_trade_no pada data notifikasi adalah nomor pesanan yang dibuat di sistem merchant,
	2、Tentukan apakah total_amount benar-benar jumlah pesanan yang sebenarnya (yaitu, jumlah saat pesanan pedagang dibuat),
	3、Verifikasi bahwa seller_id (atau seller_email) di notifikasi adalah operator yang sesuai dari out_trade_no struk (terkadang, merchant mungkin memiliki beberapa seller_id / seller_email)
	4、Verifikasi bahwa app_id adalah pedagang itu sendiri.
	*/
        if(signVerified) {
            //Berhasil diverifikasi
            //Nomor pesanan pedagang
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //Nomor transaksi Alipay
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //status perdagangan
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //Perbarui status pesanan
            orderService.updatePayOrder(Long.parseLong(outTradeNo), tradeNo, tradeStatus);


            out.println("success");

        }else {//verifikasi gagal
            out.println("fail");

            //Untuk debugging, tulis fungsi teks untuk merekam apakah program berjalan normal
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }



    }






}
