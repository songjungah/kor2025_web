package korweb.model.dto;

import korweb.model.entity.MemberEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private int mno;        // 회원번호
    private String mid;     // 회원아이디
    private String mpwd;    // 회원비밀번호
    private String mname;   // 회원닉네임
    private String memail;  // 회원이메일
    private String mimg;    // 회원프로필사진
    private MultipartFile uploadfile;   // 업로드 파일 객체

    // dto --> entity 변환필수
    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .mno(this.mno)
                .mid(this.mid)
                .mpwd(this.mpwd)
                .mname(this.mname)
                .memail(this.memail)
                .mimg(this.mimg)
                .build();
    }

}