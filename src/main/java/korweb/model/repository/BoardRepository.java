package korweb.model.repository;

import korweb.model.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

    // [2] JPA nativeQuery(SQL 문법) 이용한 조회, @Query(value = "SQL문법", nativeQuery = true)
        // 1. 특정한 카테고리 번호에 해당하는 모든 게시물 조회
        // DAO(sql) : select * from board where cno = ?;
        // JPA(nativeQuery) : select * from board where cno = :매개변수명
    @Query(value = "select * from board where cno = :cno", nativeQuery = true)
    List<BoardEntity> findByQuery1(int cno);
        // 2. 게시물 제목에 키워드를 포함하는 모든 게시물 조회
        // DAO(sql) : select * from board where btitle like '%?%'
        // JPA(nativeQuety) : select * from board where btitle like %:매개변수명%
    @Query(value = "select * from board where btitle like %:keyword%", nativeQuery = true)
    List<BoardEntity> findByQuery2(String keyword);
        // 3. 특정한 카테고리 번호에 게시물 제목에서 키워드를 포함하는 모든 게시물 조회
        // DAO(sql) : select * from board where cno = ? and btitle like %?%
        // JPA(nativeQuery) : select * from board where cno = :cno and btitle like %:keyword%
    @Query(value = "select * from board where cno = :cno and btitle like %:keyword%", nativeQuery = true)
    List<BoardEntity> findByQuery3(int cno, String keyword);
        // 4. 매개변수 속성에 키워드를 포함하는 모든 게시물 조회, 필드명 값에 따른[ sql : if(조건, 참, 거짓) ] 적용, 중첩도 가능
        // JPA(nativeQuery) : select * from board where :key like %:keyword%
        //                  : select * from board where if( :key = 'btitle', btitle like %:keyword%, bcontent like %:keyword% )
    @Query(value = "select * from board where if( :key = 'btitle', btitle like %:keyword%, bcontent like %:keyword% )", nativeQuery = true)
    List<BoardEntity> findByQuery4(String key, String keyword);

        // * 카테고리별 (1. 전체조회[keyword가 존재하지 않으면] 2. 제목btitle검색 3. 내용bcontent검색) + 페이징 처리
    @Query(value = "select * from board where cno = :cno and " +
            " if(:keyword = '', true, " +                       // 전체검색 [조건1]
            " if(:key = 'btitle', btitle like %:keyword%, " +   // 제목검색 [조건2]
            " if(:key = 'bcontent', bcontent like %:keyword%, true)))" , nativeQuery = true)    // 내용검색 [조건3]
    Page<BoardEntity> findBySearch(int cno, String key, String keyword, Pageable pageable);

    // if 조건이 실행 되었을 때 가정
        // SQL IF 조건문 : if(조건, 참, 거짓)
        // SQL IF 조건문 중첩 : if(조건1, 참1, if(조건2, 참2, if(조건3, 참3, 그외 거짓)))

    // 1. 전체조회 : select * from board where cno = :cno and true
    // 2. 제목검색 : select * from board where cno = :cno and btitle like %:keyword%
    // 3. 내용검색 : select * from board where cno = :cno and bcontent like %:keyword%
    // 4. 그 외 : select * from board where cno = :cno and true
    

}
