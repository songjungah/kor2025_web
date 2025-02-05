package korweb.service;

import korweb.model.dto.BoardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    // [1] 게시물 쓰기
    public boolean boardWrite(BoardDto boardDto) {
        // 코드 구현하기 전
        return false;
    }

    // [2]] 게시물 전체 조회
    public List<BoardDto> boardFindAll() {
        return null;
    }

    // [3] 게시물 특정(개별) 조회
    public BoardDto boardFind(int bno) {
        return null;
    }

    // [4] 게시물 특정(개별) 수정
    public boolean boardUpdate(BoardDto boardDto) {
        return false;
    }

    // [5] 게시물 특정(개별) 삭제
    public boolean boardDelete(int bno) {
        return false;
    }

}
