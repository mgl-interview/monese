package com.monese.converters;

public interface Converter<Entity, Dto> {
    Entity toEntity(Dto dto);
    Dto toDto(Entity entity);
}
