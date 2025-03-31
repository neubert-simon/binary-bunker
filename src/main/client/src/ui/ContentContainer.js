import styled, {css} from "styled-components";

// Defining variations for alignment (start or center) for the flex container
const alignVariations = {
    start: css`
        justify-content: flex-start;
    `,
    center: css`
        justify-content: center;
    `,
}

/**
 * ContentContainer is a flexible container that adjusts its alignment and layout based on screen
 * size and the provided `variation` prop.
 *
 * @component
 * @example
 * <ContentContainer variation="center">
 *   <YourContent />
 * </ContentContainer>
 */
const ContentContainer = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  gap: 3rem;
  
  ${(props) => alignVariations[props.variation]};

  @media (max-width: 576px) {
    justify-content: flex-start;  
    height: 80%;
  }

  @media (min-width: 577px) and (max-width: 768px) {
      justify-content: flex-start;
      height: 80%;
      gap: 2.5rem;
  }

  @media (min-width: 769px) and (max-width: 992px) {

  }

  @media (min-width: 993px) and (max-width: 1200px) {

  }

  @media (min-width: 1200px) and (max-width: 1599px) {

  }   
`;

export default ContentContainer;
