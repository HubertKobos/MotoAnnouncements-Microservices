import axios from "axios";

export const createAnnouncementRequest = async (formData) => {
  return await axios({
    method: "POST",
    url: "http://localhost:8087/api/announcements",
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  })
    .then(function (response) {
      return response;
    })
    .catch(function (error) {
      return error;
    });
};

export const getAnnouncements = async (userId) => {
  return await axios({
    method: "GET",
    url: `http://localhost:8087/api/announcements/${userId}`,
  })
    .then(function (response) {
      return response;
    })
    .catch(function (error) {
      return error;
    });
};

export const getAnnouncement = async (announcementId, token) => {
  return await axios({
    method: "GET",
    url: `http://localhost:8087/api/announcements/spec/${announcementId}`,
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
    .then(function (response) {
      return response;
    })
    .catch(function (error) {
      return error;
    });
};

export const filterAnnouncements = async (filterFormBody) => {
  return await axios({
    method: "POST",
    url: "http://localhost:8087/api/announcements/filter",
    data: filterFormBody,
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then(function (response) {
      return response;
    })
    .catch(function (error) {
      return error;
    });
};

export const createComment = async (commentBody, token) => {
  return await axios({
    method: "POST",
    url: `http://localhost:8087/api/announcements/comment`,
    data: commentBody,
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + token,
    },
  })
    .then(function (response) {
      return response;
    })
    .catch(function (error) {
      return error;
    });
};
