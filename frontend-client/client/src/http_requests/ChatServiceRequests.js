import axios from "axios";

export const getConversations = async (conversation_id) => {
  return await axios({
    method: "GET",
    url: `http://localhost:8087/api/chat/conversations/${conversation_id}`,
  })
    .then(function (response) {
      return response;
    })
    .catch(function (error) {
      console.log("error", error);
      return error;
    });
};

export const getLastMessages = async (
  userId1,
  userId2,
  numberOfLastMessagesToGet
) => {
  return await axios({
    method: "GET",
    url: `http://localhost:8087/api/chat/${userId1}/${userId2}/${numberOfLastMessagesToGet}`,
  })
    .then(function (response) {
      return response;
    })
    .catch(function (error) {
      console.log("error", error);
      return error;
    });
};
