import React from "react";
import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Button from "react-bootstrap/esm/Button";
import Col from "react-bootstrap/esm/Col";
import Row from "react-bootstrap/esm/Row";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "../features/userSlice";
import NewsletterModal from "./modals/NewsletterModal";
import { useState } from "react";

function Header() {
  const disptach = useDispatch();
  const navigate = useNavigate();
  const { firstName, lastName, userId } = useSelector(
    (state) => state.user.data
  );
  const [show, setShow] = useState(false);

  const hide = () => {
    setShow(false);
  };

  const newsletterHandler = () => {
    setShow(true);
  };

  return show ? (
    <NewsletterModal show={show} hide={hide} />
  ) : (
    <Navbar
      style={{
        background: "linear-gradient(to bottom, #000000, #333333)",
        marginInline: "0.2vw",
        marginTop: "0.2vh",
        borderRadius: "5px",
      }}
    >
      <Container className="mx-5">
        <Row style={{ alignItems: "center" }}>
          <Col>
            <Navbar.Brand
              style={{
                display: "flex",
                alignItems: "center",
                cursor: "pointer",
              }}
              onClick={() => navigate("/")}
            >
              <h3
                style={{
                  fontFamily: "Satisfy",
                  fontSize: "50px",
                  color: "white",
                }}
              >
                MotoAnn
              </h3>
            </Navbar.Brand>
          </Col>
          <Col>
            <Button
              style={{
                // background: "linear-gradient(45deg, #ff6f61, #ff9671, #ff6f61)",
                // borderColor: "white",
                background: "none",
                color: "white",
                borderColor: "none",
                border: "none",
                fontSize: "17px",
              }}
              onClick={() => navigate("/")}
            >
              Announcements
            </Button>
          </Col>
          <Col>
            <Button
              style={{
                // background: "linear-gradient(45deg, #ff6f61, #ff9671, #ff6f61)",
                background: "none",
                color: "white",
                borderColor: "none",
                border: "none",
                fontSize: "17px",
              }}
              onClick={() => navigate("/messages")}
            >
              Messages
            </Button>
          </Col>
          <Col>
            <Button
              style={{
                // background: "linear-gradient(45deg, #ff6f61, #ff9671, #ff6f61)",
                // borderColor: "white",
                background: "none",
                color: "white",
                borderColor: "none",
                border: "none",
                fontSize: "17px",
              }}
              onClick={() => navigate(`/profile/${userId}`)}
            >
              Profile
            </Button>
          </Col>
          <Col>
            <Button
              style={{
                // background: "linear-gradient(45deg, #ff6f61, #ff9671, #ff6f61)",
                // borderColor: "white",
                background: "none",
                color: "white",
                borderColor: "none",
                border: "none",
                fontSize: "17px",
              }}
              onClick={() => navigate("/create-announcement")}
            >
              Create Announcement
            </Button>
          </Col>
          <Col>
            <Button
              style={{
                // background: "linear-gradient(45deg, #ff6f61, #ff9671, #ff6f61)",
                // borderColor: "white",
                background: "none",
                color: "white",
                borderColor: "none",
                border: "none",
                fontSize: "17px",
              }}
              onClick={() => newsletterHandler()}
            >
              Newsletter
            </Button>
          </Col>
          <Col className="ml-auto">
            <Navbar.Collapse>
              <Navbar.Text style={{ color: "white" }}>
                Signed in as:{" "}
                <a
                  onClick={() => navigate(`/profile/${userId}`)}
                  style={{ cursor: "pointer" }}
                >
                  {firstName} {lastName}
                </a>
              </Navbar.Text>
            </Navbar.Collapse>
          </Col>
          <Col>
            <Button
              style={{
                // background: "linear-gradient(45deg, #ff6f61, #ff9671, #ff6f61)",
                // borderColor: "white",
                background: "none",
                color: "white",
                borderColor: "none",
                border: "none",
                fontSize: "17px",
              }}
              onClick={() => disptach(logout())}
            >
              Logout
            </Button>
          </Col>
        </Row>
      </Container>
    </Navbar>
  );
}

export default Header;
