import Background from "../ui/Background.js";
import AnimationContainer from "../ui/AnimationContainer";
import Title from "../ui/Title";
import DMTY from "../ui/DMTY";
import IPv4Links from "../features/IP/IPv4/IPv4Links.jsx";
import Content from "../ui/Content.js";

/**
 * IPLanding page component of the Binary Bunker application.
 * This page introduces the user to the world of IPs, providing a friendly greeting from the DMTY character.
 * It includes a title section that highlights the purpose of the page and provides quick access to IPv4-related
 * features through the IPv4Links component.
 * The user is encouraged to start exploring IP-related tools like the calculator and learning tools.
 *
 * @component
 * @returns {JSX.Element} The IPLanding page with a greeting, title, and navigation links.
 */
function IPLanding() {
  return (
    <AnimationContainer>
      <Background>
        <DMTY speechBubbleText="The world of IPs is waiting for us. Where do you want to go first?" />
        <Content>
            <Title firstTitle="Calulator &" secondTitle="Learntool" />
            <IPv4Links />
        </Content>
      </Background>
    </AnimationContainer>
  );
}

export default IPLanding;
