import { Navigate, Route, Routes, useLocation } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { AnimatePresence } from "framer-motion";
import {Toaster} from "react-hot-toast";
import GlobalSytles from "./styles/GlobalStyles";
import Home from "./pages/Home";
import AppLayout from "./pages/AppLayout";
import IPLanding from "./pages/IPLanding";
import IPv4Calculator from "./pages/IPv4Calculator";
import IPv4Visualizer from "./pages/IPv4Visualizer";
import IPv6Calculator from "./pages/IPv6Calculator";
import IPv6Visualizer from "./pages/IPv6Visualizer";
import IPLearningTool from "./pages/IPLearningTool";
import SubnetVisualizer from "./pages/SubnetVisualizer";
import OSIQuizLanding from "./pages/OSIQuizLanding.jsx";
import Error from "./pages/Error";

/**
 * Create an instance of the React Query client to manage cache and queries across the app.
 * @type {QueryClient}
 */
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 60 * 1000, //The time in milliseconds after data is considered stale. If set to Infinity, the data will never be considered stale.
    },
  },
});

/**
 * Main Application Component
 * - Provides routing, global styles, and query client context for the app.
 * - Handles page transitions with AnimatePresence and routes using React Router.
 *
 * @returns {JSX.Element} The main layout of the app.
 */
function App() {
  const location = useLocation();

  return (
    /*React Query Client Provider to share cache data and fetching functionalities*/
    <QueryClientProvider client={queryClient}>
      {/*React Query Devtools, JUST FOR DEVELOPMENT*/}
      {/*<ReactQueryDevtools initialIsOpen={false} />*/}
      {/*Global CSS Styles like root or default behaviours*/}
      <GlobalSytles />

      {/* Toaster component for displaying notifications */}
      <Toaster
          position="top-center"
          gutter={12}
          containerStyle={{
            margin: "1rem",
          }}
          toastOptions={{
            success: {
              duration: 3000,
            },
            error: {
              duration: 5000,
            },
            style: {
              fontSize: "1.6rem",
              maxWidth: "60rem",
              padding: "1.6rem 2.4rem",
              backgroundColor: "var(--color-gray)",
              color: "var(--color-white)",
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              zIndex: '10000000',
            },
          }}
      />

      {/*Application Routes and Page Transitions*/}
      <AnimatePresence mode="wait">
            <Routes location={location} key={location.pathname}>
                <Route element={<AppLayout />}>
                    <Route index element={<Navigate replace to="home" />} />
                    <Route path="home" element={<Home />} />
                    <Route path="ip" element={<IPLanding />} />
                    <Route path="ipv4-calculator" element={<IPv4Calculator />} />
                    <Route path="ipv4-visualizer" element={<IPv4Visualizer />} />
                    <Route path="ipv6-calculator" element={<IPv6Calculator />} />
                    <Route path="ipv6-visualizer" element={<IPv6Visualizer />} />
                    <Route path="ip-learntool" element={<IPLearningTool />} />
                    <Route path="subnet-visualizer" element={<SubnetVisualizer />} />
                    <Route path="osi-quiz" element={<OSIQuizLanding />} />
                    <Route path="*" element={<Error />} />
                </Route>
            </Routes>
      </AnimatePresence>
    </QueryClientProvider>
  );
}

export default App;
