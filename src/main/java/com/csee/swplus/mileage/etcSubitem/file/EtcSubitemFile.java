package com.csee.swplus.mileage.etcSubitem.file;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "_sw_mileage_record_files")
public class EtcSubitemFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int recordId;

    private String originalFilename;

    private String filename;

    private String filesize;

    private String semester;

    @Column(insertable = false)
    private LocalDateTime regdate;
}
