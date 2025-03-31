import styled from "styled-components";

/**
 * A styled component for the title with dynamic typography and responsive design.
 * The title consists of two spans with customizable text and a decorative bottom border.
 */
const StyledTitle = styled.div`
  position: relative;
  align-self: flex-start;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  flex-direction: column;

  span {
    color: var(--color-white);
    font-size: 8rem;
    font-family: impact;
    line-height: 1;
    text-transform: uppercase;
  }
  .bottom {
    width: 120%;
    height: 1rem;
    border-top-right-radius: var(--border-radius-sm);
    border-bottom-right-radius: var(--border-radius-sm);
    outline: 0.2rem solid var(--color-gray);
    margin-top: 1rem;
  }

  @media (max-width: 576px) {
    align-self: center;
    justify-content: center;
    align-items: center;

    span {
      font-size: 3rem;
    }

    .bottom {
      height: .5rem;
      border-radius: var(--border-radius-sm);
      outline: 0.2rem solid var(--color-gray);
    }
  }

  @media (min-width: 577px) and (max-width: 768px) {
    align-self: center;
    justify-content: center;
    align-items: center;

    span {
      font-size: 3.5rem;
    }

    .bottom {
      height: .5rem;
      border-radius: var(--border-radius-sm);
      outline: 0.2rem solid var(--color-gray);
    }
  }

  @media (min-width: 769px) and (max-width: 1599px) {
    span {
      font-size: 6rem;
    }

    .bottom {
      height: 1rem;
      border-radius: var(--border-radius-sm);
      outline: 0.2rem solid var(--color-gray);
    }
  }

  @media (orientation: landscape) and (max-height: 576px) {
      span {
          font-size: 4rem;
      }

      .bottom {
          height: .5rem;
          border-radius: var(--border-radius-sm);
          outline: 0.2rem solid var(--color-gray);
      }
  }
`;

/**
 * Title component that renders a main title (firstTitle) and a subtitle (secondTitle)
 * with a decorative underline.
 *
 * @param {Object} props - The properties passed to the component.
 * @param {string} [props.firstTitle=""] - The first line of the title.
 * @param {string} [props.secondTitle=""] - The second line of the title.
 *
 * @returns {JSX.Element} The styled title component.
 */
function Title({ firstTitle = "", secondTitle = "" }) {
  return (
    <StyledTitle>
      <span>{firstTitle}</span>
      <span>{secondTitle}</span>
      <div className="bottom"></div>
    </StyledTitle>
  );
}

export default Title;
