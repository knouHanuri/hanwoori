/* 스터디 조회 */
// function edit(studyId){
//     window.location.href = `/study/form?studyId=${studyId}`;
// }
function view_study(studyId){
     window.location.href = `/study/view?studyId=${studyId}`;
 }

function del_activity(studyActivityId) {
    if (confirm('스터디 활동내역을 삭제하시겠습니까?')) {
        let msg = "다시 시도해주세요.";
        fetch(`/studyActivity/delete/${studyActivityId}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) msg = "스터디 활동내역이 삭제되었습니다.";
        })
        .catch(error => {
            console.error("삭제 중 오류 발생:", error);
        })
        .finally(() => {
            alert(msg);
            location.reload();
        });
    }
}

function goSubjectNew() {
    if(confirm('원하는 과목이 없을 경우 과목 등록 페이지로 이동합니다.')){
        window.location.href='/subjects/new';
    }
}

/* 스터디 활동내역 입력 */
const inputs = {
    studyDate: document.getElementById('studyDate'),
    title: document.getElementById('title'),
    content: document.getElementById('content')
};

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
        if (confirm(`스터디 활동내역을 ${document.getElementById('submitBtn').textContent}하시겠습니까?`)) {
            document.getElementById('studyActivityForm').method = 'post';
            document.getElementById('studyActivityForm').action = '/studyActivity/formSave';
            document.getElementById('studyActivityForm').submit();
        }
    }
}