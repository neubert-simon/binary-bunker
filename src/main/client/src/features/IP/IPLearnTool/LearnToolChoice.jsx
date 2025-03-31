import {useIPLearnTool} from "./IPLearnToolContext.jsx";
import Choice from "../../../ui/Choice.js";

/**
 * Component that renders an individual multiple-choice answer option of the current question.
 *
 * @param {Object} props The component props.
 * @param {Array} props.choice An array containing the choice key and value.
 * @param {Function} props.handleClick Function to handle the click event.
 * @returns {JSX.Element} A clickable choice element.
 */
function LearnToolChoice({ choice, handleClick }) {
    const { hasValidated, data } = useIPLearnTool();
    const { correctAnswerKeys } = data;

    return <Choice
        className={`${hasValidated ? correctAnswerKeys.includes(choice[0]) ? "correct" : "wrong" : ""}`}
        onClick={() => handleClick(choice[0])}
    >
        <span>{choice[0]}.</span> {choice[1]}
    </Choice>
}

export default LearnToolChoice;