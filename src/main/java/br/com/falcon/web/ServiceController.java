package br.com.falcon.web;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.falcon.domain.User;
import br.com.falcon.multitenancy.TenantContext;
import br.com.falcon.multitenancy.TenantNameFetcher;
import br.com.falcon.repository.ServiceRepository;
import br.com.falcon.security.TokenUtils;
import br.com.falcon.web.response.ServicesResponse;

@RestController
public class ServiceController {

	@Value("${api.token.header}")
    private String tokenHeader;

    @Autowired
    private TenantNameFetcher tenantResolver;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private TokenUtils tokenUtils;

    @PreAuthorize("@securityService.hasProtectedAccess()")
    @RequestMapping(value = "industries/{industryId}/services", method = RequestMethod.GET)
    public ResponseEntity<?> view(HttpServletRequest request, Long industryId, Device device) throws AuthenticationException {
        
    		final String authToken = request.getHeader(this.tokenHeader);
		final String username = this.tokenUtils.getUsernameFromToken(authToken);
		
		try {
            tenantResolver.setUsername(username);
            ExecutorService es = Executors.newSingleThreadExecutor();
            Future<User> utrFuture = es.submit(tenantResolver);
            User utr = utrFuture.get();
            es.shutdown();
            TenantContext.setCurrentTenant(utr.getTenant());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new ResponseEntity<>(new ServicesResponse(serviceRepository.findByIndustryIdOrderByName(industryId)), HttpStatus.OK);
    }
}