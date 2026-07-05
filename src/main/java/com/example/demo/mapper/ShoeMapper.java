package com.example.demo.mapper;

import com.example.demo.model.Shoe;
import com.example.demo.model.ShoeBo;
import org.springframework.stereotype.Component;

@Component
public class ShoeMapper {

    public ShoeBo toBo(Shoe shoe) {
        ShoeBo bo = new ShoeBo();
        bo.setId(shoe.getId());
        bo.setName(shoe.getName());
        bo.setBrand(shoe.getBrand());
        bo.setType(shoe.getType());
        bo.setPurchaseDate(shoe.getPurchaseDate());
        bo.setRetired(shoe.isRetired());
        bo.setTotalDistanceKm(shoe.getTotalDistanceKm());
        bo.setNotes(shoe.getNotes());
        return bo;
    }

    public Shoe toEntity(ShoeBo bo) {
        Shoe shoe = new Shoe();
        shoe.setId(bo.getId());
        shoe.setName(bo.getName());
        shoe.setBrand(bo.getBrand());
        shoe.setType(bo.getType());
        shoe.setPurchaseDate(bo.getPurchaseDate());
        shoe.setRetired(bo.isRetired());
        shoe.setTotalDistanceKm(bo.getTotalDistanceKm());
        shoe.setNotes(bo.getNotes());
        return shoe;
    }
}
