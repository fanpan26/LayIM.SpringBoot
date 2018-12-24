package com.fyp.web.controllers;

import com.fyp.entity.result.JsonResult;

import com.fyp.service.impl.DefaultLayIMService;
import com.fyp.service.intf.LayIMService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/layim")
public class LayIMController {
    private  LayIMService layIMService;

    @GetMapping("init")
    public JsonResult InitData(){
        layIMService = new DefaultLayIMService();
        return layIMService.GetInitResult(Long.valueOf(203328));
    }
}
