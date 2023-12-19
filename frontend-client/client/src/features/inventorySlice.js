import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  data: [],
  errorMessage: "",
  errorStatusCode: "",
};

export const inventorySlice = createSlice({
  name: "inventory",
  initialState,
  reducers: {
    setInventoryData: (state, action) => {
      // reducer used only on response.code === 200
      // so resets the errors
      state.errorMessage = "";
      state.errorStatusCode = "";
      state.data = action.payload;
    },
    setInventoryError: (state, action) => {
      state.data = [];
      state.errorMessage = action.payload["error_message"];
      state.errorStatusCode = action.payload["error_status_code"];
    },
  },
});

export const { setInventoryData, setInventoryError } = inventorySlice.actions;
export default inventorySlice.reducer;
