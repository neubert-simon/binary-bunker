import LearnToolAndQuizForm from "../../ui/LearnToolAndQuizForm.js";
import {useGSAP} from "@gsap/react";
import gsap from "gsap";
import {useRef} from "react";
import {useSelector} from "react-redux";
import {useOsiQuiz} from "./OsiQuizContext.jsx";

/**
 * The OsiQuizForm component renders the interactive buttons to interact with the quiz:
 * - Validate the answer.
 * - Skip the current question.
 * - Proceed to the next question.
 *
 * It is also responsible for handling the state changes related to life points and quiz validation.
 * The component uses GSAP for smooth animations.
 *
 * @returns {JSX.Element} The form with interactive buttons for quiz interaction.
 */
function OsiQuizForm() {
    const ref = useRef();
    const currentLife = useSelector(state => state.userState.life); // Get the user's life state from the Redux store
    const {
        hasValidated,
        handleSkipQuestionClick,
        handleNextQuestionClick,
        handleValidationClick,
    } = useOsiQuiz(); // Access quiz-related functions and state from the custom context

    // GSAP animation for smooth transition of the form elements
    useGSAP(() => {
        gsap.to(ref.current, {y: 0, opacity: 1, duration: .3, ease: "power2.inOut" });
    });

    return <LearnToolAndQuizForm ref={ref}>
        <div
            className={`learnTool-validateQuestionBtn ${hasValidated ? "disabled" : ""}`}
            onClick={() => handleValidationClick()}
        >
            Validate
        </div>
        <div
            className={`learnTool-skipQuestionBtn ${currentLife > 0 ? "active" : ""} ${hasValidated ? "disabled" : ""}`}
            onClick={() => handleSkipQuestionClick()}
        >
            Skip
        </div>
        <div
            className={`learnTool-getQuestionBtn ${hasValidated ? "active" : ""}`}
            onClick={() => handleNextQuestionClick()}
        >
            Next
        </div>
    </LearnToolAndQuizForm>
}

export default OsiQuizForm;