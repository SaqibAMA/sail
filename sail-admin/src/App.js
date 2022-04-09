import NavBar from "./components/NavBar/NavBar";
import DashboardPage from "./pages/DashboardPage/DashboardPage";
import { Container } from "@mui/material";
import "./styles/App.scss";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

const app_styles = {
  page: {
    marginTop: "5rem",
  },
};

const App = () => {
  return (
    <main>
      <NavBar />

      <Container sx={app_styles.page}>
        <Router>
          <Routes>
            {/* default */}
            <Route path="*" element={<Navigate to="/" />}></Route>

            {/* Dashboard */}
            <Route exact path="/" element={<DashboardPage />}></Route>

            {/* Orders */}

            {/* Products */}
          </Routes>
        </Router>
      </Container>
    </main>
  );
};

export default App;
