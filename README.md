**사전과제2. 생태 정보 서비스 API개발**
--
---


**1. 개발 프레임워크**
* Java 1.8.x
* SpringBoot 2.x.x
* JPA
* lombok
* h2
* jjwt
* junit
* jackson

---

**2. 문제해결 전략**
* 서비스 지역 코드 컬럼을 추가하여 PK로 정하기 위해 예제의 reg(문자열)+0001(숫자네자리)을 참조하여, JPA 기본 키 매핑 전략 중 Table 전략을 사용하여 ID 설정
* 문제 내용을 확인해보니 하나의 테이블로는 해당 API 구현이 불가하다 판단하였고 지역과 프로그램을 나눠서 다대다(M:N) 관계 두 개의 테이블을 만들어서 진행
* 정규표현식의 패턴을 이용하여 같지만 다르게 표현된 지역들을 하나로 통합
* JWT는 interceptor에서 Header의 Authorization 토큰 값을 체크하여 유효한 토큰일 경우 API 사용이 가능하도록 적용
* 소스 상의 JWT는 만료기간을 주석처리해둠
 

---
**3. 빌드 및 실행 방법**
1. 개발 사항
 * 기본 문제(필수): 완료
 * 선택 문제(옵션): 미완료
 * 기본 제약사항(필수): 완료
 * 추가 제약사항(옵션): 완료    

2. 빌드
 * gradle build

3. 실행 방법
 * PORT는 8080으로 설정 되어있음
 * 해당 프로젝트에서 gradle bootRun 실행
 * 또는, 빌드 완료 후 java -jar ./build/libs/ecoInfoService-0.0.1-SNAPSHOT.jar --server.port=[PORT] 실행
 * 빌드 불가 시, https://github.com/yblee1029/jar 해당 경로에서 jar 파일을 다운로드 받을수 있음
 * DB 확인: http://localhost:8080/h2-console
---

**4. API 정보**
1. JWT 발급 후 Header의 Authorization에 Token 값을 넣어서 API를 호출을 해야 함  

2. JWT signup 계정 생성 API
    * POST /jwt/signUp
    * Content-Type: application/json
    * Body: { "userid":String, "password":String }  
   
3. JWT signin 로그인 API
    * POST /jwt/signIn
    * Content-Type: application/json
    * Body: { "userid":String, "password":String }  
   
4. JWT refresh 토큰 재발급 API 
    * POST /jwt/refresh
    * Content-Type: application/json
    * Header: Authorization: Bearer Token
    * Body: { "token":String }  

5.  데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 
    * POST /region/importCSV
    * Content-Type: multipart/form-data
    * Header: Authorization: token
    * Body: { "file":file } 

6. 생태 관광정보 지역 목록 조회 API 
    * GET /region
    * Header Authorization: token

7. 서비스 지역 코드를 기준으로 지역 조회 API 
    * GET /region/{regionId}
    * Header: Authorization: token

8. 생태 관광정보 데이터 추가 API
    * POST /program
    * Content-Type: application/json
    * Header: Authorization: token
    * Body: { "prgmDetails":String, "prgmIntro":String, "prgmName":String, "serviceRegion":String, "theme":String }
    
9. 생태 관광정보 데이터 수정 API
    * PATCH /program/{programId}
    * Content-Type: application/json
    * Header: Authorization: token
    * Body: { "prgmDetails":String, "prgmIntro":String, "prgmName":String, "serviceRegion":String, "theme":String }
    
10. 생태 관광지 중에 서비스 지역 컬럼에서 특정 지역에서 진행되는 프로그램명과 테마를 출력하는 API
    * POST /search/region
    * Content-Type: application/json
    * Header: Authorization: token
    * Body: { "region":String }
    
11. 생태 정보 데이터에 프로그램 소개 컬럼에서 특정 문자열이 포함된 레코드에서 서비스 지역 개수를 세어 출력하는 API
    * POST /search/keyword
    * Content-Type: application/json
    * Header: Authorization: token
    * Body: { "keyword":String }
    
12. 모든 레코드에 프로그램 상세 정보를 읽어와서 입력 단어의 출현빈도수를 계산하여 출력 하는 API
    * POST /search/keywordCount
    * Content-Type: application/json
    * Header: Authorization: token
    * Body: { "keyword":String }
