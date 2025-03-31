import Choice from "../../ui/Choice.js";
import {useOsiQuiz} from "./OsiQuizContext.jsx";

/**
 * Component representing a single choice in the OSI Quiz.
 * This component handles displaying the choice with its correct or incorrect
 * status based on the user's interaction and quiz validation state. It also
 * allows users to select a choice and updates the quiz state accordingly.
 *
 * @param {Object} props The component's props.
 * @param {Array} props.choice An array representing the choice, where the first
 *                               element is the key (e.g., "A") and the second is
 *                               the choice text (e.g., "Physical layer").
 * @returns {JSX.Element} The rendered choice element.
 */
function OsiQuizChoice({ choice }) {
    const { data, hasValidated, clickedChoices, handleAddChoiceClick } = useOsiQuiz();
    const { correctAnswerKeys } = data;

    /**
     * Handle the click event when a user selects a choice.
     * This adds the choice to the clickedChoices state.
     */
    function handleClick() {
        handleAddChoiceClick(choice[0]);
    }

    return <Choice
        className={`
            ${clickedChoices.includes(choice[0]) ? "active" : ""} 
            ${hasValidated ? correctAnswerKeys.includes(choice[0]) ? "correct" : "wrong" : ""}
        `}
        onClick={handleClick}
    >
        <span className="answer-indicator">{choice[0]}.</span>
        <span className="answer-text">{choice[1]}</span>
    </Choice>
}

export default OsiQuizChoice;