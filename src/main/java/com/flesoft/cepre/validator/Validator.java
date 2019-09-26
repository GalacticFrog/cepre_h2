package com.flesoft.cepre.validator;

public interface Validator<Entity> {

    public String validate(Entity e);

}
