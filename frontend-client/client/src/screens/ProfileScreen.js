import React, { useEffect, useState } from "react";

import Layout from "../components/Layout";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import ProfileComponent from "../components/ProfileComponent";
import { getAnnouncements } from "../http_requests/AnnouncementServiceRequests";
import { getUserInformation } from "../http_requests/user_service_requests";
import { Spinner } from "react-bootstrap";
import CenteredSpinner from "../components/CenteredSpinner";

function ProfileScreen() {
  const { userId } = useParams();
  const data = useSelector((state) => state.user.data);
  const [announcements, setAnnouncements] = useState();
  const [userData, setUserData] = useState();
  const [isUserDataLoaded, setIsUserDataLoaded] = useState(false);
  const [isAnnouncementsDataLoaded, setIsAnnouncementsDataLoaded] =
    useState(false);

  useEffect(() => {
    getUserInformation({ user_id: userId, token: data.token })
      .then((response) => {
        if (response.status === 200) {
          setIsUserDataLoaded(true);
          setUserData(response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
    getAnnouncements(userId)
      .then((response) => {
        if (response.status === 200) {
          setAnnouncements(response.data);
          setIsAnnouncementsDataLoaded(true);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }, [userId, data]);

  return (
    <Layout
      middle_col={
        isAnnouncementsDataLoaded && isUserDataLoaded ? (
          <ProfileComponent userData={userData} announcements={announcements} />
        ) : (
          <CenteredSpinner />
        )
      }
    />
  );
}

export default ProfileScreen;
