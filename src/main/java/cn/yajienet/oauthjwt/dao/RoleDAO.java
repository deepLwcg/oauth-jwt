package cn.yajienet.oauthjwt.dao;

import cn.yajienet.oauthjwt.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}