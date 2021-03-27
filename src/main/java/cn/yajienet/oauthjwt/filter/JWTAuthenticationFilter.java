package cn.yajienet.oauthjwt.filter;

import cn.yajienet.oauthjwt.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description JWT验证器
 * @Author WangChenguang
 * @Date 2021-02-08 1:57
 */
@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Resource(name = "userSecurityServiceImpl")
    private UserDetailsService userDetailsService;
    @Resource
    private JwtUtil jwtUtils;
    @Value("${jwt.header:Authorization}")
    private String header = "Authorization";
    @Value("${jwt.token.header:Bearer }")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(header);
        if (authHeader!=null && authHeader.startsWith(tokenHeader)){
            String authToken = authHeader.substring(tokenHeader.length());
            String username = jwtUtils.getUsernameFromToken(authToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtils.validateToken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else {
                    jwtUtils.del(authToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
