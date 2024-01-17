import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

// Récupère le token, s'il n'y en a pas ou undefined redirige vers /login
const SecureRoute = (props) => {
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const checkUserToken = () => {
    const userToken = localStorage.getItem("user-token");
    if (!userToken || userToken === "undefined") {
      setIsLoggedIn(false);
      return navigate("/login");
    }
    setIsLoggedIn(true);
  };
  useEffect(() => {
    checkUserToken();
  }, [isLoggedIn]);
  return <>{isLoggedIn ? props.children : null}</>;
};

export default SecureRoute;
