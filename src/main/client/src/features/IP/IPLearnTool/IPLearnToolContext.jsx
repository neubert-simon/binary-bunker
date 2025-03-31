import { createContext, useCallback, useContext, useReducer, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useGSAPClickAnimation } from "../../../hooks/useGSAPClickAnimation.js";
import { useMutation } from "@tanstack/react-query";
import { getIPLearntoolQuestion, validateIPLearntoolQuestion } from "../../../services/fetchData.js";
import toast from "react-hot-toast";
import { addLife, reduceLife } from "../../../services/userStatesSlice.js";

/**
 * Context for the IP Learning Tool
 */
const IpLearnToolContext = createContext();

/**
 * Initial state for the reducer
 */
const initialState = {
    data: {},
    userAnswers: [],
    hasValidated: false,
    isLoading: false,
    isLoadingValidation: false,
    startedLearnTool: false,
    error: null,
};

/**
 * Reducer function to handle state updates
 * @param {object} state Current state
 * @param {object} action Action to update state
 * @returns {object} Updated state
 */
function reducer(state, action) {
    switch (action.type) {
        case "receivedData":
            return { ...state, data: action.payload };
        case "setError":
            return { ...state, error: action.payload };
        case "userAnswer":
            return { ...state, userAnswers: [...action.payload] };
        case "startedLearnTool":
            return { ...state, startedLearnTool: true };
        case "validate":
            return { ...state, hasValidated: action.payload };
        case "reset":
            return initialState;
        default:
            throw new Error("Unknown action type");
    }
}

/**
 * Provider component for the IP Learning Tool
 * @param {object} props Component props
 * @param {React.ReactNode} props.children Child components
 */
function IPLearnToolProvider({ children }) {
    const [{ data, userAnswers, startedLearnTool, hasValidated, error }, dispatch] = useReducer(reducer, initialState);
    const [correctAnswer, setCorrectAnswer] = useState(true);
    const globalDispatch = useDispatch();
    const currentLife = useSelector(state => state.userState.life);
    const { animate } = useGSAPClickAnimation();

    /**
     * Fetches a new IP learning tool question
     */
    const { mutate: getQuestion, isPending: isLoading } = useMutation({
        mutationFn: getIPLearntoolQuestion,
        onSuccess: (data) => {
            dispatch({ type: "receivedData", payload: data });
            dispatch({ type: "validate", payload: false });
            dispatch({ type: "setError", payload: null });
            toast.success("Here is your question");
        },
        onError: (error) => {
            dispatch({ type: "setError", payload: error.message });
            toast.error(error.message);
        }
    });

    /**
     * Validates the user's answer for the current question
     */
    const { mutate: validateQuestion, isPending: isLoadingValidation } = useMutation({
        mutationFn: validateIPLearntoolQuestion,
        onSuccess: (validation) => {
            dispatch({ type: "validate", payload: true });
            dispatch({ type: "setError", payload: null });
            globalDispatch(addLife());
            setCorrectAnswer(validation);

            if(!validation) toast.error("Your answer was not correct");
            else toast.success("Your answer was correct");
        },
        onError: (error) => {
            dispatch({ type: "setError", payload: error.message });
            toast.error(error.message);
        }
    });

    /**
     * Skips the current question and fetches a new one
     */
    const handleSkipQuestionClick = useCallback(() => {
        if (currentLife === 0) return;

        if(hasValidated) return;

        animate(
            '.iplearntool-content',
            {
                x: 100,
                opacity: 0,
                duration: .3,
                ease: "power2.inOut",
            },
            () => {
                globalDispatch(reduceLife());
                dispatch({ type: "userAnswer", payload: [] });
                getQuestion();
            }
        );
    }, [currentLife, hasValidated, globalDispatch, getQuestion, animate]);

    /**
     * Moves to the next question after validation
     */
    const handleNextQuestionClick = useCallback(() => {
        if (!hasValidated) return;

        animate(
            '.iplearntool-content',
            {
                x: 100,
                opacity: 0,
                duration: .3,
                ease: "power2.inOut",
            },
            () => {
                getQuestion();
                dispatch({ type: "userAnswer", payload: [] });
            }
        );
    }, [hasValidated, getQuestion, animate]);

    /**
     * Starts the IP Learning Tool
     */
    const handleStartLearnTool = useCallback(() => {
        dispatch({ type: "startedLearnTool" });
        dispatch({ type: "setError", payload: null });
        getQuestion();
    }, [getQuestion]);

    /**
     * Validates the current question
     */
    const handleQuestionValidation = useCallback(() => {
        if(hasValidated) return;

        const payload = {
            questionData: data,
            userAnswers: [...userAnswers],
        };

        animate(
            '.iplearntool-content',
            {
                x: 100,
                opacity: 0,
                duration: .3,
                ease: "power2.inOut",
            },
            () => validateQuestion(payload)
        );

    }, [data, hasValidated, validateQuestion, userAnswers]);

    /**
     * Resets the IP Learning Tool state
     */
    const handleResetLearnTool = useCallback(() => {
        dispatch({ type: "reset" });
    }, []);

    return (
        <IpLearnToolContext.Provider
            value={{
                data,
                error,
                userAnswers,
                hasValidated,
                correctAnswer,
                isLoading,
                isLoadingValidation,
                startedLearnTool,
                handleStartLearnTool,
                handleQuestionValidation,
                handleSkipQuestionClick,
                handleNextQuestionClick,
                setUserAnswers: (answers) => dispatch({ type: "userAnswer", payload: answers }),
                handleResetLearnTool,
                dispatch
            }}
        >
            {children}
        </IpLearnToolContext.Provider>
    );
}

/**
 * Custom hook to use the IP Learning Tool context
 * @returns {object} Context value
 * @throws {Error} If used outside of `IPLearnToolProvider`
 */
function useIPLearnTool() {
    const context = useContext(IpLearnToolContext);

    if (context === undefined) {
        throw new Error("useIPLearnTool must be used within an IPLearnToolProvider");
    }

    return context;
}

export { IPLearnToolProvider, useIPLearnTool };
