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
        bo.setPrimary(shoe.getPrimary());
        bo.setResourceState(shoe.getResourceState());
        bo.setDistanceMeters(shoe.getDistanceMeters());
        bo.setBrand(shoe.getBrand());
        bo.setType(shoe.getType());
        bo.setPurchaseDate(shoe.getPurchaseDate());
        bo.setRetired(shoe.isRetired());
        bo.setNotes(shoe.getNotes());
        return bo;
    }

    public Shoe toEntity(ShoeBo bo) {
        Shoe shoe = new Shoe();
        shoe.setId(bo.getId());
        shoe.setName(bo.getName());
        shoe.setPrimary(bo.getPrimary());
        shoe.setResourceState(bo.getResourceState());
        shoe.setDistanceMeters(bo.getDistanceMeters());
        shoe.setBrand(bo.getBrand());
        shoe.setType(bo.getType());
        shoe.setPurchaseDate(bo.getPurchaseDate());
        shoe.setRetired(bo.isRetired());
        shoe.setNotes(bo.getNotes());
        return shoe;
    }
}
