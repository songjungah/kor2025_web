package korweb.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import korweb.model.dto.MemberDto;
import korweb.model.dto.PointDto;
import korweb.model.entity.MemberEntity;
import korweb.model.entity.PointEntity;
import korweb.model.repository.MemberRepository;
import korweb.model.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired private MemberRepository memberRepository;

    @Autowired private FileService fileService;

    // 1. 회원가입 서비스
    @Transactional  // 트랜잭션
    public boolean signup(MemberDto memberDto) {

        // - 프로필 사진 첨부파일 존재하면 업로드 진행
        // (1) 만약에 업로드 파일이 비어있으면 'default.jpg' 임시용 프로필 사진 등록한다.
        if (memberDto.getUploadfile().isEmpty()) {
            memberDto.setMimg("default.jpg");
        }
        else {
            // (2) 아니고 업로드 파일이 존재하면, 파일 서비스 객체내 업로드 함수를 호출한다.
            String fileName = fileService.fileUpload(memberDto.getUploadfile());  // 업로드 함수에 multipart 객체를 대입해준다.
            // (3) 만약에 업로드 후 반환된 값이 null 이면 업로드 실패, 아니면 업로드 성공
            if(fileName == null) {return false;} // 업로드를 실패하면 회원가입 실패
            else {
                memberDto.setMimg(fileName);    // 업로드 성공한 uuid + 파일명을 dto에 대입한다.
            }
        }

        // 1. 저장할 dto를 entity로 변환한다.
        MemberEntity memberEntity = memberDto.toEntity();
        // 2. 변환된 entity 를 save 한다.
        // 3. save(영속성/연결된)한 엔티티를 반환받는다.
        MemberEntity saveEntity = memberRepository.save(memberEntity);
        // 4. 만약에 영속된 엔티티의 회원번호가 0보다 크면
        if (saveEntity.getMno() > 0) {
            PointDto pointDto = PointDto.builder()
                    .pcontent("회원가입축하")
                    .pcount(100)
                    .build();
            pointPayment(pointDto, memberEntity);
            return true;}
        else { return  false;}
    }

    // 2. 로그인 서비스
    @Transactional  // 트랜잭션
    public boolean login(MemberDto memberDto) {
        // [방법1]
        /*
        // (1) 모든 회원 엔티티를 조회한다.
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        // (2) 모든 회원 엔티티를 하나씩 조회한다.
        for(int index = 0; index <= memberEntityList.size()-1; index++) {
            // (3) index 번째의 엔티이를 꺼내기
            MemberEntity memberEntity = memberEntityList.get(index);
            // (4) index 번째의 엔티티 아이디가 입력받은(dto) 아이디와 같으면
            if (memberEntity.getMid().equals(memberDto.getMid())) {
                // (5) index 번째의 엔티티 비밀번호가 입력받은(dto) 비밀번호와 같으면
                if (memberEntity.getMpwd().equals(memberDto.getMpwd())) {
                    System.out.println("Login Ok");
                    return true;    // 로그인 성공
                }
            }
            return false;   // 로그인 실패
        }
        */

        // [방법2] JAP Repository 추상메소드 활용
        boolean result = memberRepository.existsByMidAndMpwd(memberDto.getMid(), memberDto.getMpwd());

        if (result == true) {
            System.out.println("로그인 성공");
            setSession(memberDto.getMid());     // 로그인 성공시 세션에 아이디 저장

            // 로그인을 성공했을 때 포인트 지급
            PointDto pointDto = PointDto.builder()
                    .pcontent("로그인접속")
                    .pcount(1).build();
            // 현재 로그인된 엔티티 찾기 .findById(pk번호), 지정한 pk번호의 엔티티 조회
            MemberEntity memberEntity = memberRepository.findById(getMyInfo().getMno()).get();
            // 포인트 지급 함수 호출
            pointPayment(pointDto, memberEntity);

            return true;
        } else {
            System.out.println("로그인 실패");
            return false;
        }
    }

    // ===========세션 관련 함수 ========== //
    // (1) 내장된 톰캣 서버의 요청 객체
    @Autowired private HttpServletRequest request;

    // [3] 세션객체에 로그인된 회원 아이디를 추가하는 함수(로그인 성공)
    public boolean setSession(String mid) {
        // (2) 요청 객체를 이용한 톰캣내 세션 객채를 반환한다.
        HttpSession httpSession = request.getSession();
        // (3) 세션 객체에 속성(새로운 값) 추가한다.
        httpSession.setAttribute("loginId", mid);
        return true;
    }

    // [4] 세션객체에 로그인된 회원아이디 반환하는 함수(내정보 조회, 수정 등등)
    public String getSession() {
        // (2)
        HttpSession httpSession = request.getSession();
        // (4) 세션 객체에 속성명의 값 반환한다.
        // * 반환타입이 Object 이다.
        Object object = httpSession.getAttribute("loginId");
        // (5) 검사 후 타입변환
        if (object != null) {
            // 만약에 세션 정보가 존재하면
            String mid = (String)object;    // Object 타입 --> String 타입 변환
            return mid;
        }
        return null;
    }

    // [5] 세션객체내 정보 초기화 : 로그아웃
    public boolean deleteSession() {
        // (2)
        HttpSession httpSession = request.getSession();
        // (3) 세션객체 안에 특정한 속성명 제거
        httpSession.removeAttribute("loginId");
        return true;
    }

    // [6] 현재 로그인된 회원의 정보 조회
    public MemberDto getMyInfo() {
        // 1. 현재 세션에 저장된 회원 아이디 조회
        String mid = getSession();
        // 2. 만약에 로그인 사애이면
        if (mid != null) {
            // 3. 회원아이디로 엔티티 조회
            MemberEntity memberEntity = memberRepository.findByMid(mid);
            // 4. entity --> dto
            MemberDto memberDto = memberEntity.toDto();
            // 5. 반환
            return memberDto;
        }
        // 비로그인 상태이면
        return null;
    }

    // [7] 현재 로그인된 회원 탈퇴
    public boolean myDelete() {
        // 1. 현재 세션에 저장된 회원 아이디를 조회
        String mid = getSession();
        if (mid != null) {  // 2. 만약에 로그인 상태이면
            // 3. 현재 로그인된 아이디로
            MemberEntity memberEntity = memberRepository.findByMid(mid);
            // 4. 엔티티 탈퇴/삭제하기
            memberRepository.delete(memberEntity);
            // 5. 반환
            return true;
        }
        // 비로그인 상태이면
        return false;
    }

    // [8] 현재 로그인된 회원 정보 수정, mname 닉네임, memail 이메일
    @Transactional
    public boolean myUpdate(MemberDto memberDto) {
        String mid = getSession();
        if (mid != null) {
            MemberEntity memberEntity = memberRepository.findByMid(mid);
            memberEntity.setMname(memberDto.getMname());
            memberEntity.setMemail(memberDto.getMemail());
            return true;
        }
        return false;
    }

    @Autowired private PointRepository pointRepository;
    // [9] (부가서비스) 포인트 지금 함수, 지급내용 pcontent / 지금수량 pcount, 지금받는 회원엔티티
    public boolean pointPayment(PointDto pointDto, MemberEntity memberEntity) {
        PointEntity pointEntity = pointDto.toEntity();
        pointEntity.setMemberEntity(memberEntity);  // 지급받는 회원엔티티 대입

        PointEntity saveEntity = pointRepository.save(pointEntity);
        if(pointEntity.getPno() > 0) {return true;}
        else {return false;}
    }

}