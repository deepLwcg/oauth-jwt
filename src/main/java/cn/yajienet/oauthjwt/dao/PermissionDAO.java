package cn.yajienet.oauthjwt.dao;

import cn.yajienet.oauthjwt.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> selectAll();

    List<Permission> selectByRoleId(int roleId);
}