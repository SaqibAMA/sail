import { Box, Button, Typography } from "@mui/material";
import GoogleIcon from "@mui/icons-material/Google";
import { signInWithGoogle } from "../../firebase";
import { Navigate } from "react-router-dom";

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

const Login = ({ onLoginSuccess, auth }) => {

  return (
    <section style={page_styles.login_container}>

      {auth && <Navigate to="/" />}

      <Typography sx={page_styles.title}>Sign In</Typography>

      <Typography>Please sign in using your google account.</Typography>

      <Box mt={5}>
        <Button
          variant="outlined"
          startIcon={<GoogleIcon />}
          onClick={() => signInWithGoogle(onLoginSuccess)}
        >
          Sign In with Google
        </Button>
      </Box>
    </section>
  );
};

export default Login;
