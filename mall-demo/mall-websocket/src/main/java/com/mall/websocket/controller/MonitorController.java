package com.mall.websocket.controller;

import cn.hutool.system.SystemUtil;
import com.mall.websocket.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class MonitorController {
    @Autowired
    private MonitorService monitorService;
    @GetMapping("/index")
    public String socket() {
//        ModelAndView mav=new ModelAndView("/webSocket");
//        mav.addObject("userId", userId);
        return "webSocket";
    }


    @GetMapping("monitor")
    public Object monitor(){
        Map<String, Object> servers = monitorService.getServers();
//        SystemUtil
        System.out.println("servers:"+servers);
        return servers;

    }
}
