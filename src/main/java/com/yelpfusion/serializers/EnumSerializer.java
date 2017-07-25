/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.yelpfusion.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumSerializer extends StdSerializer<Enum> {
    public EnumSerializer(Class<Enum> type) {
        super(type);
    }

    @Override
    public void serialize(
        Enum anEnum,
        JsonGenerator generator,
        SerializerProvider serializerProvider
    ) throws IOException {
        List<Field> nonStaticFields = Arrays.stream(anEnum.getClass().getDeclaredFields())
            .filter(field -> {
                return !Modifier.isStatic(field.getModifiers());
            })
            .collect(Collectors.toList());

        if (nonStaticFields.size() > 0) serializeAsObject(anEnum, generator, nonStaticFields);
        else generator.writeString(anEnum.toString());
    }

    private void serializeAsObject(
        Enum anEnum, JsonGenerator generator, List<Field> fields
    ) throws IOException {
        generator.writeStartObject();
        for (Field field : fields) writeField(anEnum, generator, field);
        generator.writeEndObject();
    }

    private void writeField(Enum anEnum, JsonGenerator generator, Field field) {
        try {
            field.setAccessible(true);
            Field modifiers = field.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            generator.writeStringField(field.getName(), (String) field.get(anEnum));
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
