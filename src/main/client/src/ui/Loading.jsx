import styled from "styled-components";
import { BiLoaderCircle } from "react-icons/bi";

/**
 * Styled container for the loading component.
 * Uses Flexbox for centering and includes a rotating animation.
 */
const StyledLoading = styled.div`
  position: relative;
  flex: 1;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

  .loading-indicator {
    color: var(--color-gray);
    font-size: 8rem;
    animation: loading 1s infinite;
  }

  @keyframes loading {
    100% {
      transform: rotate(360deg);
    }
  }
`;

/**
 * Loading component that displays a spinning loader icon.
 *
 * @component
 * @example
 * return (
 *   <Loading />
 * )
 */
function Loading() {
  return (
    <StyledLoading>
      <BiLoaderCircle className="loading-indicator" />
    </StyledLoading>
  );
}

export default Loading;
