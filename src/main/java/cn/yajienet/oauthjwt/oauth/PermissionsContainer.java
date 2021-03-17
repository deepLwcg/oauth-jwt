package cn.yajienet.oauthjwt.oauth;

import cn.yajienet.oauthjwt.entity.Permission;
import cn.yajienet.oauthjwt.listener.SpringContextHolder;
import cn.yajienet.oauthjwt.service.PermissionsService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-08 0:31
 */
public class PermissionsContainer {

    private volatile static PermissionsContainer permissionsContainer;

    private PermissionsContainer(){}

    public static  PermissionsContainer getInstance(){
        if (permissionsContainer == null){
            synchronized (PermissionsContainer.class){
                if (permissionsContainer == null){
                    permissionsContainer = new PermissionsContainer();
                    permissionsContainer.init();
                }
            }
        }
        return permissionsContainer;
    }
    private PermissionsService permissionsService = SpringContextHolder.getBean(PermissionsService.class);
    //所有权限
    private final List<Permission> permissions = new ArrayList<>();
    //游客的权限
    private final List<Permission> guestPermissions = new ArrayList<>();
    //没有配置权限的URL
    private final Map<String,List<String>> notConfigPermissions = new HashMap<>();
    //没有配置权限的URL，默认是否放行
    private boolean noConfigPermission = false;

    private Map<String, Collection<ConfigAttribute>> configAttributes = new HashMap<>();;

    // 游客的角色ID
    private int guestRoleId = 3;


    //初始化 加载所有配置的权限
    private void init(){
        permissions.addAll(permissionsService.selectAll());
        loadConfigAttributes();
        guestPermissions.addAll(permissionsService.selectByRoleId(guestRoleId));
    }

    //加载所有权限
    private void loadConfigAttributes(){
        configAttributes.clear();
        for (Permission permission:permissions) {
            List<ConfigAttribute> configAttributeList = new LinkedList<>();
            ConfigAttribute name = new SecurityConfig(permission.getPermissionName());
            ConfigAttribute method = new SecurityConfig(permission.getMethod());
            ConfigAttribute isEnable = new SecurityConfig(permission.getIsEnable().toString());
            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。
            // 此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            configAttributeList.add(name);
            configAttributeList.add(method);
            configAttributeList.add(isEnable);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            configAttributes.put(permission.getUri(),configAttributeList);
        }
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> set = new HashSet<>();
        for(Map.Entry<String,  Collection<ConfigAttribute>> entry: configAttributes.entrySet())
        {
            Collection<ConfigAttribute> attrs = entry.getValue();
            if (attrs != null) {
                set.addAll(attrs);
            }
        }
        return set;
    }

    public Map<String, Collection<ConfigAttribute>> getConfigAttributes() {
        loadConfigAttributes();
        return configAttributes;
    }

    //刷新权限
    public void refreshPermissions(){
        synchronized (permissions){
            permissions.clear();
            permissions.addAll(permissionsService.selectAll());
            guestPermissions.clear();
            guestPermissions.addAll(permissionsService.selectByRoleId(guestRoleId));
            loadConfigAttributes();
        }
    }

    public boolean addNotConfigPermissions(HttpServletRequest request){
        String url = request.getRequestURI();
        String method = request.getMethod().toUpperCase();
        boolean result = false;
        synchronized (notConfigPermissions) {
            List<String> methods = notConfigPermissions.get(url);
            if (methods != null) {
                if (!methods.contains(method)) {
                    result = methods.add(method);
                }
            } else {
                List<String> methods_ = new ArrayList<>();
                methods_.add(method);
                notConfigPermissions.put(url, methods_);
                result = true;
            }
        }
        return result;
    }
    public void removeNotConfigPermissions(String url,String method){
        synchronized (notConfigPermissions){
            List<String> methods = notConfigPermissions.get(url);
            if (methods != null && methods.size() > 0){
                for (int i = (methods.size()-1); i >= 0; i-- ){
                    if (methods.get(i).equalsIgnoreCase(method)){
                        methods.remove(i);
                    }
                }
                if (methods.size() <= 0){
                    notConfigPermissions.remove(url);
                }
            }
        }
    }

    public boolean isNoConfigPermission() {
        return noConfigPermission;
    }
    public synchronized PermissionsContainer setNoConfigPermission(boolean noConfigPermission) {
        this.noConfigPermission = noConfigPermission;
        return this;
    }

    public int getGuestRoleId() {
        return guestRoleId;
    }

    public synchronized PermissionsContainer setGuestRoleId(int guestRoleId) {
        this.guestRoleId = guestRoleId;
        return this;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public List<Permission> getGuestPermissions() {
        return guestPermissions;
    }

    public Map<String, List<String>> getNotConfigPermissions() {
        return notConfigPermissions;
    }
}
