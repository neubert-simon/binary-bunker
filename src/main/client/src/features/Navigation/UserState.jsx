import {useDispatch, useSelector} from "react-redux";
import styled from "styled-components";
import Darkmode from "./Darkmode.jsx";

// Styled component for the UserState component to control the layout and styles
const StyledUserState = styled.div`
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 3rem;
    
    .state-container {
        display: flex;
        align-items: center;
        gap: 1rem;
    }
    
    .state {
        position: relative;
        width: 5rem;
        height: 5rem;
        color: var(--color-white);
        font-size: 2rem;
        font-family: impact;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .tipps-state {
        background: url("./images/TippsBg.png");
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
    }
    
    .life-state {
        background: url("./images/LifeBg.png");
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
    }
    
    .title {
        font-size: 2rem;
        text-transform: uppercase;
    }
    
    .dark & .title {
        color: var(--color-white);
    }

    @media (max-width: 576px) {
        gap: 1rem;

        .state {
            width: 3rem;
            height: 3rem;
            font-size: 1.3rem;
        }

        .title {
            font-size: 1.5rem;
        }
    }

    @media (min-width: 577px) and (max-width: 768px) {
        gap: 1rem;

        .state {
            width: 3rem;
            height: 3rem;
            font-size: 1.6rem;
        }

        .title {
            font-size: 1.5rem;
        }
    }

    @media (min-width: 769px) and (max-width: 992px) {
        gap: 1rem;

        .state {
            width: 4rem;
            height: 4rem;
            font-size: 1.6rem;
        }

        .title {
            font-size: 1.5rem;
        }
    }

    @media (min-width: 993px) and (max-width: 1200px) {
        gap: 1rem;

        .state {
            width: 4rem;
            height: 4rem;
            font-size: 1.6rem;
        }

        .title {
            font-size: 1.5rem;
        }
    }
`;

/**
 * The UserState component renders the user's state (life points or tips)
 * in a visually appealing way with background images and a responsive layout.
 *
 * It uses Redux to access the state values for 'tipps' and 'life'
 * and dynamically displays the relevant data.
 *
 * @returns {JSX.Element} The rendered user state Life points along with Darkmode toggle.
 */
function UserState() {
    const { tipps, life } = useSelector((state) => state.userState); // Get the current user state from Redux

    return <StyledUserState>
        {/*<div className="state-container">
            <div className="state tipps-state">{tipps}</div>
            <h3 className="title">Tipps</h3>
        </div>*/}
        <div className="state-container">
            <div className="state life-state">{life}</div>
            <h3 className="title">Life</h3>
        </div>
        <Darkmode />
    </StyledUserState>
}

export default UserState;