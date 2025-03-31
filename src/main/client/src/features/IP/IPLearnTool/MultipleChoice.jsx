import {useIPLearnTool} from "./IPLearnToolContext.jsx";
import LearnToolChoice from "./LearnToolChoice.jsx";
import toast from "react-hot-toast";
import {useDispatch} from "react-redux";
import {addLife} from "../../../services/userStatesSlice.js";
import StyledMultipleChoices from "../../../ui/StyledMultipleChoices.js";

/**
 * Component that renders multiple-choice answer options.
 * Handles user selection and validation.
 *
 * @returns {JSX.Element} A set of multiple-choice buttons.
 */
function MultipleChoice() {
    const { data, hasValidated, dispatch } = useIPLearnTool();
    const globalDispatch = useDispatch();
    const { correctAnswerKeys } = data;

    function handleClick(clickedChoice) {
        if(hasValidated) return;

        dispatch({ type: "validate", payload: true });
        globalDispatch(addLife())

        if(correctAnswerKeys.includes(clickedChoice)) {
            toast.success("Your choice is correct!");
        }
        else {
            toast.error("Your choice is wrong!");
        }
    }

    return <StyledMultipleChoices>
        {
            Object.entries(data.allAnswers).map((choice, index) => <LearnToolChoice key={index} choice={choice} handleClick={handleClick} />)
        }
    </StyledMultipleChoices>
}

export default MultipleChoice