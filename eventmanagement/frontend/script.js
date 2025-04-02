const API_URL = "http://localhost:8080/api";
let userToken = localStorage.getItem("userToken");
let userId = localStorage.getItem("userId");
let userRole = localStorage.getItem("userRole");

// Check authentication status
function checkAuth() {
  const authButtons = document.getElementById("auth-buttons");
  const userMenu = document.getElementById("user-menu");
  if (userToken) {
    authButtons.style.display = "none";
    userMenu.style.display = "flex";
  } else {
    authButtons.style.display = "flex";
    userMenu.style.display = "none";
  }
}

// Load upcoming events
async function loadEvents() {
  if (!userToken || !userId) {
    console.warn("No user token found! Please log in.");
    return;
  }

  const apiUrl = `${API_URL}/events/upcoming`;
  try {
    const response = await fetch(apiUrl, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${userToken}`,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) throw new Error(`Failed to fetch events: ${response.status}`);

    const events = await response.json();
    renderEvents(events);
  } catch (error) {
    console.error("Error loading events:", error);
    document.getElementById("events-container").innerHTML = `
      <p style="text-align: center; color: red;">Failed to load events.</p>`;
  }
}

async function renderEvents(events) {
  const eventsContainer = document.getElementById("events-container");
  eventsContainer.innerHTML = "";

  if (!Array.isArray(events) || events.length === 0) {
    eventsContainer.innerHTML = `<p style="text-align: center;">No events available.</p>`;
    return;
  }

  const userId = localStorage.getItem("userId");
  const userRole = localStorage.getItem("userRole");

  const filteredEvents = events.filter((event) => {
    return userRole === "Organizer" ? event.creator.id == userId : true;
  });

  const eventHTML = await Promise.all(
    filteredEvents.map(async (event) => {
      const eventDate = new Date(event.dateTime).toLocaleDateString();
      const eventTime = new Date(event.dateTime).toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit",
      });
      const isFull = event.availableSeats === 0;

      let buttonHtml = "";
      if (userRole === "Organizer" && event.creator.id == userId) {
        buttonHtml = `
          <button class="btn btn-secondary" onclick="openEditEventModal(${event.id})">Edit</button>
          <button class="btn btn-danger" onclick="deleteEvent(${event.id})">Delete</button>`;
      } else {
        const isBooked = await checkBookingStatus(event.id);

        buttonHtml = isFull
          ? `<button class="btn btn-secondary" disabled>Full</button>`
          : isBooked
          ? `<button class="btn btn-danger" onclick="cancelEvent(${event.id})">Cancel Booking</button>`
          : `<button class="btn btn-primary" onclick="bookEvent(${event.id})">Book Now</button>`;
      }

      return `
      <div class="event-card" id="event-${event.id}">
          <div class="event-image">
              <i class="fas fa-calendar-day"></i>
          </div>
          <div class="event-details">
              <h3 class="event-title">${event.title}</h3>
              <p class="event-description">${event.description}</p>
              <div class="event-info"><i class="fas fa-calendar"></i> <span>${eventDate}</span></div>
              <div class="event-info"><i class="fas fa-clock"></i> <span>${eventTime}</span></div>
              <div class="event-info"><i class="fas fa-tag"></i> <span>${event.category || "General"}</span></div>
              <div class="event-info"><i class="fas fa-ticket-alt"></i> <span>₹${event.ticketPrice.toFixed(2)}</span></div>
              <div class="event-footer">
                  <span class="event-slots"><i class="fas fa-users"></i> ${event.availableSeats || "Unlimited"} slots available</span>
                  ${buttonHtml}
              </div>
          </div>
      </div>`;
    })
  );

  eventsContainer.innerHTML = eventHTML.join("");
}

async function loadBookedEvents() {
  const userRole = localStorage.getItem("userRole");
  const userId = localStorage.getItem("userId");

  if (userRole !== "Attendee") return;

  document.getElementById("booked-events-section").style.display = "block";

  try {
    const token = localStorage.getItem("userToken");
    const response = await fetch(`${API_URL}/events/user-booked?userId=${userId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) throw new Error("Failed to fetch booked events.");

    const bookedEvents = await response.json();
    displayBookedEvents(bookedEvents);
  } catch (error) {
    console.error("Error fetching booked events:", error);
    document.getElementById("booked-events-container").innerHTML =
      "<p style='text-align: center; color: red;'>Error loading booked events.</p>";
  }
}

function displayBookedEvents(events) {
  const container = document.getElementById("booked-events-container");
  container.innerHTML = "";

  if (events.length === 0) {
    container.innerHTML =
      "<p style='text-align: center;'>No booked events found.</p>";
    return;
  }

  events.forEach((event) => {
    const eventCard = document.createElement("div");
    eventCard.classList.add("event-card");

    eventCard.innerHTML = `
      <div class="event-details">
          <h3 class="event-title">${event.title}</h3>
          <p class="event-description">${event.description}</p>
          <p class="event-date"><i class="fas fa-calendar-alt"></i> ${new Date(event.dateTime).toLocaleString()}</p>
          
          <div class="event-meta">
              <span class="event-category"><i class="fas fa-tag"></i> ${event.category}</span>
              <span>     </span>
              <span class="event-price">₹${event.ticketPrice}</span>
          </div>
      </div>
    `;

    container.appendChild(eventCard);
  });
}

async function updateWalletBalance() {
  const userToken = localStorage.getItem("userToken");
  const userId = localStorage.getItem("userId");

  if (!userToken || !userId) {
    console.error("No token found! User must log in first.");
    return;
  }

  try {
    const response = await fetch(`${API_URL}/users/wallet?userId=${userId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${userToken}`,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) throw new Error("Failed to fetch wallet balance");

    const data = await response.json();
    document.getElementById("wallet-balance").textContent = `₹${data.walletBalance.toFixed(2)}`;
  } catch (error) {
    console.error("Error fetching wallet balance:", error);
  }
}

async function checkBookingStatus(eventId) {
  const userToken = localStorage.getItem("userToken");
  if (!userToken) {
    console.error("🚨 No token found! User must log in first.");
    return false;
  }

  try {
    const response = await fetch(
      `http://localhost:8080/api/bookings/isBooked?eventId=${eventId}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${userToken}`,
          "Content-Type": "application/json",
        },
      }
    );

    if (!response.ok)
      throw new Error(`Failed to check booking status (HTTP ${response.status})`);

    return await response.json();
  } catch (error) {
    console.error("Error checking booking status:", error);
    return false;
  }
}

