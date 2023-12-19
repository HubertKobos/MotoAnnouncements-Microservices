import React, { useEffect, useState } from "react";
import AnnouncementsColumn from "../components/AnnouncementsColumn";
import { getInventory } from "../http_requests/inventory_service_requests";
import { useDispatch, useSelector } from "react-redux";
import {
  setInventoryData,
  setInventoryError,
} from "../features/inventorySlice";
import Layout from "../components/Layout";
import FilterForm from "../components/forms/FilterForm";

function MainScreen() {
  const dispatch = useDispatch();
  const { data: announcements } = useSelector((state) => state.inventory);

  // const [announcements, setAnnouncements] = useState();

  useEffect(() => {
    getInventory().then((response) => {
      if (response.status === 200) {
        dispatch(setInventoryData(response.data));
        // setAnnouncements(response.data);
      } else if (response.code !== "ERR_NETWORK") {
        dispatch(
          setInventoryError({
            error_message: response.message,
            error_status_code: response.response.status,
          })
        );
      }
    });
  }, []);
  return (
    <div>
      <Layout
        left_col={<FilterForm />}
        middle_col={<AnnouncementsColumn announcements={announcements} />}
      />
    </div>
  );
}

export default MainScreen;
