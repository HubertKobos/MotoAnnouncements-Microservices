import React from "react";
import LoginForm from "../components/forms/LoginForm";

function LoginScreen() {
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center", // Add this line
        height: "100vh",
      }}
    >
      <LoginForm />
    </div>
  );
}

export default LoginScreen;
