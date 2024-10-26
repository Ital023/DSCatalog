package io.github.ital023.dscatalog.services;

import io.github.ital023.dscatalog.dto.EmailDTO;
import io.github.ital023.dscatalog.entities.PasswordRecover;
import io.github.ital023.dscatalog.entities.User;
import io.github.ital023.dscatalog.repositories.PasswordRecoverRepository;
import io.github.ital023.dscatalog.repositories.UserRepository;
import io.github.ital023.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void createRecoverToken(EmailDTO body) {
        User user = userRepository.findByEmail(body.getEmail());

        if(user == null) throw new ResourceNotFoundException("Recurso não encontrado");

        String token = UUID.randomUUID().toString();

        PasswordRecover entity = new PasswordRecover();
        entity.setEmail(body.getEmail());
        entity.setToken(token);
        entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
        entity = passwordRecoverRepository.save(entity);

        String bodyEmail = "Acesse o link para definir uma nova senha \n\n" +
                recoverUri + token;

        emailService.sendEmail(body.getEmail(), "Recuperação de senha", bodyEmail);
    }


}
