package korweb.model.entity;

import jakarta.persistence.*;
import korweb.model.dto.MemberDto;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")     // 테이블명
public class MemberEntity extends BaseTime{

    @Id     // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // auto_increment
    private int mno;        // 회원번호

    // length = 30 / columnDefinition = "varchar(30)" 같다고 생각
    @Column(nullable = false, unique = true, columnDefinition = "varchar(30)")
    private String mid;     // 회원아이디

    @Column(nullable = false, columnDefinition = "varchar(30)")
    private String mpwd;    // 회원비밀번호

    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String mname;   // 회원닉네임

    @Column(nullable = false, unique = true, columnDefinition = "varchar(50)")
    private String memail;  // 회원이메일

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String mimg;    // 회원사진

    // entity --> dto 변환함수
    public MemberDto toDto() {
        return MemberDto.builder()
                .mno(this.mno)
                .mid(this.mid)
                .mpwd(this.mpwd)
                .mname(this.mname)
                .memail(this.memail)
                .mimg(this.mimg)
                .build();
    }

}