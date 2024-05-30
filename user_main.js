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
  localStorage.removeItem("userid");
  localStorage.removeItem("useremail");
  window.location.href = "/index.html";
}

function viewProfile() {
  window.location.href = "/user_profile.html";
}

async function getUserLocation() {
  return new Promise((resolve, reject) => {
    // Check if geolocation is available
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          // Resolve the promise with the location object
          resolve({
            longitude: position.coords.longitude,
            latitude: position.coords.latitude,
          });
        },
        (error) => {
          // Reject the promise if there's an error
          reject(error);
        }
      );
    } else {
      // Geolocation not supported
      reject(new Error("Geolocation is not supported by this browser."));
    }
  });
}

// Async function using the getUserLocation function
async function searchNgo(){
 const location = await getUserLocation();
 const lat = location.latitude;
 const lon = location.longitude;
 console.log(location,lat,lon);
 if(lat && lon){
   try {
    const response = await fetch(`http://localhost:6999/SmartCare/request/nearestNgo/${lon}/${lat}`,{
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      }
    });

     console.log(response);
     const data = await response.json();
     console.log(data);
   } catch (error) {
    console.error("Error",error);
    document.getElementById("ngo_list").innerText="No nearest NGO.."
   }
 }

}
searchNgo();