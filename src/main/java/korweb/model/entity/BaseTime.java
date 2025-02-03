package korweb.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass       // 자체 매핑이 아닌 상속 매핑으로 사용
@EntityListeners(AuditingEntityListener.class)   // 엔티티에 변화 감지
public class BaseTime {

    @CreatedDate        // 엔티티 생성날짜/시간 자동 주입
    private LocalDateTime cdate;

    @LastModifiedDate   // 엔티티 수정 생성날짜/시간 자동 주입
    private LocalDateTime udate;

}