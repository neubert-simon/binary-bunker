import {createContext, useCallback, useContext, useReducer, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useGSAPClickAnimation} from "../../hooks/useGSAPClickAnimation.js";
import {useMutation} from "@tanstack/react-query";
import { getOsiQuizQuestion} from "../../services/fetchData.js";
import toast from "react-hot-toast";
import {addLife, reduceLife} from "../../services/userStatesSlice.js";

/**
 * Context for the OSI Quiz.
 * Manages the quiz state and provides functions to
 * interact with the quiz.
 * @module OsiQuizContext
 */
const OsiQuizContext = createContext();

/**
 * Initial state for the quiz.
 * @type {Object}
 * @property {Object} data The data for the current quiz question.
 * @property {string} difficulty The difficulty level of the quiz.
 * @property {string} layer The OSI model layer being queried.
 * @property {string} questionType The type of quiz question (e.g., Multiple Choice).
 * @property {boolean} hasValidated Indicates whether the answer has been validated.
 * @property {boolean} startedOsiQuiz Indicates whether the quiz has started.
 * @property {string|null} error Error message in case of an error.
 */
const initialState = {
    data: {},
    difficulty: "",
    layer: "",
    questionType: "",
    hasValidated: false,
    startedOsiQuiz: false,
    error: null,
}

/**
 * Reducer for managing the quiz state.
 * @param {Object} state The current state.
 * @param {Object} action The action that should modify the state.
 * @returns {Object} The new state after applying the action.
 */
function reducer(state, action) {
    switch (action.type) {
        case "receivedData":
            return { ...state, data: action.payload };
        case "setError":
            return { ...state, error: action.payload };
        case "setDifficulty":
            return { ...state, difficulty: action.payload };
        case "setQuestionType":
            return { ...state, questionType: action.payload };
        case "setLayer":
            return { ...state, layer: action.payload };
        case "startedOsiQuiz":
            return { ...state, startedOsiQuiz: true };
        case "validate":
            return { ...state, hasValidated: action.payload };
        case "reset":
            return initialState;
        default:
            throw new Error("Unknown action type");
    }
}

/**
 * Provider for the OSI Quiz context.
 * Provides all states and actions required for interacting with the quiz.
 * @param {Object} props The props for the provider.
 * @param {ReactNode} props.children The child components that can access the context.
 * @returns {JSX.Element} The OsiQuizProvider that provides the context.
 */
