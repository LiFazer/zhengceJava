package cn.gov.hebei.ylbzj.config;

import cn.gov.hebei.ylbzj.service.impl.UserDetailsServiceImpl;
import cn.gov.hebei.ylbzj.utils.RSAEncodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 获取用户输入的用户名和密码
        String inputName = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();
        UserDetails userDetails = null;
        String decrypt = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(inputName, inputPassword);
            decrypt = RSAEncodeUtil.decrypt(inputPassword, RSAEncodeUtil.getPrivateKey(RSAEncodeUtil.privateKey));
            //对比密码
            boolean matches = bCryptPasswordEncoder.matches(decrypt, userDetails.getPassword());
            if (!matches) {
                throw new UsernameNotFoundException("用户名或密码不匹配");
            }
        } catch (Exception e) {
            log.error("登录异常e=", e);
            throw new UsernameNotFoundException("用户名或密码不匹配");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, decrypt, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 这里不要忘记，和UsernamePasswordAuthenticationToken比较
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
        //return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
