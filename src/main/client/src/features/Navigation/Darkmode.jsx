import {useDarkmode} from "../../hooks/useDarkmode.js";
import styled from "styled-components";

/**
 * Styled component for the dark mode toggle switch.
 */
const StyledDarkMode = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    
    label {
        position: relative;
        width: 5.5rem;
        height: 2.5rem;
        display: block;
        background-color: var(--color-blue);
        border-radius: 20rem;
        cursor: inherit;
    }
    
    label::after {
        content: "";
        position: absolute;
        top: .25rem;
        left: .3rem;
        width: 2rem;
        height: 2rem;
        background: linear-gradient(
                180deg,
                #FFF0F0,
                #F92A95
        );
        border-radius: 100%;
        transition: all .3s;
    }
    
    input {
        width: 0;
        height: 0;
        visibility: hidden;
    }
    
    input:checked + label {
        background-color: var(--color-white);
    }
    
    input:checked + label::after {
        left: calc(5.5rem - .3rem);
        transform: translateX(-100%);
        background: linear-gradient(
                180deg,
                var(--color-pink-strong),
                var(--color-purple-strong)
        );
    }
    
    label:active::after {
        width: 3rem;
    }

    @media (max-width: 576px) {
        label {
            width: 4rem;
            height: 2rem;
        }

        label::after {
            top: .1rem;
            left: .1rem;
            width: 1.8rem;
            height: 1.8rem;
        }

        input:checked + label::after {
            left: calc(4rem - .1rem);
        }

        label:active::after {
            width: 3rem;
        }
    }
`;

/**
 * Dark mode toggle component.
 * Uses a custom hook useDarkmode to manage dark mode state.
 *
 * @returns {JSX.Element} The dark mode switch component.
 */
function Darkmode() {
    const { darkMode, toggleDarkMode } = useDarkmode(); // Retrieve dark mode state and toggle function from the custom hook

    return <StyledDarkMode>
        <input
            type="checkbox"
            id="darkmode"
            onChange={(e) => toggleDarkMode()}
            checked={darkMode}
        />
        <label htmlFor="darkmode"></label>
    </StyledDarkMode>
}

export default Darkmode;