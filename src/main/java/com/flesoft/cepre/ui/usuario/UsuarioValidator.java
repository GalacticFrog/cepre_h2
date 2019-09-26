package com.flesoft.cepre.ui.usuario;

import com.flesoft.cepre.validator.Validator;
import com.flesoft.cepre.model.Usuario;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;

public class UsuarioValidator implements Validator<Usuario> {

    private static ValidatorFactory factory;

    static {
        factory = buildDefaultValidatorFactory();
    }

    @Override
    public String validate(Usuario usuario) {
        StringBuilder sb = new StringBuilder();

        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);

        if (!constraintViolations.isEmpty()) {
            //sb.append("Valida da entidade Mercadoria\n");
            constraintViolations.forEach((error) -> {
                // System.out.println(error.getMessageTemplate() + "::" + error.getPropertyPath() + "::" + error.getMessage());
                sb.append(error.getMessage() + "\n");
            });
        }

//        if (usuario != null) {
//            javax.validation.Validator validator = factory.getValidator();
//            Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);
//
//            if (!constraintViolations.isEmpty()) {
//                sb.append("Validação da entidade Mercadoria\n");
//                for (ConstraintViolation<Usuario> constraint : constraintViolations) {
//                    sb.append(String.format("%n%s: %s", constraint.getPropertyPath(), constraint.getMessage()));
//                }
//            }
//        }
        return sb.toString();
    }

}
