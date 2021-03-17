package cn.yajienet.oauthjwt.POJO;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 14:03
 */
public class User extends org.springframework.security.core.userdetails.User implements Serializable {

    private Integer id;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer id) {
        super(username, password, authorities);
        this.id = id;
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Integer id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
