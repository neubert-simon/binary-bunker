import styled from "styled-components";

/**
 * StyledMultipleChoices is a styled container component that wraps all the
 * multiple-choice options in a flexbox layout. It ensures that the options
 * are centered, aligned, and spaced properly across different screen sizes.
 *
 * @returns {JSX.Element} A flex container that wraps and styles the multiple-choice options.
 */
const StyledMultipleChoices = styled.div`
    position: relative;
    width: 90%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    gap: 2rem;
    flex-shrink: 0;

    @media (max-width: 576px) {
        width: 100%;
    }
`;

export default StyledMultipleChoices;