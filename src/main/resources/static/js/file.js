function downloadFile(element) {
    const fileId = element.getAttribute("data-id");
    const oriFileName = element.getAttribute("data-name");
    fetch(`/files/download/${fileId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Download failed");
            }

            const disposition = response.headers.get("Content-Disposition");
            let filename = oriFileName;

            if (disposition && disposition.includes("filename=")) {
                const match = disposition.match(/filename="(.+)"/);
                if (match.length > 1) {
                    filename = decodeURIComponent(match[1]);
                }
            }

            return response.blob().then(blob => {
                const url = window.URL.createObjectURL(blob);
                const link = document.createElement("a");
                link.href = url;
                link.download = filename;
                document.body.appendChild(link);
                link.click();
                link.remove();
                window.URL.revokeObjectURL(url);
            });
        })
        .catch(error => {
            console.error("Download error:", error);
            alert("파일 다운로드에 실패했습니다.");
        });
}

function deleteFile(element) {
    if (!confirm("파일을 삭제하시겠습니까?")) return;
    const fileId = element.getAttribute("data-id");
    fetch(`/files/delete/${fileId}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                alert("파일이 삭제되었습니다.");
                // 예: 삭제된 파일 div를 DOM에서 제거
                const fileDiv = document.getElementById(`file-${fileId}`);
                if (fileDiv) {
                    fileDiv.remove();
                }
            } else {
                alert("파일 삭제에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error("삭제 중 오류 발생:", error);
            alert("오류가 발생했습니다.");
        });
}
