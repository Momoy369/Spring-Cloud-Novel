package com.java2nb.novel.service.impl;

import com.java2nb.novel.entity.OrderPay;
import com.java2nb.novel.mapper.OrderPayDynamicSqlSupport;
import com.java2nb.novel.mapper.OrderPayMapper;
import com.java2nb.novel.service.OrderService;
import com.java2nb.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.select.SelectDSL.select;

/**
 * @author 11797
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderPayMapper orderPayMapper;

    private final UserService userService;


    @SneakyThrows
    @Override
    public Long createPayOrder(Byte payChannel, Integer payAmount, Long userId) {
        Date currentDate = new Date();
        Long outTradeNo = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(currentDate)+new Random().nextInt(10));
        OrderPay orderPay = new OrderPay();
        orderPay.setOutTradeNo(outTradeNo);
        orderPay.setPayChannel(payChannel);
        orderPay.setTotalAmount(payAmount);
        orderPay.setUserId(userId);
        orderPay.setCreateTime(currentDate);
        orderPay.setUpdateTime(currentDate);
        orderPayMapper.insertSelective(orderPay);
        return outTradeNo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePayOrder(Long outTradeNo, String tradeNo, String tradeStatus) {
        SelectStatementProvider selectStatement = select(OrderPayDynamicSqlSupport.id,OrderPayDynamicSqlSupport.payStatus,OrderPayDynamicSqlSupport.totalAmount,OrderPayDynamicSqlSupport.userId)
                .from(OrderPayDynamicSqlSupport.orderPay)
                .where(OrderPayDynamicSqlSupport.outTradeNo, isEqualTo(outTradeNo))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        OrderPay orderPay = orderPayMapper.selectMany(selectStatement).get(0);

        if(orderPay.getPayStatus()!=1) {
            //Pesanan ini belum diproses

            if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
                //pembayaran berhasil
                //1.Perbarui status pesanan agar berhasil
                orderPay.setPayStatus((byte) 1);
                orderPay.setUpdateTime(new Date());
                orderPayMapper.updateByPrimaryKeySelective(orderPay);

                //2.Tingkatkan saldo pengguna
                userService.addAmount(orderPay.getUserId(),orderPay.getTotalAmount()*100);






            }
        }



    }
}
