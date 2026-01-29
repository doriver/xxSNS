# 작은 SNS
## 소개
* 단체 채팅기능과 타임라인(이미지 첨부 글, 댓글, 좋아요)이 있는 작은SNS
* 이전에 작업했던 module들을 이식해 합치고, 수정및 디테일 처리해서 만들었다.
  * https://github.com/doriver/chatting02
  * https://github.com/doriver/stock_invest_sns_current


## 아키텍쳐
<img width="910" height="378" alt="image" src="https://github.com/user-attachments/assets/fbf60e82-0b2a-41af-8687-adeef7dd3b42" />

* 로컬에서 docker-compose로 Nginx, SpringBoot 실행    
MySQL은 로컬 호스트(Window)에 설치, Redis는 WSL(Windows Subsystem for Linux)에 설치

### 다중 WAS에서의 Real-time Messaging
<details>
  <summary>Nginx에서 로드밸런싱 및 WebSocket, SSE연결유지</summary>
  <div>
    <ul>
      <li>WebSocket, SSE 공통 설정
        <ul>
          <li>ip_hash 로 로드밸런싱</li>
          <li>HTTP_version 1.1 : 지속 연결(persistent connection)을 지원  </li>
          <li>연결 timeout 설정</li>
        </ul>
      </li>
      <li>WebSocket에서만 설정
        <ul>
          <li>Upgrade $http_upgrade;</li>
          <li>Connection "upgrade"</li>
        </ul>
      </li>
      <li>SSE에서만 설정
        <ul>
          <li>버퍼링 off</li>
          <li>캐시 off</li>
        </ul>
      </li>
    </ul>
  </div>
</details>
<details>
  <summary>Redis로 publish/subscribe</summary>
  <div>
    <ul>
      <li>redis의 특정채널을 subscribe
      </li>
      <li>해당 채널로 메시지가 왔을때, 실행되는 효과 정의
      </li>
      <li>trigger되는 지점에서 redis의 특정채널에게 publish
      </li>
    </ul>
  </div>
</details>
<details>
  <summary>Docker컨테이너로 WAS복제</summary>
  <div>
    <ul>
      <li>volume mount하는경우, 2개의 컨테이너가 하나의 경로에 하도록함 
      </li>
    </ul>
  </div>
</details>

### Redis를 쓰기 지연 Buffering계층으로 사용
단톡방 종료할때 채팅메시지들 일괄 저장하게하고, 여기서 Redis를 쓰기 지연 Buffering계층으로 사용
* 채팅방 종료 기능 : [MentorService.java](https://github.com/doriver/xxSNS/blob/1627f6f0a2e2eb09a1dad735ce339436cb9d7e3a/sns/src/main/java/com/example/sns/modules/chatting/application/MentorService.java#L60)

## 약식 ERD
<img width="727" height="324" alt="image" src="https://github.com/user-attachments/assets/ac12dede-d765-4a5d-8a7b-37dce46f38c9" />

* end_room테이블을 통해 비동기처리를 통한 데이터 저장에서, 데이터 누락을 관리 
* 참조필드로 ForeignKey대신 index를 사용