package korweb.model.entity;

import jakarta.persistence.*;
import korweb.model.dto.BoardDto;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reply")     // 테이블명
public class ReplyEntity extends BaseTime {

    // 1. 댓글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    // 2. 댓글내용
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String rcontent;

    // 3. 댓글작성자 : 작성자번호 : 단반향
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;

    // 4. 게시물 번호 : 단반향
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bno")
    private BoardEntity boardEntity;
    


}
