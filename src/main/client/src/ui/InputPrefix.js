import styled from "styled-components";

/**
 * The InputPrefix component is a styled input field designed for displaying
 * a short text or prefix with responsive design and customizable styles.
 * It includes automatic resizing for various screen sizes.
 *
 * @component
 * @example
 * <InputPrefix placeholder="Prefix" />
 */
const InputPrefix = styled.input`
  position: relative;
  width: 10rem;
  height: 4rem;
  text-align: center;
  font-size: 3rem;
  color: var(--color-white);
  font-family: aubrey;
  text-transform: uppercase;
  border-radius: var(--border-radius-tiny);
  border: none;
  background-color: var(--color-black);

  &:focus {
    outline: none;
  }

  @media (max-width: 576px) {
      width: 5rem;
      height: 2.5rem;
      font-size: 1.5rem;
      border-radius: var(--border-radius-mobile-tiny);
  }
    
  @media (min-width: 577px) and (max-width: 768px) {
      width: 5rem;
      height: 3rem;
      font-size: 1.8rem;
      border-radius: var(--border-radius-mobile-tiny);
  }

  @media (min-width: 769px) and (max-width: 992px) {
      width: 5rem;
      height: 3rem;
      font-size: 1.8rem;
      border-radius: var(--border-radius-mobile-tiny);
  }
    
  @media (min-width: 993px) and (max-width: 1200px) {
      width: 7rem;
      height: 3rem;
      font-size: 1.8rem;
      border-radius: var(--border-radius-mobile-tiny);
  }
    
  @media (min-width: 1200px) and (max-width: 1599px) {
      width: 10rem;
      height: 4rem;
      font-size: 2rem;
  }

    @media (orientation: landscape) and (max-height: 576px) {
      width: 5rem;
      height: 3rem;
      font-size: 1.8rem;
      border-radius: var(--border-radius-mobile-tiny);
  }
`;

export default InputPrefix;
