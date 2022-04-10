import { initializeApp } from "firebase/app";
import { getAuth, GoogleAuthProvider, signInWithPopup } from 'firebase/auth'

const firebaseConfig = {
  apiKey: "AIzaSyAU0i8RWuNg6DKiWIM8rb4cKmVCemP4yYM",
  authDomain: "sail-admin-83c4f.firebaseapp.com",
  projectId: "sail-admin-83c4f",
  storageBucket: "sail-admin-83c4f.appspot.com",
  messagingSenderId: "611139407057",
  appId: "1:611139407057:web:ab3a384edd568df188c9f0",
  measurementId: "G-9HRHF0V08E"
};

const app = initializeApp(firebaseConfig);

const provider = new GoogleAuthProvider();

export const auth = getAuth(app);
export const signInWithGoogle = (callback) => {
    signInWithPopup(auth, provider).then(res => {
        callback(res);
    }).catch(err => {
        console.log(err);
    });
}