package com.abrus.hw5.todo.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "todo")
@JsonComponent
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String description;
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long userId;

    public Todo(Long id, String title, String description, LocalDateTime dueDate, Priority priority, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    public static class Serializer extends JsonObjectSerializer<Todo> {
        @Override
        protected void serializeObject(Todo value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStringField("title", value.getTitle());
            jgen.writeStringField("description", value.getDescription());
            jgen.writeStringField("dueDate", String.valueOf(value.getDueDate()));
            jgen.writeStringField("priority", String.valueOf(value.getPriority()));
            jgen.writeStringField("status", String.valueOf(value.getStatus()));
        }
    }

    public static class Deserializer extends JsonDeserializer<Todo> {
        @Override
        public Todo deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
            ObjectCodec codec = jsonParser.getCodec();
            JsonNode tree = codec.readTree(jsonParser);
            String name = tree.get("name").textValue();
            int age = tree.get("age").intValue();
            return new Todo(
                    tree.get("id").longValue(),
                    tree.get("title").textValue(),
                    tree.get("description").textValue(),
                    LocalDateTime.parse(tree.get("dueDate").textValue()),
                    Priority.valueOf(tree.get("priority").textValue()),
                    Status.valueOf(tree.get("status").textValue())
            );
        }
    }
}
