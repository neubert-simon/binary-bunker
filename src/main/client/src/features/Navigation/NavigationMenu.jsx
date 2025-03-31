import styled from "styled-components";

// Styled component for the navigation menu container
const StyledNavigationMenu = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  pointer-events: none;
  z-index: 1000;

  .navigation-menu-small,
  .navigation-menu-middle,
  .navigation-menu-large {
    position: absolute;
    background: linear-gradient(
      180deg,
      var(--color-pink-strong),
      var(--color-purple-strong)
    );
    border-radius: 50%;
  }

  .navigation-menu-small {
    top: 3rem;
    left: 45%;
    width: 4rem;
    height: 4rem;
    transform: scale(0);
  }

  .navigation-menu-middle {
    top: 3rem;
    left: 35%;
    width: 8rem;
    height: 8rem;
    transform: scale(0);
  }

  .navigation-menu-large {
    top: 0;
    left: -15rem;
    width: 80rem;
    height: 80rem;
    transform: scale(0);
    pointer-events: all;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
  }

  @media (max-width: 576px) {
      .navigation-menu-small {
          top: 7rem;
          left: 60%;
          width: 2rem;
          height: 2rem;
      }

      .navigation-menu-middle {
          top: 12rem;
          left: 70%;
          width: 4rem;
          height: 4rem;
      }

      .navigation-menu-large {
          position: relative;
          top: -2rem;
          left: 0;
          width: 40rem;
          height: 40rem;
      }
  }

  @media (min-width: 577px) and (max-width: 768px) {
      .navigation-menu-small {
          left: 60%;
          width: 2rem;
          height: 2rem;
      }

      .navigation-menu-middle {
          top: 8rem;
          left: 70%;
          width: 4rem;
          height: 4rem;
      }

      .navigation-menu-large {
          position: relative;
          top: 0;
          left: 0;
          width: 50rem;
          height: 50rem;
      }
  }
    
  @media (min-width: 769px) and (max-width: 992px) {
      .navigation-menu-small {
          top: 3rem;
          left: 45%;
          width: 2rem;
          height: 2rem;
      }

      .navigation-menu-middle {
          left: 38%;
          width: 4rem;
          height: 4rem;
      }

      .navigation-menu-large {
          top: 1rem;
          left: -10rem;
          width: 45rem;
          height: 45rem;
      }
  }

  @media (min-width: 993px) and (max-width: 1200px) {
    .navigation-menu-small {
        top: 3rem;
        left: 43%;
        width: 3rem;
        height: 3rem;
    }
      
    .navigation-menu-middle {
        width: 6rem;
        height: 6rem;
    }

    .navigation-menu-large {
        left: -15rem;
        width: 60rem;
        height: 60rem;
    }
  }

  @media (min-width: 1200px) and (max-width: 1599px) {
    .navigation-menu-small {
      top: 3rem;
      left: 43%;
      width: 3rem;
      height: 3rem;
    }

    .navigation-menu-middle {
      width: 6rem;
      height: 6rem;
    }

    .navigation-menu-large {
      left: -7.5rem;
      width: 60rem;
      height: 60rem;
    }
  }
    
  @media (orientation: landscape) and (max-height: 576px) {
      .navigation-menu-small {
          top: 3rem;
          left: 45%;
          width: 2rem;
          height: 2rem;
      }

      .navigation-menu-middle {
          left: 38%;
          width: 4rem;
          height: 4rem;
      }

      .navigation-menu-large {
          top: 1rem;
          left: -10rem;
          width: 45rem;
          height: 45rem;
      }
  }
`;

/**
 * NavigationMenu component that renders the animated navigation menu.
 * The menu contains three levels (small, middle, large) which scale
 * and animate into view based on user interaction.
 *
 * @param {Object} props The component props
 * @param {ReactNode} props.children The content to be displayed inside the large menu
 * @returns {JSX.Element} The rendered navigation menu component
 */
function NavigationMenu({ children }) {
  return (
    <StyledNavigationMenu>
      <div className="navigation-menu-small"></div>
      <div className="navigation-menu-middle"></div>
      <div className="navigation-menu-large">{children}</div>
    </StyledNavigationMenu>
  );
}

export default NavigationMenu;
