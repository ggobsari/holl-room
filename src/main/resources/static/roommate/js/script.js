function scrollToTop() {
  //alert("!!!");
  window.scrollTo(0, 0);
}

const btnChangeTxt = document.querySelector("#btn-change-txt");
btnChangeTxt.addEventListener("click", () => {
  const text = btnChangeTxt.innerText;
  if (text === "하루") {
    btnChangeTxt.innerText = "주";
  } else if (text === "주") {
    btnChangeTxt.innerText = "달";
  } else {
    btnChangeTxt.innerText = "하루";
  }
});

function setValues() {
//  event.preventDefault();
//  alert("setValues");
//  let returnVal = false;

  const alarm = document.querySelector("#alarm");
  if (alarm.value == "") {
    alarm.value = -1;
  }

  const wakeupFrom = document.querySelector("#wakeup-from").value;
  const wakeupTo = document.querySelector("#wakeup-to").value;
  document.querySelector("#wakeup-at").value = wakeupFrom + wakeupTo;
  const sleepFrom = document.querySelector("#sleep-from").value;
  const sleepTo = document.querySelector("#sleep-to").value;
  document.querySelector("#sleep-at").value = sleepFrom + sleepTo;

  const numCleaning = btnChangeTxt.innerText + "," document.querySelector("#cleaning");
  document.querySelector("#cleaning-cycle").value = numCleaning;

  return true;
}