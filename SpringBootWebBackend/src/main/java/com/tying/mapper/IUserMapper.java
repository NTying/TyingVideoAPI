package com.tying.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tying.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tying
 * @version 1.0
 */
public interface IUserMapper extends BaseMapper<User> {

    User findMyUserByWrapper(@Param(Constants.WRAPPER)Wrapper<User> wrapper);

}
