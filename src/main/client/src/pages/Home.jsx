import Stats from "../features/Stats/Stats";
import Background from "../ui/Background.js";
import Title from "../ui/Title";
import AnimationContainer from "../ui/AnimationContainer";
import DMTY from "../ui/DMTY";
import Content from "../ui/Content.js";

/**
 * Home page component of the Binary Bunker application.
 * This page serves as the landing page for the user, introducing them to the Binary Bunker app.
 * It includes an animated character DMTY welcoming the user and provides stats related to the app's progress.
 * The page layout consists of a title, DMTY's greeting speech bubble, and an overview of the user's progress through the Stats component.
 *
 * @component
 * @returns {JSX.Element} The Home page with a welcome message, animated character, and statistics.
 */
function Home() {
  return (
    <AnimationContainer>
      <Background>
        <DMTY
            speechBubbleText="
            Hello, I am DMTY, welcome to Binary Bunker.
            Together we will learn everything about the OSI-Model and IP."
        />
        <Content>
            <Title firstTitle="Welcome" />
            <Stats />
        </Content>
      </Background>
    </AnimationContainer>
  );
}

export default Home;
