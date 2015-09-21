package de.ulfbiallas.imagedatabase.tools;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.ulfbiallas.imagedatabase.entities.Comment;

public class CommentSerializer extends JsonSerializer<Comment> {

    @Override
    public void serialize(Comment comment, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        if (comment != null) {
            jgen.writeStartObject();
            jgen.writeStringField("id", comment.getId());
            jgen.writeStringField("content", comment.getContent());
            jgen.writeStringField("time", comment.getTime().toString());
            jgen.writeStringField("authorId", comment.getAuthor().getId());
            jgen.writeStringField("authorName", comment.getAuthor().getName());
            jgen.writeEndObject();
        }

    }

}
