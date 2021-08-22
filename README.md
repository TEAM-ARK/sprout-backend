# 📚 Inflearn-Clone-Backend

## 📖 개요(Summary)

인프런 웹앱을 만들어보는 팀프로젝트

[Inflearn-clone-front repository](https://github.com/MinwooJJ/inflearn-clone-front)

---

<br />

## 🎯 목표(Objectives)

- 팀프로젝트 및 협업 경험
- Hard skill 및 Soft skill 향상

---

<br />

## 📆 개발기간(Develop period)

2021.07.05 ~ 진행중

---

<br />

## 💻 라이브러리 및 기술 스택(Library & Stack)

![image](https://user-images.githubusercontent.com/60773356/128631429-8ab1d060-b276-4809-ba8b-920f015d2274.png)


### 🔧 Core
- Java 11
- Spring MVC
- Spring Boot
- JPA
- Spring Data JPA
- Querydsl
- Spring Security

### 🔧 Build Tool
- Gradle

### 🔧 Database
- MySQL

### 🔧 AWS
- EC2
- S3

------------------------------------------
## 엔티티 연관 관계
### user와 lecture(지식 공유자)
`1:N 양방향 관계`, `lecture`의 `instructor`를 위한 관계

### user와 lecture(일반 회원)
`N:M 양방향 관계`, `course`라는 매핑 테이블을 중간에 두고 `1:N + N:1`로 풀어서 설계, 수강 중인 강좌를 위한 관계

### user와 review
`1:N 단방향 관계`

`review`에서만 `user`를 참조가능(리뷰글에 user의 이름)

### lecture와 video
`1:N 단방향 관계`

`lecture`에서만 `video`을 참조가능

### lecture와 review
`1:N 단방향 관계`

`lecture`에서만 `review`를 참조가능

### lecture와 discount
`1:1 양방향 관계`

### category
다른 테이블과 관계를 맺지 않음. `계층형 테이블 구조`로써 상위 카테고리에 하위 카테고리가 포함됨

### lecture와 lecture_hashtag
`N:M 관계`를 풀어서 사용하기 위한 `중간 관계`

`1:N 양방향 관계`

### lecture_hashtag와 hashtag
`N:1 단방향 관계`

`lecture_hashtag`에서만 `hashtag`를 참조가능










