import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import gsap from "gsap";
import { updateIsAnimating, updateIsOpen } from "../services/navigationSlice";

/**
 * Custom hook that handles navigation and animation when a link is clicked.
 * This function checks whether the navigation menu is open or animating,
 * then performs an animation for closing the menu before navigating to the new route.
 *
 * @returns {Function} The `handleLinkClick` function that can be used to handle link clicks and navigation.
 */
export function useHandleLinkClick() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  // Get the global state of the navigation
  const { isOpen, isAnimating } = useSelector((state) => state.navigation);

  /**
   * Handles the link click event and performs navigation with animation.
   * If the menu is open and not already animating, it will animate the menu closing,
   * then navigate to the desired path.
   * If the menu is not open, it will directly navigate to the path.
   *
   * @param {Event|null} e The event triggered by the click (optional, can be null).
   * @param {string} path The path to navigate to after the animation (if any).
   */
  function handleLinkClick(e = null, path) {
    if (isAnimating) return;

    if(e) e.preventDefault();

    if (isOpen) {
      dispatch(updateIsOpen(false));

      const timeline = gsap.timeline({ ease: "power2.inOut" });
      dispatch(updateIsAnimating(true));

      timeline
        .to(".navigation-link", {
          opacity: 0,
        })
        .to(".navigation-menu-large", {
          transform: "scale(0)",
          duration: 0.15,
        })
        .to(".navigation-menu-middle", {
          transform: "scale(0)",
          duration: 0.15,
        })
        .to(".navigation-menu-small", {
          transform: "scale(0)",
          duration: 0.15,
        })
        .eventCallback("onComplete", () => {
          dispatch(updateIsAnimating(false));
          navigate(path);
        });
    } else {
      navigate(path);
    }
  }

  return handleLinkClick;
}
