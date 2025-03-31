import styled from "styled-components";

/**
 * A styled component representing a single choice in the OSI Quiz.
 *
 * This component provides the layout, styles, and interactive states for a
 * quiz choice (e.g., correct, wrong, active). It uses a flexible layout with
 * responsive design to adjust the appearance based on screen size.
 */
const Choice = styled.div`
    position: relative;
    width: 40rem;
    height: 4rem;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 1rem;
    padding: 1rem;
    color: var(--color-white);
    font-family: aubrey;
    text-transform: uppercase;
    border-radius: var(--border-radius-tiny);
    border: none;
    background: linear-gradient(
            to right,
            var(--color-pink-800),
            var(--color-pink-purple-800),
            var(--color-purple-800)
    );
    flex-shrink: 0;
    transition: transform .3s ease-in-out;

    &:hover {
        transform: scale(1.05) translateY(-.2rem);
    }
    
    &.active {
        background-color: var(--color-white);
    }

    &.correct {
        border: .2rem solid var(--color-green);
    }

    &.wrong {
        border: .2rem solid var(--color-red);
    }
    
    .answer-indicator {
        position: relative;
        font-size: 2.5rem;
        font-family: impact;
        width: 3rem;
    }
    
    .answer-text {
        position: relative;
        flex-grow: 1;
        line-height: .9;
        font-size: 1.3rem;
        word-break: break-all;
    }
    
    & > :not(span) {
        text-align: center;
        flex: 1;
    }

    @media (max-width: 576px) {
        width: 100%;
        font-size: 1.1rem;

        .answer-indicator {
            font-size: 1.5rem;
            width: 2rem;
        }

        .answer-text {
            line-height: 1;
            font-size: 1rem;
        }
    }

    @media (min-width: 577px) and (max-width: 768px) {
        width: 100%;
        
        .answer-indicator {
            font-size: 2rem;
            width: 2rem;
        }

        .answer-text {
            line-height: 1;
            font-size: 1.3rem;
        }
    }

    @media (min-width: 769px) and (max-width: 992px) {
        width: 100%;

        .answer-indicator {
            font-size: 2.3rem;
            width: 2rem;
        }

        .answer-text {
            line-height: 1;
            font-size: 1.5rem;
        }
    }

    @media (min-width: 993px) and (max-width: 1200px) {
        width: 100%;

        .answer-indicator {
            font-size: 2.3rem;
            width: 2rem;
        }

        .answer-text {
            line-height: 1;
            font-size: 1.3rem;
        }
    }

    @media (min-width: 1200px) and (max-width: 1599px) {
        width: 90%;

        .answer-indicator {
            font-size: 2.3rem;
            width: 2rem;
        }

        .answer-text {
            line-height: 1;
            font-size: 1.3rem;
        }
    }
`;

export default Choice;