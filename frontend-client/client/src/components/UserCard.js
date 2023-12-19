import React from "react";
import { Card, Image, Row, Col, Spinner } from "react-bootstrap";
import default_user_avatar from "../static/images/default_user_avatar.webp";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

function UserCard({ data }) {
  const navigate = useNavigate();
  const { userId } = useSelector((state) => state.user.data);

  return (
    <>
      {data == null ? (
        <Spinner />
      ) : (
        <Card
          style={{
            display: "flex",
            alignItems: "center",
            marginBottom: "10px",
            marginTop: "10px",
            width: "30vw",
            background: "none",
            border: "none",
          }}
          onClick={() => navigate(`/messages/${data.id}/${userId}`)}
        >
          <Card.Body>
            <Row className="d-flex align-items-center">
              <Col xs={3}>
                <Image
                  src={data.avatar !== null ? data.avatar : default_user_avatar}
                  //   alt={`${firstName} ${lastName}`}
                  roundedCircle
                  style={{ width: "50px", height: "50px" }}
                />
              </Col>
              <Col xs={9}>
                <Card.Title style={{ marginLeft: "10px" }}>
                  {data.firstName} {data.lastName}
                </Card.Title>
              </Col>
            </Row>
          </Card.Body>
        </Card>
      )}
    </>
  );
}

export default UserCard;
