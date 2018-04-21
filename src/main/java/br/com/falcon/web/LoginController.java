package br.com.falcon.web;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.falcon.domain.User;
import br.com.falcon.jwt.JwtAuthenticationRequest;
import br.com.falcon.jwt.JwtAuthenticationResponse;
import br.com.falcon.multitenancy.TenantContext;
import br.com.falcon.multitenancy.TenantNameFetcher;
import br.com.falcon.security.TokenUtils;

@RestController
public class LoginController {

    @Value("${api.token.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TenantNameFetcher tenantResolver;
    
    @Autowired
    private TokenUtils tokenUtils;

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response, Device device) throws AuthenticationException {
        try {
            tenantResolver.setUsername(authenticationRequest.getUsername());
            ExecutorService es = Executors.newSingleThreadExecutor();
            Future<User> utrFuture = es.submit(tenantResolver);
            User utr = utrFuture.get();
            es.shutdown();
            TenantContext.setCurrentTenant(utr.getTenant());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String token = tokenUtils.generateToken(user, device);
        return new ResponseEntity<>(new JwtAuthenticationResponse(token, user), HttpStatus.OK);
    }
}