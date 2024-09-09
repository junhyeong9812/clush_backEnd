# Clush BackEnd

## 1. 프로젝트 설명

**Clush BackEnd**는 할 일(ToDo) 관리와 일정(Calendar) 관리 기능을 제공하는 애플리케이션입니다. 사용자는 할 일을 생성, 수정, 삭제할 수 있으며, 일정을 등록하고 공유할 수 있습니다. 공유된 일정은 수락하거나 거절할 수 있으며, 수락된 일정만 사용자에게 표시됩니다. 이를 통해 사용자 간 협업 기능을 제공합니다.

주요 기능:
- **할 일(ToDo) 관리**: 할 일을 생성, 수정, 삭제, 조회할 수 있으며, 상태(PENDING, IN_PROGRESS, COMPLETED, CANCELLED)를 관리할 수 있습니다.
- **일정(Calendar) 관리**: 사용자는 일정을 생성하고, 다른 사용자에게 공유할 수 있으며, 일정은 수락 혹은 거절할 수 있습니다.(수락 혹은 거절 프론트 연동x)
- **일정 공유**: 일정을 다른 사용자와 공유하고, 수락된 일정만 조회할 수 있습니다.

## 2. 소스 빌드 및 실행 방법

### 2.1. 사전 요구사항
- **Java 17**
- **Gradle** 7.x 이상
- **MySQL** 8.x

### 2.2. MySQL 데이터베이스 설정

MySQL에서 아래의 명령어를 통해 데이터베이스와 사용자를 생성합니다.

```sql
CREATE DATABASE clush;
CREATE USER 'clush'@'%' IDENTIFIED BY 'clush';
GRANT ALL PRIVILEGES ON clush.* TO 'clush'@'%';
FLUSH PRIVILEGES;
```

2.3. application.properties 설정
src/main/resources/application.properties 파일을 아래와 같이 설정합니다:
```
properties
spring.application.name=clush_backEnd

spring.devtools.restart.enabled=true

spring.datasource.url=jdbc:mysql://localhost:3306/clush
spring.datasource.username=clush
spring.datasource.password=clush
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.orm.jdbc.bind=TRACE

logging.level.org.springframework=INFO

spring.application.timezone=Asia/Seoul
```

2.4. 빌드 및 실행
아래 명령어를 통해 프로젝트를 빌드하고 실행합니다.
```
# 프로젝트 빌드
./gradlew clean build

# 프로젝트 실행
./gradlew bootRun
```

2.5. Swagger 문서 접근
프로젝트가 실행되면 Swagger UI에서 API 명세를 확인할 수 있습니다.

3. 주력으로 사용한 컴포넌트 설명 및 사용 이유
Spring Boot: 빠른 개발과 유지보수를 위해 사용하였습니다. Spring Boot는 의존성 관리와 설정을 간편하게 해주기 때문에 RESTful API 서버 개발에 적합합니다.
Spring Data JPA: 데이터베이스와의 상호작용을 간편하게 해줍니다. 쿼리 메서드를 통해 복잡한 SQL을 작성하지 않고도 데이터를 처리할 수 있습니다.
MySQL: 안정적이고 널리 사용되는 관계형 데이터베이스로, 데이터의 영속성과 신뢰성을 위해 선택하였습니다.
Lombok: @Getter, @NoArgsConstructor와 같은 어노테이션을 사용하여 보일러플레이트 코드를 줄이고, 유지보수를 용이하게 하기 위해 사용했습니다.
Swagger: API 문서화를 위해 사용했습니다. 이를 통해 클라이언트가 API 명세를 쉽게 확인하고 테스트할 수 있습니다.


4. API 명세
4.1. 할 일(ToDo) API
- POST /api/todo/create: 할 일 생성
- GET /api/todo/{id}: 할 일 단일 조회
- PUT /api/todo/{id}/update: 할 일 수정
- DELETE /api/todo/{id}/delete: 할 일 삭제
4.2. 일정(Calendar) API
- POST /api/calendar/create: 일정 생성
- PUT /api/calendar/{id}/update: 일정 수정
- GET /api/calendar/{id}: 단일 일정 조회
- DELETE /api/calendar/delete: 일정 삭제
- POST /api/calendar/share: 일정 공유
- POST /api/calendar/event-share/{id}/accept: 일정 공유 수락
- POST /api/calendar/event-share/{id}/decline: 일정 공유 거절
4.3. 사용자(User) API
- POST /api/users/register: 회원 가입
- POST /api/users/login: 로그인
- GET /api/users/{id}: 회원 단일 조회
- DELETE /api/users/{id}: 회원 탈퇴
- GET /api/users: 회원 전체 조회
  
5. 추가 기능 설명
- 일정 공유 기능: 사용자가 일정을 다른 사용자와 공유할 수 있습니다. 공유받은 사용자는 일정을 수락하거나 거절할 수 있으며, 수락된 일정만 조회할 수 있습니다.
- 일정 상태 관리: 일정이 생성되면 PENDING 상태이며, 공유받은 사용자가 수락하면 ACCEPTED, 거절하면 DECLINED 상태로 변경됩니다.(수락 및 거절 부분을 프론트에서 구현하지 못하여 이부분은 기본 ACCEPTED로 상태로 프론트로 공유가 되어 
화면에 보이는 부분까지만 확인했습니다.)
- 할 일(ToDo) 상태 관리: 할 일은 PENDING, IN_PROGRESS, COMPLETED, CANCELLED 상태로 관리되며, 각 상태에 따른 로직을 엔티티 내부에서 처리합니다
- 
6. 데이터베이스(DB) 스키마
  
```sql

CREATE TABLE `user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `todo` (
  `todo_id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `status` VARCHAR(20) DEFAULT 'PENDING',
  `user_id` BIGINT,
  `write_date` TIMESTAMP,
  PRIMARY KEY (`todo_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `calendar_event` (
  `calendar_id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `start_time` TIMESTAMP,
  `end_time` TIMESTAMP,
  `all_day` BOOLEAN,
  `user_id` BIGINT,
  PRIMARY KEY (`calendar_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `event_share` (
  `event_share_id` BIGINT NOT NULL AUTO_INCREMENT,
  `event_id` BIGINT,
  `shared_by_user_id` BIGINT,
  `shared_user_id` BIGINT,
  `shared_at` TIMESTAMP,
  `is_viewed` BOOLEAN DEFAULT FALSE,
  `status` VARCHAR(20) DEFAULT 'PENDING',
  PRIMARY KEY (`event_share_id`),
  FOREIGN KEY (`event_id`) REFERENCES `calendar_event` (`calendar_id`),
  FOREIGN KEY (`shared_by_user_id`) REFERENCES `user` (`user_id`),
  FOREIGN KEY (`shared_user_id`) REFERENCES `user` (`user_id`)
);
```

###프론트엔드 깃주소: https://github.com/junhyeong9812/clush_front
