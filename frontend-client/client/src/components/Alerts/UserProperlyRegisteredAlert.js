import React, { useEffect, useState } from "react";
import { Alert } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

function UserProperlyRegisteredAlert() {
  const navigate = useNavigate();
  const [secondsToCount, setSecondsToCount] = useState(3);
  useEffect(() => {
    const invterval = setInterval(() => {
      if (secondsToCount > 0) {
        setSecondsToCount((prev) => prev - 1);
      } else if (secondsToCount === 0) {
        navigate("/");
      }
    }, 1000);
    return () => {
      clearInterval(invterval);
    };
  }, [secondsToCount]);
  return (
    <Alert key="success" variant="success">
      Properly registered user redirecting in {secondsToCount}s
    </Alert>
  );
}

export default UserProperlyRegisteredAlert;
