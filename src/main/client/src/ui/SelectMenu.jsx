import styled from "styled-components";
import {useState} from "react";

// Styled component for the select menu, including styles for the select button and menu items.
const StyledSelectMenu = styled.div`
    position: relative;
    width: 20rem;
    text-transform: uppercase;
    
    .select {
        background-color: var(--color-blue);
        color: var(--color-white);
        font-size: 2rem;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-radius: var(--border-radius-tiny);
        border: .2rem #2a2f3b solid;
        padding: 1rem;
        cursor: inherit;
        z-index: 50;
        transition: all .3s ease-in-out;
    }
    
    .caret {
        width: 0;
        height: 0;
        border-left: .5rem solid transparent;
        border-right: .5rem solid transparent;
        border-top: .6rem solid var(--color-white);
        transition: all .3s ease-in-out;
    }
    
    .caret.active {
        transform: rotate(180deg);
    }
    
    .menu {
        position: absolute;
        top: 100%;
        left: 50%;
        width: 100%;
        height: 15.5rem;
        overflow-y: scroll;
        margin-top: 1rem;
        list-style: none;
        padding: .2rem .5rem;
        background-color: var(--color-blue);
        border: .1rem #363a43 solid;
        box-shadow: 0 .5rem 1rem rgba(0,0,0,.2);
        border-radius: .5rem;
        color: var(--color-white);
        transform: translateX(-50%) translateY(1rem);
        font-size: 1.8rem;
        opacity: 0;
        pointer-events: none;
        z-index: 60;
        transition: all .3s ease-in-out;
    }
    
    .menu.active {
        opacity: 1;
        transform: translateX(-50%) translateY(0);
        pointer-events: all;
    }
    
    .menu li {
        padding: .5rem 1rem;
        border-radius: var(--border-radius-tiny);
    }
    
    .menu li.active {
        background-color: var(--color-pink-800);
    }

    @media (max-width: 576px) {
        width: 13rem;
        
        .select {
            font-size: 1.3rem;
            padding: .7rem;
        }

        .menu {
            padding: .1rem .5rem;
            font-size: 1.3rem;
        }
    }

    @media (min-width: 577px) and (max-width: 768px) {
        width: 20rem;

        .select {
            font-size: 1.8rem;
            padding: .7rem;
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
 * A custom select menu component that allows the user to choose an option from a list.
 * It displays the selected item and shows a dropdown with options when clicked.
 *
 * @param {Object} props The props passed to the component.
 * @param {string} props.startLabel The label for the initially selected item.
 * @param {Array<string>} props.menuOptions The list of options available in the menu.
 * @param {Function} props.onChangeCallback The callback function that is called when the user selects an option.
 * @param {number} [props.zIndexShift=0] Optional value to shift the z-index of the menu for stacking order.
 *
 * @returns {JSX.Element} The SelectMenu component, including the button and options list.
 */
function SelectMenu({ startLabel, menuOptions, onChangeCallback, zIndexShift = 0 }) {
    const [isOpen, setIsOpen] = useState(false); // State to track whether the menu is open or closed
    const [selectedItem, setSelectedItem] = useState(startLabel); // State to track the currently selected item
    const maxZIndex = 100; // Maximum z-index for the menu (to prevent overlap with other elements)

    /**
     * Toggles the open state of the menu (open or closed) when the select button is clicked.
     */
    function handleOpenClick() {
        setIsOpen(open => !open);
    }

    /**
     * Handles the selection of an option from the menu.
     * Sets the selected item and calls the onChangeCallback.
     *
     * @param {string} selectedOption The option that the user selects from the menu.
     */
    function handleSelection(selectedOption) {
        setSelectedItem(selectedOption);
        setIsOpen(false);
        onChangeCallback(selectedOption);
    }

    return <StyledSelectMenu>
        <div className="select" onClick={handleOpenClick}>
           <span className="selected">{selectedItem}</span>
           <div className={`caret ${isOpen ? "active" : ""}`}></div>
        </div>
        <ul className={`menu ${isOpen ? "active" : ""}`} style={{zIndex: maxZIndex - zIndexShift}}>
            {
                menuOptions.map(option => {
                    return <li
                        key={option}
                        className={`${selectedItem === option ? "active" : ""}`}
                        onClick={() => handleSelection(option)}
                    >
                        {option}
                    </li>
                })
            }
        </ul>
    </StyledSelectMenu>
}

export default SelectMenu;