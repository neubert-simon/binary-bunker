import {useGSAP} from "@gsap/react";
import gsap from 'gsap';
import {useRef} from "react";
import {useSelector} from "react-redux";
import {useIPLearnTool} from "./IPLearnToolContext.jsx";
import LearnToolAndQuizForm from "../../../ui/LearnToolAndQuizForm.js";

/**
 * IPLearnToolQuizForm Component
 * Displays the form interface for the interaction with the osi quiz.
 * Handles question validation, skipping, and proceeding to the next question.
 *
 * @returns {JSX.Element} The IPLearnToolQuizForm component.
 */
function IPLearnToolQuizForm() {
    const ref = useRef();
    const currentLife = useSelector(state => state.userState.life);
    const {
        data,
        hasValidated,
        handleQuestionValidation,
        handleSkipQuestionClick,
        handleNextQuestionClick
    } = useIPLearnTool();

    const { inputFieldAmount } = data;

    useGSAP(() => {
        gsap.to(ref.current, {y: 0, opacity: 1, duration: .3, ease: "power2.inOut" });
    });

    return (
        <LearnToolAndQuizForm ref={ref}>
            {inputFieldAmount && (
                <div
                    className={`learnTool-validateQuestionBtn ${hasValidated ? "disabled" : ""}`}
                    onClick={handleQuestionValidation}
                >
                    Validate
                </div>)}
            <div
                className={`learnTool-skipQuestionBtn ${currentLife > 0 ? "active" : ""} ${hasValidated ? "disabled" : ""}`}
                onClick={handleSkipQuestionClick}
            >
                Skip
            </div>
            <div className={`learnTool-getQuestionBtn ${hasValidated ? "active" : ""}`} onClick={handleNextQuestionClick}>
                Next
            </div>
        </LearnToolAndQuizForm>
    );
}

export default IPLearnToolQuizForm;
