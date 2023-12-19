import React, { useState } from "react";
import { Container, Row, Col, Image } from "react-bootstrap";
import default_user_avatar from "../static/images/default_user_avatar.webp";
import { BiSolidMessageCheck } from "react-icons/bi";
import { BsGear } from "react-icons/bs";
import AnnouncementsColumn from "./AnnouncementsColumn";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import EditProfileModal from "./EditProfileModal";

function ProfileComponent({ userData, announcements }) {
  const navigate = useNavigate();
  const { userId, token } = useSelector((state) => state.user.data);
  const [modalShow, setModalShow] = useState(false);
  const openEditModal = () => {
    setModalShow(true);
  };
  const hideModal = () => {
    setModalShow(false);
  };
  return (
    <Container>
      {/* Render the modal component  */}
      <EditProfileModal
        show={modalShow}
        onHide={hideModal}
        data={{ userData: userData, token: token }}
      />

      <Row
        style={{
          // backgroundColor: "black",
          height: "25vh",
          position: "relative",
        }}
      >
        <Row
          style={{
            // backgroundColor: "#0e1111",
            height: "15vh",
            position: "relative",
          }}
        ></Row>

        <Row>
          <Col style={{ maxWidth: "22vh", maxHeight: "22vh" }}>
            <Image
              style={{
                position: "absolute",
                bottom: "0",
                maxHeight: "22vh",
                maxWidth: "22vh",
                borderRadius: "10%",
              }}
              src={
                userData.avatar !== null && userData.avatar !== ""
                  ? userData.avatar
                  : default_user_avatar
              }
              alt={default_user_avatar}
            />
          </Col>
          <Col>
            <div style={{ display: "flex" }}>
              <h2
                className="ProfileName"
                style={{
                  marginLeft: "7px",
                  // color: "white",
                  position: "absolute",
                  top: "5vh",
                }}
              >
                {userData.firstName} {userData.lastName}
              </h2>
              <div style={{ display: "flex", alignItems: "center" }}>
                {userId === userData.id ? (
                  <BsGear size={38} onClick={() => openEditModal()} />
                ) : (
                  <div
                    style={{ display: "flex", cursor: "pointer" }}
                    onClick={() =>
                      navigate(`/messages/${userData.id}/${userId}`)
                    }
                  >
                    <h3 style={{ fontSize: "20px", marginLeft: "7px" }}>
                      Message
                    </h3>
                    <BiSolidMessageCheck size={38} />
                  </div>
                )}
              </div>
            </div>
          </Col>
        </Row>
      </Row>
      {/* backgroundColor: "#0e1111", */}
      <Row>
        <h1
          style={{
            marginTop: "1vw",
            marginLeft: "1vw",
            marginBottom: "2vw",
            fontSize: "25px",
          }}
        >
          Created Announcements ({announcements.length})
        </h1>

        <AnnouncementsColumn announcements={announcements} />
      </Row>
    </Container>
  );
}

export default ProfileComponent;
