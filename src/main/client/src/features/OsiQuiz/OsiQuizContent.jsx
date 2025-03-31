import LearnToolAndQuizContent from "../../ui/LearnToolAndQuizContent.js";
import {useRef} from "react";
import {useGSAP} from "@gsap/react";
import gsap from "gsap";
import {useOsiQuiz} from "./OsiQuizContext.jsx";
import OsiQuizMultipleChoice from "./OsiQuizMultipleChoice.jsx";
import styled from "styled-components";
import QuestionContent from "../../ui/QuestionContent.js";

// Css for Osi Quiz Question State
const StyledQuizQuestionStat = styled.div`
    position: absolute;
    top: -2rem;
    right: -2rem;
    font-size: 2.3rem;
    text-transform: uppercase;
    color: var(--color-white);
    width: 7rem;
    height: 7rem;
    background: linear-gradient(to right, #293555, #0a0b0d);
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
    z-index: 10;

    @media (max-width: 576px) {
        top: -1.5rem;
        right: -1.5rem;
        font-size: 1.5rem;
        width: 4rem;
        height: 4rem;
    }
    
    @media (min-width: 577px) and (max-width: 768px) {
        top: -3rem;
        right: -2rem;
        font-size: 1.8rem;
        width: 5rem;
        height: 5rem;
    }

    @media (min-width: 769px) and (max-width: 992px) {
        top: -2rem;
        right: -2rem;
        font-size: 1.8rem;
        width: 5rem;
        height: 5rem;
    }

    @media (min-width: 993px) and (max-width: 1200px) {
        top: 0;
        right: -2rem;
        font-size: 1.8rem;
        width: 5rem;
        height: 5rem;
    }

    @media (min-width: 1200px) and (max-width: 1599px) {
        top: 0;
        right: -3rem;
        font-size: 2rem;
        width: 6rem;
        height: 6rem;
    }
`;

/**
 * This component renders the content for the OSI Quiz, including the current question,
 * multiple choice answers, and the current progress like how many answers have been clicked.
 * It utilizes GSAP (GreenSock Animation Platform) for smooth animations and renders
 * the question, multiple-choice options, and displays a status indicator for the number of correct answers clicked.
 *
 * @returns {JSX.Element} The JSX representation of the quiz content, including the question, multiple choice, and answer stats.
 */
function OsiQuizContent() {
    const ref = useRef();
    const { data, clickedChoices } = useOsiQuiz();
    const {
        question,
        correctAnswerKeys
    } = data;

    useGSAP(() => {
        gsap.to(ref.current, { x: 0, opacity: 1, duration: .3, ease: "power2.inOut", });
    });

    return <LearnToolAndQuizContent className="osiquiz-content" ref={ref}>
        <StyledQuizQuestionStat>
            {clickedChoices.length}/{correctAnswerKeys.length}
        </StyledQuizQuestionStat>
        <QuestionContent>
            <div className="question">{question}</div>
            <OsiQuizMultipleChoice />
        </QuestionContent>
    </LearnToolAndQuizContent>
}

export default OsiQuizContent;