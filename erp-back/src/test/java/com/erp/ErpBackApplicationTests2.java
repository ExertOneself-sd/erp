package com.erp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ErpBackApplicationTests2 {
    @Test
    void contextLoads() {}

    @Autowired
    private PetService petService;

    @Test
    public void savaPetService() {
        Pet pet = new Pet();
        pet.setPname("小貂");
        pet.setAge(12);
        pet.setColor("黄色");

        pet.setId(4);
        petService.saveOrUpdate(pet);
    }

    @Test
    public void updatePetService() {
        Pet pet = new Pet();
        pet.setPname("小猴");
        pet.setAge(12);
        pet.setColor("金黄色");
        pet.setId(2);
        petService.updateById(pet);
    }

    @Test
    public void getPetService() {
       Pet pet=petService.getById(2);
       System.out.println(pet);
    }

    @Test
    public void getListPetService() {
        QueryWrapper<Pet> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("color","黄色");
        queryWrapper.gt("id",4);
        List<Pet> list = petService.list(queryWrapper);
        for (Pet pet : list) {
            System.out.println(pet);
        }
    }

    @Test
    public void deletePetService() {
        petService.removeById(2);
    }
}
