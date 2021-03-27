package cn.yajienet.oauthjwt.service;

import cn.yajienet.oauthjwt.entity.User;

import java.util.Map;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 13:58
 */
public interface UserService {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByEmailOrUsernameOrPhone(String s);

    int selectCountByMap(Map<String,String> param);

    int insertUserRole(Integer uid,Integer rid);

    boolean register(User user);

}
