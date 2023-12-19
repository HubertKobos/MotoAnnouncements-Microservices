import React, { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import FormGroup from "react-bootstrap/esm/FormGroup";
import { useNavigate } from "react-router-dom";
import { registerRequest } from "../../http_requests/user_service_requests";
import { Alert } from "react-bootstrap";
import UserProperlyRegisteredAlert from "../Alerts/UserProperlyRegisteredAlert";

function RegisterForm() {
  const navigate = useNavigate();

  const [passwordsNotValidError, setPasswordsNotValidError] = useState(false);
  const [properlyRegistered, setProperlyRegistered] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [confirmPassword, setConfirmPassword] = useState();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();

  const submitHandler = (e) => {
    e.preventDefault();
    if (password == confirmPassword) {
      const data = {
        email: email,
        password: password,
        firstName: firstName,
        lastName: lastName,
      };

      registerRequest(data).then((response) => {
        console.log(response);
        if (response.status === 201) {
          setProperlyRegistered(true);
          setErrorMessage("");
          setPasswordsNotValidError(false);
        } else if (response.response.data.status === 409) {
          setPasswordsNotValidError(false);
          setErrorMessage(response.response.data.message);
        }
      });
    } else {
      setPasswordsNotValidError(true);
    }
  };
  return (
    <div style={{ display: "flex", flexDirection: "column" }}>
      {properlyRegistered && <UserProperlyRegisteredAlert />}
      {passwordsNotValidError && (
        <Alert key="danger" variant="danger">
          Passwords are not valid
        </Alert>
      )}
      {errorMessage !== "" && (
        <Alert key="dangerErrorMessage" variant="danger">
          {errorMessage}
        </Alert>
      )}
      <Form
        onSubmit={submitHandler}
        style={{
          padding: "3vw",
          background: "linear-gradient(to bottom, #000000, #333333)",
          boxShadow: "5px 5px 10px rgba(0, 0, 0, 0.3)",
          color: "white",
        }}
      >
        <Form.Group className="mb-3">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            required
            type="email"
            placeholder="Enter email"
            onChange={(e) => setEmail(e.target.value)}
          />
          <Form.Text style={{ color: "white" }}>
            We'll never share your email with anyone else.
          </Form.Text>
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Password</Form.Label>
          <Form.Control
            required
            type="password"
            placeholder="Password"
            minLength={6}
            onChange={(e) => setPassword(e.target.value)}
          />

          <Form.Label>Confirm your password</Form.Label>
          <Form.Control
            required
            type="password"
            minLength={6}
            placeholder="Password"
            onChange={(e) => setConfirmPassword(e.target.value)}
          />

          <Form.Label>First name</Form.Label>
          <Form.Control
            required
            type="text"
            placeholder="First name"
            onChange={(e) => setFirstName(e.target.value)}
          />

          <Form.Label>Last name</Form.Label>
          <Form.Control
            required
            type="text"
            placeholder="Last name"
            onChange={(e) => setLastName(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Text style={{ display: "flex", color: "white" }}>
            If you have account already let's
            <p
              style={{
                textDecoration: "underline",
                marginLeft: "0.2vw",
                cursor: "pointer",
              }}
              onClick={() => navigate("/login")}
            >
              sign up
            </p>
          </Form.Text>
        </Form.Group>
        <FormGroup style={{ display: "flex", justifyContent: "end" }}>
          <Button
            style={{
              // backgroundColor: "rgb(50, 50, 50)",
              background: "none",
              border: "none",
              marginRight: "15px",
              fontSize: "17px",
            }}
            type="submit"
          >
            Register
          </Button>
        </FormGroup>
      </Form>
    </div>
  );
}

export default RegisterForm;
