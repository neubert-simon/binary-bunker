import AnimationContainer from "../ui/AnimationContainer";
import Background from "../ui/Background.js";
import DMTY from "../ui/DMTY";
import Title from "../ui/Title";
import {getIPv4Visualizer} from "../services/fetchData.js";
import Form from "../ui/Form.js";
import InputText from "../ui/InputText.js";
import InputPrefix from "../ui/InputPrefix.js";
import CreateIPBtn from "../ui/CreateIPBtn.js";
import Loading from "../ui/Loading.jsx";
import IPv4VisualizerContent from "../features/IP/IPv4/IPv4VisualizerContent.jsx";
import {useIPv4Fetch} from "../hooks/useIPv4Fetch.js";
import ContentContainer from "../ui/ContentContainer.js";
import Content from "../ui/Content.js";

/**
 * The main component that renders the IPv4 Visualizer.
 * It is responsible for displaying the input form for the user
 * to enter an IPv4 address and a prefix.
 * Submitting the form will trigger sending that data to the server for processing,
 * and displaying the calculated results or a loading state.
 *
 * @returns {JSX.Element} The IPv4 Visualizer UI component.
 */
function IPv4Visualizer() {
  // Destructure the necessary states from the custom hook useIPv4Fetch
  const {
      handleSubmit,
      isCalculating,
      ip,
      setIP,
      prefix,
      setPrefix,
      data
  } = useIPv4Fetch(getIPv4Visualizer);

  return (
    <AnimationContainer>
      <Background>
        {/* DMTY component that shows a speech bubble with welcome text */}
        <DMTY speechBubbleText="Welcome to the IPv4 Visualizer. Let's analyse IPv4s together, And learn everything about Net- and Host-Bits!" />
        <Content>
            {/* Title component for displaying main and secondary titles */}
            <Title firstTitle="IPv4" secondTitle="Visualizer" />
            <ContentContainer variation="top">
                {/* Form component for submitting the IPv4 and prefix values */}
                <Form onSubmit={handleSubmit}>
                    <InputText
                        type="text"
                        id="ip"
                        name="ip"
                        maxLength={15}
                        autoComplete="off"
                        placeholder="IPv4 Address"
                        value={ip}
                        onChange={(e) => setIP(e.target.value)}
                    />
                    <InputPrefix
                        type="text"
                        id="prefix"
                        name="prefix"
                        autoComplete="off"
                        placeholder="Prefix"
                        value={prefix}
                        onChange={(e) => setPrefix(e.target.value)}
                    />
                    <CreateIPBtn>Calculate</CreateIPBtn>
                </Form>
                {isCalculating ? <Loading /> : null}
                {data && <IPv4VisualizerContent data={data} />}
            </ContentContainer>
        </Content>
      </Background>
    </AnimationContainer>
  );
}

export default IPv4Visualizer;
