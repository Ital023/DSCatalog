package io.github.ital023.dscatalog.services;

import io.github.ital023.dscatalog.dto.EmailDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    public void createRecoverToken(EmailDTO body) {
        System.out.println(body.getEmail());
    }


}
