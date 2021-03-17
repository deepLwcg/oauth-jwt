package cn.yajienet.oauthjwt.service;

import cn.yajienet.oauthjwt.entity.Permission;

import java.util.List;

public interface PermissionsService {

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> selectAll();

    List<Permission> selectByRoleId(int roleId);
}
