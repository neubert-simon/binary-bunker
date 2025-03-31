import { getIPv4Calculation } from "../services/fetchData.js";
import AnimationContainer from "../ui/AnimationContainer";
import Background from "../ui/Background.js";
import DMTY from "../ui/DMTY";
import Title from "../ui/Title";
import Form from "../ui/Form.js";
import CreateIPBtn from "../ui/CreateIPBtn.js";
import InputText from "../ui/InputText.js";
import InputPrefix from "../ui/InputPrefix.js";
import Loading from "../ui/Loading.jsx";
import IPv4CalculatorContent from "../features/IP/IPv4/IPv4CalculatorContent.jsx";
import {useIPv4Fetch} from "../hooks/useIPv4Fetch.js";
import ContentContainer from "../ui/ContentContainer.js";
import Content from "../ui/Content.js";

/**
 * The main component for the IPv4 Calculator page.
 * It renders a form where users can input an IPv4 address and a prefix,
 * calculate information about the provided IPv4 address and prefix,
 * and display the calculated result or a loading state while processing.
 *
 * @returns {JSX.Element} The IPv4 Calculator UI component.
 */
function IPv4Calculator() {
  // Destructure the necessary states from the custom hook useIPv4Fetch
  const {
    handleSubmit,
    isCalculating,
    ip,
    setIP,
    prefix,
    setPrefix,
    data
  } = useIPv4Fetch(getIPv4Calculation);

  return (
    <AnimationContainer>
      <Background>
        {/* DMTY component with a welcome speech bubble */}
        <DMTY speechBubbleText="Here we can learn all about IPv4. Let's test it out" />
        <Content>
          {/* Title component displaying the main and secondary titles */}
          <Title firstTitle="IPv4" secondTitle="Calculator" />
          <ContentContainer variation="top">
            {/* Form component to submit IPv4 address and prefix */}
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
            {data && <IPv4CalculatorContent data={data} />}
          </ContentContainer>
        </Content>
      </Background>
    </AnimationContainer>
  );
}

export default IPv4Calculator;
