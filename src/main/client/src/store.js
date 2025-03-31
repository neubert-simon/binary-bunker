import { configureStore } from "@reduxjs/toolkit";
import navigationReducer from "./services/navigationSlice";
import userStateReducer from "./services/userStatesSlice";

/**
 * The Redux store configuration.
 * Combines the `navigationReducer` and `userStateReducer` into a single store.
 * This store will be used throughout the app to manage state.
 *
 * @type {Store} The Redux store.
 */
const store = configureStore({
  reducer: {
    /**
     * Handles the navigation state, which may store information like
     * active navigation links or page transitions.
     * @type {Reducer}
     */
    navigation: navigationReducer,
    /**
     * Handles the user's state, such as login status, preferences, or data.
     * @type {Reducer}
     */
    userState: userStateReducer,
  },
});

export default store;
