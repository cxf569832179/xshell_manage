package me.ffs.www.shiro;

import java.util.List;
import java.util.Set;

import me.ffs.www.dao.mapper.ManageUserMapper;
import me.ffs.www.model.ManageUser;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.gserv.serv.commons.util.JsonMapper;

import me.ffs.www.shiro.token.MyToken;
import serv.utils.JsonUtils;

import javax.annotation.Resource;

/**
 *
 */
public class MyRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Resource
    private ManageUserMapper manageUserMapper;// 用户


    {// 告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
     // 设置用于匹配密码的CredentialsMatcher
	HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
	hashMatcher.setHashAlgorithmName(Sha1Hash.ALGORITHM_NAME);
	hashMatcher.setStoredCredentialsHexEncoded(true);// 存储散列后的密码是否为16进制
	hashMatcher.setHashIterations(1024);// 加密次数
	this.setCredentialsMatcher(hashMatcher);
    }

    // 定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {// 登录后的每次操作都会执行(当然不包括游客,匿名访问)
	// null usernames are invalid
	if (principals == null) {
	    throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
	}
	ManageUser admin = (ManageUser) getAvailablePrincipal(principals);
	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

//	info.setStringPermissions(admin.getPerms());

//	logger.info("#####此管理员拥有的权限为:{}", JsonMapper.toJsonString(admin.getPerms()));
//	logger.info("#####此管理员拥有的菜单为:{}", JsonMapper.toJsonString(admin.getNavs()));

	return info;
    }

    // 定义如何获取用户信息的业务逻辑，给shiro做登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	MyToken upToken = (MyToken) token;
	String username = upToken.getUsername();
	// Null username is invalid
	if (username == null) {
	    throw new AccountException("用户名为空......");
	}

	ManageUser admin = null;// 定义管理员
	SimpleAuthenticationInfo info = null;

	switch (upToken.getType()) {// 根据不同的条件查找管理员
	case PASSWORD:// 账号登录
	    admin = manageUserMapper.selectByPrimaryKey(username);
		logger.info("账号登录--:{}", JsonMapper.toJsonString(admin));
	    setAdmin(admin);
	    info = new SimpleAuthenticationInfo(admin, admin.getPass(), getName());
	    if (admin.getSalt() != null) {
		info.setCredentialsSalt(ByteSource.Util.bytes(admin.getSalt()));
	    }
	    break;
	default:
	    break;
	}
	// ---------------------------------分割线--------------------------------
	// 查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
	// SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
	return info;
    }

    private void setAdmin(ManageUser admin) {
	if (admin == null) {
	    throw new UnknownAccountException("不存在这个用户......");
	}
	/*Set<String> perms = permissionsService.findPermsbyId(admin.getId());
	List<BackNavigation> nav = navigationService.getNav(perms);
	admin.setPerms(perms);
	admin.setNavs(nav);*/
    }

}