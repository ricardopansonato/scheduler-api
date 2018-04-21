package br.com.falcon.multitenancy;

import br.com.falcon.domain.User;
import br.com.falcon.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TenantNameFetcher extends UnboundTenantTask<User> {

    @Autowired
    private UserRepository userTenantRelationRepository;

    @Override
    protected User callInternal() {
        User utr = userTenantRelationRepository.findByUsername(this.username);
        return utr;
    }
}
