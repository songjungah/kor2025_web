package korweb.model.repository;

import korweb.model.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    // + JPA 함수명은 무조건 카멜표기법(낙타의 혹) 첫글자는 소문자로 시작, 두번째 단어의 첫글자를 대문자로 적음
        // mystudentlist -> myStudentList(카멜표기법)
    
    // 내장 함수
    // findById(pk번호) : 지정한 pk번호의 레코드 조회
    // findAll()       : 모든 레코드 조회

    // 추상 메소드 만들기
    // 반환타입 findBy필드명(int 필드명)
    // 반환타입 findByBtitle(int btitle)     : 지정한 게시물 제목으로 레코드 조회
    // 반환타입 findByBcontent(int bcontent) : 지정한 게시물 내용으로 레코드 조회
    
    // [1] cno 로 레코드 조회
    // Page<BoardEntity> findByCno(int cno, Pageable pageable);
    
    // 만약에 cno가 참조키(fk) 일때는 fk필드명을 넣지 않고 엔티티 필드명을 넣는다.
    // 자바엔티티필드명_참조엔티티필드명
    Page<BoardEntity> findByCategoryEntity_Cno(int cno, Pageable pageable);

}
