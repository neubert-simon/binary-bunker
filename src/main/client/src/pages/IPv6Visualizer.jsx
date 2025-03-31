/**
 * Not in use currently! Maybe will be added in later updates as a new Feature
 */


import AnimationContainer from "../ui/AnimationContainer";
import Background from "../ui/Background.js";
import DMTY from "../ui/DMTY";
import Title from "../ui/Title";
import Content from "../ui/Content.js";

function IPv6Visualizer() {
  return (
    <AnimationContainer>
      <Background>
        <DMTY />
        <Content>
            <Title firstTitle="IPv6" secondTitle="Learntool" />
        </Content>
      </Background>
    </AnimationContainer>
  );
}

export default IPv6Visualizer;
