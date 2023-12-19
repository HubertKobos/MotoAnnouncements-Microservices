import React, { useState } from "react";
import { Button, FloatingLabel, InputGroup } from "react-bootstrap";
import Form from "react-bootstrap/Form";
import { createAnnouncementRequest } from "../../http_requests/AnnouncementServiceRequests";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import AnnouncementProperlyCreatedAlert from "../Alerts/AnnouncementProperlyCreatedAlert";
import { BRAND_OPTIONS } from "../../const/BRAND_OPTIONS.js";
import { CATEGORY_OPTIONS } from "../../const/CATEGORY_OPTIONS";
import { FUEL_TYPE } from "../../const/FUEL_TYPE";
import { GEARBOX_TYPE } from "../../const/GEARBOX_TYPE";
import { DRIVE_TYPE } from "../../const/DRIVE_TYPE";
import { COUNTRIES } from "../../const/COUNTRIES";

function CreateAnnouncementForm() {
  const { userId } = useSelector((state) => state.user.data);

  const [properlyCreatedAnnouncement, setProperlyCreatedAnnouncement] =
    useState(false);
  const [disableAllFields, setDisableAllFields] = useState(false);

  const [brand, setBrand] = useState();
  const [model, setModel] = useState();
  const [category, setCategory] = useState();
  const [description, setDescription] = useState();
  const [yearOfProduction, setYearOfProduction] = useState();
  const [price, setPrice] = useState();
  const [condition, setCondition] = useState();
  const [photoes, setPhotoes] = useState([]);
  const [isImported, setIsImported] = useState(false);
  const [cubicCapacity, setCubicCapacity] = useState();
  const [fuelType, setFuelType] = useState();
  const [power, setPower] = useState();
  const [gearType, setGearType] = useState();
  const [driveType, setDriveType] = useState();
  const [numberOfDoors, setNumberOfDoors] = useState();
  const [numberOfSeats, setNumberOfSeats] = useState();
  const [color, setColor] = useState();
  const [countryOfOrigin, setCountryOfOrigin] = useState();
  const [accidentFree, setAccidentFree] = useState(false);

  const filesHandler = (e) => {
    const files = Array.from(e.target.files);
    console.log("ffff", files);
    setPhotoes(files);
  };

  const submitHandler = (e) => {
    e.preventDefault();
    const formData = new FormData();
    const data = {
      authorId: userId,
      brand: brand,
      model: model,
      category: category,
      description: description,
      yearOfProduction: yearOfProduction,
      price: price,
      condition: condition === "on" ? "BRAND_NEW" : "USED",
      imported: isImported === "on" ? true : false,
      cubicCapacity: cubicCapacity,
      fuelType: fuelType,
      power: power,
      gearboxType: gearType,
      driveType: driveType,
      numberOfDoors: numberOfDoors,
      numberOfSeats: numberOfSeats,
      color: color,
      countryOfOrigin: countryOfOrigin,
      accidentFree: accidentFree === "on" ? true : false,
    };

    for (let i = 0; i < photoes.length; i++) {
      formData.append("file", photoes[i]);
    }
    formData.append(
      "data",
      new Blob([JSON.stringify(data)], { type: "application/json" })
    );

    createAnnouncementRequest(formData)
      .then((response) => {
        console.log(response);
        if (response.status === 201) {
          setDisableAllFields(true);
          setProperlyCreatedAnnouncement(true);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      {properlyCreatedAnnouncement && <AnnouncementProperlyCreatedAlert />}
      <Form onSubmit={submitHandler}>
        <FloatingLabel label="Brand" className="mb-3">
          <Form.Select
            type="text"
            required
            onChange={(e) => setBrand(e.target.value)}
            disabled={disableAllFields}
          >
            <option>Choose brand</option>
            {BRAND_OPTIONS.map((brand) => (
              <option key={brand} value={brand}>
                {brand}
              </option>
            ))}
          </Form.Select>
        </FloatingLabel>
        <FloatingLabel label="Model" className="mb-3">
          <Form.Control
            type="text"
            required
            onChange={(e) => setModel(e.target.value)}
            disabled={disableAllFields}
          />
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingSelect"
          label="Category"
          className="mb-3"
        >
          <Form.Select
            aria-label="Floating label select example"
            required
            onChange={(e) => setCategory(e.target.value)}
            disabled={disableAllFields}
          >
            <option>Choose category</option>
            {CATEGORY_OPTIONS.map((category) => (
              <option key={category} value={category}>
                {category}
              </option>
            ))}
          </Form.Select>
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingPassword"
          label="Description"
          className="mb-3"
        >
          <Form.Control
            as="textarea"
            style={{ height: "12vh" }}
            required
            onChange={(e) => setDescription(e.target.value)}
            disabled={disableAllFields}
          />
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingYear"
          label="Year of production"
          className="mb-3"
        >
          <Form.Control
            type="number"
            min="1"
            max="2099"
            required
            onChange={(e) => setYearOfProduction(e.target.value)}
            disabled={disableAllFields}
          />
        </FloatingLabel>

        <FloatingLabel
          controlId="floatingCubicCapacity"
          label="Cubic capacity"
          className="mb-3"
        >
          <Form.Control
            type="number"
            min="1"
            required
            onChange={(e) => setCubicCapacity(e.target.value)}
            disabled={disableAllFields}
          />
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingPower"
          label="Power (HP)"
          className="mb-3"
        >
          <Form.Control
            type="number"
            min="1"
            required
            onChange={(e) => setPower(e.target.value)}
            disabled={disableAllFields}
          />
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingGear"
          label="Gear type"
          className="mb-3"
        >
          <Form.Select
            aria-label="Floating label select example"
            required
            onChange={(e) => setGearType(e.target.value)}
            disabled={disableAllFields}
          >
            <option>Choose gearbox type</option>
            {GEARBOX_TYPE.map((gear) => (
              <option key={gear} value={gear}>
                {gear}
              </option>
            ))}
          </Form.Select>
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingDrive"
          label="Drive type"
          className="mb-3"
        >
          <Form.Select
            aria-label="Floating label select example"
            required
            onChange={(e) => setDriveType(e.target.value)}
            disabled={disableAllFields}
          >
            <option>Choose drive type</option>
            {DRIVE_TYPE.map((drive) => (
              <option key={drive} value={drive}>
                {drive}
              </option>
            ))}
          </Form.Select>
        </FloatingLabel>

        <FloatingLabel label="Brand" className="mb-3">
          <Form.Select
            type="text"
            required
            onChange={(e) => setFuelType(e.target.value)}
            disabled={disableAllFields}
          >
            <option>Choose fuel type</option>
            {FUEL_TYPE.map((fuel) => (
              <option key={fuel} value={fuel}>
                {fuel}
              </option>
            ))}
          </Form.Select>
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingDoors"
          label="Number of doors"
          className="mb-3"
        >
          <Form.Control
            type="number"
            min="1"
            max="2099"
            required
            onChange={(e) => setNumberOfDoors(e.target.value)}
            disabled={disableAllFields}
          />
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingSeats"
          label="Number of seats"
          className="mb-3"
        >
          <Form.Control
            type="number"
            min="1"
            max="2099"
            required
            onChange={(e) => setNumberOfSeats(e.target.value)}
            disabled={disableAllFields}
          />
        </FloatingLabel>
        <FloatingLabel label="Colour" className="mb-3">
          <Form.Control
            type="text"
            required
            onChange={(e) => setColor(e.target.value)}
            disabled={disableAllFields}
          />
        </FloatingLabel>
        <FloatingLabel label="Brand" className="mb-3">
          <Form.Select
            type="text"
            required
            onChange={(e) => setCountryOfOrigin(e.target.value)}
            disabled={disableAllFields}
          >
            <option>Choose country of origin</option>
            {COUNTRIES.map((country) => (
              <option key={country} value={country}>
                {country}
              </option>
            ))}
          </Form.Select>
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingPrice"
          label="Price ($)"
          className="mb-3"
        >
          <Form.Control
            type="number"
            step="0.01"
            min="0"
            required
            onChange={(e) => setPrice(e.target.value)}
            disabled={disableAllFields}
          />
        </FloatingLabel>
        <Form.Group className="mb-3">
          <Form.Check
            type="checkbox"
            label="Brand new condition"
            onChange={(e) => setCondition(e.target.value)}
            disabled={disableAllFields}
          />
          <Form.Check
            type="checkbox"
            label="Imported"
            onChange={(e) => setIsImported(e.target.value)}
            disabled={disableAllFields}
          />
          <Form.Check
            type="checkbox"
            label="Accident free"
            onChange={(e) => setAccidentFree(e.target.value)}
            disabled={disableAllFields}
          />
        </Form.Group>
        <Form.Group controlId="formFileMultiple" className="mb-3">
          <Form.Label>Photos</Form.Label>
          <Form.Control
            type="file"
            multiple
            onChange={filesHandler}
            disabled={disableAllFields}
            required
          />
        </Form.Group>
        <Form.Group style={{ display: "flex" }}>
          <Button
            variant="dark"
            style={{ marginLeft: "auto" }}
            type="submit"
            disabled={disableAllFields}
          >
            Create announcement
          </Button>
        </Form.Group>
      </Form>
    </>
  );
}

export default CreateAnnouncementForm;
