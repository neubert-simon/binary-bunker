import { Outlet } from "react-router-dom";
import styled from "styled-components";
import Navigation from "../features/Navigation/Navigation.jsx";

/**
 * Styled container for the entire app layout.
 * This layout includes a navigation bar and an area where different page content can be rendered
 * via React Router's `Outlet`.
 * It has responsive styling for different screen sizes and orientations.
 *
 * @component
 */
const StyledAppLayout = styled.div`
    position: relative;
    width: 100vw;
    height: 100vh;
    padding: 0 6rem 6rem 6rem;
    display: flex;
    flex-direction: column;
    gap: 3rem;
    overflow: hidden;
    
    .page-layout {
        width: 100%;
        flex-grow: 1;
    }

    @media (max-width: 576px) {
        min-height: 100dvh;
        padding: 0 2rem 2rem 2rem;
    }

    @media (min-width: 577px) and (max-width: 1599px) {
        min-height: 100dvh;
        padding: 0 2rem 2rem 2rem;
    }

    @media (orientation: landscape) and (max-height: 576px) {
        padding: 0 1rem 1rem 1rem;
    }
`;

/**
 * Layout component that includes a navigation bar and the main content area.
 * This component serves as the main container for the application.
 * The Navigation component is rendered at the top,
 * and the content for different pages is displayed within the `Outlet` component.
 *
 * @returns {JSX.Element} The layout component with navigation and page content.
 */
function AppLayout() {
  return (
    <StyledAppLayout>
      <Navigation />
      <Outlet />
    </StyledAppLayout>
  );
}

export default AppLayout;
