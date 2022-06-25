<br>

# 페이의 달인🚴🏻
<i>명지, 민욱, 주황, 영준, 소현</i><br>
<br>
### ・ 프로젝트 개요
http://www.pedal-dingco.site:9090/main<br>

<br>
<i>[소개]</i><br>
<br>
본 프로젝트에서는 더치페이 서비스를 모바일 웹 환경에 최적화시켜 개발합니다.<br> 
더치페이 서비스는 사용자가 더치페이 내역(내용, 참여인원, 금액, ...)을 입력하면 정산된 내역을 공유할 수 있는 서비스입니다.<br>
<br>
더치페이 서비스를 이용하기 위해서는 로그인이 필수적이며, 로그인 없이 회원가입, 로그인, 공지사항, FAQ 서비스에 한에서만 이용할 수 있습니다.<br>
<br>
더치페이 서비스를 이용하는 회원은 'USER'와 'ADMIN'으로 나뉩니다.<br>
USER는 일반 회원으로 서비스를 이용하기 위해 회원가입이 필요하며 소셜(구글, 네이버, 카카오) 로그인으로 회원가입을 대체할 수 있습니다. ADMIN은 관리자로 관리자 계정을 통해서 서비스를 관리하는 역할을 수행합니다.<br>
<br>
더치페이 서비스는 사용자의 편의를 고려하여 일회성 더치페이와 다회성 더치페이로 구분 시켰고, 원 단위 절사 옵션을 추가했습니다.<br>
<br>
<br>
<i>[목표 및 개발과정]</i><br>
본 프로젝트를 통해 팀원 모두가 개발의 전과정을 경험 하는 것과 실서비스 배포를 목표로 했습니다.<br>
<br>
시스템 개발 라이프 사이클(SDLC)을 경험하기 위해 프론트와 백엔드 영역을 나누지 않고 기능 단위로 개발에 참여했고, 추후에 팀장(명지)님이 전체 디자인 퍼블리싱을 맡아 개발했습니다.<br>
Cafe24의 Hosting을 구매해서 데이터베이스를 공유하고 배포를 진행하였으나, 배포시 메모리 초과의 문제가 발생했고 추후에도 사용자 트래픽의 증가가 예상되기에 서비스 확장에 용이한 AWS EC2로 이전을 했고, 이후에 데이터베이스도 RDS로 이전을 했습니다.<br> 
<br>
프로젝트는 2022.03.06일 부터 2022.06.28까지 약 4달간 진행했습니다.<br>
<br>
<br>
<i>[고도화 계획]</i><br>
추가적으로 Java ORM 기술(JPA) 도입, 도커-쿠버네티스 CI/CD 환경 구축 등의 고도화 계획을 갖고 있습니다.<br>
<br>
<br>

### ・ 개발환경
・ Frontend : HTML, CSS, JS(ES6)<br>
・ Backend : Java, Spring Boot, Mybatis<br>
・ DB : MariaDB<br>
・ IDE & Collaboration : IntelliJ, Workbench, Git, Notion<br>
・ deployment : Cafe24 Hosting -> AWS EC2, RDS<br>
<br>
<br>
### ・ 핵심기능
![image](https://user-images.githubusercontent.com/88137420/175780484-3e48f13b-eec9-4865-8f49-4ff7e6b155f5.png)
<br>
<br>
### ・ WBS
![image](https://user-images.githubusercontent.com/88137420/175780403-9e9c8024-1c49-4867-a57f-c730fd222ccf.png)
<br>
<br>
### ・ IA
![image](https://user-images.githubusercontent.com/88137420/175779853-12469885-c342-4fff-991c-19991814c165.png)
<br>
<br>
### ・ 유저 플로우
![image](https://user-images.githubusercontent.com/88137420/175780638-8b48173f-d848-41f1-b2b3-5520f1b38926.png)
<br>
<br>
### ・ ERD
![image](https://user-images.githubusercontent.com/88137420/175780521-66da4193-d9ca-499f-81a1-4ff63a513f27.png)
<br>
<br>
### ・ 회고
명지 : <br>
민욱 : <br>
주황 : <br>
영준 : <br>
소현 : <br>
