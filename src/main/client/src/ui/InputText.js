import styled from "styled-components";

/**
 * The InputText component is a styled input field designed to be used for longer
 * text input with a focus on responsiveness. It has a padding on the left side to
 * accommodate an icon or text. The input adapts its size and font for various screen sizes.
 *
 * @component
 * @example
 * <InputText placeholder="Enter your text here" />
 */
const InputText = styled.input`
  position: relative;
  width: 55rem;
  height: 4rem;
  padding-left: 5rem;
  font-size: 3rem;
  color: var(--color-white);
  font-family: aubrey;
  text-transform: uppercase;
  border-top-right-radius: var(--border-radius-tiny);
  border-bottom-right-radius: var(--border-radius-tiny);
  border-top-left-radius: var(--border-radius-md);
  border-bottom-left-radius: var(--border-radius-md);
  border: none;
  background-color: var(--color-black);

  &:focus {
    outline: none;
  }
    
  @media (max-width: 576px) {
      width: 13rem;
      height: 2.5rem;
      padding-left: 1rem;
      font-size: 1.5rem;
  } 
    
  @media (min-width: 577px) and (max-width: 768px) {
      width: 25rem;
      height: 3rem;
      padding-left: 1rem;
      font-size: 1.8rem;
  }
    
  @media (min-width: 769px) and (max-width: 992px) {
      width: 25rem;
      height: 3rem;
      padding-left: 1rem;
      font-size: 1.8rem;
  }
    
  @media (min-width: 993px) and (max-width: 1200px) {
      width: 35rem;
      height: 3rem;
      padding-left: 1rem;
      font-size: 1.8rem;
  }

  @media (min-width: 1200px) and (max-width: 1599px) {
      width: 35rem;
      height: 4rem;
      padding-left: 1rem;
      font-size: 1.8rem;
  }

  @media (orientation: landscape) and (max-height: 576px) {
      width: 25rem;
      height: 3rem;
      padding-left: 1rem;
      font-size: 1.8rem;
  }
`;

export default InputText;
