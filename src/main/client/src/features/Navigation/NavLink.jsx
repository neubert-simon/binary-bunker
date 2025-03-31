import { Link } from "react-router-dom";
import styled from "styled-components";

// Styled component for the navigation link
const StyledLink = styled(Link)`
  position: relative;
  display: inline-block;
  font-family: impact;
  font-size: 7.5rem;
  text-transform: uppercase;
  line-height: 1.3;
  color: var(--color-white);
  text-decoration: none;
  opacity: 0;
  cursor: inherit;
  transition: color 0.3s ease-out;

  &::before {
    content: attr(data-text);
    position: absolute;
    inset: 0.4rem;
    color: transparent;
    -webkit-text-stroke-width: 0.2rem;
    -webkit-text-stroke-color: var(--color-black);
    opacity: 0;
    z-index: 2;
    transition: opacity 0.3s ease;
  }

  &:hover::before {
    opacity: 1;
  }

  @media (max-width: 576px) {
    font-size: 4rem;
    line-height: 1.4;
  }

  @media (min-width: 577px) and (max-width: 768px) {
    font-size: 6rem;
    line-height: 1.4;
  }

  @media (min-width: 769px) and (max-width: 992px) {
    font-size: 4rem;
    line-height: 1.4;
  }

  @media (min-width: 993px) and (max-width: 1200px) {
    font-size: 5.5rem;
    line-height: 1.4;
  }

  @media (min-width: 1200px) and (max-width: 1599px) {
    font-size: 7rem;
    line-height: 1.4;
  }
`;

/**
 * NavLink component renders a styled link for navigation in the app.
 * It shows the label text with a transparent stroke effect, which becomes visible on hover.
 * The font size adjusts responsively based on the screen size.
 *
 * @param {Object} props The component props.
 * @param {string} props.label The text to be displayed as the link.
 * @param {function} props.clickHandler The function to handle the click event.
 * @returns {JSX.Element} The rendered styled link.
 */
function NavLink({ label, clickHandler }) {
  return (
    <StyledLink
      className="navigation-link"
      onClick={clickHandler}
      data-text={label}
    >
      {label}
    </StyledLink>
  );
}

export default NavLink;
