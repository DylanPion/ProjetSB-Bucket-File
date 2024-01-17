package com.nextu.projetSB.Entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

    @Document
    @NoArgsConstructor
    @Data
    public class User {
        @Id
        private String id;
        private String firstName;
        private String lastName;

        @Indexed(unique = true)
        @Email(message = "Le format de l'adresse email n'est pas valide")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Le format de l'adresse email n'est pas valide")
        private String login;
        private String password;

        @DocumentReference
        private List<Bucket> buckets;

        @DocumentReference
        private List<FileData> files;

        public void addBucket(Bucket bucket) {
            if (this.buckets == null) {
                this.buckets = new ArrayList<>();
            }
            this.buckets.add(bucket);
        }

        @Version
        private Long version;
        public void addFile(FileData fileData){
            if(this.files==null){
                this.files = new ArrayList<>();
            }
            this.files.add(fileData);
        }
    }

