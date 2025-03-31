import AnimationContainer from "../ui/AnimationContainer.jsx";
import Background from "../ui/Background.js";
import DMTY from "../ui/DMTY.jsx";
import Button from "../ui/Button.jsx";
import {useHandleLinkClick} from "../hooks/useHandleLinkClick.js";
import ContentBackground from "../ui/ContentBackground.js";
import {useGSAP} from "@gsap/react";
import gsap from "gsap";
import {useRef} from "react";
import ContentContainer from "../ui/ContentContainer.js";
import Content from "../ui/Content.js";
import Title from "../ui/Title.jsx";

/**
 * Error page component displayed when a page is not found.
 * This component provides a friendly error message to the user, telling them that something went wrong.
 * It also includes a "Home" button that allows the user to navigate back to the homepage.
 * The error page uses animations to make the experience more dynamic and engaging.
 *
 * @component
 * @returns {JSX.Element} The error page with animated elements and a home button.
 */
function Error() {
    const ref = useRef();
    const handleLinkClick = useHandleLinkClick();

    useGSAP(() => {
        gsap.to(ref.current, { x: 0, opacity: 1, duration: .3, ease: "power2.inOut", });
    });

    return (
        <AnimationContainer>
            <Background>
              <DMTY
                  speechBubbleText="Oh no... Something went wrong, please try again."
              />
              <Content>
                  <Title firstTitle="Oh no..." secondTitle="What happened?" />
                  <ContentContainer variation="center">
                      <ContentBackground ref={ref}>
                          <h2>Page was not found, please go back🤯</h2>
                          <Button label="Home" onClick={() => handleLinkClick(null, "/home")}/>
                      </ContentBackground>
                  </ContentContainer>
              </Content>
            </Background>
        </AnimationContainer>
    );
}

export default Error;
