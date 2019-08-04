package com.twinkle.scaffold.component.auth;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import com.twinkle.scaffold.component.auth.repo.ClientRepo;
import com.twinkle.scaffold.component.auth.repo.domain.Client;

import io.micrometer.core.instrument.util.StringUtils;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月2日 下午10:11:35 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Component
public class CustomClientDetailsService implements ClientDetailsService {

    @Autowired
    private ClientRepo clientRepo;
    
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = clientRepo.findByClientId(clientId);
        if (client == null) {
            throw new ClientRegistrationException("clientId无效");
        }
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(client.getClientId());
        clientDetails.setClientSecret(client.getClientSecret());
        if(StringUtils.isNotBlank(client.getRedirectUrl())){
            clientDetails.setRegisteredRedirectUri(new HashSet<>(Arrays.asList(client.getRedirectUrl().split(","))));
        }
        if(StringUtils.isNotBlank(client.getGrantType())){
            clientDetails.setAuthorizedGrantTypes(Arrays.asList(client.getGrantType().split(",")));
        }
        if(StringUtils.isNotBlank(client.getScope())){
            clientDetails.setScope(Arrays.asList(client.getScope().split(",")));
        }
        return clientDetails;
    }

}
