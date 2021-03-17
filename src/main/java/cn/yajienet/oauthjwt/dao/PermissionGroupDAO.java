package cn.yajienet.oauthjwt.dao;

import cn.yajienet.oauthjwt.entity.PermissionGroup;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionGroupDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(PermissionGroup record);

    int insertSelective(PermissionGroup record);

    PermissionGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PermissionGroup record);

    int updateByPrimaryKey(PermissionGroup record);
}