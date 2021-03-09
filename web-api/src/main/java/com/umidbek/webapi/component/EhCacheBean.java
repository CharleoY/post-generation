package com.umidbek.webapi.component;

import com.umidbek.webapi.security.JwtUser;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EhCacheBean {

    private final EhCacheCacheManager cacheCacheManager;

    public EhCacheBean(EhCacheCacheManager cacheCacheManager) {
        this.cacheCacheManager = cacheCacheManager;
    }

    public void putUserDetails(JwtUser user) {
        getCache().put(new Element(user.getUsername(), user));
    }

    public void removeUser(String username) {
        getCache().remove(username);
    }

    public JwtUser getUserDetails(String username) {
        if (getCache().isKeyInCache(username) && getCache().get(username) != null) {
            return (JwtUser) getCache().get(username).getObjectValue();
        }
        return null;
    }

    private Cache getCache() {
        return Objects.requireNonNull(cacheCacheManager.getCacheManager()).getCache("USERCACHE");
    }
}
