# cs-backend
고객 문의 서비스를 위한 API를 제공하는 프로젝트 입니다.

## 프로젝트 구성
Spring Boot + Java
### 구조
- configuration : 설정 정보
- controller : 컨트롤러
- queue : 큐(producer, receiver)에 메세지 추가, 처리를 위한 로직
- repository : JPA를 통한 데이터 조작을 위한 저장소 인터 페이스 정의
- service : 비즈니스 로직이 있는 서비스
- vo : value object
- vo.entity : DB 테이블 관련 클래스
- vo.enums : enum (값의 범위를 한눈에 보기 위함, 범위 이외의 값을 사용 하여 발생하는 이슈를 방지하기 위함)
- vo.validation : 값 유효성 체크를 위한 클래스
### 사용 기술
- 큐
> activemq
- 캐싱
> spring cache
- API 규격서
> swagger
- DB 저장소 및 처리
> h2
> jpa
- 테스트
> junit
- API 파라미터 유효성 체크
> spring validation
- 사용자 패스워드 암호화
> bcrypt
- 개발 도구
> gson
> lombok
> commons-lang
## 실행 방법
### 어플리케이션 실행
```
 $ ./gradlew bootRun
```
### API 규격서 (swagger UI)
- [swagger-ui 바로 가기](http://localhost:8080/swagger-ui/index.html)
- 프로젝트 기동시 접속 가능하며 swagger UI 에 접속하여 규격서 확인 및 API 호출 테스트 가능 합니다.
### DB 웹 콘솔
- [h2-console 바로 가기](http://localhost:8080/h2-console)
- 프로젝트 기동시 접속 가능하며 웹에서 DB 연결하여 테스트 가능합니다.
- ID/PW (admin/admin@1234)
## 문제 해결 전략
- DB, 큐, 캐시 모두 Embedded를 사용하여 따로 설치 없이 이용 가능합니다.
- 단, 디스크가 아닌 메모리에 올려 처리하기에 재기동시 기존에 생성한 데이터는 사라지는점 참고 바랍니다.
### 데이터 조회
- 고객 사용 페이지 접속시 모든 데이터를 조회 하지 않고, 원하는 고객 ID를 입력 해야 조회 가능하도록 하여 불 필요한 데이터 조회를 하지 않아 DB 부하를 줄였습니다.
- DB 부하를 줄이기 위해 조회성 API 대해 캐싱 처리 하였습니다.
- 단, 개인화 데이터의 경우 캐싱을 하지 않았으며 그 이유는 아래와 같습니다.
- 개인화 데이터인 고객 문의 조회의 경우 각 개인별로 호출량이 많지 않을 것으로 보여 캐싱 처리시 이점 보다 많은 캐싱 데이터로 인한(개인별로 캐싱 해야하므로) 메모리 낭비가 더 크므로 개인화 데이터는 캐싱하지 않았습니다. 
### 데이터 등록
- 등록 전에 필요한 데이터가 있는지 파라미터 유효성 체크 하며, 유효 하다면 처리 하여 불필요한 DB 조회를 방지 하였습니다.
- 등록시 처리 성능에 따라 순차적으로 처리하기 위해 큐를 사용하여 처리 하였습니다.
- 서버 부하로 처리 시간이 지연 될 경우 사용자는 대기 하지 않아도 됩니다. (큐에 넣고 바로 응답)
- 데이터 등록전 등록 가능 여부 체크 로직이 있어 이전에 등록 된게 있다면 중복 등록 되지 않습니다. 
### 사용자 정보 보안 이슈
- 사용자 개인 정보인 패스워드의 경우 암호화 하여 DB에 저장 하여 정보 보호 하였습니다.