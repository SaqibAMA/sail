import { Box, Button, Typography } from "@mui/material";
import GoogleIcon from "@mui/icons-material/Google";
import { Navigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";

const page_styles = {
  login_container: {
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
  },
  title: {
    fontSize: "2rem",
    fontWeight: "bold",
  },
};

const Login = () => {
  const auth = useAuth();

  return (
    <section style={page_styles.login_container}>

      {auth.currentUser && <Navigate to="/" />}

      <Typography sx={page_styles.title}>Sign In</Typography>

      <Typography>Please sign in using your google account.</Typography>

      <Box mt={5}>
        <Button
          variant="outlined"
          startIcon={<GoogleIcon />}
          onClick={auth.signInWithGoogle}
        >
          Sign In with Google
        </Button>
      </Box>
    </section>
  );
};

export default Login;
