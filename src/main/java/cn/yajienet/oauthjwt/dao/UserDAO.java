package cn.yajienet.oauthjwt.dao;

import cn.yajienet.oauthjwt.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByUsername(String username);

    User selectByEmail(String email);

    User selectByPhone(String phone);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int selectCountByMap(Map<String,String> param);
}