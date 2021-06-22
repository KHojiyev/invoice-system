package uzdeveloper.invoicesystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileOriginalName;

    private Long size;

    private String contentType;

    private String name;


    public Attachment(String fileOriginalName, Long size, String contentType, String name) {
        this.fileOriginalName = fileOriginalName;
        this.size = size;
        this.contentType = contentType;
        this.name = name;
    }

    public Attachment(String fileOriginalName, Long size, String contentType) {
        this.fileOriginalName = fileOriginalName;
        this.size = size;
        this.contentType = contentType;
    }
}

