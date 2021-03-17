package cn.yajienet.oauthjwt.dao;

import cn.yajienet.oauthjwt.entity.PermissionGroupPermissionKey;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionGroupPermissionDAO {
    int deleteByPrimaryKey(PermissionGroupPermissionKey key);

    int insert(PermissionGroupPermissionKey record);

    int insertSelective(PermissionGroupPermissionKey record);
}