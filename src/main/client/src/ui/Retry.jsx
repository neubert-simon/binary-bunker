import {useRef} from "react";
import {useGSAPClickAnimation} from "../hooks/useGSAPClickAnimation.js";
import {useGSAP} from "@gsap/react";
import gsap from "gsap";
import ContentBackground from "./ContentBackground.js";

/**
 * Retry component displayed when an error occurs.
 * The user can click "Retry" to reset the action.
 *
 * @component
 * @param {Object} props The props passed to the component.
 * @param {Function} props.resetCallback The callback function called when the "Retry" button is clicked, used to reset the state.
 * @param {string} props.errorMsg The error message to be displayed.
 * @returns {JSX.Element} The Retry component containing an error message and a retry button.
 */
function Retry({ resetCallback, errorMsg }) {
    const ref = useRef();
    const { animate } = useGSAPClickAnimation(); // Retrieves the `animate` function from the custom hook

    /**
     * Handles the click event on the retry button. Executes an animation where
     * the element moves to the right and fades out. After the animation finishes,
     * the `resetCallback` function is invoked to reset the state or component.
     *
     * @function
     */
    function handleRetryClick() {
        animate(
            ref.current,
            {
                x: 100,
                opacity: 0,
                duration: .3,
                ease: "power2.inOut",
            },
            () => {
                resetCallback();
            }
        );
    }

    /**
     * Executes an animation when the component is mounted,
     * making the element visible and returning it to its original position.
     *
     * @function
     */
    useGSAP(() => {
        gsap.to(ref.current, { x: 0, opacity: 1, duration: .3, ease: "power2.inOut", });
    });

    return <ContentBackground ref={ref}>
        <h2>{errorMsg}🤯</h2>
        <button onClick={handleRetryClick}>Retry</button>
    </ContentBackground>
}

export default Retry;