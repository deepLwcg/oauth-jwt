package cn.yajienet.oauthjwt.dao;

import cn.yajienet.oauthjwt.entity.UserRoleKey;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDAO {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);
}