function updateButton(eventId, isBooked) {
  const button = document.getElementById(`event-button-${eventId}`);
  if (isBooked) {
    button.innerText = "Cancel Booking";
    button.onclick = () => cancelEvent(eventId);
  } else {
    button.innerText = "Book Event";
    button.onclick = () => bookEvent(eventId);
  }
}

async function bookEvent(eventId) {
  const userToken = localStorage.getItem("userToken");

  if (!userToken) {
    console.error("No token found! User must log in first.");
    return;
  }

  try {
    const response = await fetch(
      `http://localhost:8080/api/bookings/book?eventId=${eventId}`,
      {
        method: "POST",
        headers: {
          Authorization: `Bearer ${userToken}`,
          "Content-Type": "application/json",
        },
      }
    );

    if (!response.ok) throw new Error("Failed to book event");

    alert("Booking successful!");
    checkBookingStatus(eventId);
    loadEvents();
    updateWalletBalance();
  } catch (error) {
    console.error("Error booking event:", error);
  }
}

async function cancelEvent(eventId) {
  const userToken = localStorage.getItem("userToken");

  if (!userToken) {
    console.error("No token found! User must log in first.");
    return;
  }

  try {
    const response = await fetch(
      `http://localhost:8080/api/bookings/cancel?eventId=${eventId}`,
      {
        method: "POST",
        headers: {
          Authorization: `Bearer ${userToken}`,
          "Content-Type": "application/json",
        },
      }
    );

    if (!response.ok) throw new Error("Failed to cancel event");

    alert("Booking cancelled!");
    checkBookingStatus(eventId);
    loadEvents();
    updateWalletBalance();
  } catch (error) {
    console.error("Error cancelling event:", error);
  }
}

async function deleteEvent(eventId) {
  const userToken = localStorage.getItem("userToken");
  const userId = localStorage.getItem("userId");

  if (!userToken || !userId) {
    console.error("No token found! User must log in first.");
    return;
  }

  try {
    const response = await fetch(`${API_URL}/events/delete/${eventId}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${userToken}`,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) throw new Error("Failed to delete event");

    document.getElementById(`event-${eventId}`).remove();
    alert("Event deleted successfully!");
  } catch (error) {
    console.error("Error deleting event:", error);
  }
}

