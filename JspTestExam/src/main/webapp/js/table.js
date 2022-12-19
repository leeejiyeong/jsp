//table.js

const table = {  //데이터를 넣으면 테이블 형식으로 만들어주는 기능을 실행할거임
    initData:[],    //{name:"홍길동", age:20},{name:"김민수", age:30}
    showField:[],
    makeTable:function(){
        this.table = document.createElement('table');   //this.를 붙이면 필드이다. 객체범위안에서 다 쓸수 있다. / 안붙이면 변수가되기때문에 함수 안에서만 쓸수있다.
        this.table.setAttribute('border','1');
        this.makeHead();
        this.makeBody();

        return this.table;
    },
    makeHead:function(){
        this.thead = document.createElement('thead');
        this.htr = document.createElement('tr');
        //let fields = this.initData[0];      //initdata의 첫번째 값을 가져와서 fields에 넣는다
		let fields = this.showField;
        for (let prop of fields){
            let th = document.createElement('th');
            th.innerText = prop.toUpperCase();  //prop은 initdata의 name, age를 가르킨다. 그거를 대문자로 바꿔서 넣음
            this.htr.append(th);
        }
        this.thead.append(this.htr);
        this.table.append(this.thead);

    },
    addTitle:function(title){
        let th = document.createElement('th');
        th.innerText = title;	//<th>title</th>
        this.htr.append(th);
    },

    makeBody:function(){  
        let tbody = document.createElement('tbody');
        //let sfield = this.showField;
        this.initData.forEach((item) => {		//화살표 함수를 쓰면은 this는 window가 아니라 table을 가르키는 객체이다.
            //item = {name:"홍길동", age:20}을 말한다
            let tr = document.createElement('tr');
            for(let prop of this.showField){
                let td = document.createElement('td');
                td.innerText = item[prop];
                tr.append(td);  //tr에 td를 붙임
            }
            //함수안에서 this는 window객체를 말한다. 
            //따라서 함수안에서 this.tbody.append(tr)쓰면 에러남
            tbody.append(tr);  
        });
        //this.tbody = tbody;
        this.table.append(tbody);
    },
    makeTr:function(){

    }
};