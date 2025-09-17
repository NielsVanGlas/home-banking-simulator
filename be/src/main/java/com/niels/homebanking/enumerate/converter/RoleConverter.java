package com.niels.homebanking.enumerate.converter;

import com.niels.homebanking.enumerate.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role val) {
        return val==null?null:val.getValue();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        return Role.fromValue(dbData);
    }

}
