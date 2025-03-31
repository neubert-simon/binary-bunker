import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.jsx";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import store from "./store";

/**
 * The entry point of the React application.
 * This renders the main application (`App`) inside the DOM element with id "root".
 * It wraps the app in several providers for routing and Redux state management.
 *
 * @function
 * @returns {void}
 */
createRoot(document.getElementById("root")).render(
    <StrictMode>
        <Provider store={store}>
            <BrowserRouter>
                <App />
            </BrowserRouter>
        </Provider>
    </StrictMode>
);
