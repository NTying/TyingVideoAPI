package com.tying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.Category;

/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2022-12-11 21:17:35
 */
public interface ICategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult getCategory(Long id);
}

