import React from "react";
import AnnouncementCard from "./AnnouncementCard";
import { useSelector } from "react-redux";

function AnnouncementsColumn({ announcements }) {

  return (
    <div>
      {announcements?.map((announcement) => (
        <AnnouncementCard
          announcement_data={announcement}
          key={announcement.id}
        />
      ))}
    </div>
  );
}

export default AnnouncementsColumn;
