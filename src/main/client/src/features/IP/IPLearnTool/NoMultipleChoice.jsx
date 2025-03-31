import {useIPLearnTool} from "./IPLearnToolContext.jsx";
import InputText from "../../../ui/InputText.js";
import styled from "styled-components";

const StyledNoMultipleChoice = styled.div`
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    gap: 3rem;
    flex-grow: 1;

    @media (max-width: 576px) {
        gap: 2rem;
        flex-direction: column;
    }

    @media (min-width: 577px) and (max-width: 768px) {
        gap: 2rem;
        flex-direction: column;
    }

    @media (min-width: 769px) and (max-width: 992px) {

    }

    @media (min-width: 993px) and (max-width: 1200px) {

    }

    @media (min-width: 1200px) and (max-width: 1599px) {

    }
`;

const Input = styled.input`
    position: relative;
    width: 30rem;
    height: 4rem;
    padding-left: 2rem;
    font-size: 3rem;
    color: var(--color-white);
    font-family: aubrey;
    text-transform: uppercase;
    border-radius: var(--border-radius-tiny);
    border: none;
    background: linear-gradient(
            to right,
            var(--color-pink-800),
            var(--color-pink-purple-800),
            var(--color-purple-800)
    );

    &:focus {
        outline: none;
    }

    @media (max-width: 576px) {
        width: 13rem;
        height: 2.5rem;
        padding-left: 1rem;
        font-size: 1.5rem;
    }

    @media (min-width: 577px) and (max-width: 768px) {
        width: 25rem;
        height: 3rem;
        padding-left: 1rem;
        font-size: 1.8rem;
    }

    @media (orientation: landscape) and (max-height: 576px) {
        width: 25rem;
        height: 3rem;
        padding-left: 1rem;
        font-size: 1.8rem;
    }
`;

/**
 * Component that renders an input field for questions that require text input.
 *
 * @param {Object} props The component props.
 * @param {number} props.inputFieldAmount The number of input fields required.
 * @returns {JSX.Element} A styled container with input fields.
 */
function NoMultipleChoice({ inputFieldAmount }) {
    const {userAnswers, setUserAnswers} = useIPLearnTool();
    const placeholders = ["IP Address", "Prefix"];

    function handleInputChange(index, value) {
        const updatedAnswers = [...userAnswers];
        updatedAnswers[index] = value;
        setUserAnswers(updatedAnswers);
    }

    return (
        <StyledNoMultipleChoice>
            {Array.from({ length: inputFieldAmount }).map((_, index) => (
                <Input
                    key={index}
                    type="text"
                    value={userAnswers[index] || ""}
                    onChange={(e) => handleInputChange(index, e.target.value)}
                />
            ))}
        </StyledNoMultipleChoice>
    );
}

export default NoMultipleChoice;