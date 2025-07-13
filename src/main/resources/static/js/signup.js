document.addEventListener('DOMContentLoaded', () => {
    const subjectListDiv = document.getElementById('subjectList');
    const previewList = document.getElementById('selectedSubjectList');
    const form = document.querySelector('form');
    const yearSelect = document.getElementById('filterYear');
    const semesterSelect = document.getElementById('filterSemester');

    const selectedSubjects = new Map(); // subjectId -> subjectName

    yearSelect.addEventListener('change', fetchSubjects);
    semesterSelect.addEventListener('change', fetchSubjects);

    async function fetchSubjects() {
        const year = yearSelect.value;
        const semester = semesterSelect.value;

        let url = '/api/subjects';
        const params = [];
        if (year) params.push(`grade=${year}`);
        if (semester) params.push(`semester=${semester}`);
        if (params.length) url += '?' + params.join('&');

        const res = await fetch(url);
        const subjects = await res.json();

        subjectListDiv.innerHTML = '';

        // 둘 다 비어있을 때만 안내문 띄움
        if (!year && !semester) {
            subjectListDiv.innerHTML = `
            <div class="col-12 text-muted text-center">
                학년과 학기 중 하나 이상을 선택해주세요.
            </div>`;
            return;
        }

        if (subjects.length === 0) {
            subjectListDiv.innerHTML = `
            <div class="col-12 text-muted text-center">
                해당 조건의 과목이 없습니다.
            </div>`;
            return;
        }

        subjects.forEach((s) => {
            if (selectedSubjects.has(String(s.subjectId))) return;

            const col = document.createElement('div');
            col.className = 'col-md-4'; // ✅ 3개씩 한 줄

            const card = document.createElement('div');
            card.className = 'p-3 border rounded bg-light subject-card';
            card.textContent = s.subjectName;
            card.draggable = true;
            card.dataset.id = s.subjectId;
            card.dataset.name = s.subjectName;

            card.addEventListener('dragstart', e => {
                e.dataTransfer.setData('text/plain', JSON.stringify({
                    subjectId: s.subjectId,
                    subjectName: s.subjectName
                }));
            });

            col.appendChild(card);
            subjectListDiv.appendChild(col);
        });
    }

    // 드롭 영역 설정
    const dropZone = document.getElementById('selectedSubjectPreview');
    dropZone.addEventListener('dragover', e => e.preventDefault());

    dropZone.addEventListener('drop', e => {
        e.preventDefault();
        const data = JSON.parse(e.dataTransfer.getData('text/plain'));
        const {subjectId, subjectName} = data;

        if (selectedSubjects.has(subjectId)) return;

        selectedSubjects.set(subjectId, subjectName);
        renderSelectedSubjects();
        fetchSubjects(); // 선택 영역에서 제외하기 위해 다시 그림
    });

    function renderSelectedSubjects() {
        previewList.innerHTML = '';

        if (selectedSubjects.size === 0) {
            previewList.innerHTML = '<li class="list-group-item text-muted">선택된 과목이 없습니다.</li>';
            return;
        }

        for (const [id, name] of selectedSubjects.entries()) {
            const li = document.createElement('li');
            li.className = 'list-group-item d-flex justify-content-between align-items-center';
            li.innerHTML = `
                <span>${name}</span>
                <button type="button" class="btn btn-sm btn-outline-danger" data-id="${id}">삭제</button>
            `;
            li.querySelector('button').addEventListener('click', () => {
                selectedSubjects.delete(id);
                renderSelectedSubjects();
                fetchSubjects(); // 다시 과목 목록에 추가됨
            });
            previewList.appendChild(li);
        }
    }

    // hidden input 구성
    form.addEventListener('submit', () => {
        form.querySelectorAll('input[name="subjectIds"]').forEach(el => el.remove());

        selectedSubjects.forEach((_, id) => {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'subjectIds';
            input.value = id;
            form.appendChild(input);
        });
    });
});
