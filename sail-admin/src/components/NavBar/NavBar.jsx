import { Container, Stack, Link } from "@mui/material";
import { ReactComponent as Logo } from "../../assets/logo-icon.svg";
import LogoutIcon from "@mui/icons-material/Logout";

const nav_styles = {
  nav_container: {
    backgroundColor: "#fff",
    padding: "1rem",
    borderBottom: "1px solid #ddd",
  },
  link_stack: {
    display: "flex",
    alignItems: "center",
  },
  link: {
    textDecoration: "none",
    color: "#666",
  },
};

const NavBar = () => {
  return (
    <nav style={nav_styles.nav_container}>
      <Container>
        <Stack
          direction="row"
          spacing={2}
          sx={{
            ...nav_styles.link_stack,
            justifyContent: "space-between",
          }}
        >
          <Stack direction="row" spacing={4} sx={nav_styles.link_stack}>
            <Link href="/">
                <Logo width={50} height={50} />
            </Link>
            <Link sx={nav_styles.link} href="/orders">
              Orders
            </Link>
            <Link sx={nav_styles.link} href="/products">
              Products
            </Link>
          </Stack>
          <Link sx={nav_styles.link} href="/logout">
            <LogoutIcon />
          </Link>
        </Stack>
      </Container>
    </nav>
  );
};

export default NavBar;
