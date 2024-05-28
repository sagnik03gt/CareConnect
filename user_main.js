document.querySelectorAll(".dropdown-toggle").forEach((item) => {
  item.addEventListener("click", (event) => {
    if (event.target.classList.contains("dropdown-toggle")) {
      event.target.classList.toggle("toggle-change");
    } else if (
      event.target.parentElement.classList.contains("dropdown-toggle")
    ) {
      event.target.parentElement.classList.toggle("toggle-change");
    }
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const restaurantContainer = document.querySelector(".card-slider");
  const leftRButton = document.querySelector(".restaurant-arrow-left");
  const rightRButton = document.querySelector(".restaurant-arrow-right");

  function updateButtonState() {
    leftRButton.disabled = restaurantContainer.scrollLeft <= 0;
    rightRButton.disabled =
      restaurantContainer.scrollLeft + restaurantContainer.offsetWidth >=
      restaurantContainer.scrollWidth;
  }

  leftRButton.onclick = function () {
    restaurantContainer.scrollBy({
      left: -restaurantContainer.offsetWidth / 2,
      behavior: "smooth",
    });
  };

  rightRButton.onclick = function () {
    restaurantContainer.scrollBy({
      left: restaurantContainer.offsetWidth / 2,
      behavior: "smooth",
    });
  };

  restaurantContainer.addEventListener("scroll", updateButtonState);
  updateButtonState();
});
function logOut() {
  localStorage.removeItem("User");
  window.location.href = "/index.html";
}

function viewProfile() {
  window.location.href = "/user_profile.html";
}

const fetchData = async () => {
  const baseUrl = "http://localhost:6999/SmartCare/request/nearestNgo";
  const longitude = localStorage.getItem("userlong");
  const latitude = localStorage.getItem("userlat");
  const apiUrl = `${baseUrl}/${longitude}/${latitude}`;

  try {
    const response = await fetch(apiUrl);
    if (!response.ok) {
      throw new Error(`Server error: ${response.statusText}`);
    }
    const data = await response.json();
    console.log(data);
  } catch (error) {
    console.error("There has been a problem with your fetch operation:", error);
  }
};

fetchData();

// async function listNgos() {
//   try {
//     const lat = localStorage.getItem("userlat");
//     const long = localStorage.getItem("userlong");
//     const baseUrl = "http://localhost:6999/SmartCare/request/nearestNgo";

//     const mainUrl = `${baseUrl}/${lat}/${long}`;
//     console.log(mainUrl);
//     const res = await fetch(`${baseUrl}/${long}/${lat}`, {
//       method: "GET",
//       headers: {
//         "Content-Type": "application/json",
//       },
//       body: JSON.stringify(data),
//     });
//     const result = await res.json();

//     console.log(res.status);

//     console.log(result);

//     if (result) {
//       localStorage.setItem("User", email);
//       //window.location.replace("/user_main.html");
//     } else {
//       console.error("API call successful but data.success is false");
//       message.textContent = "* Invalid Email or Password.";
//     }
//   } catch (error) {
//     console.error("Error :", error);
//     document.getElementById("error-msg").textContent = "Failed to Login.";
//   }
// }

// listNgos();
