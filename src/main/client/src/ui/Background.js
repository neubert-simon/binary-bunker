import styled from "styled-components";

/**
 * Background component that serves as a container with a gradient background.
 * It adjusts its padding and border-radius based on the screen size to ensure responsiveness.
 *
 * @component
 * @example
 * return (
 *   <Background>
 *     <YourContent />
 *   </Background>
 * )
 */
const Background = styled.div`
  position: relative;
  height: 100%;
  width: 100%;
  padding: 7rem;
  background: linear-gradient(
    180deg,
    var(--color-pink-800),
    var(--color-pink-purple-800),
    var(--color-purple-800)
  );
  border-radius: var(--border-radius-lg);
    
  @media (max-width: 768px) {
      padding: 1.5rem;
      border-radius: var(--border-radius-md);
  }
  
  @media (min-width: 769px) and (max-width: 992px) {
    border-radius: var(--border-radius-md);
    padding: 5rem 3rem; 
  }  
    
  @media (min-width: 993px) and (max-width: 1200px) {
    padding: 5rem 3rem;  
    border-radius: var(--border-radius-md);
  }

  @media (min-width: 1200px) and (max-width: 1599px) {
    padding: 5rem;
  }

  @media (orientation: landscape) and (max-height: 576px) {
      padding: 1.5rem;
      border-radius: var(--border-radius-sm);
  }    
`;

export default Background;
