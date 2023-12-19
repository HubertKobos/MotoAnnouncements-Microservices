// Try to set up WebSocket connection with the handshake at "http://localhost:8080/stomp"
let sock = new SockJS("http://localhost:8085/stomp");

// Create a new StompClient object with the WebSocket endpoint
let client = Stomp.over(sock);

// Start the STOMP communications, provide a callback for when the CONNECT frame arrives.
client.connect({}, (frame) => {
  console.log("here connecting 0<", frame);
  // Subscribe to "/topic/messages". Whenever a message arrives add the text in a list-item element in the unordered list.
  client.subscribe("/topic/asd", (payload) => {
    console.log("here subsrbing -> ", payload);
    let message_list = document.getElementById("message-list");
    let message = document.createElement("li");
    console.log("subscribe method payload ->", payload);
    message.appendChild(
      document.createTextNode(JSON.parse(payload.body).message)
    ),
      (error) => {
        console.error("Sub error", error);
      };
    message_list.appendChild(message);
  });
});

// Take the value in the ‘message-input’ text field and send it to the server with empty headers.
function sendMessage() {
  let input = document.getElementById("message-input");
  let topic = document.getElementById("topic-input");
  let message = input.value;
  const payload = {
    message: message,
  };
  const headers = {
    topic: topic.value,
  };
  console.log("send -> ", payload.message);
  client.send(`/app/chat`, headers, JSON.stringify(payload));
}
