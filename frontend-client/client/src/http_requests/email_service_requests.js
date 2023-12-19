import axios from "axios";

export const registerToNewsletter = async ({ data, token }) => {
  console.log("data ", data, "token", token)
  return await axios({
    method: "POST",
    url: "http://localhost:8087/api/email/newsletter",
    data: data,
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
    .then(function (response) {
      return response;
    })
    .catch(function (error) {
      console.log("error", error);
      return error;
    });
};
