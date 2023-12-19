import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./features/userSlice";
import inventoryReducer from "./features/inventorySlice";

export const store = configureStore({
  reducer: {
    user: userReducer,
    inventory: inventoryReducer,
  },
});
