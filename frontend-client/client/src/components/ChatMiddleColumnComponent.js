import React, { useEffect, useRef, useState } from "react";
import { Form } from "react-bootstrap";
import { useSelector } from "react-redux";
import { AiOutlineSend } from "react-icons/ai";
import { Col, Row, Spinner } from "react-bootstrap";
import MessageComponent from "./MessageComponent";

import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { useParams } from "react-router-dom";

function ChatMiddleColumnComponent(data) {
  const { userId: fromUserId } = useSelector((state) => state.user.data);
  const [receivedMessages, setReceivedMessages] = useState([]);
  const socketRef = useRef(null);
  const messageContainerRef = useRef(null);
  const { userId: toUserId } = useParams();
  const [message, setMessage] = useState();
  const [allMessages, setAllMessages] = useState([]);

  useEffect(() => {
    setAllMessages(data.messages);
  }, [data]);
  useEffect(() => {
    // Create a new WebSocket instance
    const sock = new SockJS("http://localhost:8087/stomp");

    let client = Stomp.over(sock);

    // Assign the socket to the ref variable
    socketRef.current = client;

    client.connect({}, (frame) => {
      console.log("here connecting 0<", frame);
      // Subscribe to "/topic/messages". Whenever a message arrives add the text in a list-item element in the unordered list.
      client.subscribe("/topic/asd", (payload) => {
        console.log("MESSAGE RECEIVED --> ", payload.body);
        if (JSON.parse(payload.body)["owner"] !== fromUserId) {
          setAllMessages((prev) => [...prev, JSON.parse(payload.body)]);
        }
        console.log("here subsrbing -> ", JSON.parse(payload.body));
      });
    });

    // Event handler for receiving messages from the server
    client.onmessage = (event) => {
      console.log("received message from the server !");
      const data = JSON.parse(event.data);
      const messages = [];
      console.log("data", data);
    };

    // Clean up the WebSocket connection when the component is unmounted
    return () => {
      client.disconnect();
    };
  }, []);

  function sendMessage() {
    const payload = {
      message: message,
      owner: fromUserId,
      creationTime: new Date(),
    };
    const headers = {
      "Message-From-userId": fromUserId,
      "Message-To-userId": toUserId,
    };
    console.log("send -> ", payload);
    setAllMessages((prev) => [...prev, payload]);
    socketRef.current.send(`/app/chat`, headers, JSON.stringify(payload));
  }

  useEffect(() => {
    // Scroll to the bottom of the container after adding the message
    if (messageContainerRef.current) {
      messageContainerRef.current.scrollTop =
        messageContainerRef.current.scrollHeight;
    }
  }, [allMessages]);

  return (
    <>
      {data.loader ? (
        <Spinner />
      ) : (
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            height: "84vh",
            marginTop: "40px",
          }}
        >
          <div
            className="messages-container"
            style={{
              height: "77vh",
              overflow: "auto",
              // width: "28vw",
              minWidth: "250px",
            }}
            ref={messageContainerRef}
          >
            {allMessages?.map((message) => (
              <MessageComponent data={message} key={message.creationTime} />
            ))}
          </div>
          <div
            sm={3}
            md={3}
            lg={3}
            style={{
              // position: "fixed",
              // bottom: "0",
              // width: "28vw",
              minWidth: "200px",
            }}
          >
            <Row>
              <Col lg={11}>
                <Form>
                  <Form.Group
                    className="mb-3"
                    controlId="exampleForm.ControlTextarea1"
                  >
                    <Form.Control
                      style={{
                        backgroundColor: "rgb(31, 38, 38)",
                        border: "none",
                        color: "white",
                      }}
                      as="textarea"
                      onChange={(e) => setMessage(e.target.value)}
                      rows={1}
                      // onChange={(e) => setMessage(e.target.value)}
                      onKeyDown={(e) => {
                        if (e.key === "Enter" && !e.shiftKey) {
                          e.preventDefault();
                          sendMessage(e.target.value);
                          e.target.value = "";
                        }
                      }}
                    />
                  </Form.Group>
                </Form>
              </Col>
              <Col style={{ padding: "0" }}>
                <AiOutlineSend
                  size={34}
                  style={{ marginTop: "4px" }}
                  color="rgb(229, 161, 242"
                />
              </Col>
            </Row>
          </div>
        </div>
      )}
    </>
  );
}

export default ChatMiddleColumnComponent;
