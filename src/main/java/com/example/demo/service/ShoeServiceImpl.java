package com.example.demo.service;

import com.example.demo.mapper.ShoeMapper;
import com.example.demo.model.Shoe;
import com.example.demo.model.ShoeBo;
import com.example.demo.repository.ShoeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoeServiceImpl implements ShoeService {

    private final ShoeRepository shoeRepository;
    private final ShoeMapper shoeMapper;

    public ShoeServiceImpl(ShoeRepository shoeRepository, ShoeMapper shoeMapper) {
        this.shoeRepository = shoeRepository;
        this.shoeMapper = shoeMapper;
    }

    @Override
    public ShoeBo save(ShoeBo bo) {
        Shoe entity = shoeMapper.toEntity(bo);
        return shoeMapper.toBo(shoeRepository.save(entity));
    }

    @Override
    public ShoeBo update(Long id, ShoeBo bo) {
        Shoe existing = shoeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shoe not found"));
        existing.setName(bo.getName());
        existing.setBrand(bo.getBrand());
        existing.setType(bo.getType());
        existing.setPurchaseDate(bo.getPurchaseDate());
        existing.setRetired(bo.isRetired());
        existing.setTotalDistanceKm(bo.getTotalDistanceKm());
        existing.setNotes(bo.getNotes());
        return shoeMapper.toBo(shoeRepository.save(existing));
    }

    @Override
    public List<ShoeBo> findAll() {
        return shoeRepository.findAll().stream()
                .map(shoeMapper::toBo)
                .collect(Collectors.toList());
    }

    @Override
    public ShoeBo findById(Long id) {
        return shoeRepository.findById(id)
                .map(shoeMapper::toBo)
                .orElseThrow(() -> new RuntimeException("Shoe not found"));
    }

    @Override
    public void delete(Long id) {
        shoeRepository.deleteById(id);
    }
}
