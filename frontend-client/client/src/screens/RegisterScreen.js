import React from "react";
import RegisterForm from "../components/forms/RegisterForm";

function RegisterScreen() {
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center", // Add this line
        height: "100vh",
      }}
    >
      <RegisterForm />
    </div>
  );
}

export default RegisterScreen;
