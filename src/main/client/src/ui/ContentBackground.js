import styled from "styled-components";

/**
 * ContentBackground is a styled container that holds content with a black background.
 * It is designed to have a flexible layout with responsive behavior based on screen sizes.
 *
 * @component
 * @example
 * <ContentBackground>
 *   <h2>Title</h2>
 *   <button>Click Me</button>
 * </ContentBackground>
 */
const ContentBackground = styled.div`
    position: relative;
    width: 70%;
    height: 100%;
    padding: 5rem;
    background-color: var(--color-black);
    border-radius: var(--border-radius-md);
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    flex-direction: column;
    gap: 2rem;
    transform: translateX(-10rem);
    opacity: 0;

    &::before {
        content: "";
        position: absolute;
        top: 1rem;
        left: -1rem;
        width: 100%;
        height: 100%;
        background-color: transparent;
        pointer-events: none;
        outline: 0.15rem solid var(--color-white);
        border-radius: var(--border-radius-sm);
    }
    
    h2 {
        font-size: 3rem;
        font-family: impact;
        color: var(--color-white);
        text-align: center;
        width: 70%;
    }
    
    button {
        position: relative;
        width: 14rem;
        height: 4rem;
        outline: none;
        border: none;
        font-size: 2rem;
        border-radius: var(--border-radius-tiny);
        color: var(--color-white);
        background: linear-gradient(to right, #293555, #0a0b0d);
        cursor: inherit;
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 10;
        overflow: hidden;
        transition: all .15s ease-out;
    }
    
    button:hover {  
        transform: translateY(-.2rem) scale(1.02);
    }

    @media (max-width: 576px) {
        width: 100%;
        border-radius: var(--border-radius-tiny);
        padding: 2rem;

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }

        h2 {
            font-size: 1.5rem;
            text-align: center;
        }
    }

    @media (min-width: 577px) and (max-width: 768px) {
        width: 100%;
        border-radius: var(--border-radius-tiny);
        padding: 2rem;

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }

        h2 {
            font-size: 2rem;
            text-align: center;
        }
    }

    @media (min-width: 769px) and (max-width: 992px) {
        h2 {
            font-size: 1.5rem;
        }
    }

    @media (min-width: 993px) and (max-width: 1200px) {
        h2 {
            font-size: 2rem;
        }
    }

    @media (min-width: 1200px) and (max-width: 1599px) {
        h2 {
            font-size: 2.5rem;
        }
    }
`;

export default ContentBackground;