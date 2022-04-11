import React, { useContext, useState, createContext } from "react";
import { app, db } from "../firebase";
import { getAuth, GoogleAuthProvider, signInWithPopup } from "firebase/auth";
import { collection, getDocs } from "firebase/firestore";

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
    signInWithPopup(auth, provider)
      .then((res) => {

        // access the allowed admins db
        getDocs(collection(db, "admin-accounts")).then((querySnapshot) => {
          
          // by default assume that user is not allowed
          setCurrentUser(null);
          
          // check if there are any admins that match the signed-in email
          querySnapshot.forEach(doc => {
            // if admin email is found, set currentUser to the admin
            if (doc.data().email === res.user.email) {
                setCurrentUser({
                  email: res.user.email,
                  profileImage: res.user.photoURL,
                });
            }
          });

          if (!currentUser) {
            setLoginError(true);
          }

        });
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
