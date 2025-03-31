import StartMenu from "../../ui/StartMenu.js";
import styled from "styled-components";
import {useOsiQuiz} from "./OsiQuizContext.jsx";
import SelectMenu from "../../ui/SelectMenu.jsx";
import {useGSAPClickAnimation} from "../../hooks/useGSAPClickAnimation.js";
import {useRef} from "react";
import {useQuery} from "@tanstack/react-query";
import Loading from "../../ui/Loading.jsx";
import {useGSAP} from "@gsap/react";
import gsap from "gsap";

/**
 * Styled container for the options section, which will hold the select menus.
 * It uses Flexbox for layout and adjusts for different screen sizes.
 */
const Options = styled.div`
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 3rem;
    width: 100%;

    @media (max-width: 576px) {
        flex-direction: column;
        gap: 2rem;
    }

    @media (min-width: 577px) and (max-width: 768px) {
        flex-direction: column;
        gap: 2rem;
    }

    @media (min-width: 769px) and (max-width: 992px) {
        flex-direction: column;
        gap: 2rem;
    }

    @media (min-width: 993px) and (max-width: 1200px) {
        
    }

    @media (min-width: 1200px) and (max-width: 1599px) {
        
    }
`;

/**
 * The component that renders the start menu for the OSI Quiz.
 * It allows users to select quiz settings like difficulty, layer, and question type.
 *
 * @returns {JSX.Element} The start menu with options to choose for the quiz.
 */
function OsiQuizStart() {
    const {
        dispatch,
        difficulty,
        layer,
        questionType,
        handleStartQuiz
    } = useOsiQuiz();
    const { animate } = useGSAPClickAnimation(); // Custom hook for GSAP click animation
    const ref = useRef();
    // Fetches the categories of the quiz to show them in the selection
    const { data, error, isLoading } = useQuery({
        queryKey: ["quiz-options"],
        queryFn: async () => {
            const resp = await fetch("http://localhost:8080/api/v1/categories");

            if (!resp.ok) {
                const errorData = await resp.json();
                throw new Error(errorData.message || "Unknown error occurred");
            }

            const data = await resp.json();
            return data;
        },
    });

    const {
        Difficulty,
        OSILayer,
        QuestionTypeMC
    } = data || {}; // Destructs the retrieved categories, so we can select the options on the start screen

    // Event handler to change the difficulty setting
    function handleDifficultyChange(difficulty) {
        dispatch({ type: "setDifficulty", payload: difficulty });
    }

    // Event handler to change the layer setting
    function handleLayerChange(layer) {
        dispatch({ type: "setLayer", payload: layer });
    }

    // Event handler to change the question type setting
    function handleQuestionTypeChange(questionType) {
        dispatch({ type: "setQuestionType", payload: questionType });
    }

    // Trigger the start quiz action with animation
    function handleStartQuizClick() {
        animate(
            ref.current,
            {
                x: 100,
                opacity: 0,
                duration: .3,
                ease: "power2.inOut",
            },
            () => {
                handleStartQuiz();
            }
        );
    }

    // GSAP hook to animate the start menu on page load
    useGSAP(() => {
        gsap.to(ref.current, { x: 0, opacity: 1, duration: .3, ease: "power2.inOut", });
    }, [isLoading]);

    // Show loading spinner if data is still being fetched
    if(isLoading) return <Loading />

    return <StartMenu ref={ref}>
        <h2 className="startmenu-header">Welcome to the OSI Quiz, what do you want to learn?</h2>
        <Options>
            <SelectMenu
                startLabel="Difficulty"
                menuOptions={Difficulty}
                onChangeCallback={handleDifficultyChange}
            />
            <SelectMenu
                startLabel="Layer"
                menuOptions={OSILayer}
                onChangeCallback={handleLayerChange}
                zIndexShift={1}
            />
            <SelectMenu
                startLabel="Question Type"
                menuOptions={QuestionTypeMC}
                onChangeCallback={handleQuestionTypeChange}
                zIndexShift={2}
            />
        </Options>
        <div className="startBtn" onClick={handleStartQuizClick}>Start</div>
    </StartMenu>
}

export default OsiQuizStart;