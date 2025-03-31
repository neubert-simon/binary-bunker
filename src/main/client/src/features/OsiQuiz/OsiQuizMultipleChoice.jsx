import {useOsiQuiz} from "./OsiQuizContext.jsx";
import {useState} from "react";
import StyledMultipleChoices from "../../ui/StyledMultipleChoices.js";
import toast from "react-hot-toast";
import OsiQuizChoice from "./OsiQuizChoice.jsx";

/**
 * OsiQuizMultipleChoice is a component that renders all the multiple-choice options
 * for the current quiz question. It maps through the `allAnswers` data and
 * dynamically renders each choice as a clickable option.
 * The component utilizes the OsiQuizChoice child component to handle the display and
 * interaction with each individual choice.
 *
 * @returns {JSX.Element} The rendered multiple-choice options for the current quiz question.
 */
function OsiQuizMultipleChoice() {
    const { data } = useOsiQuiz();
    const {
        allAnswers,
    } = data;

    return <StyledMultipleChoices>
        {
            Object.entries(allAnswers).map(choice => <OsiQuizChoice key={choice[0]} choice={choice} />)
        }
    </StyledMultipleChoices>
}

export default OsiQuizMultipleChoice;