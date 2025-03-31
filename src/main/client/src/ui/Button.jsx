import styled from "styled-components";

/**
 * StyledButton is a custom button component styled with `styled-components`.
 * It is a flexible button with hover effects, a gradient background, and smooth transitions.
 *
 * @component
 * @param {Object} props - The props for the button component.
 * @param {string} props.label - The label text to display on the button.
 * @param {function} props.onClick - The callback function to handle button click events.
 * @example
 * <Button label="Click Me" onClick={() => alert("Button clicked!")} />
 */
const StyledButton = styled.button`
    position: relative;
    width: 14rem;
    height: 4rem;
    border: none;
    outline: none;
    border-radius: var(--border-radius-tiny);
    background: linear-gradient(to right, #293555, #0a0b0d);
    color: var(--color-white);
    font-size: 3rem;
    cursor: inherit;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 10;
    overflow: hidden;
    transition: all .15s ease-out;

    &:hover {
        transform: translateY(-.2rem) scale(1.02);
    }
`;

/**
 * Button component that renders a styled button with hover effects.
 *
 * @param {Object} props - The button props
 * @param {string} props.label - The label to display inside the button
 * @param {function} props.onClick - The function to execute when the button is clicked
 *
 * @returns {JSX.Element} The rendered button component.
 */
function Button({ label, onClick}) {
    return <StyledButton onClick={() => onClick()}>
        {label}
    </StyledButton>
}

export default Button;