function OsiQuizProvider({ children }) {
    const [{ data, startedOsiQuiz, hasValidated, difficulty, layer, questionType, error }, dispatch] = useReducer(reducer, initialState);
    const globalDispatch = useDispatch(); //Global Dispatch
    const currentLife = useSelector(state => state.userState.life); //Global user life state
    const { animate } = useGSAPClickAnimation(); //Custom hook for GSAP click animations
    const [clickedChoices, setClickedChoices] = useState([]); //The choices selected by the user for the current question

    //Mutation for fetching a new quiz question
    const { mutate: getQuestion, isPending: isLoading } = useMutation({
        mutationFn: getOsiQuizQuestion,
        onSuccess: (data) => {
            dispatch({ type: "receivedData", payload: data });
            dispatch({ type: "validate", payload: false });
            dispatch({ type: "setError", payload: null });
            setClickedChoices([])
            toast.success("Here is your question");
        },
        onError: (error) => {
            dispatch({ type: "setError", payload: error.message });
            toast.error(error.message);
        }
    });

    /**
     * Function to skip the current question and fetch a new one.
     * @returns {void}
     */
    const handleSkipQuestionClick = useCallback(() => {
        if (currentLife === 0) return;  //Prevent going forward if no lives are left.

        if (hasValidated) return;  //Prevent skipping a question if the answer has already been validated.

        if (isLoading) return;  //Wait for the request to finish.

        animate(
            '.osiquiz-content',
            {
                x: 100,
                opacity: 0,
                duration: .3,
                ease: "power2.inOut",
            },
            () => {
                globalDispatch(reduceLife()); //Reduce the use life state.
                getQuestion({ difficulty, osilayer: layer, questionTypeMC: questionType });
            }
        );
    }, [currentLife, hasValidated, globalDispatch, getQuestion, animate, difficulty, layer, questionType]);

    /**
     * Function to move to the next question.
     * @returns {void}
     */
    const handleNextQuestionClick = useCallback(() => {
        if (!hasValidated) return;  //Ensure that the answer has been validated.

        if (isLoading) return;  //Wait for the request to finish.

        animate(
            '.osiquiz-content',
            {
                x: 100,
                opacity: 0,
                duration: .3,
                ease: "power2.inOut",
            },
            () => {
                getQuestion({ difficulty, osilayer: layer, questionTypeMC: questionType });
            }
        );
    }, [hasValidated, getQuestion, animate, difficulty, layer, questionType]);

    /**
     * Function to start the quiz.
     * @returns {void}
     */
    const handleStartQuiz = useCallback(() => {
        dispatch({ type: "startedOsiQuiz" });
        dispatch({ type: "setError", payload: null });
        getQuestion({ difficulty, osilayer: layer, questionTypeMC: questionType });
    }, [getQuestion, difficulty, layer, questionType]);

    /**
     * Function to add a choice for the answer.
     * @param {string} choice - The choice made by the player.
     * @returns {void}
     */
    const handleAddChoiceClick = useCallback((choice) => {
        const correctAnswersAmount = data?.correctAnswerKeys.length || 0;

        //Check if the user has already made this choice
        if(clickedChoices.includes(choice)) {
            setClickedChoices(choices => choices.filter(item => item !== choice));
            return;
        }

        //Check if the maximum number of answers has been exceeded
        if(clickedChoices.length >= correctAnswersAmount) {
            toast.error("You have reached the max amount of answers to choose, delete one or validate your answers!");
            return;
        }

        setClickedChoices(choices => [...choices, choice]); //Add the selected choice
    }, [data, clickedChoices]);

    /**
     * Function to validate the selected answers.
     * @returns {void}
     */
    const handleValidationClick = useCallback(() => {
        if (hasValidated) return;  //Ensure the answer has not been validated yet.

        if (isLoading) return;  //Wait for the request to finish.

        const correctAnswers = data?.correctAnswerKeys || [];
        const rightUserAnswersAmount = clickedChoices.filter(choice => correctAnswers.includes(choice)).length;
        const correctAnswersAmount = correctAnswers.length;

        dispatch({ type: "validate", payload: true });
        globalDispatch(addLife()); //Add life to the user state

        const message = `You answered ${rightUserAnswersAmount} from ${correctAnswersAmount} right`;
        if (rightUserAnswersAmount >= 1) {
            toast.success(message); //Toast for correct answers
        }
        else {
            toast.error(message); //Toast for wrong answers
        }
    }, [clickedChoices, hasValidated, data, dispatch]);

    /**
     * Function to reset the quiz.
     * @returns {void}
     */
    const handleResetQuiz = useCallback(() => {
        dispatch({ type: "reset" });
    }, []);

    return (
        <OsiQuizContext.Provider
            value={{
                data,
                error,
                hasValidated,
                isLoading,
                startedOsiQuiz,
                difficulty,
                layer,
                questionType,
                handleStartQuiz,
                handleSkipQuestionClick,
                handleNextQuestionClick,
                handleAddChoiceClick,
                handleValidationClick,
                clickedChoices,
                handleResetQuiz,
                dispatch
            }}
        >
            {children}
        </OsiQuizContext.Provider>
    );
}

/**
 * Custom hook to access the OSI Quiz context.
 * @returns {Object} The context value for the OSI quiz.
 * @throws {Error} If the hook is used outside the `OsiQuizProvider`.
 */
function useOsiQuiz() {
    const context = useContext(OsiQuizContext);

    if (context === undefined) {
        throw new Error("useOsiQuiz must be used within an OsiQuizProvider");
    }

    return context;
}

export { OsiQuizProvider, useOsiQuiz };