import React, { useState } from "react";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import Form from "react-bootstrap/Form";
import { CATEGORY_OPTIONS } from "../../const/CATEGORY_OPTIONS";
import { BRAND_OPTIONS } from "../../const/BRAND_OPTIONS";
import { Button } from "react-bootstrap";
import { filterAnnouncements } from "../../http_requests/AnnouncementServiceRequests";
import { useDispatch } from "react-redux";
import { setInventoryData } from "../../features/inventorySlice";
import { GEARBOX_TYPE } from "../../const/GEARBOX_TYPE";
import { DRIVE_TYPE } from "../../const/DRIVE_TYPE";

function FilterForm() {
  const dispatch = useDispatch();
  const [isFiltering, setIsFiltering] = useState(false);

  const [brand, setBrand] = useState(null);
  const [category, setCategory] = useState(null);
  const [condition, setCondition] = useState(null);
  const [brandNewCondition, setBrandNewCondition] = useState(false);
  const [usedCondition, setUsedCondition] = useState(false);
  const [yearOfProductionMax, setYearOfProductionMax] = useState(null);
  const [yearOfProductionMin, setYearOfProductionMin] = useState(null);
  const [priceMin, setPriceMin] = useState(null);
  const [priceMax, setPriceMax] = useState(null);
  const [model, setModel] = useState(null);
  const [cubicCapacityMin, setCubicCapacityMin] = useState(null);
  const [cubicCapacityMax, setCubicCapacityMax] = useState(null);
  const [powerMin, setPowerMin] = useState(null);
  const [powerMax, setPowerMax] = useState(null);
  const [gearType, setGearType] = useState(null);
  const [driveType, setDriveType] = useState(null);
  const [numberOfDoorsMin, setNumberOfDoorsMin] = useState(null);
  const [numberOfDoorsMax, setNumberOfDoorsMax] = useState(null);
  const [numberOfSeatsMin, setNumberOfSeatsMin] = useState(null);
  const [numberOfSeatsMax, setNumberOfSeatsMax] = useState(null);
  const [colour, setColour] = useState(null);
  const [isImported, setIsImported] = useState(null);
  const [isAccidentFree, setIsAccidentFree] = useState(null);

  const filterHandler = () => {
    setIsFiltering(true);
    if (
      (brandNewCondition === true && usedCondition === true) ||
      (brandNewCondition === false && brandNewCondition === false)
    ) {
      setCondition(null);
    } else if (brandNewCondition === true && usedCondition === false) {
      setCondition("BRAND_NEW");
    } else {
      setCondition("USED");
    }
    const filterFormBody = {
      brand: brand,
      model: model,
      category: category,
      condition: condition,
      maxPrice: priceMax,
      minPrice: priceMin,
      maxYearOfProduction: yearOfProductionMax,
      minYearOfProduction: yearOfProductionMin,
      cubicCapacityMin: cubicCapacityMin,
      cubicCapactiyMax: cubicCapacityMax,
      powerMin: powerMin,
      powerMax: powerMax,
      driveType: driveType,
      gearboxType: gearType,
      numberOfDoorsMin: numberOfDoorsMin,
      numberOfDoorsMax: numberOfDoorsMax,
      numberOfSeatsMin: numberOfSeatsMin,
      numberOfSeatsMax: numberOfSeatsMax,
      colour: colour,
      accidentFree: isAccidentFree,
      imported: isImported,
    };
    console.log(filterFormBody);
    filterAnnouncements(filterFormBody)
      .then((response) => {
        console.log("filtering response ->", response);
        if (response.status === 200) {
          dispatch(setInventoryData(response.data));
          setIsFiltering(false);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };
  return (
    <div>
      <h3>Filters</h3>
      <div style={{ maxWidth: "15vw" }}>
        <FloatingLabel
          controlId="floatingSelect"
          label="Brand"
          className="mb-3"
        >
          <Form.Select
            aria-label="Floating label select example"
            onChange={(e) => setBrand(e.target.value)}
          >
            <option>Choose brand</option>
            {BRAND_OPTIONS.map((brand) => (
              <option value={brand} key={brand}>
                {brand}
              </option>
            ))}
          </Form.Select>
        </FloatingLabel>
        <FloatingLabel label="Model" className="mb-3">
          <Form.Control
            type="text"
            onChange={(e) => setModel(e.target.value)}
          />
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingSelect"
          label="Category"
          className="mb-3"
        >
          <Form.Select
            aria-label="Floating label select example"
            onChange={(e) => setCategory(e.target.value)}
          >
            <option>Choose category</option>
            {CATEGORY_OPTIONS.map((category) => (
              <option value={category} key={category}>
                {category}
              </option>
            ))}
          </Form.Select>
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingDriveType"
          label="Drive type"
          className="mb-3"
        >
          <Form.Select
            aria-label="Floating label select example"
            onChange={(e) => setDriveType(e.target.value)}
          >
            <option>Choose drive</option>
            {DRIVE_TYPE.map((drive) => (
              <option value={drive} key={drive}>
                {drive}
              </option>
            ))}
          </Form.Select>
        </FloatingLabel>
        <FloatingLabel
          controlId="floatingSelect"
          label="GearType"
          className="mb-3"
        >
          <Form.Select
            aria-label="Floating label select example"
            onChange={(e) => setGearType(e.target.value)}
          >
            <option>Choose gear type</option>
            {GEARBOX_TYPE.map((gear) => (
              <option value={gear} key={gear}>
                {gear}
              </option>
            ))}
          </Form.Select>
        </FloatingLabel>
        <div style={{ display: "flex" }}>
          <div style={{ flex: 1 }}>
            <FloatingLabel controlId="floatingYear" label="Year (min)">
              <Form.Control
                type="number"
                min="1900"
                max="2099"
                onChange={(e) => setYearOfProductionMin(e.target.value)}
              />
            </FloatingLabel>
          </div>
          <div style={{ flex: 1 }} className="mb-3">
            <FloatingLabel controlId="floatingYear" label="Year (max)">
              <Form.Control
                type="number"
                min="1900"
                max="2099"
                onChange={(e) => setYearOfProductionMax(e.target.value)}
              />
            </FloatingLabel>
          </div>
        </div>
        <div style={{ display: "flex" }}>
          <div style={{ flex: 1 }}>
            <FloatingLabel
              controlId="floatingCubicCapacity"
              label="Cubic capacity (min)"
            >
              <Form.Control
                type="number"
                min="0"
                max="10000"
                onChange={(e) => setCubicCapacityMin(e.target.value)}
              />
            </FloatingLabel>
          </div>
          <div style={{ flex: 1 }} className="mb-3">
            <FloatingLabel
              controlId="floatingCubicCapacity"
              label="Cubic capacity (max)"
            >
              <Form.Control
                type="number"
                min="0"
                max="10000"
                onChange={(e) => setCubicCapacityMax(e.target.value)}
              />
            </FloatingLabel>
          </div>
        </div>
        <div style={{ display: "flex" }}>
          <div style={{ flex: 1 }}>
            <FloatingLabel controlId="floatingDoors" label="Doors (min)">
              <Form.Control
                type="number"
                min="0"
                max="20"
                onChange={(e) => setNumberOfDoorsMin(e.target.value)}
              />
            </FloatingLabel>
          </div>
          <div style={{ flex: 1 }} className="mb-3">
            <FloatingLabel controlId="floatingDoors" label="Doors (max)">
              <Form.Control
                type="number"
                min="0"
                max="20"
                onChange={(e) => setNumberOfDoorsMax(e.target.value)}
              />
            </FloatingLabel>
          </div>
        </div>
        <div style={{ display: "flex" }}>
          <div style={{ flex: 1 }}>
            <FloatingLabel controlId="floatingSeats" label="Seats (min)">
              <Form.Control
                type="number"
                min="0"
                max="20"
                onChange={(e) => setNumberOfSeatsMin(e.target.value)}
              />
            </FloatingLabel>
          </div>
          <div style={{ flex: 1 }} className="mb-3">
            <FloatingLabel controlId="floatingSeats" label="Seats (max)">
              <Form.Control
                type="number"
                min="0"
                max="20"
                onChange={(e) => setNumberOfSeatsMax(e.target.value)}
              />
            </FloatingLabel>
          </div>
        </div>
        <div style={{ display: "flex" }}>
          <div style={{ flex: 1 }}>
            <FloatingLabel controlId="floatingPower" label="Power (min)">
              <Form.Control
                type="number"
                min="0"
                max="10000"
                onChange={(e) => setPowerMin(e.target.value)}
              />
            </FloatingLabel>
          </div>
          <div style={{ flex: 1 }} className="mb-3">
            <FloatingLabel controlId="floatingPower" label="Power (max)">
              <Form.Control
                type="number"
                min="0"
                max="10000"
                onChange={(e) => setPowerMax(e.target.value)}
              />
            </FloatingLabel>
          </div>
        </div>
        <FloatingLabel label="Colour" className="mb-3">
          <Form.Control
            type="text"
            onChange={(e) => setColour(e.target.value)}
          />
        </FloatingLabel>
        <div style={{ display: "flex" }}>
          <div style={{ flex: 1 }}>
            <FloatingLabel controlId="floatingPrice" label="Price max ($)">
              <Form.Control
                type="number"
                step="0.01"
                min="0"
                onChange={(e) => setPriceMax(e.target.value)}
              />
            </FloatingLabel>
          </div>
          <div style={{ flex: 1 }} className="mb-3">
            <FloatingLabel controlId="floatingPrice" label="Price min ($)">
              <Form.Control
                type="number"
                step="0.01"
                min="0"
                onChange={(e) => setPriceMin(e.target.value)}
              />
            </FloatingLabel>
          </div>
        </div>

        <Form.Group className="mb-3" id="formGridCheckbox">
          <Form.Check
            type="checkbox"
            label="Brand new condition"
            onChange={(e) => setBrandNewCondition(e.target.checked)}
          />
          <Form.Check
            type="checkbox"
            label="Used condition"
            onChange={(e) => setUsedCondition(e.target.checked)}
          />
          <Form.Check
            type="checkbox"
            label="Imported"
            onChange={(e) => setIsImported(e.target.checked)}
          />
          <Form.Check
            type="checkbox"
            label="Accident free"
            onChange={(e) => setIsAccidentFree(e.target.checked)}
          />
        </Form.Group>
      </div>
      <div style={{ display: "flex", maxWidth: "15vw" }}>
        <Button
          variant="dark"
          style={{ marginLeft: "auto" }}
          onClick={() => filterHandler()}
          disabled={isFiltering}
        >
          Filter
        </Button>
      </div>
    </div>
  );
}

export default FilterForm;
