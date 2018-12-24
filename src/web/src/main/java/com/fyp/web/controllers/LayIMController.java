package com.fyp.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/layim")
public class LayIMController {

    @GetMapping("test")
    public Object Test(){
        return "hello layim";
    }
}
