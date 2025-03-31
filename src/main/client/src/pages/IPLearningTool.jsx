import Background from "../ui/Background.js";
import DMTY from "../ui/DMTY.jsx";
import AnimationContainer from "../ui/AnimationContainer.jsx";
import {IPLearnToolProvider } from "../features/IP/IPLearnTool/IPLearnToolContext.jsx";
import IPLearnToolQuiz from "../features/IP/IPLearnTool/IPLearnToolQuiz.jsx";

/**
 * IPLearningTool page component for the Binary Bunker application.
 * This page introduces the user to the IP Learning Tool, encouraging them to practice and improve their knowledge of IP addresses.
 * The DMTY character offers a friendly invitation to start practicing, and the page includes a quiz powered by the IPLearnToolProvider.
 * The IPLearnToolQuiz component contains the actual quiz interface for the user.
 *
 * @component
 * @returns {JSX.Element} The IPLearningTool page with a practice quiz and a friendly greeting from DMTY.
 */
function IPLearningTool() {
    return (
        <AnimationContainer>
            <Background>
                <DMTY speechBubbleText="Everyone needs some Practice, so let's try out the IP Learntool" />
                <IPLearnToolProvider>
                    <IPLearnToolQuiz />
                </IPLearnToolProvider>
            </Background>
        </AnimationContainer>
    );
}

export default IPLearningTool;
