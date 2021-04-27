package com.infervision.config;

import com.infervision.model.AdminDto;
import com.infervision.model.JwtToken;

import com.infervision.service.AdminShiroService;
import com.infervision.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.infervision.constants.ExceptionCode.ERROR_CHECK_NAME_ERROR100013;
import static com.infervision.constants.ExceptionCode.ERROR_CHECK_NAME_ERROR100014;
import static com.infervision.util.ConstantUtil.JWT_SECRET;
import static com.infervision.util.JwtUtil.getTimeStamp;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/3/9 1:33
 * @Version 1.0
 */
@Service
public class CheckRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckRealm.class);

    @Autowired
    AdminShiroService AdminShiroService;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    /**
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @Author fruiqi
     * @Description 当需要检测用户权限的时候会调用此方法。
     * @Date 1:55 2019/3/9
     * @Param [principals]
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String adminName = JwtUtil.getUsername(principals.toString());
        AdminDto admin = AdminShiroService.selectAdminByAdminName(adminName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(admin.getAdminGrade().toString());
        // TODO: 2019/3/9 待需要添加权限信息
//        Set<String> permission = new HashSet<>(Arrays.asList(admin));
        simpleAuthorizationInfo.addStringPermission("admin");
        simpleAuthorizationInfo.addStringPermission("superadmin");
        return simpleAuthorizationInfo;
    }

    /**
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @Author fruiqi
     * @Description 默认使用此方法进行用户名正确与否校验，出错抛出异常
     * @Date 2:30 2019/3/9
     * @Param [token]
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String tokenContent = (String) jwtToken.getCredentials();
        String name = JwtUtil.getUsername(tokenContent);
        AdminDto adminDto = AdminShiroService.selectAdminByAdminName(name);
        if (adminDto == null) {
            throw new AuthorizationException(ERROR_CHECK_NAME_ERROR100013.getInfo());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("userName", name);
        map.put("userId", adminDto.getAdminId());
        map.put("timestamp", getTimeStamp(tokenContent));
        if (!JwtUtil.verify(tokenContent, map, JWT_SECRET)) {
            throw new AuthenticationException(ERROR_CHECK_NAME_ERROR100014.getInfo());
        }
        ;

        String admin = "admin";
        if (adminDto.getAdminGrade() == 1) {
            admin = "superAdmin";
        }

        if (adminDto.getAdminGrade() == 0) {
            admin = "admin" ;
        }


        return new SimpleAuthenticationInfo(tokenContent, tokenContent, admin);
    }
}
