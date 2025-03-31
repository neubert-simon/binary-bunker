import {useGSAP} from "@gsap/react";
import gsap from "gsap";

/**
 * Custom hook to perform GSAP animations on `click events`.
 * This hook uses the GSAP library to animate elements with the given `animationProps`.
 * It ensures that the animation happens in a safe `context` with proper cleanup.
 * The animation will run on the provided selector, and an optional callback can be executed once the animation completes.
 *
 * @returns {Object} An object containing the `animate` function that can be used to trigger animations.
 *
 * @example
 * const { animate } = useGSAPClickAnimation();
 * animate('.element', { opacity: 0, duration: 1 }, () => console.log('Animation complete'));
 */
export function useGSAPClickAnimation() {
    const { contextSafe } = useGSAP();

    /**
     * Trigger an animation on the specified element.
     *
     * @param {string} selector The CSS selector of the element to animate.
     * @param {Object} animationProps The properties to animate like { opacity: 0, x: 100, duration: 1 }
     * @param {Function} [onCompleteCallback] An optional callback function that will be called once the animation completes.
     */
    const animate = contextSafe((selector, animationProps, onCompleteCallback) => {
        gsap.to(selector, {
            ...animationProps,
            onComplete: onCompleteCallback,
        });
    });

    return { animate };
}