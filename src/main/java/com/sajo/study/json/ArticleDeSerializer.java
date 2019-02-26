package com.sajo.study.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sajo.study.model.Article;
import com.sajo.study.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ArticleDeSerializer extends StdDeserializer<Article> {

    public ArticleDeSerializer() {
        this(null);
    }

    public ArticleDeSerializer(Class<User> u) {
        super(u);
    }

    @Override
    public Article deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String content = node.get("content").asText();
        String imagePath = node.get("imagePath").asText();
        int userIdx = (int) (node.get("userIdx")).numberValue();
        return new Article(new User(userIdx), content, imagePath);
    }
}
