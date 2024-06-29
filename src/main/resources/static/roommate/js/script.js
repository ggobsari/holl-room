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
  alert("setValues");
//  let returnVal = false;

  const alarm = document.querySelector("#alarm");
  if (alarm.value == "") {
    alarm.value = -1;
  }

  const wakeupFrom = document.querySelector("#wakeup-from").value;
  const wakeupTo = document.querySelector("#wakeup-to").value;
//  alert("기상 : " + wakeupFrom + wakeupTo);
  document.querySelector("#wakeup-at").value = wakeupFrom + wakeupTo;
//
  const sleepFrom = document.querySelector("#sleep-from").value;
  const sleepTo = document.querySelector("#sleep-to").value;
//  alert("취침 : " + sleepFrom + sleepTo);
  document.querySelector("#sleep-at").value = sleepFrom + sleepTo;

  const num = document.querySelector("#cleaning").value;
  if (num != "") {
    const numCleaning = btnChangeTxt.innerText + "," + num;
    document.querySelector("#cleaning-cycle").value = numCleaning;
  }

  return true;
}

function fillValuesForEdit(title, content, nocturnal, wakeup_at, alarm, sleep_at,
                    smoking, pet, cleaning_cycle, sleeping_habits, noise) {
//function fillValuesForEdit(title, content, nocturnal, wakeup_at, smoking, pet, noise) {
  alert("fillValuesForEdit");

  document.querySelector("#title").value = title;
  document.querySelector("#content").value = content;

  if (nocturnal == 'Y') {
//    alert("nocturnal : Y");
    document.querySelector("#evening").checked = true;
  } else {
//    alert("nocturnal : N");
    document.querySelector("#morning").checked = true;
  }

  if (smoking == 'Y') {
//    alert("smoking : Y");
    document.querySelector("#smoke").checked = true;
  } else {
//    alert("smoking : N");
    document.querySelector("#notsmoke").checked = true;
  }

  if (pet == 'Y') {
//    alert("pet : Y");
    document.querySelector("#has-pet").checked = true;
  } else {
//    alert("pet : N");
    document.querySelector("#no-pet").checked = true;
  }

  if (noise == '2') {
//    alert("noise : 2");
    document.querySelector("#sensitive-" + "2").checked = true;
  } else if (noise == '1') {
//    alert("noise : 1");
    document.querySelector("#sensitive-" + "1").checked = true;
  } else {
//    alert("noise : 0");
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
}

//function setTitleAndContent(title, content) {
//   document.querySelector("#title").value = title;
//   document.querySelector("#content").value = content;
//}

//const buttons = document.querySelectorAll(".my-btn");
//for (const btn of buttons) {
//  btn.addEventListener("click", () => {
//    if (btn.innerText === "오전") {
//      btn.innerText = "오후";
//    } else if (btn.innerText === "오후") {
//      btn.innerText = "오전";
//    }
//  });
//}

function fillValuesForDetail(nocturnal, smoking, pet, noise, wakeup_at, sleep_at) {
  alert("fillValuesForDetail");

//  if (nocturnal == 'Y') {
//    alert("nocturnal : Y");
//  } else {
//    alert("nocturnal : N");
//  }
//  if (smoking == 'Y') {
//    alert("smoking : Y");
//  } else {
//    alert("smoking : N");
//  }
//  if (pet == 'Y') {
//    alert("pet : Y");
//  } else {
//    alert("pet : N");
//  }
//  if (noise == '2') {
//    alert("noise : 2");
////    document.querySelector("#d-sensitive-" + "2").checked = true;
//  } else if (noise == '1') {
//    alert("noise : 1");
////    document.querySelector("#d-sensitive-" + "1").checked = true;
//  } else {
//    alert("noise : 0");
////    document.querySelector("#d-sensitive-" + "0").checked = true;
//  }


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

  document.querySelector("#d-habit").innerText = "없음";
}