import React, { useContext, useState, createContext } from "react";
import { app } from "../firebase";
import { getAuth, GoogleAuthProvider, signInWithPopup } from "firebase/auth";

const AuthContext = createContext();

export const useAuth = () => {
  return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null);

  const auth = getAuth(app);
  const provider = new GoogleAuthProvider();

  const signInWithGoogle = () => {
    signInWithPopup(auth, provider)
      .then((res) => {
        setCurrentUser({
          email: res.user.email,
          profileImage: res.user.photoURL,
        });
      })
      .catch((err) => {
        setCurrentUser(null);
      });
  };

  const handleLogout = () => {
    setCurrentUser(null);
  };

  return (
    <AuthContext.Provider
      value={{ currentUser, signInWithGoogle, handleLogout }}
    >
      {children}
    </AuthContext.Provider>
  );
};
