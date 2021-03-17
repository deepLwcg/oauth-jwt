package cn.yajienet.oauthjwt.oauth;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 16:24
 */
@Data
@Builder
public class OAuthGrantedAuthority implements GrantedAuthority {

    private final String permission;

    private final String method;

    private final String isEnable;

    @Override
    public String getAuthority() {
        return permission;
    }

    @Override
    public String toString() {
        return "OAuthGrantedAuthority{" +
                "permission='" + permission + '\'' +
                ", method='" + method + '\'' +
                ", isEnable='" + isEnable + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OAuthGrantedAuthority that = (OAuthGrantedAuthority) o;

        if (permission != null ? !permission.equals(that.permission) : that.permission != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        return isEnable != null ? isEnable.equals(that.isEnable) : that.isEnable == null;
    }

    @Override
    public int hashCode() {
        int result = permission != null ? permission.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (isEnable != null ? isEnable.hashCode() : 0);
        return result;
    }
}
