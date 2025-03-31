import styled from "styled-components";
import { useEffect, useRef, useState } from "react";
import { useGSAP } from "@gsap/react";
import gsap from "gsap";

/**
 * Styled component for the DMTY character with speech bubble.
 * The video animation and speech bubble are included in this container.
 */
const StyledDMTY = styled.div`
    position: absolute;
    height: 26rem;
    width: 26rem;
    z-index: 100;
    pointer-events: none;
    top: -13rem;
    left: 50%;
    transform: translateX(-50%);

    video {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: contain;
    }

    .speech-bubble {
        pointer-events: none;
        transform-origin: center left;
        transform: scale(0);
    }

    .speech-bubble-content {
        position: absolute;
        left: 100%;
        top: 12rem;
        width: 55rem;
        height: 15rem;
        background-color: var(--color-black);
        border-radius: var(--border-radius-lg);
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .speech-bubble-content::before {
        content: '';
        position: absolute;
        top: 50%;
        left: -2rem;
        width: 0;
        height: 0;
        border: 3rem solid transparent;
        border-bottom-color: var(--color-black);
        border-top: 0;
        border-left: 0;
        margin-left: -0.43rem;
        margin-top: -2.2rem;
        transform: rotate(180deg);
    }

    .speech-bubble__content {
        position: relative;
        width: 80%;
        display: flex;
        justify-content: center;
        align-items: center;
        color: var(--color-white);
        font-size: 1.8rem;
        text-transform: uppercase;
    }

    .speech-bubble-overlay {
        position: absolute;
        left: calc(100% + 1rem);
        top: 13rem;
        width: 55rem;
        height: 15rem;
        border-radius: var(--border-radius-lg);
        outline: 0.15rem solid #fff;
        z-index: 3;
    }

    .speech-bubble-overlay-triangle {
        position: absolute;
        top: 95%;
        left: 52%;
        width: 2.4rem;
        height: 2.8rem;
        background-color: var(--color-black);
        border-left: 0.2rem solid #fff;
        border-top: 0.2rem solid #fff;
        transform: scale(0);
        z-index: 5;
    }

    @media (max-width: 576px) {
        height: 13rem;
        width: 13rem;
        top: -6.5rem;

        .speech-bubble {
            position: absolute;
            transform-origin: top center;
        }

        .speech-bubble-content {
            position: absolute;
            left: 50%;
            top: 12rem;
            transform: translateX(-50%);
            width: 25rem;
            height: 8rem;
            border-radius: var(--border-radius-sm);
        }

        .speech-bubble-content::before {
            content: '';
            position: absolute;
            top: 0;
            left: 55%;
            border: 1.5rem solid transparent;
            border-bottom-color: var(--color-black);
            border-top: 0;
            border-left: 0;
            margin-left: -0.43rem;
            margin-top: -1rem;
            transform: translateX(-50%);
        }

        .speech-bubble__content {
            width: 80%;
            font-size: 1.2rem;
            text-align: justify;
            word-break: break-word;
        }

        .speech-bubble-overlay {
            left: 50%;
            top: 12.3rem;
            transform: translateX(-50%);
            width: 25rem;
            height: 8rem;
            border-radius: var(--border-radius-sm);
        }
    }

    @media (min-width: 577px) and (max-width: 768px) {
        height: 18rem;
        width: 18rem;
        top: -10rem;

        .speech-bubble {
            position: absolute;
            transform-origin: top center;
        }

        .speech-bubble-content {
            position: absolute;
            left: 50%;
            top: 16rem;
            transform: translateX(-50%);
            width: 35rem;
            height: 8rem;
            border-radius: var(--border-radius-sm);
        }

        .speech-bubble-content::before {
            content: '';
            position: absolute;
            top: 0;
            left: 55%;
            border: 1.5rem solid transparent;
            border-bottom-color: var(--color-black);
            border-top: 0;
            border-left: 0;
            margin-left: -0.43rem;
            margin-top: -1rem;
            transform: translateX(-50%);
        }

        .speech-bubble__content {
            width: 80%;
            font-size: 1.2rem;
            text-align: justify;
            word-break: break-word;
        }

        .speech-bubble-overlay {
            left: 50%;
            top: 16.3rem;
            transform: translateX(-50%);
            width: 35rem;
            height: 8rem;
            border-radius: var(--border-radius-sm);
        }
    }

    @media (min-width: 769px) and (max-width: 992px) {
        height: 20rem;
        width: 20rem;
        top: -10rem;

        .speech-bubble-content {
            top: 12rem;
            width: 27rem;
            height: 10rem;
            border-radius: var(--border-radius-sm);
        }

        .speech-bubble-content::before {
            margin-left: .5rem;
        }

        .speech-bubble__content {
            font-size: 1.3rem;
        }

        .speech-bubble-overlay {
            left: calc(100% + .5rem);
            top: 12.5rem;
            width: 27rem;
            height: 10rem;
            border-radius: var(--border-radius-sm);
        }
    }

    @media (min-width: 993px) and (max-width: 1430px) {
        height: 20rem;
        width: 20rem;
        top: -10rem;

        .speech-bubble-content {
            top: 12rem;
            width: 35rem;
            height: 13rem;
            border-radius: var(--border-radius-sm);
        }

        .speech-bubble-content::before {
            margin-left: .5rem;
        }

        .speech-bubble__content {
            font-size: 1.5rem;
        }

        .speech-bubble-overlay {
            left: calc(100% + .5rem);
            top: 12.5rem;
            width: 35rem;
            height: 13rem;
            border-radius: var(--border-radius-sm);
        }
    }

    @media (orientation: landscape) and (max-height: 576px) {
        height: 13rem;
        width: 13rem;
        top: -5.5rem;

        .speech-bubble-content {
            top: 6rem;
            width: 25rem;
            height: 8rem;
            border-radius: var(--border-radius-sm);
        }

        .speech-bubble-content::before {
            margin-left: .5rem;
        }

        .speech-bubble__content {
            font-size: 1rem;
        }

        .speech-bubble-overlay {
            left: calc(100% + .5rem);
            top: 6.5rem;
            width: 25rem;
            height: 8rem;
            border-radius: var(--border-radius-sm);
        }
    }
`;

