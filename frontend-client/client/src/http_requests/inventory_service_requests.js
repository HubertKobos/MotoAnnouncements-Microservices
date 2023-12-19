import axios from "axios";

export const getInventory = async () => {
  return await axios({
    method: "GET",
    url: "http://localhost:8087/api/inventory",
  })
    .then(function (response) {
      return response;
    })
    .catch(function (error) {
      console.log("error", error);
      return error;
    });
};
