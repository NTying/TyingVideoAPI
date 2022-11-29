package com.tying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tying.domain.Menu;

import java.util.List;

/**
 * 因为在启动类中添加了 MyBatisPlus 提供的 @MapperScan 注解，所以不用为这些 Mapper 类添加 @Mapper 注解
 * @author Tying
 * @version 1.0
 */
public interface IMenuMapper extends BaseMapper<Menu> {
    /**
     * 通过用户 Id 查询该用户拥有的权限
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Long id);
}
