import axios from "axios";

const API_URL = "https://localhost:5050";

const api = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

// response interceptor to handle errors globally

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 401: // Unauthorized
          authService.logout();
          window.location.href = "/login";
          break;
        case 403: // Forbidden
          console.error("Access forbidden:");
          break;
        case 404: // Resource Not Found
          console.error("Resource not found:", error.response.data);
          break;
        case 500: // Internal Server Error
          console.error("Server error:", error.response.data);
          break;
      }
    } else if (error.request) {
      console.error("Request made but didn't get the response", error.request);
    } else {
      console.error(
        "Something happened in setting up the request",
        error.message
      );
    }
    return Promise.reject(error);
  }
);

const generateUserColor = () => {
  const colors = [
    "#FF5733",
    "#33FF57",
    "#3357FF",
    "#F333FF",
    "#33FFF5",
    "#F5FF33",
    "#FF33A8",
    "#A833FF",
    "#33FFA8",
    "#FFA833",
  ];
  return colors[Math.floor(Math.random() * colors.length)];
};

export const authService = {
  login: async (username, password) => {
    try {
      const response = await api.post("/auth/login", {
        username,
        password,
      });

      // after successful login
      const userColor = generateUserColor();

      const userData = {
        ...response.data,
        color: userColor,
        loginTime: new Date().toISOString(),
      };
      localStorage.setItem("user", JSON.stringify(userData));
      localStorage.setItem("user", JSON.stringify(response.data));

      return {
        success: true,
        user: userData,
      };
    } catch (error) {
      console.error("Login error:", error);
      const errorMessage =
        error.response?.data?.message ||
        "Login failed, Please check your credentials.";

      throw new errorMessage();
    }
  },

  signup: async (username, email, password) => {
    try {
      const response = await api.post("/auth/signup", {
        username,
        email,
        password,
      });

      return {
        success: true,
        user: response.data,
      };
    } catch (error) {
      console.error("Signup error:", error);
      const errorMessage =
        error.response?.data?.message ||
        "Signup failed, Please check your credentials.";

      throw new errorMessage();
    }
  },

  logout: async () => {
    try {
      await api.post("/auth/logout");
    } catch (error) {
      console.error("Logout error:", error);
    } finally {
      localStorage.removeItem("currentUser");
      localStorage.removeItem("user");
    }
  },

  fetchCurrentUser: async () => {
    try {
      const response = await api.get("/auth/getcurrentuser");
      localStorage.setItem("user", JSON.stringify(response.data));
      return response.data;
    } catch (error) {
      console.error("Fetch current user error:", error);

      // if unauthorized, logout the user
      if (error.response && error.response.status === 401) {
        await authService.logout();
      }
    }
  },

  getCurrentUser: () => {
    const currentUserStr = localStorage.getItem("currentUser");
    const userStr = localStorage.getItem("user");

    try {
      if (currentUserStr) {
        return JSON.parse(currentUserStr);
      } else if (userStr) {
        const userData = JSON.parse(userStr);
        const userColor = generateUserColor();

        return {
          ...userData,
          color: userColor,
        };
      }
      return null;
    } catch (error) {
      console.error("Error parsing user data from localStorage:", error);
      return null;
    }
  },

  isAuthenticated: () => {
    const user =
      localStorage.getItem("currentUser") || localStorage.getItem("user");
    return !!user;
  },

  fetchPrivateMessage: async (user1, user2) => {
    try {
      const response = await api.get(
        `/api/messages/private?user1=${encodeURIComponent(user1)}
        &user2=${encodeURIComponent(user2)}`
      );

      return response.data;
    } catch (error) {
      console.error("Fetch private messages error:", error);
      throw error;
    }
  },
};
