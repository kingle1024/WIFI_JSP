# 제작 기간
2022.07.06 ~ 07.27

# 설명
서울시 Open API를 활용하여 약 17,800건의 WIFI 정보를 저장하고
나의 근처에 있는 WIFI를 보여주는 . 

# 구현 기능
- Open API 와이파이 정보 가져오기  
  - 약 17,800건의 서울시 WIFI 정보를 저장합니다.
  - 저장한 갯수를 화면에 표시해줍니다.
  - list.jsp -> SaveWifiList -> WifiService.java
- 근처 WIFI 정보 보기  
  - 입력한 좌표 LAT, LNT 기반으로 가까운 순서로 20개 보여줍니다. 
  - LAT, LNT가 없으면 오류 Alert을 띄워줍니다.
  - 저장된 WIFI 정보가 없으면 오류 Alert을 띄워줍니다.
  - list.jsp -> GetWifiList.java -> WifiService.java
- 위치 히스토리 목록
  - 근처 WIFI 검색한 LAT, LNT 히스토리를 보여줍니다.
  - 히스토리를 삭제할 수 있습니다. 
  - detail.jsp -> HistoryService.java, DeleteHistory.java
  
# 기술 스택
- JSP, Gradle, Maria DB
- JDK : 11
- Gson : 2.9.0
- okhttp : 4.9.3
