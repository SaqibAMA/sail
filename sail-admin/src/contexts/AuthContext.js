import React, { useContext, useState, createContext } from "react";
import { app } from "../firebase";
import { getAuth, GoogleAuthProvider, signInWithPopup } from "firebase/auth";

import { verifyAdmin } from "../DAL/auth";

const AuthContext = createContext();

export const useAuth = () => {
  return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null);
  const [loginError, setLoginError] = useState(false);

  const auth = getAuth(app);
  const provider = new GoogleAuthProvider();

  const signInWithGoogle = () => {
    setLoginError(false);
    signInWithPopup(auth, provider)
      .then((res) => {
        setCurrentUser(null);
        verifyAdmin(
          res.user.email,
          () => {
            setCurrentUser({
              email: res.user.email,
              profileImage: res.user.photoURL,
            });
          },
          () => {
            setLoginError(true);
          }
        );
      })
      .catch((err) => {
        setCurrentUser(null);
      });
  };

  const handleLogout = () => {
    setCurrentUser(null);
    setLoginError(false);
  };

  return (
    <AuthContext.Provider
      value={{ currentUser, loginError, signInWithGoogle, handleLogout }}
    >
      {children}
    </AuthContext.Provider>
  );
};
