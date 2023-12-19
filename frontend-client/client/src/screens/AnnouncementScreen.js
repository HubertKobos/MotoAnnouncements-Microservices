import React, { useEffect, useState } from "react";
import Header from "../components/Header";
import { Button, CardImg, Carousel, Form } from "react-bootstrap";
import { getAnnouncement } from "../http_requests/AnnouncementServiceRequests";
import { useNavigate, useParams } from "react-router-dom";
import { GoDot } from "react-icons/go";
import "../index.css";
import { useSelector } from "react-redux";
import CenteredSpinner from "../components/CenteredSpinner";
import AnnouncementsDetailsTable from "../components/Tables/AnnouncementsDetailsTable";
import AnnouncementComment from "../components/AnnouncementComment";
import { createComment } from "../http_requests/AnnouncementServiceRequests";

function AnnouncementScreen() {
  const navigate = useNavigate();
  const { userId, token } = useSelector((state) => state.user.data);
  const [index, setIndex] = useState(0);
  const { announcementId } = useParams();
  const [announcement, setAnnouncement] = useState();
  const [announcementUser, setAnnouncementUser] = useState();
  const [loading, setLoading] = useState(true);
  const [comments, setComments] = useState([]);
  const [commentContent, setCommentContent] = useState();

  function formatText(text) {
    text = text.replace(/_/g, " ");
    return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
  }
  console.log(commentContent);
  useEffect(() => {
    getAnnouncement(announcementId, token)
      .then((response) => {
        if (response.status === 200) {
          console.log(response.data);
          setAnnouncement(response.data.announcement);
          setAnnouncementUser(response.data.user);
          setComments(response.data.announcement.comments);
          setLoading(false);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }, [announcementId]);

  const handleSelect = (selectedIndex) => {
    setIndex(selectedIndex);
  };

  const submitNewComment = () => {
    if (commentContent !== "") {
      createComment(
        {
          authorId: userId,
          content: commentContent,
          announcementId: announcementId,
        },
        token,
        announcementId
      )
        .then((response) => {
          console.log(response);
        })
        .catch((error) => console.log(error));
    }
  };

  return (
    <div>
      <Header />
      {loading ? (
        <CenteredSpinner />
      ) : (
        <>
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              marginTop: "15px",
              
            }}
          >
            <div style={{ width: "25vw" }}>
              <Carousel activeIndex={index} onSelect={handleSelect}>
                <Carousel.Item>
                  <CardImg src="https://fastly.picsum.photos/id/111/4400/2656.jpg?hmac=leq8lj40D6cqFq5M_NLXkMYtV-30TtOOnzklhjPaAAQ"></CardImg>
                </Carousel.Item>
              </Carousel>
            </div>
            <div style={{ marginLeft: "30px" }}>
              <h2>{formatText(announcement.brand)}</h2>
              <h2>{formatText(announcement.model)}</h2>
              <hr />
              <h2 style={{ fontSize: "15px", maxWidth: "500px" }}>{announcement.description}</h2>
              <hr />
              <h2 style={{ fontSize: "13px" }}>
                {announcement.yearOfProduction}
                <GoDot />
                {announcement.condition === "BRAND_NEW"
                  ? "brand new"
                  : "used condition"}
                <GoDot />
                {announcement.cubicCapacity} cm³
                <GoDot />
                {announcement.power} HP
              </h2>
              <hr />
              <h3>Price: {announcement.price} $</h3>
              <hr />
              <div>
                <h3
                  style={{
                    fontSize: "15px",
                    margin: "0px",
                    textDecoration: "underline",
                    cursor: "pointer",
                  }}
                  onClick={() => navigate(`/profile/${announcementUser.id}`)}
                >
                  Owner: {announcementUser.firstName}{" "}
                  {announcementUser.lastName}
                </h3>
              </div>
            </div>
          </div>
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              marginTop: "20px",
            }}
          >
            <h2>Details</h2>
          </div>
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              marginTop: "10px",
            }}
          >
            <AnnouncementsDetailsTable announcement={announcement} />
          </div>
          <div
            style={{
              display: "flex",
              alignItems: "center",
              flexDirection: "column",
            }}
          >
            <div style={{ display: "table-column", justifyContent: "center" }}>
              <h2>Comments ({comments.length})</h2>
              <div>
                {comments.length !== 0 &&
                  comments?.map((comment) => (
                    <AnnouncementComment data={comment} />
                  ))}
              </div>
            </div>
            <div>
              <Form style={{ width: "30vw" }}>
                <Form.Group className="mb-1" controlId="commentContent">
                  {/* <Form.Label>Comment ...</Form.Label> */}
                  <Form.Control
                    as="textarea"
                    rows={3}
                    onChange={(e) => setCommentContent(e.target.value)}
                  />
                </Form.Group>
              </Form>

              <Button
                style={{
                  marginBottom: "20px",
                  marginLeft: "auto",
                  background: "none",
                  color: "black",
                }}
                variant="dark"
                onClick={() => submitNewComment()}
              >
                Create new comment
              </Button>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default AnnouncementScreen;
