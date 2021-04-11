package com.java2nb.test.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.ApiOperation;


import com.java2nb.test.domain.OrderDO;
import com.java2nb.test.service.OrderService;
import com.java2nb.common.utils.PageBean;
import com.java2nb.common.utils.Query;
import com.java2nb.common.utils.R;

/**
 * 付呗-订单信息表
 *
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-25 11:57:16
 */

@Controller
@RequestMapping("/test/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping()
    @RequiresPermissions("test:order:order")
    String Order() {
        return "test/order/order";
    }

    @ApiOperation(value = "Dapatkan daftar tabel informasi pesanan berbayar", notes = "Dapatkan daftar tabel informasi pesanan berbayar")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("test:order:order")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<OrderDO> orderList = orderService.list(query);
        int total = orderService.count(query);
        PageBean pageBean = new PageBean(orderList, total);
        return R.ok().put("data", pageBean);
    }

    @ApiOperation(value = "Halaman formulir informasi pesanan pembayaran yang baru ditambahkan", notes = "Halaman formulir informasi pesanan pembayaran yang baru ditambahkan")
    @GetMapping("/add")
    @RequiresPermissions("test:order:add")
    String add() {
        return "test/order/add";
    }

    @ApiOperation(value = "Ubah halaman formulir informasi pesanan pembayaran", notes = "Ubah halaman formulir informasi pesanan pembayaran")
    @GetMapping("/edit/{id}")
    @RequiresPermissions("test:order:edit")
    String edit(@PathVariable("id") Long id, Model model) {
            OrderDO order = orderService.get(id);
        model.addAttribute("order", order);
        return "test/order/edit";
    }

    @ApiOperation(value = "Periksa halaman Lembar Informasi Pay-Bai-Order", notes = "Periksa halaman Lembar Informasi Pay-Bai-Order")
    @GetMapping("/detail/{id}")
    @RequiresPermissions("test:order:detail")
    String detail(@PathVariable("id") Long id, Model model) {
			OrderDO order = orderService.get(id);
        model.addAttribute("order", order);
        return "test/order/detail";
    }

    /**
     * 保存
     */
    @ApiOperation(value = "Formulir informasi pesanan pembayaran baru", notes = "Formulir informasi pesanan pembayaran baru")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("test:order:add")
    public R save( OrderDO order) {
        if (orderService.save(order) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "Ubah formulir informasi pesanan pembayaran", notes = "Ubah formulir informasi pesanan pembayaran")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("test:order:edit")
    public R update( OrderDO order) {
            orderService.update(order);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "Hapus formulir informasi pesanan pembayaran", notes = "Hapus formulir informasi pesanan pembayaran")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("test:order:remove")
    public R remove( Long id) {
        if (orderService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "Hapus batch formulir informasi pesanan pembayaran", notes = "Hapus batch formulir informasi pesanan pembayaran")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("test:order:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
            orderService.batchRemove(ids);
        return R.ok();
    }

}
