package com.tying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tying.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tying
 * @version 1.0
 */
@Mapper
@Repository
public interface IUserMapper extends BaseMapper<User> {

}
