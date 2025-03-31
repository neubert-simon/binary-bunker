import styled from "styled-components";

/**
 * The InputWrapper component is a styled div that wraps an input element,
 * adding visual enhancements like a border around the input field.
 * It also ensures that pointer events (like clicks) do not interfere with the styling.
 *
 * @component
 * @example
 * <InputWrapper>
 *   <input type="text" />
 * </InputWrapper>
 */
const InputWrapper = styled.div`
  position: relative;
  pointer-events: none;

  &::before {
    content: "";
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: -2rem;
    z-index: 2;
    outline: 0.15rem solid var(--color-white);
    border-radius: var(--border-radius-sm);
    pointer-events: none;
  }
`;

export default InputWrapper;
