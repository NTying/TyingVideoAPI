package com.tying.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.Category;
import com.tying.domain.entity.Video;
import com.tying.domain.vo.CategoryVo;
import com.tying.domain.vo.VideoVo;
import com.tying.mapper.ICategoryMapper;
import com.tying.service.ICategoryService;
import com.tying.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2022-12-11 21:17:35
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<ICategoryMapper, Category> implements ICategoryService {

    @Resource
    private ICategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategoryList() {

        List<Category> categoryList = categoryMapper.selectList(null);
        List<CategoryVo> categoryVoList = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVoList);
    }

    @Override
    public ResponseResult getCategory(Long id) {
        Category category = getById(id);
        return ResponseResult.okResult(category);
    }
}

