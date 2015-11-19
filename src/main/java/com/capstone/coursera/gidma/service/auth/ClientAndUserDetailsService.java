package com.capstone.coursera.gidma.service.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;

import com.capstone.coursera.gidma.service.model.repository.UserRepository;

/**
 * A class that combines a UserDetailsService and ClientDetailsService into a single object.
 */
public class ClientAndUserDetailsService implements UserDetailsService, ClientDetailsService {

    private final ClientDetailsService clients_;

    private final UserDetailsService users_;

    private final ClientDetailsUserDetailsService clientDetailsWrapper_;
    
    @Autowired
    UserRepository userRepository;

    public ClientAndUserDetailsService(ClientDetailsService clients, UserDetailsService users) {
        super();
        clients_ = clients;
        users_ = users;
        clientDetailsWrapper_ = new ClientDetailsUserDetailsService(clients_);
        System.out.println("=====>>> ClientAndUserDetailsService: clients: " + clients);
        System.out.println("=====>>> ClientAndUserDetailsService: users: " + users);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        System.out.println("=====>>> loadClientByClientId: clientId: " + clientId);
        return clients_.loadClientByClientId(clientId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=====>>> loadUserByUsername: BEFORE....... username: " + username);
        UserDetails user = null;
        try {
            if(username.equals("signUpUser")){
                user = users_.loadUserByUsername(username);
            } else {
                
                System.out.println("=====>>> loadUserByUsername: Checking from DB ........ username ---> : " + username);
                com.capstone.coursera.gidma.service.model.User userDb = userRepository.findOne(username);

                System.out.println("=====>>> loadUserByUsername: GOT from DB ........ userDb: " + userDb);
                
                if(userDb != null){
                    if(StringUtils.isNotBlank(userDb.getMedicalRecordNumber())){
                        user = User.create(userDb.getUserId(), userDb.getPassword(), "USER", "PATIENT", "FOLLOWER");                        
                    } else {
                        user = User.create(userDb.getUserId(), userDb.getPassword(), "USER", "FOLLOWER");
                    }
                }
            }
            
            
            System.out.println("=====>>> loadUserByUsername: AFTER ..... user: " + user);
        } catch (UsernameNotFoundException e) {
            user = clientDetailsWrapper_.loadUserByUsername(username);
            System.out.println("=====>>> loadUserByUsername: UsernameNotFoundException : user: " + user);
            System.out.println("=====>>> loadUserByUsername: UsernameNotFoundException : e: " + e);
        } catch (Exception eX) {
            eX.printStackTrace();
        }
        return user;
    }

}
