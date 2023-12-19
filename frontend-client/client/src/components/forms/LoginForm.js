import React, { useState } from "react";
import { useDispatch } from "react-redux";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import FormGroup from "react-bootstrap/esm/FormGroup";
import { loginRequest } from "../../http_requests/user_service_requests";
import { setLoginUserData, setLoginUserError } from "../../features/userSlice";
import { useNavigate } from "react-router-dom";

function LoginForm() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const submit = (e) => {
    e.preventDefault();
    const formData = {
      email: email,
      password: password,
    };
    loginRequest(formData).then((response) => {
      if (response.status === 200) {
        dispatch(setLoginUserData(response.data));
      } else {
        dispatch(
          setLoginUserError({
            error_message: response.message,
            error_status_code: response.response.status,
          })
        );
      }
    });
  };
  return (
    <Form
      style={{
        padding: "3vw",
        background: "linear-gradient(to bottom, #000000, #333333)",
        boxShadow: "5px 5px 10px rgba(0, 0, 0, 0.3)",
        color: "white",
      }}
    >
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Email address</Form.Label>
        <Form.Control
          type="email"
          placeholder="Enter email"
          onChange={(e) => setEmail(e.target.value)}
        />
        <Form.Text style={{ color: "white" }}>
          We'll never share your email with anyone else.
        </Form.Text>
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>Password</Form.Label>
        <Form.Control
          type="password"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formBasicCheckbox">
        <Form.Text style={{ display: "flex", color: "white" }}>
          Don't have account ? Let's
          <p
            style={{
              textDecoration: "underline",
              marginLeft: "0.2vw",
              cursor: "pointer",
            }}
            onClick={() => navigate("/register")}
          >
            Register
          </p>
        </Form.Text>
      </Form.Group>
      <FormGroup style={{ display: "flex", justifyContent: "end" }}>
        <Button
          style={{
            // backgroundColor: "rgb(50, 50, 50)",
            background: "none",
            fontSize: "17px",
            border: "none",
            marginRight: "15px",
          }}
          onClick={(e) => submit(e)}
        >
          Login
        </Button>
      </FormGroup>
    </Form>
  );
}

export default LoginForm;
