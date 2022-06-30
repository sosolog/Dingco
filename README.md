<br>
# 페이의 달인🚴🏻
<i>명지, 민욱, 주황, 영준, 소현</i><br>
<br>
### ・ 프로젝트 개요
https://www.pedal-dingco.site/main<br>

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
프로젝트는 2022.03.06일 부터 2022.06.28까지 약 4달간 진행 했습니다.<br>
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
<br>
민욱 : 이번 프로젝트를 통해서 원하는 바를 직접 만들 수 있는 개발의 즐거움을 느낄 수 있는 시간이었습니다. 개발이 처음이라 걱정도 많이 했지만 팀원과 함께 노력하면 모든 것을 할 수 있다는 자신감을 얻을 수 있었고, 개발의 전과정을 경험하며 좋은 아키텍쳐와 좋은 코드가 무엇인지 고민할 수 있는 좋은 시간이었습니다. 앞으로 실서비스를 운영 해보며 사용자의 요구사항을 바탕으로 부족한 부분을 개선 해나가고, 대규모 트래픽을 처리할 수 있도록 아키텍쳐 설계의 단계까지 나아가고 싶습니다.
<br>
<br>
주황 : <br>
<br>
영준 : 이번 프로젝트를 참가하면서 나도 개발을 할 수 있다는 자존감이 생겼습니다. 협업 프로젝트를 하면서 너무 좋은 팀원들을 만나 아주 좋은 경험을 가지게 되었습니다. 기획부터 실 개발 배포까지 전부 참여하면서 온전하게 개발을 했습니다. 전과정에 참여를 하면서 프로세를 다 경험했고 정말 좋은 경험이 되었습니다. 전체 서비스의 구현을 하고 계속 수정을 하면서 더 좋은 아키텍쳐가 무엇인지 더 알맞는 코드 구조가 어떻게 되는지 여러 번 수정해보면서 더 좋은 경험을 하게 되었습니다. 앞으로도 현업에 나가 실서비스를 구현 및 운영해보면서 사용자의 경험을 더 좋게 해서 더 좋은 서비스를 구현 및 운영 하고 싶습니다.<br>
<br>
소현 : 기획부터 배포까지 전과정에 참여하면서 개발 프로세스에 대한 이해도를 높일 수 있었던 프로젝트였습니다. 특히, 페이방 구현시 로직의 복잡도와 예상치 못한 버그로 인하여 힘들었지만 팀원들과 같이 로직에 대한 고민을 나누며, 더 나은 서비스를 위해 노력할 수 있어서 좋았습니다. 잘 알지 못해도 서비스의 질향상을 위해 기꺼이 공부해서 서로 알려주고 도입하려 했던 팀원들의 노력들이 있기에 더욱 행복했던 프로젝트였습니다. <br>
