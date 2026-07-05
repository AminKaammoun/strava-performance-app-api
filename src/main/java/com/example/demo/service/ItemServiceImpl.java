package com.example.demo.service;

import com.example.demo.mapper.ItemMapper;
import com.example.demo.model.Item;
import com.example.demo.model.ItemBo;
import com.example.demo.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemBo save(ItemBo itemBo) {
        Item entity = itemMapper.toEntity(itemBo);
        return itemMapper.toBo(itemRepository.save(entity));
    }

    @Override
    public ItemBo update(Long id, ItemBo itemBo) {
        Item existing = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        existing.setName(itemBo.getName());
        existing.setDescription(itemBo.getDescription());
        existing.setDone(itemBo.isDone());
        return itemMapper.toBo(itemRepository.save(existing));
    }

    @Override
    public List<ItemBo> findAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toBo)
                .collect(Collectors.toList());
    }

    @Override
    public ItemBo findById(Long id) {
        return itemRepository.findById(id)
                .map(itemMapper::toBo)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
