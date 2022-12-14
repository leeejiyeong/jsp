/*** noticeAjax.js ***/

console.log('abcde'.slice(-2)); //문자열 자르기. 숫자가 하나면 시작값. 음수는 뒤에서부터 자른다
Date.prototype.yyyymmdd = function () { //자바스크립트는 자바와 다르게 임의로 라이브러리를 만들어서 사용할수 있다. yyyymmdd이름의 라이브러리를 만든것임
	let y = this.getFullYear();
	let m = this.getMonth() + 1;
	let d = this.getDate();
	let ymd = y + '-' + ('0' + m).slice(-2) + '-' + ('0' + d).slice(-2);
	return ymd;
};

console.log('바뀜');
let today = new Date(); //공지사항의 날짜를 오늘 날짜로 지정

//자바스크립트가 html보다 먼저 실행되면 값을 불러올수 없어서 에러가 나기 때문에 그것을 방지하기 위한 dom을 불러올때 실행되는 init함수에 다 넣어준다.
document.addEventListener('DOMContentLoaded', init);

function init() {
	//데이터 조회시
	const xhtp = new XMLHttpRequest();
	xhtp.open('GET', 'noticeListAjax.do'); //MOCK_DATA.json
	xhtp.send();
	xhtp.onload = function () {
		//json -> javascript 
		let data = JSON.parse(xhtp.response); //서버에 요청하면 서버에는 json페이지를 받아올 방법이 없기 때문에 json을 파싱해서 받아와야함
		console.log(data);

		//thead - 타이틀 추가
		table.initData = data;
		table.showField = ['noticeId', 'noticeWriter', 'noticeTitle', 'noticeDate', 'noticeHit', 'noticeFile'];
		let tbl = table.makeTable(); //table, thead, tbody

		document.getElementById('show').append(tbl);

		//제목칸에 삭제버튼 넣기
		let th = document.createElement('th');
		th.innerText = '삭제';
		let th2 = document.createElement('th');
		th2.innerText = '수정';
		document.querySelector('#show thead tr').append(th, th2); //show라는 아이디를 가진것 밑의 thead에 th를 붙인다

		//tbody - 삭제 버튼 추가
		document.querySelectorAll('#show tbody tr').forEach(item => { //tr을 반복적으로 찾아와서 그 tr에 버튼을 달아줄것

			item.append(addDelBtn()); //item(tr)에 td붙이기 //같은거 밑에도 반복되어서 함수로 빼버림

			//수정버튼추가
			item.append(addModBtn());
		})

	}
	//입력처리
	document.querySelector('form[name=ajaxFrm]').addEventListener('submit', addMultiNotice);
} //end of init

function addDelBtn() {
	let td = document.createElement('td');
	let btn = document.createElement('button');
	//삭제버튼에 이벤트 넣어주기
	btn.addEventListener('click', delNotice)
	btn.innerText = '삭제'; //버튼에 라벨 붙이기
	td.append(btn); //td에 버튼 붙이기

	return td;
}

function addModBtn() {
	td = document.createElement('td');
	btn = document.createElement('button');
	btn.innerText = '수정';
	btn.addEventListener('click', modNotice);
	td.append(btn);

	return td;
}

function addMultiNotice(e) {
	e.preventDefault();

	let formData = new FormData(); //FormData객체 :  multipart를 사용하는데 편리한 객체
	let writer = document.getElementById('writer').value;
	let title = document.getElementById('title').value;
	let subject = document.getElementById('subject').value;
	let nfile = document.getElementById('nfile');

	formData.append('noticeWriter', writer);
	formData.append('noticeTitle', title);
	formData.append('noticeSubject', subject);
	formData.append('noticeDate', today.yyyymmdd());
	formData.append('nfile', nfile.files[0]);

	const xhtp = new XMLHttpRequest();
	xhtp.open('post', 'noticeAddAjax.do');
	xhtp.send(formData);
	xhtp.onload = function () {
		console.log(xhtp.response);
	}


}

//key:value형식으로 입력. 
function addNotice(e) {
	e.preventDefault(); //submit의 기본기능 차단
	let writer = document.getElementById('writer').value;
	let title = document.getElementById('title').value;
	let subject = document.getElementById('subject').value;

	const xhtp = new XMLHttpRequest();
	xhtp.open('POST', 'noticeAddAjax.do'); //post방식
	xhtp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhtp.send('writer=' + writer + '&title=' + title + '&subject=' + subject + '&noticeDate=' + today.yyyymmdd());
	xhtp.onload = function () {
		let data = JSON.parse(xhtp.response);
		console.log(data); // = {noticeId:?, noticeWriter:?,...}	

		//반환된 값을 활용해서 화면 출력
		let tr = document.createElement('tr');
		table.showField.forEach(item => {
			let td = document.createElement('td');
			td.innerText = data[item];
			tr.append(td);
		})
		//새로 글 입력할때도 삭제버튼 생기게하기(이거 안써주면 글 새로 쓸때는 삭제버튼 안생김)
		tr.append(addDelBtn());

		//새로 글 추가할때도 수정버튼 생기게하기
		tr.append(addModBtn());

		document.querySelector('#show tbody').prepend(tr); //글 새로쓰면 제일 위에 추가 됨(append쓰면 맨 마지막에 추가됨)
	};
}

