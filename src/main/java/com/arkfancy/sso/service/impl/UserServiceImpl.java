package com.arkfancy.sso.service.impl;

import com.arkfancy.sso.entity.User;
import com.arkfancy.sso.mapper.UserMapper;
import com.arkfancy.sso.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author arkfancy
 * @since 2019-09-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
