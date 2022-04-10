import { useState } from "react";
import NavBar from "./components/NavBar/NavBar";
import DashboardPage from "./pages/DashboardPage/DashboardPage";
import Login from "./pages/Login/Login";
import { Container } from "@mui/material";
import "./styles/App.scss";
import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom";
import { allowedUsers } from "./data/allowedUsers";

const app_styles = {
  page: {
    marginTop: "5rem",
  },
};

const App = () => {
  const [user, setUser] = useState(null);

  const handleLogin = (res) => {
    const usrEmail = res.user.email;
    const usrImage = res.user.photoURL;

    if (allowedUsers.includes(usrEmail)) {
      setUser({
        usrEmail,
        usrImage,
      });
    } else {
      setUser(null);
    }
  };

  return (
    <main>

      <NavBar auth={user !== null} user={user} handleLogout={() => setUser(null)} />

      <Container sx={app_styles.page}>
        <Router>
          <Routes>
            
            {/* Login */}
            <Route
              exact
              path="/login"
              element={<Login onLoginSuccess={handleLogin} auth={user !== null} />}
            ></Route>

            {/* Dashboard */}
            <Route exact path="/" element={<DashboardPage auth={user !== null} />} />

            {/* Orders */}
            <Route exact path="/" element={<DashboardPage auth={user !== null} />} />

            {/* Products */}
            <Route exact path="/" element={<DashboardPage auth={user !== null} />} />

          </Routes>
        </Router>
      </Container>
    </main>
  );
};

export default App;
