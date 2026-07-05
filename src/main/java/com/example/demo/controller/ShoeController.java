package com.example.demo.controller;

import com.example.demo.model.ShoeBo;
import com.example.demo.service.ShoeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shoe")
public class ShoeController extends GenericRestController<ShoeBo, Long, ShoeService> {

    public ShoeController(ShoeService shoeService) {
        super(shoeService);
    }

    @Override
    protected ShoeService getService() {
        return service;
    }
}
