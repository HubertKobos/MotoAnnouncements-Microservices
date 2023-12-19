import React from "react";
import Layout from "../components/Layout";
import CreateAnnouncementForm from "../components/forms/CreateAnnouncementForm";

function CreateAnnouncementScreen() {
  return <Layout middle_col={<CreateAnnouncementForm />} />;
}

export default CreateAnnouncementScreen;
