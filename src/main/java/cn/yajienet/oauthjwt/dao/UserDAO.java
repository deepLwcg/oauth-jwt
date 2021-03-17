package cn.yajienet.oauthjwt.dao;

import cn.yajienet.oauthjwt.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}