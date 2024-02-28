package in.gov.forest.wildlifemis.domian;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String fileName;

    @ManyToOne
    private TypeOfDocument typeOfDocument;

    private String fileUrl;


    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private Boolean isActive;


}
