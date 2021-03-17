package cn.yajienet.oauthjwt.dao;

import cn.yajienet.oauthjwt.entity.RolePermissionKey;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionDAO {
    int deleteByPrimaryKey(RolePermissionKey key);

    int insert(RolePermissionKey record);

    int insertSelective(RolePermissionKey record);
}