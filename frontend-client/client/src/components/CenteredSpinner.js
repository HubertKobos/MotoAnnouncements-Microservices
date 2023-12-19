import React from "react";
import { Spinner } from "react-bootstrap";

function CenteredSpinner() {
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        minHeight: "80vh",
      }}
    >
      <Spinner />
    </div>
  );
}

export default CenteredSpinner;
