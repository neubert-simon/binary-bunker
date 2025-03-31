import styled from "styled-components";

// Styled component for the navigation toggle button
const StyledNavigationToggle = styled.div`
  position: relative;
  width: 7rem;
  height: 5.5rem;
  z-index: 10000;

  .upper-toggle,
  .lower-toggle {
    position: absolute;
    width: 100%;
    height: 2.5rem;
    border-radius: var(--border-radius-md);
    background-color: var(--color-gray);
    transition: all 0.2s ease-out;
  }
  
  .dark & .upper-toggle,
  .dark & .lower-toggle {
    background-color: var(--color-white);
  }

  &:hover .upper-toggle,
  &:hover .lower-toggle {
    background-color: var(--color-blue);
  }

  .lower-toggle:nth-child(2) {
    transform: translateY(3rem);
  }

  .upper-toggle::before {
    content: "";
    position: absolute;
    top: 50%;
    left: 0.8rem;
    width: 1.5rem;
    height: 1.5rem;
    background-color: var(--color-white);
    border-radius: 50%;
    transform: translateY(-50%);
  }

  .lower-toggle:nth-child(2)::before {
    content: "";
    position: absolute;
    top: 50%;
    right: 0.8rem;
    width: 1.5rem;
    height: 1.5rem;
    background-color: var(--color-white);
    border-radius: 50%;
    transform: translateY(-50%);
  }

  .dark & .upper-toggle::before {
    background-color: var(--color-main);
  }

  .dark & .lower-toggle:nth-child(2)::before {
    background-color: var(--color-main);
  }

  &.active .upper-toggle {
    transform: translateY(1.5rem) rotate(40deg);
  }

  &.active .lower-toggle {
    transform: translateY(1.5rem) rotate(-40deg);
  }

  @media (max-width: 768px) {
    width: 4rem;
    height: 3.5rem;

    .upper-toggle,
    .lower-toggle {
      height: 1.5rem;
    }

    .lower-toggle:nth-child(2) {
      transform: translateY(1.8rem);
    }

    .upper-toggle::before {
      left: 0.5rem;
      width: 0.8rem;
      height: 0.8rem;
    }

    .lower-toggle:nth-child(2)::before {
      right: 0.5rem;
      width: 0.8rem;
      height: 0.8rem;
    }

    &.active .upper-toggle {
      transform: translateY(1rem) rotate(40deg);
    }

    &.active .lower-toggle {
      transform: translateY(1rem) rotate(-40deg);
    }
  }

  @media (orientation: landscape) and (max-height: 576px) {
    width: 4rem;
    height: 3.5rem;

    .upper-toggle,
    .lower-toggle {
      height: 1.5rem;
    }

    .lower-toggle:nth-child(2) {
      transform: translateY(1.8rem);
    }

    .upper-toggle::before {
      left: 0.5rem;
      width: 0.8rem;
      height: 0.8rem;
    }

    .lower-toggle:nth-child(2)::before {
      right: 0.5rem;
      width: 0.8rem;
      height: 0.8rem;
    }

    &.active .upper-toggle {
      transform: translateY(1rem) rotate(40deg);
    }

    &.active .lower-toggle {
      transform: translateY(1rem) rotate(-40deg);
    }
  }
`;

/**
 * NavigationToggle component renders a toggle button that switches between open and closed states.
 * It shows two horizontal lines (upper and lower) that animate into a cross (X) when active.
 *
 * @param {Object} props The component props.
 * @param {boolean} props.isToggleOpen Boolean flag to determine if the toggle is in the open state.
 * @param {function} props.handleNavigationToggleClick Function to handle click events to toggle the navigation.
 * @returns {JSX.Element} The rendered navigation toggle button.
 */
function NavigationToggle({ isToggleOpen, handleNavigationToggleClick }) {
  return (
    <StyledNavigationToggle
      className={isToggleOpen ? "active" : ""}
      onClick={handleNavigationToggleClick}
    >
      <span className="upper-toggle"></span>
      <span className="lower-toggle"></span>
    </StyledNavigationToggle>
  );
}

export default NavigationToggle;
