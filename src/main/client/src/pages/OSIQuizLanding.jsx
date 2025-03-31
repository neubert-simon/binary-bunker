import AnimationContainer from "../ui/AnimationContainer";
import Background from "../ui/Background.js";
import DMTY from "../ui/DMTY";
import {OsiQuizProvider} from "../features/OsiQuiz/OsiQuizContext.jsx";
import OsiQuiz from "../features/OsiQuiz/OsiQuiz.jsx";

/**
 * OSIQuizLanding page component for the Binary Bunker application.
 * This page serves as the landing page for the OSI quiz. It provides a welcoming message from DMTY
 * and renders the quiz interface where users can select categories and start testing their knowledge
 * on the OSI model.
 *
 * The OsiQuizProvider context is used to manage the state of the quiz, and the OsiQuiz component
 * renders the actual quiz UI for the user to interact with.
 *
 * @component
 * @returns {JSX.Element} The OSI Quiz landing page with a welcome message and quiz options.
 */
function OSIQuizLanding() {
  return (
    <AnimationContainer>
      <Background>
        <DMTY speechBubbleText="Welcome to the OSI Quiz. Here you can choose the categories you want to test yourself on!" />
        <OsiQuizProvider>
            <OsiQuiz />
        </OsiQuizProvider>
      </Background>
    </AnimationContainer>
  );
}

export default OSIQuizLanding;