// Modal functions
function setupModals() {
  const loginModal = document.getElementById("login-modal");
  const signupModal = document.getElementById("signup-modal");

  const loginBtn = document.getElementById("login-btn");
  const signupBtn = document.getElementById("signup-btn");

  const closeLogin = document.getElementById("close-login");
  const closeSignup = document.getElementById("close-signup");

  if (loginBtn) {
    loginBtn.addEventListener("click", () => (loginModal.style.display = "flex"));
  }
  if (signupBtn) {
    signupBtn.addEventListener("click", () => (signupModal.style.display = "flex"));
  }

  closeLogin.addEventListener("click", () => (loginModal.style.display = "none"));
  closeSignup.addEventListener("click", () => (signupModal.style.display = "none"));

  // Switch between modals
  document.getElementById("switch-to-signup").addEventListener("click", () => {
    loginModal.style.display = "none";
    signupModal.style.display = "flex";
  });

  document.getElementById("switch-to-login").addEventListener("click", () => {
    signupModal.style.display = "none";
    loginModal.style.display = "flex";
  });

  // Close modals when clicking outside
  window.addEventListener("click", (e) => {
    if (e.target === loginModal) loginModal.style.display = "none";
    if (e.target === signupModal) signupModal.style.display = "none";
  });
}

function setupEditEventModal() {
  const editEventModal = document.getElementById("edit-event-modal");
  const closeEditEvent = document.getElementById("close-edit-event");
  let currentEventId = null;

  // Open the edit event modal when the "Edit" button is clicked
  window.openEditEventModal = function (eventId) {
    currentEventId = eventId;
    const eventTitle = document.getElementById("edit-event-title");
    const eventDescription = document.getElementById("edit-event-description");
    const eventDateTime = document.getElementById("edit-event-date-time");
    const eventCategory = document.getElementById("edit-event-category");
    const eventPrice = document.getElementById("edit-event-price");
    const eventSeats = document.getElementById("edit-event-seats");

    // Clear any previous form data
    eventTitle.value = "";
    eventDescription.value = "";
    eventDateTime.value = "";
    eventCategory.value = "";
    eventPrice.value = "";
    eventSeats.value = "";

    // Show the modal with an empty form
    editEventModal.style.display = "flex";
  };

  // Close the edit event modal when the close button is clicked
  closeEditEvent.addEventListener("click", () => {
    editEventModal.style.display = "none";
  });

  // Close modal when clicking outside
  window.addEventListener("click", (e) => {
    if (e.target === editEventModal) {
      editEventModal.style.display = "none";
    }
  });

  // Handle the form submission for editing the event
  document.getElementById("edit-event-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    // Get updated event data from the form
    const updatedEvent = {
        title: document.getElementById("edit-event-title").value,
        description: document.getElementById("edit-event-description").value,
        dateTime: document.getElementById("edit-event-date-time").value,
        category: document.getElementById("edit-event-category").value,
        ticketPrice: parseFloat(document.getElementById("edit-event-price").value),
        availableSeats: parseInt(document.getElementById("edit-event-seats").value),
      };
  
      try {
        const token = localStorage.getItem("userToken");
        const response = await fetch(`${API_URL}/events/edit/${currentEventId}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(updatedEvent),
        });
  
        if (!response.ok) throw new Error("Failed to update event");
  
        // Close modal and show success message
        editEventModal.style.display = "none";
        alert("Event updated successfully!");
  
        // Reload events or update the events list
        loadEvents();
      } catch (error) {
        console.error("Error editing event:", error);
        alert("Failed to update event. Please try again.");
      }
    });
  }
  
  function setupCreateEventModal() {
    const createEventModal = document.getElementById("create-event-modal");
    const createEventBtn = document.getElementById("hero-create-btn");
    const closeCreateEvent = document.getElementById("close-create-event");
  
    createEventBtn.addEventListener("click", () => {
      if (!userToken) {
        document.getElementById("login-modal").style.display = "flex";
      } else {
        createEventModal.style.display = "flex";
      }
    });
  
    closeCreateEvent.addEventListener("click", () => {
      createEventModal.style.display = "none";
    });
  
    // Close modal when clicking outside
    window.addEventListener("click", (e) => {
      if (e.target === createEventModal) {
        createEventModal.style.display = "none";
      }
    });
  
    // Handle create event form submission
    document.getElementById("create-event-form").addEventListener("submit", async (e) => {
      e.preventDefault();
      let userId = localStorage.getItem("userId");
      const eventData = {
        title: document.getElementById("event-title").value,
        description: document.getElementById("event-description").value,
        dateTime: document.getElementById("event-date-time").value,
        category: document.getElementById("event-category").value,
        ticketPrice: parseFloat(document.getElementById("event-price").value),
        availableSeats: parseInt(document.getElementById("event-seats").value),
      };
  
      try {
        const response = await fetch(`${API_URL}/events/create?userId=${userId}`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${userToken}`,
          },
          body: JSON.stringify(eventData),
        });
  
        if (!response.ok) throw new Error("Failed to create event");
  
        const data = await response.json();
  
        // Close modal and show success message
        createEventModal.style.display = "none";
        alert("Event created successfully!");
  
        // Reload events or redirect to event details
        loadEvents();
      } catch (error) {
        console.error("Error creating event:", error);
        alert("Failed to create event. Please try again.");
      }
    });
  }
  
  // Handle login
  document.getElementById("login-form").addEventListener("submit", async (e) => {
    e.preventDefault();
  
    const email = document.getElementById("login-email").value;
    const password = document.getElementById("login-password").value;
  
    try {
      const response = await fetch(`${API_URL}/users/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });
  
      if (!response.ok) throw new Error("Invalid email or password.");
  
      const data = await response.json();
  
      // Save user session
      localStorage.setItem("userToken", data.token);
      localStorage.setItem("userId", data.userId);
      localStorage.setItem("userRole", data.role);
  
      document.getElementById("login-modal").style.display = "none";
      checkAuth();
      updateWalletBalance();
  
      // Redirect based on role
      window.location.href =
        data.role === "index.html";
    } catch (error) {
      console.error("Login error:", error);
      alert("Login failed. Please check your credentials.");
    }
  });
  
  // Handle signup
  document.getElementById("signup-form").addEventListener("submit", async (e) => {
    e.preventDefault();
  
    const name = document.getElementById("signup-name").value;
    const email = document.getElementById("signup-email").value;
    const password = document.getElementById("signup-password").value;
    const role = document.getElementById("signup-role").value;
  
    try {
      const response = await fetch(`${API_URL}/users/signup`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password, role }),
      });
  
      if (!response.ok) throw new Error("Signup failed. Try again.");
  
      const data = await response.json();
  
      localStorage.setItem("userToken", data.token);
      localStorage.setItem("userId", data.userId);
      localStorage.setItem("userRole", role);
  
      document.getElementById("signup-modal").style.display = "none";
      checkAuth();
      updateWalletBalance();
  
      window.location.href = "index.html";
    } catch (error) {
      console.error("Signup error:", error);
      alert("Signup failed. Please try again.");
    }
  });
  
  // Handle logout
  document.getElementById("logout-btn").addEventListener("click", () => {
    localStorage.removeItem("userToken");
    localStorage.removeItem("userId");
    localStorage.removeItem("userRole");
    checkAuth();
    window.location.href = "index.html";
  });
  
  // Hero buttons
  document.getElementById("hero-events-btn").addEventListener("click", () => {
    window.location.href = "events.html";
  });
  
  // Initialize
  document.addEventListener("DOMContentLoaded", () => {
    checkAuth();
    loadEvents();
    loadBookedEvents();
    setupModals();
    setupEditEventModal();
    setupCreateEventModal();
    updateWalletBalance();
  
    const urlParams = new URLSearchParams(window.location.search);
    const scrollToSection = urlParams.get("scroll");
  
    if (scrollToSection === "upcoming") {
      const upcomingEventsSection = document.querySelector(".events");
      if (upcomingEventsSection) {
        upcomingEventsSection.scrollIntoView({ behavior: "smooth" });
      }
    }
  
    if (scrollToSection === "booked") {
      const bookedEventsSection = document.getElementById("booked-events-section");
      if (bookedEventsSection) {
        bookedEventsSection.style.display = "block";
        bookedEventsSection.scrollIntoView({ behavior: "smooth" });
      }
    }
  
    const bookedEventsButton = document.getElementById("booked-events-button");
    const userRole = localStorage.getItem("userRole");
  
    if (userRole !== "Attendee") {
      bookedEventsButton.style.display = "none";
    }
  
    const params = new URLSearchParams(window.location.search);
    const scrollTarget = params.get("scroll");
  
    if (scrollTarget === "booked") {
      const bookedEventsSection = document.getElementById("booked-events-section");
      if (bookedEventsSection) {
        bookedEventsSection.style.display = "block";
        bookedEventsSection.scrollIntoView({ behavior: "smooth" });
      }
    }
  
    const createEventBtn = document.getElementById("hero-create-btn");
    if (userRole !== "Organizer") {
      createEventBtn.style.display = "none";
    }
  });