package com.wrvpark.apiserver;

import com.wrvpark.apiserver.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiserverApplicationTests {

    @Autowired
    private CategoryService categoryService;


    @Test
    public void testCategory()
    {
       // System.out.println(categoryService.getAllCategories().size()+"------------");
    }

}
