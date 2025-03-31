import AnimationContainer from "../ui/AnimationContainer";
import Background from "../ui/Background.js";
import DMTY from "../ui/DMTY";
import Title from "../ui/Title";
import IPSubnettingLinks from "../features/IP/Subnetting/IPSubnettingLinks.jsx";
import Content from "../ui/Content.js";

/**
 * SubnetVisualizer page component for the Binary Bunker application.
 * This page serves as the Subnet Visualizer, where users can learn about subnetting and
 * visualize how the bits of an IP address work in the context of subnetting.
 *
 * The DMTY component welcomes the user, explaining the purpose of the tool, and the IPSubnettingLinks
 * component displays the interactive options and visualizations related to subnetting.
 *
 * @component
 * @returns {JSX.Element} The Subnet Visualizer page with a welcoming message and subnetting options.
 */
function SubnetVisualizer() {
  return (
    <AnimationContainer>
      <Background>
        <DMTY speechBubbleText="Welcome to the Subnet Visualizer. Here we can visualize every Bit of an IP"/>
        <Content>
            <Title firstTitle="Subnet" secondTitle="Visualizer" />
            <IPSubnettingLinks />
        </Content>
      </Background>
    </AnimationContainer>
  );
}

export default SubnetVisualizer;
