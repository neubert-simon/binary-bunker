import styled from "styled-components";

/**
 * Content is a styled container component designed to hold content
 * while maintaining flexible and responsive layout behavior.
 * It adapts to different screen sizes with media queries.
 *
 * @component
 * @example
 * <Content>
 *   <SomeOtherComponent />
 *   <AnotherComponent />
 * </Content>
 */
const Content = styled.div`
    position: relative;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    height: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-direction: column;
    gap: 3rem;

    @media (max-width: 576px) {
        top: 13rem;
        height: calc(100% - 13rem);
        justify-content: space-evenly;
        gap: 2rem;
        padding: 1rem;
    }

    @media (min-width: 577px) and (max-width: 768px) {
        top: 13rem;
        height: calc(100% - 13rem);
        justify-content: space-evenly;
        gap: 2rem;
        padding: 1rem;
    }
`;

export default Content;