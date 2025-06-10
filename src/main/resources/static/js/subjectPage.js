const submitButton = document.querySelector(".submit-button");
const subjectId = window.location.pathname.match(/\/subject\/(\d+)/)[1];

submitButton.addEventListener("click", () => {
  fetch("/student/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      subjectId: subjectId,
    }),
  }).then((res) => {
    if (!res) return alert("수강 신청을 실패하였습니다. 다시 신청해주세요");
    return window.location.replace("/student");
  });
});
