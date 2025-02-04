package korweb.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file")     // 테이블명
public class FileEntity{

    // 1. 첨부파일번호(PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fno;

    // 2. 첨부파일명
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String fname;

    // 3. 첨부파일이 위치할 게시물 번호 : 게시물번호(FK) : 단방향
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bno")
    private BoardEntity boardEntity;

}
