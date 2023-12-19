import React from "react";
import Card from "react-bootstrap/Card";
import Carousel from "react-bootstrap/Carousel";

function AnnouncementCardCarousel() {
  return (
    <Carousel>
      <Carousel.Item>
        <Card.Img
          style={{
            maxHeight: "15vh",
            objectFit: "cover",
          }}
          variant="top"
          src="https://fastly.picsum.photos/id/111/4400/2656.jpg?hmac=leq8lj40D6cqFq5M_NLXkMYtV-30TtOOnzklhjPaAAQ"
        />
      </Carousel.Item>
    </Carousel>
  );
}

export default AnnouncementCardCarousel;
