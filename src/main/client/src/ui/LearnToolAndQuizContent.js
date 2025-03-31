import styled from "styled-components";

// Styled-Component for LearnToolAndQuizContent
const LearnToolAndQuizContent = styled.div`
    position: relative;
    flex-grow: 1;
    width: 70%;
    padding: 3rem;
    background-color: var(--color-black);
    border-radius: var(--border-radius-md);
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
    
    .question {
        position: relative;
        text-align: center;
        font-size: 3rem;
        font-family: aubrey;
        color: var(--color-white);
        display: flex;
        flex-direction: column;
        word-break: break-word;
        flex-shrink: 0;
    }
    
    .question span {
        color: var(--color-pink);
    }
    
    .question-params {
        font-size: 2.2rem;
    }

    @media (max-width: 576px) {
        height: 20rem;
        width: 100%;
        padding: 2rem;
        border-radius: var(--border-radius-tiny);
        transform: translateX(-1rem);

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }

        .question {
            font-size: 1.1rem;
        }
        
        .question-params {
            font-size: 1.1rem;
        }
    }

    @media (min-width: 577px) and (max-width: 768px) {
        height: 20rem;
        width: 100%;
        padding: 2rem;
        border-radius: var(--border-radius-tiny);
        transform: translateX(-1rem);

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }

        .question {
            font-size: 1.3rem;
        }

        .question-params {
            font-size: 1.3rem;
        }
    }

    @media (min-width: 769px) and (max-width: 992px) {
        width: 100%;
        padding: 2rem;
        border-radius: var(--border-radius-tiny);
        transform: translateX(-1rem);

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }

        .question {
            font-size: 2rem;
        }

        .question-params {
            font-size: 2rem;
        }
    }

    @media (min-width: 993px) and (max-width: 1200px) {
        padding: 2rem;
        
        .question {
            font-size: 2rem;
        }

        .question-params {
            font-size: 2rem;
        }
    }

    @media (min-width: 1200px) and (max-width: 1599px) {
        height: 25rem;
        padding: 2rem;

        .question {
            font-size: 2rem;
        }

        .question-params {
            font-size: 1.5rem;
        }
    }
`;

export default LearnToolAndQuizContent;