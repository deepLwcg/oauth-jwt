package cn.yajienet.oauthjwt.service.impl;

import cn.yajienet.oauthjwt.dao.UserDAO;
import cn.yajienet.oauthjwt.entity.User;
import cn.yajienet.oauthjwt.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-08 0:16
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;


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
}
