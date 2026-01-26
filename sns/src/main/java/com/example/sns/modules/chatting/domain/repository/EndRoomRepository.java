package com.example.sns.modules.chatting.domain.repository;

import com.example.sns.modules.chatting.domain.entity.EndRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndRoomRepository extends JpaRepository<EndRoom, Long> {
    // excuted필드 값에 따른 개수
//    int countByExcuted(boolean excuted);

    // excuted가 false인 데이터를 endedAt 기준 오름차순(오래된 순)으로 상위 5개 조회
//    List<EndRoom> findTop5ByExcutedFalseOrderByEndedAtAsc();
}
