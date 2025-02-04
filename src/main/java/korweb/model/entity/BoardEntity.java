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
@Table(name = "board")     // 테이블명
public class BoardEntity extends BaseTime {
    // 1. 게시물번호(PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;

    // 2. 게시물제목
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String btitle;

    // 3. 게시물내용
    @Column(columnDefinition = "longtext")
    private String bcontent;

    // 4. 게시물조회수
    @Column
    private int bview;

    // 5. 작성자번호(FK)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;

    // 6. 카테고리번호(FK)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cno")
    private CategoryEntity categoryEntity;
}
