package com.niels.homebanking.enumerate.converter;

import com.niels.homebanking.enumerate.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender val) {
        return val==null?null:val.getValue();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        return Gender.fromValue(dbData);
    }

}
