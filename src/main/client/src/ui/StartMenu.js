import styled from "styled-components";

/**
 * A styled component that represents the start menu with a title and a button.
 * The menu is positioned relative, and includes a custom border and gradient button.
 *
 * @returns {JSX.Element} The StartMenu component, including the title and start button.
 */
const StartMenu = styled.div`
    position: relative;
    width: 70%;
    height: 100%;
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
        font-size: 5rem;
        font-family: impact;
        color: var(--color-white);
    }
    
    .startBtn {
        width: 14rem;
        height: 4rem;
        color: var(--color-white);
        border-radius: var(--border-radius-tiny);
        background: linear-gradient(to right, #293555, #0a0b0d);
        font-size: 2rem;
        cursor: inherit;
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 10;
        overflow: hidden;
        transition: all .15s ease-out;
    }
    
    .startBtn:hover {
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
            font-size: 3rem;
        }
    }
`;

export default StartMenu;