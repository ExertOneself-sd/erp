package com.erp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ErpBackApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private PetMapper petMapper;

    @Test
    public void savePet() {
        Pet pet = new Pet();
        pet.setPname("小狗");
        pet.setColor("黄色");
        pet.setAge(5);
        petMapper.insert(pet);
    }

    @Test
    public void updatePet() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setPname("小狗");
        pet.setColor("黄色");
        pet.setAge(6);
        petMapper.updateById(pet);
    }

    @Test
    public void getOne(){
        Pet pet = petMapper.selectById(1);
        System.out.println(pet);
    }

    @Test
    public void getAll(){
        List<Pet> pets = petMapper.selectList(null);
        System.out.println(pets);
    }

    @Test
    public void delpet(){
        petMapper.deleteById(1);
    }

}
