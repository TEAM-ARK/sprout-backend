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
* user와 review : 단방향 연관관계 1:N 
	- user테이블에서 review를 알고 있을 필요가 없음(실제로 인프런에 내가 쓴 후기보는 기능은 없음)
* user와 lecture : 양방향 연관관계 1:N

* review와 lecture : 양방향 연관관계 N:1

* lecture와 video : 단방향 연관관계 1:N

* lecture와 lecture_hashtag : 양방향 연관관계 1:N

* lecture_hashtag와 hashtag : 단방향 연관관계 1:1










