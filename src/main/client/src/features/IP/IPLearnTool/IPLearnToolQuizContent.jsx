import {useGSAP} from "@gsap/react";
import gsap from 'gsap';
import {useRef} from "react";
import {useIPLearnTool} from "./IPLearnToolContext.jsx";
import NoMultipleChoice from "./NoMultipleChoice.jsx";
import MultipleChoice from "./MultipleChoice.jsx";
import LearnToolAndQuizContent from "../../../ui/LearnToolAndQuizContent.js";
import QuestionContent from "../../../ui/QuestionContent.js";

/**
 * IPLearnToolQuizContent Component
 * Displays the quiz content including the question and multiple choice or input fields
 * depending on the quiz question data fetched.
 *
 * @returns {JSX.Element} The IPLearnToolQuizContent component.
 */
function IPLearnToolQuizContent() {
    const ref = useRef();
    const { data } = useIPLearnTool();
    const { question, inputFieldAmount, questionParameters  } = data;

    const multipleChoice = !inputFieldAmount;

    useGSAP(() => {
        gsap.to(ref.current, { x: 0, opacity: 1, duration: .3, ease: "power2.inOut", });
    });

    return <LearnToolAndQuizContent className="iplearntool-content" ref={ref}>
        <QuestionContent>
            <div className="question">
                <div>{question}</div>
                <div>
                    {
                        questionParameters &&
                        questionParameters.map((param, index) => <div key={index} className="question-params"><span>{index + 1}.</span> {param}</div>)
                    }
                </div>
            </div>
            { multipleChoice && <MultipleChoice />}
            { !multipleChoice && <NoMultipleChoice inputFieldAmount={inputFieldAmount} />}
        </QuestionContent>
    </LearnToolAndQuizContent>
}

export default IPLearnToolQuizContent;