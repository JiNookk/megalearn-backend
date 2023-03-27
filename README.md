# Megalearn-backend
## 프로젝트 소개
megalearn은 누구나 간편하게 강의를 시청하고 공유할 수 있는데 초점을 두고 만든 웹 서비스 입니다.

<img width="366" alt="image" src="https://user-images.githubusercontent.com/82752544/227905063-f3426595-d306-45aa-b134-0618314ca67c.png">

테스트 로그인을 통해 간편하게 로그인할 수 있습니다!

### 배포 URL
- 사용자 : [https://megalearn-frontend.fly.dev/](https://megalearn-frontend.fly.dev/)
- 어드민 : [https://megalearn-admin.fly.dev/](https://megalearn-admin.fly.dev/)

### Github
- 서버 : [https://github.com/JiNookk/megalearn-backend](https://github.com/JiNookk/megalearn-backend)
- 사용자 프론트 : [https://github.com/JiNookk/megalearn-frontend](https://github.com/JiNookk/megalearn-frontend)
- 어드민 프론트 : [https://github.com/JiNookk/megalearn-admin](https://github.com/JiNookk/megalearn-admin)

## 개발 기간
- 2022.11.26 ~ 2022.01.20
- 일주일 간격으로 8번의 스프린트를 진행했습니다.
  <br>

## 기술 스택
### Backend
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"></a>
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">

### Database
<img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgreSQL&logoColor=white"/>


<br>

## 주요 기능

- 강의 시청
- 질문 등록
- 노트 작성
- 강의 등록(판매)
- 문의 작성
- 강의 판매
- 강의 결제
- 리뷰 작성
- 로그인 / 회원가입

### 관리자

- 강의 관리
- 스킬태그 관리
- 강의 카테고리 관리

<br>

## 아키텍쳐
Layered Architecture

1. UI Layer
2. Application Layer
3. Domain Layer
4. Infrastructure Layer

<br>

## 백엔드 프로젝트 특징

*#Java, Spring Framework*

### 아키텍처

백엔드 아키텍처는 레이어드 아키텍처를 참고해서 관심사에 따라 4개의 계층으로 분리했습니다.

상위 레이어는 하위 레이어에 의존하고, 하위 레이어는 상위 레이어를 알지 못하도록 구조를 만들었습니다.

UI Layer

- 관심사: 사용자와 소프트웨어 시스템의 상호작용을 할 수 있는 진입점을 마련해줍니다.
- 아키텍처의 최상위 계층이며 이름에서 볼 수 있듯이 사용자가 직접 상호 작용하는 계층입니다.

Application Layer

- 관심사: 모든 기능 목록을 드러내고, 도메인 객체들이 협력할 수 있는 진입점을 마련해줍니다.
- 도메인 계층과 추상화 된 인프라 계층이 존재합니다.

Domain Layer 

- 관심사: 어플리케이션의 핵심 비즈니스 로직을 수행합니다.
- 엔티티와 값객체를 포함한 도메인 모델이 모여있으며, 비즈니스 로직을 검증하기 위한 테스트코드들이 모여있습니다.

Infrastructure Layer

- 관심사: 다른 계층을 지원하는데 필요한 기술 인프라를 제공합니다.

<br>

### 도메인 레이어의 단위 테스트

리팩토링시 쉽게 에러를 발견할 수 있도록 Junit을 이용한 단위테스트를 도입했습니다. 

처음에는 **Domain Layer**가 아닌 Application Layer와 UI Layer에 비즈니스 로직이 위치했습니다.

Domain Layer 이외의 다른 레이어에서 단위 테스트를 작성하려니 모킹에 너무 많은 비용이 들어 테스트 코드를 작성하는데 어려움을 겪었습니다.

무언가 잘못되고 있다는 느낌이 들어서 트레이너 분과 주변 동료분들과 고민한 부분을 의논해보았고 그 결과 구조를 크게 뜯어고치는 결정을 내리게 되었습니다.

먼저 [UI Layer → Application Layer](https://wookisdevelopinggg.tistory.com/192)로 비즈니스 로직을 옮겼고 
그 다음에는 [Application Layer → Domain Layer](https://wookisdevelopinggg.tistory.com/193)로 비즈니스 로직을 옮기는 작업을 진행했습니다.

<img width="312" alt="스크린샷 2023-02-21 오후 3 30 16" src="https://user-images.githubusercontent.com/82752544/227884163-ea933682-82a6-4109-9ae3-17b5e23131f0.png">

도메인 레이어에 비즈니스 로직을 전부 옮긴뒤 도메인모델의 로직 검증을 위한 테스트를 집중적으로 작성했습니다.

검증을 만족시키는 테스트 커버리지 기준은 90%로 정했습니다.

도메인 레이어에서 집중적으로 테스트를 한 결과 다음의 측면에서 더 나은 코드를 작성할 수 있었습니다.

- 각 레이어마다 관심사가 더 명확하게 분리되었습니다.
- 도메인레이어에서는 테스트에 필요한 모킹이 줄어들어 훨씬 효율적으로 테스트를 작성할 수 있었습니다.
- 하위 레이어의 테스트를 통해 상위 레이어의 신뢰도를 높일 수 있었습니다.

<br>

### 도메인 모델 설계

객체지향의 사실과 오해에서 읽은 내용에 주목하여 객체간의 협력에 초점을 맞추고 도메인 모델 설계를 진행했습니다. 객체간의 협력을 구성하기 앞서 먼저 객체들간의 관계를 그려보았습니다.

객체간의 관계를 확인한 뒤, 해결해야 할 **메시지**를 작성하고 메시지를 수행하기에 가장 적합한 객체를 선택했습니다.

<img width="507" alt="스크린샷 2023-02-13 오후 2 47 54" src="https://user-images.githubusercontent.com/82752544/227884750-38856cd4-b81c-4dfe-bc5b-79dc7d6e3f0c.png">

각 메시지를 인터페이스로 가지는 객체를 발견할 수 있었고 이를 바탕으로 클래스 다이어그램을 작성했습니다.

협력을 먼저 고려한 결과 이전보다는 좀 더 객체지향에 가까운 코드를 작성할 수 있었습니다.

<br>

### 값객체를 이용한 예외처리

강의의 가격을 정하면서 무료 또는 최소가격을 1만원으로 정해야 하도록 선택했습니다. 엔티티에서 가격에 대한 예외처리를 진행하게 되면 엔티티의 코드가 너무 비대해지는 문제점이 발생했습니다.

책임을 분리해주기 위해서 Price라는 값객체를 생성해서 적절한 가격인지에 대해서 예외처리할 수 있도록 코드를 작성했습니다. 이 외에도 값객체가 필요한 곳들을 따로 분리하니 다음과 같은 장점이 있었습니다.

- 값객체 내부에서 예외처리를 해결할 수 있었습니다.
- 엔티티의 필드를 값객체로 대체할 때 가독성이 더 나아졌습니다.

<br>

### Backdoor API

매주 스프린트를 진행하면서 한 주간 만든 결과물을 시연하는 시간을 가졌습니다. 매주 새로운 기능을 선보이기 위해서 작업의 속도를 어떻게 하면 높일 수 있을까 고민을 해보았습니다.

REST API를 작성할 때 POST API → GET API의 순서로 작업을 진행했는데, 데이터를 먼저 세팅하고 GET API 먼저 사용할 수 있다면 작업 속도도 빨라지고, 좀더 빠르게 피드백 받을 수 있다는 판단이 들어서 백도어 API를 도입했습니다.

기존에 POST API를 이용해서 데이터를 세팅했던 방식에서 백도어 API를 이용하여 필요한 데이터를 빠르게 채워넣으니 다음과 같은 이점이 있었습니다.

- POST API 작성 시간과 데이터 입력시간을 줄여 빠른 피드백을 받을 수 있었습니다.
- 매주 작업물 시연, 인수테스트를 위한 데이터 세팅을 간소화 할 수 있었습니다.
