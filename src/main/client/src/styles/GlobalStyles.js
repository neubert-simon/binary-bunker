import { createGlobalStyle } from "styled-components";

/**
 * GlobalStyles defines global CSS styles to be applied across the application.
 * This includes custom fonts, colors, box-sizing rules, and other styles
 * that apply universally to all elements.
 */
const GlobalStyles = createGlobalStyle`
    @font-face {
        font-family: impact;
        src: url('fonts/impact.ttf');
    }

    @font-face {
        font-family: aubrey;
        src: url('fonts/Aubrey-Regular.ttf');
    }

    :root {
        --color-pink: #FE6A5A;
        --color-pink-strong: #F92A95;
        --color-pink-purple: #FC536E;
        --color-purple: #F506B4;
        --color-purple-strong: #6157FF;

        --color-pink-800: rgba(254, 106, 90, .8);
        --color-pink-purple-800: rgba(252, 83, 110, .8);
        --color-purple-800: rgba(245, 6, 180, .8);

        --color-orange: #FE9900;
        --color-cream: #FFD1A5;
        --color-brown: #682A00;
        --color-gray: #191E2C;
        --color-blue: #293555;
        --color-red: #E00101;
        --color-green: #09E000;
        --color-black: #0A0B0D;
        --color-white: #FFFAF5;
        --color-main: #FFF0F0; 

        
        //DARKMODE:
        & .dark {
            --color-main: #191E2C;
            --color-pink: #F92A95;
            --color-purple: #6157FF;

            --color-pink-800: rgba(249, 42, 149, .8);
            --color-pink-purple-800: rgba(173, 65, 202, .8);
            --color-purple-800: rgba(97, 87, 255, .8);
        }
        

        --backdrop-color: rgba(255, 255, 255, 0.1);

        --shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.04);
        --shadow-md: 0px 0.6rem 2.4rem rgba(0, 0, 0, 0.06);
        --shadow-lg: 0 2.4rem 3.2rem rgba(0, 0, 0, 0.12);

        --border-radius-mobile-tiny: .5rem;
        --border-radius-tiny: 1rem;
        --border-radius-sm: 2rem;
        --border-radius-md: 4rem;
        --border-radius-lg: 10rem;
    }

    *,
    *::before,
    *::after {
        box-sizing: border-box;
        padding: 0;
        margin: 0;

        /* Creating animations for dark mode */
        transition: background-color 0.3s, border 0.3s;
    }

    html {
        font-size: 62.5%;
    }

    body {
        font-family: aubrey;
        line-height: 1.5;
        cursor: url("./images/Mouse.png"), auto;
        background-color: var(--color-main);
        transition: color 0.3s, background-color 0.3s;
    }

    input,
    button,
    textarea,
    select {
        font: inherit;
        color: inherit;
    }

    button {
        cursor: pointer;
    }

    *:disabled {
        cursor: not-allowed;
    }

    a {
        color: inherit;
        text-decoration: none;
    }

    ul {
        list-style: none;
    }

    p,
    h1,
    h2,
    h3,
    h4,
    h5,
    h6,
    span {
        overflow-wrap: break-word;
        hyphens: auto;
    }
`;

export default GlobalStyles;
