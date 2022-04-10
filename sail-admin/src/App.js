import NavBar from "./components/NavBar/NavBar";
import DashboardPage from "./pages/DashboardPage/DashboardPage";
import Login from "./pages/Login/Login";
import { Container } from "@mui/material";
import "./styles/App.scss";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext";

const app_styles = {
  page: {
    marginTop: "5rem",
  },
};

const App = () => {
  return (
    <AuthProvider>
      <main>
        <NavBar />

        <Container sx={app_styles.page}>
          <Router>
            <Routes>
              {/* Login */}
              <Route exact path="/login" element={<Login />}></Route>

              {/* Dashboard */}
              <Route exact path="/" element={<DashboardPage />} />
            </Routes>
          </Router>
        </Container>
      </main>
    </AuthProvider>
  );
};

export default App;
