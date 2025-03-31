import { useState } from "react";
import gsap from "gsap";
import { useGSAP } from "@gsap/react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import NavLink from "./NavLink.jsx";
import NavigationToggle from "./NavigationToggle.jsx";
import NavigationMenu from "./NavigationMenu.jsx";
import { updateIsAnimating, updateIsOpen } from "../../services/navigationSlice.js";
import { useHandleLinkClick } from "../../hooks/useHandleLinkClick.js";
import {animateNavigation} from "../../services/animateNavigation.js";
import UserState from "./UserState.jsx";

// Styled component for the navigation bar
const StyledNavigation = styled.nav`
  position: relative;
  width: 100%;
  height: 12rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 3rem;
  padding: 0 16rem;

  @media (max-width: 576px) {
    padding: 0 2rem;
    height: 8rem;
  }

  @media (min-width: 577px) and (max-width: 768px) {
    padding: 0 3rem;
  }

  @media (min-width: 769px) and (max-width: 992px) {
    padding: 0 7rem;
  }

  @media (min-width: 993px) and (max-width: 1200px) {
    padding: 0 9rem;
  }

  @media (orientation: landscape) and (max-height: 576px) {
    padding: 0 11rem;
    height: 5rem;
  }
`;

/**
 * Navigation component that handles the display and animation of the navigation menu.
 * It includes links to various pages and the ability to toggle between open/closed states.
 *
 * @returns {JSX.Element} The navigation bar with toggle and navigation links.
 */
function Navigation() {
  const [hasClicked, setHasClicked] = useState(false); // State to track whether a link has been clicked
  const handleLinkClick = useHandleLinkClick(); // Hook to handle link click behavior
  const dispatch = useDispatch(); // Redux dispatch function to update the state
  // Redux selector to get the current state of the navigation, whether it's open or animating
  const { isOpen, isAnimating } = useSelector((state) => state.navigation);

  // GSAP hook for animations triggered by changes to the isOpen or hasClicked state
  useGSAP(() => {
    if (!hasClicked || isAnimating) return;

    // Animate the navigation: opens/closes the menu with a smooth transition
    animateNavigation({ isOpen, setHasClicked, dispatch });
  }, [isOpen, hasClicked]);

  /**
   * Handles the toggle button click to open/close the navigation menu.
   * It checks if an animation is currently in progress before updating the state.
   */
  function handleNavigationToggleClick() {
    if (isAnimating) return;

    setHasClicked(true);
    dispatch(updateIsOpen(!isOpen));
  }

  return (
    <>
      <StyledNavigation>
        <NavigationToggle
          isToggleOpen={isOpen}
          handleNavigationToggleClick={handleNavigationToggleClick}
        />
        <UserState />
      </StyledNavigation>

      <NavigationMenu>
        <NavLink
          label="Home"
          clickHandler={(e) => handleLinkClick(e, "/home")}
        />
        <NavLink label="IP" clickHandler={(e) => handleLinkClick(e, "ip")} />
        <NavLink
          label="Subnetting"
          clickHandler={(e) => handleLinkClick(e, "subnet-visualizer")}
        />
        <NavLink
          label="Quiz"
          clickHandler={(e) => handleLinkClick(e, "osi-quiz")}
        />
      </NavigationMenu>
    </>
  );
}

export default Navigation;
