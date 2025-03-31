import styled from "styled-components";

// Styled-Component for LearnToolAndQuizForm
const LearnToolAndQuizForm = styled.div`
    position: relative;
    width: 70%;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 2rem;
    transform: translateY(1rem);
    opacity: 0;

    .learnTool-validateQuestionBtn,
    .learnTool-skipQuestionBtn,
    .learnTool-getQuestionBtn {
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

    .learnTool-skipQuestionBtn,
    .learnTool-getQuestionBtn {
        opacity: .7;
    }

    .learnTool-skipQuestionBtn.active,
    .learnTool-getQuestionBtn.active {
        opacity: 1;
    }

    .learnTool-skipQuestionBtn.disabled {
        opacity: 0.7;
    }
    
    .learnTool-validateQuestionBtn.disabled {
        opacity: 0.7;
    }

    .learnTool-validateQuestionBtn:hover,
    .learnTool-skipQuestionBtn:hover,
    .learnTool-getQuestionBtn:hover {
        transform: translateY(-.2rem) scale(1.02);
    }

    @media (max-width: 576px) {
        width: 100%;
        gap: 2rem;
        justify-content: center;

        .learnTool-validateQuestionBtn,
        .learnTool-skipQuestionBtn,
        .learnTool-getQuestionBtn {
            width: 6rem;
            height: 3.5rem;
            font-size: 1.5rem;
        }
    }

    @media (min-width: 577px) and (max-width: 768px) {
        
    }

    @media (min-width: 769px) and (max-width: 992px) {

    }

    @media (min-width: 993px) and (max-width: 1200px) {

    }

    @media (min-width: 1200px) and (max-width: 1599px) {

    }
`;

export default LearnToolAndQuizForm;