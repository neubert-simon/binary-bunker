import {useState, useEffect} from "react";

/**
 * Custom Hook to manage dark mode functionality.
 * This hook stores the dark mode preference in `localStorage` and applies
 * the class to the body element to toggle between dark and light modes.
 *
 * @returns {Object} An object containing the current dark mode state and a function to toggle it.
 */
export function useDarkmode() {
    // State to track if dark mode is enabled. Defaults to value in localStorage or system preference.
    const [darkMode, setDarkMode] = useState(function () {
        const storedDarkMode = localStorage.getItem("darkmode");

        // Check if a dark mode preference exists in local storage.
        if (storedDarkMode !== null) {
            return storedDarkMode === "true";
        }

        // If no preference in local storage, check the system's dark mode preference
        const systemPrefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;
        return systemPrefersDark;
    });

    // useEffect hook to update the DOM and localStorage whenever darkMode changes
    useEffect(() => {
        if (darkMode) {
            document.body.classList.add("dark");
        }
        else {
            document.body.classList.remove("dark");
        }

        localStorage.setItem("darkmode", darkMode);
    }, [darkMode]);

    /**
     * Function to toggle the dark mode state.
     * Switches the dark mode from true to false or vice versa.
     */
    function toggleDarkMode() {
        setDarkMode(darkMode => !darkMode);
    }

    return {
        darkMode,
        toggleDarkMode,
    }
}