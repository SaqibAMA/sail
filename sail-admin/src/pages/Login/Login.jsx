import { Stack, Button, Typography, Alert } from "@mui/material";
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

      <Stack mt={5} spacing={2}>
        <Button
          variant="outlined"
          startIcon={<GoogleIcon />}
          onClick={auth.signInWithGoogle}
        >
          Sign In with Google
        </Button>

        {auth.loginError && (
          <Alert severity="error">Sorry! You are not an admin.</Alert>
        )}
      </Stack>
    </section>
  );
};

export default Login;
