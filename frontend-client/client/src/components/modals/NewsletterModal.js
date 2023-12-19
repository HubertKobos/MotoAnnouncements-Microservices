import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import { BRAND_OPTIONS } from "../../const/BRAND_OPTIONS";
import { useSelector } from "react-redux";
import { registerToNewsletter } from "../../http_requests/email_service_requests";

function NewsletterModal({ show, hide }) {
  const [brand, setBrand] = useState();
  const [model, setModel] = useState();
  const { userId, token, email } = useSelector((state) => state.user.data);

  const handleRegister = () => {
    registerToNewsletter({
      data: {
        brand: brand,
        model: model,
        email: email,
        userId: userId,
      },
      token: token,
    })
      .then((response) => console.log(response.status))
      .catch((error) => console.log(error));
  };

  return (
    <Modal show={show} onHide={() => hide()} centered>
      <Modal.Header closeButton>
        <Modal.Title style={{ color: "black" }}>
          Register to newsletter
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ color: "black" }}>
        <Form>
          {/* <Form.Control
            type="text"
            id="inputEmail"
            placeholder="Email"
            style={{ marginBottom: "7px" }}
            onChange={(e) => setEmail(e.target.value)}
          /> */}
          <Form.Select
            type="text"
            required
            onChange={(e) => setBrand(e.target.value)}
            style={{ marginBottom: "7px" }}
          >
            <option>Choose brand</option>
            {BRAND_OPTIONS.map((brand) => (
              <option key={brand} value={brand}>
                {brand}
              </option>
            ))}
          </Form.Select>
          <Form.Control
            type="text"
            id="inputEmail"
            placeholder="Model"
            style={{ marginBottom: "7px" }}
            onChange={(e) => setModel(e.target.value)}
          />
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="danger" onClick={() => hide()}>
          Cancel
        </Button>
        <Button variant="dark" onClick={() => handleRegister()}>
          Register
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default NewsletterModal;
