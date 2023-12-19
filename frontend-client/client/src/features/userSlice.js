import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  data: localStorage.getItem("motoAnnUserData")
    ? JSON.parse(localStorage.getItem("motoAnnUserData"))
    : [],
  errorMessage: "",
  errorStatusCode: "",
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setLoginUserData: (state, action) => {
      // reducer used only on response.code === 200
      // so resets the errors
      state.errorMessage = "";
      state.errorStatusCode = "";
      state.data = action.payload;
      localStorage.setItem("motoAnnUserData", JSON.stringify(action.payload));
    },
    setLoginUserError: (state, action) => {
      state.data = [];
      state.errorMessage = action.payload["error_message"];
      state.errorStatusCode = action.payload["error_status_code"];
    },
    logout: (state) => {
      state.data = [];
      state.errorMessage = "";
      state.errorStatusCode = "";
      localStorage.removeItem("motoAnnUserData");
    },
    updateUserData: (state, action) => {
      console.log(action.payload);
      state.data = { ...state.data, ...action.payload };
    },
  },
});

export const { setLoginUserData, setLoginUserError, logout, updateUserData } =
  userSlice.actions;
export default userSlice.reducer;
