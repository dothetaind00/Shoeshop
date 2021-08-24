package com.project.controller.admin;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.service.OrdersService;
import com.project.service.ProductService;
import com.project.service.SizeService;
import com.project.service.UserService;

@Controller
@RequestMapping("/admin")
public class StatisticController {
    @Autowired
    private OrdersService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private SizeService sizeService;


    @GetMapping("")
    public String statisttical(Model model) {


        List<Object[]> list = orderService.listByMonth();
        double total = 0;
        int amount = 0;
        for(int i = 0;i < list.size();i++) {
            BigDecimal number = (BigDecimal) list.get(i)[1];
            amount += number.intValue();
            total += (double) list.get(i)[2];
        }

        model.addAttribute("total",total);
        model.addAttribute("amount",amount);
        model.addAttribute("order",orderService.countOrder());
        model.addAttribute("user",userService.countUser());
        model.addAttribute("product",sizeService.sumProduct());
        model.addAttribute("list",list);
        return "admin/statistics";
    }

    @PostMapping("/filter")
    public String findByMonth(@RequestParam("firstdate") String firstDate, @RequestParam("lastdate") String lastdate, Model model) {
        List<Object[]> list = orderService.listByMonth();
        List<Object[]> listMonth = orderService.findByMonth(firstDate,lastdate);
        double total = 0;
        for(int i = 0;i < list.size();i++) {
            total += (double) list.get(i)[2];
        }
        model.addAttribute("firstdate",firstDate);
        model.addAttribute("lastdate",lastdate);
        model.addAttribute("total",total);
        model.addAttribute("order",orderService.countOrder());
        model.addAttribute("user",userService.countUser());
        model.addAttribute("product",sizeService.sumProduct());
        model.addAttribute("list",listMonth);
        return "admin/statistics";
    }
}
