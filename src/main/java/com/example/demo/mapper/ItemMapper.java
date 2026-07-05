package com.example.demo.mapper;

import com.example.demo.model.Item;
import com.example.demo.model.ItemBo;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemBo toBo(Item item) {
        return new ItemBo(item.getId(), item.getName(), item.getDescription(), item.isDone());
    }

    public Item toEntity(ItemBo itemBo) {
        Item item = new Item();
        item.setId(itemBo.getId());
        item.setName(itemBo.getName());
        item.setDescription(itemBo.getDescription());
        item.setDone(itemBo.isDone());
        return item;
    }
}
