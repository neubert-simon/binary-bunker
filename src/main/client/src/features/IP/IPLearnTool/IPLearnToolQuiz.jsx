import Title from "../../../ui/Title.jsx";
import ContentContainer from "../../../ui/ContentContainer.js";
import Loading from "../../../ui/Loading.jsx";
import IPLearnToolQuizStart from "./IPLearnToolQuizStart.jsx";
import IPLearnToolQuizContent from "./IPLearnToolQuizContent.jsx";
import IPLearnToolQuizForm from "./IPLearnToolQuizForm.jsx";
import Content from "../../../ui/Content.js";
import {useIPLearnTool} from "./IPLearnToolContext.jsx";
import Retry from "../../../ui/Retry.jsx";

/**
 * IPLearnToolQuiz Component
 * Displays the IP Learn Tool Quiz interface, including loading, quiz start,
 * content, form, and retry options based on the current state of the osi quiz.
 *
 * @returns {JSX.Element} The IPLearnToolQuiz component.
 */
function IPLearnToolQuiz() {
    const {
        data,
        startedLearnTool,
        isLoading,
        isLoadingValidation,
        error,
        handleResetLearnTool,
    } = useIPLearnTool();

    return <Content>
        <Title firstTitle="IP" secondTitle="Learntool" />
        <ContentContainer variation="center">
            { (isLoading || isLoadingValidation) && <Loading /> }
            { !startedLearnTool && <IPLearnToolQuizStart /> }
            { startedLearnTool && error !== null && <Retry resetCallback={handleResetLearnTool} errorMsg={error} /> }
            { startedLearnTool && !isLoading && !isLoadingValidation && data && error === null && <IPLearnToolQuizContent /> }
            { startedLearnTool && error === null && <IPLearnToolQuizForm /> }
        </ContentContainer>
    </Content>
}

export default IPLearnToolQuiz;