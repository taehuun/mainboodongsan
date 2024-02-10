# 🏠 _마인부우동산_ 🏠

똑똑한 내 집 마련의 시작.  
주택 거래 정보 플랫폼

<br><br>![메인화면(관리자)](https://github.com/taehuun/mainboodongsan/assets/39759138/65b48ef7-2e19-4061-a24b-b122514526c8)


<h2> 1️⃣ 기획배경 </h2>

_이 곳 저 곳 비교하지 않고, 한 곳에서 원하는 조건의 집을 찾을 수 없을까?_
<br>

사용자가 찾고자 하는 지역(시/구/동)별 아파트 매매/거래 정보를 제공하자

<br>

## 2️⃣ 목표

1. 다양한 매물 정보 검색
2. 사용자 맞춤 서비스 제공
   - 관심 매물, 관심 동네를 등록하고 한 눈에 모아볼 수 있도록 한다.
3. 유저간 자유로운 소통을 위한 커뮤니티 게시판 기능
   - 이사갈 동네를 비교하고 선택하는데 도움을 준다
   - 지역 커뮤니티 활성화에 기여한다.
   <br>


## 3️⃣ 개발 환경 & 기술 스택

- 운영체제 : Window 10, Mac OS X
- Front : Vue.js, Vue-CLI, Vue-router, Vuex, axios, HTML5, CSS5, BootStrap5, JavaScript
- Back : SpringBoot, MyBatis, JSON
- Database : MySQL
- Etc : Git, Gitlab
  <br>

## 5️⃣ 프로젝트 설계

### Client (Vue)

```
mainbooHouse
.
img_readme/
public/
src/
    api/
         board.js
         community.js
         houseApi.js
         memberApi.js
    assets/
        css/
        img/
    components/
         board/
         item/
            BoardFormItem.vue
            BoardListItem.vue
         BoardDetail.vue
         BoardList.vue
         BoardModify.vue
         BoardWrite.vue
        ...
    routers/
        index.js
    store/
         counter.js
         memberStore.js
    util/
        http-commons/
    views/
         CommunityView.vue
         HomeView.vue
         HouseView.vue
         JoinView.vue
         LoginView.vue
         MyPageView.vue
         TheNoticeView.vue
    App.vue
    main.js
package.js
package-lock.json
README.md

```

### Server (Spring Boot)

```
myhouse
.
src/
    main/
        java/
            com.ssafy.house/
                controller/
                    BoardController.java
                    CommunityController.java
                    HouseController.java
                    MemberController.java
                dto/
                    BoardDto.java
                    CommunityDto.java
                    ...
                exception/
                    UnAuthorizedException.java
                mapper/
                    BoardMapper.java
                    CommunityMapper.java
                    ...
                service/
                    BoardService.java
                    BoardServiceImpl.java
                    ...
                util/
                    JWTUtil.java
                MyhouseApplication.java
        resources/
            mapper/
                board.xml
                community.xml
                ...
            application.xml
    test/
...

```

1. ERD
   ![erd](https://github.com/taehuun/mainboodongsan/assets/39759138/2917b34c-65c0-4dfa-aaf8-b7eede8a9295)

2. 기능정의서
   ![기능정의서](https://github.com/taehuun/mainboodongsan/assets/39759138/c698676c-0ced-4fdd-86ab-8d93fea3cea1)
   ![기능정의서2](https://github.com/taehuun/mainboodongsan/assets/39759138/2f8d8049-3309-4d3c-9ef4-97eafcfcfd8a)




<br>
<br>

<br>


## 8️⃣ 발표 자료 / 시연 동영상

![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_1](https://github.com/taehuun/mainboodongsan/assets/39759138/4df06ffd-2c4a-4198-b8bc-5ef9304bf0af)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_2](https://github.com/taehuun/mainboodongsan/assets/39759138/a2a868e2-bff5-4cf2-89fc-adcfdbd9c111)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_3](https://github.com/taehuun/mainboodongsan/assets/39759138/f87c7391-8a0a-4ad7-9c68-e5f742934ec1)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_5](https://github.com/taehuun/mainboodongsan/assets/39759138/926c9e0d-52b9-4f41-a95d-9a9000495b75)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_6](https://github.com/taehuun/mainboodongsan/assets/39759138/38e1da7f-1bb9-408c-9c62-21ac0a81a850)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_7](https://github.com/taehuun/mainboodongsan/assets/39759138/9a54e97a-5267-4174-969c-ea17485aaaf2)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_8](https://github.com/taehuun/mainboodongsan/assets/39759138/00de7956-c997-4b49-ac8d-48f950577efa)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_9](https://github.com/taehuun/mainboodongsan/assets/39759138/6db90ef3-1a6d-48ca-9fcc-fb455430f171)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_10](https://github.com/taehuun/mainboodongsan/assets/39759138/37dfffcb-a907-402e-8dc6-dca214ba0e38)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_11](https://github.com/taehuun/mainboodongsan/assets/39759138/3b3105ce-e6e8-444c-82ef-2323e34fa6ba)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_12](https://github.com/taehuun/mainboodongsan/assets/39759138/f8029bc1-b302-4b72-acd9-21a15cd3eb41)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_13](https://github.com/taehuun/mainboodongsan/assets/39759138/69e60e81-4607-4d09-8fea-f34955d9e903)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_14](https://github.com/taehuun/mainboodongsan/assets/39759138/9be05534-9c22-4d40-af88-60c2af262838)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_15](https://github.com/taehuun/mainboodongsan/assets/39759138/111ab483-5f5d-41b0-8a5c-2e58c2f06341)
![1707577113703-2a763d07-b2a4-48e2-86a4-f378442d202b_17](https://github.com/taehuun/mainboodongsan/assets/39759138/a19b5115-f30d-4c87-95cf-da0b8ca6a5e8)



### 시연영상
https://www.youtube.com/watch?v=YglzT93UBTU
