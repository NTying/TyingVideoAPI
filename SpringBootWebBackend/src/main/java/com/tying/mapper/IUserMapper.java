package com.tying.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tying.domain.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author Tying
 * @version 1.0
 */
public interface IUserMapper extends BaseMapper<User> {

    User findMyUserByWrapper(@Param(Constants.WRAPPER)Wrapper<User> wrapper);

}
