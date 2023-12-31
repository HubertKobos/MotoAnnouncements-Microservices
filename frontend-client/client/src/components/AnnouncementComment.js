import React, { useEffect } from "react";
import { useState } from "react";
import { Col, Row, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { BsDot } from "react-icons/bs";
import { AiOutlineHeart, AiFillHeart } from "react-icons/ai";

function AnnouncementComment({ data }) {
  const navigate = useNavigate();
  const [timeDifference, setTimeDifference] = useState("");
  const [imageLoaded, setImageLoaded] = useState();
  const [answer, setAnswer] = useState();
  const [likedAnswer, setLikedAnswer] = useState(false);
  const [numberOfLikes, setNumberOfLikes] = useState(0);


  const likePostHandler = () => {

  };

  const dislikePostHandler = () => {

  };

  useEffect(() => {
    setAnswer(data);

    const now = new Date();
    const postCreatedAt = new Date(data.creationtime);
    const differenceInSeconds = parseInt(
      Math.abs(Math.floor((postCreatedAt - now) / 1000))
    );
    setTimeDifference(differenceInSeconds);
    setLikedAnswer(data.likes);
    setNumberOfLikes(data.dislikes);
  }, [data]);

  useEffect(() => {
    const image = new Image();
    if (data.userInformations.avatar === null) {
      image.src =
        "https://fastly.picsum.photos/id/1027/200/200.jpg?hmac=fiXlkLLwYm7JmmU80uRIj9g21XD4q9v_lM_2Z57UhuA";
      image.onload = () => {
        setImageLoaded(true);
      };
    } else {
      image.src = data.userInformations.avatar;
      image.onload = () => {
        setImageLoaded(true);
      };
    }
  }, [data]);

  const formatTime = (differenceInSeconds) => {
    let convertedTime;
    if (differenceInSeconds < 60) {
      convertedTime = `${differenceInSeconds} seconds`;
    } else if (differenceInSeconds > 60 && differenceInSeconds < 3600) {
      convertedTime = `${Math.floor(differenceInSeconds / 60)} minutes`;
    } else if (differenceInSeconds > 3600 && differenceInSeconds < 86400) {
      convertedTime = `${Math.floor(differenceInSeconds / 3600)} hours`;
    } else if (differenceInSeconds > 86400 && differenceInSeconds < 5184000) {
      convertedTime = `${Math.floor(differenceInSeconds / 86400)} days`;
    } else {
      convertedTime = `${Math.floor(differenceInSeconds / 5184000)} months`;
    }
    return convertedTime;
  };

  return (
    <>
      {imageLoaded ? (
        <div
          key={data.id}
          style={{
            display: "flex",
            marginTop: "10px",
          }}
        >
          <Col xs={1} sm={1} lg={1} xl={1} xxl={1} md={1}>
            <Row
              style={{
                maxWidth: "90px",
                width: "90px",
                marginRight: "0px",
                marginTop: "5px",
              }}
            >
              <img
                src={
                  data.userInformations.avatar === null
                    ? "https://fastly.picsum.photos/id/1027/200/200.jpg?hmac=fiXlkLLwYm7JmmU80uRIj9g21XD4q9v_lM_2Z57UhuA"
                    : data.userInformations.avatar
                }
                style={{
                  width: "80px",
                  borderRadius: "50%",
                  margin: "10px",
                  marginRight: "0px",
                }}
                onClick={() => navigate(`/profile/${data.userInformations.id}`)}
              />
            </Row>
          </Col>
          <Col>
            <Row
              style={{ marginLeft: "20px" }}
              onClick={() => navigate(`/profile/${data.userInformations.id}`)}
            >
              <h1
                style={{
                  fontSize: "19px",
                  fontWeight: "400",
                  color: "#1894EE",
                  marginTop: "10px",
                  marginLeft: "7px",
                }}
              >
                {answer.userInformations.firstName}{" "}
                {data.userInformations.lastName} <BsDot color="black" />
                <span style={{ fontSize: "14px", color: "#71767B" }}>
                  {formatTime(timeDifference)} ago
                </span>
              </h1>
            </Row>
            <Row style={{ marginLeft: "40px", marginRight: "5px" }}>
              {data.content}
            </Row>
            <Row>
              <hr style={{ width: "100px", marginTop: "18px" }} />
            </Row>
            <Row>
              <div
                style={{
                  display: "flex",
                  justifyContent: "center",
                }}
              >
                {!likedAnswer ? (
                  <div style={{ display: "flex" }}>
                    <h3 style={{ fontSize: "19px", marginRight: "5px" }}>
                      {numberOfLikes}
                    </h3>
                    <AiOutlineHeart
                      size={24}
                      style={{ marginRight: "20px", marginBottom: "10px" }}
                      onClick={() => likePostHandler()}
                    />
                  </div>
                ) : (
                  <div style={{ display: "flex" }}>
                    <h3 style={{ fontSize: "19px", marginRight: "5px" }}>
                      {numberOfLikes}
                    </h3>
                    <AiFillHeart
                      size={24}
                      style={{ marginRight: "20px", marginBottom: "10px" }}
                      onClick={() => dislikePostHandler()}
                    />
                  </div>
                )}
              </div>
              <hr style={{ width: "550px" }} />
            </Row>
          </Col>
          <hr />
        </div>
      ) : (
        <Spinner />
      )}
    </>

  );
}

export default AnnouncementComment;
