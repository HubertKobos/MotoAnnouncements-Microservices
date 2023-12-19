import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Alert } from "react-bootstrap";

function AnnouncementProperlyCreatedAlert() {
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
      Properly created announcement redirecting in {secondsToCount}s
    </Alert>
  );
}

export default AnnouncementProperlyCreatedAlert;
