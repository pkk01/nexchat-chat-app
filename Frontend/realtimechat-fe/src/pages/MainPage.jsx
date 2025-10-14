import React from "react";
import { useNavigate } from "react-router-dom"; 
import "../styles/MainPage.css";

const MainPage = () => {
  const navigate = useNavigate();

  const handleGettingStarted = () => {
    navigate("/signup");
  };

  const handleLearnMore = () => {
    window.open("https://www.google.com", "_blank");
  };

  return (
    <div className="mainpage-container">
      <div className="mainpage-content">
        <div className="mainpage-header">
          <h1>Welcome to NexChat</h1>
          <p>Create an account to start chatting</p>
        </div>

        <div className="mainpage-actions auth-buttons">
          <button className="primary-btn" onClick={handleGettingStarted}>
            Getting Started
          </button>
          <button className="secondary-btn" onClick={handleLearnMore}>
            Learn More
          </button>
        </div>
      </div>
    </div>
  );
};

export default MainPage;
