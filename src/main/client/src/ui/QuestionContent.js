import styled from "styled-components";

// Styled Component for "QuestionContent"-Container
const QuestionContent = styled.div`
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: start;
    align-items: center;
    flex-direction: column;
    gap: 2rem;
    overflow-y: auto;
    overflow-x: hidden;
    padding: .5rem;
`;

export default QuestionContent;