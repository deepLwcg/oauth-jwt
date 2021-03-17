package cn.yajienet.oauthjwt.service.impl;

import cn.yajienet.oauthjwt.dao.PermissionDAO;
import cn.yajienet.oauthjwt.entity.Permission;
import cn.yajienet.oauthjwt.service.PermissionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-08 0:40
 */
@Service
public class PermissionsServiceImpl implements PermissionsService {

    @Resource
    private PermissionDAO permissionDAO;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return permissionDAO.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Permission record) {
        return permissionDAO.insert(record);
    }

    @Override
    public int insertSelective(Permission record) {
        return permissionDAO.insertSelective(record);
    }

    @Override
    public Permission selectByPrimaryKey(Integer id) {
        return permissionDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Permission record) {
        return permissionDAO.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Permission record) {
        return permissionDAO.updateByPrimaryKey(record);
    }

    @Override
    public List<Permission> selectAll() {
        return permissionDAO.selectAll();
    }

    @Override
    public List<Permission> selectByRoleId(int roleId) {
        return permissionDAO.selectByRoleId(roleId);
    }
}
