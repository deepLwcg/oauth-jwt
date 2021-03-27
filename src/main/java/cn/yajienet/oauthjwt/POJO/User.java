package cn.yajienet.oauthjwt.POJO;

import cn.yajienet.oauthjwt.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 14:03
 */
public class User extends org.springframework.security.core.userdetails.User implements Serializable {

    private List<Role> roles;

    private Integer id;

    public User(Integer id, String username, String password, List<Role> roles ,Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.roles = roles;
    }

    public User(Integer id, String username, String password,List<Role> roles , boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.roles = roles;
    }


    public User(String username, String password,List<Role> roles , boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Integer id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