/**
 * DMTY Component displaying an animated video with a speech bubble.
 * The video source is dynamically selected based on the browser type (Safari or others).
 *
 * @component
 * @param {Object} props - Component properties.
 * @param {string} props.speechBubbleText - The text to display inside the speech bubble.
 * @example
 * return (
 *   <DMTY speechBubbleText="Hello! I am DMTY!" />
 * )
 */
function DMTY({ speechBubbleText }) {
    const ref = useRef();
    const videoRef = useRef();
    const [videoSrc, setVideoSrc] = useState(""); // Controls video source dynamically

    useEffect(() => {
        // Detect if the browser is Safari to select the correct video format
        const isSafari = /^((?!chrome|android).)*safari/i.test(navigator.userAgent);
        const src = isSafari ? "./videos/DMTY.mov" : "./videos/DMTY-Chrome.webm";
        setVideoSrc(src);

        const video = videoRef.current;
        if (!video) {
            console.warn("Kein videoRef vorhanden");
            return;
        }

        // Ensure the video can play and start automatically
        const handleCanPlayThrough = async () => {
            console.log("Video kann abgespielt werden");
            try {
                video.muted = true;
                await video.play();
            } catch (err) {
                console.error("Video konnte nicht abgespielt werden", err);
            }
        };

        video.addEventListener("canplaythrough", handleCanPlayThrough);

        return () => {
            video.removeEventListener("canplaythrough", handleCanPlayThrough);
        };
    }, []);

    // GSAP animations for speech bubble appearance
    useGSAP(() => {
        const mm = gsap.matchMedia();

        mm.add("(max-width: 768px)", () => {
            gsap.fromTo(
                ".speech-bubble",
                { scale: 0 },
                { scale: 1, duration: 0.3, ease: "power2.inOut", delay: 0.5 }
            );
        });

        mm.add("(min-width: 769px)", () => {
            gsap.to(".speech-bubble", { transform: "scale(1)", duration: .3, ease: 'power2.inOut', delay: .5 });
        });

        return () => mm.kill();
    }, {scope: ref.current});

    return (
        <StyledDMTY ref={ref}>
            <video ref={videoRef} muted autoPlay loop playsInline preload="auto" poster="./images/DMTY.png">
                <source key={videoSrc} src={videoSrc} type={videoSrc.includes(".mov") ? "video/mp4" : "video/webm"} />
            </video>
            <div className="speech-bubble-content speech-bubble">
                <div className="speech-bubble__content">{speechBubbleText}</div>
            </div>
            <div className="speech-bubble-overlay speech-bubble"></div>
        </StyledDMTY>
    );
}

export default DMTY;
