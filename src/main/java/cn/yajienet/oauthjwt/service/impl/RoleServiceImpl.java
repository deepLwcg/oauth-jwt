package cn.yajienet.oauthjwt.service.impl;

import cn.yajienet.oauthjwt.dao.RoleDAO;
import cn.yajienet.oauthjwt.entity.Role;
import cn.yajienet.oauthjwt.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-03-27 23:52
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDAO roleDAO;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return roleDAO.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Role record) {
        return roleDAO.insert(record);
    }

    @Override
    public int insertSelective(Role record) {
        return roleDAO.insertSelective(record);
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return roleDAO.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return roleDAO.updateByPrimaryKey(record);
    }

    @Override
    public List<Role> selectAll() {
        return roleDAO.selectAll();
    }

    @Override
    public List<Role> selectByUserId(Integer uid) {
        return roleDAO.selectByUserId(uid);
    }
}
