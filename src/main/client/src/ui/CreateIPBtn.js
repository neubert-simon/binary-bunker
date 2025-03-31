import styled from "styled-components";

/**
 * CreateIPBtn is a customizable button component with a gradient background, dynamic font size,
 * and responsive styles based on screen size. The button scales slightly on hover to create a
 * subtle interactive effect.
 *
 * @component
 * @example
 * <CreateIPBtn>Click Me</CreateIPBtn>
 */
const CreateIPBtn = styled.button`
  position: relative;
  width: 20rem;
  height: 4rem;
  text-align: center;
  font-size: 2.5rem;
  color: var(--color-white);
  font-family: aubrey;
  text-transform: uppercase;
  border-radius: var(--border-radius-tiny);
  border: none;
  background: linear-gradient(to right, #293555, #0a0b0d);
  overflow: hidden;
  cursor: inherit;
  transition: all .15s ease-out;
    
  &:hover {
      transform: scale(1.05);
  }

  @media (max-width: 576px) {
      width: 8rem;
      height: 2.5rem;
      font-size: 1.5rem;
  }

  @media (min-width: 577px) and (max-width: 768px) {
      width: 10rem;
      height: 3rem;
      font-size: 1.5rem;
  }
  
  @media (min-width: 769px) and (max-width: 992px) {
      width: 10rem;
      height: 3rem;
      font-size: 1.5rem;
  }

  @media (min-width: 993px) and (max-width: 1200px) {
      width: 15rem;
      height: 3rem;
      font-size: 1.5rem;
  }

  @media (min-width: 1200px) and (max-width: 1599px) {
      width: 15rem;
      height: 4rem;
      font-size: 2rem;
  }

  @media (orientation: landscape) and (max-height: 576px) {
      width: 10rem;
      height: 3rem;
      font-size: 1.5rem;
  }
`;

export default CreateIPBtn;
