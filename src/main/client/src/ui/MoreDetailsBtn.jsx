import styled from "styled-components";

// Styled-Component für den MoreDetails Button
const StyledMoreDetailsBtn = styled.div`
    position: absolute;
    bottom: -2rem;
    right: -1rem;
    width: 14rem;
    height: 4rem;
    border-radius: var(--border-radius-tiny);
    background: linear-gradient(to right, #293555, #0a0b0d);
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
    
    .details-text {
        position: absolute;
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 2rem;
        color: var(--color-white);
        font-family: aubrey;
        text-transform: uppercase;
        transition: all .3s ease-out;
    }
    
    .more-details {
        top: -100%;
        opacity: 0;
    }
    
    .more-details.active {
        top: 0;
        opacity: 1;
    }

    .less-details {
        top: 100%;
        opacity: 0;
    }

    .less-details.active {
        top: 0;
        opacity: 1;
    }

    @media (max-width: 576px) {
        width: 10rem;
        height: 3rem;

        .details-text {
            font-size: 1.5rem;
        }
    }

    @media (min-width: 577px) and (max-width: 768px) {
        width: 13rem;
        height: 3.5rem;

        .details-text {
            font-size: 1.8rem;
        }
    }

    @media (min-width: 769px) and (max-width: 992px) {

    }

    @media (min-width: 993px) and (max-width: 1200px) {

    }

    @media (min-width: 1200px) and (max-width: 1599px) {

    }
`;

/**
 * MoreDetailsBtn component renders a button that toggles between "More Details" and "Less Details"
 * based on the current page state.
 *
 * @component
 * @param {Object} props The component props.
 * @param {Function} props.handleDetailsClick Function to handle click events on the button.
 * @param {number} props.page The current page state (1 for "More Details", 2 for "Less Details").
 * @returns {JSX.Element} A styled button component.
 */
function MoreDetailsBtn({handleDetailsClick, page}) {
    return <StyledMoreDetailsBtn
        onClick={handleDetailsClick}
    >
        <div className={`more-details details-text ${page === 1 ? "active" : ""}`}>More Details</div>
        <div className={`less-details details-text ${page === 2 ? "active" : ""}`}>Less Details</div>
    </StyledMoreDetailsBtn>
}

export default MoreDetailsBtn;