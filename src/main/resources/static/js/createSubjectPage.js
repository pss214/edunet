const labels = document.querySelectorAll("form > .set-image > label");
const startTime = document.querySelector(".start-time");
const endTime = document.querySelector(".end-time");
const startDate = document.querySelector(".start-date");
const endDate = document.querySelector(".end-date");
const themeRadios = document.querySelectorAll(".radio > label > input");
const subjectName = document.querySelector("#subject-name");
const attend = document.querySelector("#attend");
const price = document.querySelector("#price");
const deadDay = document.querySelector("#dead-day");
const detail = document.querySelector("#detail");
const form = document.querySelector("form");

labels.forEach((label) => {
  const input = label.querySelector("input");
  const img = label.querySelector("img");
  const span = label.querySelector("span");

  input.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        img.src = reader.result;
        img.style.display = "block";
        span.style.display = "none";
      };
      reader.readAsDataURL(file);
    }
  });
});

startTime.addEventListener("change", () => {
  if (!endTime.value) return;
  if (startTime.value < endTime.value) return;
  alert("시간을 잘못 입력하셨습니다.");
  startTime.value = "";
});

endTime.addEventListener("change", () => {
  if (!startTime.value) return;
  if (startTime.value < endTime.value) return;
  alert("시간을 잘못 입력하셨습니다.");
  endTime.value = "";
});

startDate.addEventListener("change", () => {
  if (!endDate.value) return;
  if (startDate.value < endDate.value) return;
  alert("일정을 잘못 입력하셨습니다.");
  startDate.value = "";
});

endDate.addEventListener("change", () => {
  if (!startDate.value) return;
  if (startDate.value < endDate.value) return;
  alert("일정을 잘못 입력하셨습니다.");
  endDate.value = "";
});

themeRadios.forEach((themeRadio) => {
  themeRadio.addEventListener("click", () => {
    themeRadios.forEach((themeRadio) => {
      themeRadio.closest("label").style.background = "#f6f6f6";
      themeRadio.closest("label").style.color = "#333";
    });
    themeRadio.closest("label").style.background = "#b286ff";
    themeRadio.closest("label").style.color = "#fff";
  });
});

form.addEventListener("keydown", function (e) {
  if (e.key === "Enter") {
    e.preventDefault();
  }
});

function handleInputStyleChange(inputName) {
  inputName.addEventListener("change", () => {
    if (!inputName.value) {
      inputName.style.background = "#f6f6f6";
      inputName.style.color = "#333";
      return;
    }
    inputName.style.background = "#b286ff";
    inputName.style.color = "#fff";
  });
}

handleInputStyleChange(subjectName);
handleInputStyleChange(attend);
handleInputStyleChange(price);
handleInputStyleChange(deadDay);
handleInputStyleChange(detail);
handleInputStyleChange(startTime);
handleInputStyleChange(endTime);
handleInputStyleChange(startDate);
handleInputStyleChange(endDate);
