package com.tying.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.Category;
import com.tying.service.ICategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Category)表控制层
 *
 * @author makejava
 * @since 2022-12-11 21:17:34
 */
@RestController
@RequestMapping("/app/video")
public class CategoryController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ICategoryService categoryService;

    @GetMapping("/category")
    public ResponseResult getCategory(Long id) {

        return categoryService.getCategory(id);
    }

    @GetMapping("/category/list")
    public ResponseResult getCategoryList() {

        return categoryService.getCategoryList();
    }
}

