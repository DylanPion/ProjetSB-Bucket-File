import { useState } from "react";
import "./App.css";
import {
  Route,
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import Root from "./Components/Root";
import Layout from "./Components/Layout";
import SecureRoute from "./Components/SecureRoute";
import DashBoard from "./Pages/DashBoard";
import AddBucket from "./Pages/AddBucket";
import Login from "./Pages/Login";
import Signup from "./Pages/Signup";
import "./assets/css/bootstrap.min.css";
import "./assets/css/lineicons.css";
import "./assets/css/main.css";
import "./assets/css/materialdesignicons.min.css";
import UpdateBucket from "./Pages/UpdateBucket";
import AddFile from "./Pages/AddFile";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route element={<Root />}>
      <Route path="/dashboard" element={<Layout />}>
        <Route
          index
          element={
            <SecureRoute>
              <DashBoard />
            </SecureRoute>
          }
        />
        <Route
          path="addBucket"
          element={
            <SecureRoute>
              <AddBucket />
            </SecureRoute>
          }
        />
        <Route
          path="addFile"
          element={
            <SecureRoute>
              <AddFile />
            </SecureRoute>
          }
        />
        <Route
          path="updateBucket/:bucketId"
          element={
            <SecureRoute>
              <UpdateBucket />
            </SecureRoute>
          }
        />
      </Route>
      <Route index element={<Login />} />
      <Route path="login" element={<Login />} />
      <Route path="signup" element={<Signup />} />
    </Route>
  )
);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
