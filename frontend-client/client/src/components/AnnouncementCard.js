import React from "react";
import Card from "react-bootstrap/Card";
import AnnouncementCardCarousel from "./AnnouncementCardCarousel";
import { useNavigate } from "react-router-dom";

function AnnouncementCard({ announcement_data }) {
  const navigate = useNavigate();
  return (
    <Card
      key={announcement_data.id}
      style={{ maxHeight: "17vh", marginBottom: "5px" }}
    >
      <Card.Body
        className="d-flex align-items-center"
        style={{ maxHeight: "17vh" }}
      >
        <AnnouncementCardCarousel />
        <div
          style={{ marginLeft: "10px", cursor: "pointer", maxHeight: "17vh" }}
          onClick={() => navigate(`/announcement/${announcement_data.id}`)}
        >
          <Card.Title>
            {announcement_data.brand} {announcement_data.model}
          </Card.Title>
          <Card.Text>{announcement_data.description.length>50 ? announcement_data.description.substring(0, 70) + "..." : announcement_data.description}</Card.Text>
          <h3 className="text-end mb-0" style={{ fontSize: "25px" }}>
            {announcement_data.price}$
          </h3>
        </div>
      </Card.Body>
    </Card>
  );
}

export default AnnouncementCard;
