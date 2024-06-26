package org.capsule.com.tasks.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.capsule.com.tasks.models.Session;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Session.Status, String> {

    @Override
    public String convertToDatabaseColumn(Session.Status status) {
        return status != null ? status.name() : null;
    }

    @Override
    public Session.Status convertToEntityAttribute(String dbData) {
        return dbData != null ? Session.Status.valueOf(dbData) : null;
    }
}
