package com.tying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tying.domain.entity.Role;

import java.util.List;

/**
 * @author Tying
 * @version 1.0
 */
public interface IRoleMapper extends BaseMapper<Role> {

    List<Role> findAllRoles();

    IPage<Role> findRolesByPage(Page<Role> page);
}
