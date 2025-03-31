import {useRef} from "react";
import {useIPLearnTool} from "./IPLearnToolContext.jsx";
import {useGSAPClickAnimation} from "../../../hooks/useGSAPClickAnimation.js";
import StartMenu from "../../../ui/StartMenu.js";
import {useGSAP} from "@gsap/react";
import gsap from "gsap";

/**
 * Component for starting the IP LearnTool Quiz.
 * Uses GSAP for animations and handles the quiz start action.
 *
 * @returns {JSX.Element} The start menu with a button to begin the quiz.
 */
function IPLearnToolQuizStart() {
    const ref = useRef();
    const { animate } = useGSAPClickAnimation(); // Custom GSAP click animation hook
    const { handleStartLearnTool } = useIPLearnTool(); // IP learn tool context hook

    function handleStartClick() {
        animate(
            ref.current,
            {
                x: 100,
                opacity: 0,
                duration: .3,
                ease: "power2.inOut",
            },
            () => {
                handleStartLearnTool();
            }
        );
    }

    useGSAP(() => {
        gsap.to(ref.current, { x: 0, opacity: 1, duration: .3, ease: "power2.inOut", });
    });

    return <StartMenu ref={ref}>
        <h2>Welcome to the IP Learntool, ready to start?</h2>
        <div
            className="startBtn"
            onClick={handleStartClick}
        >
            Start
        </div>
    </StartMenu>
}

export default IPLearnToolQuizStart;