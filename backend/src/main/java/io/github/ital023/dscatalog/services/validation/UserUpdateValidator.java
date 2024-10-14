package io.github.ital023.dscatalog.services.validation;

import io.github.ital023.dscatalog.dto.UserUpdateDTO;
import io.github.ital023.dscatalog.entities.User;
import io.github.ital023.dscatalog.repositories.UserRepository;
import io.github.ital023.dscatalog.resources.exceptions.FieldMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private UserRepository repository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(UserUpdateValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserUpdateDTO userUpdateDTO, ConstraintValidatorContext constraintValidatorContext) {

        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(userUpdateDTO.getEmail());

        if (user != null && userId != user.getId()) {
            list.add(new FieldMessage("email", "Email já existe!"));
        }

        for(FieldMessage e : list) {
          constraintValidatorContext.disableDefaultConstraintViolation();
          constraintValidatorContext.buildConstraintViolationWithTemplate(e.getMessage())
                  .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }


}
