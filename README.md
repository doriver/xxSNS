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

