# BACK-END 7th/3조 팀원 매칭 서비스 프로젝트 FIGHTEAM
- Website : ~~http://27.96.135.23:8080/post/home~~

현재 배포 운영이 종료되었습니다.

## Project Architecture
<img width="1449" alt="image" src="https://github.com/KIMHYOJUN97/BACK-END-7th-FIGHTEAM/assets/77912953/535a85ff-cf4d-4423-a2c2-b27bf44590dc">

## Scheduling
- Notion을 활용한 스프린트 관리
<img width="1146" alt="image" src="https://github.com/KIMHYOJUN97/BACK-END-7th-FIGHTEAM/assets/77912953/1d6f3493-db03-47b1-97e6-2a14384a5c95">

- 구글 스프레드 시트지를 활용하여 WBS 관리
![image](https://github.com/KIMHYOJUN97/BACK-END-7th-FIGHTEAM/assets/77912953/a46d1b2d-b7df-4cfd-8c92-2c25867acb0e)

## ER Diagram
![image](https://github.com/KIMHYOJUN97/BACK-END-7th-FIGHTEAM/assets/77912953/9185cca7-9cd9-435b-90b9-6b6dfb182554)

## Features
### 기능 페이지
<img width="1183" alt="image" src="https://github.com/KIMHYOJUN97/BACK-END-7th-FIGHTEAM/assets/77912953/fe123484-701f-42ac-9625-b20c7a31d935">
<img width="1154" alt="image" src="https://github.com/KIMHYOJUN97/BACK-END-7th-FIGHTEAM/assets/77912953/968d0a7d-7cb0-4930-a56d-0acec72582de">

### [@김효준] (https://github.com/KIMHYOJUN97)
- **스크럼 관리 및 채팅방**
  - 채팅방 생성
  - 실시간 양방향 채팅
  - 팀 스크럼 관리

### [@황정환] (https://github.com/Hwang-JeongHwan)
- **코드 통합관리 및 결제 서비스**
  - 결제 서비스 구현
  - 카카오페이 API 연동
  - 코드 통합관리

### [@조승근] (https://github.com/seunggeuncho)
- **메인 페이지 및 게시글**
  - 메인 홈페이지 구현
  - 게시글 CRUD
  - 댓글 CRUD

### [@김진세] (https://github.com/in3kk)
- **팀 스페이스,출결 및 신청 페이지**
  - 팀 스페이스 생성
  - 팀 스페이스 멤버 출결관리
  - 팀 스페이스 신청 관리

### [@정은정] (https://github.com/ej185652185)
- **유저 정보 관리**
  - 유저 회원가입
  - 유저 로그인

## Tech Stack

### Languages

![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white)

### Frameworks

![SpringBoot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)

### Template Engine

![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white)

### Database

![Oracle](https://img.shields.io/badge/Oracle-F80000A?style=flat&logo=Oracle&logoColor=white)

### Build Tool

![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=Gradle&logoColor=white)

### DevOps

![Naver Cloud Platform](https://img.shields.io/badge/-Naver%20Cloud-blue?style=flat&logo=iCloud&logoColor=white)


### 형상 관리 전략

![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white)

- Git Flow 전략을 사용하여 Branch를 관리하며 Main/Develop Branch로 Pull Request 시 코드 리뷰 진행 후 merge 합니다.
  ![image](https://user-images.githubusercontent.com/60968342/219870689-9b9d709c-aa55-47db-a356-d1186b434b4a.png)
  
- Main: 배포시 사용
- Develop: 개발 단계가 끝난 부분에 대해 Merge 내용 포함
- Feature: 기능 개발 단계
- Hot-Fix: Merge 후 발생한 버그 및 수정 사항 반영 시 사용
