const checkbox = document.querySelector("#toggle");
const pTag = checkbox.nextElementSibling.querySelector("p");
const sectionImgs = document.querySelectorAll(".imgArea > img");
const teacherImg = document.querySelector(".imgArea > .teacherImg");
const signupTitle = document.querySelector(".loginForm > .wrap > h2");
const userType = document.querySelector("#userType");

checkbox.addEventListener("change", function () {
  sectionImgs.forEach((sectionImg) => {
    if (sectionImg.classList.contains("hidden")) {
      sectionImg.classList.remove("hidden");
    } else {
      sectionImg.classList.add("hidden");
    }
  });
  if (checkbox.checked) {
    pTag.textContent = "T";
    signupTitle.textContent = "강사 로그인";
    userType.value = "teacher";
  } else {
    pTag.textContent = "S";
    signupTitle.textContent = "학생 로그인";
    userType.value = "student";
  }
});
