import React, { useState, useEffect } from "react";
import Layout from "../components/Layout";
import { useParams } from "react-router-dom";
import ChatMiddleColumnComponent from "../components/ChatMiddleColumnComponent";
import HistoryMessagesColumn from "../components/HistoryMessagesColumn";
import {
  getConversations,
  getLastMessages,
} from "../http_requests/ChatServiceRequests";
import { useSelector, useStore } from "react-redux";

function ChatScreen() {
  const { userId } = useSelector((state) => state.user.data);
  const { userId: conversation_id } = useParams();
  const [middleColumn, setMiddleColumn] = useState();
  const [conversations, setConversations] = useState([]);
  const [messages, setMessages] = useState();
  const [middleColLoader, setMiddleCloLoader] = useState(true);
  const [numberOfLastMessagesToGet, setNumberOfLastMessagesToGet] =
    useState(20);

  useEffect(() => {
    if (conversation_id !== undefined) {
      getLastMessages(conversation_id, userId, numberOfLastMessagesToGet)
        .then((response) => {
          console.log("RESPONSE", response);
          if (response.status === 200) {
            setMessages(response.data);
            setMiddleCloLoader(false);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }
    getConversations(userId)
      .then((response) => {
        if (response.status === 200) {
          setConversations(response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }, [conversation_id, userId]);

  useEffect(() => {
    if (conversation_id) {
      setMiddleColumn(
        <ChatMiddleColumnComponent
          conversation_id={conversation_id}
          messages={messages}
          loader={middleColLoader}
        />
      );
    } else {
      setMiddleColumn(null);
    }
  }, [conversation_id, messages, middleColLoader]);

  return (
    <Layout
      middle_col={middleColumn}
      right_col={<HistoryMessagesColumn conversations={conversations} />}
    />
  );
}

export default ChatScreen;
