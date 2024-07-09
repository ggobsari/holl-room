function scrollToTop() {
  //alert("!!!");
  window.scrollTo(0, 0);
}

//const btnChangeTxt = document.querySelector("#btn-change-txt");
//btnChangeTxt.addEventListener("click", () => {
//  const text = btnChangeTxt.innerText;
//  if (text === "하루") {
//    btnChangeTxt.innerText = "주";
//  } else if (text === "주") {
//    btnChangeTxt.innerText = "달";
//  } else {
//    btnChangeTxt.innerText = "하루";
//  }
//});


function setValues() {
//  event.preventDefault();
  alert("setValues");

  const alarm = document.querySelector("#alarm");
  if (alarm.value == "") {
    alert("alarm : -1");
    alarm.value = -1;
  }

  const wakeupFrom = document.querySelector("#wakeup-from").value;
  const wakeupTo = document.querySelector("#wakeup-to").value;
  document.querySelector("#wakeup-at").value = wakeupFrom + wakeupTo;

  const sleepFrom = document.querySelector("#sleep-from").value;
  const sleepTo = document.querySelector("#sleep-to").value;
  document.querySelector("#sleep-at").value = sleepFrom + sleepTo;

  const num = document.querySelector("#cleaning").value;
  if (num != "") {
    const numCleaning = document.querySelector("#btn-change-txt").innerText + "," + num;
    document.querySelector("#cleaning-cycle").value = numCleaning;
  }

  return true;
}

function fillValuesForEdit(title, content, nocturnal, wakeup_at, alarm, sleep_at,
            smoking, pet, cleaning_cycle, sleeping_habits, noise) {
  alert("fillValuesForEdit");

  document.querySelector("#title").value = title;
  document.querySelector("#content").value = content;

  if (nocturnal == 'Y') {
    document.querySelector("#evening").checked = true;
  } else {
    document.querySelector("#morning").checked = true;
  }

  if (smoking == 'Y') {
    document.querySelector("#smoke").checked = true;
  } else {
    document.querySelector("#notsmoke").checked = true;
  }

  if (pet == 'Y') {
    document.querySelector("#has-pet").checked = true;
  } else {
    document.querySelector("#no-pet").checked = true;
  }

  if (noise == '2') {
    document.querySelector("#sensitive-" + "2").checked = true;
  } else if (noise == '1') {
    document.querySelector("#sensitive-" + "1").checked = true;
  } else if (noise == "0") {
    document.querySelector("#sensitive-" + "0").checked = true;
  }

//  alert(wakeup_at);
//  alert(sleep_at);
  const wakeupTime = wakeup_at.split("");
  const sleepTime = sleep_at.split("");

  const wakeupFrom = document.querySelector("#wakeup-from");
  const wakeupTo = document.querySelector("#wakeup-to");
  const sleepFrom = document.querySelector("#sleep-from");
  const sleepTo = document.querySelector("#sleep-to");

  const time1 = parseInt(wakeupTime[0] + wakeupTime[1]) + 1;
  const time2 = parseInt(wakeupTime[2] + wakeupTime[3]) + 1;
  const time3 = parseInt(sleepTime[0] + sleepTime[1]) + 1;
  const time4 = parseInt(sleepTime[2] + sleepTime[3]) + 1;

  wakeupFrom.options[time1].selected = true;
  wakeupTo.options[time2].selected = true;
  sleepFrom.options[time3].selected = true;
  sleepTo.options[time4].selected = true;

//  alert("alarm : " + alarm);
  if (alarm != -1) {
    document.querySelector("#alarm").value = parseInt(alarm);
  }

  if (sleeping_habits != null) {
    const habitArr = sleeping_habits.split(",");
    for (let i = 0; i < habitArr.length; i++) {
      if (habitArr[i] == 1) {
        document.querySelector("#habit-1").checked = true;
      } else if (habitArr[i] == 2) {
        document.querySelector("#habit-2").checked = true;
      } else {
        document.querySelector("#habit-3").checked = true;
      }
    }
  }

  if (cleaning_cycle != null) {
    const arr = cleaning_cycle.split(",");
    document.querySelector("#btn-change-txt").innerText = arr[0];
    document.querySelector("#cleaning").value = parseInt(arr[1]);
  }


} // fillValuesForEdit



function fillValuesForDetail(title, content, nocturnal, wakeup_at, alarm, sleep_at,
              smoking, pet, cleaning_cycle, sleeping_habits, noise) {

  alert("fillValuesForDetail");

//  alert(wakeup_at);
//  alert(sleep_at);
  const wakeupTime = wakeup_at.split("");
  const sleepTime = sleep_at.split("");

  const wakeupFrom = document.querySelector("#d-wakeup-from");
  const wakeupTo = document.querySelector("#d-wakeup-to");
  const sleepFrom = document.querySelector("#d-sleep-from");
  const sleepTo = document.querySelector("#d-sleep-to");

  const time1 = parseInt(wakeupTime[0] + wakeupTime[1]) + 1;
  const time2 = parseInt(wakeupTime[2] + wakeupTime[3]) + 1;
  const time3 = parseInt(sleepTime[0] + sleepTime[1]) + 1;
  const time4 = parseInt(sleepTime[2] + sleepTime[3]) + 1;

  wakeupFrom.options[time1].selected = true;
  wakeupTo.options[time2].selected = true;
  sleepFrom.options[time3].selected = true;
  sleepTo.options[time4].selected = true;

  if (alarm == -1) {
    document.querySelector("#d-alarm").innerText = "? 개";
  } else {
    document.querySelector("#d-alarm").innerText = alarm + " 개";
  }

  if (sleeping_habits != null) {
    const habitArr = sleeping_habits.split(",");
    const habit = document.querySelector("#d-habit");
    for (let i = 0; i < habitArr.length; i++) {
      if (i != 0) {
        habit.innerHTML += ",&nbsp;";
      }
      if (habitArr[i] == 1) {
        habit.innerText += "코골이";
      } else if (habitArr[i] == 2) {
        habit.innerText += "이갈이";
      } else {
        habit.innerText += "잠꼬대";
      }
    }
  } else {
    habit.innerText += "❌";
  }

  if (cleaning_cycle != null) {
    const arr = cleaning_cycle.split(",");
    document.querySelector("#d-btn-change-txt").innerText = arr[0];
    document.querySelector("#d-cleaning").innerText = arr[1] + " 번";
  } else {
    document.querySelector("#d-btn-change-txt").innerText = "하루";
    document.querySelector("#d-cleaning").innerText = "? 번";
  }

//  if (noise != "") {
//    alert("noise : " + noise);
  const sensitive = document.querySelector("#d-sensitive");
  if (noise == '2') {
    sensitive.innerText = "민감한 편이다";
  } else if (noise == '1') {
    sensitive.innerText = "약간 신경쓴다";
  } else if (noise == '0') {
    sensitive.innerText = "둔감하다";
  } else {
    sensitive.innerText = "?";
  }
//  }

} // fillValuesForDetail

function clickSearch() {
  alert("clickSearch");
  const searchWord = document.querySelector("#search-word").value;
  const category = document.querySelector("#search-category").value;
//  alert(searchWord);
  const address = "/hollroom/roommate/search?searchWord=" + searchWord + "&category=" + category;
  location.href = address;
}