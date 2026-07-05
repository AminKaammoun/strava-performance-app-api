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

    // Only touches the manual/local-only fields. name/primary/resourceState/
    // distanceMeters come from Strava and are overwritten on the next sync
    // regardless, so letting a manual edit touch them would just be
    // overwritten and confusing in the meantime.
    @Override
    public ShoeBo update(String id, ShoeBo bo) {
        Shoe existing = shoeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shoe not found"));
        existing.setBrand(bo.getBrand());
        existing.setType(bo.getType());
        existing.setPurchaseDate(bo.getPurchaseDate());
        existing.setRetired(bo.isRetired());
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
    public ShoeBo findById(String id) {
        return shoeRepository.findById(id)
                .map(shoeMapper::toBo)
                .orElseThrow(() -> new RuntimeException("Shoe not found"));
    }

    @Override
    public void delete(String id) {
        shoeRepository.deleteById(id);
    }
}
