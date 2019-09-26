package com.flesoft.cepre.ui.estudiante;

import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.validator.Validator;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;

public class AulaEstudianteValidator implements Validator<AulaEstudiante> {

    private static ValidatorFactory factory;

    static {
        factory = buildDefaultValidatorFactory();
    }

    @Override
    public String validate(AulaEstudiante ae) {
        StringBuilder sb = new StringBuilder();

        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<AulaEstudiante>> constraintViolations = validator.validate(ae);

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
