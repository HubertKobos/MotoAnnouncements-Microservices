import React from "react";
import { Table, Row, Col, Spinner } from "react-bootstrap";

function AnnouncementsDetailsTable({ announcement }) {
  function formatText(text) {
    text = text.replace(/_/g, " ");
    return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
  }
  return (
    <Table
      style={{
        display: "flex",
        justifyContent: "center",
      }}
    >
      <tbody>
        <tr>
          <div
            style={{
              display: "inline-flex",
              paddingTop: "0px",
              paddingBottom: "0px",
              background: "#F5F5F5",
              marginBottom: "10px",
            }}
          >
            <div style={{ display: "flex" }}>
              <Row>
                <Col>
                  <td className="announcemets-details-cell-key">Brand</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.brand)}
                  </td>
                </Col>
                <Col style={{ marginLeft: "5vw" }}>
                  <td className="announcemets-details-cell-key">Model</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.model)}
                  </td>
                </Col>
              </Row>
            </div>
          </div>
        </tr>
        <tr>
          <div
            style={{
              display: "inline-flex",
              paddingTop: "0px",
              paddingBottom: "0px",
              background: "#F5F5F5",
              marginBottom: "10px",
            }}
          >
            <div style={{ display: "flex" }}>
              <Row>
                <Col>
                  <td className="announcemets-details-cell-key">Condition</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.condition)}
                  </td>
                </Col>
                <Col style={{ marginLeft: "5vw" }}>
                  <td className="announcemets-details-cell-key">Category</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.category)}
                  </td>
                </Col>
              </Row>
            </div>
          </div>
        </tr>
        <tr>
          <div
            style={{
              display: "inline-flex",
              paddingTop: "0px",
              paddingBottom: "0px",
              background: "#F5F5F5",
              marginBottom: "10px",
            }}
          >
            <div style={{ display: "flex" }}>
              <Row>
                <Col>
                  <td className="announcemets-details-cell-key">Color</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.color)}
                  </td>
                </Col>
                <Col style={{ marginLeft: "5vw" }}>
                  <td className="announcemets-details-cell-key">Origin</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.countryOfOrigin)}
                  </td>
                </Col>
              </Row>
            </div>
          </div>
        </tr>
        <tr>
          <div
            style={{
              display: "inline-flex",
              paddingTop: "0px",
              paddingBottom: "0px",
              background: "#F5F5F5",
              marginBottom: "10px",
            }}
          >
            <div style={{ display: "flex" }}>
              <Row>
                <Col>
                  <td className="announcemets-details-cell-key">Capacity</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {announcement.cubicCapacity}
                  </td>
                </Col>
                <Col style={{ marginLeft: "5vw" }}>
                  <td className="announcemets-details-cell-key">Drive</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.driveType)}
                  </td>
                </Col>
              </Row>
            </div>
          </div>
        </tr>
        <tr>
          <div
            style={{
              display: "inline-flex",
              paddingTop: "0px",
              paddingBottom: "0px",
              background: "#F5F5F5",
              marginBottom: "10px",
            }}
          >
            <div style={{ display: "flex" }}>
              <Row>
                <Col>
                  <td className="announcemets-details-cell-key">Fuel</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.fuelType)}
                  </td>
                </Col>
                <Col style={{ marginLeft: "5vw" }}>
                  <td className="announcemets-details-cell-key">Gearbox</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.gearboxType)}
                  </td>
                </Col>
              </Row>
            </div>
          </div>
        </tr>
        <tr>
          <div
            style={{
              display: "inline-flex",
              paddingTop: "0px",
              paddingBottom: "0px",
              background: "#F5F5F5",
              marginBottom: "10px",
            }}
          >
            <div style={{ display: "flex" }}>
              <Row>
                <Col>
                  <td className="announcemets-details-cell-key">Imported</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.imported.toString())}
                  </td>
                </Col>
                <Col style={{ marginLeft: "5vw" }}>
                  <td className="announcemets-details-cell-key">Seats</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {announcement.numberOfSeats}
                  </td>
                </Col>
              </Row>
            </div>
          </div>
        </tr>
        <tr>
          <div
            style={{
              display: "inline-flex",
              paddingTop: "0px",
              paddingBottom: "0px",
              background: "#F5F5F5",
              marginBottom: "10px",
            }}
          >
            <div style={{ display: "flex" }}>
              <Row>
                <Col>
                  <td className="announcemets-details-cell-key">Doors</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {announcement.numberOfDoors}
                  </td>
                </Col>
                <Col style={{ marginLeft: "5vw" }}>
                  <td className="announcemets-details-cell-key">Power</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {announcement.power}
                  </td>
                </Col>
              </Row>
            </div>
          </div>
        </tr>
        <tr>
          <div
            style={{
              display: "inline-flex",
              paddingTop: "0px",
              paddingBottom: "0px",
              background: "#F5F5F5",
              marginBottom: "10px",
            }}
          >
            <div style={{ display: "flex" }}>
              <Row>
                <Col>
                  <td className="announcemets-details-cell-key">Year</td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {announcement.yearOfProduction}
                  </td>
                </Col>
                <Col style={{ marginLeft: "5vw" }}>
                  <td className="announcemets-details-cell-key">
                    Accident Free
                  </td>
                </Col>
                <Col>
                  <td className="announcemets-details-cell-value">
                    {formatText(announcement.accidentFree.toString())}
                  </td>
                </Col>
              </Row>
            </div>
          </div>
        </tr>
        <hr />
      </tbody>
    </Table>
  );
}

export default AnnouncementsDetailsTable;