//삭제버튼에 이벤트 넣어주기 함수(이벤트 핸들러)
function delNotice() {
	this.parentElement.parentElement.firstChild
		.innerText; // this = btn. btn의 부모요소(td)의 부모요소(tr)의 첫번째자식요소(첫번째td:noticeId)의 텍스트내용
	let id = this.parentElement.parentElement.firstChild.innerText;

	const xhtp = new XMLHttpRequest();
	xhtp.open('post', 'noticeDelAjax.do');
	xhtp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhtp.send('id=' + id);
	xhtp.onload = delCallback;
}

//수정 버튼 이벤트 핸들러
function modNotice() {

	let oldTr = this.parentElement.parentElement;
	let clone = this.parentElement.parentElement.cloneNode(true);
	console.log(clone);
	let writer = clone.children[1].innerText;
	let title = clone.children[2].innerText;
	let date = clone.children[3].innerText;

	//oldTr의 td를 clone의 td로 대체
	let td1 = document.createElement('td'); //<td><input value=?></td>
	let inp1 = document.createElement('input');
	inp1.value = writer; //input태그의 value속성은 writer
	td1.append(inp1);
	clone.children[1].replaceWith(td1); //writer칸을 input태그로 변경// clone.children[1] = td// td를 td1로 교체

	let td2 = document.createElement('td');
	let inp2 = document.createElement('input');
	inp2.value = title;
	td2.append(inp2);
	clone.children[2].replaceWith(td2);

	let td3 = document.createElement('td');
	let inp3 = document.createElement('input');
	inp3.setAttribute('type', 'date');
	inp3.value = date;
	td3.append(inp3);
	clone.children[3].replaceWith(td3);

	//수정중일때 clone의 삭제버튼 비활성화 하기
	clone.children[6].firstChild.disabled = true;

	//clone의 수정을 변경으로 바꾸기(수정중일때 버튼이 변경으로 보이게)
	clone.children[7].firstChild.innerText = '변경'

	//변경버튼에 이벤트(db수정, 화면변경 : 변경을 눌렀을때 바뀐값 저장되게하기)
	clone.children[7].firstChild.addEventListener('click', updateNotice);

	oldTr.replaceWith(clone);

}

//변경처리
function updateNotice() {
	let id = this.parentElement.parentElement.firstChild.innerText; //버튼의 부모(td)의 부모(tr)의 첫번째 자식(td : noticeId)의 내용
	let writer = this.parentElement.parentElement.children[1].firstChild.value;
	let title = this.parentElement.parentElement.children[2].firstChild.value;
	let date = this.parentElement.parentElement.children[3].firstChild.value;
	let param = 'id=' + id + '&writer=' + writer + '&title=' + title + '&date=' + date;

	const xhtp = new XMLHttpRequest();
	xhtp.open('post', 'noticeUpdateAjax.do');
	xhtp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhtp.send(param);
	xhtp.onload = updateCallback;
}

function updateCallback() {
	console.log(this.response); //새로운 tr생성해서 그 tr을 다시 화면에서 찾아와서 그 id랑 값이 같은걸 대체 할것
	let data = JSON.parse(this.response); //json => javascript
	// 새로운 tr생성
	let tr = document.createElement('tr');
	table.showField.forEach(item => {
		let td = document.createElement('td');
		td.innerText = data[item];
		tr.append(td);
	})
	//새로 글 입력할때도 삭제버튼 생기게하기(이거 안써주면 글 새로 쓸때는 삭제버튼 안생김)
	tr.append(addDelBtn());

	//새로 글 추가할때도 수정버튼 생기게하기
	tr.append(addModBtn());

	//대체
	document.querySelectorAll('#show tbody tr').forEach(item => {
		if (item.firstChild.innerText == data.noticeId) {
			item.replaceWith(tr);
		}
	})
}


//삭제콜백함수(delCallback. 이벤트가 발생하면 그때 함수를 호출하는거)
function delCallback(e) {
	console.log(e);
	let result = JSON.parse(this.response); //this = xhtp
	if (result.retCode == 'Success') {
		console.log(result.id);
		document.querySelectorAll('#show tbody tr').forEach(item => {
			if (item.firstChild.innerText == result.id) {
				item.remove();
			}
		})
	} else if (result.retCode == 'Fail') {
		alert('처리 중 에러발생');
	}
};