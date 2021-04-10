# 게시판 프로젝트

## 1) 소개

### 스프링 부트로 만든 게시판 프로젝트

## 2) 필수사항

- Java 11 이상
- Lombok

## 3) 실행방법

### 윈도우

- .\mvnw.cmd spring-boot:run

### 맥, 리눅스

- sh mvnw spring-boot:run

## 4) 기술 스택

- Spring Boot 2.4.4
- JPA
- Hibernate
- Thymeleaf
- H2
- Lombok
- Spring Security
- Querydsl
- Bootstrap v5.0

## 5) 사용법

- 3번 명령어로 최초 실행하면 application.properties에 있는

  member.user.id와 member.admin.id에 해당하는 값을 참조하여

  아이디를 설정하고 패스워드는 랜덤으로 설정하여 계정을 생성합니다.

  패스워드는 콘솔창에 출력됩니다.
