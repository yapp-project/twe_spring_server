package com.sajo.study.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sajo.study.model.Article;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ArticleSerializer extends StdSerializer<Article> {
    public ArticleSerializer() {
        this(null);
    }

    protected ArticleSerializer(Class<Article> t) {
        super(t);
    }

    @Override
    public void serialize(Article value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        if (value.getUser() == null || value.getUser().getIdx() == null)
            gen.writeNumberField("userIdx", -1);
        else
            gen.writeNumberField("userIdx", value.getUser().getIdx());
        gen.writeStringField("content", value.getContent());
        gen.writeStringField("imagePath", value.getImagePath());
        gen.writeEndObject();
    }
}
