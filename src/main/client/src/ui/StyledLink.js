import styled, {css} from "styled-components";
import {Link} from "react-router-dom";

/**
 * A set of variations that define border radius styles.
 * @type {Object}
 */
const variations = {
    TopRightBottomRight: css`
    border-top-right-radius: var(--border-radius-md);
    border-bottom-right-radius: var(--border-radius-md);
  `,
    TopLeftBottomLeft: css`
    border-top-left-radius: var(--border-radius-md);
    border-bottom-left-radius: var(--border-radius-md);
  `,
};

/**
 * A set of outline variations that define positioning and outline styles.
 * @type {Object}
 */
const outlinevariations = {
    top: css`
    top: -0.5rem;
    left: 0;
    right: 0;
    bottom: 0.5rem;
  `,
    right: css`
    top: 0;
    left: 0.5rem;
    right: -0.5rem;
    bottom: 0;
  `,
    bottom: css`
    top: 0.8rem;
    left: 0.5rem;
    right: -0.3rem;
    bottom: -0.8rem;
  `,
    left: css`
    top: -0.5rem;
    left: -0.5rem;
    right: 0.5rem;
    bottom: 0.5rem;
  `,
};

/**
 * A styled link component that serves as a link wrapper with dynamic border-radius and outline styles.
 * It adapts its size and layout for different screen sizes and applies animations for interaction effects.
 *
 * @param {Object} props The properties passed to the component.
 * @param {string} [props.variation] Determines the border radius variation of the component.
 * @param {string} [props.outlinevariation] Determines the outline variation of the component.
 *
 * @returns {JSX.Element} The styled link element.
 */
const StyledLink = styled(Link)`
  position: relative;
  width: 25rem;
  height: 25rem;
  background-color: var(--color-gray);
  color: var(--color-white);
  font-family: aubrey;
  line-height: 1;
  border-radius: var(--border-radius-sm);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 1rem;
  cursor: inherit;
  transition: all 0.3s ease-out;

  ${(props) => variations[props.variation]}

  &:hover {
    background-color: var(--color-blue);
  }

  &::before {
    content: "";
    position: absolute;
    z-index: 2;
    outline: 0.15rem solid var(--color-white);
    border-radius: var(--border-radius-sm);
    ${(props) => variations[props.variation]};
    ${(props) => outlinevariations[props.outlinevariation]};
    transition: all 0.3s ease-out;
  }

  &:hover::before {
    transform: scale(1.03);
  }

  .label {
    font-size: 2.5rem;
    text-transform: uppercase;
    text-align: center;
  }

  .value {
    font-size: 7rem;
    font-family: impact;
  }

  @media (max-width: 576px) {
    width: 10rem;
    height: 10rem;
    line-height: 1;
    border-radius: var(--border-radius-sm);
    
    &::before {
      border-radius: var(--border-radius-sm);
    }

    .label {
      font-size: 1rem;
    }

    .value {
      font-size: 2rem;
    }
  }

  @media (min-width: 577px) and (max-width: 768px) {
    width: 13rem;
    height: 13rem;
    line-height: 1;

    .label {
      font-size: 1.5rem;
    }

    .value {
      font-size: 3rem;
    }
  }

  @media (min-width: 769px) and (max-width: 1599px) {
    width: 17rem;
    height: 17rem;
    line-height: 1;

    .label {
      font-size: 1.5rem;
    }

    .value {
      font-size: 4rem;
    }
  }

  @media (orientation: landscape) and (max-height: 576px) {
    width: 10rem;
    height: 10rem;
    line-height: 1;
    border-radius: var(--border-radius-sm);

    &::before {
      border-radius: var(--border-radius-sm);
    }

    .label {
      font-size: 1rem;
    }

    .value {
      font-size: 2rem;
    }
  }
`;

export default StyledLink;