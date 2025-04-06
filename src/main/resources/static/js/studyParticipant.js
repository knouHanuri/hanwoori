function save_participant(studyId){
    if (confirm('해당 스터디에 참여하시겠습니까?')) {
        let msg = "다시 시도해주세요.";
        fetch(`/studyParticipant/save/${studyId}`, {
            method: 'POST',
        })
            .then(response => {
                if (response.ok) msg = "해당 스터디에 참여 등록 되었습니다.";
            })
            .catch(error => {
                console.error("입력 중 오류 발생:", error);
            })
            .finally(() => {
                alert(msg);
                if(location.href.indexOf('study/form') > -1) location.href = "/study/list"; //보기페이지에서 삭제
                else location.reload(); //스터디조회에서 삭제
            });
    }
}

function del_participant(studyId){
    if (confirm('해당 스터디를 참여 삭제 하시겠습니까?')) {
        let msg = "다시 시도해주세요.";
        fetch(`/studyParticipant/delete/${studyId}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) msg = "해당 스터디에 참여 삭제 되었습니다.";
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