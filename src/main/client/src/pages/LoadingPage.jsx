import AnimationContainer from "../ui/AnimationContainer.jsx";
import styled from "styled-components";

const StyledLoadingPage = styled.div`
  position: fixed;
  height: 100vh;
  width: 100vw;
  padding: 7rem;
  background: linear-gradient(
          180deg,
          var(--color-pink-800),
          var(--color-pink-purple-800),
          var(--color-purple-800)
  );
  display: flex;
  justify-content: center;
  align-items: center;

  @media (max-width: 576px) {
    justify-content: space-evenly;
    padding: 1.5rem;
    border-radius: var(--border-radius-md);
  }

  @media (min-width: 577px) and (max-width: 768px) {
    justify-content: space-evenly;
    padding: 3rem;
    border-radius: var(--border-radius-md);
  }

  @media (min-width: 769px) and (max-width: 1599px) {
    padding: 5rem;
  }
`;

function LoadingPage() {
    return <AnimationContainer>
        <StyledLoadingPage>
            <h1>Loading...</h1>
        </StyledLoadingPage>
    </AnimationContainer>
}

export default LoadingPage;