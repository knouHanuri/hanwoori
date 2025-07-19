/* 스터디 조회 */
function studyListSearch(){
    const search_subject = document.getElementById('search_subjectId').value.trim();
    const search_status = document.getElementById('search_status').value.trim();
    const search_title = document.getElementById('search_title').value.trim();

    if (!search_subject && !search_status && !search_title) {
        alert('검색 조건을 하나 이상 입력해주세요.');
        return;
    }

    document.getElementById('studySearchForm').submit();
}

function handleEnterKey(event){
    if (event.keyCode === 13) {
        event.preventDefault();
        studyListSearch();
    }
}

function addStudyActivity(studyId){
    window.location.href = `/studyActivity/form?studyId=${studyId}`;
}

function edit(studyId){
    window.location.href = `/study/form?studyId=${studyId}`;
}

function view(studyId){
    window.location.href = `/study/view?studyId=${studyId}`;
}

function del(studyId) {
    if (confirm('스터디 활동내역, 참여자까지 모두 삭제됩니다.\n스터디를 삭제하시겠습니까?')) {
        let msg = "다시 시도해주세요.";
        fetch(`/study/delete/${studyId}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) msg = "스터디가 삭제되었습니다.";
        })
        .catch(error => {
            console.error("삭제 중 오류 발생:", error);
        })
        .finally(() => {
            alert(msg);
            if(location.href.indexOf('study/form') > -1) location.href = "/study/list"; //보기페이지에서 삭제
            else location.reload(); //스터디조회에서 삭제 
        });
    }
}

function goSubjectNew() {
    if(confirm('원하는 과목이 없을 경우 과목 등록 페이지로 이동합니다.')){
        window.location.href='/subjects/new';
    }
}

/* 스터디 입력 */
const inputs = {
    title: document.getElementById('title'),
    status: document.getElementById('status'),
    schedule: document.getElementById('schedule'),
    startDate: document.getElementById('startDate'),
    endDate: document.getElementById('endDate')
};

if(inputs['startDate'] != null && inputs['endDate'] != null ){
    inputs['startDate'].addEventListener('change', function () {
        inputs['endDate'].min = inputs['startDate'].value; // 시작 날짜를 종료 날짜의 최소값으로 설정
    });

    inputs['endDate'].addEventListener('change', function () {
        inputs['startDate'].max = inputs['endDate'].value; // 종료 날짜를 시작 날짜의 최대값으로 설정
    });
}

// 버튼 클릭 이벤트 핸들러
document.getElementById("submitBtn").addEventListener("click", function() {
    // 폼 제출
    formSubmit();
});

function formSubmit(){
    let nullCnt = 0;

    for (let input of Object.entries(inputs)) {
        if (!input[1].value.trim()) {
            alert(document.querySelector(`label[for="${input[0]}"]`).textContent + '은/는 필수 입력입니다.');
            input[1].focus();
            nullCnt++;
            return false;
        }
    }

    if (nullCnt === 0) {
        if (confirm(`스터디를 ${document.getElementById('submitBtn').textContent}하시겠습니까?`)) {
            document.getElementById('studyForm').method = 'post';
            document.getElementById('studyForm').action = '/study/formSave';
            document.getElementById('studyForm').submit();
        }
    }
}