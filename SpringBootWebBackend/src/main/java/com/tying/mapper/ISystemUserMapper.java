package com.tying.mapper;

import com.tying.domain.SystemUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Tying
 * @version 1.0
 */
@Mapper
public interface ISystemUserMapper {

    /**
     * 登录业务 DAO 层
     * @param user
     * @return
     */
    SystemUser login(SystemUser user);
}
