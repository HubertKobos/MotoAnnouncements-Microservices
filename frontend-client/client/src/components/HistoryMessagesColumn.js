import React from "react";
import UserCard from "./UserCard";

function HistoryMessagesColumn({ conversations }) {
  return (
    <div>
      {conversations.map((conv) => (
        <UserCard data={conv} key={conv.id} />
      ))}
    </div>
  );
}

export default HistoryMessagesColumn;
