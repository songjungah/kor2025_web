package korweb.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

    private long totalcount;
    private int page;
    private int totalpage;
    private int startbtn;
    private int endbtn;

    // + Object 를 사용하는 이유
    // 자바의 최상위 클래스 이므로 모든 타입들의 자료들을 저장할 수 있다.
    // 즉, data 에는 List<BoardDto> 혹은 List<ReplyDto> 등 다양한 페이징한 정보를 대입하기 위해서 사용
    private Object data;

}
