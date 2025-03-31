import { motion } from "framer-motion";

/**
 * AnimationContainer wraps its children with a smooth transition effect
 * using Framer Motion. It handles animations when components mount, update, and unmount.
 *
 * @component
 * @param {Object} props - Component properties.
 * @param {React.ReactNode} props.children - The content to be wrapped inside the animated container.
 * @example
 * return (
 *   <AnimationContainer>
 *     <YourComponent />
 *   </AnimationContainer>
 * )
 */
function AnimationContainer({ children }) {
  return (
    <motion.div
      className="page-layout"
      initial={{ y: "50%", opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      exit={{ y: "-50%", opacity: 0 }}
      transition={{ duration: 0.5, ease: "easeInOut" }}
    >
      {children}
    </motion.div>
  );
}

export default AnimationContainer;
