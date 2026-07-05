package com.example.demo.controller;

import com.example.demo.model.ItemBo;
import com.example.demo.service.ItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemController extends GenericRestController<ItemBo, Long, ItemService> {

    public ItemController(ItemService itemService) {
        super(itemService);
    }

    @Override
    protected ItemService getService() {
        return service;
    }
}
