package cn.yajienet.oauthjwt.dao;

import cn.yajienet.oauthjwt.entity.UserGroup;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(UserGroup record);

    int insertSelective(UserGroup record);

    UserGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserGroup record);

    int updateByPrimaryKey(UserGroup record);
}