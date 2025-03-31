import gsap from "gsap";
import { updateIsAnimating } from "./navigationSlice";

/**
 * Animates the navigation menu's open/close transition using GSAP.
 * This function controls the animation of the navigation menu when it's toggled open or closed.
 * It dispatches actions to manage the animation state in Redux.
 *
 * @param {Object} param0 The parameters object.
 * @param {boolean} param0.isOpen The current state of the navigation menu (open or closed).
 * @param {function} param0.setHasClicked Function to set whether the menu was clicked or not.
 * @param {function} param0.dispatch Redux dispatch function to update the animation state.
 */
export function animateNavigation({ isOpen, setHasClicked, dispatch }) {
    const timeline = gsap.timeline({ ease: "power2.inOut" });

    dispatch(updateIsAnimating(true));

    // If the menu is being opened, animate the scaling and opacity of elements
    if (isOpen) {
        timeline
            .to(".navigation-menu-small", { transform: "scale(1)", duration: 0.15 })
            .to(".navigation-menu-middle", { transform: "scale(1)", duration: 0.15 })
            .to(".navigation-menu-large", { transform: "scale(1)", duration: 0.15 })
            .to(".navigation-link", { opacity: 1 })
            .eventCallback("onComplete", () => {
                setHasClicked(false);
                dispatch(updateIsAnimating(false));
            });
    } else {
        // If the menu is being closed, animate the opacity and scaling to hide the menu
        timeline
            .to(".navigation-link", { opacity: 0 })
            .to(".navigation-menu-large", { transform: "scale(0)", duration: 0.15 })
            .to(".navigation-menu-middle", { transform: "scale(0)", duration: 0.15 })
            .to(".navigation-menu-small", { transform: "scale(0)", duration: 0.15 })
            .eventCallback("onComplete", () => {
                setHasClicked(false);
                dispatch(updateIsAnimating(false));
            });
    }
}
