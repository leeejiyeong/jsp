/**
 * noticeAjaxFetch.js : fetch api를 활용해서 ajax와 똑같이 만들어본다
 */
Date.prototype.yyyymmdd = function() { //자바스크립트는 자바와 다르게 임의로 라이브러리를 만들어서 사용할수 있다. yyyymmdd이름의 라이브러리를 만든것임
	let y = this.getFullYear();
	let m = this.getMonth() + 1;
	let d = this.getDate();
	let ymd = y + '-' + ('0' + m).slice(-2) + '-' + ('0' + d).slice(-2);
	return ymd;
};

console.log('바뀜');
let today = new Date(); //공지사항의 날짜를 오늘 날짜로 지정

console.log('noticeAjaxFetch start.')

//보여줄 항목 지정
const showFields = {
	noticeId: "공지번호",
	noticeWriter: "작성자",
	noticeTitle: "제목",
	noticeDate: "작성일자",
	noticeHit: "조회수"
}
for (const prop in showFields) {
	showFields[prop]
}

const addColumn = {		//object안에 값이 배열로 들어온것
	col1: ['button', '삭제'],	//element 유형으로 지정, 라벨이 필요함
	col2: ['button', '수정'],
	col3: ['input', 'checkbox']		//input타입은 라벨이 필요없음. type속성을 사용함
}

// addColumn.[prop][1]

//테이블 생성하는 함수
function makeTable(aryData = [], parent) {	//aryData의 데이터 건수만큼 보여줌
	let tbl = document.createElement('table')
	tbl.setAttribute('border', 1);
	let thd = document.createElement('thead')
	let tbd = document.createElement('tbody')
	let tr = document.createElement('tr')

	let fields = showFields;	//보여줄 항목을 저장(전체항목) //aryData[0]를 showFields로 바꿔줌
	//head영역
	for (const field in fields) {		//보여줄 항목만큼 만들어서 thead에 붙여줌
		let th = document.createElement('th')
		th.innerText = fields[field]
		tr.append(th);
	}
	//버튼 제목에 추가
	for (const col in addColumn) {
		let th = document.createElement('th')
		//th.innerText = addColumn[col][1];	//위에 정의한 addColumn에서 col의 배열 2번째(인덱스는 1)가 제목에 들어가야 하기 때문
		th.innerHTML = addColumn[col][0] == 'button' ? addColumn[col][1] : '<input type="checkbox">';
		//↑ 삼항연산자 사용(addColumn의 배열 첫번째가 button 이면 이름반영, 아니면 input의 checkbox)
		tr.append(th);
	}

	thd.append(tr);
	tbl.append(thd);

	//body영역
	for (const data of aryData) {		//하나씩 들어오는 값 == data
		let tr = makeTr(data);
		tbd.append(tr);
	}
	tbl.append(tbd);

	parent.append(tbl);		//생성된 테이블 요소를 매개값으로 들어온 위치에 보여주기 위해서 append하는거임
}

function makeTr(data) {
	tr = document.createElement('tr')
	for (const field in showFields) {	//fields는 다른 함수안에 있기 때문에 사용불가. showFields로 바꿔서 사용한다
		let td = document.createElement('td')
		td.innerText = data[field]
		tr.append(td)
	}
	//버튼 추가항목
	for (const col in addColumn) {
		let td = document.createElement('td')
		let elem = document.createElement(addColumn[col][0])	//버튼이 될수도있고 input태그가 될수도 있으므로
		elem.className = 'cancelbtn';	//== elem.setAttribute('class', 'cancelbtn');
		elem.innerText = addColumn[col][1];
		if (addColumn[col][0] == 'input') {
			elem.setAttribute('type', addColumn[col][1])
		}
		td.append(elem)
		tr.append(td)
	}

	return tr;	//tr을 만들고 호출한 영역으로 돌려줘야함
}

//삭제Ajax호출
function delAjaxFnc(e) {
	e.stopPropagation();
	let id = this.parentElement.parentElement.getAttribute('id');	//notice_1, notice_23, ...
	id = id.substring(7);	//id값에서 'notice_'를 자르고 읽어오기 위함
	fetch('noticeDelAjax.do', {
		method: 'post',	//페이지 요청 방식 지정. get,post요청(추가, 수정, 삭제)
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },	//{키:밸류}형식으로 넘겨줌
		//application/json, multipart/form-data
		body: 'id=' + id
	})
		.then(result => result.json())
		//눌렀을때 화면에서 바로삭제
		.then(result => {
			if (result.retCode == 'Success') {
				document.querySelector('#notice_' + result.id).remove();	//id값으로 요소(element)를 찾아서 제거
			} else if (result.retCode == 'Fail') {
				alert('처리 중 오류발생');
			}

		})
		.catch(err => console.log(err))
}

