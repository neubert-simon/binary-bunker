import styled, { css } from "styled-components";
import { Link } from "react-router-dom";

/**
 * A styled component that serves as a container for displaying interactive links.
 * The container is a flexbox, centered with wrap capability for responsiveness.
 *
 * @returns {JSX.Element} The styled container that wraps interactive links.
 */
const StyledIPLinks = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  gap: 1.5rem;
`;

export default StyledIPLinks;