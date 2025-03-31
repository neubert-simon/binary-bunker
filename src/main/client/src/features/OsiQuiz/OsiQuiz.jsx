import Title from "../../ui/Title.jsx";
import Content from "../../ui/Content.js";
import {useOsiQuiz} from "./OsiQuizContext.jsx";
import OsiQuizStart from "./OsiQuizStart.jsx";
import Loading from "../../ui/Loading.jsx";
import ContentContainer from "../../ui/ContentContainer.js";
import OsiQuizContent from "./OsiQuizContent.jsx";
import OsiQuizForm from "./OsiQuizForm.jsx";
import Retry from "../../ui/Retry.jsx";

/**
 * OSI Quiz Component to manage the different quiz states.
 *
 * Depending on the quiz status, different UI elements are rendered:
 * - If the quiz is not started yet, the start screen is shown.
 * - If the quiz is loading, a loading screen is shown.
 * - If an error occurs, a retry button is shown.
 * - If the quiz is running and no errors have occurred, the quiz content and form are displayed.
 *
 * @returns {JSX.Element} The rendered OSI Quiz UI based on the current quiz state.
 */
function OsiQuiz() {
    const {
        startedOsiQuiz,
        isLoading,
        data,
        error,
        handleResetQuiz,
    } = useOsiQuiz();

    return <Content>
        <Title firstTitle="OSI - Quiz" />
        <ContentContainer variation="center">
            { isLoading && <Loading /> }
            { !startedOsiQuiz && <OsiQuizStart /> }
            { startedOsiQuiz && error !== null && <Retry resetCallback={handleResetQuiz} errorMsg={error} /> }
            { startedOsiQuiz && !isLoading && data && error === null && <OsiQuizContent /> }
            { startedOsiQuiz && error === null && <OsiQuizForm /> }
        </ContentContainer>
    </Content>
}

export default OsiQuiz;