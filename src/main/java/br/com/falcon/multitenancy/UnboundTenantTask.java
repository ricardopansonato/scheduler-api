package br.com.falcon.multitenancy;

import static br.com.falcon.multitenancy.MultiTenantConstants.DEFAULT_TENANT_ID;

import java.util.concurrent.Callable;

public abstract class UnboundTenantTask<T> implements Callable<T> {

    protected String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public T call() throws Exception {
        TenantContext.setCurrentTenant(DEFAULT_TENANT_ID);
        return callInternal();
    }

    protected abstract T callInternal();
}
