<h1> DB dump</h1><br>
1. 'whereismyhome' 이라는 이름으로 schema를 생성<br>
2. 상단의 Server>Data Import > Import from Self-Contained File > Home_Spring_KimTaeHun_HanJeongSu_DB_Dump.sql 을 선택 <br>
3. Default Target Schema > whereismyhome을 선택 <br>
4. Start Import <br>
<h1>1. 아파트 정보 </h1><br>

<h3>1-1. 최초 "시도" 리스트 가져오기 <br></h3>
최초로 웹이 실행될 경우, 사용자에게 선택 가능한 "시도"명을 출력해 보여줍니다.
사용자가 "시도"명을 입력했을 경우, 해당 시도에 포함되어 있는 "구군" 리스트를 이어서 선택할 수 있도록 출력하고,
마지막으로 입력한 구군에 포함되어 있는 "동" 리스트를 보여주어 선택하도록 합니다. <br>
![image.png](./image.png)<br>
<h3>1-2. "시도"에 따른 "구군" 가져오기 <br></h3>
![image-1.png](./image-1.png)<br>
<h3>1-3. "구군"에 따른 "동" 가져오기<br></h3>
![image-2.png](./image-2.png)<br>
<br>
<br>

<h3>1-4. 동코드, 거래년월 입력시 해당하는 아마트 매매 정보를 가져오기<br></h3>
사용자가 "시도", "구군", "동"을 입력했을 경우 해당 지역의 동코드 값을 특정할 수 있습니다.
사용자가 입력한 주소와 일치하는 동코드 값과 "거래연도" 및 "거래월" 값을 입력받았을 때 해당하는 아파트 매매 정보를 가져와 출력하도록 합니다. <br>
![image-3.png](./image-3.png)<br>
<br>
<br>
<h1>2. 회원정보<br></h1>
<h3>2-1. 로그인 및 해당 회원정보 가져오기<br></h3>
이메일을 입력하였을 경우, 동일한 이메일을 가진 사용자가 존재한다면 그 사용자의 정보를 보여줍니다. <br>
![image-4.png](./image-4.png)<br><br>
<h3>2-2. 회원 등록<br></h3>
사용자로부터 이메일, 비밀번호, 이름 등의 데이터를 받아와 회원정보를 DB에 등록합니다.<br>
![image-5.png](./image-5.png)<br><br>
![image-6.png](./image-6.png)<br><br>
<h3>2-3. 회원 정보 수정<br></h3>
사용자의 email값을 확인하여 해당하는 사용자의 정보에서 이름 및 전화번호와 같은 데이터를 수정합니다. <br>
![image-7.png](./image-7.png)<br><br>
![image-8.png](./image-8.png)<br><br>
<br>
<h1>3. 공지사항(게시판)<br></h1>
<h3>3-1. 글 목록 가져오기<br></h3>
현재까지 작성된 게시판 글들을 가져옵니다.
![image-9.png](./image-9.png)<br><br>
<h3>3-2. 글 작성하기<br></h3>
작성중인 사용자의 이메일로 글을 작성합니다.<br>
이때, 게시판 글의 email(작성자) 정보는 회원들의 정보가 담긴 table의 email을 외래키로 참조하고 있기 때문에, 입력한 작성자의 이메일이 회원으로 등록되어 있어야합니다.<br>
![image-10.png](./image-10.png)<br>
![image-11.png](./image-11.png)<br><br>
<h3>3-3. 해당 글 가져오기(보기)<br></h3>
선택한 번호에 해당하는 글을 가져와 보여줍니다.<br>
![image-12.png](./image-12.png)<br><br>
<h3>3-4. 글 수정하기<br></h3>
선택한 번호에 해당하는 글의 제목 및 내용을 수정합니다.<br>
![image-13.png](./image-13.png)<br>
![image-14.png](./image-14.png)<br><br>
<h3>3-5. 글 삭제하기<br></h3>
선택한 번호의 글을 삭제합니다.<br>
![image-15.png](./image-15.png)<br>
![image-16.png](./image-16.png)<br><br>
<h3>3-6. 조회수 업데이트<br></h3>
선택한 번호의 글의 조회수를 업데이트합니다.<br>
![image-17.png](./image-17.png)<br>
![image-18.png](./image-18.png)<br><br>
