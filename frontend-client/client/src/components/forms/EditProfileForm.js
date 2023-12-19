import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import { Button } from "react-bootstrap";
import { updateUserData } from "../../features/userSlice";
import { useDispatch } from "react-redux";
import { updateUserInformation } from "../../http_requests/user_service_requests";

function EditProfileForm(props) {
  const dispatch = useDispatch();
  const { onHide, data } = props;
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [confirmPassword, setConfirmPassword] = useState();
  const [photoes, setPhotoes] = useState();
  const filesHandler = (e) => {
    const files = Array.from(e.target.files);
    setPhotoes(files);
  };
  const submitHandler = () => {
    let dataToUpdate = {
      firstName,
      lastName,
      email,
      password,
    };

    if (password !== "" && password !== undefined) {
      if (password === confirmPassword) {
        dataToUpdate = {
          ...dataToUpdate,
          password,
        };
      } else {
        console.log("Passwords do not match");
        return;
      }
    }

    updateUserInformation({
      userId: data.userData.id,
      data: dataToUpdate,
      token: data.token,
    })
      .then((response) => {
        if (response.status === 200) {
          dispatch(updateUserData(response.data));
          onHide();
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Form>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Email address</Form.Label>
        <Form.Control
          type="email"
          placeholder={data.userData.email}
          onChange={(e) => setEmail(e.target.value)}
        />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicFirstName">
        <Form.Label>First name</Form.Label>
        <Form.Control
          type="text"
          placeholder={data.userData.firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicLastName">
        <Form.Label>Last name</Form.Label>
        <Form.Control
          type="text"
          placeholder={data.userData.lastName}
          onChange={(e) => setLastName(e.target.value)}
        />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>Password</Form.Label>
        <Form.Control
          type="password"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
        />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicConfirmPassword">
        <Form.Label>Confirm password</Form.Label>
        <Form.Control
          type="password"
          placeholder="Password"
          onChange={(e) => setConfirmPassword(e.target.value)}
        />
      </Form.Group>
      <Form.Group controlId="formFileMultiple" className="mb-3">
        <Form.Label>Photos</Form.Label>
        <Form.Control type="file" multiple onChange={filesHandler} />
      </Form.Group>
      <div style={{ display: "flex" }}>
        <Button
          variant="dark"
          style={{ marginLeft: "auto" }}
          onClick={() => submitHandler()}
        >
          Submit
        </Button>
      </div>
    </Form>
  );
}

export default EditProfileForm;
