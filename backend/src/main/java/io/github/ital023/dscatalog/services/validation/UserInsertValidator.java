package io.github.ital023.dscatalog.services.validation;

import io.github.ital023.dscatalog.dto.UserInsertDTO;
import io.github.ital023.dscatalog.entities.User;
import io.github.ital023.dscatalog.repositories.UserRepository;
import io.github.ital023.dscatalog.resources.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserInsertDTO userInsertDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(userInsertDTO.getEmail());

        if (user != null) {
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
