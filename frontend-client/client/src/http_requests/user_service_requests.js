import axios from "axios";
import { useSelector } from "react-redux";

export const registerRequest = async (data) => {
  return await axios({
    method: "POST",
    url: "http://localhost:8087/api/user",
    data: data,
    headers: {
      "Content-Type": "application/json",
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

export const loginRequest = async (formData) => {
  return await axios({
    method: "POST",
    url: "http://localhost:8087/user/login",
    data: formData,
    headers: {
      "Content-Type": "application/json",
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

export const getUserInformation = async (data) => {
  return await axios({
    method: "GET",
    url: `http://localhost:8087/api/user/${data.user_id}`,
    headers: {
      Authorization: `Bearer ${data.token}`,
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

export const updateUserInformation = async (data) => {
  console.log("mydata ->", data);
  return await axios({
    method: "POST",
    url: `http://localhost:8087/api/user/${data.userId}/update`,
    headers: {
      Authorization: `Bearer ${data.token}`,
    },
    data: data.data,
  })
    .then(function (response) {
      console.log("response", response);
      return response;
    })
    .catch(function (error) {
      console.log("error", error);
      return error;
    });
};
