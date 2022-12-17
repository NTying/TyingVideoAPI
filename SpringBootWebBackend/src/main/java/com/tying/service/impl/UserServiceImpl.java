package com.tying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tying.constants.AppHttpCodeEnum;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.User;
import com.tying.domain.vo.UserBaseInfoVo;
import com.tying.domain.vo.UserFullInfoVo;
import com.tying.exception.SystemException;
import com.tying.mapper.IUserMapper;
import com.tying.service.IUserService;
import com.tying.utils.BeanCopyUtils;
import com.tying.utils.JsonRedisUtils;
import com.tying.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 如果继承了 ServiceImpl，指定了泛型，就不用使用 @AutoWired 手动注入 IUserMapper 实现类对象
 * 当然如果需要其他 Mapper 对象，就要手动注入
 *
 * @author Tying
 * @version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {

    @Resource
    private IUserMapper userMapper;

    @Resource
    private JsonRedisUtils<UserFullInfoVo> userFullInfoVoJsonRedisUtils;
    @Resource
    private JsonRedisUtils<UserBaseInfoVo> userBaseInfoVoJsonRedisUtils;

    @Override
    public ResponseResult baseUserInfo(Long id) {

        UserBaseInfoVo userBaseInfoVo = null;
        userBaseInfoVo = userBaseInfoVoJsonRedisUtils.getValue("userBaseInfoVo:" + id);
        if (userBaseInfoVo == null) {
            // 根据用户 id 查询用户信息
            User user = getById(id);
            // 封装成 UserInfoVo
            userBaseInfoVo = BeanCopyUtils.copyBean(user, UserBaseInfoVo.class);
            userBaseInfoVoJsonRedisUtils.setValue("userBaseInfoVo:" + id, userBaseInfoVo);
        }
        return ResponseResult.okResult(userBaseInfoVo);

    }

    @Override
    public ResponseResult baseUserInfoList(List<Long> ids) {

        List<UserBaseInfoVo> userBaseInfoVos = new ArrayList<>();

        // 从Redis 中取出要获取的基本用户信息
        List<String> keyList = ids.stream()
                .map(id -> {
                    return "userBaseInfoVo:" + id;
                })
                .collect(Collectors.toList());
        userBaseInfoVos = userBaseInfoVoJsonRedisUtils.getMultiVal(keyList);
        userBaseInfoVos.removeIf(Objects::isNull);

        // 如果Redis中没有全部所需的基本用户信息就从数据库中取
        if (userBaseInfoVos.size() < ids.size()) {
            List<User> users = userMapper.selectBatchIds(ids);
            HashMap<String, UserBaseInfoVo> map = new HashMap<>();

            List<UserBaseInfoVo> userInfoVoList = BeanCopyUtils.copyBeanList(users, UserBaseInfoVo.class);
            userBaseInfoVos.addAll(userInfoVoList);
            userBaseInfoVos = userBaseInfoVos.stream().distinct().collect(Collectors.toList());
            // 把完整的用户信息存入 redis， userId 作为 key
            for (UserBaseInfoVo userInfoVo : userBaseInfoVos) {
                map.put("userBaseInfoVo:" + userInfoVo.getId(), userInfoVo);
            }
            userBaseInfoVoJsonRedisUtils.setMultiVal(map);
        }
        return ResponseResult.okResult(userBaseInfoVos);
    }

    @Override
    public ResponseResult fullUserInfo() {

        // 获取当前用户的 id（解析 token）
        Long userId = SecurityUtils.getUserId();
        UserFullInfoVo userFullInfoVo = null;
        userFullInfoVo = userFullInfoVoJsonRedisUtils.getValue("userFullInfoVo:" + userId);
        if (userFullInfoVo == null) {
            // 根据用户 id 查询用户信息
            User user = getById(userId);
            // 封装成 UserInfoVo
            userFullInfoVo = BeanCopyUtils.copyBean(user,UserFullInfoVo.class);
            userFullInfoVoJsonRedisUtils.setValue("userFullInfoVo:" + userId, userFullInfoVo);
        }
        return ResponseResult.okResult(userFullInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        // 获取当前用户的 id（解析 token）
        Long userId = SecurityUtils.getUserId();
        // 更新条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", userId);
        userUpdateWrapper.set("avatar", user.getAvatar());
        userUpdateWrapper.set("email", user.getEmail());
        userUpdateWrapper.set("nick_name", user.getNickName());
        userUpdateWrapper.set("sex", user.getSex());
        update(userUpdateWrapper);
        return ResponseResult.okResult();
    }

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {

        // 对数据进行非空判断
        /*        if (!StringUtils.hasText(user.getUserName())) {
                    throw new SystemException(AppHttpCodeEnum.REQUIRED_USERNAME);
                }
                if (!StringUtils.hasText(user.getPassword())) {
                    throw new SystemException(AppHttpCodeEnum.REQUIRED_PASSWORD);
                }
                if (!StringUtils.hasText(user.getNickName())) {
                    throw new SystemException(AppHttpCodeEnum.REQUIRED_NICKNAME);
                }
                if (!StringUtils.hasText(user.getEmail())) {
                    throw new SystemException(AppHttpCodeEnum.REQUIRED_EMAIL);
                }*/
        if (!(StringUtils.hasText(user.getUserName())
                || StringUtils.hasText(user.getEmail())
                || StringUtils.hasText(user.getPhoneNumber()))) {
            throw new SystemException(AppHttpCodeEnum.REQUIRED_USERNAME);
        }

        // 对数据进行是否存在的判断
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (phoneNumberExist(user.getPhoneNumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
        }

        // 对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        // 存入数据库
        save(user);

        return ResponseResult.okResult();
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }

    private boolean phoneNumberExist(String phoneNumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhoneNumber, phoneNumber);
        return count(queryWrapper) > 0;
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }
}
