package cn.yajienet.oauthjwt.service.impl;

import cn.yajienet.oauthjwt.dao.UserDAO;
import cn.yajienet.oauthjwt.dao.UserRoleDAO;
import cn.yajienet.oauthjwt.entity.User;
import cn.yajienet.oauthjwt.entity.UserRoleKey;
import cn.yajienet.oauthjwt.service.UserService;
import cn.yajienet.oauthjwt.utils.RegExUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-08 0:16
 */
@Service
public class UserServiceImpl implements UserService {

    @Value(value = "${default.rid:2}")
    private Integer defaultRid;

    @Resource
    private UserDAO userDAO;
    @Resource
    private UserRoleDAO userRoleDAO;
    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userDAO.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userDAO.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userDAO.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userDAO.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userDAO.updateByPrimaryKey(record);
    }

    @Override
    public User selectByEmailOrUsernameOrPhone(String s) {
        User user;
        if(RegExUtil.isEmail(s)){
            user = userDAO.selectByEmail(s);
        }else if (RegExUtil.isPhone(s)){
            user = userDAO.selectByPhone(s);
        }else {
            user = userDAO.selectByUsername(s);
        }
        return user;
    }

    @Override
    public int selectCountByMap(Map<String, String> param) {
        return userDAO.selectCountByMap(param);
    }

    @Override
    public int insertUserRole(Integer uid, Integer rid) {
        return userRoleDAO.insert(new UserRoleKey(uid,rid));
    }

    @Transactional
    @Override
    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (insertSelective(user) > 0){
            return insertUserRole(user.getId(), defaultRid) > 0;
        }
        return false;
    }
}