//리스트 출력
fetch('noticeListAjax.do')	//요청할 url 들어감
	// .then(function(result){
	// 	return result.json();	//json메소드를 사용해서 stream -> js object타입으로 변경
	// })
	.then((result) => result.json()) 	//화살표 함수로 간단하게 표현, 리턴값이 하나이므로 return, 중괄호 생략가능
	//타입변경한 데이터를 가져오기
	.then((data) => {
		console.log(data);
		const parentEl = document.getElementById('show');
		//테이블 형식으로 화면에 출력하기
		makeTable(data, parentEl);
		//삭제버튼 이벤트
		let trs = document.querySelectorAll('#show tbody tr');
		for (const trElem of trs) {
			console.log()
			trElem.addEventListener('click', showModal)
			//id 그냥 숫자로 하면 헷갈리고 중복 가능성 있으니가 앞에 notice_를 붙여줌
			trElem.setAttribute('id', 'notice_' + trElem.firstChild.innerText)
			//삭제버튼
			trElem.querySelector('td:nth-child(6)>button').addEventListener('click', delAjaxFnc)
		}
	})
	.catch(function(err) { //서버 응답이 없을때
		console.log(err);
	})

document.addEventListener('DOMContentLoaded', function() {	//스크립트 파일이 더 위에있어서 먼저 실행되기때문에 아래에 있는 폼같은걸 찾지 못할때 써줘야한다
	//저장 버튼 이벤트(저장누르면 폼의 submit이벤트 발생하게 하기)
	document.querySelector('form[name=ajaxFrm]').addEventListener('submit', saveNoticeFnc);
	//수정 버튼 이벤트
	document.querySelector('#id01 form.modal-content.animate').addEventListener('submit', changeNoticeFnc);

})


//저장버튼 ajax호출
function saveNoticeFnc(e) {
	e.preventDefault();		// form의 submit이벤트 차단
	console.log('submit')
	let writer = document.getElementById('writer').value;	//input태그이기 때문에 value속성을 읽어온것
	let title = document.getElementById('title').value;
	let subject = document.getElementById('subject').value;

	if (!writer || !title || !subject) {
		alert('값을 입력해주세요')
		return;
	}

	let frm = document.querySelector('form[name=ajaxFrm]')
	let formData = new FormData(frm);

	fetch('noticeAddAjax.do', {
		method: 'post',
		body: formData
	})
		.then(result => result.json())
		.then(result => {
			let tr = makeTr(result);
			document.querySelector('#show tbody').prepend(tr);
		})
		.catch(err => console.log(err))
}

//변경 버튼에 대한 ajax호출
function changeNoticeFnc(e) {
	e.preventDefault();
	let title = document.querySelector('#id01 input[name="title"]').value;
	let subject = document.querySelector('#id01 textarea[name="subject"]').value;
	let noticeDate = document.querySelector('#id01 input[name="noticeDate"]').value;
	let id = document.querySelector('#id01 input[name="noticeId"]').value;

	fetch('noticeUpdateAjax.do', {
		method: "post",
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: 'id=' + id + '&title=' + title + '&date=' + noticeDate + '&subject=' + subject
	})
		.then(result => result.json())
		.then(result => {
			console.log(result)	//id값
			//화면닫고 서버에서 가져온 json데이터를 활용해서 목록에서 값을 변경
			document.querySelector('#id01').style.display = 'none';
			document.getElementById('notice_' + result.noticeId).replaceWith(makeTr(result));
			document.getElementById(result.subject).replaceWith(makerTr(result));

		})
		.catch(err => console.log(err));
}

function showModal() {
	let id = this.getAttribute('id')	//this = trElem
	id = id.substring(7)
	document.getElementById('id01').style.display = 'block';
	fetch("noticeSearchAjax.do?noticeId=" + id)
		.then(result => result.json())
		.then(result => {
			console.log(result)
			document.querySelector('#id01 input[name="noticeId"]').value = result.noticeId;
			document.querySelector('#id01 input[name="writer"]').value = result.noticeWriter;	//작성자 정보
			document.querySelector('#id01 input[name="title"]').value = result.noticeTitle;
			document.querySelector('#id01 textarea[name="subject"]').value = result.noticeSubject;
			document.querySelector('#id01 input[name="noticeDate"]').value = result.noticeDate;
			document.querySelector('#id01 img').setAttribute('src', 'attach/' + result.noticeFile)

		})
		.catch(err => console.log(err));
}