package cn.yajienet.oauthjwt.dao;

import cn.yajienet.oauthjwt.entity.UserGroupUserKey;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupUserDAO {
    int deleteByPrimaryKey(UserGroupUserKey key);

    int insert(UserGroupUserKey record);

    int insertSelective(UserGroupUserKey record);
}