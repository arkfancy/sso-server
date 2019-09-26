package com.arkfancy.sso.service.user.impl;

import com.arkfancy.sso.entity.user.User;
import com.arkfancy.sso.mapper.user.UserMapper;
import com.arkfancy.sso.service.user.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author arkfancy
 * @since 2019-09-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
