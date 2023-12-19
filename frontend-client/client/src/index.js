import React from "react";
import ReactDOM from "react-dom/client";
import "bootstrap/dist/css/bootstrap.min.css";
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { store } from "./store";
import { Provider } from "react-redux";
import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";

import LoginScreen from "./screens/LoginScreen";
import RegisterScreen from "./screens/RegisterScreen";
import MainScreen from "./screens/MainScreen";
import ProfileScreen from "./screens/ProfileScreen";
import AnnouncementScreen from "./screens/AnnouncementScreen";
import CreateAnnouncementScreen from "./screens/CreateAnnouncementScreen";
import ChatScreen from "./screens/ChatScreen";

const AppRouter = () => {
  const { token } = useSelector((state) => state.user.data);
  const isAuth = () => {
    if (token !== undefined) {
      return true;
    } else {
      return false;
    }
  };
  const router = createBrowserRouter([
    {
      path: "/login",
      element: isAuth() ? <Navigate to="/" /> : <LoginScreen />,
    },
    {
      path: "/register",
      element: isAuth() ? <Navigate to="/" /> : <RegisterScreen />,
    },
    {
      path: "/",
      element: isAuth() ? <MainScreen /> : <Navigate to="/login" />,
    },
    {
      path: "/profile/:userId",
      element: isAuth() ? <ProfileScreen /> : <LoginScreen />,
    },
    {
      path: "/announcement/:announcementId",
      element: isAuth() ? <AnnouncementScreen /> : <LoginScreen />,
    },
    {
      path: "/create-announcement",
      element: isAuth() ? <CreateAnnouncementScreen /> : <LoginScreen />,
    },
    {
      path: "/messages",
      element: isAuth() ? <ChatScreen /> : <LoginScreen />,
    },
    {
      path: "/messages/:userId/:fromUserId",
      element: isAuth() ? <ChatScreen /> : <LoginScreen />,
    },
  ]);
  return <RouterProvider router={router} />;
};

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <Provider store={store}>
    <React.StrictMode>
      <AppRouter />
    </React.StrictMode>
  </Provider>
);

reportWebVitals();
