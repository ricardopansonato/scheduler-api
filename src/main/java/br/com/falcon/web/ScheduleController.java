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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.falcon.domain.Schedule;
import br.com.falcon.domain.User;
import br.com.falcon.multitenancy.TenantContext;
import br.com.falcon.multitenancy.TenantNameFetcher;
import br.com.falcon.repository.ScheduleRepository;
import br.com.falcon.repository.ServiceRepository;
import br.com.falcon.repository.UserRepository;
import br.com.falcon.security.TokenUtils;
import br.com.falcon.web.request.ScheduleRequest;
import br.com.falcon.web.response.SchedulesResponse;

@RestController
public class ScheduleController {
	
	@Value("${api.token.header}")
    private String tokenHeader;

    @Autowired
    private TenantNameFetcher tenantResolver;
    
    @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private UserRepository professionalRepository;
    
    @Autowired
    private TokenUtils tokenUtils;
    
    @PreAuthorize("@securityService.hasProtectedAccess()")
    @RequestMapping(value = "scheduled", method = RequestMethod.GET)
    public ResponseEntity<?> view(HttpServletRequest request, Device device) throws AuthenticationException {
        
    		final String authToken = request.getHeader(this.tokenHeader);
		final String username = this.tokenUtils.getUsernameFromToken(authToken);
		User utr = null;
		
		try {
            tenantResolver.setUsername(username);
            ExecutorService es = Executors.newSingleThreadExecutor();
            Future<User> utrFuture = es.submit(tenantResolver);
            utr = utrFuture.get();
            es.shutdown();
            TenantContext.setCurrentTenant(utr.getTenant());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		if (utr == null) {
			return ResponseEntity.badRequest().build();
		}
		
		if ("CLIENT".equals(utr.getRole())) {
			return new ResponseEntity<>(new SchedulesResponse(scheduleRepository.findByClient_IdOrderByStartDesc(utr.getId())), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new SchedulesResponse(scheduleRepository.findByProfessional_IdOrderByStartDesc(utr.getId())), HttpStatus.OK);
		}
    }
    
    @PreAuthorize("@securityService.hasProtectedAccess()")
    @RequestMapping(value = "scheduled", method = RequestMethod.POST)
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody ScheduleRequest scheduleRequest, Device device) throws AuthenticationException {
        
    		final String authToken = request.getHeader(this.tokenHeader);
		final String username = this.tokenUtils.getUsernameFromToken(authToken);
		User utr = null;
		
		try {
            tenantResolver.setUsername(username);
            ExecutorService es = Executors.newSingleThreadExecutor();
            Future<User> utrFuture = es.submit(tenantResolver);
            utr = utrFuture.get();
            es.shutdown();
            TenantContext.setCurrentTenant(utr.getTenant());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		if (utr == null) {
			return ResponseEntity.badRequest().build();
		}
		
		final Schedule schedule = new Schedule();
		schedule.setClient(utr);
		schedule.setStart(scheduleRequest.getStartDate());
		schedule.setEnd(scheduleRequest.getStartDate());
		final User professional = professionalRepository.findOne(scheduleRequest.getProfessionalId());
		schedule.setProfessional(professional);
		schedule.setServiceName(serviceRepository.findOne(professional.getServiceId()).getName());
		scheduleRepository.save(schedule);
		return ResponseEntity.ok().build();
    }
}
